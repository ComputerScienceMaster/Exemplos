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
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.Sector;
import model.UserAccount;

/**
 *
 * @author USER
 */
public class SectorJpaController implements Serializable {

    public SectorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Sector sector) {
        if (sector.getCarList() == null) {
            sector.setCarList(new ArrayList<Car>());
        }
        if (sector.getUserAccountList() == null) {
            sector.setUserAccountList(new ArrayList<UserAccount>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Car> attachedCarList = new ArrayList<Car>();
            for (Car carListCarToAttach : sector.getCarList()) {
                carListCarToAttach = em.getReference(carListCarToAttach.getClass(), carListCarToAttach.getPlate());
                attachedCarList.add(carListCarToAttach);
            }
            sector.setCarList(attachedCarList);
            List<UserAccount> attachedUserAccountList = new ArrayList<UserAccount>();
            for (UserAccount userAccountListUserAccountToAttach : sector.getUserAccountList()) {
                userAccountListUserAccountToAttach = em.getReference(userAccountListUserAccountToAttach.getClass(), userAccountListUserAccountToAttach.getUserLogin());
                attachedUserAccountList.add(userAccountListUserAccountToAttach);
            }
            sector.setUserAccountList(attachedUserAccountList);
            em.persist(sector);
            for (Car carListCar : sector.getCarList()) {
                Sector oldSectoridSectorOfCarListCar = carListCar.getSectoridSector();
                carListCar.setSectoridSector(sector);
                carListCar = em.merge(carListCar);
                if (oldSectoridSectorOfCarListCar != null) {
                    oldSectoridSectorOfCarListCar.getCarList().remove(carListCar);
                    oldSectoridSectorOfCarListCar = em.merge(oldSectoridSectorOfCarListCar);
                }
            }
            for (UserAccount userAccountListUserAccount : sector.getUserAccountList()) {
                Sector oldSectoridSectorOfUserAccountListUserAccount = userAccountListUserAccount.getSectoridSector();
                userAccountListUserAccount.setSectoridSector(sector);
                userAccountListUserAccount = em.merge(userAccountListUserAccount);
                if (oldSectoridSectorOfUserAccountListUserAccount != null) {
                    oldSectoridSectorOfUserAccountListUserAccount.getUserAccountList().remove(userAccountListUserAccount);
                    oldSectoridSectorOfUserAccountListUserAccount = em.merge(oldSectoridSectorOfUserAccountListUserAccount);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Sector sector) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sector persistentSector = em.find(Sector.class, sector.getIdSector());
            List<Car> carListOld = persistentSector.getCarList();
            List<Car> carListNew = sector.getCarList();
            List<UserAccount> userAccountListOld = persistentSector.getUserAccountList();
            List<UserAccount> userAccountListNew = sector.getUserAccountList();
            List<String> illegalOrphanMessages = null;
            for (Car carListOldCar : carListOld) {
                if (!carListNew.contains(carListOldCar)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Car " + carListOldCar + " since its sectoridSector field is not nullable.");
                }
            }
            for (UserAccount userAccountListOldUserAccount : userAccountListOld) {
                if (!userAccountListNew.contains(userAccountListOldUserAccount)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain UserAccount " + userAccountListOldUserAccount + " since its sectoridSector field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Car> attachedCarListNew = new ArrayList<Car>();
            for (Car carListNewCarToAttach : carListNew) {
                carListNewCarToAttach = em.getReference(carListNewCarToAttach.getClass(), carListNewCarToAttach.getPlate());
                attachedCarListNew.add(carListNewCarToAttach);
            }
            carListNew = attachedCarListNew;
            sector.setCarList(carListNew);
            List<UserAccount> attachedUserAccountListNew = new ArrayList<UserAccount>();
            for (UserAccount userAccountListNewUserAccountToAttach : userAccountListNew) {
                userAccountListNewUserAccountToAttach = em.getReference(userAccountListNewUserAccountToAttach.getClass(), userAccountListNewUserAccountToAttach.getUserLogin());
                attachedUserAccountListNew.add(userAccountListNewUserAccountToAttach);
            }
            userAccountListNew = attachedUserAccountListNew;
            sector.setUserAccountList(userAccountListNew);
            sector = em.merge(sector);
            for (Car carListNewCar : carListNew) {
                if (!carListOld.contains(carListNewCar)) {
                    Sector oldSectoridSectorOfCarListNewCar = carListNewCar.getSectoridSector();
                    carListNewCar.setSectoridSector(sector);
                    carListNewCar = em.merge(carListNewCar);
                    if (oldSectoridSectorOfCarListNewCar != null && !oldSectoridSectorOfCarListNewCar.equals(sector)) {
                        oldSectoridSectorOfCarListNewCar.getCarList().remove(carListNewCar);
                        oldSectoridSectorOfCarListNewCar = em.merge(oldSectoridSectorOfCarListNewCar);
                    }
                }
            }
            for (UserAccount userAccountListNewUserAccount : userAccountListNew) {
                if (!userAccountListOld.contains(userAccountListNewUserAccount)) {
                    Sector oldSectoridSectorOfUserAccountListNewUserAccount = userAccountListNewUserAccount.getSectoridSector();
                    userAccountListNewUserAccount.setSectoridSector(sector);
                    userAccountListNewUserAccount = em.merge(userAccountListNewUserAccount);
                    if (oldSectoridSectorOfUserAccountListNewUserAccount != null && !oldSectoridSectorOfUserAccountListNewUserAccount.equals(sector)) {
                        oldSectoridSectorOfUserAccountListNewUserAccount.getUserAccountList().remove(userAccountListNewUserAccount);
                        oldSectoridSectorOfUserAccountListNewUserAccount = em.merge(oldSectoridSectorOfUserAccountListNewUserAccount);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = sector.getIdSector();
                if (findSector(id) == null) {
                    throw new NonexistentEntityException("The sector with id " + id + " no longer exists.");
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
            Sector sector;
            try {
                sector = em.getReference(Sector.class, id);
                sector.getIdSector();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sector with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Car> carListOrphanCheck = sector.getCarList();
            for (Car carListOrphanCheckCar : carListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Sector (" + sector + ") cannot be destroyed since the Car " + carListOrphanCheckCar + " in its carList field has a non-nullable sectoridSector field.");
            }
            List<UserAccount> userAccountListOrphanCheck = sector.getUserAccountList();
            for (UserAccount userAccountListOrphanCheckUserAccount : userAccountListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Sector (" + sector + ") cannot be destroyed since the UserAccount " + userAccountListOrphanCheckUserAccount + " in its userAccountList field has a non-nullable sectoridSector field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(sector);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Sector> findSectorEntities() {
        return findSectorEntities(true, -1, -1);
    }

    public List<Sector> findSectorEntities(int maxResults, int firstResult) {
        return findSectorEntities(false, maxResults, firstResult);
    }

    private List<Sector> findSectorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Sector.class));
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

    public Sector findSector(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Sector.class, id);
        } finally {
            em.close();
        }
    }

    public int getSectorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Sector> rt = cq.from(Sector.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
