import java.io.BufferedReader;

public interface ProgrammableCalculatorInterface {

    public interface LineReader {
        public String readLine();
    }

    public interface LinePrinter {
        public void printLine(String line);
    }

    public void programCodeReader(BufferedReader reader);

    public void setStdin(LineReader input);

    public void setStdout(LinePrinter output);

    public void run(int line);
}