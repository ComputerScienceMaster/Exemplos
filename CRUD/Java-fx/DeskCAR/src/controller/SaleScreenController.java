package controller;

import DAO.CarDAO;
import DAO.BuyerDAO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.control.Label;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import model.Car;
import model.CarTableModel;
import model.Buyer;
import utils.MainScreen;
import utils.SaleScreen;

public class SaleScreenController implements Initializable {

    private Car actualCar = new Car();
    private CarDAO carDAO = new CarDAO();
    private ArrayList<Car> listOfCars = new ArrayList<>();
    private CarTableModel tableModel = new CarTableModel();
    private Buyer buyer = new Buyer();
    private BuyerDAO buyerDAO = new BuyerDAO();

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
    private TableView<CarTableModel> saleTable;
    @FXML
    private JFXTextField searchInput;
    @FXML
    private AnchorPane saleDatailsPanel;
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
    private JFXButton detailsRequestPurchaseButton;
    @FXML
    private JFXButton detailsCancelButton;

    @FXML
    private void saleTableAction(MouseEvent evt) {
        carDetailing(saleTable.getSelectionModel().getSelectedItem().getId());
        saleDatailsPanel.setVisible(true);
    }

    @FXML
    private void searchInputAction(KeyEvent evt) {
        listOfCars = carDAO.getCarsToSaleSeached(searchInput.getText());
        populateColumn();
    }

    @FXML
    private void detailsRequestPurchaseButtonAction(ActionEvent evt) {
        requestPurchase();
    }

    @FXML
    private void detailsCancelButtonAction(ActionEvent evt) {
        saleDatailsPanel.setVisible(false);
    }

    @FXML
    private void backButtonAction(ActionEvent evt) {
        MainScreen t = new MainScreen();
        close();
        try {
            t.start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        background.setImage(new Image("/images/background.png"));
        listOfCars = carDAO.getCarsToSale();
        populateColumn();
    }

    private void requestPurchase() {
        actualCar.setBuyer(MainScreen.getBuyerLogged());
        actualCar.setDisponibility(2);
        carDAO.editCar(actualCar);
        saleDatailsPanel.setVisible(false);
        listOfCars = carDAO.getCarsToSale();
        populateColumn();
        JOptionPane.showMessageDialog(null, "Solicitação feita com sucesso!", "Solicitação aceita", JOptionPane.INFORMATION_MESSAGE);
    }

    private void carDetailing(int id) {
        actualCar = carDAO.getCar(id);

        detailsModel.setText(actualCar.getModel().getModelName());
        detailsAttributes.setText(actualCar.getAttribute().getDescription());
        detailsColor.setText(actualCar.getColor().getColorName());
        detailsEngine.setText(actualCar.getCarEngine().getEnginePower());
        detailsFuel.setText(actualCar.getFuel().getFuelName());
        detailsTire.setText(String.valueOf(actualCar.getTire()));
        detailsValue.setText(String.valueOf(actualCar.getCarValue()));
    }

    public void populateColumn() {
        saleTable.getColumns().removeAll();
        saleTable.getColumns().clear();
        saleTable.getColumns().addAll(modelo, motor, pacote, cor, combustivel, valor);

        ObservableList<CarTableModel> listForTable = FXCollections.observableArrayList();

        for (int i = 0; i < listOfCars.size(); i++) {
            tableModel = new CarTableModel();
            tableModel.setModel(listOfCars.get(i).getModel().getModelName());
            tableModel.setEngine(listOfCars.get(i).getCarEngine().getEnginePower());
            tableModel.setAttribute(listOfCars.get(i).getAttribute().getAttributeName());
            tableModel.setFuel(listOfCars.get(i).getFuel().getFuelName());
            tableModel.setColor(listOfCars.get(i).getColor().getColorName());
            tableModel.setValue(String.valueOf(listOfCars.get(i).getCarValue()));
            tableModel.setId(listOfCars.get(i).getIdCar());

            listForTable.add(i, tableModel);
        }
        id.setCellValueFactory(new PropertyValueFactory<CarTableModel, Integer>("Codigo"));
        modelo.setCellValueFactory(new PropertyValueFactory<CarTableModel, String>("Model"));
        motor.setCellValueFactory(new PropertyValueFactory<CarTableModel, String>("Engine"));
        pacote.setCellValueFactory(new PropertyValueFactory<CarTableModel, String>("Attribute"));
        combustivel.setCellValueFactory(new PropertyValueFactory<CarTableModel, String>("Fuel"));
        cor.setCellValueFactory(new PropertyValueFactory<CarTableModel, String>("Color"));
        valor.setCellValueFactory(new PropertyValueFactory<CarTableModel, String>("Value"));

        saleTable.setItems(listForTable);
    }

    private void close() {
        SaleScreen.getStage().close();
    }
}
