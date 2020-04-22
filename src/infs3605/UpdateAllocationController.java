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
import org.controlsfx.control.textfield.TextFields;

/**
 * FXML Controller class
 *
 * @author SOPHIA
 */
public class UpdateAllocationController {

    @FXML
    Button back;

    @FXML
    TextField courseCode;
    @FXML
    Text courseName;
    @FXML
    Text staffId;
    @FXML
    TextField staffName;
    @FXML
    TextField weighting;
    @FXML
    ComboBox year;
    @FXML
    ComboBox term;

    int allocationId = 0;
    PageSwitchHelper pageSwitcher = new PageSwitchHelper();

    ObservableList<String> courseCodeList = FXCollections.observableArrayList();
    ObservableList<String> staffList = FXCollections.observableArrayList();
    ObservableList<Integer> yearList = FXCollections.observableArrayList();
    ObservableList<String> termList = FXCollections.observableArrayList("Term 1", "Term 2", "Term 3", "Summer Term");
    

    public void setData(int iAllocationId, String iCourseId, String iCourseName, String iStaffId, String iStaffName, int iYear, String iTerm, String iWeighting) {
        allocationId = iAllocationId;
        courseCode.setText(iCourseId);
        courseName.setText(iCourseName);
        staffId.setText(iStaffId);
        staffName.setText(iStaffName);
        year.setValue(iYear);
        term.setValue(iTerm);
        weighting.setText(iWeighting);

        setLists();

    }

    public void setLists() {

        try {
            Database.openConnection();
            ResultSet rs1 = conn.createStatement().executeQuery("Select * FROM Courses");
            while (rs1.next()) {
                courseCodeList.add(rs1.getString(1));
            }

            ResultSet rs2 = conn.createStatement().executeQuery("Select * FROM Staff");
            while (rs2.next()) {
                staffList.add(rs2.getString(2) + " " + rs2.getString(3));
            }
            ResultSet rs3 = conn.createStatement().executeQuery("SELECT CURRENT_DATE");
            yearList.addAll(rs3.getInt(1), rs3.getInt(1) + 1, rs3.getInt(1) + 2, rs3.getInt(1) + 3, rs3.getInt(1) + 4);
            year.setItems(yearList);

            term.setItems(termList);
            TextFields.bindAutoCompletion(courseCode, courseCodeList);
            TextFields.bindAutoCompletion(staffName, staffList);

        } catch (Exception e) {

        }
    }

    @FXML
    public void handleBackButton(ActionEvent event) throws IOException {
        Stage stage = (Stage) back.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void handleSubmitButton(ActionEvent event) throws IOException, SQLException {
        String iCourseCode = courseCode.getText();
        String iTerm = (String) term.getValue();
        String iStaffName = staffName.getText();
        iStaffName = iStaffName.substring(0, iStaffName.indexOf(" "));
        int iYear = (int) year.getValue();
        String staffID = "";

        try {
            Database.openConnection();
            ResultSet rs = conn.createStatement().executeQuery("Select staff_id FROM Staff WHERE Fname = '" + iStaffName + "'");
            staffID = rs.getString(1);
            
            
                Statement st = conn.createStatement();
                String updateData = ("UPDATE ALLOCATION SET allocation_year = " + iYear + ", allocation_term = '" + iTerm + "', course_id = '"
                        + iCourseCode + "', staff_id = '" + staffID + "' WHERE allocation_id = " + allocationId);
                st.execute(updateData);
                System.out.println("insert success");

        } catch (Exception e) {
            System.out.println("SQL error");
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
