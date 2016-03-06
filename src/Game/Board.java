import java.util.ArrayList;


public class Board implements BoardGUI{
	
	private static Board boardInstance = null; // limit to 1 instance
	private static final int width = 9;
	private static final int length = 9;
	private Pawn[] pawns;
	private ArrayList<Fence> walls;
	
	/**
	 * Constructor
	 */
	private Board(){
		
	}
	
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
	 * 
	 * @return
	 */
	public int getSize(){
		return 0;
	}

	/**
	 * METHOD NOT NEEDED FOR CURRENT IMPLEMENTATION
	 * @param width
	 * @param length
	 */
	public void setSize(int width, int length){
		
	}
	
	/**
	 * 
	 * @param pos1
	 * @param pos2
	 * @param orientation
	 * @param size
	 */
	public void createWall(Position pos1, Position pos2, boolean orientation, int size){
		
	}
	
	/**
	 * 
	 * @return
	 */
	public ArrayList<Fence> getWalls(){
		return null;
		
	}
	
	public boolean placeFence(Position pos, boolean isVertical, int size){
		return false;
	}
}
