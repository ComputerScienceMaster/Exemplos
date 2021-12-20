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
@Table(name = "buyer")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Buyer.findAll", query = "SELECT b FROM Buyer b"),
    @NamedQuery(name = "Buyer.findByCpf", query = "SELECT b FROM Buyer b WHERE b.cpf = :cpf"),
    @NamedQuery(name = "Buyer.findByBuyerPassword", query = "SELECT b FROM Buyer b WHERE b.buyerPassword = :buyerPassword"),
    @NamedQuery(name = "Buyer.findByBuyerName", query = "SELECT b FROM Buyer b WHERE b.buyerName = :buyerName"),
    @NamedQuery(name = "Buyer.findByBuyerPhone", query = "SELECT b FROM Buyer b WHERE b.buyerPhone = :buyerPhone"),
    @NamedQuery(name = "Buyer.findByBuyerEmail", query = "SELECT b FROM Buyer b WHERE b.buyerEmail = :buyerEmail")})
public class Buyer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "cpf")
    private Long cpf;
    @Basic(optional = false)
    @Column(name = "buyer_password")
    private String buyerPassword;
    @Basic(optional = false)
    @Column(name = "buyer_name")
    private String buyerName;
    @Basic(optional = false)
    @Column(name = "buyer_phone")
    private long buyerPhone;
    @Basic(optional = false)
    @Column(name = "buyer_email")
    private String buyerEmail;
    @OneToMany(mappedBy = "buyer")
    private List<Car> carList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "buyer")
    private List<Purchase> purchaseList;

    public Buyer() {
    }

    public Buyer(Long cpf) {
        this.cpf = cpf;
    }

    public Buyer(Long cpf, String buyerPassword, String buyerName, long buyerPhone, String buyerEmail) {
        this.cpf = cpf;
        this.buyerPassword = buyerPassword;
        this.buyerName = buyerName;
        this.buyerPhone = buyerPhone;
        this.buyerEmail = buyerEmail;
    }

    public Long getCpf() {
        return cpf;
    }

    public void setCpf(Long cpf) {
        this.cpf = cpf;
    }

    public String getBuyerPassword() {
        return buyerPassword;
    }

    public void setBuyerPassword(String buyerPassword) {
        this.buyerPassword = buyerPassword;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public long getBuyerPhone() {
        return buyerPhone;
    }

    public void setBuyerPhone(long buyerPhone) {
        this.buyerPhone = buyerPhone;
    }

    public String getBuyerEmail() {
        return buyerEmail;
    }

    public void setBuyerEmail(String buyerEmail) {
        this.buyerEmail = buyerEmail;
    }

    @XmlTransient
    public List<Car> getCarList() {
        return carList;
    }

    public void setCarList(List<Car> carList) {
        this.carList = carList;
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
        hash += (cpf != null ? cpf.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Buyer)) {
            return false;
        }
        Buyer other = (Buyer) object;
        if ((this.cpf == null && other.cpf != null) || (this.cpf != null && !this.cpf.equals(other.cpf))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Buyer[ cpf=" + cpf + " ]";
    }
    
}
