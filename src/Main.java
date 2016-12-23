import java.util.ArrayList;
import java.util.Collections;

public class Main {
	public int[] sizes = { 3, 1, 3, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 3, 1, 2, 2, 3, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1,
			3, 1, 3, 1, 3, 3, 3, 2 };
	public String[] directions = { "+x", "-x", "+y", "-y", "+z", "-z" };

	public static void main(String[] args) {

		new Main().start();
	}

	public void start() {

		ArrayList<CubeSpace> space = newSpace();
		doSomething(space, 0);
		// int size = sizes[0];
		// for (int x = 1; x < 5; x++) {
		// for (int y = 1; y < 5; y++) {
		// for (int z = 1; z < 5; z++) {
		// if(findCubeByName(space,"" + x + y + z).isFree){
		// for(String direction : directions){
		// if(isFree(x,y,z,direction,size)){
		// validMoves.add(new Move(x,y,z,direction,size,0));
		// }
		// }
		// for(Move move : validMoves){
		// ArrayList<CubeSpace> mySpace = new ArrayList<CubeSpace>();
		// makeMove(move,mySpace);
		// }
		//
		// }
		// }
		// }
		// }

	}

	public synchronized void doSomething(ArrayList<CubeSpace> space, int number, ArrayList<Move> lastMoves) {
		if (number == 30) {
			printMoves(lastMoves);
		}
		Move lastMove = lastMoves.get(lastMoves.size() - 1);
		int size = sizes[number];
		String lastDirection = lastMove.getDirection();
		String[] nextDirections = null;;
		String[] names = new String[4];
		int x = lastMove.getX();
		int y = lastMove.getY();
		int z = lastMove.getZ();
		int x1;
		int x2;
		int y1;
		int y2;
		int z1;
		int z2;
		switch (lastDirection) {
		case "+x":
			String[] next = {"+y","-y","+z","-z"};
			nextDirections = next;
			x = x + lastMove.getSize();
			y1 = y + 1;
			y2 = y - 1;
			z1 = z + 1;
			z2 = z - 1;
			names[0] = "" + x + y1 + z;
			names[1] = "" + x + y2 + z;
			names[2] = "" + x + y + z1;
			names[3] = "" + x + y + z2;
			break;
		case "-x":
			String[] next2 = {"+y","-y","+z","-z"};
			nextDirections = next2;
			x = x - lastMove.getSize();
			y1 = y + 1;
			y2 = y - 1;
			z1 = z + 1;
			z2 = z - 1;
			names[0] = "" + x + y1 + z;
			names[1] = "" + x + y2 + z;
			names[2] = "" + x + y + z1;
			names[3] = "" + x + y + z2;
			break;
		case "+y":
			String[] next3 = {"+x","-x","+z","-z"};
			nextDirections = next3;
			y = y + lastMove.getSize();
			x1 = x + 1;
			x2 = x - 1;
			z1 = z + 1;
			z2 = z - 1;
			names[0] = "" + x1 + y + z;
			names[1] = "" + x2 + y + z;
			names[2] = "" + x + y + z1;
			names[3] = "" + x + y + z2;
			break;
		case "-y":
			String[] next4 = {"+x","-x","+z","-z"};
			nextDirections = next4;
			y = y - lastMove.getSize();
			x1 = x + 1;
			x2 = x - 1;
			z1 = z + 1;
			z2 = z - 1;
			names[0] = "" + x1 + y + z;
			names[1] = "" + x2 + y + z;
			names[2] = "" + x + y + z1;
			names[3] = "" + x + y + z2;
			break;
		case "+z":
			String[] next5 = {"+x","-x","+y","-y"};
			nextDirections = next5;
			z = z + lastMove.getSize();
			x1 = x + 1;
			x2 = x - 1;
			y1 = y + 1;
			y2 = y - 1;
			names[0] = "" + x1 + y + z;
			names[1] = "" + x2 + y + z;
			names[2] = "" + x + y1 + z;
			names[3] = "" + x + y2 + z;
			break;
		case "-z":
			String[] next6 = {"+x","-x","+y","-y"};
			nextDirections = next6;
			z = z - lastMove.getSize();
			x1 = x + 1;
			x2 = x - 1;
			y1 = z + 1;
			y2 = z - 1;
			names[0] = "" + x1 + y + z;
			names[1] = "" + x2 + y + z;
			names[2] = "" + x + y1 + z;
			names[3] = "" + x + y2 + z;
			break;
		}
		ArrayList<Move> validMoves = new ArrayList<Move>();
		for (String name : names) {
			CubeSpace cube = findCubeByName(space, name);
			if (cube != null && cube.isFree) {
				for (String direction : nextDirections) {
					if (isFree(space, x, y, z, direction, size)) {
						validMoves.add(new Move(x, y, z, direction, size, 0));
					}
				}
			}
		}
		validMoves.removeAll(Collections.singleton(null));  
		for (Move move : validMoves) {
			belegePositionen(move, space);
			lastMoves.add(move);
			doSomething(space, number + 1, lastMoves);
			lastMoves.remove(move);
			gebePositionenFrei(move, space);
		}

	}

	private synchronized void printMoves(ArrayList<Move> lastMoves) {
		for (Move move : lastMoves) {
			System.out.println("X:" + move.getX() + " Y:" + move.getY() + " Z:" + move.getZ() + " ->"
					+ move.getDirection() + "   " + move.getSize());
		}
	}

