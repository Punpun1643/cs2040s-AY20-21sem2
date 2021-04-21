public interface IApproximateTSP {

    /**
     * MST
     * Calculate an MST for the specified map.
     * For each point in the map, its link should
     * point to its parent.
     */
    void MST(TSPMap map);

    /**
     * TSP
     * Calculate a solution to the TSP problem, using the
     * approximation algorithm described in problem set 9.
     * Assume that the tour (and the DFS) should start with node 0.
     * For each point in the map, its link should
     * point to the next point on the tour.
     * This function may be called *before* MST is called
     * @param the map to perform TSP with
     */
    void TSP(TSPMap map);

    /**
     * isValidTour
     * @return whether or not the links in the current map
     * specify a valid tour.
     * If the links in the map form a valid tour, then return
     * true.  Otherwise, return false.
     */
    boolean isValidTour(TSPMap map);

    /**
     * tourDistance
     * @return the total distance of the tour, as specified
     * by the links in the map.
     * If the links do not from a valid tour, then return -1.
     */
    double tourDistance(TSPMap map);

}
