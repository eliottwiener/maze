import java.util.ArrayList;

public class Cell {
	private boolean start = false; // cell is start cell
	private boolean end = false; // cell is end cell
	private ArrayList<Cell> neighbors; // neighbors of this cell
	public Wall northWall; // north wall exists
	public Wall eastWall; // east wall exists
	public Wall southWall; // south wall exists
	public Wall westWall; // west wall exists
	private int x; // x coordinate of this cell in the maze
	private int y; // y coordinate of this cell in the maze

	// constructor
	public Cell(int x, int y) {
		this.x = x;
		this.y = y;
	}

	// set this cell as start
	public void setStart() {
		this.start = true;
	}

	// set this cell as end
	public void setEnd() {
		this.end = true;
	}

	// get x coordinate
	public int getX() {
		return x;
	}

	// get y coordinate
	public int getY() {
		return y;
	}

	// set the neighbors array
	public void setNeighbors(ArrayList<Cell> n) {
		this.neighbors = n;
	}

	// break walls between this cell and given cell
	public void breakWall(Cell n) {
		if (x == n.x) {
			if (y < n.y) {
				southWall.tearDown();
			}
			if (y > n.y) {
				northWall.tearDown();
			}
		} else if (y == n.y) {
			if (x < n.x) {
				eastWall.tearDown();
			}
			if (x > n.x) {
				westWall.tearDown();
			}
		}
	}

	//Create and assign walls to proper Cells
	public ArrayList<Wall> makeWalls(Maze m){
		ArrayList<Wall> list = new ArrayList<Wall>();
		if(northWall==null && this.y!=0){
			Cell c = m.getCell(this.x,this.y-1);
			Wall w = new Wall(this,c);
			this.northWall = w;
			c.southWall = w;
			list.add(w);
		}
		if(southWall==null && this.y!=m.getHeight()-1){
			Cell c = m.getCell(this.x,this.y+1);
			Wall w = new Wall(this,c);
			this.southWall = w;
			c.northWall = w;
			list.add(w);
		}
		if(eastWall==null && this.x!=m.getWidth()-1){
			Cell c = m.getCell(this.x+1,this.y);
			Wall w = new Wall(this,c);
			this.eastWall = w;
			c.westWall = w;
			list.add(w);
		}
		if(westWall==null && this.x!=0){
			Cell c = m.getCell(this.x-1,this.y);
			Wall w = new Wall(this,c);
			this.westWall = w;
			c.eastWall = w;
			list.add(w);
		}
		return list;
	}
	
	// draw this cell to given Sketch
	public void drawCell(Sketch s) {
		// calculate origin (top-left corner) of this cell in the maze
		int originx = s.spacing + x * s.scale;
		int originy = s.spacing + y * s.scale;

		s.stroke(153);
		s.fill(153);

		//draw walls
		if (!start && (northWall==null || northWall.isUp()))
			s.line(originx, originy, originx + s.scale, originy);
		if (!end && (southWall==null || southWall.isUp()))
			s.line(originx, originy + s.scale, originx + s.scale, originy + s.scale);
		if (eastWall==null || eastWall.isUp())
			s.line(originx + s.scale, originy, originx + s.scale, originy + s.scale);
		if (westWall==null || westWall.isUp())
			s.line(originx, originy, originx, originy + s.scale);
	}
}
