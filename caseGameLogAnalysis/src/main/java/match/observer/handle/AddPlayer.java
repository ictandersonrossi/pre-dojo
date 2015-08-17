package match.observer.handle;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import match.exception.MatchException;
import match.observer.ObserverProcessGameLog;
import match.observer.handle.util.HandleConstants;
import match.observer.handle.util.HandleUtils;
import match.scratchData.service.MatchPlayerService;

@Component
public class AddPlayer implements ApplicationListener<ObserverProcessGameLog> {

	@Autowired
	private MatchPlayerService service;
	
	private final static Logger logger = Logger.getLogger(AddPlayer.class);
	
	public void onApplicationEvent(ObserverProcessGameLog observer) {
		logger.debug(HandleConstants.ENTERING);
		
		try {
			HandleUtils.validateLineLog(observer.getLogLine());
			String[] st = observer.getLogLine().split(HandleConstants.SPACE);
			int pos = HandleUtils.middlePos(st);
			addPlayerAtLeft(st,pos);
			addPlayerAtRigth(st,pos);
			
		} catch (Exception e) {
			logger.error(e);
		}
	}

	private void addPlayerAtRigth(String[] st, int pos) throws MatchException {
		if (pos <= 0){
			return;
		}
		service.addPlayer(st[pos+1]);		
	}

	private void addPlayerAtLeft(String[] st, int pos) throws MatchException {
		if (pos <= 0){
			return;
		}		
		service.addPlayer(st[pos-1]);
	}
}
