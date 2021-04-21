import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Maze {
    private final static char TRUE_WALL = '#';
    private final static char EMPTY_SPACE = ' ';

	private int rows, columns; // size of the maze data file
	private Room[][] rooms;

	/**
	 * Creates an empty maze.
	 */
	private Maze() {
		rows = columns = 0;
		rooms = null;
	}

	/**
	 * Retrieves a room given the row and column (both 0-indexed).
	 *
	 * @param row the row index of the room
	 * @param column the column index of the room
	 * @return room the room at the requested coordinate
	 */
	public Room getRoom(int row, int column) {
		if (row >= getRows() || column >= getColumns() || row < 0 || column < 0) {
			throw new IllegalArgumentException();
		}

		return rooms[row][column];
	}

	/**
	 * @return the number of rows in the maze
	 */
	public int getRows() {
		return rows / 2;
	}

	/**
	 * @return the number of columns in the maze
	 */
	public int getColumns() {
		return columns / 2;
	}

	/**
	 * Given an input character representing a wall, converts it into the value of the wall.
	 *
	 * @param input input character representing a wall
	 * @return value of the wall
	 */
	public static int getWallValue(char input) {
		if (input == TRUE_WALL) {
			return Integer.MAX_VALUE;
		} else if (input == EMPTY_SPACE) {
			return 0;
		} else {
			return input;
		}
	}

	/**
	 * Reads in an ASCII description of a maze and returns the
	 * created maze object.
	 *
	 * @param fileName
	 * @return maze
	 * @throws IOException if the input format is invalid
	 */
	public static Maze readMaze(String fileName) throws IOException {
		FileReader fin = new FileReader(fileName);
		BufferedReader bin = new BufferedReader(fin);

		Maze maze = new Maze();

		List<String> input = new ArrayList<>();
		String line;
		while ((line = bin.readLine()) != null) {
			if (line.isEmpty()) {
				break; // end of input
			}
			if (maze.columns > 0 && line.length() != maze.columns) {
				throw new IOException("Invalid input format");
			}
			maze.columns = line.length();
			maze.rows++;
			input.add(line);
		}

		if (maze.rows % 2 == 0 || maze.columns % 2 == 0) {
			throw new IOException("Invalid input format");
		}

		maze.rooms = new Room[maze.rows / 2][maze.columns / 2];
		for (int i = 1; i < maze.rows - 1; i += 2) {
			for (int j = 1; j < maze.columns - 1; j += 2) {
				maze.rooms[i / 2][j / 2] = new Room(
						getWallValue(input.get(i - 1).charAt(j)), // north: i-1
						getWallValue(input.get(i + 1).charAt(j)), // south: i+1
						getWallValue(input.get(i).charAt(j + 1)), // east: j+1
						getWallValue(input.get(i).charAt(j - 1)) // west: j-1
				);
			}
		}

		assert (!bin.ready());
		bin.close();

		return maze;
	}
}
