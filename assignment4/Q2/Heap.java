package heap_package;
import java.util.ArrayList;
 
public class Heap{

	protected Node root;								// root of the heap
	protected Node[] nodes_array;                    // Stores the address of node corresponding to the keys
	private int max_size;                           // Maximum number of nodes heap can have 
	private static final String NullKeyException = "NullKey";      // Null key exception
	private static final String NullRootException = "NullRoot";    // Null root exception
	private static final String KeyAlreadyExistsException = "KeyAlreadyExists";   // Key already exists exception

	/* 
	   1. Can use helper methods but they have to be kept private. 
	   2. Not allowed to use any data structure. 
	*/

	public Heap(int max_size, int[] keys_array, int[] values_array) throws Exception{

		/* 
		   1. Create Max Heap for elements present in values_array.
		   2. keys_array.length == values_array.length and keys_array.length number of nodes should be created. 
		   3. Store the address of node created for keys_array[i] in nodes_array[keys_array[i]].
		   4. Heap should be stored based on the value i.e. root element of heap should 
		      have value which is maximum value in values_array.
		   5. max_size denotes maximum number of nodes that could be inserted in the heap. 
		   6. keys will be in range 0 to max_size-1.
		   7. There could be duplicate keys in keys_array and in that case throw KeyAlreadyExistsException. 
		*/

		/* 
		   For eg. keys_array = [1,5,4,50,22] and values_array = [4,10,5,23,15] : 
		   => So, here (key,value) pair is { (1,4), (5,10), (4,5), (50,23), (22,15) }.
		   => Now, when a node is created for element indexed 1 i.e. key = 5 and value = 10, 
		   	  that created node address should be saved in nodes_array[5]. 
		*/ 

		/*
		   n = keys_array.length
		   Expected Time Complexity : O(n).
		*/
		
		this.max_size = max_size;
		this.nodes_array = new Node[this.max_size];
		Node newNode1 = new Node(keys_array[0],values_array[0],null);
		nodes_array[keys_array[0]] = newNode1;
		for(int i=0;i<keys_array.length;i++){
			Node newNode = nodes_array[keys_array[i]];
			int leftInd = 2*i + 1;
			int rightInd = 2*i + 2;
			if(leftInd>=keys_array.length){
				break;
			}
			else{
				if(nodes_array[keys_array[leftInd]]!=null){
					throw new Exception(KeyAlreadyExistsException);
				}
				Node leftNode = new Node(keys_array[leftInd],values_array[leftInd],null);
				nodes_array[keys_array[leftInd]] = leftNode;
				newNode.left = leftNode;
				leftNode.parent = newNode;
			}
			if(rightInd>=keys_array.length){
				break;
			}
			else{
				if(nodes_array[keys_array[rightInd]]!=null){
					throw new Exception(KeyAlreadyExistsException);
				}
				Node rightNode = new Node(keys_array[rightInd],values_array[rightInd],null);
				nodes_array[keys_array[rightInd]] = rightNode;
				newNode.right = rightNode;
				rightNode.parent = newNode;
			}
		}
		this.root = nodes_array[keys_array[0]];
		int index = 0;
		if(nodes_array[keys_array[keys_array.length-1]].parent.right==null){
			index = (keys_array.length-2)/2;
		}
		else{
			index = (keys_array.length-3)/2;
		}
		for(int i=index;i>=0;i--){
			Node temp = nodes_array[keys_array[i]];
			Node leftHelp = temp.left;
			Node rightHelp = temp.right;
			int helpk = temp.key;
			if(rightHelp != null){
				if(rightHelp.is_complete && leftHelp.is_complete && leftHelp.height == rightHelp.height){
					temp.is_complete = true;
				}
				else{
					temp.is_complete = false;
				}
			}
			else{
				temp.is_complete = false;
			}
			temp.height = temp.left.height+1;
			bubbleDown(temp);
		}
	}

