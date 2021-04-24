import java.util.ArrayList;
public class BellmanFord {
    // DO NOT MODIFY THE TWO STATIC VARIABLES BELOW
    public static int INF = 20000000;
    public static int NEGINF = -20000000;
    // TODO: add additional attributes and/or variables needed here, if any
    public ArrayList<ArrayList<IntPair>> adjList;
    public int[] shortestDistArr;

    public BellmanFord(ArrayList<ArrayList<IntPair>> adjList) {
        // TODO: initialize your attributes here, if any
        this.adjList = adjList;
        this.shortestDistArr= new int[adjList.size()];
    }

    public void computeShortestPaths(int source) {
        //initialization of the dist
        //all dist except source is inf
        this.shortestDistArr[source] = 0;
        for (int i = 0; i < shortestDistArr.length; i++) {
            if (i != source) {
                shortestDistArr[i] = BellmanFord.INF;
            }
        }

        //first v - 1 interations
        for (int j = 1; j <= adjList.size() - 1; j++) {
            //go through all edges
            for (int k = 0; k < adjList.size(); k++) {
                for (IntPair pair : adjList.get(k)) {
                    if (shortestDistArr[k] != BellmanFord.INF) {
                        if (shortestDistArr[pair.first] > pair.second + shortestDistArr[k] && shortestDistArr[pair.first] != BellmanFord.NEGINF) {
                            shortestDistArr[pair.first] = pair.second + shortestDistArr[k];
                        }
                    }
                }
            }
        }


        //perform last iterations to check negative weight cycle
        for (int k = 0; k < adjList.size(); k++) {
            for (IntPair pair : adjList.get(k)) {
                if (shortestDistArr[pair.first] > pair.second + shortestDistArr[k] && shortestDistArr[pair.first] != BellmanFord.NEGINF) {
                    //get the first detected node
                    //then traverse all the nodes after that?
                    for (int j = 1; j <= adjList.size(); j++) {
                        for (int n = 0; n < adjList.size(); n++) {
                            for (IntPair pairr : adjList.get(n)) {
                                if (shortestDistArr[n] != BellmanFord.INF && shortestDistArr[pairr.first] > pairr.second + shortestDistArr[n] && shortestDistArr[pairr.first] != BellmanFord.NEGINF) {
                                    shortestDistArr[pairr.first] = NEGINF;
                                }
                            }
                        }
                    }
                    return;
                }
            }
        }
    }

    // TODO: add additional methods here, if any
    public int getDistance(int node) {
        // TODO: implement your getDistance operation here
        if (node >= 0 && node < adjList.size()) {
            return shortestDistArr[node];
        } else {
            return INF;
        }
    }
    public static void main(String[] args) {
        ArrayList<ArrayList<IntPair>> adjList = new ArrayList<>();
        ArrayList zero = new ArrayList<>();
        zero.add(new IntPair(3, 2));
//        zero.add(new IntPair(3, 0));
        ArrayList one = new ArrayList<>();
        one.add(new IntPair(2, -2));
//        one.add(new IntPair(0, 0));
        ArrayList two = new ArrayList<>();
        two.add(new IntPair(1, 1));
//        two.add(new IntPair(4, -1));
        ArrayList three = new ArrayList<>();
//        three.add(new IntPair(2, -1));
        ArrayList four = new ArrayList<>();
//        four.add(new IntPair(1, 0));


        adjList.add(zero);
        adjList.add(one);
        adjList.add(two);
        adjList.add(three);
        adjList.add(four);
        BellmanFord graph = new BellmanFord(adjList);
        graph.computeShortestPaths(0);
        int a = graph.getDistance(0);
        int b = graph.getDistance(1);
        int c = graph.getDistance(2);
        int d = graph.getDistance(3);
        int e = graph.getDistance(4);
        System.out.println(a);
        System.out.println(b);
        System.out.println(c);
        System.out.println(d);
        System.out.println(e);
    }
}

