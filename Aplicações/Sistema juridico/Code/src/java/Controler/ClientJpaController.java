/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controler;

import Controler.exceptions.NonexistentEntityException;
import Controler.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Model.Admin;
import Model.Client;
import Model.Document;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author USER
 */
public class ClientJpaController implements Serializable {

    public ClientJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Client client) throws PreexistingEntityException, Exception {
        if (client.getDocumentList() == null) {
            client.setDocumentList(new ArrayList<Document>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Admin adminloginUser = client.getAdminloginUser();
            if (adminloginUser != null) {
                adminloginUser = em.getReference(adminloginUser.getClass(), adminloginUser.getLoginUser());
                client.setAdminloginUser(adminloginUser);
            }
            List<Document> attachedDocumentList = new ArrayList<Document>();
            for (Document documentListDocumentToAttach : client.getDocumentList()) {
                documentListDocumentToAttach = em.getReference(documentListDocumentToAttach.getClass(), documentListDocumentToAttach.getIdDocument());
                attachedDocumentList.add(documentListDocumentToAttach);
            }
            client.setDocumentList(attachedDocumentList);
            em.persist(client);
            if (adminloginUser != null) {
                adminloginUser.getClientList().add(client);
                adminloginUser = em.merge(adminloginUser);
            }
            for (Document documentListDocument : client.getDocumentList()) {
                Client oldClientcpfRegisterOfDocumentListDocument = documentListDocument.getClientcpfRegister();
                documentListDocument.setClientcpfRegister(client);
                documentListDocument = em.merge(documentListDocument);
                if (oldClientcpfRegisterOfDocumentListDocument != null) {
                    oldClientcpfRegisterOfDocumentListDocument.getDocumentList().remove(documentListDocument);
                    oldClientcpfRegisterOfDocumentListDocument = em.merge(oldClientcpfRegisterOfDocumentListDocument);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findClient(client.getCpfRegister()) != null) {
                throw new PreexistingEntityException("Client " + client + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Client client) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Client persistentClient = em.find(Client.class, client.getCpfRegister());
            Admin adminloginUserOld = persistentClient.getAdminloginUser();
            Admin adminloginUserNew = client.getAdminloginUser();
            List<Document> documentListOld = persistentClient.getDocumentList();
            List<Document> documentListNew = client.getDocumentList();
            if (adminloginUserNew != null) {
                adminloginUserNew = em.getReference(adminloginUserNew.getClass(), adminloginUserNew.getLoginUser());
                client.setAdminloginUser(adminloginUserNew);
            }
            List<Document> attachedDocumentListNew = new ArrayList<Document>();
            for (Document documentListNewDocumentToAttach : documentListNew) {
                documentListNewDocumentToAttach = em.getReference(documentListNewDocumentToAttach.getClass(), documentListNewDocumentToAttach.getIdDocument());
                attachedDocumentListNew.add(documentListNewDocumentToAttach);
            }
            documentListNew = attachedDocumentListNew;
            client.setDocumentList(documentListNew);
            client = em.merge(client);
            if (adminloginUserOld != null && !adminloginUserOld.equals(adminloginUserNew)) {
                adminloginUserOld.getClientList().remove(client);
                adminloginUserOld = em.merge(adminloginUserOld);
            }
            if (adminloginUserNew != null && !adminloginUserNew.equals(adminloginUserOld)) {
                adminloginUserNew.getClientList().add(client);
                adminloginUserNew = em.merge(adminloginUserNew);
            }
            for (Document documentListOldDocument : documentListOld) {
                if (!documentListNew.contains(documentListOldDocument)) {
                    documentListOldDocument.setClientcpfRegister(null);
                    documentListOldDocument = em.merge(documentListOldDocument);
                }
            }
            for (Document documentListNewDocument : documentListNew) {
                if (!documentListOld.contains(documentListNewDocument)) {
                    Client oldClientcpfRegisterOfDocumentListNewDocument = documentListNewDocument.getClientcpfRegister();
                    documentListNewDocument.setClientcpfRegister(client);
                    documentListNewDocument = em.merge(documentListNewDocument);
                    if (oldClientcpfRegisterOfDocumentListNewDocument != null && !oldClientcpfRegisterOfDocumentListNewDocument.equals(client)) {
                        oldClientcpfRegisterOfDocumentListNewDocument.getDocumentList().remove(documentListNewDocument);
                        oldClientcpfRegisterOfDocumentListNewDocument = em.merge(oldClientcpfRegisterOfDocumentListNewDocument);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = client.getCpfRegister();
                if (findClient(id) == null) {
                    throw new NonexistentEntityException("The client with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Client client;
            try {
                client = em.getReference(Client.class, id);
                client.getCpfRegister();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The client with id " + id + " no longer exists.", enfe);
            }
            Admin adminloginUser = client.getAdminloginUser();
            if (adminloginUser != null) {
                adminloginUser.getClientList().remove(client);
                adminloginUser = em.merge(adminloginUser);
            }
            List<Document> documentList = client.getDocumentList();
            for (Document documentListDocument : documentList) {
                documentListDocument.setClientcpfRegister(null);
                documentListDocument = em.merge(documentListDocument);
            }
            em.remove(client);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Client> findClientEntities() {
        return findClientEntities(true, -1, -1);
    }

    public List<Client> findClientEntities(int maxResults, int firstResult) {
        return findClientEntities(false, maxResults, firstResult);
    }

    private List<Client> findClientEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Client.class));
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

    public Client findClient(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Client.class, id);
        } finally {
            em.close();
        }
    }

    public int getClientCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Client> rt = cq.from(Client.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
