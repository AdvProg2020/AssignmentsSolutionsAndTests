import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import static java.lang.Thread.sleep;

public class BankClient {
    private final String bankName;
    private PrintWriter out;
    private Scanner in;
    private static final String PATH = "./src/main/java/";

    public BankClient(String bankName, int dnsServerPort) throws IOException {
        this.bankName = bankName;
        Socket socket = new Socket("127.0.0.1", dnsServerPort);
        Scanner input = new Scanner(socket.getInputStream());
        PrintWriter output = new PrintWriter(socket.getOutputStream());
        int bankPort = getBankPortFromDNS(input, output);
        socket.close();
        
        Socket bankSocket = new Socket("127.0.0.1", bankPort);
        this.out = new PrintWriter(bankSocket.getOutputStream());
        this.in = new Scanner(bankSocket.getInputStream());
        out.println("Hello!");
        out.flush();
        in.nextLine();
    }

    private int getBankPortFromDNS(Scanner input, PrintWriter output) {
        output.println("ATM " + bankName);
        output.flush();
        String line = input.nextLine();
        return Integer.parseInt(line);
    }

    public void sendTransaction(int userId, int amount) {
        out.println(userId + " " + amount);
        out.flush();
        in.nextLine();
    }

    public void sendAllTransactions(String fileName, final int timeBetweenTransactions) {
        final File file = new File(PATH + fileName);
        new Thread(new Runnable() {
            public void run() {
                try {
                    Scanner myReader = new Scanner(file);
                    while (myReader.hasNextLine()) {
                        String data = myReader.nextLine();
                        String[] words = data.split(" ");
                        BankClient.this.sendTransaction(Integer.parseInt(words[0]), Integer.parseInt(words[1]));
                        if (timeBetweenTransactions > 0) {
                            try {
                                sleep(timeBetweenTransactions);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}