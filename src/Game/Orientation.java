package Game;

class Orientation {

	enum Direction{
		// directions
		NORTH, EAST, SOUTH, WEST;

		private Direction counterClockwise;
		private Direction clockwise;

		/**
		 * Initialisation block
		 */
		static{
			// set opposite directions for each direction

			// set counter clockwise directions for each direction
			NORTH.counterClockwise = WEST;
			EAST.counterClockwise = NORTH;
			SOUTH.counterClockwise = EAST;
			WEST.counterClockwise = SOUTH;

			// set clockwise directions for each direction
			NORTH.clockwise = EAST;
			EAST.clockwise = SOUTH;
			SOUTH.clockwise = WEST;
			WEST.clockwise = NORTH;
		}

		/**
		 * Gets the counter clockwise direction
		 * @return counterClockwise Counter Clockwise direction
		 */
		public Direction getCounterClockwise(){
			return counterClockwise;
		}

		/**
		 * Gets the clockwise direction
		 * @return clockwise Clockwise direction
		 */
		public Direction getClockwise(){
			return clockwise;
		}
	}

	/**
	 * Categorises NORTH/SOUTH and WEST/EAST as a boolean.
	 * @param direction A direction to see whether its value is true or false
	 * @return true if NORTH/SOUTH or false if EAST/WEST
	 */

	static boolean isVertical(Direction direction)
	{
		return direction == Direction.NORTH || direction == Direction.SOUTH;
	}

	/**
	 * Compares a direction and a preset boolean.
	 * @param direction A direction to be compared
	 * @param isVertical True is NORTH/SOUTH, False if EAST/WEST
	 * @return true, false Determine direction
	 */
	static boolean isParallel(Direction direction, boolean isVertical)
	{
		return isVertical(direction) == isVertical;
	}
}
