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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
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
@Table(name = "user_account")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserAccount.findAll", query = "SELECT u FROM UserAccount u"),
    @NamedQuery(name = "UserAccount.findByUserLogin", query = "SELECT u FROM UserAccount u WHERE u.userLogin = :userLogin"),
    @NamedQuery(name = "UserAccount.findByPassword", query = "SELECT u FROM UserAccount u WHERE u.password = :password"),
    @NamedQuery(name = "UserAccount.findByUserName", query = "SELECT u FROM UserAccount u WHERE u.userName = :userName"),
    @NamedQuery(name = "UserAccount.findByPhone", query = "SELECT u FROM UserAccount u WHERE u.phone = :phone"),
    @NamedQuery(name = "UserAccount.findByEmail", query = "SELECT u FROM UserAccount u WHERE u.email = :email"),
    @NamedQuery(name = "UserAccount.findByIsAdministrator", query = "SELECT u FROM UserAccount u WHERE u.isAdministrator = :isAdministrator")})
public class UserAccount implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "userLogin")
    private String userLogin;
    @Basic(optional = false)
    @Column(name = "password")
    private String password;
    @Basic(optional = false)
    @Column(name = "userName")
    private String userName;
    @Basic(optional = false)
    @Column(name = "phone")
    private String phone;
    @Basic(optional = false)
    @Column(name = "email")
    private String email;
    @Column(name = "isAdministrator")
    private Boolean isAdministrator;
    @ManyToMany(mappedBy = "userAccountList")
    private List<Travel> travelList;
    @OneToMany(mappedBy = "useraccountuserLogin")
    private List<Task> taskList;
    @JoinColumn(name = "Sector_idSector", referencedColumnName = "idSector")
    @ManyToOne(optional = false)
    private Sector sectoridSector;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<Travel> travelList1;

    public UserAccount() {
    }

    public UserAccount(String userLogin) {
        this.userLogin = userLogin;
    }

    public UserAccount(String userLogin, String password, String userName, String phone, String email) {
        this.userLogin = userLogin;
        this.password = password;
        this.userName = userName;
        this.phone = phone;
        this.email = email;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getIsAdministrator() {
        return isAdministrator;
    }

    public void setIsAdministrator(Boolean isAdministrator) {
        this.isAdministrator = isAdministrator;
    }

    @XmlTransient
    public List<Travel> getTravelList() {
        return travelList;
    }

    public void setTravelList(List<Travel> travelList) {
        this.travelList = travelList;
    }

    @XmlTransient
    public List<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }

    public Sector getSectoridSector() {
        return sectoridSector;
    }

    public void setSectoridSector(Sector sectoridSector) {
        this.sectoridSector = sectoridSector;
    }

    @XmlTransient
    public List<Travel> getTravelList1() {
        return travelList1;
    }

    public void setTravelList1(List<Travel> travelList1) {
        this.travelList1 = travelList1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userLogin != null ? userLogin.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserAccount)) {
            return false;
        }
        UserAccount other = (UserAccount) object;
        if ((this.userLogin == null && other.userLogin != null) || (this.userLogin != null && !this.userLogin.equals(other.userLogin))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.UserAccount[ userLogin=" + userLogin + " ]";
    }
    
}
