import java.util.ArrayList;

public class CubeSpace {

	public String name;
	boolean isFree;
	ArrayList<CubeSpace> neighbors;
	
	public CubeSpace(String name){
		this.name=name;
		this.isFree=true;
		this.neighbors=new ArrayList<CubeSpace>();
	}
	
	public void setNeighbor(CubeSpace cube){
		neighbors.add(cube);
	}
	
	public boolean isReachable(){
		for(CubeSpace cube : neighbors){
			if(!cube.isFree()){
				return false;
			}
		}
		return true;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setFree(boolean isFree) {
		this.isFree = isFree;
	}
	
	public boolean isFree(){
		return isFree;
	}
	
}
