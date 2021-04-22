/**
 * @authors Kairavi and Abhik
 */

package chess.board.piece;

import chess.board.Board;

/**
 * Class which represent Pawn piece
 */
public class Pawn extends Pieces {

	/**
	 * temporary variable to store Pawn rank 
	 */
	static int tempRank1 = -1; 
	
	/**
	 * temporary variable to store PAwn file
	 */
	static int tempFile1 = -1;

	static Pieces enpL = null, enpR = null;

	static boolean enpassantL, enpassantR, canEnp;

	/**
	 * temporary variable to store Pawn rank 
	 */
	public static int tempRank = -1;
	
	/**
	 * temporary variable to store Pawn file 
	 */
	public static int tempFile = -1;

	/**
	 * Pawn(boolean isWhite) Constructor for a Pawn Piece
	 * 
	 * @param isWhite - true if white else false
	 */
	public Pawn(boolean isWhite) {
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

		if (Board.cells[endRank][endFile].getPiece() != null) {
			{
				if ((Board.cells[endRank][endFile].getPiece().isWhite()
						&& !Board.cells[startRank][startFile].getPiece().isWhite())
						|| (!Board.cells[endRank][endFile].getPiece().isWhite()
								&& Board.cells[startRank][startFile].getPiece().isWhite())) {
					if (endFile == startFile - 1 || endFile == startFile + 1) {
						if (!Board.cells[startRank][startFile].getPiece().isWhite() && endRank == startRank + 1) {
							return true;
						} else if (Board.cells[startRank][startFile].getPiece().isWhite() && endRank == startRank - 1) {
							return true;
						}

					}
				}
				return false;
			}
		}

		else {
			if (Board.cells[startRank][startFile].getPiece().isWhite()) {
				if (startRank == 6 && (endRank == startRank - 2)) {
					if (isPathClear(startFile, startRank, endFile, endRank)) {
						if (endFile != 0 && endFile != 7) {
							if (Board.cells[endRank][endFile - 1].getPiece() != null
									&& !Board.cells[endRank][endFile - 1].getPiece().isWhite()) {
								if (Board.cells[endRank][endFile - 1].getPiece().getType().equals("p")) {

									enpL = Board.cells[endRank][endFile - 1].getPiece();
									enpassantL = true;
								}
							}

							if (Board.cells[endRank][endFile + 1].getPiece() != null
									&& !Board.cells[endRank][endFile + 1].getPiece().isWhite()) {

								if (Board.cells[endRank][endFile + 1].getPiece().getType().equals("p")) {
									enpR = Board.cells[endRank][endFile + 1].getPiece();
									enpassantR = true;
								}
							}
						}

						else if (endFile == 0) {

							if (Board.cells[endRank][endFile + 1].getPiece() != null
									&& !Board.cells[endRank][endFile + 1].getPiece().isWhite()) {
								if (Board.cells[endRank][endFile + 1].getPiece().getType().equals("p")) {
									enpR = Board.cells[endRank][endFile + 1].getPiece();
									enpassantR = true;
								}
							}
						} else if (endFile == 7) {
							if (Board.cells[endRank][endFile - 1].getPiece() != null
									&& !Board.cells[endRank][endFile - 1].getPiece().isWhite()) {
								if (Board.cells[endRank][endFile - 1].getPiece().getType().equals("p")) {
									enpL = Board.cells[endRank][endFile - 1].getPiece();
									enpassantL = true;
								}
							}
						}

						if (enpassantL || enpassantR) {
							tempRank1 = endRank;
							tempFile1 = endFile;
						}
						return true;
					}

				}

				else if (startRank == 6 && endRank == startRank - 1) {
					if (isPathClear(startFile, startRank, endFile, endRank)) {
						return true;
					}

				}


				else if ((startRank != 6 && endRank == startRank - 1) && (startFile == endFile)) {
					resetTempRankFile();
					return true;
				}

				else if ((startRank == 3 && endRank == 2) && (endFile == startFile + 1 || endFile == startFile - 1)) {
					if (enpassant(startFile, startRank, endFile, endRank))
						canEnp = true;
					else
						canEnp = false;
					return canEnp;

				}
			}

			else if (!Board.cells[startRank][startFile].getPiece().isWhite()) {
				if (startRank == 1 && (endRank == startRank + 2)) {
					if (isPathClear(startFile, startRank, endFile, endRank)) {
						if ((endFile != 0 && endFile != 7)) {
							if (Board.cells[endRank][endFile - 1].getPiece() != null
									&& Board.cells[endRank][endFile - 1].getPiece().isWhite()) {
								if (Board.cells[endRank][endFile - 1].getPiece().getType().equals("p")) {

									enpL = Board.cells[endRank][endFile - 1].getPiece();
									enpassantL = true;
								}
							}

							if (Board.cells[endRank][endFile + 1].getPiece() != null
									&& Board.cells[endRank][endFile + 1].getPiece().isWhite()) {

								if (Board.cells[endRank][endFile + 1].getPiece().getType().equals("p")) {
									enpR = Board.cells[endRank][endFile + 1].getPiece();
									enpassantR = true;
								}
							}
						}

						else if (endFile == 0) {
							if (Board.cells[endRank][endFile + 1].getPiece() != null
									&& Board.cells[endRank][endFile + 1].getPiece().isWhite()) {
								if (Board.cells[endRank][endFile + 1].getPiece().getType().equals("p")) {
									enpR = Board.cells[endRank][endFile + 1].getPiece();
									enpassantR = true;
								}
							}
						} else if (endFile == 7) {
							if (Board.cells[endRank][endFile - 1].getPiece() != null
									&& Board.cells[endRank][endFile - 1].getPiece().isWhite()) {
								if (Board.cells[endRank][endFile - 1].getPiece().getType().equals("p")) {
									enpL = Board.cells[endRank][endFile - 1].getPiece();
									enpassantL = true;
								}
							}
						}

						if (enpassantL || enpassantR) {
							tempRank1 = endRank;
							tempFile1 = endFile;
						}

						return true;
					}

				}

				else if (startRank == 1 && (endRank == startRank + 1)) {
					if (isPathClear(startFile, startRank, endFile, endRank)) {
						return true;
					}
				}
				else if ((startRank != 1 && endRank == startRank + 1) && (startFile == endFile)) {
					resetTempRankFile();
					return true;

				}

				else if ((startRank == 4 && endRank == 5) && (endFile == startFile + 1 || endFile == startFile - 1)) {
					if (enpassant(startFile, startRank, endFile, endRank))
						canEnp = true;
					else
						canEnp = false;
					return canEnp;
				}

			}
			return false;
		}

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

		// only need vertical path check for pawn, if not in the same column then
		// returns false
		// which in turn makes isValidMove false
		if (startFile == endFile) {
			while (startRank != endRank) {
				if (startRank > endRank) {
					if (Board.cells[startRank - 1][startFile].getPiece() != null) {
						return false;
					}
					startRank--;
				} else if (startRank < endRank) {
					if (Board.cells[startRank + 1][startFile].getPiece() != null) {
						return false;
					}
					startRank++;
				}
				Pawn.tempRank = startRank;
				Pawn.tempFile = startFile;
				// System.out.println("row col: " + startRank + startFile);
				return true;
			}
		}

		return false;
	}

