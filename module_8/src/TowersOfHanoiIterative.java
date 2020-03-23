import java.util.LinkedList;

/**
 * Algo for iterative solution for Towers of Hanoi
 * 
 * 1. Calculate the total number of moves required i.e. "pow(2, n) - 1" where n is number of disks.
 * 2. If number of disks (i.e. n) is even then interchange destination  pole and auxiliary pole.
 * 3. for i = 1 to total number of moves:
 *      if i%3 == 1:
 *          legal movement of top disk between source pole and destination pole
 *      if i%3 == 2:
 *          legal movement top disk between source pole and auxiliary pole
 *      if i%3 == 0:
 *         legal movement top disk between auxiliary pole and destination pole
 *
 * @author Jeffrey Wan
 */
public class TowersOfHanoiIterative {
    // public class for Node. The stored data is the disk numnber
    public class Node {
        int data;
        Node next;
    }

    // A Linked List Stack which is used to represent a Hanoi pole
    public class LinkedListStack {
        private Node head;  // the head node

        public LinkedListStack() { // constructor that just sets head to null and the name
            // internal data structure to keep track of head
            this.head = null;
        }

        // Pop node from the beginning of the stack
        public Node pop() throws LinkedListEmptyException {
            if (head == null) {
                throw new LinkedListEmptyException();
            }
            Node node = head; // we need to do this because pop returns data
            head = head.next; // set head node to head.next

            return node;
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

        public boolean empty() {
            return size() == 0;
        }

        /**
         * This method is used to write to output and the file. It is an overloaded method that takes 2 arguments and writes
         * each one to the file and stdout. Remember that the first item popped is A and is passed to the operation instruction
         * remember that the second item popped is B and passed to the load instruction. We lastly increment the
         * tempVarCounter every time we set a variable so that new temp variables are used when creating the machine instruction.
         * @param data This is the first item popped off the stack
         */
        public void push(int data) {
            Node OGHead = head; // we need this because new pushed node needs to point to the OGHead (original gangsta head)
            head = new Node();
            head.data = data;
            head.next = OGHead;
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
    }


    /**
     * This function makes the legal movement between two poles. Could be either direction depending on what the legal movement is.
     * @param source the source pole
     * @param destination the destination pole
     * @param sourceName source name used for logging
     * @param destinationName destination name used for logging
     */
    public void makeLegalMoveBtwTwoPoles(LinkedListStack source, LinkedListStack destination, char sourceName, char destinationName)
    {
        int pole1NextDisk = source.size();
        int pole2NextDisk = destination.size();

        if (source.empty()) { // When source is empty, the legal move is from destination to source
            source.push(pole2NextDisk);
            logMovement(destinationName, sourceName, pole2NextDisk);
        } else if (destination.empty()) { // When destination is empty, legal move is from source to destination
            destination.push(pole1NextDisk);
            logMovement(sourceName, destinationName, pole1NextDisk);
        } else if (source.size() > destination.size()) { // When top disk of pole1 > top disk of pole2
            source.push(pole1NextDisk);
            source.push(pole2NextDisk);
            logMovement(destinationName, sourceName, pole2NextDisk);
        } else { // When top disk of pole1 < or == top disk of pole2
            destination.push(pole2NextDisk);
            destination.push(pole1NextDisk);
            logMovement(sourceName, destinationName, pole1NextDisk);
        }
    }

    // Function to show the movement of disks
    private void logMovement(char source, char destination, int disk) {
        System.out.println("Move disk " + disk + " from tower " + source + " to tower " + destination);
    }

    // Function to run iterative algo to solve TowersOfHanoi game
    public void run(int num_of_disks, char sourceName, char destinationName, char spareName) {
        int diskNumber;
        int total_num_of_moves = (int) (Math.pow(2, num_of_disks) - 1); // Math.pow returns a double but we need an integer so use a cast.
        LinkedListStack source = new LinkedListStack();
        LinkedListStack destination = new LinkedListStack();
        LinkedListStack spare = new LinkedListStack();


        // If number of disks is even, then interchange destination pole and auxiliary pole
        if (num_of_disks % 2 == 0) {
            char temp = destinationName;
            destinationName = spareName;
            spareName = temp;
        }

        // Larger disks will be pushed first
        for (diskNumber = num_of_disks; diskNumber >= 1; diskNumber--)
            source.push(diskNumber);

        for (diskNumber = 1; diskNumber <= total_num_of_moves; diskNumber++) {
            if (diskNumber % 3 == 1)
                makeLegalMoveBtwTwoPoles(source, destination, sourceName, destinationName);

            else if (diskNumber % 3 == 2)
                makeLegalMoveBtwTwoPoles(source, spare, sourceName, spareName);

            else if (diskNumber % 3 == 0)
                makeLegalMoveBtwTwoPoles(spare, destination, spareName, destinationName);
        }
    }
}
