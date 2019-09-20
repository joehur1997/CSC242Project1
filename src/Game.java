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
		System.out.println("This is Java Checkers by Christopher Pak & Joe Hur");
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
		}
	}
