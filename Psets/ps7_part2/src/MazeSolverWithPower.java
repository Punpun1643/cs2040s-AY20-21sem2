import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;
import java.util.List;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;




public class MazeSolverWithPower implements IMazeSolverWithPower {
	private static final int NORTH = 0, SOUTH = 1, EAST = 2, WEST = 3;
	private static int[][] DELTAS = new int[][]{
			{-1, 0}, // North
			{1, 0}, // South
			{0, 1}, // East
			{0, -1} // West
	};

	//note: a maze either has power or no power

	//data structure for queueNode (with power)
	public static class queueNode {
		int row;
		int col;
		int dist; //distance of node from the origin
		queueNode parent; //the parent of this queueNode
		//for tracking purposes
		Room room;
		int remainingPower;




		public queueNode(int row, int col, int dist, Room room, int remainingPower) {
			this.row = row;
			this.col = col;
			this.dist = dist;
			this.room = room;
			this.remainingPower = remainingPower;
		}

//		public queueNode(int row, int col, Room room, int remainingPower) {
//			this.row = row;
//			this.col = col;
//			this.room = room;
//			this.remainingPower = remainingPower;
//
//		}

		public void setParent(queueNode node) {
			this.parent = node;
		}

		public queueNode getParent() {
			return this.parent;
		}
	}

	//data structure for queueNode with no power
	public static class queueNodeNP {
		int row;
		int col;
		int dist;
		queueNodeNP parent;

		public queueNodeNP(int row, int col, int dist) {
			this.row = row;
			this.col = col;
			this.dist = dist;
		}

		public void setParent(queueNodeNP node) {
			this.parent = node;
		}

		public queueNodeNP getParent() {
			return this.parent;
		}
	}

	//===FIELDS===//
	//common field
	Maze maze;

	//with power field
	Map<Integer, HashSet<queueNode>> steps;

	//no power fields
	int endRow;
	int endCol;
	boolean solved;
	boolean visitednp[][];
	List<Integer> step;
	//==END OF FIELDS===//

	//===METHODS===//
	private void keepSteps(queueNode node) {
		//if no node of this distance from the origin has been added
		if (!this.steps.containsKey(node.dist)) {
			HashSet<queueNode> nodeContainer = new HashSet<>();
			nodeContainer.add(node);
			this.steps.put(node.dist, nodeContainer);
		} else { // there is already this distance from the origin
			//simply add the node to the container
			this.steps.get(node.dist).add(node);
		}
	}

	private boolean validDirection(int[] directionSet) {
		int currRow = directionSet[0];
		int currCol = directionSet[1];
		int remainingPower = directionSet[2];

		if ((currRow >= 0 && currRow < maze.getRows()) && (currCol >= 0 && currCol < maze.getColumns()) && remainingPower >= 0) {
			return true;
		} else {
			return false;
		}
	}

	private void walkMaze(queueNode start, queueNode tracingNode) {

		if (start == null || tracingNode == null) {
			return;
		} else {
			//always walk the starting position
			this.maze.getRoom(tracingNode.row, tracingNode.col).onPath = true;
			while (tracingNode != start) { //keep walking
				this.maze.getRoom(tracingNode.row, tracingNode.col).onPath = true;
				tracingNode = tracingNode.getParent();
			}
		}
	}

	private Integer result(queueNode start, queueNode end) {
		if (end == null) {
			return null;
		} else {
			int result = 0;
			while (end != start) {
				result++;
				end = end.getParent();
			}
			return result;
		}
	}

	private void updateDistanceNode(queueNode node) {
		if (node.parent == null) {
			node.dist = 0;
		} else {
			node.dist = node.parent.dist + 1;
		}
	}
	//===END OF METHODS===//

	public MazeSolverWithPower() {
		// TODO: Initialize variables.
		//storing key value pairs
		this.steps = new HashMap<>();

	}

	@Override
	public void initialize(Maze maze) {
		// TODO: Initialize the solver.
		this.maze = maze;
		this.solved = false;
		this.visitednp = new boolean[this.maze.getRows()][this.maze.getColumns()];
	}

