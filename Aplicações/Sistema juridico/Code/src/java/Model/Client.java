/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author USER
 */
@Entity
@Table(name = "client")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Client.findAll", query = "SELECT c FROM Client c"),
    @NamedQuery(name = "Client.findByCpfRegister", query = "SELECT c FROM Client c WHERE c.cpfRegister = :cpfRegister"),
    @NamedQuery(name = "Client.findByFullName", query = "SELECT c FROM Client c WHERE c.fullName = :fullName"),
    @NamedQuery(name = "Client.findByNationality", query = "SELECT c FROM Client c WHERE c.nationality = :nationality"),
    @NamedQuery(name = "Client.findByBirthDate", query = "SELECT c FROM Client c WHERE c.birthDate = :birthDate"),
    @NamedQuery(name = "Client.findByMaritalStatus", query = "SELECT c FROM Client c WHERE c.maritalStatus = :maritalStatus"),
    @NamedQuery(name = "Client.findByJob", query = "SELECT c FROM Client c WHERE c.job = :job"),
    @NamedQuery(name = "Client.findByCtps", query = "SELECT c FROM Client c WHERE c.ctps = :ctps"),
    @NamedQuery(name = "Client.findByGeneralRegister", query = "SELECT c FROM Client c WHERE c.generalRegister = :generalRegister"),
    @NamedQuery(name = "Client.findByRgState", query = "SELECT c FROM Client c WHERE c.rgState = :rgState"),
    @NamedQuery(name = "Client.findByRgEmiter", query = "SELECT c FROM Client c WHERE c.rgEmiter = :rgEmiter"),
    @NamedQuery(name = "Client.findByDistrict", query = "SELECT c FROM Client c WHERE c.district = :district"),
    @NamedQuery(name = "Client.findByCep", query = "SELECT c FROM Client c WHERE c.cep = :cep"),
    @NamedQuery(name = "Client.findByAddress1", query = "SELECT c FROM Client c WHERE c.address1 = :address1"),
    @NamedQuery(name = "Client.findByAddress2", query = "SELECT c FROM Client c WHERE c.address2 = :address2"),
    @NamedQuery(name = "Client.findByCity", query = "SELECT c FROM Client c WHERE c.city = :city"),
    @NamedQuery(name = "Client.findByState", query = "SELECT c FROM Client c WHERE c.state = :state"),
    @NamedQuery(name = "Client.findByCounty", query = "SELECT c FROM Client c WHERE c.county = :county"),
    @NamedQuery(name = "Client.findByVotersTitle", query = "SELECT c FROM Client c WHERE c.votersTitle = :votersTitle"),
    @NamedQuery(name = "Client.findByPhone", query = "SELECT c FROM Client c WHERE c.phone = :phone"),
    @NamedQuery(name = "Client.findByEmail", query = "SELECT c FROM Client c WHERE c.email = :email")})
public class Client implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "cpfRegister")
    private String cpfRegister;
    @Column(name = "fullName")
    private String fullName;
    @Column(name = "nationality")
    private String nationality;
    @Column(name = "birthDate")
    @Temporal(TemporalType.DATE)
    private Date birthDate;
    @Column(name = "MaritalStatus")
    private String maritalStatus;
    @Column(name = "job")
    private String job;
    @Column(name = "ctps")
    private String ctps;
    @Column(name = "generalRegister")
    private String generalRegister;
    @Column(name = "rgState")
    private String rgState;
    @Column(name = "rgEmiter")
    private String rgEmiter;
    @Column(name = "district")
    private String district;
    @Column(name = "CEP")
    private String cep;
    @Column(name = "Address1")
    private String address1;
    @Column(name = "Address2")
    private String address2;
    @Column(name = "city")
    private String city;
    @Column(name = "state")
    private String state;
    @Column(name = "county")
    private String county;
    @Column(name = "votersTitle")
    private String votersTitle;
    @Column(name = "phone")
    private String phone;
    @Column(name = "email")
    private String email;
    @OneToMany(mappedBy = "clientcpfRegister")
    private List<Document> documentList;
    @JoinColumn(name = "Admin_loginUser", referencedColumnName = "loginUser")
    @ManyToOne(optional = false)
    private Admin adminloginUser;

    public Client() {
    }

    public Client(String cpfRegister) {
        this.cpfRegister = cpfRegister;
    }

    public String getCpfRegister() {
        return cpfRegister;
    }

    public void setCpfRegister(String cpfRegister) {
        this.cpfRegister = cpfRegister;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getCtps() {
        return ctps;
    }

    public void setCtps(String ctps) {
        this.ctps = ctps;
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

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
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

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getVotersTitle() {
        return votersTitle;
    }

    public void setVotersTitle(String votersTitle) {
        this.votersTitle = votersTitle;
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

    @XmlTransient
    public List<Document> getDocumentList() {
        return documentList;
    }

    public void setDocumentList(List<Document> documentList) {
        this.documentList = documentList;
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
        hash += (cpfRegister != null ? cpfRegister.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Client)) {
            return false;
        }
        Client other = (Client) object;
        if ((this.cpfRegister == null && other.cpfRegister != null) || (this.cpfRegister != null && !this.cpfRegister.equals(other.cpfRegister))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return fullName;
    }
    
}
