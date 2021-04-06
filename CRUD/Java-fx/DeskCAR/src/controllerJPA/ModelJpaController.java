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
import model.Model;

/**
 *
 * @author Garcia
 */
public class ModelJpaController implements Serializable {

    public ModelJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Model model) throws PreexistingEntityException, Exception {
        if (model.getCarList() == null) {
            model.setCarList(new ArrayList<Car>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Car> attachedCarList = new ArrayList<Car>();
            for (Car carListCarToAttach : model.getCarList()) {
                carListCarToAttach = em.getReference(carListCarToAttach.getClass(), carListCarToAttach.getIdCar());
                attachedCarList.add(carListCarToAttach);
            }
            model.setCarList(attachedCarList);
            em.persist(model);
            for (Car carListCar : model.getCarList()) {
                Model oldModelOfCarListCar = carListCar.getModel();
                carListCar.setModel(model);
                carListCar = em.merge(carListCar);
                if (oldModelOfCarListCar != null) {
                    oldModelOfCarListCar.getCarList().remove(carListCar);
                    oldModelOfCarListCar = em.merge(oldModelOfCarListCar);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findModel(model.getIdModel()) != null) {
                throw new PreexistingEntityException("Model " + model + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Model model) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Model persistentModel = em.find(Model.class, model.getIdModel());
            List<Car> carListOld = persistentModel.getCarList();
            List<Car> carListNew = model.getCarList();
            List<String> illegalOrphanMessages = null;
            for (Car carListOldCar : carListOld) {
                if (!carListNew.contains(carListOldCar)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Car " + carListOldCar + " since its model field is not nullable.");
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
            model.setCarList(carListNew);
            model = em.merge(model);
            for (Car carListNewCar : carListNew) {
                if (!carListOld.contains(carListNewCar)) {
                    Model oldModelOfCarListNewCar = carListNewCar.getModel();
                    carListNewCar.setModel(model);
                    carListNewCar = em.merge(carListNewCar);
                    if (oldModelOfCarListNewCar != null && !oldModelOfCarListNewCar.equals(model)) {
                        oldModelOfCarListNewCar.getCarList().remove(carListNewCar);
                        oldModelOfCarListNewCar = em.merge(oldModelOfCarListNewCar);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = model.getIdModel();
                if (findModel(id) == null) {
                    throw new NonexistentEntityException("The model with id " + id + " no longer exists.");
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
            Model model;
            try {
                model = em.getReference(Model.class, id);
                model.getIdModel();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The model with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Car> carListOrphanCheck = model.getCarList();
            for (Car carListOrphanCheckCar : carListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Model (" + model + ") cannot be destroyed since the Car " + carListOrphanCheckCar + " in its carList field has a non-nullable model field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(model);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Model> findModelEntities() {
        return findModelEntities(true, -1, -1);
    }

    public List<Model> findModelEntities(int maxResults, int firstResult) {
        return findModelEntities(false, maxResults, firstResult);
    }

    private List<Model> findModelEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Model.class));
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

    public Model findModel(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Model.class, id);
        } finally {
            em.close();
        }
    }

    public int getModelCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Model> rt = cq.from(Model.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
