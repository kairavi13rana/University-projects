/**
 * @authors Kairavi and Abhik
 */
package chess.board.piece;

import chess.board.Board;
import chess.board.utils.Cell;

/**
 * Class which represent Queen piece
 */
public class Queen extends Pieces {

	/**
	 * Queen(boolean isWhite) Constructor for a Queen Piece
	 * 
	 * @param isWhite - true if white else false
	 */
	public Queen(boolean isWhite) {
		super(isWhite);
		// TODO Auto-generated constructor stub
	}

	/**
	 * isValidMove(int startFile, int startRank, int endFile, int endRank) abstract
	 * method checks to see if move is valid from start cell to end cell
	 * 
	 * @param startFile - startCell's File
	 * @param startRank - startCell's Rank
	 * @param endFile   - endCell's File
	 * @param endRank   - endCell's Rank
	 * @return boolean
	 */
	@Override
	public boolean isValidMove(int startFile, int startRank, int endFile, int endRank) {

		boolean pathClear = isPathClear(startFile, startRank, endFile, endRank);

		if (pathClear) {
			if (Board.cells[endRank][endFile].getPiece() != null) {
				if ((Board.cells[endRank][endFile].getPiece().isWhite()
						&& !Board.cells[startRank][startFile].getPiece().isWhite())
						|| (!Board.cells[endRank][endFile].getPiece().isWhite()
								&& Board.cells[startRank][startFile].getPiece().isWhite())) {
					return true;
				} else {
					return false;
				}

			}

			else {
				return true;
			}
		}

		return false;
	}

	/**
	 * isPathClear(int startFile, int startRank, int endFile, int endRank) abstract
	 * method Checks if the path from start cell to end cell is clear
	 * 
	 * @param startFile - startCell's File
	 * @param startRank - startCell's Rank
	 * @param endFile   - endCell's File
	 * @param endRank   - endCell's Rank
	 * 
	 * @return boolean
	 */
	public boolean isPathClear(int startFile, int startRank, int endFile, int endRank) {

		// Check for vertical move
		if (startFile == endFile) {
			while (startRank != endRank) {
				if (startRank > endRank) {
					if (Board.cells[startRank - 1][startFile].getPiece() != null && endRank != startRank - 1) {
						return false;
					}
					startRank--;
				} else if (startRank < endRank) {
					if (Board.cells[startRank + 1][startFile].getPiece() != null && endRank != startRank + 1) {
						return false;
					}
					startRank++;
				}

			}
			return true;
		}

		// Check for horizontal move
		else if (startRank == endRank) {
			while (startFile != endFile) {
				if (startFile > endFile) {
					if (Board.cells[startRank][startFile - 1].getPiece() != null && endFile != startFile - 1) {
						return false;
					}
					startFile--;
				} else if (startFile < endFile) {
					if (Board.cells[startRank][startFile + 1].getPiece() != null && endFile != startFile + 1) {
						return false;
					}
					startFile++;
				}

			}
			return true;
		}

		// Check for diagonal move
		else if (startRank != endRank && startFile != endFile) {
			if (Math.abs(endRank - startRank) != Math.abs(endFile - startFile)) {
				return false;
			}
			do {
				if (startFile < endFile) {
					startFile++;
				} else {
					startFile--;
				}

				if (startRank < endRank) {
					startRank++;
				} else {
					startRank--;
				}

				if ((Board.cells[startRank][startFile].getPiece() != null)
						&& (startFile != endFile && startRank != endRank)) {

					return false;
				}

			} while (startFile != endFile && startRank != endRank);
			return true;
		}
		return false;
	}

	/**
	 * playMove(int startFile, int startRank, int endFile, int endRank, char
	 * promotion) abstract method moves a piece if isValidMove is true
	 * 
	 * @param startFile - startCell's File
	 * @param startRank - startCell's Rank
	 * @param endFile   - endCell's File
	 * @param endRank   - endCell's Rank
	 * @param promotion - promotion value Q/R/N/B for pawn promotion
	 * 
	 * @return boolean
	 */
	public boolean playMove(int startFile, int startRank, int endFile, int endRank, char promotion) {

		if (isValidMove(startFile, startRank, endFile, endRank)) {

			// Check if player's king will be in check after this move, if yes than dont
			// allow this move.
			if (King.isKingInCheck(startRank, startFile, endRank, endFile)) {
				return false;
			} else {

				if (Board.whitesTurn) {
					Board.whiteCheck = false;
				}
				if (!Board.whitesTurn) {
					Board.blackCheck = false;
				}
			}

			Board.cells[endRank][endFile].setPiece(Board.cells[startRank][startFile].getPiece());
			Board.cells[startRank][startFile].setPiece(null);
			Board.cells[endRank][endFile].getPiece().setFirstMove(true);

			if (King.isOpKingInCheck(endRank, endFile)) {
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
		return false;
	}

}
