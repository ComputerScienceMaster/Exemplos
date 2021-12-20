package controller;

import DAO.CarDAO;
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
import javax.swing.JOptionPane;
import model.Car;
import model.VendaTableModel;
import utils.AdministrationScreen;
import utils.CharacterValidation;
import utils.SaleAcceptScreen;

public class SaleAcceptScreenController implements Initializable {

    private ArrayList<Car> listOfCars = new ArrayList<>();
    private CarDAO carDAO = new CarDAO();
    private Car actualCar = new Car();
    private VendaTableModel tableModel = new VendaTableModel();

    @FXML
    private TableColumn id = new TableColumn("Codigo");
    @FXML
    private TableColumn modelo = new TableColumn("Modelo");
    @FXML
    private TableColumn valor = new TableColumn("Valor");
    @FXML
    private TableColumn comprador = new TableColumn("Comprador");
    @FXML
    private ImageView background;
    @FXML
    private Label logoLabel;
    @FXML
    private TableView<VendaTableModel> toApproveTable = new TableView();
    @FXML
    private AnchorPane toApproveDatailsPanel;
    @FXML
    private Text detailsModel;
    @FXML
    private Text detailsAttributes;
    @FXML
    private Text detailsValue;
    @FXML
    private Text detailsBuyerName;
    @FXML
    private Text detailsBuyerCPF;
    @FXML
    private Text detailsBuyerEmail;
    @FXML
    private Text detailsBuyerPhone;
    @FXML
    private JFXButton detailsAcceptBuy;
    @FXML
    private JFXButton detailsCancel;
    @FXML
    private JFXButton detailsReproveBuy;

    @FXML
    private void toApproveTableAction(MouseEvent evt) {
        detail(toApproveTable.getSelectionModel().getSelectedItem().getId());
        toApproveDatailsPanel.setVisible(true);
    }

    @FXML
    private void detailsAcceptBuyAction(ActionEvent evt) {
        acceptRequest();
        populateColumn();
        toApproveDatailsPanel.setVisible(false);
    }

    @FXML
    private void detailsCancelAction(ActionEvent evt) {
        toApproveDatailsPanel.setVisible(false);
    }

    @FXML
    private void detailsReproveBuyAction(ActionEvent evt) {
        cancelRequest();
        populateColumn();
        toApproveDatailsPanel.setVisible(false);
    }
    
    @FXML
    private void backButtonAction(ActionEvent evt){
        AdministrationScreen t = new AdministrationScreen();
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
        populateColumn();
    }

    private void detail(int id) {
        actualCar = carDAO.getCar(id);

        detailsModel.setText(actualCar.getModel().getModelName());
        detailsAttributes.setText(actualCar.getAttribute().getDescription());
        detailsValue.setText(String.valueOf(actualCar.getCarValue()));
        try {
            detailsBuyerCPF.setText(CharacterValidation.showCPF(String.valueOf(actualCar.getBuyer().getCpf())));
            detailsBuyerEmail.setText(actualCar.getBuyer().getBuyerEmail());
            detailsBuyerName.setText(actualCar.getBuyer().getBuyerName());
            detailsBuyerPhone.setText(String.valueOf(actualCar.getBuyer().getBuyerPhone()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void populateColumn() {
        toApproveTable.getColumns().removeAll();
        toApproveTable.getColumns().clear();
        toApproveTable.getColumns().addAll(modelo, valor, comprador);

        listOfCars = carDAO.getCarsToAccept();
        ObservableList<VendaTableModel> listForTable = FXCollections.observableArrayList();

        for (int i = 0; i < listOfCars.size(); i++) {
            tableModel = new VendaTableModel();
            tableModel.setModel(listOfCars.get(i).getModel().getModelName());
            tableModel.setValue(String.valueOf(listOfCars.get(i).getCarValue()));
            tableModel.setBuyer(listOfCars.get(i).getBuyer().getBuyerName());
            tableModel.setId(listOfCars.get(i).getIdCar());

            listForTable.add(i, tableModel);
        }
        id.setCellValueFactory(new PropertyValueFactory<VendaTableModel, Integer>("Codigo"));
        modelo.setCellValueFactory(new PropertyValueFactory<VendaTableModel, String>("Model"));
        valor.setCellValueFactory(new PropertyValueFactory<VendaTableModel, String>("Value"));
        comprador.setCellValueFactory(new PropertyValueFactory<VendaTableModel, String>("Buyer"));

        toApproveTable.setItems(listForTable);
    }
    
    private void acceptRequest(){
        actualCar.setDisponibility(3);
        carDAO.editCar(actualCar);
        JOptionPane.showMessageDialog(null, "Entre em contato com o comprador!", "Venda aprovada", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void cancelRequest(){
        actualCar.setDisponibility(1);
        actualCar.setBuyer(null);
        carDAO.editCar(actualCar);
        JOptionPane.showMessageDialog(null, "Entre em contato com o comprador!", "Venda reprovada", JOptionPane.ERROR_MESSAGE);
    }
    
    private void close(){
        SaleAcceptScreen.getStage().close();
    }
}
