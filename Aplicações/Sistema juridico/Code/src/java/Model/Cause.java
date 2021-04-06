/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

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
 * @author USER
 */
@Entity
@Table(name = "cause")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cause.findAll", query = "SELECT c FROM Cause c"),
    @NamedQuery(name = "Cause.findByIdCause", query = "SELECT c FROM Cause c WHERE c.idCause = :idCause"),
    @NamedQuery(name = "Cause.findByName", query = "SELECT c FROM Cause c WHERE c.name = :name")})
public class Cause implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idCause")
    private Integer idCause;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "causeidCause")
    private List<Causevariable> causevariableList;
    @JoinColumn(name = "Tag_idTag", referencedColumnName = "idTag")
    @ManyToOne(optional = false)
    private Tag tagidTag;
    @JoinColumn(name = "Admin_loginUser", referencedColumnName = "loginUser")
    @ManyToOne(optional = false)
    private Admin adminloginUser;

    public Cause() {
    }

    public Cause(Integer idCause) {
        this.idCause = idCause;
    }

    public Cause(Integer idCause, String name) {
        this.idCause = idCause;
        this.name = name;
    }

    public Integer getIdCause() {
        return idCause;
    }

    public void setIdCause(Integer idCause) {
        this.idCause = idCause;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlTransient
    public List<Causevariable> getCausevariableList() {
        return causevariableList;
    }

    public void setCausevariableList(List<Causevariable> causevariableList) {
        this.causevariableList = causevariableList;
    }

    public Tag getTagidTag() {
        return tagidTag;
    }

    public void setTagidTag(Tag tagidTag) {
        this.tagidTag = tagidTag;
    }

    public Admin getAdminloginUser() {
        return adminloginUser;
    }

    public void setAdminloginUser(Admin adminloginUser) {
        this.adminloginUser = adminloginUser;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCause != null ? idCause.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cause)) {
            return false;
        }
        Cause other = (Cause) object;
        if ((this.idCause == null && other.idCause != null) || (this.idCause != null && !this.idCause.equals(other.idCause))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return name;
    }
    
}
