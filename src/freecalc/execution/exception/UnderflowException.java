package freecalc.execution.exception;

public class UnderflowException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	 	public UnderflowException() {
	        
	 	}
	 	public UnderflowException(String message) {
	 		super(message);
	 	}
	 	public UnderflowException(Throwable cause) {
	 		super(cause);
	 	}
	 	public UnderflowException(String message, Throwable cause) {
	 		super(message, cause);
	 	}
	 
	 	@Override
	 	public String getMessage(){
	 		return super.getMessage();
	 	}
}
