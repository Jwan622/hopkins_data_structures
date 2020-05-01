import java.io.*;
/**
 *  Huffman Encoding
 *
 * Some facts about the encoding:
 * - Least frequently used letters get the longest codes like Z
 * - More frequently used letters get the shortest codes like E
 * - Cannot be decoded using frequency analysis because you don't see islands of letters than might be A or I.
 * - Huffman encoding is a binary code, just uses 0s and 1s which avoids frequency analysis.
 * - In a frequency table, if A:3, B:1, A occurs 3 times as frequently as B. Code for A would be 0, B is longer and is
 * something like 110. ASCII is 8 bits and so this encoding is shorter. ABABBA is 011001101100 which is 12 bits and ASCII
 * is 48 bits so there compression here.
 *
 * General algo for encoding from lectures:
 * 1. start greedily and pop the two least frequent letters which would be something like B:1 or D:1.
 * 2. Turn those low frequency letters into a nodes which are leaves. label them with the letter and corresponding frequency
 * 3. Then create a parent of both nodes, with label that is combined letters and frequency.
 * 4. B:1 and D:2 are removed from freq list, BD:2 is  entered back into the freq list.
 * 5. Do 2 to 4 again until list is done. that's our binary tree from frequency nodes.
 * 6. Note than when inserting into the priority queue, if there's a tie in frequency, a node that is simpler in letters or
 * if equal in letter length, alphabetical node takes priority.
 *
 * In huffman encoding trees building, protocol for resolving ties and for deciding which to put to the left and right:
 * - put ndoes with smaller integer values to the left.
 * - tiebreaker: put smaller letter to the left. So C:2 and BD:2, C:2 is to the left.
 * - put alphabetic order to the left. if B:1 and D:1 are left
 * - Note: the same priorities take precedence when inserting nodes into the priority queue except they go on top and have higher priority.
 *
 * Once you build the tree, what do you do with it? You can use to encode or decode.
 * - assign 0s and 1st  to the branches. 1s to the right branches, 0s to the left.
 * - This allows us to follow paths from root to leaves, and get encoding from the letters. Because of the way we designed the
 * tree from the frequency table, the most frequent letters are at top of tree, least frequent at the bottom.
 * Decoding:
 * - If you have 011001101100, you use that as path information. Traverse tree until you find a single letter.
 *
 * @author Jeffrey Wan
 */
public class HuffmanEncoder {
    private static String DELIMITER = "-----------------------------------";
    private static final String[] CHARS_TO_IGNORE = {".", ",", "?", "!", ")", "(", "/", "$", "#", "*"};

    public static void main(String args[]) throws IOException {
        // this creates a reference to a clear text file with text to be encoded.
        String clearTextFileName = args[0];
        File clearTextFile = new File(clearTextFileName);
        FileReader clearTextFileReader = new FileReader(clearTextFile);
        BufferedReader bufferedClearTextReader = new BufferedReader(clearTextFileReader);

        // this creates a reference to an encoded file with text to be decoded into clear text.
        String encodedTextFileName = args[1];
        File encodedTextFile = new File(encodedTextFileName);
        FileReader encodedTextFileReader = new FileReader(encodedTextFile);
        BufferedReader encodedTextBufferedReader = new BufferedReader(encodedTextFileReader);

        // this creates a reference to the frequency table
        String freqTableFileName = args[2];
        File freqTableFile = new File(freqTableFileName);
        FileReader freqTableFileReaderForLineCount = new FileReader(freqTableFile);
        FileReader freqTableFileReader = new FileReader(freqTableFile);
        BufferedReader bufferedFreqTableFileReader = new BufferedReader(freqTableFileReader);
        BufferedReader bufferedFreqTableFileReaderForLineCount = new BufferedReader(freqTableFileReaderForLineCount);

        // this creates a reference to a file where the encoded clear text will be written
        String encodedOutputFileName = args[3];
        File encodedOutputFile = new File(encodedOutputFileName);

        // this creates a reference to a file where the decoded text will be written
        String clearTextOutputFileName = args[4];
        File clearTextOutputFile = new File(clearTextOutputFileName);

        // writers to the encoded and decoded output files
        PrintWriter outputWriterEncodedTextWriter = new PrintWriter(new FileWriter(encodedOutputFile));
        PrintWriter outputWriterClearTextWriter = new PrintWriter(new FileWriter(clearTextOutputFile));

        // creating a priority queue q. makes a min-priority queue(min-heap).
        int freqTableSize = 0;
        while (bufferedFreqTableFileReaderForLineCount.readLine() != null) freqTableSize++;
        PriorityQueue pqueue = new PriorityQueue(freqTableSize);

        String freqLine;
        while((freqLine = bufferedFreqTableFileReader.readLine()) != null) {
            // creating a Huffman node object
            // and add it to the priority queue.
            String[] freqArray = freqLine.split(" ");

            // add functions adds the huffman node to the queue.
            pqueue.insert(pqueue.new PQueueNode(Integer.parseInt(freqArray[2]), freqArray[0]));
        };
        // this puts everything in a heap
        pqueue.build();

        output("Building tree...");
        Tree huffmanTree = new Tree();
        // this lines builds the huffman encoding tree using the frequency table in the priority queue
        huffmanTree.build(pqueue);

        // this displays the preorder traversal
        output(DELIMITER);
        output("Displaying preorder traversal:");
        pqueue.displayPreorder();

        // print the codes by traversing the tree
        output(DELIMITER);
        output("Printing out huffman Tree Encoding with " + huffmanTree.root.stringify() + " as root");
        huffmanTree.printEncoding(huffmanTree.root, "");

        // encoding the clear text file
        output(DELIMITER);
        StringBuilder encoded = encode(huffmanTree, bufferedClearTextReader);
        output("Clear Text to Huffman Encoded: \n" + encoded, outputWriterEncodedTextWriter);

        // decode the encoded file
        output(DELIMITER);
        StringBuilder decoded = decode(huffmanTree, encodedTextBufferedReader);
        output("Huffman Decoded to Clear Text: \n" + decoded, outputWriterClearTextWriter);

        // close every reader.
        bufferedFreqTableFileReader.close();
        encodedTextBufferedReader.close();
        bufferedClearTextReader.close();
        outputWriterEncodedTextWriter.close();
        outputWriterClearTextWriter.close();
        bufferedFreqTableFileReaderForLineCount.close();
    }

