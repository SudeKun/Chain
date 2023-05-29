class DLLNode {
	private Object data;
	private DLLNode prev;
	private DLLNode next;

	public DLLNode(Object dataToAdd) {
		data = dataToAdd;
		prev = null;
		next = null;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public DLLNode getPrev() {
		return prev;
	}

	public void setPrev(DLLNode prev) {
		this.prev = prev;
	}

	public DLLNode getNext() {
		return next;
	}

	public void setNext(DLLNode next) {
		this.next = next;
	}
}

public class DoubleLinkedList {
	DLLNode head;
	DLLNode tail;

	public DoubleLinkedList() {
		head = null;
		tail = null;
	}

	public void add_back(Object dataToAdd) {
		if (head == null && tail == null) {
			DLLNode newNode = new DLLNode(dataToAdd);
			head = newNode;
			tail = newNode;
		} else {
			DLLNode newnode = new DLLNode(dataToAdd);
			newnode.setPrev(tail);
			tail.setNext(newnode);
			tail = newnode;
		}
	}

	public void add_front(Object dataToAdd) {
		if (head == null && tail == null) {
			DLLNode newNode = new DLLNode(dataToAdd);
			head = newNode;
			tail = newNode;
		} else {
			DLLNode newnode = new DLLNode(dataToAdd);
			newnode.setNext(head);
			head.setPrev(newnode);
			head = newnode;
		}
	}

	public void display() {
		if (head == null)
			System.out.println("Linked List is Empty");
		else {
			DLLNode temp = head;
			while (temp != null) {
				System.out.println(temp.getData());
				temp = temp.getNext();
			}
		}
	}

	public void delete(Object dataToDelete) {
		if (head == null)
			System.out.println("Linked List is Empty");
		else {
			while (head.getData() == dataToDelete)
				head = head.getNext();
			DLLNode temp = head;
			DLLNode previous = null;
			while (temp != null) {
				if (temp.getData() == dataToDelete) {
					previous.setNext(temp.getNext());
					temp = previous;
				}
				previous = temp;
				temp = temp.getNext();
			}
		}
	}

	public int size() {
		int count = 0;
		DLLNode temp = head;
		while (temp != null) {
			count++;
			temp = temp.getNext();
		}
		return count;
	}

	public void displaytable() {
		if (head == null)
			System.out.println("Linked List is Empty");
		else {
			DLLNode temp = head;
			while (temp != null) {
				System.out.print(temp.getData() + " " + temp.getNext().getData());
				temp = temp.getNext().getNext();
				
				System.out.println();
			}
		}
	}
}
