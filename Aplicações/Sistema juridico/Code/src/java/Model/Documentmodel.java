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
import javax.persistence.Lob;
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
@Table(name = "documentmodel")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Documentmodel.findAll", query = "SELECT d FROM Documentmodel d"),
    @NamedQuery(name = "Documentmodel.findByIdDocumentModel", query = "SELECT d FROM Documentmodel d WHERE d.idDocumentModel = :idDocumentModel"),
    @NamedQuery(name = "Documentmodel.findByTitle", query = "SELECT d FROM Documentmodel d WHERE d.title = :title"),
    @NamedQuery(name = "Documentmodel.findBySummary", query = "SELECT d FROM Documentmodel d WHERE d.summary = :summary")})
public class Documentmodel implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idDocumentModel")
    private Integer idDocumentModel;
    @Column(name = "title")
    private String title;
    @Column(name = "summary")
    private String summary;
    @Lob
    @Column(name = "corpus")
    private String corpus;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "documentModelidDocumentModel")
    private List<Document> documentList;
    @JoinColumn(name = "Tag_idTag", referencedColumnName = "idTag")
    @ManyToOne(optional = false)
    private Tag tagidTag;

    public Documentmodel() {
    }

    public Documentmodel(Integer idDocumentModel) {
        this.idDocumentModel = idDocumentModel;
    }

    public Integer getIdDocumentModel() {
        return idDocumentModel;
    }

    public void setIdDocumentModel(Integer idDocumentModel) {
        this.idDocumentModel = idDocumentModel;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getCorpus() {
        return corpus;
    }

    public void setCorpus(String corpus) {
        this.corpus = corpus;
    }

    @XmlTransient
    public List<Document> getDocumentList() {
        return documentList;
    }

    public void setDocumentList(List<Document> documentList) {
        this.documentList = documentList;
    }

    public Tag getTagidTag() {
        return tagidTag;
    }

    public void setTagidTag(Tag tagidTag) {
        this.tagidTag = tagidTag;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDocumentModel != null ? idDocumentModel.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Documentmodel)) {
            return false;
        }
        Documentmodel other = (Documentmodel) object;
        if ((this.idDocumentModel == null && other.idDocumentModel != null) || (this.idDocumentModel != null && !this.idDocumentModel.equals(other.idDocumentModel))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return title;
    }
    
}
