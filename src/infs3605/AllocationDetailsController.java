/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infs3605;

import static infs3605.Database.conn;
import static infs3605.StaffAllocationController.knowledgewarning;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

/**
 * FXML Controller class
 *
 * @author SOPHIA
 */
public class AllocationDetailsController {

    @FXML
    Button back;

    @FXML
    Text courseCode;
    @FXML
    Text courseName;
    @FXML
    Text staffId;
    @FXML
    Text staffName;
    @FXML
    Text year;
    @FXML
    Text term;
    @FXML
    Text lic;
    @FXML
    TextArea notes;
    @FXML
    Text weighting;      

    // int allocationId = 0;
    PageSwitchHelper pageSwitcher = new PageSwitchHelper();

    ObservableList<String> courseCodeList = FXCollections.observableArrayList();
    ObservableList<String> staffList = FXCollections.observableArrayList();
    ObservableList<Integer> yearList = FXCollections.observableArrayList();
    ObservableList<String> termList = FXCollections.observableArrayList("Term 1", "Term 2", "Term 3", "Summer Term");

    public void setData(int iAllocationId) {
        // allocationId = iAllocationId;

        try {
            Database.openConnection();
            ResultSet rs = conn.createStatement().executeQuery("Select a.course_id, a.staff_id, allocation_term, allocation_year, allocation_description, lic, course_name, Fname, Lname, w.weighting_term "
                    + "FROM Courses c JOIN Allocation a ON a.course_id = c.course_id JOIN Staff s ON a.staff_id = s.staff_id JOIN Weighting w ON w.course_id = a.course_id WHERE allocation_id =" + iAllocationId);

            courseCode.setText(rs.getString(1));
            staffId.setText(rs.getString(2));
            if (rs.getString(3).equals("Summer Term")){
                term.setText("SU");
            } else if (rs.getString(3).equals("Term 1")){
                term.setText("T1");
            } else if (rs.getString(3).equals("Term 2")){
                term.setText("T2");
            } else if (rs.getString(3).equals("Term 3")){
                term.setText("T3");
            } else {
                
            }
            year.setText(rs.getString(4));
            notes.setText("Notes: " + rs.getString(5));
            weighting.setText(rs.getString(10));
            if(rs.getInt(6)==1){
                lic.setText("LIC: Yes");
            } else {
                lic.setText("LIC: No");
            }
            
            
            
            courseName.setText(rs.getString(7));
            staffName.setText(rs.getString(8) + " " + rs.getString(9));
            //conn.close();

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

