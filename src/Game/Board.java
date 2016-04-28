package Game;

import java.util.ArrayList;


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
			if(challengeMode) pawns[i].setChallengePosition();
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
		incrementPlayerTurn();
		//TODO: Check game for victory conditions
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
	 * Retrieves whichever pawn the turn belongs to.
	 * @return Pawn currently having its turn.
	 */
	private Pawn getCurrentPawn()
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
