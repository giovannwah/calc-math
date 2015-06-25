package freecalc.execution;

import java.util.LinkedList;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Stack;

import freecalc.execution.exception.FormatException;
import freecalc.parameter.Function;

public class ExpressionParser {
	public static final String[] BINARY_OPERATOR = new String [] {
		"+",
		"-",
		"*",
		"/", 
		"mod",
		"%",
		"^",
		"nrt",
		"scinot"
	};
	
	public static final String[] UNARY_OPERATOR = new String [] {
		"sin",
		"cos",
		"tan",
		"arcsin",
		"arccos",
		"arctan",
		"rec",
		"cbrt",
		"sqrt",
		"neg",
		"log",
		"ln",
	};
	
	public static ArrayList<String> tokenize(String input){
		StringTokenizer tZer = new StringTokenizer(input);
		tZer.countTokens();
		ArrayList<String> ret = new ArrayList<String>();
		while (tZer.hasMoreTokens()){
			ret.add(tZer.nextToken());
		}
		return ret;
	}
	
	public static String tokensToString(ArrayList<String> tokens){
		String ret = "";
		for (int i = 0; i < tokens.size(); i++){
			ret += tokens.get(i);
			if (i != tokens.size()-1) ret += ", ";
		}
		return ret;
	}
	
	public static String tokensToString(LinkedList<String> tokens){
		String ret = "";
		for (int i = 0; i < tokens.size(); i++){
			ret += tokens.get(i);
			if (i != tokens.size()-1) ret += ", ";
		}
		return ret;
	}
	/**
	 * Determines the precedence of the String operator. The higher the integer, the higher the precedence.
	 * @param p
	 * @return integer precedence
	 */
	public static int getPrecedence(String p){
		
		if (p.equals("+") || p.equals("-")){
			return 0;
		}
		else if (p.equals("*") || p.equals("/") || p.equals("mod") || p.equals("%")){
			return 1;
		}
		else if ( p.equals("scinot") || p.equals("^") || p.equals("nrt") || isUnaryOperator(p)){
			return 2;
		}
		else if (p.equals("(")){
			return 3;
		}
		//some sort of error.
		else return -1;
	}
	
	public static ArrayList<String> infixToPrefix(ArrayList<String> tokens){
		ArrayList<String> queue = new ArrayList<String>();
		Stack<String> stack = new Stack<String>();
		for (int i = 0; i < tokens.size(); i++){
			if (isOperator(tokens.get(i))){ // is operator
				while(!stack.isEmpty() && isOperator(stack.peek()) && getPrecedence(stack.peek()) >= getPrecedence(tokens.get(i))){
					queue.add(stack.pop());
				}
				stack.push(tokens.get(i));
			}
			else if (tokens.get(i).equals("(")){ // is left paran
				stack.push(tokens.get(i));
			}
			else if (tokens.get(i).equals(")")){ // is right paran
				while (!stack.peek().equals("(")){
					queue.add(stack.pop());
				}
				stack.pop();
			}
			else if (tokens.get(i).equals("pi") || tokens.get(i).equals("e")){
				queue.add(tokens.get(i));
			}
			else if (tokens.get(i).length() == 1 && Character.isUpperCase(tokens.get(i).charAt(0))){ //is a variable
				queue.add(tokens.get(i));
			}
			else{ // is operand (number)
				queue.add(tokens.get(i));
			}
		}
		while (!stack.isEmpty()){
			queue.add(stack.pop());
		}
		return queue;
	}
	
	public static boolean isBinaryOperator(String s){
		for (String st : BINARY_OPERATOR){
			if (st.equals(s)){
				return true;
			}
		}
		return false;
	}
	
	public static boolean isUnaryOperator(String s){
		for (String st : UNARY_OPERATOR){
			if (st.equals(s)){
				return true;
			}
		}
		return false;
	}
	
	public static boolean isOperator(String s){
		return isBinaryOperator(s) || isUnaryOperator(s);
	}
	
	public static boolean isAlphaTerm(String s){
		return (s.equals("e") || s.equals("pi") || isUnaryOperator(s) );
	}
	/**
	 * Validates that the input string is prepared for evaluation
	 * @param input
	 * @throws FormatException
	 */
	public static ArrayList<String> preprocess (ArrayList<String> input) throws FormatException{
		// check parenthesis
		int pcount = 0;
		boolean b = false;
		for (int i = 0; i < input.size(); i++){
			if (!b && input.get(i).equals(")")) throw new FormatException("Ill-formatted parenthesis in input String.");
			if (input.get(i).equals("(")){
				b = true;
				if (i < input.size()-1 && input.get(i+1).equals(")")) throw new FormatException("Cannot process empty parenthesis.");
				else pcount += 1;
			}
			else if (input.get(i).equals(")")) pcount -= 1;
		}
		if (pcount != 0) throw new FormatException("Mismatched parenthesis in input String.");
		
		// add multiplication operator wherever needed.
		ArrayList<String> ret = new ArrayList<String>(input);
		for (int i = ret.size(); i >= 0; i--){ //scan string in reverse to add multiplications
			if (i < ret.size()-1){
				if ( 	( ret.get(i).equals(")") && 
							(ret.get(i+1).equals("(") || Character.isDigit(ret.get(i+1).charAt(0)) || Character.isUpperCase(ret.get(i+1).charAt(0)) || isAlphaTerm(ret.get(i+1))))
					||	( Character.isUpperCase(ret.get(i).charAt(0)) && 
							(ret.get(i+1).equals("(") || Character.isDigit(ret.get(i+1).charAt(0)) || Character.isUpperCase(ret.get(i+1).charAt(0)) || isAlphaTerm(ret.get(i+1))))
					||	( Character.isDigit(ret.get(i).charAt(0)) && 
							(ret.get(i+1).equals("(") || Character.isUpperCase(ret.get(i+1).charAt(0)) || isAlphaTerm(ret.get(i+1))))
					||	( (ret.get(i).equals("e") || ret.get(i).equals("pi")) && 
							(ret.get(i+1).equals("(") || Character.isDigit(ret.get(i+1).charAt(0)) || Character.isUpperCase(ret.get(i+1).charAt(0)) || isAlphaTerm(ret.get(i+1)) ))
					 ){ 
					
					ArrayList<String> mul = new ArrayList<String>();
					mul.add("*");
					ret.addAll(i+1, mul);
				}
			}
		}
		return ret;
	}
	
	public static void main (String [] args){
		//log(100)3E7+7
		String t = "3 mod 4";
		ArrayList<String> test = tokenize(t);
		
		try{
			test = preprocess (test);
			ArrayList<String> t2 = infixToPrefix(test);
			System.out.println(ExecutionTreeBuilder.buildTree(t2).toString());
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
}