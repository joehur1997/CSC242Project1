import java.util.ArrayList;

public class Node {
	Piece[][] state;
	int turn = 0;
	int value;
	ArrayList<Node> children = null;
	Board game;

	public Node(Piece[][] state, int value) {
		this.state = state;
		this.value = value;
		//hard coded; can change later.
		game = new Board(1);
		//i don't really have a way to test this yet.
		getChildren();
	}

	public int compareTo(Node o) {

		return this.value - o.value;
	}

	public boolean isLeaf() {
		return children == null;
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
	//have not defined loss or win state yet, so can't do minimax (yet)
	//minimax
	public void minimax(Piece[][] state) {
		
	}
	public void hminimax() {
		
	}
	public static void main(String[] args) {
		
	}

}
