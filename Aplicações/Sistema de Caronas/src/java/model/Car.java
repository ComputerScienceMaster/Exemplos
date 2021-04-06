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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author USER
 */
@Entity
@Table(name = "car")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Car.findAll", query = "SELECT c FROM Car c"),
    @NamedQuery(name = "Car.findByPlate", query = "SELECT c FROM Car c WHERE c.plate = :plate"),
    @NamedQuery(name = "Car.findByBrand", query = "SELECT c FROM Car c WHERE c.brand = :brand"),
    @NamedQuery(name = "Car.findByModel", query = "SELECT c FROM Car c WHERE c.model = :model"),
    @NamedQuery(name = "Car.findBySituation", query = "SELECT c FROM Car c WHERE c.situation = :situation")})
public class Car implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "plate")
    private String plate;
    @Basic(optional = false)
    @Column(name = "brand")
    private String brand;
    @Basic(optional = false)
    @Column(name = "model")
    private String model;
    @Basic(optional = false)
    @Column(name = "situation")
    private String situation;
    @JoinColumn(name = "Sector_idSector", referencedColumnName = "idSector")
    @ManyToOne(optional = false)
    private Sector sectoridSector;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "carPlate")
    private List<Travel> travelList;

    public Car() {
    }

    public Car(String plate) {
        this.plate = plate;
    }

    public Car(String plate, String brand, String model, String situation) {
        this.plate = plate;
        this.brand = brand;
        this.model = model;
        this.situation = situation;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSituation() {
        return situation;
    }

    public void setSituation(String situation) {
        this.situation = situation;
    }

    public Sector getSectoridSector() {
        return sectoridSector;
    }

    public void setSectoridSector(Sector sectoridSector) {
        this.sectoridSector = sectoridSector;
    }

    @XmlTransient
    public List<Travel> getTravelList() {
        return travelList;
    }

    public void setTravelList(List<Travel> travelList) {
        this.travelList = travelList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (plate != null ? plate.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Car)) {
            return false;
        }
        Car other = (Car) object;
        if ((this.plate == null && other.plate != null) || (this.plate != null && !this.plate.equals(other.plate))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Car[ plate=" + plate + " ]";
    }
    
}
