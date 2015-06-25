package freecalc.parameter.function;

import java.util.Arrays;

import freecalc.execution.exception.ExecutionException;
import freecalc.execution.exception.OverflowException;
import freecalc.execution.exception.UnderflowException;
import freecalc.parameter.Function;
import freecalc.parameter.Parameter;

public class Modulus extends Function{
	public static final String symbol = "mod";
	
	public boolean commutative;
	public Modulus(Parameter [] p, boolean b){
		super(p);
		this.commutative = b;
	}
	
	public Modulus(Modulus a){
		this.params = Arrays.copyOf(a.params, a.params.length);
		this.t = a.type();
		this.commutative = a.commutative;
	}
	
	public Modulus(){
		super();
		this.commutative = true;
	}
	
	public Modulus(boolean b){
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
			throw new ExecutionException("Modulus object must have two and only two arguments.");
		}
		if (!this.commutative){	
			if (this.params[1].evaluate() == 0){
				throw new ExecutionException(this.params[0].evaluate()+" mod 0 is undefined.");
			}
			double ret = 0;
			try{
				ret = this.params[0].evaluate() % this.params[1].evaluate();
			} catch (Exception e){
				e.printStackTrace();
			}
			if (Function.overflow(ret)) throw new OverflowException("Modulus value is too large to be represented properly: "+ret);
			else if (Function.underflow(ret)) throw new UnderflowException("Modulus value is too small to be represented properly: "+ret);
			else return ret;
		}
		else {
			if (this.params[0].evaluate() == 0){
				throw new ExecutionException(this.params[1].evaluate()+" mod 0 is undefined.");
			}
			double ret = 0;
			try{
				ret = this.params[1].evaluate() % this.params[0].evaluate();
			} catch (Exception e){
				e.printStackTrace();
			}
			if (Function.overflow(ret)) throw new OverflowException("Modulus value is too large to be represented properly: "+ret);
			else if (Function.underflow(ret)) throw new UnderflowException("Modulus value is too small to be represented properly: "+ret);
			else return ret;
		}
	}
	
	@Override
	public Parameter copy(){
		return new Modulus(this);
	}
}
