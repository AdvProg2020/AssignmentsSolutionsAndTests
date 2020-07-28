import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;

public class BankServer {
    private final HashMap<Integer, Integer> accounts = new HashMap<Integer, Integer>();
    private int numberOfClients = 0;
    private String bankName;
    private ServerSocket serverSocket;

    public BankServer(String bankName, int dnsPort) throws IOException {
        this.bankName = bankName;
        Socket socket = new Socket("127.0.0.1", dnsPort);
        Scanner in = new Scanner(socket.getInputStream());
        PrintWriter out = new PrintWriter(socket.getOutputStream());
        this.serverSocket = new ServerSocket(0);
        registerPortInDNS(in, out);
        new Thread(new Runnable() {
            public void run() {
                while (true) {
                    try {
                        final Socket client = serverSocket.accept();
                        new Thread(new Runnable() {
                            public void run() {
                                BankServer.this.handle(client);
                            }
                        }).start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void registerPortInDNS(Scanner in, PrintWriter out) {
        out.println("bank " + bankName + " " + serverSocket.getLocalPort());
        out.flush();
        in.nextLine();
    }

    public int getBalance(int userId) {
        synchronized (accounts) {
            if (accounts.containsKey(userId))
                return accounts.get(userId);
        }
        return 0;
    }

    public int getNumberOfConnectedClients() {
        synchronized (this) {
            return numberOfClients;
        }
    }

    private void handle(Socket socket) {
        try  {
            Scanner in = new Scanner(socket.getInputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            synchronized (this) {
                this.numberOfClients++;
            }
            String[] transactionInfo;
            while (true) {
                if (in.hasNextLine()) {
                    String line = in.nextLine();
                    if (!line.equals("")) {
                        if (line.equals("Hello!")) {
                            out.println("Hello");
                            out.flush();
                            continue;
                        }
                        transactionInfo = line.split(" ");
                        int change = Integer.parseInt(transactionInfo[1]);
                        int id = Integer.parseInt(transactionInfo[0]);
                        synchronized (accounts) {
                            if (!accounts.containsKey(id) && change >= 0) {
                                accounts.put(id, change);
                                out.println("done");
                            } else if (accounts.containsKey(id) && accounts.get(id) + change >= 0) {
                                accounts.put(id, accounts.get(id) + change);
                                out.println("done");
                            } else {
                                out.println("failed");
                            }
                        }
                        out.flush();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}