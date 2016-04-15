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
	
	/**
	 * Constructor
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
	 * Validates if a fence can be placed at this position, given a board boundary
	 * @param fence
	 * @param boardWidth
	 * @param boardHeight
	 * @return true if successful
	 */
	public static boolean validateBoundary(Fence fence, int boardWidth, int boardHeight)
	{
		if(fence.isVertical)
		{
			if(fence.pos.getY() + fence.length >= boardHeight) return false;
		}
		else
		{
			if(fence.pos.getX() + fence.length >= boardWidth) return false;
		}
		return true;
	}
	
	/**
	 * Validates if any fences intersect with each other
	 * @param fence
	 * @param allFences
	 * @return true if no intersections
	 */
	public static boolean validateInteresections(Fence fence, Fence[] allFences)
	{
		int fenceParallel, fencePerpendicular, checkParallel, checkPerpendicular;
		//establish which side direction is in
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
				if(fenceParallel != checkParallel) continue; //not on same axis
				
				if((fencePerpendicular < checkPerpendicular && fenceParallel + fence.length > fencePerpendicular)
				|| (checkPerpendicular < fencePerpendicular && fencePerpendicular + check.length > fenceParallel))
				{
					//intersection here by means of fences layered inside one another
					return false; 
				}
			}
			else
			{
				//is perpendicular
				if(checkPerpendicular <= fencePerpendicular
				|| checkPerpendicular >= fencePerpendicular + fence.length) continue; //wouldn't intersect
				
				if(fenceParallel < checkParallel
				&& fenceParallel + fence.length > checkParallel)
				{
					//intersection here by fully crossing a fence's line
					return false;
				}
			}
		}
		return true;
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
