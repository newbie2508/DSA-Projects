public class BalancedBST extends BST {

    int balancefactor(BSTNode node) {
        if(node==null){
            return 0;
        }
        if(node.left==null && node.right==null){
            return 0;
        }
        else if(node.right==null){
            return (-(node.left.height));
        }
        else if(node.left==null){
            return node.right.height;
        }
        return node.right.height-node.left.height;
    }
    BSTNode rightrotate(BSTNode parent) {
        BSTNode lc = parent.left;
        BSTNode rc = lc.right;
        lc.right = parent;
        parent.left = rc;
        if(parent!=null){
            if(parent.right==null && parent.left==null){
                parent.height=1;
            }
            else if(parent.right==null){
                parent.height=parent.left.height+1;
            }
            else if(parent.left==null){
                parent.height=parent.right.height+1;
            }
            else{
                parent.height = Math.max(parent.left.height, parent.right.height) + 1;
            }
        }
        if(lc!=null){
            if(lc.right==null && lc.left==null){
                lc.height=1;
            }
            else if(lc.right==null){
                lc.height=lc.left.height+1;
            }
            else if(parent.left==null){
                lc.height=lc.right.height+1;
            }
            else{
                lc.height = Math.max(lc.left.height, lc.right.height) + 1;
            }
        }
        return lc;
    }
    BSTNode leftrotate(BSTNode parent) {
        BSTNode rc = parent.right;
        BSTNode lc = rc.left;
        rc.left = parent;
        parent.right = lc;
        if(parent!=null){
            if(parent.right==null && parent.left==null){
                parent.height=1;
            }
            else if(parent.right==null){
                parent.height=parent.left.height+1;
            }
            else if(parent.left==null){
                parent.height=parent.right.height+1;
            }
            else{
                parent.height = Math.max(parent.left.height, parent.right.height) + 1;
            }
        }
        if(rc!=null){   
            if(rc.right==null && rc.left==null){
                rc.height=1;
            }
            else if(rc.right==null){
                rc.height=rc.left.height+1;
            }
            else if(parent.left==null){
                rc.height=rc.right.height+1;
            }
            else{
                rc.height = Math.max(rc.left.height, rc.right.height) + 1;
            }
        }
        return rc;
    }
    private BSTNode avlhelpinsert(BSTNode node,int key){
        if(node==null){
            BSTNode yo=new BSTNode(key);
            return yo;
        }
        if(key>node.value){
            BSTNode rt=avlhelpinsert(node.right,key);
            node.right=rt;
            if(node.left==null){
                node.height=node.right.height+1;
            }
            else{
                node.height=(Math.max(node.right.height,node.left.height))+1;
            }
        }
        else{
            BSTNode lt=avlhelpinsert(node.left,key);
            node.left=lt;
            if(node.right==null){
                node.height=node.left.height+1;
            }
            else{
                node.height=(Math.max(node.right.height,node.left.height))+1;
            }
        }
        int balancefactor=balancefactor(node);
        if(balancefactor==0){
            return node;
        }
        if(node.left==null){
            if(balancefactor>1 && node.right.value<key){
                return leftrotate(node);
            }
            else if(balancefactor>1 && node.right.value>key){
                node.right=rightrotate(node.right);
                return leftrotate(node);
            }
        }
        else if(node.right==null){
            if(balancefactor<(-1) && node.left.value>key){
                return rightrotate(node);
            }
            else if(balancefactor<(-1) && node.left.value<key){
                node.left=leftrotate(node.left);
                return rightrotate(node);
            } 
        }
        else{
            if(balancefactor>1 && node.right.value<key){
                return leftrotate(node);
            }
            else if(balancefactor>1 && node.right.value>key){
                node.right=rightrotate(node.right);
                return leftrotate(node);
            }
            else if(balancefactor<(-1) && node.left.value>key){
                return rightrotate(node);
            }
            else if(balancefactor<(-1) && node.left.value<key){
                node.left=leftrotate(node.left);
                return rightrotate(node);
            }
        }
        return node;
    }
    public void insert(int key){
        // TO be completed by students
        this.root=avlhelpinsert(this.root,key);
    }
    private BSTNode helpremoveavl(BSTNode node,int key){
        if(node==null){
            return null;
        }
        if(node.value==key){
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
                BSTNode rtyu=helpremoveavl(node.left,yos.value);
                node.left=rtyu;
            }
        }
        else if(node.value>key){
            BSTNode lt=helpremoveavl(node.left,key);
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
        else if(node.value<key){
            BSTNode rt=helpremoveavl(node.right,key);
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
        }
        int balancefactor=balancefactor(node);
        if(balancefactor==0){
            return node;
        }
        if(node.left==null){
            if(balancefactor>1 && balancefactor(root.right)>=0){
                return leftrotate(node);
            }
            else if(balancefactor>1 && balancefactor(root.right) < 0){
                node.right=rightrotate(node.right);
                return leftrotate(node);
            }
        }
        else if(node.right==null){
            if(balancefactor<(-1) && balancefactor(root.left) <= 0){
                return rightrotate(node);
            }
            else if(balancefactor<(-1) && balancefactor(root.left) > 0){
                node.left=leftrotate(node.left);
                return rightrotate(node);
            } 
        }
        else{
            if(balancefactor>1 && balancefactor(root.right)>=0){
                return leftrotate(node);
            }
            else if(balancefactor>1 && balancefactor(root.right) < 0){
                node.right=rightrotate(node.right);
                return leftrotate(node);
            }
            else if(balancefactor<(-1) && balancefactor(root.left) <= 0){
                return rightrotate(node);
            }
            else if(balancefactor<(-1) && balancefactor(root.left) > 0){
                node.left=leftrotate(node.left);
                return rightrotate(node);
            }
        }
        return node;
        
    }
    public boolean delete(int key){
        // TO be completed by students
	    if(search(key)){
		    this.root=helpremoveavl(this.root, key);
            return true;
        }
        else{
            return false;
        }
    }
    
}


