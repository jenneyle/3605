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
    @FXML
    Text repeatLectures;
    @FXML
    Text totalFace;
    @FXML
    Text totalStaff;

    PageSwitchHelper pageSwitcher = new PageSwitchHelper();

    public void setData(int iWeightingId) {

        try {
            Database.openConnection();
            ResultSet rs = conn.createStatement().executeQuery("SELECT weight_id, course_id, Year, Term, students_enrolled, tutorial_hrs, lecture_hrs, consultation_hrs, marking_hrs, tutorial_prep, lecture_prep, staff_development, weighting_term, repeat_lecture FROM Weighting WHERE weight_id = '" + iWeightingId + "'");

            courseCode.setText(rs.getString(2));
            term.setText("Term: " + rs.getString(4));
            year.setText("Year: " + rs.getString(3));
            studentsEnrolled.setText("Students Enrolled: " + rs.getString(5));
            tutorialHrs.setText("Tutorial Hours: " + rs.getString(6));
            lectureHrs.setText("Lecture Hours: " + rs.getString(7));
            consultationHrs.setText("Consultation Hours: " + rs.getString(8));
            markingHrs.setText("Marking Hours: " + rs.getString(9));
            tutorialPrep.setText("Tutorial Preparation: " + rs.getString(10));
            lecturePrep.setText("Lecture Preparation: " + rs.getString(11));
            staffDev.setText("Staff Development Hours: " + rs.getString(12));
            totalWeighting.setText("Total Weighting: " + rs.getString(13));

            if (rs.getInt(14) == 1) {
                repeatLectures.setText("Yes");
            }
            if (rs.getInt(14) == 0) {
                repeatLectures.setText("No");
            }

//            if(rs.getInt(6)==1){
//                lic.setText("LIC: Yes");
//            } else {
//                lic.setText("LIC: No");
//            }
//            courseName.setText(rs.getString(7));
//            staffName.setText(rs.getString(8) + " " + rs.getString(9));
            //conn.close();
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
