
import java.util.Arrays;
/**
 * Contains static routines for solving the problem of balancing m jobs on p processors
 * with the constraint that each processor can only perform consecutive jobs.
 */
public class LoadBalancing {

    /**
     * Checks if it is possible to assign the specified jobs to the specified number of processors such that no
     * processor's load is higher than the specified query load.
     *
     * @param jobSize the sizes of the jobs to be performed
     * @param queryLoad the maximum load allowed for any processor
     * @param p the number of processors
     * @return true iff it is possible to assign the jobs to p processors so that no processor has more than queryLoad load.
     */

    public static boolean feasibleLoad(int[] jobSize, int queryLoad, int p) {
        // TODO: Implement this
        int total = 0;
        int count = 0;

        //allocate the job into each p and count
        for(int i = 0; i < jobSize.length; i++){
            if(jobSize[i] > queryLoad){
                return false;
            }
            else{
                total += jobSize[i];
                if(total > queryLoad){
                    total = jobSize[i];
                    count++;
                }
            }
        }
        //plus one the last p
        return count + 1 <= p;
    }


    //runtime for iterating through the array: O(n)

    /**
     * Returns the minimum achievable load given the specified jobs and number of processors.
     *
     * @param jobSize the sizes of the jobs to be performed
     * @param p the number of processors
     * @return the maximum load for a job assignment that minimizes the maximum load
     */
    public static int findLoad(int[] jobSize, int p) {
        // TODO: Implement this
        int totalSize = 0;
        int n = jobSize.length;

        for(int i = 0; i < n; i++){
            totalSize += jobSize[i];
        }

        int begin = 0;
        int end = totalSize;

        while(begin < end){
            int mid = begin + (end - begin)/2;

            if(LoadBalancing.feasibleLoad(jobSize, mid, p)){
                end = mid;
            }
            else {
                begin = mid + 1;
            }
        }
        return begin;
    }
    //runtime iterating through the array: O(n)
    //runtime binary search: O(logm) where m is the length of array that were to contain integers from 0 to totalSize
    //total runtime: O(n) + O(logm) = O(n)

    // These are some arbitrary testcases.
    public static int[][] testCases = {
            {1, 3, 5, 7, 9, 11, 10, 8, 6, 4},
            {67, 65, 43, 42, 23, 17, 9, 100},
            {4, 100, 80, 15, 20, 25, 30},
            {2, 3, 4, 5, 6, 7, 8, 100, 99, 98, 97, 96, 95, 94, 93, 92, 91, 90, 89, 88, 87, 86, 85, 84, 83},
            {7}
    };

    /**
     * Some simple tests for the findLoad routine.
     */
    public static void main(String[] args) {
        for (int p = 1; p < 30; p++) {
            System.out.println("Processors: " + p);
            for (int[] testCase : testCases) {
                System.out.println(findLoad(testCase, p));
            }
        }
    }
}
