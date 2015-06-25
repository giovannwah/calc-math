package freecalc.execution.exception;

public class ExecutionException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	 	public ExecutionException() {
		 
	 	}

	    public ExecutionException(String message) {
	        super(message);
	    }

	    public ExecutionException(Throwable cause) {
	        super(cause);
	    }

	    public ExecutionException(String message, Throwable cause) {
	        super(message, cause);
	    } 
	    
	    @Override
		 public String getMessage(){
			 return super.getMessage();
		 }
}
