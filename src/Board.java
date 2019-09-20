public class Board {
	public static char[] vertical = {'a','b','c','d','e','f','g','h'};
	Piece[][] gameBoard;
	char[][] represent;
	int turn = 0;
	boolean running = true;
	
	public Board(int size) {
		int rep=0;
		if (size==1) {
			rep=10;
		} else if (size==2) {
			rep=18;
		}
		represent = new char[rep][rep];
		//character representation for board setup
		for(int i = 0; i < represent.length; i++) {
			for(int j = 0; j < represent.length; j++) {
				if(i == 0) {
					if(j >= 1 && j%2 == 0) {
						//							System.out.print((j/2));
						represent[i][j] = Character.forDigit((j/2), rep);
					}
					else {
						//							System.out.print(" ");
						represent[i][j] = ('\0');
					}
				}
				if(i >= 1 &&  i%2 != 0) {

					if(j%2 == 0 && j > 1) {
						//							System.out.print("-");
						represent[i][j] = '-';

					}
					else if (j%2 != 0 && j >=  1) {
						//							System.out.print("+");
						represent[i][j] = '+';
					}
					else {
						//							System.out.print(" ");
						represent[i][j] = ' ';
					}
				}
				if(i >= 1 && i%2 == 0) {
					if(j%2 != 0) {
						//							System.out.print("|");
						represent[i][j] = '|';
					}
					if(j%2 == 0 && j == 0) {
						if (size==1) {
							char[] letters = {'A', 'B', 'C', 'D'};
							//						System.out.print(letters[(i-2)/2]);
							represent[i][j] = letters[(i-2)/2];
						} else if (size==2) {
							char[] letters = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'};
							//						System.out.print(letters[(i-2)/2]);
							represent[i][j] = letters[(i-2)/2];
						}
					}
					if(j%2 == 0 && j > 0) {
						//							System.out.print(" ");
						represent[i][j] = ' ';
					}
				}
			}
			//				System.out.println("");
		}
		switch (size) {
		case 1:
			gameBoard = new Piece[4][4];
			for(int i = 0; i < gameBoard.length; i++) {
				for(int j = 0; j < gameBoard.length; j++) {
					gameBoard[i][j] = new Piece(i,j);
				}
			}
			//init for pieces
			//black
			for(int j = 1; j < gameBoard.length; j+=2) {
				gameBoard[0][j] = new Piece('p','b',2,j);
			}
			//white
			for(int j = 0; j < gameBoard.length; j+=2) {
				gameBoard[3][j] = new Piece('p','w',8,j);
			}
			break;
		case 2:
			gameBoard = new Piece[8][8];
			for(int i = 0; i < gameBoard.length; i++) {
				for(int j = 0; j < gameBoard.length; j++) {
					gameBoard[i][j] = new Piece(i,j);
				}
			}
			//init for pieces
			//black
			for(int j = 1; j < gameBoard.length; j+=2) {
				gameBoard[0][j] = new Piece('p','b',2,j);
				gameBoard[1][j-1] = new Piece('p','b',2,j);
				gameBoard[2][j] = new Piece('p','b',2,j);
			}
			//white
			for(int j = 0; j < gameBoard.length; j+=2) {
				gameBoard[5][j] = new Piece('p','w',8,j);
				gameBoard[6][j+1] = new Piece('p','w',8,j);
				gameBoard[7][j] = new Piece('p','w',8,j);
			}
			break;
		}
		
	}
	
	public void movePiece(Piece moved, String newPos) {
		int tempX = moved.x;
		int tempY = moved.y;
		moved.update(newPos);
		gameBoard[moved.x][moved.y] = moved;
		gameBoard[tempX][tempY] = new Piece(tempX, tempY);

	}
	public void scale() {
		for(int i = 0; i < gameBoard.length; i++) {
			for(int j = 0; j < gameBoard.length; j++) {
				represent[2*i+2][2*j+2] = gameBoard[i][j].name;
			}
		}
	}
	//method for both determining legal and out of bounds moves

	public boolean legalMove(String move, Piece piece) {
		boolean legal = false;
		char[] vert = {'a','b','c','d'};
		int x = Character.digit(move.charAt(1), 10);
		int y = getIndex(vert, move.charAt(1));
		if((x > gameBoard.length || x < 0) || (y > gameBoard.length || y < 0 )) {
			legal = true;
		}
		if((x < gameBoard.length || x > 0) || (y < gameBoard.length || y > 0 )) {
			for(String moves: piece.avalMoves) {
				if(moves.equalsIgnoreCase(move)) {

					legal = true;
				}
			}
		}
		return legal;
	}
	public void capture(Piece piece, Piece other){
		if(piece.name == 'b') {
			for(String moves: piece.avalMoves) {
				int x = Character.digit(moves.charAt(1),10);
				int y = getIndex(vertical, moves.charAt(0));
				if(!isEmpty(y,x) && other.name != piece.name) {
					if(piece.x > other.x && other.x > 0) {
						for(String delMoves: piece.avalMoves) {
							piece.avalMoves.remove(delMoves);
						}
						piece.avalMoves.add((vertical[piece.y+2])+ "" + (piece.x + 2));
					}
					if(piece.x < other.x && other.x < gameBoard.length-1) {
						for(String delMoves: piece.avalMoves) {
							piece.avalMoves.remove(delMoves);
						}
						piece.avalMoves.add((vertical[piece.y+2])+ "" + (piece.x - 2));
					}
				}
				else {
					return;
				}
			}
		}
		if(piece.name == 'w') {
			for(String moves: piece.avalMoves) {
				int x = Character.digit(moves.charAt(1),10);
				int y = getIndex(vertical, moves.charAt(0));
				if(!isEmpty(y,x) && other.name != piece.name) {
					if(piece.x > other.x && other.x > 0) {
						for(String delMoves: piece.avalMoves) {
							piece.avalMoves.remove(delMoves);
					
						}
						if(isEmpty(y,x)) {

							piece.avalMoves.add((vertical[piece.y+2])+ "" + (piece.x + 2));
						}
					}
					if(piece.x < other.x && other.x < gameBoard.length-1) {
						for(String delMoves: piece.avalMoves) {
							piece.avalMoves.remove(delMoves);
						}
						piece.avalMoves.add((vertical[piece.y+2])+ "" + (piece.x - 2));
					}
				}
				else {
					return;
				}
			}
		}
	}
	public boolean isCapturable(Piece piece, Piece capture) {
		boolean capturable = false;
		if(piece.role == 'p') {
			if(piece.name == 'b' ) {
				for(String moves: piece.avalMoves) {
					int x = Character.digit(moves.charAt(1),10);
					int y = getIndex(vertical, moves.charAt(0));
					if(!isEmpty(y,x) && capture.name != piece.name) {
						if(piece.x < capture.x && capture.x > 0 && capture.y < 3 && capture.y > 0) {
							if(isEmpty(capture.y+1,capture.x-1)) {
								piece.captures.add(vertical[capture.y+1] + "" + (capture.x+1));
								
								capturable =  true;
							}
						}
						if(piece.x > capture.x && capture.x < gameBoard.length-1 && capture.y < 3 && capture.y > 0) {
							if(isEmpty(capture.y+1, capture.x -1)) {
								piece.captures.add(vertical[capture.y+1] + "" + (capture.x-1));
								capturable = true; 
							}
						}

						else {
							continue;
						}
					}
				}
			}
			if(piece.name == 'w' ) {
				for(String moves: piece.avalMoves) {
					int x = Character.digit(moves.charAt(1),10);
					int y = getIndex(vertical, moves.charAt(0));
					if(!isEmpty(y,x) && capture.name != piece.name) {
						if(piece.x < capture.x && capture.x > 0 && capture.y < 3 && capture.y > 0) {
							if(isEmpty(capture.y-1,capture.x+1)) {
								piece.captures.add(vertical[capture.y-1] + "" + (capture.x-1));
								capturable =  true;
							}
						}
						if(piece.x > capture.x && capture.x < gameBoard.length-1 && capture.y < 3 && capture.y > 0) {
							if(isEmpty(capture.y-1, capture.x-1)) {
								piece.captures.add(vertical[capture.y-1] + "" + (capture.x-1));
								capturable = true; 
							}
						}

						else {
							continue;
						}
					}
				}
			}
		}
		return capturable;
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
	public static void printArr(char[][] arr) {
		for(char[] row: arr) {
			for(char unit: row) {
				System.out.print(unit);
			}
			System.out.println("");
		}
	}
	public boolean isEmpty(int col, int row) {
		if (gameBoard[col][row].name == '\0') {
			return true;
		}
		return false;
	}	
	public static void main(String[] args) {
		Board board = new Board(1);
		board.scale();
		printArr(board.represent);
	}

}