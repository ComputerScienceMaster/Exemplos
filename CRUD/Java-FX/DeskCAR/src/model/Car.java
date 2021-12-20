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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
 * @author Garcia
 */
@Entity
@Table(name = "car")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Car.findAll", query = "SELECT c FROM Car c"),
    @NamedQuery(name = "Car.findByIdCar", query = "SELECT c FROM Car c WHERE c.idCar = :idCar"),
    @NamedQuery(name = "Car.findByTire", query = "SELECT c FROM Car c WHERE c.tire = :tire"),
    @NamedQuery(name = "Car.findByDisponibility", query = "SELECT c FROM Car c WHERE c.disponibility = :disponibility"),
    @NamedQuery(name = "Car.findByCarValue", query = "SELECT c FROM Car c WHERE c.carValue = :carValue")})
public class Car implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_car")
    private Integer idCar;
    @Basic(optional = false)
    @Column(name = "tire")
    private int tire;
    @Basic(optional = false)
    @Column(name = "disponibility")
    private int disponibility;
    @Basic(optional = false)
    @Column(name = "car_value")
    private float carValue;
    @JoinColumn(name = "fuel", referencedColumnName = "id_fuel")
    @ManyToOne(optional = false)
    private Fuel fuel;
    @JoinColumn(name = "buyer", referencedColumnName = "cpf")
    @ManyToOne
    private Buyer buyer;
    @JoinColumn(name = "car_engine", referencedColumnName = "id_engine")
    @ManyToOne(optional = false)
    private CarEngine carEngine;
    @JoinColumn(name = "attribute", referencedColumnName = "id_atributo")
    @ManyToOne(optional = false)
    private Attribute attribute;
    @JoinColumn(name = "color", referencedColumnName = "id_color")
    @ManyToOne(optional = false)
    private Color color;
    @JoinColumn(name = "model", referencedColumnName = "id_model")
    @ManyToOne(optional = false)
    private Model model;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "car")
    private List<Purchase> purchaseList;

    public Car() {
    }

    public Car(Integer idCar) {
        this.idCar = idCar;
    }

    public Car(Integer idCar, int tire, int disponibility, float carValue) {
        this.idCar = idCar;
        this.tire = tire;
        this.disponibility = disponibility;
        this.carValue = carValue;
    }

    public Integer getIdCar() {
        return idCar;
    }

    public void setIdCar(Integer idCar) {
        this.idCar = idCar;
    }

    public int getTire() {
        return tire;
    }

    public void setTire(int tire) {
        this.tire = tire;
    }

    public int getDisponibility() {
        return disponibility;
    }

    public void setDisponibility(int disponibility) {
        this.disponibility = disponibility;
    }

    public float getCarValue() {
        return carValue;
    }

    public void setCarValue(float carValue) {
        this.carValue = carValue;
    }

    public Fuel getFuel() {
        return fuel;
    }

    public void setFuel(Fuel fuel) {
        this.fuel = fuel;
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public void setBuyer(Buyer buyer) {
        this.buyer = buyer;
    }

    public CarEngine getCarEngine() {
        return carEngine;
    }

    public void setCarEngine(CarEngine carEngine) {
        this.carEngine = carEngine;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    @XmlTransient
    public List<Purchase> getPurchaseList() {
        return purchaseList;
    }

    public void setPurchaseList(List<Purchase> purchaseList) {
        this.purchaseList = purchaseList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCar != null ? idCar.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Car)) {
            return false;
        }
        Car other = (Car) object;
        if ((this.idCar == null && other.idCar != null) || (this.idCar != null && !this.idCar.equals(other.idCar))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Car[ idCar=" + idCar + " ]";
    }
    
}
