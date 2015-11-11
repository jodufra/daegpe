/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipleiria.dae.gpe.lib.utilities;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author Joel
 */
public class Security {

    public static String GetMD5Hash(String input) {
        if (input == null || input.isEmpty()) {
            return "";
        }

        String hash = "";
        try {
            byte[] bytesOfMessage = DatatypeConverter.parseBase64Binary(input);
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] thedigest = md.digest(bytesOfMessage);
            hash = DatatypeConverter.printBase64Binary(thedigest);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Security.class.getName()).log(Level.SEVERE, null, ex);
        }

        return hash.replace("-", "").toLowerCase();
    }

   

}
 