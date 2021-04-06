package View;

import Controllers.UserAccountJpaController;
import Controllers.exceptions.IllegalOrphanException;
import Controllers.exceptions.NonexistentEntityException;
import Model.UserAccount;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpSession;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@ManagedBean(name = "ManageUserBean")
@SessionScoped
public class ManageUserBean {

    public ManageUserBean() {
    }

    private UserAccount actualUser = new UserAccount();
    private UserAccount loginUser = new UserAccount();
    private List<UserAccount> listUsers = new ArrayList<>();
    private UserAccountJpaController controlUser = new UserAccountJpaController(EmProvider.getInstance().getEntityManagerFactory());
    private String filter;
    private boolean validUser;

    public String gotoEditarUsuario(String origem) {
        if ("Admin".equals(origem)) {
            return "/Private/AdminModule/editAdmin?faces-redirect=true";
        } else {
            HttpSession session = ManageSessions.getSession();
            actualUser = (UserAccount) session.getAttribute("usr");
            return "/Public/UserModule/EditUser?faces-redirect=true";
        }

    }

    public String gotoManageUser() {
        allUsers();
        return "/Admin/GerenciarPaginas/GerenciarUsuario?faces-redirect=true";
    }

    public String gotoShowUserDetails() {
        return "/Public/exibirDetalhesUsuario?faces-redirect=true";
    }

    public String gotoDashboardUser() {
        return "/Public/UserModule/DashboardUser?faces-redirect=true";
    }

    public String gotoShowListOfUsers() {
        allUsers();
        return "/Public/exibirListaUser?faces-redirect=true";
    }

    public String gotoShowImage() {
        return "/Visualizador/VisualizarImagemAdmin?faces-redirect=true";
    }

    public String finishAddUserPicture() {
        return "/Admin/GerenciarPaginas/GerenciarUsuario?faces-redirect=true";
    }

    //Find
    public List<UserAccount> allUsers() {
        EntityManager em = EmProvider.getInstance().getEntityManagerFactory().createEntityManager();
        listUsers = controlUser.findUserAccountEntities();
        return listUsers;
    }

    public void loadUsers() {
        allUsers();
    }

    //create
    public String gotoAddUsers() {
        loginUser = new UserAccount();
        return "/AddUser.xhtml?faces-redirect=true";
    }

