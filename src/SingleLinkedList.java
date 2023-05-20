class SLLNode{
    private Object data;
    private SLLNode link;

    public SLLNode(Object dataToAdd){
        data = dataToAdd;
        link=null;
    }

    public Object getData(){
        return data;
    }
    public void setData(Object data){
        this.data=data;
    }
    public SLLNode getLink(){
        return link;
    }
    public void setLink(SLLNode link){
        this.link =link;
    }

}

public class SingleLinkedList {
    private SLLNode head;

    public SingleLinkedList() {
        head=null;
    }

    public void add(Object dataToAdd){
        if (head == null){
            SLLNode newnode = new SLLNode(dataToAdd);
            head = newnode;
        }
        else{
            SLLNode temp = head;
            while(temp.getLink() != null) temp =temp.getLink();
            SLLNode newnode = new SLLNode(dataToAdd);
            temp.setLink(newnode);
        }
    }

    public void delete(Object dataToDelete){
        if(head==null) System.out.println("Linked List is Empty");
        else{
            while(head.getData()==dataToDelete) head=head.getLink();
            SLLNode temp = head;
            SLLNode previous =null;
            while(temp!=null){
                if(temp.getData()==dataToDelete){
                    previous.setLink(temp.getLink());
                    temp=previous;
                }
                previous=temp;
                temp=temp.getLink();
            }
        }
    }

    public void display(){
        if (head==null) System.out.println("Linked List is Empty");
        else{
            SLLNode temp=head;
            while(temp!=null){
                System.out.println(temp.getData());
                temp=temp.getLink();
            }
        }
    }

    public int size(){
        int count =0;
        if(head == null) System.out.println("Linked List is Empty");
        else {
            SLLNode temp =head;
            while(temp !=null){
                count++;
                temp=temp.getLink();
            }
        }
        return count;
    }
}
