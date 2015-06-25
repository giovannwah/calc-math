package freecalc.parameter.function;

import java.util.Arrays;

import freecalc.execution.exception.ExecutionException;
import freecalc.execution.exception.OverflowException;
import freecalc.execution.exception.UnderflowException;
import freecalc.parameter.Function;
import freecalc.parameter.Parameter;

public class Negative extends Function{
	public static final String symbol = "neg";
	public Negative(Parameter [] p){
		super(p);
	}
	public Negative(Negative a){
		this.params = Arrays.copyOf(a.params, a.params.length);
		this.t = a.type();
	}
	public Negative(){
		super();
	}
	@Override
	public String getSymbol(){
		return symbol;
	}
	@Override
	public double evaluate() throws ExecutionException, OverflowException, UnderflowException{
		if (this.params.length != 1){
			throw new ExecutionException("Negative object can only have one execution argument.");
		}
		double ret = 0;
		ret = -1 * this.params[0].evaluate();
		if (Function.overflow(ret)) throw new OverflowException("Negative value is too large to be represented properly: "+ret);
		else if (Function.underflow(ret)) throw new UnderflowException("Negative value is too small to be represented properly: "+ret);
		else return ret;
	}
	
	@Override
	public Parameter copy(){
		return new Negative(this);
	}
}
