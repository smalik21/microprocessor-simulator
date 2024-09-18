package simulator.instruction;

import simulator.memory.Memory;
import simulator.memory.Register;

public class InstructionExecutor {

    private Memory memory;
    private Register registers;
    private int programCounter;

    public InstructionExecutor(Memory memory, Register registers, int programCounter) {
        this.memory = memory;
        this.registers = registers;
        this.programCounter = programCounter;
    }

    public void execute() {
        while (true) {
            // Fetch instruction from memory at the current program counter
            String instructionName = memory.getMemory(programCounter);

            // If instruction is null, it indicates the end of the program
            if (instructionName == null) {
                return;
//                throw new IllegalStateException("No instruction at address " + Integer.toHexString(programCounter));
            }

            // Move the program counter to the next memory location
            programCounter++;

            // Get the instruction details from the predefined instruction set
            Instruction instruction = Instruction.getPredefinedInstructions().get(instructionName);

            if (instruction == null) {
                throw new IllegalArgumentException("Unknown instruction: " + instructionName);
            }

            // Fetch the operands based on the instruction's operand count
            String[] operands = new String[instruction.getOperandCount()];
            for (int i = 0; i < instruction.getOperandCount(); i++) {
                operands[i] = memory.getMemory(programCounter);
                programCounter++;  // Move to the next operand
            }

            // Execute the instruction
            switch (instructionName) {
                case "MVI":
                    executeMVI(operands);
                    break;
                case "MOV":
                    executeMOV(operands);
                    break;
                case "ADD":
                    executeADD(operands);
                    break;
                case "SUB":
                    executeSUB(operands);
                    break;
                case "LDA":
                    executeLDA(operands);
                    break;
                case "STA":
                    executeSTA(operands);
                    break;
                case "HLT":
                    // Halt the execution
                    return;
                default:
                    throw new IllegalArgumentException("Unhandled instruction: " + instructionName);
            }
        }
    }

    private void executeMVI(String[] operands) {
        // MVI expects two operands: register and immediate value
        String register = operands[0];  // Register (e.g., "A", "B", etc.)
        String immediateValue = operands[1];  // Immediate value (e.g., "3EH")

        // Convert the immediate value to an integer
        int value = Integer.parseInt(immediateValue.replace("H", ""), 16);

        // Store the value in the specified register
        switch (register) {
            case "A":
                registers.setAccumulator(value);
                break;
            case "B":
                registers.setGeneralPurpose(0, value);
                break;
            case "C":
                registers.setGeneralPurpose(1, value);
                break;
            case "D":
                registers.setGeneralPurpose(2, value);
                break;
            case "E":
                registers.setGeneralPurpose(3, value);
                break;
            case "H":
                registers.setGeneralPurpose(4, value);
                break;
            case "L":
                registers.setGeneralPurpose(5, value);
                break;
            default:
                throw new IllegalArgumentException("Invalid register: " + register);
        }
    }

    private void executeMOV(String[] operands) {
        // MOV expects two operands: source and destination registers
        String destinationRegister = operands[0];  // Destination register (e.g., "A", "B", etc.)
        String sourceRegister = operands[1];  // Source register (e.g., "A", "B", etc.)

        // Get the value from the source register
        int value = getRegisterValue(sourceRegister);

        // Set the value in the destination register
        setRegisterValue(destinationRegister, value);
    }

    private int getRegisterValue(String register) {
        return switch (register) {
            case "A" -> registers.getAccumulator();
            case "B" -> registers.getGeneralPurpose(0);
            case "C" -> registers.getGeneralPurpose(1);
            case "D" -> registers.getGeneralPurpose(2);
            case "E" -> registers.getGeneralPurpose(3);
            case "H" -> registers.getGeneralPurpose(4);
            case "L" -> registers.getGeneralPurpose(5);
            default -> throw new IllegalArgumentException("Invalid register: " + register);
        };
    }

    private void setRegisterValue(String register, int value) {
        switch (register) {
            case "A":
                registers.setAccumulator(value);
                break;
            case "B":
                registers.setGeneralPurpose(0, value);
                break;
            case "C":
                registers.setGeneralPurpose(1, value);
                break;
            case "D":
                registers.setGeneralPurpose(2, value);
                break;
            case "E":
                registers.setGeneralPurpose(3, value);
                break;
            case "H":
                registers.setGeneralPurpose(4, value);
                break;
            case "L":
                registers.setGeneralPurpose(5, value);
                break;
            default:
                throw new IllegalArgumentException("Invalid register: " + register);
        }
    }

    private void executeADD(String[] operands) {
        // ADD expects one operand: the register to add to the accumulator
        String register = operands[0];

        // Get the value from the register
        int value = getRegisterValue(register);

        // Add it to the accumulator
        int accumulator = registers.getAccumulator();
        int result = accumulator + value;

        // Update the accumulator with the result (only keep 8 bits)
        registers.setAccumulator(result & 0xFF);

        // Update flags based on the result
        registers.getFlagRegister().updateFlags(result);

        // Update Carry flag if result is greater than 8 bits
        registers.getFlagRegister().setCarry(result > 0xFF);
    }

    private void executeSUB(String[] operands) {
        // SUB expects one operand: the register to subtract from the accumulator
        String register = operands[0];

        // Get the value from the register
        int value = getRegisterValue(register);

        // Subtract the value from the accumulator
        int accumulator = registers.getAccumulator();
        int result = accumulator - value;

        // Update the accumulator with the result (only keep 8 bits)
        registers.setAccumulator(result & 0xFF);

        // Update flags based on the result
        registers.getFlagRegister().updateFlags(result);

        // Update Carry flag for subtraction
        registers.getFlagRegister().setCarry(result < 0);
    }

    private void executeLDA(String[] operands) {
        // LDA expects one operand: memory address
        String memoryAddress = operands[0];

        // Convert memory address to integer
        int address = Integer.parseInt(memoryAddress.replace("H", ""), 16);

        // Get the value from memory and store it in the accumulator
        int value = Integer.parseInt(memory.getMemory(address).replace("H", ""), 16);
        registers.setAccumulator(value);
    }

    private void executeSTA(String[] operands) {
        // STA expects one operand: memory address
        String memoryAddress = operands[0];

        // Convert memory address to integer
        int address = Integer.parseInt(memoryAddress.replace("H", ""), 16);

        int accumulatorValue = registers.getAccumulator();
        String hexValue = String.format("%02XH", accumulatorValue);

        memory.setMemory(address, hexValue);
    }
}
