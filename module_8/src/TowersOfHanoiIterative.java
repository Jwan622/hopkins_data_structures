import java.util.LinkedList;

/**
 * Algo for iterative solution for Towers of Hanoi
 * 
 * 1. Calculate the total number of moves required which is 2^n - 1" where n is number of disks.
 * 2. If the number of disks (again, it is n) is even, then interchange the destination pole and the spare pole.
 * 3. For i = 1 to the total number of moves:
 *      if i % 3 == 1:
 *          make the legal move of the top disk between source pole and destination pole
 *      if i % 3 == 2:
 *          make the legal move of the top disk between source pole and spare pole
 *      if i % 3 == 0:
 *         make the legal move of the top disk between spare pole and destination pole
 *
 * @author Jeffrey Wan
 */
public class TowersOfHanoiIterative {
    // global steps variable to store the algo steps
    public StringBuilder steps = new StringBuilder().append("");

    // public class for Node. The stored data is the disk number
    public class Node {
        int data;
        Node next;
    }

    // A Linked List Stack which is used to represent a Hanoi pole. This is mostly taken from lab 1
    public class LinkedListStack {
        private Node head;  // the head node
        private Node nullNode;

        public LinkedListStack() { // constructor that just sets head to null and the name
            // internal data structure to keep track of head
            this.head = null;
            this.nullNode = new Node();
            this.nullNode.data = 0;
        }

        /**
         * Pop node from the beginning of the stack. We need the null node because we need a place holder node that says the stack is empty
         * @return a node that represents a disk on the tower
         */
        public Node pop() {
            if (head == null) {
                return nullNode;
            }
            Node node = head; // we need to do this because pop returns data
            head = head.next; // set head node to head.next

            return node;
        }

        /**
         * This method is used when pushing a disk onto a poll.
         * @param data This data is the disk number
         */
        public void push(int data) {
            Node OGHead = head; // we need this because new pushed node needs to point to the OGHead (original gangsta head)
            head = new Node();
            head.data = data;
            head.next = OGHead;
        }
    }

    /**
     * This function makes the legal movement between two poles. Could be either direction depending on what the legal movement is.
     * If the source is empty, the legal move is to the source
     * If the destination is empty, the legal move is to the destination
     * If the source is greater than the destination, the legal move is to the destination
     * If the source is equal to or less than the destination, the legal move is to the source.
     * @param source the source pole
     * @param destination the destination pole
     * @param sourceName source name used for logging
     * @param destinationName destination name used for logging
     */
    public void makeLegalMoveBtwTwoPoles(LinkedListStack source, LinkedListStack destination, char sourceName, char destinationName)
    {
        int pole1NextDisk = source.pop().data;
        int pole2NextDisk = destination.pop().data;

        if (pole1NextDisk == 0) { // When source is empty, the legal move is from destination to source
            source.push(pole2NextDisk);
            logMovement(destinationName, sourceName, pole2NextDisk);
        } else if (pole2NextDisk == 0) { // When destination is empty, legal move is from source to destination
            destination.push(pole1NextDisk);
            logMovement(sourceName, destinationName, pole1NextDisk);
        } else if (pole1NextDisk > pole2NextDisk) { // When top disk of pole1 > top disk of pole2
            source.push(pole1NextDisk);
            source.push(pole2NextDisk);
            logMovement(destinationName, sourceName, pole2NextDisk);
        } else { // When top disk of pole1 < or == top disk of pole2
            destination.push(pole2NextDisk);
            destination.push(pole1NextDisk);
            logMovement(sourceName, destinationName, pole1NextDisk);
        }
    }

    /**
     * Function to show the movement of disks
     * @param sourceName the name of the source
     * @param destinationName the name of the destination
     * @param disk the disk number
     */
    private void logMovement(char sourceName, char destinationName, int disk) {
        String separator = "\n";
        steps.append("Move disk " + disk + " from tower " + sourceName + " to tower " + destinationName).append(separator);
    }

    // Function to run iterative algo to solve TowersOfHanoi game
    public StringBuilder run(int num_of_disks, char sourceName, char destinationName, char spareName) {
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
        for (int diskNumber = num_of_disks; diskNumber >= 1; diskNumber--)
            source.push(diskNumber);

        for (int move = 1; move <= total_num_of_moves; move++) {
            if (move % 3 == 1)
                makeLegalMoveBtwTwoPoles(source, destination, sourceName, destinationName);

            else if (move % 3 == 2) {
                makeLegalMoveBtwTwoPoles(source, spare, sourceName, spareName);
            } else if (move % 3 == 0)
                makeLegalMoveBtwTwoPoles(spare, destination, spareName, destinationName);
        }

        // removes the last newline of the StringBuilder
        steps.setLength(steps.length() - 1);
        return steps;
    }
}