	private ArrayList<Integer> helpgetMax(Node temp,int max_value,ArrayList<Integer> max_keys){
		Node leftHelp = temp.left;
		Node rightHelp = temp.right;
		if(leftHelp!=null && leftHelp.value == temp.value){
			max_keys.add(leftHelp.key);
			max_keys = helpgetMax(leftHelp,this.root.value,max_keys);
		}
		else if(rightHelp!=null && rightHelp.value == temp.value){
			max_keys.add(rightHelp.key);
			max_keys = helpgetMax(rightHelp,this.root.value,max_keys);
		}
		return max_keys;
	}

	public ArrayList<Integer> getMax() throws Exception{

		/* 
		   1. Returns the keys with maximum value in the heap.
		   2. There could be multiple keys having same maximum value. You have
		      to return all such keys in ArrayList (order doesn't matter).
		   3. If heap is empty, throw NullRootException.

		   Expected Time Complexity : O(1).
		*/
		ArrayList<Integer> max_keys = new ArrayList<Integer>();    // Keys with maximum values in heap.
		if(this.root==null){
			throw new Exception(NullRootException);
		}
		
		Node help = this.root;
		max_keys.add(help.key);
		return helpgetMax(help,this.root.value,max_keys);
		
		// To be filled in by the student
	}

	public void insert(int key, int value) throws Exception{
		/* 
		   1. Insert a node whose key is "key" and value is "value" in heap 
		      and store the address of new node in nodes_array[key]. 
		   2. If key is already present in heap, throw KeyAlreadyExistsException.

		   Expected Time Complexity : O(logn).
		*/

		// To be filled in by the student
		Node newNode = new Node(key,value,null);
		if(this.root==null){
			this.root = newNode;
			nodes_array[key] = newNode;
			return;
		}
		if(nodes_array[key]!=null){
			throw new Exception(KeyAlreadyExistsException);
		}

		nodes_array[key] = newNode;
		Node help = this.root;
		while(true){
			if(help.is_complete){
				while(help.left!=null){
					help.height = help.height+1;
					help.is_complete = false;
					help = help.left;
				}
				help.left = newNode;
				newNode.parent = help;
				help.height = help.height+1;
				help.is_complete = false;
				break;
			}
			else{
				while(!help.left.is_complete){
					if(help.left.is_complete && help.left.height-1 == help.right.height){
						help.is_complete = true;
					}
					else{
						help.is_complete = false;
					}
					help = help.left;
				}
				if(help.right==null){
					help.right = newNode;
					newNode.parent = help;
					help.is_complete = true;
					break;
				}
				else{
					help = help.right;
				}
			}
		}
		while(help!=null && help.value<value){
			swap(help,newNode);
			newNode = help;
			help = help.parent;
		}
		return;	
	}

	private Node getLastNode(Node node) {
		if(node.right==null){
			node.height = 1;
			node.is_complete = true;
			Node help = node.left;
			node.left = null;
			return help;
		}
		if(!node.left.is_complete){
			while(!node.left.is_complete){
				node.is_complete = true;
				node.height = node.height -1;
				node = node.left;
			}
			node.height = 1;
			node.is_complete = true;
			Node help = node.left;
			node.left = null;
			return help;
		}
		else if(!node.right.is_complete){
			return getLastNode(node.right);
		}
		else if(node.left.height>node.right.height){
			return getLastNode(node.left);
		}
		else{
			while(node.right!=null){
				node = node.right;
			}
			Node help = node;
			node.parent.right = null;
			return node;
		}
	}

	private void swap(Node node1, Node node2) {
		Node help1 = nodes_array[node1.key];
		Node help2 = nodes_array[node2.key];
    	int helpKey = node1.key;
    	int helpvalue = node1.value;
    	node1.key = node2.key;
    	node1.value = node2.value;
    	node2.key = helpKey;
    	node2.value = helpvalue;
		nodes_array[node1.key] = help1;
		nodes_array[node2.key] = help2;
	}

	private void restoreHeapProperty(Node node) {
    	while (node != null) {
        	int max = node.value;
        	Node maxNode = node;
        	if (node.left != null && node.left.value > max) {
            	max = node.left.value;
            	maxNode = node.left;
        	}
        	if (node.right != null && node.right.value > max) {
            	max = node.right.value;
            	maxNode = node.right;
        	}
        	if (maxNode == node) {
            	break;
        	}
        	swap(maxNode, node);
        	node = maxNode;
    	}
	}


