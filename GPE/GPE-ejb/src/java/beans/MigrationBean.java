/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import dtos.UserDTO;
import dtos.UserRoleDTO;
import java.util.GregorianCalendar;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

/**
 *
 * @author 2121000
 */
@Singleton
@Startup
public class MigrationBean {

    @Inject
    private UserBean userBean;
    
    @Inject
    private UserRoleBean userRoleBean;

    @PostConstruct
    public void populateDB() {
        userRoleBean.save(new UserRoleDTO("User"));
        userRoleBean.save(new UserRoleDTO("Admin"));
        userRoleBean.save(new UserRoleDTO("Developer"));

        UserRoleDTO role = userRoleBean.findByName("Developer");
        GregorianCalendar calendar = new GregorianCalendar(1990, 3, 12);
        userBean.save(new UserDTO("2120189", "Duarte Mateus", "2120189@my.ipleiria.pt", "developer", "", calendar.getTime(), role));

        calendar = new GregorianCalendar(1993, 7, 26);
        userBean.save(new UserDTO("2121000", "Joel Francisco", "2121000@my.ipleiria.pt", "developer", "", calendar.getTime(), role));

        calendar = new GregorianCalendar(1987, 0, 16);
        userBean.save(new UserDTO("2120680", "Pedro Silva", "2120680@my.ipleiria.pt", "developer", "", calendar.getTime(), role));
    }

}
