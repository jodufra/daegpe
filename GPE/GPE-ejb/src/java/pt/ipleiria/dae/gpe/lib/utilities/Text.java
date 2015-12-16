/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipleiria.dae.gpe.lib.utilities;

import java.text.Normalizer;

/**
 *
 * @author Joel
 */
public class Text {

    public static String GenerateSlug(String string, boolean toLowerCase, boolean removeSpaces) {
        string = Normalizer.normalize(string, Normalizer.Form.NFD);
        string = string.replaceAll("[^\\p{ASCII}]", "");
        if (toLowerCase) {
            string = string.toLowerCase();
        }
        String pattern = "[\\~#%&*{}+/:<>@.?|\"-]";
        String replacement = " ";
        string = string.replaceAll(pattern, replacement).replaceAll("\\s+", " ");

        if (removeSpaces) {
            string = string.replaceAll(" ", "");
        } else {
            string = string.replaceAll("\\s", "-");
        }
        return string;
    }

}
