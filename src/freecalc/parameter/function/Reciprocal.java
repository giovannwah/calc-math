package freecalc.parameter.function;

import java.util.Arrays;

import freecalc.execution.exception.ExecutionException;
import freecalc.execution.exception.OverflowException;
import freecalc.execution.exception.UnderflowException;
import freecalc.parameter.Function;
import freecalc.parameter.Parameter;

public class Reciprocal extends Function{
	public static final String symbol = "rec";
	public Reciprocal(Parameter [] p){
		super(p);
	}
	public Reciprocal(Reciprocal a){
		this.params = Arrays.copyOf(a.params, a.params.length);
		this.t = a.type();
	}
	public Reciprocal(){
		super();
	}
	@Override
	public String getSymbol(){
		return symbol;
	}
	@Override
	public double evaluate() throws ExecutionException, OverflowException, UnderflowException{
		if (this.params.length != 1){
			throw new ExecutionException("Reciprocal object can only have one execution argument.");
		}
		double ret = this.params[0].evaluate();
		if (ret == 0.0){
			throw new ExecutionException("Reciprocal: Can't divide by zero.");
		}
		
		ret = 1.0/ret;
		
		if (Function.overflow(ret)) throw new OverflowException("Reciprocal value is too large to be represented properly: "+ret);
		else if (Function.underflow(ret)) throw new UnderflowException("Reciprocal value is too small to be represented properly: "+ret);
		else return ret;
	}
	
	@Override
	public Parameter copy(){
		return new Reciprocal(this);
	}
}
