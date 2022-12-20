package chess;

import java.awt.*;
import java.util.ArrayList;

public abstract class ChessPiece {

    protected Board b;
    protected int x, y;
    protected boolean isInDanger, isWhite;
    protected int moveCount = 0;

    public ChessPiece(Board b, boolean isWhite, int x, int y) {
        this.b = b;
        this.x = x;
        this.y = y;
        this.isWhite = isWhite;
    }

    public void setInDanger(boolean isInDanger){
        this.isInDanger = isInDanger;
    }

    public boolean isWhite(){
        return isWhite;
    }
    public Move getPosition(){
        return new Move(x, y);
    }

    public boolean isFirstMove(){
        return moveCount < 1;
    }
    public boolean isSecondMove(){
        return moveCount <= 1;
    }

    public void move(Move m){
        this.x = m.getX();
        this.y = m.getY();
        this.moveCount++;
    }

    public abstract ArrayList<Move> getMoves();
    public abstract Image getImage();
    public void applySpecialMove(Move moveWithSpecial){}

    protected ArrayList<Move> getCardinalMoves(int length){
        ArrayList<Move> moves = new ArrayList<Move>();
        // cardinals
        for (int i = 1; i <= length; i++){
            if (add(moves, new Move(x - i, y)))
                break;
        }
        for (int i = 1; i <= length; i++){
            if (add(moves, new Move(x + i, y)))
                break;
        }
        for (int i = 1; i <= length; i++){
            if (add(moves, new Move(x, y - i)))
                break;
        }
        for (int i = 1; i <= length; i++){
            if (add(moves, new Move(x, y + i)))
                break;
        }
        return moves;
    }

    protected ArrayList<Move> getDiagonalMoves(int length){
        ArrayList<Move> moves = new ArrayList<Move>();
        // diagonals
        for (int i = 1; i <= length; i++){
            if (add(moves, new Move(x - i, y - i)))
                break;
        }
        for (int i = 1; i <= length; i++){
            if (add(moves, new Move(x - i, y + i)))
                break;
        }
        for (int i = 1; i <= length; i++){
            if (add(moves, new Move(x + i, y - i)))
                break;
        }
        for (int i = 1; i <= length; i++){
            if (add(moves, new Move(x + i, y + i)))
                break;
        }
        return moves;
    }

    protected ArrayList<Move> prune(ArrayList<Move> moves){
        var prunedMoves = new ArrayList<Move>();
        // get rid of impossible moves
        for (Move m : moves){
            if (m == null)
                continue;
            if (m.getX() < 0 || m.getX() >= b.size())
                continue;
            if (m.getY() < 0 || m.getY() >= b.size())
                continue;
            prunedMoves.add(m);
        }

        return prunedMoves;
    }

    /**
     * Checks to make sure that the proposed move is valid.
     * @param moves array to add to
     * @param move move to add to the array
     * @return true if we need to stop here. (Break the loop)
     */
    private boolean add(ArrayList<Move> moves, Move move){
        var m = b.get(move);
        if (m != null && m.isWhite != isWhite) {
            moves.add(b.checkIfMoveValid(move, isWhite));
            return true;
        } else {
            if (m != null)
                return true;
            moves.add(b.checkIfMoveValid(move, isWhite));
        }
        return false;
    }

}
