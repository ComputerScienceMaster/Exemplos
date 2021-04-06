package view;

import Util.StringUtils;
import controller.CarJpaController;
import controller.TaskJpaController;
import controller.TravelJpaController;
import controller.UserAccountJpaController;
import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import model.Car;
import model.Task;
import model.Travel;
import model.UserAccount;
import org.primefaces.model.DualListModel;

@ManagedBean
@SessionScoped

public class TravelManagedBean {

    //Actual
    private Travel actualTravel = new Travel();
    private Car actualCar = new Car();
    private Task actualTask = new Task();
    private UserAccount actualUserAccount = new UserAccount();
    //List
    private ArrayList<Travel> listOfTravels = new ArrayList<>();
    private ArrayList<Car> listOfCars = new ArrayList<>();
    private ArrayList<Task> listOfTasks = new ArrayList<>();
    private ArrayList<UserAccount> listOfUserAccounts = new ArrayList<>();

    //Controller
    private TravelJpaController controlTravel = new TravelJpaController(EmProvider.getInstance().getEntityManagerFactory());
    private CarJpaController controlCar = new CarJpaController(EmProvider.getInstance().getEntityManagerFactory());
    private TaskJpaController controlTask = new TaskJpaController(EmProvider.getInstance().getEntityManagerFactory());
    private UserAccountJpaController controlUser = new UserAccountJpaController(EmProvider.getInstance().getEntityManagerFactory());

    //auxiliarys
    private Date dateInitial;
    private Date dateEnd;
    private String hourInitial;
    private String hourEnd;
    private String minuteInitial;
    private String minuteEnd;
    private DualListModel<String> users = new DualListModel<>();

    //filters
    private String filterDestination;
    private Car filterCar;
    private String filterActivation;
    private Date filterDate;

    public TravelManagedBean() {
    }

    public String gotoAddTravel() {
        actualTravel = new Travel();
        hourInitial = "00";
        hourEnd = "00";
        minuteInitial = "00";
        minuteEnd = "00";
        loadCars();
        loadUsers();
        return "/public/manageTravel/addTravel.xhtml?faces-redirect=true";
    }

    public String gotoListTravels() {
        return "/public/manageTravel/ManageTravel.xhtml?faces-redirect=true";
    }

    public String gotoDetails() {
        loadTasks();
        actualTask = new Task();
        return "/public/manageTravel/detailsTravel.xhtml?faces-redirect=true";
    }

    public String gotoEditTravel() {
        if (isThisMyself(actualTravel.getOwner())) {
            return "/public/manageTravel/EditTravel.xhtml?faces-redirect=true";
        } else {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Você não têm permissão para editar esta ação", "!"));
            return "#";
        }
    }

    public void loadTravels() {
        listOfTravels = new ArrayList(controlTravel.findTravelEntities());
    }

    public void loadCars() {
        listOfCars = new ArrayList(controlCar.findCarEntities());
    }

    //filters
    public void findActiveTravels() {
        listOfTravels.clear();
        EntityManager em = EmProvider.getInstance().getEntityManagerFactory().createEntityManager();
        List<Travel> travel = em.createQuery("SELECT t FROM Travel t WHERE t.isDone = 'false'", Travel.class)
                .getResultList();
        listOfTravels = new ArrayList<>(travel);
    }

    public void filterByDate() {
            listOfTravels.clear();
            EntityManager em = EmProvider.getInstance().getEntityManagerFactory().createEntityManager();
            List<Travel> travel = em.createQuery("SELECT t FROM Travel t WHERE t.dateInitial = :dateInitial and t.isDone='false'", Travel.class).setParameter("dateInitial", filterDate).getResultList();
            listOfTravels = new ArrayList<>(travel);
    }

    public void filterByActivation() {
        if ("naoFinalizadas".equals(filterActivation)) {
            EntityManager em = EmProvider.getInstance().getEntityManagerFactory().createEntityManager();
            List<Travel> travel = em.createQuery("SELECT t FROM Travel t WHERE t.isDone = 'false'", Travel.class)
                    .getResultList();
            listOfTravels = new ArrayList<Travel>(travel);
            return;
        }
        listOfTravels = new ArrayList<>(controlTravel.findTravelEntities());
    }

    public void filterTravelsByDestination() {
        listOfTravels.clear();
        EntityManager em = EmProvider.getInstance().getEntityManagerFactory().createEntityManager();
        List<Travel> travels = em.createQuery("SELECT a FROM Travel a WHERE a.destination LIKE :destination and a.isDone='false'", Travel.class)
                .setParameter("destination", "%" + filterDestination + "%")
                .getResultList();
        listOfTravels = new ArrayList<>(travels);
    }

