package View;

import Controler.TagJpaController;
import Controler.AdminJpaController;
import Controler.exceptions.NonexistentEntityException;
import Model.Tag;
import Utilitarios.EmProvider;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.ManagedBean;
import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

@ManagedBean
@RequestScoped
public class ManageTagsManagedBean {

    //Tags
    private Tag actualTag = new Tag();
    private Tag selectedTag = new Tag();

    //lists
    private List<Tag> listTags = new ArrayList<>();

    //controllers
    private TagJpaController controlTags = new TagJpaController(EmProvider.getInstance().getEntityManagerFactory());
    private AdminJpaController controlUser = new AdminJpaController(EmProvider.getInstance().getEntityManagerFactory());

    //Auxiliarys
    private String tagTypeOfInsertion;
    private String filter;
    private TreeNode TagsTree;

    public ManageTagsManagedBean() {
    }

    public void loadTree() {
        TagsTree = new DefaultTreeNode();
        Tag root = controlTags.findTag(1); // instead get root object from database 
        TagsTree = newNodeWithChildren(root, null);
    }

    public TreeNode newNodeWithChildren(Tag ttParent, TreeNode parent) {
        TreeNode newNode = new DefaultTreeNode(ttParent, parent);
        for (Tag tt : ttParent.getTagList()) {
            TreeNode newNode2 = newNodeWithChildren(tt, newNode);
        }
        return newNode;
    }

    //goto
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
            Logger.getLogger(ManageTagsManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
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
            Logger.getLogger(ManageTagsManagedBean.class.getName()).log(Level.SEVERE, null, ex);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Falha :("));
        } catch (Exception ex) {
            Logger.getLogger(ManageTagsManagedBean.class.getName()).log(Level.SEVERE, null, ex);
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

    public void unselectTag() {
        selectedTag = null;
    }

    public Tag getActualTag() {
        return actualTag;
    }

    public void setActualTag(Tag actualTag) {
        this.actualTag = actualTag;
    }

    public Tag getSelectedTag() {
        return selectedTag;
    }

    public void setSelectedTag(Tag selectedTag) {
        this.selectedTag = selectedTag;
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

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public TreeNode getTagsTree() {
        return TagsTree;
    }

    public void setTagsTree(TreeNode TagsTree) {
        this.TagsTree = TagsTree;
    }

}
