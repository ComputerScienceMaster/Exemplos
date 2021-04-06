package View;


import Utilitarios.EmProvider;
import Controler.exceptions.IllegalOrphanException;
import Controler.exceptions.NonexistentEntityException;
import Components.ComponentsContainer;
import Controler.AdminJpaController;
import Model.Admin;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;


public class ManageLoginManagedBean {
    
    
    public ManageLoginManagedBean() {
    }
    
    private Admin actualAdmin = new Admin();
    private List<Admin> administrator = new ArrayList<>();
    private AdminJpaController control = new AdminJpaController(EmProvider.getInstance().getEntityManagerFactory());
    
    
    public String gotoLogin(){
        return "/Admin/login";
    }
     
      
      
    public String finishEditarAdministrador() throws NonexistentEntityException, Exception, NonexistentEntityException, NonexistentEntityException, NonexistentEntityException, NonexistentEntityException, NonexistentEntityException{
        control.edit(actualAdmin);
        actualAdmin = new Admin();
        return "gotoGerenciarAdministrador";
    }
   
     
    public String gotoGerenciarAdministrador(){ 
        //SecretariaJpaController control = new SecretariaJpaController(EmProvider.getInstance().getEntityManagerFactory());
        administrator = control.findAdminEntities();
        return "gotoGerenciarAdministrador";
    }
    
    public String gotoAdicionarAdministrador(){
        return "gotoAdicionarAdministrador";
    }
    
    public String finishAdicionarAdministrador() throws Exception{
        control.create(actualAdmin);
        actualAdmin = new Admin();
        administrator = control.findAdminEntities();
        return "gotoGerenciarAdministrador";
    }
    
    public String excluirAdministrador() throws IllegalOrphanException, NonexistentEntityException{
        //PrefeitoJpaController control = new PrefeitoJpaController(EmProvider.getInstance().getEntityManagerFactory());
        control.destroy(actualAdmin.getLoginUser());
        actualAdmin = new Admin();
        administrator = control.findAdminEntities();
        return "gotoGerenciarAdministrador";
    }
    
    public String gotoAdicionarArquivoAdministrador(){
        actualAdmin = control.findAdmin(actualAdmin.getLoginUser());
        return "gotoAdicionarArquivoAdministrador";
    }
    
    public String gotoVisualizarImagem(){
        return "gotoVisualizarImagem";
    }
    
    public String finalizarAdicaoFotosAdministrador(){
        return "gotoGerenciarAdministrador";
    }
    
    public String validateUsernamePassword() {
        
        
        if (validateUser(actualAdmin)) {
            HttpSession session = ManageSession.getSession();
            session.setAttribute("username", actualAdmin.getLoginUser());
            return "Welcome";
        } else {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Senha Incorreta",
                            "Por favor, tente novamente"));
            return "login";
        }
    }
    
    public boolean validateUser(Admin comparar){
        Admin validate = control.findAdmin(actualAdmin.getLoginUser());
        if (validate == null){
            return false;
        }
        if(validate.getLoginUser().equals(comparar.getLoginUser()) && 
                validate.getPasswordUser().equals(comparar.getPasswordUser())){
            return true;
        }
        return false;
    }
 
    //logout event, invalidate session
    public String logout() {
        HttpSession session = ManageSession.getSession();
        session.invalidate();
        return "/Public/Dashboard";
    }
    
    public ComponentsContainer getComponentsContainer(){
        return new ComponentsContainer();
    }
    
    public Admin getActualAdmin() {
        return actualAdmin;
    }

    public void setActualAdmin(Admin actualAdmin) {
        this.actualAdmin = actualAdmin;
    }

    public List<Admin> getAdmin() {
        return administrator;
    }

    public void setAdmin(List<Admin> administrator) {
        this.administrator = administrator;
    }

    public AdminJpaController getControl() {
        return control;
    }

    public void setControl(AdminJpaController control) {
        this.control = control;
    }

    
    
    
    
}
