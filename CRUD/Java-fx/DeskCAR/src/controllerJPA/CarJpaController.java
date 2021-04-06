/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllerJPA;

import controllerJPA.exceptions.IllegalOrphanException;
import controllerJPA.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.Fuel;
import model.Buyer;
import model.CarEngine;
import model.Attribute;
import model.Color;
import model.Model;
import model.Purchase;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.Car;

/**
 *
 * @author Garcia
 */
public class CarJpaController implements Serializable {

    public CarJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Car car) {
        if (car.getPurchaseList() == null) {
            car.setPurchaseList(new ArrayList<Purchase>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Fuel fuel = car.getFuel();
            if (fuel != null) {
                fuel = em.getReference(fuel.getClass(), fuel.getIdFuel());
                car.setFuel(fuel);
            }
            Buyer buyer = car.getBuyer();
            if (buyer != null) {
                buyer = em.getReference(buyer.getClass(), buyer.getCpf());
                car.setBuyer(buyer);
            }
            CarEngine carEngine = car.getCarEngine();
            if (carEngine != null) {
                carEngine = em.getReference(carEngine.getClass(), carEngine.getIdEngine());
                car.setCarEngine(carEngine);
            }
            Attribute attribute = car.getAttribute();
            if (attribute != null) {
                attribute = em.getReference(attribute.getClass(), attribute.getIdAtributo());
                car.setAttribute(attribute);
            }
            Color color = car.getColor();
            if (color != null) {
                color = em.getReference(color.getClass(), color.getIdColor());
                car.setColor(color);
            }
            Model model = car.getModel();
            if (model != null) {
                model = em.getReference(model.getClass(), model.getIdModel());
                car.setModel(model);
            }
            List<Purchase> attachedPurchaseList = new ArrayList<Purchase>();
            for (Purchase purchaseListPurchaseToAttach : car.getPurchaseList()) {
                purchaseListPurchaseToAttach = em.getReference(purchaseListPurchaseToAttach.getClass(), purchaseListPurchaseToAttach.getIdPurchase());
                attachedPurchaseList.add(purchaseListPurchaseToAttach);
            }
            car.setPurchaseList(attachedPurchaseList);
            em.persist(car);
            if (fuel != null) {
                fuel.getCarList().add(car);
                fuel = em.merge(fuel);
            }
            if (buyer != null) {
                buyer.getCarList().add(car);
                buyer = em.merge(buyer);
            }
            if (carEngine != null) {
                carEngine.getCarList().add(car);
                carEngine = em.merge(carEngine);
            }
            if (attribute != null) {
                attribute.getCarList().add(car);
                attribute = em.merge(attribute);
            }
            if (color != null) {
                color.getCarList().add(car);
                color = em.merge(color);
            }
            if (model != null) {
                model.getCarList().add(car);
                model = em.merge(model);
            }
            for (Purchase purchaseListPurchase : car.getPurchaseList()) {
                Car oldCarOfPurchaseListPurchase = purchaseListPurchase.getCar();
                purchaseListPurchase.setCar(car);
                purchaseListPurchase = em.merge(purchaseListPurchase);
                if (oldCarOfPurchaseListPurchase != null) {
                    oldCarOfPurchaseListPurchase.getPurchaseList().remove(purchaseListPurchase);
                    oldCarOfPurchaseListPurchase = em.merge(oldCarOfPurchaseListPurchase);
                }
            }
            em.getTransaction().commit();
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
            Car persistentCar = em.find(Car.class, car.getIdCar());
            Fuel fuelOld = persistentCar.getFuel();
            Fuel fuelNew = car.getFuel();
            Buyer buyerOld = persistentCar.getBuyer();
            Buyer buyerNew = car.getBuyer();
            CarEngine carEngineOld = persistentCar.getCarEngine();
            CarEngine carEngineNew = car.getCarEngine();
            Attribute attributeOld = persistentCar.getAttribute();
            Attribute attributeNew = car.getAttribute();
            Color colorOld = persistentCar.getColor();
            Color colorNew = car.getColor();
            Model modelOld = persistentCar.getModel();
            Model modelNew = car.getModel();
            List<Purchase> purchaseListOld = persistentCar.getPurchaseList();
            List<Purchase> purchaseListNew = car.getPurchaseList();
            List<String> illegalOrphanMessages = null;
            for (Purchase purchaseListOldPurchase : purchaseListOld) {
                if (!purchaseListNew.contains(purchaseListOldPurchase)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Purchase " + purchaseListOldPurchase + " since its car field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (fuelNew != null) {
                fuelNew = em.getReference(fuelNew.getClass(), fuelNew.getIdFuel());
                car.setFuel(fuelNew);
            }
            if (buyerNew != null) {
                buyerNew = em.getReference(buyerNew.getClass(), buyerNew.getCpf());
                car.setBuyer(buyerNew);
            }
            if (carEngineNew != null) {
                carEngineNew = em.getReference(carEngineNew.getClass(), carEngineNew.getIdEngine());
                car.setCarEngine(carEngineNew);
            }
            if (attributeNew != null) {
                attributeNew = em.getReference(attributeNew.getClass(), attributeNew.getIdAtributo());
                car.setAttribute(attributeNew);
            }
            if (colorNew != null) {
                colorNew = em.getReference(colorNew.getClass(), colorNew.getIdColor());
                car.setColor(colorNew);
            }
            if (modelNew != null) {
                modelNew = em.getReference(modelNew.getClass(), modelNew.getIdModel());
                car.setModel(modelNew);
            }
            List<Purchase> attachedPurchaseListNew = new ArrayList<Purchase>();
            for (Purchase purchaseListNewPurchaseToAttach : purchaseListNew) {
                purchaseListNewPurchaseToAttach = em.getReference(purchaseListNewPurchaseToAttach.getClass(), purchaseListNewPurchaseToAttach.getIdPurchase());
                attachedPurchaseListNew.add(purchaseListNewPurchaseToAttach);
            }
            purchaseListNew = attachedPurchaseListNew;
            car.setPurchaseList(purchaseListNew);
            car = em.merge(car);
            if (fuelOld != null && !fuelOld.equals(fuelNew)) {
                fuelOld.getCarList().remove(car);
                fuelOld = em.merge(fuelOld);
            }
            if (fuelNew != null && !fuelNew.equals(fuelOld)) {
                fuelNew.getCarList().add(car);
                fuelNew = em.merge(fuelNew);
            }
            if (buyerOld != null && !buyerOld.equals(buyerNew)) {
                buyerOld.getCarList().remove(car);
                buyerOld = em.merge(buyerOld);
            }
            if (buyerNew != null && !buyerNew.equals(buyerOld)) {
                buyerNew.getCarList().add(car);
                buyerNew = em.merge(buyerNew);
            }
            if (carEngineOld != null && !carEngineOld.equals(carEngineNew)) {
                carEngineOld.getCarList().remove(car);
                carEngineOld = em.merge(carEngineOld);
            }
            if (carEngineNew != null && !carEngineNew.equals(carEngineOld)) {
                carEngineNew.getCarList().add(car);
                carEngineNew = em.merge(carEngineNew);
            }
            if (attributeOld != null && !attributeOld.equals(attributeNew)) {
                attributeOld.getCarList().remove(car);
                attributeOld = em.merge(attributeOld);
            }
            if (attributeNew != null && !attributeNew.equals(attributeOld)) {
                attributeNew.getCarList().add(car);
                attributeNew = em.merge(attributeNew);
            }
            if (colorOld != null && !colorOld.equals(colorNew)) {
                colorOld.getCarList().remove(car);
                colorOld = em.merge(colorOld);
            }
            if (colorNew != null && !colorNew.equals(colorOld)) {
                colorNew.getCarList().add(car);
                colorNew = em.merge(colorNew);
            }
            if (modelOld != null && !modelOld.equals(modelNew)) {
                modelOld.getCarList().remove(car);
                modelOld = em.merge(modelOld);
            }
            if (modelNew != null && !modelNew.equals(modelOld)) {
                modelNew.getCarList().add(car);
                modelNew = em.merge(modelNew);
            }
            for (Purchase purchaseListNewPurchase : purchaseListNew) {
                if (!purchaseListOld.contains(purchaseListNewPurchase)) {
                    Car oldCarOfPurchaseListNewPurchase = purchaseListNewPurchase.getCar();
                    purchaseListNewPurchase.setCar(car);
                    purchaseListNewPurchase = em.merge(purchaseListNewPurchase);
                    if (oldCarOfPurchaseListNewPurchase != null && !oldCarOfPurchaseListNewPurchase.equals(car)) {
                        oldCarOfPurchaseListNewPurchase.getPurchaseList().remove(purchaseListNewPurchase);
                        oldCarOfPurchaseListNewPurchase = em.merge(oldCarOfPurchaseListNewPurchase);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = car.getIdCar();
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

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Car car;
            try {
                car = em.getReference(Car.class, id);
                car.getIdCar();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The car with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Purchase> purchaseListOrphanCheck = car.getPurchaseList();
            for (Purchase purchaseListOrphanCheckPurchase : purchaseListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Car (" + car + ") cannot be destroyed since the Purchase " + purchaseListOrphanCheckPurchase + " in its purchaseList field has a non-nullable car field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Fuel fuel = car.getFuel();
            if (fuel != null) {
                fuel.getCarList().remove(car);
                fuel = em.merge(fuel);
            }
            Buyer buyer = car.getBuyer();
            if (buyer != null) {
                buyer.getCarList().remove(car);
                buyer = em.merge(buyer);
            }
            CarEngine carEngine = car.getCarEngine();
            if (carEngine != null) {
                carEngine.getCarList().remove(car);
                carEngine = em.merge(carEngine);
            }
            Attribute attribute = car.getAttribute();
            if (attribute != null) {
                attribute.getCarList().remove(car);
                attribute = em.merge(attribute);
            }
            Color color = car.getColor();
            if (color != null) {
                color.getCarList().remove(car);
                color = em.merge(color);
            }
            Model model = car.getModel();
            if (model != null) {
                model.getCarList().remove(car);
                model = em.merge(model);
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

    public Car findCar(Integer id) {
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
