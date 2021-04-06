
package Model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "admin")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Admin.findAll", query = "SELECT a FROM Admin a"),
    @NamedQuery(name = "Admin.findByLoginUser", query = "SELECT a FROM Admin a WHERE a.loginUser = :loginUser"),
    @NamedQuery(name = "Admin.findByPasswordUser", query = "SELECT a FROM Admin a WHERE a.passwordUser = :passwordUser"),
    @NamedQuery(name = "Admin.findByFullNameUser", query = "SELECT a FROM Admin a WHERE a.fullNameUser = :fullNameUser"),
    @NamedQuery(name = "Admin.findByGeneralRegister", query = "SELECT a FROM Admin a WHERE a.generalRegister = :generalRegister"),
    @NamedQuery(name = "Admin.findByRgState", query = "SELECT a FROM Admin a WHERE a.rgState = :rgState"),
    @NamedQuery(name = "Admin.findByRgEmiter", query = "SELECT a FROM Admin a WHERE a.rgEmiter = :rgEmiter"),
    @NamedQuery(name = "Admin.findByEmailUser", query = "SELECT a FROM Admin a WHERE a.emailUser = :emailUser"),
    @NamedQuery(name = "Admin.findByOab", query = "SELECT a FROM Admin a WHERE a.oab = :oab"),
    @NamedQuery(name = "Admin.findByMaritalStatus", query = "SELECT a FROM Admin a WHERE a.maritalStatus = :maritalStatus"),
    @NamedQuery(name = "Admin.findByPhone", query = "SELECT a FROM Admin a WHERE a.phone = :phone"),
    @NamedQuery(name = "Admin.findByBirthDate", query = "SELECT a FROM Admin a WHERE a.birthDate = :birthDate"),
    @NamedQuery(name = "Admin.findByCep", query = "SELECT a FROM Admin a WHERE a.cep = :cep"),
    @NamedQuery(name = "Admin.findByCity", query = "SELECT a FROM Admin a WHERE a.city = :city"),
    @NamedQuery(name = "Admin.findByState", query = "SELECT a FROM Admin a WHERE a.state = :state"),
    @NamedQuery(name = "Admin.findByAddress1", query = "SELECT a FROM Admin a WHERE a.address1 = :address1"),
    @NamedQuery(name = "Admin.findByAddress2", query = "SELECT a FROM Admin a WHERE a.address2 = :address2"),
    @NamedQuery(name = "Admin.findByComplement", query = "SELECT a FROM Admin a WHERE a.complement = :complement"),
    @NamedQuery(name = "Admin.findByNationality", query = "SELECT a FROM Admin a WHERE a.nationality = :nationality")})
public class Admin implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "loginUser")
    private String loginUser;
    @Basic(optional = false)
    @Column(name = "passwordUser")
    private String passwordUser;
    @Basic(optional = false)
    @Column(name = "fullNameUser")
    private String fullNameUser;
    @Column(name = "generalRegister")
    private String generalRegister;
    @Column(name = "rgState")
    private String rgState;
    @Column(name = "rgEmiter")
    private String rgEmiter;
    @Basic(optional = false)
    @Column(name = "emailUser")
    private String emailUser;
    @Column(name = "oab")
    private String oab;
    @Column(name = "maritalStatus")
    private String maritalStatus;
    @Column(name = "phone")
    private String phone;
    @Column(name = "birthDate")
    @Temporal(TemporalType.DATE)
    private Date birthDate;
    @Column(name = "CEP")
    private String cep;
    @Column(name = "city")
    private String city;
    @Column(name = "state")
    private String state;
    @Column(name = "address1")
    private String address1;
    @Column(name = "address2")
    private String address2;
    @Column(name = "complement")
    private String complement;
    @Column(name = "nationality")
    private String nationality;
    @Lob
    @Column(name = "imageUser")
    private byte[] imageUser;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "adminloginUser")
    private List<Cause> causeList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "adminloginUser")
    private List<Client> clientList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "adminloginUser")
    private List<Company> companyList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userloginUser")
    private List<Trial> trialList;

    public Admin() {
    }

    public Admin(String loginUser) {
        this.loginUser = loginUser;
    }

    public Admin(String loginUser, String passwordUser, String fullNameUser, String emailUser) {
        this.loginUser = loginUser;
        this.passwordUser = passwordUser;
        this.fullNameUser = fullNameUser;
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

    public String getFullNameUser() {
        return fullNameUser;
    }

    public void setFullNameUser(String fullNameUser) {
        this.fullNameUser = fullNameUser;
    }

    public String getGeneralRegister() {
        return generalRegister;
    }

    public void setGeneralRegister(String generalRegister) {
        this.generalRegister = generalRegister;
    }

    public String getRgState() {
        return rgState;
    }

    public void setRgState(String rgState) {
        this.rgState = rgState;
    }

    public String getRgEmiter() {
        return rgEmiter;
    }

    public void setRgEmiter(String rgEmiter) {
        this.rgEmiter = rgEmiter;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    public String getOab() {
        return oab;
    }

    public void setOab(String oab) {
        this.oab = oab;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public byte[] getImageUser() {
        return imageUser;
    }

    public void setImageUser(byte[] imageUser) {
        this.imageUser = imageUser;
    }

    @XmlTransient
    public List<Cause> getCauseList() {
        return causeList;
    }

    public void setCauseList(List<Cause> causeList) {
        this.causeList = causeList;
    }

    @XmlTransient
    public List<Client> getClientList() {
        return clientList;
    }

    public void setClientList(List<Client> clientList) {
        this.clientList = clientList;
    }

    @XmlTransient
    public List<Company> getCompanyList() {
        return companyList;
    }

    public void setCompanyList(List<Company> companyList) {
        this.companyList = companyList;
    }

    @XmlTransient
    public List<Trial> getTrialList() {
        return trialList;
    }

    public void setTrialList(List<Trial> trialList) {
        this.trialList = trialList;
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
        if (!(object instanceof Admin)) {
            return false;
        }
        Admin other = (Admin) object;
        if ((this.loginUser == null && other.loginUser != null) || (this.loginUser != null && !this.loginUser.equals(other.loginUser))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return loginUser;
    }
    
}
