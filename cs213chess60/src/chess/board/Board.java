/**
 * @authors Kairavi And Abhik 
 */
package chess.board;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import chess.board.piece.Bishop;
import chess.board.piece.King;
import chess.board.piece.Knight;
import chess.board.piece.Pawn;
import chess.board.piece.Queen;
import chess.board.piece.Rook;
import chess.board.utils.Cell;

/**
 * Class which defines chess board.
 */
public class Board {

	/**
	 * represent 8X8 chess board grid.
	 */
	public static Cell[][] cells = new Cell[8][8];

	/**
	 * stores user input start rank
	 */
	public int startRank;
	
	/**
	 * stores user input start file
	 */
	public int startFile;
	
	/**
	 * stores user input end rank
	 */
	public int endRank;
	
	/**
	 * stores user input end file
	 */
	public int endFile;
	
	/**
	 *  stores user input pawn promotion
	 */
	public char promotion;
	
	/**
	 *  indicates if white resigns
	 */
	public static boolean whiteResigns = false;
	
	/**
	 *  indicates if black resigns
	 */
	public static boolean blackResigns = false;
	
	/**
	 * indicates if draw has been requested
	 */
	public static boolean drawRequested = false;
	
	/**
	 *  indicates if draw request accepted
	 */
	public static boolean drawAccepted = false;

	/**
	 *  indicates whose turn it is white or black. 
	 */
	public static boolean whitesTurn = false;
	
	/**
	 *  indicates if game is over.
	 */
	public static boolean gameOver = false;
	
	/**
	 * indicates if white player is in check
	 */
	public static boolean whiteCheck = false;
	
	/**
	 * indicates if black player is in check
	 */
	public static boolean blackCheck = false;
	
	/**
	 * indicates if checkmate
	 */
	public static boolean checkMate = false;
	
	/**
	 * indicates if white player wins
	 */
	public static boolean whiteWins = false;
	
	/**
	 * indicates if black player wins
	 */
	public static boolean blackWins = false;

	/**
	 * Board() empty constructor for Board class.
	 */
	public Board() {

	}

	/**
	 * resetBoard() : initializes the chess board with for fresh start with all the
	 * pieces.
	 * 
	 */
	public void resetBoard() {

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {

				if ((i % 2 != 0 && j % 2 == 0) || (i % 2 == 0 && j % 2 != 0)) {
					cells[i][j] = new Cell(true);
				} else {
					cells[i][j] = new Cell(false);
				}
			}
		}

		// create all the piepce on the board for new game.
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (i == 0) {
					if (j == 0 || j == 7) {
						cells[i][j].setPiece(new Rook(false));
						cells[i][j].getPiece().setType("R");
					} else if (j == 1 || j == 6) {
						cells[i][j].setPiece(new Knight(false));
						cells[i][j].getPiece().setType("N");
					} else if (j == 2 || j == 5) {
						cells[i][j].setPiece(new Bishop(false));
						cells[i][j].getPiece().setType("B");
					} else if (j == 3) {
						cells[i][j].setPiece(new Queen(false));
						cells[i][j].getPiece().setType("Q");
					} else {
						cells[i][j].setPiece(new King(false));
						cells[i][j].getPiece().setType("K");
					}
				}
				if (i == 1) {
					cells[i][j].setPiece(new Pawn(false));
					cells[i][j].getPiece().setType("p");
				}

