/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Garcia
 */
@Entity
@Table(name = "car_engine")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CarEngine.findAll", query = "SELECT c FROM CarEngine c"),
    @NamedQuery(name = "CarEngine.findByIdEngine", query = "SELECT c FROM CarEngine c WHERE c.idEngine = :idEngine"),
    @NamedQuery(name = "CarEngine.findByEnginePower", query = "SELECT c FROM CarEngine c WHERE c.enginePower = :enginePower")})
public class CarEngine implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_engine")
    private Integer idEngine;
    @Basic(optional = false)
    @Column(name = "engine_power")
    private String enginePower;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "carEngine")
    private List<Car> carList;

    public CarEngine() {
    }

    public CarEngine(Integer idEngine) {
        this.idEngine = idEngine;
    }

    public CarEngine(Integer idEngine, String enginePower) {
        this.idEngine = idEngine;
        this.enginePower = enginePower;
    }

    public Integer getIdEngine() {
        return idEngine;
    }

    public void setIdEngine(Integer idEngine) {
        this.idEngine = idEngine;
    }

    public String getEnginePower() {
        return enginePower;
    }

    public void setEnginePower(String enginePower) {
        this.enginePower = enginePower;
    }

    @XmlTransient
    public List<Car> getCarList() {
        return carList;
    }

    public void setCarList(List<Car> carList) {
        this.carList = carList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEngine != null ? idEngine.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CarEngine)) {
            return false;
        }
        CarEngine other = (CarEngine) object;
        if ((this.idEngine == null && other.idEngine != null) || (this.idEngine != null && !this.idEngine.equals(other.idEngine))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.CarEngine[ idEngine=" + idEngine + " ]";
    }
    
}
