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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author SOPHIA
 */
public class StaffDetailsController {

    @FXML
    Button back;

    @FXML
    Text staffId;
    @FXML
    Text staffName;
    @FXML
    Text email;
    @FXML
    Text magicTxt;
    @FXML
    Text staffType;
    @FXML
    Text staffCapacity;

    // int allocationId = 0;
    PageSwitchHelper pageSwitcher = new PageSwitchHelper();

//    ObservableList<String> courseCodeList = FXCollections.observableArrayList();
//    ObservableList<String> staffList = FXCollections.observableArrayList();
//    ObservableList<Integer> yearList = FXCollections.observableArrayList();
//    ObservableList<String> termList = FXCollections.observableArrayList("Term 1", "Term 2", "Term 3", "Summer Term");
    public void setData(String iStaffId) {
        // allocationId = iAllocationId;

        try {
            Database.openConnection();
            ResultSet rs = conn.createStatement().executeQuery("Select * FROM Staff WHERE staff_id = '" + iStaffId + "'");
            staffName.setText(rs.getString(2) + " " + rs.getString(3)
                    + " (" + rs.getString(1) + ")");
            staffType.setText(rs.getString(4));
            staffCapacity.setText(rs.getString(5));
            email.setText(rs.getString(6));

            rs = conn.createStatement().executeQuery(("SELECT SUM(allocation_weight) "
                    + "FROM Staff s LEFT OUTER JOIN Allocation a "
                    + "ON s.staff_id = a.staff_id "
                    + "WHERE allocation_year = strftime('%Y', date('now')) "
                    + "AND s.staff_id = '" + iStaffId + "'"));

            magicTxt.setText(rs.getString(1));
            System.out.println(magicTxt.getText());
            if (magicTxt.getText().equals("")) {
                staffCapacity.setText("0 / " + staffCapacity.getText());
            } else {
                staffCapacity.setText(magicTxt.getText() + " / " + staffCapacity.getText());
            }

//            if(rs.getInt(6)==1){
//                lic.setText("LIC: Yes");
//            } else {
//                lic.setText("LIC: No");
//            }
//            
//            
//            
//            courseName.setText(rs.getString(7));
//            staffName.setText(rs.getString(8) + " " + rs.getString(9));
//            //conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

//        courseCode.setText(iCourseId);
//        courseName.setText(iCourseName);
//        staffId.setText(iStaffId);
//        staffName.setText(iStaffName);
//        year.setValue(iYear);
//        term.setValue(iTerm);
    @FXML
    public void handleBackButton(ActionEvent event) throws IOException {
        Stage stage = (Stage) back.getScene().getWindow();
        stage.close();
    }
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

