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
import Model.Trial;
import Model.Variable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author USER
 */
public class TrialJpaController implements Serializable {

    public TrialJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Trial trial) {
        if (trial.getVariableList() == null) {
            trial.setVariableList(new ArrayList<Variable>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tag tagidTag = trial.getTagidTag();
            if (tagidTag != null) {
                tagidTag = em.getReference(tagidTag.getClass(), tagidTag.getIdTag());
                trial.setTagidTag(tagidTag);
            }
            Admin userloginUser = trial.getUserloginUser();
            if (userloginUser != null) {
                userloginUser = em.getReference(userloginUser.getClass(), userloginUser.getLoginUser());
                trial.setUserloginUser(userloginUser);
            }
            List<Variable> attachedVariableList = new ArrayList<Variable>();
            for (Variable variableListVariableToAttach : trial.getVariableList()) {
                variableListVariableToAttach = em.getReference(variableListVariableToAttach.getClass(), variableListVariableToAttach.getIdVariable());
                attachedVariableList.add(variableListVariableToAttach);
            }
            trial.setVariableList(attachedVariableList);
            em.persist(trial);
            if (tagidTag != null) {
                tagidTag.getTrialList().add(trial);
                tagidTag = em.merge(tagidTag);
            }
            if (userloginUser != null) {
                userloginUser.getTrialList().add(trial);
                userloginUser = em.merge(userloginUser);
            }
            for (Variable variableListVariable : trial.getVariableList()) {
                Trial oldTrialidTrialOfVariableListVariable = variableListVariable.getTrialidTrial();
                variableListVariable.setTrialidTrial(trial);
                variableListVariable = em.merge(variableListVariable);
                if (oldTrialidTrialOfVariableListVariable != null) {
                    oldTrialidTrialOfVariableListVariable.getVariableList().remove(variableListVariable);
                    oldTrialidTrialOfVariableListVariable = em.merge(oldTrialidTrialOfVariableListVariable);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Trial trial) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Trial persistentTrial = em.find(Trial.class, trial.getIdTrial());
            Tag tagidTagOld = persistentTrial.getTagidTag();
            Tag tagidTagNew = trial.getTagidTag();
            Admin userloginUserOld = persistentTrial.getUserloginUser();
            Admin userloginUserNew = trial.getUserloginUser();
            List<Variable> variableListOld = persistentTrial.getVariableList();
            List<Variable> variableListNew = trial.getVariableList();
            List<String> illegalOrphanMessages = null;
            for (Variable variableListOldVariable : variableListOld) {
                if (!variableListNew.contains(variableListOldVariable)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Variable " + variableListOldVariable + " since its trialidTrial field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (tagidTagNew != null) {
                tagidTagNew = em.getReference(tagidTagNew.getClass(), tagidTagNew.getIdTag());
                trial.setTagidTag(tagidTagNew);
            }
            if (userloginUserNew != null) {
                userloginUserNew = em.getReference(userloginUserNew.getClass(), userloginUserNew.getLoginUser());
                trial.setUserloginUser(userloginUserNew);
            }
            List<Variable> attachedVariableListNew = new ArrayList<Variable>();
            for (Variable variableListNewVariableToAttach : variableListNew) {
                variableListNewVariableToAttach = em.getReference(variableListNewVariableToAttach.getClass(), variableListNewVariableToAttach.getIdVariable());
                attachedVariableListNew.add(variableListNewVariableToAttach);
            }
            variableListNew = attachedVariableListNew;
            trial.setVariableList(variableListNew);
            trial = em.merge(trial);
            if (tagidTagOld != null && !tagidTagOld.equals(tagidTagNew)) {
                tagidTagOld.getTrialList().remove(trial);
                tagidTagOld = em.merge(tagidTagOld);
            }
            if (tagidTagNew != null && !tagidTagNew.equals(tagidTagOld)) {
                tagidTagNew.getTrialList().add(trial);
                tagidTagNew = em.merge(tagidTagNew);
            }
            if (userloginUserOld != null && !userloginUserOld.equals(userloginUserNew)) {
                userloginUserOld.getTrialList().remove(trial);
                userloginUserOld = em.merge(userloginUserOld);
            }
            if (userloginUserNew != null && !userloginUserNew.equals(userloginUserOld)) {
                userloginUserNew.getTrialList().add(trial);
                userloginUserNew = em.merge(userloginUserNew);
            }
            for (Variable variableListNewVariable : variableListNew) {
                if (!variableListOld.contains(variableListNewVariable)) {
                    Trial oldTrialidTrialOfVariableListNewVariable = variableListNewVariable.getTrialidTrial();
                    variableListNewVariable.setTrialidTrial(trial);
                    variableListNewVariable = em.merge(variableListNewVariable);
                    if (oldTrialidTrialOfVariableListNewVariable != null && !oldTrialidTrialOfVariableListNewVariable.equals(trial)) {
                        oldTrialidTrialOfVariableListNewVariable.getVariableList().remove(variableListNewVariable);
                        oldTrialidTrialOfVariableListNewVariable = em.merge(oldTrialidTrialOfVariableListNewVariable);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = trial.getIdTrial();
                if (findTrial(id) == null) {
                    throw new NonexistentEntityException("The trial with id " + id + " no longer exists.");
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
            Trial trial;
            try {
                trial = em.getReference(Trial.class, id);
                trial.getIdTrial();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The trial with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Variable> variableListOrphanCheck = trial.getVariableList();
            for (Variable variableListOrphanCheckVariable : variableListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Trial (" + trial + ") cannot be destroyed since the Variable " + variableListOrphanCheckVariable + " in its variableList field has a non-nullable trialidTrial field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Tag tagidTag = trial.getTagidTag();
            if (tagidTag != null) {
                tagidTag.getTrialList().remove(trial);
                tagidTag = em.merge(tagidTag);
            }
            Admin userloginUser = trial.getUserloginUser();
            if (userloginUser != null) {
                userloginUser.getTrialList().remove(trial);
                userloginUser = em.merge(userloginUser);
            }
            em.remove(trial);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Trial> findTrialEntities() {
        return findTrialEntities(true, -1, -1);
    }

    public List<Trial> findTrialEntities(int maxResults, int firstResult) {
        return findTrialEntities(false, maxResults, firstResult);
    }

    private List<Trial> findTrialEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Trial.class));
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

    public Trial findTrial(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Trial.class, id);
        } finally {
            em.close();
        }
    }

    public int getTrialCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Trial> rt = cq.from(Trial.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
