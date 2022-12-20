package chess;

public class Board {

    public enum BoardStatus {
        None,
        WhiteInCheck,
        BlackInCheck
    };

    private final ChessPiece[][] board = new ChessPiece[8][8];
    private BoardStatus status = BoardStatus.None;

    /**
     * create a basic chess board in default configuration
     */
    public Board(){
        for (int i = 0; i < size(); i++) {
            board[i][1] = new Pawn(this, true, i, 1);
            board[i][size() - 2] = new Pawn(this, false, i, size() - 2);
        }
        // white
        board[0][0] = new Rook(this, true,0, 0);
        board[size()-1][0] = new Rook(this, true, size() - 1, 0);
        board[1][0] = new Knight(this, true, 1, 0);
        board[size()-2][0] = new Knight(this, true, size() - 2, 0);
        board[2][0] = new Bishop(this, true, 2, 0);
        board[size()-3][0] = new Bishop(this, true, size() - 3, 0);
        board[3][0] = new Queen(this, true, 3, 0);
        board[size()-4][0] = new King(this, true, size() - 4, 0);

        // black
        board[0][size()-1] = new Rook(this, false,0, size()-1);
        board[size()-1][size()-1] = new Rook(this, false, size() - 1, size()-1);
        board[1][size()-1] = new Knight(this, false, 1, size()-1);
        board[size()-2][size()-1] = new Knight(this, false, size() - 2, size()-1);
        board[2][size()-1] = new Bishop(this, false, 2, size()-1);
        board[size()-3][size()-1] = new Bishop(this, false, size() - 3, size()-1);
        board[3][size()-1] = new Queen(this, false, 3, size()-1);
        board[size()-4][size()-1] = new King(this, false, size() - 4, size()-1);
    }

    public boolean movePiece(Move movingPiece, Move newPos){
        return movePiece(movingPiece.getX(), movingPiece.getY(), newPos.getX(), newPos.getY());
    }

    public boolean movePiece(int x, int y, int newX, int newY){
        ChessPiece selectedPiece;
        // make sure the place we are moving from has a piece
        if ((selectedPiece = get(x, y)) == null)
            return false;

        // check for check
        if (status != BoardStatus.None){
            // when the king is in check no piece can move.
            if (!(selectedPiece instanceof King))
                return false;
            if (status == BoardStatus.WhiteInCheck){
                // don't allow the wrong king to be moved
                if (!selectedPiece.isWhite)
                    return false;
            } else {
                // don't allow the wrong king to be moved
                if (selectedPiece.isWhite)
                    return false;
            }
            // prevent moves which are invalid.
            for (int i = 0; i < board.length; i++){
                for (int j = 0; j < board.length; j++){
                    var p = get(i, j);
                    // make sure that we only check moves on pieces of the opposite color
                    if (p != null && p.isWhite != selectedPiece.isWhite){
                        var moves = p.getMoves();
                        for (Move m : moves){
                            // don't allow for king to move to pieces which would still put him in danger.
                            if (m.getX() == newX && m.getY() == newY)
                                return false;
                        }
                    }
                }
            }
        }

        // apply move like normal.
        var moves = selectedPiece.getMoves();
        for (Move m : moves){
            // reject the moves that don't correspond to where we want to move to.
            if (m.getX() != newX || m.getY() != newY)
                continue;
            ChessPiece movedPiece = get(m);
            // make sure they are of the same color. Since we know this move is the position we want to move to
            // we can early exit because we are not allowed to move on top of our own pieces
            if (movedPiece != null && selectedPiece.isWhite == movedPiece.isWhite)
                return false;
            // if we were unable to set the piece down we failed to move the piece
            if (!set(m, selectedPiece))
                return false;
            selectedPiece.move(m);
            // run special conditions. Only matters for pieces which have special conditions, since is defaulted to empty body.
            selectedPiece.applySpecialMove(m);
            set(x, y, null);
            updateDangerStatus();
            return true;
        }
        return false;
    }

    /**
     * Updates the danger status of all the pieces. Pieces which are threatened are marked as in danger
     */
    public void updateDangerStatus(){
        status = BoardStatus.None;
        for (int i = 0; i < board.length; i++){
            for (int j = 0; j < board.length; j++){
                var p = get(i, j);
                if (p != null){
                    var moves = p.getMoves();
                    for (Move m : moves){
                        var pieceInDanger = get(m);
                        // make sure that the piece isn't on the same team. White cannot endanger white.
                        if (pieceInDanger != null && pieceInDanger.isWhite != p.isWhite) {
                            // we only care if the king is in danger.
                            if (pieceInDanger instanceof King) {
                                status = pieceInDanger.isWhite() ? BoardStatus.WhiteInCheck : BoardStatus.BlackInCheck;
                                return;
                            }
                        }
                    }
                }
            }
        }
    }

    public BoardStatus getStatus(){
        return status;
    }

    public ChessPiece get(Move m){
        if (m == null)
            return null;
        return get(m.getX(), m.getY());
    }

    public ChessPiece get(int x, int y){
        if (x < 0 || x >= board.length)
            return null;
        if (y < 0 || y >= board.length)
            return null;
        return board[x][y];
    }

    public boolean set(Move m, ChessPiece piece) {
        if (m == null)
            return false;
        return set(m.getX(), m.getY(), piece);
    }

    public boolean set(int x, int y, ChessPiece piece){
        if (x < 0 || x >= board.length)
            return false;
        if (y < 0 || y >= board.length)
            return false;
        board[x][y] = piece;
        return true;
    }

    protected Move checkIfMoveValid(Move m, boolean isWhite){
        if (m == null)
            return null;
        if (get(m) != null && get(m).isWhite == isWhite)
            return null;
        return m;
    }

    public int size(){
        return board.length;
    }

}
