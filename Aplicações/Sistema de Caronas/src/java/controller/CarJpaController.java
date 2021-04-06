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
import model.Car;

/**
 *
 * @author USER
 */
public class CarJpaController implements Serializable {

    public CarJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Car car) throws PreexistingEntityException, Exception {
        if (car.getTravelList() == null) {
            car.setTravelList(new ArrayList<Travel>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sector sectoridSector = car.getSectoridSector();
            if (sectoridSector != null) {
                sectoridSector = em.getReference(sectoridSector.getClass(), sectoridSector.getIdSector());
                car.setSectoridSector(sectoridSector);
            }
            List<Travel> attachedTravelList = new ArrayList<Travel>();
            for (Travel travelListTravelToAttach : car.getTravelList()) {
                travelListTravelToAttach = em.getReference(travelListTravelToAttach.getClass(), travelListTravelToAttach.getIdTravel());
                attachedTravelList.add(travelListTravelToAttach);
            }
            car.setTravelList(attachedTravelList);
            em.persist(car);
            if (sectoridSector != null) {
                sectoridSector.getCarList().add(car);
                sectoridSector = em.merge(sectoridSector);
            }
            for (Travel travelListTravel : car.getTravelList()) {
                Car oldCarPlateOfTravelListTravel = travelListTravel.getCarPlate();
                travelListTravel.setCarPlate(car);
                travelListTravel = em.merge(travelListTravel);
                if (oldCarPlateOfTravelListTravel != null) {
                    oldCarPlateOfTravelListTravel.getTravelList().remove(travelListTravel);
                    oldCarPlateOfTravelListTravel = em.merge(oldCarPlateOfTravelListTravel);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCar(car.getPlate()) != null) {
                throw new PreexistingEntityException("Car " + car + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Car car) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Car persistentCar = em.find(Car.class, car.getPlate());
            Sector sectoridSectorOld = persistentCar.getSectoridSector();
            Sector sectoridSectorNew = car.getSectoridSector();
            List<Travel> travelListOld = persistentCar.getTravelList();
            List<Travel> travelListNew = car.getTravelList();
            List<String> illegalOrphanMessages = null;
            for (Travel travelListOldTravel : travelListOld) {
                if (!travelListNew.contains(travelListOldTravel)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Travel " + travelListOldTravel + " since its carPlate field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (sectoridSectorNew != null) {
                sectoridSectorNew = em.getReference(sectoridSectorNew.getClass(), sectoridSectorNew.getIdSector());
                car.setSectoridSector(sectoridSectorNew);
            }
            List<Travel> attachedTravelListNew = new ArrayList<Travel>();
            for (Travel travelListNewTravelToAttach : travelListNew) {
                travelListNewTravelToAttach = em.getReference(travelListNewTravelToAttach.getClass(), travelListNewTravelToAttach.getIdTravel());
                attachedTravelListNew.add(travelListNewTravelToAttach);
            }
            travelListNew = attachedTravelListNew;
            car.setTravelList(travelListNew);
            car = em.merge(car);
            if (sectoridSectorOld != null && !sectoridSectorOld.equals(sectoridSectorNew)) {
                sectoridSectorOld.getCarList().remove(car);
                sectoridSectorOld = em.merge(sectoridSectorOld);
            }
            if (sectoridSectorNew != null && !sectoridSectorNew.equals(sectoridSectorOld)) {
                sectoridSectorNew.getCarList().add(car);
                sectoridSectorNew = em.merge(sectoridSectorNew);
            }
            for (Travel travelListNewTravel : travelListNew) {
                if (!travelListOld.contains(travelListNewTravel)) {
                    Car oldCarPlateOfTravelListNewTravel = travelListNewTravel.getCarPlate();
                    travelListNewTravel.setCarPlate(car);
                    travelListNewTravel = em.merge(travelListNewTravel);
                    if (oldCarPlateOfTravelListNewTravel != null && !oldCarPlateOfTravelListNewTravel.equals(car)) {
                        oldCarPlateOfTravelListNewTravel.getTravelList().remove(travelListNewTravel);
                        oldCarPlateOfTravelListNewTravel = em.merge(oldCarPlateOfTravelListNewTravel);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = car.getPlate();
                if (findCar(id) == null) {
                    throw new NonexistentEntityException("The car with id " + id + " no longer exists.");
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
            Car car;
            try {
                car = em.getReference(Car.class, id);
                car.getPlate();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The car with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Travel> travelListOrphanCheck = car.getTravelList();
            for (Travel travelListOrphanCheckTravel : travelListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Car (" + car + ") cannot be destroyed since the Travel " + travelListOrphanCheckTravel + " in its travelList field has a non-nullable carPlate field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Sector sectoridSector = car.getSectoridSector();
            if (sectoridSector != null) {
                sectoridSector.getCarList().remove(car);
                sectoridSector = em.merge(sectoridSector);
            }
            em.remove(car);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Car> findCarEntities() {
        return findCarEntities(true, -1, -1);
    }

    public List<Car> findCarEntities(int maxResults, int firstResult) {
        return findCarEntities(false, maxResults, firstResult);
    }

    private List<Car> findCarEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Car.class));
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

    public Car findCar(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Car.class, id);
        } finally {
            em.close();
        }
    }

    public int getCarCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Car> rt = cq.from(Car.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
