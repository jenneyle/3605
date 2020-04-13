/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infs3605;

import com.sun.prism.impl.Disposer;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.stage.Stage;

/**
 *
 * @author jenneyle
 */
//public class WeightingDetailButtonCell extends TableCell<Record, Boolean> {
//
//    //TODO: Sophia to make the button with an image of a pencilnot text
//    Button cellButton = new Button("Details");
//    Database database = new Database();
//    
//    WeightingDetailButtonCell() {
//
//        //Action when the button is pressed
//        cellButton.setOnAction(new EventHandler<ActionEvent>() {
//
//            @Override
//            public void handle(ActionEvent t) {
//                Weighting currentRow = (Weighting) WeightingDetailButtonCell.this.getTableView().getItems().get(WeightingDetailButtonCell.this.getIndex());
//                FXMLLoader fxmlLoader = new FXMLLoader();
//                fxmlLoader.setLocation(getClass().getResource("WeightingDetails.fxml"));
//
//                try {
//                    fxmlLoader.load();
//                } catch (IOException e) {
//                }
//
//                int weightingId = 0;
//
//
//                WeightingDetailsController weightingDetailsController = fxmlLoader.getController();
//                weightingDetailsController.setData(currentRow.getId());
//
//                Parent p = fxmlLoader.getRoot();
//                Stage stage = new Stage();
//                stage.setTitle("Weighting Details Page");
//                stage.setScene(new Scene(p));
//                stage.show();
//            }
//        });
//    }
//
//    //Display button if the row is not empty
//    @Override
//    protected void updateItem(Boolean t, boolean empty) {
//        super.updateItem(t, empty);
//        if (!empty) {
//            setGraphic(cellButton);
//        }
//    }
//}
//
//
