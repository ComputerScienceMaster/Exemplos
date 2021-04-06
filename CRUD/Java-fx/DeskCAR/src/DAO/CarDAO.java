package DAO;

import controllerJPA.ColorJpaController;
import controllerJPA.ModelJpaController;
import controllerJPA.FuelJpaController;
import controllerJPA.CarEngineJpaController;
import controllerJPA.AttributeJpaController;
import controllerJPA.CarJpaController;
import java.util.ArrayList;
import model.*;
import controllerJPA.*;
import java.util.List;
import javax.persistence.EntityManager;
import javax.smartcardio.ATR;
import javax.swing.JOptionPane;

public class CarDAO {

    //Listas
    private ArrayList<Car> listOfCars = new ArrayList<>();
    private ArrayList<Car> aux = new ArrayList<>();
    private ArrayList<Model> listOfModels = new ArrayList<>();
    private ArrayList<Attribute> listOfAttributes = new ArrayList<>();
    private ArrayList<Color> listOfColors = new ArrayList<>();
    private ArrayList<CarEngine> listOfEngines = new ArrayList<>();
    private ArrayList<Fuel> listOfFuels = new ArrayList<>();

    //Controlador JPA
    private CarJpaController controllCar = new CarJpaController(EmProvider.getInstance().getEntityManagerFactory());
    private ModelJpaController controllModel = new ModelJpaController(EmProvider.getInstance().getEntityManagerFactory());
    private AttributeJpaController controllAttributes = new AttributeJpaController(EmProvider.getInstance().getEntityManagerFactory());
    private ColorJpaController controllColor = new ColorJpaController(EmProvider.getInstance().getEntityManagerFactory());
    private CarEngineJpaController controllEngine = new CarEngineJpaController(EmProvider.getInstance().getEntityManagerFactory());
    private FuelJpaController controllFuel = new FuelJpaController(EmProvider.getInstance().getEntityManagerFactory());

    // ------------------------------------- Inserção, Edição e Remoção -------------------------------------
    public void insertCar(Car car) {
        try {
            controllCar.create(car);
            JOptionPane.showMessageDialog(null, "Carro adicionado com sucesso.");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Não foi possivel inserir o veículo.");
        }
    }

    public void removeCar(int id) {
        try {
            controllCar.destroy(id);
            JOptionPane.showMessageDialog(null, "Carro removido com sucesso.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Não foi possivel remover este veiculo.");
        }
    }

    public void editCar(Car car) {
        try {
            controllCar.edit(car);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Não foi possivél solicitar a alteração no estado do veículo.");
        }
    }

    // ------------------------------------- Consultas -------------------------------------
    public ArrayList<Car> getCarsToSale() {
        EntityManager em = EmProvider.getInstance().getEntityManagerFactory().createEntityManager();
        List<Car> type = em.createQuery("SELECT a FROM Car a WHERE a.disponibility = 1", Car.class)
                .getResultList();
        return listOfCars = new ArrayList<>(type);
    }

    public ArrayList<Car> getCarsToAccept() {
        EntityManager em = EmProvider.getInstance().getEntityManagerFactory().createEntityManager();
        List<Car> type = em.createQuery("SELECT a FROM Car a WHERE a.disponibility = 2", Car.class)
                .getResultList();
        return listOfCars = new ArrayList<>(type);
    }

    public ArrayList<Car> getCarsSold() {
        EntityManager em = EmProvider.getInstance().getEntityManagerFactory().createEntityManager();
        List<Car> type = em.createQuery("SELECT a FROM Car a WHERE a.disponibility = 3", Car.class)
                .getResultList();
        return listOfCars = new ArrayList<>(type);
    }

    public ArrayList<Car> getCarsToSaleSeached(String search) {
        EntityManager em = EmProvider.getInstance().getEntityManagerFactory().createEntityManager();
        List<Car> type = em.createQuery("SELECT di FROM Car di JOIN di.model u WHERE u.modelName LIKE :search AND di.disponibility = 1", Car.class)
                .setParameter("search", "%" + search + "%")
                .getResultList();
        return listOfCars = new ArrayList<>(type);
    }
    
    public Car getCar(int id) {
        Car c = controllCar.findCar(id);
        return c;
    }

    // ------------------------------------- Metodos de população das listas -------------------------------------
    private void populaListaDeCarros() {
        listOfCars = new ArrayList(controllCar.findCarEntities());
    }

    private void populateListOfModels() {
        listOfModels = new ArrayList(controllEngine.findCarEngineEntities());
    }

    private void populateListOfAttributes() {
        listOfAttributes = new ArrayList(controllAttributes.findAttributeEntities());
    }

    private void populateListOfColors() {
        listOfColors = new ArrayList(controllColor.findColorEntities());
    }

    private void populateListOfFuels() {
        listOfFuels = new ArrayList(controllFuel.findFuelEntities());
    }

    private void populateListOfEngines() {
        listOfEngines = new ArrayList(controllEngine.findCarEngineEntities());
    }

    // ------------------------------------- Getters e Setters -------------------------------------
    public ArrayList<Model> getListOfModels() {
        populateListOfModels();
        return listOfModels;
    }

    public ArrayList<Attribute> getListOfAttributes() {
        populateListOfAttributes();
        return listOfAttributes;
    }

    public ArrayList<Color> getListOfColors() {
        populateListOfColors();
        return listOfColors;
    }

    public ArrayList<CarEngine> getListOfEngines() {
        populateListOfEngines();
        return listOfEngines;
    }

    public ArrayList<Fuel> getListOfFuels() {
        populateListOfFuels();
        return listOfFuels;
    }

    public Attribute getAttribute(int id) {
        return controllAttributes.findAttribute(id);
    }
    
    public Model getModel(int id){
        return controllModel.findModel(id);
    }
    
    public Color getColor(int id){
        return controllColor.findColor(id);
    }
    
    public Fuel getFuel(int id){
        return controllFuel.findFuel(id);
    }
    
    public CarEngine getEngine(int id){
        return controllEngine.findCarEngine(id);
    }
}
