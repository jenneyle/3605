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
            //later edition -- dont need current year in table as we can get current date from SQL. (Select CURRENT_YEAR;)
            ResultSet rs2 = conn.createStatement().executeQuery("SELECT CURRENT_DATE");
            updateYearList.addAll(rs2.getInt(1), rs2.getInt(1) + 1, rs2.getInt(1) + 2, rs2.getInt(1) + 3, rs2.getInt(1) + 4);
            updateYearComboBox.setItems(updateYearList);
        } catch (Exception e) {

        }
    }

    //Submit updates to the Weighting table
    public void handleSubmitBtn(ActionEvent event) throws IOException, SQLException {

        String courseCode = updateCourseComboBox.getValue();
        String term = updateTermComboBox.getValue();
        int year = updateYearComboBox.getValue();

        String updateStudents = updateWeightingStudents.getText();
        int intUpdateStudents = Integer.valueOf(updateStudents);

        String updateFaceHrs = updateWeightingFaceHrs.getText();
        int intUpdateFaceHrs = Integer.valueOf(updateFaceHrs);

        String updatePrepDevHrs = updateWeightingPrep.getText();
        int intUpdatePrepDevHrs = Integer.valueOf(updatePrepDevHrs);

        Statement st = conn.createStatement();
        try {
            String updateDatabase = ("UPDATE Weighting SET course_id = '" + courseCode
                    + "' , Year = '" + year
                    + "' , Term = '" + term
                    + "' ,  students_enrolled = '" + intUpdateStudents
                    + "' ,  face_time = '" + intUpdateFaceHrs
                    + "' ,  prep_dev = '" + intUpdatePrepDevHrs
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
}
