/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Authenticator;

import biometrics150109n.properties.Pair;
import biometrics150109n.properties.PropertyModel;
import java.util.concurrent.ConcurrentHashMap;


/**
 *
 * @author ASUS
 */
public class DataHandler {
    public static final double MATCH_TOLERANCE = 0.65;
    public static final int KEY_DURATION_TOLERANCE = 25;
    public static final int DIGRAPH_DELAY_TOLERANCE = 30;

    public static PropertyModel createPropertyModel(KeyEventHandler handler){
        ConcurrentHashMap<Integer, Long> pressDuration = new ConcurrentHashMap<>();
        ConcurrentHashMap<Pair, Long> digraphDelay = new ConcurrentHashMap<>();

        for (int key1 : handler.getKeyCounts().keySet()) {
            pressDuration.put(key1, handler.getKeyDurations().get(key1) / handler.getKeyCounts().get(key1));
        }
        for (int key : handler.getDigraphCounts().keySet()) {
            for (Integer key2 : handler.getDigraphCounts().get(key).keySet()) {
                Pair pi = new Pair(key, key2);
                digraphDelay.put(pi, handler.getDigraphDelays().get(key).get(key2) / handler.getDigraphCounts().get(key).get(key2));
            }
        }

        PropertyModel newModel = new PropertyModel();
        newModel.setDigraphDelay(digraphDelay);
        newModel.setPressDuration(pressDuration);
        return newModel;
    }
    
    
    public static boolean authenticate(PropertyModel newModel, PropertyModel reference){
        int durationMatches = 0;
        int durationMismatches = 0;

        int delayMatches = 0;
        int delayMismatches = 0;

        for (int i : newModel.getPressDuration().keySet()) {
            if (reference.getPressDuration().containsKey(i)) {
                if (Math.abs(reference.getPressDuration().get(i) - newModel.getPressDuration().get(i)) < KEY_DURATION_TOLERANCE) {
                    durationMatches++;
                } else {
                    durationMismatches++;
                }
            } else {
                // durationMismatches++;
            }
        }

        for (Pair pair : newModel.getDigraphDelay().keySet()) {
            if (reference.getDigraphDelay().containsKey(pair)) {
                if (Math.abs(reference.getDigraphDelay().get(pair) - newModel.getDigraphDelay().get(pair)) < DIGRAPH_DELAY_TOLERANCE) {
                    delayMatches++;
                } else {
                    delayMismatches++;
                }
            } else {
                // delayMismatches++;
            }
        }

        double f = 1.5 * ((durationMatches / (durationMismatches * 1.0 + durationMatches))
                + (delayMatches / (delayMatches * 1.0 + delayMismatches))) / 2.5;

        return f >= MATCH_TOLERANCE;    
    }  
}

