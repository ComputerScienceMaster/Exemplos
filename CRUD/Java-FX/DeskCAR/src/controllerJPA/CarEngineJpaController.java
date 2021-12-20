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
import model.CarEngine;

/**
 *
 * @author Garcia
 */
public class CarEngineJpaController implements Serializable {

    public CarEngineJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CarEngine carEngine) throws PreexistingEntityException, Exception {
        if (carEngine.getCarList() == null) {
            carEngine.setCarList(new ArrayList<Car>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Car> attachedCarList = new ArrayList<Car>();
            for (Car carListCarToAttach : carEngine.getCarList()) {
                carListCarToAttach = em.getReference(carListCarToAttach.getClass(), carListCarToAttach.getIdCar());
                attachedCarList.add(carListCarToAttach);
            }
            carEngine.setCarList(attachedCarList);
            em.persist(carEngine);
            for (Car carListCar : carEngine.getCarList()) {
                CarEngine oldCarEngineOfCarListCar = carListCar.getCarEngine();
                carListCar.setCarEngine(carEngine);
                carListCar = em.merge(carListCar);
                if (oldCarEngineOfCarListCar != null) {
                    oldCarEngineOfCarListCar.getCarList().remove(carListCar);
                    oldCarEngineOfCarListCar = em.merge(oldCarEngineOfCarListCar);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCarEngine(carEngine.getIdEngine()) != null) {
                throw new PreexistingEntityException("CarEngine " + carEngine + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CarEngine carEngine) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CarEngine persistentCarEngine = em.find(CarEngine.class, carEngine.getIdEngine());
            List<Car> carListOld = persistentCarEngine.getCarList();
            List<Car> carListNew = carEngine.getCarList();
            List<String> illegalOrphanMessages = null;
            for (Car carListOldCar : carListOld) {
                if (!carListNew.contains(carListOldCar)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Car " + carListOldCar + " since its carEngine field is not nullable.");
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
            carEngine.setCarList(carListNew);
            carEngine = em.merge(carEngine);
            for (Car carListNewCar : carListNew) {
                if (!carListOld.contains(carListNewCar)) {
                    CarEngine oldCarEngineOfCarListNewCar = carListNewCar.getCarEngine();
                    carListNewCar.setCarEngine(carEngine);
                    carListNewCar = em.merge(carListNewCar);
                    if (oldCarEngineOfCarListNewCar != null && !oldCarEngineOfCarListNewCar.equals(carEngine)) {
                        oldCarEngineOfCarListNewCar.getCarList().remove(carListNewCar);
                        oldCarEngineOfCarListNewCar = em.merge(oldCarEngineOfCarListNewCar);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = carEngine.getIdEngine();
                if (findCarEngine(id) == null) {
                    throw new NonexistentEntityException("The carEngine with id " + id + " no longer exists.");
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
            CarEngine carEngine;
            try {
                carEngine = em.getReference(CarEngine.class, id);
                carEngine.getIdEngine();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The carEngine with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Car> carListOrphanCheck = carEngine.getCarList();
            for (Car carListOrphanCheckCar : carListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This CarEngine (" + carEngine + ") cannot be destroyed since the Car " + carListOrphanCheckCar + " in its carList field has a non-nullable carEngine field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(carEngine);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CarEngine> findCarEngineEntities() {
        return findCarEngineEntities(true, -1, -1);
    }

    public List<CarEngine> findCarEngineEntities(int maxResults, int firstResult) {
        return findCarEngineEntities(false, maxResults, firstResult);
    }

    private List<CarEngine> findCarEngineEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CarEngine.class));
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

    public CarEngine findCarEngine(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CarEngine.class, id);
        } finally {
            em.close();
        }
    }

    public int getCarEngineCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CarEngine> rt = cq.from(CarEngine.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
