package Game;

import java.util.ArrayList;

public class Fence 
{
	final int FENCE_LENGTH = 2;
	
	/**Origin of fence, from top left*/ 
	private Position pos;
	/**Length of the fence, in board units*/ 
	private int length;
	/**Determines the orientation of this fence*/ 
	private boolean isVertical;
	
	
	//debugging
	public static void main(String[] args)
	{
		Fence[] allFences = new Fence[10];
		int size = 4;
		boolean result = false;
		allFences[0] = new Fence(new Position(0,1), false, 2);
		
		result = validateMovement(new Position(1,0), Orientation.Direction.SOUTH, allFences);
		System.out.println(result + ": Attempt move from 1,0 to 1,1 with fence blocking.");
		result = validateMovement(new Position(1,1), Orientation.Direction.NORTH, allFences);
		System.out.println(result + ": Attempt move from 1,1 to 1,0 with fence blocking.");
		result = validateMovement(new Position(2,0), Orientation.Direction.SOUTH, allFences);
		System.out.println(result + ": Move from 2,0 to 2,1 while clear.");
		
		result = validateBoundary(new Fence(-1, 0, false, 2), size, size);
		System.out.println(result + ": Check boundary place horizontal fence at -1,0.");
		result = validateBoundary(new Fence(2, 0, false, 2), size, size);
		System.out.println(result + ": Boundary place horizontal fence at 2,0.");
		result = validateBoundary(new Fence(3, 0, false, 2), size, size);
		System.out.println(result + ": Check boundary place horizontal fence at 3,0.");
		
		result = validateInteresections(new Fence(new Position(1,0), true, 1), allFences);
		System.out.println(result + ": Check place short vertical fence at 1,0.");
		result = validateInteresections(new Fence(new Position(1,0), true, 2), allFences);
		System.out.println(result + ": Check attempt placing intersecting vertical fence at 1,0.");

		result = validateInteresections(new Fence(new Position(1,1), false, 2), allFences);
		System.out.println(result + ": Check place vertical fence at 1,1.");
		result = validateInteresections(new Fence(new Position(2,1), false, 2), allFences);
		System.out.println(result + ": Check place vertical fence at 2,1.");
		result = validateInteresections(new Fence(new Position(2,0), true, 2), allFences);
		System.out.println(result + ": Check place vertical fence at 2,0.");
		allFences[1] = new Fence(new Position(2,0), false, 2);
		
	}
	
	
	/**
	 * Standard Constructor
	 */
	public Fence(Position pos, boolean isVertical, int length)
	{
		this.pos = pos;
		this.isVertical = isVertical;
		this.length = length;
	}
	/**
	 * Constructor using default fence length (2)
	 */
	public Fence(Position pos, boolean isVertical)
	{
		this.pos = pos;
		this.isVertical = isVertical;
		this.length = FENCE_LENGTH;
	}
	/**
	 * Constructor building its own position
	 */
	public Fence(int X, int Y, boolean isVertical, int length)
	{
		this.pos = new Position(X,Y);
		this.isVertical = isVertical;
		this.length = length;
	}
	/**
	 * Constructor building its own position and default length (2)
	 */
	public Fence(int X, int Y, boolean isVertical)
	{
		this.pos = new Position(X,Y);
		this.isVertical = isVertical;
		this.length = FENCE_LENGTH;
	}
	
	/**
	 * Validates if a fence can be placed at this position, given a board boundary
	 * @param fence
	 * @param boardWidth
	 * @param boardHeight
	 * @return True if there is no boundary crossing
	 */
	public static boolean validateBoundary(Fence fence, int boardWidth, int boardHeight)
	{
		if(fence.isVertical)
		{
			if(fence.pos.getY() < 0
			|| fence.pos.getY() + fence.length > boardHeight) return false;
		}
		else
		{
			if(fence.pos.getX() < 0
			|| fence.pos.getX() + fence.length > boardWidth) return false;
		}
		return true;
	}
	
