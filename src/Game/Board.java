package Game;

import Game.BoardGUI;
import Game.Fence;
import Game.Pawn;

import java.util.ArrayList;


public class Board implements BoardGUI {
	
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
	public int getSizeX(){
		return width;
	}
	
	/**
	 * Returns the final Y size of the board.
	 * @return
	 */
	public int getSizeY(){
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
			pawns[i] = new Pawn(i + 1, maxPawnFences());
			if(challengeMode) pawns[i].setChallengePosition();
		}
		
		pawnTurn = 0;
	}
	
	/**
	 * @return The id of the pawn the turn belongs to.
	 */
	public int getPawnTurn()
	{
		return pawnTurn;
	}
	
	/**
	 * Ends the turn for the current pawn.
	 */
	public void endTurn()
	{
		incrementPlayerTurn();
		//TODO: Check game for victory conditions
	}
	
	public int maxPawnFences()
	{
		return maxFences/pawns.length;
	}
	
	/**
	 * Gets a list of all fences from all pawns.
	 * @return Complete ArrayList of fences on the board.
	 */
	public ArrayList<Fence> getFences()
	{
		ArrayList<Fence> allFences = new ArrayList<Fence>();
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
	public Fence[] getFencesArray()
	{
		ArrayList<Fence> allFences = new ArrayList<Fence>();
		for(Pawn p : pawns)
		{
			allFences.addAll(p.getFences());
		}
		return (Fence[]) allFences.toArray();
		
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
	public boolean pawnMove(Position position)
	{
		Pawn p = getCurrentPawn();
		if(!p.positionIsValidMove(position)) return false;
		
		p.updateCurrentPosition(position);
		this.endTurn();
		return true;
	}
	
	/**
	 * Player Action (Ends the turn if called and returns true)
	 * Attempts to place a fence on the board, belonging to the pawn taking this turn.
	 * @param position
	 * @param isVertical
	 * @return True if the action was successful.
	 */
	public boolean pawnPlaceFence(Position position, boolean isVertical)
	{
		Fence fence = new Fence(position, isVertical);
		if(!Fence.isPlaceable(fence, Board.width, Board.height, getFencesArray())) return false;
		Pawn p = getCurrentPawn();
		if(p.getFenceCount() >= this.maxPawnFences()) return false;
		
		p.addFence(fence);
		this.endTurn();
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
		int id = getFenceOwnerID(fenceObject);
		if(id == pawnTurn || id < 0) return false;
		
		pawns[id].removeFence(fenceObject);
		this.endTurn();
		return false;
	}
	
	/**
	 * @param fenceObject
	 * @return The Pawn the fence is stored in, or -1 if there is no match.
	 */
	private int getFenceOwnerID(Fence fenceObject)
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
	public Pawn getCurrentPawn()
	{
		return pawns[pawnTurn];
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
