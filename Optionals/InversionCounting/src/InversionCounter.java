//class InversionCounter {
//    public static long countSwaps(int[] arr) {
//        int[] spaceArr = new int[arr.length];
//        return mergesort(arr, spaceArr, 0, arr.length - 1);
//    }
//
//    public static long mergesort(int[] arr, int[] spaceArr, int begin, int end) {
//        if (begin >= end) {
//            return 0;
//        }
//
//        long countSwap = 0;
//        int mid = begin + (end - begin) / 2;
//
//        countSwap += mergesort(arr, spaceArr, begin, mid);
//        countSwap += mergesort(arr, spaceArr, mid + 1, end);
//
//        countSwap += helper(arr, spaceArr, begin, mid, mid + 1, end);
//
//        return countSwap;
//    }
//    /**
//     * Given an input array so that arr[left1] to arr[right1] is sorted and arr[left2] to arr[right2] is sorted
//     * (also left2 = right1 + 1), merges the two so that arr[left1] to arr[right2] is sorted, and returns the
//     * minimum amount of adjacent swaps needed to do so.
//     */
//
//    public static long helper (int[] arr, int[] spaceArr, int left1, int right1, int left2, int right2) {
//        int start = left1;
//        int currentIndex = start;
//        int swapCount = 0;
//        while (left1 <= right1 && left2 <= right2) {
//            if (arr[left1] <= arr[left2]) {
//                spaceArr[currentIndex] = arr[left1];
//                left1++;
//            } else {
//                spaceArr[currentIndex] = arr[left2];
//                swapCount += right1 - left1 + 1;
//                left2++;
//            }
//            currentIndex++;
//        }
//        while (left1 <= right1) {
//            spaceArr[currentIndex] = arr[left1];
//            left1++;
//            currentIndex++;
//        }
//        while (left2 <= right2) {
//            spaceArr[currentIndex] = arr[left2];
//            left2++;
//            currentIndex++;
//        }
//        for (int i = 0; i < arr.length; i++) {
//            arr[i] = spaceArr[i];
//        }
//        return swapCount;
//    }
//
//
////        int indexLeft = left1;
////        int indexRight = left2;
////        int currentIndex = indexLeft;
////        long swapCount = 0;
////        while (indexLeft <= right1 && indexRight <= right2) {
////            if (arr[indexLeft] <= arr[indexRight]) {
////                spaceArr[currentIndex] = arr[indexLeft];
////                indexLeft++;
////            } else {
////                spaceArr[currentIndex] = arr[indexRight];
////                swapCount += (right1 - indexLeft + 1);
////                indexRight++;
////            }
////            currentIndex++;
////        }
////        while (indexLeft <= right1) {
////            spaceArr[currentIndex] = arr[indexLeft];
////            indexLeft++;
////            currentIndex++;
////        }
////        while (indexRight <= right2) {
////            spaceArr[currentIndex] = arr[indexRight];
////            indexRight++;
////            currentIndex++;
////        }
////        for (int i = 0; i < arr.length; i++) {
////            arr[i] = spaceArr[i];
////        }
////        return swapCount;
////    }
//
//    public static long mergeAndCount(int[] arr, int left1, int right1, int left2, int right2) {
//        int[] spaceArr = new int[arr.length];
//        return helper(arr, spaceArr, left1, right1, left2, right2);
//    }
//}
import java.util.Arrays;

class InversionCounter {
    public static long mergeSort(int[] arr, int[] spaceArr, int begin, int end) {
        if (begin >= end) {
            return 0;
        }
        long countSwap = 0;
        int mid = begin + (end - begin) / 2;

        countSwap += mergeSort(arr, spaceArr, begin, mid);
        countSwap += mergeSort(arr, spaceArr,mid + 1, end);
        countSwap += helper(arr, spaceArr, begin, mid, mid+1, end);

        return countSwap;
    }
    public static long countSwaps(int[] arr) {
        int[] temp = new int[arr.length];
        return mergeSort(arr, temp, 0,  arr.length - 1);
    }

    /**
     * Given an input array so that arr[left1] to arr[right1] is sorted and arr[left2] to arr[right2] is sorted
     * (also left2 = right1 + 1), merges the two so that arr[left1] to arr[right2] is sorted, and returns the
     * minimum amount of adjacent swaps needed to do so.
     */
    public static long helper(int[] arr, int[] spaceArr, int left1, int right1, int left2, int right2) {
        int start = left1;
        int currentIndex = start;
        long countSwap = 0;

        while (left1 <= right1 && left2 <= right2) {
            if (arr[left1] <= arr[left2]) {
                spaceArr[currentIndex] = arr[left1];
                left1++;
            } else {
                spaceArr[currentIndex] = arr[left2];
                countSwap += (right1 - (left1 - 1));
                left2++;
            }

            currentIndex++;
        }

        while (left2 <= right2) {
            spaceArr[currentIndex] = arr[left2];
            left2++;
            currentIndex++;
        }

        while (left1 <= right1) {
            spaceArr[currentIndex] = arr[left1];
            left1++;
            currentIndex++;
        }
        
        //copy arr
        for (int i = start; i <= right2; i++) {
            arr[i] = spaceArr[i];
        }

        return countSwap;
    }

    public static long mergeAndCount(int[] arr, int left1, int right1, int left2, int right2) {
        int[] temp = new int[arr.length];
        return helper(arr, temp, left1, right1, left2, right2);
    }
}
