package freecalc.parameter.function;

import java.util.Arrays;

import freecalc.execution.exception.ExecutionException;
import freecalc.execution.exception.OverflowException;
import freecalc.execution.exception.UnderflowException;
import freecalc.parameter.Function;
import freecalc.parameter.Parameter;

public class Percent extends Function{
	public static final String symbol = "%";
	
	public boolean commutative;
	public Percent(Parameter [] p, boolean b){
		super(p);
		this.commutative = b;
	}
	
	public Percent(Percent a){
		this.params = Arrays.copyOf(a.params, a.params.length);
		this.t = a.type();
		this.commutative = a.commutative;
	}
	
	public Percent(){
		super();
		this.commutative = true;
	}
	
	public Percent(boolean b){
		super();
		this.commutative = b;
	}
	@Override
	public String getSymbol(){
		return symbol;
	}
	public boolean getCommutative(){
		return this.commutative;
	}
	public void setCommutative(boolean b){
		this.commutative = b;
	}
	@Override
	public double evaluate() throws ExecutionException, OverflowException, UnderflowException{
		if (!this.commutative){
			if (this.params.length != 2){
				throw new ExecutionException("Percent object must have two execution arguments.");
			}
			double ret = 0;
			ret = (0.01*this.params[0].evaluate()) * this.params[1].evaluate();
			if (Function.overflow(ret)) throw new OverflowException("Percent value is too large to be represented properly: "+ret);
			else if (Function.underflow(ret)) throw new UnderflowException("Percent value is too small to be represented properly: "+ret);
			else return ret;
		}
		else {
			if (this.params.length != 2){
				throw new ExecutionException("Percent object must have two execution arguments.");
			}
			double ret = 0;
			ret = (0.01*this.params[1].evaluate()) * this.params[0].evaluate();
			if (Function.overflow(ret)) throw new OverflowException("Percent value is too large to be represented properly: "+ret);
			else if (Function.underflow(ret)) throw new UnderflowException("Percent value is too small to be represented properly: "+ret);
			else return ret;
		}
	}
	
	@Override
	public Parameter copy(){
		return new Percent(this);
	}
}
