/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipleiria.dae.gpe.lib.beans;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import pt.ipleiria.dae.gpe.lib.dtos.AdministratorDTO;
import pt.ipleiria.dae.gpe.lib.dtos.StudentDTO;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import pt.ipleiria.dae.gpe.lib.dtos.ManagerDTO;
import pt.ipleiria.dae.gpe.lib.dtos.UCDTO;
import pt.ipleiria.dae.gpe.lib.entities.EventGroup;
import pt.ipleiria.dae.gpe.lib.exceptions.EntityNotFoundException;
import pt.ipleiria.dae.gpe.lib.exceptions.EntityValidationException;
import pt.ipleiria.dae.gpe.lib.entities.EventType;

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

            // Users
            AdministratorDTO admin;
            admin = new AdministratorDTO("admin", "Administrator", "admin@gpe.pt", "admin");
            userBean.save(admin);

            ManagerDTO manager;
            manager = new ManagerDTO("manager", "Manager", "manager@gpe.pt", "manager");
            userBean.save(manager);
            
            manager = new ManagerDTO("manager2", "Manager2", "manager2@gpe.pt", "manager2");
            userBean.save(manager);

            StudentDTO student;
            student = new StudentDTO("student", "Student", "student@my.ipleiria.pt", "student");
            userBean.save(student);

            student = new StudentDTO("2120189", "Duarte Mateus", "2120189@my.ipleiria.pt", "student");
            userBean.save(student);

            student = new StudentDTO("2120680", "Pedro Silva", "2120680@my.ipleiria.pt", "student");
            userBean.save(student);

            student = new StudentDTO("2121000", "Joel Francisco", "2121000@my.ipleiria.pt", "student");
            userBean.save(student);

            for (int i = 0; i < 100; i++) {
                String id = (9999000 + i) + "";
                student = new StudentDTO(id, "Dummy", "dummy" + id + "@my.ipleiria.pt", "student");
                userBean.save(student);
            }

            // UCs
            UCDTO uc;
            uc = new UCDTO("DAE", "Desenvolvimento de Aplicações Empresariais");
            ucBean.save(uc);
            for (int i = 1; i <= 100; i++) {
                uc = new UCDTO("uc" + i, "Unidade Curricular " + i);
                ucBean.save(uc);
            }

            // Events
            EventGroup eventGroup;
            uc = new UCDTO(1, "DAE", "Desenvolvimento de Aplicações Empresariais");
            manager = new ManagerDTO(2, "manager", "Manager", "manager@gpe.pt");
            Calendar dateStart = new GregorianCalendar(2015, 8, 13);
            Calendar dateEnd = new GregorianCalendar(2016, 0, 8);
            Calendar timeStart = new GregorianCalendar(0, 0, 0, 18, 0, 0);
            Calendar timeDuration = new GregorianCalendar(0, 0, 0, 2, 0, 0);
            eventGroup = new EventGroup("DAE (T)", EventType.AULATEORICA, "DAE Teórico", "A.S.2.12", dateStart, dateEnd, timeStart, timeDuration, false, false, true, false, false, false, false, new ArrayList<Integer>(), uc, manager);
            eventBean.save(eventGroup);
            timeStart = new GregorianCalendar(0, 0, 0, 15, 0, 0);
            timeDuration = new GregorianCalendar(0, 0, 0, 2, 0, 0);
            eventGroup = new EventGroup("DAE (PL1)", EventType.AULAPRATICA, "DAE Prático Laboratorial 1", "A.S.2.12", dateStart, dateEnd, timeStart, timeDuration, false, true, false, false, false, false, false, new ArrayList<Integer>(), uc, manager);
            eventBean.save(eventGroup);

            //UC to Students & Student to UCs
            student = new StudentDTO(4, "", "", "");
            userBean.addUCStudent(student, uc); //UC to Student
            ucBean.addStudentUC(uc, student); //Student to UC
            student = new StudentDTO(5, "", "", "");
            userBean.addUCStudent(student, uc); //UC to Student
            ucBean.addStudentUC(uc, student); //Student to UC
            student = new StudentDTO(6, "", "", "");
            userBean.addUCStudent(student, uc); //UC to Student
            ucBean.addStudentUC(uc, student); //Student to UC
            student = new StudentDTO(7, "", "", "");
            userBean.addUCStudent(student, uc); //UC to Student
            ucBean.addStudentUC(uc, student); //Student to UC

            // Attendances
            //AttendanceDTO attendance;
            //student = new StudentDTO(3, "student", "Student", "student@my.ipleiria.pt");
            //event = new EventDTO(1, "DAE T", EventType.AULATEORICA, "DAE Te�rico", WeekDay.SATURDAY, Room.D, 4, 17, "2015:13:15;2016:1:4", 22, "1:2", uc, manager);
            //attendance = new AttendanceDTO(student, event);
            //attendanceBean.save(attendance);
            //attendance = new AttendanceDTO(1, student, event, false);
            //userBean.addAttendanceStudent(attendance);
            //eventBean.addAttendanceEvent(attendance);
            System.out.println("DB seeded");
        } catch (EntityValidationException | EntityNotFoundException e) {
            System.out.println("MigrationBean.populateDB() - !!! Exception while populating the database");
        }

    }

}
