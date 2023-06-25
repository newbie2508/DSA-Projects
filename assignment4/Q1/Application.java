import java.util.ArrayList;
import java.util.Scanner;  
import java.io.File;
import java.io.FileNotFoundException;

public class Application extends Tree {
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
            nodeleft.count = nodetobesplit.val.get(0);
            nodeleft.max_s = nodetobesplit.s.get(0);
            nodeleft.max_value = nodetobesplit.val.get(0);
            noderight.s.add(nodetobesplit.s.get(2));
            noderight.val.add(nodetobesplit.val.get(2));
            noderight.s.add(nodetobesplit.s.get(3));
            noderight.val.add(nodetobesplit.val.get(3));
            noderight.count = nodetobesplit.val.get(2) + nodetobesplit.val.get(3);
            if (nodetobesplit.val.get(2) >= nodetobesplit.val.get(3)) {
                noderight.max_s = nodetobesplit.s.get(2);
                noderight.max_value = nodetobesplit.val.get(2);
            } else if(nodetobesplit.val.get(2) < nodetobesplit.val.get(3)) {
                noderight.max_s = nodetobesplit.s.get(3);
                noderight.max_value = nodetobesplit.val.get(3);
            }
            if (nodetobesplit == this.root) {
                TreeNode newnode = new TreeNode();
                newnode.s.add(node1last);
                newnode.val.add(value);
                newnode.children.add(nodeleft);
                newnode.children.add(noderight);
                newnode.count = newnode.count + value + nodeleft.count + noderight.count;
                if (nodeleft.max_value >= noderight.max_value) {
                    if (value > nodeleft.max_value) {
                        newnode.max_s = node1last;
                        newnode.max_value = value;
                    } else if(value <= nodeleft.max_value) {
                        newnode.max_s = nodeleft.max_s;
                        newnode.max_value = nodeleft.max_value;
                    }
                } else if(nodeleft.max_value < noderight.max_value) {
                    if (value >= noderight.max_value) {
                        newnode.max_s = node1last;
                        newnode.max_value = value;
                    } else if(value < noderight.max_value) {
                        newnode.max_s = noderight.max_s;
                        newnode.max_value = noderight.max_value;
                    }
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
            parent.count = parent.count - nodetobesplit.count;
            parent.children.remove(nodetobesplit);
            parent.children.add(count, noderight);
            parent.children.add(count, nodeleft);
            parent.count = parent.count + value + noderight.count + nodeleft.count;
        } else {
            TreeNode nodeleft = new TreeNode();
            TreeNode noderight = new TreeNode();
            String node1last = nodetobesplit.s.get(1);
            int value = nodetobesplit.val.get(1);
            nodeleft.height = nodetobesplit.height;
            noderight.height = nodetobesplit.height;
            nodeleft.s.add(nodetobesplit.s.get(0));
            nodeleft.val.add(nodetobesplit.val.get(0));
            nodeleft.count = nodetobesplit.val.get(0);
            nodeleft.max_s = nodetobesplit.s.get(0);
            nodeleft.max_value = nodetobesplit.val.get(0);
            noderight.s.add(nodetobesplit.s.get(2));
            noderight.val.add(nodetobesplit.val.get(2));
            noderight.s.add(nodetobesplit.s.get(3));
            noderight.val.add(nodetobesplit.val.get(3));
            noderight.count = nodetobesplit.val.get(2) + nodetobesplit.val.get(3);
            if (nodetobesplit.val.get(2) >= nodetobesplit.val.get(3)) {
                noderight.max_s = nodetobesplit.s.get(2);
                noderight.max_value = nodetobesplit.val.get(2);
            } else {
                noderight.max_s = nodetobesplit.s.get(3);
                noderight.max_value = nodetobesplit.val.get(3);
            }
            ArrayList<TreeNode> nodetobesplitchild = nodetobesplit.children;
            if (nodetobesplit == this.root) {
                TreeNode newnode = new TreeNode();
                newnode.s.add(node1last);
                newnode.val.add(value);
                newnode.children.add(nodeleft);
                newnode.children.add(noderight);
                nodeleft.children.add(nodetobesplitchild.get(0));
                nodeleft.count = nodeleft.count + nodetobesplit.children.get(0).count;
                nodeleft.children.add(nodetobesplitchild.get(1));
                nodeleft.count = nodeleft.count + nodetobesplit.children.get(1).count;
                if (nodetobesplitchild.get(0).max_value >= nodetobesplitchild.get(1).max_value) {
                    if (nodeleft.max_value <= nodetobesplitchild.get(0).max_value) {
                        nodeleft.max_value = nodetobesplitchild.get(0).max_value;
                        nodeleft.max_s = nodetobesplitchild.get(0).max_s;
                    }
                } else {
                    if (nodeleft.max_value < nodetobesplitchild.get(1).max_value) {
                        nodeleft.max_value = nodetobesplitchild.get(1).max_value;
                        nodeleft.max_s = nodetobesplitchild.get(1).max_s;
                    }
                }
                for (int i = 2; i <= 4; i++) {
                    noderight.children.add(nodetobesplitchild.get(i));
                    noderight.count = noderight.count + nodetobesplit.children.get(i).count;
                }
                for(int i=2;i<=4;i++){
                    if(noderight.max_value<nodetobesplitchild.get(i).max_value){
                        noderight.max_s=nodetobesplitchild.get(i).max_s;
                        noderight.max_value=nodetobesplitchild.get(i).max_value;
                    }
                    if(noderight.max_value==nodetobesplitchild.get(i).max_value){
                        if(noderight.max_s.compareTo(nodetobesplitchild.get(i).max_s)>0){
                            noderight.max_s=nodetobesplitchild.get(i).max_s;
                            noderight.max_value=nodetobesplitchild.get(i).max_value;
                        }
                    }
                }
                if (nodeleft.max_value >= noderight.max_value) {
                    if (value > nodeleft.max_value) {
                        newnode.max_s = node1last;
                        newnode.max_value = value;
                    } else if(value <= nodeleft.max_value) {
                        newnode.max_s = nodeleft.max_s;
                        newnode.max_value = nodeleft.max_value;
                    }
                } else if(nodeleft.max_value < noderight.max_value) {
                    if (value >= noderight.max_value) {
                        newnode.max_s = node1last;
                        newnode.max_value = value;
                    } else if(value < noderight.max_value) {
                        newnode.max_s = noderight.max_s;
                        newnode.max_value = noderight.max_value;
                    }
                }
                newnode.height = this.root.height + 1;
                newnode.count = value + nodeleft.count + noderight.count;
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
            parent.count = parent.count - nodetobesplit.count;
            parent.children.remove(nodetobesplit);
            parent.children.add(count, noderight);
            parent.children.add(count, nodeleft);
            nodeleft.children.add(nodetobesplitchild.get(0));
            nodeleft.count = nodeleft.count + nodetobesplit.children.get(0).count;
            nodeleft.children.add(nodetobesplitchild.get(1));
            nodeleft.count = nodeleft.count + nodetobesplit.children.get(1).count;
            if (nodetobesplitchild.get(0).max_value >= nodetobesplitchild.get(1).max_value) {
                if (nodeleft.max_value <= nodetobesplitchild.get(0).max_value) {
                    nodeleft.max_value = nodetobesplitchild.get(0).max_value;
                    nodeleft.max_s = nodetobesplitchild.get(0).max_s;
                }
            } else {
                if (nodeleft.max_value < nodetobesplitchild.get(1).max_value) {
                    nodeleft.max_value = nodetobesplitchild.get(1).max_value;
                    nodeleft.max_s = nodetobesplitchild.get(1).max_s;
                }
            }
            for (int i = 2; i <= 4; i++) {
                noderight.children.add(nodetobesplitchild.get(i));
                noderight.count = noderight.count + nodetobesplit.children.get(i).count;
            }
            for(int i=2;i<=4;i++){
                if(noderight.max_value<nodetobesplitchild.get(i).max_value){
                    noderight.max_s=nodetobesplitchild.get(i).max_s;
                    noderight.max_value=nodetobesplitchild.get(i).max_value;
                }
                if(noderight.max_value==nodetobesplitchild.get(i).max_value){
                    if(noderight.max_s.compareTo(nodetobesplitchild.get(i).max_s)>0){
                        noderight.max_s=nodetobesplitchild.get(i).max_s;
                        noderight.max_value=nodetobesplitchild.get(i).max_value;
                    }
                }
            }
            parent.count = parent.count + value + noderight.count + nodeleft.count;
        }
    }

    private void inserthelp(String str, TreeNode root, TreeNode parent) {
        if (root == null) {
            TreeNode yo = new TreeNode();
            yo.s.add(str);
            yo.val.add(1);
            yo.count = 1;
            yo.max_s=str;
            yo.max_value=1;
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
            root.count = root.count + 1;
            if(root.max_value==1){
                if(str.compareTo(root.max_s)<0){
                    root.max_s=str;
                }
            }
            if (root.s.size() == 4) {
                split(root, parent);
            }
            return;
        }
        inserthelp(str, root.children.get(count), root);
        root.count = 0;
        for (int i = 0; i < root.val.size(); i++) {
            root.count = root.count + root.val.get(i);
        }
        for (int i = 0; i < root.children.size(); i++) {
            root.count = root.count + root.children.get(i).count;
        }
        for (int i = 0; i < root.val.size(); i++) {
            if(root.max_value<root.val.get(i)){
                root.max_s=root.s.get(i);
                root.max_value=root.val.get(i);
            }
            if(root.max_value==root.children.get(i).max_value){
                if(root.max_s.compareTo(root.s.get(i))>0){
                    root.max_s=root.s.get(i);
                    root.max_value=root.val.get(i);
                }
            }
        }
        for (int i = 0; i < root.children.size(); i++) {
            if(root.max_value<root.children.get(i).max_value){
                root.max_s=root.children.get(i).max_s;
                root.max_value=root.children.get(i).max_value;
            }
            if(root.max_value==root.children.get(i).max_value){
                if(root.max_s.compareTo(root.children.get(i).max_s)>0){
                    root.max_s=root.children.get(i).max_s;
                    root.max_value=root.children.get(i).max_value;
                }
            }
        }
        if(root.s.size() == 4){
            split(root, parent);
            return;
        }
    }
    public void insert(String s){
        // TO be completed by students
        inserthelp(s, this.root, null);
    }
    private int incrementhelp(String str, TreeNode root) {
        if (root.s.contains(str)) {
            int yo = root.s.indexOf(str);
            int old = root.val.get(yo);
            old++;
            root.val.set(yo, old);
            root.count = root.count + 1;
            if(old>root.max_value){
                root.max_value=old;
                root.max_s=root.s.get(yo);
            }
            else if(root.max_value==old){
                if(root.s.get(yo).compareTo(root.max_s)<0){
                    root.max_value=old;
                    root.max_s=root.s.get(yo);
                }
            }
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
        root.count = 0;
        for (int i = 0; i < root.val.size(); i++) {
            root.count = root.count + root.val.get(i);
        }
        for (int i = 0; i < root.children.size(); i++) {
            root.count = root.count + root.children.get(i).count;
        }
        for (int i = 0; i < root.val.size(); i++) {
            if(root.max_value<root.val.get(i)){
                root.max_s=root.s.get(i);
                root.max_value=root.val.get(i);
            }
            if(root.max_value==root.val.get(i)){
                if(root.max_s.compareTo(root.s.get(i))>0){
                    root.max_s=root.s.get(i);
                    root.max_value=root.val.get(i);
                }
            }
        }
        for (int i = 0; i < root.children.size(); i++) {
            if(root.max_value<root.children.get(i).max_value){
                root.max_s=root.children.get(i).max_s;
                root.max_value=root.children.get(i).max_value;
            }
            if(root.max_value==root.children.get(i).max_value){
                if(root.max_s.compareTo(root.children.get(i).max_s)>0){
                    root.max_s=root.children.get(i).max_s;
                    root.max_value=root.children.get(i).max_value;
                }
            }
        }
        return yo;
    }
    public int increment(String s){
        // TO be completed by students
        return incrementhelp(s, this.root);
    }
    private int decrementhelp(String str, TreeNode root) {
        if (root.s.contains(str)) {
            int yo = root.s.indexOf(str);
            int old = root.val.get(yo);
            old--;
            root.val.set(yo, old);
            root.count = root.count - 1;
            old=old+1;
            if(old==root.max_value){
                root.max_value--;
                for(int i=0;i<root.s.size();i++){
                    if(root.s.get(i).compareTo(str)<0 && root.val.get(i)==root.max_value){
                        root.max_s=root.s.get(i);
                        root.max_value=root.val.get(i);
                    }
                    if(root.val.get(i)>root.max_value){
                        root.max_s=root.s.get(i);
                        root.max_value=root.val.get(i);
                    }
                }
                for(int i=0;i<root.children.size();i++){
                    if(root.children.get(i).max_s.compareTo(str)<0 && root.children.get(i).max_value==root.max_value){
                        root.max_s=root.children.get(i).max_s;
                        root.max_value=root.children.get(i).max_value;
                    }
                    if(root.children.get(i).max_value>root.max_value){
                        root.max_s=root.children.get(i).max_s;
                        root.max_value=root.children.get(i).max_value;
                    }
                }
            }
            old--;
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
        root.count = 0;
        for (int i = 0; i < root.val.size(); i++) {
            root.count = root.count + root.val.get(i);
        }
        for (int i = 0; i < root.children.size(); i++) {
            root.count = root.count + root.children.get(i).count;
        }
        for (int i = 0; i < root.children.size(); i++) {
            if(root.children.get(i).s.contains(root.max_s)){
                if(root.max_value>root.children.get(i).val.get(root.children.get(i).s.indexOf(root.max_s))){
                    root.max_value--;
                    for(int j=0;j<root.s.size();j++){
                        if(root.max_value==root.val.get(j) && root.s.get(j).compareTo(root.children.get(i).max_s)<0){
                            root.max_s=root.s.get(j);
                            root.max_value=root.val.get(j);
                        }
                        if(root.max_value<root.val.get(j)){
                            root.max_s=root.s.get(j);
                            root.max_value=root.val.get(j);
                        }
                    }
                    for(int j=0;j<root.children.size();j++){
                        if(root.max_value==root.children.get(j).max_value && root.children.get(j).max_s.compareTo(root.children.get(i).max_s)<0){
                            root.max_value=root.children.get(j).max_value;
                            root.max_s=root.children.get(j).max_s;
                        }
                        if(root.max_value<root.children.get(j).max_value){
                            root.max_value=root.children.get(j).max_value;
                            root.max_s=root.children.get(j).max_s;
                        }
                    }
                }
                break;
            }
        }
        return yo;
    }
    public int decrement(String s){
        // TO be completed by students
        return decrementhelp(s, this.root);
    }

    public void buildTree(String fileName){
        //TO be completed by students
        File file = new File(fileName);
        Scanner input;
        try {
            input = new Scanner(file);
            while (input.hasNext()) {
                String word  = input.next();
                if(super.search(word)){
                    increment(word);
                }
                else{
                    insert(word);
                }
            }   
        } 
        catch (FileNotFoundException e) {
            System.out.println(e);
        }
    }
    private TreeNode commonancestor(String s1,String s2, TreeNode root){
        if(root.s.contains(s1) || root.s.contains(s2)){
            return root;
        }
        for(int i=0;i<root.s.size();i++){
            if(root.s.get(i).compareTo(s2)<0 && root.s.get(i).compareTo(s1)>0){
                return root;
            }
        }
        int count=0;
        for(int i=0;i<root.s.size();i++){
            if(s2.compareTo(root.s.get(i))>0){
                count++;
            }
        }
        return commonancestor(s1, s2, root.children.get(count));
    }
    private int s1freq(String s1,String s2,TreeNode commonNode,int freq){
        if(!commonNode.s.contains(s1) && !commonNode.s.contains(s2)){
            int yo=0;
            for(int i=commonNode.s.size()-1;i>=0;i--){
                if(commonNode.s.get(i).compareTo(s1)>0){
                    yo++;
                    freq=freq+commonNode.val.get(i);
                    freq=freq+commonNode.children.get(i+1).count;
                }
            }
            return cumulativeFreqs2node(s1, s2, commonNode.children.get(commonNode.s.size()-yo), freq);
        }
        else{
            int indexs1=commonNode.s.indexOf(s1);
            for(int i=commonNode.s.size()-1;i>=indexs1;i--){
                freq=freq+commonNode.val.get(i);
                if(commonNode.children.size()!=0){
                    freq=freq+commonNode.children.get(i+1).count;
                }
            }
            return freq;
        }
    }
    private int s2freq(String s1,String s2,TreeNode commonNode,int freq){
        if(!commonNode.s.contains(s1) && !commonNode.s.contains(s2)){
            int yo=0;
            for(int i=0;i<commonNode.s.size();i++){
                if(commonNode.s.get(i).compareTo(s2)<0){
                    yo++;
                    freq=freq+commonNode.val.get(i);
                    freq=freq+commonNode.children.get(i).count;
                }
            }
            return cumulativeFreqs1node(s1, s2, commonNode.children.get(yo), freq);
        }
        else{
            int indexs2=commonNode.s.indexOf(s2);
            for(int i=indexs2;i>=0;i--){
                freq=freq+commonNode.val.get(i);
                if(commonNode.children.size()!=0){
                    freq=freq+commonNode.children.get(i).count;
                }
            }
            return freq;
        }
    }
    private int cumulativeFreqs2node(String s1,String s2,TreeNode commonNode,int freq){
        if(commonNode.s.contains(s2)){
            int indexs2=commonNode.s.indexOf(s2);
            int yo=0;
            freq=freq+commonNode.val.get(indexs2);
            for(int i=indexs2-1;i>=0;i--){
                if(commonNode.s.get(i).compareTo(s1)>0){
                    yo++;
                    freq=freq+commonNode.val.get(i);
                    freq=freq+commonNode.children.get(i+1).count;
                }
            }
            return cumulativeFreqs2node(s1, s2, commonNode.children.get(indexs2-yo), freq);
        }
        else if(!commonNode.s.contains(s1) && !commonNode.s.contains(s2)){
            int yo=0;
            for(int i=commonNode.s.size()-1;i>=0;i--){
                if(commonNode.s.get(i).compareTo(s1)>0){
                    yo++;
                    freq=freq+commonNode.val.get(i);
                    freq=freq+commonNode.children.get(i+1).count;
                }
            }
            return cumulativeFreqs2node(s1, s2, commonNode.children.get(commonNode.s.size()-yo), freq);
        }
        else{
            int indexs1=commonNode.s.indexOf(s1);
            for(int i=commonNode.s.size()-1;i>=indexs1;i--){
                freq=freq+commonNode.val.get(i);
                if(commonNode.children.size()!=0){
                    freq=freq+commonNode.children.get(i+1).count;
                }
            }
            return freq;
        }
    }
    private int cumulativeFreqs1node(String s1,String s2,TreeNode commonNode,int freq){
        if(commonNode.s.contains(s1)){
            int indexs1=commonNode.s.indexOf(s1);
            int yo=0;
            freq=freq+commonNode.val.get(indexs1);
            for(int i=indexs1+1;i<commonNode.s.size();i++){
                if(commonNode.s.get(i).compareTo(s2)<0){
                    yo++;
                    freq=freq+commonNode.val.get(i);
                    freq=freq+commonNode.children.get(i).count;
                }
            }
            return cumulativeFreqs1node(s1, s2, commonNode.children.get(indexs1+yo+1), freq);
        }
        else if(!commonNode.s.contains(s1) && !commonNode.s.contains(s2)){
            int yo=0;
            for(int i=0;i<commonNode.s.size();i++){
                if(commonNode.s.get(i).compareTo(s2)<0){
                    yo++;
                    freq=freq+commonNode.val.get(i);
                    freq=freq+commonNode.children.get(i).count;
                }
            }
            return cumulativeFreqs1node(s1, s2, commonNode.children.get(yo), freq);
        }
        else{
            int indexs2=commonNode.s.indexOf(s2);
            for(int i=indexs2;i>=0;i--){
                freq=freq+commonNode.val.get(i);
                if(commonNode.children.size()!=0){
                    freq=freq+commonNode.children.get(i).count;
                }
            }
            return freq;
        }
    }
    public int cumulativeFreq(String s1, String s2){
        // TO be completed by students
        if (this.root == null) {
            return 0 ;
        }
        if(s1.equals(s2)){
            return super.getVal(s2);
        }
        TreeNode commonNode=commonancestor(s1, s2, this.root);
        if(commonNode.s.contains(s1) && commonNode.s.contains(s2)){
            if(commonNode.s.size()==2){
                int ans= commonNode.val.get(0)+commonNode.val.get(1);
                if(commonNode.children.size()!=0){
                    return ans+commonNode.children.get(1).count;
                }
                return ans;
            }
            if(commonNode.s.size()==3){
                if(commonNode.s.get(0).equals(s1) && commonNode.s.get(1).equals(s2)){
                    int ans=commonNode.val.get(0)+commonNode.val.get(1);
                    if(commonNode.children.size()!=0){
                        return ans+commonNode.children.get(1).count;
                    }
                    return ans;
                }
                if(commonNode.s.get(1).equals(s1) && commonNode.s.get(2).equals(s2)){
                    int ans=commonNode.val.get(1)+commonNode.val.get(2);
                    if(commonNode.children.size()!=0){
                        return ans+commonNode.children.get(2).count;
                    }
                    return ans;
                }
                if(commonNode.s.get(0).equals(s1) && commonNode.s.get(2).equals(s2)){
                    int ans= commonNode.val.get(1)+commonNode.val.get(2)+commonNode.val.get(0);
                    if(commonNode.children.size()!=0){
                        return ans+commonNode.children.get(2).count+commonNode.children.get(1).count;
                    }
                    return ans;
                }
            }
        }
        else if(commonNode.s.contains(s1)){
            return cumulativeFreqs1node(s1, s2,commonNode,0);
        }
        else if(commonNode.s.contains(s2)){
            return cumulativeFreqs2node(s1, s2, commonNode, 0);
        }
        else{
            int ans=0;
            int rtindex=0;
            for(int i=0;i<commonNode.s.size();i++){
                if(commonNode.s.get(i).compareTo(s1)<0){
                    rtindex++;
                }
            }
            int ryindex=0;
            for(int i=commonNode.s.size()-1;i>=0;i--){
                if(commonNode.s.get(i).compareTo(s2)>0){
                    ryindex++;
                }
            }
            for(int i=rtindex+1;i<=commonNode.children.size()-ryindex-2;i++){
                ans=ans+commonNode.children.get(i).count;
            }
            for(int i=rtindex;i<=commonNode.s.size()-ryindex-1;i++){
                ans=ans+commonNode.val.get(i);
            }   
            int rt=s1freq(s1,s2,commonNode.children.get(rtindex),0);
            int ry=s2freq(s1,s2,commonNode.children.get(commonNode.children.size()-ryindex-1),0);
            return rt+ry+ans;
        }
        return 0;
    }
    private String s2max(String s1,String s2,TreeNode commonNode,String maxs,int maxval){
        if(!commonNode.s.contains(s1) && !commonNode.s.contains(s2)){
            int yo=0;
            for(int i=0;i<commonNode.s.size();i++){
                if(commonNode.s.get(i).compareTo(s2)<0){
                    yo++;
                    if(maxval<commonNode.val.get(i)){
                        maxval=commonNode.val.get(i);
                        maxs=commonNode.s.get(i);
                    }
                    else if(maxval==commonNode.val.get(i)){
                        if(maxs.compareTo(commonNode.s.get(i))>0){
                            maxs=commonNode.s.get(i);
                            maxval=commonNode.val.get(i);
                        }
                    }
                    if(maxval<commonNode.children.get(i).max_value){
                        maxval=commonNode.children.get(i).max_value;
                        maxs=commonNode.children.get(i).max_s;
                    }
                    else if(maxval==commonNode.children.get(i).max_value){
                        if(maxs.compareTo(commonNode.children.get(i).max_s)>0){
                            maxval=commonNode.children.get(i).max_value;
                            maxs=commonNode.children.get(i).max_s;
                        }
                    }
                }
            }
            return s2max(s1, s2, commonNode.children.get(yo), maxs,maxval);
        }
        else{
            int indexs2=commonNode.s.indexOf(s2);
            for(int i=indexs2;i>=0;i--){
                if(maxval<commonNode.val.get(i)){
                    maxval=commonNode.val.get(i);
                    maxs=commonNode.s.get(i);
                }
                else if(maxval==commonNode.val.get(i)){
                    if(maxs.compareTo(commonNode.s.get(i))>0){
                        maxs=commonNode.s.get(i);
                        maxval=commonNode.val.get(i);
                    }
                }
                if(commonNode.children.size()!=0){
                    if(maxval<commonNode.children.get(i).max_value){
                        maxval=commonNode.children.get(i).max_value;
                        maxs=commonNode.children.get(i).max_s;
                    }
                    else if(maxval==commonNode.children.get(i).max_value){
                        if(maxs.compareTo(commonNode.children.get(i).max_s)>0){
                            maxval=commonNode.children.get(i).max_value;
                            maxs=commonNode.children.get(i).max_s;
                        }
                    }
                }
            }
            return maxs;
        }
    }
    private String s1max(String s1,String s2,TreeNode commonNode,String maxs,int maxval){
        if(!commonNode.s.contains(s1) && !commonNode.s.contains(s2)){
            int yo=0;
            for(int i=commonNode.s.size()-1;i>=0;i--){
                if(commonNode.s.get(i).compareTo(s1)>0){
                    yo++;
                    if(maxval<commonNode.val.get(i)){
                        maxval=commonNode.val.get(i);
                        maxs=commonNode.s.get(i);
                    }
                    else if(maxval==commonNode.val.get(i)){
                        if(maxs.compareTo(commonNode.s.get(i))>0){
                            maxs=commonNode.s.get(i);
                            maxval=commonNode.val.get(i);
                        }
                    }
                    if(maxval<commonNode.children.get(i+1).max_value){
                        maxval=commonNode.children.get(i+1).max_value;
                        maxs=commonNode.children.get(i+1).max_s;
                    }
                    else if(maxval==commonNode.children.get(i+1).max_value){
                        if(maxs.compareTo(commonNode.children.get(i+1).max_s)>0){
                            maxval=commonNode.children.get(i+1).max_value;
                            maxs=commonNode.children.get(i+1).max_s;
                        }
                    }
                }
            }
            return s1max(s1, s2, commonNode.children.get(commonNode.s.size()-yo), maxs,maxval);
        }
        else{
            int indexs1=commonNode.s.indexOf(s1);
            for(int i=commonNode.s.size()-1;i>=indexs1;i--){
                if(maxval<commonNode.val.get(i)){
                    maxval=commonNode.val.get(i);
                    maxs=commonNode.s.get(i);
                }
                else if(maxval==commonNode.val.get(i)){
                    if(maxs.compareTo(commonNode.s.get(i))>0){
                        maxs=commonNode.s.get(i);
                        maxval=commonNode.val.get(i);
                    }
                }
                if(commonNode.children.size()!=0){
                    if(maxval<commonNode.children.get(i+1).max_value){
                        maxval=commonNode.children.get(i+1).max_value;
                        maxs=commonNode.children.get(i+1).max_s;
                    }
                    else if(maxval==commonNode.children.get(i+1).max_value){
                        if(maxs.compareTo(commonNode.children.get(i+1).max_s)>0){
                            maxval=commonNode.children.get(i+1).max_value;
                            maxs=commonNode.children.get(i+1).max_s;
                        }
                    }
                }
            }
            return maxs;
        }
    }
    private String maxFreqs2node(String s1,String s2,TreeNode commonNode,String maxs,int maxval){
        if(commonNode.s.contains(s2)){
            int indexs2=commonNode.s.indexOf(s2);
            int yo=0;
            maxs=commonNode.s.get(indexs2);
            maxval=commonNode.val.get(indexs2);
            for(int i=indexs2-1;i>=0;i--){
                if(commonNode.s.get(i).compareTo(s1)>0){
                    yo++;
                    if(maxval<commonNode.val.get(i)){
                        maxval=commonNode.val.get(i);
                        maxs=commonNode.s.get(i);
                    }
                    else if(maxval==commonNode.val.get(i)){
                        if(maxs.compareTo(commonNode.s.get(i))>0){
                            maxs=commonNode.s.get(i);
                            maxval=commonNode.val.get(i);
                        }
                    }
                    if(maxval<commonNode.children.get(i+1).max_value){
                        maxval=commonNode.children.get(i+1).max_value;
                        maxs=commonNode.children.get(i+1).max_s;
                    }
                    else if(maxval==commonNode.children.get(i+1).max_value){
                        if(maxs.compareTo(commonNode.children.get(i+1).max_s)>0){
                            maxval=commonNode.children.get(i+1).max_value;
                            maxs=commonNode.children.get(i+1).max_s;
                        }
                    }
                }
            }
            return maxFreqs2node(s1, s2, commonNode.children.get(indexs2-yo), maxs,maxval);
        }
        else if(!commonNode.s.contains(s1) && !commonNode.s.contains(s2)){
            int yo=0;
            for(int i=commonNode.s.size()-1;i>=0;i--){
                if(commonNode.s.get(i).compareTo(s1)>0){
                    yo++;
                    if(maxval<commonNode.val.get(i)){
                        maxval=commonNode.val.get(i);
                        maxs=commonNode.s.get(i);
                    }
                    else if(maxval==commonNode.val.get(i)){
                        if(maxs.compareTo(commonNode.s.get(i))>0){
                            maxs=commonNode.s.get(i);
                            maxval=commonNode.val.get(i);
                        }
                    }
                    if(maxval<commonNode.children.get(i+1).max_value){
                        maxval=commonNode.children.get(i+1).max_value;
                        maxs=commonNode.children.get(i+1).max_s;
                    }
                    else if(maxval==commonNode.children.get(i+1).max_value){
                        if(maxs.compareTo(commonNode.children.get(i+1).max_s)>0){
                            maxval=commonNode.children.get(i+1).max_value;
                            maxs=commonNode.children.get(i+1).max_s;
                        }
                    }
                }
            }
            return maxFreqs2node(s1, s2, commonNode.children.get(commonNode.s.size()-yo), maxs,maxval);
        }
        else{
            int indexs1=commonNode.s.indexOf(s1);
            for(int i=commonNode.s.size()-1;i>=indexs1;i--){
                if(maxval<commonNode.val.get(i)){
                    maxval=commonNode.val.get(i);
                    maxs=commonNode.s.get(i);
                }
                else if(maxval==commonNode.val.get(i)){
                    if(maxs.compareTo(commonNode.s.get(i))>0){
                        maxs=commonNode.s.get(i);
                        maxval=commonNode.val.get(i);
                    }
                }
                if(commonNode.children.size()!=0){
                    if(maxval<commonNode.children.get(i+1).max_value){
                        maxval=commonNode.children.get(i+1).max_value;
                        maxs=commonNode.children.get(i+1).max_s;
                    }
                    else if(maxval==commonNode.children.get(i+1).max_value){
                        if(maxs.compareTo(commonNode.children.get(i+1).max_s)>0){
                            maxval=commonNode.children.get(i+1).max_value;
                            maxs=commonNode.children.get(i+1).max_s;
                        }
                    }
                }
            }
            return maxs;
        }
    }
    private String maxFreqs1node(String s1,String s2,TreeNode commonNode,String maxs,int maxval){
        if(commonNode.s.contains(s1)){
            int indexs1=commonNode.s.indexOf(s1);
            int yo=0;
            maxs=commonNode.s.get(indexs1);
            maxval=commonNode.val.get(indexs1);
            for(int i=indexs1+1;i<commonNode.s.size();i++){
                if(commonNode.s.get(i).compareTo(s2)<0){
                    yo++;
                    if(maxval<commonNode.val.get(i)){
                        maxval=commonNode.val.get(i);
                        maxs=commonNode.s.get(i);
                    }
                    else if(maxval==commonNode.val.get(i)){
                        if(maxs.compareTo(commonNode.s.get(i))>0){
                            maxs=commonNode.s.get(i);
                            maxval=commonNode.val.get(i);
                        }
                    }
                    if(maxval<commonNode.children.get(i).max_value){
                        maxval=commonNode.children.get(i).max_value;
                        maxs=commonNode.children.get(i).max_s;
                    }
                    else if(maxval==commonNode.children.get(i).max_value){
                        if(maxs.compareTo(commonNode.children.get(i).max_s)>0){
                            maxval=commonNode.children.get(i).max_value;
                            maxs=commonNode.children.get(i).max_s;
                        }
                    }
                }
            }
            return maxFreqs1node(s1, s2, commonNode.children.get(indexs1+yo+1), maxs,maxval);
        }
        else if(!commonNode.s.contains(s1) && !commonNode.s.contains(s2)){
            int yo=0;
            for(int i=0;i<commonNode.s.size();i++){
                if(commonNode.s.get(i).compareTo(s2)<0){
                    yo++;
                    if(maxval<commonNode.val.get(i)){
                        maxval=commonNode.val.get(i);
                        maxs=commonNode.s.get(i);
                    }
                    else if(maxval==commonNode.val.get(i)){
                        if(maxs.compareTo(commonNode.s.get(i))>0){
                            maxs=commonNode.s.get(i);
                            maxval=commonNode.val.get(i);
                        }
                    }
                    if(maxval<commonNode.children.get(i).max_value){
                        maxval=commonNode.children.get(i).max_value;
                        maxs=commonNode.children.get(i).max_s;
                    }
                    else if(maxval==commonNode.children.get(i).max_value){
                        if(maxs.compareTo(commonNode.children.get(i).max_s)>0){
                            maxval=commonNode.children.get(i).max_value;
                            maxs=commonNode.children.get(i).max_s;
                        }
                    }
                }
            }
            return maxFreqs1node(s1, s2, commonNode.children.get(yo), maxs,maxval);
        }
        else{
            int indexs2=commonNode.s.indexOf(s2);
            for(int i=indexs2;i>=0;i--){
                if(maxval<commonNode.val.get(i)){
                    maxval=commonNode.val.get(i);
                    maxs=commonNode.s.get(i);
                }
                else if(maxval==commonNode.val.get(i)){
                    if(maxs.compareTo(commonNode.s.get(i))>0){
                        maxs=commonNode.s.get(i);
                        maxval=commonNode.val.get(i);
                    }
                }
                if(commonNode.children.size()!=0){
                    if(maxval<commonNode.children.get(i).max_value){
                        maxval=commonNode.children.get(i).max_value;
                        maxs=commonNode.children.get(i).max_s;
                    }
                    else if(maxval==commonNode.children.get(i).max_value){
                        if(maxs.compareTo(commonNode.children.get(i).max_s)>0){
                            maxval=commonNode.children.get(i).max_value;
                            maxs=commonNode.children.get(i).max_s;
                        }
                    }
                }
            }
            return maxs;
        }
    }
    public String maxFreq(String s1, String s2){
        // TO be completed by students
        if (this.root == null) {
            return "";
        }
        if(s1.equals(s2)){
            return s1;
        }
        TreeNode commonNode=commonancestor(s1, s2, this.root);
        if(commonNode.s.contains(s1) && commonNode.s.contains(s2)){
            if(commonNode.s.size()==2){
                String ans;
                int ansvalue;
                if(commonNode.val.get(0)>=(commonNode.val.get(1))){
                    ans=commonNode.s.get(0);
                    ansvalue=commonNode.val.get(0);
                }
                else{
                    ans=commonNode.s.get(1);
                    ansvalue=commonNode.val.get(1);
                }
                if(commonNode.children.size()!=0){
                    if(ansvalue<commonNode.children.get(1).max_value){
                        ansvalue=commonNode.children.get(1).max_value;
                        ans=commonNode.children.get(1).max_s;
                    }
                    else if(ansvalue==commonNode.children.get(1).max_value){
                        if(ans.compareTo(commonNode.children.get(1).max_s)>0){
                            ansvalue=commonNode.children.get(1).max_value;
                            ans=commonNode.children.get(1).max_s;
                        }
                    }
                }
                return ans;
            }
            if(commonNode.s.size()==3){
                if(commonNode.s.get(0).equals(s1) && commonNode.s.get(1).equals(s2)){
                    String ans;
                    int ansvalue;
                    if(commonNode.val.get(0)>=(commonNode.val.get(1))){
                        ans=commonNode.s.get(0);
                        ansvalue=commonNode.val.get(0);
                    }
                    else{
                        ans=commonNode.s.get(1);
                        ansvalue=commonNode.val.get(1);
                    }
                    if(commonNode.children.size()!=0){
                        if(ansvalue<commonNode.children.get(1).max_value){
                            ansvalue=commonNode.children.get(1).max_value;
                            ans=commonNode.children.get(1).max_s;
                        }
                        else if(ansvalue==commonNode.children.get(1).max_value){
                            if(ans.compareTo(commonNode.children.get(1).max_s)>0){
                                ansvalue=commonNode.children.get(1).max_value;
                                ans=commonNode.children.get(1).max_s;
                            }
                        }
                    }
                    return ans;
                }
                if(commonNode.s.get(1).equals(s1) && commonNode.s.get(2).equals(s2)){
                    String ans;
                    int ansvalue;
                    if(commonNode.val.get(1)>=(commonNode.val.get(2))){
                        ans=commonNode.s.get(1);
                        ansvalue=commonNode.val.get(1);
                    }
                    else{
                        ans=commonNode.s.get(2);
                        ansvalue=commonNode.val.get(2);
                    }
                    if(commonNode.children.size()!=0){
                        if(ansvalue<commonNode.children.get(2).max_value){
                            ansvalue=commonNode.children.get(2).max_value;
                            ans=commonNode.children.get(2).max_s;
                        }
                        else if(ansvalue==commonNode.children.get(2).max_value){
                            if(ans.compareTo(commonNode.children.get(2).max_s)>0){
                                ansvalue=commonNode.children.get(2).max_value;
                                ans=commonNode.children.get(2).max_s;
                            }
                        }
                    }
                    return ans;
                }
                if(commonNode.s.get(0).equals(s1) && commonNode.s.get(2).equals(s2)){
                    String ans="";
                    int ansvalue=0;
                    for(int i=0;i<=2;i++){
                        if(ansvalue<commonNode.val.get(i)){
                            ansvalue=commonNode.val.get(i);
                            ans=commonNode.s.get(i);
                        }
                        if(ansvalue==commonNode.val.get(i)){
                            if(ans.compareTo(commonNode.s.get(i))>0){
                                ansvalue=commonNode.val.get(i);
                                ans=commonNode.s.get(i);
                            }
                        }
                    }
                    if(commonNode.children.size()!=0){
                        for(int i=1;i<=2;i++){
                            if(ansvalue<commonNode.children.get(i).max_value){
                                ansvalue=commonNode.children.get(i).max_value;
                                ans=commonNode.children.get(i).max_s;
                            }
                            if(ansvalue==commonNode.children.get(i).max_value){
                                if(ans.compareTo(commonNode.children.get(i).max_s)>0){
                                    ansvalue=commonNode.children.get(i).max_value;
                                    ans=commonNode.children.get(i).max_s;
                                }
                            }
                        }
                    }
                    return ans;
                }
            }
        }
        else if(commonNode.s.contains(s1)){
            return maxFreqs1node(s1, s2,commonNode,"",0);
        }
        else if(commonNode.s.contains(s2)){
            return maxFreqs2node(s1, s2,commonNode,"",0);
        }
        else{
            String ans="";
            int ansvalue=0;
            int rtindex=0;
            for(int i=0;i<commonNode.s.size();i++){
                if(commonNode.s.get(i).compareTo(s1)<0){
                    rtindex++;
                }
            }
            int ryindex=0;
            for(int i=commonNode.s.size()-1;i>=0;i--){
                if(commonNode.s.get(i).compareTo(s2)>0){
                    ryindex++;
                }
            }
            for(int i=rtindex+1;i<=commonNode.children.size()-ryindex-2;i++){
                if(ansvalue<commonNode.children.get(i).max_value){
                    ansvalue=commonNode.children.get(i).max_value;
                    ans=commonNode.children.get(i).max_s;
                }
                if(ansvalue==commonNode.children.get(i).max_value){
                    if(ans.compareTo(commonNode.children.get(i).max_s)>0){
                        ansvalue=commonNode.children.get(i).max_value;
                        ans=commonNode.children.get(i).max_s;
                    }
                }
            }
            for(int i=rtindex;i<=commonNode.s.size()-ryindex-1;i++){
                if(ansvalue<commonNode.val.get(i)){
                    ansvalue=commonNode.val.get(i);
                    ans=commonNode.s.get(i);
                }
                if(ansvalue==commonNode.val.get(i)){
                    if(ans.compareTo(commonNode.s.get(i))>0){
                        ansvalue=commonNode.val.get(i);
                        ans=commonNode.s.get(i);
                    }
                }
            }   
            String rt=s1max(s1,s2,commonNode.children.get(rtindex),ans,ansvalue);
            String ry=s2max(s1,s2,commonNode.children.get(commonNode.children.size()-ryindex-1),ans,ansvalue);
            if(super.getVal(rt)<super.getVal(ry)){
                return ry;
            }
            return rt;
        }
        return "";
    }
    
}