    public String finishAddUser(String origin) {
        try {
            controlUser.create(actualUser);
        } catch (Exception ex) {
            Logger.getLogger(ManageUserBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        loginUser = actualUser;
        doLogon();

        if ("Admin".equals(origin)) {
            return "/Private/AdminModule/DashboardAdmin?faces-redirect=true";
        } else {
            return "/Public/UserModule/DashboardUser?faces-redirect=true";
        }
    }

    public StreamedContent getActualImage() throws IOException {
        if (actualUser != null && actualUser.getImageUser() != null) {
            FacesContext context = FacesContext.getCurrentInstance();
            if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
                return new DefaultStreamedContent();
            } else {
                return new DefaultStreamedContent(new ByteArrayInputStream(actualUser.getImageUser()));
            }
        }
        return null;
    }

    public void uploadUserImage(FileUploadEvent event) {
        actualUser.setImageUser(event.getFile().getContents());
    }

    public String gotoAddUserFile() {
        actualUser = controlUser.findUserAccount(actualUser.getIdUserAccount());
        return "/Admin/AdicionarPaginas/AdicionarArquivoUsuario?faces-redirect=true";
    }

    // edit
    public String finishEditUser(String origin) {
        try {
            controlUser.edit(actualUser);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ManageUserBean.class.getName()).log(Level.SEVERE, null, ex);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Falha :("));
        } catch (Exception ex) {
            Logger.getLogger(ManageUserBean.class.getName()).log(Level.SEVERE, null, ex);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Falha!"));
        }
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Sucesso!"));
        actualUser = controlUser.findUserAccount(actualUser.getIdUserAccount());
        if (null != origin) {
            switch (origin) {
                case "Admin":
                    return "/Admin/GerenciarPaginas/GerenciarUsuario?faces-redirect=true";
                case "Public":
                    return "/Public/UserModule/DashboardUser?faces-redirect=true";
                default:
                    return "/Public/UserModule/DashboardUser?faces-redirect=true";
            }
        }

        return null;

    }

    public String doLogon() {

        if (validateUser(loginUser)) {
            HttpSession session = ManageSessions.getSession();
            session.setAttribute("userLogin", loginUser.getLoginUser());
            session.setAttribute("userId", loginUser.getIdUserAccount());
            session.setAttribute("userName", loginUser.getNameUser());
            return "/Public/UserModule/DashboardUser?faces-redirect=true";
        } else {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            " Usuario ou Senha Incorreta",
                            "Por favor, tente novamente"));
            return "/Public/Index";
        }
    }

    public void loadUserInSession() {
        actualUser = ManageSessions.loadUserInSession();
    }

    public String doLogoff() {
        HttpSession session = ManageSessions.getSession();
        session.invalidate();
        return "/Public/UserModule/LogoutMessage?faces-redirect=true";
    }

    public boolean validateUser(UserAccount comparar) {
        UserAccount validate = findUserByLogin(comparar.getLoginUser());
        if (validate == null) {
            return false;
        }
        if (validate.getLoginUser().toUpperCase().equals(comparar.getLoginUser().toUpperCase())
                && validate.getPasswordUser().equals(comparar.getPasswordUser())) {
            loginUser = validate;
            return true;
        }
        return false;
    }

    public void isUserAvaliable() {
        UserAccount userReturned = findUserByLogin(actualUser.getLoginUser());
        if (userReturned == null) {
            validUser = true;
        } else {
            validUser = false;
        }
    }

    public UserAccount findUserByLogin(String login) {
        EntityManager em = EmProvider.getInstance().getEntityManagerFactory().createEntityManager();
        Query qry = em.createNamedQuery("UserAccount.findByLoginUser");
        qry.setParameter("loginUser", login);
        UserAccount userReturned = null;
        try {
            userReturned = (UserAccount) qry.getSingleResult();
        } catch (Exception e) {
            System.out.println("usuário disponível");
        }
        return userReturned;
    }

    //Excluir
    public String deleteUser() throws IllegalOrphanException, NonexistentEntityException {
        //PrefeitoJpaController controlUser = new PrefeitoJpaController(EmProvider.getInstance().getEntityManagerFactory());
        controlUser.destroy(actualUser.getIdUserAccount());
        actualUser = new UserAccount();
        allUsers();
        return "/Admin/GerenciarPaginas/GerenciarUsuario?faces-redirect=true";
    }

    public StreamedContent getAsImage() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            return new DefaultStreamedContent();
        } else {
            String id = ManageSessions.getLoginUser();
            UserAccount p = controlUser.findUserAccount(ManageSessions.getUserId());
            return new DefaultStreamedContent(new ByteArrayInputStream(p.getImageUser()));
        }
    }

    public UserAccount getActualUser() {
        return actualUser;
    }

    public void setActualUser(UserAccount actualUser) {
        this.actualUser = actualUser;
    }

    public UserAccount getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(UserAccount loginUser) {
        this.loginUser = loginUser;
    }

    public List<UserAccount> getListUsers() {
        return listUsers;
    }

    public void setListUsers(List<UserAccount> listUsers) {
        this.listUsers = listUsers;
    }

    public UserAccountJpaController getControlUser() {
        return controlUser;
    }

    public void setControlUser(UserAccountJpaController controlUser) {
        this.controlUser = controlUser;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public boolean isValidUser() {
        return validUser;
    }

    public void setValidUser(boolean validUser) {
        this.validUser = validUser;
    }

}
