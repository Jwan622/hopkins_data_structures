/**
 * This cpu class processes incoming postfix characters and maintains both stacks for postfix and the final machine instruction output.
 * @author Jeffrey Wan
 */

public class Cpu {
    private LinkedListStack llsForPostfix;
    private LinkedListStack llsForOutput;
    private int tempVarCounter = 1;

    Cpu() {
        // this linkedListStack is for processing the postfix
        this.llsForPostfix = new LinkedListStack();
        // the instruction output can also be stored on a stack until written to file. So we'll be using two stacks.
        this.llsForOutput = new LinkedListStack();
    }

    /**
     * When an operator is encountered, we should pop the postfix stack and make the necessary changes to the postfix
     * stack and the output stack (output stack holds the instructions)
     * @param operator This is the operator used to build an instruction
     */
    public void handleOperator(Operator operator) throws LinkedListEmptyException {
        try {
            String charA = this.llsForPostfix.pop();
            String charB = this.llsForPostfix.pop();
            handlePostfix();
            buildInstruction(charA, charB, operator);
        } catch (LinkedListEmptyException exception) {
            throw new LinkedListEmptyException("Stack does not have enough items, cannot execute operation: " + operator.token());
        }
    }

    public void handleOperand(String operand) {
        this.llsForPostfix.push(operand);
    }

    /**
     * This method is used to write to output and the file. It is an overloaded method that takes 2 arguments and writes
     * each one to the file and stdout. Remember that the first item popped is A and is passed to the operation instruction
     * remember that the second item popped is B and passed to the load instruction. We lastly increment the
     * tempVarCounter every time we set a variable so that new temp variables are used when creating the machine instruction.
     * @param charA This is the first item popped off the stack
     * @param charB This is the second item popped off the stack.
     * @param operator This is the operator instance that will be used to determine which machine instructions to add to
     *                 the output stack
     */
    private void buildInstruction(String charA, String charB, Operator operator) throws OperatorException {
        String tempVariable = generateTempName();

        if (operator.isAdd()) {
            llsForOutput.push("LD " + charB);
            llsForOutput.push("AD " + charA);
            llsForOutput.push("ST " + tempVariable);
            this.tempVarCounter++;
        } else if (operator.isMultiply()) {
            llsForOutput.push("LD " + charB);
            llsForOutput.push("ML " + charA);
            llsForOutput.push("ST " + tempVariable);
            this.tempVarCounter++;
        } else if (operator.isSubtract()) {
            llsForOutput.push("LD " + charB);
            llsForOutput.push("SB " + charA);
            llsForOutput.push("ST " + tempVariable);
            this.tempVarCounter++;
        } else if (operator.isDivision()) {
            llsForOutput.push("LD " + charB);
            llsForOutput.push("DV " + charA);
            llsForOutput.push("ST " + tempVariable);
            this.tempVarCounter++;
        } else if (operator.isExponent()) {
            llsForOutput.push("LD " + charB);
            llsForOutput.push("EXP " + charA);
            llsForOutput.push("ST " + tempVariable);
            this.tempVarCounter++;
        } else {
            throw new OperatorException("operator: " + operator.token() + " not supported");
        }
    }

    private void handlePostfix() {
        this.llsForPostfix.push(generateTempName());
    }

    public StringBuilder instructionSequence() {
        llsForOutput.reverse();
        StringBuilder data = llsForOutput.allData();
        // to keep this method idempotent, reverse it back to the original state so this method can be called multiple
        // times and return the same result.
        llsForOutput.reverse();
        return data;
    }

    /**
     * The callcount is incremented every time we stsore a value in a variable. This method simple appends the
     * incremented count to the TEMP string.
     * @return String
     */
    public String generateTempName() {
        return "TEMP" + Integer.toString(this.tempVarCounter);
    }

    public boolean postfixNeedsMoreOperators() {
        // if the postfix has > 1 left at the end, that means the postfix statement needs more variables.
        return this.llsForPostfix.size() > 1;
    }
}
