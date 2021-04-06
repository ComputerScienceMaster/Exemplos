/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Controllers.exceptions.IllegalOrphanException;
import Controllers.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Model.Ratings;
import Model.UserAccount;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author USER
 */
public class UserAccountJpaController implements Serializable {

    public UserAccountJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(UserAccount useraccount) {
        if (useraccount.getRatingsList() == null) {
            useraccount.setRatingsList(new ArrayList<Ratings>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Ratings> attachedRatingsList = new ArrayList<Ratings>();
            for (Ratings ratingsListRatingsToAttach : useraccount.getRatingsList()) {
                ratingsListRatingsToAttach = em.getReference(ratingsListRatingsToAttach.getClass(), ratingsListRatingsToAttach.getRatingsPK());
                attachedRatingsList.add(ratingsListRatingsToAttach);
            }
            useraccount.setRatingsList(attachedRatingsList);
            em.persist(useraccount);
            for (Ratings ratingsListRatings : useraccount.getRatingsList()) {
                UserAccount oldUseraccountOfRatingsListRatings = ratingsListRatings.getUseraccount();
                ratingsListRatings.setUseraccount(useraccount);
                ratingsListRatings = em.merge(ratingsListRatings);
                if (oldUseraccountOfRatingsListRatings != null) {
                    oldUseraccountOfRatingsListRatings.getRatingsList().remove(ratingsListRatings);
                    oldUseraccountOfRatingsListRatings = em.merge(oldUseraccountOfRatingsListRatings);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(UserAccount useraccount) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            UserAccount persistentUseraccount = em.find(UserAccount.class, useraccount.getIdUserAccount());
            List<Ratings> ratingsListOld = persistentUseraccount.getRatingsList();
            List<Ratings> ratingsListNew = useraccount.getRatingsList();
            List<String> illegalOrphanMessages = null;
            for (Ratings ratingsListOldRatings : ratingsListOld) {
                if (!ratingsListNew.contains(ratingsListOldRatings)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Ratings " + ratingsListOldRatings + " since its useraccount field is not nullable.");
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
            useraccount.setRatingsList(ratingsListNew);
            useraccount = em.merge(useraccount);
            for (Ratings ratingsListNewRatings : ratingsListNew) {
                if (!ratingsListOld.contains(ratingsListNewRatings)) {
                    UserAccount oldUseraccountOfRatingsListNewRatings = ratingsListNewRatings.getUseraccount();
                    ratingsListNewRatings.setUseraccount(useraccount);
                    ratingsListNewRatings = em.merge(ratingsListNewRatings);
                    if (oldUseraccountOfRatingsListNewRatings != null && !oldUseraccountOfRatingsListNewRatings.equals(useraccount)) {
                        oldUseraccountOfRatingsListNewRatings.getRatingsList().remove(ratingsListNewRatings);
                        oldUseraccountOfRatingsListNewRatings = em.merge(oldUseraccountOfRatingsListNewRatings);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = useraccount.getIdUserAccount();
                if (findUserAccount(id) == null) {
                    throw new NonexistentEntityException("The useraccount with id " + id + " no longer exists.");
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
            UserAccount useraccount;
            try {
                useraccount = em.getReference(UserAccount.class, id);
                useraccount.getIdUserAccount();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The useraccount with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Ratings> ratingsListOrphanCheck = useraccount.getRatingsList();
            for (Ratings ratingsListOrphanCheckRatings : ratingsListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Useraccount (" + useraccount + ") cannot be destroyed since the Ratings " + ratingsListOrphanCheckRatings + " in its ratingsList field has a non-nullable useraccount field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(useraccount);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<UserAccount> findUserAccountEntities() {
        return findUserAccountEntities(true, -1, -1);
    }

    public List<UserAccount> findUserAccountEntities(int maxResults, int firstResult) {
        return findUserAccountEntities(false, maxResults, firstResult);
    }

    private List<UserAccount> findUserAccountEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(UserAccount.class));
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

    public UserAccount findUserAccount(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(UserAccount.class, id);
        } finally {
            em.close();
        }
    }

    public int getUseraccountCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<UserAccount> rt = cq.from(UserAccount.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
