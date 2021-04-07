package Model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "administrator")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Administrator.findAll", query = "SELECT a FROM Administrator a"),
    @NamedQuery(name = "Administrator.findByLoginAdministrator", query = "SELECT a FROM Administrator a WHERE a.loginAdministrator = :loginAdministrator"),
    @NamedQuery(name = "Administrator.findByPasswordAdministrator", query = "SELECT a FROM Administrator a WHERE a.passwordAdministrator = :passwordAdministrator")})
public class Administrator implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "loginAdministrator")
    private String loginAdministrator;
    @Basic(optional = false)
    @Column(name = "PasswordAdministrator")
    private String passwordAdministrator;

    public Administrator() {
    }

    public Administrator(String loginAdministrator) {
        this.loginAdministrator = loginAdministrator;
    }

    public Administrator(String loginAdministrator, String passwordAdministrator) {
        this.loginAdministrator = loginAdministrator;
        this.passwordAdministrator = passwordAdministrator;
    }

    public String getLoginAdministrator() {
        return loginAdministrator;
    }

    public void setLoginAdministrator(String loginAdministrator) {
        this.loginAdministrator = loginAdministrator;
    }

    public String getPasswordAdministrator() {
        return passwordAdministrator;
    }

    public void setPasswordAdministrator(String passwordAdministrator) {
        this.passwordAdministrator = passwordAdministrator;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (loginAdministrator != null ? loginAdministrator.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Administrator)) {
            return false;
        }
        Administrator other = (Administrator) object;
        if ((this.loginAdministrator == null && other.loginAdministrator != null) || (this.loginAdministrator != null && !this.loginAdministrator.equals(other.loginAdministrator))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return loginAdministrator;
    }
    
}
