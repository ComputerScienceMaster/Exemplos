/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
import controller.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.Sector;
import model.Travel;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.Task;
import model.UserAccount;

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

    public void create(UserAccount userAccount) throws PreexistingEntityException, Exception {
        if (userAccount.getTravelList() == null) {
            userAccount.setTravelList(new ArrayList<Travel>());
        }
        if (userAccount.getTaskList() == null) {
            userAccount.setTaskList(new ArrayList<Task>());
        }
        if (userAccount.getTravelList1() == null) {
            userAccount.setTravelList1(new ArrayList<Travel>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sector sectoridSector = userAccount.getSectoridSector();
            if (sectoridSector != null) {
                sectoridSector = em.getReference(sectoridSector.getClass(), sectoridSector.getIdSector());
                userAccount.setSectoridSector(sectoridSector);
            }
            List<Travel> attachedTravelList = new ArrayList<Travel>();
            for (Travel travelListTravelToAttach : userAccount.getTravelList()) {
                travelListTravelToAttach = em.getReference(travelListTravelToAttach.getClass(), travelListTravelToAttach.getIdTravel());
                attachedTravelList.add(travelListTravelToAttach);
            }
            userAccount.setTravelList(attachedTravelList);
            List<Task> attachedTaskList = new ArrayList<Task>();
            for (Task taskListTaskToAttach : userAccount.getTaskList()) {
                taskListTaskToAttach = em.getReference(taskListTaskToAttach.getClass(), taskListTaskToAttach.getIdTask());
                attachedTaskList.add(taskListTaskToAttach);
            }
            userAccount.setTaskList(attachedTaskList);
            List<Travel> attachedTravelList1 = new ArrayList<Travel>();
            for (Travel travelList1TravelToAttach : userAccount.getTravelList1()) {
                travelList1TravelToAttach = em.getReference(travelList1TravelToAttach.getClass(), travelList1TravelToAttach.getIdTravel());
                attachedTravelList1.add(travelList1TravelToAttach);
            }
            userAccount.setTravelList1(attachedTravelList1);
            em.persist(userAccount);
            if (sectoridSector != null) {
                sectoridSector.getUserAccountList().add(userAccount);
                sectoridSector = em.merge(sectoridSector);
            }
            for (Travel travelListTravel : userAccount.getTravelList()) {
                travelListTravel.getUserAccountList().add(userAccount);
                travelListTravel = em.merge(travelListTravel);
            }
            for (Task taskListTask : userAccount.getTaskList()) {
                UserAccount oldUseraccountuserLoginOfTaskListTask = taskListTask.getUseraccountuserLogin();
                taskListTask.setUseraccountuserLogin(userAccount);
                taskListTask = em.merge(taskListTask);
                if (oldUseraccountuserLoginOfTaskListTask != null) {
                    oldUseraccountuserLoginOfTaskListTask.getTaskList().remove(taskListTask);
                    oldUseraccountuserLoginOfTaskListTask = em.merge(oldUseraccountuserLoginOfTaskListTask);
                }
            }
            for (Travel travelList1Travel : userAccount.getTravelList1()) {
                UserAccount oldOwnerOfTravelList1Travel = travelList1Travel.getOwner();
                travelList1Travel.setOwner(userAccount);
                travelList1Travel = em.merge(travelList1Travel);
                if (oldOwnerOfTravelList1Travel != null) {
                    oldOwnerOfTravelList1Travel.getTravelList1().remove(travelList1Travel);
                    oldOwnerOfTravelList1Travel = em.merge(oldOwnerOfTravelList1Travel);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findUserAccount(userAccount.getUserLogin()) != null) {
                throw new PreexistingEntityException("UserAccount " + userAccount + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(UserAccount userAccount) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            UserAccount persistentUserAccount = em.find(UserAccount.class, userAccount.getUserLogin());
            Sector sectoridSectorOld = persistentUserAccount.getSectoridSector();
            Sector sectoridSectorNew = userAccount.getSectoridSector();
            List<Travel> travelListOld = persistentUserAccount.getTravelList();
            List<Travel> travelListNew = userAccount.getTravelList();
            List<Task> taskListOld = persistentUserAccount.getTaskList();
            List<Task> taskListNew = userAccount.getTaskList();
            List<Travel> travelList1Old = persistentUserAccount.getTravelList1();
            List<Travel> travelList1New = userAccount.getTravelList1();
            List<String> illegalOrphanMessages = null;
            for (Travel travelList1OldTravel : travelList1Old) {
                if (!travelList1New.contains(travelList1OldTravel)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Travel " + travelList1OldTravel + " since its owner field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (sectoridSectorNew != null) {
                sectoridSectorNew = em.getReference(sectoridSectorNew.getClass(), sectoridSectorNew.getIdSector());
                userAccount.setSectoridSector(sectoridSectorNew);
            }
            List<Travel> attachedTravelListNew = new ArrayList<Travel>();
            for (Travel travelListNewTravelToAttach : travelListNew) {
                travelListNewTravelToAttach = em.getReference(travelListNewTravelToAttach.getClass(), travelListNewTravelToAttach.getIdTravel());
                attachedTravelListNew.add(travelListNewTravelToAttach);
            }
            travelListNew = attachedTravelListNew;
            userAccount.setTravelList(travelListNew);
            List<Task> attachedTaskListNew = new ArrayList<Task>();
            for (Task taskListNewTaskToAttach : taskListNew) {
                taskListNewTaskToAttach = em.getReference(taskListNewTaskToAttach.getClass(), taskListNewTaskToAttach.getIdTask());
                attachedTaskListNew.add(taskListNewTaskToAttach);
            }
            taskListNew = attachedTaskListNew;
            userAccount.setTaskList(taskListNew);
            List<Travel> attachedTravelList1New = new ArrayList<Travel>();
            for (Travel travelList1NewTravelToAttach : travelList1New) {
                travelList1NewTravelToAttach = em.getReference(travelList1NewTravelToAttach.getClass(), travelList1NewTravelToAttach.getIdTravel());
                attachedTravelList1New.add(travelList1NewTravelToAttach);
            }
            travelList1New = attachedTravelList1New;
            userAccount.setTravelList1(travelList1New);
            userAccount = em.merge(userAccount);
            if (sectoridSectorOld != null && !sectoridSectorOld.equals(sectoridSectorNew)) {
                sectoridSectorOld.getUserAccountList().remove(userAccount);
                sectoridSectorOld = em.merge(sectoridSectorOld);
            }
            if (sectoridSectorNew != null && !sectoridSectorNew.equals(sectoridSectorOld)) {
                sectoridSectorNew.getUserAccountList().add(userAccount);
                sectoridSectorNew = em.merge(sectoridSectorNew);
            }
            for (Travel travelListOldTravel : travelListOld) {
                if (!travelListNew.contains(travelListOldTravel)) {
                    travelListOldTravel.getUserAccountList().remove(userAccount);
                    travelListOldTravel = em.merge(travelListOldTravel);
                }
            }
            for (Travel travelListNewTravel : travelListNew) {
                if (!travelListOld.contains(travelListNewTravel)) {
                    travelListNewTravel.getUserAccountList().add(userAccount);
                    travelListNewTravel = em.merge(travelListNewTravel);
                }
            }
            for (Task taskListOldTask : taskListOld) {
                if (!taskListNew.contains(taskListOldTask)) {
                    taskListOldTask.setUseraccountuserLogin(null);
                    taskListOldTask = em.merge(taskListOldTask);
                }
            }
            for (Task taskListNewTask : taskListNew) {
                if (!taskListOld.contains(taskListNewTask)) {
                    UserAccount oldUseraccountuserLoginOfTaskListNewTask = taskListNewTask.getUseraccountuserLogin();
                    taskListNewTask.setUseraccountuserLogin(userAccount);
                    taskListNewTask = em.merge(taskListNewTask);
                    if (oldUseraccountuserLoginOfTaskListNewTask != null && !oldUseraccountuserLoginOfTaskListNewTask.equals(userAccount)) {
                        oldUseraccountuserLoginOfTaskListNewTask.getTaskList().remove(taskListNewTask);
                        oldUseraccountuserLoginOfTaskListNewTask = em.merge(oldUseraccountuserLoginOfTaskListNewTask);
                    }
                }
            }
            for (Travel travelList1NewTravel : travelList1New) {
                if (!travelList1Old.contains(travelList1NewTravel)) {
                    UserAccount oldOwnerOfTravelList1NewTravel = travelList1NewTravel.getOwner();
                    travelList1NewTravel.setOwner(userAccount);
                    travelList1NewTravel = em.merge(travelList1NewTravel);
                    if (oldOwnerOfTravelList1NewTravel != null && !oldOwnerOfTravelList1NewTravel.equals(userAccount)) {
                        oldOwnerOfTravelList1NewTravel.getTravelList1().remove(travelList1NewTravel);
                        oldOwnerOfTravelList1NewTravel = em.merge(oldOwnerOfTravelList1NewTravel);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = userAccount.getUserLogin();
                if (findUserAccount(id) == null) {
                    throw new NonexistentEntityException("The userAccount with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            UserAccount userAccount;
            try {
                userAccount = em.getReference(UserAccount.class, id);
                userAccount.getUserLogin();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The userAccount with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Travel> travelList1OrphanCheck = userAccount.getTravelList1();
            for (Travel travelList1OrphanCheckTravel : travelList1OrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This UserAccount (" + userAccount + ") cannot be destroyed since the Travel " + travelList1OrphanCheckTravel + " in its travelList1 field has a non-nullable owner field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Sector sectoridSector = userAccount.getSectoridSector();
            if (sectoridSector != null) {
                sectoridSector.getUserAccountList().remove(userAccount);
                sectoridSector = em.merge(sectoridSector);
            }
            List<Travel> travelList = userAccount.getTravelList();
            for (Travel travelListTravel : travelList) {
                travelListTravel.getUserAccountList().remove(userAccount);
                travelListTravel = em.merge(travelListTravel);
            }
            List<Task> taskList = userAccount.getTaskList();
            for (Task taskListTask : taskList) {
                taskListTask.setUseraccountuserLogin(null);
                taskListTask = em.merge(taskListTask);
            }
            em.remove(userAccount);
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

    public UserAccount findUserAccount(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(UserAccount.class, id);
        } finally {
            em.close();
        }
    }

    public int getUserAccountCount() {
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
