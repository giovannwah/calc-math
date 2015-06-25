package freecalc.parameter.function;

import java.util.Arrays;

import freecalc.execution.exception.ExecutionException;
import freecalc.execution.exception.OverflowException;
import freecalc.execution.exception.UnderflowException;
import freecalc.parameter.Function;
import freecalc.parameter.Parameter;

public class Division extends Function{
	public static final String symbol = "/";
	
	public boolean commutative;
	public Division(Parameter [] p, boolean b){
		super(p);
		this.commutative = b;
	}
	
	public Division(Division a){
		this.params = Arrays.copyOf(a.params, a.params.length);
		this.t = a.type();
		this.commutative = a.commutative;
	}
	
	public Division(){
		super();
		this.commutative = true;
	}
	
	public Division(boolean b){
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
			double ret = this.params[0].evaluate();
			if (ret == 0){
				return 0;
			}
			else if (this.containsZero()){
				throw new ExecutionException("You can't divide by zero, silly.");
			}
			else if (this.params.length != 2){
				throw new ExecutionException("Division takes two and only two arguments.");
			}
			ret /= this.params[1].evaluate();
			
			if (Function.overflow(ret)) throw new OverflowException("Division value is too large to be represented properly: "+ret);
			else if (Function.underflow(ret)) throw new UnderflowException("Division value is too small to be represented properly: "+ret);
			else return ret;
		}
		else {
			double ret = this.params[1].evaluate();
			if (ret == 0){
				return 0;
			}
			else if (this.containsZero()){
				throw new ExecutionException("You can't divide by zero, silly.");
			}
			else if (this.params.length != 2){
				throw new ExecutionException("Division takes two and only two arguments.");
			}
			ret /= this.params[0].evaluate();
			
			if (Function.overflow(ret)) throw new OverflowException("Division value is to large to be represented properly: "+ret);
			else if (Function.underflow(ret)) throw new UnderflowException("Division value is too small to be represented properly: "+ret);
			else return ret;
		}
	}
	
	@Override
	public Parameter copy(){
		return new Division(this);
	}
}
