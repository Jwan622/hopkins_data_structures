/*
Jeffrey Wan
Class EN.605.202.81.SP20 Data Structures
Lab 2
*/

import java.time.Instant;
import java.time.Duration;
import java.io.*;

/**
 * Rules of the Towers Of Hanoi
 *  * 1) Only one disk can be moved at a time.
 *  * 2) Each move consists of taking the upper disk from one of the stacks and placing it on top of another stack i.e. a disk can only be moved if it is the uppermost disk on a stack.
 *  * 3) No disk may be placed on top of a smaller disk.
 *
 *  This class class both the recursive and iterative solutions to this game and times the algorithm
 * @author Jeffrey Wan
 * @version 1.0
 * @since 2020-03-23
 */

public class TowersOfHanoiRunner {
    private static PrintWriter outputWriter;
    private static String DELIMITER = "-----------------------------------";

    public static void main(String args[]) throws IOException {
        int numberOfDisks = Integer.parseInt(args[0]);

        // this creates an output file which will be properly formatted
        String outputFile = args[1];
        File outputf = new File(outputFile);

        //writer to the output file. Using a PrintWriter to take advantage of printf
        outputWriter = new PrintWriter(new FileWriter(outputf));

        // storage for the algorithm steps
        StringBuilder recursiveSteps;
        StringBuilder iterativeSteps;

        long start = Instant.now().toEpochMilli();
        recursiveSteps = TowersOfHanoiRecursive.run(numberOfDisks, 'A', 'B', 'C');  // the last 3 args are the names of the rods used in the game
        long finish = Instant.now().toEpochMilli();
        long timeElapsed = finish - start;
        System.out.println(recursiveSteps);
        outputWriter.println(recursiveSteps);
        System.out.println("Recursive time elapsed: " + timeElapsed);
        outputWriter.println("Recursive time elapsed: " + timeElapsed);

        System.out.println(DELIMITER);
        outputWriter.println(DELIMITER);

        start = Instant.now().toEpochMilli();
        iterativeSteps = new TowersOfHanoiIterative().run(numberOfDisks, 'A', 'B', 'C');  // the last 3 args are the names of the rods used in the game
        finish = Instant.now().toEpochMilli();
        timeElapsed = finish - start;
        System.out.println(iterativeSteps);
        outputWriter.println(iterativeSteps);
        System.out.println("Iterative time elapsed: " + timeElapsed);
        outputWriter.println("Iterative time elapsed: " + timeElapsed);


        outputWriter.close();
    }
}
