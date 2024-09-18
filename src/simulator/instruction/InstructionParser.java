package simulator.instruction;

import simulator.memory.Memory;

import java.util.Map;

public class InstructionParser {

    private Memory memory;
    private int programCounter;
    private Map<String, Instruction> instructionSet;

    public InstructionParser(Memory memory, int programCounter) {
        this.memory = memory;
        this.programCounter = programCounter;
        this.instructionSet = Instruction.getPredefinedInstructions();
    }

    public void parse(String instruction) throws IllegalArgumentException {
        String[] parts = instruction.trim().split("\\s+");

        // Validate instruction type
        String type = parts[0];
        if (!instructionSet.containsKey(type)) {
            throw new IllegalArgumentException("Invalid instruction type: " + type);
        }

        Instruction predefinedInstruction = instructionSet.get(type);
        String[] operands = new String[parts.length - 1];

        // Extract operands
        if (parts.length > 1) {
            System.arraycopy(parts, 1, operands, 0, operands.length);
        }

        // Validate operand count
        if (operands.length != predefinedInstruction.getOperandCount()) {
            throw new IllegalArgumentException(
                    "Instruction " + type + " expects " + predefinedInstruction.getOperandCount() +
                            " operands, but got " + operands.length
            );
        }

        // Validate operand types (implement specific validation per type)
        validateOperands(operands, predefinedInstruction.getOperandTypes());

        for(String part: parts) {
            memory.setMemory(programCounter, part);
            programCounter++;
        }
    }

    // Function to validate operand types
    private void validateOperands(String[] operands, Instruction.OperandType[] expectedTypes) throws IllegalArgumentException {
        for (int i = 0; i < operands.length; i++) {
            switch (expectedTypes[i]) {
                case REGISTER:
                    if (!isValidRegister(operands[i])) {
                        throw new IllegalArgumentException("Invalid register operand: " + operands[i]);
                    }
                    break;
                case MEMORY:
                    if (!isValidMemoryAddress(operands[i])) {
                        throw new IllegalArgumentException("Invalid memory address: " + operands[i]);
                    }
                    break;
                case IMMEDIATE:
                    if (!isValidImmediateValue(operands[i])) {
                        throw new IllegalArgumentException("Invalid immediate value: " + operands[i]);
                    }
                    break;
            }
        }
    }

    // Placeholder function to validate register names (e.g., A, B, C, etc.)
    private boolean isValidRegister(String operand) {
        return operand.matches("[A-EH-L]");
    }

    // Placeholder function to validate memory addresses (e.g., 7000H)
    private boolean isValidMemoryAddress(String operand) {
        return operand.matches("[0-7A-Fa-f]{1,4}H");
    }

    // Placeholder function to validate immediate values (e.g., 3EH)
    private boolean isValidImmediateValue(String operand) {
        return operand.matches("[0-9A-Fa-f]{1,2}H");
    }
}
