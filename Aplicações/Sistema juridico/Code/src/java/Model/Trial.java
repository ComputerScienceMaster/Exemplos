
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


@Entity
@Table(name = "trial")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Trial.findAll", query = "SELECT t FROM Trial t"),
    @NamedQuery(name = "Trial.findByIdTrial", query = "SELECT t FROM Trial t WHERE t.idTrial = :idTrial"),
    @NamedQuery(name = "Trial.findByName", query = "SELECT t FROM Trial t WHERE t.name = :name")})
public class Trial implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idTrial")
    private Integer idTrial;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "trialidTrial")
    private List<Variable> variableList;
    @JoinColumn(name = "Tag_idTag", referencedColumnName = "idTag")
    @ManyToOne(optional = false)
    private Tag tagidTag;
    @JoinColumn(name = "User_loginUser", referencedColumnName = "loginUser")
    @ManyToOne(optional = false)
    private Admin userloginUser;

    public Trial() {
    }

    public Trial(Integer idTrial) {
        this.idTrial = idTrial;
    }

    public Trial(Integer idTrial, String name) {
        this.idTrial = idTrial;
        this.name = name;
    }

    public Integer getIdTrial() {
        return idTrial;
    }

    public void setIdTrial(Integer idTrial) {
        this.idTrial = idTrial;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlTransient
    public List<Variable> getVariableList() {
        return variableList;
    }

    public void setVariableList(List<Variable> variableList) {
        this.variableList = variableList;
    }

    public Tag getTagidTag() {
        return tagidTag;
    }

    public void setTagidTag(Tag tagidTag) {
        this.tagidTag = tagidTag;
    }

    public Admin getUserloginUser() {
        return userloginUser;
    }

    public void setUserloginUser(Admin userloginUser) {
        this.userloginUser = userloginUser;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTrial != null ? idTrial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Trial)) {
            return false;
        }
        Trial other = (Trial) object;
        if ((this.idTrial == null && other.idTrial != null) || (this.idTrial != null && !this.idTrial.equals(other.idTrial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return name;
    }
    
}
