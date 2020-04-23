/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infs3605;

import static infs3605.Database.conn;
import java.util.*;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author jenneyle
 */
public class UpdateWeightingsController implements Initializable {

    /**
     * Initializes the controller class.
     */
    PageSwitchHelper pageSwitcher = new PageSwitchHelper();

    @FXML
    Button updateWeightingSubmit;

    @FXML
    Button updateWeightingBack;

    @FXML
    ComboBox<String> updateCourseComboBox;

    @FXML
    ComboBox<String> updateTermComboBox;

    @FXML
    ComboBox<Integer> updateYearComboBox;

    @FXML
    private TextField updateWeightingStudents;

    @FXML
    private TextField updateWeightingFaceHrs;

    @FXML
    private TextField updateWeightingPrep;

    @FXML
    private Label updateMsg;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        ObservableList<String> updateCourseList = FXCollections.observableArrayList();
        ObservableList<Integer> updateYearList = FXCollections.observableArrayList();
        ObservableList<String> updateTermList = FXCollections.observableArrayList("Term 1", "Term 2", "Term 3", "Summer Term");
        updateTermComboBox.setItems(updateTermList);

        try {
            Database.openConnection();
            ResultSet rs1 = conn.createStatement().executeQuery("Select * FROM Courses");
            while (rs1.next()) {
                updateCourseList.add(rs1.getString(1));
                updateCourseComboBox.setItems(updateCourseList);
            }
            ResultSet rs2 = conn.createStatement().executeQuery("SELECT CURRENT_DATE");
            updateYearList.addAll(rs2.getInt(1), rs2.getInt(1) + 1, rs2.getInt(1) + 2, rs2.getInt(1) + 3, rs2.getInt(1) + 4);
            updateYearComboBox.setItems(updateYearList);
            updateMsg.setVisible(false);
        } catch (Exception e) {

        }
    }

    //Submit updates to the Weighting table
    public void handleSubmitBtn(ActionEvent event) throws IOException, SQLException {

        String courseCode = updateCourseComboBox.getValue();
        String term = updateTermComboBox.getValue();
        int year = updateYearComboBox.getValue();

        String updateStudents = updateWeightingStudents.getText();
        double doubleUpdateStudents = Double.valueOf(updateStudents);
        double roundStudent = Math.round(doubleUpdateStudents*100)/100;

        String updateFaceHrs = updateWeightingFaceHrs.getText();
        double doubleUpdateFaceHrs = Double.valueOf(updateFaceHrs);
        double roundFaceHrs = Math.round(doubleUpdateFaceHrs*100)/100;

        String updatePrepDevHrs = updateWeightingPrep.getText();
        double doubleUpdatePrepDevHrs = Double.valueOf(updatePrepDevHrs);
        double roundPrepDevHrs = Math.round(doubleUpdatePrepDevHrs*100)/100;
        
//        double a = 123.13698;
//    double roundOff = Math.round(a*100)/100;

        Statement st = conn.createStatement();
        try {
            String updateDatabase = ("UPDATE Weighting SET course_id = '" + courseCode
                    + "' , Year = '" + year
                    + "' , Term = '" + term
                    + "' ,  students_enrolled = '" + roundStudent
                    + "' ,  face_time = '" + roundFaceHrs
                    + "' ,  prep_dev = '" + roundPrepDevHrs
                    + "' WHERE course_id = '" + courseCode + "' AND Year = '" + year + "' AND Term = '" + term + "';");
            st.execute(updateDatabase);
            System.out.println("Submitted");
            updateMsg.setText("Successfully submitted");
            updateMsg.setVisible(true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //Back button to Weighting page
    public void handleBackBtn(ActionEvent event) throws IOException {
        pageSwitcher.switcher(event, "Weighting.fxml");
    }
    
       @FXML
    public void handleAllocateBtn(MouseEvent event) throws IOException {
        pageSwitcher.switcher(event, "StaffAllocation.fxml");
    }

    //button to view course weightings
    @FXML
    public void handleWeightingBtn(MouseEvent event) throws IOException {
        pageSwitcher.switcher(event, "Weighting.fxml");
    }

    //button to view staff details
    @FXML
    public void handleStaffBtn(MouseEvent event) throws IOException {
        pageSwitcher.switcher(event, "StaffTable.fxml");
    }

    @FXML
    public void handleCourseBtn(MouseEvent event) throws IOException {
        pageSwitcher.switcher(event, "CourseTable.fxml");
    }

    @FXML
    public void handleCurrentAlloBtn(MouseEvent event) throws IOException {
        pageSwitcher.switcher(event, "AllocationTable.fxml");
    }
    
     @FXML
    public void handleLogoutBtn(MouseEvent event) throws IOException {
        pageSwitcher.switcher(event, "Login.fxml");
    }
    
    @FXML
    public void handleReportsBtn(MouseEvent event) throws IOException {
        pageSwitcher.switcher(event, "Reports.fxml");
    
   
    }
    
}
