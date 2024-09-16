package simulator.memory;

public class Memory {
    private final int[] memory;

    public Memory() {
        memory = new int[65536];
    }

    public void setMemory(int index, int value) {
        this.memory[index] = value;
    }

    public int getMemory(int index) {
        return this.memory[index];
    }
}
