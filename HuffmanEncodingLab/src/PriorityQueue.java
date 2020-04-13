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

    public MinHeap heap;

    public PriorityQueue(int freqTableSize) {
        heap = new MinHeap(freqTableSize);
    }

    public void insert(PQueueNode node) {
        heap.insert(node);
    }

    public PQueueNode remove() {
        return heap.removeMin();
    }

    public void build() {
        heap.build();
    }

    public void displayPreorder() {
        System.out.println("Displaying Preorder");
        heap.displayPreorder(heap.min());
    }

    public int size() {
        return heap.size;
    }

    public class MinHeap {
        private PQueueNode[] heap;
        private int size;

        public MinHeap(int freqTableSize) {
            this.heap = new PQueueNode[freqTableSize];
            this.size = 0;
        }

        public void insert(PQueueNode node) {
            heap[size] = node;
            int endOfHeap = size() - 1;
            int parent = parent(endOfHeap);

            while ((parent != endOfHeap && (heap[endOfHeap].integerData < heap[parent(endOfHeap)].integerData)) ||
                    (parent != endOfHeap && heap[endOfHeap].integerData == heap[parent(endOfHeap)].integerData && (heap[endOfHeap].data.compareTo(heap[parent(endOfHeap)].data) < 0))) {
                swap(endOfHeap, parent);
                endOfHeap = parent;
                parent = parent(endOfHeap);
            }
            this.size++;
        }

        public void build() {
            for (int i = size() / 2; i >= 0; i--) {
                minHeapify(i);
            }
        }

        public PQueueNode min() {
            return heap[0];
        }

        public PQueueNode removeMin() {
            if (size() == 0) {
                throw new IllegalStateException("Min heap is empty!");
            } else if (size() == 1) {
                PQueueNode min = heap[0];
                size--;
                return min;
            }

            // remove the last item, and set it as new root
            PQueueNode min = heap[0];
            PQueueNode lastItem = heap[size() - 1];
            heap[0] = lastItem;
            size--;

            // bubble the root node down until heap property is maintained
            minHeapify(0);

            // return min key
            return min;
        }

        private void minHeapify(int i) {
            int left = left(i);
            int right = right(i);
            int smallest;

            // find the smallest key between current node and its left child. If equal values are, do nothing
            if (left <= size() - 1 && heap[left].integerData < heap[i].integerData) {
                smallest = left;
            } else {
                smallest = i;
            }

            // find the smallest key between current smallest (current or left) and its right child. If equal values are, do nothing
            if (right <= size() - 1 && heap[right].integerData < heap[smallest].integerData) {
                smallest = right;
            }

            // if the smallest key is not the current key then bubble-down it.
            if (smallest != i) {
                swap(i, smallest);
                minHeapify(smallest);
            }
        }

        private int right(int i) {
            return 2 * i + 2;
        }

        private int left(int i) {
            return 2 * i + 1;
        }

        private int parent(int i) {
            if (i % 2 == 1) {
                return i / 2;
            }
            return (i - 1) / 2;
        }

        private void swap(int i, int parent) {
            PQueueNode temp = heap[parent];
            heap[parent] = heap[i];
            heap[i] = temp;
        }

        public void displayPreorder(PQueueNode node) {
            if (node == null) {
                return;
            }
            System.out.println(node.data + ":" + node.integerData);
            displayPreorder(node.leftChild);
            displayPreorder(node.rightChild);
        }
    }
}