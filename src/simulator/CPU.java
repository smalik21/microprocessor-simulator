package simulator;

import simulator.instruction.InstructionExecutor;
import simulator.instruction.InstructionParser;
import simulator.memory.Memory;
import simulator.memory.Register;

public class CPU {
    private Register registers;
    private Memory memory;
    private InstructionParser parser;
    private InstructionExecutor executor;
    private int programCounter;

    public CPU() {
        registers = new Register();
        memory = new Memory();
        parser = new InstructionParser();
        executor = new InstructionExecutor(memory, registers);
        programCounter = 0x8000;
    }
}
