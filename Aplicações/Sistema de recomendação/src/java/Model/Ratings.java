/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
@Table(name = "ratings")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ratings.findAll", query = "SELECT r FROM Ratings r"),
    @NamedQuery(name = "Ratings.findByIdBook", query = "SELECT r FROM Ratings r WHERE r.ratingsPK.idBook = :idBook"),
    @NamedQuery(name = "Ratings.findByIdUserAccount", query = "SELECT r FROM Ratings r WHERE r.ratingsPK.idUserAccount = :idUserAccount"),
    @NamedQuery(name = "Ratings.findByStars", query = "SELECT r FROM Ratings r WHERE r.stars = :stars")})
public class Ratings implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected RatingsPK ratingsPK;
    @Column(name = "stars")
    private Integer stars;
    @JoinColumn(name = "idBook", referencedColumnName = "idBook", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Book book;
    @JoinColumn(name = "idUserAccount", referencedColumnName = "idUserAccount", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private UserAccount useraccount;

    public Ratings() {
    }

    public Ratings(RatingsPK ratingsPK) {
        this.ratingsPK = ratingsPK;
    }

    public Ratings(int idBook, int idUserAccount) {
        this.ratingsPK = new RatingsPK(idBook, idUserAccount);
    }

    public RatingsPK getRatingsPK() {
        return ratingsPK;
    }

    public void setRatingsPK(RatingsPK ratingsPK) {
        this.ratingsPK = ratingsPK;
    }

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public UserAccount getUseraccount() {
        return useraccount;
    }

    public void setUseraccount(UserAccount useraccount) {
        this.useraccount = useraccount;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ratingsPK != null ? ratingsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ratings)) {
            return false;
        }
        Ratings other = (Ratings) object;
        if ((this.ratingsPK == null && other.ratingsPK != null) || (this.ratingsPK != null && !this.ratingsPK.equals(other.ratingsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Ratings[ ratingsPK=" + ratingsPK + " ]";
    }
    
}
