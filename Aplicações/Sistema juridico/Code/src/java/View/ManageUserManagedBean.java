package View;

import Utilitarios.EmProvider;
import Controler.AdminJpaController;
import Controler.exceptions.IllegalOrphanException;
import Controler.exceptions.NonexistentEntityException;
import Model.Admin;
import Utilitarios.GetCEP;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

public class ManageUserManagedBean {

    public ManageUserManagedBean() {
    }

    private Admin actualUser = new Admin();
    private Admin loginUser = new Admin();
    private List<Admin> listUsers = new ArrayList<>();
    private AdminJpaController controlUser = new AdminJpaController(EmProvider.getInstance().getEntityManagerFactory());
    private String filter;
    private boolean validUser;

    public String gotoEditarUsuario(String origem) {
        if ("Admin".equals(origem)) {
            return "/Admin/EditarPaginas/EditUser?faces-redirect=true";
        } else {
            HttpSession session = ManageSession.getSession();
            actualUser = (Admin) session.getAttribute("usr");
            return "/Public/UserModule/EditUser?faces-redirect=true";
        }

    }

    public String gotoGerenciarUsuario() {
        //SecretariaJpaController controlUser = new SecretariaJpaController(EmProvider.getInstance().getEntityManagerFactory());
        allUsuarios();
        return "/Admin/GerenciarPaginas/GerenciarUsuario?faces-redirect=true";
    }

    public String gotoExibirDetalhesUsuario() {
        return "/Public/exibirDetalhesUsuario?faces-redirect=true";
    }

    public String gotoPerfilUsuarioMobile() {
        carregarUsuarioNaSessao();
        return "/PublicMobile/PerfilUsuarioMobile?faces-redirect=true";
    }

    public String gotoExibirDetalhesUsuarioMobile() {
        return "/PublicMobile/exibirDetalheUsuarioMobile?faces-redirect=true";
    }

    public String gotoVisualizarImagemMobile() {
        return "/Visualizador/VisualizarImagemUsuarioMobile?faces-redirect=true";
    }

    public String gotoPrincipalMobile() {
        return "/PublicMobile/PrincipalMobile?faces-redirect=true";
    }

    public String gotoCadastrarNovoUsuario() {
        return "/PublicMobile/CadastrarNovoUsuarioMobile?faces-redirect=true";
    }

    public String gotoDashboardUser() {
        return "/Public/UserModule/DashboardUser?faces-redirect=true";
    }

    public String gotoExibirListaUsuario() {
        allUsuarios();
        return "/Public/exibirListaUser?faces-redirect=true";
    }

    public String gotoVisualizarImagem() {
        return "/Visualizador/VisualizarImagemAdmin?faces-redirect=true";
    }

    public String finalizarAdicaoFotosUsuario() {
        return "/Admin/GerenciarPaginas/GerenciarUsuario?faces-redirect=true";
    }

