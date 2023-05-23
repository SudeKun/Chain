import java.util.Arrays;

public class SingleLinkedList{
    private Node head;

    public void add(Object dataToAdd){
        if(head==null){
            Node newnode= new Node(dataToAdd);
            head=newnode;
        }
        else{
            Node temp=head;
            while(temp.getLink()!=null){
                temp=temp.getLink();
            }
            Node newnode=new Node(dataToAdd);
            temp.setLink(newnode);

        }
    }

    public void delete(Object dataToDelete) {
        if(head == null) {
            System.out.println("Linked list is empty");
            return;
        }

        // Delete nodes at the beginning of the list that match dataToDelete
        while(head != null && head.getData() == (dataToDelete)) {
            head = head.getLink();
        }

        // Delete nodes in the middle or end of the list that match dataToDelete
        Node temp = head;
        Node previous = null;
        while(temp != null) {
            if(temp.getData() == dataToDelete) {
                System.out.println(" ");
                if(previous == null) { // the matching node is the first node
                    head = temp.getLink();
                } else {
                    previous.setLink(temp.getLink());
                }
            } else {
                previous = temp;
            }
            temp = temp.getLink();
        }
    }

    public void display(){
        if(head==null){
            System.out.println("linked list is empty");
        }
        else{
            Node temp=head;
            while(temp!=null){
                System.out.print(temp.getData()+" ");
                temp=temp.getLink();
            }
        }
    }

    public int size(){
        int count=0;
        if(head==null){
            System.out.println("linked list is empty");
        }

        else{
            Node temp=head;
            while(temp!=null){
                count++;
                temp=temp.getLink();
            }
        }
        return count;
    }


    public boolean isEmpty() {
        return head==null;
    }
    public Node getHead(){
        return head;
    }
    public void setHead(Node temp){
        head = temp;
    }
    public void removePlus(int[] position) {
        Node temp = head;
        while (temp.getLink() != null){
            if (temp.getData() == null ) {
                temp = temp.getLink();
                continue;
            }
            else if (temp.getData() == position ){
                temp.setData(null);
            }
            temp = temp.getLink();
        }
        if (temp.getLink() == null)
        {
            if (temp.getData() == position){
                temp.setData(null);
            }
        }
    }

}