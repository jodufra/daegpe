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
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

/**
 *
 * @author 2121000
 */
@Singleton
@Startup
public class MigrationBean {

    @EJB
    private beans.UserBean userBean;

    @EJB
    private beans.UserRoleBean userRoleBean;

    @PostConstruct
    public void populateDB() {
        System.out.println("Seeding DB");

        userRoleBean.save(new UserRoleDTO("User"));
        userRoleBean.save(new UserRoleDTO("Admin"));
        userRoleBean.save(new UserRoleDTO("Developer"));

        UserRoleDTO role = userRoleBean.findByName("Developer");
        UserDTO user = new UserDTO("dev.gpe", "Developer", "developer@gpe.pt", "", "", role);
        user.setNewPassword("developer");
        userBean.save(user);

        role = userRoleBean.findByName("User");
        user = new UserDTO("2120189", "Duarte Mateus", "2120189@my.ipleiria.pt", "", "", role);
        user.setNewPassword("user");
        userBean.save(user);

        user = new UserDTO("2121000", "Joel Francisco", "2121000@my.ipleiria.pt", "", "", role);
        user.setNewPassword("user");
        userBean.save(user);

        user = new UserDTO("2120680", "Pedro Silva", "2120680@my.ipleiria.pt", "", "", role);
        user.setNewPassword("user");
        userBean.save(user);

        System.out.println("DB seeded");
    }

}
