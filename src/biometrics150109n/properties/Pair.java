/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biometrics150109n.properties;

import java.io.Serializable;

/**
 *
 * @author ASUS
 */
public class Pair implements Serializable{
    
    private final int predecessor;
    private final int current;
    
    public Pair(int predecessor, int current){
        this.predecessor = predecessor;
        this.current = current;
    }

    public int getPredecessor() {
        return predecessor;
    }

    public int getCurrent() {
        return current;
    }
    
    public boolean crossCheck(Object obj){
        if(!(obj instanceof Pair)){
            return false;
        }
        Pair checkPair = (Pair) obj;
        return this.predecessor == checkPair.getPredecessor() && this.current == checkPair.getCurrent();
    }
    
}
