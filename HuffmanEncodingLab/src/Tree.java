public class Tree {
    public PriorityQueue.PQueueNode root;
    public final String REVERSE = "reverse";

    public String createNewData(String first, String second) {
        return first + second;
    }

    public String sort(String input) {
        char[] arr = input.toCharArray();
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                if (arr[j] > arr[i]) {
                    char temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }
        }
        return String.copyValueOf(arr);
    }

    public void build(PriorityQueue pqueue) throws RuntimeException {
        while (pqueue.size() > 1) {
            // first min extract.
            System.out.println("first min: " +  pqueue.heap.min().data);
            PriorityQueue.PQueueNode firstMin = pqueue.remove();
            // second min extract.

            System.out.println("second min: " +  pqueue.heap.min().data);
            PriorityQueue.PQueueNode secondMin = pqueue.remove();
            // new node f which is equal
            int newIntegerData = firstMin.integerData + secondMin.integerData;
            String newData;
            PriorityQueue.PQueueNode left;
            PriorityQueue.PQueueNode right;

            if (firstMin.integerData < secondMin.integerData) {
                left = firstMin;
                right = secondMin;
                newData = sort(this.createNewData(firstMin.data, secondMin.data));
            } else if (firstMin.integerData > secondMin.integerData) {
                left = secondMin;
                right = firstMin;
                newData = sort(this.createNewData(secondMin.data, firstMin.data));
            } else {
                if ((firstMin.data.length() == 1 && secondMin.data.length() == 1) ||
                        (firstMin.data.length() > 1 && secondMin.data.length() > 1)) {
                    if (firstMin.data.compareTo(secondMin.data) < 0) {
                        left = firstMin;
                        right = secondMin;
                        newData = sort(this.createNewData(firstMin.data, secondMin.data));
                    } else {
                        right = firstMin;
                        left = secondMin;
                        newData = sort(this.createNewData(secondMin.data, firstMin.data));
                    }
                } else {
                    if (firstMin.data.length() == 1 && secondMin.data.length() > 1) {
                        left = firstMin;
                        right = secondMin;
                        newData = sort(this.createNewData(firstMin.data, secondMin.data));
                    } else {
                        right = firstMin;
                        left = secondMin;
                        newData = sort(this.createNewData(secondMin.data, firstMin.data));
                    }
                }
            }
//            if (firstMin.data.length() == 1 && secondMin.data.length() == 1) {
//                if (firstMin.data.compareTo(secondMin.data) < 0) {
//                    left = firstMin;
//                    right = secondMin;
//                    newData = this.createNewData(firstMin.data, secondMin.data);
//                } else {
//                    right = firstMin;
//                    left = secondMin;
//                    newData = this.createNewData(secondMin.data, firstMin.data);
//                }
//            } else if (firstMin.data.length() == 1 && secondMin.data.length() > 1) {
//                left = firstMin;
//                right = secondMin;
//                newData = sort(this.createNewData(firstMin.data, secondMin.data));
//            } else if (firstMin.data.length() > 1 && secondMin.data.length() == 1) {
//                right = firstMin;
//                left = secondMin;
//                newData = sort(this.createNewData(secondMin.data, firstMin.data));
//            } else {
//                if (firstMin.data.compareTo(secondMin.data) < 0) {
//                    left = firstMin;
//                    right = secondMin;
//                    newData = sort(this.createNewData(firstMin.data, secondMin.data));
//                } else if (firstMin.data.compareTo(secondMin.data) > 0) {
//                    right = firstMin;
//                    left = secondMin;
//                    newData = sort(this.createNewData(secondMin.data, firstMin.data));
//                } else {
//                    throw new RuntimeException("Two elements in priority queue cannot have same string data");
//                }
//            }
            PriorityQueue.PQueueNode newCombinedHuffmanNode = pqueue.new PQueueNode(newIntegerData, newData);
            // Assign to the combinedNode left and right children
            newCombinedHuffmanNode.leftChild = left;
            newCombinedHuffmanNode.rightChild = right;

            // mark the last combined node as the root node. used for printing the tree. Also, add this node to the priority-queue.
            this.root = newCombinedHuffmanNode;
            pqueue.insert(newCombinedHuffmanNode);
        }
    }

    // recursive function to print the huffman-code through the tree traversal. Here is the huffman - code generated.
    public void printEncoding(PriorityQueue.PQueueNode root, String huffmanCode) {
        // base case; if the left and right are null then its a leaf node and we print the code generated by
        // traversing the tree.
        if (root.leftChild == null && root.rightChild == null) {
            System.out.println(root.data + ":" + huffmanCode);
            return;
        }

        // if we go to left then add "0" to the code.
        // if we go to the right add "1" to the code.
        // recursive calls for left and
        // right sub-tree of the generated tree.
        printEncoding(root.leftChild, huffmanCode + "0");
        printEncoding(root.rightChild, huffmanCode + "1");
    }
}
