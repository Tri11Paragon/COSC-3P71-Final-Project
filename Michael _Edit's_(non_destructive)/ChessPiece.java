package project.chess;

import java.awt.*;
import java.util.ArrayList;

public abstract class ChessPiece {

    protected Board b;
    protected int x, y;
    protected boolean isInDanger, isWhite;
    protected boolean isFirstMove = true;

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
        return isFirstMove;
    }

    public void setMoved(){
        isFirstMove = false;
    }

    public abstract ArrayList<Move> getMoves();
    public abstract Image getImage();
    public void applySpecialMove(Move moveWithSpecial){}

    protected ArrayList<Move> getCardinalMoves(int length){
        ArrayList<Move> moves = new ArrayList<Move>();
        for (int i = 1; i <= length; i++){
            // cardinals
            moves.add(new Move(x - i, y));
            moves.add(new Move(x + i, y));
            moves.add(new Move(x, y - i));
            moves.add(new Move(x, y + i));
        }
        return moves;
    }

    protected ArrayList<Move> getDiagonalMoves(int length){
        ArrayList<Move> moves = new ArrayList<Move>();
        for (int i = 1; i <= length; i++){
            // cardinals
            moves.add(new Move(x - i, y - i));
            moves.add(new Move(x + i, y + i));
            moves.add(new Move(x + i, y - i));
            moves.add(new Move(x - i, y + i));
        }
        return moves;
    }

    public abstract ChessPiece clone();
}