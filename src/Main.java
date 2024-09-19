import simulator.CPU;
import simulator.gui.GUI;

public class Main {
    public static void main(String[] args) {
        CPU cpu = new CPU();
        new GUI(cpu);
    }
}