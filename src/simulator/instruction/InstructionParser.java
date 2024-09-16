package simulator.instruction;

public class InstructionParser {

    public Instruction parse(String instruction) {
        String[] parts = instruction.split(" ");
        String type = parts[0];
        String[] operands;

        if(parts.length > 1) {
            operands = new String[parts.length - 1];
            System.arraycopy(parts, 1, operands, 0, operands.length);
        }
        else {
            operands = new String[0];
        }

        return new Instruction(type, operands);
    }
}
