/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Authenticator;

import java.awt.event.KeyEvent;
import java.util.HashMap;
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
    private final ConcurrentHashMap<Integer, HashMap<Integer, Long>> digraphDelays;
    private final ConcurrentHashMap<Integer, HashMap<Integer, Integer>> digraphCounts;

  
     public KeyEventHandler() {
        prevKey = -1;
        prevKeyTimeOfRelease = 0;
        pressedKeys = new ConcurrentHashMap<>();
        keyDurations = new ConcurrentHashMap<>();
        keyCounts = new ConcurrentHashMap<>();
        digraphDelays = new ConcurrentHashMap<>();
        digraphCounts = new ConcurrentHashMap<>();
    }

    public ConcurrentHashMap<Integer, Long> getPressedKeys() {
        return pressedKeys;
    }

    public ConcurrentHashMap<Integer, Long> getKeyDurations() {
        return keyDurations;
    }

    public ConcurrentHashMap<Integer, Integer> getKeyCounts() {
        return keyCounts;
    }

    public ConcurrentHashMap<Integer, HashMap<Integer, Long>> getDigraphDelays() {
        return digraphDelays;
    }

    public ConcurrentHashMap<Integer, HashMap<Integer, Integer>> getDigraphCounts() {
        return digraphCounts;
    }
     
     public void pressKey(KeyEvent keyEvent) {  
        Long time = System.currentTimeMillis();
        int currentKey = keyEvent.getKeyCode();
    
        if (prevKey != -1 && !pressedKeys.containsKey(prevKey)) {
            long duration = time - prevKeyTimeOfRelease;
            
            if (digraphCounts.containsKey(prevKey)) {
                if (digraphCounts.get(prevKey).containsKey(currentKey)) {
                    digraphDelays.get(prevKey).put(currentKey, digraphDelays.get(prevKey).get(currentKey) + duration);
                    digraphCounts.get(prevKey).put(currentKey, digraphCounts.get(prevKey).get(currentKey) + 1);
                } else {
                    digraphDelays.get(prevKey).put(currentKey, duration);
                    digraphCounts.get(prevKey).put(currentKey, 1);
                }
            } else {
                HashMap<Integer, Long> secondGraphDelay = new HashMap<>();
                secondGraphDelay.put(currentKey, duration);
                digraphDelays.put(prevKey, secondGraphDelay);
                HashMap<Integer, Integer> secondGraphCount = new HashMap<>();
                secondGraphCount.put(currentKey, 1);
                digraphCounts.put(prevKey, secondGraphCount);
            }     
        prevKey = currentKey;
        }
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
