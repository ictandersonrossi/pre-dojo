package match.observable;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

import match.observer.ObserverProcessGameLog;

@Component("processGameLog")
public class ProcessGameLog implements ApplicationEventPublisherAware  {
	private ApplicationEventPublisher publisher;

	public void processLine(String logLine){
		ObserverProcessGameLog event = new ObserverProcessGameLog(this,logLine);
		publisher.publishEvent(event);
	}
	
	public void setApplicationEventPublisher(ApplicationEventPublisher publisher) {
		this.publisher = publisher;
	}
}
