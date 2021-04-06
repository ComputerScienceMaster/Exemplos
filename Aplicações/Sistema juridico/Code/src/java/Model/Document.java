/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;
import javax.persistence.Basic;
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
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author USER
 */
@Entity
@Table(name = "document")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Document.findAll", query = "SELECT d FROM Document d"),
    @NamedQuery(name = "Document.findByIdDocument", query = "SELECT d FROM Document d WHERE d.idDocument = :idDocument"),
    @NamedQuery(name = "Document.findByTitle", query = "SELECT d FROM Document d WHERE d.title = :title"),
    @NamedQuery(name = "Document.findBySummary", query = "SELECT d FROM Document d WHERE d.summary = :summary"),
    @NamedQuery(name = "Document.findByCompanycnpj", query = "SELECT d FROM Document d WHERE d.companycnpj = :companycnpj")})
public class Document implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idDocument")
    private Integer idDocument;
    @Column(name = "title")
    private String title;
    @Column(name = "summary")
    private String summary;
    @Lob
    @Column(name = "Corpus")
    private String corpus;
    @Column(name = "Company_cnpj")
    private String companycnpj;
    @JoinColumn(name = "Client_cpfRegister", referencedColumnName = "cpfRegister")
    @ManyToOne
    private Client clientcpfRegister;
    @JoinColumn(name = "DocumentModel_idDocumentModel", referencedColumnName = "idDocumentModel")
    @ManyToOne(optional = false)
    private Documentmodel documentModelidDocumentModel;
    @JoinColumn(name = "Tag_idTag", referencedColumnName = "idTag")
    @ManyToOne(optional = false)
    private Tag tagidTag;

    public Document() {
    }

    public Document(Integer idDocument) {
        this.idDocument = idDocument;
    }

    public Integer getIdDocument() {
        return idDocument;
    }

    public void setIdDocument(Integer idDocument) {
        this.idDocument = idDocument;
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

    public String getCompanycnpj() {
        return companycnpj;
    }

    public void setCompanycnpj(String companycnpj) {
        this.companycnpj = companycnpj;
    }

    public Client getClientcpfRegister() {
        return clientcpfRegister;
    }

    public void setClientcpfRegister(Client clientcpfRegister) {
        this.clientcpfRegister = clientcpfRegister;
    }

    public Documentmodel getDocumentModelidDocumentModel() {
        return documentModelidDocumentModel;
    }

    public void setDocumentModelidDocumentModel(Documentmodel documentModelidDocumentModel) {
        this.documentModelidDocumentModel = documentModelidDocumentModel;
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
        hash += (idDocument != null ? idDocument.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Document)) {
            return false;
        }
        Document other = (Document) object;
        if ((this.idDocument == null && other.idDocument != null) || (this.idDocument != null && !this.idDocument.equals(other.idDocument))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return title;
    }
    
}
