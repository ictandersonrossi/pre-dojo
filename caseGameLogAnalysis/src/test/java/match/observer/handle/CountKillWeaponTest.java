package match.observer.handle;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import match.observer.ObserverProcessGameLog;
import match.scratchData.bean.KillWeapon;
import match.scratchData.bean.MatchPlayers;
import match.scratchData.service.MatchService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/test/resources/applicationContext.xml")
public class CountKillWeaponTest {

	@Autowired
	private CountKillWeapon test;
	
	@Autowired
	private MatchService match;		
	
	@Mock
	private ObserverProcessGameLog observer;	
	
	private static String EX1 = "23/04/2013 15:36:04 - Roman killed Nick using M16";
	private static String EX1_RIGHT = "Roman";	
	private static String EX1_WEAPON = "M16";
	
	private static String EX2 = "23/04/2013 15:36:33 - <WORLD> killed Nick1 by DROWN";
	private static String EX2_LEFT = "<WORLD>";
	@Before
	public void init(){
		MockitoAnnotations.initMocks(this);
	}	
	
	@Test
	public void ex1_CountKill(){
		when(observer.getLogLine()).thenReturn(EX1);
		test.onApplicationEvent(observer);	
		
		MatchPlayers player = Utils.searchFor(EX1_RIGHT,match);
		assertNotNull(player);

		KillWeapon weapon = searchFor(EX1_WEAPON,player);
		assertNotNull(weapon);
		
		assertTrue(weapon.getCount() > 0);
	}	
	
	@Test
	public void ex2_NotCountKill(){
		when(observer.getLogLine()).thenReturn(EX2);
		test.onApplicationEvent(observer);	
		
        boolean tf = (Utils.searchFor(EX2_LEFT,match) == null);
        assertTrue(tf);		
	}		
	
	private KillWeapon searchFor(String weaponName, MatchPlayers player){
		Set<KillWeapon> weaponList = player.getListKillWeapon();
		for (KillWeapon weapon : weaponList) {
			
			if (weapon.getName().equals(weaponName)){
				return weapon;
			}
			
		}
		
		return null;
	}
	
	
}
