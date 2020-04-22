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
public class WeightingTableController implements Initializable {

    @FXML
    Button back;

    @FXML
    TableView weightingTable;
    TableColumn detailWeighting;
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
        TableColumn weightingStudents = new TableColumn(" STUDENTS\n ENROLLED");
        TableColumn<Weighting, Integer> wfacetoface_hrs = new TableColumn("FACE TO FACE\n HOURS");
        TableColumn<Weighting, Integer> wpd_hrs = new TableColumn("   PREPARATION AND\n DEVELOPMENT HOURS");
        TableColumn totalweighting = new TableColumn("    TOTAL\nWEIGHTING");
        TableColumn<Weighting, Integer> weightingRepeatLecture = new TableColumn(" REPEATED\n LECTURE");

        detailWeighting = new TableColumn("");
        editWeighting = new TableColumn("edit");

        weightingTable.getColumns().addAll(weightingCourse, weightingYear, weightingTerm, weightingStudents, wfacetoface_hrs, wpd_hrs
                ,weightingRepeatLecture, totalweighting, detailWeighting, editWeighting);

        ObservableList<Weighting> weighting = FXCollections.observableArrayList();

        try {
            //ResultSet rs = database.getResultSet("SELECT * FROM Weighting");
            ResultSet rs = database.getResultSet("SELECT weight_id, course_id, Year, Term, students_enrolled,\n"
                    + "(tutorial_hrs+lecture_hrs+consultation_hrs) as 'facetoface_hours',\n"
                    + "(marking_hrs+tutorial_prep+lecture_prep+staff_development) as 'pd_hours', \n"
                    + "weighting_term, repeat_lecture FROM Weighting");
            while (rs.next()) {
                //weighting.add(new Weighting(rs.getInt(1),rs.getString(2),rs.getInt(3),rs.getString(4),rs.getInt(5),rs.getInt(6),rs.getInt(7),Math.round(rs.getDouble(8)*10.0)/10.0),rs.getInt(9));
                weighting.add(new Weighting(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getInt(5), rs.getInt(6), rs.getInt(7), (Math.round(rs.getDouble(8) * 10.0) / 10.0), rs.getInt(9)));
//                weighting.add(new Weighting(rs.getString(1), rs.getString(2), rs.getInt(3),rs.getString(4), rs.getInt(5), rs.getInt(6), rs.getInt(7), rs.getDouble(8)));
                //TODO: formula
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        weightingCourse.setCellValueFactory(new PropertyValueFactory<>("course_id"));
        weightingYear.setCellValueFactory(new PropertyValueFactory<>("Year"));
        weightingTerm.setCellValueFactory(new PropertyValueFactory<>("Term"));
        weightingStudents.setCellValueFactory(new PropertyValueFactory<>("students_enrolled"));
        //weightingFaceHrs.setCellValueFactory(new PropertyValueFactory<Weighting, Integer>("face_time"));
        wfacetoface_hrs.setCellValueFactory(new PropertyValueFactory<>("facetoface_hours"));
        weightingRepeatLecture.setCellValueFactory(new PropertyValueFactory<>("repeat_lecture"));
        wpd_hrs.setCellValueFactory(new PropertyValueFactory<>("pd_hours"));
        totalweighting.setCellValueFactory(new PropertyValueFactory<>("weighting_term"));
        detailWeighting.setCellValueFactory(new PropertyValueFactory<Weighting, String>("detailsButton"));
        editWeighting.setCellValueFactory(new PropertyValueFactory<Weighting, String>("editButton"));
        
        weightingTable.setItems(weighting);
        setDetailButtons();
        setEditButtons();
        

    }

    //uncomment to make it work
    public void setDetailButtons() {
        System.out.println("clicked");
        // Detail Button
        detailWeighting.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Disposer.Record, Boolean>
                        , ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Disposer.Record, Boolean> p) {
                return new SimpleBooleanProperty(p.getValue() != null);
            }
        });

        detailWeighting.setCellFactory(
                new Callback<TableColumn<Disposer.Record, Boolean>
                        , TableCell<Disposer.Record, Boolean>>() {

            @Override
            public TableCell<Disposer.Record, Boolean> call(TableColumn<Disposer.Record, Boolean> p) {
                return new WeightingDetailButtonCell();
            }

        });

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
            //need to change to weighting edit button cell
                return new WeightingDetailButtonCell();
                //return new WeightingEditButtonCell();
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
        pageSwitcher.switcher(event, "WeightingTable.fxml");
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

    @FXML
    public void handleLogoutBtn(MouseEvent event) throws IOException {
        pageSwitcher.switcher(event, "Login.fxml");
    }

}