	@Override
	public Integer pathSearch(int startRow, int startCol, int endRow,
							  int endCol, int superpowers) throws Exception {
		// TODO: Find shortest path with powers allowed.
		if (maze == null) {
			throw new Exception("Oh no! You cannot call me without initializing the maze!");
		}

		if (startRow < 0 || startCol < 0 || startRow >= maze.getRows() || startCol >= maze.getColumns() ||
				endRow < 0 || endCol < 0 || endRow >= maze.getRows() || endCol >= maze.getColumns()) {
			throw new IllegalArgumentException("Invalid start/end coordinate");
		}

//		if (superpowers == 0) {
//			return pathSearch(startRow, startCol, endRow, endCol);
//
//		}

		steps.clear();

		//haven't visited anything. Set all to false
		for (int i = 0; i < this.maze.getRows(); i++) {
			for (int j = 0; j < this.maze.getColumns(); j++) {
				this.maze.getRoom(i, j).onPath = false;
			}
		}

		Queue<queueNode> queue = new LinkedList<>();
		//row, column, amount of superPower
		//at first we haven't used any superpower
		boolean[][][] visited = new boolean[this.maze.getRows()][this.maze.getColumns()][superpowers + 1];
		boolean[][] visitedNode = new boolean[this.maze.getRows()][this.maze.getColumns()];
//		queueNode sourceNode = new queueNode(startRow, startCol, 0, this.maze.getRoom(startRow, startCol), superpowers);
		queueNode sourceNode = new queueNode(startRow, startCol, 0, this.maze.getRoom(startRow, startCol), superpowers);
		queueNode end = null;
		//initializing visitedNode
		for (int i = 0; i < this.maze.getRows(); i++) {
			for (int j = 0; j < this.maze.getColumns(); j++) {
				visitedNode[i][j] = false;
			}
		}

		//sourceNode is always visited
		visited[sourceNode.row][sourceNode.col][superpowers] = true;
		visitedNode[sourceNode.row][sourceNode.col] = true;
		//add this node in the queue
		queue.add(sourceNode);
		//track distance and add to the map index 0 --> container
		keepSteps(sourceNode);
		sourceNode.dist = 0;
//		updateDistanceNode(sourceNode);

//		this.solved = false;
		//always visit the starting node
		this.maze.getRoom(startRow, startCol).onPath = true;
		if (startRow == endRow && startCol == endCol) { //don't need to walk further
			end = sourceNode;
//			this.solved = true;

		} else {
			while (!queue.isEmpty()) { //do BFS
				queueNode focused = queue.poll(); //first in first out
				Room focusedRoom = focused.room;
				int currentPow = focused.remainingPower;

				int[][] directionPower = {{focused.row + DELTAS[0][0], focused.col + DELTAS[0][1], focusedRoom.hasNorthWall() ? currentPow - 1 : currentPow}
						, {focused.row + DELTAS[1][0], focused.col + DELTAS[1][1], focusedRoom.hasSouthWall() ? currentPow - 1 : currentPow}
						, {focused.row + DELTAS[2][0], focused.col + DELTAS[2][1], focusedRoom.hasEastWall() ? currentPow - 1 : currentPow}
						, {focused.row + DELTAS[3][0], focused.col + DELTAS[3][1], focusedRoom.hasWestWall() ? currentPow - 1 : currentPow}};

				for (int[] directionSet : directionPower) {
					int currRemainingPower = directionSet[2];
					int nextRow = directionSet[0];
					int nextCol = directionSet[1];

					if (validDirection(directionSet)) {
						if (!visited[nextRow][nextCol][currRemainingPower]) {
							queueNode nextNode = new queueNode(nextRow, nextCol, 0, this.maze.getRoom(nextRow, nextCol), currRemainingPower);
//							queueNode nextNode = new queueNode(nextRow, nextCol, this.maze.getRoom(nextRow, nextCol), remainingPower);
							queue.add(nextNode);
							visited[nextRow][nextCol][currRemainingPower] = true;
							nextNode.setParent(focused);
							updateDistanceNode(nextNode);



							if (!visitedNode[nextNode.row][nextNode.col]) {
								visitedNode[nextNode.row][nextNode.col] = true;
								keepSteps(nextNode);
							}

							if (nextRow == endRow && nextCol == endCol && (end == null || result(sourceNode, nextNode) < result(sourceNode, end))) {
								end = nextNode;
							}
						}
					}
				}
			}

//			if (end.row == endRow && end.col == endCol) {
//				this.solved = true;
//			}

		}
		walkMaze(sourceNode, end);
		return result(sourceNode, end);
//		if (solved) {
//			walkMaze(sourceNode, end);
//			return result(sourceNode, end);
//		} else {
//			return null;
//		}
//		return result(sourceNode, end);
	}

