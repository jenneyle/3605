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
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author jenneyle
 */
public class EditWeightingController {


    @FXML
    Button update;
    @FXML
    Text courseCode;
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
    TextArea description;
    @FXML
    CheckBox repeatLectures;
    
    int weightId;

    int weightingId = 0;
    PageSwitchHelper pageSwitcher = new PageSwitchHelper();

    public void setData(int iWeightingId) {
        weightId = iWeightingId;
       // updateMsg.setVisible(false);

        try {
            Database.openConnection();
            ResultSet rs = conn.createStatement().executeQuery("Select * FROM Weighting WHERE weight_id = " + iWeightingId);

            if (rs.getString(4).equals("Term 1")) {
                courseCode.setText(rs.getString(2) + " " + rs.getInt(3) + "T1");
            } else if (rs.getString(4).equals("Term 2")) {
                courseCode.setText(rs.getString(2) + " " + rs.getInt(3) + "T2");
            } else if (rs.getString(4).equals("Term 3")) {
                courseCode.setText(rs.getString(2) + " " + rs.getInt(3) + "T3");
            } else if (rs.getString(4).equals("Summer Term")) {
                courseCode.setText(rs.getString(2) + " " + rs.getInt(3) + " Summer Term");
            } else {

            }

            studentsEnrol.setText(Integer.toString(rs.getInt(5)));
            tutorialHrs.setText(Integer.toString(rs.getInt(8)));
            lectureHrs.setText(Integer.toString(rs.getInt(9)));
            consultHrs.setText(Integer.toString(rs.getInt(10)));
            markingHrs.setText(Integer.toString(rs.getInt(11)));
            tutorialPrep.setText(Integer.toString(rs.getInt(12)));
            lecturePrep.setText(Integer.toString(rs.getInt(13)));
            staffDev.setText(Integer.toString(rs.getInt(14)));
            totalWeighting.setText(Integer.toString(rs.getInt(6)));
            description.setText(rs.getString(7));

            if (rs.getInt(15) == 1) {
                repeatLectures.setSelected(true);

            }
            if (rs.getInt(15) == 0) {
                repeatLectures.setSelected(false);
            }

        } catch (Exception e) {
            System.out.println("here");
            e.printStackTrace();

        }

//        setLists();
    }

    @FXML
    public void handleSubmitButton(ActionEvent event) throws IOException, SQLException {

        int iStudentsEnrolled = Integer.parseInt(studentsEnrol.getText());
        int iTutorialHrs = Integer.parseInt(tutorialHrs.getText());
        int iLectureHrs = Integer.parseInt(lectureHrs.getText());
        int iConsultHrs = Integer.parseInt(consultHrs.getText());
        int iMarkingHrs = Integer.parseInt(markingHrs.getText());
        int iTutorialPrep = Integer.parseInt(tutorialPrep.getText());
        int iLecturePrep = Integer.parseInt(lecturePrep.getText());
        int iStaffDev = Integer.parseInt(staffDev.getText());
        int iWeightingTotal = Integer.parseInt(totalWeighting.getText());
        String iDescription = description.getText();

        int iRepeatLectures = 0;

        if (repeatLectures.isSelected()) {
            iRepeatLectures = 1;
            iLectureHrs = iLectureHrs * 2;
        }

        Statement st = conn.createStatement();
        try {
            String updateDatabase = ("UPDATE Weighting SET students_enrolled = '" + iStudentsEnrolled
                    + "' ,  weighting_term = '" + iWeightingTotal
                    + "' ,  tutorial_hrs = '" + iTutorialHrs
                    + "' ,  lecture_hrs = '" + iLectureHrs
                    + "' ,  consultation_hrs = '" + iConsultHrs
                    + "' ,  marking_hrs = '" + iMarkingHrs
                    + "' ,  tutorial_prep = '" + iTutorialPrep
                    + "' ,  lecture_prep = '" + iLecturePrep
                    + "' ,  staff_development = '" + iStaffDev
                    + "' ,  description = '" + iDescription
                    + "' ,  repeat_lecture = '" + iRepeatLectures
                    + "' WHERE weight_id = " + weightId);
            st.execute(updateDatabase);
            Notifications updatenotification = Notifications.create().text("Update Success").hideAfter(Duration.seconds(2)).position(Pos.CENTER);
            updatenotification.showInformation();
            System.out.println("Submitted");
           
            //Stage stage = (Stage) back.getScene().getWindow();
            //stage.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}
