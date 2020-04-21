/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infs3605;

import com.sun.prism.impl.Disposer.Record;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Cell;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * FXML Controller class
 *
 * @author freey
 */
public class AllocationTableController implements Initializable {

    @FXML
    public TableView allocationTable;
    @FXML
    TableColumn editAllocation;
    @FXML
    TableColumn deleteAllocation;
    @FXML
    TableColumn viewDetailsAllocation;
    @FXML
    public ComboBox yearSelectionCB;
    @FXML
    public ComboBox termSelectionCB;
    @FXML
    public ComboBox courseSelectionCB;
    @FXML
    public Button clearFilterBtn;
    @FXML
    public Button exportToExcelBtn;
    @FXML
    public Button importbtn;
    Database database = new Database();
    PageSwitchHelper pageSwitcher = new PageSwitchHelper();

    static ObservableList<Allocation> data = FXCollections.observableArrayList();
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
        TableColumn weighting = new TableColumn("WEIGHTING");
        TableColumn staffName = new TableColumn("STAFF ALLOCATED");
        TableColumn warning1 = new TableColumn("Warning");
        TableColumn warning2 = new TableColumn("Warning");
        editAllocation = new TableColumn("");
        deleteAllocation = new TableColumn("");
        viewDetailsAllocation = new TableColumn("");

        //Add columns to tableview
        allocationTable.getColumns().addAll(year, term, courseId, weighting
                , staffName, warning1, warning2, viewDetailsAllocation, editAllocation, deleteAllocation);
        warning1.setVisible(false);
        warning2.setVisible(false);
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

        setAllTable();

        //Based on Allocation.class, populate the cells of the table
        allocationID.setCellValueFactory(new PropertyValueFactory<Allocation, Integer>("allocation_id"));
        allocationID.setVisible(false);
        year.setCellValueFactory(new PropertyValueFactory<Allocation, Integer>("Year"));
        term.setCellValueFactory(new PropertyValueFactory<Allocation, String>("Term"));
        courseId.setCellValueFactory(new PropertyValueFactory<Allocation, String>("Course_id"));
        weighting.setCellValueFactory(new PropertyValueFactory<Allocation, Integer>("Weight"));
        staffName.setCellValueFactory(new PropertyValueFactory<Allocation, String>("Staff_name"));
        warning1.setCellValueFactory(new PropertyValueFactory<Allocation, String>("warning1"));
        warning2.setCellValueFactory(new PropertyValueFactory<Allocation, String>("warning2"));
        editAllocation.setCellValueFactory(new PropertyValueFactory<Allocation, String>("editButton"));
        deleteAllocation.setCellValueFactory(new PropertyValueFactory<Allocation, String>("deleteButton"));
        viewDetailsAllocation.setCellValueFactory(new PropertyValueFactory<Allocation, String>("detailsButton"));

