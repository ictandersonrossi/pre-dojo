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

import match.exception.MatchException;
import match.observer.ObserverProcessGameLog;
import match.scratchData.bean.MatchData;
import match.scratchData.service.MatchService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/test/resources/applicationContext.xml")
public class BeginEndMatchTest {

	@Autowired
	BeginEndMatch test;

	@Autowired
	private MatchService match;	
	
	@Mock
	private ObserverProcessGameLog observer;
	
	private static String START = "23/04/2013 15:34:22 - New match 11348965 has started";
	private static String END = "23/04/2013 15:39:22 - Match 11348965 has ended";
	private static String ID = "11348965";
	
	@Before
	public void init(){
		MockitoAnnotations.initMocks(this);
	}	
	
	@Test
	public void ex1_startMatch() throws MatchException{
		when(observer.getLogLine()).thenReturn(START);
		test.onApplicationEvent(observer);
		
		MatchData md = match.getCurrentMathData();
		
		assertTrue(md.getId().equals(ID));		
	}	
	
	@Test
	public void ex2_endtMatch() throws MatchException{
		when(observer.getLogLine()).thenReturn(END);
		test.onApplicationEvent(observer);
		
		MatchData md = match.getCurrentMathData();
		
		assertFalse(md.getId().equals(ID));		
	}	
	
	
}
