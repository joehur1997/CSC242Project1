public class Player {
	
	String color;
	char piece;
	
	public Player(String color) {
		if(color.equalsIgnoreCase("black")) {
			this.piece = 'b';
		}
		else {
			this.piece = 'w';
		}
	}

}