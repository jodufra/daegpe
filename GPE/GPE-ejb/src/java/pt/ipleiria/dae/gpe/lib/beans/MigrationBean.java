/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipleiria.dae.gpe.lib.beans;

import java.util.Date;
import pt.ipleiria.dae.gpe.lib.dtos.AdministratorDTO;
import pt.ipleiria.dae.gpe.lib.dtos.StudentDTO;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import pt.ipleiria.dae.gpe.lib.dtos.EventDTO;
import pt.ipleiria.dae.gpe.lib.dtos.ManagerDTO;
import pt.ipleiria.dae.gpe.lib.dtos.UCDTO;
import pt.ipleiria.dae.gpe.lib.entities.UC;

/**
 *
 * @author 2121000
 */
@Singleton
@Startup
public class MigrationBean {

    @EJB
    private pt.ipleiria.dae.gpe.lib.beans.UserBean userBean;
    
    @EJB
    private EventBean eventBean;

    @PostConstruct 
    public void populateDB() {
        System.out.println("Seeding DB");

        AdministratorDTO admin;
        admin = new AdministratorDTO("dev", "Developer", "developer@gpe.pt", "admin");
        userBean.save(admin);

        StudentDTO student;
        student = new StudentDTO("2120189", "Duarte Mateus", "2120189@my.ipleiria.pt", "student");
        userBean.save(student);

        student = new StudentDTO("2121000", "Joel Francisco", "2121000@my.ipleiria.pt", "student");
        userBean.save(student);

        student = new StudentDTO("2120680", "Pedro Silva", "2120680@my.ipleiria.pt", "student");
        userBean.save(student);

        EventDTO event;
        event = new EventDTO("Aula Semanal", "a1", new Date(), (short) 60, null, null);
        eventBean.save(event);
        
        event = new EventDTO("Seminário", "s1", new Date(), (short) 60, null, null);
        eventBean.save(event);
        
        event = new EventDTO("Workshop", "w1", new Date(), (short) 60, null, null);
        eventBean.save(event);
        
        System.out.println("DB seeded");
    }

}
