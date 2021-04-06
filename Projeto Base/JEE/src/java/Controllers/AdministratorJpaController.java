/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Controllers.exceptions.NonexistentEntityException;
import Controllers.exceptions.PreexistingEntityException;
import Model.Administrator;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author USER
 */
public class AdministratorJpaController implements Serializable {

    public AdministratorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Administrator administrator) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(administrator);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAdministrator(administrator.getLoginAdministrator()) != null) {
                throw new PreexistingEntityException("Administrator " + administrator + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Administrator administrator) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            administrator = em.merge(administrator);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = administrator.getLoginAdministrator();
                if (findAdministrator(id) == null) {
                    throw new NonexistentEntityException("The administrator with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Administrator administrator;
            try {
                administrator = em.getReference(Administrator.class, id);
                administrator.getLoginAdministrator();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The administrator with id " + id + " no longer exists.", enfe);
            }
            em.remove(administrator);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Administrator> findAdministratorEntities() {
        return findAdministratorEntities(true, -1, -1);
    }

    public List<Administrator> findAdministratorEntities(int maxResults, int firstResult) {
        return findAdministratorEntities(false, maxResults, firstResult);
    }

    private List<Administrator> findAdministratorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Administrator.class));
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

    public Administrator findAdministrator(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Administrator.class, id);
        } finally {
            em.close();
        }
    }

    public int getAdministratorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Administrator> rt = cq.from(Administrator.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
