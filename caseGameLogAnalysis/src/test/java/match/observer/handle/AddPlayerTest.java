package match.observer.handle;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
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

import match.exception.MatchException;
import match.observer.ObserverProcessGameLog;
import match.scratchData.bean.MatchPlayers;
import match.scratchData.service.MatchService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/test/resources/applicationContext.xml")
public class AddPlayerTest {
	
	@Autowired
	private AddPlayer test;
	
	@Autowired
	private MatchService match;	
	
	@Mock
	private ObserverProcessGameLog observer;
	
	private static String EX1 = "23/04/2013 15:36:04 - Roman killed Nick using M16";
	private static String EX1_LEFT = "Roman";
	private static String EX1_RIGHT = "Nick";
	
	private static String EX2 = "23/04/2013 15:36:33 - <WORLD> killed Nick by DROWN";
	private static String EX2_LEFT = "<WORLD>";
	private static String EX2_RIGHT = "Nick";
	
	@Before
	public void init(){
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void ex1(){
		when(observer.getLogLine()).thenReturn(EX1);
		test.onApplicationEvent(observer);
		searchFor(EX1_LEFT);
		searchFor(EX1_RIGHT);
	}

	@Test
	public void ex2(){
		when(observer.getLogLine()).thenReturn(EX2);
		test.onApplicationEvent(observer);
		searchFor(EX2_LEFT);
		searchFor(EX2_RIGHT);
	}	
	
	
	private void searchFor(String playerName){
		boolean found;
		try {
			Set<MatchPlayers> listPlayer;
			listPlayer = match.getCurrentMathData().getPlayers();
			assertNotNull(listPlayer);
			found = false;
			for (MatchPlayers player : listPlayer) {
				if (player.getName().equals(playerName)){
					found = true;
					break;
				}
			}
		} catch (MatchException e) {
			found = false;
		}
	 
		assertTrue(found);
	}
	
}
