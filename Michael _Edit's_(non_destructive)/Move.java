package project.chess;

public class Move {

    enum SpecialConditions {
        none, leftEnPassant, rightEnPassant, leftCastle, rightCastle
    }

    private final int x,y;
    private final SpecialConditions specialCondition;

    public Move(int x, int y){
        this.x = x;
        this.y = y;
        specialCondition = SpecialConditions.none;
    }
    public Move(int x, int y, SpecialConditions specialCondition){
        this.x = x;
        this.y = y;
        this.specialCondition = specialCondition;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public SpecialConditions getSpecialCondition(){
        return specialCondition;
    }
}
