package view;

import javax.faces.bean.ManagedBean;
import controller.CarJpaController;
import controller.SectorJpaController;
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
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import model.Car;
import model.Sector;
import model.Travel;
import model.UserAccount;

@ManagedBean(name = "CarManagedBean")
@SessionScoped
public class CarManagedBean {

    //lists
    private ArrayList<Car> listOfCars = new ArrayList<>();
    private ArrayList<Sector> listOfSectors = new ArrayList<>();
    private ArrayList<Travel> listOfTravels = new ArrayList<>();

    //actual
    private Sector actualSector = new Sector();
    private Car actualCar = new Car();

    //coontrollers
    private CarJpaController controlCar = new CarJpaController(EmProvider.getInstance().getEntityManagerFactory());
    private SectorJpaController controlSector = new SectorJpaController(EmProvider.getInstance().getEntityManagerFactory());
    private UserAccountJpaController controlUserAccount = new UserAccountJpaController(EmProvider.getInstance().getEntityManagerFactory());

    //aux
    private String filterSituation;

    public CarManagedBean() {
    }

    //gotos
    public String gotoAddCars() {
        actualCar = new Car();
        return "/public/manageCar/addCars.xhtml?faces-redirect=true";
    }

    public String gotoListCars() {
        loadCars();
        return "/public/manageCar/ManageCars.xhtml?faces-redirect=true";
    }
    
    public String gotoVisualizeTravels() {
        filterByCar();
        return "/public/manageCar/VisualizeTravels.xhtml?faces-redirect=true";
    }

    public String gotoEditCars() {
        UserAccount myself = controlUserAccount.findUserAccount(ManageSessions.getUserId());

        if (myself.getIsAdministrator() || myself.getSectoridSector().getName().equals(actualCar.getSectoridSector().getName())) {
            return "/public/manageCar/EditCars.xhtml?faces-redirect=true";
        } else {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Você não tem Permissão para editar este carro", "!"));
            return "#";
        }
    }

    //loads
    public void loadCars() {
        listOfCars = new ArrayList(controlCar.findCarEntities());
        Calendar cal = Calendar.getInstance();
        Date today = cal.getTime();
        for (Car c : listOfCars) {
            Car cp = c;
            List<Travel> listOfTravelByCar = c.getTravelList();
            for (Travel t : listOfTravelByCar) {
                if (!t.getCarPlate().getSituation().equals("Em Manutenção")) {
                    if (removeTime(t.getDateInitial()).equals(removeTime(today)) && isHappeningNow(t.getTimeInitial(), t.getTimeEnd())) {
                        c.setSituation("Ocupado");
                    } else {
                        c.setSituation("Livre");
                    }
                }
                if (cp.getSituation().equals(c.getSituation())) {
                    try {
                        controlCar.edit(c);
                    } catch (NonexistentEntityException ex) {
                        Logger.getLogger(CarManagedBean.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (Exception ex) {
                        Logger.getLogger(CarManagedBean.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }

    public Date removeTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public boolean isHappeningNow(String timeInitial, String timeEnd) {

        Calendar c = Calendar.getInstance();
        Date now = c.getTime();

        //creating timeinitial in date type
        Calendar ti = Calendar.getInstance();
        ti.setTime(now);
        ti.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeInitial.substring(0, timeInitial.indexOf(":"))));
        ti.set(Calendar.MINUTE, Integer.parseInt(timeInitial.substring(timeInitial.indexOf(":") + 1)));
        ti.set(Calendar.SECOND, 0);
        ti.set(Calendar.MILLISECOND, 0);

        //creating timeend in date type
        Calendar te = Calendar.getInstance();
        te.setTime(now);
        te.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeEnd.substring(0, timeEnd.indexOf(":"))));
        te.set(Calendar.MINUTE, Integer.parseInt(timeEnd.substring(timeEnd.indexOf(":") + 1)));
        te.set(Calendar.SECOND, 0);
        te.set(Calendar.MILLISECOND, 0);

        //today
        if (ti.compareTo(c) < 0 && te.compareTo(c) > 0) {
            return true;
        }

        return false;
    }

    public void loadSectors() {
        listOfSectors = new ArrayList(controlSector.findSectorEntities());
    }

    //filter
    public void filterBySectors() {
        listOfCars = new ArrayList<>(actualSector.getCarList());
    }

    public void filterBySituation() {
        listOfCars.clear();
        EntityManager em = EmProvider.getInstance().getEntityManagerFactory().createEntityManager();
        List<Car> cars = em.createQuery("SELECT c FROM Car c WHERE c.situation = :situation", Car.class)
                .setParameter("situation", filterSituation)
                .getResultList();
        listOfCars = new ArrayList<>(cars);
    }

    public void filterByCar() {
        listOfTravels.clear();
        List<Travel> travels = actualCar.getTravelList();
        List<Travel> filtered = new ArrayList<>();
        Calendar c = Calendar.getInstance();
        Date today = c.getTime();
        if (today != null) {
            for (Travel t : travels) {
                if ((removeTime(t.getDateInitial()).compareTo(removeTime(today)) == 0 
                        || removeTime(t.getDateInitial()).compareTo(removeTime(today)) > 0) 
                        && !t.getIsDone()) {
                    filtered.add(t);
                }
            }
        }
        listOfTravels = new ArrayList<>(filtered);
    }

    //saves
    public String saveCars() {
        try {
            controlCar.create(actualCar);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gotoListCars();
    }

    public String editCar() {
        try {
            controlCar.edit(actualCar);
            loadCars();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return gotoListCars();
    }

    public void destroyCars() throws NonexistentEntityException {
        UserAccount myself = controlUserAccount.findUserAccount(ManageSessions.getUserId());
        try {
            if (myself.getIsAdministrator() || myself.getSectoridSector().getName().equals(actualCar.getSectoridSector().getName())) {
                controlCar.destroy(actualCar.getPlate());
            } else {
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Você não tem Permissão para remover este carro", "!"));
            }
        } catch (IllegalOrphanException ex) {
            ex.printStackTrace();
        }
        gotoListCars();
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

    public ArrayList<Sector> getListOfSectors() {
        return listOfSectors;
    }

    public void setListOfSectors(ArrayList<Sector> listOfSectors) {
        this.listOfSectors = listOfSectors;
    }

    public Sector getActualSector() {
        return actualSector;
    }

    public void setActualSector(Sector actualSector) {
        this.actualSector = actualSector;
    }

    public String getFilterSituation() {
        return filterSituation;
    }

    public void setFilterSituation(String filterSituation) {
        this.filterSituation = filterSituation;
    }

    public ArrayList<Travel> getListOfTravels() {
        return listOfTravels;
    }

    public void setListOfTravels(ArrayList<Travel> listOfTravels) {
        this.listOfTravels = listOfTravels;
    }

}