	public synchronized void doSomething(ArrayList<CubeSpace> space, int number) {
		int size = sizes[number];
		ArrayList<Move> validMoves = new ArrayList<Move>();
		for (int x = 1; x < 5; x++) {
			for (int y = 1; y < 5; y++) {
				for (int z = 1; z < 5; z++) {
					if (findCubeByName(space, "" + x + y + z).isFree) {
						for (String direction : directions) {
							if (isFree(space, x, y, z, direction, size)) {
								validMoves.add(new Move(x, y, z, direction, size, 0));
							}
						}
					}
				}
			}
		}
		validMoves.removeAll(Collections.singleton(null));  
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

	private synchronized boolean isFree(ArrayList<CubeSpace> space, int x, int y, int z, String direction, int amount) {
		CubeSpace cube = findCubeByName(space, "" + x + y + z);
		if (!cube.isFree()) {
			return false;
		}
		switch (direction) {
		case "+x":
			if(amount-1==0){
				return false;
			}
			for (int i = amount - 1; i > 0; i--) {
				x++;
				CubeSpace nextCube = findCubeByName(space, "" + x + y + z);
				if (nextCube == null || !nextCube.isFree()) {
					return false;
				}
			}
			return true;
		case "-x":
			if(amount-1==0){
				return false;
			}
			for (int i = amount - 1; i > 0; i--) {
				x--;
				CubeSpace nextCube = findCubeByName(space, "" + x + y + z);
				if (nextCube == null || !nextCube.isFree()) {
					return false;
				}
			}
			return true;
		case "+y":
			if(amount-1==0){
				return false;
			}
			for (int i = amount - 1; i > 0; i--) {
				y++;
				CubeSpace nextCube = findCubeByName(space, "" + x + y + z);
				if (nextCube == null || !nextCube.isFree()) {
					return false;
				}
			}
			return true;
		case "-y":
			if(amount-1==0){
				return false;
			}
			for (int i = amount - 1; i > 0; i--) {
				y--;
				CubeSpace nextCube = findCubeByName(space, "" + x + y + z);
				if (nextCube == null || !nextCube.isFree()) {
					return false;
				}
			}
			return true;
		case "+z":
			if(amount-1==0){
				return false;
			}
			for (int i = amount - 1; i > 0; i--) {
				z++;
				CubeSpace nextCube = findCubeByName(space, "" + x + y + z);
				if (nextCube == null || !nextCube.isFree()) {
					return false;
				}
			}
			return true;
		case "-z":
			if(amount-1==0){
				return false;
			}
			for (int i = amount - 1; i > 0; i--) {
				z--;
				CubeSpace nextCube = findCubeByName(space, "" + x + y + z);
				if (nextCube == null || !nextCube.isFree()) {
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
		int z = move.getY();
		CubeSpace cube = findCubeByName(space, "" + x + y + z);
		cube.setFree(false);
		switch (direction) {
		case "+x":
			for (int i = amount - 1; i > 0; i--) {
				x++;
				CubeSpace nextCube = findCubeByName(space, "" + x + y + z);
				nextCube.setFree(false);
			}
			break;
		case "-x":
			for (int i = amount - 1; i > 0; i--) {
				x--;
				CubeSpace nextCube = findCubeByName(space, "" + x + y + z);
				nextCube.setFree(false);
			}
			break;
		case "+y":
			for (int i = amount - 1; i > 0; i--) {
				y++;
				CubeSpace nextCube = findCubeByName(space, "" + x + y + z);
				nextCube.setFree(false);
			}
			break;
		case "-y":
			for (int i = amount - 1; i > 0; i--) {
				y--;
				CubeSpace nextCube = findCubeByName(space, "" + x + y + z);
				nextCube.setFree(false);
			}
			break;
		case "+z":
			for (int i = amount - 1; i > 0; i--) {
				z++;
				CubeSpace nextCube = findCubeByName(space, "" + x + y + z);
				nextCube.setFree(false);
			}
			break;
		case "-z":
			for (int i = amount - 1; i > 0; i--) {
				z--;
				CubeSpace nextCube = findCubeByName(space, "" + x + y + z);
				nextCube.setFree(false);
			}
			break;
		}
	}

	public synchronized void gebePositionenFrei(Move move, ArrayList<CubeSpace> space) {
		String direction = move.getDirection();
		int amount = move.getSize();
		int x = move.getX();
		int y = move.getY();
		int z = move.getY();
		CubeSpace cube = findCubeByName(space, "" + x + y + z);
		cube.setFree(true);
		switch (direction) {
		case "+x":
			for (int i = amount - 1; i > 0; i--) {
				x++;
				CubeSpace nextCube = findCubeByName(space, "" + x + y + z);
				nextCube.setFree(true);
			}
			break;
		case "-x":
			for (int i = amount - 1; i > 0; i--) {
				x--;
				CubeSpace nextCube = findCubeByName(space, "" + x + y + z);
				nextCube.setFree(true);
			}
			break;
		case "+y":
			for (int i = amount - 1; i > 0; i--) {
				y++;
				CubeSpace nextCube = findCubeByName(space, "" + x + y + z);
				nextCube.setFree(true);
			}
			break;
		case "-y":
			for (int i = amount - 1; i > 0; i--) {
				y--;
				CubeSpace nextCube = findCubeByName(space, "" + x + y + z);
				nextCube.setFree(true);
			}
			break;
		case "+z":
			for (int i = amount - 1; i > 0; i--) {
				z++;
				CubeSpace nextCube = findCubeByName(space, "" + x + y + z);
				nextCube.setFree(true);
			}
			break;
		case "-z":
			for (int i = amount - 1; i > 0; i--) {
				z--;
				CubeSpace nextCube = findCubeByName(space, "" + x + y + z);
				nextCube.setFree(true);
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
