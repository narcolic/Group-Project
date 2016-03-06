
public class Position {

	private int x;
	private int y;
	
	/**
	 * Constructor
	 */
	public Position(){
		
	}
	
	/**
	 * Constructor
	 * 
	 * @param x
	 * @param y
	 */
	public Position(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getX(){
		return x;
	}
	
	/**
	 * 
	 * @param x
	 */
	public void setX(int x){
		this.x = x;
	}
	
	/**
	 * 
	 * @return y
	 */
	public int getY(){
		return y;
	}
	
	/**
	 * 
	 * @param y
	 */
	public void setY(int y){
		this.y = y;
	}
}
