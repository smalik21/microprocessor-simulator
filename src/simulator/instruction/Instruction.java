package simulator.instruction;

import java.util.HashMap;
import java.util.Map;

public class Instruction {

    public enum OperandType {
        REGISTER, MEMORY, IMMEDIATE
    }

    private String name;
    private int operandCount;
    private OperandType[] operandTypes;

    public Instruction(String name, int operandCount, OperandType[] operandTypes) {
        this.name = name;
        this.operandCount = operandCount;
        this.operandTypes = operandTypes;
    }

    public String getName() {
        return name;
    }

    public int getOperandCount() {
        return operandCount;
    }

    public OperandType[] getOperandTypes() {
        return operandTypes;
    }

    public static Map<String, Instruction> getPredefinedInstructions() {
        Map<String, Instruction> instructionSet = new HashMap<>();

        instructionSet.put("MVI", new Instruction("MVI", 2, new OperandType[]{OperandType.REGISTER, OperandType.IMMEDIATE}));
        instructionSet.put("MOV", new Instruction("MOV", 2, new OperandType[]{OperandType.REGISTER, OperandType.REGISTER}));
        instructionSet.put("LDA", new Instruction("LDA", 1, new OperandType[]{OperandType.MEMORY}));
        instructionSet.put("STA", new Instruction("STA", 1, new OperandType[]{OperandType.MEMORY}));
        instructionSet.put("ADD", new Instruction("ADD", 1, new OperandType[]{OperandType.REGISTER}));
        instructionSet.put("SUB", new Instruction("SUB", 1, new OperandType[]{OperandType.REGISTER}));
        instructionSet.put("HLT", new Instruction("HLT", 0, new OperandType[]{}));

        return instructionSet;
    }
}
