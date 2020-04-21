/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infs3605;

import static infs3605.Database.conn;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author SOPHIA
 */
public class UpdateStaffController {

    @FXML
    Button back;

    @FXML
    Text staffId;
    @FXML
    TextField staffFName;
    @FXML
    TextField staffLName;
    @FXML
    TextField staffEmail;
    @FXML
    ComboBox staffType;
    @FXML
    TextField staffCapacity;

     PageSwitchHelper pageSwitcher = new PageSwitchHelper();

  //  ObservableList<String> courseCodeList = FXCollections.observableArrayList();
  //  ObservableList<String> staffList = FXCollections.observableArrayList();
    ObservableList<String> staffTypeList = FXCollections.observableArrayList("Full-time Teaching", "Full-time Research", "Full-time Teaching/Research", "Casual Teaching");
    

    public void setData(String uStaffId) {
        
        try {
        Database.openConnection();
            ResultSet rs = conn.createStatement().executeQuery("Select * FROM Staff WHERE staff_id = '" + uStaffId + "'");
            
           
        staffId.setText(uStaffId);
        staffFName.setText(rs.getString(2));
        staffLName.setText(rs.getString(3));        
        staffCapacity.setText(rs.getString(5));   
        staffEmail.setText(rs.getString(6));   
        staffType.setItems(staffTypeList);
        staffType.setValue(rs.getString("staff_type"));
        
             } catch (Exception ex){
         ex.printStackTrace();
        }
    }

    
    @FXML
    public void handleBackButton(ActionEvent event) throws IOException {
        Stage stage = (Stage) back.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void handleSubmitButton(ActionEvent event) throws IOException, SQLException {
        String iStaffId = staffId.getText();
        String iStaffFName = staffFName.getText();
        String iStaffLName = staffLName.getText();
        String iStaffEmail = staffEmail.getText();
        String iStaffType = (String) staffType.getValue();
        int iStaffCapacity = Integer.parseInt(staffCapacity.getText());        

        try {
            Statement st = conn.createStatement();
                String updateData = ("UPDATE Staff SET Fname = '" +iStaffFName+ "', Lname = '" +iStaffLName+ "', staff_type = '" +iStaffType+ "', staff_capacity = " +iStaffCapacity+ ", staff_email = '" + iStaffEmail+ "' WHERE staff_id = '" +iStaffId+ "'");
                st.execute(updateData);
                System.out.println("insert success");   
                //reload page
                
        } catch (Exception e) {
            e.printStackTrace();
        }

//        ConstraintsCheck rulecheck = new ConstraintsCheck();
//        rulecheck.check(iCourseCode, staffID, iYear, iTerm);
//        ArrayList<String> warning = ConstraintsCheck.warning;
//        if (warning.isEmpty() || knowledgewarning == true) {

      
//                knowledgewarning = false;
////                success.setText("Allocation Success!");
//                TimerTask task = new TimerTask() {
//                    @Override
//                    public void run() {
////                        success.setText("");
//                    }
//                };
//                Timer timer = new Timer();
//                timer.schedule(task, 5000);
                

            
            
//        } else {
//            Parent root = FXMLLoader.load(getClass().getResource("Warning.fxml"));
//            Scene scene = new Scene(root);
//            Stage stage = new Stage();
//            stage.setScene(scene);
//            stage.show();
//        }

    }
}
