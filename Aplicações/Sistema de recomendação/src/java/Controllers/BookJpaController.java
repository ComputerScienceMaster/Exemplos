/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Controllers.exceptions.IllegalOrphanException;
import Controllers.exceptions.NonexistentEntityException;
import Model.Book;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Model.Ratings;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author USER
 */
public class BookJpaController implements Serializable {

    public BookJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public int create(Book book) {
        if (book.getRatingsList() == null) {
            book.setRatingsList(new ArrayList<Ratings>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Ratings> attachedRatingsList = new ArrayList<Ratings>();
            for (Ratings ratingsListRatingsToAttach : book.getRatingsList()) {
                ratingsListRatingsToAttach = em.getReference(ratingsListRatingsToAttach.getClass(), ratingsListRatingsToAttach.getRatingsPK());
                attachedRatingsList.add(ratingsListRatingsToAttach);
            }
            book.setRatingsList(attachedRatingsList);
            em.persist(book);
            for (Ratings ratingsListRatings : book.getRatingsList()) {
                Book oldBookOfRatingsListRatings = ratingsListRatings.getBook();
                ratingsListRatings.setBook(book);
                ratingsListRatings = em.merge(ratingsListRatings);
                if (oldBookOfRatingsListRatings != null) {
                    oldBookOfRatingsListRatings.getRatingsList().remove(ratingsListRatings);
                    oldBookOfRatingsListRatings = em.merge(oldBookOfRatingsListRatings);
                }
            }
            em.getTransaction().commit();
            return book.getIdBook();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Book book) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Book persistentBook = em.find(Book.class, book.getIdBook());
            List<Ratings> ratingsListOld = persistentBook.getRatingsList();
            List<Ratings> ratingsListNew = book.getRatingsList();
            List<String> illegalOrphanMessages = null;
            for (Ratings ratingsListOldRatings : ratingsListOld) {
                if (!ratingsListNew.contains(ratingsListOldRatings)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Ratings " + ratingsListOldRatings + " since its book field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Ratings> attachedRatingsListNew = new ArrayList<Ratings>();
            for (Ratings ratingsListNewRatingsToAttach : ratingsListNew) {
                ratingsListNewRatingsToAttach = em.getReference(ratingsListNewRatingsToAttach.getClass(), ratingsListNewRatingsToAttach.getRatingsPK());
                attachedRatingsListNew.add(ratingsListNewRatingsToAttach);
            }
            ratingsListNew = attachedRatingsListNew;
            book.setRatingsList(ratingsListNew);
            book = em.merge(book);
            for (Ratings ratingsListNewRatings : ratingsListNew) {
                if (!ratingsListOld.contains(ratingsListNewRatings)) {
                    Book oldBookOfRatingsListNewRatings = ratingsListNewRatings.getBook();
                    ratingsListNewRatings.setBook(book);
                    ratingsListNewRatings = em.merge(ratingsListNewRatings);
                    if (oldBookOfRatingsListNewRatings != null && !oldBookOfRatingsListNewRatings.equals(book)) {
                        oldBookOfRatingsListNewRatings.getRatingsList().remove(ratingsListNewRatings);
                        oldBookOfRatingsListNewRatings = em.merge(oldBookOfRatingsListNewRatings);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = book.getIdBook();
                if (findBook(id) == null) {
                    throw new NonexistentEntityException("The book with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Book book;
            try {
                book = em.getReference(Book.class, id);
                book.getIdBook();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The book with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Ratings> ratingsListOrphanCheck = book.getRatingsList();
            for (Ratings ratingsListOrphanCheckRatings : ratingsListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Book (" + book + ") cannot be destroyed since the Ratings " + ratingsListOrphanCheckRatings + " in its ratingsList field has a non-nullable book field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(book);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Book> findBookEntities() {
        return findBookEntities(true, -1, -1);
    }

    public List<Book> findBookEntities(int maxResults, int firstResult) {
        return findBookEntities(false, maxResults, firstResult);
    }

    private List<Book> findBookEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Book.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Book findBook(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Book.class, id);
        } finally {
            em.close();
        }
    }

    public int getBookCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Book> rt = cq.from(Book.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
