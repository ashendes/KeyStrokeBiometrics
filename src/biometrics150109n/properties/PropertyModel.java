/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biometrics150109n.properties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author ASUS
 */
public class PropertyModel implements Serializable{
    private String username;   
    private ConcurrentHashMap<Integer, Long> pressDuration;
    private ArrayList<Long> digraphDelay;
    
    public PropertyModel(){
        pressDuration = new ConcurrentHashMap<>();
        digraphDelay = new ArrayList<>();
    }

    public ConcurrentHashMap<Integer, Long> getPressDuration() {
        return pressDuration;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getUsername() {
        return username;
    } 
    
    public void setPressDuration(ConcurrentHashMap<Integer, Long> pressDuration) {
        this.pressDuration = pressDuration;
    }

    /*public ConcurrentHashMap<Pair, Long> getDigraphDelay() {
        return digraphDelay;
    }

    public void setDigraphDelay(ConcurrentHashMap<Pair, Long> digraphDelay) {
        this.digraphDelay = digraphDelay;
    }*/

    public ArrayList<Long> getDigraphDelay() {
        return digraphDelay;
    }

    public void setDigraphDelay(ArrayList<Long> digraphDelay) {
        this.digraphDelay = digraphDelay;
    }

 
    
    
    
}
