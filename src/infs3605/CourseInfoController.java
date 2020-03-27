/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infs3605;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author SOPHIA
 */
public class CourseInfoController {

    @FXML
    Button back;

    @FXML
    Text courseCode;
    @FXML
    Text courseName;
    @FXML
    ImageView t1Offer;
    @FXML
    ImageView t2Offer;
    @FXML
    ImageView t3Offer;
    @FXML
    ImageView tsOffer;

    @FXML
    public void handleBackButton(ActionEvent event) throws IOException {

        Stage stage = (Stage) back.getScene().getWindow();
        stage.close();
    }

    public void setData(String iCourseId, String iCourseName, int t1, int t2, int t3, int ts) {
        courseCode.setText(iCourseId);
        courseName.setText(iCourseName);
        //If course is availble in aterm show the green tick
        if (t1 == 1) {
            t1Offer.setVisible(true);
        }
        if (t2 == 1) {
            t2Offer.setVisible(true);
        }
        if (t3 == 1) {
            t3Offer.setVisible(false);
        }
        if (ts == 1) {
            tsOffer.setVisible(true);
        }
    }
}
