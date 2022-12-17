package chess;

import ui.Display;

import java.awt.*;
import java.util.ArrayList;

public class Pawn extends ChessPiece {

    private Image white = Display.loadImage("./resources/chess_piece_2_black_pawn.png");
    private Image black = Display.loadImage("./resources/chess_piece_2_white_pawn.png");

    public Pawn(Board b, boolean isWhite, int x, int y) {
        super(b,isWhite,x,y);
    }

    public Image getImage(){
        if (isWhite)
            return white;
        return black;
    }

    @Override
    public ArrayList<Move> getMoves() {
        ArrayList<Move> moves = new ArrayList<Move>();
        if (isWhite) {
            moves.add(new Move(x, y + 1));
            if (isFirstMove)
                moves.add(new Move(x, y + 2));
        } else {
            moves.add(new Move(x, y - 1));
            if (isFirstMove)
                moves.add(new Move(x, y - 2));
        }
        ChessPiece neighbour = null;

        if (isWhite){
            // En passant
            if ((neighbour = b.get(x-1, y)) != null && checkNeighbourEnPassant(neighbour))
                moves.add(new Move(x-1, y + 1, Move.SpecialConditions.leftEnPassant));
            // En passant
            if ((neighbour = b.get(x+1, y)) != null && checkNeighbourEnPassant(neighbour))
                moves.add(new Move(x+1, + 1, Move.SpecialConditions.rightEnPassant));
        } else {
            // unfortunately have to flip the direction depending on player type
            // En passant
            if ((neighbour = b.get(x-1, y)) != null && checkNeighbourEnPassant(neighbour))
                moves.add(new Move(x-1, y - 1, Move.SpecialConditions.leftEnPassant));
            // En passant
            if ((neighbour = b.get(x+1, y)) != null && checkNeighbourEnPassant(neighbour))
                moves.add(new Move(x+1, - 1, Move.SpecialConditions.rightEnPassant));
        }

        return moves;
    }

    private boolean checkNeighbourEnPassant(ChessPiece neighbour){
        return neighbour instanceof Pawn && ((Pawn) neighbour).isFirstMove() && neighbour.isWhite != this.isWhite;
    }

    @Override
    public void applySpecialMove(Move moveWithSpecial){
        var specialCondition = moveWithSpecial.getSpecialCondition();
        if(specialCondition == Move.SpecialConditions.leftEnPassant)
            enPassantLeft();
        else if (specialCondition == Move.SpecialConditions.rightEnPassant)
            enPassantRight();
    }

    private void enPassantLeft(){
        if (isWhite)
            b.set(x-1, y, null);
        else
            b.set(x+1, y, null);
    }

    private void enPassantRight(){
        if (isWhite)
            b.set(x+1, y, null);
        else
            b.set(x-1, y, null);
    }
}
