package chess;

import java.util.ArrayList;

public class Pawn extends ChessPiece {

    private final boolean isFirstMove = true;

    public Pawn(Board b, boolean isWhite, int x, int y) {
        super(b,isWhite,x,y);
    }

    public boolean isFirstMove(){
        return isFirstMove;
    }

    @Override
    public ArrayList<Move> getMoves() {
        ArrayList<Move> moves = new ArrayList<Move>();
        moves.add(new Move(x, y + 1));
        if (isFirstMove)
            moves.add(new Move(x, y + 2));
        ChessPiece neighbour = null;
        // En passant
        if ((neighbour = b.get(x-1, y)) != null && neighbour instanceof Pawn && ((Pawn) neighbour).isFirstMove())
            moves.add(new Move(x-1, 1, true));
        // En passant
        if ((neighbour = b.get(x+1, y)) != null && neighbour instanceof Pawn && ((Pawn) neighbour).isFirstMove())
            moves.add(new Move(x+1, 1, true));
        return moves;
    }
}
