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
@Table(name = "fuel")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Fuel.findAll", query = "SELECT f FROM Fuel f"),
    @NamedQuery(name = "Fuel.findByIdFuel", query = "SELECT f FROM Fuel f WHERE f.idFuel = :idFuel"),
    @NamedQuery(name = "Fuel.findByFuelName", query = "SELECT f FROM Fuel f WHERE f.fuelName = :fuelName")})
public class Fuel implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_fuel")
    private Integer idFuel;
    @Basic(optional = false)
    @Column(name = "fuel_name")
    private String fuelName;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fuel")
    private List<Car> carList;

    public Fuel() {
    }

    public Fuel(Integer idFuel) {
        this.idFuel = idFuel;
    }

    public Fuel(Integer idFuel, String fuelName) {
        this.idFuel = idFuel;
        this.fuelName = fuelName;
    }

    public Integer getIdFuel() {
        return idFuel;
    }

    public void setIdFuel(Integer idFuel) {
        this.idFuel = idFuel;
    }

    public String getFuelName() {
        return fuelName;
    }

    public void setFuelName(String fuelName) {
        this.fuelName = fuelName;
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
        hash += (idFuel != null ? idFuel.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Fuel)) {
            return false;
        }
        Fuel other = (Fuel) object;
        if ((this.idFuel == null && other.idFuel != null) || (this.idFuel != null && !this.idFuel.equals(other.idFuel))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Fuel[ idFuel=" + idFuel + " ]";
    }
    
}
