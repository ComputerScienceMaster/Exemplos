/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllerJPA;

import controllerJPA.exceptions.IllegalOrphanException;
import controllerJPA.exceptions.NonexistentEntityException;
import controllerJPA.exceptions.PreexistingEntityException;
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
import model.Fuel;

/**
 *
 * @author Garcia
 */
public class FuelJpaController implements Serializable {

    public FuelJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Fuel fuel) throws PreexistingEntityException, Exception {
        if (fuel.getCarList() == null) {
            fuel.setCarList(new ArrayList<Car>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Car> attachedCarList = new ArrayList<Car>();
            for (Car carListCarToAttach : fuel.getCarList()) {
                carListCarToAttach = em.getReference(carListCarToAttach.getClass(), carListCarToAttach.getIdCar());
                attachedCarList.add(carListCarToAttach);
            }
            fuel.setCarList(attachedCarList);
            em.persist(fuel);
            for (Car carListCar : fuel.getCarList()) {
                Fuel oldFuelOfCarListCar = carListCar.getFuel();
                carListCar.setFuel(fuel);
                carListCar = em.merge(carListCar);
                if (oldFuelOfCarListCar != null) {
                    oldFuelOfCarListCar.getCarList().remove(carListCar);
                    oldFuelOfCarListCar = em.merge(oldFuelOfCarListCar);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findFuel(fuel.getIdFuel()) != null) {
                throw new PreexistingEntityException("Fuel " + fuel + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Fuel fuel) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Fuel persistentFuel = em.find(Fuel.class, fuel.getIdFuel());
            List<Car> carListOld = persistentFuel.getCarList();
            List<Car> carListNew = fuel.getCarList();
            List<String> illegalOrphanMessages = null;
            for (Car carListOldCar : carListOld) {
                if (!carListNew.contains(carListOldCar)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Car " + carListOldCar + " since its fuel field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Car> attachedCarListNew = new ArrayList<Car>();
            for (Car carListNewCarToAttach : carListNew) {
                carListNewCarToAttach = em.getReference(carListNewCarToAttach.getClass(), carListNewCarToAttach.getIdCar());
                attachedCarListNew.add(carListNewCarToAttach);
            }
            carListNew = attachedCarListNew;
            fuel.setCarList(carListNew);
            fuel = em.merge(fuel);
            for (Car carListNewCar : carListNew) {
                if (!carListOld.contains(carListNewCar)) {
                    Fuel oldFuelOfCarListNewCar = carListNewCar.getFuel();
                    carListNewCar.setFuel(fuel);
                    carListNewCar = em.merge(carListNewCar);
                    if (oldFuelOfCarListNewCar != null && !oldFuelOfCarListNewCar.equals(fuel)) {
                        oldFuelOfCarListNewCar.getCarList().remove(carListNewCar);
                        oldFuelOfCarListNewCar = em.merge(oldFuelOfCarListNewCar);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = fuel.getIdFuel();
                if (findFuel(id) == null) {
                    throw new NonexistentEntityException("The fuel with id " + id + " no longer exists.");
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
            Fuel fuel;
            try {
                fuel = em.getReference(Fuel.class, id);
                fuel.getIdFuel();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The fuel with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Car> carListOrphanCheck = fuel.getCarList();
            for (Car carListOrphanCheckCar : carListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Fuel (" + fuel + ") cannot be destroyed since the Car " + carListOrphanCheckCar + " in its carList field has a non-nullable fuel field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(fuel);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Fuel> findFuelEntities() {
        return findFuelEntities(true, -1, -1);
    }

    public List<Fuel> findFuelEntities(int maxResults, int firstResult) {
        return findFuelEntities(false, maxResults, firstResult);
    }

    private List<Fuel> findFuelEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Fuel.class));
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

    public Fuel findFuel(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Fuel.class, id);
        } finally {
            em.close();
        }
    }

    public int getFuelCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Fuel> rt = cq.from(Fuel.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
