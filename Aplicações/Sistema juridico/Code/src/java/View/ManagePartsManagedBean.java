package View;

import Controler.DocumentpartJpaController;
import Controler.TagJpaController;
import Controler.TrialJpaController;
import Controler.AdminJpaController;
import Controler.exceptions.NonexistentEntityException;
import Model.Documentmodel;
import Model.Documentpart;
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
public class ManagePartsManagedBean {

    //Tags
    private Tag actualTag = new Tag();
    private Tag selectedTag = new Tag();
    private Documentpart actualDocumentpart = new Documentpart();
    private Trial actualTrials = new Trial();

    //lists
    private List<Tag> listTags = new ArrayList<>();
    private List<Documentpart> listDocuments = new ArrayList<>();
    private List<Trial> listTrials = new ArrayList<>();

    //controllers
    private TagJpaController controlTags = new TagJpaController(EmProvider.getInstance().getEntityManagerFactory());
    private DocumentpartJpaController controlDocumentpart = new DocumentpartJpaController(EmProvider.getInstance().getEntityManagerFactory());
    private AdminJpaController controlUser = new AdminJpaController(EmProvider.getInstance().getEntityManagerFactory());
    private TrialJpaController controlTrials = new TrialJpaController(EmProvider.getInstance().getEntityManagerFactory());

    //Auxiliarys
    private String tagTypeOfInsertion;
    private String filter;
    private TreeNode TagsTree;

    public ManagePartsManagedBean() {
    }

    public void loadTree() {
        TagsTree = new DefaultTreeNode();
        Tag root = controlTags.findTag(1); // instead get root object from database 
        TagsTree = newNodeWithChildren(root, null);
        getAllParts();
        loadTrials();

    }

    public void refreshVariables(){
        actualTrials = controlTrials.findTrial(actualTrials.getIdTrial());
    }
    
    public void loadTrials() {
        listTrials = controlTrials.findTrialEntities();
    }

    public TreeNode newNodeWithChildren(Tag ttParent, TreeNode parent) {
        TreeNode newNode = new DefaultTreeNode(ttParent, parent);
        for (Tag tt : ttParent.getTagList()) {
            TreeNode newNode2 = newNodeWithChildren(tt, newNode);
        }
        return newNode;
    }

    //goto
    public String goToAddParts() {
        actualTag = new Tag();
        actualDocumentpart = new Documentpart();
        Tag root = controlTags.findTag(1);
        if (root == null) {
            root = new Tag();
            root.setLevel(0);
            root.setTitle("Direito");
            controlTags.create(root);
        }
        listTags.clear();
        loadTree();
        getAllParts();
        actualTag = new Tag();
        return "/Public/PartsModule/AddPart?faces-redirect=true";
    }

    public String goToManageParts() {
        Tag root = controlTags.findTag(1);
        if (root == null) {
            root = new Tag();
            root.setLevel(0);
            root.setTitle("Direito");
            controlTags.create(root);
        }
        getAllParts();
        loadTree();
        getAllDocuments();
        return "/Public/PartsModule/ManageParts?faces-redirect=true";
    }

    public String goToEditPart() {
        getAllParts();
        return "/Public/PartsModule/EditPart?faces-redirect=true";
    }

    public void unselectTag() {
        selectedTag = null;
    }

    //Find
    public void getAllParts() {
        listTags.clear();
        listTags = controlTags.findTagEntities();
    }

    public void getAllDocuments() {
        listDocuments.clear();
        listDocuments = controlDocumentpart.findDocumentpartEntities();
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
        listDocuments = controlDocumentpart.findDocumentpartEntities();
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
            Logger.getLogger(ManagePartsManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String finishAdicionarDocumentpart() {
        try {
            if (actualDocumentpart.getPartidPart() == null) {
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage("Por favor selecione uma categoria"));
            } else {
                controlDocumentpart.create(actualDocumentpart);
                getAllDocuments();
                return "/Public/PartsModule/ManageParts?faces-redirect=true";
            }

        } catch (Exception ex) {
            Logger.getLogger(ManagePartsManagedBean.class.getName()).log(Level.SEVERE, null, ex);

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
            Logger.getLogger(ManagePartsManagedBean.class.getName()).log(Level.SEVERE, null, ex);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Falha :("));
        } catch (Exception ex) {
            Logger.getLogger(ManagePartsManagedBean.class.getName()).log(Level.SEVERE, null, ex);
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
    public String finishEditDocumentpart() {
        try {
            if (actualDocumentpart.getPartidPart() == null) {
                actualDocumentpart.setPartidPart(controlDocumentpart.findDocumentpart(actualDocumentpart.getIdDocumentPart()).getPartidPart());
                controlDocumentpart.edit(actualDocumentpart);
            } else {
                controlDocumentpart.edit(actualDocumentpart);
            }
        } catch (Exception ex) {
            Logger.getLogger(ManagePartsManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        actualDocumentpart = new Documentpart();
        getAllDocuments();
        return "/Public/PartsModule/ManageParts?faces-redirect=true";
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
    
     public void removePart() {
        try {
            controlDocumentpart.destroy(actualDocumentpart.getIdDocumentPart());
            getAllParts();
            loadTree();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void removeDocumentPart() {
        try {
            controlDocumentpart.destroy(actualDocumentpart.getIdDocumentPart());
            actualDocumentpart = new Documentpart();
            getAllDocuments();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //Apply filters
    
     public void filterPartsByName() {
        listDocuments.clear();

        List<Documentpart> result = new ArrayList<>();

        EntityManager em = EmProvider.getInstance().getEntityManagerFactory().createEntityManager();
        Query q = em.createQuery("SELECT t FROM Documentpart t WHERE t.title LIKE :title", Documentpart.class
        );
        q.setParameter("title", "%" + filter + "%");
        result = q.getResultList();
        listDocuments = result;
    }
    
     
    public String filterDocumentsByTag() {
        listDocuments.clear();
        listDocuments = selectedTag.getDocumentpartList();
        return "/Public/PartsModule/ManageParts?faces-redirect=true";
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

    public void setActualTag(Tag actualPart) {
        this.actualTag = actualPart;
    }

    public List<Tag> getListTags() {
        return listTags;
    }

    public void setListTags(List<Tag> listParts) {
        this.listTags = listParts;
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

    public Documentpart getActualDocumentpart() {
        return actualDocumentpart;
    }

    public void setActualDocumentpart(Documentpart actualDocumentpart) {
        this.actualDocumentpart = actualDocumentpart;
    }

    public List<Documentpart> getListDocuments() {
        return listDocuments;
    }

    public void setListDocuments(List<Documentpart> listDocuments) {
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

    public Trial getActualTrials() {
        return actualTrials;
    }

    public void setActualTrials(Trial actualTrials) {
        this.actualTrials = actualTrials;
    }

    public List<Trial> getListTrials() {
        return listTrials;
    }

    public void setListTrials(List<Trial> listTrials) {
        this.listTrials = listTrials;
    }

    
}
