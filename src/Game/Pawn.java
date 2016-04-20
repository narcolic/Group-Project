package Game;

import java.awt.Color;


public class Pawn {

    private int pawnID;
    private Position position;
    private Color pawnColor;

    /**
     * Constructor
     */
    public Pawn(int newID) {
        pawnID = newID;
        setDefaultPosition();
        setDefaultColor();
    }

    public void updateCurrentPosition(Position newPosition) {
        this.position = newPosition;
    }

    public Position getPosition() {
        return position;
    }

/*    public void setNewPawnID(int newID) {
        this.pawnID = newID;
    }*/
    public void setDefaultPosition() {
        switch (pawnID) {
            case 1:
                this.position.setXY(4,8);
                break;
            case 2:
                this.position.setXY(4,0);
                break;
            case 3:
                this.position.setXY(0,4);
                break;
            case 4:
                this.position.setXY(8,4);
                break;
        }
    }
    
    public void setChallengePosition()  {
        switch (pawnID) {
	        case 1:
	            this.position.setXY(0,8);
	            break;
	        case 2:
	            this.position.setXY(8,0);
	            break;
	        case 3:
	            this.position.setXY(0,0);
	            break;
	        case 4:
	            this.position.setXY(8,8);
	            break;
	    }
	}

    public void setDefaultColor() {
        switch (pawnID) {
            case 1:
                this.pawnColor = Color.RED;
                break;
            case 2:
                this.pawnColor = Color.BLUE;
                break;
            case 3:
                this.pawnColor = Color.GREEN;
                break;
            case 4:
                this.pawnColor = Color.YELLOW;
                break;
        }
    }
    
    /**
     * Performs various checks to see if a valid move can be made in the direction specified
     * @param direction
     * @param boardWidth
     * @param boardHeight
     * @param allPawns
     * @param allFences
     * @return True if move can be made in this direction
     */
    public boolean canMove(Orientation.Direction direction, int boardWidth, int boardHeight, Pawn[] allPawns, Fence[] allFences)
    {
    	Position newPosition = directionPosition(position, direction);
    	//can't if outside board
    	if(outsideBoundary(newPosition, boardWidth, boardHeight)) return false;
    	//can't if fence is in the way
    	if(!Fence.validateMovement(position, direction, allFences)) return false;
    	
    	if(validatePawns(position, direction, allPawns))
    	{
    		//Face to Face mechanics
    		Position behindPawnPosition = directionPosition(newPosition, direction);
    		boolean noJump = false;
        	if(outsideBoundary(behindPawnPosition, boardWidth, boardHeight)) noJump = true;
        	if(!Fence.validateMovement(newPosition, direction, allFences)) noJump = true;
        	
        	if(noJump)
        	{
        		boolean noLeft = false, noRight = false;
        		Position leftOfPawn = directionPosition(newPosition, direction.getCounterClockwise());
        		Position rightOfPawn = directionPosition(newPosition, direction.getClockwise());
        		
            	if(outsideBoundary(leftOfPawn, boardWidth, boardHeight)) noLeft = true;
            	if(!Fence.validateMovement(newPosition, direction.getCounterClockwise(), allFences)) noLeft = true;
            	
            	if(outsideBoundary(rightOfPawn, boardWidth, boardHeight)) noRight = true;
            	if(!Fence.validateMovement(newPosition, direction.getClockwise(), allFences)) noRight = true;
            	
            	if(noLeft && noRight)
            	{
            		//Completely blocked, can't move in that direction
            		return false;
            	}
            	else
            	{
            		//can move in one or both0 of the two directions
            		return true;
            	}
        	}
        	else
        	{
        		//can make the jump
        		return true;
        	}
    	}
    	else
    	{
    		//can move as normal
    		return true;
    	}
    }
    
    /**
     * Checks if there is a pawn on the space being moved to.
     * @param direction
     * @param allPawns
     * @return True if a pawn is on the occupied space.
     */
    public static boolean validatePawns (Position position, Orientation.Direction direction, Pawn[] allPawns){
		Position newPosition = directionPosition(position, direction);
		validatePawns(newPosition, allPawns);
    	return false;
    }
    /**
     * Checks if there is a pawn on the specified position.
     * @param position
     * @param allPawns
     * @return True if a pawn is on the occupied space.
     */
    public static boolean validatePawns (Position position, Pawn[] allPawns){
		for(Pawn pawn : allPawns)
		{
			if(pawn.position.equals(position))
			{
				return true;
			}
		}
    	return false;
    }
    
    public boolean outsideBoundary(Position position, int boardWidth, int boardHeight)
    {
    	if(position.getX() < 0 || position.getX() >= boardWidth
    	|| position.getY() < 0 || position.getY() >= boardHeight) return true;
    	return false;
    }
    
    public static Position directionPosition(Position position, Orientation.Direction direction)
    {
    	switch (direction){
		case EAST:
			position = new Position(position, 1, 0);
			break;
		case NORTH:
			position = new Position(position, 0, -1);
			break;
		case SOUTH:
			position = new Position(position, 0, 1);
			break;
		case WEST:
			position = new Position(position, -1, 0);
			break;
    	}
    	return position;
    }
}
