package match.scratchData.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import match.exception.MatchException;
import match.observer.handle.util.HandleConstants;
import match.observer.handle.util.HandleUtils;
import match.scratchData.bean.KillWeapon;
import match.scratchData.bean.MatchData;
import match.scratchData.bean.MatchPlayers;

@Service
public class MatchService {

	private final static Logger logger = Logger.getLogger(MatchService.class);
	
	private MatchData matchData;
	
	private List<MatchData> listMathData;
	
	public MatchData getCurrentMathData() throws MatchException{
		
		if (matchData==null){
			newMatch(HandleConstants.UNKNOWN_MATCH);
		}
		
		return matchData;
	}
	
	public List<MatchData> getListMatchData(){
		return listMathData;
	}

	public void setCurrentDate(Date currentDate) throws MatchException{
		getCurrentMathData().setCurrentDate(currentDate);
	}
	
	public MatchData endMatch(){
		if (matchData == null){
			return null;
		}
		
		MatchData data = this.matchData;
		this.matchData = null;
		
		markAward(data);
		markBestWeapon(data);
		rankingPlayers(data);
		
		return data;
	}

	private void rankingPlayers(MatchData match) {
		Set<MatchPlayers> players = match.getPlayers();
		if (players == null){
			return;
		}		
		Set<MatchPlayers> ranking = buildRanking(players);
		match.setPlayers(ranking);
	}
	
	private void markBestWeapon(MatchData match){
		Set<MatchPlayers> players = match.getPlayers();
		if (players == null){
			return;
		}
		
		for (MatchPlayers p : players) {
			Set<KillWeapon> weapon = sortWeapon(p.getListKillWeapon());
			if (weapon != null){
				KillWeapon w = weapon.iterator().next();
				p.setBestWeapon(w);
			}
		}
		
	}
	
	private Set<KillWeapon> sortWeapon(Set<KillWeapon> weapon) {
		if(weapon == null){
			return null;
		}
		
		Set<KillWeapon> sorted = buildReverseSortedWeapon();
		sorted.addAll(weapon);
		return sorted;
	}
	
	private Set<KillWeapon> buildReverseSortedWeapon(){
		return new TreeSet<KillWeapon>(new Comparator<KillWeapon>() {
			public int compare(KillWeapon k1, KillWeapon k2) {
				if (k2.getCount() == k1.getCount()){
					return -1;
				}
				
				return new Integer(k2.getCount()).compareTo(k1.getCount());
			}
		});		
	}

	private void markAward(MatchData match){
		Set<MatchPlayers> players = match.getPlayers();
		
		if (players == null){
			return;
		}
		
		for (MatchPlayers p : players) {
			if (p.getDie() == 0){
				p.setAward(true);
			}
		}
	}
	
	public MatchData newMatch(String id) throws MatchException{
		try{
			validate(id);
			
			MatchData match = new MatchData();
			match.setId(id);
			
			if (this.listMathData == null){
				this.listMathData = new ArrayList<MatchData>();
			}
			
			this.listMathData.add(match);
			this.matchData = match;
			
			return match;
		}catch(Exception e){
			logger.error(e);
			throw new MatchException(e);
		}
	}
	
	private void validate(String id) {
		if ( !(HandleUtils.isValidString(id))) {
				throw new IllegalArgumentException(HandleConstants.INVALID_ARGS+id);
			}
	}	

	private Set<MatchPlayers> buildRanking(Set<MatchPlayers> listPlayer){
		if(listPlayer == null){
			return null;
		}
		
		Set<MatchPlayers> players = buidReverseSortedPlayers();
		players.addAll(listPlayer);
		return players;
	}
		
	private Set<MatchPlayers> buidReverseSortedPlayers(){
		Set<MatchPlayers> players = new TreeSet<MatchPlayers>(new Comparator<MatchPlayers>() {
			public int compare(MatchPlayers o1, MatchPlayers o2) {
				
				if (o2.getKill() == o1.getKill()){
					return -1;
				}
				
				return new Integer(o2.getKill()).compareTo(o1.getKill());
			}
		});
		
		return players;
	}	
	
	
}
