package simulator;

import simulator.instruction.InstructionExecutor;
import simulator.instruction.InstructionParser;
import simulator.memory.Memory;
import simulator.memory.Register;

import java.util.ArrayList;
import java.util.List;

public class CPU {
    private Register registers;
    private Memory memory;
    private InstructionParser parser;
    private InstructionExecutor executor;
    private int programCounter;

    public CPU() {
        reset();
    }

    public void reset() {
        programCounter = 0x8000;
        registers = new Register();
        memory = new Memory();
        parser = new InstructionParser(memory, programCounter);
        executor = new InstructionExecutor(memory, registers, programCounter);
    }


    public Memory getMemory() {
        return memory;
    }

    public Register getRegisters() {
        return registers;
    }

    public String[] processInput(String input) {
        String[] inputLines = input.split("\\r?\\n");

        List<String> programLines = new ArrayList<>();

        for(String line: inputLines) {
            String trimmedLine = line.trim();
            if(!trimmedLine.isEmpty()) {
                programLines.add(trimmedLine);
            }
        }

        return programLines.toArray(new String[0]);
    }

    public void parseInput(String input) {
        String[] processedInput = processInput(input);

        for(String inputLine: processedInput) {
            parser.parse(inputLine);
        }
    }

    public void executeProgram() {
        programCounter = 0x8000;
        executor.execute();
    }

    public void display() {
        // Displaying Register values
        System.out.println("Registers:");
        System.out.printf("A: %02X\n", registers.getAccumulator());

        char[] regNames = {'B', 'C', 'D', 'E', 'H', 'L'};
        for (int i = 0; i < regNames.length; i++) {
            System.out.printf("%s: %02X\n", regNames[i], registers.getGeneralPurpose(i));
        }

        // Displaying Flag Register
        System.out.println("Flag Register:");
        System.out.println("Sign: " + (registers.getFlagRegister().isSign() ? "1" : "0"));
        System.out.println("Zero: " + (registers.getFlagRegister().isZero() ? "1" : "0"));
        System.out.println("Auxiliary Carry: " + (registers.getFlagRegister().isAuxiliaryCarry() ? "1" : "0"));
        System.out.println("Parity: " + (registers.getFlagRegister().isParity() ? "1" : "0"));
        System.out.println("Carry: " + (registers.getFlagRegister().isCarry() ? "1" : "0"));

        // Displaying Memory values between 0x0000 and 0x8000
        System.out.println("\nMemory (non-empty locations from 0x0000 to 0x8000):");
        for (int address = 0x0000; address < 0x8000; address++) {
            String memoryValue = memory.getMemory(address);
            if (memoryValue != null && !memoryValue.isEmpty()) {
                System.out.printf("%04X: %s\n", address, memoryValue);
            }
        }
    }
}
