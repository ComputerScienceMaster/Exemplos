package View;

import Controler.AdminJpaController;
import Controler.TagJpaController;
import Controler.TrialJpaController;
import Controler.VariableJpaController;
import Controler.exceptions.NonexistentEntityException;
import Model.Tag;
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
import org.primefaces.model.TreeNode;

@ManagedBean
@RequestScoped
public class ManageTrialsManagedBean {

    //Tags
    private Trial actualTrial = new Trial();
    private Trial selectedTrial = new Trial();
    private Tag selectedTag = new Tag();
    private Variable actualVariable = new Variable();
    

    //lists
    private List<Trial> listTrial = new ArrayList<>();
    private List<Variable> listVariable = new ArrayList<>();
    private List<Tag> listTags = new ArrayList<>();

    //Controls
    TrialJpaController controlTrial = new TrialJpaController(EmProvider.getInstance().getEntityManagerFactory());
    VariableJpaController controlVariable = new VariableJpaController(EmProvider.getInstance().getEntityManagerFactory());
    AdminJpaController controlUser = new AdminJpaController(EmProvider.getInstance().getEntityManagerFactory());
    TagJpaController controlTags = new TagJpaController(EmProvider.getInstance().getEntityManagerFactory());

    //auxiliarys
    private String tagTypeOfInsertion;
    private TreeNode TagsTree;
    private String filter;

    public ManageTrialsManagedBean() {
    }

    //goto
    public String goToAddTrials() {
        actualTrial = new Trial();
        return "/Public/TrialModule/AddTrial?faces-redirect=true";
    }

    public String goToManageTrials() {
        actualTrial = new Trial();
        getAllTrials();
        getAllTags();
        return "/Public/TrialModule/ManageTrials?faces-redirect=true";
    }

    public String goToManageVariable() {
        if (actualTrial == null || "".equals(actualTrial.getIdTrial())) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Não há nenhum processo selecionado!"));
        } else {
            actualTrial = controlTrial.findTrial(actualTrial.getIdTrial());
            listVariable = actualTrial.getVariableList();
            return "/Public/TrialModule/ManageVariables?faces-redirect=true";
        }
        return "/Public/TrialModule/AddDocument?faces-redirect=true";
    }

    public String goToEditVariables() {
        actualTrial = controlTrial.findTrial(actualTrial.getIdTrial());
        listVariable = actualTrial.getVariableList();
        return "/Public/TrialModule/EditVariable?faces-redirect=true";
    }

    public String goToEditTrial() {
        return "/Public/TrialModule/EditTrial?faces-redirect=true";
    }

    //Find
    public void getAllTrials() {
        listTrial.clear();
        listTrial = controlTrial.findTrialEntities();
    }

    public void getAllTags() {
        listTags.clear();
        listTags = controlTags.findTagEntities();
    }

    public void getAllVariables() {
        listVariable = controlTrial.findTrial(actualTrial.getIdTrial()).getVariableList();
    }

    public void finishAddTrial() {
        try {
            actualTrial.setUserloginUser(controlUser.findAdmin(ManageSession.getUserName()));
            controlTrial.create(actualTrial);
            actualTrial = new Trial();
            getAllTrials();
        } catch (Exception ex) {
            Logger.getLogger(ManageTrialsManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void finishAddVariable() {
        try {
            actualVariable.setTrialidTrial(actualTrial);
            controlVariable.create(actualVariable);
            listVariable = controlTrial.findTrial(actualTrial.getIdTrial()).getVariableList();
            actualVariable = new Variable();
        } catch (Exception ex) {
            Logger.getLogger(ManageTrialsManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // edit
    public String finishEditTrial() {
        try {

            controlTrial.edit(actualTrial);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ManageTrialsManagedBean.class.getName()).log(Level.SEVERE, null, ex);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Falha :("));
        } catch (Exception ex) {
            Logger.getLogger(ManageTrialsManagedBean.class.getName()).log(Level.SEVERE, null, ex);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Falha!"));
        }
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Sucesso!"));
        getAllTrials();
        actualTrial = new Trial();
        return "/Public/TrialModule/ManageTrials?faces-redirect=true";
    }

    public String finishEditVariable() {
        try {
            controlVariable.edit(actualVariable);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Sucesso!"));
            getAllVariables();
            actualVariable = new Variable();
            return "/Public/TrialModule/ManageVariables?faces-redirect=true";
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ManageTrialsManagedBean.class.getName()).log(Level.SEVERE, null, ex);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Falha :("));
        } catch (Exception ex) {
            Logger.getLogger(ManageTrialsManagedBean.class.getName()).log(Level.SEVERE, null, ex);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Falha!"));
        }
        return "/Public/ErrorPages/ErrorGeneric?faces-redirect=true";
    }

    //Excluir
    public void removeTrial() {
        try {
            controlTrial.destroy(actualTrial.getIdTrial());
            actualTrial = new Trial();
            getAllTrials();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void removeVariable() {
        try {
            controlVariable.destroy(actualVariable.getIdVariable());
            actualVariable = new Variable();
            getAllVariables();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //Apply filters
    public String filterTrialsByUser() {
        listTrial.clear();
        Admin u = controlUser.findAdmin(ManageSession.getUserId());
        listTrial = u.getTrialList();
        return "/Public/TrialModule/ManageTrials?faces-redirect=true";
    }

    public void filterTrialsByName() {
        listTrial.clear();

        List<Trial> result = new ArrayList<>();

        EntityManager em = EmProvider.getInstance().getEntityManagerFactory().createEntityManager();
        Query q = em.createQuery("SELECT t FROM Trial t WHERE t.name LIKE :name", Trial.class);
        q.setParameter("name", "%" + filter + "%");
        result = q.getResultList();
        listTrial = result;
    }

    public void filterTrialsByTag() {
        listTrial.clear();
        Tag t = controlTags.findTag(selectedTag.getIdTag());
        listTrial = t.getTrialList();
    }

    public Trial getActualTrial() {
        return actualTrial;
    }

    public void setActualTrial(Trial actualTrial) {
        this.actualTrial = actualTrial;
    }

    public Trial getSelectedTrial() {
        return selectedTrial;
    }

    public void setSelectedTrial(Trial selectedTrial) {
        this.selectedTrial = selectedTrial;
    }

    public Variable getActualVariable() {
        return actualVariable;
    }

    public void setActualVariable(Variable actualVariable) {
        this.actualVariable = actualVariable;
    }

    public List<Trial> getListTrial() {
        return listTrial;
    }

    public void setListTrial(List<Trial> listTrial) {
        this.listTrial = listTrial;
    }

    public List<Variable> getListVariable() {
        return listVariable;
    }

    public void setListVariable(List<Variable> listVariable) {
        this.listVariable = listVariable;
    }

    public List<Tag> getListTags() {
        return listTags;
    }

    public void setListTags(List<Tag> listTags) {
        this.listTags = listTags;
    }

    public String getTagTypeOfInsertion() {
        return tagTypeOfInsertion;
    }

    public void setTagTypeOfInsertion(String tagTypeOfInsertion) {
        this.tagTypeOfInsertion = tagTypeOfInsertion;
    }

    public TreeNode getTagsTree() {
        return TagsTree;
    }

    public void setTagsTree(TreeNode TagsTree) {
        this.TagsTree = TagsTree;
    }

    public Tag getSelectedTag() {
        return selectedTag;
    }

    public void setSelectedTag(Tag selectedTag) {
        this.selectedTag = selectedTag;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

}
