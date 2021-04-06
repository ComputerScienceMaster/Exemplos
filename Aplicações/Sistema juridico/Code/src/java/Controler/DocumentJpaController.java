/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controler;

import Controler.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Model.Client;
import Model.Document;
import Model.Documentmodel;
import Model.Tag;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author USER
 */
public class DocumentJpaController implements Serializable {

    public DocumentJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Document document) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Client clientcpfRegister = document.getClientcpfRegister();
            if (clientcpfRegister != null) {
                clientcpfRegister = em.getReference(clientcpfRegister.getClass(), clientcpfRegister.getCpfRegister());
                document.setClientcpfRegister(clientcpfRegister);
            }
            Documentmodel documentModelidDocumentModel = document.getDocumentModelidDocumentModel();
            if (documentModelidDocumentModel != null) {
                documentModelidDocumentModel = em.getReference(documentModelidDocumentModel.getClass(), documentModelidDocumentModel.getIdDocumentModel());
                document.setDocumentModelidDocumentModel(documentModelidDocumentModel);
            }
            Tag tagidTag = document.getTagidTag();
            if (tagidTag != null) {
                tagidTag = em.getReference(tagidTag.getClass(), tagidTag.getIdTag());
                document.setTagidTag(tagidTag);
            }
            em.persist(document);
            if (clientcpfRegister != null) {
                clientcpfRegister.getDocumentList().add(document);
                clientcpfRegister = em.merge(clientcpfRegister);
            }
            if (documentModelidDocumentModel != null) {
                documentModelidDocumentModel.getDocumentList().add(document);
                documentModelidDocumentModel = em.merge(documentModelidDocumentModel);
            }
            if (tagidTag != null) {
                tagidTag.getDocumentList().add(document);
                tagidTag = em.merge(tagidTag);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Document document) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Document persistentDocument = em.find(Document.class, document.getIdDocument());
            Client clientcpfRegisterOld = persistentDocument.getClientcpfRegister();
            Client clientcpfRegisterNew = document.getClientcpfRegister();
            Documentmodel documentModelidDocumentModelOld = persistentDocument.getDocumentModelidDocumentModel();
            Documentmodel documentModelidDocumentModelNew = document.getDocumentModelidDocumentModel();
            Tag tagidTagOld = persistentDocument.getTagidTag();
            Tag tagidTagNew = document.getTagidTag();
            if (clientcpfRegisterNew != null) {
                clientcpfRegisterNew = em.getReference(clientcpfRegisterNew.getClass(), clientcpfRegisterNew.getCpfRegister());
                document.setClientcpfRegister(clientcpfRegisterNew);
            }
            if (documentModelidDocumentModelNew != null) {
                documentModelidDocumentModelNew = em.getReference(documentModelidDocumentModelNew.getClass(), documentModelidDocumentModelNew.getIdDocumentModel());
                document.setDocumentModelidDocumentModel(documentModelidDocumentModelNew);
            }
            if (tagidTagNew != null) {
                tagidTagNew = em.getReference(tagidTagNew.getClass(), tagidTagNew.getIdTag());
                document.setTagidTag(tagidTagNew);
            }
            document = em.merge(document);
            if (clientcpfRegisterOld != null && !clientcpfRegisterOld.equals(clientcpfRegisterNew)) {
                clientcpfRegisterOld.getDocumentList().remove(document);
                clientcpfRegisterOld = em.merge(clientcpfRegisterOld);
            }
            if (clientcpfRegisterNew != null && !clientcpfRegisterNew.equals(clientcpfRegisterOld)) {
                clientcpfRegisterNew.getDocumentList().add(document);
                clientcpfRegisterNew = em.merge(clientcpfRegisterNew);
            }
            if (documentModelidDocumentModelOld != null && !documentModelidDocumentModelOld.equals(documentModelidDocumentModelNew)) {
                documentModelidDocumentModelOld.getDocumentList().remove(document);
                documentModelidDocumentModelOld = em.merge(documentModelidDocumentModelOld);
            }
            if (documentModelidDocumentModelNew != null && !documentModelidDocumentModelNew.equals(documentModelidDocumentModelOld)) {
                documentModelidDocumentModelNew.getDocumentList().add(document);
                documentModelidDocumentModelNew = em.merge(documentModelidDocumentModelNew);
            }
            if (tagidTagOld != null && !tagidTagOld.equals(tagidTagNew)) {
                tagidTagOld.getDocumentList().remove(document);
                tagidTagOld = em.merge(tagidTagOld);
            }
            if (tagidTagNew != null && !tagidTagNew.equals(tagidTagOld)) {
                tagidTagNew.getDocumentList().add(document);
                tagidTagNew = em.merge(tagidTagNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = document.getIdDocument();
                if (findDocument(id) == null) {
                    throw new NonexistentEntityException("The document with id " + id + " no longer exists.");
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
            Document document;
            try {
                document = em.getReference(Document.class, id);
                document.getIdDocument();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The document with id " + id + " no longer exists.", enfe);
            }
            Client clientcpfRegister = document.getClientcpfRegister();
            if (clientcpfRegister != null) {
                clientcpfRegister.getDocumentList().remove(document);
                clientcpfRegister = em.merge(clientcpfRegister);
            }
            Documentmodel documentModelidDocumentModel = document.getDocumentModelidDocumentModel();
            if (documentModelidDocumentModel != null) {
                documentModelidDocumentModel.getDocumentList().remove(document);
                documentModelidDocumentModel = em.merge(documentModelidDocumentModel);
            }
            Tag tagidTag = document.getTagidTag();
            if (tagidTag != null) {
                tagidTag.getDocumentList().remove(document);
                tagidTag = em.merge(tagidTag);
            }
            em.remove(document);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Document> findDocumentEntities() {
        return findDocumentEntities(true, -1, -1);
    }

    public List<Document> findDocumentEntities(int maxResults, int firstResult) {
        return findDocumentEntities(false, maxResults, firstResult);
    }

    private List<Document> findDocumentEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Document.class));
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

    public Document findDocument(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Document.class, id);
        } finally {
            em.close();
        }
    }

    public int getDocumentCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Document> rt = cq.from(Document.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
