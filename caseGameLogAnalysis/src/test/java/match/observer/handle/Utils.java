package match.observer.handle;

import java.util.Set;

import match.exception.MatchException;
import match.scratchData.bean.MatchPlayers;
import match.scratchData.service.MatchService;

public class Utils {
	
	/**
	 * Return MatchPlayer found by the 'playerName'
	 * 
	 * @param playerName
	 * @param match
	 * @return
	 */
	public static MatchPlayers searchFor(String playerName, MatchService match){
		try {
			Set<MatchPlayers> listPlayer;
			listPlayer = match.getCurrentMathData().getPlayers();
			if ((listPlayer != null) && !(listPlayer.isEmpty())){
				for (MatchPlayers player : listPlayer) {
					if (player.getName().equals(playerName)){
						return player;
					}
				}		
			}
		} catch (MatchException e) {
		}
	 	
		return null;
	}	
	
}
