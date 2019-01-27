import java.util.ArrayList;

import processing.core.PApplet;

public class Sketch extends PApplet {

	int scale = 10; // must be >=2 to render correctly
	int mazeWidth = 25; // width in # of cells
	int mazeHeight = 25; // height in # of cells
	int spacing = 10; //size of border around maze in pixels
	int fps = 200; //fps to run animation at

	public Maze maze = new Maze(mazeWidth, mazeHeight); //the maze

	public ArrayList<Wall> walls;	//a list of all walls in the maze
	public ArrayList<CellSet> sets = new ArrayList<CellSet>(); //all the CellSets
	
	Cell c; //to hold the active Cell
	Cell cNext; //to hold another Cell

	public void setup() {
		//size(spacing * 2 + scale * mazeWidth, spacing * 2 + scale * mazeHeight);
		//Cannot build processing applet with variables in the size() method, oh well
		size(270,270); //set size of the sketch
		frameRate(fps); //set fps
		ellipseMode(CORNER); //ellipses drawn using top-left corner as origin
		smooth(); //draw with anti-aliasing
		
		maze.getCell(mazeWidth - 1, mazeHeight - 1).setEnd(); // set bottom right Cell as END point - only effects drawing
		maze.getCell(0, 0).setStart(); //mark top left cell as START point - only effects drawing
		
		//easier access
		walls = maze.walls;
		
		//create all CellSets
		for(int y = 0 ; y < mazeHeight ; y++){
			for(int x = 0 ; x < mazeWidth ; x++){
				sets.add(new CellSet(maze.getCell(x, y)));
			}
		}
		
		
		
	}

	public void draw() { //automatically loops at specified frameRate
		background(0); //black out background (clearing any drawing)
		maze.drawMaze(this); //draw the maze
		
		if (sets.size()!=1) { //if there is only one set, the maze is done
			Wall w = (Wall) Rand.pickRandom(walls); //pick a random wall
			CellSet setA = getSetContaining(w.a); //get the set with one of the Cells for that wall...
			CellSet setB = getSetContaining(w.b);//and then get one for the other
			if(setA != setB){ //if the two Cells are in different sets
				w.tearDown(); //tear down the wall between them
				setA.add(setB); //union the two sets by adding setB to setA...
				sets.remove(setB); //and getting removing setB from the set list
			}
			walls.remove(w); //remove that wall so it doesn't come up again
		} else { //maze is finished
			noLoop(); //stop looping the draw() method
			
			//put green border to indicate completion
			noFill();
			stroke(color(0,255,0));
			rect(0,0,width-1,height-1);
		}
	}
	
	//get the set in sets containing the given Cell. There is always only one.
	public CellSet getSetContaining(Cell c){
		for(CellSet cs : sets){
			if(cs.memberOf(c)){
				return cs;
			}
		}
		return null;
	}
}