        setEditButtons();
        setDeleteButtons();
        setDetailsButtons();
        //Populate the Table
        allocationTable.setItems(data);
    }

    //Fetch Rows from Database and set Table
    public void setAllTable() {
        //Get Complete Rows from Database
        data.clear();
        try {
            ResultSet rs = database.getResultSet("SELECT DISTINCT a.allocation_id"
                    + ", a.course_id, a.allocation_year"
                    //                    + ", a.allocation_term, ROUND(w.weighting_term,2"
                    + ", a.allocation_term, w.weighting_term"
                    + ", s.Fname || ' ' || s.Lname AS 'staff_name'"
                    + " FROM Allocation a"
                    + " JOIN Staff s"
                    + " ON s.staff_id = a.staff_id"
                    + " LEFT OUTER JOIN Weighting w"
                    + " ON a.course_id = w.course_id"
                    + " AND a.allocation_year = w.Year"
                    + " AND a.allocation_term = w.Term"
            );
            while (rs.next()) {
                data.add(new Allocation(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), Math.round((rs.getDouble(5)) * 10.0) / 10.0, rs.getString(6)));
            }
            ResultSet rs1 = database.getResultSet("SELECT w.course_id, year, term, weighting_term,allocation_id\n"
                    + "FROM Weighting w \n"
                    + "left JOIN Allocation a\n"
                    + "on w.course_id=a.course_id and year=allocation_year and term=allocation_term\n"
                    + "where allocation_id is NULL");
            while (rs1.next()) {
                data.add(new Allocation(0, rs1.getString(1), rs1.getInt(2), rs1.getString(3), Math.round((rs1.getDouble(4)) * 10.0) / 10.0, ""));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
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
                return new AllocationButtonCell();
            }

        });

    }
    //delete buttons

    public void setDeleteButtons() {
        // Edit Button
        deleteAllocation.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Record, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Record, Boolean> p) {
                return new SimpleBooleanProperty(p.getValue() != null);
            }
        });

        deleteAllocation.setCellFactory(
                new Callback<TableColumn<Record, Boolean>, TableCell<Record, Boolean>>() {

            @Override
            public TableCell<Record, Boolean> call(TableColumn<Record, Boolean> p) {
                return new AllocationDeleteButtonCell();
            }

        });

    }

    public void setDetailsButtons() {
        // Edit Button
        viewDetailsAllocation.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Record, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Record, Boolean> p) {
                return new SimpleBooleanProperty(p.getValue() != null);
            }
        });

        viewDetailsAllocation.setCellFactory(
                new Callback<TableColumn<Record, Boolean>, TableCell<Record, Boolean>>() {

            @Override
            public TableCell<Record, Boolean> call(TableColumn<Record, Boolean> p) {
                return new AllocationDetailButtonCell();
            }

        });

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

    @FXML
    public void selectYearComboBox(ActionEvent event) {
        data.removeAll(data);

        //Determine if other fields are null
        boolean termSelected = (termSelectionCB.getValue() != null)
                && (termSelectionCB.getValue() != termSelectionCB.getPromptText());
        boolean courseSelected = (courseSelectionCB.getValue() != null)
                && (courseSelectionCB.getValue() != courseSelectionCB.getPromptText());

        if (termSelected == false && courseSelected == false) {
            termSelectionCB.setValue(termSelectionCB.getPromptText());
            courseSelectionCB.setValue(courseSelectionCB.getPromptText());

            //Get Complete Rows from Database
            try {
                ResultSet rs = database.getResultSet("SELECT DISTINCT a.allocation_id"
                        + ", a.course_id, a.allocation_year"
                        + ", a.allocation_term, w.weighting_term"
                        + ", s.Fname || ' ' || s.Lname AS 'staff_name'"
                        + " FROM Allocation a"
                        + " JOIN Staff s"
                        + " ON s.staff_id = a.staff_id"
                        + " LEFT OUTER JOIN Weighting w"
                        + " ON a.course_id = w.course_id"
                        + " AND a.allocation_year = w.Year"
                        + " AND a.allocation_term = w.Term"
                        + " WHERE a.allocation_year = " + yearSelectionCB.getValue()
                );

                while (rs.next()) {
                    data.add(new Allocation(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), Math.round((rs.getDouble(5)) * 10.0) / 10.0, rs.getString(6)));
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        } else if (termSelected == true && courseSelected == false) {
            courseSelectionCB.setValue(courseSelectionCB.getPromptText());

            //Get Complete Rows from Database
            try {
                ResultSet rs = database.getResultSet("SELECT DISTINCT a.allocation_id"
                        + ", a.course_id, a.allocation_year"
                        + ", a.allocation_term, w.weighting_term"
                        + ", s.Fname || ' ' || s.Lname AS 'staff_name'"
                        + " FROM Allocation a"
                        + " JOIN Staff s"
                        + " ON s.staff_id = a.staff_id"
                        + " LEFT OUTER JOIN Weighting w"
                        + " ON a.course_id = w.course_id"
                        + " AND a.allocation_year = w.Year"
                        + " AND a.allocation_term = w.Term"
                        + " WHERE a.allocation_year = " + yearSelectionCB.getValue()
                        + " AND a.allocation_term = '" + termSelectionCB.getValue() + "'"
                );

                while (rs.next()) {
                    data.add(new Allocation(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), Math.round((rs.getDouble(5)) * 10.0) / 10.0, rs.getString(6)));
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        } else if (termSelected == false && courseSelected == true) {
            termSelectionCB.setValue(termSelectionCB.getPromptText());

            //Get Complete Rows from Database
            try {
                ResultSet rs = database.getResultSet("SELECT DISTINCT a.allocation_id"
                        + ", a.course_id, a.allocation_year"
                        + ", a.allocation_term, w.weighting_term"
                        + ", s.Fname || ' ' || s.Lname AS 'staff_name'"
                        + " FROM Allocation a"
                        + " JOIN Staff s"
                        + " ON s.staff_id = a.staff_id"
                        + " LEFT OUTER JOIN Weighting w"
                        + " ON a.course_id = w.course_id"
                        + " AND a.allocation_year = w.Year"
                        + " AND a.allocation_term = w.Term"
                        + " WHERE a.allocation_year = " + yearSelectionCB.getValue()
                        + " AND course_id = '" + courseSelectionCB.getValue() + "'"
                );

                while (rs.next()) {
                    data.add(new Allocation(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), Math.round((rs.getDouble(5)) * 10.0) / 10.0, rs.getString(6)));
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else {
            //Get Complete Rows from Database
            try {
                ResultSet rs = database.getResultSet("SELECT DISTINCT a.allocation_id"
                        + ", a.course_id, a.allocation_year"
                        + ", a.allocation_term, w.weighting_term"
                        + ", s.Fname || ' ' || s.Lname AS 'staff_name'"
                        + " FROM Allocation a"
                        + " JOIN Staff s"
                        + " ON s.staff_id = a.staff_id"
                        + " LEFT OUTER JOIN Weighting w"
                        + " ON a.course_id = w.course_id"
                        + " AND a.allocation_year = w.Year"
                        + " AND a.allocation_term = w.Term"
                        + " WHERE a.allocation_year = " + yearSelectionCB.getValue()
                        + " AND allocation_term = '" + termSelectionCB.getValue() + "'"
                        + " AND course_id = '" + courseSelectionCB.getValue() + "'"
                );
                while (rs.next()) {
                    data.add(new Allocation(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), Math.round((rs.getDouble(5)) * 10.0) / 10.0, rs.getString(6)));
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        }

        //Populate the Table
        allocationTable.setItems(data);
        setEditButtons();
        setDeleteButtons();
        setDetailsButtons();
    }

    @FXML
    public void selectTermComboBox(ActionEvent event) {
        data.removeAll(data);

        //Determine if other fields are null
        boolean yearSelected = (yearSelectionCB.getValue() != null)
                && (yearSelectionCB.getValue() != yearSelectionCB.getPromptText());
        boolean courseSelected = (courseSelectionCB.getValue() != null)
                && (courseSelectionCB.getValue() != courseSelectionCB.getPromptText());

        if (yearSelected == false && courseSelected == false) {
            yearSelectionCB.setValue(yearSelectionCB.getPromptText());
            courseSelectionCB.setValue(courseSelectionCB.getPromptText());

            //Get Complete Rows from Database
            try {
                ResultSet rs = database.getResultSet("SELECT DISTINCT a.allocation_id"
                        + ", a.course_id, a.allocation_year"
                        + ", a.allocation_term, w.weighting_term"
                        + ", s.Fname || ' ' || s.Lname AS 'staff_name'"
                        + " FROM Allocation a"
                        + " JOIN Staff s"
                        + " ON s.staff_id = a.staff_id"
                        + " LEFT OUTER JOIN Weighting w"
                        + " ON a.course_id = w.course_id"
                        + " AND a.allocation_year = w.Year"
                        + " AND a.allocation_term = w.Term"
                        + " WHERE a.allocation_term = '" + termSelectionCB.getValue() + "'"
                );
                while (rs.next()) {
                    data.add(new Allocation(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), Math.round((rs.getDouble(5)) * 10.0) / 10.0, rs.getString(6)));
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        } else if (yearSelected == true && courseSelected == false) {
            courseSelectionCB.setValue(courseSelectionCB.getPromptText());

            //Get Complete Rows from Database
            try {
                ResultSet rs = database.getResultSet("SELECT DISTINCT a.allocation_id"
                        + ", a.course_id, a.allocation_year"
                        + ", a.allocation_term, w.weighting_term"
                        + ", s.Fname || ' ' || s.Lname AS 'staff_name'"
                        + " FROM Allocation a"
                        + " JOIN Staff s"
                        + " ON s.staff_id = a.staff_id"
                        + " LEFT OUTER JOIN Weighting w"
                        + " ON a.course_id = w.course_id"
                        + " AND a.allocation_year = w.Year"
                        + " AND a.allocation_term = w.Term"
                        + " WHERE a.allocation_term = '" + termSelectionCB.getValue() + "'"
                        + " AND a.allocation_year = " + yearSelectionCB.getValue()
                );
                while (rs.next()) {
                    data.add(new Allocation(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), Math.round((rs.getDouble(5)) * 10.0) / 10.0, rs.getString(6)));
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        } else if (yearSelected == false && courseSelected == true) {
            termSelectionCB.setValue(termSelectionCB.getPromptText());

            //Get Complete Rows from Database
            try {
                ResultSet rs = database.getResultSet("SELECT DISTINCT a.allocation_id"
                        + ", a.course_id, a.allocation_year"
                        + ", a.allocation_term, w.weighting_term"
                        + ", s.Fname || ' ' || s.Lname AS 'staff_name'"
                        + " FROM Allocation a"
                        + " JOIN Staff s"
                        + " ON s.staff_id = a.staff_id"
                        + " LEFT OUTER JOIN Weighting w"
                        + " ON a.course_id = w.course_id"
                        + " AND a.allocation_year = w.Year"
                        + " AND a.allocation_term = w.Term"
                        + " WHERE a.allocation_term = '" + termSelectionCB.getValue() + "'"
                        + " AND course_id = '" + courseSelectionCB.getValue() + "'"
                );
                while (rs.next()) {
                    data.add(new Allocation(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), Math.round((rs.getDouble(5)) * 10.0) / 10.0, rs.getString(6)));
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else {
            //Get Complete Rows from Database
            try {
                ResultSet rs = database.getResultSet("SELECT DISTINCT a.allocation_id"
                        + ", a.course_id, a.allocation_year"
                        + ", a.allocation_term, w.weighting_term"
                        + ", s.Fname || ' ' || s.Lname AS 'staff_name'"
                        + " FROM Allocation a"
                        + " JOIN Staff s"
                        + " ON s.staff_id = a.staff_id"
                        + " LEFT OUTER JOIN Weighting w"
                        + " ON a.course_id = w.course_id"
                        + " AND a.allocation_year = w.Year"
                        + " AND a.allocation_term = w.Term" + " WHERE a.allocation_term = '" + termSelectionCB.getValue() + "'"
                        + " AND allocation_year = " + yearSelectionCB.getValue()
                        + " AND course_id = '" + courseSelectionCB.getValue() + "'"
                );
                while (rs.next()) {
                    data.add(new Allocation(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), Math.round((rs.getDouble(5)) * 10.0) / 10.0, rs.getString(6)));
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        }

        //Populate the Table
        allocationTable.setItems(data);
        setEditButtons();
        setDeleteButtons();
        setDetailsButtons();
    }

    @FXML
    public void selectCourseComboBox(ActionEvent event) {
        data.removeAll(data);
        //Determine if other fields are null
        boolean yearSelected = (yearSelectionCB.getValue() != null)
                && (yearSelectionCB.getValue() != yearSelectionCB.getPromptText());
        boolean termSelected = (termSelectionCB.getValue() != null)
                && (termSelectionCB.getValue() != termSelectionCB.getPromptText());

        if (yearSelected == false && termSelected == false) {
            yearSelectionCB.setValue(yearSelectionCB.getPromptText());
            termSelectionCB.setValue(termSelectionCB.getPromptText());

            //Get Complete Rows from Database
            try {
                ResultSet rs = database.getResultSet("SELECT DISTINCT a.allocation_id"
                        + ", a.course_id, a.allocation_year"
                        + ", a.allocation_term, w.weighting_term"
                        + ", s.Fname || ' ' || s.Lname AS 'staff_name'"
                        + " FROM Allocation a"
                        + " JOIN Staff s"
                        + " ON s.staff_id = a.staff_id"
                        + " LEFT OUTER JOIN Weighting w"
                        + " ON a.course_id = w.course_id"
                        + " AND a.allocation_year = w.Year"
                        + " AND a.allocation_term = w.Term"
                        + " WHERE a.course_id = '" + courseSelectionCB.getValue() + "'"
                );
                while (rs.next()) {
                    data.add(new Allocation(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), Math.round((rs.getDouble(5)) * 10.0) / 10.0, rs.getString(6)));
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        } else if (yearSelected == true && termSelected == false) {
            termSelectionCB.setValue(termSelectionCB.getPromptText());

            //Get Complete Rows from Database
            try {
                ResultSet rs = database.getResultSet("SELECT DISTINCT a.allocation_id"
                        + ", a.course_id, a.allocation_year"
                        + ", a.allocation_term, w.weighting_term"
                        + ", s.Fname || ' ' || s.Lname AS 'staff_name'"
                        + " FROM Allocation a"
                        + " JOIN Staff s"
                        + " ON s.staff_id = a.staff_id"
                        + " LEFT OUTER JOIN Weighting w"
                        + " ON a.course_id = w.course_id"
                        + " AND a.allocation_year = w.Year"
                        + " AND a.allocation_term = w.Term"
                        + " WHERE a.course_id = '" + courseSelectionCB.getValue() + "'"
                        + " AND a.allocation_year = " + yearSelectionCB.getValue()
                );
                while (rs.next()) {
                    data.add(new Allocation(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), Math.round((rs.getDouble(5)) * 10.0) / 10.0, rs.getString(6)));
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        } else if (yearSelected == false && termSelected == true) {
            termSelectionCB.setValue(termSelectionCB.getPromptText());

            //Get Complete Rows from Database
            try {
                ResultSet rs = database.getResultSet("SELECT DISTINCT a.allocation_id"
                        + ", a.course_id, a.allocation_year"
                        + ", a.allocation_term, w.weighting_term"
                        + ", s.Fname || ' ' || s.Lname AS 'staff_name'"
                        + " FROM Allocation a"
                        + " JOIN Staff s"
                        + " ON s.staff_id = a.staff_id"
                        + " LEFT OUTER JOIN Weighting w"
                        + " ON a.course_id = w.course_id"
                        + " AND a.allocation_year = w.Year"
                        + " AND a.allocation_term = w.Term"
                        + " WHERE a.course_id = '" + courseSelectionCB.getValue() + "'"
                        + " AND a.allocation_term = '" + termSelectionCB.getValue() + "'"
                );
                while (rs.next()) {
                    data.add(new Allocation(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), Math.round((rs.getDouble(5)) * 10.0) / 10.0, rs.getString(6)));
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else {
            //Get Complete Rows from Database
            try {
                ResultSet rs = database.getResultSet("SELECT DISTINCT a.allocation_id"
                        + ", a.course_id, a.allocation_year"
                        + ", a.allocation_term, w.weighting_term"
                        + ", s.Fname || ' ' || s.Lname AS 'staff_name'"
                        //+ ",a.warning1,a.warning2"
                        + " FROM Allocation a"
                        + " JOIN Staff s"
                        + " ON s.staff_id = a.staff_id"
                        + " LEFT OUTER JOIN Weighting w"
                        + " ON a.course_id = w.course_id"
                        + " AND a.allocation_year = w.Year"
                        + " AND a.allocation_term = w.Term"
                        + " WHERE a.course_id = '" + courseSelectionCB.getValue() + "'"
                        + " AND allocation_year = " + yearSelectionCB.getValue()
                        + " AND allocation_term = '" + termSelectionCB.getValue() + "'"
                );
                while (rs.next()) {
                    data.add(new Allocation(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), Math.round((rs.getDouble(5)) * 10.0) / 10.0, rs.getString(6)));
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        }

        //Populate the Table
        allocationTable.setItems(data);
        setEditButtons();
        setDeleteButtons();
        setDetailsButtons();
    }

    public void handleExportBtn(ActionEvent event) {
        try {
            ResultSet rs = database.getResultSet("SELECT * FROM Allocation");

            Workbook wb = new XSSFWorkbook();
            Sheet sheet = wb.createSheet("Current Allocations");
            org.apache.poi.ss.usermodel.Font headerFont = wb.createFont();
            headerFont.setBold(true);

            CellStyle headerCellStyle = wb.createCellStyle();
            headerCellStyle.setFont(headerFont);

            Row headerRow = sheet.createRow(0);

            headerRow.createCell(1).setCellValue("Year");
            headerRow.createCell(2).setCellValue("Term");
            headerRow.createCell(3).setCellValue("Course Code");
            headerRow.createCell(4).setCellValue("Staff ID");

//                XSSFFont font = wb.createFont();
//                font.setBold(true);
//                XSSFCellStyle style = header.getRowStyle();
//                style.setFont(font);
//               // header.getRowStyle().getFont().setBold(true);
//                
            int index = 1;

            while (rs.next()) {
                Row row = sheet.createRow(index);
                row.createCell(0).setCellValue(rs.getString(1));
                row.createCell(1).setCellValue(rs.getString(2));
                row.createCell(2).setCellValue(rs.getString(3));
                row.createCell(3).setCellValue(rs.getString(4));
                row.createCell(4).setCellValue(rs.getString(5));

                index++;
            }

            FileOutputStream fileOut = new FileOutputStream("Current Allocations.xlsx");

            wb.write(fileOut);
            fileOut.close();

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Export Download");
            alert.setHeaderText(null);
            alert.setContentText("Export to excel spreadsheet is complete!");
            alert.showAndWait();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void clearFilters(ActionEvent event) {
        data.removeAll(data);
        termSelectionCB.setValue(termSelectionCB.getPromptText());
        yearSelectionCB.setValue(yearSelectionCB.getPromptText());
        courseSelectionCB.setValue(courseSelectionCB.getPromptText());

        setAllTable();

        //Populate the Table
        allocationTable.setItems(data);
        setEditButtons();
        setDeleteButtons();
        setDetailsButtons();
    }
    public void handleimport(ActionEvent event){
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        FileChooser fileChooser =new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(stage);
        try {
            Importing_excel_allocationtable.reading_excel(selectedFile);
        } catch (IOException ex) {
            Logger.getLogger(AllocationTableController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
