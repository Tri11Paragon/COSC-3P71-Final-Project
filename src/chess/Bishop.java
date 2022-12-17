package chess;

import java.awt.*;
import java.util.ArrayList;

import static ui.Display.loadImage;

public class Bishop extends ChessPiece {

    private Image whiteBishop = loadImage("./resources/chess_piece_2_black_bishop.png");
    private Image blackBishop = loadImage("./resources/chess_piece_2_white_bishop.png");

    public Bishop(Board b, boolean isWhite, int x, int y) {
        super(b,isWhite,x,y);
    }

    public Image getImage(){
        if (isWhite)
            return whiteBishop;
        return blackBishop;
    }

    @Override
    public ArrayList<Move> getMoves() {
        return new ArrayList<Move>(super.getDiagonalMoves(b.size()));
    }
}
