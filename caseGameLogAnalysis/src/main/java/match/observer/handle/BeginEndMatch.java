package match.observer.handle;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import match.exception.MatchException;
import match.observer.ObserverProcessGameLog;
import match.observer.handle.util.HandleConstants;
import match.observer.handle.util.HandleUtils;
import match.scratchData.service.MatchService;

@Component
public class BeginEndMatch implements ApplicationListener<ObserverProcessGameLog> {

	final static Logger logger = Logger.getLogger(BeginEndMatch.class);
	
	@Autowired
	private MatchService service;
	
	public void onApplicationEvent(ObserverProcessGameLog observer) {
		logger.debug(HandleConstants.ENTERING);
		try {
			HandleUtils.validateLineLog(observer.getLogLine());
			String[] st = observer.getLogLine().split(HandleConstants.SPACE);
			
			int pos = HandleUtils.search(st,HandleConstants.HAS);

			startMatch(st,pos);
			endMatch(st,pos);
		} catch (Exception e) {
			logger.error(e);
		}
	}

	private void endMatch(String[] st, int pos) throws MatchException {
		if (HandleConstants.ENDED.equals(st[pos+1])){
			service.endMatch();
		}
	}

	private void startMatch(String[] st, int pos) throws MatchException {
		if (HandleConstants.STARTED.equals(st[pos+1])){
			service.newMatch(st[pos-1]);
		}
	}

}
