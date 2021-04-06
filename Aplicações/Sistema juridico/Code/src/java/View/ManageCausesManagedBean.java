package View;

import Controler.CauseJpaController;
import Controler.CausevariableJpaController;
import Controler.TagJpaController;
import Controler.TrialJpaController;
import Controler.AdminJpaController;
import Controler.exceptions.NonexistentEntityException;
import Model.Causevariable;
import Model.Tag;
import Model.Cause;
import Model.Company;
import Model.Trial;
import Model.Admin;
import Model.Variable;
import Utilitarios.EmProvider;
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
public class ManageCausesManagedBean {

    //Tags
    private Cause actualCause = new Cause();
    private Cause selectedCause = new Cause();
    private Tag selectedTag = new Tag();
    private Trial selectedTrial = new Trial();
    private Causevariable actualVariable = new Causevariable();

    //lists
    private List<Cause> listCauses = new ArrayList<>();
    private List<Causevariable> listVariableCauses = new ArrayList<>();
    private List<Tag> listTags = new ArrayList<>();
    private List<Trial> listTrials = new ArrayList<>();

    //Controls
    CauseJpaController controlCauses = new CauseJpaController(EmProvider.getInstance().getEntityManagerFactory());
    CausevariableJpaController controlVariable = new CausevariableJpaController(EmProvider.getInstance().getEntityManagerFactory());
    AdminJpaController controlUser = new AdminJpaController(EmProvider.getInstance().getEntityManagerFactory());
    TagJpaController controlTags = new TagJpaController(EmProvider.getInstance().getEntityManagerFactory());
    TrialJpaController controlTrials = new TrialJpaController(EmProvider.getInstance().getEntityManagerFactory());
    
    // auxliliarys
    private String filter;

    public ManageCausesManagedBean() {
    }

    //goto
    public String goToManageCauses() {
        actualCause = new Cause();
        getAllCauses();
        getAllTags();
        return "/Public/CauseModule/ManageCauses?faces-redirect=true";
    }

