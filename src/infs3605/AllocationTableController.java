/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infs3605;

import com.sun.prism.impl.Disposer.Record;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author freey
 */
public class AllocationTableController implements Initializable {

    @FXML
    public TableView allocationTable;
    TableColumn editAllocation;
    @FXML
    public ComboBox yearSelectionCB;
    @FXML
    public ComboBox termSelectionCB;
    @FXML
    public ComboBox courseSelectionCB;
    
    Database database = new Database();
    PageSwitchHelper pageSwitcher = new PageSwitchHelper();
   
    ObservableList<Allocation> data = FXCollections.observableArrayList();
    ObservableList<String> years = FXCollections.observableArrayList();
    ObservableList<String> terms = FXCollections.observableArrayList();
    ObservableList<String> courses = FXCollections.observableArrayList();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //Initialise the columns
        TableColumn allocationID = new TableColumn("ID");
        TableColumn year = new TableColumn("YEAR");
        TableColumn term = new TableColumn("TERM");
        TableColumn courseId = new TableColumn("COURSE ID");
        TableColumn staffId = new TableColumn("STAFF ID");
        TableColumn weighting = new TableColumn("WEIGHTING");
        editAllocation = new TableColumn("EDIT");
        //Add columns to tableview
        allocationTable.getColumns().addAll(year, term, courseId, staffId, weighting, editAllocation);
        
        //Get Complete Rows from Database for ComboBoxes - years, terms, courses
        try {
            ResultSet yearRS = database.getResultSet("SELECT DISTINCT allocation_year FROM Allocation");
            while (yearRS.next()) {
                years.add(yearRS.getString(1));
            }
            ResultSet termRS = database.getResultSet("SELECT DISTINCT allocation_term FROM Allocation");
            while (termRS.next()) {
                terms.add(termRS.getString(1));
            }
            ResultSet courseRS = database.getResultSet("SELECT DISTINCT course_id FROM Allocation");
            while (courseRS.next()) {
                courses.add(courseRS.getString(1));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        //Populate Combo Box
        yearSelectionCB.setItems(years);
        termSelectionCB.setItems(terms);
        courseSelectionCB.setItems(courses);
        
        //Get Complete Rows from Database
        try {
            ResultSet rs = database.getResultSet("SELECT DISTINCT a.allocation_id, a.course_id, a.allocation_year"
                                                + ", a.allocation_term, w.weighting_term, a.staff_id"
                                                + " FROM Allocation a" 
                                                + " LEFT OUTER JOIN Weighting w" 
                                                + " ON a.course_id = w.course_id" 
                                                + " AND a.allocation_year = w.Year" 
                                                + " AND a.allocation_term = w.Term"
                                                );
            while (rs.next()) {
                data.add(new Allocation(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getDouble(5), rs.getString(6)));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        //Based on Allocation.class, populate the cells of the table
        allocationID.setCellValueFactory(new PropertyValueFactory<Allocation, Integer>("allocation_id"));
        allocationID.setVisible(false);
        year.setCellValueFactory(new PropertyValueFactory<Allocation, Integer>("Year"));
        term.setCellValueFactory(new PropertyValueFactory<Allocation, String>("Term"));
        courseId.setCellValueFactory(new PropertyValueFactory<Allocation, String>("Course_id"));
        weighting.setCellValueFactory(new PropertyValueFactory<Allocation, Integer>("Weight"));
        staffId.setCellValueFactory(new PropertyValueFactory<Allocation, String>("Staff_id"));
        editAllocation.setCellValueFactory(new PropertyValueFactory<Allocation, String>("editButton"));
        
        setEditButtons();
        
        //Populate the Table
        allocationTable.setItems(data);

    }
    
    public void setEditButtons() {
        // Edit Button
        editAllocation.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Record, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Record, Boolean> p) {
                return new SimpleBooleanProperty(p.getValue() != null);
            }
        });

        editAllocation.setCellFactory(
                new Callback<TableColumn<Record, Boolean>, TableCell<Record, Boolean>>() {

            @Override
            public TableCell<Record, Boolean> call(TableColumn<Record, Boolean> p) {
                return new ButtonCell();
            }

        });
        
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
    
    @FXML
    public void selectYearComboBox(ActionEvent event) {        
        data.removeAll(data);
        termSelectionCB.setValue(termSelectionCB.getPromptText());
        courseSelectionCB.setValue(courseSelectionCB.getPromptText());
        
        //Get Complete Rows from Database
        try {
            ResultSet rs = database.getResultSet("SELECT DISTINCT a.allocation_id, a.course_id, a.allocation_year"
                                                + ", a.allocation_term, w.weighting_term, a.staff_id"
                                                + " FROM Allocation a" 
                                                + " LEFT OUTER JOIN Weighting w" 
                                                + " ON a.course_id = w.course_id" 
                                                + " AND a.allocation_year = w.Year" 
                                                + " AND a.allocation_term = w.Term"
                                                + " WHERE a.allocation_year = " + yearSelectionCB.getValue()
                                                );
            while (rs.next()) {
                data.add(new Allocation(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getDouble(5), rs.getString(6)));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        //Populate the Table
        allocationTable.setItems(data);
        setEditButtons();
    }
    
    @FXML
    public void selectTermComboBox(ActionEvent event) {        
        data.removeAll(data);
        yearSelectionCB.setValue(yearSelectionCB.getPromptText());
        courseSelectionCB.setValue(courseSelectionCB.getPromptText());
        
        //Get Complete Rows from Database
        try {
            ResultSet rs = database.getResultSet("SELECT DISTINCT a.allocation_id, a.course_id, a.allocation_year"
                                                + ", a.allocation_term, w.weighting_term, a.staff_id"
                                                + " FROM Allocation a" 
                                                + " LEFT OUTER JOIN Weighting w" 
                                                + " ON a.course_id = w.course_id" 
                                                + " AND a.allocation_year = w.Year" 
                                                + " AND a.allocation_term = w.Term"
                                                + " WHERE a.allocation_term = '" + termSelectionCB.getValue() + "'"
                                                );
            while (rs.next()) {
                data.add(new Allocation(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getDouble(5), rs.getString(6)));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        //Populate the Table
        allocationTable.setItems(data);
        setEditButtons();
    }
    
    @FXML
    public void selectCourseComboBox(ActionEvent event) {        
        data.removeAll(data);
        termSelectionCB.setValue(termSelectionCB.getPromptText());
        yearSelectionCB.setValue(courseSelectionCB.getPromptText());
        
        //Get Complete Rows from Database
        try {
            ResultSet rs = database.getResultSet("SELECT DISTINCT a.allocation_id, a.course_id, a.allocation_year"
                                                + ", a.allocation_term, w.weighting_term, a.staff_id"
                                                + " FROM Allocation a" 
                                                + " LEFT OUTER JOIN Weighting w" 
                                                + " ON a.course_id = w.course_id" 
                                                + " AND a.allocation_year = w.Year" 
                                                + " AND a.allocation_term = w.Term"
                                                + " WHERE a.course_id = '" + courseSelectionCB.getValue() + "'"
                                                );
            while (rs.next()) {
                data.add(new Allocation(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getDouble(5), rs.getString(6)));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        //Populate the Table
        allocationTable.setItems(data);
        setEditButtons();
    }
}

