import java.util.ArrayList;

public class Piece {
	char role;
	char color;
	int x;
	int y;
	int[] position = {x,y};
	boolean isEmpty;
	ArrayList<String> avalMoves;
	
	public Piece (char role, char color, int x, int y) {
		this.role = role;
		this.color = color;
		this.x = x;
		this.y = y;
		position[0]=x;
		position[1]=y;
		isEmpty=false;
	}
	
	public Piece (int x, int y) {
		this.x = x;
		this.y = y;
		position[0]=x;
		position[1]=y;
		isEmpty=true;
	}
	
	public void setRole(char role) {
		this.role = role;
	}
	
	public void setColor(char color) {
		this.color=color;
	}
	
	public void setX(int x) {
		this.x = x;
		position[0]=x;
	}
	
	public void setY(int y) {
		this.y=y;
		position[1]=y;
	}
	
	public void setAbsPosition(int x, int y) {
		this.x=x;
		this.y=y;
		position[0]=x;
		position[1]=y;
	}
	
	public void setIsEmpty(boolean isEmpty) {
		this.isEmpty=isEmpty;
	}
	
	public char getRole() {
		return role;
	}
	
	public char getColor() {
		return color;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int[] getPosition() {
		return position;
	}
	
	public void clearPiece() {
		role='\0';
		color='\0';
		this.isEmpty=true;
	}
	
	public void updatePiece(Piece piece) {
		this.setIsEmpty(false);
		this.role=piece.role;
		this.color=piece.color;
	}
}
