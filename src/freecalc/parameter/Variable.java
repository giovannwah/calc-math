package freecalc.parameter;

import freecalc.execution.exception.OverflowException;
import freecalc.execution.exception.UnderflowException;


public class Variable implements Parameter {
	private int t;
	private String symbol;
	private double assignedValue;
	private boolean hasValue;
	
	public Variable(String s){
		this.symbol = s;
		this.t = 1;
		this.assignedValue = 0;
		this.hasValue = false;
	}
	
	public Variable(){
		this.symbol = "";
		this.t = 1;
		this.assignedValue = 0;
		this.hasValue = false;
	}
	
	public Variable(Variable v){
		this.symbol = new String(v.symbol);
		this.t = 1;
		this.assignedValue = v.assignedValue;
		this.hasValue = v.hasValue;
	}
	
	public Variable(String s, double d){
		this.symbol = s;
		this.t = 1;
		this.assignedValue = d;
		this.hasValue = true;
	}
	
	public Variable(double d){
		this.symbol = "";
		this.t = 1;
		this.assignedValue = d;
		this.hasValue = true;
	}
	
	/**
	 * Returns whether or not this Variable has a value assigned to it
	 * @return
	 */
	public boolean getHasValue(){
		return this.hasValue;
	}
	
	/**
	 * Sets whether or not this Variable has a value assigned to it
	 * @param b
	 */
	public void setHasValue(boolean b){
		this.hasValue = b;
	}
	
	@Override
	public String getSymbol(){
		return this.symbol;
	}
	
	public void setSymbol(String s){
		this.symbol = new String(s);
	}

	public double getAssignedValue(){
		return this.assignedValue;
	}
	
	public void setAssignedValue(double d){
		this.assignedValue = d;
	}
	@Override
	public int type() {
		return t;
	}
	
	public double evaluate() throws OverflowException, UnderflowException{
		if (Function.overflow(this.assignedValue)) throw new OverflowException("Variable value is too large to be represented properly: "+this.symbol+" = "+this.assignedValue);
		else if (Function.underflow(this.assignedValue)) throw new UnderflowException("Variable value is too small to be represented properly: "+this.symbol+" = "+this.assignedValue);
		else return this.assignedValue;
	}

	@Override
	public Parameter copy() {
		return new Variable(this);
	}
}
