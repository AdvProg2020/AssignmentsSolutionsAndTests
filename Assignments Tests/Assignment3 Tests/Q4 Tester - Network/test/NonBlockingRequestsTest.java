import org.junit.BeforeClass;
import org.junit.Test;
import java.io.IOException;
import static java.lang.Thread.sleep;
import static org.junit.Assert.*;

public class NonBlockingRequestsTest {
    static DNS dnsServer;
    static BankServer server1;
    static BankServer server2;
    static final int DNS_PORT = 8090;


    @BeforeClass
    public static void createServers_N() throws IOException {
        dnsServer = new DNS(DNS_PORT);
        server1 = new BankServer("mellat", DNS_PORT);
        server2 = new BankServer("melli", DNS_PORT);
    }

    @Test
    public void testSingleServerSingleClient_N() throws IOException {
        BankClient client1 = new BankClient("mellat", DNS_PORT);
        client1.sendAllTransactions("testA", 0);
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(11, server1.getBalance(11111));
    }

    @Test
    public void testSingleServerMultiClient_N() throws IOException {
        BankClient client1 = new BankClient("mellat", DNS_PORT);
        BankClient client2 = new BankClient("mellat", DNS_PORT);
        client1.sendAllTransactions("testB", 10);
        client2.sendAllTransactions("testC", 10);
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(9100, server1.getBalance(22));
    }

    @Test
    public void testMultiServerMultiClient_N() throws IOException {
        BankClient client1 = new BankClient("mellat", DNS_PORT);
        BankClient client2 = new BankClient("melli", DNS_PORT);
        client1.sendAllTransactions("testD", 0);
        client2.sendAllTransactions("testE", 0);
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(4000, server1.getBalance(123456));
        assertEquals(4000, server2.getBalance(123456));
    }
}
