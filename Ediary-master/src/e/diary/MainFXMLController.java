
package e.diary;

import ECUtils.GUIValidator;
import ECUtils.MyUtils;
import e.bean.Diary;
import e.dao.DiaryDAO;
import java.awt.HeadlessException;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.LinkedList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import e.diary.DiaryFXMLController;

/**
 * FXML Controller class
 *
 * @author perseus85
 */
public class MainFXMLController implements Initializable {
    
    GUIValidator val = new GUIValidator();
    static String srch = "";
    boolean update = false;
    Diary updt = new Diary();
    @FXML
    private DatePicker dtDate;
    @FXML
    private TextField txtDescription;
    @FXML
    private Button btnDelete;
    @FXML
    private TableView<?> tableView;
    @FXML
    private Button btnBack;
    @FXML
    private Label lblEntry;
    @FXML
    private Button btnAdd;
    @FXML
    private TextField txtSrch;
    @FXML
    private Label lblOption;
    @FXML
    private ComboBox<String> cmbColumn;
    @FXML
    private Button btnUpdate;
    @FXML
    private Label lblName;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     * 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        val.addRequiredValidator(txtDescription);
        btnAdd.setText("CREATE");
        enableOption(false);
        LinkedList<String> col = new LinkedList<>();
//        col.addFirst("ALL");
//        col.add("DATE");
//        col.add("DESCRIPTION");
        col.addFirst("DESCRIPTION");
        cmbColumn.getItems().addAll(col);
        MyUtils.selectComboBoxValue(cmbColumn, "DESCRIPTION");
        fillTable();
        lblName.setText("Welcome "+DiaryFXMLController.curUser.getUname());
//        JOptionPane.showMessageDialog(null, ""+currUsr.getUid()+"=="+currUsr.getUname()+"=="+currUsr.getPass());
    }    
    @FXML
    @SuppressWarnings("CallToPrintStackTrace")
    private void handleButtonEvent(ActionEvent event) {
        if(event.getSource()==btnBack){
            try {
                Stage stage = (Stage) btnBack.getScene().getWindow();
                    Parent root = FXMLLoader.load(getClass().getResource("DiaryFXML.fxml"));
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(event.getSource()==btnAdd){
            try {
                 if(val.validateAll()){
                     Diary d = new Diary();
                     d.setUid(DiaryFXMLController.curUser.getUid());
                     d.setEdate(Date.valueOf(dtDate.getValue()));
                     d.setDescrip(txtDescription.getText());
                     if(update == false)
                     DiaryDAO.insertEntry(d);
                     else
                     DiaryDAO.updateEntry(d, updt);
                    Stage stage = (Stage) btnBack.getScene().getWindow();
                    Parent root = FXMLLoader.load(getClass().getResource("MainFXML.fxml"));
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                 }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(event.getSource()==btnDelete){
            try {
                Diary d = new Diary();
                d.setUid(MyUtils.getSelColValue("uid", tableView));
                d.setDescrip(MyUtils.getSelColValue("descrip", tableView));
                DiaryDAO.removeEntry(d);
                fillTable();
            } catch (HeadlessException e) {
                e.printStackTrace();
            }
        
        }
        if(event.getSource()==btnUpdate){
            try { update = true;
                updt.setUid(MyUtils.getSelColValue("uid", tableView));
                updt.setDescrip(MyUtils.getSelColValue("descrip", tableView));
                updt.setEdate(Date.valueOf(DiaryDAO.getDate(updt.getUid(),updt.getDescrip())));//Local to Date
                txtDescription.setText(updt.getDescrip());
                dtDate.setValue(updt.getEdate().toLocalDate());
                btnAdd.setText("DONE");
                updateMode(true);
            } catch (HeadlessException e) {
                e.printStackTrace();
            }
        
        }
    }

    public void enableOption(boolean b){
        btnDelete.setDisable(!b);
        btnUpdate.setDisable(!b);
    }
    public void updateMode(boolean b){
        btnDelete.setDisable(b); 
        tableView.setDisable(b); 
        btnBack.setDisable(b); 
        lblEntry.setText("Update Entry");
        txtSrch.setDisable(b); 
        cmbColumn.setDisable(b); 
        btnUpdate.setDisable(b);
    }
    
    private void fillTable() {
        LinkedList<Diary> res = DiaryDAO.search( cmbColumn.getValue(), DiaryFXMLController.curUser.getUid());
        MyUtils.populateTable(tableView, res, Diary.class);
        enableOption(false);
    }

    @FXML
    private void DATE(ActionEvent event) {
    }

    @FXML
    private void mouseClick(MouseEvent event) {
        btnDelete.setDisable(false);
        btnUpdate.setDisable(false);
    }

    @FXML
    private void keyRelelease(KeyEvent event) {
        srch = txtSrch.getText();
        fillTable();
    }

    @FXML
    private void handleSrchColumn(ActionEvent event) {
    }
}
