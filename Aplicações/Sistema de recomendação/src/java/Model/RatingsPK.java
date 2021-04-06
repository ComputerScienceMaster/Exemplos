/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author USER
 */
@Embeddable
public class RatingsPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "idBook")
    private int idBook;
    @Basic(optional = false)
    @Column(name = "idUserAccount")
    private int idUserAccount;

    public RatingsPK() {
    }

    public RatingsPK(int idBook, int idUserAccount) {
        this.idBook = idBook;
        this.idUserAccount = idUserAccount;
    }

    public int getIdBook() {
        return idBook;
    }

    public void setIdBook(int idBook) {
        this.idBook = idBook;
    }

    public int getIdUserAccount() {
        return idUserAccount;
    }

    public void setIdUserAccount(int idUserAccount) {
        this.idUserAccount = idUserAccount;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idBook;
        hash += (int) idUserAccount;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RatingsPK)) {
            return false;
        }
        RatingsPK other = (RatingsPK) object;
        if (this.idBook != other.idBook) {
            return false;
        }
        if (this.idUserAccount != other.idUserAccount) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.RatingsPK[ idBook=" + idBook + ", idUserAccount=" + idUserAccount + " ]";
    }
    
}
