package chess;

public class Board {

    private final ChessPiece[][] board = new ChessPiece[8][8];

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
        board[0][0] = new Rook(this, false,0, 0);
        board[size()-1][0] = new Rook(this, false, size() - 1, 0);
        board[1][0] = new Knight(this, false, 1, 0);
        board[size()-2][0] = new Knight(this, false, size() - 2, 0);
        board[2][0] = new Bishop(this, false, 2, 0);
        board[size()-3][0] = new Bishop(this, false, size() - 3, 0);
        board[3][0] = new Queen(this, false, 3, 0);
        board[size()-4][0] = new King(this, false, size() - 4, 0);
    }

    public boolean movePiece(int x, int y, int newX, int newY){
        ChessPiece selectedPiece;
        // make sure the place we are moving from has a piece
        if ((selectedPiece = get(x, y)) == null)
            return false;
        var moves = selectedPiece.getMoves();
        for (Move m : moves){
            // reject the moves that don't correspond to where we want to move to.
            if (m.getX() != newX || m.getY() != newY)
                continue;
            ChessPiece movedPiece = get(m);
            // make sure they are of the same color. Since we know this move is the position we want to move to
            // we can early exit because we are not allowed to move on top of our own pieces
            if (selectedPiece.isWhite == movedPiece.isWhite)
                return false;
            // if we were unable to set the piece down we failed to move the piece
            if (!set(m, selectedPiece))
                return false;
            // run special conditions. Only matters for pieces which have special conditions, since is defaulted to empty body.
            selectedPiece.applySpecialMove(m);
            set(x, y, null);
            return true;
        }
        return false;
    }

    public ChessPiece get(Move m){
        return get(m.getX(), m.getY());
    }

    public ChessPiece get(int x, int y){
        if (x < 0 || x > board.length)
            return null;
        if (y < 0 || y > board.length)
            return null;
        return board[x][y];
    }

    public boolean set(Move m, ChessPiece piece) {
        return set(m.getX(), m.getY(), piece);
    }

    protected boolean set(int x, int y, ChessPiece piece){
        if (x < 0 || x > board.length)
            return false;
        if (y < 0 || y > board.length)
            return false;
        board[x][y] = piece;
        return true;
    }

    public int size(){
        return board.length;
    }

}
