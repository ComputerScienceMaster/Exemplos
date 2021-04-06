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
@Table(name = "variable")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Variable.findAll", query = "SELECT v FROM Variable v"),
    @NamedQuery(name = "Variable.findByIdVariable", query = "SELECT v FROM Variable v WHERE v.idVariable = :idVariable"),
    @NamedQuery(name = "Variable.findByVariableName", query = "SELECT v FROM Variable v WHERE v.variableName = :variableName"),
    @NamedQuery(name = "Variable.findByVariableContent", query = "SELECT v FROM Variable v WHERE v.variableContent = :variableContent")})
public class Variable implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idVariable")
    private Integer idVariable;
    @Column(name = "variableName")
    private String variableName;
    @Column(name = "variableContent")
    private String variableContent;
    @JoinColumn(name = "Trial_idTrial", referencedColumnName = "idTrial")
    @ManyToOne(optional = false)
    private Trial trialidTrial;

    public Variable() {
    }

    public Variable(Integer idVariable) {
        this.idVariable = idVariable;
    }

    public Integer getIdVariable() {
        return idVariable;
    }

    public void setIdVariable(Integer idVariable) {
        this.idVariable = idVariable;
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

    public Trial getTrialidTrial() {
        return trialidTrial;
    }

    public void setTrialidTrial(Trial trialidTrial) {
        this.trialidTrial = trialidTrial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idVariable != null ? idVariable.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Variable)) {
            return false;
        }
        Variable other = (Variable) object;
        if ((this.idVariable == null && other.idVariable != null) || (this.idVariable != null && !this.idVariable.equals(other.idVariable))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Variable[ idVariable=" + idVariable + " ]";
    }
    
}
