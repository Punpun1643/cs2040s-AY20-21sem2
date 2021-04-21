import java.util.ArrayList;
import java.util.List;

public class TSPGraph implements IApproximateTSP {
    int[] parentArr;
    List<Integer> DFSList = new ArrayList<>();

    @Override
    public void MST(TSPMap map) {
        // TODO: implement this method
        //we will use Prim's algo
        //we need a priority queue to update the next-to-be visited node
        //Priority = weight
        //Key = Integer (indentify node)
        TreeMapPriorityQueue<Double, Integer> pq = new TreeMapPriorityQueue<>();

        //initialize the parentArr
        this.parentArr = new int[map.getCount()];

        //initializing priority queue
        for (int i = 0; i < map.getCount(); i++) {
            pq.add(i, Double.POSITIVE_INFINITY);
        }

        pq.decreasePriority(0, 0.0);

        while (!pq.isEmpty()) {
            Integer minKey = pq.extractMin();
            for (int i = 0; i < map.getCount(); i++) {
                if (pq.lookup(i) != null) {
                    double distanceBetween = map.pointDistance(minKey, i);

                    if (distanceBetween < pq.lookup(i)) {
                        //we update the new weight
                        //also update priority queue
                        pq.decreasePriority(i, distanceBetween);

                        //also need to re-update the parent i.e. parent of node i becomes minKey
                        //parent is the other end

                        this.parentArr[i] = minKey;
                    }
                }
            }
        }

        //start node has no parent
        map.setLink(0, 0, false);

        //we set link between parent and child
        for (int i = 1; i < map.getCount(); i++) {
//            map.setLink(i, this.parent.get(i), false);
            map.setLink(i, this.parentArr[i], false);
        }

        map.redraw();
    }

    //modified DFS to be used with TSP
    //find the starting then just recursively do DFS
    public void modifiedDFS(int[] list, int source) {
        for (int i = 1; i < list.length; i++) {
            if (list[i] == source) {
                this.DFSList.add(i);
                modifiedDFS(list, i);
            }
        }
    }

    @Override
    public void TSP(TSPMap map) {
        MST(map);
        // TODO: implement the rest of this method.

        //starting node
        int start = 0;

        //starting node is added
        this.DFSList.add(start);

        //(checked)
        //perform modified DFS
        for (int i = 1; i < map.getCount(); i++) {
            if (this.parentArr[i] == start) {
                //keep adding to the back
                this.DFSList.add(i);
                modifiedDFS(this.parentArr, i);
            }
        }

        //this should give the num of nodes should be equal to that of map
        int DFSListSize = DFSList.size();

        //link 2 nodes before and after
        for (int i = 0; i < DFSListSize - 1; i++) {
            map.setLink(DFSList.get(i), DFSList.get(i + 1), false);
        }

        //link the last to the origin
        if (map.getCount() > 0) {
            map.setLink(DFSList.get(DFSListSize - 1), DFSList.get(0),false);
        }

        map.redraw();
    }

    @Override
    public boolean isValidTour(TSPMap map) {
        // Note: this function should with with *any* map, and not just results from TSP().
        // TODO: implement this method
        int count = 1;
        Integer source = 0;
        Integer start = 0;
        Integer nextNode = map.getLink(start);

        if (map != null) {
            if (map.getCount() == 1) {
                return true;
            } else {
                while (nextNode != source) {

                    if (nextNode == -1) {
                        return false;
                    }

                    if (map.getCount() == count && nextNode == source) {
                        return true;
                    } else if (map.getCount() == count && nextNode != source) {
                        return false;
                    } else {
                        nextNode = map.getLink(nextNode);
                        count++;
                    }
                }
                return count == map.getCount();
            }

        } else {
            return false;
        }

    }

    public double helperDistance(TSPMap map, double accDist, Integer source, Integer start) {

                if (map.getLink(source) == start) {
                    return accDist + map.pointDistance(source, start);
                } else {
                    return helperDistance(map, accDist + map.pointDistance(map.getLink(source), source), map.getLink(source),start);
                }

    }

    @Override
    public double tourDistance(TSPMap map) {
        // Note: this function should with with *any* map, and not just results from TSP().
        // TODO: implement this method
        if (isValidTour(map)) {
            return helperDistance(map, 0.0, 0, 0);
        } else {
            return -1;
        }
    }

    public static void main(String[] args) {
        TSPMap map = new TSPMap(args.length > 0 ? args[0] : "hundredpoints.txt");
        TSPGraph graph = new TSPGraph();
////
        graph.MST(map);
        graph.TSP(map);
        System.out.println(graph.isValidTour(map));
        System.out.println(graph.tourDistance(map));
    }
}
