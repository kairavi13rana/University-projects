/**
 * @authors Kairavi and Abhik
 */
package chess.board.utils;
import chess.board.piece.Pieces;

/**
 * Class which defines Cell of chess board.
 */
public class Cell {

	/**
	 * A cell is representing square(Black/White) on chessboard, which can be empty or have a piece. 
	 *
	 */

	public Pieces piece;
	
	/**
	 * color of the cell is given by boolean isBlack
	 */
	private boolean isBlack;
	
	/**
	 * variable to store file and rank of cell.
	 */
	private int file, rank;
	
	/**
	 * Single param constructor for Cell with defining color and null piece.
	 * @param isBlack - defines color of cell
	 */
	
	public Cell(Boolean isBlack) {
		this.piece = null;
		this.isBlack = isBlack;
	}
	
	/**
	 * Two param constructor for cell with piece and isBlack
	 * @param piece - current piece in cell
	 * @param isBlack - true if cell is black and false if cell is white
	 */
	public Cell(Pieces piece, boolean isBlack) {
		this.piece = piece;
		this.isBlack = isBlack;
	}
	
	/**
	 * setPiece()
	 * To set a piece in this cell.
	 * @param piece - piece to be set.
	 * 
	 * 
	 */
	public void setPiece(Pieces piece) {
		this.piece = piece;
	}
	
	/**
	 * getPiece()
	 * Gets the piece that is on the current cell
	 * @return Piece - piece that is on the current cell
	 */
	public Pieces getPiece()
	{
		return this.piece;
	}
	
	/**
	 * isBlack()
	 * Checks if the cell is black or not
	 * @return boolean - Returns true if cell is black
	 */
	public boolean isBlack() {
		return isBlack;
	}

	/**
	 * getFile()
	 * return file of cell.
	 * @return int - Returns file of cell.
	 */
	public int getFile() {
		return file;
	}

	/**
	 * setFile()
	 * set file of cell.
	 * @param file - file of cell.
	 */
	public void setFile(int file) {
		this.file = file;
	}

	/**
	 * getRank()
	 * return rank of cell.
	 * @return int - rank of cell.
	 */
	public int getRank() {
		return rank;
	}

	/**
	 * setRank()
	 * set rank of cell.
	 * @param rank - rank of cell.
	 */
	public void setRank(int rank) {
		this.rank = rank;
	}
	
	
}
