package Game;

import java.util.ArrayList;

public class Fence {

	private static int size;
	private Position pos1;
	private Position pos2;
	
	/**
	 * Constructor
	 */
	public Fence(Position pos1, Position pos2)
	{
		this.pos1 = pos1;
		this.pos2 = pos2;
	}
	
	/**
	 * Checks through a list to see if there is a fence blocking collision between two points
	 * @param fenceList
	 * @param pos1
	 * @param pos2
	 * @return true if there is a collision
	 */
	public static boolean checkCollision(ArrayList<Fence> fenceList, Position pos1, Position pos2)
	{
		for(Fence f : fenceList)
		{
			if(f.pos1.equals(pos1) && f.pos2.equals(pos2)) return true;
		}
		return false;
	}
	
	/**
	 * Checks through a list to see if there is a fence occupying this space already
	 * @param fenceList
	 * @param f
	 * @return true if there is a fence
	 */
	public static boolean checkCollision(ArrayList<Fence> fenceList, Fence f)
	{
		return checkCollision(fenceList, f.pos1, f.pos2);
	}
	
	/**
	 * Adds fences to the provided list as long as there is no obstruction from other fences. Does not check for board boundaries.
	 * @param fenceList
	 * @param origin
	 * @param isVertical
	 * @return True if there was no obstruction - fenced added with no error.
	 */
	public static boolean addFence(ArrayList<Fence> fenceList, Position origin, boolean isVertical)
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
		
		//completed process without breaking
		if(!obstructed)
		{
			fenceList.addAll(tempList);
		}
		
		return !obstructed;
	}
}
