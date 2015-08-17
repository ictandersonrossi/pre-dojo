package match.exception;

public class MatchException extends Exception {

	private static final long serialVersionUID = 6734451580672529333L;

	public MatchException() {
		super();
	}

	public MatchException(String message, Throwable cause) {
		super(message, cause);
	}

	public MatchException(String message) {
		super(message);
	}

	public MatchException(Throwable cause) {
		super(cause);
	}	
	
}
