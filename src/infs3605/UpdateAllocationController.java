/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infs3605;

import static infs3605.Database.conn;
import java.awt.Checkbox;
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
import javafx.scene.control.TextArea;
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
    TextArea allocationNotes;
    @FXML
    Checkbox lic;
    @FXML
    TextField courseCode;
    @FXML
    Text courseName;
    @FXML
    Text totalWeighting;
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

    public void setData(int uAllocationId) {
        try {
            Database.openConnection();
            ResultSet rs = conn.createStatement().executeQuery("SELECT DISTINCT Allocation.allocation_id, Allocation.allocation_term, Allocation.allocation_year, Allocation.course_id, Allocation.staff_id, Courses.course_name, Staff.Fname, Staff.Lname, Allocation.allocation_weight, Allocation.lic, Allocation.allocation_description, Weighting.weighting_term FROM Staff JOIN Allocation ON Staff.staff_id = Allocation.staff_id JOIN Courses ON Courses.course_id = Allocation.course_id Join Weighting ON Weighting.course_id = Allocation.course_id WHERE allocation_id = " + uAllocationId);

            allocationId = uAllocationId;
            courseCode.setText(rs.getString("course_id") + " " + rs.getString("course_name"));
            courseName.setText(rs.getString("course_name"));
            staffId.setText(rs.getString("staff_id"));
            staffName.setText(rs.getString(7) + " " + rs.getString(8));
            year.setValue(rs.getString("allocation_year"));
            term.setValue(rs.getString("allocation_term"));
            weighting.setText(rs.getString("allocation_weight"));
            allocationNotes.setText(rs.getString("allocation_description"));
            totalWeighting.setText("/ " + rs.getString("weighting_term"));
            if (rs.getInt("lic") == 1) {
                //keep textbox ticked

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        setLists();

    }

    public void setLists() {

        try {
            Database.openConnection();
            ResultSet rs1 = conn.createStatement().executeQuery("Select * FROM Courses");
            while (rs1.next()) {
                courseCodeList.add(rs1.getString(1) + " " + rs1.getString(2));
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
            e.printStackTrace();
        }
    }

    @FXML
    public void handleSubmitButton(ActionEvent event) throws IOException, SQLException {
        String iCourseCode = courseCode.getText();
        String iTerm = (String) term.getValue();
        String iStaffName = staffName.getText();
        String iAllocationNotes = allocationNotes.getText();
        iStaffName = iStaffName.substring(0, iStaffName.indexOf(" "));
//ISSUES
        String iYearString = (String) year.getValue();
        int iYear = Integer.parseInt(iYearString);
        double iWeighting = Double.parseDouble(weighting.getText());
        String staffID = "";

        try {
            Database.openConnection();
            ResultSet rs = conn.createStatement().executeQuery("Select staff_id FROM Staff WHERE Fname = '" + iStaffName + "'");
            staffID = rs.getString(1);

            Statement st = conn.createStatement();
            String updateData = ("UPDATE ALLOCATION SET allocation_year = " + iYear + ", allocation_term = '" + iTerm + "', course_id = '"
                    + iCourseCode + "', staff_id = '" + staffID + "', allocation_description = '" + iAllocationNotes + "', allocation_weight = "
                    + iWeighting + " WHERE allocation_id = " + allocationId);
            st.execute(updateData);
            System.out.println("insert success");

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
