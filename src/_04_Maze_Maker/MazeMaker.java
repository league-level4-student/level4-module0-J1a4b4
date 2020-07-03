package _04_Maze_Maker;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;


public class MazeMaker{
	
	private static int width;
	private static int height;
	
	private static Maze maze;
	
	private static Random randGen = new Random();
	private static Stack<Cell> uncheckedCells = new Stack<Cell>();
	
	
	public static Maze generateMaze(int w, int h){
		width = w;
		height = h;
		maze = new Maze(width, height);
		
		//4. select a random cell to start
		Random random = new Random();
		int randX = random.nextInt(width);
		int randY = random.nextInt(height);
		//5. call selectNextPath method with the randomly selected cell
		selectNextPath(maze.getCell(randX, randY));
		return maze;
	}

	//6. Complete the selectNextPathMethod
	private static void selectNextPath(Cell currentCell) {
		//A. mark cell as visited
		currentCell.setBeenVisited(true);
		//B. Get an ArrayList of unvisited neighbors using the current cell and the method below
		ArrayList<Cell> unvisitedNeighbors = getUnvisitedNeighbors(currentCell);
		//C. if has unvisited neighbors,
		if (unvisitedNeighbors.size() > 0) {
			//C1. select one at random.
			Cell cell = unvisitedNeighbors.get(randGen.nextInt(unvisitedNeighbors.size()));
			//C2. push it to the stack
			uncheckedCells.push(cell);
			//C3. remove the wall between the two cells
			removeWalls(currentCell, cell);
			//C4. make the new cell the current cell and mark it as visited
			currentCell = cell;
			cell.setBeenVisited(true);
			//C5. call the selectNextPath method with the current cell
			selectNextPath(currentCell);
		}
		//D. if all neighbors are visited
		if (unvisitedNeighbors.size() == 0) {
			//D1. if the stack is not empty
			if (uncheckedCells.isEmpty() == false) {
				// D1a. pop a cell from the stack
				// D1b. make that the current cell
				currentCell = uncheckedCells.pop();
				// D1c. call the selectNextPath method with the current cell
				selectNextPath(currentCell);
			}
		}
	}

	//7. Complete the remove walls method.
	//   This method will check if c1 and c2 are adjacent.
	//   If they are, the walls between them are removed.
	private static void removeWalls(Cell c1, Cell c2) {
		if (c1.getX() == c2.getX() && c1.getY() < c2.getY()) {
			c1.setSouthWall(false);
			c2.setNorthWall(false);
		}else if (c1.getX() == c2.getX() && c1.getY() > c2.getY()) {
			c1.setNorthWall(false);
			c2.setSouthWall(false);
		}else if (c1.getX() < c2.getX() && c1.getY() == c2.getY()) {
			c1.setEastWall(false);
			c2.setWestWall(false);
		}else if (c1.getX() > c2.getX() && c1.getY() == c2.getY()) {
			c1.setWestWall(false);
			c2.setEastWall(false);
		}
	}
	
	//8. Complete the getUnvisitedNeighbors method
	//   Any unvisited neighbor of the passed in cell gets added
	//   to the ArrayList
	private static ArrayList<Cell> getUnvisitedNeighbors(Cell c) {
		ArrayList<Cell> unvisitedNeighbors = new ArrayList<Cell>();
		if (c.getY() - 1 >= 0 && maze.getCell(c.getX(), c.getY() - 1).hasBeenVisited() == false) {
			unvisitedNeighbors.add(maze.cells[c.getX()][c.getY() - 1]);
		}
		if (c.getX() - 1 >= 0 && maze.getCell(c.getX() - 1, c.getY()).hasBeenVisited() == false) {
			unvisitedNeighbors.add(maze.cells[c.getX() - 1][c.getY()]);
		}
		if (c.getY() + 1 < maze.getHeight() && maze.getCell(c.getX(), c.getY() + 1).hasBeenVisited() == false) {
			unvisitedNeighbors.add(maze.cells[c.getX()][c.getY() + 1]);
		}
		if (c.getX() + 1 < maze.getWidth() && maze.getCell(c.getX() + 1, c.getY()).hasBeenVisited() == false) {
			unvisitedNeighbors.add(maze.cells[c.getX() + 1][c.getY()]);
		}
		return unvisitedNeighbors;
	}
}
