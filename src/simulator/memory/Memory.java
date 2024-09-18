package simulator.memory;

public class Memory {
    private final String[] memory;

    public Memory() {
        memory = new String[65536];
    }

    public void setMemory(int index, String value) {
        this.memory[index] = value;
    }

    public String getMemory(int index) {
        return this.memory[index];
    }
}
