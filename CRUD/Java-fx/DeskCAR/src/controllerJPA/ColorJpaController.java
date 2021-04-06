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
import model.Color;

/**
 *
 * @author Garcia
 */
public class ColorJpaController implements Serializable {

    public ColorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Color color) throws PreexistingEntityException, Exception {
        if (color.getCarList() == null) {
            color.setCarList(new ArrayList<Car>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Car> attachedCarList = new ArrayList<Car>();
            for (Car carListCarToAttach : color.getCarList()) {
                carListCarToAttach = em.getReference(carListCarToAttach.getClass(), carListCarToAttach.getIdCar());
                attachedCarList.add(carListCarToAttach);
            }
            color.setCarList(attachedCarList);
            em.persist(color);
            for (Car carListCar : color.getCarList()) {
                Color oldColorOfCarListCar = carListCar.getColor();
                carListCar.setColor(color);
                carListCar = em.merge(carListCar);
                if (oldColorOfCarListCar != null) {
                    oldColorOfCarListCar.getCarList().remove(carListCar);
                    oldColorOfCarListCar = em.merge(oldColorOfCarListCar);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findColor(color.getIdColor()) != null) {
                throw new PreexistingEntityException("Color " + color + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Color color) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Color persistentColor = em.find(Color.class, color.getIdColor());
            List<Car> carListOld = persistentColor.getCarList();
            List<Car> carListNew = color.getCarList();
            List<String> illegalOrphanMessages = null;
            for (Car carListOldCar : carListOld) {
                if (!carListNew.contains(carListOldCar)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Car " + carListOldCar + " since its color field is not nullable.");
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
            color.setCarList(carListNew);
            color = em.merge(color);
            for (Car carListNewCar : carListNew) {
                if (!carListOld.contains(carListNewCar)) {
                    Color oldColorOfCarListNewCar = carListNewCar.getColor();
                    carListNewCar.setColor(color);
                    carListNewCar = em.merge(carListNewCar);
                    if (oldColorOfCarListNewCar != null && !oldColorOfCarListNewCar.equals(color)) {
                        oldColorOfCarListNewCar.getCarList().remove(carListNewCar);
                        oldColorOfCarListNewCar = em.merge(oldColorOfCarListNewCar);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = color.getIdColor();
                if (findColor(id) == null) {
                    throw new NonexistentEntityException("The color with id " + id + " no longer exists.");
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
            Color color;
            try {
                color = em.getReference(Color.class, id);
                color.getIdColor();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The color with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Car> carListOrphanCheck = color.getCarList();
            for (Car carListOrphanCheckCar : carListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Color (" + color + ") cannot be destroyed since the Car " + carListOrphanCheckCar + " in its carList field has a non-nullable color field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(color);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Color> findColorEntities() {
        return findColorEntities(true, -1, -1);
    }

    public List<Color> findColorEntities(int maxResults, int firstResult) {
        return findColorEntities(false, maxResults, firstResult);
    }

    private List<Color> findColorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Color.class));
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

    public Color findColor(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Color.class, id);
        } finally {
            em.close();
        }
    }

    public int getColorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Color> rt = cq.from(Color.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
