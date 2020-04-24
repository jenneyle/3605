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
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 *
 * @author freey
 */
public class CourseInfoDetailButtonCell extends TableCell<Disposer.Record, Boolean> {

  Image closeImage = new Image(this.getClass().getResourceAsStream("/resources/detail.png"));

    //Image newimg = pencilImage.
    Button cellButton = new Button("", new ImageView(closeImage));
    Database database = new Database();
    CourseInfoController courseInfoController = new CourseInfoController();

    CourseInfoDetailButtonCell() {

        //Action when the button is pressed
        cellButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                String courseID = CourseInfoDetailButtonCell.this.getTableRow().getItem().toString();
                //Lauch the Course Detail Popup

                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("CourseInfo.fxml"));
                    Parent root = loader.load();

                    //The following both lines are the only addition we need to pass the arguments
                    CourseInfoController controller2 = loader.getController();
                    controller2.start(courseID);
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                       stage.setTitle("Course Details");
                    stage.show();
                } catch (IOException io) {
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
