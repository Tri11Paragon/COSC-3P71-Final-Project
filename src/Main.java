import chess.Board;
import ui.Display;

public class Main {
    public static void main(String[] args) {
        Board mainBoard = new Board();
        Display display = new Display(mainBoard);
        long frames = 0;
        long lastTime = System.currentTimeMillis();
        long frameTime = System.currentTimeMillis();
        while(display.update()){
            display.repaint();
            // limit usage of system resources by slowing the speed down to 60 fps.
            while ((System.currentTimeMillis() - frameTime) < 64f){
                Thread.yield();
            }
            frameTime = System.currentTimeMillis();

            // print out the FPS of the display.
            frames++;
            if (System.currentTimeMillis() - lastTime > 1000){
                System.out.println("FPS: " + frames);
                frames = 0;
                lastTime = System.currentTimeMillis();
            }
        }
        System.out.println("Hello world!");
    }
}