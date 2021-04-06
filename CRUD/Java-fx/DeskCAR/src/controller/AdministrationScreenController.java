package controller;

import DAO.CarDAO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.awt.RenderingHints;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import model.Attribute;
import model.Car;
import model.CarEngine;
import model.Color;
import model.Fuel;
import model.Model;
import utils.AdministrationScreen;
import utils.CharacterValidation;
import utils.SaleAcceptScreen;
import utils.SalesHistoryScreen;
import utils.MainScreen;
import utils.RemoveCarScreen;

public class AdministrationScreenController implements Initializable {
    
    private CarDAO carDAO = new CarDAO();
    private Car actualCar = new Car();
    private Attribute actualAttribute = new Attribute();
    private Color actualColor = new Color();
    private CarEngine actualEngine = new CarEngine();
    private Model actualModel = new Model();
    private Fuel actualFuel = new Fuel();
    
    @FXML
    private ImageView background;
    @FXML
    private AnchorPane mainPanel;
    @FXML
    private JFXButton addCarButton;
    @FXML
    private JFXButton removeCarButton;
    @FXML
    private JFXButton acceptSaleButton;
    @FXML
    private JFXButton salesHistoryButton;
    @FXML
    private JFXButton exitButton;
    @FXML
    private AnchorPane addCarPanel;
    @FXML
    private JFXComboBox<Label> addCarModel;
    @FXML
    private JFXComboBox<Label> addCarAttributes;
    @FXML
    private JFXComboBox<Label> addCarColor;
    @FXML
    private JFXComboBox<Label> addCarEngine;
    @FXML
    private JFXComboBox<Label> addCarFuel;
    @FXML
    private JFXTextField addCarTire;
    @FXML
    private JFXTextField addCarValue;
    @FXML
    private JFXButton addCarAddButton;
    @FXML
    private JFXButton addCarCancel;
    
    @FXML
    
    private void addCarButtonAction(ActionEvent evt) {
        addCarPanel.setVisible(true);
        addCarValue.setText("");
    }
    