	@Override
	public Integer pathSearch(int startRow, int startCol, int endRow, int endCol) throws Exception {
		// TODO: Find shortest path.
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
		for (int i = 0; i < maze.getRows(); i++) {
			for (int j = 0; j < maze.getColumns(); j++) {
				this.visitednp[i][j] = false;
				maze.getRoom(i, j).onPath = false;
			}
		}

		//initializing result, counter, step
		int result = 0;
		int counter = 0;
		this.step = new ArrayList<>();


		visitednp[startRow][startCol] = true;

		Queue<queueNodeNP> q = new LinkedList<>();
		queueNodeNP tracingNode = null;
		queueNodeNP sourceNode = new queueNodeNP(startRow, startCol, 0);

		sourceNode.setParent(null);

		q.add(sourceNode);

		while (!q.isEmpty()) {
			this.step.add(counter, q.size());
			Queue nextQ = new LinkedList<>();
			for (queueNodeNP node : q) {
				queueNodeNP focused = node;
				int currRow = focused.row;
				int currCol = focused.col;

				if (currRow == endRow && currCol == endCol) {
					this.solved = true;
					tracingNode = focused;
					result = tracingNode.dist;
				}

				visitednp[currRow][currCol] = true;

				if (!maze.getRoom(currRow, currCol).hasNorthWall() && !visitednp[currRow + DELTAS[0][0]][currCol + DELTAS[0][1]]) {
					queueNodeNP northNode = new queueNodeNP(currRow + DELTAS[0][0], currCol + DELTAS[0][1], focused.dist +1);
					nextQ.add(northNode);
					visitednp[northNode.row][northNode.col] = true;
					northNode.setParent(focused);
				}

				if (!maze.getRoom(currRow, currCol).hasSouthWall() && !visitednp[currRow + DELTAS[1][0]][currCol + DELTAS[1][1]]) {
					queueNodeNP southNode = new queueNodeNP(currRow + DELTAS[1][0], currCol + DELTAS[1][1], focused.dist + 1);
					nextQ.add(southNode);
					visitednp[southNode.row][southNode.col] = true;
					southNode.setParent(focused);
				}

				if (!maze.getRoom(currRow, currCol).hasEastWall() && !visitednp[currRow + DELTAS[2][0]][currCol + DELTAS[2][1]]) {
					queueNodeNP eastNode = new queueNodeNP(currRow + DELTAS[2][0], currCol + DELTAS[2][1], focused.dist + 1);
					nextQ.add(eastNode);
					visitednp[eastNode.row][eastNode.col] = true;
					eastNode.setParent(focused);
				}

				if (!maze.getRoom(currRow, currCol).hasWestWall() && !visitednp[currRow + DELTAS[3][0]][currCol + DELTAS[3][1]]) {
					queueNodeNP westNode = new queueNodeNP(currRow + DELTAS[3][0], currCol + DELTAS[3][1], focused.dist + 1);
					nextQ.add(westNode);
					visitednp[westNode.row][westNode.col] = true;
					westNode.setParent(focused);
				}
			}
			q = nextQ;
			counter++;
		}

		if (solved) {
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
		if (this.steps.isEmpty()) {
			if (k > step.size() - 1) {
				return 0;
			} else {
				return step.get(k);
			}
		} else {
			if (steps.get(k) == null) {
				return 0;
			} else {
				return steps.get(k).size();
			}
		}

	}

	public static void main(String[] args) {
		try {
			Maze maze = Maze.readMaze("maze-dense.txt");
			IMazeSolverWithPower solver = new MazeSolverWithPower();
			solver.initialize(maze);

			System.out.println(solver.pathSearch(0, 0, 2, 3, 1));
			MazePrinter.printMaze(maze);

			for (int i = 0; i <= 9; ++i) {
				System.out.println("Steps " + i + " Rooms: " + solver.numReachable(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
