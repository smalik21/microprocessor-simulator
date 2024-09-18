package simulator.memory;

public class FlagRegister {

    private boolean sign;
    private boolean zero;
    private boolean auxiliaryCarry;
    private boolean parity;
    private boolean carry;

    public boolean isSign() {
        return sign;
    }

    public void setSign(boolean sign) {
        this.sign = sign;
    }

    public boolean isZero() {
        return zero;
    }

    public void setZero(boolean zero) {
        this.zero = zero;
    }

    public boolean isAuxiliaryCarry() {
        return auxiliaryCarry;
    }

    public void setAuxiliaryCarry(boolean auxiliaryCarry) {
        this.auxiliaryCarry = auxiliaryCarry;
    }

    public boolean isParity() {
        return parity;
    }

    public void setParity(boolean parity) {
        this.parity = parity;
    }

    public boolean isCarry() {
        return carry;
    }

    public void setCarry(boolean carry) {
        this.carry = carry;
    }

    public void updateFlags(int result) {
        result &= 0xFF;
        setSign((result & 0x80) != 0);                  // Sign bit check (MSB)
        setZero(result == 0);                           // Zero flag
        setParity(Integer.bitCount(result) % 2 == 0);   // Even parity
        // Auxiliary Carry, Carry can be updated based on specific instructions
    }
}