	/**
	 * Validates if any fences intersect with each other
	 * @param fence
	 * @param allFences
	 * @return True if there are no intersections
	 */
	public static boolean validateInteresections(Fence fence, Fence[] allFences)
	{
		int fenceParallel, fencePerpendicular, checkParallel, checkPerpendicular;
		//establish which side direction is in
		//System.out.println("	Fence: " + fence.pos.getX() + "|" + fence.pos.getY() + ", length " + fence.length);
		if (!fence.isVertical) {
			//fence is horizontal, so parallel fences are along the x
			fenceParallel = fence.pos.getX();
			fencePerpendicular = fence.pos.getY();
		} else {
			//is vertical so along the y
			fenceParallel = fence.pos.getY();
			fencePerpendicular = fence.pos.getX();
		}
		
		for(Fence check : allFences)
		{
			if(check == null) continue;
			//System.out.println("	Check: " + check.pos.getX() + "|" + check.pos.getY() + ", length " + check.length);
			if (!check.isVertical){
				checkParallel = check.pos.getX();
				checkPerpendicular = check.pos.getY();
			} else {
				checkParallel = check.pos.getY();
				checkPerpendicular = check.pos.getX();
			}
			
			if (check.isVertical == fence.isVertical)
			{
				//is parallel
				if(fencePerpendicular != checkPerpendicular) continue; //not lying on same axis
				
				if((fenceParallel < checkParallel + check.length && fenceParallel + fence.length > checkParallel)
				|| (checkParallel < fenceParallel + fence.length && checkParallel + check.length > fenceParallel))
				{
					//intersection here by means of fences layered inside one another
					return false; 
				}
			}
			else
			{
				//is perpendicular
				if(checkParallel >= fencePerpendicular
				|| checkParallel + check.length <= fencePerpendicular) continue; //not along fence's parallel line
				
				if(fenceParallel < checkPerpendicular
				&& fenceParallel + fence.length > checkPerpendicular) //intersects along perpendicular
				{
					//intersection here by fully crossing a fence's line
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * Validates if a move collides with a fence or not
	 * @param pos
	 * @param direction
	 * @param allFences
	 * @return True if there is no collision
	 */
	public static boolean validateMovement(Position pos, Orientation.Direction direction, Fence[] allFences)
	{
		for(Fence fence : allFences)
		{
			if(fence == null) continue;
			//ignore fences that are parallel to the movement direction
			if(Orientation.isParallel(direction, fence.isVertical)) continue;
			
			if(Orientation.isVertical(direction))
			{
				//	System.out.println("    px " + pos.getX() + ": left " + fence.pos.getX() + " | right " + (fence.pos.getX() + fence.length));
				if(fence.pos.getX() <= pos.getX()
				&& fence.pos.getX() + fence.length > pos.getX())
				{
					//	System.out.println("    py " + pos.getY() + ": y " + fence.pos.getY());
					if(direction == Orientation.Direction.NORTH
					&& fence.pos.getY() == pos.getY()) return true;
					
					if(direction == Orientation.Direction.SOUTH
					&& fence.pos.getY() == pos.getY() + 1) return true;
				}
			}
			else
			{
				if(fence.pos.getY() <= pos.getY()
				&& fence.pos.getY() + fence.length > pos.getY())
				{
					if(direction == Orientation.Direction.WEST
					&& fence.pos.getX() == pos.getX()) return true;
					
					if(direction == Orientation.Direction.EAST
					&& fence.pos.getX() == pos.getX() + 1) return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Checks through a list to see if there is a fence blocking collision between two points
	 * @param fenceList
	 * @param pos1
	 * @param pos2
	 * @return true if there is a collision
	 
	public static boolean checkCollision(ArrayList<Fence> fenceList, Position pos1, Position pos2)
	{
		for(Fence f : fenceList)
		{
			if(f.pos1.equals(pos1) && f.pos2.equals(pos2)) return true;
		}
		return false;
	}
	
	*//**
	 * Checks through a list to see if there is a fence occupying this space already
	 * @param fenceList
	 * @param f
	 * @return true if there is a fence
	 *//*
	public static boolean checkCollision(ArrayList<Fence> fenceList, Fence f)
	{
		return checkCollision(fenceList, f.pos1, f.pos2);
	}
	
	*//**
	 * Creates a fence using the parameters provided there is no intersection.
	 * @param fenceList
	 * @param origin
	 * @param isVertical
	 * @return returns a fence if valid, null otherwise
	 *//*
	public static ArrayList<Fence> generateFence(ArrayList<Fence> fenceList, Position origin, boolean isVertical)
	{
		boolean obstructed = false;
		ArrayList<Fence> tempList = new ArrayList<Fence>();
		Fence tempF = null;
		Fence previousTempF = null;
		
		for(int i = 0; i < size; i++)
		{
			previousTempF = tempF;
			if(!isVertical) //check up, then to the right for both.
			{
				tempF = new Fence(
						new Position(origin, i, 0),
						new Position(origin, i, 1)
						);
			}
			else
			{
				tempF = new Fence(
						new Position(origin, 0, i),
						new Position(origin, 1, i)
						);
			}
			
			obstructed = checkCollision(fenceList, tempF);//check
			if(obstructed) break;
			
			if(previousTempF != null) //check for intersections if size is larger than 1
			{
				//check across for both y / x values
				obstructed = (
						checkCollision(fenceList, tempF.pos1, previousTempF.pos1) && 
						checkCollision(fenceList, tempF.pos2, previousTempF.pos2));
			}
			if(obstructed) break;
			//fence placement is valid
			tempList.add(tempF);
		}
		
		if(obstructed)
		{
			return null;
		}
		else
		{
			return tempList;
		}
	}
	
	*//**
	 * Adds fences to the provided list as long as there is no obstruction from other fences. Does not check for board boundaries.
	 * @param fenceList
	 * @param origin
	 * @param isVertical
	 * @return True if there was no obstruction - fenced added with no error.
	 *//*
	private static boolean addFence(ArrayList<Fence> fenceList, Position origin, boolean isVertical)
	{
		ArrayList<Fence> tempList = new ArrayList<Fence>();
		
		tempList = generateFence(fenceList, origin, isVertical);
		
		//completed process without breaking
		if(tempList == null) return false;
		
		fenceList.addAll(tempList);
		return true;
	}

	public static boolean addFence(ArrayList<Fence> fenceList, Position origin, boolean isVertical, int boardWidth, int boardHeight)
	{
		ArrayList<Fence> tempList = new ArrayList<Fence>();
		
		tempList = generateFence(fenceList, origin, isVertical);
		
		//completed process without breaking
		if(tempList == null) return false;
		
		//fence does not go past the board boundary
		if(checkBoundaryCollision(tempList, Board.getInstance().getSizeX(), Board.getInstance().getSizeY())) return false;
		
		fenceList.addAll(tempList);
		return true;
	}
	
	*//**
	 * Checks a proposed place-able fence list against the boundaries of a game board
	 * @param placedFence
	 * @param width
	 * @param height
	 * @return
	 *//*
	public static boolean checkBoundaryCollision(ArrayList<Fence> placedFence, int width, int height)
	{
		for(Fence f : placedFence)
		{
			if(f.pos1.getX() < 0 || f.pos1.getX() > width - 2) return true;
			if(f.pos1.getY() < 0 || f.pos1.getY() > width - 2) return true;
			if(f.pos2.getX() < 0 || f.pos2.getX() > width - 2) return true;
			if(f.pos2.getY() < 0 || f.pos2.getY() > width - 2) return true;
		}
		return false;
	}
	
	public static boolean checkBoundaryCollision(Fence fence, int width, int height)
	{
		if(fence.pos1.getX() < 0 || fence.pos1.getX() > width - 2) return true;
		if(fence.pos1.getY() < 0 || fence.pos1.getY() > width - 2) return true;
		if(fence.pos2.getX() < 0 || fence.pos2.getX() > width - 2) return true;
		if(fence.pos2.getY() < 0 || fence.pos2.getY() > width - 2) return true;
		return false;
	}
	*/
}
