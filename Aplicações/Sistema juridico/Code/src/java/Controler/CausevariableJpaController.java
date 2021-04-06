/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controler;

import Controler.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Model.Cause;
import Model.Causevariable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author USER
 */
public class CausevariableJpaController implements Serializable {

    public CausevariableJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Causevariable causevariable) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cause causeidCause = causevariable.getCauseidCause();
            if (causeidCause != null) {
                causeidCause = em.getReference(causeidCause.getClass(), causeidCause.getIdCause());
                causevariable.setCauseidCause(causeidCause);
            }
            em.persist(causevariable);
            if (causeidCause != null) {
                causeidCause.getCausevariableList().add(causevariable);
                causeidCause = em.merge(causeidCause);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Causevariable causevariable) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Causevariable persistentCausevariable = em.find(Causevariable.class, causevariable.getIdCauseVariable());
            Cause causeidCauseOld = persistentCausevariable.getCauseidCause();
            Cause causeidCauseNew = causevariable.getCauseidCause();
            if (causeidCauseNew != null) {
                causeidCauseNew = em.getReference(causeidCauseNew.getClass(), causeidCauseNew.getIdCause());
                causevariable.setCauseidCause(causeidCauseNew);
            }
            causevariable = em.merge(causevariable);
            if (causeidCauseOld != null && !causeidCauseOld.equals(causeidCauseNew)) {
                causeidCauseOld.getCausevariableList().remove(causevariable);
                causeidCauseOld = em.merge(causeidCauseOld);
            }
            if (causeidCauseNew != null && !causeidCauseNew.equals(causeidCauseOld)) {
                causeidCauseNew.getCausevariableList().add(causevariable);
                causeidCauseNew = em.merge(causeidCauseNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = causevariable.getIdCauseVariable();
                if (findCausevariable(id) == null) {
                    throw new NonexistentEntityException("The causevariable with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Causevariable causevariable;
            try {
                causevariable = em.getReference(Causevariable.class, id);
                causevariable.getIdCauseVariable();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The causevariable with id " + id + " no longer exists.", enfe);
            }
            Cause causeidCause = causevariable.getCauseidCause();
            if (causeidCause != null) {
                causeidCause.getCausevariableList().remove(causevariable);
                causeidCause = em.merge(causeidCause);
            }
            em.remove(causevariable);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Causevariable> findCausevariableEntities() {
        return findCausevariableEntities(true, -1, -1);
    }

    public List<Causevariable> findCausevariableEntities(int maxResults, int firstResult) {
        return findCausevariableEntities(false, maxResults, firstResult);
    }

    private List<Causevariable> findCausevariableEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Causevariable.class));
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

    public Causevariable findCausevariable(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Causevariable.class, id);
        } finally {
            em.close();
        }
    }

    public int getCausevariableCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Causevariable> rt = cq.from(Causevariable.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
