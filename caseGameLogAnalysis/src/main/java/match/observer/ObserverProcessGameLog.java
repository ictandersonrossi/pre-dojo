package match.observer;

import org.springframework.context.ApplicationEvent;

import match.observable.ProcessGameLog;

public class ObserverProcessGameLog extends ApplicationEvent   {

	private static final long serialVersionUID = -8756127851626912127L;

	private String logLine;
	
	private ProcessGameLog source;
	
	public ObserverProcessGameLog(ProcessGameLog source, String logLine) {
		super(source);
		this.logLine = logLine;
		this.source = source;
	}

	public String getLogLine() {
		return logLine;
	}

	public void setLogLine(String logLine) {
		this.logLine = logLine;
	}

	public ProcessGameLog getSource() {
		return source;
	}

	public void setSource(ProcessGameLog source) {
		this.source = source;
	}
}
