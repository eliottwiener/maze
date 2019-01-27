import java.util.ArrayList;

import processing.core.PApplet;

public class Sketch extends PApplet {

	int scale = 10; // must be >=2 to render correctly
	int mazeWidth = 25; // width in # of cells
	int mazeHeight = 25; // height in # of cells
	int spacing = 10; //size of border around maze in pixels
	int fps = 25; //fps to run animation at

	public Maze maze = new Maze(mazeWidth, mazeHeight); //the maze

	public ArrayList<Cell> frontiers = new ArrayList<Cell>();
	//The 'FRONTIER List'
	//A list of *ALL* Cells in the maze of state FRONTIER
	//continuously updated as it's changed
	
	Cell c; //to hold the active Cell

	public void setup() {
		//size(spacing * 2 + scale * mazeWidth, spacing * 2 + scale * mazeHeight);
		//Cannot build processing applet with variables in the size() method, oh well
		size(270,270); //set size of the sketch
		frameRate(fps); //set fps
		ellipseMode(CORNER); //ellipses drawn using top-left corner as origin
		smooth(); //draw with anti-aliasing
		
		maze.getCell(mazeWidth - 1, mazeHeight - 1).setEnd(); // set bottom right Cell as END point - only effects drawing
		maze.getCell(0, 0).setStart(); //mark top left cell as START point - only effects drawing
		
		c = maze.getCell(Rand.randomInt(mazeWidth),Rand.randomInt(mazeHeight)); //starting cell for the algorithm is random
		c.setIn(); //mark it IN and all neighbors as FRONTIER
		frontiers.addAll(c.frontierNeighbors()); //add all of its neighbors to the 'FRONTIER List'
	}

	public void draw() { //automatically loops at specified frameRate
		background(0); //black out background (clearing any drawing)
		maze.drawMaze(this); //draw the maze
		
		if (!frontiers.isEmpty()) { //if there are no more FRONTIER cells, the maze is done
			
			c = (Cell) Rand.pickRandom(frontiers); //pick a random cell, c, from the 'FRONTIER List'
			c.breakWall(c.randomInNeighbor()); //break the wall between c and a random IN neighbor
			c.setIn(); //set c to IN and all OUT neighbors to FRONTIER
			
			//Add all *NEW* FRONTIER Cells to the 'FRONTIER List'
			for(Cell i : c.frontierNeighbors()){
				if(!frontiers.contains(i)){
					frontiers.add(i);
				}
			}
			
			frontiers.remove(c); //remove c from the 'FRONTIER List'
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