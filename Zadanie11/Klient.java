import java.io.*;
import java.net.*;
import java.util.*;
import java.math.BigInteger;

class Klient implements NetConnection {

    private String toBeAdded;
    private List<BigInteger> lines = new ArrayList<>();

    @Override
    public void password(String password) {
        toBeAdded = password;
    }

    @Override
    public void connect(String host, int port) {

        Socket socket = null;
        BufferedReader bufferedReader = null;
        PrintWriter printWriter = null;

        try {
            socket = new Socket(host, port);
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            printWriter = new PrintWriter(socket.getOutputStream(), true);

            String line;
            line = bufferedReader.readLine();
            line = bufferedReader.readLine();
            line = bufferedReader.readLine();

            printWriter.println("Program");

            line = bufferedReader.readLine();
            line = bufferedReader.readLine();
            line = bufferedReader.readLine();

            BigInteger sum = BigInteger.ZERO;
            while (!Objects.equals(line = bufferedReader.readLine(), "EOD")) {
                try {
                    sum = sum.add(new BigInteger(line));
                } catch (NumberFormatException e) {
                }
            }

            BigInteger passwd = new BigInteger(toBeAdded);
            sum = sum.add(passwd);

            line = bufferedReader.readLine();
            line = bufferedReader.readLine();

            int indeksO = line.indexOf('o') + 2;
            int indeksA = line.indexOf('a') - 1;
            String lineSubstring = line.substring(indeksO, indeksA);
            BigInteger serverSum = new BigInteger(lineSubstring);

            if (sum.compareTo(serverSum) == 0) {
                printWriter.println(sum);
            } else {
                printWriter.println(ODPOWIEDZ_DLA_OSZUSTA);
            }

        } catch (IOException e) {
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (printWriter != null) {
                    printWriter.close();
                }
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
            }
        }
    }
}