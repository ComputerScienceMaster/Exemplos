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
@Table(name = "color")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Color.findAll", query = "SELECT c FROM Color c"),
    @NamedQuery(name = "Color.findByIdColor", query = "SELECT c FROM Color c WHERE c.idColor = :idColor"),
    @NamedQuery(name = "Color.findByColorName", query = "SELECT c FROM Color c WHERE c.colorName = :colorName")})
public class Color implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_color")
    private Integer idColor;
    @Basic(optional = false)
    @Column(name = "color_name")
    private String colorName;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "color")
    private List<Car> carList;

    public Color() {
    }

    public Color(Integer idColor) {
        this.idColor = idColor;
    }

    public Color(Integer idColor, String colorName) {
        this.idColor = idColor;
        this.colorName = colorName;
    }

    public Integer getIdColor() {
        return idColor;
    }

    public void setIdColor(Integer idColor) {
        this.idColor = idColor;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
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
        hash += (idColor != null ? idColor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Color)) {
            return false;
        }
        Color other = (Color) object;
        if ((this.idColor == null && other.idColor != null) || (this.idColor != null && !this.idColor.equals(other.idColor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Color[ idColor=" + idColor + " ]";
    }
    
}
