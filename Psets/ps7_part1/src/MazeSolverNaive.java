/**
 * Solves the shortest path in a maze problem using Depth-First Search!
 *
 * @author Joe the Average but Enthusiastic Coder
 */
public class MazeSolverNaive implements IMazeSolver {
	private static final int NORTH = 0, SOUTH = 1, EAST = 2, WEST = 3;
	private static int[][] DELTAS = new int[][]{
			{-1, 0}, // North
			{1, 0},  // South
			{0, 1},  // East
			{0, -1}  // West
	};

	private Maze maze;
	private boolean solved = false;
	private boolean[][] visited;
	private int endRow, endCol;

	public MazeSolverNaive() {
		solved = false;
		maze = null;
	}

	@Override
	public void initialize(Maze maze) {
		this.maze = maze;
		visited = new boolean[maze.getRows()][maze.getColumns()];
		solved = false;
	}

	@Override
	public Integer pathSearch(int startRow, int startCol, int endRow, int endCol) throws Exception {
		if (maze == null) {
			throw new Exception("Oh no! You cannot call me without initializing the maze!");
		}

		if (startRow < 0 || startCol < 0 || startRow >= maze.getRows() || startCol >= maze.getColumns() ||
				endRow < 0 || endCol < 0 || endRow >= maze.getRows() || endCol >= maze.getColumns()) {
			throw new IllegalArgumentException("Invalid start/end coordinate");
		}

		// set all visited flag to false
		// before we begin our search
		for (int i = 0; i < maze.getRows(); ++i) {
			for (int j = 0; j < maze.getColumns(); ++j) {
				this.visited[i][j] = false;
				maze.getRoom(i, j).onPath = false;
			}
		}

		this.endRow = endRow;
		this.endCol = endCol;
		solved = true;

		return solve(startRow, startCol, 0);
	}

	private boolean canGo(int row, int col, int dir) {
		// not needed since our maze has a surrounding block of wall
		// but Joe the Average Coder is a defensive coder!
		if (row + DELTAS[dir][0] < 0 || row + DELTAS[dir][0] >= maze.getRows()) return false;
		if (col + DELTAS[dir][1] < 0 || col + DELTAS[dir][1] >= maze.getColumns()) return false;

		switch (dir) {
			case NORTH:
				return !maze.getRoom(row, col).hasNorthWall();
			case SOUTH:
				return !maze.getRoom(row, col).hasSouthWall();
			case EAST:
				return !maze.getRoom(row, col).hasEastWall();
			case WEST:
				return !maze.getRoom(row, col).hasWestWall();
		}

		return false;
	}

	private Integer solve(int row, int col, int rooms) {
		if (visited[row][col]) {
			return null;
		}

		visited[row][col] = true;
		maze.getRoom(row, col).onPath = true;

		if (row == endRow && col == endCol) {
			// YES! we found it!
			return rooms;
		}

		// for each of the 4 directions
		for (int direction = 0; direction < 4; ++direction) {
			if (canGo(row, col, direction)) { // can we go in that direction?
				// yes we can :)
				Integer soln = solve(row + DELTAS[direction][0], col + DELTAS[direction][1], rooms + 1);
				if (soln != null) return soln;
			}
		}

		maze.getRoom(row, col).onPath = false;
		return null;
	}

	@Override
	public Integer numReachable(int k) {
		throw new UnsupportedOperationException();
	}

	public static void main(String[] args) {
		try {
			Maze maze = Maze.readMaze("maze-sample.txt");
			IMazeSolver solver = new MazeSolverNaive();

			solver.initialize(maze);
			System.out.println(solver.pathSearch(0, 0, 3, 3));
			MazePrinter.printMaze(maze);

			System.out.println();

			System.out.println(solver.pathSearch(0, 0, 2, 3));
			MazePrinter.printMaze(maze);

			System.out.println("Average is the new cool!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
