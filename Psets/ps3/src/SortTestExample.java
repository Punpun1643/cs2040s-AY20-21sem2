/**
 * This class is a simple example for how to use the sorting classes.
 * It sorts three numbers, and measures how long it takes.
 */
public class SortTestExample {

    public static void main(String[] args) {
        // Create three key value pairs
        KeyValuePair[] testArray = new KeyValuePair[10];
        testArray[0] = new KeyValuePair(1, 1);
        testArray[1] = new KeyValuePair(9, 70);
        testArray[2] = new KeyValuePair(7, 28);
        testArray[3] = new KeyValuePair(4, 10);
        testArray[4] = new KeyValuePair(5, 15);
        testArray[5] = new KeyValuePair(6, 28);
        testArray[6] = new KeyValuePair(3, 3);
        testArray[7] = new KeyValuePair(8, 28);
        testArray[8] = new KeyValuePair(2, 3);
        testArray[9] = new KeyValuePair(10, 82);
        // Create a stopwatch
        StopWatch watch = new StopWatch();
        ISort sortingObject = new SorterA();

        // Do the sorting
        watch.start();
        sortingObject.sort(testArray);
        watch.stop();

        System.out.println(testArray[0]);
        System.out.println(testArray[1]);
        System.out.println(testArray[2]);
        System.out.println(testArray[3]);
        System.out.println(testArray[4]);
        System.out.println(testArray[5]);
        System.out.println(testArray[6]);
        System.out.println(testArray[7]);
        System.out.println(testArray[8]);
        System.out.println(testArray[9]);

        System.out.println("Time: " + watch.getTime());
    }

}
