package View;

import Controler.ClientJpaController;
import Controler.CompanyJpaController;
import Controler.AdminJpaController;
import Utilitarios.EmProvider;
import Controler.exceptions.NonexistentEntityException;
import Model.Client;
import Model.Company;
import Model.Admin;
import Utilitarios.GetCEP;
import static java.awt.SystemColor.control;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.ManagedBean;
import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.Query;

@ManagedBean
@RequestScoped
public class ManageClientManagedBean {

    public ManageClientManagedBean() {
    }

    private Client actualClient = new Client();
    private Company actualCompany = new Company();
    private List<Client> listClients = new ArrayList<>();
    private List<Company> listCompany = new ArrayList<>();

    private ClientJpaController controlClient = new ClientJpaController(EmProvider.getInstance().getEntityManagerFactory());
    private CompanyJpaController controlCompany = new CompanyJpaController(EmProvider.getInstance().getEntityManagerFactory());
    private AdminJpaController controlUser = new AdminJpaController(EmProvider.getInstance().getEntityManagerFactory());

    private String filter;
    private String filterCompany;
    private String typeOfPerson;

    //goto
    public String goToAddClients() {
        return "/Public/ClientModule/AddClient?faces-redirect=true";
    }

    public String goToEditClient() {
        typeOfPerson = "newPF";
        return "/Public/ClientModule/EditClient?faces-redirect=true";
    }
    
    public String goToEditCompany() {
        typeOfPerson = "newPJ";
        return "/Public/ClientModule/EditClient?faces-redirect=true";
    }

    public String goToManageClients() {
        getAllClients();
        getAllCompanys();
        return "/Public/ClientModule/ManageClients?faces-redirect=true";
    }

    public boolean isPfSelected() {
        if ("newPF".equals(typeOfPerson)) {
            return true;
        }
        return false;
    }

    public boolean isPjSelected() {
        if ("newPJ".equals(typeOfPerson)) {
            return true;
        }
        return false;
    }

