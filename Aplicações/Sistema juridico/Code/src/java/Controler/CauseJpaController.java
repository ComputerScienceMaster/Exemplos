/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controler;

import Controler.exceptions.IllegalOrphanException;
import Controler.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Model.Tag;
import Model.Admin;
import Model.Cause;
import Model.Causevariable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author USER
 */
public class CauseJpaController implements Serializable {

    public CauseJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cause cause) {
        if (cause.getCausevariableList() == null) {
            cause.setCausevariableList(new ArrayList<Causevariable>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tag tagidTag = cause.getTagidTag();
            if (tagidTag != null) {
                tagidTag = em.getReference(tagidTag.getClass(), tagidTag.getIdTag());
                cause.setTagidTag(tagidTag);
            }
            Admin adminloginUser = cause.getAdminloginUser();
            if (adminloginUser != null) {
                adminloginUser = em.getReference(adminloginUser.getClass(), adminloginUser.getLoginUser());
                cause.setAdminloginUser(adminloginUser);
            }
            List<Causevariable> attachedCausevariableList = new ArrayList<Causevariable>();
            for (Causevariable causevariableListCausevariableToAttach : cause.getCausevariableList()) {
                causevariableListCausevariableToAttach = em.getReference(causevariableListCausevariableToAttach.getClass(), causevariableListCausevariableToAttach.getIdCauseVariable());
                attachedCausevariableList.add(causevariableListCausevariableToAttach);
            }
            cause.setCausevariableList(attachedCausevariableList);
            em.persist(cause);
            if (tagidTag != null) {
                tagidTag.getCauseList().add(cause);
                tagidTag = em.merge(tagidTag);
            }
            if (adminloginUser != null) {
                adminloginUser.getCauseList().add(cause);
                adminloginUser = em.merge(adminloginUser);
            }
            for (Causevariable causevariableListCausevariable : cause.getCausevariableList()) {
                Cause oldCauseidCauseOfCausevariableListCausevariable = causevariableListCausevariable.getCauseidCause();
                causevariableListCausevariable.setCauseidCause(cause);
                causevariableListCausevariable = em.merge(causevariableListCausevariable);
                if (oldCauseidCauseOfCausevariableListCausevariable != null) {
                    oldCauseidCauseOfCausevariableListCausevariable.getCausevariableList().remove(causevariableListCausevariable);
                    oldCauseidCauseOfCausevariableListCausevariable = em.merge(oldCauseidCauseOfCausevariableListCausevariable);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cause cause) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cause persistentCause = em.find(Cause.class, cause.getIdCause());
            Tag tagidTagOld = persistentCause.getTagidTag();
            Tag tagidTagNew = cause.getTagidTag();
            Admin adminloginUserOld = persistentCause.getAdminloginUser();
            Admin adminloginUserNew = cause.getAdminloginUser();
            List<Causevariable> causevariableListOld = persistentCause.getCausevariableList();
            List<Causevariable> causevariableListNew = cause.getCausevariableList();
            List<String> illegalOrphanMessages = null;
            for (Causevariable causevariableListOldCausevariable : causevariableListOld) {
                if (!causevariableListNew.contains(causevariableListOldCausevariable)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Causevariable " + causevariableListOldCausevariable + " since its causeidCause field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (tagidTagNew != null) {
                tagidTagNew = em.getReference(tagidTagNew.getClass(), tagidTagNew.getIdTag());
                cause.setTagidTag(tagidTagNew);
            }
            if (adminloginUserNew != null) {
                adminloginUserNew = em.getReference(adminloginUserNew.getClass(), adminloginUserNew.getLoginUser());
                cause.setAdminloginUser(adminloginUserNew);
            }
            List<Causevariable> attachedCausevariableListNew = new ArrayList<Causevariable>();
            for (Causevariable causevariableListNewCausevariableToAttach : causevariableListNew) {
                causevariableListNewCausevariableToAttach = em.getReference(causevariableListNewCausevariableToAttach.getClass(), causevariableListNewCausevariableToAttach.getIdCauseVariable());
                attachedCausevariableListNew.add(causevariableListNewCausevariableToAttach);
            }
            causevariableListNew = attachedCausevariableListNew;
            cause.setCausevariableList(causevariableListNew);
            cause = em.merge(cause);
            if (tagidTagOld != null && !tagidTagOld.equals(tagidTagNew)) {
                tagidTagOld.getCauseList().remove(cause);
                tagidTagOld = em.merge(tagidTagOld);
            }
            if (tagidTagNew != null && !tagidTagNew.equals(tagidTagOld)) {
                tagidTagNew.getCauseList().add(cause);
                tagidTagNew = em.merge(tagidTagNew);
            }
            if (adminloginUserOld != null && !adminloginUserOld.equals(adminloginUserNew)) {
                adminloginUserOld.getCauseList().remove(cause);
                adminloginUserOld = em.merge(adminloginUserOld);
            }
            if (adminloginUserNew != null && !adminloginUserNew.equals(adminloginUserOld)) {
                adminloginUserNew.getCauseList().add(cause);
                adminloginUserNew = em.merge(adminloginUserNew);
            }
            for (Causevariable causevariableListNewCausevariable : causevariableListNew) {
                if (!causevariableListOld.contains(causevariableListNewCausevariable)) {
                    Cause oldCauseidCauseOfCausevariableListNewCausevariable = causevariableListNewCausevariable.getCauseidCause();
                    causevariableListNewCausevariable.setCauseidCause(cause);
                    causevariableListNewCausevariable = em.merge(causevariableListNewCausevariable);
                    if (oldCauseidCauseOfCausevariableListNewCausevariable != null && !oldCauseidCauseOfCausevariableListNewCausevariable.equals(cause)) {
                        oldCauseidCauseOfCausevariableListNewCausevariable.getCausevariableList().remove(causevariableListNewCausevariable);
                        oldCauseidCauseOfCausevariableListNewCausevariable = em.merge(oldCauseidCauseOfCausevariableListNewCausevariable);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cause.getIdCause();
                if (findCause(id) == null) {
                    throw new NonexistentEntityException("The cause with id " + id + " no longer exists.");
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
            Cause cause;
            try {
                cause = em.getReference(Cause.class, id);
                cause.getIdCause();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cause with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Causevariable> causevariableListOrphanCheck = cause.getCausevariableList();
            for (Causevariable causevariableListOrphanCheckCausevariable : causevariableListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cause (" + cause + ") cannot be destroyed since the Causevariable " + causevariableListOrphanCheckCausevariable + " in its causevariableList field has a non-nullable causeidCause field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Tag tagidTag = cause.getTagidTag();
            if (tagidTag != null) {
                tagidTag.getCauseList().remove(cause);
                tagidTag = em.merge(tagidTag);
            }
            Admin adminloginUser = cause.getAdminloginUser();
            if (adminloginUser != null) {
                adminloginUser.getCauseList().remove(cause);
                adminloginUser = em.merge(adminloginUser);
            }
            em.remove(cause);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cause> findCauseEntities() {
        return findCauseEntities(true, -1, -1);
    }

    public List<Cause> findCauseEntities(int maxResults, int firstResult) {
        return findCauseEntities(false, maxResults, firstResult);
    }

    private List<Cause> findCauseEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cause.class));
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

    public Cause findCause(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cause.class, id);
        } finally {
            em.close();
        }
    }

    public int getCauseCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cause> rt = cq.from(Cause.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