				if (i == 7) {
					if (j == 0 || j == 7) {
						cells[i][j].setPiece(new Rook(true));
						cells[i][j].getPiece().setType("R");
					} else if (j == 1 || j == 6) {
						cells[i][j].setPiece(new Knight(true));
						cells[i][j].getPiece().setType("N");
					} else if (j == 2 || j == 5) {
						cells[i][j].setPiece(new Bishop(true));
						cells[i][j].getPiece().setType("B");
					} else if (j == 3) {
						cells[i][j].setPiece(new Queen(true));
						cells[i][j].getPiece().setType("Q");
					} else {
						cells[i][j].setPiece(new King(true));
						cells[i][j].getPiece().setType("K");
					}
				}
				if (i == 6) {
					cells[i][j].setPiece(new Pawn(true));
					cells[i][j].getPiece().setType("p");
				}

			}
		}
	}

	/**
	 * printBoard() - print board on console.
	 * 
	 */
	public void printBoard() {

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 9; j++) {
				if (j == 8) {
					int z = 8 - i;
					System.out.print(Integer.toString(z));
					System.out.println();
					continue;
				} else if (cells[i][j].isBlack() && cells[i][j].getPiece() == null) {
					System.out.print("## ");
				} else if (!cells[i][j].isBlack() && cells[i][j].getPiece() == null) {
					System.out.print("   ");
				}
				// if there is a piece on the cell than print that piece information instead of
				// color.
				else {
					if (cells[i][j].getPiece().getType() == "R") {
						if (cells[i][j].getPiece().isWhite()) {
							System.out.print("wR ");
						} else {
							System.out.print("bR ");
						}
					}
					if (cells[i][j].getPiece().getType() == "N") {
						if (cells[i][j].getPiece().isWhite()) {
							System.out.print("wN ");
						} else {
							System.out.print("bN ");
						}

					}
					if (cells[i][j].getPiece().getType() == "B") {
						if (cells[i][j].getPiece().isWhite()) {
							System.out.print("wB ");
						} else {
							System.out.print("bB ");
						}

					}
					if (cells[i][j].getPiece().getType() == "Q") {
						if (cells[i][j].getPiece().isWhite()) {
							System.out.print("wQ ");
						} else {
							System.out.print("bQ ");
						}

					}
					if (cells[i][j].getPiece().getType() == "K") {
						if (cells[i][j].getPiece().isWhite()) {
							System.out.print("wK ");
						} else {
							System.out.print("bK ");
						}

					}
					if (cells[i][j].getPiece().getType() == "p") {
						if (cells[i][j].getPiece().isWhite()) {
							System.out.print("wp ");
						} else {
							System.out.print("bp ");
						}

					}

				}
			}
		}
		System.out.print(" a  b  c  d  e  f  g  h ");
		System.out.println("");
	}

	/**
	 * isValidInput(String input) will check and parse the player input and return
	 * false if input is invalid.
	 * 
	 * @param input - player input
	 * @return boolean
	 */
	public boolean isValidInput(String input) {

		StringTokenizer st = null;
		input = input.toLowerCase();
		input = input.trim();

		if (input.length() < 4) {
			return false;
		}

		ArrayList<String> ipList = new ArrayList<String>();

		st = new StringTokenizer(input);

		while (st.hasMoreTokens()) {
			ipList.add(st.nextToken());
		}

		if (ipList.size() == 1 && !ipList.get(0).trim().equalsIgnoreCase("resign")
				&& !ipList.get(0).trim().equalsIgnoreCase("draw")) {
			return false;
		}

		if (ipList.size() == 3 && ipList.get(2).trim().equalsIgnoreCase("draw?")) {
			drawRequested = true;
		}
		if (ipList.size() == 1 && drawRequested && ipList.get(0).trim().equalsIgnoreCase("draw")) {
			drawAccepted = true;
			gameOver = true;
			return true;
		}

		if (ipList.size() == 1 && ipList.get(0).trim().equalsIgnoreCase("resign")) {
			gameOver = true;
			if (whitesTurn) {
				blackWins = true;
			}
			if (!whitesTurn) {
				whiteWins = true;
			}
			return true;
		}

		if (ipList.size() == 3 && !ipList.get(2).trim().equalsIgnoreCase("draw?")) {
			drawRequested = true;
		}

		startFile = extractFileFromText(ipList.get(0));
		startRank = extractRankFromText(ipList.get(0));
		endFile = extractFileFromText(ipList.get(1));
		endRank = extractRankFromText(ipList.get(1));

		if (startFile == -1 || startRank == -1 || endFile == -1 || endRank == -1) {
			return false;
		}

		if (ipList.size() == 3) {
			promotion = extractPawnPromotionPiece(ipList.get(2));
		} else {
			promotion = extractPawnPromotionPiece(null);
		}

		return true;

	}

	/**
	 * validateAndExecuteMove(String playerInput) will take the player input as
	 * parameter and than validate it, if it with isValdInput() method, if validated
	 * it will execute the move.
	 * 
	 * @param playerInput - player input
	 * @return boolean
	 */

	public boolean validateAndExecuteMove(String playerInput) {

		if (gameOver == true) {
			return false;
		}

		boolean validinput = isValidInput(playerInput);
		if (validinput == false) {
			return false;
		}
		if (cells[startRank][startFile].getPiece() == null) {
			return false;
		}

		if (whitesTurn) {
			if (!(cells[startRank][startFile].getPiece().isWhite())) {
				return false;
			}
		}

		if (!whitesTurn) {
			if (cells[startRank][startFile].getPiece().isWhite()) {
				return false;
			}
		}

		boolean b = cells[startRank][startFile].getPiece().playMove(startFile, startRank, endFile, endRank, promotion);

		return b;

	}

	/**
	 * gameOver() - check for game over
	 * 
	 * @return boolean
	 */
	public boolean gameOver() {

		if (gameOver == true) {
			return true;
		}

		else {
			return false;
		}

	}

	/**
	 * getGameStatus() return the current status of the game.
	 * 
	 * @return String
	 */
	public String getGameStatus() {
		if (checkMate == true) {
			return "checkMate";
		}

		if (drawAccepted == true) {
			return "drawAccepted";
		}

		if (whiteCheck == true || blackCheck == true) {
			return "inCheck";
		}

		if (whiteWins == true || blackResigns == true) {
			return "whiteWins";
		}
		if (blackWins == true || whiteResigns == true) {
			return "blackWins";
		}

		return "running";
	}

	/**
	 * extractFileFromText(String input) return the file from text.
	 * 
	 * @return int
	 */
	private int extractFileFromText(String input) {
		char file = input.trim().charAt(0);
		int tempFile = 0;
		switch (file) {
		case 'a':
			tempFile = 0;
			break;
		case 'b':
			tempFile = 1;
			break;
		case 'c':
			tempFile = 2;
			break;
		case 'd':
			tempFile = 3;
			break;
		case 'e':
			tempFile = 4;
			break;
		case 'f':
			tempFile = 5;
			break;
		case 'g':
			tempFile = 6;
			break;
		case 'h':
			tempFile = 7;
			break;
		default:
			tempFile = -1;
			break;
		}

		return tempFile;
	}

	/**
	 * extractRankFromText(String input) return the rank from text.
	 * 
	 * @return int
	 */
	private int extractRankFromText(String input) {
		char rank = input.trim().charAt(1);
		int tempRank = 0;
		switch (rank) {
		case '1':
			tempRank = 7;
			break;
		case '2':
			tempRank = 6;
			break;
		case '3':
			tempRank = 5;
			break;
		case '4':
			tempRank = 4;
			break;
		case '5':
			tempRank = 3;
			break;
		case '6':
			tempRank = 2;
			break;
		case '7':
			tempRank = 1;
			break;
		case '8':
			tempRank = 0;
			break;
		default:
			tempRank = -1;
			break;
		}

		return tempRank;
	}

	/**
	 * extractPawnPromotionPiece(String input) return the pawn promotion piece value.
	 * 
	 * @return char
	 */
	private char extractPawnPromotionPiece(String input) {
		if (input == null) {
			return 'Q';
		} else {
			return input.charAt(0);
		}
	}
}
