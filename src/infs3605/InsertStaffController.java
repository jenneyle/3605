/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infs3605;

import static infs3605.Database.conn;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author jenneyle
 */
public class InsertStaffController implements Initializable {

    @FXML
    Button back;
    @FXML
    Button submit;

    @FXML
    ComboBox<String> insertStaffType;
//    @FXML
//    ComboBox<Integer> insertStaffCapacity;
    @FXML
    private TextField insertStaffFname;
    @FXML
    private TextField insertStaffCapacity;
    @FXML
    private TextField insertStaffLname;
    @FXML
    private TextField insertStaffZid;
    @FXML
    private TextField insertStaffEmail;
//    @FXML
//    private Label updateMsg;

    PageSwitchHelper pageSwitcher = new PageSwitchHelper();

    public void handleBackButton(ActionEvent event) throws IOException {
        pageSwitcher.switcher(event, "StaffTable.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> staffTypeList = FXCollections.observableArrayList("Full-time Teaching", "Full-time Research", "Full-time Teaching/Research", "Casual Teaching");
        insertStaffType.setItems(staffTypeList);

//        updateMsg.setVisible(false);
    }

    public void handleInsertBtn(ActionEvent event) throws IOException, SQLException {

//        ObservableList<String> staffTypeList = FXCollections.observableArrayList("Education", "Research");
//        insertStaffType.setItems(staffTypeList);
        String type = insertStaffType.getValue();
//        ObservableList<Integer> staffCapacityList = FXCollections.observableArrayList(3, 6);
//        insertStaffCapacity.setItems(staffCapacityList);
        String insertCapacity = insertStaffCapacity.getText();
        double capacity = Double.valueOf(insertCapacity);
//        Integer capacity = insertStaffCapacity.getValue();
        String insertFname = insertStaffFname.getText();
        String insertLname = insertStaffLname.getText();
        String insertZid = insertStaffZid.getText();
        String insertEmail = insertStaffEmail.getText();

        Statement st = conn.createStatement();
        try {
            String insertData = ("INSERT INTO STAFF (staff_id, Fname, Lname, staff_type, staff_capacity, staff_email)" + " VALUES ('" + insertZid + "','"
                    + insertFname + "', '" + insertLname + "', '" + type + "', '" + capacity + "', '" + insertEmail + "')");

            st.execute(insertData);
            Notifications insertnotification=Notifications.create().text("New Staff Added").hideAfter(Duration.seconds(2)).position(Pos.CENTER);
            insertnotification.showInformation();
            System.out.println("Submitted");
//            updateMsg.setText("Successfully submitted");
//            updateMsg.setTextFill(Color.web("#008000"));
//            updateMsg.setVisible(true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
     @FXML
    public void handleAllocateBtn(MouseEvent event) throws IOException {
        pageSwitcher.switcher(event, "StaffAllocation.fxml");
    }

    //button to view course weightings
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
