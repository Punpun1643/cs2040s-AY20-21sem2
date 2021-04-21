public interface IMazeSolver {
	/**
	 * (Re-)Initializes the solver with a maze.
	 *
	 * @param maze the maze to initialize the solver with
	 */
	void initialize(Maze maze);

	/**
	 * Returns the minimum possible fear level over all possible paths
	 * from the given starting coordinate to the target coordinate.
	 *
	 * @param startRow the row index of the starting coordinate
	 * @param startCol the column index of the starting coordinate
	 * @param endRow the row index of the target coordinate
	 * @param endCol the column index of the target coordinate
	 * @return null if there is no path from start to end
	 * @throws Exception
	 */
	Integer pathSearch(int startRow, int startCol, int endRow, int endCol) throws Exception;

	/**
	 * Returns the minimum possible fear level over all possible paths
	 * from the given starting coordinate to the target coordinate,
	 * with the new fear level rules.
	 *
	 * @param startRow the row index of the starting coordinate
	 * @param startCol the column index of the starting coordinate
	 * @param endRow the row index of the target coordinate
	 * @param endCol the column index of the target coordinate
	 * @return null if there is no path from start to end
	 * @throws Exception
	 */
	Integer bonusSearch(int startRow, int startCol, int endRow, int endCol) throws Exception;

	/**
	 * Returns the minimum possible fear level over all possible paths
	 * from the given starting coordinate to the target coordinate,
	 * with the new fear level rules.
	 * We now have a special room that can set your fear level to -1.
	 *
	 * @param startRow the row index of the starting coordinate
	 * @param startCol the column index of the starting coordinate
	 * @param endRow the row index of the target coordinate
	 * @param endCol the column index of the target coordinate
	 * @param sRow the row index of the special room coordinate
	 * @param sCol the column index of the special room coordinate
	 * @return null if there is no path from start to end
	 * @throws Exception
	 */
	Integer bonusSearch(int startRow, int startCol, int endRow, int endCol, int sRow, int sCol) throws Exception;
}
