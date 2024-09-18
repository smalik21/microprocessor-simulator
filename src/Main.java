import simulator.CPU;

public class Main {
    public static void main(String[] args) {
        CPU cpu = new CPU();

        String program = """
        MVI A 02H
        MVI B 03H
        SUB B
        MVI C 3H
        ADD C
        MVI A 2H
        STA 02H
        HLT
        """;

        cpu.parseInput(program);
        cpu.executeProgram();
        cpu.display();
    }
}