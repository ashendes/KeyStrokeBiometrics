/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IO;

import biometrics150109n.properties.PropertyModel;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author ASUS
 */
public class DataIO {   
    
    public static void saveData(PropertyModel model){
        try(FileOutputStream fileOutputStream = new FileOutputStream(model.getUsername() + ".txt");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);)
        {
            objectOutputStream.writeObject(model);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DataIO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DataIO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static PropertyModel readData(String username){
        try (FileInputStream fileInputStream = new FileInputStream(username + ".txt");
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);)
        {
            PropertyModel model = (PropertyModel) objectInputStream.readObject();
            return model;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DataIO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(DataIO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }
    
}
