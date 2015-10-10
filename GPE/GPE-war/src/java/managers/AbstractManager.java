/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managers;

/**
 *
 * @author Joel
 */
public class AbstractManager {

    protected String Redirect(String url) {
        if (url.charAt(0) != '/') {
            url = "/" + url;
        }

        url += (url.contains("?") ? "&" : "?") + "faces-redirect=true";

        return url;
    }

}
