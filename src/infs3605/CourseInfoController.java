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
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author SOPHIA
 */
public class CourseInfoController implements Initializable {

    @FXML
    Button back;

    @FXML
    Text courseCode;
    @FXML
    Text courseName;
    @FXML
    Text courseDesc;
    @FXML
    TextArea allocationNotes;
    @FXML
    ImageView t1Offer;
    @FXML
    ImageView t2Offer;
    @FXML
    ImageView t3Offer;
    @FXML
    ImageView tsOffer;
    @FXML
    TableView historyTable;
    @FXML
    Text repeatLectures;

    String courseID;

    CourseDetails course;
    CourseHistoricalAllocation history;
    Database database = new Database();

    ObservableList<CourseHistoricalAllocation> data = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void start(String courseID) {
        try {
            ResultSet rs = database.getResultSet("SELECT course_id, course_name"
                    + ", course_description, t1_offer, t2_offer, t3_offer"
                    + ", summer_Offer, allocation_notes"
                    + " FROM Courses"
                    + " WHERE course_id = '" + courseID + "'"
            );

            while (rs.next()) {
                course = new CourseDetails(rs.getString(1), rs.getString(2),
                         rs.getString(3), rs.getInt(4),
                         rs.getInt(5), rs.getInt(6),
                         rs.getInt(7), rs.getString(8));

                System.out.println("CourseId: " + courseID + " | ");

                courseCode.setText(course.getCourse_id());
                courseName.setText(course.getCourseName());
                courseDesc.setText(course.getCourseDesc());
                allocationNotes.setText(course.getAllocationNotes());

                //If course is availble in aterm show the green tick
                if (course.getT1Offer() == 1) {
                    t1Offer.setVisible(true);
                }
                if (course.getT2Offer() == 1) {
                    t2Offer.setVisible(true);
                }
                if (course.getT3Offer() == 1) {
                    t3Offer.setVisible(true);
                }
                if (course.getTsOffer() == 1) {
                    tsOffer.setVisible(true);
                }
            }
//            
            ResultSet a = database.getResultSet("SELECT repeat_lecture FROM Weighting");
            while (a.next()){
            }
            
            //Get Historical Allocations
            ResultSet ha = database.getResultSet(
                    "Select allocation_year "
                    + "|| replace(allocation_term, 'erm ', '')"
                    + ", Fname || ' ' || Lname "
                    + " FROM Allocation a JOIN Staff s"
                    + " ON a.staff_id = s.staff_id"
                    + " WHERE course_id = '" + courseID + "'"
                    + " AND allocation_year < strftime('%Y', date('now'))"
                    + " ORDER BY a.allocation_year DESC"
            );
            
            while (ha.next()) {
                data.add(new CourseHistoricalAllocation(ha.getString(1), ha.getString(2)));
            }
            
            TableColumn yearTerm = new TableColumn("DATE");
            TableColumn staffName = new TableColumn("STAFF NAME");
            historyTable.getColumns().addAll(yearTerm, staffName);
            yearTerm.setCellValueFactory(new PropertyValueFactory<Allocation, String>("yearTerm"));
            staffName.setCellValueFactory(new PropertyValueFactory<Allocation, String>("staffName"));
            historyTable.setItems(data);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    @FXML
    public void handleBackButton(ActionEvent event) throws IOException {
        Stage stage = (Stage) back.getScene().getWindow();
        stage.close();
    }

}
