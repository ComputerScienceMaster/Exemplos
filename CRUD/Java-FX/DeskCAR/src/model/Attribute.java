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
@Table(name = "attribute")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Attribute.findAll", query = "SELECT a FROM Attribute a"),
    @NamedQuery(name = "Attribute.findByIdAtributo", query = "SELECT a FROM Attribute a WHERE a.idAtributo = :idAtributo"),
    @NamedQuery(name = "Attribute.findByAttributeName", query = "SELECT a FROM Attribute a WHERE a.attributeName = :attributeName"),
    @NamedQuery(name = "Attribute.findByDescription", query = "SELECT a FROM Attribute a WHERE a.description = :description")})
public class Attribute implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_atributo")
    private Integer idAtributo;
    @Basic(optional = false)
    @Column(name = "attribute_name")
    private String attributeName;
    @Basic(optional = false)
    @Column(name = "description")
    private String description;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "attribute")
    private List<Car> carList;

    public Attribute() {
    }

    public Attribute(Integer idAtributo) {
        this.idAtributo = idAtributo;
    }

    public Attribute(Integer idAtributo, String attributeName, String description) {
        this.idAtributo = idAtributo;
        this.attributeName = attributeName;
        this.description = description;
    }

    public Integer getIdAtributo() {
        return idAtributo;
    }

    public void setIdAtributo(Integer idAtributo) {
        this.idAtributo = idAtributo;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        hash += (idAtributo != null ? idAtributo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Attribute)) {
            return false;
        }
        Attribute other = (Attribute) object;
        if ((this.idAtributo == null && other.idAtributo != null) || (this.idAtributo != null && !this.idAtributo.equals(other.idAtributo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Attribute[ idAtributo=" + idAtributo + " ]";
    }
    
}
