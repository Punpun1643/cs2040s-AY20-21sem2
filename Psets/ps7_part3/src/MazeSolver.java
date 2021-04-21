import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.PriorityQueue;

public class MazeSolver implements IMazeSolver {
	private static final int TRUE_WALL = Integer.MAX_VALUE;
	private static final int EMPTY_SPACE = 0;
	private static final List<Function<Room, Integer>> WALL_FUNCTIONS = Arrays.asList(
			Room::getNorthWall,
			Room::getEastWall,
			Room::getWestWall,
			Room::getSouthWall
	);
	private static final int[][] DELTAS = new int[][] {
			{ -1, 0 }, // North
			{ 0, 1 }, // East
			{ 0, -1 }, // West
			{ 1, 0 } // South
	};

	static class queueNode implements Comparable<queueNode> {
		int row;
		int col;
		int currDist; //distance of node from the origin
		queueNode parent; //the parent of this queueNode
		//for tracking purposes
		boolean visited; //maybe to mark if that node is done visited



		public queueNode(int row, int col) {
			this.row = row;
			this.col = col;
		}

		public void setParent(queueNode node) {
			this.parent = node;
		}

		public queueNode getParent() {
			return this.parent;
		}

		@Override
		public int compareTo(queueNode node) {
			if (this.currDist < node.currDist) {
				return -1;
			} else if (this.currDist > node.currDist) {
				return 1;
			} else {
				return 0;
			}
		}
	}

	class nodeComparator implements Comparator<queueNode> {
		public nodeComparator() {
		}

		@Override
		public int compare(queueNode node1, queueNode node2) {
			return Integer.compare(node1.currDist, node2.currDist);
		}
	}

	//FIELDS
	private Maze maze;
	boolean solved;
	// if the nodes have been visited
	boolean[][] visited;
	int[] minDistArr; //maybe to keep the min dist of all passed nodes
	PriorityQueue<queueNode> queue;
	//min dist of each node
	int[][] distOfNodes;
	int endRow;
	int endCol;

	int sRow;
	int sCol;

	public MazeSolver() {
		// TODO: Initialize variables.
		this.maze = null;
		this.solved = false;
	}

	@Override
	public void initialize(Maze maze) {
		// TODO: Initialize the solver.
		this.maze = maze;
		this.solved = false;
		this.visited = new boolean[this.maze.getRows()][this.maze.getColumns()];
		this.queue = new PriorityQueue<>(new nodeComparator());
		this.distOfNodes = new int[this.maze.getRows()][this.maze.getColumns()];
	}

	//function for Dijkstra's algorithm
	public void findMin(queueNode source) {
		//set distance of source to 0
		source.currDist = 0;
		distOfNodes[source.row][source.col] = 0;
		this.queue.add(source);
		visited[source.row][source.col] = true;

		while(!queue.isEmpty()) {
			queueNode leastDistNode = queue.remove();
			//here i hope to add the adjacent nodes of the least Dist node that has never been visited
			//then continue the process recursively
			if (leastDistNode.row == this.endRow && leastDistNode.col == this.endCol) {
				this.solved = true;

			}
			neighbours(leastDistNode);
		}
	}

