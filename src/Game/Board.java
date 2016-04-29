package Game;

import java.util.ArrayList;

import Game.Orientation.Direction;


public class Board {
	
	private static Board boardInstance = null; // limit to 1 instance
	private static final int width = 9;
	private static final int height = 9;
	private static final int maxFences = 20;
	
	private boolean canRemoveFences;
	private Pawn[] pawns;
	private int pawnTurn;
	
	
	/**
	 * Constructor
	 */
	private Board(){}
	
	/**
	 * Board created only when required
	 * @return boardInstance 
	 */
	public static Board getInstance(){
		if(boardInstance == null){
			boardInstance = new Board(); // only creates the board if no other board exists
		}
		return boardInstance;
	}
	
	/**
	 * Returns the final X size of the board.
	 * @return
	 */
	int getSizeX(){
		return width;
	}
	
	/**
	 * Returns the final Y size of the board.
	 * @return
	 */
	int getSizeY(){
		return height;
	}

	/**
	 * Sets up the board and begins a game based on a set of conditions.
	 * @param fourPlayer: false for two players, true for four players.
	 * @param challengeMode: false for classic, true for challenge rules.
	 */
	public void setupBoard(boolean fourPlayer, boolean challengeMode)
	{
		if(!fourPlayer){
			pawns = new Pawn[2];
		}else{
			pawns = new Pawn[4];
		}
		//populate the pawns array and give them fences
		for(int i = 0; i < pawns.length; i++)
		{
			pawns[i] = new Pawn(i);
			if(challengeMode) 
			{
				pawns[i].setChallengePosition();
				pawns[i].setChallengeGoal();
			}
		}
		
		canRemoveFences = challengeMode;
		pawnTurn = 0;
		startTurn();
	}
	
	/**
	 * @return The id of the pawn the turn belongs to.
	 */
	int getPawnTurn()
	{
		return pawnTurn;
	}
	/**
	 * @return The id of the pawn the turn belongs to.
	 */
	int getPreviousPawnTurn()
	{
		if(pawnTurn - 1 < 0)
		{
			return pawns.length - 1;
		}
		return pawnTurn - 1;
	}
	
	/**
	 * Begins the turn for the current pawn.
	 */
	private void startTurn()
	{
		getCurrentPawn().calculateAllValidPositions(
				Board.width, 
				Board.height, 
				this.pawns, 
				this.getFencesArray());
	}
	
	/**
	 * Ends the turn for the current pawn.
	 * @return True if no interrupt by victory conditions
	 */
	private boolean endTurn()
	{
		if (!getCurrentPawn().isOnGoalTile()) incrementPlayerTurn();
		return true;
	}
	
	int getMaxPawnFences()
	{
		return maxFences/pawns.length;
	}
	
	//THINGS FOR GAMEVIEW TO CALL
	
	/**
	 * Gets a list of all fences from all pawns.
	 * @return Complete ArrayList of fences on the board.
	 */
	private ArrayList<Fence> getFences()
	{
		ArrayList<Fence> allFences = new ArrayList<>();
		for(Pawn p : pawns)
		{
			allFences.addAll(p.getFences());
		}
		return allFences;
		
	}
	/**
	 * Gets a list of all fences from all pawns, and converts it to an read-only array.
	 * @return Complete Fence[] array of fences on the board.
	 */
	Fence[] getFencesArray()
	{
		Object[] array = getFences().toArray();
		Fence[] fences = new Fence[array.length];
		for(int i = 0; i < fences.length; i++)
		{
			fences[i] = (Fence)array[i];
		}
		return fences;
		
	}
	/**
	 * @return Position of all pawns in order of pawn id.
	 */
	Position[] getPawnPositionsArray()
	{
		Position[] pawnPositions = new Position[pawns.length];
		for(int i = 0; i < pawns.length; i++)
		{
			pawnPositions[i] = pawns[i].getPosition();
		}
		return pawnPositions;
	}
	/**
	 * @return Number of fences available to pawns as an array in order of pawn id.
	 */
	int[] getPawnFenceCountArray()
	{
		int[] fenceCount = new int[pawns.length];
		for(int i = 0; i < pawns.length; i++)
		{
			fenceCount[i] = pawns[i].getFenceCount();
		}
		return fenceCount;
	}
	int getNumberOfPawns()
	{
		return pawns.length;
	}
	
