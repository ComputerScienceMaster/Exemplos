/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controler;

import Controler.exceptions.IllegalOrphanException;
import Controler.exceptions.NonexistentEntityException;
import Controler.exceptions.PreexistingEntityException;
import Model.Admin;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Model.Cause;
import java.util.ArrayList;
import java.util.List;
import Model.Client;
import Model.Company;
import Model.Trial;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author USER
 */
public class AdminJpaController implements Serializable {

    public AdminJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Admin admin) throws PreexistingEntityException, Exception {
        if (admin.getCauseList() == null) {
            admin.setCauseList(new ArrayList<Cause>());
        }
        if (admin.getClientList() == null) {
            admin.setClientList(new ArrayList<Client>());
        }
        if (admin.getCompanyList() == null) {
            admin.setCompanyList(new ArrayList<Company>());
        }
        if (admin.getTrialList() == null) {
            admin.setTrialList(new ArrayList<Trial>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Cause> attachedCauseList = new ArrayList<Cause>();
            for (Cause causeListCauseToAttach : admin.getCauseList()) {
                causeListCauseToAttach = em.getReference(causeListCauseToAttach.getClass(), causeListCauseToAttach.getIdCause());
                attachedCauseList.add(causeListCauseToAttach);
            }
            admin.setCauseList(attachedCauseList);
            List<Client> attachedClientList = new ArrayList<Client>();
            for (Client clientListClientToAttach : admin.getClientList()) {
                clientListClientToAttach = em.getReference(clientListClientToAttach.getClass(), clientListClientToAttach.getCpfRegister());
                attachedClientList.add(clientListClientToAttach);
            }
            admin.setClientList(attachedClientList);
            List<Company> attachedCompanyList = new ArrayList<Company>();
            for (Company companyListCompanyToAttach : admin.getCompanyList()) {
                companyListCompanyToAttach = em.getReference(companyListCompanyToAttach.getClass(), companyListCompanyToAttach.getCnpj());
                attachedCompanyList.add(companyListCompanyToAttach);
            }
            admin.setCompanyList(attachedCompanyList);
            List<Trial> attachedTrialList = new ArrayList<Trial>();
            for (Trial trialListTrialToAttach : admin.getTrialList()) {
                trialListTrialToAttach = em.getReference(trialListTrialToAttach.getClass(), trialListTrialToAttach.getIdTrial());
                attachedTrialList.add(trialListTrialToAttach);
            }
            admin.setTrialList(attachedTrialList);
            em.persist(admin);
            for (Cause causeListCause : admin.getCauseList()) {
                Admin oldAdminloginUserOfCauseListCause = causeListCause.getAdminloginUser();
                causeListCause.setAdminloginUser(admin);
                causeListCause = em.merge(causeListCause);
                if (oldAdminloginUserOfCauseListCause != null) {
                    oldAdminloginUserOfCauseListCause.getCauseList().remove(causeListCause);
                    oldAdminloginUserOfCauseListCause = em.merge(oldAdminloginUserOfCauseListCause);
                }
            }
            for (Client clientListClient : admin.getClientList()) {
                Admin oldAdminloginUserOfClientListClient = clientListClient.getAdminloginUser();
                clientListClient.setAdminloginUser(admin);
                clientListClient = em.merge(clientListClient);
                if (oldAdminloginUserOfClientListClient != null) {
                    oldAdminloginUserOfClientListClient.getClientList().remove(clientListClient);
                    oldAdminloginUserOfClientListClient = em.merge(oldAdminloginUserOfClientListClient);
                }
            }
            for (Company companyListCompany : admin.getCompanyList()) {
                Admin oldAdminloginUserOfCompanyListCompany = companyListCompany.getAdminloginUser();
                companyListCompany.setAdminloginUser(admin);
                companyListCompany = em.merge(companyListCompany);
                if (oldAdminloginUserOfCompanyListCompany != null) {
                    oldAdminloginUserOfCompanyListCompany.getCompanyList().remove(companyListCompany);
                    oldAdminloginUserOfCompanyListCompany = em.merge(oldAdminloginUserOfCompanyListCompany);
                }
            }
            for (Trial trialListTrial : admin.getTrialList()) {
                Admin oldUserloginUserOfTrialListTrial = trialListTrial.getUserloginUser();
                trialListTrial.setUserloginUser(admin);
                trialListTrial = em.merge(trialListTrial);
                if (oldUserloginUserOfTrialListTrial != null) {
                    oldUserloginUserOfTrialListTrial.getTrialList().remove(trialListTrial);
                    oldUserloginUserOfTrialListTrial = em.merge(oldUserloginUserOfTrialListTrial);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAdmin(admin.getLoginUser()) != null) {
                throw new PreexistingEntityException("Admin " + admin + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Admin admin) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Admin persistentAdmin = em.find(Admin.class, admin.getLoginUser());
            List<Cause> causeListOld = persistentAdmin.getCauseList();
            List<Cause> causeListNew = admin.getCauseList();
            List<Client> clientListOld = persistentAdmin.getClientList();
            List<Client> clientListNew = admin.getClientList();
            List<Company> companyListOld = persistentAdmin.getCompanyList();
            List<Company> companyListNew = admin.getCompanyList();
            List<Trial> trialListOld = persistentAdmin.getTrialList();
            List<Trial> trialListNew = admin.getTrialList();
            List<String> illegalOrphanMessages = null;
            for (Cause causeListOldCause : causeListOld) {
                if (!causeListNew.contains(causeListOldCause)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cause " + causeListOldCause + " since its adminloginUser field is not nullable.");
                }
            }
            for (Client clientListOldClient : clientListOld) {
                if (!clientListNew.contains(clientListOldClient)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Client " + clientListOldClient + " since its adminloginUser field is not nullable.");
                }
            }
            for (Company companyListOldCompany : companyListOld) {
                if (!companyListNew.contains(companyListOldCompany)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Company " + companyListOldCompany + " since its adminloginUser field is not nullable.");
                }
            }
            for (Trial trialListOldTrial : trialListOld) {
                if (!trialListNew.contains(trialListOldTrial)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Trial " + trialListOldTrial + " since its userloginUser field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Cause> attachedCauseListNew = new ArrayList<Cause>();
            for (Cause causeListNewCauseToAttach : causeListNew) {
                causeListNewCauseToAttach = em.getReference(causeListNewCauseToAttach.getClass(), causeListNewCauseToAttach.getIdCause());
                attachedCauseListNew.add(causeListNewCauseToAttach);
            }
            causeListNew = attachedCauseListNew;
            admin.setCauseList(causeListNew);
            List<Client> attachedClientListNew = new ArrayList<Client>();
            for (Client clientListNewClientToAttach : clientListNew) {
                clientListNewClientToAttach = em.getReference(clientListNewClientToAttach.getClass(), clientListNewClientToAttach.getCpfRegister());
                attachedClientListNew.add(clientListNewClientToAttach);
            }
            clientListNew = attachedClientListNew;
            admin.setClientList(clientListNew);
            List<Company> attachedCompanyListNew = new ArrayList<Company>();
            for (Company companyListNewCompanyToAttach : companyListNew) {
                companyListNewCompanyToAttach = em.getReference(companyListNewCompanyToAttach.getClass(), companyListNewCompanyToAttach.getCnpj());
                attachedCompanyListNew.add(companyListNewCompanyToAttach);
            }
            companyListNew = attachedCompanyListNew;
            admin.setCompanyList(companyListNew);
            List<Trial> attachedTrialListNew = new ArrayList<Trial>();
            for (Trial trialListNewTrialToAttach : trialListNew) {
                trialListNewTrialToAttach = em.getReference(trialListNewTrialToAttach.getClass(), trialListNewTrialToAttach.getIdTrial());
                attachedTrialListNew.add(trialListNewTrialToAttach);
            }
            trialListNew = attachedTrialListNew;
            admin.setTrialList(trialListNew);
            admin = em.merge(admin);
            for (Cause causeListNewCause : causeListNew) {
                if (!causeListOld.contains(causeListNewCause)) {
                    Admin oldAdminloginUserOfCauseListNewCause = causeListNewCause.getAdminloginUser();
                    causeListNewCause.setAdminloginUser(admin);
                    causeListNewCause = em.merge(causeListNewCause);
                    if (oldAdminloginUserOfCauseListNewCause != null && !oldAdminloginUserOfCauseListNewCause.equals(admin)) {
                        oldAdminloginUserOfCauseListNewCause.getCauseList().remove(causeListNewCause);
                        oldAdminloginUserOfCauseListNewCause = em.merge(oldAdminloginUserOfCauseListNewCause);
                    }
                }
            }
            for (Client clientListNewClient : clientListNew) {
                if (!clientListOld.contains(clientListNewClient)) {
                    Admin oldAdminloginUserOfClientListNewClient = clientListNewClient.getAdminloginUser();
                    clientListNewClient.setAdminloginUser(admin);
                    clientListNewClient = em.merge(clientListNewClient);
                    if (oldAdminloginUserOfClientListNewClient != null && !oldAdminloginUserOfClientListNewClient.equals(admin)) {
                        oldAdminloginUserOfClientListNewClient.getClientList().remove(clientListNewClient);
                        oldAdminloginUserOfClientListNewClient = em.merge(oldAdminloginUserOfClientListNewClient);
                    }
                }
            }
            for (Company companyListNewCompany : companyListNew) {
                if (!companyListOld.contains(companyListNewCompany)) {
                    Admin oldAdminloginUserOfCompanyListNewCompany = companyListNewCompany.getAdminloginUser();
                    companyListNewCompany.setAdminloginUser(admin);
                    companyListNewCompany = em.merge(companyListNewCompany);
                    if (oldAdminloginUserOfCompanyListNewCompany != null && !oldAdminloginUserOfCompanyListNewCompany.equals(admin)) {
                        oldAdminloginUserOfCompanyListNewCompany.getCompanyList().remove(companyListNewCompany);
                        oldAdminloginUserOfCompanyListNewCompany = em.merge(oldAdminloginUserOfCompanyListNewCompany);
                    }
                }
            }
            for (Trial trialListNewTrial : trialListNew) {
                if (!trialListOld.contains(trialListNewTrial)) {
                    Admin oldUserloginUserOfTrialListNewTrial = trialListNewTrial.getUserloginUser();
                    trialListNewTrial.setUserloginUser(admin);
                    trialListNewTrial = em.merge(trialListNewTrial);
                    if (oldUserloginUserOfTrialListNewTrial != null && !oldUserloginUserOfTrialListNewTrial.equals(admin)) {
                        oldUserloginUserOfTrialListNewTrial.getTrialList().remove(trialListNewTrial);
                        oldUserloginUserOfTrialListNewTrial = em.merge(oldUserloginUserOfTrialListNewTrial);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = admin.getLoginUser();
                if (findAdmin(id) == null) {
                    throw new NonexistentEntityException("The admin with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Admin admin;
            try {
                admin = em.getReference(Admin.class, id);
                admin.getLoginUser();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The admin with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Cause> causeListOrphanCheck = admin.getCauseList();
            for (Cause causeListOrphanCheckCause : causeListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Admin (" + admin + ") cannot be destroyed since the Cause " + causeListOrphanCheckCause + " in its causeList field has a non-nullable adminloginUser field.");
            }
            List<Client> clientListOrphanCheck = admin.getClientList();
            for (Client clientListOrphanCheckClient : clientListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Admin (" + admin + ") cannot be destroyed since the Client " + clientListOrphanCheckClient + " in its clientList field has a non-nullable adminloginUser field.");
            }
            List<Company> companyListOrphanCheck = admin.getCompanyList();
            for (Company companyListOrphanCheckCompany : companyListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Admin (" + admin + ") cannot be destroyed since the Company " + companyListOrphanCheckCompany + " in its companyList field has a non-nullable adminloginUser field.");
            }
            List<Trial> trialListOrphanCheck = admin.getTrialList();
            for (Trial trialListOrphanCheckTrial : trialListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Admin (" + admin + ") cannot be destroyed since the Trial " + trialListOrphanCheckTrial + " in its trialList field has a non-nullable userloginUser field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(admin);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Admin> findAdminEntities() {
        return findAdminEntities(true, -1, -1);
    }

    public List<Admin> findAdminEntities(int maxResults, int firstResult) {
        return findAdminEntities(false, maxResults, firstResult);
    }

    private List<Admin> findAdminEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Admin.class));
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

    public Admin findAdmin(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Admin.class, id);
        } finally {
            em.close();
        }
    }

    public int getAdminCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Admin> rt = cq.from(Admin.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