    // loads
    public void loadUsers() {
        listOfUserAccounts = new ArrayList(controlUser.findUserAccountEntities());
        List<String> source = new ArrayList<>();
        List<String> target = new ArrayList<>();

        for (UserAccount a : listOfUserAccounts) {
            source.add(a.getUserLogin());
        }
        users = new DualListModel<String>(source, target);
    }

    public void loadTasks() {
        actualTravel = controlTravel.findTravel(actualTravel.getIdTravel());
        listOfTasks = new ArrayList<>(actualTravel.getTaskList());
    }

    //saves
    public String saveTravels() {
        if (actualCar.getSituation().equals("Em Manutenção")) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Este carro está em manutenção", "!"));
            return "#";
        }
        UserAccount myself = new UserAccount();
        myself.setUserLogin(ManageSessions.getUserId());
        List<UserAccount> usersToAdd = new ArrayList<>();
        for (String a : users.getTarget()) {
            usersToAdd.add(controlUser.findUserAccount(a));
        }

        hourInitial = StringUtils.treatHoursAndMinutesWithOneDigit(hourInitial);
        hourEnd = StringUtils.treatHoursAndMinutesWithOneDigit(hourEnd);
        minuteInitial = StringUtils.treatHoursAndMinutesWithOneDigit(minuteInitial);
        minuteEnd = StringUtils.treatHoursAndMinutesWithOneDigit(minuteEnd);

        try {
            String timeInitial = hourInitial + ":" + minuteInitial;
            String timeEnd = hourEnd + ":" + minuteEnd;
            actualTravel.setTimeInitial(timeInitial);
            actualTravel.setTimeEnd(timeEnd);
            actualTravel.setOwner(myself);
            actualTravel.setDateEnd(dateEnd);
            actualTravel.setUserAccountList(usersToAdd);
            actualTravel.setCarPlate(actualCar);
            if (dateInitial != null) {
                actualTravel.setDateInitial(dateInitial);
            } else {
                Calendar c = Calendar.getInstance();
                actualTravel.setDateInitial(c.getTime());

            }
            actualTravel.setIsDone(false);
            controlTravel.create(actualTravel);
            return gotoListTravels();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "#";
    }

