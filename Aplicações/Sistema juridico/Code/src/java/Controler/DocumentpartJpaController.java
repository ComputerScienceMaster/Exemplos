/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controler;

import Controler.exceptions.NonexistentEntityException;
import Model.Documentpart;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Model.Tag;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author USER
 */
public class DocumentpartJpaController implements Serializable {

    public DocumentpartJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Documentpart documentpart) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tag partidPart = documentpart.getPartidPart();
            if (partidPart != null) {
                partidPart = em.getReference(partidPart.getClass(), partidPart.getIdTag());
                documentpart.setPartidPart(partidPart);
            }
            em.persist(documentpart);
            if (partidPart != null) {
                partidPart.getDocumentpartList().add(documentpart);
                partidPart = em.merge(partidPart);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Documentpart documentpart) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Documentpart persistentDocumentpart = em.find(Documentpart.class, documentpart.getIdDocumentPart());
            Tag partidPartOld = persistentDocumentpart.getPartidPart();
            Tag partidPartNew = documentpart.getPartidPart();
            if (partidPartNew != null) {
                partidPartNew = em.getReference(partidPartNew.getClass(), partidPartNew.getIdTag());
                documentpart.setPartidPart(partidPartNew);
            }
            documentpart = em.merge(documentpart);
            if (partidPartOld != null && !partidPartOld.equals(partidPartNew)) {
                partidPartOld.getDocumentpartList().remove(documentpart);
                partidPartOld = em.merge(partidPartOld);
            }
            if (partidPartNew != null && !partidPartNew.equals(partidPartOld)) {
                partidPartNew.getDocumentpartList().add(documentpart);
                partidPartNew = em.merge(partidPartNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = documentpart.getIdDocumentPart();
                if (findDocumentpart(id) == null) {
                    throw new NonexistentEntityException("The documentpart with id " + id + " no longer exists.");
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
            Documentpart documentpart;
            try {
                documentpart = em.getReference(Documentpart.class, id);
                documentpart.getIdDocumentPart();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The documentpart with id " + id + " no longer exists.", enfe);
            }
            Tag partidPart = documentpart.getPartidPart();
            if (partidPart != null) {
                partidPart.getDocumentpartList().remove(documentpart);
                partidPart = em.merge(partidPart);
            }
            em.remove(documentpart);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Documentpart> findDocumentpartEntities() {
        return findDocumentpartEntities(true, -1, -1);
    }

    public List<Documentpart> findDocumentpartEntities(int maxResults, int firstResult) {
        return findDocumentpartEntities(false, maxResults, firstResult);
    }

    private List<Documentpart> findDocumentpartEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Documentpart.class));
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

    public Documentpart findDocumentpart(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Documentpart.class, id);
        } finally {
            em.close();
        }
    }

    public int getDocumentpartCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Documentpart> rt = cq.from(Documentpart.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
