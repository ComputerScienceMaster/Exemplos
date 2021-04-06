package View;

import Controler.ClientJpaController;
import Controler.DocumentJpaController;
import Controler.DocumentmodelJpaController;
import Controler.TagJpaController;
import Controler.TrialJpaController;
import Controler.AdminJpaController;
import Controler.exceptions.NonexistentEntityException;
import Model.Cause;
import Model.Causevariable;
import Model.Client;
import Model.Company;
import Model.Document;
import Model.Documentmodel;
import Model.Documentpart;
import Model.Tag;
import Model.Trial;
import Utilitarios.EmProvider;
import Utilitarios.StringUtils;
import java.util.ArrayList;
import java.util.List;
import static java.util.Locale.filter;
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
public class ManageDocumentsManagedBean {

    //Tags
    private Tag actualTag = new Tag();
    private Tag selectedTag = new Tag();
    private Documentmodel selectedDocumentModels = new Documentmodel();
    private Documentpart selectedDocumentpart = new Documentpart();
    private Document actualDocument = new Document();

    //objects to edit the text
    private String documentPartToReplace = new String();
    private Tag selectedTagToSearchParts = new Tag();
    private Documentpart documentPartSelected = new Documentpart();
    private Client clientSelectedToReplace = new Client();
    private Trial trialToReplace = new Trial();
    private Cause causeToReplace = new Cause();
    private Company companySelectedToReplace = new Company();

    //lists
    private List<Tag> listTags = new ArrayList<>();
    private List<Document> listDocuments = new ArrayList<>();
    private List<Client> listOfClients = new ArrayList<>();
    private List<Documentmodel> listModels = new ArrayList<>();
    private List<Documentpart> listDocumentParts = new ArrayList<>();
    private List<Trial> listOfTrialsToReplace = new ArrayList<>();
    private List<String> listOfPartsToReplace = new ArrayList<>();
    private List<Causevariable> listOfVariablesToReplace = new ArrayList<>();
    private List<Documentpart> listOfPartsByTag = new ArrayList<>();
    private List<Cause> listOfCausesToReplace = new ArrayList<>();
    private List<Company> listOfCompanys = new ArrayList<>();

    //controllers
    private TagJpaController controlTags = new TagJpaController(EmProvider.getInstance().getEntityManagerFactory());
    private DocumentJpaController controlDocument = new DocumentJpaController(EmProvider.getInstance().getEntityManagerFactory());
    private AdminJpaController controlUser = new AdminJpaController(EmProvider.getInstance().getEntityManagerFactory());
    private DocumentmodelJpaController controlDocumentmodel = new DocumentmodelJpaController(EmProvider.getInstance().getEntityManagerFactory());
    private ClientJpaController controlClient = new ClientJpaController(EmProvider.getInstance().getEntityManagerFactory());
    private TrialJpaController controlTrials = new TrialJpaController(EmProvider.getInstance().getEntityManagerFactory());

    //Auxiliarys
    private TreeNode TagsTree;
    private String corpusToSave = "";
    private String filterClients = "";
    private String filterTrials = "";
    private String filterDocumentName = "";
    private String filterCauses = "";
    private String filterCompanys = "";
    private String typeOfPerson = "";

    public ManageDocumentsManagedBean() {
    }

    public void loadTree() {

        listDocumentParts = new ArrayList<>();
        listOfPartsToReplace = new ArrayList<>();
        listOfPartsByTag = new ArrayList<>();

        documentPartToReplace = new String();
        selectedTagToSearchParts = new Tag();
        documentPartSelected = new Documentpart();
        clientSelectedToReplace = new Client();
        TagsTree = new DefaultTreeNode();
        Tag root = controlTags.findTag(1); // instead get root object from database 
        if (root == null) {
            root = new Tag();
            root.setLevel(0);
            root.setTitle("Direito");
            controlTags.create(root);
        }
        TagsTree = newNodeWithChildren(root, null);
        getAllTags();

    }

