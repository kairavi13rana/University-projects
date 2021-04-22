
/**
 * @authors Kairavi and Abhik
 */

package chess.board.piece;

import chess.board.Board;
import chess.board.utils.Cell;

/**
 * Class which represent Knight piece
 */
public class Knight extends Pieces {

	/**
	 * Knight(boolean isWhite) Constructor for a Knight Piece
	 * 
	 * @param isWhite - true if white else false
	 */
	public Knight(boolean isWhite) {
		super(isWhite);
		// TODO Auto-generated constructor stub
	}

	/**
	 * isValidMove(int startFile, int startRank, int endFile, int endRank) abstract method checks to see if move is valid from start cell to
	 * end cell
	 * 
	 * @param startFile - startCell's File
	 * @param startRank - startCell's Rank
	 * @param endFile   - endCell's File
	 * @param endRank   - endCell's Rank
	 * @return boolean
	 */
	@Override
	public boolean isValidMove(int startFile, int startRank, int endFile, int endRank) {

		/* Check for left or right L shaped kinght move */
		if (startRank + 2 != endRank && startRank - 2 != endRank && startRank + 1 != endRank
				&& startRank - 1 != endRank) {
			return false;
		}

		if (startFile + 2 != endFile && startFile - 2 != endFile && startFile + 1 != endFile
				&& startFile - 1 != endFile) {
			return false;
		}

		if ((startRank + 2 == endRank || startRank - 2 == endRank)
				&& !(startFile + 1 == endFile || startFile - 1 == endFile)) {
			return false;
		}
		if ((startFile + 2 == endFile || startFile - 2 == endFile)
				&& !(startRank + 1 == endRank || startRank - 1 == endRank)) {
			return false;
		}

		if ((startFile + 1 == endFile || startFile - 1 == endFile)
				&& (startRank + 1 == endRank || startRank - 1 == endRank)) {
			return false;
		}

		if ((startFile + 2 == endFile || startFile - 2 == endFile)
				&& (startRank + 2 == endRank || startRank - 2 == endRank)) {
			return false;
		}

		
		/* Check for end cell if it is null or have piece, if it has than what color of piece */
		if (Board.cells[endRank][endFile].getPiece() == null) {
			return true;
		}
		if (Board.cells[endRank][endFile].getPiece() != null) {
			if ((Board.cells[startRank][startFile].getPiece().isWhite()
					&& Board.cells[endRank][endFile].getPiece().isWhite())
					|| (!Board.cells[startRank][startFile].getPiece().isWhite()
							&& !Board.cells[endRank][endFile].getPiece().isWhite())) {
				return false;
			} else if ((Board.cells[startRank][startFile].getPiece().isWhite()
					&& !Board.cells[endRank][endFile].getPiece().isWhite())
					|| (!Board.cells[startRank][startFile].getPiece().isWhite()
							&& Board.cells[endRank][endFile].getPiece().isWhite())) {

				return true;

			}

			return false;
		}

		return false;
	}

	/**
	 * isPathClear(int startFile, int startRank, int endFile, int endRank) abstract method Checks if the path from start cell to end cell is
	 * clear
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
		return false;
	}

	/**
	 * playMove(int startFile, int startRank, int endFile, int endRank, char promotion) abstract method moves a piece if isValidMove is true
	 * 
	 * @param startFile - startCell's File
	 * @param startRank - startCell's Rank
	 * @param endFile   - endCell's File
	 * @param endRank   - endCell's Rank
	 * @param promotion - promotion value Q/R/N/B for pawn promotion
	 * 
	 * @return boolean
	 */
	@Override
	public boolean playMove(int startFile, int startRank, int endFile, int endRank, char promotion) {
		if (isValidMove(startFile, startRank, endFile, endRank)) {

			//Check if king is in check or not before allowing the current move
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
