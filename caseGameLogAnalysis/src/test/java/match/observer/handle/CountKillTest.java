package match.observer.handle;

import static org.mockito.Mockito.when;
import static org.junit.Assert.*;

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
public class CountKillTest {

	@Autowired
	private CountKill test;
	
	@Autowired
	private MatchService match;	
	
	@Mock
	private ObserverProcessGameLog observer;
	
	private static String EX1 = "23/04/2013 15:36:04 - Roman killed Nick using M16";
	private static String EX1_RIGHT = "Roman";

	private static String EX2 = "23/04/2013 15:36:04 - Roman2 killed Nick using M16";
	private static String EX2_RIGHT = "Roman2";
	
	private static String EX3 = "23/04/2013 15:36:04 - Roman3 killed Nick using M16";
	private static String EX3_RIGHT = "Roman3";	

	private static String EX4_0 = "23/04/2013 15:36:00 - Roman4 killed Nick using M16";
	private static String EX4_1 = "23/04/2013 15:36:10 - Roman4 killed Nick using M16";
	private static String EX4_2 = "23/04/2013 15:36:30 - Roman4 killed Nick using M16";
	private static String EX4_3 = "23/04/2013 15:36:50 - Roman4 killed Nick using M16";
	private static String EX4_4 = "23/04/2013 15:37:00 - Roman4 killed Nick using M16";
	private static String EX4_RIGHT = "Roman4";	
	
	
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

		assertEquals(1, player.getKill());
	}

	@Test
	public void ex2_IncrementStreak(){
		when(observer.getLogLine()).thenReturn(EX2);
		test.onApplicationEvent(observer);	
		
		MatchPlayers player = Utils.searchFor(EX2_RIGHT,match);
		assertNotNull(player);

		assertEquals(1, player.getCurrentStreak());
	}
	
	@Test
	public void ex3_IncrementAndMaintainMaxStreak(){
		MatchPlayers player = ex3_partCount();
		assertEquals(1, player.getMaxStreak());
		
		player = ex3_partCount();
		assertEquals(2, player.getMaxStreak());
		
		player.setCurrentStreak(0);
		
		player = ex3_partCount();
		assertEquals(2, player.getMaxStreak());	
		
		player = ex3_partCount();
		assertEquals(2, player.getMaxStreak());	
		
		player = ex3_partCount();
		assertEquals(3, player.getMaxStreak());			
	}	
	
	private MatchPlayers ex3_partCount(){
		when(observer.getLogLine()).thenReturn(EX3);
		test.onApplicationEvent(observer);	
		
		MatchPlayers player = Utils.searchFor(EX3_RIGHT,match);
		assertNotNull(player);		
		
		return player;
	}
	
	
	@Test
	public void ex4_killAward(){
		when(observer.getLogLine()).thenReturn(EX4_0);
		test.onApplicationEvent(observer);	
		
		MatchPlayers player = Utils.searchFor(EX4_RIGHT,match);
		assertNotNull(player);
		assertEquals(false, player.isAward());		
		
		when(observer.getLogLine()).thenReturn(EX4_1);
		test.onApplicationEvent(observer);
		
		when(observer.getLogLine()).thenReturn(EX4_2);
		test.onApplicationEvent(observer);
		
		when(observer.getLogLine()).thenReturn(EX4_3);
		test.onApplicationEvent(observer);
		
		when(observer.getLogLine()).thenReturn(EX4_4);
		test.onApplicationEvent(observer);		
		
		player = Utils.searchFor(EX4_RIGHT,match);
		assertNotNull(player);

		assertEquals(true, player.isAward());
	}	
	
}
