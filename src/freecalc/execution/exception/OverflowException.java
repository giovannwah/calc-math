package freecalc.execution.exception;

public class OverflowException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	 	public OverflowException() {
	        
	 	}
	 	public OverflowException(String message) {
	 		super(message);
	 	}
	 	public OverflowException(Throwable cause) {
	 		super(cause);
	 	}
	 	public OverflowException(String message, Throwable cause) {
	 		super(message, cause);
	 	}
	 
	 	@Override
	 	public String getMessage(){
	 		return super.getMessage();
	 	}
}
