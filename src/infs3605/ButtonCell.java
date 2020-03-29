/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infs3605;

import com.sun.prism.impl.Disposer.Record;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author freey
 */
public class ButtonCell extends TableCell<Record, Boolean> {

    //TODO: Sophia to make the button with an image of a pencilnot text
    Button cellButton = new Button("Edit");
    Database database = new Database();

    ButtonCell() {

        //Action when the button is pressed
        cellButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                Allocation currentRow = (Allocation) ButtonCell.this.getTableView().getItems().get(ButtonCell.this.getIndex());
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("AllocationInfo.fxml"));

                try {
                    fxmlLoader.load();
                } catch (IOException e) {
                }

                String courseId = "";
                String courseName = "";
                String staffId = "";
                String staffName = "";
                String yearTerm = "";

                try {
                    ResultSet rs = database.getResultSet("SELECT Allocation.allocation_term, Allocation.allocation_year,"
                            + " Allocation.course_id, Allocation.staff_id, Courses.course_name, Staff.Fname, Staff.Lname"
                            + " FROM Staff JOIN Allocation ON Staff.staff_id = Allocation.staff_id JOIN Courses ON"
                            + " Courses.course_id = Allocation.course_id WHERE allocation_id =" + currentRow.getId());
                    System.out.println("hi");
                    courseId = rs.getString(3);
                    courseName = rs.getString(5);
                    staffId = rs.getString(4);
                    staffName = rs.getString(6) + " " + rs.getString(7);
                    yearTerm = rs.getString(1) + " " + rs.getString(2);

                } catch (SQLException e) {
                    e.printStackTrace();
                }

                AllocationInfoController allocationInfoController = fxmlLoader.getController();
                allocationInfoController.setData(courseId, courseName, staffId, staffName, yearTerm);

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
