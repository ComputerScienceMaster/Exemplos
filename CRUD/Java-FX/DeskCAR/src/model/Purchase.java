/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Garcia
 */
@Entity
@Table(name = "purchase")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Purchase.findAll", query = "SELECT p FROM Purchase p"),
    @NamedQuery(name = "Purchase.findByIdPurchase", query = "SELECT p FROM Purchase p WHERE p.idPurchase = :idPurchase"),
    @NamedQuery(name = "Purchase.findByPurchaseDate", query = "SELECT p FROM Purchase p WHERE p.purchaseDate = :purchaseDate")})
public class Purchase implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_purchase")
    private Integer idPurchase;
    @Basic(optional = false)
    @Column(name = "purchase_date")
    @Temporal(TemporalType.DATE)
    private Date purchaseDate;
    @JoinColumn(name = "car", referencedColumnName = "id_car")
    @ManyToOne(optional = false)
    private Car car;
    @JoinColumn(name = "buyer", referencedColumnName = "cpf")
    @ManyToOne(optional = false)
    private Buyer buyer;

    public Purchase() {
    }

    public Purchase(Integer idPurchase) {
        this.idPurchase = idPurchase;
    }

    public Purchase(Integer idPurchase, Date purchaseDate) {
        this.idPurchase = idPurchase;
        this.purchaseDate = purchaseDate;
    }

    public Integer getIdPurchase() {
        return idPurchase;
    }

    public void setIdPurchase(Integer idPurchase) {
        this.idPurchase = idPurchase;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public void setBuyer(Buyer buyer) {
        this.buyer = buyer;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPurchase != null ? idPurchase.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Purchase)) {
            return false;
        }
        Purchase other = (Purchase) object;
        if ((this.idPurchase == null && other.idPurchase != null) || (this.idPurchase != null && !this.idPurchase.equals(other.idPurchase))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Purchase[ idPurchase=" + idPurchase + " ]";
    }
    
}
