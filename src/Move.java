
public class Move {
	int x,y,z;
	String direction;
	int size;
	int number;
	
	public Move(int x, int y,int z, String direction, int size, int number){
		this.x=x;
		this.y=y;
		this.z=z;
		this.direction=direction;
		this.size=size;
		this.number=number;
	}
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getZ() {
		return z;
	}
	public void setZ(int z) {
		this.z = z;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
}