    @FXML
    private void removeCarButtonAction(ActionEvent evt) {
        RemoveCarScreen t = new RemoveCarScreen();
        close();
        try {
            t.start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void acceptSaleButtonAction(ActionEvent evt) {
        SaleAcceptScreen t = new SaleAcceptScreen();
        close();
        try {
            t.start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void salesHistoryButtonAction(ActionEvent evt) {
        SalesHistoryScreen t = new SalesHistoryScreen();
        close();
        try {
            t.start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void exitButtonAction(ActionEvent evt) {
        MainScreen t = new MainScreen();
        close();
        try {
            t.start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void addCarAddButtonAction(ActionEvent evt) {
        populateCar();
        if (validate()) {
            carDAO.insertCar(actualCar);
            addCarPanel.setVisible(false);
            clear();
        } else {
            JOptionPane.showMessageDialog(null, "Não foi possivel adicionar o carro.");
        }
    }
    
    @FXML
    private void addCarCancelAction(ActionEvent evt) {
        addCarPanel.setVisible(false);
        clear();
    }
    
    @FXML
    private void addCarTireAction(KeyEvent evt){
        CharacterValidation.acceptNumbers(evt);
    }
    
    @FXML
    private void addCarValueAction(KeyEvent evt){
        CharacterValidation.acceptMoney(evt, addCarValue.getText());
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        background.setImage(new Image("/images/background.png"));
        populateComboBox();
        addCarPanel.setVisible(false);
    }
    
    private void populateComboBox() {
        addCarModel.getItems().add(0, new Label("Gol"));
        addCarModel.getItems().add(1, new Label("Voyage"));
        addCarModel.getItems().add(2, new Label("Fox"));
        addCarModel.getItems().add(3, new Label("Saveiro"));
        addCarModel.getItems().add(4, new Label("Amarok"));
        addCarModel.getItems().add(5, new Label("Spacefox"));
        addCarModel.getItems().add(6, new Label("Polo Sedan"));
        addCarModel.getItems().add(7, new Label("Virtus"));
        addCarModel.getItems().add(8, new Label("Jetta"));
        addCarModel.getItems().add(9, new Label("Touareg"));
        
        addCarFuel.getItems().add(0, new Label("GASOLINA"));
        addCarFuel.getItems().add(1, new Label("ETANOL"));
        addCarFuel.getItems().add(2, new Label("FLEX"));
        addCarFuel.getItems().add(3, new Label("DIESEL"));
        
        addCarAttributes.getItems().add(0, new Label("Basic"));
        addCarAttributes.getItems().add(1, new Label("Comfort"));
        addCarAttributes.getItems().add(2, new Label("Premium"));
        addCarAttributes.getItems().add(3, new Label("Sport"));
        
        addCarColor.getItems().add(0, new Label("Preto"));
        addCarColor.getItems().add(1, new Label("Branco"));
        addCarColor.getItems().add(2, new Label("Cinza Chumbo"));
        addCarColor.getItems().add(3, new Label("Prata"));
        addCarColor.getItems().add(4, new Label("Amarelo"));
        addCarColor.getItems().add(5, new Label("Bege"));
        addCarColor.getItems().add(6, new Label("Vermelho"));
        addCarColor.getItems().add(7, new Label("Azul Metalico"));
        addCarColor.getItems().add(8, new Label("Vinho"));
        
        addCarEngine.getItems().add(0, new Label("1.0"));
        addCarEngine.getItems().add(1, new Label("1.4"));
        addCarEngine.getItems().add(2, new Label("1.6"));
        addCarEngine.getItems().add(3, new Label("1.8"));
        addCarEngine.getItems().add(4, new Label("2.0"));
        addCarEngine.getItems().add(5, new Label("2.8"));
    }
    
    private void populateCar() {
        actualAttribute = carDAO.getAttribute(addCarAttributes.getSelectionModel().getSelectedIndex() + 1);
        actualColor = carDAO.getColor(addCarColor.getSelectionModel().getSelectedIndex() + 1);
        actualFuel = carDAO.getFuel(addCarFuel.getSelectionModel().getSelectedIndex() + 1);
        actualModel = carDAO.getModel(addCarModel.getSelectionModel().getSelectedIndex() + 1);
        actualEngine = carDAO.getEngine(addCarEngine.getSelectionModel().getSelectedIndex() + 1);
        actualCar.setIdCar(carDAO.getCarsToSale().size() + 1);
        
        actualCar.setDisponibility(1);
        actualCar.setAttribute(actualAttribute);
        actualCar.setFuel(actualFuel);
        actualCar.setColor(actualColor);
        actualCar.setModel(actualModel);
        actualCar.setCarEngine(actualEngine);
        actualCar.setCarValue(CharacterValidation.validateMoney(addCarValue.getText()));
        
        try {
            actualCar.setTire(Integer.valueOf(addCarTire.getText()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private boolean validate() {
        if (actualCar.getAttribute()== null) {
            return false;
        }
        if (actualCar.getFuel()== null) {
            return false;
        }
        if (actualCar.getColor() == null) {
            return false;
        }
        if (actualCar.getModel() == null) {
            return false;
        }
        if (actualCar.getCarEngine()== null) {
            return false;
        }
        if (actualCar.getTire()== 0) {
            return false;
        }
        return true;
    }
    
    private void clear() {
        addCarModel.getSelectionModel().clearSelection();
        addCarFuel.getSelectionModel().clearSelection();
        addCarAttributes.getSelectionModel().clearSelection();
        addCarColor.getSelectionModel().clearSelection();
        addCarEngine.getSelectionModel().clearSelection();
        addCarTire.setText(null);
        addCarValue.setText(null);
    }
    
    private void close() {
        AdministrationScreen.getStage().close();
    }
}
