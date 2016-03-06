
public class Player extends Pawn {

	private String name;
	private Language language;
	private int playerID;
	
	/**
	 * Constructor
	 */
	public Player(){
		
	}
	
	/**
	 * 
	 * @return name 
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * 
	 * @return playerID 
	 */
	public int getPlayerID(){
		return playerID;
	}
	
	/**
	 * 
	 * @param name
	 */
	public void setName(String name){
		this.name = name;
	}
	
	/**
	 * 
	 * @param playerID
	 */
	public void setPlayerID(int playerID){
		this.playerID = playerID;
	}
	
}
