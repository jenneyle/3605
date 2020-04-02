/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infs3605;

import com.sun.prism.impl.Disposer;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author jenneyle
 */
//To dispay Weighting table
public class WeightingController implements Initializable {

    @FXML
    Button back;

    @FXML
    TableView weightingTable;

    Database database = new Database();
    PageSwitchHelper pageSwitcher = new PageSwitchHelper();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        TableColumn weightingCourse = new TableColumn("COURSE ID");
        TableColumn weightingYear = new TableColumn("YEAR");
        TableColumn weightingTerm = new TableColumn("TERM");
        TableColumn weightingStudents = new TableColumn("STUDENTS ENROLLED");
        TableColumn weightingFaceHrs = new TableColumn("FACE TO FACE HOURS");
        TableColumn weightingPrepHrs = new TableColumn("PREPARATION AND DEVELOPMENT HOURS");
        TableColumn weightingTotal = new TableColumn("TOTAL WEIGHTING");

        weightingTable.getColumns().addAll(weightingCourse, weightingYear, weightingTerm, weightingStudents, weightingFaceHrs, weightingPrepHrs, weightingTotal);

        ObservableList<Weighting> weighting = FXCollections.observableArrayList();

        try {
            ResultSet rs = database.getResultSet("SELECT * FROM Weighting");
            while (rs.next()) {
                weighting.add(new Weighting(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getInt(5), rs.getInt(6), rs.getInt(7), rs.getDouble(8)));
//                weighting.add(new Weighting(rs.getString(1), rs.getString(2), rs.getInt(3),rs.getString(4), rs.getInt(5), rs.getInt(6), rs.getInt(7), rs.getDouble(8)));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        weightingCourse.setCellValueFactory(new PropertyValueFactory<Weighting, String>("course_id"));
        weightingYear.setCellValueFactory(new PropertyValueFactory<Weighting, Integer>("Year"));
        weightingTerm.setCellValueFactory(new PropertyValueFactory<Weighting, String>("Term"));
        weightingStudents.setCellValueFactory(new PropertyValueFactory<Weighting, Integer>("students_enrolled"));
        weightingFaceHrs.setCellValueFactory(new PropertyValueFactory<Weighting, Integer>("face_time"));
        weightingPrepHrs.setCellValueFactory(new PropertyValueFactory<Weighting, Integer>("prep_dev"));
        weightingTotal.setCellValueFactory(new PropertyValueFactory<Weighting, Integer>("weighting_term"));
        weightingTable.setItems(weighting);

    }

    @FXML
    public void handleUpdateWeightingBtn(ActionEvent event) throws IOException {
        pageSwitcher.switcher(event, "UpdateWeightings.fxml");

    }

    public void handleBackBtn(ActionEvent event) throws IOException {
        pageSwitcher.switcher(event, "AllocationTable.fxml");
        System.out.println("Switching to Allocation Table");
    }
    
    //button to allocate staff to course
    @FXML
    public void handleAllocateBtn(MouseEvent event) throws IOException {
        pageSwitcher.switcher(event, "StaffAllocation.fxml");
    }

}
