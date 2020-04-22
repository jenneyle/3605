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
import javafx.scene.text.Text;
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
    Text courseCode;
    @FXML
    Text term;
    @FXML
    Text year;
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

       // weightingId = iWeightingId;
       
       try{
       Database.openConnection();
            ResultSet rs = conn.createStatement().executeQuery("Select * FROM Weighting WHERE weight_id = " + iWeightingId);
            
          courseCode.setText(rs.getString(2));
        year.setText(Integer.toString(rs.getInt(3)));
        term.setText(rs.getString(4));
        studentsEnrol.setText(Integer.toString(rs.getInt(5)));
        tutorialHrs.setText(Integer.toString(rs.getInt(8)));
        lectureHrs.setText(Integer.toString(rs.getInt(9)));
        consultHrs.setText(Integer.toString(rs.getInt(10)));
        markingHrs.setText(Integer.toString(rs.getInt(11)));
        tutorialPrep.setText(Integer.toString(rs.getInt(12)));
        lecturePrep.setText(Integer.toString(rs.getInt(13)));
        staffDev.setText(Integer.toString(rs.getInt(14)));
        totalWeighting.setText(Integer.toString(rs.getInt(6)));
            //Add description, and repeat lecture.
            
            
       } catch(Exception e){
           System.out.println("here");
           e.printStackTrace();
           
           
       }
       

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


}


