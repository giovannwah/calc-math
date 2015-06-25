package freecalc.parameter.function;

import java.util.Arrays;

import freecalc.execution.exception.ExecutionException;
import freecalc.execution.exception.OverflowException;
import freecalc.execution.exception.UnderflowException;
import freecalc.parameter.Function;
import freecalc.parameter.Parameter;

public class Addition extends Function{
	public static final String symbol = "+";
	public Addition(Parameter [] p){
		super(p);
	}
	public Addition(Addition a){
		this.params = Arrays.copyOf(a.params, a.params.length);
		this.t = a.type();
	}
	public Addition(){
		super();
	}
	@Override
	public String getSymbol(){
		return symbol;
	}
	@Override
	public double evaluate() throws ExecutionException, OverflowException, UnderflowException{
		double ret = 0;
		if (this.params.length != 2){
			throw new ExecutionException("Addition takes two and only two arguments.");
		}
		try{
			ret = this.params[0].evaluate() + this.params[1].evaluate();
		}
		catch (Exception e){
			e.printStackTrace();
		}
		if (Function.overflow(ret)) throw new OverflowException("Addition value is to large to be represented properly: "+ret);
		else if (Function.underflow(ret)) throw new UnderflowException("Addition value is too small to be represented properly: "+ret);
		else return ret;
	}
	
	@Override
	public Parameter copy(){
		return new Addition(this);
	}
}
