
public class CubeSpace {
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setFree(boolean isFree) {
		this.isFree = isFree;
	}

	public String name;
	boolean isFree;
	
	public CubeSpace(String name){
		this.name=name;
		this.isFree=true;
	}
	
	public boolean isFree(){
		return isFree;
	}
}
