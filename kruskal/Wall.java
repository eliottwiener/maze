
public class Wall {
	boolean up = true;
	Cell a;
	Cell b;
	
	//constructor
	public Wall(Cell a, Cell b){
		this.a=a;
		this.b=b;
	}
	
	//tear down the wall
	public void tearDown(){
		this.up=false;
	}
	
	//is this wall still up?
	public boolean isUp(){
		return up;
	}
}