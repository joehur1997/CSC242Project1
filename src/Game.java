import java.util.Scanner;

public class Game {
	static boolean running = true;

	int[][] board;
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
		System.out.println("This is Java Hello world Checkers by Christopher Pak & Joe Hur");
		System.out.println("Choose your game: ");
		System.out.println(" 1. Small 4x4 Checkers \n OR \n 2. Big 8x8 Checkers");
		int gameSize = sc.nextInt();
		System.out.println("You picked: " + gameSize);
		//ai stuff

		System.out.println("Do you want to play Black (B) or White (W) (Black goes first!!!)");
		char playerChoice =  sc.next().charAt(0);


		Board game = new Board(gameSize);
		game.scale();
		printArr(game.represent);
		while(running) {
			if(game.turn %2 == 0) {
				System.out.println("Black's turn!");
				System.out.println("Make your move by using: src-dst");
				String bmove = sc.next();

				String[] binfo = bmove.split("-");
				char[] trans = {'a','b','c','d', 'e', 'f', 'g', 'h'};

				int yPos = Integer.MAX_VALUE;
				int xPos = Integer.MAX_VALUE;
				int yPos2 = 2*getIndex(trans, binfo[0].charAt(0)) + 2; 
				int xPos2 = 2*Character.forDigit(binfo[0].charAt(1), 10);

				int i = 0;
				while(yPos > game.represent.length || xPos > game.represent.length) {
					System.out.println("you moved piece " + binfo[0] + " to position " + binfo[1]);
					yPos = 2*getIndex(trans, binfo[0].charAt(0)) + 2; 
					xPos = 2*Character.forDigit(binfo[0].charAt(1), 10);
					yPos2 = 2*getIndex(trans, binfo[1].charAt(0)) + 2;
					System.out.println(yPos2);
					xPos2 = 2*Character.forDigit(binfo[1].charAt(1), 10);
					System.out.println(xPos2);
					if(i > 0) {
						System.out.println("not a valid move, try again");
					}
					i++;
				}
				
				game.gameBoard[yPos][xPos].update(binfo[1]);
				game.movePiece(game.gameBoard[yPos][xPos], binfo[1]);
				game.scale();
				printArr(game.represent);
				System.out.println("You played " + binfo[0]+"-" + binfo[1]);
				game.turn++;
			}
			if(game.turn% 2 != 0) {
				System.out.println("White's turn!");
				System.out.println("Make your move by using: src-dst");
				String wmove = sc.next();
				String[] winfo = wmove.split("-");
				char[] trans = {'a','b','c','d', 'e', 'f', 'g', 'h'};

				int yPos = Integer.MAX_VALUE;
				int xPos = Integer.MAX_VALUE;
				int yPos2 = 2*getIndex(trans, winfo[0].charAt(0)) + 2; 
				int xPos2 = 2*Character.forDigit(winfo[0].charAt(1), 10);

				int i = 0;
				while(yPos > game.represent.length || xPos > game.represent.length) {
					System.out.println("you moved piece " + winfo[0] + " to position " + winfo[1]);
					yPos = 2*getIndex(trans, winfo[0].charAt(0)) + 2; 
					xPos = 2*Character.forDigit(winfo[0].charAt(1), 10);
					yPos2 = 2*getIndex(trans, winfo[0].charAt(0)) + 2; 
					xPos2 = 2*Character.forDigit(winfo[0].charAt(1), 10);

					if(i > 0) {
						System.out.println("not a valid move, try again");
					}
					i++;
					
				}
				game.gameBoard[yPos][xPos].update(winfo[1]);
				game.scale();
				printArr(game.represent);
				System.out.println("You played" + winfo[0]+"-" + winfo[1]);
				game.turn++;
			}
		}
	}
}