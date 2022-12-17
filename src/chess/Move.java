package chess;

import java.util.ArrayList;

public class Move {

    private final int x,y;
    private final boolean enPassant;

    public Move(int x, int y){
        this.x = x;
        this.y = y;
        enPassant = false;
    }
    public Move(int x, int y, boolean enPassant){
        this.x = x;
        this.y = y;
        this.enPassant = enPassant;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isEnPassant(){
        return enPassant;
    }
}
