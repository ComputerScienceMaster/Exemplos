/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.Task;
import model.Travel;
import model.UserAccount;

/**
 *
 * @author USER
 */
public class TaskJpaController implements Serializable {

    public TaskJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Task task) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Travel travelIdTravel = task.getTravelIdTravel();
            if (travelIdTravel != null) {
                travelIdTravel = em.getReference(travelIdTravel.getClass(), travelIdTravel.getIdTravel());
                task.setTravelIdTravel(travelIdTravel);
            }
            UserAccount useraccountuserLogin = task.getUseraccountuserLogin();
            if (useraccountuserLogin != null) {
                useraccountuserLogin = em.getReference(useraccountuserLogin.getClass(), useraccountuserLogin.getUserLogin());
                task.setUseraccountuserLogin(useraccountuserLogin);
            }
            em.persist(task);
            if (travelIdTravel != null) {
                travelIdTravel.getTaskList().add(task);
                travelIdTravel = em.merge(travelIdTravel);
            }
            if (useraccountuserLogin != null) {
                useraccountuserLogin.getTaskList().add(task);
                useraccountuserLogin = em.merge(useraccountuserLogin);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Task task) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Task persistentTask = em.find(Task.class, task.getIdTask());
            Travel travelIdTravelOld = persistentTask.getTravelIdTravel();
            Travel travelIdTravelNew = task.getTravelIdTravel();
            UserAccount useraccountuserLoginOld = persistentTask.getUseraccountuserLogin();
            UserAccount useraccountuserLoginNew = task.getUseraccountuserLogin();
            if (travelIdTravelNew != null) {
                travelIdTravelNew = em.getReference(travelIdTravelNew.getClass(), travelIdTravelNew.getIdTravel());
                task.setTravelIdTravel(travelIdTravelNew);
            }
            if (useraccountuserLoginNew != null) {
                useraccountuserLoginNew = em.getReference(useraccountuserLoginNew.getClass(), useraccountuserLoginNew.getUserLogin());
                task.setUseraccountuserLogin(useraccountuserLoginNew);
            }
            task = em.merge(task);
            if (travelIdTravelOld != null && !travelIdTravelOld.equals(travelIdTravelNew)) {
                travelIdTravelOld.getTaskList().remove(task);
                travelIdTravelOld = em.merge(travelIdTravelOld);
            }
            if (travelIdTravelNew != null && !travelIdTravelNew.equals(travelIdTravelOld)) {
                travelIdTravelNew.getTaskList().add(task);
                travelIdTravelNew = em.merge(travelIdTravelNew);
            }
            if (useraccountuserLoginOld != null && !useraccountuserLoginOld.equals(useraccountuserLoginNew)) {
                useraccountuserLoginOld.getTaskList().remove(task);
                useraccountuserLoginOld = em.merge(useraccountuserLoginOld);
            }
            if (useraccountuserLoginNew != null && !useraccountuserLoginNew.equals(useraccountuserLoginOld)) {
                useraccountuserLoginNew.getTaskList().add(task);
                useraccountuserLoginNew = em.merge(useraccountuserLoginNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = task.getIdTask();
                if (findTask(id) == null) {
                    throw new NonexistentEntityException("The task with id " + id + " no longer exists.");
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
            Task task;
            try {
                task = em.getReference(Task.class, id);
                task.getIdTask();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The task with id " + id + " no longer exists.", enfe);
            }
            Travel travelIdTravel = task.getTravelIdTravel();
            if (travelIdTravel != null) {
                travelIdTravel.getTaskList().remove(task);
                travelIdTravel = em.merge(travelIdTravel);
            }
            UserAccount useraccountuserLogin = task.getUseraccountuserLogin();
            if (useraccountuserLogin != null) {
                useraccountuserLogin.getTaskList().remove(task);
                useraccountuserLogin = em.merge(useraccountuserLogin);
            }
            em.remove(task);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Task> findTaskEntities() {
        return findTaskEntities(true, -1, -1);
    }

    public List<Task> findTaskEntities(int maxResults, int firstResult) {
        return findTaskEntities(false, maxResults, firstResult);
    }

    private List<Task> findTaskEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Task.class));
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

    public Task findTask(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Task.class, id);
        } finally {
            em.close();
        }
    }

    public int getTaskCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Task> rt = cq.from(Task.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
