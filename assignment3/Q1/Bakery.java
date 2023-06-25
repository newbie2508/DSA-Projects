import java.util.*;

public class Bakery {

    static int help(int num,SkipList skiplistobj){
        SkipListNode current = skiplistobj.head;
        for(int i=skiplistobj.height-1;i>=0;i--){
            while(current.next.get(i)!=skiplistobj.tail && current.next.get(i).value<=num){
                current = current.next.get(i);
            }
            if(current.value==skiplistobj.tail.value){
                return skiplistobj.tail.value;
            }
        }
        return current.next.get(0).value;
    }
    static int solve(ArrayList<Integer> cakes){
        // TO be completed by students
        SkipList skiplistobj = new SkipList();
        int count = 0;
        for(int i=0;i<cakes.size();i++){
            int upbnd = help(cakes.get(i),skiplistobj);
            if(upbnd!=skiplistobj.tail.value){
                skiplistobj.delete(upbnd);
                skiplistobj.insert(cakes.get(i));
            }
            else{
                skiplistobj.insert(cakes.get(i));
                count++;
            }
        }
        return count;
    }

    
}
