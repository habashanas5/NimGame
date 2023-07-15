
package nimgame;

import java.util.ArrayList;
import java.util.List;
import static java.lang.Math.min;
import static java.lang.Math.max;


public class Game {
    private State init;
    private List <State> closedList;
    private List <State> childrenOfRoot;
    private boolean minMax;   // true = max, false = min
    
    private final int MINUS_INFINITY = -1000;
    private final int PLUS_INFINITY = 1000;
    
    public Game(List <Integer> packs) {
        init = new State(packs);
        closedList = new ArrayList <> ();
        childrenOfRoot = new ArrayList <> ();
        minMax = true;    // max turn
    }
    
    public boolean isTerminalState(State s) {
        boolean terminal = true;
        for(int i : s.packs) {
            if(i > 2)
                terminal = false;
        }
        return terminal;
    }
    
    public int alphaBeta(State node, int alpha, int beta, boolean minMax, int count) {
        if(isTerminalState(node)) {
            return minMax ? 1 : -1;
        }
        int v;
        if(minMax) {
            v = MINUS_INFINITY;
            List <State> childrenStates = node.expand();
            for(State child : childrenStates) {
                v = max(v, alphaBeta(child, alpha, beta, false, count+1));
                child.setHeuristic(v);
                if(count == 0) {
                    childrenOfRoot.add(child);
                }
                alpha = max(v, alpha);
                if(beta <= alpha)
                    break;
            }
            node.setHeuristic(v);
            return v;
        } else {
            v = PLUS_INFINITY;
            List <State> childrenStates = node.expand();
            for(State child : childrenStates) {
                v = min(v, alphaBeta(child, alpha, beta, true, count+1));
                child.setHeuristic(v);
                if(count == 0) {
                    childrenOfRoot.add(child);
                }
                beta = min(beta, v);
                if(beta <= alpha)
                    break;
            }
            node.setHeuristic(v);
            return v;
        }
    }
    
    public void launch() {
        // Choose: <2, 4, 1, 3, 5>
        List <State> children = init.expand();
        for(State s : children)
            System.out.println(s.toString());
        long start = System.nanoTime();
        int returnedValue = alphaBeta(init, MINUS_INFINITY, PLUS_INFINITY, true, 0);
        long end = System.nanoTime();
//        System.out.println("Returned value = " + returnedValue);
//        System.out.println("Size of children = " + childrenOfRoot.size());
        System.out.println("Execution time = " + (end - start) + " nanoseconds");
        System.out.println("Best moves:");
        for(State s : childrenOfRoot)
            if(s.getHeuristic() == -1)
                System.out.print(s.toString() + ",");
//            System.out.print(s.getHeuristic() + ",");
        System.out.println();
    }
}
