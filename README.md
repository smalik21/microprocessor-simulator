# Microprocessor Simulator

This project is a simulator for a basic microprocessor, designed to help users understand the fundamental operations of a CPU. It includes a graphical user interface (GUI) for easy interaction and visualization of the processor's state.

You can find and download the executable file `microprocessor-simulator.jar` from the root folder of this repository.

## Features

- Simulates basic microprocessor operations
- GUI for code input, execution, and state visualization
- Register display
- Memory visualization
- Supports basic assembly language instructions

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 8 or higher
- Java Swing library (typically included in JDK)

### Running the Simulator

1. Compile the Java files in the project.
2. Run the `Main` class to start the simulator.

## Using the Simulator

1. The main window consists of three panels:
   - Code Editor (center)
   - Registers (left)
   - Memory (right)

2. Enter your assembly code in the Code Editor.

3. Click the "Run" button to execute the code.

4. The Register and Memory panels will update to show the current state of the processor.

5. Use the "Reset" button to clear the current state and start over.

## Supported Instructions

The simulator supports the following instructions:

- `MVI` (Move Immediate): Loads an immediate value into a register
  - Example: `MVI A 02H`

- `MOV` (Move): Copies the content of one register to another
  - Example: `MOV B A`

- `ADD`: Adds the content of a register to the accumulator
  - Example: `ADD B`

- `SUB`: Subtracts the content of a register from the accumulator
  - Example: `SUB C`

- `LDA` (Load Accumulator): Loads the accumulator with the content of a memory location
  - Example: `LDA 2000H`

- `STA` (Store Accumulator): Stores the content of the accumulator in a memory location
  - Example: `STA 2008H`

- `HLT` (Halt): Stops the execution of the program
  - Example: `HLT`

## Example Program

Here's a sample program that demonstrates the use of these instructions:

```assembly
MVI A 02H
MVI B 03H
SUB B
MVI C 05H
ADD C
STA 2008H
HLT
```

This program:
1. Loads 02H into the accumulator
2. Loads 03H into register B
3. Subtracts B from A
4. Loads 05H into register C
5. Adds C to the accumulator
6. Stores the result in memory location 2008H
7. Halts the program

## Project Structure

- `Main.java`: Entry point of the application
- `CPU.java`: Central Processing Unit implementation
- `GUI.java`: Graphical User Interface
- `Memory.java`: Memory implementation
- `Register.java`: Register implementation
- `FlagRegister.java`: Flag Register implementation
- `InstructionExecutor.java`: Executes parsed instructions
- `InstructionParser.java`: Parses input instructions
- `Instruction.java`: Defines instruction structure and types
