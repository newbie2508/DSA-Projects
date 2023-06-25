// import java.util.LinkedList;
// import java.util.Queue;

public class PointQuadtree {

    enum Quad {
        NW,
        NE,
        SW,
        SE
    }

    public PointQuadtreeNode root;

    public PointQuadtree() {
        this.root = null;
    }

    private boolean helpInsert(CellTower a,PointQuadtreeNode temp){
        if(temp.celltower.x == a.x && temp.celltower.y == a.y){
            return false;
        }
        if(a.x<temp.celltower.x && a.y>=temp.celltower.y){
            if(temp.quadrants[0]==null){
                PointQuadtreeNode newNode = new PointQuadtreeNode(a);
                temp.quadrants[0] = newNode;
                return true;
            }
            else{
                return helpInsert(a, temp.quadrants[0]);
            }
        }
        else if(a.x>=temp.celltower.x && a.y>temp.celltower.y){
            if(temp.quadrants[1]==null){
                PointQuadtreeNode newNode = new PointQuadtreeNode(a);
                temp.quadrants[1] = newNode;
                return true;
            }
            else{
                return helpInsert(a, temp.quadrants[1]);
            }
        }
        else if(a.x<=temp.celltower.x && a.y<temp.celltower.y){
            if(temp.quadrants[2]==null){
                PointQuadtreeNode newNode = new PointQuadtreeNode(a);
                temp.quadrants[2] = newNode;
                return true;
            }
            else{
                return helpInsert(a, temp.quadrants[2]);
            }
        }
        else if(a.x>temp.celltower.x && a.y<=temp.celltower.y){
            if(temp.quadrants[3]==null){
                PointQuadtreeNode newNode = new PointQuadtreeNode(a);
                temp.quadrants[3] = newNode;
                return true;
            }
            else{
                return helpInsert(a, temp.quadrants[3]);
            }
        }
        else{
            return false;
        }
    }

    public boolean insert(CellTower a) {
        // TO be completed by students
        if(root==null){
            root = new PointQuadtreeNode(a);
            return true;
        }
        PointQuadtreeNode temp = this.root;
        return helpInsert(a, temp);
    }

    private boolean helpSearch(int x, int y, PointQuadtreeNode temp){
        if(temp.celltower.x == x && temp.celltower.y ==y){
            return true;
        }
        if(x<temp.celltower.x && y>=temp.celltower.y){
            if(temp.quadrants[0]==null){
                return false;
            }
            else{
                return helpSearch(x,y,temp.quadrants[0]);
            }
        }
        else if(x>=temp.celltower.x && y>temp.celltower.y){
            if(temp.quadrants[1]==null){
                return false;
            }
            else{
                return helpSearch(x,y,temp.quadrants[1]);
            }
        }
        else if(x<=temp.celltower.x && y<temp.celltower.y){
            if(temp.quadrants[2]==null){
                return false;
            }
            else{
                return helpSearch(x,y,temp.quadrants[2]);
            }
        }
        else if(x>temp.celltower.x && y<=temp.celltower.y){
            if(temp.quadrants[3]==null){
                return false;
            }
            else{
                return helpSearch(x,y,temp.quadrants[3]);
            }
        }
        return false;
    }

    public boolean cellTowerAt(int x, int y) {
        // TO be completed by students
        return helpSearch(x, y, this.root);
    }

    private int find(int x, int y, PointQuadtreeNode temp){
        if(x<temp.celltower.x && y>=temp.celltower.y){
            return 0;
        }
        if(x>=temp.celltower.x && y>temp.celltower.y){
            return 1;
        }
        if(x<=temp.celltower.x && y<temp.celltower.y){
            return 2;
        }
        return 3;
    }

