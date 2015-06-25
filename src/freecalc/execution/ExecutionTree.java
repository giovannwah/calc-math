package freecalc.execution;

import freecalc.execution.exception.ExecutionException;
import freecalc.execution.exception.OverflowException;
import freecalc.execution.exception.UnderflowException;
import freecalc.parameter.Scalar;
import freecalc.parameter.function.*;

import java.util.LinkedList;
import java.util.Queue;

public class ExecutionTree {
	
	private ExecutionTreeNode root;
	private LinkedList<LinkedList<ExecutionTreeNode>> level;
	private int depth;
	
	/**
	 * Creates a new ExecutionTree object based on the argument root node. The resulting object 
	 * can then be evaluated.
	 * @param r
	 */
	public ExecutionTree (ExecutionTreeNode r){
		setRoot(r);
	}
	
	public ExecutionTree (ExecutionTree ori){
		this.level = ori.level;
		this.depth = ori.depth;
		this.root = ori.root;
	}
	
	public ExecutionTree (){
		this.level = null;
		this.depth = 0;
		this.root = null;
	}
	
	/**
	 * Evaluates the equation represented by the ExecutionTree
	 * @return
	 * @throws UnderflowException 
	 * @throws OverflowException 
	 * @throws Exception
	 */
	public double evaluateTree() throws ExecutionException, OverflowException, UnderflowException{
		if (this.level != null){
			for (int i = this.level.size()-1; i >= 0; i--){
				for (ExecutionTreeNode n : this.level.get(i)){
					n.evaluateNode();
				}
			}
			return this.root.getStoredValue();
		}
		else {
			throw new ExecutionException("ERROR in evaluateTree: tree level variable is null.");
		}
	}
	
	public ExecutionTreeNode getRoot(){
		return this.root;
	}

	public void setRoot(ExecutionTreeNode r){
		this.root = r;
		if (this.root != null){
			this.level = new LinkedList<LinkedList<ExecutionTreeNode>>();
			int index = -1;
			Queue<ExecutionTreeNode> q = new LinkedList<ExecutionTreeNode>();
			
			q.add(this.root);
			while (!q.isEmpty()){
				if (q.peek().getDepth() == this.level.size()){
					this.level.add(new LinkedList<ExecutionTreeNode>());
					index++;
				}
				ExecutionTreeNode temp = q.remove();
				this.level.get(index).add(temp);
				if (temp.getChildren().size() > 0){
					for (ExecutionTreeNode n : temp.getChildren()){
						if (!q.add(n)) System.err.println("Queue is full!");
					}
				}
			}
			this.depth = level.size();
		}
		else {
			this.depth = 0;
			this.level = null;
		}
	}
	
	@Override
	public String toString(){
		String ret = getEquationString(this.root);
		try{
			double ans = this.evaluateTree();
			ret += " = " + ans;
		}
		catch (Exception ex){
			ex.printStackTrace();
		}
		return ret;
	}
	
	public String getEquationString(ExecutionTreeNode e){
		String ret = new String();
		if (e.getValue().type() == 0){ //Scalar
			ret += e.getValue().getSymbol();
		}
		else if (e.getValue().type() == 1){ //Variable
			ret += e.getValue().getSymbol();
		}
		else if (e.getValue().type() == 2){ //Function
			ret += "\""+e.getValue().getSymbol()+"\"( ";
			if (e.getChildren().size() > 0){
				for (int i = 0; i < e.getChildren().size(); i++){
					ret += getEquationString(e.getChildren().get(i));
					if (i != e.getChildren().size()-1) ret += ", ";
				}
			}
			ret += " )";
			
		}
		return ret;
	} 
	
	public static void main (String [] arg){
		Scalar s1 = new Scalar(Math.PI/2);

		Trigonometric s = new Trigonometric(true, 2);
		
		ExecutionTreeNode root = new ExecutionTreeNode(s);
		ExecutionTreeNode n1 = new ExecutionTreeNode(s1);
		root.add(n1);
		
		ExecutionTree tree = new ExecutionTree(root);
		try{
			System.out.println(tree.toString());
		}
		catch(Exception e){e.printStackTrace();}
	}
}
