package player;

public class Player {

	
	
	private  String playerName;
	private  boolean isPlayerTurn;
	private  int playerRole;
	
	
	
	
	public Player(boolean isPlayerTurn, int playerRole, String playerName){
		this.isPlayerTurn = isPlayerTurn;
		this.playerRole = playerRole;
	    this.playerName = playerName;
	}
	
	



	public  boolean getPlayerTurn() {
		return isPlayerTurn;
	}
	
	public  int getPlayerRole() {
		return playerRole;
	}
	
	
	public  String getPlayerName() {
		return playerName;
	}
	
	
	
	
	
	public  void changePlayerTurn(boolean isPlayerTurn) {
		
		if(isPlayerTurn)
		{
			isPlayerTurn = false;
		}else
		{
			isPlayerTurn = true;
		}
		
	}
	
	
	
}
