import java.util.ArrayList;


public class Board implements BoardGUI{
	
	private int width;
	private int length;
	private Pawn[] pawns;
	private ArrayList<Wall> walls;
	
	/**
	 * Constructor
	 */
	public Board(){
		
	}
	
	/**
	 * 
	 * @return
	 */
	public int getSize(){
		return 0;
	}

	/**
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
	public ArrayList<Wall> getWalls(){
		return null;
		
	}
}
