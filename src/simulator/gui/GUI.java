package simulator.gui;

import simulator.CPU;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame {

    private JTextArea codeEditor;
    private JPanel memoryDisplay;
    private JLabel[] registerBlocks;
    private final CPU cpu;

    public GUI(CPU cpu) {
        this.cpu = cpu;
        initComponents();
    }

    Font mainFont = new Font("Arial", Font.PLAIN, 16);

    private void initComponents() {
        // Setting up the main window
        setTitle("Microprocessor Simulator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLayout(new BorderLayout(20, 20));  // Added 10px gap between the components

        // Set padding for the main content
        JPanel contentPane = new JPanel(new BorderLayout(20, 20));  // Add padding between panels
        contentPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));  // Padding from window borders
        setContentPane(contentPane);

        // Code editor panel
        JPanel codeEditorPanel = new JPanel(new BorderLayout());
        codeEditorPanel.setBorder(new TitledBorder("Code Editor"));
        codeEditor = new JTextArea();
        codeEditor.setBorder(new CompoundBorder(
                codeEditor.getBorder(),
                new EmptyBorder(10, 10, 10, 10)
        ));
        codeEditor.setFont(mainFont);
        codeEditor.setText("""
           MVI A 02H\s
           MVI B 03H\s
           SUB B\s
           MVI C 05H\s
           ADD C\s
           STA 2008H\s
           HLT\s
           \s""");
        JScrollPane codeEditorScrollPane = new JScrollPane(codeEditor);
        codeEditorPanel.add(codeEditorScrollPane, BorderLayout.CENTER);
        contentPane.add(codeEditorPanel, BorderLayout.CENTER);

        // Register panel with individual register blocks
        JPanel registerPanel = new JPanel(new GridLayout(0, 2, 10, 10)); // 2 columns for label and value, added gap
        registerPanel.setBorder(new TitledBorder("Registers"));
        registerPanel.setFont(mainFont);
        registerBlocks = new JLabel[7]; // A, B, C, D, E, H, L

        String[] regNames = {"Accumulator", "B", "C", "D", "E", "H", "L"};

        for (int i = 0; i < regNames.length; i++) {
            JLabel label = new JLabel(regNames[i]);
            label.setFont(mainFont);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            registerPanel.add(label);
            registerBlocks[i] = new JLabel("00H", SwingConstants.CENTER); // Initialize as 0
            registerBlocks[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
            registerBlocks[i].setOpaque(true);
            registerBlocks[i].setPreferredSize(new Dimension(30, 20));
            registerPanel.add(registerBlocks[i]);
        }
        contentPane.add(registerPanel, BorderLayout.WEST);

        // Memory panel with fixed height and top alignment
        JPanel memoryPanel = new JPanel();
        memoryPanel.setBorder(new TitledBorder("Memory"));
        memoryPanel.setLayout(new BorderLayout());
        memoryPanel.setPreferredSize(new Dimension(200, 400)); // Set fixed height and width

        memoryDisplay = new JPanel();
        memoryDisplay.setLayout(new BoxLayout(memoryDisplay, BoxLayout.Y_AXIS)); // Align blocks vertically
        JScrollPane memoryScrollPane = new JScrollPane(memoryDisplay);
        memoryPanel.add(memoryScrollPane, BorderLayout.CENTER);

        contentPane.add(memoryPanel, BorderLayout.EAST);


        // Buttons panel (Run, Reset)
        JPanel buttonPanel = new JPanel();
        JButton runButton = new JButton("Run");
        JButton resetButton = new JButton("Reset");
        buttonPanel.add(runButton);
        buttonPanel.add(resetButton);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        // Event listeners
        runButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                runProgram();
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetProgram();
            }
        });

        // Show window
        setVisible(true);
    }

    private void clearMemoryDisplay() {
        memoryDisplay.removeAll();
        memoryDisplay.revalidate();
        memoryDisplay.repaint();
    }

    // Method to run the assembly code
    private void runProgram() {
        cpu.reset();
        String program = codeEditor.getText();
        cpu.parseInput(program);
        cpu.executeProgram();
        updateDisplays();
    }

    // Method to reset the program
    private void resetProgram() {
        codeEditor.setText("");
        cpu.reset();
        updateDisplays();
    }

    // Method to update both register and memory displays after execution
    private void updateDisplays() {
        updateRegisterDisplay();
        updateMemoryDisplay();
    }

    // Method to update the register display
    private void updateRegisterDisplay() {
        // Update the values in the register blocks after execution
        registerBlocks[0].setText(String.format("%02XH", cpu.getRegisters().getAccumulator())); // Accumulator

        // Display individual B, C, D, E, H, L registers
        registerBlocks[1].setText(String.format("%02XH", cpu.getRegisters().getGeneralPurpose(0))); // B
        registerBlocks[2].setText(String.format("%02XH", cpu.getRegisters().getGeneralPurpose(1))); // C
        registerBlocks[3].setText(String.format("%02XH", cpu.getRegisters().getGeneralPurpose(2))); // D
        registerBlocks[4].setText(String.format("%02XH", cpu.getRegisters().getGeneralPurpose(3))); // E
        registerBlocks[5].setText(String.format("%02XH", cpu.getRegisters().getGeneralPurpose(4))); // H
        registerBlocks[6].setText(String.format("%02XH", cpu.getRegisters().getGeneralPurpose(5))); // L
    }

    // Method to update the memory display (in blocks)
    private void updateMemoryDisplay() {
        clearMemoryDisplay(); // Clear previous memory blocks

        for (int address = 0x0000; address < 0x8000; address++) {
            String value = cpu.getMemory().getMemory(address);
            if (value != null && !value.isEmpty()) {
                JPanel memoryBlock = new JPanel();
                memoryBlock.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                memoryBlock.setPreferredSize(new Dimension(60, 30)); // Set a consistent size for blocks
                memoryBlock.setLayout(new GridLayout(1, 2));

                JLabel addressLabel = new JLabel(String.format("%04XH", address), SwingConstants.CENTER);
                JLabel valueLabel = new JLabel(value, SwingConstants.CENTER);

                // Apply the main font to the labels
                addressLabel.setFont(mainFont);
                valueLabel.setFont(mainFont);

                memoryBlock.add(addressLabel, BorderLayout.WEST);
                memoryBlock.add(valueLabel, BorderLayout.CENTER);

                memoryDisplay.add(memoryBlock);
            }
        }
        memoryDisplay.revalidate(); // Revalidate to ensure the layout is updated
        memoryDisplay.repaint(); // Force repaint to reflect changes
    }

}
