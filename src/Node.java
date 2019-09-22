import java.util.ArrayList;

public class Node {
	char aiColor;
	Piece[][] state;
	int turn = 0;
	int value;
	ArrayList<Node> children;
	Board game;

	public Node(Piece[][] state, int value) {
		this.state = state;
		this.value = value;
		//hard coded; can change later.
		game = new Board(1);
		//i don't really have a way to test this yet.
		children = new ArrayList<Node>();
		getChildren();
	}

	public int compareTo(Node o) {

		return this.value - o.value;
	}

	public boolean isLeaf() {
		return (children == null) && !(game.check(state));
	}
	//	this is the idea, if you get it
	public void getChildren() {
		//Black's turn
		if(turn % 2 == 0) {
			for(Piece[] row: state) {
				for(Piece piece : row) {
					if(piece.color == 'b') {
						//haven't done the setup for available moves for piece yet. I'm a bit confused with ur code so ama wait.
						for(String moves: piece.avalMoves) {
							//haven't decided on a heuristic function or how to determine terminal states and stuff yet. Sorry for being jank right now it'll get better
							int value = 0;

							Piece[][] nextState = game.futureState(moves, game);
							children.add(new Node(nextState, value));
						}
					}
				}
			}
		}
	}
	//minimax
	public int minimax(Node node) {
		if(!node.game.check(node.state)){
			return node.value;
		}
		if() {

		}
	}
	//returns move thats best
	public String minimaxDec(Piece[][] state){
		return "";
	}
	public int maxValue(Piece[][] state) {
		if(!game.check(state)) {
			return value;
		}
		int v = Integer.MIN_VALUE;
		for(Piece[] row: state) {
			for(Piece piece: row) {
				for(String moves: piece.avalMoves) {
					v = Math.max(v, minValue(game.futureState(moves, game)));
				}
			}
		}
		return v;
	}
	public int minValue(Piece[][] state) {
		if(!game.check(state)) {
			return value;
		}
		int v = Integer.MAX_VALUE;
		for(Piece[] row: state) {
			for(Piece piece: row) {
				for(String moves: piece.avalMoves) {
					v = Math.min(v, maxValue(game.futureState(moves, game)));
				}
			}
		}
		return v;
	}


	public void hminimax() {

	}
	public static void main(String[] args) {

	}

}
