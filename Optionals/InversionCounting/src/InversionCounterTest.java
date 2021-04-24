import org.junit.Test;

import static org.junit.Assert.*;

public class InversionCounterTest {

    @Test
    public void countSwapsTest1() {
        int[] arr = {3, 1, 2};
        assertEquals(2L, InversionCounter.countSwaps(arr));
    }

    @Test
    public void countSwapsTest2() {
        int[] arr = {1, 8, 2, 9};
        assertEquals(1L, InversionCounter.countSwaps(arr));
    }

    @Test
    public void mergeAndCountTest1() {
        int[] arr = {3, 1, 2};
        assertEquals(2L, InversionCounter.mergeAndCount(arr, 0, 0, 1, 2));
    }

    @Test
    public void mergeAndCountTest2() {
//        int[] arr = {2, 3, 4, 1};
        int[] arr = {1, 8, 2, 9};
        assertEquals(1L, InversionCounter.mergeAndCount(arr, 0, 1, 2, 3));
    }
}