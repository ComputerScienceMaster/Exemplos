/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controler;

import Controler.exceptions.IllegalOrphanException;
import Controler.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Model.Tag;
import Model.Document;
import Model.Documentmodel;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author USER
 */
public class DocumentmodelJpaController implements Serializable {

    public DocumentmodelJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Documentmodel documentmodel) {
        if (documentmodel.getDocumentList() == null) {
            documentmodel.setDocumentList(new ArrayList<Document>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tag tagidTag = documentmodel.getTagidTag();
            if (tagidTag != null) {
                tagidTag = em.getReference(tagidTag.getClass(), tagidTag.getIdTag());
                documentmodel.setTagidTag(tagidTag);
            }
            List<Document> attachedDocumentList = new ArrayList<Document>();
            for (Document documentListDocumentToAttach : documentmodel.getDocumentList()) {
                documentListDocumentToAttach = em.getReference(documentListDocumentToAttach.getClass(), documentListDocumentToAttach.getIdDocument());
                attachedDocumentList.add(documentListDocumentToAttach);
            }
            documentmodel.setDocumentList(attachedDocumentList);
            em.persist(documentmodel);
            if (tagidTag != null) {
                tagidTag.getDocumentmodelList().add(documentmodel);
                tagidTag = em.merge(tagidTag);
            }
            for (Document documentListDocument : documentmodel.getDocumentList()) {
                Documentmodel oldDocumentModelidDocumentModelOfDocumentListDocument = documentListDocument.getDocumentModelidDocumentModel();
                documentListDocument.setDocumentModelidDocumentModel(documentmodel);
                documentListDocument = em.merge(documentListDocument);
                if (oldDocumentModelidDocumentModelOfDocumentListDocument != null) {
                    oldDocumentModelidDocumentModelOfDocumentListDocument.getDocumentList().remove(documentListDocument);
                    oldDocumentModelidDocumentModelOfDocumentListDocument = em.merge(oldDocumentModelidDocumentModelOfDocumentListDocument);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Documentmodel documentmodel) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Documentmodel persistentDocumentmodel = em.find(Documentmodel.class, documentmodel.getIdDocumentModel());
            Tag tagidTagOld = persistentDocumentmodel.getTagidTag();
            Tag tagidTagNew = documentmodel.getTagidTag();
            List<Document> documentListOld = persistentDocumentmodel.getDocumentList();
            List<Document> documentListNew = documentmodel.getDocumentList();
            List<String> illegalOrphanMessages = null;
            for (Document documentListOldDocument : documentListOld) {
                if (!documentListNew.contains(documentListOldDocument)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Document " + documentListOldDocument + " since its documentModelidDocumentModel field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (tagidTagNew != null) {
                tagidTagNew = em.getReference(tagidTagNew.getClass(), tagidTagNew.getIdTag());
                documentmodel.setTagidTag(tagidTagNew);
            }
            List<Document> attachedDocumentListNew = new ArrayList<Document>();
            for (Document documentListNewDocumentToAttach : documentListNew) {
                documentListNewDocumentToAttach = em.getReference(documentListNewDocumentToAttach.getClass(), documentListNewDocumentToAttach.getIdDocument());
                attachedDocumentListNew.add(documentListNewDocumentToAttach);
            }
            documentListNew = attachedDocumentListNew;
            documentmodel.setDocumentList(documentListNew);
            documentmodel = em.merge(documentmodel);
            if (tagidTagOld != null && !tagidTagOld.equals(tagidTagNew)) {
                tagidTagOld.getDocumentmodelList().remove(documentmodel);
                tagidTagOld = em.merge(tagidTagOld);
            }
            if (tagidTagNew != null && !tagidTagNew.equals(tagidTagOld)) {
                tagidTagNew.getDocumentmodelList().add(documentmodel);
                tagidTagNew = em.merge(tagidTagNew);
            }
            for (Document documentListNewDocument : documentListNew) {
                if (!documentListOld.contains(documentListNewDocument)) {
                    Documentmodel oldDocumentModelidDocumentModelOfDocumentListNewDocument = documentListNewDocument.getDocumentModelidDocumentModel();
                    documentListNewDocument.setDocumentModelidDocumentModel(documentmodel);
                    documentListNewDocument = em.merge(documentListNewDocument);
                    if (oldDocumentModelidDocumentModelOfDocumentListNewDocument != null && !oldDocumentModelidDocumentModelOfDocumentListNewDocument.equals(documentmodel)) {
                        oldDocumentModelidDocumentModelOfDocumentListNewDocument.getDocumentList().remove(documentListNewDocument);
                        oldDocumentModelidDocumentModelOfDocumentListNewDocument = em.merge(oldDocumentModelidDocumentModelOfDocumentListNewDocument);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = documentmodel.getIdDocumentModel();
                if (findDocumentmodel(id) == null) {
                    throw new NonexistentEntityException("The documentmodel with id " + id + " no longer exists.");
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
            Documentmodel documentmodel;
            try {
                documentmodel = em.getReference(Documentmodel.class, id);
                documentmodel.getIdDocumentModel();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The documentmodel with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Document> documentListOrphanCheck = documentmodel.getDocumentList();
            for (Document documentListOrphanCheckDocument : documentListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Documentmodel (" + documentmodel + ") cannot be destroyed since the Document " + documentListOrphanCheckDocument + " in its documentList field has a non-nullable documentModelidDocumentModel field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Tag tagidTag = documentmodel.getTagidTag();
            if (tagidTag != null) {
                tagidTag.getDocumentmodelList().remove(documentmodel);
                tagidTag = em.merge(tagidTag);
            }
            em.remove(documentmodel);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Documentmodel> findDocumentmodelEntities() {
        return findDocumentmodelEntities(true, -1, -1);
    }

    public List<Documentmodel> findDocumentmodelEntities(int maxResults, int firstResult) {
        return findDocumentmodelEntities(false, maxResults, firstResult);
    }

    private List<Documentmodel> findDocumentmodelEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Documentmodel.class));
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

    public Documentmodel findDocumentmodel(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Documentmodel.class, id);
        } finally {
            em.close();
        }
    }

    public int getDocumentmodelCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Documentmodel> rt = cq.from(Documentmodel.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
