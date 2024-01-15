import java.io.BufferedReader;
import java.util.HashMap;
import java.util.TreeMap;

class ProgrammableCalculator implements ProgrammableCalculatorInterface {

    private BufferedReader reader;
    private LineReader input;
    private LinePrinter output;

    @Override
    public void programCodeReader(BufferedReader reader) {
        this.reader = reader;
    }

    @Override
    public void setStdin(LineReader input) {
        this.input = input;
    }

    @Override
    public void setStdout(LinePrinter output) {
        this.output = output;
    }

    @Override
    public void run(int line) {

        HashMap<Integer, String> lines = new HashMap<>();
        HashMap<String, Integer> variables = new HashMap<>();

        try {
            String getLine = reader.readLine();
            while (getLine != null) {

                int lineNumber = Integer.parseInt(getLine.substring(0, getLine.indexOf(' ')));
                String content = getLine.substring(getLine.indexOf(' ') + 1);
                lines.put(lineNumber, content);
                getLine = reader.readLine();

            }
        } catch (Exception e) {
        }

        TreeMap<Integer, String> sorted = new TreeMap<>();
        sorted.putAll(lines);
        Integer iterator = line;
        while (iterator != null) {

            String currentLine = lines.get(iterator);
            String tempToUpper = currentLine.toUpperCase();

            if (tempToUpper.contains("IF")) {

                String afterIf = currentLine.substring(currentLine.indexOf('F') + 2);
                String leftSide = afterIf.substring(0, afterIf.indexOf(' '));
                char operator = afterIf.charAt(afterIf.indexOf(' ') + 1);
                String afterOpeartor = afterIf.substring(afterIf.indexOf(operator) + 2);
                String rightSide = afterOpeartor.substring(0, afterOpeartor.indexOf(" "));
                boolean check = false;
                int tempLeft, tempRight;

                if (leftSide.charAt(0) < 58) {
                    tempLeft = Integer.parseInt(leftSide);
                } else {
                    tempLeft = variables.get(leftSide);
                }

                if (rightSide.charAt(0) < 58) {
                    tempRight = Integer.parseInt(rightSide);
                } else {
                    tempRight = variables.get(rightSide);
                }

                switch (operator) {
                    case '=':
                        if (tempLeft == tempRight) {
                            check = true;
                        }
                        break;
                    case '<':
                        if (tempLeft < tempRight) {
                            check = true;
                        }
                        break;
                    case '>':
                        if (tempLeft > tempRight) {
                            check = true;
                        }
                }

                if (check) {
                    String goTo = afterOpeartor.substring(rightSide.length() + 1 + 5);
                    int goToLine = Integer.parseInt(goTo);
                    iterator = goToLine;
                    continue;
                }

            } else if (tempToUpper.contains("LET")) {

                int operatorIndex = currentLine.indexOf('=');
                String variableKey = currentLine.substring(tempToUpper.indexOf('T') + 2, operatorIndex - 1);
                String afterEquals = currentLine.substring(operatorIndex + 2);

                if (!afterEquals.contains(" ")) {
                    if ((int) afterEquals.charAt(0) < 58) {
                        variables.put(variableKey, Integer.parseInt(afterEquals));
                    } else {
                        variables.put(variableKey, variables.get(afterEquals));
                    }

                } else {
                    String leftSide = afterEquals.substring(0, afterEquals.indexOf(' '));
                    char operator = afterEquals.charAt(afterEquals.indexOf(' ') + 1);
                    String rightSide = afterEquals.substring(afterEquals.indexOf(operator) + 2);
                    int tempLeft, tempRight;

                    if ((int) leftSide.charAt(0) < 58) {
                        tempLeft = Integer.parseInt(leftSide);
                    } else {
                        tempLeft = variables.get(leftSide);
                    }

                    if ((int) rightSide.charAt(0) < 58) {
                        tempRight = Integer.parseInt(rightSide);
                    } else {
                        tempRight = variables.get(rightSide);
                    }

                    switch (operator) {
                        case '+':
                            variables.put(variableKey, tempLeft + tempRight);
                            break;
                        case '-':
                            variables.put(variableKey, tempLeft - tempRight);
                            break;
                        case '*':
                            variables.put(variableKey, tempLeft * tempRight);
                            break;
                        case '/':
                            variables.put(variableKey, tempLeft / tempRight);
                    }
                }

            } else if (tempToUpper.contains("END")) {
                return;

            } else if (tempToUpper.contains("GOTO")) {

                int goToLine = Integer.parseInt(currentLine.substring(currentLine.indexOf(" ") + 1));
                iterator = goToLine;
                continue;

            } else if (tempToUpper.contains("PRINT")) {

                if (currentLine.contains("\"")) {
                    String afterFirstQuote = currentLine.substring(currentLine.indexOf('\"') + 1);
                    String toBeOutputed = afterFirstQuote.substring(0, afterFirstQuote.indexOf('\"'));
                    output.printLine(toBeOutputed);
                } else {
                    output.printLine(Integer.toString(variables.get(currentLine.substring(tempToUpper.indexOf('T') + 2))));
                }

            } else if (tempToUpper.contains("INPUT")) {

                String inputLine = input.readLine();
                String variableKey = currentLine.substring(tempToUpper.indexOf('T') + 2);
                variables.put(variableKey, Integer.parseInt(inputLine));
            }

            iterator = sorted.higherKey(iterator);
        }
    }
}