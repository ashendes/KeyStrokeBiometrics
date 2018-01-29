/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IO;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author ASUS
 */
public class Cryptographer {
    private static final byte[] key = "Biometric150109N".getBytes();
    private static final String transformation = "AES/ECB/PKCS5Padding";
    
    public static void encryptedSave(Serializable object, OutputStream ostream) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException {
        try {
            // Length is 16 byte
            SecretKeySpec keySpec = new SecretKeySpec(key, transformation);

            // Create cipher
            Cipher cipher = Cipher.getInstance(transformation);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            SealedObject sealedObject = new SealedObject(object, cipher);

            // Wrap the output stream
            CipherOutputStream cos = new CipherOutputStream(ostream, cipher);
            ObjectOutputStream outputStream = new ObjectOutputStream(cos);
            outputStream.writeObject(sealedObject);
            outputStream.close();
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(Cryptographer.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
}
