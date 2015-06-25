package freecalc.parameter.function;

import java.util.Arrays;

import freecalc.execution.exception.ExecutionException;
import freecalc.execution.exception.OverflowException;
import freecalc.execution.exception.UnderflowException;
import freecalc.parameter.Function;
import freecalc.parameter.Parameter;

public class Multiplication extends Function{
	public static final String symbol = "*";
	public Multiplication(Parameter [] p){
		super(p);
	}
	public Multiplication(Multiplication a){
		this.params = Arrays.copyOf(a.params, a.params.length);
		this.t = a.type();
	}
	public Multiplication(){
		super();
	}
	@Override
	public String getSymbol(){
		return symbol;
	}
	@Override
	public double evaluate() throws ExecutionException, OverflowException, UnderflowException{
		if (this.containsZero()){
			return 0;
		}
		if (this.params.length != 2){
			throw new ExecutionException("Multiplication takes two and only two arguments.");
		}
		double ret = 0;
		ret = this.params[0].evaluate() * this.params[1].evaluate();
		if (Function.overflow(ret)) throw new OverflowException("Multiplication value is too large to be represented properly: "+ret);
		else if (Function.underflow(ret)) throw new UnderflowException("Multiplication value is too small to be represented properly: "+ret);
		else return ret;
	}

	@Override
	public Parameter copy(){
		return new Multiplication(this);
	}
}
