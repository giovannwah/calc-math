package freecalc.parameter.function;

import java.util.Arrays;

import freecalc.execution.exception.ExecutionException;
import freecalc.execution.exception.OverflowException;
import freecalc.execution.exception.UnderflowException;
import freecalc.parameter.Function;
import freecalc.parameter.Parameter;

public class Subtraction extends Function{
	public static final String symbol = "-";
	
	public boolean commutative;
	public Subtraction(Parameter [] p, boolean b){
		super(p);
		this.commutative = b;
	}
	public Subtraction(Subtraction a){
		this.params = Arrays.copyOf(a.params, a.params.length);
		this.t = a.type();
		this.commutative = a.commutative;
	}
	public Subtraction(){
		super();
		this.commutative = true;
	}
	public Subtraction(boolean b){
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
			double ret = 0;
			if (this.params.length != 2){
				throw new ExecutionException("Subtraction takes two and only two arguments.");
			}
			ret = this.params[0].evaluate() - this.params[1].evaluate();
			if (Function.overflow(ret)) throw new OverflowException("Subtraction value is too large to be represented properly: "+ret);
			else if (Function.underflow(ret)) throw new UnderflowException("Subtraction value is too small to be represented properly: "+ret);
			else return ret;
		}
		else {
			double ret = 0;
			if (this.params.length != 2){
				throw new ExecutionException("Subtraction takes two and only two arguments.");
			}
			ret = this.params[1].evaluate() - this.params[0].evaluate();
			if (Function.overflow(ret)) throw new OverflowException("Subtraction value is too large to be represented properly: "+ret);
			else if (Function.underflow(ret)) throw new UnderflowException("Subtraction value is too small to be represented properly: "+ret);
			else return ret;
		}
	}
	
	@Override
	public Parameter copy(){
		return new Subtraction(this);
	}
}
