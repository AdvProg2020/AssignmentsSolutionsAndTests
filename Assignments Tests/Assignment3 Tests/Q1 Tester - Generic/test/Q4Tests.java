import org.junit.BeforeClass;
import org.junit.Test;
import java.io.IOException;
import static org.junit.Assert.*;
import java.util.Arrays;

public class Q4Tests {

    @Test(expected = IllegalStateException.class)
    public void testIllegalStateExceptionGetMin() {
        AkbarWorks<Comparable> akbarWorks = new AkbarWorks();
        akbarWorks.getMin();
    }

    @Test(expected = IllegalStateException.class)
    public void testIllegalStateExceptionGetLast() {
        AkbarWorks<Comparable> akbarWorks = new AkbarWorks();
        akbarWorks.getLast(true);
    }

    @Test(expected = IllegalStateException.class)
    public void testIllegalStateExceptionGetFirst() {
        AkbarWorks<Comparable> akbarWorks = new AkbarWorks();
        akbarWorks.getFirst(false);
    }

    @Test
    public void getMin() throws Exception {
        AkbarWorks<String> akbarWorks = new AkbarWorks<String>();
        akbarWorks.add("Abbas");
        akbarWorks.add("Mohammad");
        akbarWorks.add("Javad");
        akbarWorks.add("Hadi");
        akbarWorks.add("Parsa");
        assertEquals("Abbas", akbarWorks.getMin());
        assertEquals("Hadi", akbarWorks.getMin());
    }

    @Test
    public void getLast() throws Exception {
        AkbarWorks<Double> akbarWorks = new AkbarWorks<Double>();
        akbarWorks.add(2.17);
        akbarWorks.add(2.26);
        akbarWorks.add(3.1);
        akbarWorks.add(4.12);
        Double number1 = 4.12;
        Double number2 = 3.1;
        assertEquals(number1, akbarWorks.getLast(true));
        assertEquals(number2, akbarWorks.getLast(true));
    }

    @Test
    public void getFirst() throws Exception {
        AkbarWorks<Integer> akbarWorks = new AkbarWorks<Integer>();
        akbarWorks.add(1);
        akbarWorks.add(2);
        akbarWorks.add(3);
        akbarWorks.add(4);
        assertEquals(Integer.valueOf(4), akbarWorks.getLast(true));
        assertEquals(Integer.valueOf(3), akbarWorks.getLast(false));
        assertEquals(Integer.valueOf(3), akbarWorks.getLast(false));
    }

    @Test
    public void getLess() throws Exception {
        Double russians = 6.3;
        Double independenceDay = 7.04;
        Double twinTowers = 9.11;
        Double vForVendetta = 11.5;
        AkbarWorks<Double> akbarWorks = new AkbarWorks<Double>();
        akbarWorks.add(twinTowers);
        akbarWorks.add(independenceDay);
        akbarWorks.add(russians);
        akbarWorks.add(vForVendetta);
        Comparable[] answer = akbarWorks.getLess(8.0, true);
        assertTrue(Arrays.asList(answer).contains(independenceDay));
        assertEquals(twinTowers, akbarWorks.getMin());
    }

    @Test
    public void getRecentlyRemoved() throws Exception {
        AkbarWorks<String> akbarWorks = new AkbarWorks<String>();
        akbarWorks.add("Joey");
        akbarWorks.add("Pheobe");
        akbarWorks.add("Eragon");
        akbarWorks.add("Lily");
        akbarWorks.add("Swirly");
        akbarWorks.getMin();
        assertEquals("Eragon", akbarWorks.getRecentlyRemoved(1)[0]);
        akbarWorks.getMin();
        assertEquals("Eragon", akbarWorks.getRecentlyRemoved(2)[1]);
        akbarWorks.getMin();
        assertEquals("Eragon", akbarWorks.getRecentlyRemoved(7)[2]);
    }
}