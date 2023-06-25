public class Polynomial extends LinkedList{
    public Polynomial add(Polynomial p){
        //to be implemented by the student
        int len1=this.len();
        int len2=p.len();
        if(len1==1 && this.head.data==0){
            return p;
        }
        if(len2==1 && p.head.data==0){
            return this;
        }
        Polynomial newlist=new Polynomial();
        if(len1>=len2){
            Node p1=this.head;
            Node p2=p.head;
            int k=len1-len2;
            for(int i=0;i<=k-1;i++){
                newlist.insert(p1.data);
                p1=p1.next;
            }
            while(p1!=null && p2!=null){
                newlist.insert(p1.data+p2.data);
                p1=p1.next;
                p2=p2.next;
            }
        }
        else if(len1<len2){
            Node p1=this.head;
            Node p2=p.head;
            int k=len2-len1;
            for(int i=0;i<=k-1;i++){
                newlist.insert(p2.data);
                p2=p2.next;
            }
            while(p1!=null && p2!=null){
                newlist.insert(p1.data+p2.data);
                p1=p1.next;
                p2=p2.next;
            }
        }
        Node rat=newlist.head;
        Node newhead=newlist.head;;
        while(true){
            if(rat.data==0){
                rat=rat.next;
                if(rat!=null){
                    newhead=rat;
                }
                if(rat==null){
                    break;
                }
            }
            else{
                break;
            }
        }
        newlist.head=newhead;
        return newlist;
    }

    public Polynomial mult(Polynomial p){
        //to be implemented by the student
        int len1=this.len();
        int len2=p.len();
        if((len1==1 && this.head.data==0) || (len2==1 && p.head.data==0)){
            Polynomial yos=new Polynomial();
            yos.insert(0);
            return yos;
        }
        Polynomial output=new Polynomial();
        for(int i=0;i<=((len1-1)+(len2-1));i++){
            output.insert(0);
        }
        Node k=output.head;
        Node l=output.head;
        Node p1=this.head;
        while(p1!=null && k!=null){
            l=k;
            Node p2=p.head;
            while(p2!=null && l!=null){
                l.data=l.data+((p1.data)*(p2.data));
                p2=p2.next;
                l=l.next;
            }
            p1=p1.next;
            k=k.next;
        }
        Node rat=output.head;
        Node newhead=output.head;;
        while(true){
            if(rat.data==0){
                rat=rat.next;
                if(rat!=null){
                    newhead=rat;
                }
            }
            else{
                break;
            }
        }
        output.head=newhead;
        return output;
    }
    /*public static void main(String[] args){
        Polynomial l1=new Polynomial();
        l1.insert(-1);
        l1.insert(-1);
        l1.insert(-2);
        Polynomial l2=new Polynomial();
        l2.insert(1);
        l2.insert(1);
        l2.insert(2);
        Polynomial p3=l1.mult(l2);
        Node temp=p3.head;
        while(temp!=null){
            System.out.print(temp.data+" ");
            temp=temp.next;
        }
        System.out.println();
        Polynomial p4=l1.add(l2);
        temp=p4.head;
        while(temp!=null){
            System.out.print(temp.data+" ");
            temp=temp.next;
        }
    }*/
}