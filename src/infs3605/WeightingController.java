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
    TableColumn updateWeighting;

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
        TableColumn weightingStudents = new TableColumn("STUDENTS\nENROLLED");
        TableColumn wtutorial_hrs = new TableColumn("TUTORIAL\nHOURS");
        TableColumn wlecture_hrs = new TableColumn("LECTURE\nHOURS");
        TableColumn wconsultation_hrs = new TableColumn("CONSULTATION\nHOURS");
        TableColumn wmarking_hrs = new TableColumn("MARKING\nHOURS");
        TableColumn wtutorial_prep = new TableColumn("TUTORIAL\nPREPARATION");
        TableColumn wlecture_prep = new TableColumn("LECTURE\nPREPARATION");
        TableColumn wstaff_development = new TableColumn("STAFF\nDEVELOPMENT\nHOURS");
        TableColumn weightingTotal = new TableColumn("TOTAL\nWEIGHTING");
        editWeighting = new TableColumn("DETAILS");
        updateWeighting = new TableColumn("UPDATE");

        weightingTable.getColumns().addAll(weightingCourse, weightingYear, weightingTerm, weightingStudents, wtutorial_hrs, wlecture_hrs, wconsultation_hrs, wmarking_hrs, wtutorial_prep, wlecture_prep, wstaff_development, weightingTotal,editWeighting, updateWeighting);

        ObservableList<Weighting> weighting = FXCollections.observableArrayList();

        try {
            //ResultSet rs = database.getResultSet("SELECT * FROM Weighting");
            ResultSet rs = database.getResultSet("SELECT weight_id, course_id, Year, Term, students_enrolled, tutorial_hrs, lecture_hrs, consultation_hrs, marking_hrs, tutorial_prep, lecture_prep, staff_development, ROUND(weighting_term, 2) FROM Weighting");
            while (rs.next()) {
                weighting.add(new Weighting(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getInt(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9), rs.getInt(10), rs.getInt(11), rs.getInt(12), rs.getDouble(13)));
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
        //weightingFaceHrs.setCellValueFactory(new PropertyValueFactory<Weighting, Integer>("face_time"));
        wtutorial_hrs.setCellValueFactory(new PropertyValueFactory<Weighting, Integer>("tutorial_hrs"));
        wlecture_hrs.setCellValueFactory(new PropertyValueFactory<Weighting, Integer>("lecture_hrs"));
        wconsultation_hrs.setCellValueFactory(new PropertyValueFactory<Weighting, Integer>("consultation_hrs"));
        
        wmarking_hrs.setCellValueFactory(new PropertyValueFactory<Weighting, Integer>("marking_hrs"));
        wtutorial_prep.setCellValueFactory(new PropertyValueFactory<Weighting, Integer>("tutorial_prep"));
        wlecture_prep.setCellValueFactory(new PropertyValueFactory<Weighting, Integer>("lecture_prep"));
        
        wstaff_development.setCellValueFactory(new PropertyValueFactory<Weighting, Integer>("staff_development"));
        weightingTotal.setCellValueFactory(new PropertyValueFactory<Weighting, Double>("weighting_term"));
        editWeighting.setCellValueFactory(new PropertyValueFactory<Allocation, String>("editButton"));
        //uncomment to make it work
        //setEditButtons();
        updateWeighting.setCellValueFactory(new PropertyValueFactory<Allocation, String>("updateButton"));
        setUpdateButtons();
        weightingTable.setItems(weighting);

    }
    //uncomment to make it work
//    public void setEditButtons() {
//        // Detail Button
//        editWeighting.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Disposer.Record, Boolean>, ObservableValue<Boolean>>() {
//            @Override
//            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Disposer.Record, Boolean> p) {
//                return new SimpleBooleanProperty(p.getValue() != null);
//            }
//        });
//
//        editWeighting.setCellFactory(
//                new Callback<TableColumn<Disposer.Record, Boolean>, TableCell<Disposer.Record, Boolean>>() {
//
//            @Override
//            public TableCell<Disposer.Record, Boolean> call(TableColumn<Disposer.Record, Boolean> p) {
//                return new WeightingDetailButtonCell();
//            }
//
//        });
//
//    }
    public void setUpdateButtons() {
        // Edit Button
        updateWeighting.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Disposer.Record, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Disposer.Record, Boolean> p) {
                return new SimpleBooleanProperty(p.getValue() != null);
            }
        });

        updateWeighting.setCellFactory(
                new Callback<TableColumn<Disposer.Record, Boolean>, TableCell<Disposer.Record, Boolean>>() {

            @Override
            public TableCell<Disposer.Record, Boolean> call(TableColumn<Disposer.Record, Boolean> p) {
                return new AllocationButtonCell();
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
