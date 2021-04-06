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
import Model.Documentpart;
import java.util.ArrayList;
import java.util.List;
import Model.Document;
import Model.Cause;
import Model.Documentmodel;
import Model.Trial;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author USER
 */
public class TagJpaController implements Serializable {

    public TagJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tag tag) {
        if (tag.getDocumentpartList() == null) {
            tag.setDocumentpartList(new ArrayList<Documentpart>());
        }
        if (tag.getDocumentList() == null) {
            tag.setDocumentList(new ArrayList<Document>());
        }
        if (tag.getCauseList() == null) {
            tag.setCauseList(new ArrayList<Cause>());
        }
        if (tag.getDocumentmodelList() == null) {
            tag.setDocumentmodelList(new ArrayList<Documentmodel>());
        }
        if (tag.getTagList() == null) {
            tag.setTagList(new ArrayList<Tag>());
        }
        if (tag.getTrialList() == null) {
            tag.setTrialList(new ArrayList<Trial>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tag parentId = tag.getParentId();
            if (parentId != null) {
                parentId = em.getReference(parentId.getClass(), parentId.getIdTag());
                tag.setParentId(parentId);
            }
            List<Documentpart> attachedDocumentpartList = new ArrayList<Documentpart>();
            for (Documentpart documentpartListDocumentpartToAttach : tag.getDocumentpartList()) {
                documentpartListDocumentpartToAttach = em.getReference(documentpartListDocumentpartToAttach.getClass(), documentpartListDocumentpartToAttach.getIdDocumentPart());
                attachedDocumentpartList.add(documentpartListDocumentpartToAttach);
            }
            tag.setDocumentpartList(attachedDocumentpartList);
            List<Document> attachedDocumentList = new ArrayList<Document>();
            for (Document documentListDocumentToAttach : tag.getDocumentList()) {
                documentListDocumentToAttach = em.getReference(documentListDocumentToAttach.getClass(), documentListDocumentToAttach.getIdDocument());
                attachedDocumentList.add(documentListDocumentToAttach);
            }
            tag.setDocumentList(attachedDocumentList);
            List<Cause> attachedCauseList = new ArrayList<Cause>();
            for (Cause causeListCauseToAttach : tag.getCauseList()) {
                causeListCauseToAttach = em.getReference(causeListCauseToAttach.getClass(), causeListCauseToAttach.getIdCause());
                attachedCauseList.add(causeListCauseToAttach);
            }
            tag.setCauseList(attachedCauseList);
            List<Documentmodel> attachedDocumentmodelList = new ArrayList<Documentmodel>();
            for (Documentmodel documentmodelListDocumentmodelToAttach : tag.getDocumentmodelList()) {
                documentmodelListDocumentmodelToAttach = em.getReference(documentmodelListDocumentmodelToAttach.getClass(), documentmodelListDocumentmodelToAttach.getIdDocumentModel());
                attachedDocumentmodelList.add(documentmodelListDocumentmodelToAttach);
            }
            tag.setDocumentmodelList(attachedDocumentmodelList);
            List<Tag> attachedTagList = new ArrayList<Tag>();
            for (Tag tagListTagToAttach : tag.getTagList()) {
                tagListTagToAttach = em.getReference(tagListTagToAttach.getClass(), tagListTagToAttach.getIdTag());
                attachedTagList.add(tagListTagToAttach);
            }
            tag.setTagList(attachedTagList);
            List<Trial> attachedTrialList = new ArrayList<Trial>();
            for (Trial trialListTrialToAttach : tag.getTrialList()) {
                trialListTrialToAttach = em.getReference(trialListTrialToAttach.getClass(), trialListTrialToAttach.getIdTrial());
                attachedTrialList.add(trialListTrialToAttach);
            }
            tag.setTrialList(attachedTrialList);
            em.persist(tag);
            if (parentId != null) {
                parentId.getTagList().add(tag);
                parentId = em.merge(parentId);
            }
            for (Documentpart documentpartListDocumentpart : tag.getDocumentpartList()) {
                Tag oldPartidPartOfDocumentpartListDocumentpart = documentpartListDocumentpart.getPartidPart();
                documentpartListDocumentpart.setPartidPart(tag);
                documentpartListDocumentpart = em.merge(documentpartListDocumentpart);
                if (oldPartidPartOfDocumentpartListDocumentpart != null) {
                    oldPartidPartOfDocumentpartListDocumentpart.getDocumentpartList().remove(documentpartListDocumentpart);
                    oldPartidPartOfDocumentpartListDocumentpart = em.merge(oldPartidPartOfDocumentpartListDocumentpart);
                }
            }
            for (Document documentListDocument : tag.getDocumentList()) {
                Tag oldTagidTagOfDocumentListDocument = documentListDocument.getTagidTag();
                documentListDocument.setTagidTag(tag);
                documentListDocument = em.merge(documentListDocument);
                if (oldTagidTagOfDocumentListDocument != null) {
                    oldTagidTagOfDocumentListDocument.getDocumentList().remove(documentListDocument);
                    oldTagidTagOfDocumentListDocument = em.merge(oldTagidTagOfDocumentListDocument);
                }
            }
            for (Cause causeListCause : tag.getCauseList()) {
                Tag oldTagidTagOfCauseListCause = causeListCause.getTagidTag();
                causeListCause.setTagidTag(tag);
                causeListCause = em.merge(causeListCause);
                if (oldTagidTagOfCauseListCause != null) {
                    oldTagidTagOfCauseListCause.getCauseList().remove(causeListCause);
                    oldTagidTagOfCauseListCause = em.merge(oldTagidTagOfCauseListCause);
                }
            }
            for (Documentmodel documentmodelListDocumentmodel : tag.getDocumentmodelList()) {
                Tag oldTagidTagOfDocumentmodelListDocumentmodel = documentmodelListDocumentmodel.getTagidTag();
                documentmodelListDocumentmodel.setTagidTag(tag);
                documentmodelListDocumentmodel = em.merge(documentmodelListDocumentmodel);
                if (oldTagidTagOfDocumentmodelListDocumentmodel != null) {
                    oldTagidTagOfDocumentmodelListDocumentmodel.getDocumentmodelList().remove(documentmodelListDocumentmodel);
                    oldTagidTagOfDocumentmodelListDocumentmodel = em.merge(oldTagidTagOfDocumentmodelListDocumentmodel);
                }
            }
            for (Tag tagListTag : tag.getTagList()) {
                Tag oldParentIdOfTagListTag = tagListTag.getParentId();
                tagListTag.setParentId(tag);
                tagListTag = em.merge(tagListTag);
                if (oldParentIdOfTagListTag != null) {
                    oldParentIdOfTagListTag.getTagList().remove(tagListTag);
                    oldParentIdOfTagListTag = em.merge(oldParentIdOfTagListTag);
                }
            }
            for (Trial trialListTrial : tag.getTrialList()) {
                Tag oldTagidTagOfTrialListTrial = trialListTrial.getTagidTag();
                trialListTrial.setTagidTag(tag);
                trialListTrial = em.merge(trialListTrial);
                if (oldTagidTagOfTrialListTrial != null) {
                    oldTagidTagOfTrialListTrial.getTrialList().remove(trialListTrial);
                    oldTagidTagOfTrialListTrial = em.merge(oldTagidTagOfTrialListTrial);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tag tag) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tag persistentTag = em.find(Tag.class, tag.getIdTag());
            Tag parentIdOld = persistentTag.getParentId();
            Tag parentIdNew = tag.getParentId();
            List<Documentpart> documentpartListOld = persistentTag.getDocumentpartList();
            List<Documentpart> documentpartListNew = tag.getDocumentpartList();
            List<Document> documentListOld = persistentTag.getDocumentList();
            List<Document> documentListNew = tag.getDocumentList();
            List<Cause> causeListOld = persistentTag.getCauseList();
            List<Cause> causeListNew = tag.getCauseList();
            List<Documentmodel> documentmodelListOld = persistentTag.getDocumentmodelList();
            List<Documentmodel> documentmodelListNew = tag.getDocumentmodelList();
            List<Tag> tagListOld = persistentTag.getTagList();
            List<Tag> tagListNew = tag.getTagList();
            List<Trial> trialListOld = persistentTag.getTrialList();
            List<Trial> trialListNew = tag.getTrialList();
            List<String> illegalOrphanMessages = null;
            for (Documentpart documentpartListOldDocumentpart : documentpartListOld) {
                if (!documentpartListNew.contains(documentpartListOldDocumentpart)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Documentpart " + documentpartListOldDocumentpart + " since its partidPart field is not nullable.");
                }
            }
            for (Document documentListOldDocument : documentListOld) {
                if (!documentListNew.contains(documentListOldDocument)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Document " + documentListOldDocument + " since its tagidTag field is not nullable.");
                }
            }
            for (Cause causeListOldCause : causeListOld) {
                if (!causeListNew.contains(causeListOldCause)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cause " + causeListOldCause + " since its tagidTag field is not nullable.");
                }
            }
            for (Documentmodel documentmodelListOldDocumentmodel : documentmodelListOld) {
                if (!documentmodelListNew.contains(documentmodelListOldDocumentmodel)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Documentmodel " + documentmodelListOldDocumentmodel + " since its tagidTag field is not nullable.");
                }
            }
            for (Trial trialListOldTrial : trialListOld) {
                if (!trialListNew.contains(trialListOldTrial)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Trial " + trialListOldTrial + " since its tagidTag field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (parentIdNew != null) {
                parentIdNew = em.getReference(parentIdNew.getClass(), parentIdNew.getIdTag());
                tag.setParentId(parentIdNew);
            }
            List<Documentpart> attachedDocumentpartListNew = new ArrayList<Documentpart>();
            for (Documentpart documentpartListNewDocumentpartToAttach : documentpartListNew) {
                documentpartListNewDocumentpartToAttach = em.getReference(documentpartListNewDocumentpartToAttach.getClass(), documentpartListNewDocumentpartToAttach.getIdDocumentPart());
                attachedDocumentpartListNew.add(documentpartListNewDocumentpartToAttach);
            }
            documentpartListNew = attachedDocumentpartListNew;
            tag.setDocumentpartList(documentpartListNew);
            List<Document> attachedDocumentListNew = new ArrayList<Document>();
            for (Document documentListNewDocumentToAttach : documentListNew) {
                documentListNewDocumentToAttach = em.getReference(documentListNewDocumentToAttach.getClass(), documentListNewDocumentToAttach.getIdDocument());
                attachedDocumentListNew.add(documentListNewDocumentToAttach);
            }
            documentListNew = attachedDocumentListNew;
            tag.setDocumentList(documentListNew);
            List<Cause> attachedCauseListNew = new ArrayList<Cause>();
            for (Cause causeListNewCauseToAttach : causeListNew) {
                causeListNewCauseToAttach = em.getReference(causeListNewCauseToAttach.getClass(), causeListNewCauseToAttach.getIdCause());
                attachedCauseListNew.add(causeListNewCauseToAttach);
            }
            causeListNew = attachedCauseListNew;
            tag.setCauseList(causeListNew);
            List<Documentmodel> attachedDocumentmodelListNew = new ArrayList<Documentmodel>();
            for (Documentmodel documentmodelListNewDocumentmodelToAttach : documentmodelListNew) {
                documentmodelListNewDocumentmodelToAttach = em.getReference(documentmodelListNewDocumentmodelToAttach.getClass(), documentmodelListNewDocumentmodelToAttach.getIdDocumentModel());
                attachedDocumentmodelListNew.add(documentmodelListNewDocumentmodelToAttach);
            }
            documentmodelListNew = attachedDocumentmodelListNew;
            tag.setDocumentmodelList(documentmodelListNew);
            List<Tag> attachedTagListNew = new ArrayList<Tag>();
            for (Tag tagListNewTagToAttach : tagListNew) {
                tagListNewTagToAttach = em.getReference(tagListNewTagToAttach.getClass(), tagListNewTagToAttach.getIdTag());
                attachedTagListNew.add(tagListNewTagToAttach);
            }
            tagListNew = attachedTagListNew;
            tag.setTagList(tagListNew);
            List<Trial> attachedTrialListNew = new ArrayList<Trial>();
            for (Trial trialListNewTrialToAttach : trialListNew) {
                trialListNewTrialToAttach = em.getReference(trialListNewTrialToAttach.getClass(), trialListNewTrialToAttach.getIdTrial());
                attachedTrialListNew.add(trialListNewTrialToAttach);
            }
            trialListNew = attachedTrialListNew;
            tag.setTrialList(trialListNew);
            tag = em.merge(tag);
            if (parentIdOld != null && !parentIdOld.equals(parentIdNew)) {
                parentIdOld.getTagList().remove(tag);
                parentIdOld = em.merge(parentIdOld);
            }
            if (parentIdNew != null && !parentIdNew.equals(parentIdOld)) {
                parentIdNew.getTagList().add(tag);
                parentIdNew = em.merge(parentIdNew);
            }
            for (Documentpart documentpartListNewDocumentpart : documentpartListNew) {
                if (!documentpartListOld.contains(documentpartListNewDocumentpart)) {
                    Tag oldPartidPartOfDocumentpartListNewDocumentpart = documentpartListNewDocumentpart.getPartidPart();
                    documentpartListNewDocumentpart.setPartidPart(tag);
                    documentpartListNewDocumentpart = em.merge(documentpartListNewDocumentpart);
                    if (oldPartidPartOfDocumentpartListNewDocumentpart != null && !oldPartidPartOfDocumentpartListNewDocumentpart.equals(tag)) {
                        oldPartidPartOfDocumentpartListNewDocumentpart.getDocumentpartList().remove(documentpartListNewDocumentpart);
                        oldPartidPartOfDocumentpartListNewDocumentpart = em.merge(oldPartidPartOfDocumentpartListNewDocumentpart);
                    }
                }
            }
            for (Document documentListNewDocument : documentListNew) {
                if (!documentListOld.contains(documentListNewDocument)) {
                    Tag oldTagidTagOfDocumentListNewDocument = documentListNewDocument.getTagidTag();
                    documentListNewDocument.setTagidTag(tag);
                    documentListNewDocument = em.merge(documentListNewDocument);
                    if (oldTagidTagOfDocumentListNewDocument != null && !oldTagidTagOfDocumentListNewDocument.equals(tag)) {
                        oldTagidTagOfDocumentListNewDocument.getDocumentList().remove(documentListNewDocument);
                        oldTagidTagOfDocumentListNewDocument = em.merge(oldTagidTagOfDocumentListNewDocument);
                    }
                }
            }
            for (Cause causeListNewCause : causeListNew) {
                if (!causeListOld.contains(causeListNewCause)) {
                    Tag oldTagidTagOfCauseListNewCause = causeListNewCause.getTagidTag();
                    causeListNewCause.setTagidTag(tag);
                    causeListNewCause = em.merge(causeListNewCause);
                    if (oldTagidTagOfCauseListNewCause != null && !oldTagidTagOfCauseListNewCause.equals(tag)) {
                        oldTagidTagOfCauseListNewCause.getCauseList().remove(causeListNewCause);
                        oldTagidTagOfCauseListNewCause = em.merge(oldTagidTagOfCauseListNewCause);
                    }
                }
            }
            for (Documentmodel documentmodelListNewDocumentmodel : documentmodelListNew) {
                if (!documentmodelListOld.contains(documentmodelListNewDocumentmodel)) {
                    Tag oldTagidTagOfDocumentmodelListNewDocumentmodel = documentmodelListNewDocumentmodel.getTagidTag();
                    documentmodelListNewDocumentmodel.setTagidTag(tag);
                    documentmodelListNewDocumentmodel = em.merge(documentmodelListNewDocumentmodel);
                    if (oldTagidTagOfDocumentmodelListNewDocumentmodel != null && !oldTagidTagOfDocumentmodelListNewDocumentmodel.equals(tag)) {
                        oldTagidTagOfDocumentmodelListNewDocumentmodel.getDocumentmodelList().remove(documentmodelListNewDocumentmodel);
                        oldTagidTagOfDocumentmodelListNewDocumentmodel = em.merge(oldTagidTagOfDocumentmodelListNewDocumentmodel);
                    }
                }
            }
            for (Tag tagListOldTag : tagListOld) {
                if (!tagListNew.contains(tagListOldTag)) {
                    tagListOldTag.setParentId(null);
                    tagListOldTag = em.merge(tagListOldTag);
                }
            }
            for (Tag tagListNewTag : tagListNew) {
                if (!tagListOld.contains(tagListNewTag)) {
                    Tag oldParentIdOfTagListNewTag = tagListNewTag.getParentId();
                    tagListNewTag.setParentId(tag);
                    tagListNewTag = em.merge(tagListNewTag);
                    if (oldParentIdOfTagListNewTag != null && !oldParentIdOfTagListNewTag.equals(tag)) {
                        oldParentIdOfTagListNewTag.getTagList().remove(tagListNewTag);
                        oldParentIdOfTagListNewTag = em.merge(oldParentIdOfTagListNewTag);
                    }
                }
            }
            for (Trial trialListNewTrial : trialListNew) {
                if (!trialListOld.contains(trialListNewTrial)) {
                    Tag oldTagidTagOfTrialListNewTrial = trialListNewTrial.getTagidTag();
                    trialListNewTrial.setTagidTag(tag);
                    trialListNewTrial = em.merge(trialListNewTrial);
                    if (oldTagidTagOfTrialListNewTrial != null && !oldTagidTagOfTrialListNewTrial.equals(tag)) {
                        oldTagidTagOfTrialListNewTrial.getTrialList().remove(trialListNewTrial);
                        oldTagidTagOfTrialListNewTrial = em.merge(oldTagidTagOfTrialListNewTrial);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tag.getIdTag();
                if (findTag(id) == null) {
                    throw new NonexistentEntityException("The tag with id " + id + " no longer exists.");
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
            Tag tag;
            try {
                tag = em.getReference(Tag.class, id);
                tag.getIdTag();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tag with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Documentpart> documentpartListOrphanCheck = tag.getDocumentpartList();
            for (Documentpart documentpartListOrphanCheckDocumentpart : documentpartListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Tag (" + tag + ") cannot be destroyed since the Documentpart " + documentpartListOrphanCheckDocumentpart + " in its documentpartList field has a non-nullable partidPart field.");
            }
            List<Document> documentListOrphanCheck = tag.getDocumentList();
            for (Document documentListOrphanCheckDocument : documentListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Tag (" + tag + ") cannot be destroyed since the Document " + documentListOrphanCheckDocument + " in its documentList field has a non-nullable tagidTag field.");
            }
            List<Cause> causeListOrphanCheck = tag.getCauseList();
            for (Cause causeListOrphanCheckCause : causeListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Tag (" + tag + ") cannot be destroyed since the Cause " + causeListOrphanCheckCause + " in its causeList field has a non-nullable tagidTag field.");
            }
            List<Documentmodel> documentmodelListOrphanCheck = tag.getDocumentmodelList();
            for (Documentmodel documentmodelListOrphanCheckDocumentmodel : documentmodelListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Tag (" + tag + ") cannot be destroyed since the Documentmodel " + documentmodelListOrphanCheckDocumentmodel + " in its documentmodelList field has a non-nullable tagidTag field.");
            }
            List<Trial> trialListOrphanCheck = tag.getTrialList();
            for (Trial trialListOrphanCheckTrial : trialListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Tag (" + tag + ") cannot be destroyed since the Trial " + trialListOrphanCheckTrial + " in its trialList field has a non-nullable tagidTag field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Tag parentId = tag.getParentId();
            if (parentId != null) {
                parentId.getTagList().remove(tag);
                parentId = em.merge(parentId);
            }
            List<Tag> tagList = tag.getTagList();
            for (Tag tagListTag : tagList) {
                tagListTag.setParentId(null);
                tagListTag = em.merge(tagListTag);
            }
            em.remove(tag);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tag> findTagEntities() {
        return findTagEntities(true, -1, -1);
    }

    public List<Tag> findTagEntities(int maxResults, int firstResult) {
        return findTagEntities(false, maxResults, firstResult);
    }

    private List<Tag> findTagEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tag.class));
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

    public Tag findTag(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tag.class, id);
        } finally {
            em.close();
        }
    }

    public int getTagCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tag> rt = cq.from(Tag.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
