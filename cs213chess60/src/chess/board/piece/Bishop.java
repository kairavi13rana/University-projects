
/**
 * @authors Kairavi and Abhik
 */
package chess.board.piece;

import chess.board.Board;

/**
 * Class which represent Bishop piece
 */
public class Bishop extends Pieces {

	/**
	 * Bishop(boolean isWhite) one argument constructor for Bishop which initialize
	 * object by setting isWhite true/false to indicates its color.
	 * 
	 * @param isWhite - true for white and false for black.
	 */
	public Bishop(boolean isWhite) {
		super(isWhite);
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

		// Only diagonal move is allowed
		if (Math.abs(endRank - startRank) != Math.abs(endFile - startFile)) {
			return false;
		}
		// Check for clear path
		if (!isPathClear(startFile, startRank, endFile, endRank)) {
			return false;
		}

		// check for new cell for any piece.
		if (Board.cells[endRank][endFile].getPiece() == null) {
			return true;
		}

		// if cell is not empty and have same color piece
		else if ((Board.cells[startRank][startFile].getPiece().isWhite()
				&& Board.cells[endRank][endFile].getPiece().isWhite())
				|| (!Board.cells[startRank][startFile].getPiece().isWhite()
						&& !Board.cells[endRank][endFile].getPiece().isWhite())) {

			return false;
		}

		// if cell is not empty and have opposite color piece
		else if ((Board.cells[startRank][startFile].getPiece().isWhite()
				&& !Board.cells[endRank][endFile].getPiece().isWhite())
				|| (!Board.cells[startRank][startFile].getPiece().isWhite()
						&& Board.cells[endRank][endFile].getPiece().isWhite())) {
			return true;

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
	@Override
	public boolean isPathClear(int startFile, int startRank, int endFile, int endRank) {

		do {
			if (startFile < endFile) {
				startFile++;
			} else if (startFile > endFile) {
				startFile--;
			} else {

			}

			if (startRank < endRank) {
				startRank++;
			} else if (startRank > endRank) {
				startRank--;
			} else {

			}

			if (Board.cells[startRank][startFile].getPiece() != null) {
				if ((Board.cells[startRank][startFile].getPiece() != null)
						&& (startFile != endFile && startRank != endRank)) {
					return false;
				}
			}

		} while (startFile != endFile && startRank != endRank);

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

			if (King.isKingInCheck(startRank, startFile, endRank, endFile)) {
				return false;
			} else {

				if (Board.whitesTurn == true) {
					Board.whiteCheck = false;
				}
				if (!Board.whitesTurn == true) {
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
