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
}
