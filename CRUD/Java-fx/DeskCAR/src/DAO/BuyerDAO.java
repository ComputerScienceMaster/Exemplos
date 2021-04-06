package DAO;

import controllerJPA.BuyerJpaController;
import controllerJPA.EmProvider;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import model.Buyer;

public class BuyerDAO {
    
    //Listas
    private ArrayList<Buyer> listOfBuyers = new ArrayList<>();
    
    //Controlador JPA
    private BuyerJpaController controllBuyer = new BuyerJpaController(EmProvider.getInstance().getEntityManagerFactory());

    // ------------------------------------- Inserção -------------------------------------
    public void insertBuyer(Buyer buyer){
        try {
            controllBuyer.create(buyer);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Não foi pussivel efetuar o cadastro.");
        }
    }
    
    // ------------------------------------- Metodos Auxiliares -------------------------------------
    private void populateListOfBuyer(){
        listOfBuyers = new ArrayList(controllBuyer.findBuyerEntities());
    }
    
    // ------------------------------------- Getters e Setters -------------------------------------
    public ArrayList getListOfBuyer(){
        populateListOfBuyer();
        return listOfBuyers;
    }

    public Buyer getUser(long id){
        Buyer buyer = controllBuyer.findBuyer(id);
        return buyer;
    }
}
