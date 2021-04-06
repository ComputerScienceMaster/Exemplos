/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Controllers.exceptions.NonexistentEntityException;
import Controllers.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Model.Book;
import Model.Ratings;
import Model.RatingsPK;
import Model.UserAccount;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author USER
 */
public class RatingsJpaController implements Serializable {

    public RatingsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ratings ratings) throws PreexistingEntityException, Exception {
        if (ratings.getRatingsPK() == null) {
            ratings.setRatingsPK(new RatingsPK());
        }
        ratings.getRatingsPK().setIdBook(ratings.getBook().getIdBook());
        ratings.getRatingsPK().setIdUserAccount(ratings.getUseraccount().getIdUserAccount());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Book book = ratings.getBook();
            if (book != null) {
                book = em.getReference(book.getClass(), book.getIdBook());
                ratings.setBook(book);
            }
            UserAccount useraccount = ratings.getUseraccount();
            if (useraccount != null) {
                useraccount = em.getReference(useraccount.getClass(), useraccount.getIdUserAccount());
                ratings.setUseraccount(useraccount);
            }
            em.persist(ratings);
            if (book != null) {
                book.getRatingsList().add(ratings);
                book = em.merge(book);
            }
            if (useraccount != null) {
                useraccount.getRatingsList().add(ratings);
                useraccount = em.merge(useraccount);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findRatings(ratings.getRatingsPK()) != null) {
                throw new PreexistingEntityException("Ratings " + ratings + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Ratings ratings) throws NonexistentEntityException, Exception {
        ratings.getRatingsPK().setIdBook(ratings.getBook().getIdBook());
        ratings.getRatingsPK().setIdUserAccount(ratings.getUseraccount().getIdUserAccount());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ratings persistentRatings = em.find(Ratings.class, ratings.getRatingsPK());
            Book bookOld = persistentRatings.getBook();
            Book bookNew = ratings.getBook();
            UserAccount useraccountOld = persistentRatings.getUseraccount();
            UserAccount useraccountNew = ratings.getUseraccount();
            if (bookNew != null) {
                bookNew = em.getReference(bookNew.getClass(), bookNew.getIdBook());
                ratings.setBook(bookNew);
            }
            if (useraccountNew != null) {
                useraccountNew = em.getReference(useraccountNew.getClass(), useraccountNew.getIdUserAccount());
                ratings.setUseraccount(useraccountNew);
            }
            ratings = em.merge(ratings);
            if (bookOld != null && !bookOld.equals(bookNew)) {
                bookOld.getRatingsList().remove(ratings);
                bookOld = em.merge(bookOld);
            }
            if (bookNew != null && !bookNew.equals(bookOld)) {
                bookNew.getRatingsList().add(ratings);
                bookNew = em.merge(bookNew);
            }
            if (useraccountOld != null && !useraccountOld.equals(useraccountNew)) {
                useraccountOld.getRatingsList().remove(ratings);
                useraccountOld = em.merge(useraccountOld);
            }
            if (useraccountNew != null && !useraccountNew.equals(useraccountOld)) {
                useraccountNew.getRatingsList().add(ratings);
                useraccountNew = em.merge(useraccountNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                RatingsPK id = ratings.getRatingsPK();
                if (findRatings(id) == null) {
                    throw new NonexistentEntityException("The ratings with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(RatingsPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ratings ratings;
            try {
                ratings = em.getReference(Ratings.class, id);
                ratings.getRatingsPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ratings with id " + id + " no longer exists.", enfe);
            }
            Book book = ratings.getBook();
            if (book != null) {
                book.getRatingsList().remove(ratings);
                book = em.merge(book);
            }
            UserAccount useraccount = ratings.getUseraccount();
            if (useraccount != null) {
                useraccount.getRatingsList().remove(ratings);
                useraccount = em.merge(useraccount);
            }
            em.remove(ratings);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Ratings> findRatingsEntities() {
        return findRatingsEntities(true, -1, -1);
    }

    public List<Ratings> findRatingsEntities(int maxResults, int firstResult) {
        return findRatingsEntities(false, maxResults, firstResult);
    }

    private List<Ratings> findRatingsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ratings.class));
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

    public Ratings findRatings(RatingsPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ratings.class, id);
        } finally {
            em.close();
        }
    }

    public int getRatingsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ratings> rt = cq.from(Ratings.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
