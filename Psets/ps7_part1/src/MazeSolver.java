import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;
import java.util.List;

public class MazeSolver implements IMazeSolver {
	private static final int NORTH = 0, SOUTH = 1, EAST = 2, WEST = 3;
	private static int[][] DELTAS = new int[][] {
		{ -1, 0 }, // North
		{ 1, 0 }, // South
		{ 0, 1 }, // East
		{ 0, -1 } // West
	};

	private Maze maze;
	private boolean solved = false;
	private boolean[][] visited;
	private int endRow;
	private int endCol;
	private List<Integer> step;

	public MazeSolver() {
		// TODO: Initialize variables.
		this.solved = false;
		this.maze = null;
	}

	@Override
	public void initialize(Maze maze) {
		// TODO: Initialize the solver.
		this.maze = maze;
		this.solved = false;
		this.visited = new boolean[maze.getRows()][maze.getColumns()];
	}

	//data structure for queue node
	static class queueNode {
		int row;
		int col;
		int dist; //distance of node from the origin
		queueNode parent; //the parent of this queueNode
							//for tracking purposes

		public queueNode(int row, int col, int dist) {
			this.row = row;
			this.col = col;
			this.dist = dist;
		}

		public void setParent(queueNode node) {
			this.parent = node;
		}

		public queueNode getParent() {
			return this.parent;
		}
	}


	@Override
	public Integer pathSearch(int startRow, int startCol, int endRow, int endCol) throws Exception {
		// TODO: Find shortest path.
		//(checked)
		this.endRow = endRow;
		this.endCol = endCol;
		this.solved = false;

		if (maze == null) {
			throw new Exception("Oh no! You cannot call me without initializing the maze!");
		}

		if (startRow < 0 || startCol < 0 || startRow >= maze.getRows() || startCol >= maze.getColumns() ||
				endRow < 0 || endCol < 0 || endRow >= maze.getRows() || endCol >= maze.getColumns()) {
			throw new IllegalArgumentException("Invalid start/end coordinate");
		}

		//initializing maze (checked)
		for (int i = 0; i < maze.getRows(); ++i) {
			for (int j = 0; j < maze.getColumns(); ++j) {
				this.visited[i][j] = false;
				maze.getRoom(i, j).onPath = false;
			}
		}

		//initializing result, counter, step
		int result = 0;
		int counter = 0;
		this.step = new ArrayList<>();

//		if (visited[startRow][startCol]) {
//			return null;
//		}

		//mark the source node as visited
		visited[startRow][startCol] = true;

		//current queue for BFS
		Queue<queueNode> q = new LinkedList<>();

		//tracing node to walk the maze
		queueNode tracingNode = null;
		queueNode sourceNode = new queueNode(startRow, startCol, 0);
		//source node has no parent
		sourceNode.setParent(null);
		//enqueue source node
		q.add(sourceNode);

		while (!q.isEmpty()) {
			//num nodes at this step
			this.step.add(counter, q.size());

			Queue<queueNode> nextQ = new LinkedList<>();
			//iterating through all nodes in the current queue
			for (queueNode node : q) {
				queueNode focused = node;
				int currRow = focused.row;
				int currCol = focused.col;
				//return min distance if we reached end point
				if (currRow == endRow && currCol == endCol) {
					this.solved = true;
					tracingNode = focused;
					result = tracingNode.dist;
				}
				//haven't reached so dequeue then traverse adjacent nodes
//				q.remove();
				visited[currRow][currCol] = true;

				if (!maze.getRoom(currRow, currCol).hasNorthWall() && !visited[currRow + DELTAS[0][0]][currCol + DELTAS[0][1]]) {
					queueNode northNode = new queueNode(currRow + DELTAS[0][0], currCol + DELTAS[0][1], focused.dist +1);
					nextQ.add(northNode);
					visited[northNode.row][northNode.col] = true;
					northNode.setParent(focused);
				}

				if (!maze.getRoom(currRow, currCol).hasSouthWall() && !visited[currRow + DELTAS[1][0]][currCol + DELTAS[1][1]]) {
					queueNode southNode = new queueNode(currRow + DELTAS[1][0], currCol + DELTAS[1][1], focused.dist + 1);
					nextQ.add(southNode);
					visited[southNode.row][southNode.col] = true;
					southNode.setParent(focused);
				}

				if (!maze.getRoom(currRow, currCol).hasEastWall() && !visited[currRow + DELTAS[2][0]][currCol + DELTAS[2][1]]) {
					queueNode eastNode = new queueNode(currRow + DELTAS[2][0], currCol + DELTAS[2][1], focused.dist + 1);
					nextQ.add(eastNode);
					visited[eastNode.row][eastNode.col] = true;
					eastNode.setParent(focused);
				}

				if (!maze.getRoom(currRow, currCol).hasWestWall() && !visited[currRow + DELTAS[3][0]][currCol + DELTAS[3][1]]) {
					queueNode westNode = new queueNode(currRow + DELTAS[3][0], currCol + DELTAS[3][1], focused.dist + 1);
					nextQ.add(westNode);
					visited[westNode.row][westNode.col] = true;
					westNode.setParent(focused);
				}
			}
			q = nextQ;
			counter++;
		}
		//now we walk the maze
		if (solved) {
//			System.out.println("reached here");
			this.maze.getRoom(sourceNode.row, sourceNode.col).onPath = true;
			while (tracingNode.getParent() != null) {
				this.maze.getRoom(tracingNode.row, tracingNode.col).onPath = true;
				tracingNode = tracingNode.getParent();

			}
			return result;
		} else {
			this.maze.getRoom(sourceNode.row, sourceNode.col).onPath = true;
			return null;
		}


	}

	@Override
	public Integer numReachable(int k) throws Exception {
		// TODO: Find number of reachable rooms.
		if (k > step.size() - 1) {
			return 0;
		} else {
			return step.get(k);
		}

	}

	public static void main(String[] args) {
		try {
			Maze maze = Maze.readMaze("maze-sample.txt");
			IMazeSolver solver = new MazeSolver();
			solver.initialize(maze);

			System.out.println(solver.pathSearch(0, 0, 2, 4));
			MazePrinter.printMaze(maze);

			for (int i = 0; i <= 9; ++i) {
				System.out.println("Steps " + i + " Rooms: " + solver.numReachable(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}


