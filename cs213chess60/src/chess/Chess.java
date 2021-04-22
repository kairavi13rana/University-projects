/**
 * @authors Kairavi and Abhik
 */
package chess;

import java.util.Scanner;

import chess.board.Board;
/**
 * 
 * The Main Chess Game Class
 *
 */
public class Chess {

	/**
	 * Main method of Chess game.
	 * @param args - method argument.
	 */
	public static void main(String[] args) {
		// final
		Scanner scanner = new Scanner(System.in);
		Board board = new Board();

		board.resetBoard();
		board.printBoard();

		boolean boardValidation = true;
		
		//Game Loop
		while (board.gameOver() == false) {

			System.out.print("\nWhite's move: ");
			Board.whitesTurn = true;
			String input = scanner.nextLine();

			boardValidation = board.validateAndExecuteMove(input);

			while (board.gameOver() == false && boardValidation == false) {
				System.out.println("Illegal move, try again");
				System.out.print("\nWhite's move: ");
				input = scanner.nextLine();
				boardValidation = board.validateAndExecuteMove(input);
			}

			if (board.getGameStatus() == "inCheck") {
				System.out.println("Check");
			}

			if (board.gameOver() == true || board.getGameStatus() == "checkMate") {
				System.out.println();
				board.printBoard();
				System.out.println();
				break;
			}

			System.out.println();
			board.printBoard();

			System.out.print("\nBlack's move: ");
			Board.whitesTurn = false;
			input = scanner.nextLine();

			boardValidation = board.validateAndExecuteMove(input);

			while (board.gameOver() == false && boardValidation == false) {
				System.out.println("Illegal move, try again");
				System.out.print("\nBlack's move: ");
				input = scanner.nextLine();
				boardValidation = board.validateAndExecuteMove(input);

			}

			if (board.getGameStatus() == "inCheck") {
				System.out.println("Check");
			}

			if (board.gameOver() == true || board.getGameStatus() == "checkMate") {
				System.out.println();
				board.printBoard();
				System.out.println();
				break;
			}

			System.out.println();
			board.printBoard();

		}

		if (board.getGameStatus() == "checkMate" && Board.whitesTurn) {
			System.out.println("Checkmate");
			System.out.println("White wins");
		} else if (board.getGameStatus() == "checkMate" && !Board.whitesTurn) {
			System.out.println("Checkmate");
			System.out.println("Black wins");
		} else if (board.getGameStatus() == "drawAccepted") {
			System.out.println("Draw");
		} else if (board.getGameStatus() == "whiteWins") {
			System.out.println("White wins");
		} else if (board.getGameStatus() == "blackWins") {
			System.out.println("Black wins");
		}

	}

}