	public ArrayList<Integer> deleteMax() throws Exception{

		/* 
		   1. Remove nodes with the maximum value in the heap and returns their keys.
		   2. There could be multiple nodes having same maximum value. You have
		      to delete all such nodes and return all such keys in ArrayList (order doesn't matter).
		   3. If heap is empty, throw NullRootException.

		   Expected Average Time Complexity : O(logn).
		*/

		if(this.root==null){
			throw new Exception(NullRootException);
		}

		ArrayList<Integer> max_keys = new ArrayList<Integer>();   // Keys with maximum values in heap that will be deleted.
		max_keys = getMax();
        for (int i=0;i<max_keys.size();i++) {
            Node lastNode = getLastNode(this.root);
            swap(this.root, lastNode);
            restoreHeapProperty(this.root);
			nodes_array[max_keys.get(i)] = null;
        }
		// To be filled in by the student
		return max_keys;
	}

	public void update(int key, int diffvalue) throws Exception{

		/* 
		   1. Update the heap by changing the value of the node whose key is "key" to value+diffvalue.
		   2. If key doesn't exists in heap, throw NullKeyException.

		   Expected Time Complexity : O(logn).
		*/
		if(nodes_array[key]==null){
			throw new Exception(NullKeyException);
		}
		else {
			Node temp = nodes_array[key];
			int oldValue = temp.value;
			temp.value += diffvalue;
			diffvalue = temp.value;
			if(temp.parent!=null && diffvalue > temp.parent.value) {
				while(temp.parent != null && temp.value > temp.parent.value) {
					swap(temp, temp.parent);
					temp = temp.parent;
				}
			} 
			else{
				bubbleDown(temp);
			}
		}
	}
	
	private void bubbleDown(Node node) {
		while (node.left != null) {
			Node maxChild = node.left;
			if (node.right != null && node.right.value > maxChild.value) {
				maxChild = node.right;
			}
			if (node.value <= maxChild.value) {
				swap(node, maxChild);
				node = maxChild;
			} else {
				break;
			}
		}
	}
	
	public int getMaxValue() throws Exception{

		/* 
		   1. Returns maximum value in the heap.
		   2. If heap is empty, throw NullRootException.

		   Expected Time Complexity : O(1).
		*/

		// To be filled in by the student
		if(this.root==null){
			throw new Exception(NullRootException);
		}
		return this.root.value;
	}

	private ArrayList<Integer> helpgetKeys(Node temp,ArrayList<Integer> keys){
		Node leftHelp = temp.left;
		Node rightHelp = temp.right;
		if(leftHelp!=null){
			keys.add(leftHelp.key);
			keys = helpgetKeys(leftHelp,keys);
		}
		if(rightHelp!=null){
			keys.add(rightHelp.key);
			keys = helpgetKeys(rightHelp,keys);
		}
		return keys;
	}

	public ArrayList<Integer> getKeys() throws Exception{

		/*
		   1. Returns keys of the nodes stored in heap.
		   2. If heap is empty, throw NullRootException.
		 
		   Expected Time Complexity : O(n).
		*/
		ArrayList<Integer> keys = new ArrayList<Integer>();   // Stores keys of nodes in heap
		if(this.root==null){
			throw new Exception(NullRootException);
		}
		keys.add(this.root.key);
		return helpgetKeys(this.root,keys);

		// To be filled in by the student

	}
	// Write helper functions(if any) here (They have to be private).

	
	private void printyo(Node node){
		Node root = node;
		if(root==null){
			return;
		}
		if(root.left==null && root.right==null){
			System.out.println(root.value +" - "+ root.key  +" - "+ root.height  +" - "+ root.is_complete +" - "+  root.parent.value);
			return;
		}
		System.out.println(root.value +" - "+ root.key  +" - "+ root.height  +" - "+ root.is_complete);
		printyo(root.left);
		printyo(root.right);
		
	}
	
}
