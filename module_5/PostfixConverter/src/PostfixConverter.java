/*
Jeffrey Wan
Class EN.605.202.81.SP20 Data Structures
Lab 1
*/

import java.io.*;

/**
 * This is the runner class which calls the other classes and reads input/writes to output. It takes in postfix expressions
 * and outputs machine instructions to an output file. The application utilizes a stack implemented using a linked list
 * to keep track of the postfix expressions and the output machine instructions.
 * Example command when running this program: java postfix.PostfixConverter inputFilename outputFilename
 * @author Jeffrey Wan
 * @version 1.0
 * @since 2020-02-28
 */

public class PostfixConverter {
    private static String POSTFIX_DELIMITER = "--------------";
    private static PrintWriter outputWriter;

    public static void main(String args[]) throws IOException, OperatorException {
        // takes the input filename from args[0] which is passed into this program via the command line
        String inputFile = args[0];
        File inputf = new File(inputFile);

        // this creates an output file which will be properly formatted
        String outputFile = args[1];
        File outputf = new File(outputFile);

        //writer to the output file. Using a PrintWriter to take advantage of printf
        outputWriter = new PrintWriter(new FileWriter(outputf));
        BufferedReader br = new BufferedReader(new FileReader(inputf));

        String postfixLine;

        // so we first check edge cases like an empty string, single operand, invalid character.
        // If it is none of those, we will iterate through the postfix expression and convert it to machine instruction
        while ((postfixLine = br.readLine()) != null) {
            // write some gaurd clauses for edge cases
            if (postfixLine.equals("")) {
                // handle empty string case
                writeToFileAndStdOut("Handling Postfix line: " + postfixLine);
                writeToFileAndStdOut("Postfix: " + postfixLine + " , is just an empty string. Doing nothing", POSTFIX_DELIMITER);
            } else if (postfixLine.length() == 1 && Operand.isOperand(postfixLine.toCharArray()[0])) {
                // handle single operand edge case
                writeToFileAndStdOut("Handling Postfix line: " + postfixLine);
                writeToFileAndStdOut("Postfix unbalanced. Too few operators", POSTFIX_DELIMITER);
            } else if (postfixLine.length() == 1 && !Operand.isOperand(postfixLine.toCharArray()[0])) {
                writeToFileAndStdOut("Handling Postfix line: " + postfixLine);
                writeToFileAndStdOut("Postfix " + postfixLine + ", is just a single invalid operand. Doing nothing", POSTFIX_DELIMITER);
            } else {
                // handle common case scenario.
                writeToFileAndStdOut("Handling Postfix line: " + postfixLine);
                Cpu cpu = new Cpu();
                char[] postfixChars = postfixLine.trim().toCharArray(); // remove leading and trailing whitespace and split
                                                                        // into char array
                try {
                    for (char c:postfixChars) {
                        String token = Character.toString(c);
                        if (new Operator(token).isOperator()) {
                            Operator operator = new Operator(token);
                            System.out.println("operator found: " + token);

                            // pop two off the stack if c is an operator. The first popped char is charA
                            cpu.handleOperator(operator);
                        } else if (Operand.isOperand(c)){
                            System.out.println("character found: " + token);
                            cpu.handleOperand(token);
                        } else {
                            throw new OperandException("Character is not a valid operand or operator: " + c);
                        }
                    }
                } catch (LinkedListEmptyException | OperatorException | OperandException exception) {
                    writeToFileAndStdOut(exception.getMessage(), POSTFIX_DELIMITER);
                    continue;
                }

                // if the linkedList that stores the postfix has more than 1 item left, then not enough operators
                if (cpu.postfixNeedsMoreOperators()) {
                    writeToFileAndStdOut("Postfix unbalanced. Too few operators", POSTFIX_DELIMITER);
                    continue;
                }
                // the instructionSequence is the machine instruction
                outputWriter.println(cpu.instructionSequence());
                System.out.println(cpu.instructionSequence());
                outputWriter.println(POSTFIX_DELIMITER);
                System.out.println(postfixLine + " converted to machine instruction and written to " + outputFile);
                System.out.println(POSTFIX_DELIMITER);
            }
        }

        outputWriter.close();
        br.close();
    };

    /**
     * This method is used to write to output and the file. It is an overloaded method that takes 2 arguments and writes
     * each one to the file and stdout
     * @param first This is the first string to write to file and stdout
     * @param second  This is the second string to write to file and stdout
     */
    public static void writeToFileAndStdOut(String first, String second) {
        // writes to stdout and file. This is an overloaded method that can write two different lines to both destinations
        outputWriter.println(first);
        System.out.println(first);
        outputWriter.println(second);
        System.out.println(second);
    }

    /**
     * This method is used to write the single argument to output and the file.
     * @param first This is the first string to write to file and stdout
     */
    public static void writeToFileAndStdOut(String first) {
        // writes to stdout and file
        outputWriter.println(first);
        System.out.println(first);
    }
}