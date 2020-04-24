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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
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
    CheckBox lic;
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
    ComboBox<String> year;
    @FXML
    ComboBox<String> term;

    int allocationId = 0;
    PageSwitchHelper pageSwitcher = new PageSwitchHelper();
    Database database=new Database();

    ObservableList<String> courseCodeList = FXCollections.observableArrayList();
    ObservableList<String> staffList = FXCollections.observableArrayList();
    ObservableList<String> yearList = FXCollections.observableArrayList();
    ObservableList<String> termList = FXCollections.observableArrayList("Term 1", "Term 2", "Term 3", "Summer Term");

    public void setData(int uAllocationId) {
        try {
            Database.openConnection();
            ResultSet rs = conn.createStatement().executeQuery("SELECT DISTINCT Allocation.allocation_id, Allocation.allocation_term, Allocation.allocation_year, Allocation.course_id, Allocation.staff_id, Courses.course_name, Staff.Fname, Staff.Lname, Allocation.allocation_weight, Allocation.lic, Allocation.allocation_description, Weighting.weighting_term FROM Staff JOIN Allocation ON Staff.staff_id = Allocation.staff_id JOIN Courses ON Courses.course_id = Allocation.course_id Join Weighting ON Weighting.course_id = Allocation.course_id WHERE allocation_id = " + uAllocationId);

            allocationId = uAllocationId;
           courseCode.setText(rs.getString("course_id") + " " + rs.getString("course_name"));
            staffName.setText(rs.getString(7) + " " + rs.getString(8));
            year.setValue(rs.getString("allocation_year"));
            term.setValue(rs.getString("allocation_term"));
            weighting.setText(rs.getString("allocation_weight"));
            allocationNotes.setText(rs.getString("allocation_description"));
            totalWeighting.setText("/ " + rs.getString("weighting_term"));
            if (rs.getInt("lic") == 1) {
                lic.setSelected(true);
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
                //courseCodeList.add(rs1.getString(1));
            }

            ResultSet rs2 = conn.createStatement().executeQuery("Select * FROM Staff");
            while (rs2.next()) {
                staffList.add(rs2.getString(2) + " " + rs2.getString(3));
            }
            ResultSet rs3 = conn.createStatement().executeQuery("SELECT DISTINCT year FROM Weighting;");
            while (rs3.next()) {
                yearList.addAll(rs3.getString(1));
            }
            year.setItems(yearList);

            term.setItems(termList);
            TextFields.bindAutoCompletion(courseCode, courseCodeList);
            TextFields.bindAutoCompletion(staffName, staffList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void handleAllocation_weight(MouseEvent event) {
        String scourseCode = courseCode.getText().substring(0,8);
        String sterm = term.getValue();
        String syear = year.getValue();
        String av_weight = "select weighting_term from weighting where course_id='" + scourseCode + "'"
                + " and term='" + sterm + "' and year='" + syear + "';";
        try {
            ResultSet rs = database.getResultSet(av_weight);
            while (rs.next()) {
                double weight = Math.rint(rs.getDouble(1) * 10.0) / 10.0;
                totalWeighting.setText("/ " + Double.toString(weight));
            }
        } catch (SQLException ex) {
            Logger.getLogger(StaffAllocationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void handleSubmitButton(ActionEvent event) throws IOException, SQLException {
        String iCourseCode = courseCode.getText().substring(0,8);
        String iTerm = (String) term.getValue();
        String iStaffName = staffName.getText();
        String iAllocationNotes = allocationNotes.getText();
        iStaffName = iStaffName.substring(0, iStaffName.indexOf(" "));
        String iYearString =  year.getValue().toString();
        int iYear = Integer.parseInt(iYearString);
        double iWeighting = Double.parseDouble(weighting.getText());
        int iLic = 0;
        
        
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
            Notifications updatenotification=Notifications.create().text("Update Success").hideAfter(Duration.seconds(2)).position(Pos.CENTER);
            updatenotification.showInformation();
            
            //System.out.println("insert success");

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
