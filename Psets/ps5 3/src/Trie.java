import java.util.ArrayList;

public class Trie {

    // Wildcards
    final char WILDCARD = '.';    

    private class TrieNode {
        // TODO: Create your TrieNode class here.
	   int[] present_chars = new int[62];
    }

    
    public Trie() {
        // TODO: Initialise a trie class here.
	   TrieNode root;
    }

    // inserts string s into the Trie
    void insert(String s) {
        // TODO
    }

    // checks whether string s exists inside the Trie or not
    boolean contains(String s) {
        // TODO
        return false;
    }

    // Search for string with prefix matching the specified pattern sorted by lexicographical order.
    // Return results in the specified ArrayList.
    // Only return at most the first limit results.
    void prefixSearch(String s, ArrayList<String> results, int limit) {
        // TODO
    }


    // Simplifies function call by initializing an empty array to store the results.
    // PLEASE DO NOT CHANGE the implementation for this function as it will be used
    // to run the test cases.
    String[] prefixSearch(String s, int limit) {
        ArrayList<String> results = new ArrayList<String>();
        prefixSearch(s, results, limit);
        return results.toArray(new String[0]);
    }


    public static void main(String[] args) {
        Trie t = new Trie();
        t.insert("peter");
        t.insert("piper");
        t.insert("picked");
        t.insert("a");
        t.insert("peck");
        t.insert("of");
        t.insert("pickled");
        t.insert("peppers");
        t.insert("pepppito");
        t.insert("pepi");
        t.insert("pik");

        String[] result1 = t.prefixSearch("pe", 10);
        String[] result2 = t.prefixSearch("pe.*", 10);
        // result1 should be:
        // ["peck", "pepi", "peppers", "pepppito", "peter"]
        // result2 should contain the same elements with result1 but may be ordered arbitrarily
    }
}