	/**
	 * @return The valid positions of the current Pawn
	 */
	Position[] getValidPositionArray()
	{
		Pawn p = this.getCurrentPawn();
		Object[] array = p.getValidMoves().toArray();
		Position[] validPositions = new Position[array.length];
		for(int i = 0; i < validPositions.length; i++)
		{
			validPositions[i] = (Position)array[i];
		}
		return validPositions;
	}
	
	public boolean isChallengeMode() {
		return this.canRemoveFences;
	}
	
	/*
	 * FOLLOWING ARE A LIST OF ACTIONS AVAILABLE TO THE PLAYERS. 
	 * WHEN ANY OF THEM ARE CALLED, THE ACTION IS EXECUTED (IF POSSIBLE)
	 * AND THEIR TURN IS ENDED
	 */
	
	/**
	 * Player Action (Ends the turn if called and returns true)
	 * Attempts to move the pawn to the position indicated.
	 * @param position
	 * @return True if the action was successful.
	 */
	boolean pawnMove(Position position)
	{
		Pawn p = getCurrentPawn();
		if(!p.positionIsValidMove(position)) return false;
		
		p.updateCurrentPosition(position);
		if(this.endTurn())
		{
			this.startTurn();
		}
		return true;
	}
	
	/**
	 * Player Action (Ends the turn if called and returns true)
	 * Attempts to place a fence on the board, belonging to the pawn taking this turn.
	 * @param position
	 * @param isVertical
	 * @return True if the action was successful.
	 */
	boolean pawnPlaceFence(Position position, boolean isVertical)
	{
		Fence fence = new Fence(position, isVertical);
		if(!Fence.isPlaceable(fence, Board.width, Board.height, getFencesArray())) return false;
		Pawn p = getCurrentPawn();
		if(p.getFenceCount() >= this.getMaxPawnFences()) return false;
		
		//TODO: cannot stop any player from reaching their goal tile/s!
		if(!validateFencePathBlock(fence, getFencesArray())) return false;
		
		p.addFence(fence);
		if(this.endTurn())
		{
			this.startTurn();
		}
		return true;
	}
	
	/**
	 * Player Action (Ends the turn if called and returns true)
	 * Attempts to remove a designated fence from the board, provided it belongs to another player.
	 * @param fenceObject
	 * @return True if the action was successful.
	 */
	public boolean pawnRemoveFence(Fence fenceObject)
	{
		if(!canRemoveFences) return false;

		int id = getFenceOwnerID(fenceObject);
		if(id == pawnTurn || id < 0) return false;
		
		pawns[id].removeFence(fenceObject);
		if(this.endTurn())
		{
			this.startTurn();
		}
		return true;
	}

	/**
	 * Player Action (Ends the turn if called and returns true)
	 * Attempts to find a fence at the position clicked and run pawnRemoveFence on that.
	 * @param boardX x Position of fence
	 * @param boardY y Position of fence
	 * @param determineFenceOrientation Number between 0-2 indicating if the direction is Hor, Ver or Cross
	 * @return True if the action was successful.
	 */
	public boolean pawnRemoveFence(int boardX, int boardY, int determineFenceOrientation) {
		Fence fence = getFenceAtPos(boardX, boardY, determineFenceOrientation);
		if(fence == null) return false;
		return pawnRemoveFence(fence);
	}
	
	public Fence getFenceAtPos(int x, int y, int knownOrientation) {
		if(knownOrientation < 2)
		{
			boolean vertical = (knownOrientation == 1);
			if(vertical) { x += 1; } else { y += 1; }
			for (Fence fence : getFences())
			{
				if (Fence.fenceAtLocation(fence, x, y, vertical)) {
					return fence;
				}
			}
		}
		else
		{
			return Fence.fenceAtLocationByMiddle(getFencesArray(), x, y);
		}
		return null;
	}

