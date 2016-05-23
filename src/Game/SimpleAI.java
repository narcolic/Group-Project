package Game;

/**
 * AI determines actions on a turn by turn basis
 * If it is in the lead, keep moving
 * If it is behind, place a wall to block increase opponent distance
 */
public class SimpleAI {
    private int[] turnsToWin;

    private Position bestMove; public Position getBestMove(){return bestMove;}
    private boolean placeFence; public boolean getPlaceFence(){return placeFence;}
    private Position fencePosition; public Position getFencePosition(){return fencePosition;}
    private boolean fenceVertical; public boolean getFenceOrientation(){return fenceVertical;}

    private class FencePlacement{
        public int reward;
        public int cost;
        public boolean invalid;
        public Fence fence;
        public int netWorth()
        {
            if(invalid) return Integer.MIN_VALUE;
            return reward - cost;
        }
    }


    /**
     * Calculates the distance of each pawn to their nearest goal
     * @param board
     */
    public void calculateTurnsToWin(Board board) {
        System.out.println("Calculating turns to win (Current Pawn " + (board.getCurrentPawn().getPawnID()+1) + ")");
        Position[] pos = board.getPawnPositionsArray();
        turnsToWin = new int[pos.length];
        for(int i = 0; i < pos.length; i++) {
            int[][] pathMap = board.generatePathMap(pos[i], board.getPawnGoalsArray(i), board.getFencesArray());
            turnsToWin[i] = pathMap[pos[i].getX()][pos[i].getY()] + i;//first player advantage
            System.out.println("Pawn " + (i + 1) + ": " + turnsToWin[i]);
        }
    }

    /**
     * Attempts to find the best move for the current pawn
     * @param board
     */
    public void calculateMove(Board board) {
        calculateTurnsToWin(board);
        //reset
        bestMove = null;
        placeFence = false;
        fencePosition = null;
        fenceVertical = false;
        Pawn myPawn = board.getCurrentPawn();
        int leadPawnID = myPawn.getPawnID();

        //first see if it is closest to the goal
        int lowest = turnsToWin[myPawn.getPawnID()];
        for(int i = 0; i < turnsToWin.length; i++)
        {
            if (turnsToWin[i] < lowest)
            {
                leadPawnID = i;
                lowest = turnsToWin[i];
            }
        }
        calculateMovePosition(board, myPawn);

        if (leadPawnID != myPawn.getPawnID())
        {//Another pawn is beating me
            calculateFencePlacement(board, myPawn, leadPawnID);
        }
    }

    /**
     * Test placing fences around lead player
     * @param board
     * @param myPawn
     */
    private void calculateFencePlacement(Board board, Pawn myPawn, int leadPawnID) {
        //test place fences around lead player to see what
        //forces largest amount of extra movement for least cost
        //since placing a fence costs 1 turn, that is minimum cost

        Fence testFence;
        //keep track of each move's cost and reward
        FencePlacement[] moves = new FencePlacement[8];
        for(int i = 0; i < moves.length; i++) moves[i] = new FencePlacement();

        testFence(testPlacePosition(board.getPawnPositionsArray()[leadPawnID], 2, false, false, false, board)
                , moves[0], board, myPawn, leadPawnID);
        testFence(testPlacePosition(board.getPawnPositionsArray()[leadPawnID], 2, true, false, false, board)
                , moves[1], board, myPawn, leadPawnID);
        testFence(testPlacePosition(board.getPawnPositionsArray()[leadPawnID], 2, true, true, false, board)
                , moves[2], board, myPawn, leadPawnID);
        testFence(testPlacePosition(board.getPawnPositionsArray()[leadPawnID], 2, false, true, false, board)
                , moves[3], board, myPawn, leadPawnID);
        testFence(testPlacePosition(board.getPawnPositionsArray()[leadPawnID], 2, false, false, true, board)
                , moves[4], board, myPawn, leadPawnID);
        testFence(testPlacePosition(board.getPawnPositionsArray()[leadPawnID], 2, true, false, true, board)
                , moves[5], board, myPawn, leadPawnID);
        testFence(testPlacePosition(board.getPawnPositionsArray()[leadPawnID], 2, true, true, true, board)
                , moves[6], board, myPawn, leadPawnID);
        testFence(testPlacePosition(board.getPawnPositionsArray()[leadPawnID], 2, false, true, true, board)
                , moves[7], board, myPawn, leadPawnID);

        int bestWorth = 0;
        int placementID = -1;
        for(int i = 0; i < moves.length; i++) {
            if(moves[i].invalid) continue;
            if(moves[i].netWorth() > bestWorth)
            {
                bestWorth = moves[i].netWorth();
                placementID = i;
            }
        }
        //placementID = 0;
        if(placementID != -1) {
            placeFence = true;
            fencePosition = moves[placementID].fence.getPosition();
            fenceVertical = moves[placementID].fence.getOrientation();
        }
    }

