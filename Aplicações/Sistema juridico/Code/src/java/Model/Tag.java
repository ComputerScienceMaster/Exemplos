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
@Table(name = "tag")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tag.findAll", query = "SELECT t FROM Tag t"),
    @NamedQuery(name = "Tag.findByIdTag", query = "SELECT t FROM Tag t WHERE t.idTag = :idTag"),
    @NamedQuery(name = "Tag.findByTitle", query = "SELECT t FROM Tag t WHERE t.title = :title"),
    @NamedQuery(name = "Tag.findByLevel", query = "SELECT t FROM Tag t WHERE t.level = :level")})
public class Tag implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idTag")
    private Integer idTag;
    @Basic(optional = false)
    @Column(name = "title")
    private String title;
    @Column(name = "level")
    private Integer level;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "partidPart")
    private List<Documentpart> documentpartList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tagidTag")
    private List<Document> documentList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tagidTag")
    private List<Cause> causeList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tagidTag")
    private List<Documentmodel> documentmodelList;
    @OneToMany(mappedBy = "parentId")
    private List<Tag> tagList;
    @JoinColumn(name = "parentId", referencedColumnName = "idTag")
    @ManyToOne
    private Tag parentId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tagidTag")
    private List<Trial> trialList;

    public Tag() {
    }

    public Tag(Integer idTag) {
        this.idTag = idTag;
    }

    public Tag(Integer idTag, String title) {
        this.idTag = idTag;
        this.title = title;
    }

    public Integer getIdTag() {
        return idTag;
    }

    public void setIdTag(Integer idTag) {
        this.idTag = idTag;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @XmlTransient
    public List<Documentpart> getDocumentpartList() {
        return documentpartList;
    }

    public void setDocumentpartList(List<Documentpart> documentpartList) {
        this.documentpartList = documentpartList;
    }

    @XmlTransient
    public List<Document> getDocumentList() {
        return documentList;
    }

    public void setDocumentList(List<Document> documentList) {
        this.documentList = documentList;
    }

    @XmlTransient
    public List<Cause> getCauseList() {
        return causeList;
    }

    public void setCauseList(List<Cause> causeList) {
        this.causeList = causeList;
    }

    @XmlTransient
    public List<Documentmodel> getDocumentmodelList() {
        return documentmodelList;
    }

    public void setDocumentmodelList(List<Documentmodel> documentmodelList) {
        this.documentmodelList = documentmodelList;
    }

    @XmlTransient
    public List<Tag> getTagList() {
        return tagList;
    }

    public void setTagList(List<Tag> tagList) {
        this.tagList = tagList;
    }

    public Tag getParentId() {
        return parentId;
    }

    public void setParentId(Tag parentId) {
        this.parentId = parentId;
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
        hash += (idTag != null ? idTag.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tag)) {
            return false;
        }
        Tag other = (Tag) object;
        if ((this.idTag == null && other.idTag != null) || (this.idTag != null && !this.idTag.equals(other.idTag))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return title;
    }
    
}
