/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Authenticator;

import biometrics150109n.properties.Pair;
import biometrics150109n.properties.PropertyModel;
import java.util.concurrent.ConcurrentHashMap;
import static java.util.logging.Level.INFO;
import java.util.logging.Logger;


/**
 *
 * @author ASUS
 */
public class DataHandler {
    public static final double MATCH_TOLERANCE = 0.6;
    public static final int KEY_DURATION_TOLERANCE = 25;
    public static final int DIGRAPH_DELAY_TOLERANCE = 100;

    public static PropertyModel createPropertyModel(KeyEventHandler handler, String username){
        ConcurrentHashMap<Integer, Long> pressDuration = new ConcurrentHashMap<>();
        ConcurrentHashMap<Pair, Long> digraphDelay = new ConcurrentHashMap<>();

        for (int key : handler.getKeyCounts().keySet()) {
            pressDuration.put(key, handler.getKeyDurations().get(key) / handler.getKeyCounts().get(key));
        }
        for (int key1 : handler.getDigraphCounts().keySet()) {
            for (int key2 : handler.getDigraphCounts().get(key1).keySet()) {
                Pair p = new Pair(key1, key2);
                digraphDelay.put(p, handler.getDigraphDelays().get(key1).get(key2) / handler.getDigraphCounts().get(key1).get(key2));
            }
        }

        PropertyModel newModel = new PropertyModel();
        newModel.setUsername(username);
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
                 durationMismatches++;
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
                 delayMismatches++;
            }
        }
        Logger.getLogger(DataHandler.class.getName()).log(INFO, Integer.toString(durationMatches));
        Logger.getLogger(DataHandler.class.getName()).log(INFO, Integer.toString(durationMismatches));
        Logger.getLogger(DataHandler.class.getName()).log(INFO, Integer.toString(delayMatches));
        Logger.getLogger(DataHandler.class.getName()).log(INFO, Integer.toString(delayMismatches));
        double f = 0.5*((durationMatches/(durationMatches+durationMismatches))+(delayMatches/(delayMatches+delayMismatches)));
        Logger.getLogger(DataHandler.class.getName()).log(INFO, Double.toString(f));
        return f >= MATCH_TOLERANCE;    
    }  
}