	/**
	 * playMove(int startFile, int startRank, int endFile, int endRank, char promotion) abstract method moves a piece if isValidMove is true
	 * 
	 * @param startFile - startCell's File
	 * @param startRank - startCell's Rank
	 * @param endFile   - endCell's File
	 * @param endRank   - endCell's Rank
	 * @param promotion - promotion value Q/R/N/B for pawn promotiontion
	 * 
	 * @return boolean
	 */
	@Override
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
			if ((startRank == 1 && endRank == 0) && Board.cells[startRank][startFile].getPiece().isWhite()) {

				Board.cells[startRank][startFile].setPiece(null);
				switch (promotion) {
				case 'Q':
					Board.cells[endRank][endFile].setPiece(new Queen(true));
					Board.cells[endRank][endFile].getPiece().setFirstMove(true);
					break;
				case 'N':
					Board.cells[endRank][endFile].setPiece(new Knight(true));
					Board.cells[endRank][endFile].getPiece().setFirstMove(true);
					break;
				case 'B':
					Board.cells[endRank][endFile].setPiece(new Bishop(true));
					Board.cells[endRank][endFile].getPiece().setFirstMove(true);
					break;
				case 'R':
					Board.cells[endRank][endFile].setPiece(new Rook(true));
					Board.cells[endRank][endFile].getPiece().setFirstMove(true);
					break;
				default:
					return true;

				}
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
				return true;
			}

			else if ((startRank == 6 && endRank == 7) && !Board.cells[startRank][startFile].getPiece().isWhite()) {
				Board.cells[startRank][startFile].setPiece(null);
				switch (promotion) {
				case 'Q':
					Board.cells[endRank][endFile].setPiece(new Queen(false));
					Board.cells[endRank][endFile].getPiece().setFirstMove(true);
					break;
				case 'N':
					Board.cells[endRank][endFile].setPiece(new Knight(false));
					Board.cells[endRank][endFile].getPiece().setFirstMove(true);
					break;
				case 'B':
					Board.cells[endRank][endFile].setPiece(new Bishop(false));
					Board.cells[endRank][endFile].getPiece().setFirstMove(true);
					break;
				case 'R':
					Board.cells[endRank][endFile].setPiece(new Rook(false));
					Board.cells[endRank][endFile].getPiece().setFirstMove(true);
					break;
				default:
					return true;

				}
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
				return true;
			} else {

				if (canEnp) {
					Board.cells[tempRank1][tempFile1].setPiece(null);
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

				return true;
			}

		}
		return false;
	}

	/**
	 * enpassant(int startFile, int startRank, int endFile, int endRank) check for
	 * valid enpassant move.
	 * 
	 * @param startFile - old block's File
	 * @param startRank - old block's Rank
	 * @param endFile   - new block's File
	 * @param endRank   - new blocks Rank
	 * 
	 * @return boolean
	 */
	public boolean enpassant(int startFile, int startRank, int endFile, int endRank) {
		if (Pawn.tempRank == endRank && Pawn.tempFile == endFile) {

			if (enpassantL) {

				if (Board.cells[startRank][startFile].getPiece().equals(enpL)) {
					enpassantL = false;
					enpassantR = false;
					enpL = null;
					enpR = null;
					return true;
				}

			}

			if (enpassantR) {
				if (Board.cells[startRank][startFile].getPiece().equals(enpR)) {
					enpassantL = false;
					enpassantR = false;
					enpL = null;
					enpR = null;
					return true;
				}
			}

		}

		enpassantL = false;
		enpassantR = false;
		enpL = null;
		enpR = null;

		return false;
	}

	/**
	 * reset temporary Pawn rank and file
	 */
	public static void resetTempRankFile() {
		tempRank = -1;
		tempFile = -1;
	}
}