	public void findMinModified(queueNode source) {
		//set distance of source to 0
		source.currDist = 0;
		distOfNodes[source.row][source.col] = 0;
		this.queue.add(source);
		visited[source.row][source.col] = true;

		while(!queue.isEmpty()) {
			queueNode leastDistNode = queue.remove();
			//here i hope to add the adjacent nodes of the least Dist node that has never been visited
			//then continue the process recursively
			if (leastDistNode.row == this.endRow && leastDistNode.col == this.endCol) {
				this.solved = true;

			}
			neighboursModified(leastDistNode);
		}
	}
	//function to process all the neighbours of the passed node
	public void neighbours(queueNode node) {
		int edgeDistance;
		int newDistance;

		//I need directions (the 4 possible directions)
		//I also need to check whether the wall is the outter wall ---> it will return max value anyway
		int currRow = node.row;
		int currCol = node.col;

//		int[][] directions = {{currRow + DELTAS[0][0], currCol + DELTAS[0][1]},

//				{currRow + DELTAS[1][0], currCol + DELTAS[1][1]},
//				{currRow + DELTAS[2][0], currCol + DELTAS[2][1]},
//				{currRow + DELTAS[3][0], currCol + DELTAS[3][1]}};
		//mark the pop node as visited
		visited[currRow][currCol] = true;
		//then check all the 4 directions
		Room currRoom = this.maze.getRoom(currRow, currCol);

		if (currRoom.getNorthWall() != Integer.MAX_VALUE && !visited[currRow + DELTAS[0][0]][currCol + DELTAS[0][1]]) {
			int currNodeDistance = distOfNodes[currRow + DELTAS[0][0]][currCol + DELTAS[0][1]];
			edgeDistance = currRoom.getNorthWall();

			if (edgeDistance == 0) {
				edgeDistance++;
			}


			/*
			int expectedDist;
			if (edgeDistance == 1) {
				expectedDist = node.currDist + 1;
			} else if (node.currDist < edgeDistance) {
				expectedDist = edgeDistance;
			} else {
				expectedDist = node.currDist;
			}
 			*/
			queueNode currentNode = new queueNode(currRow + DELTAS[0][0], currCol + DELTAS[0][1]);
			if (node.currDist + edgeDistance < currNodeDistance) {
				//hmm what if the node is already inside the queue?
				if (queue.contains(currentNode)) {
					//just update the distance
					currentNode.currDist = node.currDist + edgeDistance;
					distOfNodes[currentNode.row][currentNode.col] = node.currDist + edgeDistance;
				} else {
					//new node
					queueNode newNode = new queueNode(currRow + DELTAS[0][0], currCol + DELTAS[0][1]);
					//update distance
					newNode.currDist = node.currDist + edgeDistance;
					distOfNodes[newNode.row][newNode.col] = node.currDist + edgeDistance;
					//add new node to queue
					this.queue.add(newNode);
				}
			}
		}

		if (currRoom.getEastWall() != Integer.MAX_VALUE && !visited[currRow + DELTAS[1][0]][currCol + DELTAS[1][1]]) {
			int currNodeDistance = distOfNodes[currRow + DELTAS[1][0]][currCol + DELTAS[1][1]];
			edgeDistance = currRoom.getEastWall();

			if (edgeDistance == 0) {
				edgeDistance++;
			}

			queueNode currentNode = new queueNode(currRow + DELTAS[1][0], currCol + DELTAS[1][1]);
			if (node.currDist + edgeDistance < currNodeDistance) {
				//hmm what if the node is already inside the queue?
				if (queue.contains(currentNode)) {
					//just update the distance
					currentNode.currDist = node.currDist + edgeDistance;
					distOfNodes[currentNode.row][currentNode.col] = node.currDist + edgeDistance;
				} else {
					//new node
					queueNode newNode = new queueNode(currRow + DELTAS[1][0], currCol + DELTAS[1][1]);
					//update distance
					newNode.currDist = node.currDist + edgeDistance;
					distOfNodes[newNode.row][newNode.col] = node.currDist + edgeDistance;
					//add new node to queue
					this.queue.add(newNode);
				}
			}
		}

		if (currRoom.getWestWall() != Integer.MAX_VALUE && !visited[currRow + DELTAS[2][0]][currCol + DELTAS[2][1]]) {
			int currNodeDistance = distOfNodes[currRow + DELTAS[2][0]][currCol + DELTAS[2][1]];
			edgeDistance = currRoom.getWestWall();

			if (edgeDistance == 0) {
				edgeDistance++;
			}

			queueNode currentNode = new queueNode(currRow + DELTAS[2][0], currCol + DELTAS[2][1]);
			if (node.currDist + edgeDistance < currNodeDistance) {
				//hmm what if the node is already inside the queue?
				if (queue.contains(currentNode)) {
					//just update the distance
					currentNode.currDist = node.currDist + edgeDistance;
					distOfNodes[currentNode.row][currentNode.col] = node.currDist + edgeDistance;
				} else {
					//new node
					queueNode newNode = new queueNode(currRow + DELTAS[2][0], currCol + DELTAS[2][1]);
					//update distance
					newNode.currDist = node.currDist + edgeDistance;
					distOfNodes[newNode.row][newNode.col] = node.currDist + edgeDistance;
					//add new node to queue
					this.queue.add(newNode);
				}
			}
		}

		if (currRoom.getSouthWall() != Integer.MAX_VALUE && !visited[currRow + DELTAS[3][0]][currCol + DELTAS[3][1]]) {
			int currNodeDistance = distOfNodes[currRow + DELTAS[3][0]][currCol + DELTAS[3][1]];
			edgeDistance = currRoom.getSouthWall();

			if (edgeDistance == 0) {
				edgeDistance++;
			}

			queueNode currentNode = new queueNode(currRow + DELTAS[3][0], currCol + DELTAS[3][1]);
			if (node.currDist + edgeDistance < currNodeDistance) {
				//hmm what if the node is already inside the queue?
				if (queue.contains(currentNode)) {
					//just update the distance
					currentNode.currDist = node.currDist + edgeDistance;
					distOfNodes[currentNode.row][currentNode.col] = node.currDist + edgeDistance;
				} else {
					//new node
					queueNode newNode = new queueNode(currRow + DELTAS[3][0], currCol + DELTAS[3][1]);
					//update distance
					newNode.currDist = node.currDist + edgeDistance;
					distOfNodes[newNode.row][newNode.col] = node.currDist + edgeDistance;
					//add new node to queue
					this.queue.add(newNode);
				}
			}
		}
	}

