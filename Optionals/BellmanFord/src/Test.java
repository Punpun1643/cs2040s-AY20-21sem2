import java.util.ArrayList;
import static org.junit.Assert.*;

public class Test {
    @org.junit.Test
    public void test1() {
        ArrayList<IntPair> list_0 = new ArrayList<>();
        list_0.add(new IntPair(3, 2));
        ArrayList<IntPair> list_1 = new ArrayList<>();
        list_1.add(new IntPair(2, -2));
        ArrayList<IntPair> list_2 = new ArrayList<>();
        list_2.add(new IntPair(1, 1));
        ArrayList<IntPair> list_3 = new ArrayList<>();
        ArrayList<IntPair> list_4 = new ArrayList<>();
        ArrayList<ArrayList<IntPair>> finalList = new ArrayList<>();
        finalList.add(list_0);
        finalList.add(list_1);
        finalList.add(list_2);
        finalList.add(list_3);
        finalList.add(list_4);
        BellmanFord test = new BellmanFord(finalList);
        test.computeShortestPaths(0);
        // When 0 is the source...
        assertEquals(0, test.getDistance(0));
        assertEquals(20000000, test.getDistance(1));
        assertEquals(20000000, test.getDistance(2));
        assertEquals(2, test.getDistance(3));
        assertEquals(20000000, test.getDistance(4));
        test.computeShortestPaths(1);
        // When 1 is the source...
        assertEquals(20000000, test.getDistance(0));
        assertEquals(-20000000, test.getDistance(1));
        assertEquals(-20000000, test.getDistance(2));
        assertEquals(20000000, test.getDistance(3));
        assertEquals(20000000, test.getDistance(4));
        test.computeShortestPaths(4);
        // When 4 is the source...
        assertEquals(20000000, test.getDistance(0));
        assertEquals(20000000, test.getDistance(1));
        assertEquals(20000000, test.getDistance(2));
        assertEquals(20000000, test.getDistance(3));
        assertEquals(0, test.getDistance(4));

    }

    @org.junit.Test
    public void test2() {
        ArrayList<IntPair> list_0 = new ArrayList<>();
        list_0.add(new IntPair(1, 1));
        list_0.add(new IntPair(2, 4));
        ArrayList<IntPair> list_1 = new ArrayList<>();
        list_1.add(new IntPair(2, 1));
        list_1.add(new IntPair(4, 2));
        ArrayList<IntPair> list_2 = new ArrayList<>();
        list_2.add(new IntPair(5, 10));
        ArrayList<IntPair> list_3 = new ArrayList<>();
        list_3.add(new IntPair(2, 1));
        ArrayList<IntPair> list_4 = new ArrayList<>();
        list_4.add(new IntPair(3, -3));
        ArrayList<IntPair> list_5 = new ArrayList<>();
        ArrayList<ArrayList<IntPair>> finalList = new ArrayList<>();
        finalList.add(list_0);
        finalList.add(list_1);
        finalList.add(list_2);
        finalList.add(list_3);
        finalList.add(list_4);
        finalList.add(list_5);
        BellmanFord test = new BellmanFord(finalList);
        test.computeShortestPaths(0);
        // When 0 is the source...
        assertEquals(0, test.getDistance(0));
        assertEquals(1, test.getDistance(1)); // 0
        assertEquals(1, test.getDistance(2)); //
        assertEquals(0, test.getDistance(3));
        assertEquals(3, test.getDistance(4));
        assertEquals(11, test.getDistance(5));
        test.computeShortestPaths(1);
        // When 1 is the source...
        assertEquals(20000000, test.getDistance(0));
        assertEquals(0, test.getDistance(1)); // 0
        assertEquals(0, test.getDistance(2)); //
        assertEquals(-1, test.getDistance(3));
        assertEquals(2, test.getDistance(4));
        assertEquals(10, test.getDistance(5));
        test.computeShortestPaths(4);
        // When 4 is the source...
        assertEquals(20000000, test.getDistance(0));
        assertEquals(20000000, test.getDistance(1)); // 0
        assertEquals(-2, test.getDistance(2)); //
        assertEquals(-3, test.getDistance(3));
        assertEquals(0, test.getDistance(4));
        assertEquals(8, test.getDistance(5));
    }
}

