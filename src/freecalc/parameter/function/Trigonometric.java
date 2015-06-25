package freecalc.parameter.function;

import java.util.Arrays;

import freecalc.execution.exception.ExecutionException;
import freecalc.execution.exception.OverflowException;
import freecalc.execution.exception.UnderflowException;
import freecalc.parameter.Function;
import freecalc.parameter.Parameter;
import freecalc.parameter.Scalar;

public class Trigonometric extends Function{
	private boolean radians;
	private int function;
	
	public Trigonometric(Parameter [] p, boolean rad, int fun){
		super(p);
		this.radians = rad;
		setFunction(fun);
	}
	
	public Trigonometric(boolean rad, int fun){
		super();
		this.radians = rad;
		setFunction(fun);
	}
	
	public Trigonometric(Trigonometric a){
		this.params = Arrays.copyOf(a.params, a.params.length);
		this.t = a.type();
		this.radians = a.radians;
		this.function = a.function;
	}
	
	public Trigonometric(){
		super();
		this.radians = true;
		this.function = 0;
	}
	
	@Override
	public String getSymbol(){
		switch (this.function){
		case 0: return "sin"; 
		case 1: return "cos";
		case 2: return "tan";
		case 3: return "arcsin";
		case 4: return "arccos";
		default: return "arctan";
		}
	}
	public void setFunction(int i){
		if (i < 6 && i >= 0){
			this.function = i;
		}
		else System.err.println("ERROR setting Trigonometric Function: argument must be in the range of (0, 5): "+i);
	}
	
	public int getFunction(){
		return this.function;
	}
	
	public void setRadians(boolean b){
		this.radians = b;
	}
	
	public boolean getRadians(){
		return this.radians;
	}
	@Override
	public double evaluate() throws ExecutionException, OverflowException, UnderflowException{
		if (this.params.length != 1){
			throw new ExecutionException("Trigonometric object can only have one execution argument.");
		}
		double ret = 0;
			switch (this.function){
			case 0:{ // sin
				if (this.radians){
					ret = Math.sin(this.params[0].evaluate());
				}
				else{
					ret = Math.sin(Math.toRadians(this.params[0].evaluate()));
				}
				break;
			}
			case 1:{ // cos
				if (this.radians){
					ret = Math.cos(this.params[0].evaluate());
				}
				else{
					ret = Math.cos(Math.toRadians(this.params[0].evaluate()));
				}
				break;
			}
			case 2:{ // tan
				double d = this.params[0].evaluate();
				if (!inTanDomain(d)){
					throw new ExecutionException("Argument is out of tan function domain.");
				}
				if (this.radians){
					ret = Math.tan(this.params[0].evaluate());
				}
				else{
					ret = Math.tan(Math.toRadians(this.params[0].evaluate()));
				}
				break;
			}
			case 3:{ // arcsin
				double d = this.params[0].evaluate();
				if (d < -1 || d > 1) throw new ExecutionException("Argument is out of arcsin function domain.");
				if (this.radians){
					ret = Math.asin(this.params[0].evaluate());
				}
				else{
					ret = Math.toDegrees(Math.asin(this.params[0].evaluate()));
				}
				break;
			}
			case 4:{ // arccos
				double d = this.params[0].evaluate();
				if (d < -1 || d > 1) throw new ExecutionException("Argument is out of arccos function domain.");
				if (this.radians){
					ret = Math.acos(this.params[0].evaluate());
				}
				else{
					ret = Math.toDegrees(Math.acos(this.params[0].evaluate()));
				}
				break;
			}
			default:{ // arctan
				if (this.radians){
					ret = Math.atan(this.params[0].evaluate());
				}
				else{
					ret = Math.toDegrees(Math.atan(this.params[0].evaluate()));
				}
				break;
			}
			}
			if (Function.overflow(ret)) throw new OverflowException("Trigonometric value is too large to be represented properly: "+ret);
			else if (Function.underflow(ret)) throw new UnderflowException("Trigonometric value is too small to be represented properly: "+ret);
			else return ret;
	}
	
	/**
	 * Returns true if the number is within the domain of tan function
	 * @param x
	 * @return
	 */
	public static boolean inTanDomain(double x){
		double d = (x-(Math.PI/2))/Math.PI;
		if ( isInteger(d) ) return false;
		else return true;
	}
	
	@Override
	public Parameter copy(){
		return new Trigonometric(this);
	}
	
	public static void main (String [] main){
			System.out.println(Math.cos(104949));
	}
}