	//modified neighbours to suit the new conditions
	public void neighboursModified(queueNode node) {
		int edgeDistance;
		int newDistance;

		//I need directions (the 4 possible directions)
		//I also need to check whether the wall is the outter wall ---> it will return max value anyway
		int currRow = node.row;
		int currCol = node.col;

//		int[][] directions = {{currRow + DELTAS[0][0], currCol + DELTAS[0][1]},

//				{currRow + DELTAS[1][0], currCol + DELTAS[1][1]},
//				{currRow + DELTAS[2][0], currCol + DELTAS[2][1]},
//				{currRow + DELTAS[3][0], currCol + DELTAS[3][1]}};
		//mark the pop node as visited
		visited[currRow][currCol] = true;
		//then check all the 4 directions
		Room currRoom = this.maze.getRoom(currRow, currCol);

		if (currRoom.getNorthWall() != Integer.MAX_VALUE && !visited[currRow + DELTAS[0][0]][currCol + DELTAS[0][1]]) {
			int currNodeDistance = distOfNodes[currRow + DELTAS[0][0]][currCol + DELTAS[0][1]];
			//scariness level of adjacent node
			edgeDistance = currRoom.getNorthWall();

			if (edgeDistance == 0) {
				edgeDistance++;
			}
			/*
			if (currentNode.row = sRow && currentNode.col = sCol) {
				expectedDist = -1;
			}
			 */
			//update new conditions
			queueNode currentNode = new queueNode(currRow + DELTAS[1][0], currCol + DELTAS[1][1]);
			int expectedDist;
			if (currentNode.row == sRow && currentNode.col == sCol) {
				expectedDist = -1;
			} else if (edgeDistance == 1) {
				expectedDist = node.currDist + 1;
			} else if (node.currDist < edgeDistance) {
				expectedDist = edgeDistance;
			} else {
				expectedDist = node.currDist;
			}

			if (expectedDist == -1) {
				currentNode.currDist = expectedDist;
				distOfNodes[currentNode.row][currentNode.col] = expectedDist;
			} else if (expectedDist < currNodeDistance) {
				//hmm what if the node is already inside the queue?
				if (queue.contains(currentNode)) {
					//just update the distance
					currentNode.currDist = expectedDist;
					distOfNodes[currentNode.row][currentNode.col] = expectedDist;
				} else {
					//new node
					queueNode newNode = new queueNode(currRow + DELTAS[0][0], currCol + DELTAS[0][1]);
					//update distance
					newNode.currDist = expectedDist;
					distOfNodes[newNode.row][newNode.col] = expectedDist;
					//add new node to queue
					this.queue.add(newNode);
				}
			}
		}

		if (currRoom.getEastWall() != Integer.MAX_VALUE && !visited[currRow + DELTAS[1][0]][currCol + DELTAS[1][1]]) {
			int currNodeDistance = distOfNodes[currRow + DELTAS[1][0]][currCol + DELTAS[1][1]];
			//scariness level of adjacent node
			edgeDistance = currRoom.getEastWall();

			if (edgeDistance == 0) {
				edgeDistance++;
			}

			//update new conditions
			queueNode currentNode = new queueNode(currRow + DELTAS[1][0], currCol + DELTAS[1][1]);
			int expectedDist;
			if (currentNode.row == sRow && currentNode.col == sCol) {
				expectedDist = -1;
			} else if (edgeDistance == 1) {
				expectedDist = node.currDist + 1;
			} else if (node.currDist < edgeDistance) {
				expectedDist = edgeDistance;
			} else {
				expectedDist = node.currDist;
			}

			if (expectedDist == -1) {
				currentNode.currDist = expectedDist;
				distOfNodes[currentNode.row][currentNode.col] = expectedDist;
			} else if (expectedDist < currNodeDistance) {
				//hmm what if the node is already inside the queue?
				if (queue.contains(currentNode)) {
					//just update the distance
					currentNode.currDist = expectedDist;
					distOfNodes[currentNode.row][currentNode.col] = expectedDist;
				} else {
					//new node
					queueNode newNode = new queueNode(currRow + DELTAS[1][0], currCol + DELTAS[1][1]);
					//update distance
					newNode.currDist = expectedDist;
					distOfNodes[newNode.row][newNode.col] = expectedDist;
					//add new node to queue
					this.queue.add(newNode);
				}
			}
		}

		if (currRoom.getWestWall() != Integer.MAX_VALUE && !visited[currRow + DELTAS[2][0]][currCol + DELTAS[2][1]]) {
			int currNodeDistance = distOfNodes[currRow + DELTAS[2][0]][currCol + DELTAS[2][1]];
			//scariness level of adjacent node
			edgeDistance = currRoom.getWestWall();

			if (edgeDistance == 0) {
				edgeDistance++;
			}

			//update new conditions
			int expectedDist;
			queueNode currentNode = new queueNode(currRow + DELTAS[2][0], currCol + DELTAS[2][1]);
			if (currentNode.row == sRow && currentNode.col == sCol) {
				expectedDist = -1;
			} else if (edgeDistance == 1) {
				expectedDist = node.currDist + 1;
			} else if (node.currDist < edgeDistance) {
				expectedDist = edgeDistance;
			} else {
				expectedDist = node.currDist;
			}

			if (expectedDist == -1) {
				currentNode.currDist = expectedDist;
				distOfNodes[currentNode.row][currentNode.col] = expectedDist;
			} else if (expectedDist < currNodeDistance) {
				//hmm what if the node is already inside the queue?
				if (queue.contains(currentNode)) {
					//just update the distance
					currentNode.currDist = expectedDist;
					distOfNodes[currentNode.row][currentNode.col] = expectedDist;
				} else {
					//new node
					queueNode newNode = new queueNode(currRow + DELTAS[2][0], currCol + DELTAS[2][1]);
					//update distance
					newNode.currDist = expectedDist;
					distOfNodes[newNode.row][newNode.col] = expectedDist;
					//add new node to queue
					this.queue.add(newNode);
				}
			}
		}

		if (currRoom.getSouthWall() != Integer.MAX_VALUE && !visited[currRow + DELTAS[3][0]][currCol + DELTAS[3][1]]) {
			int currNodeDistance = distOfNodes[currRow + DELTAS[3][0]][currCol + DELTAS[3][1]];
			//scariness level of adjacent node
			edgeDistance = currRoom.getSouthWall();

			if (edgeDistance == 0) {
				edgeDistance++;
			}

			//update new conditions
			queueNode currentNode = new queueNode(currRow + DELTAS[1][0], currCol + DELTAS[1][1]);
			int expectedDist;
			if (currentNode.row == sRow && currentNode.col == sCol) {
				expectedDist = -1;
			} else if (edgeDistance == 1) {
				expectedDist = node.currDist + 1;
			} else if (node.currDist < edgeDistance) {
				expectedDist = edgeDistance;
			} else {
				expectedDist = node.currDist;
			}

			if (expectedDist == -1) {
				currentNode.currDist = expectedDist;
				distOfNodes[currentNode.row][currentNode.col] = expectedDist;
			} else if (expectedDist < currNodeDistance) {
				//hmm what if the node is already inside the queue?
				if (queue.contains(currentNode)) {
					//just update the distance
					currentNode.currDist = expectedDist;
					distOfNodes[currentNode.row][currentNode.col] = expectedDist;
				} else {
					//new node
					queueNode newNode = new queueNode(currRow + DELTAS[3][0], currCol + DELTAS[3][1]);
					//update distance
					newNode.currDist = expectedDist;
					distOfNodes[newNode.row][newNode.col] = expectedDist;
					//add new node to queue
					this.queue.add(newNode);
				}
			}
		}
	}
	@Override
	public Integer pathSearch(int startRow, int startCol, int endRow, int endCol) throws Exception {
		// TODO: Find minimum fear level.
		if (maze == null) {
			throw new Exception("Oh no! You cannot call me without initializing the maze!");
		}

		if (startRow < 0 || startCol < 0 || startRow >= maze.getRows() || startCol >= maze.getColumns() ||
				endRow < 0 || endCol < 0 || endRow >= maze.getRows() || endCol >= maze.getColumns()) {
			throw new IllegalArgumentException("Invalid start/end coordinate");
		}

		//initialize maze condition
		for (int i = 0; i < this.maze.getRows(); i++) {
			for (int j = 0; j < this.maze.getColumns(); j++) {
				this.distOfNodes[i][j] = Integer.MAX_VALUE;
				this.visited[i][j] = false;
			}
		}
		this.solved = false;

		queueNode source = new queueNode(startRow, startCol);
		queueNode endNode = new queueNode(endRow, endCol);
		findMin(source);

		if (visited[endRow][endCol]) {

			return this.distOfNodes[endRow][endCol];

		} else {
			return null;
		}
	}

