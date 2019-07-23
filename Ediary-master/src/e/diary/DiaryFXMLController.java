package e.diary;
import e.dao.UserDAO;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;
import java.awt.HeadlessException;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import e.bean.User;
import e.dao.DiaryDAO;
import java.util.LinkedList;

/**
 * FXML Controller class
 *
 * @author perseus85
 */

public class DiaryFXMLController implements Initializable {
    //User Defined Variable 
    public static User curUser = new User();
    public static String unm = new String();
    
    @FXML
    private Button btnLogin;
    @FXML
    private Button btnExit;
    @FXML
    private ComboBox<String> cmbUsrNm;
    @FXML
    private TextField txtUserName;
    @FXML
    private TextField txtPasswd;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtUserName.setDisable(true);
        txtPasswd.setDisable(true);
        cmbUsrNm.getItems().addAll(UserDAO.getUnames());
        btnLogin.setDisable(true);
    }

    @FXML
    @SuppressWarnings({"CallToPrintStackTrace", "StringEquality"})
    private void handleButtonEvent(ActionEvent event) throws IOException {
        if (event.getSource() == btnLogin) {
            try {
                curUser = UserDAO.validate(txtUserName.getText(), txtPasswd.getText());
                JOptionPane.showMessageDialog(null,"Checking frm dtbs "+curUser.getUid()+"::"+curUser.getUname()+"::"+curUser.getPass());     
                if(curUser.getUid()=="0"){
                    if(cmbUsrNm.getValue().equalsIgnoreCase("New/Existing User")){
                        LinkedList<String> usrnms = UserDAO.getUnames();
                        JOptionPane.showMessageDialog(null, usrnms);
                        if(usrnms.contains(txtUserName.getText())){
                            JOptionPane.showMessageDialog(null, "INVALID PASSWORD!!!");
                        } else {
                            UserDAO.registerUser(txtUserName.getText(), txtPasswd.getText());
                            JOptionPane.showMessageDialog(null, " Registered User " + txtUserName.getText() + " Successfully");
                            Stage stage = (Stage) btnExit.getScene().getWindow();
                            Parent root = FXMLLoader.load(getClass().getResource("MainFXML.fxml"));
                            Scene scene = new Scene(root);
                            stage.setScene(scene);
                        }
                    }
                } else {
                    // For login
                    JOptionPane.showMessageDialog(null," Getting User "+curUser.getUname()+" successfully logged in ");
                    Stage stage = (Stage) btnExit.getScene().getWindow();
                    Parent root = FXMLLoader.load(getClass().getResource("MainFXML.fxml"));
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                }
            } catch (HeadlessException e) {
                e.printStackTrace();
            }

        }
        if (event.getSource() == btnExit) {
            System.exit(0);
        }
    }

    @FXML
    private void handleCmb(ActionEvent event) {
        if (cmbUsrNm.getValue().equalsIgnoreCase("New/Existing User")) {
            txtUserName.clear();
            txtUserName.setDisable(false);
            txtPasswd.setDisable(false);
            btnLogin.setDisable(false);
            btnLogin.setText("REGISTER/LOGIN");
        } else {
            txtUserName.setText(cmbUsrNm.getValue());
            txtUserName.setDisable(true);
            txtPasswd.setDisable(false);
            btnLogin.setDisable(false);
            btnLogin.setText("LOGIN");
        }
    }
}