import javax.xml.crypto.dsig.keyinfo.KeyValue;
import java.util.Random;

public class SortingTester2 {
    static Random rand = new Random();
    static int upperBound = 1000;

    public static boolean checkSort(ISort sorter, int size) {
        //TODO: implement this

        KeyValuePair[] testArray = new KeyValuePair[size];
        ISort sortingObject = sorter;
        for(int i = 1; i <= size; i++) {
            testArray[i - 1] = new KeyValuePair(rand.nextInt(upperBound), i);
        }

        StopWatch watch = new StopWatch();
        watch.start();
        sortingObject.sort(testArray);
        watch.stop();

        for(int j = 0; j < size - 1; j++){
            if(testArray[j].getKey() > testArray[j + 1].getKey()){

//                System.out.println("Time: " + watch.getTime());
                return false;
            }
        }

//        System.out.println("Time: " + watch.getTime());
        return true;
    }

    public static boolean isStable(ISort sorter, int size) {
        //TODO: implement this
        KeyValuePair[] testArray = new KeyValuePair[size];
        ISort sortingObject = sorter;

        for(int i = 0; i < size - 2; i++){
            testArray[i] = new KeyValuePair(2, i + 3);
        }
        testArray[size - 2] = new KeyValuePair(1, 1);
        testArray[size - 1] = new KeyValuePair(1, 2);


        sortingObject.sort(testArray);



        for(int j = 0; j < size - 1; j++){
            if(testArray[j].getValue() > testArray[j + 1].getValue()){
                System.out.println("false");
                return false;
            }
        }
        System.out.println("true");
        return true;

    }

    public static void main(String[] args) {
        //TODO: implement this
        ISort SorterA = new SorterA();
        ISort SorterB = new SorterB();
        ISort SorterC = new SorterC();
        ISort SorterD = new SorterD();
        ISort SorterE = new SorterE();
        ISort SorterF = new SorterF();

        ISort[] sorterList = new ISort[] {SorterA, SorterB, SorterC, SorterD, SorterE, SorterF};
        int[] size = new int[]{5000, 10000, 20000, 40000};

        //sorted list

        //reversed sorted list
        //do the sorting
        System.out.println("Check Sort");
        float countTime = 0;
        for(ISort sorter : sorterList){
            StopWatch watch = new StopWatch();

            for(int num : size){
                KeyValuePair[] testArray = new KeyValuePair[num];
                for(int k = 0; k < num; k++){
                    testArray[k] = new KeyValuePair(k, k);
                }
                watch.start();
                sorter.sort(testArray);
                    watch.stop();
                    countTime = watch.getTime();
                    System.out.printf("%s size is: %d%n", sorter, num);
                    System.out.printf("%s time is: %f%n", sorter, countTime);
//                    System.out.printf("%sis true %n", sorter);


                countTime = 0;
            }
        }




    }
}
