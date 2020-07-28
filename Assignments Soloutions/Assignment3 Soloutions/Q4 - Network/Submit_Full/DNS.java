import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;

public class DNS {
    private final HashMap<String, Integer> bankPorts = new HashMap<String, Integer>();

    public DNS(int dnsPort) throws IOException {
        final ServerSocket listener = new ServerSocket(dnsPort);
        new Thread(new Runnable() {
            public void run() {
                while (true) {
                    try {
                        final Socket socket = listener.accept();
                        new Thread(new Runnable() {
                            public void run() {
                                DNS.this.handle(socket);
                            }
                        }).start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void handle(Socket socket) {
        try {
            Scanner in = new Scanner(socket.getInputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            String[] query;
            query = in.nextLine().split(" ");
            synchronized (bankPorts) {
                if (query[0].equals("bank")) {
                    bankPorts.put(query[1], Integer.parseInt(query[2]));
                    out.println("done");
                } else if (query[0].equals("ATM")) {
                    out.println(bankPorts.get(query[1]));
                }
            }
            out.flush();
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

    public int getBankServerPort(String bankName) {
        synchronized (bankPorts) {
            if (bankPorts.containsKey(bankName))
                return bankPorts.get(bankName);
        }
        return -1;
    }
}