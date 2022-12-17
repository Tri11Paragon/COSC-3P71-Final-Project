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

    public ChessPiece get(int x, int y){
        if (x < 0 || x > board.length)
            return null;
        if (y < 0 || y > board.length)
            return null;
        return board[x][y];
    }

    public int size(){
        return board.length;
    }

}