    public void findCep() {
        String cep = actualClient.getCep();

        GetCEP c = new GetCEP();
        try {
            actualClient.setCity(c.getCidade(cep));
            actualClient.setState(c.getUF(cep));
            actualClient.setAddress1(c.getEndereco(cep));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void findCepCompany() {
        String cep = actualCompany.getCep();

        GetCEP c = new GetCEP();
        try {
            actualCompany.setCity(c.getCidade(cep));
            actualCompany.setState(c.getUF(cep));
            actualCompany.setAddress1(c.getEndereco(cep));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Find
    public void filterClientsByName() {
        listClients.clear();

        List<Client> result = new ArrayList<>();

        EntityManager em = EmProvider.getInstance().getEntityManagerFactory().createEntityManager();
        Query q = em.createQuery("SELECT t FROM Client t WHERE t.fullName LIKE :fullName", Client.class
        );
        q.setParameter("fullName", "%" + filter + "%");
        result = q.getResultList();
        listClients = result;
    }

    public void filterCompanyByName() {
        listCompany.clear();

        List<Company> result = new ArrayList<>();

        EntityManager em = EmProvider.getInstance().getEntityManagerFactory().createEntityManager();
        Query q = em.createQuery("SELECT t FROM Company t WHERE t.name LIKE :name", Company.class);
        q.setParameter("name", "%" + filterCompany + "%");
        result = q.getResultList();
        listCompany = result;
    }

    public List<Client> getAllClients() {
        listClients.clear();
        EntityManager em = EmProvider.getInstance().getEntityManagerFactory().createEntityManager();
        listClients = controlClient.findClientEntities();
        return listClients;
    }
    
    public List<Company> getAllCompanys() {
        listCompany.clear();
        EntityManager em = EmProvider.getInstance().getEntityManagerFactory().createEntityManager();
        listCompany = controlCompany.findCompanyEntities();
        return listCompany;
    }

    public void carregarUsuarios() {
        getAllClients();
    }

    public void clear() {
        actualClient = new Client();
    }

    public String finishAddClient() {
        try {
            Admin actualUser = controlUser.findAdmin(ManageSession.getUserName());
            actualClient.setAdminloginUser(actualUser);
            controlClient.create(actualClient);
            actualClient = new Client();
            getAllClients();
            return "/Public/ClientModule/ManageClients";
        } catch (Exception ex) {
            Logger.getLogger(ManageClientManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "/Public/ErrorPages/ErrorIncluding";
    }

    // edit
    public String finishEditClient() {
        try {
            controlClient.edit(actualClient);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ManageClientManagedBean.class.getName()).log(Level.SEVERE, null, ex);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Falha :("));
        } catch (Exception ex) {
            Logger.getLogger(ManageClientManagedBean.class.getName()).log(Level.SEVERE, null, ex);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Falha!"));
        }
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Sucesso!"));
        actualClient = controlClient.findClient(actualClient.getCpfRegister());
        return "/Public/ClientModule/ManageClients";

    }

    //Excluir
    public String removeClient() {
        try {
            controlClient.destroy(actualClient.getCpfRegister());
            actualClient = new Client();
            getAllClients();
            return "/Public/ClientModule/ManageClients";
        } catch (Exception e) {
            e.printStackTrace();
            return "/Public/ErrorPages/ErrorDeleting";
        }

    }

    public String finishAddCompany() {
        try {
            Admin actualUser = controlUser.findAdmin(ManageSession.getUserName());
            actualCompany.setAdminloginUser(actualUser);
            controlCompany.create(actualCompany);
            actualCompany = new Company();
            getAllClients();
            return "/Public/ClientModule/ManageClients";
        } catch (Exception ex) {
            Logger.getLogger(ManageClientManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "/Public/ErrorPages/ErrorIncluding";
    }

    // edit
    public String finishEditCompany() {
        try {
            controlCompany.edit(actualCompany);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ManageClientManagedBean.class.getName()).log(Level.SEVERE, null, ex);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Falha :("));
        } catch (Exception ex) {
            Logger.getLogger(ManageClientManagedBean.class.getName()).log(Level.SEVERE, null, ex);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Falha!"));
        }
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Sucesso!"));
        actualCompany = controlCompany.findCompany(actualCompany.getCnpj());
        return "/Public/ClientModule/ManageClients";

    }

    //Excluir
    public String removeCompany() {
        try {
            controlCompany.destroy(actualCompany.getCnpj());
            actualCompany = new Company();
            getAllClients();
            return "/Public/ClientModule/ManageClients";
        } catch (Exception e) {
            e.printStackTrace();
            return "/Public/ErrorPages/ErrorDeleting";
        }

    }

    public Client getActualClient() {
        return actualClient;
    }

    public void setActualClient(Client actualClient) {
        this.actualClient = actualClient;
    }

    public List<Client> getListClients() {
        return listClients;
    }

    public void setListClients(List<Client> listClients) {
        this.listClients = listClients;
    }

    public ClientJpaController getControlClient() {
        return controlClient;
    }

    public void setControlClient(ClientJpaController controlClient) {
        this.controlClient = controlClient;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getTypeOfPerson() {
        return typeOfPerson;
    }

    public void setTypeOfPerson(String typeOfPerson) {
        this.typeOfPerson = typeOfPerson;
    }

    public Company getActualCompany() {
        return actualCompany;
    }

    public void setActualCompany(Company actualCompany) {
        this.actualCompany = actualCompany;
    }

    public String getFilterCompany() {
        return filterCompany;
    }

    public void setFilterCompany(String filterCompany) {
        this.filterCompany = filterCompany;
    }

    public List<Company> getListCompany() {
        return listCompany;
    }

    public void setListCompany(List<Company> listCompany) {
        this.listCompany = listCompany;
    }
}
