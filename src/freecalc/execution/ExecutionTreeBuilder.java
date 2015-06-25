package freecalc.execution;

import java.util.ArrayList;
import java.util.Stack;

import freecalc.execution.exception.ExecutionException;
import freecalc.parameter.Parameter;
import freecalc.parameter.Scalar;
import freecalc.parameter.Variable;
import freecalc.parameter.function.*;

/**
 * Capable of converting a String array of an expression in prefix (Polish) notation into
 * an ExecutionTree Object. 
 * @author giovadmin
 *
 */
public class ExecutionTreeBuilder {
	public static ExecutionTree buildTree(ArrayList<String> expression) throws ExecutionException{
		ExecutionTree tree = new ExecutionTree();
		Stack<ExecutionTreeNode> nStack = new Stack<ExecutionTreeNode>();
		
	//	for (int i = expression.size()-1; i >= 0; i--){
		for (int i = 0; i < expression.size(); i++){
			if (ExpressionParser.isBinaryOperator(expression.get(i))){
				Parameter value = null;
				if (expression.get(i).equals("+")){
					value = new Addition();
				}
				else if (expression.get(i).equals("-")){
					value = new Subtraction();
				}
				else if (expression.get(i).equals("*")){
					value = new Multiplication();
				}
				else if (expression.get(i).equals("/")){
					value = new Division();
				}
				else if (expression.get(i).equals("mod")){
					value = new Modulus();
				}
				else if (expression.get(i).equals("%")){
					value = new Percent();
				}
				else if (expression.get(i).equals("^")){
					value = new Power();
				}
				else if (expression.get(i).equals("nrt")){
					value = new Root(2, true);
				}
				else if (expression.get(i).equals("scinot")){
					value = new ScientificNotation();
				}
				ExecutionTreeNode n = new ExecutionTreeNode(value);
				n.add(nStack.pop());
				n.add(nStack.pop());
				nStack.push(n);
			}
			else if (ExpressionParser.isUnaryOperator(expression.get(i))){
				Parameter value = null;
				if (expression.get(i).equals("sin")){
					value = new Trigonometric(Globals.getRadians(), 0);
				}
				else if (expression.get(i).equals("cos")){
					value = new Trigonometric(Globals.getRadians(), 1);
				}
				else if (expression.get(i).equals("tan")){
					value = new Trigonometric(Globals.getRadians(), 2);
				}
				else if (expression.get(i).equals("arcsin")){
					value = new Trigonometric(Globals.getRadians(), 3);
				}
				else if (expression.get(i).equals("arccos")){
					value = new Trigonometric(Globals.getRadians(), 4);
				}
				else if (expression.get(i).equals("arctan")){
					value = new Trigonometric(Globals.getRadians(), 5);
				}
				else if (expression.get(i).equals("rec")){
					value = new Reciprocal();
				}
				else if (expression.get(i).equals("cbrt")){
					value = new Root(1, true);
				}
				else if (expression.get(i).equals("sqrt")){
					value = new Root(0, true);
				}
				else if (expression.get(i).equals("neg")){
					value = new Negative();
				}
				else if (expression.get(i).equals("log")){
					value = new Logarithm(false);
				}
				else if (expression.get(i).equals("ln")){
					value = new Logarithm(true);
				}
				ExecutionTreeNode n = new ExecutionTreeNode(value);
				n.add(nStack.pop());
				nStack.push(n);
			}
			else if (expression.get(i).equals("pi")){
				ExecutionTreeNode etn = new ExecutionTreeNode(new Scalar(Math.PI));
				nStack.push(etn);
			}
			else if (expression.get(i).equals("e")){
				ExecutionTreeNode etn = new ExecutionTreeNode(new Scalar(Math.E));
				nStack.push(etn);
			}
			else if (expression.get(i).length() == 1 && Character.isUpperCase(expression.get(i).charAt(0))){
				Variable v = Globals.getVar(expression.get(i));
				if(v.getHasValue()){
					ExecutionTreeNode etn = new ExecutionTreeNode(v);
					nStack.push(etn);
				}
				else throw new ExecutionException("Variable '"+v.getSymbol()+"' has no assigned value.");
			}
			else {
				ExecutionTreeNode etn = new ExecutionTreeNode(new Scalar(Double.parseDouble(expression.get(i))));
				nStack.push(etn);
			}
		}
		tree.setRoot(nStack.pop());
		return tree;
	}
}
