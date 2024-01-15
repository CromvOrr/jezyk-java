public abstract class CalculatorOperations {
    static final int MEMORY_SIZE = 10;
    static final int STACK_SIZE = 10;

    public abstract void setAccumulator(int value);

    public abstract void subtractFromAccumulator(int value);

    public abstract void subtractMemoryFromAccumulator(int index);

    public abstract int getMemory(int index);

    public abstract int getAccumulator();

    public abstract void addToAccumulator(int value);

    public abstract void addMemoryToAccumulator(int index);

    public abstract void accumulatorToMemory(int index);

    public abstract void exchangeMemoryWithAccumulator(int index);

    public abstract void reset();

    public abstract void pushAccumulatorOnStack();

    public abstract void pullAccumulatorFromStack();
}