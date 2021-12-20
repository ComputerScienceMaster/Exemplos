
package View;

import Controller.PersonJpaController;
import Controller.exceptions.NonexistentEntityException;
import Model.Person;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
 
@ManagedBean
@SessionScoped
public class managePersonBean {
    private Person actualPerson = new Person();
    private List<Person> listOfPersons = new ArrayList<>();
    private PersonJpaController personControl = new PersonJpaController(EmProvider.getInstance().getEntityManagerFactory());
    
    public String gotoCreatePerson(){
        actualPerson = new Person();
        return "/Public/PersonModule/addPerson?faces-redirect=true";
    }
    
    public String gotoManagePersons(){
        listOfPersons = new ArrayList<>();
        return "/Public/PersonModule/ManagePerson?faces-redirect=true";
    }
    
     public String gotoEditPerson(){
        return "/Public/PersonModule/editPerson?faces-redirect=true";
    }
    
    public String create(){
        personControl.create(actualPerson);
        return "/Public/PersonModule/ManagePerson?faces-redirect=true";
    }
    
    public String delete(){
        try {
            personControl.destroy(actualPerson.getId());
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(managePersonBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "/Public/PersonModule/ManagePerson?faces-redirect=true";
    }
    
    public String edit(){
        try {
            personControl.edit(actualPerson);
        } catch (Exception ex) {
            Logger.getLogger(managePersonBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "/Public/PersonModule/ManagePerson?faces-redirect=true";
    }
    public void loadPersons(){
        listOfPersons = personControl.findPersonEntities();
    }
    public managePersonBean() {
    }

    public Person getActualPerson() {
        return actualPerson;
    }

    public void setActualPerson(Person actualPerson) {
        this.actualPerson = actualPerson;
    }

    public List<Person> getListOfPersons() {
        return listOfPersons;
    }

    public void setListOfPersons(List<Person> listOfPersons) {
        this.listOfPersons = listOfPersons;
    }
    
    
    
    
}
