/**
 * @authors Kairavi and Abhik
 */

package chess.board.piece;

/**
 * Abstract class which defines Pieces methods and variables
 */
public abstract class Pieces {

	/** boolean to check the color of piece white/black */
	private boolean isWhite;

	/** Type of the piece pawn(p)/king(K)/queen(Q)/rook(R)/knight(N)/bishop(B) */
	private String type;

	/** boolean value firstMove for a piece when the piece has moved */
	boolean firstMove;

	/**
	 * One argument constructor that creates a new instance of Piece with defining isWhite(true/false)
	 * 
	 * @param isWhite - color of the piece white(true) or black(false) 
	 */

	public Pieces(boolean isWhite) {
		super();
		this.isWhite = isWhite;
		this.type = null;
		this.firstMove = false;
	}

	/**
	 * isWhite() Checks to see whether a piece is white, returns true if white,
	 * false if black
	 * 
	 * @return boolean
	 * 
	 * 
	 */
	public boolean isWhite() {
		return isWhite;
	}

	/**
	 * isFirstMove() Checks if piece has moved for first time, returns true if yes
	 * 
	 * @return boolean
	 */
	public boolean isFirstMove() {
		return firstMove;
	}
	
	/**
	 * setFirstMove(boolean firstMove) will set the firstMove variable 
	 * @param firstMove - define the first move of piece is done or not.
	 */
	public void setFirstMove(boolean firstMove) {
		this.firstMove = firstMove;
	}

	/**
	 * getType() returns a String as the type of the piece
	 * @return String 
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * setType(String type) returns a String as the type of the piece
	 * 
	 * @param type - define the type of the piece pawn(p)/king(K)/queen(Q)/rook(R)/knight(N)/bishop(B)
	 *  
	 */

	public void setType(String type) {
		this.type = type;
	}

	/**
	 * isValidMove(int startFile, int startRank, int endFile, int endRank) abstract method checks to see if move is valid from start cell to end cell
	 * 
	 * @param startFile - startCell's File
	 * @param startRank - startCell's Rank
	 * @param endFile - endCell's File
	 * @param endRank - endCell's Rank
	 * @return boolean
	 */
	public abstract boolean isValidMove(int startFile, int startRank, int endFile, int endRank);

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
	public abstract boolean playMove(int startFile, int startRank, int endFile, int endRank, char promotion);

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
	public abstract boolean isPathClear(int startFile, int startRank, int endFile, int endRank);
}