    public void replaceOnCorpus() {
        if ("newPF".equals(typeOfPerson)) {
            if (clientSelectedToReplace == null
                    || documentPartSelected == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Certifique-se que você selecionou todas as informações corretamente", ""));
                return;
            }
            getAllTags();
            Client selected = clientSelectedToReplace;
            String partToReplace = documentPartSelected.getCorpus();
            String resultPartToReplace = StringUtils.replaceFieldsInPart(selected, partToReplace);

            String toFindOnCorpus = documentPartToReplace;
            if (documentPartToReplace == null || "".equals(documentPartSelected)) {
                toFindOnCorpus = "!#trecho1#!";
            }
            String corpus = corpusToSave;
            String contentToReplace = resultPartToReplace;

            if (corpus != null && !corpus.equals("") && toFindOnCorpus != null && !toFindOnCorpus.equals("") && contentToReplace != null && !contentToReplace.equals("")) {
                corpusToSave = StringUtils.replaceContentOnCorpus(corpus, toFindOnCorpus, contentToReplace);
            }
        }
        if("newPJ".equals(typeOfPerson)){
             if (companySelectedToReplace == null
                    || documentPartSelected == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Certifique-se que você selecionou todas as informações corretamente", ""));
                return;
            }
            getAllTags();
            Company selected = companySelectedToReplace;
            String partToReplace = documentPartSelected.getCorpus();
            String resultPartToReplace = StringUtils.replaceFieldsInPart(selected, partToReplace);

            String toFindOnCorpus = documentPartToReplace;
            if (documentPartToReplace == null || "".equals(documentPartSelected)) {
                toFindOnCorpus = "!#trecho1#!";
            }
            String corpus = corpusToSave;
            String contentToReplace = resultPartToReplace;

            if (corpus != null && !corpus.equals("") && toFindOnCorpus != null && !toFindOnCorpus.equals("") && contentToReplace != null && !contentToReplace.equals("")) {
                corpusToSave = StringUtils.replaceContentOnCorpus(corpus, toFindOnCorpus, contentToReplace);
            }
        }

    }

    public void loadVariables() {
        if (causeToReplace == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Tenha certeza que selecionou o um processo", ""));
            return;
        }
        listOfVariablesToReplace = causeToReplace.getCausevariableList();
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Variaveis carregadas!", "Suas variáveis foram carregadas com sucesso");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void replaceOnUserVariablesOnCorpus() {
        if (listOfVariablesToReplace == null || listOfVariablesToReplace.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Opa!", "Carregue suas variáveis antes!"));
            return;
        }
        String corpus = corpusToSave;
        if (corpus != null && !"".equals(corpus) && listOfVariablesToReplace != null && listOfVariablesToReplace.size() > 0) {
            corpusToSave = StringUtils.replaceUserVariablesOnCorpus(corpus, listOfVariablesToReplace);
        }

    }

    public TreeNode newNodeWithChildren(Tag ttParent, TreeNode parent) {
        TreeNode newNode = new DefaultTreeNode(ttParent, parent);
        for (Tag tt : ttParent.getTagList()) {
            TreeNode newNode2 = newNodeWithChildren(tt, newNode);
        }
        return newNode;
    }

    public void filterDocumentPartsByTag() {
        actualDocument = new Document();
        actualDocument.setDocumentModelidDocumentModel(new Documentmodel());
        documentPartSelected = new Documentpart();
        listOfPartsByTag = selectedTagToSearchParts.getDocumentpartList();
    }

    public void filterClientsByName() {
        listOfClients.clear();

        List<Client> result = new ArrayList<>();

        EntityManager em = EmProvider.getInstance().getEntityManagerFactory().createEntityManager();
        Query q = em.createQuery("SELECT c FROM Client c WHERE c.fullName LIKE :fullName", Client.class
        );
        q.setParameter("fullName", "%" + filterClients + "%");
        result = q.getResultList();
        listOfClients = result;
    }

    public void filterDocumentsByName() {
        listDocuments.clear();

        List<Document> result = new ArrayList<>();

        EntityManager em = EmProvider.getInstance().getEntityManagerFactory().createEntityManager();
        Query q = em.createQuery("SELECT d FROM Document d WHERE d.title LIKE :title", Document.class
        );
        q.setParameter("title", "%" + filterDocumentName + "%");
        result = q.getResultList();
        listDocuments = result;
    }

    public void filterTrialsByName() {
        listOfTrialsToReplace.clear();

        List<Trial> result = new ArrayList<>();

        EntityManager em = EmProvider.getInstance().getEntityManagerFactory().createEntityManager();
        Query q = em.createQuery("SELECT t FROM Trial t WHERE t.name LIKE :name", Trial.class
        );
        q.setParameter("name", "%" + filterTrials + "%");
        result = q.getResultList();
        listOfTrialsToReplace = result;
    }

