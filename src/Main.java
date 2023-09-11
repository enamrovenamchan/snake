import java.util.ArrayList;

public class Main {
	public int[] sizes = { 3, 1, 3, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 3, 1, 2, 2, 3, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1,
			3, 1, 3, 1, 3, 3, 3, 2 };
	public String[] directions = { "+x", "-x", "+y", "-y", "+z", "-z" };
	public int loops=0;
	public int[] numbers={0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
	
	public void printNumbers(){
		for(int i=0;i<numbers.length;i++){
			System.out.println(i+" wurde so oft gezählt: "+numbers[i]);
		}
	}
	
	public void countNumbers(int number){
		numbers[number]=numbers[number]+1;
	}
	
	public static void main(String[] args) {

		new Main().start();
	}

	public void start() {

		ArrayList<CubeSpace> space = newSpace();
		doSomething(space, 0);
	}
	
	public synchronized void doSomething(ArrayList<CubeSpace> space, int number) {
		int size = sizes[number];
		ArrayList<Move> validMoves = new ArrayList<Move>();
		validMoves.add(new Move(1, 1, 1, "+x", size));
		validMoves.add(new Move(1, 2, 1, "+x", size));
		validMoves.add(new Move(1, 2, 2, "+x", size));
		for (Move move : validMoves) {
			if (move != null) {
				ArrayList<CubeSpace> mySpace = newSpace();
				ArrayList<Move> moves = new ArrayList<Move>();
				moves.add(move);
				belegePositionen(move, mySpace);
				doSomething(mySpace, number + 1, moves);
			}
		}
	}

	public synchronized void doSomething(ArrayList<CubeSpace> space, int number, ArrayList<Move> lastMoves) {
//		loops++;
//		countNumbers(number);
//		if(loops%1000000==0){
//			printNumbers();
//		}
		if (number>=39) {
			System.out.println("____________________________________________________________________________________________________");
//			printNumbers();
			printMoves(lastMoves);
			System.out.println("____________________________________________________________________________________________________");
		}
		if (number<39) {
			Move lastMove = lastMoves.get(lastMoves.size() - 1);
			int size = sizes[number];
			String lastDirection = lastMove.getDirection();
			String[] nextDirections = null;
			int lastSize = lastMove.getSize();
			int x = lastMove.getX();
			int y = lastMove.getY();
			int z = lastMove.getZ();
			switch (lastDirection) {
			case "+x":
				x = x + lastSize;
				String[] next = { "+y", "-y", "+z", "-z" };
				nextDirections = next;
				break;
			case "-x":
				x = x - lastSize;
				String[] next2 = { "+y", "-y", "+z", "-z" };
				nextDirections = next2;
				break;
			case "+y":
				y = y + lastSize;
				String[] next3 = { "+x", "-x", "+z", "-z" };
				nextDirections = next3;
				break;
			case "-y":
				y = y - lastSize;
				String[] next4 = { "+x", "-x", "+z", "-z" };
				nextDirections = next4;
				break;
			case "+z":
				z = z + lastSize;
				String[] next5 = { "+x", "-x", "+y", "-y" };
				nextDirections = next5;
				break;
			case "-z":
				z = z - lastSize;
				String[] next6 = { "+x", "-x", "+y", "-y" };
				nextDirections = next6;
				break;
			}
			ArrayList<Move> validMoves = new ArrayList<Move>();
			for (String direction : nextDirections) {
				if (isFree(space, x, y, z, direction, size)) {
					validMoves.add(new Move(x, y, z, direction, size));
				}
			}
			for (Move move : validMoves) {
				belegePositionen(move, space);
				lastMoves.add(move);
				doSomething(space, number + 1, lastMoves);
				lastMoves.remove(move);
				gebePositionenFrei(move, space);
			} 
		}


	}

	public synchronized void printMoves(ArrayList<Move> lastMoves) {
		for (Move move : lastMoves) {
			System.out.println("X:" + move.getX() + " Y:" + move.getY() + " Z:" + move.getZ() + " ->"
					+ move.getDirection() + "   " + move.getSize());
		}
	}

	public synchronized boolean isFree(ArrayList<CubeSpace> cubeSpace, int x, int y, int z, String direction, int amount) {
		CubeSpace cube = findCubeByName(cubeSpace, "" + x + y + z);
		if(cube==null){
			return false;
		}
		if (!cube.isFree()) {
			return false;
		}
		switch (direction) {
		case "+x":
			for (int i = amount; i > 0; i--) {
				x++;
				if(!isFreeCube(cubeSpace,x,y,z)){
					return false;
				}
			}
			return true;
		case "-x":
			for (int i = amount; i > 0; i--) {
				x--;
				if(!isFreeCube(cubeSpace,x,y,z)){
					return false;
				}
			}
			return true;
		case "+y":
			for (int i = amount; i > 0; i--) {
				y++;
				if(!isFreeCube(cubeSpace,x,y,z)){
					return false;
				}
			}
			return true;
		case "-y":
			for (int i = amount; i > 0; i--) {
				y--;
				if(!isFreeCube(cubeSpace,x,y,z)){
					return false;
				}
			}
			return true;
		case "+z":
			for (int i = amount; i > 0; i--) {
				z++;
				if(!isFreeCube(cubeSpace,x,y,z)){
					return false;
				}
			}
			return true;
		case "-z":
			for (int i = amount; i > 0; i--) {
				z--;
				if(!isFreeCube(cubeSpace,x,y,z)){
					return false;
				}
			}
			return true;

		}

		return false;
	}

	public synchronized void belegePositionen(Move move, ArrayList<CubeSpace> space) {
		String direction = move.getDirection();
		int amount = move.getSize();
		int x = move.getX();
		int y = move.getY();
		int z = move.getZ();
		CubeSpace cube = findCubeByName(space, "" + x + y + z);
		cube.setFree(false);
		switch (direction) {
		case "+x":
			for (int i = amount - 1; i > 0; i--) {
				x++;
				findCubeByName(space, "" + x + y + z).setFree(false);
			}
			break;
		case "-x":
			for (int i = amount - 1; i > 0; i--) {
				x--;
				findCubeByName(space, "" + x + y + z).setFree(false);
			}
			break;
		case "+y":
			for (int i = amount - 1; i > 0; i--) {
				y++;
				findCubeByName(space, "" + x + y + z).setFree(false);
			}
			break;
		case "-y":
			for (int i = amount - 1; i > 0; i--) {
				y--;
				findCubeByName(space, "" + x + y + z).setFree(false);
			}
			break;
		case "+z":
			for (int i = amount - 1; i > 0; i--) {
				z++;
				findCubeByName(space, "" + x + y + z).setFree(false);
			}
			break;
		case "-z":
			for (int i = amount - 1; i > 0; i--) {
				z--;
				findCubeByName(space, "" + x + y + z).setFree(false);
			}
			break;
		}
	}
	
	public synchronized boolean isFreeCube(ArrayList<CubeSpace> cubeSpace,int x, int y, int z){
		CubeSpace cube = findCubeByName(cubeSpace, "" + x + y + z);
		if (cube == null || !cube.isFree()) {
			return false;
		}
		return true;
	}

	public synchronized void gebePositionenFrei(Move move, ArrayList<CubeSpace> space) {
		String direction = move.getDirection();
		int amount = move.getSize();
		int x = move.getX();
		int y = move.getY();
		int z = move.getZ();
		CubeSpace cube = findCubeByName(space, "" + x + y + z);
		cube.setFree(true);
		switch (direction) {
		case "+x":
			for (int i = amount; i > 0; i--) {
				x++;
				findCubeByName(space, "" + x + y + z).setFree(true);
			}
			break;
		case "-x":
			for (int i = amount; i > 0; i--) {
				x--;
				findCubeByName(space, "" + x + y + z).setFree(true);
			}
			break;
		case "+y":
			for (int i = amount; i > 0; i--) {
				y++;
				findCubeByName(space, "" + x + y + z).setFree(true);
			}
			break;
		case "-y":
			for (int i = amount; i > 0; i--) {
				y--;
				findCubeByName(space, "" + x + y + z).setFree(true);
			}
			break;
		case "+z":
			for (int i = amount; i > 0; i--) {
				z++;
				findCubeByName(space, "" + x + y + z).setFree(true);
			}
			break;
		case "-z":
			for (int i = amount; i > 0; i--) {
				z--;
				findCubeByName(space, "" + x + y + z).setFree(true);
			}
			break;
		}
	}

	public synchronized CubeSpace findCubeByName(ArrayList<CubeSpace> space, String name) {
		for (CubeSpace cube : space) {
			if (cube.getName().equals(name)) {
				return cube;
			}
		}
		return null;
	}

	public synchronized ArrayList<CubeSpace> newSpace() {
		ArrayList<CubeSpace> space = new ArrayList<CubeSpace>();
		for (int x = 1; x < 5; x++) {
			for (int y = 1; y < 5; y++) {
				for (int z = 1; z < 5; z++) {
					space.add(new CubeSpace("" + x + y + z));
				}
			}
		}
		return space;
	}
}
