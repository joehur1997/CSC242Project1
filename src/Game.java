import java.util.Scanner;

public class Game {
	static boolean running = true;
	int[][] board;
	static boolean humanTurn;

	public Game(int n) {
		board = new int[4][4];
		for(int[] rows : board) {
			for(int square : rows) {
				square = 0;
			}
		}

	}
	public static void printArr(char[][] arr) {
		for(char[] rows : arr) {
			for(char unit : rows) {
				System.out.print(unit);
			}
			System.out.println("");
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
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		boolean running = true;
		System.out.println("This is Java Checkers by Christopher Pak & Joe Hur");
		System.out.println("Choose your game: ");
		System.out.println(" 1. Small 4x4 Checkers \n OR \n 2. Big 8x8 Checkers");
		int gameSize = sc.nextInt();
		sc.nextLine();
		System.out.println("You picked: " + gameSize);
		System.out.println("Pick a color to play as: b for black or w for white: ");
		String colorinput = sc.nextLine();
		char color = colorinput.charAt(0);
		Board board = new Board(gameSize, color);
		board.scale();
		printArr(board.represent);
		System.out.println("Turn: " + board.turn);
		System.out.println("Type your move in src-dest format (example: a2-b1) or type quit to end the game");
		int nocapturn=0;
		while (running) {
			if(!board.gameContinue(board, board.playerColor)||!board.gameContinue(board, board.AIColor)) {
				running=false;
				break;
			}
			if (gameSize==1) {
				if (nocapturn>4) {
					running=false;
					break;
				}
			} else {
				if (nocapturn>6) {
					running=false;
					break;
				}
			}
			if (board.turn%2==0 && board.playerColor=='b') {
				System.out.println("Your turn...");
				String move = sc.nextLine();
				if (move.equals("quit")) {
					System.out.println("Quit Game!");
					running=false;
					break;
				}
				System.out.println("*******************************************************");
				board.movePiece(move, board);
				printArr(board.represent);
				if (!board.capThisTurn) {
					nocapturn++;
				}
				System.out.println("Turn: " + board.turn);
			} else if (board.turn%2==1 && board.playerColor=='w') {
				System.out.println("Your turn...");
				String move = sc.nextLine();
				if (move.equals("quit")) {
					System.out.println("Quit Game!");
					running=false;
					break;
				}
				System.out.println("*******************************************************");
				board.movePiece(move, board);
				printArr(board.represent);
				if (!board.capThisTurn) {
					nocapturn++;
				}
				System.out.println("Turn: " + board.turn);
			} else {
				String aimove = board.makeAiMove(board);
				System.out.println(aimove);
				System.out.println("Enter anything to let the AI opponent go: ");
				sc.nextLine();
				System.out.println("*******************************************************");
				board.movePiece(aimove, board);
				printArr(board.represent);
				if (!board.capThisTurn) {
					nocapturn++;
				}
			}

		}

		
		
		/*
		 * Scanner sc = new Scanner(System.in); boolean running = true;
		 * System.out.println("This is Java Checkers by Christopher Pak & Joe Hur");
		 * System.out.println("Choose your game: ");
		 * System.out.println(" 1. Small 4x4 Checkers \n OR \n 2. Big 8x8 Checkers");
		 * int gameSize = sc.nextInt(); sc.nextLine(); System.out.println("You picked: "
		 * + gameSize); System.out.
		 * println("Pick a Color: Black (b) or White (w) (Black goes first!): "); String
		 * playerColor = sc.next(); if(playerColor.charAt(0) == 'b') { humanTurn = true;
		 * } else { humanTurn = false; }
		 * 
		 * Board board = new Board(gameSize); board.scale(); printArr(board.represent);
		 * // looks nicer this way: turn 0 is weird System.out.println("Turn: " +
		 * board.turn + 1); System.out.
		 * println("Type your move in src-dest format (example: a2-b1) or type quit to end the game"
		 * ); while (running) { String move = sc.nextLine(); if (move.equals("quit")) {
		 * System.out.println("Quit Game!"); running=false; break; }
		 * System.out.println("*******************************************************")
		 * ; if(humanTurn) { System.out.println("Turn: " + board.turn + 1); System.out.
		 * println("Type your move in src-dest format (example: a2-b1) or type quit to end the game"
		 * ); board.movePiece(move, board); printArr(board.represent); humanTurn =
		 * false; board.turn++; } System.out.println("Turn: " + board.turn); }
		 */
	}
}
