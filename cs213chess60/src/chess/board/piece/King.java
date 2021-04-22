
/**
 * @authors Kairavi and Abhik
 */

package chess.board.piece;

import chess.board.Board;

/**
 * Class which represent King piece
 */
public class King extends Pieces {

	/**
	 * King(boolean isWhite) Constructor for a King Piece
	 * 
	 * @param isWhite - true if white else false
	 */
	public King(boolean isWhite) {
		super(isWhite);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * represent rook initial rank
	 */
	public static int oldRookRank = -1;
	
	/**
	 * represent rook initial file
	 */
	public static int oldRookFile = -1;
	
	/**
	 * represent rook new rank
	 */
	public static int newRookRank = -1;
	
	/**
	 * represent rook new file
	 */
	public static int newRookFile = -1;

	/**
	 * indicates if castling done
	 */
	public static boolean castlingCheck = false;
	
	/**
	 * indicates if castling move.
	 */
	public static boolean isCastlingMove = false;

	/**
	 * isValidMove(int startFile, int startRank, int endFile, int endRank) abstract method checks to see if move is valid from start cell to end cell
	 * 
	 * @param startFile - startCell's File
	 * @param startRank - startCell's Rank
	 * @param endFile - endCell's File
	 * @param endRank - endCell's Rank
	 * @return boolean
	 */
	@Override
	public boolean isValidMove(int startFile, int startRank, int endFile, int endRank) {

		// king can only move in one direction and check for castling.

		if (playCastleMove(startFile, startRank, endFile, endRank)) {
			isCastlingMove = true;
			return true;
		} else {
			isCastlingMove = false;
		}

		if (endFile != startFile + 1 && endFile != startFile - 1 && endFile != startFile) {
			return false;
		}
		if (endRank != startRank + 1 && endRank != startRank - 1 && endRank != startRank) {
			return false;
		}

		// Check if endCell is empty
		if (Board.cells[endRank][endFile].getPiece() != null) {

			// if same color piece is there
			if ((Board.cells[startRank][startFile].getPiece().isWhite()
					&& Board.cells[endRank][endFile].getPiece().isWhite())
					|| (!Board.cells[startRank][startFile].getPiece().isWhite()
							&& !Board.cells[endRank][endFile].getPiece().isWhite())) {
				return false;
			}

			// if opposite color piece is there
			else if ((Board.cells[startRank][startFile].getPiece().isWhite()
					&& !Board.cells[endRank][endFile].getPiece().isWhite())
					|| (!Board.cells[startRank][startFile].getPiece().isWhite()
							&& Board.cells[endRank][endFile].getPiece().isWhite())) {
				return true;

			}

			return false;
		}
		return true;

	}

	/**
	 * playCastleMove(int startFile, int startRank, int endFile, int endRank) checks if a King can make a castling move with a Rook
	 *  
	 * @param startFile - startCell's File
	 * @param startRank - startCell's Rank
	 * @param endFile - endCell's File
	 * @param endRank - endCell's Rank
	 * 
	 * @return boolean
	 */

	public boolean playCastleMove(int startFile, int startRank, int endFile, int endRank) {

		if (Board.cells[startRank][startFile].getPiece() == null) {
			return false;
		}

		if (Board.whitesTurn && Board.whiteCheck) {
			return false;
		}
		if (!Board.whitesTurn && Board.blackCheck) {
			return false;
		}

		if (Board.cells[startRank][startFile].getPiece() != null) {
			if (Board.whitesTurn && Board.cells[startRank][startFile].getPiece().getType() == "K"
					&& !(Board.cells[startRank][startFile].getPiece().isWhite())) {
				return false;
			}
		}

		if (Board.cells[startRank][startFile].getPiece() != null) {
			if (!Board.whitesTurn && Board.cells[startRank][startFile].getPiece().getType() == "K"
					&& (Board.cells[startRank][startFile].getPiece().isWhite())) {

				return false;
			}
		}

		if (Board.whitesTurn) {

			// if king is in check than move is not allowed
			if (Board.whiteCheck) {
				return false;
			}

			// white king must be in this position
			if (!(startRank == 7 && startFile == 4)) {
				return false;
			}

			if (Board.cells[startRank][startFile].getPiece().isFirstMove()) {
				return false;
			}

			if (!(endRank == 7 && (endFile == 2 || endFile == 6))) {
				return false;
			}

			if (endRank == 7 && endFile == 2) {

				if (Board.cells[7][0].getPiece() == null) {
					return false;
				}

				if (!((Board.cells[7][0].getPiece().getType() == "R") && (Board.cells[7][0].getPiece().isWhite()))) {
					return false;
				}

				if (Board.cells[7][0].getPiece().isFirstMove()) {
					return false;
				}

				// chosen rook
				oldRookRank = 7;
				oldRookFile = 0;

				newRookRank = 7;
				newRookFile = 3;

				// path must be clear between king and chosen rook
				if (!(Board.cells[7][1].getPiece() == null && Board.cells[7][2].getPiece() == null
						&& Board.cells[7][3].getPiece() == null)) {
					return false;
				}

				if (isKingInCheck(startRank, startFile, 7, 3)) {
					return false;
				}
				castlingCheck = true;
				if (isKingInCheck(startRank, startFile, endRank, endFile)) {
					return false;
				}

				return true;
			}

			if (endRank == 7 && endFile == 6) {

				if (Board.cells[7][7].getPiece() == null) {
					return false;
				}

				// rook must be in corner and not moved.
				if (!((Board.cells[7][7].getPiece().getType() == "R") && (Board.cells[7][7].getPiece().isWhite()))) {
					return false;
				}

				if (Board.cells[7][7].getPiece().isFirstMove()) {
					return false;
				}

				oldRookRank = 7;
				oldRookFile = 7;

				newRookRank = 7;
				newRookFile = 5;

				// path must be clear between king and chosen rook
				if (!(Board.cells[7][5].getPiece() == null && Board.cells[7][6].getPiece() == null)) {
					return false;
				}

				if (isKingInCheck(startRank, startFile, 7, 6)) {
					return false;
				}
				castlingCheck = true;
				if (isKingInCheck(startRank, startFile, endRank, endFile)) {
					return false;
				}

				oldRookRank = 7;
				oldRookFile = 7;
				newRookRank = 7;
				newRookFile = 5;

				return true;
			}

			return false;

		}

		// If Black's turn
		if (!(Board.cells[startRank][startFile].getPiece().isWhite())) {

			// if king is in check than move is not allowed
			if (!Board.whitesTurn) {
				if (Board.blackCheck) {
					return false;
				}
			}

			// black king must be in this position
			if (!(startRank == 0 && startFile == 4)) {
				return false;
			}

			// king must not have moved
			if (Board.cells[startRank][startFile].getPiece().isFirstMove()) {
				return false;
			}

			if (!(endRank == 0 && (endFile == 2 || endFile == 6))) {
				return false;
			}

			if (endRank == 0 && endFile == 2) {

				if (Board.cells[0][0].getPiece() == null) {
					return false;
				}

				// rook must be in corner and not moved.
				if (!((Board.cells[0][0].getPiece().getType() == "R")
						&& !(Board.cells[0][0].getPiece().isWhite()))) {
					return false;
				}

				if (Board.cells[0][0].getPiece().isFirstMove()) {
					return false;
				}

				oldRookRank = 0;
				oldRookFile = 0;

				newRookRank = 0;
				newRookFile = 3;

				// path must be clear between king and chosen rook
				if (!(Board.cells[0][1].getPiece() == null && Board.cells[0][2].getPiece() == null
						&& Board.cells[0][3].getPiece() == null)) {
					return false;
				}

				if (isKingInCheck(startRank, startFile, 0, 3)) {
					return false;
				}
				castlingCheck = true;
				if (isKingInCheck(startRank, startFile, endRank, endFile)) {
					return false;
				}

				return true;
			}

			if (endRank == 0 && endFile == 6) {

				if (Board.cells[0][7].getPiece() == null) {
					return false;
				}

				if (!((Board.cells[0][7].getPiece().getType() == "R")
						&& !(Board.cells[0][7].getPiece().isWhite()))) {
					return false;
				}

				if (Board.cells[0][7].getPiece().isFirstMove()) {
					return false;
				}

				oldRookRank = 0;
				oldRookFile = 7;

				newRookRank = 0;
				newRookFile = 5;

				// path must be clear between king and chosen rook
				if (!(Board.cells[0][5].getPiece() == null && Board.cells[0][6].getPiece() == null)) {
					return false;
				}

				if (isKingInCheck(startRank, startFile, 0, 5)) {
					return false;
				}
				castlingCheck = true;
				if (isKingInCheck(startRank, startFile, endRank, endFile)) {
					return false;
				}

				oldRookRank = 0;
				oldRookFile = 7;
				newRookRank = 0;
				newRookFile = 5;

				return true;
			}
			return false;

		}

		return false;
	}

	 /**
	 * isKingInCheck(int startRank, int startFile, int endRank, int endFile) check if current player king will be in check or not with this move.
	 * 
	 * @param startFile - startCell's File
	 * @param startRank - startCell's Rank
	 * @param endFile - endCell's File
	 * @param endRank - endCell's Rank
	 * 
	 * @return boolean
	 */
	public static boolean isKingInCheck(int startRank, int startFile, int endRank, int endFile) {

		int kingRank = -1;
		int kingFile = -1;
		Pieces rook = null;
		Pieces king = null;
		Pieces temp = null;

		if (castlingCheck == true) {

			//Castling Move
			rook = Board.cells[newRookRank][newRookFile].getPiece();
			Board.cells[newRookRank][newRookFile].setPiece(Board.cells[oldRookRank][oldRookFile].getPiece());
			Board.cells[oldRookRank][oldRookFile].setPiece(null);

			king = Board.cells[endRank][endFile].getPiece();
			Board.cells[endRank][endFile].setPiece(Board.cells[startRank][startFile].getPiece());
			Board.cells[startRank][startFile].setPiece(null);

		} else {

			temp = Board.cells[endRank][endFile].getPiece();
			Board.cells[endRank][endFile].setPiece(Board.cells[startRank][startFile].getPiece());
			Board.cells[startRank][startFile].setPiece(null);
		}

		if (castlingCheck == true) {
			kingRank = endRank;
			kingFile = endFile;
		}
		else {
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					if (Board.cells[i][j].getPiece() != null) {
						if ((Board.whitesTurn && Board.cells[i][j].getPiece().isWhite())
								|| (!Board.whitesTurn && !Board.cells[i][j].getPiece().isWhite())) {
							if (Board.cells[i][j].getPiece().getType() == "K") {
								kingRank = i;
								kingFile = j;
							}
						}
					}

				}
			}
		}
		// check if current move can put king in check
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (Board.cells[i][j].getPiece() != null) {
					if ((Board.whitesTurn && !Board.cells[i][j].getPiece().isWhite())
							|| (!Board.whitesTurn && Board.cells[i][j].getPiece().isWhite())) {
						if (Board.cells[i][j].getPiece().isValidMove(j, i, kingFile, kingRank)) {
							if (castlingCheck == true) {
								Board.cells[oldRookRank][oldRookFile]
										.setPiece(Board.cells[newRookRank][newRookFile].getPiece());
								Board.cells[newRookRank][newRookFile].setPiece(rook);
								Board.cells[startRank][startFile].setPiece(Board.cells[endRank][endFile].getPiece());
								Board.cells[endRank][endFile].setPiece(king);
							} else {
								Board.cells[startRank][startFile].setPiece(Board.cells[endRank][endFile].getPiece());
								Board.cells[endRank][endFile].setPiece(temp);
							}
							castlingCheck = false;
							return true;
						}
					}
				}
			}
		}
		if (castlingCheck == true) {
			Board.cells[oldRookRank][oldRookFile].setPiece(Board.cells[newRookRank][newRookFile].getPiece());
			Board.cells[newRookRank][newRookFile].setPiece(rook);
			Board.cells[startRank][startFile].setPiece(Board.cells[endRank][endFile].getPiece());
			Board.cells[endRank][endFile].setPiece(king);
		} else {
			Board.cells[startRank][startFile].setPiece(Board.cells[endRank][endFile].getPiece());
			Board.cells[endRank][endFile].setPiece(temp);
		}
		castlingCheck = false;
		return false;
	}

	/**
	 * isOpKingInCheck(int endRank, int endFile) will check if current move will put opposite king in check.
	 * 
	 * @param endFile - endCell's File
	 * @param endRank - endCell's Rank
	 *
	 * @return boolean
	 */

	public static boolean isOpKingInCheck(int endRank, int endFile) {

		int kingRank = -1;
		int kingFile = -1;

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (Board.cells[i][j].getPiece() != null) {
					if ((Board.cells[endRank][endFile].getPiece().isWhite() && !Board.cells[i][j].getPiece().isWhite())
							|| !(Board.cells[endRank][endFile].getPiece().isWhite()
									&& Board.cells[i][j].getPiece().isWhite())) {
						if (Board.cells[i][j].getPiece().getType() == "K") {
							kingRank = i;
							kingFile = j;
						}

					}
				}

			}
		}
		
		if (kingRank == -1) {
			if (Board.whitesTurn) {
				Board.blackCheck = false;
			}
			if (!Board.whitesTurn) {
				Board.whiteCheck = false;
			}
			return false;
		}

		// check for possible moves which can put king in check.
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {

				if (Board.cells[i][j].getPiece() != null) {
					if (!(Board.cells[endRank][endFile].getPiece().isWhite() && !Board.cells[i][j].getPiece().isWhite())
							|| (Board.cells[endRank][endFile].getPiece().isWhite()
									&& Board.cells[i][j].getPiece().isWhite())) {
						if (Board.cells[i][j].getPiece().isValidMove(j, i, kingFile, kingRank)) {
							return true;
						}

					}
				}
			}

		}
		return false;
	}
	
	/**
	 * opKingCheckMate(int endRank, int endFile) will check if the move will put opposite king in checkmate.
	 * 
	 * @param endFile - endCell's File
	 * @param endRank - endCell's Rank
	 * 
	 * @return boolean
	 */
	public static boolean opKingCheckMate(int endRank, int endFile) {

		// check for opposite player checkmate
		if (!(isOpKingInCheck(endRank, endFile))) {
			return false;
		}

		int kingRank = -1;
		// first, find location of opponent's king
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {

				// if we have come across a piece
				if (Board.cells[i][j].getPiece() != null) {

					// only check for opponents king
					if ((!Board.cells[endRank][endFile].getPiece().isWhite() && Board.cells[i][j].getPiece().isWhite())
							|| (Board.cells[endRank][endFile].getPiece().isWhite()
									&& !Board.cells[i][j].getPiece().isWhite())) {

						if (Board.cells[i][j].getPiece().getType() == "K") {
							kingRank = i;
						}

					}
				}

			}
		}

		// Check for all the possible moves to decide if opponent is checkmate.
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				
				if (Board.cells[i][j].getPiece() != null) {

					// check for opposite pieces.
					if ((Board.cells[endRank][endFile].getPiece().isWhite() && !Board.cells[i][j].getPiece().isWhite())
							|| (!Board.cells[endRank][endFile].getPiece().isWhite()
									&& Board.cells[i][j].getPiece().isWhite())) {

						for (int k = 0; k < 8; k++) {
							for (int l = 0; l < 8; l++) {

								if (Board.cells[i][j].getPiece() != null) {

									// check for valid moves & iterate through all the moves virtually.
									if (Board.cells[i][j].getPiece().isValidMove(j, i, l, k)) {

										
										Pieces temp = Board.cells[k][l].getPiece();
										Board.cells[k][l].setPiece(Board.cells[i][j].getPiece());
										Board.cells[i][j].setPiece(null);

										if (!(isOpKingInCheck(endRank, endFile))) {

											Board.cells[i][j].setPiece(Board.cells[k][l].getPiece());
											Board.cells[k][l].setPiece(temp);
										
											Board.checkMate = false;
											return false;
										}
										Board.cells[i][j].setPiece(Board.cells[k][l].getPiece());
										Board.cells[k][l].setPiece(temp);

									}

								}
							}

						}

					}
				}
			}

		}
		
		Board.checkMate = true;
		return true;
	}
	
	/**
	 * playMove(int startFile, int startRank, int endFile, int endRank, char promotion) abstract method moves a piece if isValidMove is true
	 * 
	 * @param startFile - startCell's File
	 * @param startRank - startCell's Rank
	 * @param endFile - endCell's File
	 * @param endRank - endCell's Rank
	 * @param promotion  - promotion value Q/R/N/B for pawn promotion
	 * 
	 * @return boolean
	 */
	@Override
	public boolean playMove(int startFile, int startRank, int endFile, int endRank, char promotion) {
		if (isValidMove(startFile, startRank, endFile, endRank)) {

			// Check if current move will put king in check.
			if (isKingInCheck(startRank, startFile, endRank, endFile)) {
				return false;
			} else {
				if (Board.whitesTurn) {
					Board.whiteCheck = false;
				}
				if (!Board.whitesTurn) {
					Board.blackCheck = false;
				}
			}
			if (playCastleMove(startFile, startRank, endFile, endRank)) {
				
				// move king
				Board.cells[endRank][endFile].setPiece(Board.cells[startRank][startFile].getPiece());
				Board.cells[startRank][startFile].setPiece(null);
				Board.cells[endRank][endFile].getPiece().setFirstMove(true);

				// move rook
				Board.cells[newRookRank][newRookFile].setPiece(Board.cells[oldRookRank][oldRookFile].getPiece());
				Board.cells[oldRookRank][oldRookFile].setPiece(null);
				Board.cells[newRookRank][newRookFile].getPiece().setFirstMove(true);

				if (isOpKingInCheck(endRank, endFile)) {

					if (Board.whitesTurn) {
						Board.blackCheck = true;
					}
					if (!Board.whitesTurn) {
						Board.whiteCheck = true;
					}
				} else {
					if (Board.whitesTurn) {
						Board.blackCheck = false;
					}
					if (!Board.whitesTurn) {
						Board.whiteCheck = false;
					}
				}

				if (King.opKingCheckMate(endRank, endFile)) {
					Board.checkMate = true;
				}
				Pawn.resetTempRankFile();
				return true;
			}

			Board.cells[endRank][endFile].setPiece(Board.cells[startRank][startFile].getPiece());
			Board.cells[startRank][startFile].setPiece(null);
			Board.cells[endRank][endFile].getPiece().setFirstMove(true);

			
			if (isOpKingInCheck(endRank, endFile)) {
				if (Board.whitesTurn) {
					Board.blackCheck = true;
				}
				if (!Board.whitesTurn) {
					Board.whiteCheck = true;
				}
			} else {
				if (Board.whitesTurn) {
					Board.blackCheck = false;
				}
				if (!Board.whitesTurn) {
					Board.whiteCheck = false;
				}
			}


			if (opKingCheckMate(endRank, endFile)) {
				Board.checkMate = true;
			}

			Pawn.resetTempRankFile();
			return true;
		}
		return false;

	}
	
	/**
	 * isPathClear(int startFile, int startRank, int endFile, int endRank) abstract method Checks if the path from start cell to end cell is clear
	 * 
	 * @param startFile - startCell's File
	 * @param startRank - startCell's Rank
	 * @param endFile - endCell's File
	 * @param endRank - endCell's Rank
	 * 
	 * @return boolean
	 */
	@Override
	public boolean isPathClear(int startFile, int startRank, int endFile, int endRank) {
		return false;
	}
}
