/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.Car;
import model.UserAccount;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.Task;
import model.Travel;

/**
 *
 * @author USER
 */
public class TravelJpaController implements Serializable {

    public TravelJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Travel travel) {
        if (travel.getUserAccountList() == null) {
            travel.setUserAccountList(new ArrayList<UserAccount>());
        }
        if (travel.getTaskList() == null) {
            travel.setTaskList(new ArrayList<Task>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Car carPlate = travel.getCarPlate();
            if (carPlate != null) {
                carPlate = em.getReference(carPlate.getClass(), carPlate.getPlate());
                travel.setCarPlate(carPlate);
            }
            UserAccount owner = travel.getOwner();
            if (owner != null) {
                owner = em.getReference(owner.getClass(), owner.getUserLogin());
                travel.setOwner(owner);
            }
            List<UserAccount> attachedUserAccountList = new ArrayList<UserAccount>();
            for (UserAccount userAccountListUserAccountToAttach : travel.getUserAccountList()) {
                userAccountListUserAccountToAttach = em.getReference(userAccountListUserAccountToAttach.getClass(), userAccountListUserAccountToAttach.getUserLogin());
                attachedUserAccountList.add(userAccountListUserAccountToAttach);
            }
            travel.setUserAccountList(attachedUserAccountList);
            List<Task> attachedTaskList = new ArrayList<Task>();
            for (Task taskListTaskToAttach : travel.getTaskList()) {
                taskListTaskToAttach = em.getReference(taskListTaskToAttach.getClass(), taskListTaskToAttach.getIdTask());
                attachedTaskList.add(taskListTaskToAttach);
            }
            travel.setTaskList(attachedTaskList);
            em.persist(travel);
            if (carPlate != null) {
                carPlate.getTravelList().add(travel);
                carPlate = em.merge(carPlate);
            }
            if (owner != null) {
                owner.getTravelList().add(travel);
                owner = em.merge(owner);
            }
            for (UserAccount userAccountListUserAccount : travel.getUserAccountList()) {
                userAccountListUserAccount.getTravelList().add(travel);
                userAccountListUserAccount = em.merge(userAccountListUserAccount);
            }
            for (Task taskListTask : travel.getTaskList()) {
                Travel oldTravelIdTravelOfTaskListTask = taskListTask.getTravelIdTravel();
                taskListTask.setTravelIdTravel(travel);
                taskListTask = em.merge(taskListTask);
                if (oldTravelIdTravelOfTaskListTask != null) {
                    oldTravelIdTravelOfTaskListTask.getTaskList().remove(taskListTask);
                    oldTravelIdTravelOfTaskListTask = em.merge(oldTravelIdTravelOfTaskListTask);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Travel travel) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Travel persistentTravel = em.find(Travel.class, travel.getIdTravel());
            Car carPlateOld = persistentTravel.getCarPlate();
            Car carPlateNew = travel.getCarPlate();
            UserAccount ownerOld = persistentTravel.getOwner();
            UserAccount ownerNew = travel.getOwner();
            List<UserAccount> userAccountListOld = persistentTravel.getUserAccountList();
            List<UserAccount> userAccountListNew = travel.getUserAccountList();
            List<Task> taskListOld = persistentTravel.getTaskList();
            List<Task> taskListNew = travel.getTaskList();
            List<String> illegalOrphanMessages = null;
            for (Task taskListOldTask : taskListOld) {
                if (!taskListNew.contains(taskListOldTask)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Task " + taskListOldTask + " since its travelIdTravel field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (carPlateNew != null) {
                carPlateNew = em.getReference(carPlateNew.getClass(), carPlateNew.getPlate());
                travel.setCarPlate(carPlateNew);
            }
            if (ownerNew != null) {
                ownerNew = em.getReference(ownerNew.getClass(), ownerNew.getUserLogin());
                travel.setOwner(ownerNew);
            }
            List<UserAccount> attachedUserAccountListNew = new ArrayList<UserAccount>();
            for (UserAccount userAccountListNewUserAccountToAttach : userAccountListNew) {
                userAccountListNewUserAccountToAttach = em.getReference(userAccountListNewUserAccountToAttach.getClass(), userAccountListNewUserAccountToAttach.getUserLogin());
                attachedUserAccountListNew.add(userAccountListNewUserAccountToAttach);
            }
            userAccountListNew = attachedUserAccountListNew;
            travel.setUserAccountList(userAccountListNew);
            List<Task> attachedTaskListNew = new ArrayList<Task>();
            for (Task taskListNewTaskToAttach : taskListNew) {
                taskListNewTaskToAttach = em.getReference(taskListNewTaskToAttach.getClass(), taskListNewTaskToAttach.getIdTask());
                attachedTaskListNew.add(taskListNewTaskToAttach);
            }
            taskListNew = attachedTaskListNew;
            travel.setTaskList(taskListNew);
            travel = em.merge(travel);
            if (carPlateOld != null && !carPlateOld.equals(carPlateNew)) {
                carPlateOld.getTravelList().remove(travel);
                carPlateOld = em.merge(carPlateOld);
            }
            if (carPlateNew != null && !carPlateNew.equals(carPlateOld)) {
                carPlateNew.getTravelList().add(travel);
                carPlateNew = em.merge(carPlateNew);
            }
            if (ownerOld != null && !ownerOld.equals(ownerNew)) {
                ownerOld.getTravelList().remove(travel);
                ownerOld = em.merge(ownerOld);
            }
            if (ownerNew != null && !ownerNew.equals(ownerOld)) {
                ownerNew.getTravelList().add(travel);
                ownerNew = em.merge(ownerNew);
            }
            for (UserAccount userAccountListOldUserAccount : userAccountListOld) {
                if (!userAccountListNew.contains(userAccountListOldUserAccount)) {
                    userAccountListOldUserAccount.getTravelList().remove(travel);
                    userAccountListOldUserAccount = em.merge(userAccountListOldUserAccount);
                }
            }
            for (UserAccount userAccountListNewUserAccount : userAccountListNew) {
                if (!userAccountListOld.contains(userAccountListNewUserAccount)) {
                    userAccountListNewUserAccount.getTravelList().add(travel);
                    userAccountListNewUserAccount = em.merge(userAccountListNewUserAccount);
                }
            }
            for (Task taskListNewTask : taskListNew) {
                if (!taskListOld.contains(taskListNewTask)) {
                    Travel oldTravelIdTravelOfTaskListNewTask = taskListNewTask.getTravelIdTravel();
                    taskListNewTask.setTravelIdTravel(travel);
                    taskListNewTask = em.merge(taskListNewTask);
                    if (oldTravelIdTravelOfTaskListNewTask != null && !oldTravelIdTravelOfTaskListNewTask.equals(travel)) {
                        oldTravelIdTravelOfTaskListNewTask.getTaskList().remove(taskListNewTask);
                        oldTravelIdTravelOfTaskListNewTask = em.merge(oldTravelIdTravelOfTaskListNewTask);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = travel.getIdTravel();
                if (findTravel(id) == null) {
                    throw new NonexistentEntityException("The travel with id " + id + " no longer exists.");
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
            Travel travel;
            try {
                travel = em.getReference(Travel.class, id);
                travel.getIdTravel();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The travel with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Task> taskListOrphanCheck = travel.getTaskList();
            for (Task taskListOrphanCheckTask : taskListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Travel (" + travel + ") cannot be destroyed since the Task " + taskListOrphanCheckTask + " in its taskList field has a non-nullable travelIdTravel field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Car carPlate = travel.getCarPlate();
            if (carPlate != null) {
                carPlate.getTravelList().remove(travel);
                carPlate = em.merge(carPlate);
            }
            UserAccount owner = travel.getOwner();
            if (owner != null) {
                owner.getTravelList().remove(travel);
                owner = em.merge(owner);
            }
            List<UserAccount> userAccountList = travel.getUserAccountList();
            for (UserAccount userAccountListUserAccount : userAccountList) {
                userAccountListUserAccount.getTravelList().remove(travel);
                userAccountListUserAccount = em.merge(userAccountListUserAccount);
            }
            em.remove(travel);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Travel> findTravelEntities() {
        return findTravelEntities(true, -1, -1);
    }

    public List<Travel> findTravelEntities(int maxResults, int firstResult) {
        return findTravelEntities(false, maxResults, firstResult);
    }

    private List<Travel> findTravelEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Travel.class));
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

    public Travel findTravel(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Travel.class, id);
        } finally {
            em.close();
        }
    }

    public int getTravelCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Travel> rt = cq.from(Travel.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
