package controller;

import DAO.BuyerDAO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import model.Buyer;
import utils.AdministrationScreen;
import utils.CharacterValidation;
import utils.MainScreen;
import utils.SaleScreen;

public class MainScreenController implements Initializable {

    private final String admPassword = "123";
    private BuyerDAO buyerDAO = new BuyerDAO();
    private Buyer actualBuyer = new Buyer();

    @FXML
    private ImageView background;
    @FXML
    private AnchorPane mainPanel;
    @FXML
    private JFXButton administrationBtn;
    @FXML
    private JFXButton exitBtn;
    @FXML
    private JFXButton saleBtn;
    @FXML
    private AnchorPane loginPanel;
    @FXML
    private JFXTextField loginUserCPF;
    @FXML
    private JFXTextField registerEmail;
    @FXML
    private JFXPasswordField loginUserPassword;
    @FXML
    private JFXButton loginEnter;
    @FXML
    private JFXButton loginRegister;
    @FXML
    private AnchorPane registerPanel;
    @FXML
    private JFXTextField registerCPF;
    @FXML
    private JFXPasswordField registerPassword;
    @FXML
    private JFXButton registerAdd;
    @FXML
    private JFXButton registerBack;
    @FXML
    private JFXTextField registerName;
    @FXML
    private JFXTextField registerPhone;
    @FXML
    private AnchorPane administratorLoginPanel;
    @FXML
    private JFXPasswordField administratorPassword;

    @FXML
    private void administrationButtonAction(ActionEvent evt) {
        mainPanel.setVisible(false);
        administratorLoginPanel.setVisible(true);
    }

    @FXML
    private void saleButtonAction(ActionEvent evt) {
        mainPanel.setVisible(false);
        loginPanel.setVisible(true);
    }

    @FXML
    private void exitButtonAction(ActionEvent evt) {
        System.exit(0);
    }

    @FXML
    private void loginEnterButtonAction(ActionEvent evt) {
        if (validateLogin(Long.parseLong(loginUserCPF.getText()), loginUserPassword.getText())) {
            System.out.println("Logou certinho!");
            MainScreen.setBuyerLogged(actualBuyer);
        }
        SaleScreen t = new SaleScreen();
        close();
        try {
            t.start(new Stage());
        } catch (Exception ex) {
        }
    }

    @FXML
    private void loginRegisterAction(ActionEvent evt) {
        loginPanel.setVisible(true);
        registerPanel.setVisible(true);
    }

    @FXML
    private void registerAddAction(ActionEvent evt) {
        if (CharacterValidation.validateCPF(registerCPF.getText())) {
            populateBuyer();
            if (validate()) {
                buyerDAO.insertBuyer(actualBuyer);
                MainScreen.setBuyerLogged(actualBuyer);
                SaleScreen t = new SaleScreen();
                close();
                try {
                    t.start(new Stage());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(null, "É necessario preencher todos os dados!", "alerta", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "CPF inválido!", "alerta", JOptionPane.ERROR_MESSAGE);
        }
    }

    @FXML
    private void registerBackAction(ActionEvent evt) {
        registerPanel.setVisible(false);
        loginPanel.setVisible(true);
    }

    @FXML
    private void loginAdministratorButtonAction(ActionEvent evt) {
        if (validateAdministratorPassword(administratorPassword.getText())) {
            AdministrationScreen t = new AdministrationScreen();
            close();
            try {
                t.start(new Stage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Senha incorreta!", "alerta", JOptionPane.ERROR_MESSAGE);
        }
    }

    @FXML
    private void loginUserCPFAction(KeyEvent evt) {
        CharacterValidation.acceptCpfCell(evt, loginUserCPF.getText());
    }

    @FXML
    private void registerCPFAction(KeyEvent evt) {
        CharacterValidation.acceptCpfCell(evt, registerCPF.getText());
    }

    @FXML
    private void registerPhoneAction(KeyEvent evt) {
        CharacterValidation.acceptCpfCell(evt, registerPhone.getText());
    }

    @FXML
    private void backAdministratorButtonAction(ActionEvent evt) {
        administratorLoginPanel.setVisible(false);
        mainPanel.setVisible(true);
    }

    @FXML
    private void logoAction(MouseEvent evt) {
        loginPanel.setVisible(false);
        registerPanel.setVisible(false);
        administratorLoginPanel.setVisible(false);
        mainPanel.setVisible(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        background.setImage(new Image("/images/background.png"));
    }

    private boolean validateAdministratorPassword(String toConfirmPassword) {
        if (admPassword.equals(toConfirmPassword)) {
            return true;
        } else {
            return false;
        }
    }

    private void populateBuyer() {
        actualBuyer.setBuyerEmail(registerEmail.getText());
        actualBuyer.setBuyerName(registerName.getText());
        actualBuyer.setBuyerPassword(registerPassword.getText());
        try {
            actualBuyer.setCpf(Long.parseLong(registerCPF.getText()));
            actualBuyer.setBuyerPhone(Long.parseLong(registerPhone.getText()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean validate() {
        if (actualBuyer.getCpf().SIZE < 10) {
            return false;
        }
        if (actualBuyer.getBuyerEmail() == null) {
            return false;
        }
        if (actualBuyer.getBuyerName() == null) {
            return false;
        }
        if (actualBuyer.getBuyerPassword() == null) {
            return false;
        }
        if (actualBuyer.getBuyerPhone() == 0) {
            return false;
        }
        return true;
    }

    private boolean validateLogin(Long cpf, String senha) {
        actualBuyer = new Buyer();
        actualBuyer = buyerDAO.getUser(cpf);
        if (actualBuyer != null) {
            if (actualBuyer.getBuyerPassword().equals(senha)) {
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Senha incorreta");
                return false;
            }
        } else {
            JOptionPane.showMessageDialog(null, "Usuario não encontrado.");
            return false;
        }
    }

    private void close() {
        MainScreen.getStage().close();
    }
}