	/**
	 * @param fenceObject
	 * @return The Pawn the fence is stored in, or -1 if there is no match.
	 */
	int getFenceOwnerID(Fence fenceObject)
	{
		for(Pawn p : pawns)
		{
			for(Fence f : p.getFences())
			{
				if(f == fenceObject)
				{
					return p.getPawnID();
				}
			}
		}
		return -1;
	}
	
	/**
	 * TODO: Not working yet
	 * Checks each pawn to see if they can get to their respective goals through Dijkstra's algorithm
	 * @return True if the fence does not block
	 */
	public boolean validateFencePathBlock(Fence fence, Fence[] allFences)
	{
		return true;
		/*
		Fence[] fences = new Fence[allFences.length + 1];
		for(int i = 0; i < allFences.length; i++) {
			fences[i] = allFences[i];
		}
		fences[fences.length - 1] = fence;
		
		for(Pawn p : pawns)
		{
			boolean foundGoal = false;
			boolean exploring = true;
			int[][] pathMap = new int[width][height];
			pathMap[p.getPosition().getX()][p.getPosition().getY()] = 1;
			
			//recurse until can't expand further
			while (exploring) {
				exploring = false;
				for(int y = 0; y < height; y++) {
					for(int x = 0; x < width; x++) {
						Position pos = new Position(x, y);
						//boolean expandWest = false;
						boolean expandEast = false;
						//boolean expandNorth = false;
						boolean expandSouth = false;
						
						if (x + 1 < width) expandEast = pathMap[x + 1][y] > 0 
								&& Fence.noFenceCollision(pos, Direction.EAST, fences);
						if (y + 1 < height) expandSouth = pathMap[x][y + 1] > 0 
								&& Fence.noFenceCollision(pos, Direction.SOUTH, fences);

						//expandWest = pathMap[Math.max(x - 1, 0)][y] > 0 
						//		&& Fence.noFenceCollision(pos, Direction.WEST, fences);
						//expandNorth = pathMap[x][Math.max(y - 1, 0)] > 0 
						//		&& Fence.noFenceCollision(pos, Direction.NORTH, fences);

						if(p.isOnGoalTile(new Position (x, y)) && pathMap[x][y] > 0)
						{
							//show pathfinding in code
							System.out.println("Pawn " + p.getPawnID());
							for(int b = 0; b < height; b++) {
								System.out.print("    | ");
								for(int a = 0; a < width; a++) {
									System.out.print(" " + pathMap[a][b] + " ");
								}
								System.out.println(" | ");
							}
							
							foundGoal = true;
							exploring = false;
							break;
						}
						else
						{
							if(expandEast || expandSouth)
							{
								if(pathMap[x][y] == 0) 
								{
									pathMap[x][y] = 1;
								} else {
									pathMap[x][y]++;
									if (expandEast && pathMap[x + 1][y] == 0) { 
										pathMap[x + 1][y] += 1; }
									if (expandSouth && pathMap[x][y + 1] == 0) {
										pathMap[x][y + 1] += 1; }
								}
								exploring = true;
							}
						}
						
						if (pathMap[x][y] > width * height + 1) return false; //break infinite loops
					}
					if (foundGoal) break;
				}
			}
			
			if (!foundGoal) return false;
		}
		
		return true;
		*/
	}
	
	/**
	 * Retrieves whichever pawn the turn belongs to.
	 * @return Pawn currently having its turn.
	 */
	Pawn getCurrentPawn()
	{
		return pawns[getPawnTurn()];
	}

	/**
	 * Cyclically increment the player turn between 0 and the pawn length-1
	 */
	private void incrementPlayerTurn()
	{
		pawnTurn++;
		if(pawnTurn >= pawns.length)
		{
			pawnTurn = 0;
		}
	}
}
