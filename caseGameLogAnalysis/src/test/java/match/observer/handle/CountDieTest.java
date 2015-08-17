package match.observer.handle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import match.observer.ObserverProcessGameLog;
import match.scratchData.bean.MatchPlayers;
import match.scratchData.service.MatchService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/test/resources/applicationContext.xml")
public class CountDieTest {
	@Autowired
	private CountDie test;
	
	@Autowired
	private MatchService match;	
	
	@Mock
	private ObserverProcessGameLog observer;
	
	private static String EX1 = "23/04/2013 15:36:04 - Roman killed Nick using M16";
	private static String EX1_RIGHT = "Nick";
	
	private static String EX2 = "23/04/2013 15:36:33 - <WORLD> killed Nick1 by DROWN";
	private static String EX2_LEFT = "<WORLD>";
	private static String EX2_RIGHT = "Nick1";
	
	private static String EX3 = "23/04/2013 15:36:33 - <WORLD> killed Nick2 by DROWN";
	private static String EX3_RIGHT = "Nick2";
	
	private static String EX4 = "23/04/2013 15:36:33 - <WORLD> killed Nick3 by DROWN";
	private static String EX4_RIGHT = "Nick3";	
	
	@Before
	public void init(){
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void ex1_dieAndAddPlayers(){
		when(observer.getLogLine()).thenReturn(EX1);
		test.onApplicationEvent(observer);
		
		boolean tf = (Utils.searchFor(EX1_RIGHT,match) != null);
		
		assertTrue(tf);
	}

	@Test
	public void ex2_dieAndDontAddWORLD(){
		when(observer.getLogLine()).thenReturn(EX2);
		test.onApplicationEvent(observer);
		
        boolean tf = (Utils.searchFor(EX2_LEFT,match) == null);
        assertTrue(tf);
		
		tf = (Utils.searchFor(EX2_RIGHT,match) != null);
		assertTrue(tf);
	}	
	
	@Test
	public void ex3_dieAndIncrementDie(){
		when(observer.getLogLine()).thenReturn(EX3);
		test.onApplicationEvent(observer);
		
		MatchPlayers player = Utils.searchFor(EX3_RIGHT, match);;
		
		assertEquals(1, player.getDie());
	}	
	
	@Test
	public void ex4_dieAndMaintainStreak(){
		when(observer.getLogLine()).thenReturn(EX4);
		test.onApplicationEvent(observer);
		
		MatchPlayers player = Utils.searchFor(EX4_RIGHT, match);
		
		assertEquals(1, player.getDie());
		
		player.setMaxStreak(5);
		player.setCurrentStreak(3);
		
		test.onApplicationEvent(observer);
		player = Utils.searchFor(EX4_RIGHT, match);
		
		assertEquals(5, player.getMaxStreak());
		assertEquals(0, player.getCurrentStreak());
	}	
	
	
}