    /**
     * Finds the net worth of placing a fence here
     * @param fence
     * @param placement
     * @param board
     * @param myPawn
     * @param leadPawnID
     */
    private void testFence(Fence fence, FencePlacement placement, Board board, Pawn myPawn, int leadPawnID) {
        if(fence == null)
        {
            placement.invalid = true;
            return;
        }
        //find the pat hmap of both pawns if fence is placed
        Position leadPawnStart = board.getPawnPositionsArray()[leadPawnID];
        int[][] myModMap = board.generatePathMap(myPawn.getPosition(), myPawn.getGoalTileArray(), board.getFencesArray(fence));
        int[][] leadModMap = board.generatePathMap(leadPawnStart, board.getPawnGoalsArray(leadPawnID), board.getFencesArray(fence));

        //get cost eg. 13 - 10 + 1 = 4 (if it increases its own path distance by 3)
        placement.cost = myModMap[myPawn.getPosition().getX()][myPawn.getPosition().getY()] - turnsToWin[myPawn.getPawnID()] + 1;
        //reward is just increase for pawn
        placement.reward = leadModMap[leadPawnStart.getX()][leadPawnStart.getY()] - turnsToWin[leadPawnID];
        placement.fence = fence;
    }

    /**
     * @param focus
     * @param fenceLength
     * @param makeVertical
     * @return null, or a fence aligned to the focus either to the left or right end, or vertical equivalent
     */
    private Fence testPlacePosition(Position focus, int fenceLength, boolean farEnd, boolean bottomRight, boolean makeVertical, Board board)
    {
        Fence check = null;
        if(!bottomRight) {
            if (!farEnd) {
                check = new Fence(focus, makeVertical);
            } else {
                if (makeVertical) {
                    check = new Fence(new Position(focus, 0, 1 - 2), makeVertical);
                } else {
                    check = new Fence(new Position(focus, 1 - 2, 0), makeVertical);
                }
            }
        } else {
            if (!farEnd) {
                if (makeVertical) {
                    check = new Fence(new Position(focus, 1, 0), makeVertical);
                } else {
                    check = new Fence(new Position(focus, 0, 1), makeVertical);
                }
            } else {
                if (makeVertical) {
                    check = new Fence(new Position(focus, 1, 1 - 2), makeVertical);
                } else {
                    check = new Fence(new Position(focus, 1 - 2, 1), makeVertical);
                }
            }
        }

        if (Fence.isPlaceable(check, board.getSizeX(), board.getSizeY(), board.getFencesArray()))
        {
            return check;
        }
        else
        {
            return null;
        }
    }

    /**
     * Find which valid move places this pawn closest to the goal.
     * @param board
     * @param myPawn
     */
    private void calculateMovePosition(Board board, Pawn myPawn) {
        //find next move position
        int[][] currentMap = board.generatePathMap(myPawn.getPosition(), myPawn.getGoalTileArray(), board.getFencesArray());
        //get current distance from goal
        int distance = currentMap[myPawn.getPosition().getX()][myPawn.getPosition().getY()];

        //look for valid move which gets closest to the goal (closest to 1)
        for(Position validMove : myPawn.getValidMoves())
        {
            int moveDist = currentMap[validMove.getX()][validMove.getY()];
            if (moveDist < distance && moveDist != 0)
            {
                distance = moveDist;
                bestMove = validMove;
            }
        }
    }
}
