
package Model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;


@Entity
@Table(name = "UserAccount")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserAccount.findAll", query = "SELECT u FROM UserAccount u"),
    @NamedQuery(name = "UserAccount.findByLoginUser", query = "SELECT u FROM UserAccount u WHERE u.loginUser = :loginUser"),
    @NamedQuery(name = "UserAccount.findByPasswordUser", query = "SELECT u FROM UserAccount u WHERE u.passwordUser = :passwordUser"),
    @NamedQuery(name = "UserAccount.findByNameUser", query = "SELECT u FROM UserAccount u WHERE u.nameUser = :nameUser"),
    @NamedQuery(name = "UserAccount.findByLastNameUser", query = "SELECT u FROM UserAccount u WHERE u.lastNameUser = :lastNameUser"),
    @NamedQuery(name = "UserAccount.findByEmailUser", query = "SELECT u FROM UserAccount u WHERE u.emailUser = :emailUser")})
public class UserAccount implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "loginUser")
    private String loginUser;
    @Basic(optional = false)
    @Column(name = "passwordUser")
    private String passwordUser;
    @Basic(optional = false)
    @Column(name = "nameUser")
    private String nameUser;
    @Column(name = "lastNameUser")
    private String lastNameUser;
    @Basic(optional = false)
    @Column(name = "emailUser")
    private String emailUser;
    @Lob
    @Column(name = "imageUser")
    private byte[] imageUser;

    public UserAccount() {
    }

    public UserAccount(String loginUser) {
        this.loginUser = loginUser;
    }

    public UserAccount(String loginUser, String passwordUser, String nameUser, String emailUser) {
        this.loginUser = loginUser;
        this.passwordUser = passwordUser;
        this.nameUser = nameUser;
        this.emailUser = emailUser;
    }

    public String getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(String loginUser) {
        this.loginUser = loginUser;
    }

    public String getPasswordUser() {
        return passwordUser;
    }

    public void setPasswordUser(String passwordUser) {
        this.passwordUser = passwordUser;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getLastNameUser() {
        return lastNameUser;
    }

    public void setLastNameUser(String lastNameUser) {
        this.lastNameUser = lastNameUser;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    public byte[] getImageUser() {
        return imageUser;
    }

    public void setImageUser(byte[] imageUser) {
        this.imageUser = imageUser;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (loginUser != null ? loginUser.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserAccount)) {
            return false;
        }
        UserAccount other = (UserAccount) object;
        if ((this.loginUser == null && other.loginUser != null) || (this.loginUser != null && !this.loginUser.equals(other.loginUser))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nameUser;
    }
    
}
