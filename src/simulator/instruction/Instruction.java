package simulator.instruction;

public class Instruction {
    private final String type;
    private final String[] operands;

    public Instruction(String type, String[] operands) {
        this.type = type;
        this.operands = operands;
    }

    public String getType() {
        return this.type;
    }

    public String[] getOperands() {
        return this.operands;
    }
}
