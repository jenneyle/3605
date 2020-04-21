
import com.sun.prism.impl.Disposer;
import infs3605.Database;
import infs3605.EditWeightingController;
import infs3605.Weighting;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.stage.Stage;

///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package infs3605;
//
//import com.sun.prism.impl.Disposer;
//import java.io.IOException;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.control.TableCell;
//import javafx.stage.Stage;
//
///**
// *
// * @author jenneyle
// */
//public class WeightingEditButtonCell extends TableCell<Disposer.Record, Boolean> {
//
//    Button cellButton = new Button("Edit");
//    Database database = new Database();
//
//    WeightingEditButtonCell() {
//        System.out.println("here");
//        cellButton.setOnAction(new EventHandler<ActionEvent>() {
//
//            @Override
//            public void handle(ActionEvent t) {
//                //change this
//                Weighting currentRow = (Weighting) WeightingEditButtonCell.this.getTableView().getItems().get(WeightingEditButtonCell.this.getIndex());
//
//                FXMLLoader fxmlLoader = new FXMLLoader();
//                fxmlLoader.setLocation(getClass().getResource("EditWeighting.fxml"));
//                try {
//                    fxmlLoader.load();
//                } catch (IOException e) {
//                }
//
//                int weightingId = 0;
//                String courseId = "";
//                   int year = 0;
//                String term = "";
//                int weighting = 0;
//                int students = 0;
//                int tutehrs = 0;
//                int lecturehrs = 0;
//                int consulthrs = 0;
//                int markinghrs = 0;
//                int tuteprep = 0;
//                int lectureprep = 0;
//                int staffdev = 0;
//
//                try {
//                    //change database
//                    ResultSet rs = database.getResultSet("SELECT weighting_id, course_id, Year, Term, students_enrolled, weighting_term, tutorial_hrs, lecture_hrs, consultation_hrs, marking_hrs, tutorial,prep, lecture_prep, staff_development"
//                            + " FROM Weighting"
//                            + " WHERE weighting_id =" + currentRow.getWeight_id());
//                    System.out.println("hi");
//                    weightingId = rs.getInt(1);
//                    courseId = rs.getString(2);
//                    year = rs.getInt(3);
//                    term = rs.getString(4);
//                    students = rs.getInt(5);
//                    weighting = rs.getInt(6);
//                    tutehrs = rs.getInt(7);
//                    lecturehrs = rs.getInt(8);
//                    consulthrs = rs.getInt(9);
//                    markinghrs = rs.getInt(10);
//                    tuteprep = rs.getInt(11);
//                    lectureprep = rs.getInt(12);
//                    staffdev = rs.getInt(13);
//
//                    EditWeightingController editWeightingController = fxmlLoader.getController();
//                    editWeightingController.setData(weightingId, courseId, year, term, students, weighting, tutehrs, lecturehrs, consulthrs, markinghrs, tuteprep, lectureprep, staffdev);
//
//                    Parent p = fxmlLoader.getRoot();
//                    Stage stage = new Stage();
//                    stage.setTitle("Weighting Edit Page");
//                    stage.setScene(new Scene(p));
//                    stage.show();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        });
//    }
//
//}
public class WeightingEditButtonCell extends TableCell<Disposer.Record, Boolean> {

    //TODO: Sophia to make the button with an image of a pencilnot text
    Button cellButton = new Button("Edit");
    Database database = new Database();
    
    WeightingEditButtonCell() {

        //Action when the button is pressed
        cellButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                Weighting currentRow = (Weighting) WeightingEditButtonCell.this.getTableView().getItems().get(WeightingEditButtonCell.this.getIndex());
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("EditWeighting.fxml"));

                try {
                    fxmlLoader.load();
                } catch (IOException e) {
                }
            

                EditWeightingController editWeightingController = fxmlLoader.getController();
                editWeightingController.setData(currentRow.getWeight_id());

                Parent p = fxmlLoader.getRoot();
                Stage stage = new Stage();
                stage.setTitle("Allocation Info Page");
                stage.setScene(new Scene(p));
                stage.show();
            }
        });
    }

    //Display button if the row is not empty
    @Override
    protected void updateItem(Boolean t, boolean empty) {
        super.updateItem(t, empty);
        if (!empty) {
            setGraphic(cellButton);
        }
    }
}
