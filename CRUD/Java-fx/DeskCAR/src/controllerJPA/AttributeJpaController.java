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
import model.Attribute;

/**
 *
 * @author Garcia
 */
public class AttributeJpaController implements Serializable {

    public AttributeJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Attribute attribute) throws PreexistingEntityException, Exception {
        if (attribute.getCarList() == null) {
            attribute.setCarList(new ArrayList<Car>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Car> attachedCarList = new ArrayList<Car>();
            for (Car carListCarToAttach : attribute.getCarList()) {
                carListCarToAttach = em.getReference(carListCarToAttach.getClass(), carListCarToAttach.getIdCar());
                attachedCarList.add(carListCarToAttach);
            }
            attribute.setCarList(attachedCarList);
            em.persist(attribute);
            for (Car carListCar : attribute.getCarList()) {
                Attribute oldAttributeOfCarListCar = carListCar.getAttribute();
                carListCar.setAttribute(attribute);
                carListCar = em.merge(carListCar);
                if (oldAttributeOfCarListCar != null) {
                    oldAttributeOfCarListCar.getCarList().remove(carListCar);
                    oldAttributeOfCarListCar = em.merge(oldAttributeOfCarListCar);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAttribute(attribute.getIdAtributo()) != null) {
                throw new PreexistingEntityException("Attribute " + attribute + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Attribute attribute) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Attribute persistentAttribute = em.find(Attribute.class, attribute.getIdAtributo());
            List<Car> carListOld = persistentAttribute.getCarList();
            List<Car> carListNew = attribute.getCarList();
            List<String> illegalOrphanMessages = null;
            for (Car carListOldCar : carListOld) {
                if (!carListNew.contains(carListOldCar)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Car " + carListOldCar + " since its attribute field is not nullable.");
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
            attribute.setCarList(carListNew);
            attribute = em.merge(attribute);
            for (Car carListNewCar : carListNew) {
                if (!carListOld.contains(carListNewCar)) {
                    Attribute oldAttributeOfCarListNewCar = carListNewCar.getAttribute();
                    carListNewCar.setAttribute(attribute);
                    carListNewCar = em.merge(carListNewCar);
                    if (oldAttributeOfCarListNewCar != null && !oldAttributeOfCarListNewCar.equals(attribute)) {
                        oldAttributeOfCarListNewCar.getCarList().remove(carListNewCar);
                        oldAttributeOfCarListNewCar = em.merge(oldAttributeOfCarListNewCar);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = attribute.getIdAtributo();
                if (findAttribute(id) == null) {
                    throw new NonexistentEntityException("The attribute with id " + id + " no longer exists.");
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
            Attribute attribute;
            try {
                attribute = em.getReference(Attribute.class, id);
                attribute.getIdAtributo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The attribute with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Car> carListOrphanCheck = attribute.getCarList();
            for (Car carListOrphanCheckCar : carListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Attribute (" + attribute + ") cannot be destroyed since the Car " + carListOrphanCheckCar + " in its carList field has a non-nullable attribute field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(attribute);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Attribute> findAttributeEntities() {
        return findAttributeEntities(true, -1, -1);
    }

    public List<Attribute> findAttributeEntities(int maxResults, int firstResult) {
        return findAttributeEntities(false, maxResults, firstResult);
    }

    private List<Attribute> findAttributeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Attribute.class));
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

    public Attribute findAttribute(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Attribute.class, id);
        } finally {
            em.close();
        }
    }

    public int getAttributeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Attribute> rt = cq.from(Attribute.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
