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
@Table(name = "book")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Book.findAll", query = "SELECT b FROM Book b"),
    @NamedQuery(name = "Book.findByIdBook", query = "SELECT b FROM Book b WHERE b.idBook = :idBook"),
    @NamedQuery(name = "Book.findByNameBook", query = "SELECT b FROM Book b WHERE b.nameBook = :nameBook"),
    @NamedQuery(name = "Book.findByAuthorBook", query = "SELECT b FROM Book b WHERE b.authorBook = :authorBook"),
    @NamedQuery(name = "Book.findByAbstractBook", query = "SELECT b FROM Book b WHERE b.abstractBook = :abstractBook"),
    @NamedQuery(name = "Book.findByPagesBook", query = "SELECT b FROM Book b WHERE b.pagesBook = :pagesBook"),
    @NamedQuery(name = "Book.findByPublisherBook", query = "SELECT b FROM Book b WHERE b.publisherBook = :publisherBook"),
    @NamedQuery(name = "Book.findByLanguageBook", query = "SELECT b FROM Book b WHERE b.languageBook = :languageBook"),
    @NamedQuery(name = "Book.findByCountryBook", query = "SELECT b FROM Book b WHERE b.countryBook = :countryBook"),
    @NamedQuery(name = "Book.findByGenreBook", query = "SELECT b FROM Book b WHERE b.genreBook = :genreBook")})
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idBook")
    private Integer idBook;
    @Basic(optional = false)
    @Column(name = "nameBook")
    private String nameBook;
    @Column(name = "authorBook")
    private String authorBook;
    @Column(name = "abstractBook")
    private String abstractBook;
    @Column(name = "pagesBook")
    private Integer pagesBook;
    @Column(name = "publisherBook")
    private String publisherBook;
    @Column(name = "languageBook")
    private String languageBook;
    @Column(name = "countryBook")
    private String countryBook;
    @Column(name = "genreBook")
    private String genreBook;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "book")
    private List<Ratings> ratingsList;

    public Book() {
    }

    public Book(Integer idBook) {
        this.idBook = idBook;
    }

    public Book(Integer idBook, String nameBook) {
        this.idBook = idBook;
        this.nameBook = nameBook;
    }

    public Integer getIdBook() {
        return idBook;
    }

    public void setIdBook(Integer idBook) {
        this.idBook = idBook;
    }

    public String getNameBook() {
        return nameBook;
    }

    public void setNameBook(String nameBook) {
        this.nameBook = nameBook;
    }

    public String getAuthorBook() {
        return authorBook;
    }

    public void setAuthorBook(String authorBook) {
        this.authorBook = authorBook;
    }

    public String getAbstractBook() {
        return abstractBook;
    }

    public void setAbstractBook(String abstractBook) {
        this.abstractBook = abstractBook;
    }

    public Integer getPagesBook() {
        return pagesBook;
    }

    public void setPagesBook(Integer pagesBook) {
        this.pagesBook = pagesBook;
    }

    public String getPublisherBook() {
        return publisherBook;
    }

    public void setPublisherBook(String publisherBook) {
        this.publisherBook = publisherBook;
    }

    public String getLanguageBook() {
        return languageBook;
    }

    public void setLanguageBook(String languageBook) {
        this.languageBook = languageBook;
    }

    public String getCountryBook() {
        return countryBook;
    }

    public void setCountryBook(String countryBook) {
        this.countryBook = countryBook;
    }

    public String getGenreBook() {
        return genreBook;
    }

    public void setGenreBook(String genreBook) {
        this.genreBook = genreBook;
    }

    @XmlTransient
    public List<Ratings> getRatingsList() {
        return ratingsList;
    }

    public void setRatingsList(List<Ratings> ratingsList) {
        this.ratingsList = ratingsList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idBook != null ? idBook.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Book)) {
            return false;
        }
        Book other = (Book) object;
        if ((this.idBook == null && other.idBook != null) || (this.idBook != null && !this.idBook.equals(other.idBook))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Book[ idBook=" + idBook + " ]";
    }
    
}
