/*
Jeffrey Wan
Class EN.605.202.81.SP20 Data Structures
Lab 2
*/

import java.io.*;

/**
 * Rules of the Towers Of Hanoi
 *  * 1) Only one disk can be moved at a time.
 *  * 2) Each move consists of taking the upper disk from one of the stacks and placing it on top of another stack i.e. a disk can only be moved if it is the uppermost disk on a stack.
 *  * 3) No disk may be placed on top of a smaller disk.
 *
 *  This class is the main entry point for this Towers of Hanoi application
 * @author Jeffrey Wan
 * @version 1.0
 * @since 2020-03-23
 */
public class TowersOfHanoiRunner {
    private static PrintWriter outputWriter;
    private static FileWriter timeWriter;
    private static String DELIMITER = "-----------------------------------";

    /**
     * This method reads in the args. If you run `java TowersOfHanoiRunner 4 towers_of_hanoi_output.txt timetable.txt`,
     * The 4 is the numberOfDisks and is args[0]
     * towers_of_hanoi_output.txt is the output file name and is args[1]
     * timetable.txt is where the times for the recursive and iterative solutions will be recorded and the name is provided by args[2]
     * @param args arguments passed in from the command line.
     * @throws IOException In the event of a file error
     */
    public static void main(String args[]) throws IOException {
        int numberOfDisks = Integer.parseInt(args[0]);

        // this creates an output file which will be properly formatted
        String outputFile = args[1];
        File outputf = new File(outputFile);

        String timeFile = args[2];
        File timeFilef = new File(timeFile);

        //writer to the output file. Using a PrintWriter to take advantage of printf
        outputWriter = new PrintWriter(new FileWriter(outputf));
        timeWriter = new FileWriter(timeFilef);

        timeWriter.write(String.format("%20s %20s %20s \r\n", "NumberOfDisks", "Recursive", "Iterative"));
        // storage for the algorithm steps
        StringBuilder recursiveSteps;
        StringBuilder iterativeSteps;

        for (int i = 1; i <= numberOfDisks; i++) {
            output("Number of Disks: " + String.valueOf(i));
            long start = System.nanoTime();
            recursiveSteps = new TowersOfHanoiRecursive().run(i, 'A', 'B', 'C');  // the last 3 args are the names of the rods used in the game
            long finish = System.nanoTime();
            long timeElapsedRecursive = finish - start;
            output(recursiveSteps);
            output("Recursive time elapsed: " + timeElapsedRecursive);

            output(DELIMITER);

            output("Number of Disks: " + String.valueOf(i));
            start = System.nanoTime();
            iterativeSteps = new TowersOfHanoiIterative().run(i, 'A', 'B', 'C');  // the last 3 args are the names of the rods used in the game
            finish = System.nanoTime();
            long timeElapsedIterative = finish - start;
            output(iterativeSteps);
            output("Iterative time elapsed: " + timeElapsedIterative);

            timeWriter.write(String.format("%20s %20s %20s", i, timeElapsedRecursive, timeElapsedIterative));
            timeWriter.write("\n");
        }
        outputWriter.close();
        timeWriter.close();
    }

    /**
     * this is going to write the message to standard out and the output file
     * @param message A message to output to standard out and the file
     */
    private static void output(String message) {
        System.out.println(message);
        outputWriter.println(message);
    }

    /**
     * This is an overloaded method and is going to write the algorithm steps to standard out and the output file.
     * @param message a StringBuilder string to output to standard out and the file
     */
    private static void output(StringBuilder message) {
        System.out.println(message);
        outputWriter.println(message);
    }
}
