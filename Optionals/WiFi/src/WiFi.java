import java.util.Arrays;

class WiFi {

    /**
     * Implement your solution here
     */
    public static double computeDistance(int[] houses, int numOfAccessPoints) {
        Arrays.sort(houses);
        //max radius to cover
        double end = (houses[houses.length - 1]/ numOfAccessPoints) / 2;
        double begin = 0;

        //prevent infinite loop
        while(end - begin > 0.01){
            double mid = begin + (end - begin)/2;
            if(coverable(houses, numOfAccessPoints, mid)){
                end = mid;
            }
            else{
                begin = mid;
            }
        }
        return (double) begin;
    }


    /**
     * Implement your solution here
     */
    public static boolean coverable(int[] houses, int numOfAccessPoints, double distance) {
        double maxDist = distance * 2;
        int countRouter = 1;
        Arrays.sort(houses);

        if (numOfAccessPoints * maxDist >= houses[houses.length - 1]) {
            return true;
        }

        int startPoint = houses[0];

        for(int j = 0; j < houses.length; j++){
            if(houses[j] > maxDist + startPoint){
                countRouter += 1;
                startPoint = houses[j];
            }

        }
        if(countRouter > numOfAccessPoints){
            return false;
        }
        else{
            return true;
        }

    }



}
