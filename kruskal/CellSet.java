import java.util.Vector;

public class CellSet{
	Vector<Cell> cells = new Vector<Cell>();
	
	//starts with one Cell, since each Cell is in its own set at the beginning
	public CellSet(Cell cell){
		this.cells.add(cell);
	}
	
	//is the given Cell in this set?
	public boolean memberOf(Cell c){
		return cells.contains(c);
	}
	
	//add all members of the given set to this set
	public void add(CellSet cs){
		cells.addAll(cs.cells);
	}
	
}
