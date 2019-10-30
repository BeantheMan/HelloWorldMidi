import java.util.ArrayList; //Cameron Allen, 10/24/19, trains prediction suffix tree

public class Tree<T> { // git clone, ls, cd, git add --all, git commit -m "message", git push, git checkout .            git-fork.com
	
	Node root; //the root of the prediction suffix tree
	int L = 4; //maximum token sequence length
	double pMin;
	int totalInputTokens;
	
	Tree() {
		root = new Node();
		pMin = 0.15;
	}
	
	int getL() {
		return L;
	}
	
	void train(ArrayList<T> input) { //trains PST
		
		for (int i = 1; i <= L; i++) {
			
			for (int j = i - 1; j < input.size() - 1; j++) { 
				
				ArrayList<T> curSequence = new ArrayList<T>(); 
				
				for (int p = i; p > 0; p--) { //populates curSequence
					curSequence.add(input.get((j - p) + 1));
				}
				
				Node theNewNode = new Node(); //creates a new node
				
				theNewNode.setTokenSequence(curSequence); //sets the tokenSequence in
				
				root.addNode(theNewNode); //adds new node to the PST
			}
		}
		totalInputTokens = input.size();
		root.pMinElimination(totalInputTokens, pMin);
	}
	void print () {
		System.out.println("[]");
		root.print(); //prints the prediction suffix tree using the trained root node
		
	}
}
