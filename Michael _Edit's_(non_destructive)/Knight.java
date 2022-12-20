package project.chess;

import java.awt.*;
import java.util.ArrayList;

import static project.ui.Display.loadImage;

public class Knight extends ChessPiece {

    private Image whiteKnight = loadImage("./resources/chess_piece_2_black_knight.png");
    private Image blackKnight = loadImage("./resources/chess_piece_2_white_knight.png");

    public Knight(Board b, boolean isWhite, int x, int y) {
        super(b,isWhite,x,y);
    }

    public Image getImage(){
        if (isWhite)
            return whiteKnight;
        return blackKnight;
    }

    @Override
    public ArrayList<Move> getMoves() {
        ArrayList<Move> moves = new ArrayList<Move>();
        moves.add(b.checkIfMoveValid(new Move(x + 2, y + 1), isWhite));
        moves.add(b.checkIfMoveValid(new Move(x + 2, y - 1), isWhite));
        moves.add(b.checkIfMoveValid(new Move(x - 2, y + 1), isWhite));
        moves.add(b.checkIfMoveValid(new Move(x - 2, y - 1), isWhite));
        moves.add(b.checkIfMoveValid(new Move(x - 1, y - 2), isWhite));
        moves.add(b.checkIfMoveValid(new Move(x + 1, y - 2), isWhite));
        moves.add(b.checkIfMoveValid(new Move(x - 1, y + 2), isWhite));
        moves.add(b.checkIfMoveValid(new Move(x + 1, y + 2), isWhite));
        return prune(moves);
    }

    public Knight clone () {
        return new Knight(this.b,this.isWhite,this.x,this.y);
    }
}
