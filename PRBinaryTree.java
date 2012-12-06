
import java.util.*;

public class PRBinaryTree<T>  {

  protected static class BinaryNode<U> {
		protected U data;
		protected BinaryNode<U> left;
		protected BinaryNode<U> right;


		//constructors
		public BinaryNode (U data) {
			this.data = data;
			this.left = null;
			this.right = null;
		}

		public String toString() {
			return this.data.toString();
		}
	}

	BinaryNode<T> root;  //reference to the node root of the tree

	//constructor empty binary tree
	public PRBinaryTree(){
		root = null;
	}

	//constructor: creates a new tree with a node with data in the root and left and right empty
	public PRBinaryTree(T data) {
		BinaryNode<T> n = new  BinaryNode<T>(data);
		this.root = n;
	}

	//constructor: given a node, constructs a binary tree whose root is that node
	private PRBinaryTree(BinaryNode<T> n){
		this.root = n;
	}

	//constructor: given data, and two binary trees constructs a new binary tree
	public PRBinaryTree(T data, PRBinaryTree<T> leftS, PRBinaryTree<T> rightS) {
		BinaryNode<T> root = new BinaryNode<T>(data);
		if (leftS != null) {
			root.left = leftS.root;
		}
		if (rightS != null) {
			root.right = rightS.root;
		}
		this.root = root;
	}

	public boolean isEmpty() {
		return (this.root == null);
	}

	public boolean isLeaf() {
		if (root != null) {
			if (root.left == null && root.right == null)
				return true;
			else
				return false;
		}
		else return false;
	}

	public T getData () {
		if (root != null)
			return root.data;
		else
			return null;
	}

	public PRBinaryTree<T> getLeftSubTree(){
		if (root != null) {
			if (root.left != null) {
				PRBinaryTree<T> leftTree = new PRBinaryTree<T>(root.left);
				return leftTree;
			}
			else
				return new PRBinaryTree<T>();  //empty tree
		}
		else
				return null;
	}

	public PRBinaryTree<T> getRightSubTree(){
		if (root != null) {
			if (root.right != null) {
				PRBinaryTree<T> rightTree = new PRBinaryTree<T>(root.right);
				return rightTree;
			}
			else
				return new PRBinaryTree<T>();  //empty tree
		}
		else
				return null;
	}

	public void inOrder(List<T> listData) {
		if (!isEmpty()){
			inOrder(root,listData);
		}
	}

	private void inOrder(BinaryNode<T> r, List<T> listData) {
		if (r.left!=null) {
			inOrder(r.left,listData);
		}
		listData.add(r.data);
		if (r.right!=null) {
			inOrder(r.right,listData);
		}
	}

	//displays a tree in the console
	public void displayTree() {
		Stack<BinaryNode<T>> globalStack = new Stack<BinaryNode<T>>();
	    globalStack.push(root);
	    int nBlanks = 32;
	    boolean isRowEmpty = false;
	    System.out.println(
	    "......................................................");
	    while(isRowEmpty==false)
	       {
	       Stack<BinaryNode<T>> localStack = new Stack<BinaryNode<T>>();
	       isRowEmpty = true;

	       for(int j=0; j<nBlanks; j++)
	          System.out.print(' ');

	       while(globalStack.isEmpty()==false)
	          {
	          BinaryNode<T> temp = (BinaryNode<T>)globalStack.pop();
	          if(temp != null)
	             {
	             System.out.print(temp.data);
	             localStack.push(temp.left);
	             localStack.push(temp.right);

	             if(temp.left != null || temp.right != null)
	                isRowEmpty = false;
	             }
	          else
	             {
	             System.out.print("--");
	             localStack.push(null);
	             localStack.push(null);
	             }
	          for(int j=0; j<nBlanks*2-2; j++)
	             System.out.print(' ');
	          }  // end while globalStack not empty
	       System.out.println();
	       nBlanks /= 2;
	       while(localStack.isEmpty()==false)
	          globalStack.push( localStack.pop() );
	       }  // end while isRowEmpty is false
	    System.out.println(
	    "......................................................");

	}

