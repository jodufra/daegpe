/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipleiria.dae.gpe.web.models.student;

import java.util.List;
import javax.faces.context.FacesContext;
import pt.ipleiria.dae.gpe.lib.beans.AttendanceBean;
import pt.ipleiria.dae.gpe.lib.beans.UserBean;
import pt.ipleiria.dae.gpe.lib.dtos.AttendanceDTO;
import pt.ipleiria.dae.gpe.lib.dtos.UserDTO;
import pt.ipleiria.dae.gpe.lib.beans.query.order.AttendanceOrderBy;
import pt.ipleiria.dae.gpe.lib.beans.query.options.StudentAttendanceFindOptions;
import pt.ipleiria.dae.gpe.lib.exceptions.EntityNotFoundException;

/**
 *
 * @author Joel
 */
public class AttendanceIndexModel {

    private final AttendanceBean attendanceBean;
    
    private final UserBean userBean;

    public int pageId;
    public final int pageSize = 20;
    public AttendanceOrderBy orderBy;
    public String search;

    public long count;
    public int pagesCount;

    public AttendanceIndexModel(AttendanceBean attendanceBean, UserBean userBean) {
        this.attendanceBean = attendanceBean;
        this.userBean = userBean;
        this.pageId = 1;
        this.orderBy = AttendanceOrderBy.EventNameAsc;
        this.search = "";
        this.count = 0;
    }

    public int getPageId() {
        return pageId;
    }

    public void setPageId(int pageId) {
        this.pageId = pageId;
    }

    public int getOrderBy() {
        switch (orderBy) {
            case EventNameAsc:
                return 1;
            case EventNameDesc:
                return 2;
            case UCNameAsc:
                return 3;
            case UCNameDesc:
                return 4;
            case ManagerNameAsc:
                return 5;
            case ManagerNameDesc:
                return 6;
            case DateEndAsc:
                return 7;
            case DateEndDesc:
                return 8;
            case DateStartAsc:
                return 9;
            case DateStartDesc:
                return 10;
            case IsPresentAsc:
                return 11;
            case IsPresentDesc:
                return 12;
        }
        return 0;
    }

    public void setOrderBy(int val) {
        switch (val) {
            case 1:
                orderBy = AttendanceOrderBy.EventNameAsc;
                break;
            case 2:
                orderBy = AttendanceOrderBy.EventNameDesc;
                break;
            case 3:
                orderBy = AttendanceOrderBy.UCNameAsc;
                break;
            case 4:
                orderBy = AttendanceOrderBy.UCNameDesc;
                break;
            case 5:
                orderBy = AttendanceOrderBy.ManagerNameAsc;
                break;
            case 6:
                orderBy = AttendanceOrderBy.ManagerNameDesc;
                break;
            case 7:
                orderBy = AttendanceOrderBy.DateEndAsc;
                break;
            case 8:
                orderBy = AttendanceOrderBy.DateEndDesc;
                break;
            case 9:
                orderBy = AttendanceOrderBy.DateStartAsc;
                break;
            case 10:
                orderBy = AttendanceOrderBy.DateStartDesc;
                break;
            case 11:
                orderBy = AttendanceOrderBy.IsPresentAsc;
                break;
            case 12:
                orderBy = AttendanceOrderBy.IsPresentDesc;
                break;
        }
    }

    public int getPageSize() {
        return pageSize;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.pageId = 1;
        this.orderBy = AttendanceOrderBy.EventNameAsc;
        this.search = search;
    }

    public long getCount() {
        return count;
    }

    public int getPagesCount() {
        return pagesCount;
    }

    //TODO - Melhorar
    
       public List<AttendanceDTO> getActiveAttendances() throws EntityNotFoundException {
        UserDTO userDTO = userBean.findByUsername(FacesContext.getCurrentInstance().getExternalContext().getRemoteUser());
        StudentAttendanceFindOptions options = new StudentAttendanceFindOptions(pageId, pageSize, orderBy, userDTO, search);
        List<AttendanceDTO> list = attendanceBean.findStudentActiveAttendances(options);
        this.count = options.count;
        this.pagesCount = (int) Math.ceil((double) count / (double) pageSize);
        return list;
    }
       
    public List<AttendanceDTO> getAttendances() throws EntityNotFoundException {
        UserDTO userDTO = userBean.findByUsername(FacesContext.getCurrentInstance().getExternalContext().getRemoteUser());
        StudentAttendanceFindOptions options = new StudentAttendanceFindOptions(pageId, pageSize, orderBy, userDTO, search);
        List<AttendanceDTO> list = attendanceBean.findStudentAttendances(options);
        this.count = options.count;
        this.pagesCount = (int) Math.ceil((double) count / (double) pageSize);
        return list;
    }
}
