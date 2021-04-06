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
import javax.persistence.Lob;
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
@Table(name = "useraccount")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserAccount.findAll", query = "SELECT u FROM UserAccount u"),
    @NamedQuery(name = "UserAccount.findByIdUserAccount", query = "SELECT u FROM UserAccount u WHERE u.idUserAccount = :idUserAccount"),
    @NamedQuery(name = "UserAccount.findByLoginUser", query = "SELECT u FROM UserAccount u WHERE u.loginUser = :loginUser"),
    @NamedQuery(name = "UserAccount.findByPasswordUser", query = "SELECT u FROM UserAccount u WHERE u.passwordUser = :passwordUser"),
    @NamedQuery(name = "UserAccount.findByNameUser", query = "SELECT u FROM UserAccount u WHERE u.nameUser = :nameUser"),
    @NamedQuery(name = "UserAccount.findByLastNameUser", query = "SELECT u FROM UserAccount u WHERE u.lastNameUser = :lastNameUser"),
    @NamedQuery(name = "UserAccount.findByEmailUser", query = "SELECT u FROM UserAccount u WHERE u.emailUser = :emailUser")})
public class UserAccount implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idUserAccount")
    private Integer idUserAccount;
    @Basic(optional = false)
    @Column(name = "loginUser", unique = true)
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "useraccount")
    private List<Ratings> ratingsList;

    public UserAccount() {
    }

    public UserAccount(Integer idUserAccount) {
        this.idUserAccount = idUserAccount;
    }

    public UserAccount(Integer idUserAccount, String loginUser, String passwordUser, String nameUser, String emailUser) {
        this.idUserAccount = idUserAccount;
        this.loginUser = loginUser;
        this.passwordUser = passwordUser;
        this.nameUser = nameUser;
        this.emailUser = emailUser;
    }

    public Integer getIdUserAccount() {
        return idUserAccount;
    }

    public void setIdUserAccount(Integer idUserAccount) {
        this.idUserAccount = idUserAccount;
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

    @XmlTransient
    public List<Ratings> getRatingsList() {
        return ratingsList;
    }

    public void setRatingsList(List<Ratings> ratingsList) {
        this.ratingsList = ratingsList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUserAccount != null ? idUserAccount.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserAccount)) {
            return false;
        }
        UserAccount other = (UserAccount) object;
        if ((this.idUserAccount == null && other.idUserAccount != null) || (this.idUserAccount != null && !this.idUserAccount.equals(other.idUserAccount))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.UserAccount[ idUserAccount=" + idUserAccount + " ]";
    }
    
}
