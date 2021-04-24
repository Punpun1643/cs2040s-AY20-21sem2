
import static org.junit.Assert.*;

public class WiFiTest {

    @org.junit.Test
    public void computeDistance() {
        int[] houses = {1, 3, 10};
        int numAccessPoints = 2;
        assertEquals(1.0, WiFi.computeDistance(houses, numAccessPoints), 0.5);
    }

    @org.junit.Test
    public void coverable1() {
        int[] houses = {1, 3, 10};
        int numAccessPoints = 2;
        assertTrue(WiFi.coverable(houses, numAccessPoints, 1.0));
    }

    @org.junit.Test
    public void coverable2() {
        int[] houses = {1, 3, 10};
        int numAccessPoints = 2;
        assertFalse(WiFi.coverable(houses, numAccessPoints, 0.5));
    }
}