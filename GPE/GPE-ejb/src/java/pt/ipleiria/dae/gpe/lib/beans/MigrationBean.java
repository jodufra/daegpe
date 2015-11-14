/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipleiria.dae.gpe.lib.beans;

import pt.ipleiria.dae.gpe.lib.dtos.AdministratorDTO;
import pt.ipleiria.dae.gpe.lib.dtos.StudentDTO;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import pt.ipleiria.dae.gpe.lib.dtos.AttendanceDTO;
import pt.ipleiria.dae.gpe.lib.dtos.EventDTO;
import pt.ipleiria.dae.gpe.lib.dtos.ManagerDTO;
import pt.ipleiria.dae.gpe.lib.dtos.UCDTO;
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
    @EJB
    private pt.ipleiria.dae.gpe.lib.beans.AttendanceBean attendanceBean;

    @PostConstruct
    public void populateDB() {
        try {
            System.out.println("Seeding DB");

            // UCs
            UCDTO uc;
            for (int i = 1; i <= 100; i++) {
                uc = new UCDTO("uc" + i, "Unidade Curricular " + i);
                ucBean.save(uc);
            }

            // Users
            AdministratorDTO admin;
            admin = new AdministratorDTO("admin", "Administrator", "admin@gpe.pt", "admin");
            userBean.save(admin);

            ManagerDTO manager;
            manager = new ManagerDTO("manager", "Manager", "manager@gpe.pt", "manager");
            userBean.save(manager);

            StudentDTO student;
            student = new StudentDTO("student", "Student", "student@my.ipleiria.pt", "student");
            userBean.save(student);

            student = new StudentDTO("2120189", "Duarte Mateus", "2120189@my.ipleiria.pt", "student");
            userBean.save(student);

            student = new StudentDTO("2121000", "Joel Francisco", "2121000@my.ipleiria.pt", "student");
            userBean.save(student);

            admin = new AdministratorDTO("2120680", "Pedro Silva", "2120680@my.ipleiria.pt", "student");
            userBean.save(admin);

            for (int i = 0; i < 100; i++) {
                String id = (9999000 + i) + "";
                student = new StudentDTO(id, "Dummy", "dummy" + id + "@my.ipleiria.pt", "student");
                userBean.save(student);
            }

            // Events
            EventDTO event;
            uc = new UCDTO(1, "uc" + 1, "Unidade Curricular " + 1);
            manager = new ManagerDTO(2, "manager", "Manager", "manager@gpe.pt");
            event = new EventDTO("event1", EventType.AULA, "Aula de DAE", EventDayWeek.SABADO, Room.D, 4, 17, 20, 22, "1:2", uc, manager);
            eventBean.save(event);

            uc = new UCDTO(2, "uc" + 2, "Unidade Curricular " + 2);
            event = new EventDTO("event2", EventType.AULA, "Aula de DAE", EventDayWeek.SABADO, Room.A, 4, 17, 20, 22, "1:2", uc, manager);
            eventBean.save(event);

            //UC to Students & Student to UCs
            student = new StudentDTO(3, "student", "Student Dummy", "dummy@my.ipleiria.pt");
            uc = new UCDTO(10, "uc" + 10, "Unidade Curricular " + 10);
            userBean.addUCStudent(student, uc); //UC to Student
            ucBean.addStudentUC(uc, student); //Student to UC

            // Attendances
            AttendanceDTO attendance;
            student = new StudentDTO(3, "student", "Student", "student@my.ipleiria.pt");
            event = new EventDTO(1, "event1", EventType.AULA, "Aula de DAE", EventDayWeek.SABADO, Room.D, 4, 17, 20, 22, "1:2", uc, manager);
            attendance = new AttendanceDTO(student, event, true);
            attendanceBean.save(attendance);
            attendance = new AttendanceDTO(1, student, event, true);
            userBean.addAttendanceStudent(attendance);
            eventBean.addAttendanceEvent(attendance);

            System.out.println("DB seeded");
        } catch (EntityValidationException | EntityNotFoundException e) {
            System.out.println("MigrationBean.populateDB() - !!! Exception while populating the database");
        }

    }

}
