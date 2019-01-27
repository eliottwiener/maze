import java.util.Stack;
import processing.core.*;

public class Sketch extends PApplet {

	int scale = 10; // must be >=2 to render correctly
	int mazeWidth = 25; // width in # of cells
	int mazeHeight = 25; // height in # of cells
	int spacing = 10; //size of border around maze in pixels
	int fps = 25; //fps to run animation at

	public Stack<Cell> stack = new Stack<Cell>(); //stack for DFS
	public Maze maze = new Maze(mazeWidth, mazeHeight); //the maze

	Cell c; //to hold the active Cell
	Cell nextC; //to hold the upcoming cell

	public void setup() {
		//size(spacing * 2 + scale * mazeWidth, spacing * 2 + scale * mazeHeight);
		//Cannot build processing applet with variables in the size() method, oh well
		size(270,270); //set size of the sketch
		frameRate(fps); //set fps
		ellipseMode(CORNER); //ellipses drawn using top-left corner as origin
		smooth(); //draw with anti-aliasing
		
		maze.getCell(mazeWidth - 1, mazeHeight - 1).setEnd(); // set bottom right Cell as END point
		maze.getCell(0,0).setStart(); //starting cell is the top-left cell
		
		c = maze.getCell(Rand.randomInt(mazeWidth),Rand.randomInt(mazeHeight)); //starting cell for algorithm is random!
		c.setVisited(); //mark it visited
		
		//go through the algorithm's process once so the stack isn't empty
		nextC = c.randomNeighbor(); //pick a random neighbor, nextC
		nextC.setVisited(); //mark it visited
		c.breakWall(nextC); //break the walls between c and nextC
		stack.push(c); //push c onto the stack
		c = nextC; //recurse with nextC as the new c
	}

	public void draw() { //automatically loops at specified frameRate
		background(0); //black out background (clearing any drawing)
		maze.drawMaze(this); //draw the maze
		
		if (!stack.isEmpty()) {//if the maze is incomplete || account for stack starting empty
			if (c.isDeadEnd() || c.isEnd() || c.isStart()) { //if c has no unvisited neighbors, or it's the start/end cell, start backtracking...
				nextC = stack.pop();//by popping the last cell off the stack...
				c = nextC;//and recursing with it as c
			} else { // if there are unvisited neighbors...
				nextC = c.randomNeighbor(); //pick a random neighbor, nextC
				nextC.setVisited(); //mark it visited
				c.breakWall(nextC); //break the walls between c and nextC
				stack.push(c); //push c onto the stack
				c = nextC; //recurse with nextC as the new c
			}
			stroke(0); //stroke black
			fill(255); //fill white
			ellipse(c.getX() * scale + spacing + 1, c.getY() * scale + spacing + 1, scale - 2, scale - 2); // draw 'stylus' ellipse
		} else { //maze is finished
			noLoop(); //stop looping the draw() method
			
			//put green border to indicate completion
			noFill();
			stroke(color(0,255,0));
			rect(0,0,width-1,height-1);
		}
	}
}