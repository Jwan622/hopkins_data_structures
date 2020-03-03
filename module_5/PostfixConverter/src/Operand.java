/**
 *
 * This class is used to check if incoming postfix characters are operands so that they can be added to the stack.
 * Valid operands in this application are any letter, lowercase or uppercase, and number.
 * @author Jeffrey Wan
 */

public class Operand {
    public static boolean isOperand(char character) {
        return Character.isLetter(character) || Character.isDigit(character);
    }
}

/**
 *
 * Exception to indicate that Operand is invalid.
 */
class OperandException extends RuntimeException {
    public OperandException(String msg){
        super(msg);
    }
}