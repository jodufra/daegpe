package pt.ipleiria.dae.gpe.lib.entities;

import pt.ipleiria.dae.gpe.lib.core.AbstractEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author joeld
 */
@Entity
@NamedQueries({})
@Table(name = "EVENTS")
public class Event extends AbstractEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer idEvent;

    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    protected String internalId;

    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    private String name;

    @NotNull
    @Temporal(TemporalType.DATE)
    private Date dateStart;

    @NotNull
    @Min(0)
    @Max(720)
    private short minutes;

    @Size(max = 255)
    private String search;

    //@ManyToOne(optional = true)
    //@JoinColumn(name = "IDUC")
    //@NotNull
    private UC uc;

    //@ManyToOne(optional = false)
    //@JoinColumn(name = "IDMANAGER")
    //@NotNull
    private Manager manager;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "event")
    private Collection<Attendance> participants;

    public Event() {
        this.participants = new ArrayList<>();
    }

    public Event(String name, String internalId, Date dateStart, short minutes, UC uc, Manager manager) {
        this.name = name;
        this.internalId = internalId;
        this.dateStart = dateStart;
        this.minutes = minutes;
        this.uc = uc;
        this.manager = manager;
        this.participants = new ArrayList<>();
    }

    public Event(Integer idEvent, String internalId, String name, Date dateStart, short minutes, String search, UC uc, Manager manager) {
        this.idEvent = idEvent;
        this.name = name;
        this.dateStart = dateStart;
        this.minutes = minutes;
        this.search = search;
        this.uc = uc;
        this.manager = manager;
        this.internalId = internalId;
        this.participants = new ArrayList<>();
    }

    public Integer getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(Integer idEvent) {
        this.idEvent = idEvent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public short getMinutes() {
        return minutes;
    }

    public void setMinutes(short minutes) {
        this.minutes = minutes;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public UC getUc() {
        return uc;
    }

    public void setUc(UC uc) {
        this.uc = uc;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }
    
    public Collection<Attendance> getParticipants() {
        return participants;
    }

    public void setParticipants(Collection<Attendance> participants) {
        this.participants = participants;
    }

    public void addParticipant(Attendance a) {
        this.participants.add(a);
    }

    @Override
    public boolean isNew() {
        return idEvent == 0;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + Objects.hashCode(this.idEvent);
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
        final Event other = (Event) obj;
        if (!Objects.equals(this.idEvent, other.idEvent)) {
            return false;
        }
        return true;
    }

    public String getInternalId() {
        return internalId;
    }

    public void setInternalId(String internalId) {
        this.internalId = internalId;
    }
    
    @Override
    public String toString()
    {
        return this.name;
    }
    
}