    private CellTower help(int x, int y, int r, PointQuadtreeNode temp, int cost){
        CellTower ans = null;
        if(temp==null){
            return ans;
        }
        if(temp.celltower.distance(x,y)<r){
            ans = temp.celltower;
            cost = ans.cost;
            CellTower ans1 = help(x,y,r,temp.quadrants[0],cost);
            if(ans1!=null && ans1.cost<ans.cost){
                ans = ans1;
                cost = ans.cost;
            }
            CellTower ans2 = help(x,y,r,temp.quadrants[1],cost);
            if(ans2!=null && ans2.cost<ans.cost){
                ans = ans2;
                cost = ans.cost;
            }
            CellTower ans3 = help(x,y,r,temp.quadrants[2],cost);
            if(ans3!=null && ans3.cost<ans.cost){
                ans = ans3;
                cost = ans.cost;
            }
            CellTower ans4 = help(x,y,r,temp.quadrants[3],cost);
            if(ans4!=null && ans4.cost<ans.cost){
                ans = ans4;
                cost = ans.cost;
            }
        }
        else if(temp.celltower.distance(x,y)>=r){
            if(temp.celltower.distance(x, y)==r){
                ans = temp.celltower;
                cost = ans.cost;
            }
            int p1 = find(x-r,y,temp);
            int p2 = find(x,y+r,temp);
            int p3 = find(x+r,y,temp);
            int p4 = find(x,y-r,temp);
            CellTower ans1 = help(x,y,r,temp.quadrants[p1],cost);
            if(ans==null){
                ans = ans1;
            }
            if(ans1!=null && ans1.cost<ans.cost){
                ans = ans1;
                cost = ans.cost;
            }
            if(p1!=p2){
                CellTower ans2 = help(x,y,r,temp.quadrants[p2],cost);
                if(ans==null){
                    ans = ans2;
                }
                if(ans2!=null && ans2.cost<ans.cost){
                    ans = ans2;
                    cost = ans.cost;
                }
            }
            if(p1!=p3 && p2!=p3){
                CellTower ans3 = help(x,y,r,temp.quadrants[p3],cost);
                if(ans==null){
                    ans = ans3;
                }
                if(ans3!=null && ans3.cost<ans.cost){
                    ans = ans3;
                    cost = ans.cost;
                }
            }
            if(p1!=p4 && p2!=p4 && p3!=p4){
                CellTower ans4 = help(x,y,r,temp.quadrants[p4],cost);
                if(ans==null){
                    ans = ans4;
                }
                if(ans4!=null && ans4.cost<ans.cost){
                    ans = ans4;
                    cost = ans.cost;
                }
            }
        }
        return ans;
    }

    public CellTower chooseCellTower(int x, int y, int r) {
        // TO be completed by students
        if(root==null){
            return null;
        }
        CellTower ans = null;
        ans = help(x,y,r,this.root,2147483647);
        return ans;
    }

    /*
    
    public static void main(String args[]){
        PointQuadtree obj = new PointQuadtree();
        CellTower c1 = new CellTower(0,0,5);
        CellTower c2 = new CellTower(-2,0,4);
        CellTower c3 = new CellTower(2,3,10);
        CellTower c4 = new CellTower(-4,6,9);
        System.out.println(obj.insert(c1));
        System.out.println(obj.insert(c2));
        System.out.println(obj.insert(c3));
        obj.printer();
        System.out.println(obj.cellTowerAt(-2,0)); // returns true
        System.out.println(obj.cellTowerAt(2,4)); // returns false
        CellTower ans = obj.chooseCellTower(0, 6, 5); // returns c3
        System.out.println(ans.x+" "+ans.y);
        System.out.println(obj.insert(c4));
        obj.printer();
        ans = obj.chooseCellTower(0, 6, 5);
        System.out.println(ans.x+" "+ans.y);
        CellTower c5 = new CellTower(-3,7,5);
        CellTower c6 = new CellTower(-3,3,4);
        CellTower c7 = new CellTower(-6,7,2);
        CellTower c8 = new CellTower(-5,4,9);
        obj.insert(c5);
        obj.insert(c6);
        obj.insert(c7);
        obj.insert(c8);
        obj.insert(c3);
        obj.printer();
        ans = obj.chooseCellTower(-2, 6, 2);
        System.out.println(ans.x+" "+ans.y);
    }
    */
}
