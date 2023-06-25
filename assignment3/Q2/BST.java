import java.util.*;
public class BST {

    public BSTNode root;

    public BST() {
        root = null;
    }
    private BSTNode helpinsert(BSTNode node,int num){
        if(node==null){
            BSTNode yo=new BSTNode(num);
            return yo;
        }
        if(num>node.value){
            BSTNode rt=helpinsert(node.right,num);
            node.right=rt;
            if(node.left==null){
                node.height=node.right.height+1;
            }
            else{
                node.height=(Math.max(node.right.height,node.left.height))+1;
            }
            return node;
        }
        else{
            BSTNode lt=helpinsert(node.left,num);
            node.left=lt;
            if(node.right==null){
                node.height=node.left.height+1;
            }
            else{
                node.height=(Math.max(node.right.height,node.left.height))+1;
            }
            return node;
        }
    }
    public void insert(int num) {
        // TO be completed by students
        this.root=helpinsert(this.root, num);
    }
    public BSTNode lmax(BSTNode root) {
        if (root == null) {
          return null;
        }
        if(root.right==null){
            return root;
        }
        return lmax(root.right);
    }
    private BSTNode helpremove(BSTNode node,int num){
        if(node==null){
            return null;
        }
        if(node.value==num){
            if(node.left==null && node.right==null){
                return null;
            }
            else if(node.left==null){
                return node.right;
            }
            else if(node.right==null){
                return node.left;
            }
            else if(node.left!=null && node.right!=null){
                BSTNode yos=lmax(node.left);
                node.value=yos.value;
                BSTNode rtyu=helpremove(node.left,yos.value);
                node.left=rtyu;
            }
        }
        else if(node.value>num){
            BSTNode lt=helpremove(node.left,num);
            node.left=lt;
            if(node.left==null && node.right==null){
                node.height=1;
            }
            if(node.left==null && node.right!=null){
                node.height=node.right.height+1;
            }
            if(node.left!=null && node.right==null){
                node.height=node.left.height+1;
            }
            if(node.right!=null && node.left!=null){
                node.height=Math.max(node.left.height,node.right.height)+1;
            }
            return node;
        }
        BSTNode rt=helpremove(node.right,num);
        node.right=rt;
        if(node.left==null && node.right==null){
            node.height=1;
        }
        if(node.left==null && node.right!=null){
            node.height=node.right.height+1;
        }
        if(node.left!=null && node.right==null){
            node.height=node.left.height+1;
        }
        if(node.right!=null && node.left!=null){
            node.height=Math.max(node.left.height,node.right.height)+1;
        }
        return node;
        
    }
    public boolean delete(int num) {
        // TO be completed by students
        boolean ty=search(num);
        if(ty){
		    this.root=helpremove(this.root, num);
            return true;
        }
        else{
            return false;
        }
    }
    private boolean searchhelp(BSTNode node,int num){
        if(node==null){
            return false;
        }
        if((node.value)==num){
            return true;
        }
        else if(node.value>num){
            return searchhelp(node.left,num);
        }
        else{
            return searchhelp(node.right,num);
        }
    }
    public boolean search(int num) {
        // TO be completed by students
        return searchhelp(this.root, num);
    }
    private ArrayList<Integer> helpinorder(BSTNode node,ArrayList<Integer> yo){
        if(node==null){
            return null;
        }
        helpinorder(node.left,yo);
        yo.add(node.value);
        helpinorder(node.right,yo);
        return yo;
    }
    public ArrayList<Integer> inorder() {
        // TO be completed by students
        ArrayList<Integer> al= new ArrayList<>();
		return helpinorder(this.root,al);
    }
    private ArrayList<Integer> helppreorder(BSTNode node,ArrayList<Integer> yo){
        if(node==null){
            return null;
        }
        yo.add(node.value);
        helppreorder(node.left,yo);
        helppreorder(node.right,yo);
        return yo;
    }
    public ArrayList<Integer> preorder() {
        // TO be completed by students
		ArrayList<Integer> al = new ArrayList<>();
		return helppreorder(this.root, al);
    }
    private ArrayList<Integer> helppostorder(BSTNode node,ArrayList<Integer> yo){
        if(node==null){
            return null;
        }
        helppostorder(node.left,yo);
        helppostorder(node.right,yo);
        yo.add(node.value);
        return yo;
    }
    public ArrayList<Integer> postorder() {
        // TO be completed by students
		ArrayList<Integer> al = new ArrayList<>();
		return helppostorder(this.root, al);
    }
    private void seeheighthelp(BSTNode node){
        if(node==null){
            return;
        }
        System.out.print(node.height+" ");
        seeheighthelp(node.left);
        seeheighthelp(node.right);
    }
    public void seeheight(){
        seeheighthelp(this.root);

    }
    
}