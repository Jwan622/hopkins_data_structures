/**
 *
 * This is a stack implemented using a linked list. It implements standard methods for a stack and also some optional
 * ones like reverse and allData. Each node contains data and a next pointer.
 * @author Jeffrey Wan
 */

public class LinkedListStack {
    private Node head;  // the head node

    // private nested class for Node
    private class Node {
        String data;
        Node next;
    }

    public LinkedListStack() { // internal data structure to keep track of head
        head = null;
    }

    // Pop node from the beginning of the stack
    public String pop() throws LinkedListEmptyException {
        if (head == null) {
            throw new LinkedListEmptyException();
        }
        String data = head.data; // we need to do this because pop returns data
        head = head.next; // set head node to head.next

        return data;
    }

    public int size() {
        Node temp = head;
        int count = 0;
        while (temp != null) {
            count++;
            temp = temp.next;
        }

        return count;
    }

    /**
     * This method is used to write to output and the file. It is an overloaded method that takes 2 arguments and writes
     * each one to the file and stdout. Remember that the first item popped is A and is passed to the operation instruction
     * remember that the second item popped is B and passed to the load instruction. We lastly increment the
     * tempVarCounter every time we set a variable so that new temp variables are used when creating the machine instruction.
     * @param data This is the first item popped off the stack
     */
    public void push(String data) {
        Node OGHead = head; // we need this because new pushed node needs to point to the OGHead (original gangsta head)
        head = new Node();
        head.data = data;
        head.next = OGHead;
    }

    /**
     *  This method iterates through the stack, gets data, and adds it to string with a new line in between
     * @return all the data in string format with a newline in between each data.
     */
    public StringBuilder allData() {
        StringBuilder sequence = new StringBuilder();
        String separator = "";
        Node temp = this.head;
        while (temp != null) {
            String instruction = temp.data;
            sequence.append(separator).append(instruction);
            separator = "\n";
            temp = temp.next; // this will iterate through the list and print out the data in each node
        }
        return sequence;
    }

    public void reverse() {
        // reverse the linked list. Needed to display all data with the top being the bottom item on the stack.
        Node current = this.head;
        Node prev = null;
        Node next = null;

        while (current != null) {
            next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        }

        this.head = prev;
    }
}

/**
 *
 * Exception to indicate that LinkedList is empty. Occurs when popping from an empty list.
 */
class LinkedListEmptyException extends RuntimeException {
    public LinkedListEmptyException() {
        // used in this class when popping a stack with no items.
        super();
    }

    public LinkedListEmptyException(String msg) {
        // used in the parent class when popping a stack with no items to add a custom message.
        super(msg);
    }
}

