import java.util.ArrayList;

public class Tree {

    public TreeNode root;

    public Tree() {
        root = null;
    }
    private void split(TreeNode nodetobesplit, TreeNode parent) {
        if (nodetobesplit.children.size() == 0) {
            TreeNode nodeleft = new TreeNode();
            TreeNode noderight = new TreeNode();
            nodeleft.height = nodetobesplit.height;
            noderight.height = nodetobesplit.height;
            String node1last = nodetobesplit.s.get(1);
            int value = nodetobesplit.val.get(1);
            nodeleft.s.add(nodetobesplit.s.get(0));
            nodeleft.val.add(nodetobesplit.val.get(0));
            noderight.s.add(nodetobesplit.s.get(2));
            noderight.val.add(nodetobesplit.val.get(2));
            noderight.s.add(nodetobesplit.s.get(3));
            noderight.val.add(nodetobesplit.val.get(3));
            if (nodetobesplit == this.root) {
                TreeNode newnode = new TreeNode();
                newnode.s.add(node1last);
                newnode.val.add(value);
                newnode.children.add(nodeleft);
                newnode.children.add(noderight);
                newnode.height = this.root.height + 1;
                this.root = newnode;
                return;
            }
            int count = 0;
            for (int i = 0; i < parent.s.size(); i++) {
                if (node1last.compareTo(parent.s.get(i)) < 0) {
                    break;
                } else {
                    count++;
                }
            }
            parent.s.add(count, node1last);
            parent.val.add(count, value);
            parent.children.remove(nodetobesplit);
            parent.children.add(count, noderight);
            parent.children.add(count, nodeleft);
        } else {
            TreeNode nodeleft = new TreeNode();
            TreeNode noderight = new TreeNode();
            String node1last = nodetobesplit.s.get(1);
            int value = nodetobesplit.val.get(1);
            nodeleft.height = nodetobesplit.height;
            noderight.height = nodetobesplit.height;
            nodeleft.s.add(nodetobesplit.s.get(0));
            nodeleft.val.add(nodetobesplit.val.get(0));
            noderight.s.add(nodetobesplit.s.get(2));
            noderight.val.add(nodetobesplit.val.get(2));
            noderight.s.add(nodetobesplit.s.get(3));
            noderight.val.add(nodetobesplit.val.get(3));
            ArrayList<TreeNode> nodetobesplitchild = nodetobesplit.children;
            if (nodetobesplit == this.root) {
                TreeNode newnode = new TreeNode();
                newnode.s.add(node1last);
                newnode.val.add(value);
                newnode.children.add(nodeleft);
                newnode.children.add(noderight);
                nodeleft.children.add(nodetobesplitchild.get(0));
                nodeleft.children.add(nodetobesplitchild.get(1));
                for (int i = 2; i <= 4; i++) {
                    noderight.children.add(nodetobesplitchild.get(i));
                }
                newnode.height = this.root.height + 1;
                this.root = newnode;
                return;
            }
            int count = 0;
            for (int i = 0; i < parent.s.size(); i++) {
                if (node1last.compareTo(parent.s.get(i)) < 0) {
                    break;
                } else {
                    count++;
                }
            }
            parent.s.add(count, node1last);
            parent.val.add(count, value);
            parent.children.remove(nodetobesplit);
            parent.children.add(count, noderight);
            parent.children.add(count, nodeleft);
            nodeleft.children.add(nodetobesplitchild.get(0));
            nodeleft.children.add(nodetobesplitchild.get(1));
            for (int i = 2; i <= 4; i++) {
                noderight.children.add(nodetobesplitchild.get(i));
            }
        }
    }

    private void inserthelp(String str, TreeNode root, TreeNode parent) {
        if (root == null) {
            TreeNode yo = new TreeNode();
            yo.s.add(str);
            yo.val.add(1);
            this.root = yo;
            return;
        }
        int count = 0;
        for (int i = 0; i < root.s.size(); i++) {
            if (str.compareTo(root.s.get(i)) < 0) {
                break;
            } else if (str.compareTo(root.s.get(i)) > 0) {
                count++;
            }
        }
        if (root.children.size() == 0) {
            root.s.add(count, str);
            root.val.add(count, 1);
            if (root.s.size() == 4) {
                split(root, parent);
            }
            return;
        }
        inserthelp(str, root.children.get(count), root);
        if(root.s.size() == 4){
            split(root, parent);
            return;
        }
    }

    public void insert(String s) {
        // TO be completed by students
        inserthelp(s, this.root, null);
    }

    private TreeNode predecessor(TreeNode root) {
        if (root.children.size() == 0) {
            return root;
        }
        return predecessor(root.children.get(root.children.size() - 1));
    }

    private TreeNode getsibling(TreeNode node, TreeNode parent) {
        if (parent == null) {
            return null;
        }
        int index = parent.children.indexOf(node);
        if (index == 0) {
            return parent.children.get(index + 1);
        } else {
            return parent.children.get(index - 1);
        }
    }

