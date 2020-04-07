/**
 * This will be used to build the tree.
 * Implementation: The priority queue is a linked list of priority queue nodes.
 */
public class PriorityQueue {
    public class PQueueNode {
        int integerData;
        String data;
        PQueueNode next;
        public PQueueNode leftChild;
        public PQueueNode rightChild;

        public PQueueNode(int integerData, String data) {
            this.integerData = integerData;
            this.data = data;
            this.next = null;
            this.leftChild = null;
            this.rightChild = null;
        }

        public String stringify() {
            return data + ":" + integerData;
        }
    }

    private LinkList list;

    public PriorityQueue() {
        list = new LinkList();
    }

    public void insert(PQueueNode node) {
        list.insert(node);
    }

    public PQueueNode remove() {
        return list.remove();
    }

    public void displayList() {
        System.out.println("Smallest Value to Largest");
        list.display();
    }

    public void displayPreorder(PQueueNode node) {
        System.out.println("Displaying Preorder");
        list.displayPreorder(node);
    }

    public int size() {
        return list.size();
    }

    /**
     * An internal class used by PriorityQueue. this LinkedList is how PriorityQueue is implemented.
     */
    private class LinkList {
        private PQueueNode head;
        private int size;

        private LinkList() {
            this.head = null;
            this.size = 0;
        }

        /**
         * Inserts node right before the node with a higher integer data. PQueueNodes with higher frequencies are later in the list.
         * @param node
         */
        private void insert(PQueueNode node) {
            int integerData = node.integerData;
            PQueueNode previous = null;
            PQueueNode current = this.head;
            boolean newHead = true;
            while (current != null && integerData > current.integerData) {
                previous = current;
                current = current.next;
                newHead = false;
            }

            if (current == null && previous == null) {
                this.head = node;
                this.head.next = null;
            } else {
                if (newHead) {
                    this.head = node;
                } else {
                    previous.next = node;
                }
                node.next = current;
            }
            this.size++;
        }

        private PQueueNode remove() {
            if(null == head) {
                return null;
            }
            PQueueNode temp = this.head;
            head = head.next;
            // decrement size of priority queue
            this.size--;
            return temp;
        }

        private int size() {
            return this.size;
        }

        private void display() {
            PQueueNode current = this.head;

            // iterate until the end == current == null
            while (current != null) {
                System.out.println(current.stringify());
                current = current.next;
            }

            System.out.println("size of frequency pqueue: " + size());
            System.out.println("");
        }

        private void displayPreorder(PQueueNode node) {
            if (node == null) {
                return;
            }
            System.out.println(node.data + ":" + node.integerData);
            displayPreorder(node.leftChild);
            displayPreorder(node.rightChild);
        }
    }
}
