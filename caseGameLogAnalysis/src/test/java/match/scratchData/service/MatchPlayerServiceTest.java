package match.scratchData.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import match.exception.MatchException;
import match.scratchData.bean.MatchPlayers;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/test/resources/applicationContext.xml")
public class MatchPlayerServiceTest {

	@Autowired
	private MatchPlayerService test;
	
	private static String PLAYER = "player";
	private static String LOGTIME= "23/04/2013 15:34:22";
	
	@Test
	public void ex1_addPlayerTest() throws MatchException{
		MatchPlayers player = test.addPlayer(PLAYER);
		assertNotNull(player);
	}
	
	@Test(expected=MatchException.class)
	public void ex1_wrongParamsTest() throws MatchException{
		test.addPlayer(null);
	}	
	
	@Test
	public void ex2_searchForTest() throws MatchException{
		test.addPlayer(PLAYER);
		MatchPlayers player = test.searchFor(PLAYER);
		assertNotNull(player);
		
		player = test.searchFor(PLAYER+"0");
		assertNull(player);
	}	
	
	@Test
	public void ex3_incrementKillTest() throws MatchException{
		test.incrementKill(PLAYER, LOGTIME);
	}
	
	@Test(expected=MatchException.class)
	public void ex3_wrongParamsTest() throws MatchException{
		test.incrementKill(PLAYER, "  ");
	}	
	
	@Test(expected=MatchException.class)
	public void ex3_wrongParams2Test() throws MatchException{
		test.incrementKill("", LOGTIME);
	}	
	
	@Test
	public void ex3_countTest() throws MatchException{
		MatchPlayers player = test.incrementKill(PLAYER, LOGTIME);
		
		assertTrue(player.getKill() > 0);
		assertTrue(player.getMaxStreak() > 0);
		assertTrue(player.getCurrentStreak() > 0);
		assertTrue(player.getAwardCount() > 0);
	}	
	
	@Test
	public void ex4_dieTest() throws MatchException{
		MatchPlayers player = test.incrementDie(PLAYER);
		
		assertTrue(player.getDie() > 0);
		assertTrue(player.getCurrentStreak() == 0);
	}	
	
}
