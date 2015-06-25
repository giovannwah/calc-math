package freecalc.parameter;

import freecalc.execution.exception.OverflowException;
import freecalc.execution.exception.UnderflowException;


public class Scalar implements Parameter{
	
	public String symbol;
	private double value;
	private int t;
	
	public Scalar(double v){
		this.value = v;
		this.symbol = ""+this.value;
		this.t = 0;
	}
	public Scalar(Scalar s){
		this.value = s.value;
		this.symbol = new String(s.symbol);
		this.t = s.t;
	}
	public Scalar(){
		this.t = 0;
		this.symbol = "0";
		this.value = 0;
	}
	
	public void setValue(double v){
		this.value = v;
		this.setSymbol(v);
	}
	
	public double getValue(){
		return this.value;
	}
	
	public int type() {
		return t;
	}
	
	public void setSymbol(String s){
		this.symbol = s;
	}
	public void setSymbol(double d){
		this.symbol = ""+d;
	}
	
	@Override
	public String getSymbol(){
		return this.symbol;
	}
	public double evaluate() throws OverflowException, UnderflowException {
		if (Function.overflow(this.value)) throw new OverflowException("Scalar value is too large to be represented properly: "+this.value);
		else if (Function.underflow(this.value)) throw new UnderflowException("Scalar value is too small to be represented properly: "+this.value);
		else return this.value;
	}
	@Override
	public Parameter copy() {
		return new Scalar(this);
	}
}