    public void filterCausesByName() {
        listOfCausesToReplace.clear();

        List<Cause> result = new ArrayList<>();

        EntityManager em = EmProvider.getInstance().getEntityManagerFactory().createEntityManager();
        Query q = em.createQuery("SELECT t FROM Cause t WHERE t.name LIKE :name", Cause.class
        );
        q.setParameter("name", "%" + filterCauses + "%");
        result = q.getResultList();
        listOfCausesToReplace = result;
    }

    public void numberOfDocumentPartsInModel() {
        if (actualDocument.getDocumentModelidDocumentModel() != null && actualDocument.getDocumentModelidDocumentModel().getCorpus() != null) {
            if (corpusToSave == null || "".equals(corpusToSave)) {
                corpusToSave = actualDocument.getDocumentModelidDocumentModel().getCorpus();
            } else {
                corpusToSave = StringUtils.replaceModelOnCorpus(corpusToSave, actualDocument.getDocumentModelidDocumentModel().getCorpus());
            }

        } else if (listModels != null && !listModels.isEmpty()) {
            corpusToSave += listModels.get(0).getCorpus();
        }

        listOfPartsToReplace.clear();
        if (corpusToSave != null) {
            String toAnalise = corpusToSave;
            int numberOfParts = StringUtils.countOcurrences(toAnalise, "!#trecho");
            for (int i = 0; i < numberOfParts; i++) {
                listOfPartsToReplace.add("!#trecho" + (i + 1) + "#!");
            }
        }
        loadClients();
    }

    //goto
    public String goToAddDocuments() {
        actualTag = new Tag();
        actualDocument = new Document();
        corpusToSave = "";
        listOfVariablesToReplace = new ArrayList<>();
        Tag root = controlTags.findTag(1);
        if (root == null) {
            root = new Tag();
            root.setLevel(0);
            root.setTitle("Direito");
            controlTags.create(root);
        }
        loadTree();
        getAllTags();
        loadTrials();
        listModels = controlDocumentmodel.findDocumentmodelEntities();
        actualTag = new Tag();
        return "/Public/DocumentsModule/AddDocument?faces-redirect=true";
    }

    public String goToManageDocuments() {
        getAllTags();
        loadTree();
        getAllDocuments();
        return "/Public/DocumentsModule/ManageDocuments?faces-redirect=true";
    }

    public String goToEditDocument() {
        corpusToSave = actualDocument.getCorpus();
        getAllTags();
        loadTree();
        loadTrials();
        return "/Public/DocumentsModule/EditDocument?faces-redirect=true";
    }

    public String goToVisualizeDocument() {
        corpusToSave = actualDocument.getCorpus();
        getAllTags();
        loadTree();
        return "/Public/DocumentsModule/VisualizeDocument?faces-redirect=true";
    }

    public void unselectTag() {
        selectedTag = null;
    }

    //Find
    public void getAllTags() {
        listTags.clear();
        listTags = controlTags.findTagEntities();
    }

    public void loadTrials() {
        listOfTrialsToReplace.clear();
        listOfTrialsToReplace = controlTrials.findTrialEntities(10, 1);
    }

    public void getAllDocuments() {
        listDocuments.clear();
        listDocuments = controlDocument.findDocumentEntities();
    }

    public void loadTrial() {
        trialToReplace = controlTrials.findTrial(trialToReplace.getIdTrial());
    }

    public void loadDocumentparts() {
        EntityManager em = EmProvider.getInstance().getEntityManagerFactory().createEntityManager();
        listDocuments = controlDocument.findDocumentEntities();
        loadTree();
    }

