import java.util.ArrayList;

public class Piece {

	char role = 'p';
	char name;
	ArrayList<String> captures;
	ArrayList<String> avalMoves;
	String pos;
	int x;
	int y;

	public Piece(char role, char name, int x, int y) {
		//four by four, will need better methods to do this task, but want to code in a way that makes sense right off the bat
		this.name = name;
		this.x = x;
		this.y = y;
		

	}
	public Piece(int x, int y) {
		this.name = '\0';
		this.x = x;
		this.y = y;
		role = '\0';
		this.avalMoves = null;
	}
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}
	public char getName() {
		return name;
	}
	public String getPos() {
		char[] vert = {'a','b','c','d'};

		return vert[y] + "" + x;
	}
	public void getMoves() {
		char[] vert = {'a','b','c','d'};
		if(role == 'p' || role == 'k') {
			if(name == 'b') {
				// not edge case moves.
				if(x > 0 && x < 4) {
					if(y > 0 && y < 4) {
						avalMoves.add(vert[(y+1)] + "" + (x+1));
						avalMoves.add(vert[(y+1)] + "" + (x-1));
					}
				}
				//edge moves
				if(x == 0) {
					avalMoves.add(vert[(y+1)] + "" + (x+1));
				}
				if(x == 3) {
					avalMoves.add(vert[(y+1)] + "" + (x-1));
				}
			}	
			if(name == 'w') {
				if(x > 0 && x < 4) {
					if(y > 0 && y < 4) {
						avalMoves.add(vert[(y-1)] + ":" + (x+1));
						avalMoves.add(vert[(y-1)] + ":" + (x-1));
					}
				}
				//edge moves
				if(x == 0) {
					avalMoves.add(vert[(y-1)] + ":" + (x+1));
				}
				if(x == 3) {
					avalMoves.add(vert[(y-1)] + ":" + (x-1));
				}
			}
		}
	}
	public void update(String pos) {
		char[] vert = {'a','b','c','d'};
		int newX = Character.getNumericValue(pos.charAt(1));
		int newY = getIndex(vert, pos.charAt(0));
		setX(newX);
		setY(newY);
		System.out.println(getPos());
	}

	public static int getIndex(char[] arr, char word) {
		int sol = -1;
		for(int i = 0; i < arr.length; i++) {
			if(word == arr[i]) {
				sol = i;
			}
		}
		return sol;

	}
	public static void main(String[] args) {

	}
}
