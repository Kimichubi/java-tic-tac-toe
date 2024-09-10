package game;
import player.Player;


public class Game {

	
	Player firstPlayer;
	Player secondPlayer ;
	private static boolean isWinner;
	private static int playerTurn;
	

	
	public void start() {
		
		
		
	}
	
	
	
	
	public  void playersInfos( String firstPlayerName, int firstPlayerRole, String secondPlayerName)	{
		
		 firstPlayer = new Player(firstPlayerRole == 0 ? true: false, firstPlayerRole, firstPlayerName);
		
		 secondPlayer = new Player(firstPlayerRole == 0 ? false: true, firstPlayerRole == 0 ? 1 : 0, secondPlayerName);
	
		
	}
	
	
	
	public String getPlayer1Name() {
		return firstPlayer.getPlayerName();
	}

	public String getPlayer2Name() {
		return secondPlayer.getPlayerName();
	}

	
	
	
	
	
	
	
	
	
	
}
