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
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author freey
 */
public class StaffAllocationController implements Initializable {

    PageSwitchHelper pageSwitcher = new PageSwitchHelper();
    Database database = new Database();

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
    private CheckBox licCheckBox;

    @FXML
    private TextArea allocationNotes;
    @FXML
    private Label success;

    @FXML
    private ComboBox<String> termComboBox;
    @FXML
    private ComboBox<String> yearComboBox;
    @FXML
    private ComboBox<String> courseComboBox;
    @FXML
    Label available_weight;
    @FXML
    TextField allocation_weight;
    String staffID;
    String staffFname;
    Timer timer = new Timer();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        courseName = new Text();

        ObservableList<String> courseList = FXCollections.observableArrayList();
        ObservableList<String> staffList = FXCollections.observableArrayList();
        ObservableList<String> yearList = FXCollections.observableArrayList();
        ObservableList<String> termList = FXCollections.observableArrayList("Term 1", "Term 2", "Term 3", "Summer Term");
        termComboBox.setItems(termList);

        try {
            Database.openConnection();
            ResultSet rs1 = conn.createStatement().executeQuery("Select * FROM Courses");
            while (rs1.next()) {
                courseList.add(rs1.getString(1));
            }
            courseComboBox.setItems(courseList);
            ResultSet rs2 = conn.createStatement().executeQuery("Select * FROM Staff");
            while (rs2.next()) {
                staffList.add(rs2.getString(2) + " " + rs2.getString(3));
            }
            staffComboBox.setItems(staffList);
            ResultSet rs3 = conn.createStatement().executeQuery("SELECT DISTINCT year FROM Weighting;");
            while (rs3.next()) {
                yearList.addAll(rs3.getString(1));
            }
            
            yearComboBox.setItems(yearList);
        } catch (Exception e) {

        }
    }

    public void handleAllocation_weight(MouseEvent event) {
        String courseCode = courseComboBox.getValue();
        String term = termComboBox.getValue();
        String year = yearComboBox.getValue();
        String av_weight = "select weighting_term from weighting where course_id='" + courseCode + "'"
                + " and term='" + term + "' and year='" + year + "';";
        try {
            ResultSet rs = database.getResultSet(av_weight);
            while (rs.next()) {
                double weight = Math.rint(rs.getDouble(1) * 10.0) / 10.0;
                available_weight.setText("/ " + Double.toString(weight));
            }
        } catch (SQLException ex) {
            Logger.getLogger(StaffAllocationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void handleyear(MouseEvent event) {
        System.out.println("This is the Year Combobox of StaffAllocation ");
        String courseCode = courseComboBox.getValue();
        String term = termComboBox.getValue();
        String av_year = "select year from weighting where course_id='"+ courseCode + "'"
                + " and term='" + term +"'";
        ObservableList<String> yearList = FXCollections.observableArrayList();
        try {
            ResultSet rs = database.getResultSet(av_year);
            while (rs.next()) {
                yearList.add(rs.getString(1));
            }
        } catch (SQLException ex){
            Logger.getLogger(StaffAllocationController.class.getName()).log(Level.SEVERE, null, ex);
        }
        termComboBox.setValue(termComboBox.getPromptText());
        yearComboBox.setItems(yearList);
    }

    public void handleterm(MouseEvent event) {
        System.out.println("This is the Term Combobox of StaffAllocation");
        String courseCode = courseComboBox.getValue();
        String year = yearComboBox.getValue();
        String av_term = "select term from weighting where course_id='" + courseCode + "'"
                + " and year='" + year +"'";
        ObservableList<String> termList = FXCollections.observableArrayList();
        try {
            ResultSet rs = database.getResultSet(av_term);
            while (rs.next()) {
                available_weight.setText(Double.toString(rs.getDouble(1)));
                termList.add(rs.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(StaffAllocationController.class.getName()).log(Level.SEVERE, null, ex);
        }
        termComboBox.setItems(termList);
    }

    //TODO: autofill coursename
    public void handleSubmitBtn(ActionEvent event) throws IOException, SQLException {
        ConstraintsCheck.warning.clear();
        String courseCode = courseComboBox.getValue();
        String term = termComboBox.getValue();
        String staffName = staffComboBox.getValue();
        String notes = allocationNotes.getText();
        String weightinput = allocation_weight.getText();
        double weight = 0;
        if (weightinput.isEmpty()) {
            weightinput = available_weight.getText().substring(1);
            weight = Double.valueOf(weightinput);
        } else {
            try {
                weight = Double.valueOf(weightinput);
            } catch (Exception ex) {
                success.setText("you must enter a numeric number in textfield");
            }
        }

        int licCheck = 0;

        if (licCheckBox.isSelected()) {
            licCheck = 1;
        }

        //getting first name of the staff
        staffName = staffName.substring(0, staffName.indexOf(" "));

        //getting the staff ID from the staff name
        try {
            Database.openConnection();
            ResultSet rs = conn.createStatement().executeQuery("Select staff_id FROM Staff WHERE Fname = '" + staffName + "'");
            staffID = rs.getString(1);
        } catch (Exception e) {
        }

        int year = Integer.parseInt(yearComboBox.getValue());
        ConstraintsCheck rulecheck = new ConstraintsCheck();

        boolean exist = rulecheck.duplicateallocation(courseCode, staffID, year, term);
        
        if (exist == true) {
            
            Notifications dunotification=Notifications.create().text("Row not inserted due to a duplicate Allocation!").hideAfter(Duration.seconds(2)).position(Pos.CENTER);
            dunotification.showWarning();
            
        } else {
            rulecheck.check(courseCode, staffID, year, term, licCheck, weight);
            if (ConstraintsCheck.warning.size() == 0 || knowledgewarning == true) {
                Statement st = conn.createStatement();
                try {
                    String insertData = ("INSERT INTO ALLOCATION (allocation_year, allocation_term, course_id, staff_id, lic, allocation_description,allocation_weight)"
                            + " VALUES ('" + year + "','" + term + "','" + courseCode + "','" + staffID + "'," + licCheck + ",'" + notes + "'," + weight + ")");
                    st.execute(insertData);
                    knowledgewarning = false;
                    Notifications successnotification=Notifications.create().text("Allocation Success!").hideAfter(Duration.seconds(2)).position(Pos.CENTER);
                    successnotification.showInformation();
                    clear();
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

    }
    
    public void clear() {
        staffComboBox.setValue(staffComboBox.getPromptText());
        licCheckBox.setSelected(false);
        allocationNotes.setText("");
        allocation_weight.setText("");
        courseComboBox.setValue(courseComboBox.getPromptText());
        yearComboBox.setValue(yearComboBox.getPromptText());
        
        
    }

    @FXML
    public void handleBackBtn(ActionEvent event) throws IOException {
        pageSwitcher.switcher(event, "AllocationTable.fxml");
        System.out.println("Switching to Allocation Table");
    }

    @FXML
    public void handleAllocateBtn(ActionEvent event) throws IOException {
        pageSwitcher.switcher(event, "AllocationTable.fxml");
        System.out.println("Switching to Allocation Table");
    }

    @FXML
    public void handleWeightingBtn(MouseEvent event) throws IOException {
        pageSwitcher.switcher(event, "WeightingTable.fxml");
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
