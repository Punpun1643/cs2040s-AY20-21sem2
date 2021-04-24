import java.util.Comparator;

public class IntPairComparator implements Comparator<IntPair> {

    @Override
    public int compare(IntPair ip1, IntPair ip2) {
        if (ip1.second == ip2.second) {
            return 0;
        }

        return Integer.compare(ip1.first, ip2.first);

    }
}