    public void submitToThisTrip() {
        if (actualTravel.getIsRideAllowed() == false) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Esta viagem não permite caronas", "!"));
            return;
        }
        List<UserAccount> listToEdit = actualTravel.getUserAccountList();
        try {
            UserAccount toAdd = controlUser.findUserAccount(ManageSessions.getUserId());
            for (UserAccount a : listToEdit) {
                if (a.getUserLogin().equals(ManageSessions.getUserId())) {
                    FacesContext context = FacesContext.getCurrentInstance();
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Você já está nessa viagem", "!"));
                    return;
                }
            }
            listToEdit.add(toAdd);
            controlTravel.edit(actualTravel);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void removeUserFromTravel() {
        UserAccount myself = controlUser.findUserAccount(ManageSessions.getUserId());
        List<UserAccount> listToEdit = actualTravel.getUserAccountList();
        try {
            if (isThisMyself(actualUserAccount) || myself.getIsAdministrator()) {
                listToEdit.remove(actualUserAccount);
                actualTravel.setUserAccountList(listToEdit);
                controlTravel.edit(actualTravel);
            } else {
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Opa! você não tem permissão pra remover outras pessoas de uma viagem", "!"));
                return;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setTravelDone() {
        try {
            actualTravel.setIsDone(true);
            controlTravel.edit(actualTravel);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void saveTask() {
        UserAccount myself = controlUser.findUserAccount(ManageSessions.getUserId());
        try {
            actualTask.setUseraccountuserLogin(myself);
            actualTask.setTravelIdTravel(actualTravel);
            controlTask.create(actualTask);
            actualTask = new Task();
            loadTasks();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //edits
    public String editTravel() {
        actualTravel = controlTravel.findTravel(actualTravel.getIdTravel());

        hourInitial = StringUtils.treatHoursAndMinutesWithOneDigit(hourInitial);
        hourEnd = StringUtils.treatHoursAndMinutesWithOneDigit(hourEnd);
        minuteInitial = StringUtils.treatHoursAndMinutesWithOneDigit(minuteInitial);
        minuteEnd = StringUtils.treatHoursAndMinutesWithOneDigit(minuteEnd);

        try {
            String timeInitial = hourInitial + ":" + minuteInitial;
            String timeEnd = hourEnd + ":" + minuteEnd;
            actualTravel.setTimeInitial(timeInitial);
            actualTravel.setTimeEnd(timeEnd);
            actualTravel.setDateEnd(dateEnd);
            actualTravel.setCarPlate(actualCar);
            if (dateInitial != null) {
                actualTravel.setDateInitial(dateInitial);
            } else {
                Calendar c = Calendar.getInstance();
                actualTravel.setDateInitial(c.getTime());
            }
            controlTravel.edit(actualTravel);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return gotoListTravels();
    }

    //destroy
    public void destroyTravels() {
        UserAccount myself = controlUser.findUserAccount(ManageSessions.getUserId());
        try {
            if (myself.getIsAdministrator() || (actualTravel.getOwner() != null && actualTravel.getOwner().getUserLogin().equals(myself.getUserLogin()))) {
                for (Task t : actualTravel.getTaskList()) {
                    controlTask.destroy(t.getIdTask());
                }
                controlTravel.destroy(actualTravel.getIdTravel());
                loadTravels();
            } else {
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Opa! você não tem permissão para remover essa viagem", "!"));
                return;
            }

        } catch (IllegalOrphanException ex) {
            Logger.getLogger(TravelManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(TravelManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void destroyTask() {
        UserAccount myself = controlUser.findUserAccount(ManageSessions.getUserId());
        try {
            if (isThisMyself(actualTask.getUseraccountuserLogin()) || myself.getIsAdministrator()) {
                controlTask.destroy(actualTask.getIdTask());
                loadTasks();
            } else {
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Opa! você não tem permissão para excluir tarefas de outras pessoas", "!"));
                return;
            }
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(TravelManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean isThisMyself(UserAccount toVerify) {
        String userInSession = ManageSessions.getUserId();
        if (userInSession.equals(toVerify.getUserLogin())) {
            return true;
        }
        return false;
    }

    //Gets and sets
    public ArrayList<Travel> getListOfTravels() {
        return listOfTravels;
    }

    public void setListOfTravels(ArrayList<Travel> listOfTravels) {
        this.listOfTravels = listOfTravels;
    }

    public Travel getActualTravel() {
        return actualTravel;
    }

    public void setActualTravel(Travel actualTravel) {
        this.actualTravel = actualTravel;
    }

    public Car getActualCar() {
        return actualCar;
    }

    public void setActualCar(Car actualCar) {
        this.actualCar = actualCar;
    }

    public ArrayList<Car> getListOfCars() {
        return listOfCars;
    }

    public void setListOfCars(ArrayList<Car> listOfCars) {
        this.listOfCars = listOfCars;
    }

    public Date getDateInitial() {
        return dateInitial;
    }

    public void setDateInitial(Date dateInitial) {
        this.dateInitial = dateInitial;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Task getActualTask() {
        return actualTask;
    }

    public void setActualTask(Task actualTask) {
        this.actualTask = actualTask;
    }

    public ArrayList<Task> getListOfTasks() {
        return listOfTasks;
    }

    public void setListOfTasks(ArrayList<Task> listOfTasks) {
        this.listOfTasks = listOfTasks;
    }

    public UserAccount getActualUserAccount() {
        return actualUserAccount;
    }

    public void setActualUserAccount(UserAccount actualUserAccount) {
        this.actualUserAccount = actualUserAccount;
    }

    public ArrayList<UserAccount> getListOfUserAccounts() {
        return listOfUserAccounts;
    }

    public void setListOfUserAccounts(ArrayList<UserAccount> listOfUserAccounts) {
        this.listOfUserAccounts = listOfUserAccounts;
    }

    public DualListModel<String> getUsers() {
        return users;
    }

    public void setUsers(DualListModel<String> users) {
        this.users = users;
    }

    public String getFilterDestination() {
        return filterDestination;
    }

    public void setFilterDestination(String filterDestination) {
        this.filterDestination = filterDestination;
    }

    public Car getFilterCar() {
        return filterCar;
    }

    public void setFilterCar(Car filterCar) {
        this.filterCar = filterCar;
    }

    public String getFilterActivation() {
        return filterActivation;
    }

    public void setFilterActivation(String filterActivation) {
        this.filterActivation = filterActivation;
    }

    public String getHourInitial() {
        return hourInitial;
    }

    public void setHourInitial(String hourInitial) {
        this.hourInitial = hourInitial;
    }

    public String getHourEnd() {
        return hourEnd;
    }

    public void setHourEnd(String hourEnd) {
        this.hourEnd = hourEnd;
    }

    public String getMinuteInitial() {
        return minuteInitial;
    }

    public void setMinuteInitial(String minuteInitial) {
        this.minuteInitial = minuteInitial;
    }

    public String getMinuteEnd() {
        return minuteEnd;
    }

    public void setMinuteEnd(String minuteEnd) {
        this.minuteEnd = minuteEnd;
    }

    public Date getFilterDate() {
        return filterDate;
    }

    public void setFilterDate(Date filterDate) {
        this.filterDate = filterDate;
    }

}
