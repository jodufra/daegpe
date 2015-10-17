/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author joeld
 */
@Entity
@NamedQueries({})
@Table(name = "UCS")
public class UC extends AbstractEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer idUC;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    private String internalId;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    private String name;

    @Size(max = 255)
    private String search;
    
    @ManyToMany
    @JoinTable(name = "UCS_VS_STUDENTS",
            joinColumns = @JoinColumn(name = "IDUC", referencedColumnName = "IDUC"),
            inverseJoinColumns = @JoinColumn(name = "IDSTUDENT", referencedColumnName = "IDUSER")
    )
    private Collection<Student> students;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "uc")
    private Collection<Event> events;

    public UC() {
        this.idUC = 0;
        this.internalId = "";
        this.name = "";
    }

    public UC(String internalId, String name) {
        this.idUC = 0;
        this.internalId = internalId;
        this.name = name;
    }

    public UC(Integer idUC, String internalId, String name) {
        this.idUC = idUC;
        this.internalId = internalId;
        this.name = name;
    }

    public Integer getIdUC() {
        return idUC;
    }

    public void setIdUC(Integer idUC) {
        this.idUC = idUC;
    }

    public String getInternalId() {
        return internalId;
    }

    public void setInternalId(String internalId) {
        this.internalId = internalId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public Collection<Event> getEvents() {
        return events;
    }

    public void setEvents(Collection<Event> events) {
        this.events = events;
    }

    public Collection<Student> getStudents() {
        return students;
    }

    public void setStudents(Collection<Student> students) {
        this.students = students;
    }
    

    @Override
    public boolean isNew() {
        return this.idUC == 0;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.idUC);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UC other = (UC) obj;
        if (!Objects.equals(this.idUC, other.idUC)) {
            return false;
        }
        return true;
    }

}
