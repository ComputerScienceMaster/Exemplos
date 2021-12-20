/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllerJPA;

import controllerJPA.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.Car;
import model.Buyer;
import model.Purchase;

/**
 *
 * @author Garcia
 */
public class PurchaseJpaController implements Serializable {

    public PurchaseJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Purchase purchase) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Car car = purchase.getCar();
            if (car != null) {
                car = em.getReference(car.getClass(), car.getIdCar());
                purchase.setCar(car);
            }
            Buyer buyer = purchase.getBuyer();
            if (buyer != null) {
                buyer = em.getReference(buyer.getClass(), buyer.getCpf());
                purchase.setBuyer(buyer);
            }
            em.persist(purchase);
            if (car != null) {
                car.getPurchaseList().add(purchase);
                car = em.merge(car);
            }
            if (buyer != null) {
                buyer.getPurchaseList().add(purchase);
                buyer = em.merge(buyer);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Purchase purchase) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Purchase persistentPurchase = em.find(Purchase.class, purchase.getIdPurchase());
            Car carOld = persistentPurchase.getCar();
            Car carNew = purchase.getCar();
            Buyer buyerOld = persistentPurchase.getBuyer();
            Buyer buyerNew = purchase.getBuyer();
            if (carNew != null) {
                carNew = em.getReference(carNew.getClass(), carNew.getIdCar());
                purchase.setCar(carNew);
            }
            if (buyerNew != null) {
                buyerNew = em.getReference(buyerNew.getClass(), buyerNew.getCpf());
                purchase.setBuyer(buyerNew);
            }
            purchase = em.merge(purchase);
            if (carOld != null && !carOld.equals(carNew)) {
                carOld.getPurchaseList().remove(purchase);
                carOld = em.merge(carOld);
            }
            if (carNew != null && !carNew.equals(carOld)) {
                carNew.getPurchaseList().add(purchase);
                carNew = em.merge(carNew);
            }
            if (buyerOld != null && !buyerOld.equals(buyerNew)) {
                buyerOld.getPurchaseList().remove(purchase);
                buyerOld = em.merge(buyerOld);
            }
            if (buyerNew != null && !buyerNew.equals(buyerOld)) {
                buyerNew.getPurchaseList().add(purchase);
                buyerNew = em.merge(buyerNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = purchase.getIdPurchase();
                if (findPurchase(id) == null) {
                    throw new NonexistentEntityException("The purchase with id " + id + " no longer exists.");
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
            Purchase purchase;
            try {
                purchase = em.getReference(Purchase.class, id);
                purchase.getIdPurchase();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The purchase with id " + id + " no longer exists.", enfe);
            }
            Car car = purchase.getCar();
            if (car != null) {
                car.getPurchaseList().remove(purchase);
                car = em.merge(car);
            }
            Buyer buyer = purchase.getBuyer();
            if (buyer != null) {
                buyer.getPurchaseList().remove(purchase);
                buyer = em.merge(buyer);
            }
            em.remove(purchase);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Purchase> findPurchaseEntities() {
        return findPurchaseEntities(true, -1, -1);
    }

    public List<Purchase> findPurchaseEntities(int maxResults, int firstResult) {
        return findPurchaseEntities(false, maxResults, firstResult);
    }

    private List<Purchase> findPurchaseEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Purchase.class));
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

    public Purchase findPurchase(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Purchase.class, id);
        } finally {
            em.close();
        }
    }

    public int getPurchaseCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Purchase> rt = cq.from(Purchase.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
