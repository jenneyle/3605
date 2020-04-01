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
public class AllocationInfoController {

    @FXML
    Button back;

    @FXML
    Text courseCode;
    @FXML
    Text courseName;
    @FXML
    Text staffId;
    @FXML
    Text staffName;
    @FXML
    Text yearTerm;

    @FXML
    public void handleBackButton(ActionEvent event) throws IOException {

        Stage stage = (Stage) back.getScene().getWindow();
        stage.close();
    }

    public void setData(String iCourseId, String iCourseName, String iStaffId, String iStaffName, String iYearTerm) {
        courseCode.setText(iCourseId);
        courseName.setText(iCourseName);
        staffId.setText(iStaffId);
        staffName.setText(iStaffName);
        yearTerm.setText(iYearTerm);

    }
}
