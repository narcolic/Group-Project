package Game;

class Position {

	private int x; // x position
	private int y; // y position
	
	/**
	 * Constructor
	 */
	Position(){
		setX(0); // set initial x value
		setY(0); // set initial y value
	}
	
	/**
	 * Constructor
	 * 
	 * @param x Value for x position
	 * @param y Value for y position
	 */
	Position(int x, int y){
		setXY(x,y);
	}
	
	/**
	 * Copies coordinates from parameter position to this, which additions
	 * @param pos Position
	 * @param addX Value to add to x position
	 * @param addY Value to add to y position
	 */
	Position(Position pos, int addX, int addY)
	{
		setX(pos.getX() + addX);
		setY(pos.getY() + addY);
	}
	
	/**
	 * Get value of X
	 * @return x  The current x value
	 */
	int getX(){
		return this.x;
	}
	
	/**
	 * Set position x
	 * @param x New x position
	 */
	private void setX(int x){
		this.x = x;
	}
	
	/**
	 * Get position y
	 * @return y The current y value
	 */
	int getY(){
		return this.y;
	}
	
	/**
	 * Set position y
	 * @param y New y position
	 */
	private void setY(int y){
		this.y = y;
	}
	
	/**
	 * Set x and y position
	 * @param x Value for x
	 * @param y Value for y
	 */
	void setXY(int x, int y)
	{
		setX(x);
		setY(y);
	}
	
	/**
	 * Returns true when two positions have equal coordinates.
	 * @param pos Position to be compared
	 * @return true If position being compared matches the x and y position
	 */
	boolean equals(Position pos)
	{
		return (this.x == pos.x && this.y == pos.y);
	}
}
