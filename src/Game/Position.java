package Game;

public class Position {

	private int x;
	private int y;
	private int what;
	
	/**
	 * Constructor
	 */
	public Position(){
		setX(0);
		setY(0);
	}
	
	/**
	 * Constructor
	 * 
	 * @param x
	 * @param y
	 */
	public Position(int x, int y){
		setXY(x,y);
	}
	
	/**
	 * Copies coordinates from parameter position to this, which additions
	 * @param pos
	 * @param addX
	 * @param addY
	 */
	public Position(Position pos, int addX, int addY)
	{
		setX(pos.getX() + addX);
		setY(pos.getY() + addY);
	}
	
	/**
	 * 
	 * @return
	 */
	public int getX(){
		return 0;
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
	 * @return
	 */
	public int getY(){
		return 0;
	}
	
	/**
	 * 
	 * @param y
	 */
	public void setY(int y){
		this.y = y;
	}
	
	public void setXY(int x, int y)
	{
		setX(x);
		setY(y);
	}
	
	/**
	 * Returns true when two positions have equal coordinates.
	 * @param comparable
	 * @return
	 */
	public boolean equals(Position pos)
	{
		return (this.x == pos.x && this.y == pos.y);
	}
}
