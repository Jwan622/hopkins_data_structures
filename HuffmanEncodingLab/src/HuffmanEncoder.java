import java.io.*;
/**
 * Huffman Encoding
 *
 * - Least frequently used letters get the longest codes
 * - More frequently used letters get the shortest codes
 * - Cannot be decoded using frequency analysis
 * - Huffman encoding is a binary code, just uses 0s and 1s which avoids frequency analysis
 * - in a frequency table, if A:3, B:1, A occurs 3 times as frequently as B. Code for A would be 0, B is longer and is
 * something like 110. ASCII is 8 bits and so this encoding is shorter. ABABBA is 011001101100 which is 12 bits and ASCII
 * is 48 bits so there compression here.
 *
 * General algo for encoding from lectures:
 * 1. start greedily and the least frequent letter which would be something like B:1 or D:1.
 * 2. Turn those low frequency letters into a nodes which are leaves. label them with the letter and corresponding frequency
 * 3. Then create a parent of both nodes, with label that is combined letters and frequency.
 * 4. B:1 and D:2 are removed from freq list, BD:2 is  entered back into the freq list.
 * 5. Do 2 to 4 again until list is done. that's our binary tree from frequency nodes.
 *
 * In huffman encoding trees building, protocal for resolving ties and for deciding which to put to the left and right:
 * - put smaller values to the left.
 * - tiebreaker: put smaller letter to the left. So C:2 and BD:2, C:2 is to the left.
 * - put alphabetic order to the left. if B:1 and D:1 are left
 *
 *
 * Once you build the tree, what do you do with it? YOu can encode.
 * - assign 0s and 1st  to the branches. 1s to the right branches, 0s to the left.
 * - This allows us to follow paths from root to leaves, and get codes. Because of the way we designed the tree from the
 * frequency table, the most frequent letters are at top of tree, least frequent at the bottom.
 * - More immune to freuency analysis since the strings aren't broken up by symbols. harder to decipher because thbere are no standalone letters.
 *
 * Decoding:
 * - If you have 011001101100, you use that as path information. Traverse tree until you find a single letter.
 *
 *
 * How to get frequency table:
 * - build it from a agreed upon work like shakespeare. agree on text source.
 *
 * Algo for this project:
 * 1.  Build a min heap via a priority queue that contains 6 nodes where each node represents root of a tree with single node.
 * 2. Extract two minimum frequency nodes from min heap. Add the nodes and put back in priority queue.
 * 3. Do this until one node remains.
 *
 * Algo for printing codes from tree:
 * - Steps to print codes from Huffman Tree:
Traverse the tree formed starting from the root. Maintain an auxiliary array. While moving to the left child, write 0 to the array. While moving to the right child, write 1 to the array. Print the array when a leaf node is encountered.


 Protocol for left and right:
 - smaller values to the left first
 - if tie, tiebreakers:
    - simpler to the left. C and BD, C to the left.
    - if both simple or both complex, then alphabetic ordering to resolve tie.
 * @author Jeffrey Wan
 */
public class HuffmanEncoder {
    private static PrintWriter outputWriterCleanText;
    private static PrintWriter outputWriterEncodedText;
    private static String DELIMITER = "-----------------------------------";

    // This code is contributed by Kunwar Desh Deepak Singh
    public static void main(String args[]) throws IOException {
        // this creates an output file which will be properly formatted
        String clearTextFileName = args[0];
        File clearTextFile = new File(clearTextFileName);
        FileReader clearTextFileReader = new FileReader(clearTextFile);

        String encodedTextFileName = args[1];
        File encodedTextFile = new File(encodedTextFileName);
        FileReader encodedTextFileReader = new FileReader(encodedTextFile);

        String freqTableFileName = args[2];
        File freqTableFile = new File(freqTableFileName);
        FileReader freqTableFileReader = new FileReader(freqTableFile);

        String encodedOutputFileName = args[3];
        File encodedOutputFile = new File(encodedOutputFileName);

        String clearTextOutputFileName = args[4];
        File clearTextOutputFile = new File(clearTextOutputFileName);

        //writer to the output file. Using a PrintWriter to take advantage of printf
        outputWriterEncodedText = new PrintWriter(new FileWriter(encodedOutputFile));
        outputWriterCleanText = new PrintWriter(new FileWriter(clearTextOutputFile));
        BufferedReader freqTableBufferedReader = new BufferedReader(freqTableFileReader);
        BufferedReader clearTextBr = new BufferedReader(clearTextFileReader);
        BufferedReader encodedTextBr = new BufferedReader(encodedTextFileReader);

        // creating a priority queue q. makes a min-priority queue(min-heap).
        PriorityQueue pqueue = new PriorityQueue();

        String freqLine;
        while((freqLine = freqTableBufferedReader.readLine()) != null) {
            // creating a Huffman node object
            // and add it to the priority queue.

            String[] freqArray = freqLine.split(" ");

            // add functions adds the huffman node to the queue.
            pqueue.insert(pqueue.new PQueueNode(Integer.parseInt(freqArray[2]), freqArray[0]));
        }
        pqueue.displayList();

        // create a root node
        Tree huffmanTree = new Tree();
        // Here we will extract the two minimum value
        // from the heap each time until
        // its size reduces to 1, extract until
        // all the nodes are extracted.
        System.out.println("building tree...");
        huffmanTree.build(pqueue);
        pqueue.displayPreorder(huffmanTree.root);
        // print the codes by traversing the tree
        System.out.println(DELIMITER);
        System.out.println("Printing out huffman Tree Encoding with " + huffmanTree.root.stringify() + " as root");
        huffmanTree.printEncoding(huffmanTree.root, "");

        // close every reader.
        freqTableBufferedReader.close();
        encodedTextBr.close();
        clearTextBr.close();
    }



    /**
     * this is going to write the message to standard out and the output file
     * @param message A message to output to standard out and the file
     */
    private static void output(String message) {
        System.out.println(message);
    }
}
