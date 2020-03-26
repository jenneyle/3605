/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infs3605;

import java.io.IOException;
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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 *
 * @author Mashilan
 */
public class DisplayAllocationController implements Initializable {

    //  PageSwitcherHelper pageSwitcher = new PageSwitcherHelper();
    Database database = new Database();

    PageSwitchHelper pageSwitcher = new PageSwitchHelper();

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

        allocationlist.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("CourseInfo.fxml"));

                try {
                    fxmlLoader.load();
                } catch (IOException e) {
                }

                String courseId = allocationlist.getSelectionModel().getSelectedItem().getCourse_id();
                String courseName = "";
                int t1 = 0;
                int t2 = 0;
                int t3 = 0;
                int s = 0;

                try {
                    ResultSet rs = database.getResultSet("SELECT * FROM Courses WHERE course_id = '" + courseId + "'");
                    courseName = rs.getString(2);
                    t1 = rs.getInt(3);
                    t2 = rs.getInt(4);
                    t3 = rs.getInt(5);
                    s = rs.getInt(6);

                } catch (SQLException e) {
                    e.printStackTrace();
                }

                CourseInfoController courseInfoController = fxmlLoader.getController();
                courseInfoController.setData(courseId, courseName, t1, t2, t3, s);

                Parent p = fxmlLoader.getRoot();
                Stage stage = new Stage();
                stage.setTitle(" Course Info Page");
                stage.setScene(new Scene(p));
                stage.show();
            }
        });
    }

    private ObservableList<Allocation> getAllocationListData() {
        List<Allocation> allocationListToReturn = new ArrayList<>();
        try {
            //TODO: Rani to change
            ResultSet rs1 = database.getResultSet("SELECT allocation_year,allocation_term,Allocation.course_id,staff_id,(weighting_term+face_time+prep_dev) as weight FROM Allocation\n"
                    + "inner join Weighting on Allocation.course_id=Weighting.course_id and allocation_year=year and allocation_term=term\n"
                    + "GROUP by Allocation.course_id");
            while (rs1.next()) {
                allocationListToReturn.add(
                        new Allocation(rs1.getInt("allocation_year"), rs1.getString("allocation_term"), rs1.getString("course_id"), rs1.getString("staff_id"), rs1.getDouble("weight"))
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

    @FXML
    public void handleAllocateBtn(ActionEvent event) throws IOException {
        pageSwitcher.switcher(event, "StaffAllocation.fxml");

    }

    @FXML
    public void handleCourseInfoBtn(ActionEvent event) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("CourseInfo.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 410, 300);
            Stage stage = new Stage();
            stage.setTitle(" Course Info Page");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
