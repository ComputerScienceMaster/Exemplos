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
import Model.Trial;
import Model.Variable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author USER
 */
public class VariableJpaController implements Serializable {

    public VariableJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Variable variable) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Trial trialidTrial = variable.getTrialidTrial();
            if (trialidTrial != null) {
                trialidTrial = em.getReference(trialidTrial.getClass(), trialidTrial.getIdTrial());
                variable.setTrialidTrial(trialidTrial);
            }
            em.persist(variable);
            if (trialidTrial != null) {
                trialidTrial.getVariableList().add(variable);
                trialidTrial = em.merge(trialidTrial);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Variable variable) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Variable persistentVariable = em.find(Variable.class, variable.getIdVariable());
            Trial trialidTrialOld = persistentVariable.getTrialidTrial();
            Trial trialidTrialNew = variable.getTrialidTrial();
            if (trialidTrialNew != null) {
                trialidTrialNew = em.getReference(trialidTrialNew.getClass(), trialidTrialNew.getIdTrial());
                variable.setTrialidTrial(trialidTrialNew);
            }
            variable = em.merge(variable);
            if (trialidTrialOld != null && !trialidTrialOld.equals(trialidTrialNew)) {
                trialidTrialOld.getVariableList().remove(variable);
                trialidTrialOld = em.merge(trialidTrialOld);
            }
            if (trialidTrialNew != null && !trialidTrialNew.equals(trialidTrialOld)) {
                trialidTrialNew.getVariableList().add(variable);
                trialidTrialNew = em.merge(trialidTrialNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = variable.getIdVariable();
                if (findVariable(id) == null) {
                    throw new NonexistentEntityException("The variable with id " + id + " no longer exists.");
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
            Variable variable;
            try {
                variable = em.getReference(Variable.class, id);
                variable.getIdVariable();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The variable with id " + id + " no longer exists.", enfe);
            }
            Trial trialidTrial = variable.getTrialidTrial();
            if (trialidTrial != null) {
                trialidTrial.getVariableList().remove(variable);
                trialidTrial = em.merge(trialidTrial);
            }
            em.remove(variable);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Variable> findVariableEntities() {
        return findVariableEntities(true, -1, -1);
    }

    public List<Variable> findVariableEntities(int maxResults, int firstResult) {
        return findVariableEntities(false, maxResults, firstResult);
    }

    private List<Variable> findVariableEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Variable.class));
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

    public Variable findVariable(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Variable.class, id);
        } finally {
            em.close();
        }
    }

    public int getVariableCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Variable> rt = cq.from(Variable.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
