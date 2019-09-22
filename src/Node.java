import java.util.ArrayList;

public class Node {
	
	Board state;
	int turn = 0;
	int value;
	boolean blackwin;
	boolean whitewin;
//note: it doesn't matter what role AI is, just play with min or max. 
	//we need result(s,a), action(s), isTerminal(s), which returns an int.
		//minimax
	public int isTerminal(Board state) {
		state.check(state.gameBoard);
	}
	
	public int minimax(Board board) {
		if(!node.game.check(board.)){
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
