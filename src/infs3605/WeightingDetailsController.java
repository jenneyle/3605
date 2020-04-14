/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infs3605;

import static infs3605.Database.conn;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author jenneyle
 */
public class WeightingDetailsController {

    @FXML
    Button back;
    @FXML
    Text courseCode;
    @FXML
    Text term;
    @FXML
    Text year;
    @FXML
    Text studentsEnrolled;
    @FXML
    Text tutorialHrs;
    @FXML
    Text lectureHrs;
    @FXML
    Text consultationHrs;
    @FXML
    Text markingHrs;
    @FXML
    Text tutorialPrep;
    @FXML
    Text lecturePrep;
    @FXML
    Text staffDev;
    @FXML
    Text totalWeighting;

    PageSwitchHelper pageSwitcher = new PageSwitchHelper();
    
    
    public void setData(int iWeightingId) {
        // allocationId = iAllocationId;

        try {
            Database.openConnection();
            ResultSet rs = conn.createStatement().executeQuery("SELECT weight_id, course_id, Year, Term, students_enrolled, tutorial_hrs, lecture_hrs, consultation_hrs, marking_hrs, tutorial_prep, lecture_prep, staff_development, ROUND(weighting_term, 2) FROM Weighting WHERE weight_id" + iWeightingId);

            courseCode.setText(rs.getString(2));
            term.setText(rs.getString(4));
            year.setText(rs.getString(3));
            studentsEnrolled.setText(rs.getString(5));
            tutorialHrs.setText(rs.getString(6));
            lectureHrs.setText(rs.getString(7));
            consultationHrs.setText(rs.getString(8));
            markingHrs.setText(rs.getString(9));
            tutorialPrep.setText(rs.getString(10));
            lecturePrep.setText(rs.getString(11));
            staffDev.setText(rs.getString(12));
            totalWeighting.setText(rs.getString(13));
            
//            if(rs.getInt(6)==1){
//                lic.setText("LIC: Yes");
//            } else {
//                lic.setText("LIC: No");
//            }
            
            
            
//            courseName.setText(rs.getString(7));
//            staffName.setText(rs.getString(8) + " " + rs.getString(9));
            conn.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    @FXML
    public void handleBackButton(ActionEvent event) throws IOException {
        Stage stage = (Stage) back.getScene().getWindow();
        stage.close();
    }

}
