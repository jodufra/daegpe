package pt.ipleiria.dae.gpe.lib.dtos;

import pt.ipleiria.dae.gpe.lib.core.AbstractDTO;
import java.util.Date;
import pt.ipleiria.dae.gpe.lib.entities.Event;

/**
 *
 * @author joeld
 */
public class EventDTO extends AbstractDTO{

    private Integer idEvent;
    private String internalId;
    private String name;
    private Date dateStart;
    private short minutes;
    private UCDTO uc;
    private ManagerDTO manager;
    

     public EventDTO(Integer idEvent, String name, String internalId, Date dateStart, short minutes, UCDTO uc, ManagerDTO manager) {
        super(null);
        this.idEvent = idEvent;
        this.internalId = internalId;
        this.name = name;
        this.dateStart = dateStart;
        this.minutes = minutes;
        this.uc = uc;
        this.manager = manager;
        this.New = idEvent == 0;
    }

    public EventDTO(String name, String internalId, Date dateStart, short minutes, UCDTO uc, ManagerDTO manager) {
        super(null);
        this.idEvent = 0;
        this.internalId = internalId;
        this.name = name;
        this.dateStart = dateStart;
        this.minutes = minutes;
        this.uc = uc;
        this.manager = manager;
    }
    
    public EventDTO(Event event) {
        super(event);
        this.idEvent = event.getIdEvent();
        this.internalId = event.getInternalId();
        this.name = event.getName();
        this.dateStart = event.getDateStart();
        this.minutes = event.getMinutes();
        this.uc = null;
        this.manager = null;
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

    public UCDTO getUc() {
        return uc;
    }

    public void setUc(UCDTO uc) {
        this.uc = uc;
    }

    public ManagerDTO getManager() {
        return manager;
    }

    public void setManager(ManagerDTO manager) {
        this.manager = manager;
    }

    @Override
    public Object getRelationalId() {
        return getIdEvent();
    }

    public String getInternalId() {
        return internalId;
    }

    public void setInternalId(String internalId) {
        this.internalId = internalId;
    }

 
    
    
}
