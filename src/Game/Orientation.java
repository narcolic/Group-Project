package Game;

public class Orientation {
	public enum Direction{
		NORTH, EAST, SOUTH, WEST;

		private Direction opposite;
		
		/**
		 * Initialisation block
		 */
		static{
			NORTH.opposite = SOUTH;
			EAST.opposite = WEST;
			SOUTH.opposite = NORTH;
			WEST.opposite = EAST;
		}
		
		/**
		 * 
		 * @return opposite Gets the opposite direction
		 */
		public Direction getOppositeDirection(){
			return opposite;
		}
	}
	
	/**
	 * Categorises NORTH/SOUTH and WEST/EAST as a boolean.
	 * @param direction
	 * @return true if NORTH/SOUTH
	 */
	public static boolean isVertical(Direction direction)
	{
		if (direction == Direction.NORTH || direction == Direction.SOUTH) return true;
		return false;
	}
	/**
	 * Compares two directions to see if they are of the same directions.
	 * @param direction1
	 * @param direction2
	 * @return
	 */
	public static boolean isParallel(Direction direction1, Direction direction2)
	{
		return isVertical(direction1) == isVertical(direction2);
	}
	/**
	 * Compares a direction and a preset boolean.
	 * @param direction
	 * @param isVertical
	 * @return
	 */
	public static boolean isParallel(Direction direction, boolean isVertical)
	{
		return isVertical(direction) == isVertical;
	}
}
