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
@Table(name = "documentpart")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Documentpart.findAll", query = "SELECT d FROM Documentpart d"),
    @NamedQuery(name = "Documentpart.findByIdDocumentPart", query = "SELECT d FROM Documentpart d WHERE d.idDocumentPart = :idDocumentPart"),
    @NamedQuery(name = "Documentpart.findByTitle", query = "SELECT d FROM Documentpart d WHERE d.title = :title"),
    @NamedQuery(name = "Documentpart.findBySummary", query = "SELECT d FROM Documentpart d WHERE d.summary = :summary")})
public class Documentpart implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idDocumentPart")
    private Integer idDocumentPart;
    @Column(name = "title")
    private String title;
    @Column(name = "summary")
    private String summary;
    @Lob
    @Column(name = "corpus")
    private String corpus;
    @JoinColumn(name = "Part_idPart", referencedColumnName = "idTag")
    @ManyToOne(optional = false)
    private Tag partidPart;

    public Documentpart() {
    }

    public Documentpart(Integer idDocumentPart) {
        this.idDocumentPart = idDocumentPart;
    }

    public Integer getIdDocumentPart() {
        return idDocumentPart;
    }

    public void setIdDocumentPart(Integer idDocumentPart) {
        this.idDocumentPart = idDocumentPart;
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

    public Tag getPartidPart() {
        return partidPart;
    }

    public void setPartidPart(Tag partidPart) {
        this.partidPart = partidPart;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDocumentPart != null ? idDocumentPart.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Documentpart)) {
            return false;
        }
        Documentpart other = (Documentpart) object;
        if ((this.idDocumentPart == null && other.idDocumentPart != null) || (this.idDocumentPart != null && !this.idDocumentPart.equals(other.idDocumentPart))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return title;
    }
    
}
