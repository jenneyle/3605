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
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
    TableColumn detailsStaff;
    TableColumn deleteStaff;
    @FXML
    public ComboBox staffTypeSelectionCB;
    @FXML
    public TextField filterField;
    @FXML
    public Button clearFilterBtn;

    Database database = new Database();
    PageSwitchHelper pageSwitcher = new PageSwitchHelper();

   static ObservableList<Staff> data = FXCollections.observableArrayList();
    SortedList<Staff> sortedData;
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
        TableColumn leftover = new TableColumn("LEFTOVER CAPACITY");
        editStaff = new TableColumn("");
        detailsStaff = new TableColumn("");
        deleteStaff = new TableColumn("");

        //Add columns to tableview
        staffTable.getColumns().addAll(id, fName, lName, type, capacity, leftover
                , detailsStaff, editStaff, deleteStaff);

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
        leftover.setCellValueFactory(new PropertyValueFactory<Staff, Double>("leftoverCapacity"));
        editStaff.setCellValueFactory(new PropertyValueFactory<Staff, String>("editButton"));
        detailsStaff.setCellValueFactory(new PropertyValueFactory<Staff, String>("detailsButton"));
        deleteStaff.setCellValueFactory(new PropertyValueFactory<Staff, String>("deleteButton"));

        setSearchField();

        setEditButtons();
        setDetailButtons();
        setDeleteButtons();

    }

    //https://stackoverflow.com/questions/44317837/create-search-textfield-field-to-search-in-a-javafx-tableview
    public void setSearchField() {
        //1.Set Search Field
        FilteredList<Staff> filteredData = new FilteredList<>(data, p -> true);
        // 2. Set the filter Predicate whenever the filter changes.
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(Staff -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name field in your object with filter.
                String lowerCaseFilter = newValue.toLowerCase();

                if (String.valueOf(Staff.getFirstName()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                    // Filter matches first name.

                } else if (String.valueOf(Staff.getLastName()).toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                }

                return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList. 
        sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(staffTable.comparatorProperty());
        // 5. Add sorted (and filtered) data to the table.
        staffTable.setItems(sortedData);

    }

    @FXML
    public void selectStaffType() {
        data.removeAll(data);

        //Get Complete Rows from Database
        try {
            ResultSet rs = database.getResultSet("SELECT s.staff_id, Fname, Lname"
                    + ", staff_type, staff_capacity"
                    + ", (staff_capacity - SUM(allocation_weight)) "
                    + "FROM Staff s LEFT OUTER JOIN Allocation a "
                    + "ON s.staff_id = a.staff_id "
                    + "WHERE allocation_year = strftime('%Y', date('now')) "
                    + "GROUP BY s.staff_id "
                    + "UNION "
                    + "SELECT staff_id, Fname, Lname, staff_type, staff_capacity"
                    + ", '' AS Leftover FROM Staff"
                    + " WHERE staff_type = '" + staffTypeSelectionCB.getValue() + "'"
            );
            while (rs.next()) {
                data.add(new Staff(rs.getString(1), rs.getString(2)
                        , rs.getString(3), rs.getString(4), rs.getDouble(5)
                        , rs.getString(6)));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        //Populate the Table
        staffTable.setItems(data);
        setEditButtons();
        setDetailButtons();
        setDeleteButtons();
    }

    //Fetch Rows from Database and set Table
    public void setAllTable() {
        //Get Complete Rows from Database
        try {
            ResultSet rs = database.getResultSet("SELECT s.staff_id, Fname, Lname"
                    + ", staff_type, staff_capacity"
                    + ", (staff_capacity - SUM(allocation_weight)) "
                    + "FROM Staff s LEFT OUTER JOIN Allocation a "
                    + "ON s.staff_id = a.staff_id "
                    + "WHERE allocation_year = strftime('%Y', date('now')) "
                    + "GROUP BY s.staff_id "
                    + "UNION "
                    + "SELECT staff_id, Fname, Lname, staff_type, staff_capacity"
                    + ", '' AS Leftover FROM Staff"
            );
            while (rs.next()) {
                data.add(new Staff(rs.getString(1), rs.getString(2)
                        , rs.getString(3), rs.getString(4), rs.getDouble(5)
                        , rs.getString(6)));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        setSearchField();

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
                return new StaffButtonCell();
            }
        });
    }
    
     public void setDeleteButtons() {
        // Edit Button
        deleteStaff.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Disposer.Record, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Disposer.Record, Boolean> p) {
                return new SimpleBooleanProperty(p.getValue() != null);
            }
        });
        deleteStaff.setCellFactory(
                new Callback<TableColumn<Disposer.Record, Boolean>, TableCell<Disposer.Record, Boolean>>() {

            @Override
            public TableCell<Disposer.Record, Boolean> call(TableColumn<Disposer.Record, Boolean> p) {
                return new StaffDeleteButtonCell();
            }
        });
    }

    public void setDetailButtons() {
        detailsStaff.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Disposer.Record, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Disposer.Record, Boolean> p) {
                return new SimpleBooleanProperty(p.getValue() != null);
            }
        });
        detailsStaff.setCellFactory(
                new Callback<TableColumn<Disposer.Record, Boolean>, TableCell<Disposer.Record, Boolean>>() {

            @Override
            public TableCell<Disposer.Record, Boolean> call(TableColumn<Disposer.Record, Boolean> p) {
                return new StaffDetailButtonCell();
            }
        });
    }

    public void clearFilters(ActionEvent event) {
        data.removeAll();
        staffTypeSelectionCB.setValue(staffTypeSelectionCB.getPromptText());

        setAllTable();

        //Populate the Table
        staffTable.setItems(sortedData);

        filterField.setText("");
        filterField.setPromptText(filterField.getPromptText());
        setEditButtons();
        setDetailButtons();
        setDeleteButtons();
    }

    public void handleInsertStaffBtn(ActionEvent event) throws IOException {
        pageSwitcher.switcher(event, "InsertStaff.fxml");
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
