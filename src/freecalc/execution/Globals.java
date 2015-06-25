package freecalc.execution;

import java.util.LinkedList;

import freecalc.execution.exception.ExecutionException;
import freecalc.parameter.Variable;

public class Globals {
	
	private static LinkedList<Variable> variables = new LinkedList<Variable>();
	private static boolean radians = true;
	private static String [] names = new String [] {
		"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"
	};
	
	public static void setRadians(boolean r){
		radians = r;
	}
	
	public static boolean getRadians(){
		return radians;
	}
	
	public static void setVariables(LinkedList<Variable> list){
		variables = list;
	}
	
	public static LinkedList<Variable> getVariables(){
		return variables;
	}
	
	/**
	 * Creates 26 variables, each named after an uppercase letter of the alphabet.
	 */
	public static void initiateVariables(){
		for (int i = 0; i < names.length; i++){
			Variable v = new Variable(names[i]);
			variables.add(v);
		}
	}
	
	/**
	 * Sets the value of the Variable with the given name.
	 * @param String name
	 * @param double value
	 * @throws Exception
	 */
	public static void setVarValue(String name, double value) throws Exception{
		int index = -1;
		for (int i = 0; i < names.length; i++){
			if (name.equals(names[i])){
				index = i;
				break;
			}
		}
		if (index == -1) throw new Exception ("Invalid Variable name.");
		else {
			Variable v = variables.get(index);
			v.setAssignedValue(value);
			v.setHasValue(true);
		}
	}
	
	/**
	 * Removes the value of the Variable with the given name.
	 * @param name
	 * @throws Exception
	 */
	public static void removeVarValue(String name) throws Exception{
		int index = -1;
		for (int i = 0; i < names.length; i++){
			if (name.equals(names[i])){
				index = i;
				break;
			}
		}
		if (index == -1) throw new Exception ("Invalid Variable name.");
		else {
			variables.get(index).setHasValue(false);
		}
	}
	
	/**
	 * Returns the variable with the given name, otherwise throws ExecutionException.
	 * @param name
	 * @return
	 * @throws ExecutionException
	 */
	public static Variable getVar(String name) throws ExecutionException{
		for (int i = 0; i < names.length; i++){
			if (name.equals(names[i])) return variables.get(i);
		}
		throw new ExecutionException("Variable '"+name+"' does not exist.");
	}
}
