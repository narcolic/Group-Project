package Game;

import java.util.ArrayList;


public class Pawn {

    private int pawnID;
    private Position position;
    private boolean aiPlayer;
    private ArrayList<Position> validMoves;
    private ArrayList<Position> goalTiles;
    private ArrayList<Fence> myFences;


    //debugging
    public static void main(String[] args) {
        //test for valid positions and face to face conditions
        Fence[] allFences = new Fence[10];
        Pawn[] allPawns = new Pawn[3];
        int size = 9;
        allFences[0] = new Fence(new Position(4, 8), false, 2);
        allFences[1] = new Fence(new Position(8, 0), true, 1);
        allPawns[0] = new Pawn(0);
        allPawns[1] = new Pawn(1);

        //Test for valid positions from an edge with fence above
        Pawn p = allPawns[0];
        p.calculateAllValidPositions(size, size, allPawns, allFences);
        System.out.println("Valid moves 1: " + p.validMoves.size());
        for (Position pos : p.validMoves) {
            System.out.println("Valid Move from (" + p.position.getX() + "," + p.position.getY() + ") : (" + pos.getX() + "," + pos.getY() + ")");
        }

        //Test for top right corner, fence on the left
        p.updateCurrentPosition(new Position(8, 0));
        p.calculateAllValidPositions(size, size, allPawns, allFences);
        System.out.println("Valid moves 2: " + p.validMoves.size());
        for (Position pos : p.validMoves) {
            System.out.println("Valid Move from (" + p.position.getX() + "," + p.position.getY() + ") : (" + pos.getX() + "," + pos.getY() + ")");
        }
        //Fig.6 Test (jump over)
        allPawns[1].updateCurrentPosition(new Position(8, 1));
        p.calculateAllValidPositions(size, size, allPawns, allFences);
        System.out.println("Valid moves 3: " + p.validMoves.size());
        for (Position pos : p.validMoves) {
            System.out.println("Valid Move from (" + p.position.getX() + "," + p.position.getY() + ") : (" + pos.getX() + "," + pos.getY() + ")");
        }
        //Fig.9 Test (jump to the side because fence behind pawn)
        allFences[1] = new Fence(new Position(8, 2), false, 1);
        p.calculateAllValidPositions(size, size, allPawns, allFences);
        System.out.println("Valid moves 4: " + p.validMoves.size());
        for (Position pos : p.validMoves) {
            System.out.println("Valid Move from (" + p.position.getX() + "," + p.position.getY() + ") : (" + pos.getX() + "," + pos.getY() + ")");
        }
        //Fig.10 Test (jump to the side because pawn behind pawn)
        allFences[1] = null;
        allPawns[2] = new Pawn(2);
        p.updateCurrentPosition(new Position(4, 4));
        allPawns[1].updateCurrentPosition(new Position(4, 3));
        allPawns[2].updateCurrentPosition(new Position(4, 2));
        p.calculateAllValidPositions(size, size, allPawns, allFences);
        System.out.println("Valid moves 5: " + p.validMoves.size());
        for (Position pos : p.validMoves) {
            System.out.println("Valid Move from (" + p.position.getX() + "," + p.position.getY() + ") : (" + pos.getX() + "," + pos.getY() + ")");
        }


        System.out.println("DONE");

    }


    /**
     * Constructor
     */
    Pawn(int newID) {
        pawnID = newID;
        position = new Position();
        goalTiles = new ArrayList<>();
        myFences = new ArrayList<>();
        setDefaultPosition();
        setDefaultGoal();
    }

    /**
     * @return The pawnID minus one, to match arrays numbering.
     */
    int getPawnID() {
        return this.pawnID;
    }

    void updateCurrentPosition(Position newPosition) {
        this.position = newPosition;
    }

    void addFence(Fence fence) {
        myFences.add(fence);
    }

    void removeFence(Fence fence) {
        myFences.remove(fence);
    }

    Position getPosition() {
        return this.position;
    }

    ArrayList<Fence> getFences() {
        return this.myFences;
    }

    boolean isAi() {
        return aiPlayer;
    }
    /**
     * @return Number of fences active.
     */
    int getFenceCount() {
        return this.myFences.size();
    }

    ArrayList<Position> getValidMoves() {
        return validMoves;
    }
    
    Position[] getGoalTileArray() {
    	Position[] positions = new Position[goalTiles.size()];
    	for(int i = 0; i < positions.length; i++) {
    		positions[i] = goalTiles.get(i);
    	}
    	return positions;
    }

    boolean positionIsValidMove(Position position) {
        if(position == null) return false;
        for (Position pos : validMoves) {
            if (pos.equals(position)) return true;
        }
        return false;
    }

    private void setDefaultPosition() {
        switch (pawnID) {
            case 0:
                this.position.setXY(4, 8);
                break;
            case 1:
                this.position.setXY(4, 0);
                break;
            case 2:
                this.position.setXY(0, 4);
                break;
            case 3:
                this.position.setXY(8, 4);
                break;
        }
    }
    
    public void setDefaultGoal()
    {
        switch (pawnID) {
        case 0:
            for(int i = 0; i < 9; i++) {
            	goalTiles.add(new Position(i, 0));
            }
            break;
        case 1:
            for(int i = 0; i < 9; i++) {
            	goalTiles.add(new Position(i, 8));
            }
            break;
        case 2:
            for(int i = 0; i < 9; i++) {
            	goalTiles.add(new Position(8, i));
            }
            break;
        case 3:
            for(int i = 0; i < 9; i++) {
            	goalTiles.add(new Position(0, i));
            }
            break;
        }
    }

    public void setBehaviour(boolean makeAI) {
        aiPlayer = makeAI;
    }

