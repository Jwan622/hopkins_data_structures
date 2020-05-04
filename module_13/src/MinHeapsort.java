/**
 * This heapsort class creates a heapsort by inserting numbers and percolating them up until the heap rules are no longer
 * violated and returns the data in sorted order by repeating removing the head node and heapifying after the node removal
 */

public class MinHeapsort {
    private int[] heap;
    private int size;

    public MinHeapsort(int freqTableSize) {
        this.heap = new int[freqTableSize];
        this.size = 0;
    }

    public int[] sort(int[] data) {
        for (int datum: data) {
            insert(datum);
        }
        // create a new array for the sorted values. Have to make a copy of the size because removeMin actually decrements size.
        // the sortedSize is used to create the new array but also is used as the stopping case for the sort below.
        int sortedSize = size;
        int[] sorted = new int[sortedSize];

        // pluck the min value off from heap. use SortedSize as the stopping case.
        for(int i = 0; i < sortedSize; i++) {
            sorted[i] = removeMin();
        }

        // return sorted values
        return sorted;
    }

    private void insert(int datum) {
        heap[size] = datum;
        this.size++;
        int endOfHeap = size - 1;
        int parent = parent(endOfHeap);

        while (parent != endOfHeap && (heap[endOfHeap] < heap[parent])) {
            swap(endOfHeap, parent);

            // set endOfHeap to parent. parent becomes the parent of the old parent. We're basically percolating up the smallest value.
            endOfHeap = parent;
            parent = parent(endOfHeap);
        }
    }

    /**
     * used to just print out the heap for debugging purposes
     */
    public void printHeap() {
        for (int i = 0; i < size; i++) {
            System.out.println("data:" + heap[i]);
        }
    }

    public int min() {
        return heap[0];
    }

    /**
     * remove the head node. Used when plucking off the tree for sorting.
     * @return
     */
    public int removeMin() {
        if (size == 0) {
            throw new IllegalStateException("Min heap is empty!");
        } else if (size == 1) {
            size--;
            return min();
        }

        // remove the last item, and set it as new root
        int min = min();
        int lastItem = heap[size - 1];
        heap[0] = lastItem;
        size--;

        // bubble the root node down until heap property is maintained
        minHeapify(0);

        // return min key
        return min;
    }

    /**
     * bubble node down until heap is valid.
     * @param i the starting index to minHeapify from
     */
    private void minHeapify(int i) {
        int left = left(i);
        int right = right(i);
        int smallest;

        // find the smallest key between current node and its left child. If equal values are, do nothing
        if (left <= size - 1 && (heap[left] < heap[i])) {
            smallest = left;
        } else {
            smallest = i;
        }

        // find the smallest key between current smallest (current or left) and its right child. If equal, use tiebreakers.
        if (right <= size - 1 && (heap[right] < heap[smallest])) {
            smallest = right;
        }

        // if the smallest key is not the current key then bubble-down it and call this again recursively.
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
        int temp = heap[parent];
        heap[parent] = heap[i];
        heap[i] = temp;
    }
}
