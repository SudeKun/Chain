class ColumnNode {
    private String Coulmn;
    private ColumnNode down;
    private LineNode right;

    public ColumnNode(String dataToAdd) {
        Coulmn = dataToAdd;
        down = null;
        right = null;
    }

    public String getCoulmn() {
        return Coulmn;
    }

    public void setCoulmn(String coulmn) {
        Coulmn = coulmn;
    }

    public ColumnNode getDown() {
        return down;
    }

    public void setDown(ColumnNode down) {
        this.down = down;
    }

    public LineNode getRight() {
        return right;
    }

    public void setRight(LineNode right) {
        this.right = right;
    }
}

class LineNode {
    private String LineCont;
    private LineNode next;

    public LineNode(String dataToAdd) {
        LineCont = dataToAdd;
        next = null;
    }

    public String getLineCont() {
        return LineCont;
    }

    public void setLineCont(String lineCont) {
        LineCont = lineCont;
    }

    public LineNode getNext() {
        return next;
    }

    public void setNext(LineNode next) {
        this.next = next;
    }
}

public class MultiLinkedList {
    private ColumnNode head;

    public void addCoulmn(String dataToAdd) {
        if (head == null) {
            ColumnNode newnode = new ColumnNode(dataToAdd);
            head = newnode;
        }

        else {
            ColumnNode temp = head;
            while (temp.getDown() != null)
                temp = temp.getDown();
            ColumnNode newnode = new ColumnNode(dataToAdd);
            temp.setDown(newnode);
        }
    }

    public void addLine(String Coulmn, String Line) {
        if (head == null) {
            System.out.println("Add coulmn before line.");
        } else {
            ColumnNode temp = head;
            while (temp != null) {
                if (Coulmn.equals(temp.getCoulmn())) {
                    LineNode temp2 = temp.getRight();
                    if (temp2 == null) {
                        LineNode newnode = new LineNode(Line);
                        temp.setRight(newnode);
                    } else {
                        while (temp2.getNext() != null)
                            temp2 = temp2.getNext();
                        LineNode newnode = new LineNode(Line);
                        temp2.setNext(newnode);
                    }
                }
                    temp = temp.getDown();
                }

        }
    }

    public void display()
    {
        if (head == null)
            System.out.println("linked list is empty ");
        else {
        ColumnNode temp = head;
        int counter = 0;
        while (temp != null)
        {
            Game.cn.getTextWindow().setCursorPosition(35,6+counter);
            //System.out.print(temp.getCoulmn()+" -> ");
            LineNode temp2 = temp.getRight();
            while (temp2.getNext() != null){
                System.out.print(temp2.getLineCont()+"+");
                temp2 = temp2.getNext();
            }
            System.out.print(temp2.getLineCont());
            temp = temp.getDown();
            counter++;
        }

        }
    }


}