/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infs3605;

import com.sun.prism.impl.Disposer;
import static infs3605.Database.conn;
import static infs3605.AllocationTableController.data;
import static infs3605.Database.conn;
import java.sql.ResultSet;
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

/**
 *
 * @author freey
 */
public class StaffDeleteButtonCell extends TableCell<Disposer.Record, Boolean> {

    Button cellButton = new Button("Delete");
    Database database = new Database();

    StaffDeleteButtonCell() {

        //Action when the button is pressed
        cellButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                Staff currentRow = (Staff) StaffDeleteButtonCell.this.getTableView().getItems().get(StaffDeleteButtonCell.this.getIndex());

                //get current allocations of this staff
                Database.openConnection();
                try {
                    ResultSet rs = conn.createStatement().executeQuery("SELECT CURRENT_DATE");
                    String sCurrentYear = rs.getString(1).substring(0, 4);
                    int intCurrentYear = Integer.parseInt(sCurrentYear);
                    System.out.println(intCurrentYear);

                    ResultSet rs1 = conn.createStatement().executeQuery("SELECT * FROM Allocation WHERE staff_id = '" + currentRow.getStaffId() + "' AND allocation_year >= " + intCurrentYear);
                    if (rs1.next()) {
                        //alert you cannot delete
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Warning");
                        alert.setHeaderText(null);
                        alert.setContentText("Unable to delete this field as this staff member is currently allocated to course/s. /n Please re-assign allocations to continue deleting this field.");
                        alert.showAndWait();
                    } else {
                        //alert message to display for confirmation
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Confirm Delete");
                        alert.setHeaderText(null);
                        alert.setContentText("Are you sure you want to delete this field?");
                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.get() == ButtonType.OK) {
                            //remove selected item from the table list
                            data.remove(currentRow);
                            try {
                                Statement st = conn.createStatement();
                                String query = ("DELETE FROM Staff WHERE staff_id = " + currentRow.getStaffId());
                                System.out.println("deleting field");

                                st.execute(query);
                            } catch (SQLException ex) {
                                Logger.getLogger(StaffDeleteButtonCell.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } else {
                            System.out.println("Delete Cancelled");
                        }

                    }

                } catch (SQLException ex) {
                    Logger.getLogger(StaffDeleteButtonCell.class.getName()).log(Level.SEVERE, null, ex);
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
