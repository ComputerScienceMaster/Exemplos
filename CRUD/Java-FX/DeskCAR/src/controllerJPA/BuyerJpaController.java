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
import model.Buyer;
import model.Purchase;

/**
 *
 * @author Garcia
 */
public class BuyerJpaController implements Serializable {

    public BuyerJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Buyer buyer) throws PreexistingEntityException, Exception {
        if (buyer.getCarList() == null) {
            buyer.setCarList(new ArrayList<Car>());
        }
        if (buyer.getPurchaseList() == null) {
            buyer.setPurchaseList(new ArrayList<Purchase>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Car> attachedCarList = new ArrayList<Car>();
            for (Car carListCarToAttach : buyer.getCarList()) {
                carListCarToAttach = em.getReference(carListCarToAttach.getClass(), carListCarToAttach.getIdCar());
                attachedCarList.add(carListCarToAttach);
            }
            buyer.setCarList(attachedCarList);
            List<Purchase> attachedPurchaseList = new ArrayList<Purchase>();
            for (Purchase purchaseListPurchaseToAttach : buyer.getPurchaseList()) {
                purchaseListPurchaseToAttach = em.getReference(purchaseListPurchaseToAttach.getClass(), purchaseListPurchaseToAttach.getIdPurchase());
                attachedPurchaseList.add(purchaseListPurchaseToAttach);
            }
            buyer.setPurchaseList(attachedPurchaseList);
            em.persist(buyer);
            for (Car carListCar : buyer.getCarList()) {
                Buyer oldBuyerOfCarListCar = carListCar.getBuyer();
                carListCar.setBuyer(buyer);
                carListCar = em.merge(carListCar);
                if (oldBuyerOfCarListCar != null) {
                    oldBuyerOfCarListCar.getCarList().remove(carListCar);
                    oldBuyerOfCarListCar = em.merge(oldBuyerOfCarListCar);
                }
            }
            for (Purchase purchaseListPurchase : buyer.getPurchaseList()) {
                Buyer oldBuyerOfPurchaseListPurchase = purchaseListPurchase.getBuyer();
                purchaseListPurchase.setBuyer(buyer);
                purchaseListPurchase = em.merge(purchaseListPurchase);
                if (oldBuyerOfPurchaseListPurchase != null) {
                    oldBuyerOfPurchaseListPurchase.getPurchaseList().remove(purchaseListPurchase);
                    oldBuyerOfPurchaseListPurchase = em.merge(oldBuyerOfPurchaseListPurchase);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findBuyer(buyer.getCpf()) != null) {
                throw new PreexistingEntityException("Buyer " + buyer + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Buyer buyer) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Buyer persistentBuyer = em.find(Buyer.class, buyer.getCpf());
            List<Car> carListOld = persistentBuyer.getCarList();
            List<Car> carListNew = buyer.getCarList();
            List<Purchase> purchaseListOld = persistentBuyer.getPurchaseList();
            List<Purchase> purchaseListNew = buyer.getPurchaseList();
            List<String> illegalOrphanMessages = null;
            for (Purchase purchaseListOldPurchase : purchaseListOld) {
                if (!purchaseListNew.contains(purchaseListOldPurchase)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Purchase " + purchaseListOldPurchase + " since its buyer field is not nullable.");
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
            buyer.setCarList(carListNew);
            List<Purchase> attachedPurchaseListNew = new ArrayList<Purchase>();
            for (Purchase purchaseListNewPurchaseToAttach : purchaseListNew) {
                purchaseListNewPurchaseToAttach = em.getReference(purchaseListNewPurchaseToAttach.getClass(), purchaseListNewPurchaseToAttach.getIdPurchase());
                attachedPurchaseListNew.add(purchaseListNewPurchaseToAttach);
            }
            purchaseListNew = attachedPurchaseListNew;
            buyer.setPurchaseList(purchaseListNew);
            buyer = em.merge(buyer);
            for (Car carListOldCar : carListOld) {
                if (!carListNew.contains(carListOldCar)) {
                    carListOldCar.setBuyer(null);
                    carListOldCar = em.merge(carListOldCar);
                }
            }
            for (Car carListNewCar : carListNew) {
                if (!carListOld.contains(carListNewCar)) {
                    Buyer oldBuyerOfCarListNewCar = carListNewCar.getBuyer();
                    carListNewCar.setBuyer(buyer);
                    carListNewCar = em.merge(carListNewCar);
                    if (oldBuyerOfCarListNewCar != null && !oldBuyerOfCarListNewCar.equals(buyer)) {
                        oldBuyerOfCarListNewCar.getCarList().remove(carListNewCar);
                        oldBuyerOfCarListNewCar = em.merge(oldBuyerOfCarListNewCar);
                    }
                }
            }
            for (Purchase purchaseListNewPurchase : purchaseListNew) {
                if (!purchaseListOld.contains(purchaseListNewPurchase)) {
                    Buyer oldBuyerOfPurchaseListNewPurchase = purchaseListNewPurchase.getBuyer();
                    purchaseListNewPurchase.setBuyer(buyer);
                    purchaseListNewPurchase = em.merge(purchaseListNewPurchase);
                    if (oldBuyerOfPurchaseListNewPurchase != null && !oldBuyerOfPurchaseListNewPurchase.equals(buyer)) {
                        oldBuyerOfPurchaseListNewPurchase.getPurchaseList().remove(purchaseListNewPurchase);
                        oldBuyerOfPurchaseListNewPurchase = em.merge(oldBuyerOfPurchaseListNewPurchase);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = buyer.getCpf();
                if (findBuyer(id) == null) {
                    throw new NonexistentEntityException("The buyer with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Buyer buyer;
            try {
                buyer = em.getReference(Buyer.class, id);
                buyer.getCpf();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The buyer with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Purchase> purchaseListOrphanCheck = buyer.getPurchaseList();
            for (Purchase purchaseListOrphanCheckPurchase : purchaseListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Buyer (" + buyer + ") cannot be destroyed since the Purchase " + purchaseListOrphanCheckPurchase + " in its purchaseList field has a non-nullable buyer field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Car> carList = buyer.getCarList();
            for (Car carListCar : carList) {
                carListCar.setBuyer(null);
                carListCar = em.merge(carListCar);
            }
            em.remove(buyer);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Buyer> findBuyerEntities() {
        return findBuyerEntities(true, -1, -1);
    }

    public List<Buyer> findBuyerEntities(int maxResults, int firstResult) {
        return findBuyerEntities(false, maxResults, firstResult);
    }

    private List<Buyer> findBuyerEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Buyer.class));
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

    public Buyer findBuyer(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Buyer.class, id);
        } finally {
            em.close();
        }
    }

    public int getBuyerCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Buyer> rt = cq.from(Buyer.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
