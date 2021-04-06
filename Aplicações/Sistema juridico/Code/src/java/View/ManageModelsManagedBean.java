package View;

import Controler.DocumentmodelJpaController;
import Controler.TagJpaController;
import Controler.TrialJpaController;
import Controler.AdminJpaController;
import Controler.exceptions.NonexistentEntityException;
import Model.Cause;
import Model.Documentmodel;
import Model.Tag;
import Model.Trial;
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
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

@ManagedBean
@RequestScoped
public class ManageModelsManagedBean {

    //Tags
    private Tag actualTag = new Tag();
    private Tag selectedTag = new Tag();
    private Documentmodel actualDocumentModel = new Documentmodel();
    private Trial actualTrial = new Trial();
    private Cause actualCause = new Cause();

    //lists
    private List<Tag> listTags = new ArrayList<>();
    private List<Documentmodel> listDocuments = new ArrayList<>();
    private List<Trial> listTrials = new ArrayList<>();
    private List<Cause> listCause = new ArrayList<>();

    //controllers
    private TagJpaController controlTags = new TagJpaController(EmProvider.getInstance().getEntityManagerFactory());
    private DocumentmodelJpaController controlDocumentModel = new DocumentmodelJpaController(EmProvider.getInstance().getEntityManagerFactory());
    private AdminJpaController controlUser = new AdminJpaController(EmProvider.getInstance().getEntityManagerFactory());
    private TrialJpaController controlTrial = new TrialJpaController(EmProvider.getInstance().getEntityManagerFactory());

    //Auxiliarys
    private String tagTypeOfInsertion;
    private String filter;
    private String filterCause;
    private TreeNode TagsTree;

    public ManageModelsManagedBean() {
    }

    public void loadTree() {
        TagsTree = new DefaultTreeNode();
        Tag root = controlTags.findTag(1); // instead get root object from database 
        TagsTree = newNodeWithChildren(root, null);
        getAllModels();
        loadTrials();

    }

    public void loadTrials() {
        listTrials = controlTrial.findTrialEntities();
    }

    public TreeNode newNodeWithChildren(Tag ttParent, TreeNode parent) {
        TreeNode newNode = new DefaultTreeNode(ttParent, parent);
        for (Tag tt : ttParent.getTagList()) {
            TreeNode newNode2 = newNodeWithChildren(tt, newNode);
        }
        return newNode;
    }
    
    public void refreshVariables(){
        actualTrial = controlTrial.findTrial(actualTrial.getIdTrial());
    }

    //goto
    public String goToAddModels() {
        actualTag = new Tag();
        actualDocumentModel = new Documentmodel();
        Tag root = controlTags.findTag(1);
        if (root == null) {
            root = new Tag();
            root.setLevel(0);
            root.setTitle("Direito");
            controlTags.create(root);
        }
        listTags.clear();
        loadTree();
        getAllModels();
        actualTag = new Tag();
        return "/Public/ModelsModule/AddModel?faces-redirect=true";
    }

    public String goToManageModels() {
        Tag root = controlTags.findTag(1);
        if (root == null) {
            root = new Tag();
            root.setLevel(0);
            root.setTitle("Direito");
            controlTags.create(root);
        }
        getAllModels();
        loadTree();
        getAllDocuments();
        return "/Public/ModelsModule/ManageModel?faces-redirect=true";
    }

    public String goToEditModel() {
        getAllModels();
        return "/Public/ModelsModule/EditModel?faces-redirect=true";
    }

    public void unselectTag() {
        selectedTag = null;
    }

    //Find
    public void getAllModels() {
        listTags.clear();
        listTags = controlTags.findTagEntities();
    }

    public void getAllDocuments() {
        listDocuments.clear();
        listDocuments = controlDocumentModel.findDocumentmodelEntities();
    }

    public boolean isNewClassificationSelected() {
        if (tagTypeOfInsertion == null) {
            return false;
        } else if (tagTypeOfInsertion.equals("newClassification")) {
            return true;
        }
        return false;
    }

    public boolean isEditClassificationSelected() {
        if (tagTypeOfInsertion == null) {
            return false;
        } else if (tagTypeOfInsertion.equals("editClassification")) {
            return true;
        }
        return false;
    }

    public boolean isRemoveClassificationSelected() {
        if (tagTypeOfInsertion == null) {
            return false;
        } else if (tagTypeOfInsertion.equals("removeClassification")) {
            return true;
        }
        return false;
    }

    public void loadDocumentparts() {
        listDocuments.clear();
        EntityManager em = EmProvider.getInstance().getEntityManagerFactory().createEntityManager();
        listDocuments = controlDocumentModel.findDocumentmodelEntities();
        loadTree();
    }

