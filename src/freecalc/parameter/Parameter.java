package freecalc.parameter;

import freecalc.execution.exception.ExecutionException;
import freecalc.execution.exception.OverflowException;
import freecalc.execution.exception.UnderflowException;

public interface Parameter {
	/**
	 * Provides the identification of the Parameter
	 * @return 0 for Scalar, 1 for Variable, 2 for Function
	 */
	public int type();
	
	/**
	 * Evaluates the parameter
	 * @return the double value that the parameter evaluates out to
	 * @throws Exception
	 */
	public double evaluate() throws ExecutionException, OverflowException, UnderflowException;
	
	/**
	 * Creates an exact copy of the calling Parameter object
	 * @return new Parameter object
	 */
	public Parameter copy();
	
	/**
	 * Returns the String that represents the Parameter
	 * @return String symbol
	 */
	public String getSymbol();
}
