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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Rjian
 */
public class CourseTableController implements Initializable {

    @FXML
    public TableView courseTable;
    TableColumn editCourse;
    @FXML
    public ComboBox courseSelectionCB;
    @FXML
    public Button clearFilterBtn;
    
    Database database = new Database();
    PageSwitchHelper pageSwitcher = new PageSwitchHelper();
   
    ObservableList<Course> data = FXCollections.observableArrayList();
    ObservableList<String> courses = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Initialise the columns
        TableColumn idCol = new TableColumn("COURSE ID");
        TableColumn nameCol = new TableColumn("COURSE NAME");
        TableColumn t1Col = new TableColumn("T1");
        TableColumn t2Col = new TableColumn("T2");
        TableColumn t3Col = new TableColumn("T3");
        editCourse = new TableColumn("EDIT");
        //Add columns to tableview
        courseTable.getColumns().addAll(idCol, nameCol, t1Col, t2Col, t3Col, editCourse);
        
        //Get Complete Rows from Database for ComboBoxes - years, terms, courses
        try {
            ResultSet coursesRS = database.getResultSet("SELECT DISTINCT course_id FROM Courses");
            while (coursesRS.next()) {
                courses.add(coursesRS.getString(1));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        //Populate Combo Box
        courseSelectionCB.setItems(courses);
        
        setAllTable();
        
        //Based on Course, populate the cells of the table
        idCol.setCellValueFactory(new PropertyValueFactory<Course, Integer>("course_id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<Course, String>("courseName"));
        t1Col.setCellValueFactory(new PropertyValueFactory<Course, Double>("t1Offer"));
        
        //https://code.makery.ch/blog/javafx-8-tableview-cell-renderer/
//        t1Col.setCellFactory(column -> {
//            return new TableCell<Course, String>() {
//                @Override
//                protected void updateItem(String item, boolean empty) {
//                    super.updateItem(item, empty);
//                    if (item.equals("1")) {
//                        setStyle("-fx-background-color: green");
//                    } else {
//                        setStyle("-fx-background-color: red");
//                    }
//                }
//            };
//        });
        t2Col.setCellValueFactory(new PropertyValueFactory<Course, Double>("t2Offer"));
        t3Col.setCellValueFactory(new PropertyValueFactory<Course, Double>("t3Offer"));
        editCourse.setCellValueFactory(new PropertyValueFactory<Course, String>("editButton"));
        
        setEditButtons();
        
        //Populate the Table
        courseTable.setItems(data);
    }    
    
    @FXML
    public void selectCourseType(){
        data.removeAll(data);
        
        //Get Complete Rows from Database
        try {
            ResultSet rs = database.getResultSet("SELECT course_id, course_name"
                                                + ", t1_offer, t2_offer, t3_offer"
                                                + " FROM Courses"
                                                + " WHERE course_id = '" + courseSelectionCB.getValue() + "'"
                                                );
            while (rs.next()) {
                data.add(new Course(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5)));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        //Populate the Table
        courseTable.setItems(data);
        setEditButtons();
    }
    
    //Fetch Rows from Database and set Table
    public void setAllTable() {
        //Get Complete Rows from Database
        try {
            ResultSet rs = database.getResultSet("SELECT course_id, course_name"
                                                + ", t1_offer, t2_offer, t3_offer"
                                                + " FROM Courses"
                                                );
            while (rs.next()) {
                data.add(new Course(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5)));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public void setEditButtons() {
        // Edit Button
        editCourse.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Disposer.Record, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Disposer.Record, Boolean> p) {
                return new SimpleBooleanProperty(p.getValue() != null);
            }
        });

        editCourse.setCellFactory(
                new Callback<TableColumn<Disposer.Record, Boolean>, TableCell<Disposer.Record, Boolean>>() {

            @Override
            public TableCell<Disposer.Record, Boolean> call(TableColumn<Disposer.Record, Boolean> p) {
                return new ButtonCell();
            }

        });
        
    }
    
    public void clearFilters(ActionEvent event) {
        data.removeAll(data);
        courseSelectionCB.setValue(courseSelectionCB.getPromptText());
        
        setAllTable();
        
        //Populate the Table
        courseTable.setItems(data);
        setEditButtons();
    }
    
    //button to allocate staff to course
    @FXML
    public void handleAllocateBtn(ActionEvent event) throws IOException {
        pageSwitcher.switcher(event, "StaffAllocation.fxml");
    }
    //button to view course weightings
    @FXML
    public void handleWeightingBtn(ActionEvent event) throws IOException {
        pageSwitcher.switcher(event, "Weighting.fxml");
    }   
    
}
