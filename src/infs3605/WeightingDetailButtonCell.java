/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infs3605;

import com.sun.prism.impl.Disposer;
import com.sun.prism.impl.Disposer.Record;
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
 * @author jenneyle
 */
public class WeightingDetailButtonCell extends TableCell<Record, Boolean> {

    //TODO: Sophia to make the button with an image of a pencilnot text
    Image closeImage = new Image(this.getClass().getResourceAsStream("/resources/detail.png"));

    //Image newimg = pencilImage.
    Button cellButton = new Button("", new ImageView(closeImage));
    Database database = new Database();

    WeightingDetailButtonCell() {

        //Action when the button is pressed
        cellButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                //int weightID = Integer.parseInt(WeightingDetailButtonCell.this.getTableRow().getItem().toString());
                Weighting currentRow = (Weighting) WeightingDetailButtonCell.this.getTableView().getItems().get(WeightingDetailButtonCell.this.getIndex());

                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("WeightingDetails.fxml"));
                    Parent root = loader.load();
                    
                    //The following both lines are the only addition we need to pass the arguments
                    WeightingDetailsController weightingDetailsController = loader.getController();
                    weightingDetailsController.setData(currentRow.getWeight_id());
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (IOException io) {
                    io.printStackTrace();
                }
            }
        });
    }

//    Display button if the row is not empty
    @Override
    protected void updateItem(Boolean t, boolean empty) {
        super.updateItem(t, empty);
        if (!empty) {
            setGraphic(cellButton);
        }
    }
}
