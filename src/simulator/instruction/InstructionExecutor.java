package simulator.instruction;

import simulator.memory.Memory;
import simulator.memory.Register;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class InstructionExecutor {

    private Memory memory;
    private Register registers;

    private Map<String, Consumer<String[]>> instructionMap;

    public InstructionExecutor(Memory memory, Register registers) {
        this.memory = memory;
        this.registers = registers;
        instructionMap = new HashMap<>();
    }

    public void execute(Instruction instruction) {
        // implement instruction execution logic
    }
}
