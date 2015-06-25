package freecalc.execution;

import java.util.LinkedList;

import freecalc.execution.exception.ExecutionException;
import freecalc.execution.exception.OverflowException;
import freecalc.execution.exception.UnderflowException;
import freecalc.parameter.Function;
import freecalc.parameter.Parameter;
import freecalc.parameter.Scalar;
import freecalc.parameter.function.*;

public class ExecutionTreeNode {
	
	private ExecutionTreeNode parent;
	private LinkedList<ExecutionTreeNode> children;
	private int depth;
	private Parameter value;
	private double storedValue;
	
	public ExecutionTreeNode(){
		this.parent = null;
		this.children = new LinkedList<ExecutionTreeNode>();
		this.depth = 0;
		this.value = null;
		this.storedValue = 0;
	}
	
	public ExecutionTreeNode(ExecutionTreeNode par, Parameter val){
		this.parent = par;
		if (this.parent != null) this.depth = this.parent.depth+1;
		else this.depth = 0;
		this.value = val;
		this.children = new LinkedList<ExecutionTreeNode>();
		this.storedValue = 0;
	}
	
	public ExecutionTreeNode(Parameter val){
		this.parent = null;
		if (this.parent != null) this.depth = this.parent.depth+1;
		else this.depth = 0;
		this.value = val;
		this.children = new LinkedList<ExecutionTreeNode>();
		this.storedValue = 0;
	}
	
	public ExecutionTreeNode(ExecutionTreeNode ori){
		this.children = new LinkedList<ExecutionTreeNode>();
		for (ExecutionTreeNode n : ori.children){
			this.children.add(n);
		}
		this.parent = ori.parent;
		this.value = ori.value;
		this.depth = ori.depth;
		this.storedValue = ori.storedValue;
	}
	
	/**
	 * Adds node n to the children of this node, and retroactively adjusts the
	 * depths of all child nodes.
	 * @param n
	 */
	public void add(ExecutionTreeNode n){
		if (!this.children.contains(n)) this.children.add(n);
		n.parent = this;
		n.depth = this.depth+1;
		for (ExecutionTreeNode t : n.children){
			n.add(t);
		}
	}
	
	public ExecutionTreeNode copy(){
		return new ExecutionTreeNode(this);
	}
	
	public boolean isLeaf(){
		if (this.children.size() == 0) return true;
		else return false;
	}
	
	public void setChildren(LinkedList<ExecutionTreeNode> n){
		if (n != null) this.children = n;
		else System.err.println("ERROR in setChildren: parameter can't be null.");
	}
	
	public LinkedList<ExecutionTreeNode> getChildren(){
		return this.children;
	}
	
	public void setParent(ExecutionTreeNode p){
		this.parent = p;
	}
	
	public ExecutionTreeNode getParent(){
		return this.parent;
	}
	
	public void setDepth(int d){
		if (d >= 0) this.depth = d;
		else System.err.println("ERROR in setDepth: depth must be 0 or greater.");
	}
	
	public int getDepth(){
		return this.depth;
	}
	
	public void setValue(Parameter p){
		this.value = p;
	}
	
	public Parameter getValue(){
		return this.value;
	}
	
	public void setStoredValue(double d){
		this.storedValue = d;
	}
	
	public double getStoredValue(){
		return this.storedValue;
	}
	
	@Override
	public String toString(){
		return this.value.getSymbol();
	}
	
	/**
	 * Get the value of the node. Store that value in the storedValue variable
	 * @throws UnderflowException 
	 * @throws OverflowException 
	 * @throws ExecutionException 
	 */
	public void evaluateNode() throws ExecutionException, OverflowException, UnderflowException{
		if (this.isLeaf()){
			this.storedValue = this.value.evaluate();
		}
		else {
			Parameter [] par = new Parameter [this.children.size()];
			for (int i = 0; i < par.length; i++){
				par[i] = new Scalar(this.children.get(i).storedValue);
			}
				
			((Function)this.value).setParams(par);
			this.storedValue = ((Function)this.value).evaluate();
		} 
	}
}
