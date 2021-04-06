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
@Table(name = "model")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Model.findAll", query = "SELECT m FROM Model m"),
    @NamedQuery(name = "Model.findByIdModel", query = "SELECT m FROM Model m WHERE m.idModel = :idModel"),
    @NamedQuery(name = "Model.findByModelName", query = "SELECT m FROM Model m WHERE m.modelName = :modelName")})
public class Model implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_model")
    private Integer idModel;
    @Basic(optional = false)
    @Column(name = "model_name")
    private String modelName;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "model")
    private List<Car> carList;

    public Model() {
    }

    public Model(Integer idModel) {
        this.idModel = idModel;
    }

    public Model(Integer idModel, String modelName) {
        this.idModel = idModel;
        this.modelName = modelName;
    }

    public Integer getIdModel() {
        return idModel;
    }

    public void setIdModel(Integer idModel) {
        this.idModel = idModel;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
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
        hash += (idModel != null ? idModel.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Model)) {
            return false;
        }
        Model other = (Model) object;
        if ((this.idModel == null && other.idModel != null) || (this.idModel != null && !this.idModel.equals(other.idModel))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Model[ idModel=" + idModel + " ]";
    }
    
}
