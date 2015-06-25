package freecalc.parameter;

import java.util.Arrays;

import freecalc.execution.exception.ExecutionException;
import freecalc.execution.exception.OverflowException;
import freecalc.execution.exception.UnderflowException;


public class Function implements Parameter{
	public static String symbol = "???";
	protected Parameter[] params;
	protected int t;
	
	public Function(Parameter[] p){
		this.params = p;
		this.t = 2;
	}
	public Function(){
		this.params = null;
		this.t = 2;
	}
	
	public Parameter[] getParams(){
		return this.params;
	}
	
	public void setParams(Parameter[] p){
		this.params = p;
	}
	
	public String getSymbol(){
		return symbol;
	}
	/**
	 * Determines if the parameter list contains any Parameters that evaluate to zero
	 * @return contains zero or not
	 */
	public boolean containsZero(){
		for (Parameter p : params){
			try{
				if (p.evaluate() == 0){
					return true;
				}
			}
			catch (Exception e){
				e.printStackTrace();
			}
		}
		return false;
	}
	
	public int type() {
		return t;
	}
	
	/**
	 * Returns true if the double value being observed is very close to an Integer
	 * @param d
	 * @return true or false
	 */
	public static boolean isInteger(double d){
		if (d % 1 == 0.0) return true; 
		else if (d % 1 <= 0.0000000000001) return true; // close enough
		else if (d % 1 >= 0.9999999999999) return true; // close enough
		else return false;
	}
	
	/**
	 * Returns true if the double value being observed is less than Double.MIN_VALUE
	 * @param d
	 * @return true or false
	 */
	public static boolean underflow(double d){
		if (Double.compare(-Double.MAX_VALUE, d) > 0) return true;
		else return false;
	}
	
	/**
	 * Returns true if the double value being observed is greater than Double.MAX_VALUE
	 * @param d
	 * @return true or false
	 */
	public static boolean overflow(double d){
		if (Double.compare(Double.MAX_VALUE, d) < 0) return true;
		else return false;
	}
	
	public String printParams(){
		String s = new String();
		
		return s;
	}
	
	/**
	 * Every function must be able to be evaluated
	 * @return
	 * @throws Exception 
	 */
	public double evaluate() throws ExecutionException, OverflowException, UnderflowException{ 
		return 0; 
	}
	
	public Parameter copy() {
		return null;
	}
	
	public static void main (String [] args){
		System.out.println(87.999999999999%1);
	}
}
