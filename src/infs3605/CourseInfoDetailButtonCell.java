/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infs3605;

/**
 *
 * @author Rjian
 */
import com.sun.prism.impl.Disposer;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;

/**
 *
 * @author freey
 */
public class CourseInfoDetailButtonCell extends TableCell<Disposer.Record, Boolean> {

    Button cellButton = new Button("Details");
    Database database = new Database();
    CourseInfoController courseInfoController = new CourseInfoController();

    CourseInfoDetailButtonCell() {

        //Action when the button is pressed
        cellButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t){
                String courseID = CourseInfoDetailButtonCell.this.getTableRow().getItem().toString();
                //Lauch the Course Detail Popup
                
                try {
                    courseInfoController.intialiser(t, courseID, "CourseInfo.fxml");
                } catch (IOException io){
                    io.printStackTrace();
                }
                
                

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