	@Override
	public Integer bonusSearch(int startRow, int startCol, int endRow, int endCol) throws Exception {
		// TODO: Find minimum fear level given new rules.
		//dealing with edge exceptions
		if (maze == null) {
			throw new Exception("Oh no! You cannot call me without initializing the maze!");
		}

		if (startRow < 0 || startCol < 0 || startRow >= maze.getRows() || startCol >= maze.getColumns() ||
				endRow < 0 || endCol < 0 || endRow >= maze.getRows() || endCol >= maze.getColumns()) {
			throw new IllegalArgumentException("Invalid start/end coordinate");
		}

		//initialize maze condition
		for (int i = 0; i < this.maze.getRows(); i++) {
			for (int j = 0; j < this.maze.getColumns(); j++) {
				this.distOfNodes[i][j] = Integer.MAX_VALUE;
				this.visited[i][j] = false;
			}
		}
		this.solved = false;

		queueNode source = new queueNode(startRow, startCol);
		queueNode endNode = new queueNode(endRow, endCol);

		//modified findMin to suit the new condition
		findMinModified(source);

		if (visited[endRow][endCol]) {

			return this.distOfNodes[endRow][endCol];

		} else {
			return null;
		}

	}

	@Override
	public Integer bonusSearch(int startRow, int startCol, int endRow, int endCol, int sRow, int sCol) throws Exception {
		// TODO: Find minimum fear level given new rules and special room.
		if (maze == null) {
			throw new Exception("Oh no! You cannot call me without initializing the maze!");
		}

		if (startRow < 0 || startCol < 0 || startRow >= maze.getRows() || startCol >= maze.getColumns() ||
				endRow < 0 || endCol < 0 || endRow >= maze.getRows() || endCol >= maze.getColumns()) {
			throw new IllegalArgumentException("Invalid start/end coordinate");
		}

		//initialize maze condition
		for (int i = 0; i < this.maze.getRows(); i++) {
			for (int j = 0; j < this.maze.getColumns(); j++) {
				this.distOfNodes[i][j] = Integer.MAX_VALUE;
				this.visited[i][j] = false;
			}
		}
		this.solved = false;

		queueNode source = new queueNode(startRow, startCol);
		queueNode endNode = new queueNode(endRow, endCol);

		//modified findMin to suit the new condition
		this.sRow = sRow;
		this.sCol = sCol;

		findMinModified(source);

		if (visited[endRow][endCol]) {
			return this.distOfNodes[endRow][endCol];
		} else {
			return null;
		}

	}

	public static void main(String[] args) {
		try {
			Maze maze = Maze.readMaze("haunted-maze-sample.txt");
			IMazeSolver solver = new MazeSolver();
			solver.initialize(maze);

//			System.out.println(solver.pathSearch(0, 0, 0, 1));
			System.out.println(solver.bonusSearch(0, 0, 0, 3));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
