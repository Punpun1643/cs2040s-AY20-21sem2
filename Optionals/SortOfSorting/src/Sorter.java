class Sorter {

    public static void sortStrings(String[] arr) {
        // TODO: implement your sorting function here
        //sort using insertion sort
        for (int i = 1; i < arr.length; i++) {
            int track = i;
            while (track >= 1 && isGreaterThan(arr[track - 1], arr[track])) {
                swap(arr, track - 1, track);
                track--;
            }
        }
    }

    //swapping index a and b in the arr
    public static void swap(String[] arr, int a, int b) {
        String temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }
    //check whether str1 is greater than str2
    public static boolean isGreaterThan(String str1, String str2) {
        //your implementation here
        char firstCharStr1 = str1.charAt(0);
        char firstCharStr2 = str2.charAt(0);

        if (Character.compare(firstCharStr1, firstCharStr2) == 0) {
            char secondCharStr1 = str1.charAt(1);
            char secondCharStr2 = str2.charAt(1);

            return Character.compare(secondCharStr1, secondCharStr2) > 0;
        } else {
            return Character.compare(firstCharStr1, firstCharStr2) > 0;
        }
    }
}
