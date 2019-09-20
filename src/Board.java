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
		int incx;//x pos increment for pieces
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
			incx=0;
			for(int j = 1; j < gameBoard.length; j+=2) {
				gameBoard[0][j] = new Piece('p','b',4+incx,2);
				incx=incx+4;
			}
			//white
			incx=0;
			for(int j = 0; j < gameBoard.length; j+=2) {
				gameBoard[3][j] = new Piece('p','w',2+incx,8);
				incx=incx+4;
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
			incx=0;//x pos increment for pieces
			for(int j = 1; j < gameBoard.length; j+=2) {
				gameBoard[0][j] = new Piece('p','b',4+incx,2);
				gameBoard[1][j-1] = new Piece('p','b',2+incx,4);
				gameBoard[2][j] = new Piece('p','b',4+incx,6);
				incx=incx+4;
			}
			//white
			incx=0;
			for(int j = 0; j < gameBoard.length; j+=2) {
				gameBoard[5][j] = new Piece('p','w',2+incx,12);
				gameBoard[6][j+1] = new Piece('p','w',4+incx,14);
				gameBoard[7][j] = new Piece('p','w',2+incx,16);
				incx=incx+4;
			}
			break;
		}	
	}
	
	public static int[] findPiecePos(String string) {
		int[] piecepos = {0,0,0,0}; //in order: gameboardrow, gameboardcol, representrow, representcol
		if (string.charAt(0)=='a') {//for y (row) lookup
			piecepos[0]=0;
			piecepos[2]=2;
		} else if (string.charAt(0)=='b') {
			piecepos[0]=1;
			piecepos[2]=4;
		} else if (string.charAt(0)=='c') {
			piecepos[0]=2;
			piecepos[2]=6;
		} else if (string.charAt(0)=='d') {
			piecepos[0]=3;
			piecepos[2]=8;
		} else if (string.charAt(0)=='e') {
			piecepos[0]=4;
			piecepos[2]=10;
		} else if (string.charAt(0)=='f') {
			piecepos[0]=5;
			piecepos[2]=12;
		} else if (string.charAt(0)=='g') {
			piecepos[0]=6;
			piecepos[2]=14;
		} else if (string.charAt(0)=='h') {
			piecepos[0]=7;
			piecepos[2]=16;
		}
		
		if (string.charAt(1)=='1') { //for x (column) lookup
			piecepos[1]=0;
			piecepos[3]=2;
		} else if (string.charAt(1)=='2') {
			piecepos[1]=1;
			piecepos[3]=4;
		} else if (string.charAt(1)=='3') {
			piecepos[1]=2;
			piecepos[3]=6;
		} else if (string.charAt(1)=='4') {
			piecepos[1]=3;
			piecepos[3]=8;
		} else if (string.charAt(1)=='5') {
			piecepos[1]=4;
			piecepos[3]=10;
		} else if (string.charAt(1)=='6') {
			piecepos[1]=5;
			piecepos[3]=12;
		} else if (string.charAt(1)=='7') {
			piecepos[1]=6;
			piecepos[3]=14;
		} else if (string.charAt(1)=='8') {
			piecepos[1]=7;
			piecepos[3]=16;
		}
		
		return piecepos;
	}
	
	public static void movePiece(String move, Board board) {
		System.out.println("Making move: " + move);
		String[] moveinfo = move.split("-");
		String src = moveinfo[0];
		int[] srcpositions = findPiecePos(src);
		
		char color = board.gameBoard[srcpositions[0]][srcpositions[1]].getColor(); //gets original color
		String dest = moveinfo[1];
		int[] destpositions = findPiecePos(dest);

		if (!board.gameBoard[srcpositions[0]][srcpositions[1]].isEmpty) {
			if (board.gameBoard[destpositions[0]][destpositions[1]].isEmpty) {
				board.gameBoard[srcpositions[0]][srcpositions[1]].clearPiece();
				board.gameBoard[destpositions[0]][destpositions[1]].updatePiece();
				board.gameBoard[destpositions[0]][destpositions[1]].setAbsPosition(destpositions[3], destpositions[2]); //make sure rows (Y) come first
				board.represent[srcpositions[2]][srcpositions[3]]=' ';
				board.represent[destpositions[2]][destpositions[3]]=color;
			} else if (board.gameBoard[destpositions[0]][destpositions[1]].color==color) {
				System.out.println("You already have a piece there!");
			}
		} else {
			System.out.println("No piece in that location!");
		}
	}
	
	public void scale() {
		for(int i = 0; i < gameBoard.length; i++) {
			for(int j = 0; j < gameBoard.length; j++) {
				represent[2*i+2][2*j+2] = gameBoard[i][j].color;
			}
		}
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
		if (gameBoard[col][row].color == '\0') {
			return true;
		}
		return false;
	}	
	public static void main(String[] args) {
		Board board = new Board(1);
		board.scale();
		printArr(board.represent);
		String testmove="a2-b1";
		movePiece(testmove, board);
		printArr(board.represent);
		testmove="d1-c2";
		movePiece(testmove, board);
		printArr(board.represent);
		testmove="a2-b1";
		movePiece(testmove, board);
		printArr(board.represent);
		
		//System.out.println("start position:"); 
		//System.out.println("Empty? : " + board.gameBoard[3][0].isEmpty); //testing getting piece info
		//System.out.println(board.gameBoard[3][0].getY() + " " + board.gameBoard[3][0].getX());
		//System.out.println(board.represent[8][2]); //tracking piece position on 2d array
		//System.out.println("new position:"); 
		//System.out.println("Empty? : " + board.gameBoard[2][1].isEmpty); //testing getting piece info
		//System.out.println(board.gameBoard[2][1].getY() + " " + board.gameBoard[2][1].getX());
		//System.out.println(board.represent[6][4]); //tracking piece position on 2d array
	}

}