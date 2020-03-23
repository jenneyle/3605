/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infs3605;

import java.util.*;

import static infs3605.Database.conn;
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
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author freey
 */
public class StaffAllocationController implements Initializable {

   PageSwitchHelper pageSwitcher = new PageSwitchHelper();
    @FXML
    Button submit;

    @FXML
    Text courseName;

    @FXML
    private ComboBox<String> staffComboBox;

    @FXML
    private ComboBox<String> courseComboBox;
String staffID;
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        courseComboBox.setPromptText("Please select a course code");
        staffComboBox.setPromptText("Please select a staff member");

        courseName = new Text();

        ObservableList<String> courseList = FXCollections.observableArrayList();
        ObservableList<String> staffList = FXCollections.observableArrayList();

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
                staffID = rs2.getString(1);

            }
        } catch (Exception e) {

        }
    }

    //TODO: autofill coursename
    public void handleSubmitBtn(ActionEvent event) throws IOException, SQLException {

       // String staffName = staffComboBox.getValue();
        String courseCode = courseComboBox.getValue();

        Statement st = conn.createStatement();
        try {
            String insertData = ("INSERT INTO ALLOCATION (ALLOCATION_YEAR, ALLOCATION_TERM, COURSE_ID, STAFF_ID)" + " VALUES ('2020','T1','" + courseCode + "','"
                    + staffID + "')");
            st.execute(insertData);
            
             

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        pageSwitcher.switcher(event, "Display_allocation.fxml");
    }

}