    private void borrowing(TreeNode borrower, TreeNode lender, TreeNode parent) {
        int indexlender = parent.children.indexOf(lender);
        int indexborrower = parent.children.indexOf(borrower);
        int parentindex = 0;
        if (indexlender > indexborrower) {
            parentindex = indexborrower;
        } else {
            parentindex = indexlender;
        }
        borrower.s.add(parent.s.get(parentindex));
        borrower.val.add(parent.val.get(parentindex));
        if (indexborrower == 0) {
            parent.s.set(parentindex, lender.s.get(0));
            parent.val.set(parentindex, lender.val.get(0));
            lender.s.remove(0);
            lender.val.remove(0);
        } else {
            parent.s.set(parentindex, lender.s.get(lender.s.size() - 1));
            parent.val.set(parentindex, lender.val.get(lender.val.size() - 1));
            lender.s.remove(lender.s.size() - 1);
            lender.val.remove(lender.val.size() - 1);
        }
    }

    private void merging(TreeNode empty, TreeNode node2, TreeNode parent) {
        if (empty.children.size() == 0) {
            int node1index = parent.children.indexOf(empty);
            int node2index = parent.children.indexOf(node2);
            int parentindex = 0;
            if (node1index > node2index) {
                parentindex = node2index;
            } else {
                parentindex = node1index;
            }
            TreeNode merged = new TreeNode();
            merged.s.add(node2.s.get(0));
            merged.val.add(node2.val.get(0));
            if ((node2.s.get(0)).compareTo(parent.s.get(parentindex)) > 0) {
                merged.s.add(0, parent.s.get(parentindex));
                merged.val.add(0, parent.val.get(parentindex));
            } else {
                merged.s.add(parent.s.get(parentindex));
                merged.val.add(parent.val.get(parentindex));
            }
            if (parent == this.root && this.root.s.size() == 1) {
                parent.s.remove(parentindex);
                parent.val.remove(parentindex);
                parent.children.remove(node1index);
                parent.children.remove(parent.children.indexOf(node2));
                this.root = merged;
                return;
            }
            parent.s.remove(parentindex);
            parent.val.remove(parentindex);
            parent.children.remove(node1index);
            parent.children.remove(parent.children.indexOf(node2));
            parent.children.add(parentindex, merged);
        } else {
            int node1index = parent.children.indexOf(empty);
            int node2index = parent.children.indexOf(node2);
            int parentindex = 0;
            if (node1index > node2index) {
                parentindex = node2index;
            } else {
                parentindex = node1index;
            }
            TreeNode merged = new TreeNode();
            merged.height = node2.height;
            merged.s.add(node2.s.get(0));
            merged.val.add(node2.val.get(0));
            if ((node2.s.get(0)).compareTo(parent.s.get(parentindex)) > 0) {
                merged.s.add(0, parent.s.get(parentindex));
                merged.val.add(0, parent.val.get(parentindex));
            } else {
                merged.s.add(parent.s.get(parentindex));
                merged.val.add(parent.val.get(parentindex));
            }
            if (parent == this.root && this.root.s.size() == 1) {
                parent.s.remove(parentindex);
                parent.val.remove(parentindex);
                if (node1index < node2index) {
                    merged.children.add(empty.children.get(0));
                    merged.children.add(node2.children.get(0));
                    merged.children.add(node2.children.get(1));
                } else {
                    merged.children.add(node2.children.get(0));
                    merged.children.add(node2.children.get(1));
                    merged.children.add(empty.children.get(0));
                }
                parent.children.remove(node1index);
                parent.children.remove(parent.children.indexOf(node2));
                this.root = merged;
                return;
            }
            parent.s.remove(parentindex);
            parent.val.remove(parentindex);
            if (node1index < node2index) {
                merged.children.add(empty.children.get(0));
                merged.children.add(node2.children.get(0));
                merged.children.add(node2.children.get(1));
            } else {
                merged.children.add(node2.children.get(0));
                merged.children.add(node2.children.get(1));
                merged.children.add(empty.children.get(0));
            }
            parent.children.remove(node1index);
            parent.children.remove(parent.children.indexOf(node2));
            parent.children.add(parentindex, merged);
        }
    }

