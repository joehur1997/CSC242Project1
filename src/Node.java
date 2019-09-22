import java.util.ArrayList;

public class Node {

	Board state;
	int turn = 0;
	int value;
	public Node(Board state) {

	}
	//note: it doesn't matter what role AI is, just play with min or max. 
	//we need result(s,a), action(s), isTerminal(s), which returns an int.
	//minimax
	public int isTerminal(Board state) {
		state.score = 0;
		state.check(state.gameBoard);
		if(state.blackwin == true && state.whitewin == false) {
			state.score = 1;
		}
		if(state.blackwin == false && state.whitewin == true) {
			state.score = -1;
		}
		if(state.blackwin == false && state.whitewin == false) {
			state.score = 0;
		}
		return state.score;

	}
	//black will be max, white will be min.

	//returns move thats best
	public String minimaxDec(Board state) {
		String bestMove = "none";
		//initial state, black move first 
		if(turn %2 == 0) {
			System.out.println("Best move for black is: ");
			System.out.println(maxValue);
		}

		if(turn %2 != 0) {
			System.out.println("Best move for black is: ");

		}
	}
	//max and min value
	public int maxValue(Board state) {
		String bestMove = "none";
		if(!state.check(state.gameBoard)) {
			isTerminal(state);
			return state.score;
		}
		int v = Integer.MIN_VALUE;
		for(Piece[] row: state.gameBoard) {
			for(Piece piece: row) {
				if(piece.color == 'w') {
					for(String moves: piece.avalMoves) {
						Board result = state.futureState(moves, state);
						v = Math.max(v, minValue(result));
					}
				}
			}
		}
		return v;
	}
	public int minValue(Board state) {
		if(!state.check(state.gameBoard)) {
			isTerminal(state);
			return state.score;
		}
		int v = Integer.MAX_VALUE;
		for(Piece[] row: state.gameBoard) {
			for(Piece piece: row) {
				if(piece.color == 'b') {
					for(String moves: piece.avalMoves) {
						Board result = state.futureState(moves, state);
						v = Math.min(v, maxValue(result));
					}
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
