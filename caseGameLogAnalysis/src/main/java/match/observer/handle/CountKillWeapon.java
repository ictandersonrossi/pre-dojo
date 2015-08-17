package match.observer.handle;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import match.observer.ObserverProcessGameLog;
import match.observer.handle.util.HandleConstants;
import match.observer.handle.util.HandleUtils;
import match.scratchData.service.KillWeaponService;

@Component
public class CountKillWeapon implements ApplicationListener<ObserverProcessGameLog> {

	final static Logger logger = Logger.getLogger(CountKillWeapon.class);
	
	@Autowired
	private KillWeaponService service;
	
	public void onApplicationEvent(ObserverProcessGameLog observer) {
		logger.debug(HandleConstants.ENTERING);
		try {
			HandleUtils.validateLineLog(observer.getLogLine());
			String[] st = observer.getLogLine().split(HandleConstants.SPACE);
			int pos = HandleUtils.middlePos(st);
			String weaponName = buildWeapon(st,pos);
			String playerName = buildPlayerName(st,pos);
			validate(weaponName, playerName);
			service.addWeapon(weaponName, playerName);
			
		} catch (Exception e) {
			logger.error(e);
		}
	}

	private void validate(String weaponName, String playerName) {
		if (HandleUtils.isValidString(playerName) &&
		    HandleUtils.isValidString(weaponName)){
			logger.debug("Player and name is valid "+playerName+","+weaponName);
		} else {
			throw new IllegalArgumentException("Player or weapon is invalid "+playerName+","+weaponName);
		}
		
	}

	private String buildPlayerName(String[] st, int pos) {
		if (pos <= 0){
			return null;
		}
		
		return st[pos-1];
	}

	private String buildWeapon(String[] st, int pos) {
		if (pos <= 0){
			return null;
		}
		return st[st.length-1];
	}


	
}
