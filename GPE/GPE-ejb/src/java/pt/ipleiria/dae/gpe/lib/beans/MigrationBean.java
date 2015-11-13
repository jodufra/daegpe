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
import pt.ipleiria.dae.gpe.lib.entities.Manager;
import pt.ipleiria.dae.gpe.lib.exceptions.EntityNotFoundException;
import pt.ipleiria.dae.gpe.lib.exceptions.EntityValidationException;
import pt.ipleiria.dae.gpe.lib.utilities.EventDayWeek;
import pt.ipleiria.dae.gpe.lib.utilities.EventType;
import pt.ipleiria.dae.gpe.lib.utilities.Room;

/**
 *
 * @author 2121000
 */
@Singleton
@Startup
public class MigrationBean {

    @EJB
    private pt.ipleiria.dae.gpe.lib.beans.UCBean ucBean;
    @EJB
    private pt.ipleiria.dae.gpe.lib.beans.UserBean userBean;
    @EJB
    private pt.ipleiria.dae.gpe.lib.beans.EventBean eventBean;

    @PostConstruct
    public void populateDB() {
        try {
            System.out.println("Seeding DB");

            UCDTO ucDTO = new UCDTO("ucPed", "Aqui vai Ela a UC");
            ucBean.save(ucDTO);
            
            UCDTO ucDTO2 = new UCDTO("ucPed2", "Aqui vai Ela a UC2");
            ucBean.save(ucDTO2);
            
            // UCs
            for (int i = 0; i < 100; i++) {
                ucBean.save(new UCDTO("" + i, "UC" + i));
            }
            
            
            
            ManagerDTO managerPedro = new ManagerDTO("managerPedro", "mPedro", "managerpedro@manager.com", "managerPedro");
            userBean.save(managerPedro);

            // Users
            AdministratorDTO admin;
            admin = new AdministratorDTO("admin", "Administrator", "admin@gpe.pt", "admin");
            userBean.save(admin);
            
            ManagerDTO manager;
            manager = new ManagerDTO("manager", "Manager", "manager@gpe.pt", "manager"); 
            userBean.save(manager);

            StudentDTO student;
            student = new StudentDTO("2120189", "Duarte Mateus", "2120189@my.ipleiria.pt", "student");
            userBean.save(student);

            student = new StudentDTO("2121000", "Joel Francisco", "2121000@my.ipleiria.pt", "student");
            userBean.save(student);

            admin = new AdministratorDTO("2120680", "Pedro Silva", "2120680@my.ipleiria.pt", "student");
            userBean.save(admin);
            
            for (int i = 0; i < 100; i++) {
                String id = (9999000 + i) + "";
                student = new StudentDTO(id, "Dummy Student", id + "@my.ipleiria.pt", "student");
                userBean.save(student);
            }

            // Events
            EventDTO event;
            //userBean.save(new AdministratorDTO("adminPedro", "AdminPedro", "adminpedro@admin.com", "adminPedro"))
            //event = new EventDTO("event1", EventType.AULA, "Aula de DAE", EventDayWeek.SABADO, Room.D, 4, 17, 20, 22, "1:2", ucDTO, managerPedro );
            //eventBean.save(event);
            
            //event = new EventDTO("event2", EventType.AULA, "Aula de DAE", EventDayWeek.SABADO, Room.A, 4, 17, 20, 22, "1:2", ucDTO2, (ManagerDTO) userBean.find("manager"));
            //eventBean.save(event);

            System.out.println("DB seeded");
        } catch (EntityValidationException | EntityNotFoundException e) {
            System.out.println("MigrationBean.populateDB() - !!! Exception while populating the database");
        }

    }

}
