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
@Table(name = "causevariable")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Causevariable.findAll", query = "SELECT c FROM Causevariable c"),
    @NamedQuery(name = "Causevariable.findByIdCauseVariable", query = "SELECT c FROM Causevariable c WHERE c.idCauseVariable = :idCauseVariable"),
    @NamedQuery(name = "Causevariable.findByVariableName", query = "SELECT c FROM Causevariable c WHERE c.variableName = :variableName"),
    @NamedQuery(name = "Causevariable.findByVariableContent", query = "SELECT c FROM Causevariable c WHERE c.variableContent = :variableContent")})
public class Causevariable implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idCauseVariable")
    private Integer idCauseVariable;
    @Basic(optional = false)
    @Column(name = "variableName")
    private String variableName;
    @Basic(optional = false)
    @Column(name = "variableContent")
    private String variableContent;
    @JoinColumn(name = "Cause_idCause", referencedColumnName = "idCause")
    @ManyToOne(optional = false)
    private Cause causeidCause;

    public Causevariable() {
    }

    public Causevariable(Integer idCauseVariable) {
        this.idCauseVariable = idCauseVariable;
    }

    public Causevariable(Integer idCauseVariable, String variableName, String variableContent) {
        this.idCauseVariable = idCauseVariable;
        this.variableName = variableName;
        this.variableContent = variableContent;
    }

    public Integer getIdCauseVariable() {
        return idCauseVariable;
    }

    public void setIdCauseVariable(Integer idCauseVariable) {
        this.idCauseVariable = idCauseVariable;
    }

    public String getVariableName() {
        return variableName;
    }

    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }

    public String getVariableContent() {
        return variableContent;
    }

    public void setVariableContent(String variableContent) {
        this.variableContent = variableContent;
    }

    public Cause getCauseidCause() {
        return causeidCause;
    }

    public void setCauseidCause(Cause causeidCause) {
        this.causeidCause = causeidCause;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCauseVariable != null ? idCauseVariable.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Causevariable)) {
            return false;
        }
        Causevariable other = (Causevariable) object;
        if ((this.idCauseVariable == null && other.idCauseVariable != null) || (this.idCauseVariable != null && !this.idCauseVariable.equals(other.idCauseVariable))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return variableName;
    }
    
}
