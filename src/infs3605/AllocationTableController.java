/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infs3605;

import com.sun.prism.impl.Disposer.Record;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
    TableView allocationTable;

    Database database = new Database();
    PageSwitchHelper pageSwitcher = new PageSwitchHelper();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        TableColumn year = new TableColumn("YEAR");
        TableColumn term = new TableColumn("TERM");
        TableColumn courseId = new TableColumn("COURSE ID");
        TableColumn staffId = new TableColumn("STAFF ID");
        TableColumn editAllocation = new TableColumn("EDIT");

        allocationTable.getColumns().addAll(year, term, courseId, staffId, editAllocation);

        ObservableList<Allocation> data = FXCollections.observableArrayList();

        //TODO: Rani to edit
        try {
            ResultSet rs = database.getResultSet("SELECT * FROM Allocation ORDER BY allocation_year, allocation_term");
            while (rs.next()) {
                data.add(new Allocation(rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5)));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        year.setCellValueFactory(new PropertyValueFactory<Allocation, Integer>("Year"));
        term.setCellValueFactory(new PropertyValueFactory<Allocation, String>("Term"));
        courseId.setCellValueFactory(new PropertyValueFactory<Allocation, String>("Course_id"));
        staffId.setCellValueFactory(new PropertyValueFactory<Allocation, String>("Staff_id"));
        editAllocation.setCellValueFactory(new PropertyValueFactory<Allocation, String>("editButton"));

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
        allocationTable.setItems(data);

    }
    
      //button to allocate staff to course
    @FXML
    public void handleAllocateBtn(ActionEvent event) throws IOException {
        pageSwitcher.switcher(event, "StaffAllocation.fxml");
    }
}

