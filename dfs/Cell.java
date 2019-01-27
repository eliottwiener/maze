import java.util.ArrayList;

public class Cell {
	private boolean visited = false; //if cell was visited by the DFS
	private boolean start = false; //cell is start cell
	private boolean end = false; //cell is end cell
	private ArrayList<Cell> neighbors; //neighbors of this cell
	private boolean northWall = true; //north wall exists
	private boolean eastWall = true; //east wall exists
	private boolean southWall = true; //south wall exists
	private boolean westWall = true; //west wall exists
	private int x; //x coordinate of this cell in the maze
	private int y; //y coordinate of this cell in the maze

	//constructor
	public Cell(int x, int y) { 
		this.x = x;
		this.y = y;
	}

	//set this Cell as visited
	public void setVisited() {
		this.visited = true;
	}

	//is this the end cell?
	public boolean isEnd() {
		return end;
	}
	
	//is this the start cell?
	public boolean isStart() {
		return start;
	}

	//set this cell as start
	public void setStart() {
		this.start = true;
	}

	//set this cell as end
	public void setEnd() {
		this.end = true;
	}
	
	//get x coordinate
	public int getX(){
		return x;
	}
	
	//get y coordinate
	public int getY(){
		return y;
	}

	//set the neighbors array
	public void setNeighbors(ArrayList<Cell> n) {
		this.neighbors = n;
	}

	//are there unvisited neighbors?
	public boolean isDeadEnd() {
		for (Cell c : neighbors) {
			if (!c.visited)
				return false;
		}
		return true;
	}

	//get unvisited neighbors
	private ArrayList<Cell> availableNeighbors() {
		ArrayList<Cell> list = new ArrayList<Cell>();
		for (Cell c : neighbors) {
			if (!c.visited)
				list.add(c);
		}
		return list;
	}

	//pick a random unvisited neighbor
	public Cell randomNeighbor() {
		return (Cell) Rand.pickRandom(availableNeighbors());
	}

	//break walls between this cell and given cell
	public void breakWall(Cell n) {
		if (x == n.x) {
			if (y < n.y) {
				southWall = false;
				n.northWall = false;
			}
			if (y > n.y) {
				northWall = false;
				n.southWall = false;
			}
		} else if (y == n.y) {
			if (x < n.x) {
				eastWall = false;
				n.westWall = false;
			}
			if (x > n.x) {
				westWall = false;
				n.eastWall = false;
			}
		}
	}
	
	//draw this cell to given Sketch
	public void drawCell(Sketch s) {
		//calculate origin (top-left corner) of this cell in the maze
		int originx = s.spacing + x * s.scale;
		int originy = s.spacing + y * s.scale;

		s.stroke(153); //draw with light-gray
		s.fill(153); //fill with light-grey
		if(northWall && southWall && eastWall && westWall){
			//if cell is unvisited, draw a solid light-grey block
			s.rect(originx, originy, s.scale, s.scale);
		} else {
			//if visited, draw intact walls
			if (northWall && !start) s.line(originx, originy, originx + s.scale, originy);
			if (southWall && !end)   s.line(originx, originy + s.scale, originx + s.scale, originy + s.scale);
			if (eastWall)            s.line(originx + s.scale, originy, originx + s.scale, originy + s.scale);
			if (westWall)            s.line(originx, originy, originx, originy + s.scale);
		}
	}
}