    public void finishAddTag() {
        try {

            if (selectedTag == null || selectedTag.getTitle() == null) {
                actualTag.setLevel(1);
                actualTag.setParentId(controlTags.findTag(1));
                controlTags.create(actualTag);
            } else {
                actualTag.setParentId(selectedTag);
                controlTags.create(actualTag);
            }
            loadTree();
            actualTag = new Tag();
        } catch (Exception ex) {
            Logger.getLogger(ManageModelsManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String finishAdicionarDocumentmodel() {
        try {
            if (actualDocumentModel.getTagidTag() == null) {
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage("Por favor selecione uma categoria"));
            } else {
                controlDocumentModel.create(actualDocumentModel);
                getAllDocuments();
                return "/Public/ModelsModule/ManageModel?faces-redirect=true";
            }

        } catch (Exception ex) {
            Logger.getLogger(ManageModelsManagedBean.class.getName()).log(Level.SEVERE, null, ex);

        }
        return "/Public/ErrorPages/ErrorIncluding?faces-redirect=true";
    }

    // edit
    public void finishEditTag() {
        try {
            List<Tag> results = EmProvider.getInstance().getEntityManagerFactory().createEntityManager().createNamedQuery("Tag.findByTitle")
                    .setParameter("title", selectedTag.getTitle())
                    .getResultList();
            Tag toPersist = results.get(0);
            toPersist.setTitle(actualTag.getTitle());

            controlTags.edit(toPersist);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ManageModelsManagedBean.class.getName()).log(Level.SEVERE, null, ex);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Falha :("));
        } catch (Exception ex) {
            Logger.getLogger(ManageModelsManagedBean.class.getName()).log(Level.SEVERE, null, ex);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Falha!"));
        }
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Sucesso!"));
        selectedTag = new Tag();
        actualTag = new Tag();
        loadTree();
    }

    // edit
    public String finishEditDocumentmodel() {
        try {
            if (actualDocumentModel.getTagidTag() == null) {
                actualDocumentModel.setTagidTag(controlDocumentModel.findDocumentmodel(actualDocumentModel.getIdDocumentModel()).getTagidTag());
                controlDocumentModel.edit(actualDocumentModel);
            } else {
                controlDocumentModel.edit(actualDocumentModel);
            }
        } catch (Exception ex) {
            Logger.getLogger(ManageModelsManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        actualDocumentModel = new Documentmodel();
        getAllDocuments();
        return "/Public/ModelsModule/ManageModel?faces-redirect=true";
    }

    //Excluir
    public void removeTag() {
        try {
            controlTags.destroy(selectedTag.getIdTag());
            actualTag = new Tag();
            loadTree();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void removeDocumentmodel() {
        try {
            controlDocumentModel.destroy(actualDocumentModel.getIdDocumentModel());
            actualDocumentModel = new Documentmodel();
            getAllDocuments();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //Apply filters
    public void filterModelsByName() {
        listDocuments.clear();

        List<Documentmodel> result = new ArrayList<>();

        EntityManager em = EmProvider.getInstance().getEntityManagerFactory().createEntityManager();
        Query q = em.createQuery("SELECT t FROM Documentmodel t WHERE t.title LIKE :title", Documentmodel.class
        );
        q.setParameter("title", "%" + filter + "%");
        result = q.getResultList();
        listDocuments = result;
    }

    public String filterDocumentsByTag() {
        listDocuments.clear();
        listDocuments = selectedTag.getDocumentmodelList();
        return "/Public/ModelsModule/ManageModel?faces-redirect=true";
    }

    public void filterTrialsByName() {
        listTrials.clear();
        List<Trial> result = new ArrayList<>();

        EntityManager em = EmProvider.getInstance().getEntityManagerFactory().createEntityManager();
        Query q = em.createQuery("SELECT t FROM Trial t WHERE t.name LIKE :name", Trial.class
        );
        q.setParameter("name", "%" + filter + "%");
        result = q.getResultList();
        listTrials = result;
    }

    public Tag getActualTag() {
        return actualTag;
    }

    public void setActualTag(Tag actualModel) {
        this.actualTag = actualModel;
    }

    public List<Tag> getListTags() {
        return listTags;
    }

    public void setListTags(List<Tag> listTags) {
        this.listTags = listTags;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public TreeNode getRoot() {
        return TagsTree;
    }

    public void setRoot(TreeNode root) {
        this.TagsTree = root;
    }

    public Documentmodel getActualDocumentmodel() {
        return actualDocumentModel;
    }

    public void setActualDocumentmodel(Documentmodel actualDocumentmodel) {
        this.actualDocumentModel = actualDocumentmodel;
    }

    public List<Documentmodel> getListDocuments() {
        return listDocuments;
    }

    public void setListDocuments(List<Documentmodel> listDocuments) {
        this.listDocuments = listDocuments;
    }

    public Tag getSelectedTag() {
        return selectedTag;
    }

    public void setSelectedTag(Tag selectedTag) {
        this.selectedTag = selectedTag;
    }

    public String getTagTypeOfInsertion() {
        return tagTypeOfInsertion;
    }

    public void setTagTypeOfInsertion(String tagTypeOfInsertion) {
        this.tagTypeOfInsertion = tagTypeOfInsertion;
    }

    public TreeNode geTagsTree() {
        return TagsTree;
    }

    public void setTagsTree(TreeNode TagsTree) {
        this.TagsTree = TagsTree;
    }

    public Documentmodel getActualDocumentModel() {
        return actualDocumentModel;
    }

    public void setActualDocumentModel(Documentmodel actualDocumentModel) {
        this.actualDocumentModel = actualDocumentModel;
    }

    public Trial getActualTrial() {
        return actualTrial;
    }

    public void setActualTrial(Trial actualTrial) {
        this.actualTrial = actualTrial;
    }

    public List<Trial> getListTrials() {
        return listTrials;
    }

    public void setListTrials(List<Trial> listTrials) {
        this.listTrials = listTrials;
    }

    public String getFilterCause() {
        return filterCause;
    }

    public void setFilterCause(String filterCause) {
        this.filterCause = filterCause;
    }

    public Cause getActualCause() {
        return actualCause;
    }

    public void setActualCause(Cause actualCause) {
        this.actualCause = actualCause;
    }

    public List<Cause> getListCause() {
        return listCause;
    }

    public void setListCause(List<Cause> listCause) {
        this.listCause = listCause;
    }

}
