package freecalc.parameter.function;

import java.util.Arrays;

import freecalc.execution.exception.ExecutionException;
import freecalc.execution.exception.OverflowException;
import freecalc.execution.exception.UnderflowException;
import freecalc.parameter.Function;
import freecalc.parameter.Parameter;

public class Logarithm extends Function {
	private boolean natural;
	public Logarithm(Parameter [] p, boolean nat){
		super(p);
		this.natural = nat;
	}
	
	public Logarithm(boolean nat){
		super();
		this.natural = nat;
	}
	
	public Logarithm(Logarithm a){
		this.params = Arrays.copyOf(a.params, a.params.length);
		this.t = a.type();
		this.natural = a.natural;
	}
	
	public Logarithm(){
		super();
		this.natural = true;
	}
	@Override
	public String getSymbol(){
		if (this.natural) return "ln";
		else return "log";
	}
	
	public void setNatural(boolean b){
		this.natural = b;
	}
	
	public boolean getNatural(){
		return this.natural;
	}
	
	@Override
	public double evaluate() throws ExecutionException, OverflowException, UnderflowException{
		if (this.params.length != 1){
			throw new ExecutionException("Logarithm object can only have one execution argument.");
		}
		double ret;
		if ((ret = this.params[0].evaluate()) <= 0.0){
			throw new ExecutionException("Logarithm cannot evaluate anything less than or equal to zero.");
		}
		try{
			if (this.natural){
				ret = Math.log(this.params[0].evaluate());
			}
			else ret = Math.log10(this.params[0].evaluate());
			
		} catch (Exception e){
			e.printStackTrace();
		}
		if (Function.overflow(ret)) throw new OverflowException("Logarithm value is too large to be represented properly: "+ret);
		else if (Function.underflow(ret)) throw new UnderflowException("Logarithm value is too small to be represented properly: "+ret);
		else return ret;
	}
	
	@Override
	public Parameter copy(){
		return new Logarithm(this);
	}
}
