import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Board {
	public static char[] vertical = {'a','b','c','d','e','f','g','h'};
	Piece[][] gameBoard;
	char[][] represent;
	int size;
	static int turn = 0;
	boolean blackwin;
	boolean whitewin;
	boolean capThisTurn;
	int score;
	char playerColor;
	char AIColor;

	public Board (Piece[][] gameBoard, char[][] represent, int size, int turn, boolean blackwin, boolean whitewin, int score) {
		this.vertical = vertical;
		this.gameBoard = gameBoard;
		this.represent = represent;
		this.size = size;
		this.turn = turn;
		this.blackwin = blackwin;
		this.whitewin = whitewin;
		this.score = score;
	}
	
	public void setCapThisTurn(boolean capThisTurn) {
		this.capThisTurn = capThisTurn;
	}
	
	public Board(int size, char playerColor) {
		this.playerColor=playerColor;
		if (playerColor=='b') {
			this.AIColor='w';
		} else {
			this.AIColor='b';
		}
		int rep=0;
		if (size==1) {
			rep=10;
			this.size=size;
		} else if (size==2) {
			rep=18;
			this.size=size;
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
	
	public static String getInputfromPos (int row, int col) {
		char[] input = {'x','x'};
		if(row==0) {
			input[0]='a';
		} else if (row==1){
			input[0]='b';
		} else if (row==2){
			input[0]='c';
		} else if (row==3){
			input[0]='d';
		} else if (row==4){
			input[0]='e';
		} else if (row==5){
			input[0]='f';
		} else if (row==6){
			input[0]='g';
		} else if (row==7){
			input[0]='h';
		}
		
		if(col==0) {
			input[1]='1';
		} else if (col==1){
			input[1]='2';
		} else if (col==2){
			input[1]='3';
		} else if (col==3){
			input[1]='4';
		} else if (col==4){
			input[1]='5';
		} else if (col==5){
			input[1]='6';
		} else if (col==6){
			input[1]='7';
		} else if (col==7){
			input[1]='8';
		}
		
		String cookedinput = String.valueOf(input);
		return cookedinput;
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


	
	public static boolean isValidDest (String pos) {
		if ((pos.charAt(0)=='a'||pos.charAt(0)=='c'||pos.charAt(0)=='e'||pos.charAt(0)=='g')&& (Character.getNumericValue(pos.charAt(1))%2!=0)) {
			System.out.println("INVALID MOVE: Can only move pieces diagonally!");
			return false;
		} else if ((pos.charAt(0)=='b'||pos.charAt(0)=='d'||pos.charAt(0)=='f'||pos.charAt(0)=='h')&& (Character.getNumericValue(pos.charAt(1))%2==0)) {
			System.out.println("INVALID MOVE: Can only move pieces diagonally!");
			return false;
		} else {
			return true;
		}
	}
	
	

	public static boolean isValidInput (String pos, Board board) {
		boolean isValid=true;
		char letter = pos.charAt(0);
		int num = Character.getNumericValue(pos.charAt(1));
		if (board.size==1) {
			if ((letter!='a'&& letter!='b'&&letter!='c'&&letter!='d')||num>4||num==0||pos.length()>2) {
				System.out.println("INVALID MOVE: Move is outside of board or incorrect input!");
				isValid=false;
			} 
		}
		if (board.size==2) {
			boolean validchar=false;
			for (char x : vertical) {
				if (x==letter) {
					validchar=true;
				}
			}
			if (!validchar||num>8||num==0||pos.length()>2) {
				System.out.println("INVALID MOVE: Move is outside of board or incorrect input");
				isValid=false;
			}
		}
		return isValid;
	}
	//strictly for avalmoves in piece.java
	//captures exclusive
	//need help :(
	public void getMoves(String pos) {
		char yCoor = pos.charAt(0);
		int y = getIndex(vertical, yCoor);
		int x = Character.digit(pos.charAt(1), 10) - 1;
		Piece temp =gameBoard[y][x];
		temp.avalMoves.add(pos);
		
		if(isEmpty(y,x)) {
			return;
		}
		//hardcode to add moves
		if(!isEmpty(y,x)) {
			if(gameBoard[y][x].role == 'p') {
				if(gameBoard[y][x].color == 'b') {
					//edge
					if(x == 0) {
						if(isEmpty(y+1,x+1)) {
							temp.avalMoves.add(vertical[y+1] + "" + (x+1));
						}
					}
					if(x == gameBoard.length) {
						if(isEmpty(y+1,x-1)) {
							temp.avalMoves.add(vertical[y+1] + "" + (x-1));
						}
					}
					if(x > 0 && x < gameBoard.length) {
						if(isEmpty(y+1,x-1)) {
							temp.avalMoves.add(vertical[y+1] + "" + (x-1));
						}
						if(isEmpty(y+1,x+1)) {
							temp.avalMoves.add(vertical[y+1] + "" + (x+1));
						}
					}
				}
				if(gameBoard[y][x].color == 'w') {
					//edge
					if(x == 0) {
						if(isEmpty(y-1,x+1)) {
							temp.avalMoves.add(vertical[y-1] + "" + (x+1));
						}
					}
					if(x == gameBoard.length) {
						if(isEmpty(y-1,x-1)) {
							temp.avalMoves.add(vertical[y-1] + "" + (x-1));
						}
					}
					if(x > 0 && x < gameBoard.length) {
						if(isEmpty(y-1,x-1)) {
							temp.avalMoves.add(vertical[y-1] + "" + (x-1));
						}
						if(isEmpty(y-1,x+1)) {
							temp.avalMoves.add(vertical[y-1] + "" + (x+1));
						}
					}
				}
			}
		}
		gameBoard[y][x].avalMoves = temp.avalMoves;
	}
	//see if game can still run
	//even if this code doesn't work, it shows the idea. We can use this to determine the terminal state. 
	public boolean check(Piece[][] gameBoard) {
		boolean canRun = true;
		//case where there are no pieces that are white or black
		boolean noWhite = false;
		boolean noBlack = false;
		for(Piece[] rows: gameBoard) {
			for(Piece pieces: rows) {
				//if theres a single piece that has white color
				if(pieces.color == 'w') {
					noWhite = true;
				}
				if(pieces.color == 'b') {
					noBlack = true;
				}
				
			}			
		}
		boolean noPiece = noWhite || noBlack;
		//case for availible moves
		boolean BcanMove = false;
		boolean WcanMove = false;
		for(Piece[] rows: gameBoard) {
			for(Piece pieces: rows) {
				while(pieces.color == 'b') {
					if(!pieces.avalMoves.isEmpty()) {
						BcanMove = true;
					}
				}
				while(pieces.color == 'w') {
					if(!pieces.avalMoves.isEmpty()) {
						WcanMove = true;
					}
				}
			}
		}
		//utility
		if(WcanMove || noWhite) {
			blackwin = true;
			whitewin = false;
		}
		if(BcanMove || noBlack) {
			whitewin = true;
			blackwin = false;
		}
		boolean noMoves = WcanMove || BcanMove;
		canRun = noMoves || noPiece;
		//cut off: average checkers game lasts 50 moves, and we don't want computer overloading.
		if(turn > 55) {
			canRun = false;
			whitewin = false;
			blackwin = false;
		}
		return canRun;
	}
	public static boolean kingPiece(Piece piece, int size) { //hehe actually decided to make a separate method for kinging
		if (piece.color=='b') {
			if (size==1 && piece.y==8 &&(piece.x>=2&&piece.x<=8)) {
				piece.setRole('k');
				System.out.println("Kinged this piece");
				return true;
			} else if (size==2 && piece.y==16 &&(piece.x>=2&&piece.x<=16)) {
				piece.setRole('k');
				System.out.println("Kinged this piece");
				return true;
			} else {
				return false;
			}
		} else {
			if (piece.y==2 &&(piece.x>=2&&piece.x<=16)) {
				piece.setRole('k');
				System.out.println("Kinged this piece");
				return true;
			} else {
				return false;
			}
		}
	}
	
	public static boolean isValidKingMove(Board board, int[] srcpositions, int[] destpositions) {
		boolean isValid=true;
		if (srcpositions[0]<destpositions[0]&&srcpositions[1]>destpositions[1]) {
			for (int i=1; i<Math.abs(destpositions[0]-srcpositions[0]); i++) {
				if (!board.gameBoard[srcpositions[0]+i][srcpositions[1]-i].isEmpty) {
					isValid=false;
				}
			}
		} else if (srcpositions[0]>destpositions[0]&&srcpositions[1]>destpositions[1]) {
			for (int i=1; i<Math.abs(destpositions[0]-srcpositions[0]); i++) {
				if (!board.gameBoard[srcpositions[0]-i][srcpositions[1]-i].isEmpty) {
					isValid=false;
				}
			}
		} else if (srcpositions[0]>destpositions[0]&&srcpositions[1]<destpositions[1]) {
			for (int i=1; i<Math.abs(destpositions[0]-srcpositions[0]); i++) {
				if (!board.gameBoard[srcpositions[0]-i][srcpositions[1]+i].isEmpty) {
					isValid=false;
				}
			}
		} else {
			for (int i=1; i<Math.abs(destpositions[0]-srcpositions[0]); i++) {
				if (!board.gameBoard[srcpositions[0]+i][srcpositions[1]+i].isEmpty) {
					isValid=false;
				}
			}
		}
		return isValid;
	}
	
	public static void movePieceNoCapture(Board board, int[] srcpositions, int[] destpositions, char color) { //moving pieces without capturing
		if (board.gameBoard[srcpositions[0]][srcpositions[1]].role=='p') {
			board.gameBoard[destpositions[0]][destpositions[1]].updatePiece(board.gameBoard[srcpositions[0]][srcpositions[1]]);
			board.gameBoard[srcpositions[0]][srcpositions[1]].clearPiece();
			board.gameBoard[destpositions[0]][destpositions[1]].setAbsPosition(destpositions[3], destpositions[2]);
			board.represent[srcpositions[2]][srcpositions[3]]=' ';
			if (kingPiece(board.gameBoard[destpositions[0]][destpositions[1]], board.size)) {//checks if can make king and changes the visual accordingly
				board.represent[destpositions[2]][destpositions[3]]=Character.toUpperCase(color);
			} else {
				board.represent[destpositions[2]][destpositions[3]]=color;
			}
			board.gameBoard[destpositions[0]][destpositions[1]].getPieceInfo();
		} else if (board.gameBoard[srcpositions[0]][srcpositions[1]].role=='k') { //moving kings without capture
			board.gameBoard[destpositions[0]][destpositions[1]].updatePiece(board.gameBoard[srcpositions[0]][srcpositions[1]]);
			board.gameBoard[srcpositions[0]][srcpositions[1]].clearPiece();
			board.gameBoard[destpositions[0]][destpositions[1]].setAbsPosition(destpositions[3], destpositions[2]);
			board.represent[srcpositions[2]][srcpositions[3]]=' ';
			board.represent[destpositions[2]][destpositions[3]]=Character.toUpperCase(color);
			board.gameBoard[destpositions[0]][destpositions[1]].getPieceInfo();
		}
		board.capThisTurn=false;
	}
	
	public static boolean canPieceCaptureSmall(Board board, int row, int col) {
		boolean canCapture=false;
		char color = board.gameBoard[row][col].getColor();
		if (board.gameBoard[row][col].role=='p') {
			if (board.gameBoard[row][col].color=='b') {
				if (col<2 && row<2) {
					if (!board.gameBoard[row+1][col+1].isEmpty && board.gameBoard[row+1][col+1].color!='b' && board.gameBoard[row+2][col+2].isEmpty) {
						canCapture=true;
					}
				} else if (col>1 && row<2) {
					if (!board.gameBoard[row+1][col-1].isEmpty && board.gameBoard[row+1][col-1].color!='b' && board.gameBoard[row+2][col-2].isEmpty) {
						canCapture=true;
					}
				}
			} else { //white
				if (col<2 && row>1) {
					if (!board.gameBoard[row-1][col+1].isEmpty && board.gameBoard[row-1][col+1].color!='w' && board.gameBoard[row-2][col+2].isEmpty) {
						canCapture=true;
					}
				} else if (col>1 && row>1) {
					if (!board.gameBoard[row-1][col-1].isEmpty && board.gameBoard[row-1][col-1].color!='w' && board.gameBoard[row-2][col-2].isEmpty) {
						canCapture=true;
					}
				}
			}
		} else { //kings
			if (col<2 && row<2) {
				if (!board.gameBoard[row+1][col+1].isEmpty && board.gameBoard[row+1][col+1].color!=color && board.gameBoard[row+2][col+2].isEmpty) {
					canCapture=true;
				}
			} else if (col>1 && row<2) {
				if (!board.gameBoard[row+1][col-1].isEmpty && board.gameBoard[row+1][col-1].color!=color && board.gameBoard[row+2][col-2].isEmpty) {
					canCapture=true;
				}
			} else if (col<2 && row>1) {
				if (!board.gameBoard[row-1][col+1].isEmpty && board.gameBoard[row-1][col+1].color!=color && board.gameBoard[row-2][col+2].isEmpty) {
					canCapture=true;
				}
			} else if (col>1 && row>1) {
				if (!board.gameBoard[row-1][col-1].isEmpty && board.gameBoard[row-1][col-1].color!=color && board.gameBoard[row-2][col-2].isEmpty) {
					canCapture=true;
				}
			}
		}
		return canCapture;
	}
	
	public static String getSingleAICapture(Board board, int row, int col) {
		String capture="";
		char color = board.gameBoard[row][col].getColor();
		if (board.gameBoard[row][col].role=='p') {
			if (board.gameBoard[row][col].color=='b') {
				if (col<2 && row<2) {
					if (!board.gameBoard[row+1][col+1].isEmpty && board.gameBoard[row+1][col+1].color!='b' && board.gameBoard[row+2][col+2].isEmpty) {
						capture = createMove(getInputfromPos(row, col), getInputfromPos(row+1, col+1));
					}
				} else if (col>1 && row<2) {
					if (!board.gameBoard[row+1][col-1].isEmpty && board.gameBoard[row+1][col-1].color!='b' && board.gameBoard[row+2][col-2].isEmpty) {
						capture = createMove(getInputfromPos(row, col), getInputfromPos(row+1, col-1));
					}
				}
			} else { //white
				if (col<2 && row>1) {
					if (!board.gameBoard[row-1][col+1].isEmpty && board.gameBoard[row-1][col+1].color!='w' && board.gameBoard[row-2][col+2].isEmpty) {
						capture = createMove(getInputfromPos(row, col), getInputfromPos(row-1, col+1));
					}
				} else if (col>1 && row>1) {
					if (!board.gameBoard[row-1][col-1].isEmpty && board.gameBoard[row-1][col-1].color!='w' && board.gameBoard[row-2][col-2].isEmpty) {
						capture = createMove(getInputfromPos(row, col), getInputfromPos(row-1, col-1));
					}
				}
			}
		} else { //kings
			if (col<2 && row<2) {
				if (!board.gameBoard[row+1][col+1].isEmpty && board.gameBoard[row+1][col+1].color!=color && board.gameBoard[row+2][col+2].isEmpty) {
					capture = createMove(getInputfromPos(row, col), getInputfromPos(row+1, col+1));
				}
			} else if (col>1 && row<2) {
				if (!board.gameBoard[row+1][col-1].isEmpty && board.gameBoard[row+1][col-1].color!=color && board.gameBoard[row+2][col-2].isEmpty) {
					capture = createMove(getInputfromPos(row, col), getInputfromPos(row+1, col-1));
				}
			} else if (col<2 && row>1) {
				if (!board.gameBoard[row-1][col+1].isEmpty && board.gameBoard[row-1][col+1].color!=color && board.gameBoard[row-2][col+2].isEmpty) {
					capture = createMove(getInputfromPos(row, col), getInputfromPos(row-1, col+1));
				}
			} else if (col>1 && row>1) {
				if (!board.gameBoard[row-1][col-1].isEmpty && board.gameBoard[row-1][col-1].color!=color && board.gameBoard[row-2][col-2].isEmpty) {
					capture = createMove(getInputfromPos(row, col), getInputfromPos(row-1, col-1));
				}
			}
		}
		return capture;
	}
	
	public static boolean canPieceCapture(Board board, int row, int col) {
		boolean canCapture=false;
		char color = board.gameBoard[row][col].getColor();
		if (board.gameBoard[row][col].role=='p') {//checking for regular pieces
			if (board.gameBoard[row][col].color=='b') {//for black regular pieces
				if (col>5) {
					if (!board.gameBoard[row+1][col-1].isEmpty && board.gameBoard[row+1][col-1].color!='b' && board.gameBoard[row+2][col-2].isEmpty) {
						canCapture=true;
					}
				} else if (col<2) {
					if (!board.gameBoard[row+1][col+1].isEmpty && board.gameBoard[row+1][col+1].color!='b' && board.gameBoard[row+2][col+2].isEmpty) {
						canCapture=true;
					}
				} else {
					if ((!board.gameBoard[row+1][col-1].isEmpty && board.gameBoard[row+1][col-1].color!='b' && board.gameBoard[row+2][col-2].isEmpty)
							||(!board.gameBoard[row+1][col+1].isEmpty && board.gameBoard[row+1][col+1].color!='b' && board.gameBoard[row+2][col+2].isEmpty)) {
						canCapture=true;
					}
				}
			} else if (board.gameBoard[row][col].color=='w') { //for white regular pieces
				if (col>5) {
					if (!board.gameBoard[row-1][col-1].isEmpty && board.gameBoard[row-1][col-1].color!='w' && board.gameBoard[row-2][col-2].isEmpty) {
						canCapture=true;
					}
				} else if (col<2) {
					if (!board.gameBoard[row-1][col+1].isEmpty && board.gameBoard[row-1][col+1].color!='w' && board.gameBoard[row-2][col+2].isEmpty) {
						canCapture=true;
					}
				} else {
					if ((!board.gameBoard[row-1][col-1].isEmpty && board.gameBoard[row-1][col-1].color!='w' && board.gameBoard[row-2][col-2].isEmpty)
							||(!board.gameBoard[row-1][col+1].isEmpty && board.gameBoard[row-1][col+1].color!='w' && board.gameBoard[row-2][col+2].isEmpty)) {
						canCapture=true;
					}
				}
			}
		} else { //checking for kings
			if (row<2) {
				if (col>5) {
					if (!board.gameBoard[row+1][col-1].isEmpty && board.gameBoard[row+1][col-1].color!=color && board.gameBoard[row+2][col-2].isEmpty) {
						canCapture=true;
					}
				} else if (col<2) {
					if (!board.gameBoard[row+1][col+1].isEmpty && board.gameBoard[row+1][col+1].color!=color && board.gameBoard[row+2][col+2].isEmpty) {
						canCapture=true;
					}
				} else {
					if ((!board.gameBoard[row+1][col-1].isEmpty && board.gameBoard[row+1][col-1].color!=color && board.gameBoard[row+2][col-2].isEmpty)
							||(!board.gameBoard[row+1][col+1].isEmpty && board.gameBoard[row+1][col+1].color!=color && board.gameBoard[row+2][col+2].isEmpty)) {
						canCapture=true;
					}
				}
			} else if (row>5) {
				if (col>5) {
					if (!board.gameBoard[row-1][col-1].isEmpty && board.gameBoard[row-1][col-1].color!=color && board.gameBoard[row-2][col-2].isEmpty) {
						canCapture=true;
					}
				} else if (col<2) {
					if (!board.gameBoard[row-1][col+1].isEmpty && board.gameBoard[row-1][col+1].color!=color && board.gameBoard[row-2][col+2].isEmpty) {
						canCapture=true;
					}
				} else {
					if ((!board.gameBoard[row-1][col-1].isEmpty && board.gameBoard[row-1][col-1].color!=color && board.gameBoard[row-2][col-2].isEmpty)
							||(!board.gameBoard[row-1][col+1].isEmpty && board.gameBoard[row-1][col+1].color!=color && board.gameBoard[row-2][col+2].isEmpty)) {
						canCapture=true;
					}
				}
			} else  {
				if (col<2) {
					if ((!board.gameBoard[row+1][col+1].isEmpty && board.gameBoard[row+1][col+1].color!=color && board.gameBoard[row+2][col+2].isEmpty)
							|| (!board.gameBoard[row-1][col+1].isEmpty && board.gameBoard[row-1][col+1].color!=color && board.gameBoard[row-2][col+2].isEmpty)) {
						canCapture=true;
					}
				} else if (col>5 ) {
					if ((!board.gameBoard[row-1][col-1].isEmpty && board.gameBoard[row-1][col-1].color!=color && board.gameBoard[row-2][col-2].isEmpty)
							|| (!board.gameBoard[row+1][col-1].isEmpty && board.gameBoard[row+1][col-1].color!=color && board.gameBoard[row+2][col-2].isEmpty)) {
						canCapture=true;
					}
				} else {
					if ((!board.gameBoard[row+1][col+1].isEmpty && board.gameBoard[row+1][col+1].color!=color && board.gameBoard[row+2][col+2].isEmpty)
							|| (!board.gameBoard[row-1][col+1].isEmpty && board.gameBoard[row-1][col+1].color!=color && board.gameBoard[row-2][col+2].isEmpty)
							|| (!board.gameBoard[row-1][col-1].isEmpty && board.gameBoard[row-1][col-1].color!=color && board.gameBoard[row-2][col-2].isEmpty)
							|| (!board.gameBoard[row+1][col-1].isEmpty && board.gameBoard[row+1][col-1].color!=color && board.gameBoard[row+2][col-2].isEmpty)) {
						canCapture=true;
					}
				}
			}
		}
		return canCapture;
	}
	
	public static ArrayList<String> getAICaptures (Board board, char color) {
		int n=4;
		ArrayList<String> captures = new ArrayList<String>();
		if (board.size==2) {
			n=8;
		}
		for (int i =0; i<n;i++) {
			for (int j=0; j<n; j++) {
				if (n==4) {
					if (!board.gameBoard[i][j].isEmpty && board.gameBoard[i][j].getColor()==color) {
						if (canPieceCaptureSmall(board, i, j)) {
							String capture = getSingleAICapture(board, i, j);
							captures.add(capture);
						}
					}
				} else { //larger board
					if (!board.gameBoard[i][j].isEmpty && board.gameBoard[i][j].getColor()==color) {
						if (canPieceCapture(board, i, j)) {
							String capture = getSingleAICapture(board, i, j);
							captures.add(capture);
						}
					}

				}
			}
		}
		return captures;
	}
	
	public static boolean gameContinue(Board board, char color) {
		int n=4;
		if (board.size==2) {
			n=8;
		}
		boolean foundpiece = false;
		for (int i =0; i<n;i++) {
			for (int j=0; j<n; j++) {
				if (n==4) {
					if (!board.gameBoard[i][j].isEmpty && board.gameBoard[i][j].getColor()==color) {
						foundpiece = true;
						break;
					}
				} else { //larger board
					if (!board.gameBoard[i][j].isEmpty && board.gameBoard[i][j].getColor()==color) {
						foundpiece = true;
					} 
				}
			}
		}
		if (foundpiece) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean capturePossible(Board board, char color) {
		int n=4;
		boolean canCapture=false;
		if (board.size==2) {
			n=8;
		}
		for (int i =0; i<n;i++) {
			for (int j=0; j<n; j++) {
				if (n==4) {
					if (!board.gameBoard[i][j].isEmpty && board.gameBoard[i][j].getColor()==color) {
						if (canPieceCaptureSmall(board, i, j)) {
							canCapture=true;
							System.out.println("Row: " + i + ", Col: " + j);
							break;
						}
					}
				} else { //larger board
					if (!board.gameBoard[i][j].isEmpty && board.gameBoard[i][j].getColor()==color) {
						if (canPieceCapture(board, i, j)) {
							canCapture=true;
							System.out.println("Row: " + i + ", Col: " + j);
							break;
						}
					}

				}
			}
		}
		return canCapture;
	}
	
	public static String createMove(String input1, String input2) {
		String move = input1 + "-" + input2;
		return move;
	}
	
	public static ArrayList<String> getAiMoves (Board board) {
		ArrayList<String> moves = new ArrayList<String>();
		char color='b';
		if (board.playerColor=='b') {
			color = 'w';
		}
		int n=4;
		if (board.size==2) {
			n=8;
		}
		for (int row =0; row<n;row++) {
			for (int col=0; col<n; col++) {
				if (n==4) {
					if (!board.gameBoard[row][col].isEmpty && board.gameBoard[row][col].getColor()==color) {
						if (color=='b' && board.gameBoard[row][col].getRole()!='k') {
							if (col==3 && row!=3) {
								if (board.gameBoard[row+1][col-1].isEmpty) {
									String move = createMove(getInputfromPos(row, col), getInputfromPos(row+1, col-1));
									moves.add(move);
								}
							} else if (col==0 && row!=3) {
								if (board.gameBoard[row+1][col+1].isEmpty) {
									String move = createMove(getInputfromPos(row, col), getInputfromPos(row+1, col+1));
									moves.add(move);
								}
							} else if (row!=3) {
								if (board.gameBoard[row+1][col+1].isEmpty) {
									String move = createMove(getInputfromPos(row, col), getInputfromPos(row+1, col+1));
									moves.add(move);
								}
								if (board.gameBoard[row+1][col-1].isEmpty) {
									String move = createMove(getInputfromPos(row, col), getInputfromPos(row+1, col-1));
									moves.add(move);
								}
							}
						} else if (color=='w' && board.gameBoard[row][col].getRole()!='k') { //white
							if (col==3 && row!=0) {
								if (board.gameBoard[row-1][col-1].isEmpty) {
									String move = createMove(getInputfromPos(row, col), getInputfromPos(row-1, col-1));
									moves.add(move);
								}
							} else if (col==0 && row!=0) {
								if (board.gameBoard[row-1][col+1].isEmpty) {
									String move = createMove(getInputfromPos(row, col), getInputfromPos(row-1, col+1));
									moves.add(move);
								}
							} else if (row!=0) {
								if (board.gameBoard[row-1][col-1].isEmpty) {
									String move = createMove(getInputfromPos(row, col), getInputfromPos(row-1, col-1));
									moves.add(move);
								}
								if (board.gameBoard[row-1][col+1].isEmpty) {
									String move = createMove(getInputfromPos(row, col), getInputfromPos(row-1, col+1));
									moves.add(move);
								}
							}
						} else if (board.gameBoard[row][col].getRole()=='k') { //kings
							if (col==3 && row==0) {
								if (board.gameBoard[row+1][col-1].isEmpty) {
									String move = createMove(getInputfromPos(row, col), getInputfromPos(row+1, col-1));
									moves.add(move);
								}
							} else if (col==0 && row==3) {
								if (board.gameBoard[row+1][col+1].isEmpty) {
									String move = createMove(getInputfromPos(row, col), getInputfromPos(row+1, col+1));
									moves.add(move);
								}
							} else if (row!=0 && row!=3 && col==0) {
								if (board.gameBoard[row+1][col+1].isEmpty) {
									String move = createMove(getInputfromPos(row, col), getInputfromPos(row+1, col+1));
									moves.add(move);
								}
								if (board.gameBoard[row-1][col+1].isEmpty) {
									String move = createMove(getInputfromPos(row, col), getInputfromPos(row-1, col+1));
									moves.add(move);
								}
							} else if (row!=0 && row!=3 && col==3) {
								if (board.gameBoard[row+1][col-1].isEmpty) {
									String move = createMove(getInputfromPos(row, col), getInputfromPos(row+1, col-1));
									moves.add(move);
								}
								if (board.gameBoard[row-1][col-1].isEmpty) {
									String move = createMove(getInputfromPos(row, col), getInputfromPos(row-1, col-1));
									moves.add(move);
								}
							} else if ((row>0&&row<3)&&(col>0&&col<3)) {
								if (board.gameBoard[row+1][col+1].isEmpty) {
									String move = createMove(getInputfromPos(row, col), getInputfromPos(row+1, col+1));
									moves.add(move);
								}
								if (board.gameBoard[row-1][col+1].isEmpty) {
									String move = createMove(getInputfromPos(row, col), getInputfromPos(row-1, col+1));
									moves.add(move);
								}
								if (board.gameBoard[row+1][col-1].isEmpty) {
									String move = createMove(getInputfromPos(row, col), getInputfromPos(row+1, col-1));
									moves.add(move);
								}
								if (board.gameBoard[row-1][col-1].isEmpty) {
									String move = createMove(getInputfromPos(row, col), getInputfromPos(row-1, col-1));
									moves.add(move);
								}
							} else if (row==3&&col==2) {
								if (board.gameBoard[row-1][col+1].isEmpty) {
									String move = createMove(getInputfromPos(row, col), getInputfromPos(row-1, col+1));
									moves.add(move);
								}
								if (board.gameBoard[row-1][col-1].isEmpty) {
									String move = createMove(getInputfromPos(row, col), getInputfromPos(row-1, col-1));
									moves.add(move);
								}
							} else if (row==0&&col==1) {
								if (board.gameBoard[row+1][col+1].isEmpty) {
									String move = createMove(getInputfromPos(row, col), getInputfromPos(row+1, col+1));
									moves.add(move);
								}
								if (board.gameBoard[row+1][col-1].isEmpty) {
									String move = createMove(getInputfromPos(row, col), getInputfromPos(row+1, col-1));
									moves.add(move);
								}
							}
						}
					}
				} /*
					 * else { //larger board 8x8 if (!board.gameBoard[row][col].isEmpty &&
					 * board.gameBoard[row][col].getColor()==color) {
					 * 
					 * }
					 * 
					 * }
					 */
			}
		}
		
		return moves;
	}
	
	public static String makeAiMove(Board board) {
		String move;
		if (capturePossible(board, board.AIColor)) {
			ArrayList<String> captures = getAICaptures(board, board.AIColor);
			Random rand = new Random();
			if (captures.size()-1==0) {
				move = captures.get(0);
				return move;
			}
			int index = rand.nextInt(captures.size()-1);
			move = captures.get(index);
			return move;
		} else {
			ArrayList<String> moves = getAiMoves(board);
			Random rand = new Random();
			if (moves.size()-1==0) {
				move = moves.get(0);
				return move;
			}
			int index = rand.nextInt(moves.size()-1);
			move = moves.get(index);
			return move;
		}
	}
	
	
	public static void capturePiece (Board board, int[] srcpositions, int[] destpositions, char color) { //capturing pieces with regular pieces
		System.out.println("Trying to capture...");
		int rowchange=1;
		int rightorleft=1;
		if(color=='w') {
			rowchange=(-1);
			System.out.println("White is trying to capture black...");
		} else {
			System.out.println("Black is trying to capture white...");
		}
		if (srcpositions[1]>destpositions[1]) {//checks if capturing right or left
			rightorleft=-1;
		}
		if (board.gameBoard[destpositions[0]+rowchange][destpositions[1]+rightorleft].isEmpty) {
				board.gameBoard[destpositions[0]+rowchange][destpositions[1]+rightorleft].updatePiece(board.gameBoard[srcpositions[0]][srcpositions[1]]);
				board.gameBoard[srcpositions[0]][srcpositions[1]].clearPiece();
				board.gameBoard[destpositions[0]][destpositions[1]].clearPiece();
				board.gameBoard[destpositions[0]+rowchange][destpositions[1]+rightorleft].setAbsPosition(destpositions[3]+(2*rightorleft), destpositions[2]+(2*rowchange));
				board.represent[srcpositions[2]][srcpositions[3]]=' ';
				board.represent[destpositions[2]][destpositions[3]]=' ';
				if (kingPiece(board.gameBoard[destpositions[0]+rowchange][destpositions[1]+rightorleft], board.size)) {//checks if can make king and changes the visual accordingly
					board.represent[destpositions[2]+(2*rowchange)][destpositions[3]+(2*rightorleft)]=Character.toUpperCase(color);
				} else {
					board.represent[destpositions[2]+(2*rowchange)][destpositions[3]+(2*rightorleft)]=color;
				}
				board.gameBoard[destpositions[0]+rowchange][destpositions[1]+rightorleft].getPieceInfo();
				int piecerow=destpositions[0]+rowchange;
				int piececol=destpositions[1]+rightorleft;
				System.out.println("Capture successful");
				board.setCapThisTurn(true);
				if (board.size==1) {
					if (canPieceCaptureSmall(board, piecerow, piececol)) {
						System.out.println("This piece can capture another piece!");
						board.setCapThisTurn(false);
					} else {
						turn++;
					}
				} else {
					if (canPieceCapture(board, piecerow, piececol)) {
						System.out.println("This piece can capture another piece!");
						board.setCapThisTurn(false);
					} else {
						turn++;
					}
				}
		} else {
			System.out.println("INVALID MOVE: Unable to capture due to blocking piece!");
		}
	}
	
	public static void capturePieceKing (Board board, int[] srcpositions, int[] destpositions, char color) { //capturing pieces with kings
		System.out.println("Trying to capture...");
		int rowchange=1;
		int rightorleft=1;
		if (color=='w' && (srcpositions[0]>destpositions[0]) ) {
			rowchange = -1;
		} else if (color=='b' && (srcpositions[0]>destpositions[0])) {
			rowchange = -1;
		}
		if (srcpositions[1]>destpositions[1]) {//checks if capturing right or left
			rightorleft=-1;
		}
		System.out.println(srcpositions[1] + " " + destpositions[1]);
		if (board.gameBoard[destpositions[0]+rowchange][destpositions[1]+rightorleft].isEmpty) {
				board.gameBoard[destpositions[0]+rowchange][destpositions[1]+rightorleft].updatePiece(board.gameBoard[srcpositions[0]][srcpositions[1]]);
				board.gameBoard[srcpositions[0]][srcpositions[1]].clearPiece();
				board.gameBoard[destpositions[0]][destpositions[1]].clearPiece();
				board.gameBoard[destpositions[0]+rowchange][destpositions[1]+rightorleft].setAbsPosition(destpositions[3]+(2*rightorleft), destpositions[2]+(2*rowchange));
				board.represent[srcpositions[2]][srcpositions[3]]=' ';
				board.represent[destpositions[2]][destpositions[3]]=' ';
				board.represent[destpositions[2]+(2*rowchange)][destpositions[3]+(2*rightorleft)]=Character.toUpperCase(color);
				board.gameBoard[destpositions[0]+rowchange][destpositions[1]+rightorleft].getPieceInfo();
				int piecerow=destpositions[0]+rowchange;
				int piececol=destpositions[1]+rightorleft;
				System.out.println("Capture successful");
				board.setCapThisTurn(true);
				if (board.size==1) {
					if (canPieceCaptureSmall(board, piecerow, piececol)) {
						System.out.println("This piece can capture another piece!");
						board.setCapThisTurn(false);
					} else {
						turn++;
					}
				} else {
					if (canPieceCapture(board, piecerow, piececol)) {
						System.out.println("This piece can capture another piece!");
						board.setCapThisTurn(false);
					} else {
						turn++;
					}
				}

		} else {
			System.out.println("INVALID MOVE: Unable to capture due to blocking piece!");
		}
	}


	//sorry, but some feedback on this method:
	//this method does the job in terms of moving the piece but coding the tree and capture mechanic gets really hard. We need to be able to see states ahead.
	//Ama try to work around this b/c i like the method, but this is just a just in case, to remind both me and you :)
	//i think i fixed it. idk yet tho
	public static void movePiece(String move, Board board) {
		System.out.println("Making move: " + move);
		String[] moveinfo = move.split("-");
		String src = moveinfo[0];
		if (!isValidInput(src, board)) {
			return;
		}
		int[] srcpositions = findPiecePos(src);

		char color = board.gameBoard[srcpositions[0]][srcpositions[1]].getColor(); //gets original color
		if (turn%2==0) {
			if (color!='b') {
				System.out.println("This is black's turn! You tried to move a white piece.");
				return;
			}
		} else {
			if (color!='w') {
				System.out.println("This is white's turn! You tried to move a black piece.");
				return;
			}
		}
		String dest = moveinfo[1];
		if (!isValidInput(dest, board)) {
			return;
		}
		if (!isValidDest(dest)) {
			return;
		}
		int[] destpositions = findPiecePos(dest);

		if (!board.gameBoard[srcpositions[0]][srcpositions[1]].isEmpty) {
			if (board.gameBoard[srcpositions[0]][srcpositions[1]].role=='p') { //checks if regular piece or king
				if ((srcpositions[0]>=destpositions[0]&&color == 'b')||destpositions[0]-srcpositions[0]>1) { //reg black can only move 1 row down (increase row)
					System.out.println("INVALID MOVE: Non-king pieces can only move 1 square forward diagonally!");
				} else if ((srcpositions[0]<=destpositions[0]&&color=='w')||srcpositions[0]-destpositions[0]>1) { //reg white can only move 1 row up (decrease row)
					System.out.println("INVALID MOVE: Non-king pieces can only move 1 square forward diagonally!");
				} else if (board.gameBoard[destpositions[0]][destpositions[1]].isEmpty) { //moving logic mainly here. need to clean up and maybe make separate method
					if (!capturePossible(board, color)) {
						movePieceNoCapture(board, srcpositions, destpositions, color);
						turn++;
					} else {
						System.out.println("INVALID MOVE: You must capture a piece if you can!");
						return;
					}
				} else if (board.gameBoard[destpositions[0]][destpositions[1]].color==color) {
					System.out.println("INVALID MOVE: You already have a piece at " + dest + "!");
					
		//**********************logic for capturing pieces with REGULAR pieces STARTS below. yeah its messy af and needs a proper separate method later*************************
				} else if (board.gameBoard[destpositions[0]][destpositions[1]].color!=color) { 
					capturePiece(board, srcpositions, destpositions, color);
				}
		//**********************logic for capturing pieces with REGULAR pieces ENDS here. yeah its messy af and needs a proper separate method later***************************
				
			} else if (board.gameBoard[srcpositions[0]][srcpositions[1]].role=='k') { //logic if piece is KING
				if (board.gameBoard[destpositions[0]][destpositions[1]].isEmpty) { //moving logic mainly here. need to clean up and maybe make separate method
					if (isValidKingMove(board, srcpositions, destpositions)) {
						if (!capturePossible(board, color)) {
							movePieceNoCapture(board, srcpositions, destpositions, color);
							turn++;
						} else {
							System.out.println("INVALID MOVE: You must capture a piece if you can!");
							return;
						}
					} else {
						System.out.println("The King is blocked by piece!");
					}
				} else if (board.gameBoard[destpositions[0]][destpositions[1]].color==color) {
					System.out.println("INVALID MOVE: You already have a piece at " + dest + "!");
					
		//**********************logic for capturing pieces with KING pieces STARTS below. yeah its messy af and needs a proper separate method later*************************
				} else if (board.gameBoard[destpositions[0]][destpositions[1]].color!=color) {
					if (isValidKingMove(board, srcpositions, destpositions)) {
						capturePieceKing(board, srcpositions, destpositions, color);
					} else {
						System.out.println("The King is blocked by piece!");
					}
				}
		//**********************logic for capturing pieces with KING pieces ENDS here. yeah its messy af and needs a proper separate method later*************************
			} 
		} else {
			System.out.println("INVALID MOVE: There is no piece at " + src + "!");
		}
	}
	//this is the workaround im starting on: we can keep the next state intact, but idk what it does yet.
	// careful for doing shallow copies
	public Board futureState(String move, Board board){
		Board next = new Board(board.gameBoard, board.represent, board.size, board.turn, board.blackwin, board.whitewin, board.score);
		movePiece(move, next);
		return next;	
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
		Board board = new Board(1, 'b');
		board.scale();
		printArr(board.represent);

		Scanner sc = new Scanner(System.in);
		String move = sc.nextLine();
		System.out.println(move);

		String testmove="a2-b1";
		movePiece(testmove, board);
		printArr(board.represent);

		testmove="d1-b3";
		movePiece(testmove, board);
		printArr(board.represent);

		testmove="b1-a2";
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