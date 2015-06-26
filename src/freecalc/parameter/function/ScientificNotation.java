package freecalc.parameter.function;

import java.util.Arrays;

import freecalc.execution.exception.ExecutionException;
import freecalc.execution.exception.OverflowException;
import freecalc.execution.exception.UnderflowException;
import freecalc.parameter.Parameter;
import freecalc.parameter.Function;

public class ScientificNotation extends Function{

	public static final String symbol = "scinot";
	
	public boolean commutative;
	public ScientificNotation(Parameter [] p, boolean b){
		super(p);
		this.commutative = b;
	}
	
	public ScientificNotation(ScientificNotation a){
		this.params = Arrays.copyOf(a.params, a.params.length);
		this.t = a.type();
		this.commutative = a.commutative;
	}
	
	public ScientificNotation(){
		super();
		this.commutative = true;
	}
	
	public ScientificNotation(boolean b){
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
		if (this.params.length != 2){
			throw new ExecutionException("ScientificNotation object must have two and only two arguments.");
		}
		if (!this.commutative){
			double ret = 0;
			double d = this.params[1].evaluate();
			if (!Function.isInteger(d)) throw new ExecutionException("ScientificNotation object must have an integer as its second argument.");
			ret = this.params[0].evaluate() * (Math.pow(10, d));
			if (Function.overflow(ret)) throw new OverflowException("ScientificNotation value is too large to be represented properly: "+ret);
			else if (Function.underflow(ret)) throw new UnderflowException("ScientificNotation value is too small to be represented properly: "+ret);
			else return ret;
		}
		else {
			double ret = 0;
			double d = this.params[0].evaluate();
			if (!Function.isInteger(d)) throw new ExecutionException("ScientificNotation object must have an integer as its second argument.");
			ret = this.params[1].evaluate() * (Math.pow(10, d));
			if (Function.overflow(ret)) throw new OverflowException("ScientificNotation value is too large to be represented properly: "+ret);
			else if (Function.underflow(ret)) throw new UnderflowException("ScientificNotation value is too small to be represented properly: "+ret);
			else return ret;
		}
	}
	
	@Override
	public Parameter copy(){
		return new ScientificNotation(this);
	}
}
