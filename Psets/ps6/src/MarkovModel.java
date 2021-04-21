import java.util.HashMap;
import java.util.Random;
import java.util.ArrayList;

/**
 * This is the main class for your Markov Model.
 *
 * Assume that the text will contain ASCII characters in the range [1,255].
 * ASCII character 0 (the NULL character) will be treated as a non-character.
 *
 * Any such NULL characters in the original text should be ignored.
 */
public class MarkovModel {
	int order;
	HashMap<String, int[]> immediateCharHM;
	HashMap<String, Integer> kGramHM;

	// Use this to generate random numbers as needed
	private Random generator = new Random();

	// This is a special symbol to indicate no character
	public static final char NOCHARACTER = (char) 0;

	/**
	 * Constructor for MarkovModel class.
	 *
	 * @param order the number of characters to identify for the Markov Model sequence
	 * @param seed the seed used by the random number generator
	 */

	public static String subString(String str, int start, int end) {
		String string = "";
		for (int i = start; i <= end; i++) {
			string = string + str.charAt(i);
		}
		return string;
	}

	public MarkovModel(int order, long seed) {
		// Initialize your class here
		this.order = order;
		this.immediateCharHM = new HashMap<>();
		this.kGramHM = new HashMap<String, Integer>();

		// Initialize the random number generator
		generator.setSeed(seed);
	}

	/**
	 * Builds the Markov Model based on the specified text string.
	 */
	public void initializeText(String text) {
		// Build the Markov model here
		int textSize = text.length();
		int k = this.order;

		// num times iteration
		for (int i = 0; i < textSize - k; i++) {
			//getting the kGram
			String focusedKGram = text.substring(i, i + k);
//			String focusedKGram = subString(text, i, i + k);
			char immediateChar = text.charAt(i + k);
			int immediateCharASCII = (int) immediateChar;

			//if HT already has that key
			if (this.immediateCharHM.containsKey(focusedKGram)) {
				 //updating the array of characters
				int[] tempArr = this.immediateCharHM.get(focusedKGram);
				tempArr[immediateCharASCII] += 1;
				this.immediateCharHM.put(focusedKGram, tempArr);

				//update freq of existing string
				this.kGramHM.put(focusedKGram, this.kGramHM.get(focusedKGram) + 1);
			} else { // no key
				//add key
				this.immediateCharHM.put(focusedKGram, new int[256]);
				//update value both HTs
				int[] tempArr = this.immediateCharHM.get(focusedKGram);
				tempArr[immediateCharASCII] += 1;
				this.immediateCharHM.put(focusedKGram, tempArr);
				this.kGramHM.put(focusedKGram, 1);
			}
		}
	}

	/**
	 * Returns the number of times the specified kgram appeared in the text.
	 */
	public int getFrequency(String kgram) {
		if (kgram.length() == 0) {
			return 0;
		} else if (kgram.length() == this.order) {
			if (this.kGramHM.containsKey(kgram)) {
				return this.kGramHM.get(kgram);
			} else {
				return 0;
			}
		} else {
			return 0;
		}
	}

	/**
	 * Returns the number of times the character c appears immediately after the specified kgram.
	 */
	public int getFrequency(String kgram, char c) {
		if (kgram.length() == this.order) {
			if (this.immediateCharHM.containsKey(kgram)) {
				int[] tempArr = immediateCharHM.get(kgram);
				return tempArr[(int) c];
 			} else {
				return 0;
			}
		} else {
			return 0;
		}

	}


	/**
	 * Generates the next character from the Markov Model.
	 * Return NOCHARACTER if the kgram is not in the table, or if there is no
	 * valid character following the kgram.
	 */
	public char nextCharacter(String kgram) {
		// See the problem set description for details
		// on how to make the random selection.

		if (immediateCharHM.containsKey(kgram)) {
			int[] possibleChoices = immediateCharHM.get(kgram);
			//iterating through choices
			ArrayList<Character> template = new ArrayList<Character>();
			for(int i = 0; i < possibleChoices.length; i++) {
				if (possibleChoices[i] != 0) {
					int frequency = possibleChoices[i];

					for (int j = 0; j < frequency; j++) {
						template.add((char) i);
					}
				}
			}

			int randomIndex = generator.nextInt(template.size());
			return template.get(randomIndex);
		} else {
			return NOCHARACTER;
		}

	}
}