	//METHODS TO BE IMPLEMENTED
	//It builds a binary tree given an string s with an arithmetic expression in postfix notation
	public PRBinaryTree<Character> createsBinaryExprTree(String chain) throws IllegalArgumentException{

		PRBinaryTree<Character> arbin = new PRBinaryTree<Character>();
		Stack<PRBinaryTree<Character>> pila = new Stack<PRBinaryTree<Character>>();
		PRBinaryTree<Character> arbin_left = null;
		PRBinaryTree<Character> arbin_right = null;

		for(int i=0;i<chain.length();i++){

			Character car = chain.charAt(i);
			boolean bool = Character.isDigit(car);

			if(bool){

				arbin = new PRBinaryTree<Character>(car);
				pila.push(arbin);
			}else{
				if(pila.isEmpty())
					throw new IllegalArgumentException();

				arbin_right = pila.pop();

				if(pila.isEmpty())
					throw new IllegalArgumentException();


				arbin_left = pila.pop();




				arbin = new PRBinaryTree<Character>(car,arbin_left,arbin_right);
				pila.push(arbin);
			}
		}


		if(pila.isEmpty())
			throw new IllegalArgumentException();

		arbin = pila.pop();

		if(!pila.isEmpty())
			throw new IllegalArgumentException();

		return arbin;

	}

	//Returns an string with the elements of the binary tree in postorder
	public String printPostOrder() {

		String chain = "";

        if (root.left != null) chain += getLeftSubTree().printPostOrder();
        if (root.right != null) chain += getRightSubTree().printPostOrder();
        chain += root.data;

        return chain;
	}

	//Returns true if it is a extended binary tree, otherwise it returns false
	public boolean isExtendedTree() {
		if(root == null)
			return true;

		if(root.left != null && root.right == null || root.left == null && root.right != null){
			return false;
		}else{
			getRightSubTree().isExtendedTree();
			getLeftSubTree().isExtendedTree();

		}
		return true;
	}

	//Returns a string with the expression in the tree in infix notation with parenthesis
	public String printInOrder() {

		String chain = "";


		if(root.left != null)chain += "(" + getLeftSubTree().printInOrder();
		chain += root.data;
		if(root.right != null)chain += getRightSubTree().printInOrder() + ")";

		return chain;
	}


	//Returns a string containing the expression in the tree using functional notation
	public String functionalNotation() {
		String chain = "";

		if(root.data.equals('+')){
			chain += "Sum(";
		}else{
		if(root.data.equals('-')){
			chain += "Resta(";
		}else{
		if(root.data.equals('*')){
			chain += "Mult(";
		}else{
		if(root.data.equals('/')){
			chain += "Div(";
		}else{
		if(root.data.equals('^')){
			chain += "Exp(";
		}}}}}

		if(isLeaf())
		chain += root.data;
		if(root.left != null)chain += getLeftSubTree().functionalNotation() + ",";
		if(root.right != null)chain += getRightSubTree().functionalNotation() + ")";

		return chain;
	}

	//Returns the value resulting of the evaluation of the arithmetic expression in the tree
	public float evaluate(){

		float value = 0;

		if(isLeaf()){
			String strAmount=root.data +"";
			float amount=Float.parseFloat(strAmount);

			return amount;

		}else{if(root.data.equals('+')){
			return getLeftSubTree().evaluate() + getRightSubTree().evaluate();
		}else{
		if(root.data.equals('-')){
			return getLeftSubTree().evaluate() - getRightSubTree().evaluate();
		}else{
		if(root.data.equals('*')){
			return getLeftSubTree().evaluate() * getRightSubTree().evaluate();
		}else{
		if(root.data.equals('/')){
			return getLeftSubTree().evaluate() / getRightSubTree().evaluate();
		}else{
		if(root.data.equals('^')){
			return (float) Math.pow(getLeftSubTree().evaluate(),getRightSubTree().evaluate());
		}}}}}
		}
		return value;

	}
	public int height(PRBinaryTree<Character> b) {

		if (b.root == null)
			return 0;

		int hi = height(b.getLeftSubTree());
		int hd = height(b.getRightSubTree());

		return 1 + Math.max(hi,hd);
	}
	//It returns the elements of the tree in an array traversing the tree from level 1 to height.
	public void toArray(T[] v) {

		int i=1;
		Queue<BinaryNode<T>> q = new LinkedList<BinaryNode<T>>();

		q.add(root);


		while(!q.isEmpty() && i<v.length){

			BinaryNode<T> actual = q.peek();
			q.remove();
			if(actual!=null){
				v[i++] = actual.data;
				if (actual.left != null)
					q.add(actual.left);
				else
					q.add(null);
				if (actual.right != null)
					q.add(actual.right);
				else
					q.add(null);
			}
			else{
				q.add(null);
				q.add(null);
				i++;
			}
		}
	}

}
