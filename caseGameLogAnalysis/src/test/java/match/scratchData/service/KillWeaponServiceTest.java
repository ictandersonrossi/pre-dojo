package match.scratchData.service;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import match.exception.MatchException;
import match.scratchData.bean.KillWeapon;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/test/resources/applicationContext.xml")
public class KillWeaponServiceTest {

	@Autowired
	private KillWeaponService test;
	
	
	private static String WEAPON = "weapon";
	private static String PLAYER = "player";
	private static String PLAYER_INVALID = "<WORLD>";
	
	@Test
	public void ex1_addWeaponTest(){
		try {
			KillWeapon weapon = test.addWeapon(WEAPON, PLAYER);
			int qtd = weapon.getCount();
			assertEquals(1, qtd);
		} catch (MatchException e) {
			e.printStackTrace();
		}
	}

	@Test(expected=MatchException.class)
	public void ex2_wrongParams() throws MatchException{
		test.addWeapon("", PLAYER);
	}

	@Test(expected=MatchException.class)
	public void ex2_wrongParam2s() throws MatchException{
		test.addWeapon(WEAPON, null);
	}	
	
	@Test(expected=MatchException.class)
	public void ex3_wrongPlayer() throws MatchException{
		test.addWeapon(WEAPON, PLAYER_INVALID);
	}	
}
