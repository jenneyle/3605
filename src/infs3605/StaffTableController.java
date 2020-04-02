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
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Rjian
 */
public class StaffTableController implements Initializable {
    
    @FXML
    public TableView staffTable;
    TableColumn editStaff;
    @FXML
    public ComboBox staffTypeSelectionCB;
    @FXML
    public Button clearFilterBtn;
    
    Database database = new Database();
    PageSwitchHelper pageSwitcher = new PageSwitchHelper();
   
    ObservableList<Staff> data = FXCollections.observableArrayList();
    ObservableList<String> types = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Initialise the columns
        TableColumn id = new TableColumn("STAFF ID");
        TableColumn fName = new TableColumn("FIRST NAME");
        TableColumn lName = new TableColumn("LAST NAME");
        TableColumn type = new TableColumn("STAFF TYPE");
        TableColumn capacity = new TableColumn("TEACHING CAPACITY");
        editStaff = new TableColumn("EDIT");
        //Add columns to tableview
        staffTable.getColumns().addAll(id, fName, lName, type, capacity, editStaff);
        
        //Get Complete Rows from Database for ComboBoxes - years, terms, courses
        try {
            ResultSet typesRS = database.getResultSet("SELECT DISTINCT staff_type FROM Staff");
            while (typesRS.next()) {
                types.add(typesRS.getString(1));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        //Populate Combo Box
        staffTypeSelectionCB.setItems(types);
        
        setAllTable();
        
        //Based on Staff.class, populate the cells of the table
        id.setCellValueFactory(new PropertyValueFactory<Staff, Integer>("staffId"));
        id.setVisible(false);
        fName.setCellValueFactory(new PropertyValueFactory<Staff, String>("firstName"));
        lName.setCellValueFactory(new PropertyValueFactory<Staff, String>("lastName"));
        type.setCellValueFactory(new PropertyValueFactory<Staff, String>("staffType"));
        capacity.setCellValueFactory(new PropertyValueFactory<Staff, Double>("staffCapacity"));
        editStaff.setCellValueFactory(new PropertyValueFactory<Staff, String>("editButton"));
        
        setEditButtons();
        
        //Populate the Table
        staffTable.setItems(data);
    }    
    
    @FXML
    public void selectStaffType(){
        data.removeAll(data);
        
        //Get Complete Rows from Database
        try {
            ResultSet rs = database.getResultSet("SELECT staff_id, Fname, Lname"
                                                + ", staff_type, staff_capacity"
                                                + " FROM Staff"
                                                + " WHERE staff_type = '" + staffTypeSelectionCB.getValue() + "'"
                                                );
            while (rs.next()) {
                data.add(new Staff(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getDouble(5)));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        //Populate the Table
        staffTable.setItems(data);
        setEditButtons();
    }
    
    //Fetch Rows from Database and set Table
    public void setAllTable() {
        //Get Complete Rows from Database
        try {
            ResultSet rs = database.getResultSet("SELECT staff_id, Fname, Lname"
                                                + ", staff_type, staff_capacity "
                                                + "FROM Staff"
                                                );
            while (rs.next()) {
                data.add(new Staff(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getDouble(5)));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public void setEditButtons() {
        // Edit Button
        editStaff.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Disposer.Record, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Disposer.Record, Boolean> p) {
                return new SimpleBooleanProperty(p.getValue() != null);
            }
        });

        editStaff.setCellFactory(
                new Callback<TableColumn<Disposer.Record, Boolean>, TableCell<Disposer.Record, Boolean>>() {

            @Override
            public TableCell<Disposer.Record, Boolean> call(TableColumn<Disposer.Record, Boolean> p) {
                return new ButtonCell();
            }

        });
        
    }
    
    public void clearFilters(ActionEvent event) {
        data.removeAll(data);
        staffTypeSelectionCB.setValue(staffTypeSelectionCB.getPromptText());
        
        setAllTable();
        
        //Populate the Table
        staffTable.setItems(data);
        setEditButtons();
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

    @FXML
    public void handleCourseBtn(MouseEvent event) throws IOException {
        pageSwitcher.switcher(event, "CourseTable.fxml");
    }
    
    @FXML
    public void handleCurrentAlloBtn(MouseEvent event) throws IOException {
        pageSwitcher.switcher(event, "AllocationTable.fxml");
    }
}
