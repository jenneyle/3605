
package infs3605;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;


public class WeightingEditButtonCell extends TableCell<Disposer.Record, Boolean> {

    //TODO: Sophia to make the button with an image of a pencilnot text
  Image pencilImage = new Image(this.getClass().getResourceAsStream("/resources/Pencil1.png"));
    
   //Image newimg = pencilImage.
    
    
     Button cellButton = new Button("", new ImageView(pencilImage));
      
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
                stage.setTitle("Edit Course Weighting");
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
