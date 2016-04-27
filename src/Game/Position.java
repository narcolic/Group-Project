package Game;

public class Position {

	private int x; // x position
	private int y; // y position
	
	/**
	 * Constructor
	 */
	public Position(){
		setX(0); // set initial x value
		setY(0); // set initial y value
	}
	
	/**
	 * Constructor
	 * 
	 * @param x Value for x position
	 * @param y Value for y position
	 */
	public Position(int x, int y){
		setXY(x,y);
	}
	
	/**
	 * Copies coordinates from parameter position to this, which additions
	 * @param pos Position
	 * @param addX Value to add to x position
	 * @param addY Value to add to y position
	 */
	public Position(Position pos, int addX, int addY)
	{
		setX(pos.getX() + addX);
		setY(pos.getY() + addY);
	}
	
	/**
	 * Get value of X
	 * @return x  The current x value
	 */
	public int getX(){
		return this.x;
	}
	
	/**
	 * Set position x
	 * @param x New x position
	 */
	public void setX(int x){
		this.x = x;
	}
	
	/**
	 * Get position y
	 * @return y The current y value
	 */
	public int getY(){
		return this.y;
	}
	
	/**
	 * Set position y
	 * @param y New y position
	 */
	public void setY(int y){
		this.y = y;
	}
	
	/**
	 * Set x and y position
	 * @param x Value for x
	 * @param y Value for y
	 */
	public void setXY(int x, int y)
	{
		setX(x);
		setY(y);
	}
	
	/**
	 * Returns true when two positions have equal coordinates.
	 * @param pos Position to be compared
	 * @return true If position being compared matches the x and y position
	 */
	public boolean equals(Position pos)
	{
		return (this.x == pos.x && this.y == pos.y);
	}
}
