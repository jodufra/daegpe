/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipleiria.dae.gpe.web.models.student;

import java.util.List;
import javax.faces.context.FacesContext;
import pt.ipleiria.dae.gpe.lib.beans.AttendanceBean;
import pt.ipleiria.dae.gpe.lib.dtos.AttendanceDTO;
import pt.ipleiria.dae.gpe.lib.dtos.UserDTO;
import pt.ipleiria.dae.gpe.lib.utilities.AttendanceOrderBy;
import pt.ipleiria.dae.gpe.lib.utilities.StudentAttendanceFindOptions;

/**
 *
 * @author Joel
 */
public class AttendanceIndexModel {

    private final AttendanceBean attendanceBean;

    public int pageId;
    public final int pageSize = 20;
    public AttendanceOrderBy orderBy;
    public String search;

    public long count;
    public int pagesCount;

    public AttendanceIndexModel(AttendanceBean attendanceBean) {
        this.attendanceBean = attendanceBean;
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
            case StudentNameAsc:
                return 3;
            case StudentNameDesc:
                return 4;
            case IsPresentAsc:
                return 5;
            case IsPresentDesc:
                return 6;
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
                orderBy = AttendanceOrderBy.StudentNameAsc;
                break;
            case 4:
                orderBy = AttendanceOrderBy.StudentNameDesc;
                break;
            case 5:
                orderBy = AttendanceOrderBy.IsPresentAsc;
                break;
            case 6:
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

    public List<AttendanceDTO> getAttendances() {
        StudentAttendanceFindOptions options = new StudentAttendanceFindOptions(pageId, pageSize, orderBy, (UserDTO) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user"), search);
        List<AttendanceDTO> list = attendanceBean.findFromStudent(options);
        this.count = options.count;
        this.pagesCount = (int) Math.ceil((double) count / (double) pageSize);
        return list;
    }
}
