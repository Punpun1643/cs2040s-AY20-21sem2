public class Guessing {

    // Your local variables here
    private int low = 0;
    private int high = 1000;
    private int mid;
    /**
     * Implement how your algorithm should make a guess here
     */
    public int guess() {
        this.mid = low + (high - low)/2;
        return  this.mid;
    }

    /**
     * Implement how your algorithm should update its guess here
     */
    public void update(int answer) {
        if(answer == 1){
            this.high = mid;
        }
        else{
            this.low = mid + 1;
        }
    }
}
