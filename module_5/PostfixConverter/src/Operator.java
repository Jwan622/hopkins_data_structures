@SuppressWarnings("FieldCanBeLocal")

/**
 *
 * This class is used to check if incoming postfix characters are operators and also helps the CPU determine which
 * machine instruction to write to the output file.
 * @author Jeffrey Wan
 */
public class Operator {
    private String operator;

    private String ADD = "+";
    private String SUBTRACT = "-";
    private String DIVISION = "/";
    private String MULTIPLY = "*";
    private String EXPONENT = "$";

    Operator(String operator) {
        this.operator = operator;
    }

    public String token() {
        // needed when the actual token itself and not the class is needed like in the file output
        return this.operator;
    }

    public boolean isAdd() {
        return this.operator.equals(ADD);
    }

    public boolean isSubtract() {
        return this.operator.equals(SUBTRACT);
    }

    public boolean isMultiply() {
        return this.operator.equals(MULTIPLY);
    }

    public boolean isDivision() {
        return this.operator.equals(DIVISION);
    }

    public boolean isExponent() { return this.operator.equals(EXPONENT); }

    public boolean isOperator() {
        // needed to check if the incoming token is an operator or not.
        return this.operator.equals(ADD) ||
                this.operator.equals(SUBTRACT) ||
                this.operator.equals(DIVISION) ||
                this.operator.equals(MULTIPLY) ||
                this.operator.equals(EXPONENT);
    }
}

/**
 *
 * Exception to indicate that Operator is invalid.
 */
class OperatorException extends RuntimeException {
    public OperatorException(String msg){
        super(msg);
    }
}