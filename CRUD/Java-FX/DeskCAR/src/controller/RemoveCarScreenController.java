package controller;

import DAO.CarDAO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Car;
import model.CarTableModel;
import utils.AdministrationScreen;
import utils.RemoveCarScreen;

public class RemoveCarScreenController implements Initializable {

    private Car actualCar = new Car();
    private CarDAO carDAO = new CarDAO();
    private ArrayList<Car> listOfCars = new ArrayList<>();
    private CarTableModel tableModel = new CarTableModel();
    
    @FXML
    private TableColumn id = new TableColumn("Codigo");
    @FXML
    private TableColumn modelo = new TableColumn("Modelo");
    @FXML
    private TableColumn motor = new TableColumn("Motor");
    @FXML
    private TableColumn pacote = new TableColumn("Pacote");
    @FXML
    private TableColumn cor = new TableColumn("Cor");
    @FXML
    private TableColumn combustivel = new TableColumn("Combustível");
    @FXML
    private TableColumn valor = new TableColumn("Valor");
    @FXML
    private ImageView background;
    @FXML
    private Label logoLabel;
    @FXML
    private TableView<CarTableModel> removeTable;
    @FXML
    private JFXTextField searchInput;
    @FXML
    private AnchorPane removeDatailsPanel;
    @FXML
    private Text detailsModel;
    @FXML
    private Text detailsAttributes;
    @FXML
    private Text detailsColor;
    @FXML
    private Text detailsFuel;
    @FXML
    private Text detailsEngine;
    @FXML
    private Text detailsTire;
    @FXML
    private Text detailsValue;
    @FXML
    private JFXButton detailsRemoveButton;
    @FXML
    private JFXButton detailsCancelButton;

    @FXML
    private void backButtonAction(ActionEvent evt) {
        AdministrationScreen t = new AdministrationScreen();
        close();
        try {
            t.start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void removeTableAction(MouseEvent evt) {
        carDetailing(removeTable.getSelectionModel().getSelectedItem().getId());
        removeDatailsPanel.setVisible(true);
    }

    @FXML
    private void searchInputAction(KeyEvent evt) {
        listOfCars = carDAO.getCarsToSaleSeached(searchInput.getText());
        populateColumn();
    }

    @FXML
    private void detailsRemoveButtonAction(ActionEvent evt) {
        carDAO.removeCar(removeTable.getSelectionModel().getSelectedItem().getId());
        listOfCars = carDAO.getCarsToSale();
        populateColumn();
        removeDatailsPanel.setVisible(false);
    }

    @FXML
    private void detailsCancelButtonAction(ActionEvent evt) {
        removeDatailsPanel.setVisible(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        background.setImage(new Image("/images/background.png"));
        listOfCars = carDAO.getCarsToSale();
        populateColumn();
    }
    
    private void carDetailing(int id){
        actualCar = carDAO.getCar(id);
        
        detailsAttributes.setText(actualCar.getAttribute().getDescription());
        detailsColor.setText(actualCar.getColor().getColorName());
        detailsEngine.setText(actualCar.getCarEngine().getEnginePower());
        detailsFuel.setText(actualCar.getFuel().getFuelName());
        detailsModel.setText(actualCar.getModel().getModelName());
        detailsTire.setText(String.valueOf(actualCar.getTire()));
        detailsValue.setText(String.valueOf(actualCar.getCarValue()));
    }

    public void populateColumn() {
        removeTable.getColumns().removeAll();
        removeTable.getColumns().clear();
        removeTable.getColumns().addAll(modelo, motor, pacote, cor, combustivel, valor);
        ObservableList<CarTableModel> listaParaTabela = FXCollections.observableArrayList();

        for (int i = 0; i < listOfCars.size(); i++) {
            tableModel = new CarTableModel();
            tableModel.setModel(listOfCars.get(i).getModel().getModelName());
            tableModel.setEngine(listOfCars.get(i).getCarEngine().getEnginePower());
            tableModel.setAttribute(listOfCars.get(i).getAttribute().getAttributeName());
            tableModel.setFuel(listOfCars.get(i).getFuel().getFuelName());
            tableModel.setColor(listOfCars.get(i).getColor().getColorName());
            tableModel.setValue(String.valueOf(listOfCars.get(i).getCarValue()));
            tableModel.setId(listOfCars.get(i).getIdCar());

            listaParaTabela.add(i, tableModel);
        }
        id.setCellValueFactory(new PropertyValueFactory<CarTableModel, Integer>("Codigo"));
        modelo.setCellValueFactory(new PropertyValueFactory<CarTableModel, String>("Model"));
        motor.setCellValueFactory(new PropertyValueFactory<CarTableModel, String>("Engine"));
        pacote.setCellValueFactory(new PropertyValueFactory<CarTableModel, String>("Attribute"));
        combustivel.setCellValueFactory(new PropertyValueFactory<CarTableModel, String>("Fuel"));
        cor.setCellValueFactory(new PropertyValueFactory<CarTableModel, String>("Color"));
        valor.setCellValueFactory(new PropertyValueFactory<CarTableModel, String>("Value"));

        removeTable.setItems(listaParaTabela);
    }

    private void close() {
        RemoveCarScreen.getStage().close();
    }
}
