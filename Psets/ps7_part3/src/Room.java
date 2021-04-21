/**
 * Represents a single room in the maze.
 */
public class Room {
	private final int westWall;
	private final int eastWall;
	private final int northWall;
	private final int southWall;

	Room(int north, int south, int east, int west) {
		northWall = north;
		southWall = south;
		eastWall = east;
		westWall = west;
	}

	/**
	 * @return the value of the wall to the west of the room
	 */
	public int getWestWall() {
		return westWall;
	}

	/**
	 * @return the value of the wall to the east of the room
	 */
	public int getEastWall() {
		return eastWall;
	}

	/**
	 * @return the value of the wall to the north of the room
	 */
	public int getNorthWall() {
		return northWall;
	}

	/**
	 * @return the value of the wall to the south of the room
	 */
	public int getSouthWall() {
		return southWall;
	}
}
