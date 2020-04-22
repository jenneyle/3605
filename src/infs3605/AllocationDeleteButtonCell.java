/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infs3605;

import com.sun.prism.impl.Disposer;
import static infs3605.Database.conn;
import static infs3605.AllocationTableController.data;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author freey
 */
public class AllocationDeleteButtonCell extends TableCell<Disposer.Record, Boolean> {
    Image deleteImg = new Image(this.getClass().getResourceAsStream("/resources/close.png"));
    Button cellButton = new Button("", new ImageView(deleteImg));
    Database database = new Database();

    AllocationDeleteButtonCell() {

        //Action when the button is pressed
        cellButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirm Delete");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to delete this field?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    Allocation currentRow = (Allocation) AllocationDeleteButtonCell.this.getTableView().getItems().get(AllocationDeleteButtonCell.this.getIndex());
                    //remove selected item from the table list
                    data.remove(currentRow);
                    try {
                        Statement st = conn.createStatement();
                        String query = ("DELETE FROM Allocation WHERE allocation_id = " + currentRow.getId());
                        System.out.println("deleting field ");

                        st.execute(query);
                    } catch (SQLException ex) {
                        Logger.getLogger(AllocationDeleteButtonCell.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    System.out.println("Delete Cancelled");
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
