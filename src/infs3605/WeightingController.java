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
    TableColumn editWeighting;

    Database database = new Database();
    PageSwitchHelper pageSwitcher = new PageSwitchHelper();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //Initialise the columns
        TableColumn weightingCourse = new TableColumn("COURSE ID");
        TableColumn weightingYear = new TableColumn("YEAR");
        TableColumn weightingTerm = new TableColumn("TERM");
        TableColumn weightingStudents = new TableColumn("STUDENTS ENROLLED");
        TableColumn weightingFaceHrs = new TableColumn("FACE TO FACE HOURS");
        TableColumn weightingPrepHrs = new TableColumn("PREPARATION AND DEVELOPMENT HOURS");
        TableColumn weightingTotal = new TableColumn("TOTAL WEIGHTING");
        editWeighting = new TableColumn("EDIT");

        weightingTable.getColumns().addAll(weightingCourse, weightingYear, weightingTerm, weightingStudents, weightingFaceHrs, weightingPrepHrs, weightingTotal,editWeighting);

        ObservableList<Weighting> weighting = FXCollections.observableArrayList();

        try {
            ResultSet rs = database.getResultSet("SELECT * FROM Weighting");
            while (rs.next()) {
                weighting.add(new Weighting(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getInt(5), rs.getInt(6), rs.getInt(7), rs.getDouble(8)));
//                weighting.add(new Weighting(rs.getString(1), rs.getString(2), rs.getInt(3),rs.getString(4), rs.getInt(5), rs.getInt(6), rs.getInt(7), rs.getDouble(8)));
           //TODO: formula
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
        weightingTotal.setCellValueFactory(new PropertyValueFactory<Weighting, Double>("weighting_term"));
        editWeighting.setCellValueFactory(new PropertyValueFactory<Allocation, String>("editButton"));
        setEditButtons();
        weightingTable.setItems(weighting);

    }
    public void setEditButtons() {
        // Edit Button
        editWeighting.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Disposer.Record, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Disposer.Record, Boolean> p) {
                return new SimpleBooleanProperty(p.getValue() != null);
            }
        });

        editWeighting.setCellFactory(
                new Callback<TableColumn<Disposer.Record, Boolean>, TableCell<Disposer.Record, Boolean>>() {

            @Override
            public TableCell<Disposer.Record, Boolean> call(TableColumn<Disposer.Record, Boolean> p) {
                return new ButtonCell();
            }

        });

    }

    //button to update course weightings
    @FXML
    public void handleUpdateWeightingBtn(ActionEvent event) throws IOException {
        pageSwitcher.switcher(event, "UpdateWeightings.fxml");

    }

    //button to go back to main page
    public void handleBackBtn(ActionEvent event) throws IOException {
        pageSwitcher.switcher(event, "AllocationTable.fxml");
        System.out.println("Switching to Allocation Table");
    }
    
    //button to allocate staff to course
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
    //button to view course details
    @FXML
    public void handleCourseBtn(MouseEvent event) throws IOException {
        pageSwitcher.switcher(event, "CourseTable.fxml");
    }
    
    @FXML
    public void handleCurrentAlloBtn(MouseEvent event) throws IOException {
        pageSwitcher.switcher(event, "AllocationTable.fxml");
    }

}
