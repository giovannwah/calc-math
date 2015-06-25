package freecalc.parameter.function;

import java.util.Arrays;

import freecalc.execution.exception.ExecutionException;
import freecalc.execution.exception.OverflowException;
import freecalc.execution.exception.UnderflowException;
import freecalc.parameter.Function;
import freecalc.parameter.Parameter;

public class Root extends Function{
	private int function;
	private boolean commutative;
	public Root(Parameter [] p, int fu, boolean com){
		super(p);
		this.function = fu;
		this.commutative = com;
	}
	
	public Root(int fu, boolean com){
		super();
		this.function = fu;
		this.commutative = com;
	}
	
	public Root(Root a){
		this.params = Arrays.copyOf(a.params, a.params.length);
		this.t = a.type();
		this.function = a.function;
		this.commutative = a.commutative;
	}
	
	public Root(){
		super();
		this.function = 0; //sqrt
		this.commutative = false;
	}
	
	@Override
	public String getSymbol(){
		switch(this.function){
			case 0: return "sqrt";
			case 1: return "cbrt";
			default: return "nrt";
		}
	}
	
	public void setFunction(int b){
		this.function = b;
	}
	
	public int getFunction(){
		return this.function;
	}
	
	public void setCommutative(boolean b){
		this.commutative = b;
	}
	
	public boolean getCommutative(){
		return this.commutative;
	}
	
	@Override
	public double evaluate() throws ExecutionException, OverflowException, UnderflowException{
		double ret = 0;
			if (this.function < 2){
				if (this.params.length != 1){
					throw new ExecutionException("Square/Cube Root object can only have one execution argument.");
				}
				if ((ret = this.params[0].evaluate()) < 0.0){
					throw new ExecutionException("Root cannot evaluate anything less zero.");
				}
				if (ret == 0) return 0;
				else if (this.function == 1) ret = Math.cbrt(this.params[0].evaluate());
				else if (this.function == 0) ret = Math.sqrt(this.params[0].evaluate());
			}
			else {
				if (this.params.length != 2){
					throw new ExecutionException("Nth Root object must have exactly two execution arguments.");
				}
				if (!this.commutative){
					if ((ret = this.params[1].evaluate()) < 0.0){
						throw new ExecutionException("Root cannot evaluate anything less zero.");
					}
					if (ret == 0) return 0;
					ret = nthroot(this.params[0].evaluate(), this.params[1].evaluate());
				}
				else {
					if ((ret = this.params[0].evaluate()) < 0.0){
						throw new ExecutionException("Root cannot evaluate anything less zero.");
					}
					if (ret == 0) return 0;
					ret = nthroot(this.params[1].evaluate(), this.params[0].evaluate());
				}
			}
			if (Function.overflow(ret)) throw new OverflowException("Root value is too large to be represented properly: "+ret);
			else if (Function.underflow(ret)) throw new UnderflowException("Root value is too small to be represented properly: "+ret);
			else return ret;
	}
	
	/**
	 * Returns the nth root of the number x.
	 * @param n = root
	 * @param x = number
	 * @return
	 */
	public static double nthroot(double n, double x){
		return nthroot(n, x, .0000001);
	}
	
	/**
	 * Returns the nth root of the number x, with accuracy p.
	 * @param n = root
	 * @param x = number
	 * @param p = accuracy
	 * @return
	 */
	public static double nthroot(double n, double x, double p){
	    if(x == 0) return 0;
	    double x1 = x;
	    double x2 = x / n;  
	    while (Math.abs(x1 - x2) > p){
	    	x1 = x2;
	        x2 = ((n - 1.0) * x2 + x / Math.pow(x2, n - 1.0)) / n;
	    }
	    return x2;
	}
	
	@Override
	public Parameter copy(){
		return new Root(this);
	}
}
