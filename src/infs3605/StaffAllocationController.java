/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infs3605;

import java.util.*;

import static infs3605.Database.conn;
import static infs3605.LoginController.loggedInUser;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;
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
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author freey
 */
public class StaffAllocationController implements Initializable {

    PageSwitchHelper pageSwitcher = new PageSwitchHelper();

    public static Boolean knowledgewarning = false;
    @FXML
    Button submit;

    @FXML
    Button back;

    @FXML
    Text courseName;

    @FXML
    private ComboBox<String> staffComboBox;
    @FXML
    private Label success;

    @FXML
    private ComboBox<String> termComboBox;
    @FXML
    private ComboBox<Integer> yearComboBox;
    @FXML
    private ComboBox<String> courseComboBox;
    String staffID;
    String staffFname;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        courseName = new Text();

        ObservableList<String> courseList = FXCollections.observableArrayList();
        ObservableList<String> staffList = FXCollections.observableArrayList();
        ObservableList<Integer> yearList = FXCollections.observableArrayList();
        ObservableList<String> termList = FXCollections.observableArrayList("Term 1", "Term 2", "Term 3", "Summer Term");
        termComboBox.setItems(termList);

        try {
            Database.openConnection();
            ResultSet rs1 = conn.createStatement().executeQuery("Select * FROM Courses");
            while (rs1.next()) {
                courseList.add(rs1.getString(1));
                courseComboBox.setItems(courseList);

            }

            ResultSet rs2 = conn.createStatement().executeQuery("Select * FROM Staff");
            while (rs2.next()) {
                staffList.add(rs2.getString(2) + " " + rs2.getString(3));
                staffComboBox.setItems(staffList);
                // staffID = rs2.getString(1);

            }
            ResultSet rs3 = conn.createStatement().executeQuery("SELECT CURRENT_DATE");
            yearList.addAll(rs3.getInt(1), rs3.getInt(1) + 1, rs3.getInt(1) + 2, rs3.getInt(1) + 3, rs3.getInt(1) + 4);
            yearComboBox.setItems(yearList);
        } catch (Exception e) {

        }
    }

    //TODO: autofill coursename
    public void handleSubmitBtn(ActionEvent event) throws IOException, SQLException {
        ConstraintsCheck.warning.clear();
        String courseCode = courseComboBox.getValue();
        String term = termComboBox.getValue();
        String staffName = staffComboBox.getValue();

        //getting first name of the staff
        staffName = staffName.substring(0, staffName.indexOf(" "));

        //getting the staff ID from the staff name
        try {
            Database.openConnection();
            ResultSet rs = conn.createStatement().executeQuery("Select staff_id FROM Staff WHERE Fname = '" + staffName + "'");
            staffID = rs.getString(1);
        } catch (Exception e) {
        }

        int year = yearComboBox.getValue();
        ConstraintsCheck rulecheck = new ConstraintsCheck();
        rulecheck.check(courseCode, staffID, year, term);
        ArrayList<String> warning = ConstraintsCheck.warning;

        if (warning.isEmpty() || knowledgewarning == true) {
            Statement st = conn.createStatement();
            try {
                String insertData="";
                switch(warning.size()){
                    case 0:insertData = ("INSERT INTO ALLOCATION (allocation_year, allocation_term, course_id, staff_id)"
                        + " VALUES ('" + year + "','" + term + "','" + courseCode + "','" + staffID + "')");
                    break;
                    case 1:insertData = ("INSERT INTO ALLOCATION (allocation_year, allocation_term, course_id, staff_id,warning1)"
                        + " VALUES ('" + year + "','" + term + "','" + courseCode + "','" + staffID + "','"+warning.get(0)+"')");
                    break;
                    case 2:insertData = ("INSERT INTO ALLOCATION (allocation_year, allocation_term, course_id, staff_id,warning1,warning2)"
                        + " VALUES ('" + year + "','" + term + "','" + courseCode + "','" + staffID + "','"+warning.get(0)+"','"+warning.get(1)+"')");
                    break;
                }
                st.execute(insertData);
                knowledgewarning = false;
                success.setText("Allocation Success!");
                TimerTask task= new TimerTask() {
                    @Override
                    public void run() {
                        success.setText("");
                    }
                };
                Timer timer=new Timer();
                timer.schedule(task,5000);
                System.out.println("insert success");

            } catch (Exception ex) {
                ex.printStackTrace();
            }
            //pageSwitcher.switcher(event, "DisplayAllocation.fxml");
        } else {
            Parent root = FXMLLoader.load(getClass().getResource("Warning.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        }

    }

    public void handleBackBtn(ActionEvent event) throws IOException {
        pageSwitcher.switcher(event, "AllocationTable.fxml");
        System.out.println("Switching to Allocation Table");
    }
    
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
}
