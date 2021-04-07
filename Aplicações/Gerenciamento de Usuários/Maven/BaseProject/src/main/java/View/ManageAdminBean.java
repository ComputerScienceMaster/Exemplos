package View;

import Controllers.AdministratorJpaController;
import Controllers.exceptions.IllegalOrphanException;
import Controllers.exceptions.NonexistentEntityException;
import Model.Administrator;
import Model.UserAccount;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ManagedBean(name = "ManageAdminBean")
@SessionScoped
public class ManageAdminBean {
  
    private Administrator actualAdministrator = new Administrator();
    private List<Administrator> administrators = new ArrayList<>();
    private AdministratorJpaController controlAdmin = new AdministratorJpaController(EmProvider.getInstance().getEntityManagerFactory());

    public String gotoLogin() {
        return "/Private/DoLogin?faces-redirect=true";
    }

    public String finishEditAdministrator() throws Exception {
        controlAdmin.edit(actualAdministrator);
        actualAdministrator = new Administrator();
        loadAdmins();
        return "/Private/AdminModule/DashboardAdmin?faces-redirect=true";
    }

    public String gotoManageAdministrator() {
        administrators = controlAdmin.findAdministratorEntities();
        return "/Private/AdminModule/DashboardAdmin?faces-redirect=true";
    }
    
    public String gotoEditAdministrator() {
        return "/Private/AdminModule/EditAdministrator?faces-redirect=true";
    }

    public String gotoAddAdministrator() {
        actualAdministrator = new Administrator();
        return "/Private/AdminModule/AddAdmin?faces-redirect=true";
    }

    public String finishAddAdministrator() throws Exception {
        controlAdmin.create(actualAdministrator);
        actualAdministrator = new Administrator();
        administrators = controlAdmin.findAdministratorEntities();
        return "/Public/AdminModule/DashboardAdmin?faces-redirect=true";
    }

    public String removeAdministrator() throws NonexistentEntityException {
        controlAdmin.destroy(actualAdministrator.getLoginAdministrator());
        actualAdministrator = new Administrator();
        administrators = controlAdmin.findAdministratorEntities();
        return "/Private/AdminModule/DashboardAdmin?faces-redirect=true";
    }

    public void loadAdmins(){
        administrators = new ArrayList<>();
        administrators = controlAdmin.findAdministratorEntities();
    }
    
    public String gotoAddFileAdministrator() {
        actualAdministrator = controlAdmin.findAdministrator(actualAdministrator.getLoginAdministrator());
        return "/Admin/Welcome?faces-redirect=true";
    }

    public String validateUsernamePassword() {

        if (validateUser(actualAdministrator)) {
            HttpSession session = ManageSessions.getSession();
            session.setAttribute("username", actualAdministrator.getLoginAdministrator());
            return "/Private/Welcome?faces-redirect=true";
        } else {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Senha Incorreta",
                            "Por favor, tente novamente"));
            return "/Private/doLogin?faces-redirect=true";
        }
    }

    public boolean validateUser(Administrator comparar) {
        Administrator validate = controlAdmin.findAdministrator(actualAdministrator.getLoginAdministrator());
        if (validate == null) {
            return false;
        }
        if (validate.getLoginAdministrator().equals(comparar.getLoginAdministrator())
                && validate.getPasswordAdministrator().equals(comparar.getPasswordAdministrator())) {
            return true;
        }
        return false;
    }

    public String doLogon() {

        if (validateUser(actualAdministrator)) {
            HttpSession session = ManageSessions.getSession();
            session.setAttribute("usr", actualAdministrator);
            return "/Private/AdminModule/DashboardAdmin?faces-redirect=true";
        } else {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            " Usuario ou Senha Incorreta",
                            "Por favor, tente novamente"));
            return "#";
        }
    }

    public boolean validateUser(UserAccount comparar) {
        Administrator validate = controlAdmin.findAdministrator(actualAdministrator.getLoginAdministrator());
        if (validate == null) {
            return false;
        }
        if (validate.getLoginAdministrator().toUpperCase().equals(comparar.getLoginUser().toUpperCase())
                && validate.getPasswordAdministrator().equals(comparar.getPasswordUser())) {
            actualAdministrator = validate;
            return true;
        }
        return false;
    }

    public String logout() {
        HttpSession session = ManageSessions.getSession();
        session.invalidate();
        return "/Public/UserModule/LogoutMessage?faces-redirect=true";
    }

    
    //Gets and sets
    public Administrator getActualAdministrator() {
        return actualAdministrator;
    }

    public void setActualAdministrator(Administrator actualAdministrator) {
        this.actualAdministrator = actualAdministrator;
    }

    public List<Administrator> getAdministrator() {
        return administrators;
    }

    public void setAdministrator(List<Administrator> administrator) {
        this.administrators = administrator;
    }

    public AdministratorJpaController getControl() {
        return controlAdmin;
    }

    public void setControl(AdministratorJpaController control) {
        this.controlAdmin = control;
    }

}
