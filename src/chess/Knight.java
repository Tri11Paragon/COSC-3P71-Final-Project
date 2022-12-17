package chess;

import java.util.ArrayList;

public class Knight extends ChessPiece {

    public Knight(Board b, boolean isWhite, int x, int y) {
        super(b,isWhite,x,y);
    }

    @Override
    public ArrayList<Move> getMoves() {
        ArrayList<Move> moves = new ArrayList<Move>();
        moves.add(new Move(x + 2, y + 1));
        moves.add(new Move(x + 2, y - 1));
        moves.add(new Move(x - 2, y + 1));
        moves.add(new Move(x - 2, y - 1));
        moves.add(new Move(x - 1, y - 2));
        moves.add(new Move(x + 1, y - 2));
        moves.add(new Move(x - 1, y + 2));
        moves.add(new Move(x + 1, y + 2));
        return moves;
    }
}
