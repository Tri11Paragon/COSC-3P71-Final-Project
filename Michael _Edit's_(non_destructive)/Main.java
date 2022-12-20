package project;

import project.chess.Board;
import project.ui.Display;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        Board mainBoard = new Board();

        int maxDepth = 5;
        Board[] bestStates = new Board[maxDepth];
        // mainBoard.getMoves();

        // display stuff //
        Display display = new Display(mainBoard);
        display.setTitle("Computing Game...");
        alphaBeta(mainBoard,bestStates,maxDepth,Integer.MIN_VALUE,Integer.MAX_VALUE,true,display);
        System.out.println(">>Ai Done Computing<<");
        display.setTitle("Solution Playback");

        int counter = 0;
        long frames = 0;
        long lastTime = System.currentTimeMillis();
        long frameTime = System.currentTimeMillis();
        while(display.update(bestStates[counter%(maxDepth+1)])){
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

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (Exception ignored) {}

            counter++;

        }


    }

    public static int alphaBeta (Board position, Board[] bestStates, int depth, int alpha, int beta, boolean maximizing,
    Display frame) {
        if (depth == 0 || position.equals("WhiteInCheck") || position.equals("BlackInCheck")) {
            bestStates[depth] = position;
            return position.evaluate();
        }

        System.out.println("Depth: "+depth);
        frame.update(position);
        frame.repaint();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception ignored) {}

        if (maximizing) {
            int maxEval = Integer.MIN_VALUE;
            for (Board state: position.getMoves(maximizing) ) {
                int eval = alphaBeta(state,bestStates,depth-1, alpha,beta,false,frame);
                maxEval = Math.max(maxEval,eval);
                alpha = Math.max(alpha,eval);
                if (beta <= alpha) {
                    break;
                }
            }
            bestStates[depth-1] = position;
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (Board state: position.getMoves(maximizing) ) {
                int eval = alphaBeta(state,bestStates,depth-1,alpha,beta,true,frame);
                minEval = Math.min(minEval,eval);
                beta = Math.min(beta,eval);
                if (beta <= alpha) {
                    break;
                }
            }
            bestStates[depth-1] = position;
            return minEval;
        }
    }

    public static int minMax (Board position, Board[] bestStates, int depth, boolean maximizing) {
        if (depth == 0) {
            bestStates[depth] = position;
            return position.evaluate();
        }

        if (maximizing) {
            int maxEval = Integer.MIN_VALUE;
            for (Board state: position.getMoves(maximizing) ) {
                int eval = minMax(state,bestStates,depth-1, false);
                maxEval = Math.max(maxEval,eval);
            }
            bestStates[depth-1] = position;
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (Board state: position.getMoves(maximizing) ) {
                int eval = minMax(state,bestStates,depth-1, true);
                minEval = Math.min(minEval,eval);
            }
            bestStates[depth-1] = position;
            return minEval;
        }
    }

}