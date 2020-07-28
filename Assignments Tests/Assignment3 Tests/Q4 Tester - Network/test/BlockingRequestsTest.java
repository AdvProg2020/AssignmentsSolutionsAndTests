import org.junit.BeforeClass;
import org.junit.Test;
import java.io.IOException;
import static org.junit.Assert.*;

public class BlockingRequestsTest {
    static DNS dnsServer;
    static BankServer server1;
    static BankServer server2;
    static final int DNS_PORT = 8080;


    @BeforeClass
    public static void createServers_B() throws IOException {
        dnsServer = new DNS(DNS_PORT);
        assertEquals(-1, dnsServer.getBankServerPort("mellat"));
        server1 = new BankServer("mellat", DNS_PORT);
        assertNotEquals(-1, dnsServer.getBankServerPort("mellat"));
        server2 = new BankServer("melli", DNS_PORT);
    }

    @Test
    public void testClientCreation_B() throws IOException {
        int priorNumberOfClients = server2.getNumberOfConnectedClients();
        new BankClient("melli", DNS_PORT);
        assertEquals(priorNumberOfClients + 1, server2.getNumberOfConnectedClients());
        new BankClient("melli", DNS_PORT);
        assertEquals(priorNumberOfClients + 2, server2.getNumberOfConnectedClients());
        new BankClient("melli", DNS_PORT);
        assertEquals(priorNumberOfClients + 3, server2.getNumberOfConnectedClients());
        new BankClient("melli", DNS_PORT);
        assertEquals(priorNumberOfClients + 4, server2.getNumberOfConnectedClients());
        new BankClient("melli", DNS_PORT);
        assertEquals(priorNumberOfClients + 5, server2.getNumberOfConnectedClients());
    }

    @Test
    public void testSingleServerSingleClient_B() throws IOException {
        BankClient client1 = new BankClient("mellat", DNS_PORT);
        assertEquals(0, server1.getBalance(111));
        assertEquals(0, server1.getBalance(222));
        client1.sendTransaction(111, +7);
        client1.sendTransaction(222, +7);
        client1.sendTransaction(111, +5);
        assertEquals(7, server1.getBalance(222));
        assertEquals(12, server1.getBalance(111));
        client1.sendTransaction(111, -3);
        client1.sendTransaction(222, -4);
        assertEquals(9, server1.getBalance(111));
        assertEquals(3, server1.getBalance(222));
        client1.sendTransaction(111, -5);
        client1.sendTransaction(222, -5);
        assertEquals(4, server1.getBalance(111));
        assertEquals(3, server1.getBalance(222));
    }


    @Test
    public void testSingleServerMultiClient_B() throws IOException {
        BankClient client1 = new BankClient("mellat", DNS_PORT);
        BankClient client2 = new BankClient("mellat", DNS_PORT);
        assertEquals(0, server1.getBalance(333));
        assertEquals(0, server1.getBalance(444));
        assertEquals(0, server1.getBalance(555));
        assertEquals(0, server1.getBalance(666));
        client1.sendTransaction(333, +5);
        client1.sendTransaction(444, +7);
        client2.sendTransaction(555, -5);
        client2.sendTransaction(666, +7);
        assertEquals(5, server1.getBalance(333));
        assertEquals(7, server1.getBalance(444));
        assertEquals(0, server1.getBalance(555));
        assertEquals(7, server1.getBalance(666));
        client1.sendTransaction(333, +5);
        client2.sendTransaction(444, -7);
        client1.sendTransaction(555, -5);
        client2.sendTransaction(666, +7);
        assertEquals(10, server1.getBalance(333));
        assertEquals(0, server1.getBalance(444));
        assertEquals(0, server1.getBalance(555));
        assertEquals(14, server1.getBalance(666));
    }
}