    public void findCep() {
        String cep = actualUser.getCep();
        GetCEP c = new GetCEP();
        try {
            actualUser.setCity(c.getCidade(cep));
            actualUser.setState(c.getUF(cep));
            actualUser.setAddress1(c.getEndereco(cep));
            actualUser.setComplement(c.getBairro(cep));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Find
    public List<Admin> allUsuarios() {
        EntityManager em = EmProvider.getInstance().getEntityManagerFactory().createEntityManager();
        listUsers = controlUser.findAdminEntities();
        return listUsers;
    }

    public void carregarUsuarios() {
        allUsuarios();
    }

    //create
    public String gotoAdicionarUsuario(String Origem) {
        actualUser = new Admin();
        loginUser = new Admin();
        if ("Admin".equals(Origem)) {
            return "/Admin/AdicionarPaginas/AddUser?faces-redirect=true";
        } else {
            return "/AddUser?faces-redirect=true";
        }

    }

    public String finishAdicionarUsuario(String origem) {
        try {
            controlUser.create(actualUser);
        } catch (Exception ex) {
            Logger.getLogger(ManageUserManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        loginUser = actualUser;
        realizarLogon();

        if ("Admin".equals(origem)) {
            return "/Admin/GerenciarPaginas/GerenciarUsuario?faces-redirect=true";
        } else {
            return "/Public/UserModule/DashboardUser?faces-redirect=true";
        }
    }

    public StreamedContent getImagemAtual() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            return new DefaultStreamedContent();
        } else {
            return new DefaultStreamedContent(new ByteArrayInputStream(actualUser.getImageUser()));
        }
    }

    public void uploadImagemUsuario(FileUploadEvent event) {
        actualUser.setImageUser(event.getFile().getContents());
    }

    public String gotoAdicionarArquivoUsuario() {
        actualUser = controlUser.findAdmin(actualUser.getLoginUser());
        return "/Admin/AdicionarPaginas/AdicionarArquivoUsuario?faces-redirect=true";
    }

    // edit
    public String finishEditarUsuario(String origem) {
        try {
            controlUser.edit(actualUser);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ManageUserManagedBean.class.getName()).log(Level.SEVERE, null, ex);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Falha :("));
        } catch (Exception ex) {
            Logger.getLogger(ManageUserManagedBean.class.getName()).log(Level.SEVERE, null, ex);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Falha!"));
        }
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Sucesso!"));
        actualUser = controlUser.findAdmin(actualUser.getLoginUser());
        if (null != origem) {
            switch (origem) {
                case "Admin":
                    return "/Admin/GerenciarPaginas/GerenciarUsuario?faces-redirect=true";
                case "Public":
                    return "/Public/UserModule/DashboardUser?faces-redirect=true";
                case "PublicMobile":
                    return "/PublicMobile/PerfilUsuarioMobile?faces-redirect=true";
                default:
                    return "/Public/UserModule/DashboardUser?faces-redirect=true";
            }
        }

        return null;

    }

    public String realizarLogon() {

        if (validateUser(loginUser)) {
            HttpSession session = ManageSession.getSession();
            session.setAttribute("userid", loginUser);
            session.setAttribute("userName", loginUser.getFullNameUser());
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

    public void carregarUsuarioNaSessao() {
        HttpSession session = ManageSession.getSession();
        Admin usr = (Admin) session.getAttribute("userid");
        usr = controlUser.findAdmin(usr.getLoginUser());
        actualUser = usr;
    }

    public String realizarLogonMobile() {

        if (validateUser(loginUser)) {
            HttpSession session = ManageSession.getSession();
            session.setAttribute("userid", loginUser);
            session.setAttribute("userName", loginUser.getFullNameUser());
            return "/PublicMobile/DashboardUsuarioMobile?faces-redirect=true";
        } else {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            " Usuario ou Senha Incorreta",
                            "Por favor, tente novamente"));
            return "/PublicMobile/RealizarLogonMobile";
        }
    }

    public String realizarLogoff() {
        HttpSession session = ManageSession.getSession();
        session.invalidate();
        return "/Public/UserModule/LogoutMessage?faces-redirect=true";
    }

    public String realizarLogoffMobile() {
        HttpSession session = ManageSession.getSession();
        session.invalidate();
        return "/PublicMobile/DashboardMobile?faces-redirect=true";
    }

    public boolean validateUser(Admin comparar) {
        Admin validate = controlUser.findAdmin(loginUser.getLoginUser());
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

    public void verificarUsuarioExistente(String user) {
        Admin validate = controlUser.findAdmin(user);
        if (validate == null) {
            validUser = true;
        } else {
            validUser = false;
        }
    }

    //Excluir
    public String excluirUsuario() throws IllegalOrphanException, NonexistentEntityException {
        //PrefeitoJpaController controlUser = new PrefeitoJpaController(EmProvider.getInstance().getEntityManagerFactory());
        controlUser.destroy(actualUser.getLoginUser());
        actualUser = new Admin();
        allUsuarios();
        return "/Admin/GerenciarPaginas/GerenciarUsuario?faces-redirect=true";
    }

    public StreamedContent getComoImagem() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            return new DefaultStreamedContent();
        } else {
            String id = context.getExternalContext().getRequestParameterMap().get("loginUsuario");
            Admin p = controlUser.findAdmin(id);
            return new DefaultStreamedContent(new ByteArrayInputStream(p.getImageUser()));
        }
    }

    public Admin getActualUser() {
        return actualUser;
    }

    public void setActualUser(Admin actualUser) {
        this.actualUser = actualUser;
    }

    public List<Admin> getListUsers() {
        return listUsers;
    }

    public void setListUsers(List<Admin> listUsers) {
        this.listUsers = listUsers;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public Admin getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(Admin loginUser) {
        this.loginUser = loginUser;
    }

    public boolean isValidUser() {
        return validUser;
    }

    public void setValidUser(boolean validUser) {
        this.validUser = validUser;
    }

    public AdminJpaController getControlUser() {
        return controlUser;
    }

    public void setControlUser(AdminJpaController controlUser) {
        this.controlUser = controlUser;
    }

}
