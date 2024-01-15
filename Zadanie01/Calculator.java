import java.util.*;

class Calculator extends CalculatorOperations {

    private int top = 0, accumulator = 0;
    private int[] stack = new int[STACK_SIZE];
    private int[] memory = new int[MEMORY_SIZE];

    @Override
    public void setAccumulator(int value) {
        accumulator = value;
    }

    @Override
    public void subtractFromAccumulator(int value) {
        accumulator -= value;
    }

    @Override
    public void subtractMemoryFromAccumulator(int index) {
        accumulator -= memory[index];
    }

    @Override
    public int getMemory(int index) {
        return memory[index];
    }

    @Override
    public int getAccumulator() {
        return accumulator;
    }

    @Override
    public void addToAccumulator(int value) {
        accumulator += value;
    }

    @Override
    public void addMemoryToAccumulator(int index) {
        accumulator += memory[index];
    }

    @Override
    public void accumulatorToMemory(int index) {
        memory[index] = accumulator;
    }

    @Override
    public void exchangeMemoryWithAccumulator(int index) {
        int temp = accumulator;
        accumulator = memory[index];
        memory[index] = temp;
    }

    @Override
    public void reset() {
        accumulator = 0;
        Arrays.fill(memory, 0);
        Arrays.fill(stack, 0);
    }

    @Override
    public void pushAccumulatorOnStack() {
        stack[top] = accumulator;
        if (top < STACK_SIZE - 1) {
            top++;
        }
    }

    @Override
    public void pullAccumulatorFromStack() {
        accumulator = stack[top];
        stack[top] = 0;
        if (top != 0) {
            top--;
        }
    }
}