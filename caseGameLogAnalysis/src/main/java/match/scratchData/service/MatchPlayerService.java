package match.scratchData.service;

import java.text.ParseException;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import match.exception.MatchException;
import match.observer.handle.util.HandleConstants;
import match.observer.handle.util.HandleUtils;
import match.scratchData.bean.MatchPlayers;

@Service
public class MatchPlayerService {
	
	private final static Logger logger = Logger.getLogger(MatchPlayerService.class);

	@Autowired
	private MatchService match;
	
	public MatchPlayers addPlayer(String playerName) throws MatchException{
		try{
			validatePlayerName(playerName);
			
			Set<MatchPlayers> listPlayer = match.getCurrentMathData().getPlayers();
			
			if (listPlayer == null) {
				listPlayer = buildListPlayers();
			}
			MatchPlayers player = buildMatchPlayer(playerName);
			
			if (!listPlayer.add(player)){
				player = searchFor(playerName);
			}
			
			return player;
		}catch(Exception e){
			logger.error(e);
			throw new MatchException(e);
		}
	}	
	
	private void validatePlayerName(String playerName) {
		if ( !(HandleUtils.isValidString(playerName))) {
				throw new IllegalArgumentException(HandleConstants.INVALID_ARGS+playerName);
			}
	}
	
	private void validateProcessable(String playerName){
		validatePlayerName(playerName);
	
		if ("<WORLD>".equals(playerName)){
		  throw new IllegalArgumentException(HandleConstants.NOT_PROCESS_ARGS+playerName);
		}
	}

	private Set<MatchPlayers> buildListPlayers() throws MatchException{
		Set<MatchPlayers> listPlayer = new TreeSet<MatchPlayers>(); 
		match.getCurrentMathData().setPlayers(listPlayer);
		return listPlayer;
	}	
	
	private MatchPlayers buildMatchPlayer(String playerName){
		MatchPlayers player = new MatchPlayers();
		player.setName(playerName);
		
		return player;
	}
	
	public MatchPlayers incrementKill(String playerName, String currentLogTime) throws MatchException {
		try {
			validatePlayerName(playerName);
			validateProcessable(playerName);
			validateTime(currentLogTime);
			addCurrentTime(currentLogTime);
			MatchPlayers player = addPlayer(playerName);
			incrementKill(player);
			incrementStreak(player);
			incrementAwardCount(player);
			award5kills(player);
			return player;
		} catch (Exception e) {
			logger.error(e);
			throw new MatchException(e);
		}
	}
	
	private void validateTime(String currentLogTime) {
		try {
			HandleUtils.parseToDate(currentLogTime);
		} catch (ParseException e) {
			throw new IllegalArgumentException(HandleConstants.INVALID_ARGS+currentLogTime);
		}
	}

	private void addCurrentTime(String currentLogTime) throws ParseException, MatchException {
		Date dt = HandleUtils.parseToDate(currentLogTime);
		match.setCurrentDate(dt);
	}	
	
	private void award5kills(MatchPlayers player) throws MatchException {
		
		if (player.isAward()){
			return;
		}
		
		Date currentDate = match.getCurrentMathData().getCurrentDate();
		Date awardDate = player.getAwardDate();
		int awardCount = player.getAwardCount();
		
		if (awardDate == null){
			awardDate = currentDate;
			awardCount = 0;
			
			player.setAwardDate(awardDate);
		}
		
		if ((awardCount >= 5) && (minutesDiff(awardDate,currentDate) <= 1 )){
			player.setAward(true);
		}
	}

	private long minutesDiff(Date start, Date end){
		long diff = end.getTime() - start.getTime();
		long diffMinutes = diff / (60 * 1000) % 60;
		
		return diffMinutes;
	}	
	
	private void incrementAwardCount(MatchPlayers player) {
		int award = player.getAwardCount();
		award++;
		player.setAwardCount(award);
	}

	private void incrementStreak(MatchPlayers player) {
		int max = player.getMaxStreak();
		int curr = player.getCurrentStreak()+1;
		
		if (curr > max){
			max = curr;
		}
		
		player.setMaxStreak(max);
		player.setCurrentStreak(curr);
		
	}

	private void incrementKill(MatchPlayers player) {
		int kill = player.getKill();
		kill++;
		player.setKill(kill);
	}

	public MatchPlayers incrementDie(String playerName) throws MatchException{
		validatePlayerName(playerName);
		MatchPlayers player = addPlayer(playerName);
		incrementDie(player);
		resetStreak(player);
		return player;
	}


	
	private void resetStreak(MatchPlayers player) {
		int max = player.getMaxStreak();
		int curr = player.getCurrentStreak();
		
		if (curr > max){
			max = curr;
		}
		
		curr = 0;
		
		player.setMaxStreak(max);
		player.setCurrentStreak(curr);
	}

	private void incrementDie(MatchPlayers player) {
		int die = player.getDie();
		die++;
		player.setDie(die);
	}

	public MatchPlayers searchFor(String playerName) throws MatchException {
		try {
			validatePlayerName(playerName);
			
			Set<MatchPlayers> listPlayer = match.getCurrentMathData().getPlayers();
			for (MatchPlayers player : listPlayer) {
				if (player.getName().equals(playerName)){
					return player;
				}
			}
			
			return null;
			
		}catch(Exception e){
			logger.error(e);
			throw new MatchException(e);
		}
	}	
	
}
