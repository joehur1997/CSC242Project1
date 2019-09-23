import java.util.ArrayList;
import java.util.Random;

public class State {

	Board state;
	boolean whiteMove;
	boolean blackMove;
	String bestMoves;
	int turn = 0;
	int value;
	public State(Board state, String bestMoves) {
		this.state = state;
		this.bestMoves = bestMoves;

	}
	//note: it doesn't matter what role AI is, just play with min or max. 
	//we need result(s,a), action(s), isTerminal(s), which returns an int.
	//minimax
	public int isTerminal(Board state) {
		state.score = 0;
		if(turn%2 == 0) {
			blackMove = true;
			whiteMove = !blackMove;
		}
		else {
			whiteMove = true;
			blackMove = !whiteMove;
		}
		if(state.check(state.gameBoard)) {
			if(state.blackwin == true && state.whitewin == false) {
				state.score = 2;
			}
			if(state.blackwin == false && state.whitewin == true) {
				state.score = -2;
			}
			if(state.blackwin == false && state.whitewin == false) {
				state.score = 0;
			}
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
			bestMove =  getActions(state, maxValue(state));
		}

		if(turn %2 != 0) {
			System.out.println("Best move for white is: ");
			bestMove = getActions(state, minValue(state));
		}
		System.out.println(bestMove);
		return bestMove;
	}
	public String getActions(Board board, int score) {
		ArrayList<String> bestActions = new ArrayList<String>();
		for(Piece[] row: board.gameBoard) {
			for(Piece piece: row) {
				if(whiteMove) {
					if(piece.color == 'w') {
						for(String moves: piece.avalMoves) {
							if(board.futureState(moves, board).score == score) {
								bestActions.add(moves);
							}
						}
					}
				}
				if(blackMove) {
					if(piece.color == 'b') {
						for(String moves: piece.avalMoves) {
							if(board.futureState(moves, board).score == score) {
								bestActions.add(moves);
							}
						}
					}
				}
			}
		}
		Random rng = new Random();
		int pick = rng.nextInt(bestActions.size());
		return bestActions.get(pick);

	}
	//max and min value
	public int maxValue(Board state) {
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
						if(minValue(result) > v) {

						}
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

	//alpha-beta pruning
	public String alphaBeta(Board state) {
		String bestMove = "none";
		//initial state, black move first 
	
		if(turn %2 == 0) {
			int v = abMaxValue(state, Integer.MAX_VALUE, Integer.MIN_VALUE);
			System.out.println("Best move for black is: ");
			bestMove =  getActions(state, v);
	
		}
		if(turn %2 != 0) {		
			int v = abMaxValue(state, Integer.MAX_VALUE, Integer.MIN_VALUE);
			System.out.println("Best move for white is: ");
			bestMove = getActions(state, abMinValue(v));
	
	
		}
		System.out.println(bestMove);
		return bestMove;
	}
	public int abMaxValue(Board state, int alpha, int beta) {
		if(!state.check(state.gameBoard)) {
			isTerminal(state);
			return state.score;
		}
		int v = Integer.MIN_VALUE;
		for(Piece[] row: state.gameBoard) {
			for(Piece piece: row) {
				if(piece.color == 'b') {
					for(String moves: piece.avalMoves) {
						v = Math.max(v, abMinValue(state.futureState(moves, state), alpha, beta));
						if(v >= beta) {return v;}
						alpha = Math.max(alpha, v);
					}
				}
			}
		}
		return v;
	}
	public int abMinValue(Board state, int alpha, int beta) {
		if(!state.check(state.gameBoard)) {
			isTerminal(state);
			return state.score;
		}
		int v = Integer.MAX_VALUE;
		for(Piece[] row: state.gameBoard) {
			for(Piece piece: row) {
				if(piece.color == 'w') {
					for(String moves: piece.avalMoves) {
						v = Math.min(v, abMaxValue(state.futureState(moves, state), alpha, beta));
						if(v <= alpha) {return v;}
						beta = Math.min(beta, v);

					}
				}
			}
		}
		return v;
	}

	public static void main(String[] args) {


	}

}