    public void loadClients() {
        listOfClients.clear();
        EntityManager em = EmProvider.getInstance().getEntityManagerFactory().createEntityManager();
        listOfClients = controlClient.findClientEntities(10, 0);
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
            Logger.getLogger(ManageDocumentsManagedBean.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String finishAdicionarDocument() {
        actualDocument.setCorpus(corpusToSave);

        try {
            if (actualDocument.getTagidTag() == null || actualDocument.getTitle() == null || "".equals(actualDocument.getTitle())) {
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage("Não há nenhum processo selecionado!"));
            } else {
                controlDocument.create(actualDocument);
                getAllDocuments();
                return "/Public/DocumentsModule/ManageDocuments?faces-redirect=true";

            }

        } catch (Exception ex) {
            Logger.getLogger(ManageDocumentsManagedBean.class
                    .getName()).log(Level.SEVERE, null, ex);

        }
        return "/Public/ErrorPages/ErrorIncludingDocumentNameOrTagFault?faces-redirect=true";
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
            Logger.getLogger(ManageDocumentsManagedBean.class
                    .getName()).log(Level.SEVERE, null, ex);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Falha :("));

        } catch (Exception ex) {
            Logger.getLogger(ManageDocumentsManagedBean.class
                    .getName()).log(Level.SEVERE, null, ex);
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
    public String finishEditDocument() {
        actualDocument = controlDocument.findDocument(actualDocument.getIdDocument());
        actualDocument.setCorpus(corpusToSave);
        try {
            if (actualDocument.getTagidTag() == null) {
                actualDocument.setTagidTag(controlDocument.findDocument(actualDocument.getIdDocument()).getTagidTag());
                controlDocument.edit(actualDocument);
            } else {
                controlDocument.edit(actualDocument);

            }
        } catch (Exception ex) {
            Logger.getLogger(ManageDocumentsManagedBean.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        actualDocument = new Document();
        getAllDocuments();
        return "/Public/DocumentsModule/ManageDocuments?faces-redirect=true";
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

    public void removeDocument() {
        try {
            controlDocument.destroy(actualDocument.getIdDocument());
            actualDocument = new Document();
            getAllDocuments();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //Apply filters
    public void filterDocumentsByTag() {
        listModels.clear();
        listDocumentParts.clear();

        listDocumentParts = new ArrayList<>();
        listOfPartsToReplace = new ArrayList<>();
        listOfPartsByTag = new ArrayList<>();

        documentPartToReplace = new String();
        selectedTagToSearchParts = new Tag();
        documentPartSelected = new Documentpart();
        clientSelectedToReplace = new Client();

        selectedTag = controlTags.findTag(selectedTag.getIdTag());
        listModels = selectedTag.getDocumentmodelList();
        listDocuments = selectedTag.getDocumentList();
        listDocumentParts = selectedTag.getDocumentpartList();
    }

    public void filterCompanysByName() {
        listOfCompanys.clear();

        List<Company> result = new ArrayList<>();

        EntityManager em = EmProvider.getInstance().getEntityManagerFactory().createEntityManager();
        Query q = em.createQuery("SELECT t FROM Company t WHERE t.name LIKE :name", Company.class
        );
        q.setParameter("name", "%" + filterCompanys + "%");
        result = q.getResultList();
        listOfCompanys = result;
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

    //gets and sets
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

    public Documentmodel getSelectedDocumentModels() {
        return selectedDocumentModels;
    }

    public void setSelectedDocumentModels(Documentmodel selectedDocumentModels) {
        this.selectedDocumentModels = selectedDocumentModels;
    }

    public Document getActualDocument() {
        return actualDocument;
    }

    public void setActualDocument(Document actualDocument) {
        this.actualDocument = actualDocument;
    }

    public List<Tag> getListTags() {
        return listTags;
    }

    public void setListTags(List<Tag> listTags) {
        this.listTags = listTags;
    }

    public List<Document> getListDocuments() {
        return listDocuments;
    }

    public void setListDocuments(List<Document> listDocuments) {
        this.listDocuments = listDocuments;
    }

    public List<Documentmodel> getListModels() {
        return listModels;
    }

    public void setListModels(List<Documentmodel> listModels) {
        this.listModels = listModels;
    }

    public TreeNode getRoot() {
        return TagsTree;
    }

    public void setRoot(TreeNode root) {
        this.TagsTree = root;
    }

    public List<Documentpart> getListDocumentParts() {
        return listDocumentParts;
    }

    public void setListDocumentParts(List<Documentpart> listDocumentParts) {
        this.listDocumentParts = listDocumentParts;
    }

    public List<String> getListOfPartsToReplace() {
        return listOfPartsToReplace;
    }

    public void setListOfPartsToReplace(List<String> listOfSelectedDocumentParts) {
        this.listOfPartsToReplace = listOfSelectedDocumentParts;
    }

    public Documentpart getSelectedDocumentpart() {
        return selectedDocumentpart;
    }

    public void setSelectedDocumentpart(Documentpart selectedDocumentpart) {
        this.selectedDocumentpart = selectedDocumentpart;
    }

    public String getDocumentPartToReplace() {
        return documentPartToReplace;
    }

    public void setDocumentPartToReplace(String documentPartToReplace) {
        this.documentPartToReplace = documentPartToReplace;
    }

    public Tag getSelectedTagToSearchParts() {
        return selectedTagToSearchParts;
    }

    public void setSelectedTagToSearchParts(Tag selectedTagToSearchParts) {
        this.selectedTagToSearchParts = selectedTagToSearchParts;
    }

    public Documentpart getDocumentPartSelected() {
        return documentPartSelected;
    }

    public void setDocumentPartSelected(Documentpart documentPartSelected) {
        this.documentPartSelected = documentPartSelected;
    }

    public Client getClientSelectedToReplace() {
        return clientSelectedToReplace;
    }

    public void setClientSelectedToReplace(Client clientSelectedToReplace) {
        this.clientSelectedToReplace = clientSelectedToReplace;
    }

    public List<Client> getListOfClients() {
        return listOfClients;
    }

    public void setListOfClients(List<Client> listOfClients) {
        this.listOfClients = listOfClients;
    }

    public List<Documentpart> getListOfPartsByTag() {
        return listOfPartsByTag;
    }

    public void setListOfPartsByTag(List<Documentpart> listOfPartsByTag) {
        this.listOfPartsByTag = listOfPartsByTag;
    }

    public String getCorpusToSave() {
        return corpusToSave;
    }

    public void setCorpusToSave(String corpusToSave) {
        this.corpusToSave = corpusToSave;
    }

    public Trial getTrialToReplace() {
        return trialToReplace;
    }

    public void setTrialToReplace(Trial trialToReplace) {
        this.trialToReplace = trialToReplace;
    }

    public TreeNode getTagsTree() {
        return TagsTree;
    }

    public void setTagsTree(TreeNode TagsTree) {
        this.TagsTree = TagsTree;
    }

    public List<Trial> getListOfTrialsToReplace() {
        return listOfTrialsToReplace;
    }

    public void setListOfTrialsToReplace(List<Trial> listOfTrialsToReplace) {
        this.listOfTrialsToReplace = listOfTrialsToReplace;
    }

    public String getFilterClients() {
        return filterClients;
    }

    public void setFilterClients(String filterClients) {
        this.filterClients = filterClients;
    }

    public String getFilterTrials() {
        return filterTrials;
    }

    public void setFilterTrials(String filterTrials) {
        this.filterTrials = filterTrials;
    }

    public String getFilterDocumentName() {
        return filterDocumentName;
    }

    public void setFilterDocumentName(String filterDocumentName) {
        this.filterDocumentName = filterDocumentName;
    }

    public List<Causevariable> getListOfVariablesToReplace() {
        return listOfVariablesToReplace;
    }

    public void setListOfVariablesToReplace(List<Causevariable> listOfVariablesToReplace) {
        this.listOfVariablesToReplace = listOfVariablesToReplace;
    }

    public List<Cause> getListOfCausesToReplace() {
        return listOfCausesToReplace;
    }

    public void setListOfCausesToReplace(List<Cause> listOfCausesToReplace) {
        this.listOfCausesToReplace = listOfCausesToReplace;
    }

    public Cause getCauseToReplace() {
        return causeToReplace;
    }

    public void setCauseToReplace(Cause causeToReplace) {
        this.causeToReplace = causeToReplace;
    }

    public String getFilterCauses() {
        return filterCauses;
    }

    public void setFilterCauses(String filterCauses) {
        this.filterCauses = filterCauses;
    }

    public String getFilterCompanys() {
        return filterCompanys;
    }

    public void setFilterCompanys(String filterCompanys) {
        this.filterCompanys = filterCompanys;
    }

    public List<Company> getListOfCompanys() {
        return listOfCompanys;
    }

    public void setListOfCompanys(List<Company> listOfCompanys) {
        this.listOfCompanys = listOfCompanys;
    }

    public Company getCompanySelectedToReplace() {
        return companySelectedToReplace;
    }

    public void setCompanySelectedToReplace(Company companySelectedToReplace) {
        this.companySelectedToReplace = companySelectedToReplace;
    }

    public String getTypeOfPerson() {
        return typeOfPerson;
    }

    public void setTypeOfPerson(String typeOfPerson) {
        this.typeOfPerson = typeOfPerson;
    }
}
