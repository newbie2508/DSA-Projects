public class LinkedList{ 
    
    public Node head;
    public Node tail;
    public LinkedList(){
        head = null;
        tail=null;
    }

    public void insert(int c){
        //to be completed by the student
        Node newnode=new Node(c);
        if(head==null){
            head=newnode;
            tail=newnode;
        }
        else{
            tail.next=newnode;
            tail=tail.next;
        }
    }

    public int len(){
        //to be completed by the student
        if(head==null){
            return 0;
        }
        Node yo=head;
        boolean ans=true;
        while(yo!=null){
            if((yo.data)!=0){
                ans=false;
            }
            yo=yo.next;
        }
        if(ans){
            head=tail;
            return 1;
        }
        Node temp=head;
        int count=0;
        while(temp!=null){
            count++;
            temp=temp.next;
        }
        return count;
    }
}



