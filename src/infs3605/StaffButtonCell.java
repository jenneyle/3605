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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 *
 * @author freey
 */
public class StaffButtonCell extends TableCell<Record, Boolean> {

    //TODO: Sophia to make the button with an image of a pencilnot text
  Image pencilImage = new Image(this.getClass().getResourceAsStream("/resources/Pencil1.png"));
    
   //Image newimg = pencilImage.
    
    
     Button cellButton = new Button("", new ImageView(pencilImage));
      
     Database database = new Database();

//    AllocationDeleteButtonCell() {
//
//        //Action when the button is pressed
//        cellButton.setOnAction(new EventHandler<ActionEvent>() {
//
//            @Override
//            public void handle(ActionEvent t) {
//                Allocation currentRow = (Allocation) AllocationButtonCell.this.getTableView().getItems().get(AllocationButtonCell.this.getIndex());
//                //remove selected item from the table list
//                	
//            }
//        });
//    }
    
    StaffButtonCell() {

        //Action when the button is pressed
        cellButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                Staff currentRow = (Staff) StaffButtonCell.this.getTableView().getItems().get(StaffButtonCell.this.getIndex());
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("UpdateStaff.fxml"));

                try {
                    fxmlLoader.load();
                } catch (IOException e) {
                }

//                int allocationId =0;
//                String courseId = "";
//                String courseName = "";
//                String staffId = "";
//                String staffName = "";
//                int year = 0;
//                String term = "";
//                String weighting = "";
               // String weight_term = Double.toString(weighting);
                
             
//                try {
//                    ResultSet rs = database.getResultSet("SELECT Allocation.allocation_id, Allocation.allocation_term, Allocation.allocation_year,"
//                            + " Allocation.course_id, Allocation.staff_id, Courses.course_name, Staff.Fname, Staff.Lname, Weighting.weighting_term"
//                            + " FROM Staff JOIN Allocation ON Staff.staff_id = Allocation.staff_id JOIN Courses ON"
//                            + " Courses.course_id = Allocation.course_id JOIN Weighting on Weighting.course_id = Courses.course_id WHERE allocation_id =" + currentRow.getStaffId());
//                    System.out.println("hi");
//                    allocationId = rs.getInt(1);
//                    courseId = rs.getString(4);
//                    courseName = rs.getString(6);
//                    staffId = rs.getString(5);
//                    staffName = rs.getString(7) + " " + rs.getString(8);
//                    year = rs.getInt(3);
//                    term = rs.getString(2);
//                    weighting = rs.getString(9);

//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }

                UpdateStaffController updateStaffController = fxmlLoader.getController();
                updateStaffController.setData(currentRow.getStaffId());

                Parent p = fxmlLoader.getRoot();
                Stage stage = new Stage();
                stage.setTitle("Edit Staff Details");
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