    void setChallengePosition() {
        switch (pawnID) {
            case 0:
                this.position.setXY(0, 8);
                break;
            case 1:
                this.position.setXY(8, 0);
                break;
            case 2:
                this.position.setXY(0, 0);
                break;
            case 3:
                this.position.setXY(8, 8);
                break;
        }
    }
    
    public void setChallengeGoal()
    {
        goalTiles.clear();
        switch (pawnID) {
        case 0:
        	goalTiles.add(new Position(8, 0));
            break;
        case 1:
        	goalTiles.add(new Position(0, 8));
            break;
        case 2:
        	goalTiles.add(new Position(8, 8));
            break;
        case 3:
        	goalTiles.add(new Position(0, 0));
            break;
        }
    }

    /**
     * Calculates ALL valid moves a pawn can make this turn.
     *
     * @param boardWidth
     * @param boardHeight
     * @param allPawns
     * @param allFences
     */
    void calculateAllValidPositions(int boardWidth, int boardHeight, Pawn[] allPawns, Fence[] allFences) {
        //reset all moves
        validMoves = new ArrayList<>();

        calculateValidPosition(Orientation.Direction.EAST,
                boardWidth, boardHeight, allPawns, allFences);
        calculateValidPosition(Orientation.Direction.NORTH,
                boardWidth, boardHeight, allPawns, allFences);
        calculateValidPosition(Orientation.Direction.SOUTH,
                boardWidth, boardHeight, allPawns, allFences);
        calculateValidPosition(Orientation.Direction.WEST,
                boardWidth, boardHeight, allPawns, allFences);
    }

    /**
     * Calculates all valid moves that can be made by from moving in this direction
     *
     * @param direction
     * @param boardWidth
     * @param boardHeight
     * @param allPawns
     * @param allFences
     */
    private void calculateValidPosition(Orientation.Direction direction, int boardWidth, int boardHeight, Pawn[] allPawns, Fence[] allFences) {
        Position newPosition = directionPosition(position, direction);
        //can't if outside board
        if (outsideBoundary(newPosition, boardWidth, boardHeight)) return;
        //can't if fence is in the way
        if (!Fence.noFenceCollision(position, direction, allFences)) return;

        if (pawnAtPosition(position, direction, allPawns)) {
            //Face to Face mechanics
            Position behindPawnPosition = directionPosition(newPosition, direction);
            boolean noJump = false;
            if (outsideBoundary(behindPawnPosition, boardWidth, boardHeight)) noJump = true;
            if (!Fence.noFenceCollision(newPosition, direction, allFences)) noJump = true;
            if (pawnAtPosition(newPosition, direction, allPawns)) noJump = true;

            //if something is preventing the pawn from jumping over
            if (noJump) {
                boolean noLeft = false, noRight = false;
                Position leftOfPawn = directionPosition(newPosition, direction.getCounterClockwise());
                Position rightOfPawn = directionPosition(newPosition, direction.getClockwise());

                if (outsideBoundary(leftOfPawn, boardWidth, boardHeight)) noLeft = true;
                if (!Fence.noFenceCollision(newPosition, direction.getCounterClockwise(), allFences)) noLeft = true;

                if (outsideBoundary(rightOfPawn, boardWidth, boardHeight)) noRight = true;
                if (!Fence.noFenceCollision(newPosition, direction.getClockwise(), allFences)) noRight = true;

                //can move in one or both0 of the two directions
                for (Position existingMove : validMoves) {
                    if (existingMove.equals(leftOfPawn)) noLeft = true;
                    if (existingMove.equals(rightOfPawn)) noRight = true;
                }
                if (!noLeft) validMoves.add(leftOfPawn);
                if (!noRight) validMoves.add(rightOfPawn);
            } else {
                //can make the jump
                validMoves.add(behindPawnPosition);
            }
        } else {
            //can move as normal
            validMoves.add(newPosition);
        }
    }

    /**
     * Checks if there is a pawn on the space being moved to.
     *
     * @param direction
     * @param allPawns
     * @return True if a pawn is on the occupied space.
     */
    private static boolean pawnAtPosition(Position position, Orientation.Direction direction, Pawn[] allPawns) {
        Position newPosition = directionPosition(position, direction);
        return validatePawns(newPosition, allPawns);
    }

    /**
     * Checks if there is a pawn on the specified position.
     *
     * @param position
     * @param allPawns
     * @return True if a pawn is on the occupied space.
     */
    private static boolean validatePawns(Position position, Pawn[] allPawns) {
        for (Pawn pawn : allPawns) {
            if (pawn == null) continue;
            if (pawn.position.equals(position)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param position
     * @param boardWidth
     * @param boardHeight
     * @return True if outside boundary
     */
    public static boolean outsideBoundary(Position position, int boardWidth, int boardHeight) {
        return position.getX() < 0 || position.getX() >= boardWidth
                || position.getY() < 0 || position.getY() >= boardHeight;
    }

    /**
     * @param position
     * @param direction
     * @return Position 1 away in direction from in position
     */
    public static Position directionPosition(Position position, Orientation.Direction direction) {
        switch (direction) {
            case EAST:
                position = new Position(position, 1, 0);
                break;
            case NORTH:
                position = new Position(position, 0, -1);
                break;
            case SOUTH:
                position = new Position(position, 0, 1);
                break;
            case WEST:
                position = new Position(position, -1, 0);
                break;
        }
        return position;
    }

    /**
     * @return True if the pawn is on a Goal Tile
     */
    public boolean isOnGoalTile() {
        return isOnGoalTile(position);
    }
    
    /**
     * @param pos
     * @return True if position is on a goalTile
     */
    public boolean isOnGoalTile(Position pos) {
    	for(Position goal : goalTiles) {
    		if (pos.equals(goal)) return true;
    	}
        return false;
    }

}

//Ctrl-Shift * / for collapse/open