    public String goToManageVariable() {
        if (actualCause == null) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Não há nenhum processo selecionado!"));
        } else {
            listTrials = controlTrials.findTrialEntities();
            actualCause = controlCauses.findCause(actualCause.getIdCause());
            listVariableCauses = actualCause.getCausevariableList();
            return "/Public/CauseModule/ManageCauseVariables?faces-redirect=true";
        }
        return "/Public/CauseModule/ManageCauses?faces-redirect=true";
    }

    public String goToEditVariables() {
        actualCause = controlCauses.findCause(actualCause.getIdCause());
        listVariableCauses = actualCause.getCausevariableList();
        return "/Public/CauseModule/EditCauseVariables?faces-redirect=true";
    }

    public String goToEditCause() {
        return "/Public/CauseModule/EditCause?faces-redirect=true";
    }

    //Find
    public void getAllCauses() {
        listCauses.clear();
        listCauses = controlCauses.findCauseEntities();
    }

    public void getAllTags() {
        listTags.clear();
        listTags = controlTags.findTagEntities();
    }

    public void getAllVariables() {
        listVariableCauses = controlCauses.findCause(actualCause.getIdCause()).getCausevariableList();
    }
    
    public void loadVariablesFromModel(){
        List<Variable> toInsert = selectedTrial.getVariableList();
        for(Variable v: toInsert){
            Causevariable c = new Causevariable();
            c.setVariableName(v.getVariableName());
            c.setVariableContent(v.getVariableContent());
            c.setCauseidCause(actualCause);
            controlVariable.create(c);
        }
        Cause result =  controlCauses.findCause(actualCause.getIdCause());
        listVariableCauses = result.getCausevariableList();
    }

    public void finishAddCause() {
        try {
            actualCause.setAdminloginUser(controlUser.findAdmin(ManageSession.getUserName()));
            controlCauses.create(actualCause);
            actualCause = new Cause();
            getAllCauses();
        } catch (Exception ex) {
            Logger.getLogger(ManageCausesManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void finishAddVariable() {
        try {
            actualVariable.setCauseidCause(actualCause);
            controlVariable.create(actualVariable);
            listVariableCauses = controlCauses.findCause(actualCause.getIdCause()).getCausevariableList();
            actualVariable = new Causevariable();
        } catch (Exception ex) {
            Logger.getLogger(ManageCausesManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // edit
    public String finishEditCause() {
        try {

            controlCauses.edit(actualCause);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ManageCausesManagedBean.class.getName()).log(Level.SEVERE, null, ex);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Falha :("));
        } catch (Exception ex) {
            Logger.getLogger(ManageCausesManagedBean.class.getName()).log(Level.SEVERE, null, ex);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Falha!"));
        }
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Sucesso!"));
        getAllCauses();
        actualCause = new Cause();
        return "/Public/CauseModule/ManageCauses?faces-redirect=true";
    }

    public String finishEditVariable() {
        try {
            controlVariable.edit(actualVariable);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Sucesso!"));
            getAllVariables();
            actualVariable = new Causevariable();
            return "/Public/CauseModule/ManageCauseVariables?faces-redirect=true";
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ManageCausesManagedBean.class.getName()).log(Level.SEVERE, null, ex);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Falha :("));
        } catch (Exception ex) {
            Logger.getLogger(ManageCausesManagedBean.class.getName()).log(Level.SEVERE, null, ex);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Falha!"));
        }
        return "/Public/ErrorPages/ErrorGeneric?faces-redirect=true";
    }

    //Excluir
    public void removeCause() {
        try {
            controlCauses.destroy(actualCause.getIdCause());
            actualCause = new Cause();
            getAllCauses();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void removeVariable() {
        try {
            controlVariable.destroy(actualVariable.getIdCauseVariable());
            actualVariable = new Causevariable();
            getAllVariables();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //Apply filters
    public String filterCausesByUser() {
        listCauses.clear();
        Admin u = controlUser.findAdmin(ManageSession.getUserId());
        listCauses = u.getCauseList();
        return "/Public/CauseModule/ManageCauses?faces-redirect=true";
    }

    public void filterCausesByName(){
        listCauses.clear();

        List<Cause> result = new ArrayList<>();

        EntityManager em = EmProvider.getInstance().getEntityManagerFactory().createEntityManager();
        Query q = em.createQuery("SELECT t FROM Cause t WHERE t.name LIKE :name", Cause.class);
        q.setParameter("name", "%" + filter + "%");
        result = q.getResultList();
        listCauses = result;
    }
    
    public void filterCausesByTag() {
        listCauses.clear();
        Tag t = controlTags.findTag(selectedTag.getIdTag());
        listCauses = t.getCauseList();
    }

    public Cause getActualCause() {
        return actualCause;
    }

    public void setActualCause(Cause actualCause) {
        this.actualCause = actualCause;
    }

    public Cause getSelectedCause() {
        return selectedCause;
    }

    public void setSelectedCause(Cause selectedCause) {
        this.selectedCause = selectedCause;
    }

    public Tag getSelectedTag() {
        return selectedTag;
    }

    public void setSelectedTag(Tag selectedTag) {
        this.selectedTag = selectedTag;
    }

    public Causevariable getActualVariable() {
        return actualVariable;
    }

    public void setActualVariable(Causevariable actualVariable) {
        this.actualVariable = actualVariable;
    }

    public List<Cause> getListCauses() {
        return listCauses;
    }

    public void setListCauses(List<Cause> listCauses) {
        this.listCauses = listCauses;
    }

    public List<Causevariable> getListVariableCauses() {
        return listVariableCauses;
    }

    public void setListVariableCauses(List<Causevariable> listVariableCauses) {
        this.listVariableCauses = listVariableCauses;
    }

    public List<Tag> getListTags() {
        return listTags;
    }

    public void setListTags(List<Tag> listTags) {
        this.listTags = listTags;
    }

    public Trial getSelectedTrial() {
        return selectedTrial;
    }

    public void setSelectedTrial(Trial selectedTrial) {
        this.selectedTrial = selectedTrial;
    }

    public List<Trial> getListTrials() {
        return listTrials;
    }

    public void setListTrials(List<Trial> listTrials) {
        this.listTrials = listTrials;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }
}
