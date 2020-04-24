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
import javafx.scene.control.TextArea;
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
    @FXML
    TextArea description;

    @FXML
    Text marking_percent;
    PageSwitchHelper pageSwitcher = new PageSwitchHelper();

    public void setData(int iWeightingId) {

        try {
            Database.openConnection();
            ResultSet rs = conn.createStatement().executeQuery("SELECT weight_id, course_id, Year, Term, students_enrolled, tutorial_hrs, lecture_hrs, consultation_hrs, (students_enrolled*marking_hrs*0.01*0.75) as 'total_marking', tutorial_prep, lecture_prep, staff_development, weighting_term, repeat_lecture, (tutorial_hrs+lecture_hrs+consultation_hrs) as 'facetoface_hours', (tutorial_prep+lecture_prep+staff_development) as 'pd_hours', description, marking_hrs FROM Weighting WHERE weight_id = '" + iWeightingId + "'");
            
            if (rs.getString(4).equals("Term 1")) {
                courseCode.setText(rs.getString(2) + " " + rs.getInt(3)+"T1");
            } else if (rs.getString(4).equals("Term 2")) {
                courseCode.setText(rs.getString(2) + " " + rs.getInt(3)+"T2");
            } else if (rs.getString(4).equals("Term 3")) {
                courseCode.setText(rs.getString(2) + " " + rs.getInt(3)+"T3");
            } else if (rs.getString(4).equals("Summer Term")) {
                courseCode.setText(rs.getString(2) + " " + rs.getInt(3)+" Summer Term");
            } else {
                
            }

            studentsEnrolled.setText(rs.getString(5));
            tutorialHrs.setText(rs.getString(6));
            lectureHrs.setText(rs.getString(7));
            consultationHrs.setText(rs.getString(8));
            markingHrs.setText(rs.getString(9) + "hrs");
            tutorialPrep.setText(rs.getString(10));
            lecturePrep.setText(rs.getString(11));
            staffDev.setText(rs.getString(12));
            totalWeighting.setText(rs.getString(13));
            totalFace.setText(rs.getString(15));
            totalStaff.setText(rs.getString(16));
            description.setText(rs.getString(17));
            marking_percent.setText(" " + rs.getString(18));

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
