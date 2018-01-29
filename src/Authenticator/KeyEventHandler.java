/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Authenticator;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author ASUS
 */
public class KeyEventHandler {
    private int prevKey;
    private long prevKeyTimeOfRelease;    
    private final ConcurrentHashMap<Integer, Long> pressedKeys;
    private final ConcurrentHashMap<Integer, Long> keyDurations;
    private final ConcurrentHashMap<Integer, Integer> keyCounts;    
    private final ArrayList<Long> digraphDelays;
  
     public KeyEventHandler() {
        prevKey = -1;
        prevKeyTimeOfRelease = 0;
        pressedKeys = new ConcurrentHashMap<>();
        keyDurations = new ConcurrentHashMap<>();
        keyCounts = new ConcurrentHashMap<>();
        digraphDelays = new ArrayList<>();
    }

    public ArrayList<Long> getDigraphDelays() {
        return digraphDelays;
    }

    public ConcurrentHashMap<Integer, Long> getPressedKeys() {
        return pressedKeys;
    }
    public ConcurrentHashMap<Integer, Long> getKeyDurations() {
        return keyDurations;
    }
    public ConcurrentHashMap<Integer, Integer> getKeyCounts() {        return keyCounts;
    }
     
     public void pressKey(KeyEvent keyEvent) {  
        Long time = System.currentTimeMillis();
        int currentKey = keyEvent.getKeyCode();
        pressedKeys.put(currentKey,time);
        if(prevKey != -1){
            Long duration = time - prevKeyTimeOfRelease;    
            digraphDelays.add(duration);
        }
        
        prevKey = currentKey;
    }
        
    public void releaseKey(KeyEvent keyEvent) {
        Long time = System.currentTimeMillis();
        int currentKey = keyEvent.getKeyCode(); 
        long duration = time - pressedKeys.get(currentKey);
        if (keyCounts.containsKey(currentKey)) {
            keyCounts.put(currentKey, keyCounts.get(currentKey) + 1);
            keyDurations.put(currentKey, duration + keyDurations.get(currentKey));
        } else {
            keyCounts.put(currentKey, 1);
            keyDurations.put(currentKey, duration);
        }           
        prevKeyTimeOfRelease = time;
        pressedKeys.remove(currentKey);        
    }
    

    
     
}
