package view;

import model.Task;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import controller.TaskJpaController;
import controller.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import javax.faces.bean.SessionScoped;

@ManagedBean(name="TaskManagedBean")
@SessionScoped
public class TaskManagedBean {

    private ArrayList<Task> listOfTasks = new ArrayList<>();
    private Task ActualTask = new Task();
    private TaskJpaController controlTask = new TaskJpaController(EmProvider.getInstance().getEntityManagerFactory());

    public TaskManagedBean() {
    }
    
    //gotos
    public String gotoAddTasks(){
        ActualTask = new Task();
        return "/public/manageTask/addTasks.xhtml?faces-redirect=true";
    }
    
    public String gotoListTasks(){
        loadTasks();
        return "/public/manageTask/ManageTasks.xhtml?faces-redirect=true";
    }
    
    public String gotoEditUsers(){
        return "/public/manageTask/EditTasks.xhtml?faces-redirect=true";
    }

    public void loadTasks() {
        listOfTasks = new ArrayList(controlTask.findTaskEntities());
    }

    public String saveTasks() {
        try {
            controlTask.create(ActualTask);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gotoListTasks();
    }
  
    public String editTask() {
        try {
            controlTask.edit(ActualTask);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return gotoListTasks();
    }

    public void destroyTasks() throws NonexistentEntityException {
        controlTask.destroy(ActualTask.getIdTask());
        gotoListTasks();
    }

    public Task getActualTask() {
        return ActualTask;
    }

    public void setActualTask(Task actualTask) {
        this.ActualTask = actualTask;
    }

    public ArrayList<Task> getListOfTasks() {
        return listOfTasks;
    }

    public void setListOfTasks(ArrayList<Task> listOfTasks) {
        this.listOfTasks = listOfTasks;
    }
}