    /**
     * this is an overloaded method that writes the message to standard out.
     * @param message A message to output to standard out
     */
    private static void output(String message) {
        System.out.println(message);
    }

    /**
     * this is an overloaded method that writes the message to standard out and the writer
     * @param message A message to output to standard out
     */
    private static void output(String message, PrintWriter writer) {
        System.out.println(message);
        writer.println(message);
    }

    /**
     *
     * @param huffmanTree the huffmanTree
     * @param bufferedClearTextReader the clear text reader.
     * @return
     * @throws IOException
     */
    private static StringBuilder encode(Tree huffmanTree, BufferedReader bufferedClearTextReader) throws IOException, LetterNotFound {
        String clearTextLine;
        StringBuilder finalCode = new StringBuilder();
        while((clearTextLine = bufferedClearTextReader.readLine()) != null) {
            finalCode.append(String.format("%1$35s", clearTextLine + " <encodes to>: "));
            try {
                // get rid of whitespaces
                clearTextLine = clearTextLine.replaceAll("\\s+", "");
                char[] letters = clearTextLine.toCharArray();

                // if text line is entirely ignorable characters, throw an error, handle it, and move on.
                Boolean stringHasEncodables = hasEncodables(letters);
                if (!stringHasEncodables) {
                    throw new LetterNotFound("<Not a valid code>");
                }

                outerLoopForClear:
                for (int i = 0; i < letters.length; i++) {
                    for (String character: CHARS_TO_IGNORE) {
                        if (character.equals(String.valueOf(letters[i]))) {
                            continue outerLoopForClear;
                        }
                    }

                    StringBuilder code = huffmanTree.search(huffmanTree.root, new StringBuilder(), String.valueOf(letters[i]).toUpperCase());
                    finalCode.append(code);
                }
            } catch (LetterNotFound error){
                finalCode.append(error.getMessage());
                output("<" + clearTextLine + " is not a valid string to encode. Moving onto the next line>");
            }

            finalCode.append("\n\n");
        }
        return finalCode;
    }

    private static StringBuilder decode(Tree huffmanTree, BufferedReader encodedTextBufferedReader) throws IOException {
        String encodedTextLine;
        StringBuilder finalDecoded = new StringBuilder();
        while((encodedTextLine = encodedTextBufferedReader.readLine()) != null){
            StringBuilder decoded = huffmanTree.searchForLetter(encodedTextLine.toUpperCase());
            finalDecoded.append(decoded);
        }

        return finalDecoded;
    }

    /**
     * if string is solely comprised of non-encodable characters, return false. If it has an encodable letter
     * then we can proceed and try to encode
     * @param letters the character array of letters
     * @return return true if there are encodable letters present. returns false if text line is entirely ignorable characters like ... or *.
     */
    private static Boolean hasEncodables(char[] letters) {
        boolean stringHasEncodables = false;
        Boolean[] allCheck = new Boolean[letters.length];

        for (int i = 0; i < letters.length; i++) {
            boolean found = false;
            for (String errorChar: CHARS_TO_IGNORE) {
                if (errorChar.equals(String.valueOf(letters[i]))) {
                    found = true;
                    break;
                }
            }
            allCheck[i] = found;
        }

        for (Boolean bool : allCheck) {
            if (!bool) {
                stringHasEncodables = true;
                break;
            }
        }

        return stringHasEncodables;
    }

    /**
     *
     * Exception to indicate that LinkedList is empty. Occurs when popping from an empty list.
     */
    static class LetterNotFound extends RuntimeException {
        public LetterNotFound(String msg) {
            // used in the parent class when a letter does not have an encoding
            super(msg);
        }
    }
}
