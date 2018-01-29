/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Authenticator;

import biometrics150109n.properties.UserPropertyModel;
import java.util.concurrent.ConcurrentHashMap;
import static java.util.logging.Level.INFO;
import java.util.logging.Logger;


/**
 *
 * @author ASUS
 */
public class DataHandler {
    public static final double MATCH_TOLERANCE = 0.7;
    public static final int KEY_DURATION_TOLERANCE = 25;
    public static final int DIGRAPH_DELAY_TOLERANCE = 50;

    public static UserPropertyModel createPropertyModel(KeyEventHandler handler, String username, String password){
        ConcurrentHashMap<Integer, Long> pressDuration = new ConcurrentHashMap<>();
        for (int key : handler.getKeyCounts().keySet()) {
            pressDuration.put(key, handler.getKeyDurations().get(key) / handler.getKeyCounts().get(key));
        }    

        UserPropertyModel newModel = new UserPropertyModel();
        newModel.setUsername(username);
        newModel.setPassword(password);
        newModel.setDigraphDelay(handler.getDigraphDelays());
        newModel.setPressDuration(pressDuration);
        return newModel;
    }
    
    
    public static boolean authenticate(UserPropertyModel newModel, UserPropertyModel reference){
        if (!newModel.getPassword().equals(reference.getPassword())){
            Logger.getLogger(DataHandler.class.getName()).log(INFO, "Password mismatch " + newModel.getPassword() + " "  + reference.getPassword() );        
            return false;
        }
        
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
        for (int i=0;i<newModel.getDigraphDelay().size();i++){
            if(Math.abs(reference.getDigraphDelay().get(i) - newModel.getDigraphDelay().get(i)) < DIGRAPH_DELAY_TOLERANCE){
                delayMatches++;
            }
            else{
                delayMismatches++;
            }
        }
        
        Logger.getLogger(DataHandler.class.getName()).log(INFO, "duration match = " +Integer.toString(durationMatches));
        Logger.getLogger(DataHandler.class.getName()).log(INFO, "duration mismatch = " +Integer.toString(durationMismatches));
        Logger.getLogger(DataHandler.class.getName()).log(INFO, "delay match = " +Integer.toString(delayMatches));
        Logger.getLogger(DataHandler.class.getName()).log(INFO, "delay mismatch = " +Integer.toString(delayMismatches));
        double f = 0.5*(((double)durationMatches/(durationMatches+durationMismatches))+((double)delayMatches/(delayMatches+delayMismatches)));
        Logger.getLogger(DataHandler.class.getName()).log(INFO, "f = " + Double.toString(f));
        return f >= MATCH_TOLERANCE;    
    }  
}

