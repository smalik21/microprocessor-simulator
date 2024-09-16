package simulator.memory;

public class Register {
    private final int[] generalPurposeRegisters;
    private int accumulator;
    private FlagRegister flagRegister;

    public Register() {
        generalPurposeRegisters = new int[6];    // B C D E H L
        accumulator = 0;
        flagRegister = new FlagRegister();
    }

    public int getAccumulator() {
        return this.accumulator;
    }

    public void setAccumulator(int value) {
        this.accumulator = value;
    }

    public int getGeneralPurpose(int index) {
        return this.generalPurposeRegisters[index];
    }

    public void setGeneralPurpose(int index, int value) {
        this.generalPurposeRegisters[index] = value;
    }

    public FlagRegister getFlagRegister() {
        return this.flagRegister;
    }
}
