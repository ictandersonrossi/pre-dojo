package match.scratchData.service;

import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import match.exception.MatchException;
import match.observer.handle.util.HandleConstants;
import match.observer.handle.util.HandleUtils;
import match.scratchData.bean.KillWeapon;
import match.scratchData.bean.MatchPlayers;


@Service
public class KillWeaponService {
	
	private final static Logger logger = Logger.getLogger(KillWeaponService.class);

	@Autowired
	private MatchPlayerService matchPlayerDao;	
	
	public KillWeapon addWeapon(String weaponName, String playerName) throws MatchException{
		try {
			validate(weaponName,playerName);
			validateProcessable(playerName);
			MatchPlayers player = processdPlayer(playerName);
			processListWeapon(weaponName,player);
			KillWeapon weapon = searchFor(weaponName, player);
			incrementCount(weapon);
			return weapon;
		} catch (Exception e){
			logger.error(e);
			throw new MatchException(e);
		}
	}
	
	private void validateProcessable(String playerName){
		if ("<WORLD>".equals(playerName)){
		  throw new IllegalArgumentException(HandleConstants.NOT_PROCESS_ARGS+playerName);
		}
	}	
	
	private void validate(String weaponName, String playerName){
		if ( !((HandleUtils.isValidString(playerName)) && 
			   (HandleUtils.isValidString(weaponName)))){
			throw new IllegalArgumentException(HandleConstants.INVALID_ARGS+weaponName+","+playerName);
		}
	}
	
	private void incrementCount(KillWeapon weapon) {
		if (weapon == null){
			logger.debug("Weapon not determined");
			return;
		}
		
		int count = weapon.getCount();
		count++;
		weapon.setCount(count);
	}

	private KillWeapon searchFor(String weaponName,MatchPlayers player){
		Set<KillWeapon> listWeapon = player.getListKillWeapon();
		for (KillWeapon weapon : listWeapon) {
			if (weapon.getName().equals(weaponName)) {
				return weapon;
			}
		}
		
		return null;
	}
	
	private Set<KillWeapon> processListWeapon(String weaponName, MatchPlayers player){
		Set<KillWeapon> listWeapon = player.getListKillWeapon();
		KillWeapon weapon = buildWeapon(weaponName);
		
		if (listWeapon == null){
			listWeapon = builtListWeapon();
			player.setListKillWeapon(listWeapon);
		}
		
		listWeapon.add(weapon);
		
		return listWeapon;
	}
	
	private MatchPlayers processdPlayer(String playerName) throws MatchException{
		matchPlayerDao.addPlayer(playerName);
		return matchPlayerDao.searchFor(playerName);		
	}
	
	private Set<KillWeapon> builtListWeapon(){
		return new TreeSet<KillWeapon>();
	}

	private KillWeapon buildWeapon(String weaponName){
		KillWeapon weapon = new KillWeapon();
		weapon.setName(weaponName);
		
		return weapon;
	}
}
