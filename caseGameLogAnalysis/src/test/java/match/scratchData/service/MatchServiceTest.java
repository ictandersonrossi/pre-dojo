package match.scratchData.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import match.exception.MatchException;
import match.scratchData.bean.KillWeapon;
import match.scratchData.bean.MatchData;
import match.scratchData.bean.MatchPlayers;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/test/resources/applicationContext.xml")
public class MatchServiceTest {

	@Autowired
	private MatchService test;
	
	private static String ID = "1234";
	private static String UNKNOWN_MATCH ="unknown match";
	
	@Test
	public void ex1_startMatch() throws MatchException{
		MatchData match = test.newMatch(ID);
		assertNotNull(match);
		
		MatchData match2  = test.getCurrentMathData();
		assertNotNull(match2);
		
		assertTrue(match.equals(match2));
	}
	
	@Test(expected=MatchException.class)
	public void ex1_wrongID() throws MatchException{
		test.newMatch("");
	}	
	
	@Test
	public void ex2_endMatch() throws MatchException{
		MatchData match = test.newMatch(ID);
		assertNotNull(match);
		
		MatchData data = test.endMatch();
		
		assertTrue(test.getCurrentMathData().getId().equals(UNKNOWN_MATCH));
		assertTrue(data.getId().equals(ID));
	}	
	
	@Test
	public void ex2_endMatchConsolidate() throws MatchException{
		MatchData match = test.newMatch(ID);
		
		insertPlayers(match);
		insertWeapons(match);
		
		MatchData data = test.endMatch();
		
		MatchPlayers player = data.getPlayers().iterator().next();
		
		assertTrue(player.getBestWeapon().getName().equals("weapon4"));
		assertTrue(player.getName().equals("Name4"));
		assertTrue(player.isAward()); 
	}

	private void insertWeapons(MatchData match) {
		Set<MatchPlayers> players = match.getPlayers();
		for (MatchPlayers p : players) {
			Set<KillWeapon> list = buildWeaponList();			
			p.setListKillWeapon(list);
		}
		
	}

	private Set<KillWeapon> buildWeaponList() {
		Set<KillWeapon> list = new TreeSet<KillWeapon>();
		
		KillWeapon weapon;
		
		weapon = new KillWeapon();
		weapon.setName("weapon1");
		weapon.setCount(1);
		list.add(weapon);
		
		weapon = new KillWeapon();
		weapon.setName("weapon2");
		weapon.setCount(2);
		list.add(weapon);
		
		weapon = new KillWeapon();
		weapon.setName("weapon3");
		weapon.setCount(3);
		list.add(weapon);
		
		weapon = new KillWeapon();
		weapon.setName("weapon4");
		weapon.setCount(4);
		list.add(weapon);
		return list;
	}

	
	
	private void insertPlayers(MatchData match) {
		Set<MatchPlayers> list = new TreeSet<MatchPlayers>();
		match.setPlayers(list);
		
		MatchPlayers player;
		
		player = new MatchPlayers();
		player.setName("Name1");
		player.setKill(1);
		list.add(player);
		
		player = new MatchPlayers();
		player.setName("Name2");
		player.setKill(2);
		list.add(player);
		
		player = new MatchPlayers();
		player.setName("Name3");
		player.setKill(3);
		list.add(player);
		
		player = new MatchPlayers();
		player.setName("Name4");
		player.setKill(4);
		list.add(player);		
		
	}	
	
}
