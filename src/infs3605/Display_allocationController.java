/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infs3605;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author Mashilan
 */
public class Display_allocationController implements Initializable {

    //  PageSwitcherHelper pageSwitcher = new PageSwitcherHelper();
    Database database = new Database();

    @FXML
    private TableView<Allocation> allocationlist;
    @FXML
    private TableColumn<Allocation, Integer> year;
    @FXML
    private TableColumn<Allocation, String> term;
    @FXML
    private TableColumn<Allocation, String> course_id;
    @FXML
    private TableColumn<Allocation, String> staff_id;
    @FXML
    private TableColumn<Allocation, Double> weight;
    @FXML
    private TableColumn<Allocation, String> allocateStaffCol;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        year.setCellValueFactory(new PropertyValueFactory<Allocation, Integer>("Year"));
        term.setCellValueFactory(new PropertyValueFactory<Allocation, String>("Term"));
        course_id.setCellValueFactory(new PropertyValueFactory<Allocation, String>("Course_id"));
        staff_id.setCellValueFactory(new PropertyValueFactory<Allocation, String>("Staff_id"));
        weight.setCellValueFactory(new PropertyValueFactory<Allocation, Double>("Weight"));

        allocateStaffCol = new TableColumn("Action");
        allocateStaffCol.setCellValueFactory(new PropertyValueFactory<Allocation, String>("button"));
        allocationlist.setItems(this.getAllocationListData());
    }

    private ObservableList<Allocation> getAllocationListData() {
        List<Allocation> allocationListToReturn = new ArrayList<>();
        try {
            ResultSet rs = database.getResultSet("SELECT year,term,course_id,staff_id,sum(percentage*value) as weight FROM Allocation \n"
                    + "inner join Course_Weight on Allocation.allocation_id=Course_Weight.allocation_id\n"
                    + "GROUP by Allocation.allocation_id");
            while (rs.next()) {
                allocationListToReturn.add(
                        new Allocation(rs.getInt("year"), rs.getString("term"), rs.getString("course_id"), rs.getString("staff_id"), rs.getDouble("weight"))
                );
                //System.out.println(rs.getDouble("weight"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        System.out.println(allocationListToReturn);
        //System.out.println(allocationListToReturn.get(0).getWeight());
        return FXCollections.observableArrayList(allocationListToReturn);
    }
    }
