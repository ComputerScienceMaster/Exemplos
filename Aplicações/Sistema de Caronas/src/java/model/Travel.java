/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author USER
 */
@Entity
@Table(name = "travel")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Travel.findAll", query = "SELECT t FROM Travel t"),
    @NamedQuery(name = "Travel.findByIdTravel", query = "SELECT t FROM Travel t WHERE t.idTravel = :idTravel"),
    @NamedQuery(name = "Travel.findByDestination", query = "SELECT t FROM Travel t WHERE t.destination = :destination"),
    @NamedQuery(name = "Travel.findByDescription", query = "SELECT t FROM Travel t WHERE t.description = :description"),
    @NamedQuery(name = "Travel.findByDateInitial", query = "SELECT t FROM Travel t WHERE t.dateInitial = :dateInitial"),
    @NamedQuery(name = "Travel.findByDateEnd", query = "SELECT t FROM Travel t WHERE t.dateEnd = :dateEnd"),
    @NamedQuery(name = "Travel.findByTimeInitial", query = "SELECT t FROM Travel t WHERE t.timeInitial = :timeInitial"),
    @NamedQuery(name = "Travel.findByTimeEnd", query = "SELECT t FROM Travel t WHERE t.timeEnd = :timeEnd"),
    @NamedQuery(name = "Travel.findByIsRideAllowed", query = "SELECT t FROM Travel t WHERE t.isRideAllowed = :isRideAllowed"),
    @NamedQuery(name = "Travel.findByIsDone", query = "SELECT t FROM Travel t WHERE t.isDone = :isDone")})
public class Travel implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_travel")
    private Integer idTravel;
    @Basic(optional = false)
    @Column(name = "destination")
    private String destination;
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @Column(name = "date_initial")
    @Temporal(TemporalType.DATE)
    private Date dateInitial;
    @Basic(optional = false)
    @Column(name = "date_end")
    @Temporal(TemporalType.DATE)
    private Date dateEnd;
    @Column(name = "time_initial")
    private String timeInitial;
    @Column(name = "time_end")
    private String timeEnd;
    @Column(name = "isRideAllowed")
    private Boolean isRideAllowed;
    @Basic(optional = false)
    @Column(name = "isDone")
    private boolean isDone;
    @JoinTable(name = "travel_has_user_account", joinColumns = {
        @JoinColumn(name = "travel_id_viagem", referencedColumnName = "id_travel")}, inverseJoinColumns = {
        @JoinColumn(name = "user_account_userLogin", referencedColumnName = "userLogin")})
    @ManyToMany
    private List<UserAccount> userAccountList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "travelIdTravel")
    private List<Task> taskList;
    @JoinColumn(name = "car_plate", referencedColumnName = "plate")
    @ManyToOne(optional = false)
    private Car carPlate;
    @JoinColumn(name = "owner", referencedColumnName = "userLogin")
    @ManyToOne(optional = false)
    private UserAccount owner;

    public Travel() {
    }

    public Travel(Integer idTravel) {
        this.idTravel = idTravel;
    }

    public Travel(Integer idTravel, String destination, Date dateInitial, Date dateEnd, boolean isDone) {
        this.idTravel = idTravel;
        this.destination = destination;
        this.dateInitial = dateInitial;
        this.dateEnd = dateEnd;
        this.isDone = isDone;
    }

    public Integer getIdTravel() {
        return idTravel;
    }

    public void setIdTravel(Integer idTravel) {
        this.idTravel = idTravel;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateInitial() {
        return dateInitial;
    }

    public void setDateInitial(Date dateInitial) {
        this.dateInitial = dateInitial;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getTimeInitial() {
        return timeInitial;
    }

    public void setTimeInitial(String timeInitial) {
        this.timeInitial = timeInitial;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public Boolean getIsRideAllowed() {
        return isRideAllowed;
    }

    public void setIsRideAllowed(Boolean isRideAllowed) {
        this.isRideAllowed = isRideAllowed;
    }

    public boolean getIsDone() {
        return isDone;
    }

    public void setIsDone(boolean isDone) {
        this.isDone = isDone;
    }

    @XmlTransient
    public List<UserAccount> getUserAccountList() {
        return userAccountList;
    }

    public void setUserAccountList(List<UserAccount> userAccountList) {
        this.userAccountList = userAccountList;
    }

    @XmlTransient
    public List<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }

    public Car getCarPlate() {
        return carPlate;
    }

    public void setCarPlate(Car carPlate) {
        this.carPlate = carPlate;
    }

    public UserAccount getOwner() {
        return owner;
    }

    public void setOwner(UserAccount owner) {
        this.owner = owner;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTravel != null ? idTravel.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Travel)) {
            return false;
        }
        Travel other = (Travel) object;
        if ((this.idTravel == null && other.idTravel != null) || (this.idTravel != null && !this.idTravel.equals(other.idTravel))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Travel[ idTravel=" + idTravel + " ]";
    }
    
}