    private void deletehelp(String str, TreeNode root, TreeNode parent) {
        if (root == this.root && root.children.size() == 0 && root.s.size() == 1) {
            root.val.remove(root.s.indexOf(str));
            root.s.remove(str);
            root.height=0;
            return;
        }
        if (root.s.contains(str)) {
            if (root.children.size() == 0) {
                if (root.s.size() > 1) {
                    root.val.remove(root.s.indexOf(str));
                    root.s.remove(str);
                } else if (root.s.size() == 1) {
                    TreeNode sibling = getsibling(root, parent);
                    if (sibling.s.size() > 1) {
                        root.val.remove(root.s.indexOf(str));
                        root.s.remove(str);
                        borrowing(root, sibling, parent);
                    } else if (sibling.s.size() == 1) {
                        root.val.remove(root.s.indexOf(str));
                        root.s.remove(str);
                        merging(root, sibling, parent);
                    }
                }
                return;
            } else {
                int index = root.s.indexOf(str);
                TreeNode predec = predecessor(root.children.get(index));
                String predecstr = predec.s.get(predec.s.size() - 1);
                int predecval = predec.val.get(predec.val.size() - 1);
                predec.s.set(predec.s.size() - 1, str);
                predec.val.set(predec.val.size() - 1, root.val.get(index));
                root.s.set(index, predecstr);
                root.val.set(index, predecval);
                deletehelp(str, root.children.get(index), root);
                if (root.s.size() == 0) {
                    TreeNode sibling = getsibling(root, parent);
                    if (sibling == null) {
                        return;
                    }
                    if (sibling.s.size() > 1) {
                        borrowing(root, sibling, parent);
                    } else if (sibling.s.size() == 1) {
                        merging(root, sibling, parent);
                    }
                }
                return;
            }
        }
        int count = 0;
        for (int i = 0; i < root.s.size(); i++) {
            if (str.compareTo(root.s.get(i)) < 0) {
                break;
            } else if (str.compareTo(root.s.get(i)) > 0) {
                count++;
            }
        }
        deletehelp(str, root.children.get(count), root);
        if (root.s.size() == 0) {
            TreeNode sibling = getsibling(root, parent);
            if (sibling == null) {
                return;
            }
            if (sibling.s.size() > 1) {
                borrowing(root, sibling, parent);
            } else if (sibling.s.size() == 1) {
                merging(root, sibling, parent);
            }
        }
    }

    public boolean delete(String s) {
        // TO be completed by students
        if (search(s)) {
            deletehelp(s, this.root, null);
            return true;
        } else {
            return false;
        }
    }

    private boolean searchhelp(String str, TreeNode root) {
        if (root == null) {
            return false;
        }
        if (root.s.contains(str)) {
            return true;
        }
        if (root.children.size() == 0) {
            return false;
        }
        int count = 0;
        for (int i = 0; i < root.s.size(); i++) {
            if (str.compareTo(root.s.get(i)) < 0) {
                break;
            } else if (str.compareTo(root.s.get(i)) > 0) {
                count++;
            }
        }
        return searchhelp(str, root.children.get(count));
    }

    public boolean search(String s) {
        // TO be completed by students
        return searchhelp(s, this.root);
    }

    private int incrementhelp(String str, TreeNode root) {
        if (root.s.contains(str)) {
            int yo = root.s.indexOf(str);
            int old = root.val.get(yo);
            old++;
            root.val.set(yo, old);
            return old;
        }
        if (root.children.size() == 0) {
            return 0;
        }
        int count = 0;
        for (int i = 0; i < root.s.size(); i++) {
            if (str.compareTo(root.s.get(i)) < 0) {
                break;
            } else if (str.compareTo(root.s.get(i)) > 0) {
                count++;
            }
        }
        int yo = incrementhelp(str, root.children.get(count));
        return yo;
    }

    public int increment(String s) {
        // TO be completed by students
        return incrementhelp(s, this.root);
    }

    private int decrementhelp(String str, TreeNode root) {
        if (root.s.contains(str)) {
            int yo = root.s.indexOf(str);
            int old = root.val.get(yo);
            old--;
            root.val.set(yo, old);
            return old;
        }
        if (root.children.size() == 0) {
            return 0;
        }
        int count = 0;
        for (int i = 0; i < root.s.size(); i++) {
            if (str.compareTo(root.s.get(i)) < 0) {
                break;
            } else if (str.compareTo(root.s.get(i)) > 0) {
                count++;
            }
        }
        int yo = decrementhelp(str, root.children.get(count));
        return yo;
    }

    public int decrement(String s) {
        // TO be completed by students
        return decrementhelp(s, this.root);
    }

    public int getHeight() {
        // TO be completed by students
        return this.root.height;
    }

    private int valhelp(String str, TreeNode root) {
        if (root.s.contains(str)) {
            int yo = root.s.indexOf(str);
            return root.val.get(yo);
        }
        if (root.children.size() == 0) {
            return 0;
        }
        int count = 0;
        for (int i = 0; i < root.s.size(); i++) {
            if (str.compareTo(root.s.get(i)) < 0) {
                break;
            } else if (str.compareTo(root.s.get(i)) > 0) {
                count++;
            }
        }
        return valhelp(str, root.children.get(count));
    }

    public int getVal(String s) {
        // TO be completed by students
        return valhelp(s, this.root);
    }

}
