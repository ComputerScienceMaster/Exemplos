package controller;

import DAO.CarDAO;
import DAO.BuyerDAO;
import com.jfoenix.controls.JFXButton;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Buyer;
import model.Car;
import model.CarTableModel;
import utils.AdministrationScreen;
import utils.CharacterValidation;
import utils.SalesHistoryScreen;

public class SalesHistoryScreenController implements Initializable {

    private Car actualCar = new Car();
    private CarDAO carDAO = new CarDAO();
    private ArrayList<Car> listOfCars = new ArrayList<>();
    private CarTableModel tableModel = new CarTableModel();
    private Buyer actualBuyer = new Buyer();
    private BuyerDAO buyerDAO = new BuyerDAO();

    @FXML
    private TableColumn modelo = new TableColumn("Modelo");
    @FXML
    private TableColumn comprador = new TableColumn("Comprador");
    @FXML
    private TableColumn valor = new TableColumn("Valor");
    @FXML
    private ImageView background;
    @FXML
    private Label logoLabel;
    @FXML
    private TableView<CarTableModel> saleHistoryTable;
    @FXML
    private AnchorPane historicDatailsPanel;
    @FXML
    private Text detailsModel;
    @FXML
    private Text detailsAttributes;
    @FXML
    private Text detailsValue;
    @FXML
    private JFXButton detailsCancel;
    @FXML
    private Text detailsBuyerName;
    @FXML
    private Text detailsBuyerCPF;
    @FXML
    private Text detailsBuyerEmail;
    @FXML
    private Text detailsBuyerPhone;

    @FXML
    private void saleHistoryTableAction(MouseEvent evt) {
        detailingPurchase(saleHistoryTable.getSelectionModel().getSelectedItem().getId());
        historicDatailsPanel.setVisible(true);

    }

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
    private void detailsBackAction(ActionEvent evt) {
        historicDatailsPanel.setVisible(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        background.setImage(new Image("/images/background.png"));
        listOfCars = carDAO.getCarsSold();
        populateColumn();
    }

    private void detailingPurchase(int id) {
        actualCar = carDAO.getCar(id);

        detailsModel.setText(actualCar.getModel().getModelName());
        detailsAttributes.setText(actualCar.getAttribute().getDescription());
        detailsBuyerCPF.setText(CharacterValidation.showCPF(String.valueOf(actualCar.getBuyer().getCpf())));
        detailsBuyerEmail.setText(actualCar.getBuyer().getBuyerEmail());
        detailsBuyerName.setText(actualCar.getBuyer().getBuyerName());
        detailsBuyerPhone.setText(String.valueOf(actualCar.getBuyer().getBuyerPhone()));
        detailsValue.setText(String.valueOf(actualCar.getCarValue()));
    }

    public void populateColumn() {
        saleHistoryTable.getColumns().removeAll();
        saleHistoryTable.getColumns().clear();
        saleHistoryTable.getColumns().addAll(modelo, comprador, valor);

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
            tableModel.setBuyer(listOfCars.get(i).getBuyer().getBuyerName());

            listaParaTabela.add(i, tableModel);
        }
        modelo.setCellValueFactory(new PropertyValueFactory<CarTableModel, String>("Model"));
        valor.setCellValueFactory(new PropertyValueFactory<CarTableModel, String>("Value"));
        comprador.setCellValueFactory(new PropertyValueFactory<CarTableModel, String>("Buyer"));

        saleHistoryTable.setItems(listaParaTabela);
    }

    private void close() {
        SalesHistoryScreen.getStage().close();
    }
}
