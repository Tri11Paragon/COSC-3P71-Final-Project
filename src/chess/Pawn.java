package chess;

import java.util.ArrayList;

public class Pawn extends ChessPiece {

    public Pawn(Board b, boolean isWhite, int x, int y) {
        super(b,isWhite,x,y);
    }

    @Override
    public ArrayList<Move> getMoves() {
        ArrayList<Move> moves = new ArrayList<Move>();
        moves.add(new Move(x, y + 1));
        if (isFirstMove)
            moves.add(new Move(x, y + 2));
        ChessPiece neighbour = null;

        if (isWhite){
            // En passant
            if ((neighbour = b.get(x-1, y)) != null && neighbour instanceof Pawn && ((Pawn) neighbour).isFirstMove())
                moves.add(new Move(x-1, y + 1, Move.SpecialConditions.leftEnPassant));
            // En passant
            if ((neighbour = b.get(x+1, y)) != null && neighbour instanceof Pawn && ((Pawn) neighbour).isFirstMove())
                moves.add(new Move(x+1, + 1, Move.SpecialConditions.rightEnPassant));
        } else {
            // unfortunately have to flip the direction depending on player type
            // En passant
            if ((neighbour = b.get(x-1, y)) != null && neighbour instanceof Pawn && ((Pawn) neighbour).isFirstMove())
                moves.add(new Move(x-1, y - 1, Move.SpecialConditions.leftEnPassant));
            // En passant
            if ((neighbour = b.get(x+1, y)) != null && neighbour instanceof Pawn && ((Pawn) neighbour).isFirstMove())
                moves.add(new Move(x+1, - 1, Move.SpecialConditions.rightEnPassant));
        }

        return moves;
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
