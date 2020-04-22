/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infs3605;

import static infs3605.Database.conn;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author jenneyle
 */

public class EditWeightingController {

    @FXML
    Button back;
    @FXML
    Button update;
    @FXML
    TextField courseCode;
    @FXML
    TextField term;
    @FXML
    TextField year;
    @FXML
    TextField totalWeighting;
    @FXML
    TextField studentsEnrol;
    @FXML
    TextField tutorialHrs;
    @FXML
    TextField lectureHrs;
    @FXML
    TextField markingHrs;
    @FXML
    TextField tutorialPrep;
    @FXML
    TextField lecturePrep;
    @FXML
    TextField staffDev;
    @FXML
    TextField consultHrs;
    @FXML
    Label udateMsg;

    int weightingId = 0;
    PageSwitchHelper pageSwitcher = new PageSwitchHelper();

    public void setData(int iWeightingId) {

        weightingId = iWeightingId;
//        courseCode.setText(iCourseCode);
//        year.setText(Integer.toString(iYear));
//        term.setText(iTerm);
//        studentsEnrol.setText(Integer.toString(iStudentsEnrolled));
//        tutorialHrs.setText(Integer.toString(iTutorialHrs));
//        lectureHrs.setText(Integer.toString(iLectureHrs));
//        consultHrs.setText(Integer.toString(iConsultHrs));
//        markingHrs.setText(Integer.toString(iMarkingHrs));
//        tutorialPrep.setText(Integer.toString(iTutorialPrep));
//        lecturePrep.setText(Integer.toString(iLecturePrep));
//        staffDev.setText(Integer.toString(iStaffDev));
//        totalWeighting.setText(Integer.toString(iWeightingTotal));

//        setLists();
    }

    @FXML
    public void handleBackButton(ActionEvent event) throws IOException {
        Stage stage = (Stage) back.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void handleSubmitButton(ActionEvent event) throws IOException, SQLException {
//        String iCourseCode = courseCode.getText();
//        String iTerm = term.getText();
//        int iYear = Integer.parseInt(year.getText());
        int iStudentsEnrolled = Integer.parseInt(studentsEnrol.getText());
        int iTutorialHrs = Integer.parseInt(tutorialHrs.getText());
        int iLectureHrs = Integer.parseInt(lectureHrs.getText());
        int iConsultHrs = Integer.parseInt(consultHrs.getText());
        int iMarkingHrs = Integer.parseInt(markingHrs.getText());
        int iTutorialPrep = Integer.parseInt(tutorialPrep.getText());
        int iLecturePrep = Integer.parseInt(lecturePrep.getText());
        int iStaffDev = Integer.parseInt(staffDev.getText());
        int iWeightingTotal = Integer.parseInt(totalWeighting.getText());

           Statement st = conn.createStatement();
        try {
            String updateDatabase = ("UPDATE Weighting SET course_id = '" + courseCode
                    + "' , Year = '" + year
                    + "' , Term = '" + term
                    + "' ,  students_enrolled = '" + iStudentsEnrolled
                    + "' ,  weighting_term = '" + iWeightingTotal
                    + "' ,  tutorial_hrs = '" + iTutorialHrs
                    + "' ,  lecture_hrs = '" + iLectureHrs
                    + "' ,  consultation_hrs = '" + iConsultHrs
                    + "' ,  marking_hrs = '" + iMarkingHrs
                    + "' ,  tutorial_prep = '" + iTutorialPrep
                    + "' ,  lecture_prep = '" + iLecturePrep
                    + "' ,  staff_development = '" + iStaffDev
                    + "' WHERE course_id = '" + courseCode + "' AND Year = '" + year + "' AND Term = '" + term + "';");
            st.execute(updateDatabase);
            System.out.println("Submitted");
//            updateMsg.setText("Successfully submitted");
//            updateMsg.setVisible(true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

//    @Override
//    public void initialize(URL location, ResourceBundle resources) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }

}


