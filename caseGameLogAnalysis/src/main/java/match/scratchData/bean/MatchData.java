package match.scratchData.bean;

import java.util.Date;
import java.util.Set;

public class MatchData {
	
	private String id;
	
	private Set<MatchPlayers> players;

	private Date currentDate;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Set<MatchPlayers> getPlayers() {
		return players;
	}

	public void setPlayers(Set<MatchPlayers> players) {
		this.players = players;
	}

	public Date getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
	}

}
