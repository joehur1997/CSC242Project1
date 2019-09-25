import java.util.ArrayList;
import java.util.Random;

public class AI {

	Board state;
	boolean whiteMove;
	boolean blackMove;
	int turn = 0;
	int value;
	int algo;
	String movePlayed;
	public AI(Board state, int algo) {
		this.state = state;
		if(algo == 1) {
			movePlayed = randMove(state);
		}
		if(algo == 2) {
			movePlayed = minimaxDec(state);
		}
		if(algo == 3) {
			movePlayed = alphaBeta(state);
		}
		if(algo == 4) {
			movePlayed == hminiMax
		}
	}
	public void setBoard(Board state) {
		this.state = state;
	}
	//note: it doesn't matter what role AI is, just play with min or max. 
	//we need result(s,a), action(s), isTerminal(s), which returns an int.
	public String randMove(Board state) {
		Random rnd = new Random();
		ArrayList<String> randomMoveList = new ArrayList<String>();
		if(state.turn % 2 == 0) {
			for(Piece[] row: state.gameBoard) {
				for(Piece piece: row) {
					if(piece.color == 'b') {
						for(String moves: piece.avalMoves) {
							randomMoveList.add(moves);
						}
					}
				}
			}
		}
		else {
			for(Piece[] row: state.gameBoard) {
				for(Piece piece: row) {
					if(piece.color == 'w') {
						for(String moves: piece.avalMoves) {
							randomMoveList.add(moves);
						}
					}
				}
			}
		}
		int rng = rnd.nextInt(randomMoveList.size() -1);
		return randomMoveList.get(rng);
	}
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
				state.score = 1;
			}
			if(state.blackwin == false && state.whitewin == true) {
				state.score = -1;
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
				if(piece.color == 'b') {
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
				if(piece.color == 'w') {
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
			bestMove = getActions(state, v);


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


	//functions needed for heuristics:
	//number of pieces(color)
	public int numOfpieces(char color, Board state) {
		int i = 0;
		if(color == 'b') {
			for(Piece[] row: state.gameBoard) {
				for(Piece piece: row) {
					if(piece.color == 'b') {
						i++;
					}
				}
			}
		}
		if(color == 'w') {
			for(Piece[] row: state.gameBoard) {
				for(Piece piece: row) {
					if(piece.color == 'w') {
						i++;
					}
				}
			}
		}
		else {
			System.out.println("there are no such pieces.");
			i = 0;
		}
		return i;
	}
	//total piece value(color)
	public int pieceValue(char color, Board state) {
		int i = 0;
		if(color == 'b') {
			for(Piece[] row: state.gameBoard) {
				for(Piece piece: row) {
					if(piece.color == 'b') {
						if(piece.role == 'p') {
							i++;
						}
						if(piece.role == 'k') {
							i+= (int) 1.5;
						}
					}
				}
			}
		}
		if(color == 'w') {
			for(Piece[] row: state.gameBoard) {
				for(Piece piece: row) {
					if(piece.color == 'w') {
						if(piece.role == 'p') {
							i++;
						}
						if(piece.role == 'k') {
							i+= (int) 1.5;
						}
					}
				}
			}
		}
		else {
			System.out.println("there are no such pieces.");
			i = 0;
		}
		return i;
	}
	public int cutOff(Board state) {
		int eval = 0;
		if(turn %2 == 0) {
			eval = pieceValue('b', state) + numOfpieces('b', state);
		}
		if(turn %2 != 0) {
			eval = pieceValue('b', state) + numOfpieces('b', state);
		}
		return eval;

	}
	public int hminValue(Board state, int depth) {
		int i = 0;
		while (i < depth) {
			int v = Integer.MAX_VALUE;
			for(Piece[] row: state.gameBoard) {
				for(Piece piece: row) {
					if(piece.color == 'b') {
						for(String moves: piece.avalMoves) {
							Board result = state.futureState(moves, state);
							v = Math.min(v, hmaxValue(result, i+1));
						}
					}
				}
			}	
		}

		return v;
	}
	public int hminValue(Board state, int depth) {
		int i = 0;
		while (i < depth) {
			int v = Integer.MAX_VALUE;
			for(Piece[] row: state.gameBoard) {
				for(Piece piece: row) {
					if(piece.color == 'b') {
						for(String moves: piece.avalMoves) {
							Board result = state.futureState(moves, state);
							v = Math.min(v, hminValue(result, i));
						}
					}
				}
			}	
		}

		return v;
	}


	public static void main(String[] args) {


	}

}
