package project.chess;

import java.util.ArrayList;

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
        ArrayList<Move> moves;
        moves = selectedPiece.getMoves();
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
            // run special conditions. Only matters for pieces which have special conditions, since is defaulted to empty body.
            if (movedPiece != null)
                selectedPiece.applySpecialMove(m);
            set(x, y, null);
            return true;
        }
        return false;
    }

    public ArrayList<Board> getMoves(boolean isWhitesTurn) {
        ArrayList<Board> moveStates = new ArrayList<>();
        Board curr;

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if ((board[i][j] = get(i, j)) == null || (isWhitesTurn && !board[i][j].isWhite()) ||
                        (!isWhitesTurn && board[i][j].isWhite()) ) continue;
                for (Move pieceMove : board[i][j].getMoves()) {
                    curr = deepCopy();
                    curr.movePiece(board[i][j].x,board[i][j].y,pieceMove.getX(),pieceMove.getY());
                    moveStates.add(deepCopy());
                }
            }
        }

        return moveStates;
    }

    public int evaluate () {
        int materialTotal = 0;

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == null) {continue;}
                if (King.class.equals(board[i][j].getClass())) {
                    if (board[i][j].isWhite) {
                        materialTotal += 900;
                    } else {
                        materialTotal -= 900;
                    }
                } else if (Queen.class.equals(board[i][j].getClass())) {
                    if (board[i][j].isWhite) {
                        materialTotal += 9;
                    } else {
                        materialTotal -= 9;
                    }
                } else if (Rook.class.equals(board[i][j].getClass())) {
                    if (board[i][j].isWhite) {
                        materialTotal += 5;
                    } else {
                        materialTotal -= 5;
                    }
                } else if (Knight.class.equals(board[i][j].getClass()) || Bishop.class.equals(board[i][j].getClass())) {
                    if (board[i][j].isWhite) {
                        materialTotal += 3;
                    } else {
                        materialTotal -= 3;
                    }
                } else if (Pawn.class.equals(board[i][j].getClass())) {
                    if (board[i][j].isWhite) {
                        materialTotal += 1;
                    } else {
                        materialTotal -= 1;
                    }
                }
            }
        }

        return materialTotal;
    }

    public Board deepCopy () {
        Board temp = new Board();

        for (int i = 0; i < temp.board.length; i++) {
            for (int j = 0; j < temp.board[0].length; j++) {
                try {
                    if (this.board[i][j] == null) {
                        temp.board[i][j] = null;
                        continue;
                    }

                    temp.board[i][j] = this.board[i][j].clone();
                } catch (Exception e) { e.printStackTrace();}
            }
        }

        return temp;
    }

    public ChessPiece get(Move m){
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
        return set(m.getX(), m.getY(), piece);
    }

    protected boolean set(int x, int y, ChessPiece piece){
        if (x < 0 || x >= board.length)
            return false;
        if (y < 0 || y >= board.length)
            return false;
        board[x][y] = piece;
        return true;
    }

    public int size(){
        return board.length;
    }

}