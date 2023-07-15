
package nimgame;

import java.util.ArrayList;
import java.util.List;


public class State {
    List <Integer> packs;
    List <State> children;
    private int heuristic;
    
    public State(List <Integer> packs) {
        this.packs = packs;
        children = new ArrayList <> ();
        heuristic = 0;
    }
    
    public void setHeuristic(int h) {
        this.heuristic = h;
    }
    
    public int getHeuristic() {
        return this.heuristic;
    }
    
    public boolean isEqual(State s1, State s2) {
        boolean equal = true;
        for(int i=0; i<s1.packs.size(); ++i) {
            if(s1.packs.get(i) != s2.packs.get(i)) {
                equal = false;
                break;
            }
        }
        return equal;
    }
    
    public boolean isRepeated(State s) {
        boolean repeated = false;
        for(State child : children) {
            if(isEqual(child, s))
                repeated = true;
        }
        return repeated;
    }
    
    public List <State> expand() {
        boolean canCut = true;
        boolean canAdd = true;
        int cutIndex = 0;
        int iteration = 0;
        int k = 1;
        for(;iteration < packs.size();) {
            if(packs.get(iteration) <= 2) {
                iteration++;
                continue;
            }
            List <Integer> childPacks = new ArrayList <> ();
            int indexCopy = cutIndex;
            for(int j=0; j<packs.size(); ++j) {
                int temp = packs.get(j);
                if(temp > 2 && canCut && j == indexCopy) {
                    if(temp - k != 0) {
                        childPacks.add(k);
                        childPacks.add(temp-k);
                        canCut = false;
                        indexCopy = j;
                    } else {
                        k = 1;
                        cutIndex++;
                        canAdd = false;
                        iteration++;
                    }
                } else {
                    childPacks.add(temp);
                    if(j == indexCopy)
                        indexCopy++;
                }
            }
            canCut = true;
            k++;
            State child = new State(childPacks);
            if(canAdd && !isRepeated(child))
                children.add(child);
            canAdd = true;
        }
        return children;
    }
    
    @Override
    public String toString() {
        String s = "";
        for(int pack : packs)
            s += pack + " ";
        return s;
    }
}
