package freecalc.parameter.function;

import java.util.Arrays;

import freecalc.execution.exception.ExecutionException;
import freecalc.execution.exception.OverflowException;
import freecalc.execution.exception.UnderflowException;
import freecalc.parameter.Function;
import freecalc.parameter.Parameter;

public class Power extends Function{
	public static final String symbol = "^";
	
	public boolean commutative;
	public Power(Parameter [] p, boolean b){
		super(p);
		this.commutative = b;
	}
	
	public Power(Power a){
		this.params = Arrays.copyOf(a.params, a.params.length);
		this.t = a.type();
		this.commutative = a.commutative;
	}

	public Power(){
		super();
		this.commutative = true;
	}
	
	public Power (boolean b){
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
				throw new ExecutionException("Power object can have two and only two execution arguments.");
			}
			double ret = 0;
			ret = Math.pow(this.params[0].evaluate(), this.params[1].evaluate());
			if (Function.overflow(ret)) throw new OverflowException("Power value is too large to be represented properly: "+ret);
			else if (Function.underflow(ret)) throw new UnderflowException("Power value is too small to be represented properly: "+ret);
			else return ret;
		}
		else {
			if (this.params.length != 2){
				throw new ExecutionException("Power object can have two and only two execution arguments.");
			}
			double ret = 0;
			ret = Math.pow(this.params[1].evaluate(), this.params[0].evaluate());
			if (Function.overflow(ret)) throw new OverflowException("Power value is too large to be represented properly: "+ret);
			else if (Function.underflow(ret)) throw new UnderflowException("Power value is too small to be represented properly: "+ret);
			else return ret;
		}
	}
	
	@Override
	public Parameter copy(){
		return new Power(this);
	}
}
