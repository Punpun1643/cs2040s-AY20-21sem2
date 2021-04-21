///////////////////////////////////
// This is the main shift register class.
// Notice that it implements the ILFShiftRegister interface.
// You will need to fill in the functionality.
///////////////////////////////////

/**
 * class ShiftRegister
 * @author
 * Description: implements the ILFShiftRegister interface.
 */
public class ShiftRegister implements ILFShiftRegister {
    ///////////////////////////////////
    // Create your class variables here
    ///////////////////////////////////
    // TODO:
    private int[] shiftRegister;
    private int tap;
    private int size;



    ///////////////////////////////////
    // Create your constructor here:
    ///////////////////////////////////
    ShiftRegister(int size, int tap) {
        // TODO:
        this.tap = tap;
        this.size = size;
        this.shiftRegister = new int[size];

    }

    ///////////////////////////////////
    // Create your class methods here:
    ///////////////////////////////////
    /**
     * setSeed
     * @param seed
     * Description:
     */
    @Override
    public void setSeed(int[] seed) {
        // TODO:
        if(seed.length == size && tap <= seed.length - 1){
            for(int i = seed.length - 1; i >= 0; i--){
                shiftRegister[seed.length - 1 - i] = seed[i];
            }
        }

    }

    /**
     * shift
     * @return
     * Description:
     */
    @Override
    public int shift() {
        // TODO:
        int xor = shiftRegister[0] ^ shiftRegister[shiftRegister.length - 1 - tap];

        for(int i = 0; i < shiftRegister.length - 1; i++){
            shiftRegister[i] = shiftRegister[i + 1];
        }

        shiftRegister[shiftRegister.length - 1] = xor;

        return xor;


    }

    /**
     * generate
     * @param k
     * @return
     * Description:
     */
    @Override
    public int generate(int k) {
        // TODO:
        int[] shifter = new int[k];

        for (int i = 0; i < k; i++) {
            shifter[i] = this.shift(); //shift then put in array //calling the shift method
        }

        return this.toBinary(shifter); //convert to binary using the method //calling the toBinary method
    }

    /**
     * Returns the integer representation for a binary int array.
     * @param array
     * @return
     */
    private int toBinary(int[] array) {
        // TODO:
        int output = 0;

        for (int i = 0; i < array.length; i++) {
           output = (output * 2) + array[i];
        }

        return output;
    }

}
