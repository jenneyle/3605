///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package infs3605;
//
//import static infs3605.Database.conn;
//import java.io.IOException;
//import java.net.URL;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.ResourceBundle;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.Initializable;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//import javafx.scene.control.TextField;
//import javafx.stage.Stage;
//
///**
// * FXML Controller class
// *
// * @author jenneyle
// */
//
//public class EditWeightingController implements Initializable {
//
//    @FXML
//    Button back;
//    @FXML
//    Button update;
//    @FXML
//    TextField courseCode;
//    @FXML
//    TextField term;
//    @FXML
//    TextField year;
//    @FXML
//    TextField totalWeighting;
//    @FXML
//    TextField studentsEnrol;
//    @FXML
//    TextField tutorialHrs;
//    @FXML
//    TextField lectureHrs;
//    @FXML
//    TextField markingHrs;
//    @FXML
//    TextField tutorialPrep;
//    @FXML
//    TextField lecturePrep;
//    @FXML
//    TextField staffDev;
//    @FXML
//    TextField consultHrs;
//    @FXML
//    Label udateMsg;
//
//    int weightingId = 0;
//    PageSwitchHelper pageSwitcher = new PageSwitchHelper();
//
//    public void setData(int iWeightingId, String iCourseCode, int iYear, String iTerm, int iStudentsEnrolled, int iTutorialHrs, int iLectureHrs, int iConsultHrs, int iMarkingHrs, int iTutorialPrep, int iLecturePrep, int iStaffDev, int iWeightingTotal) {
//
//        weightingId = iWeightingId;
//        courseCode.setText(iCourseCode);
//        year.setValue(iYear);
//        term.setText(iTerm);
//        studentsEnrol.setValue(iStudentsEnrolled);
//        tutorialHrs.setValue(iTutorialHrs);
//        lectureHrs.setValue(iLectureHrs);
//        consultHrs.setValue(iConsultHrs);
//        markingHrs.setValue(iMarkingHrs);
//        tutorialPrep.setValue(iTutorialPrep);
//        lecturePrep.setValue(iLecturePrep);
//        staffDev.setValue(iStaffDev);
//        totalWeighting.setValue(iWeightingTotal);
//
////        setLists();
//    }
//
//    @FXML
//    public void handleBackButton(ActionEvent event) throws IOException {
//        Stage stage = (Stage) back.getScene().getWindow();
//        stage.close();
//    }
//
//    @FXML
//    public void handleSubmitButton(ActionEvent event) throws IOException, SQLException {
//        String iCourseCode = courseCode.getText();
//        String iTerm = term.getText();
//        int iYear = year.getValue();
//        int iStudentsEnrolled = studentsEnrol.getValue();
//        int iTutorialHrs = tutorialHrs.getValue();
//        int iLectureHrs = lectureHrs.getValue();
//        int iConsultHrs = consultHrs.getValue();
//        int iMarkingHrs = markingHrs.getValue();
//        int iTutorialPrep = tutorialPrep.getValue();
//        int iLecturePrep = lecturePrep.getValue();
//        int iStaffDev = staffDev.getValue();
//        int iWeightingTotal = totalWeighting.getValue();
//
//           Statement st = conn.createStatement();
//        try {
//            String updateDatabase = ("UPDATE Weighting SET course_id = '" + courseCode
//                    + "' , Year = '" + year
//                    + "' , Term = '" + term
//                    + "' ,  students_enrolled = '" + iStudentsEnrolled
//                    + "' ,  weighting_term = '" + iWeightingTotal
//                    + "' ,  tutorial_hrs = '" + iTutorialHrs
//                    + "' ,  lecture_hrs = '" + iLectureHrs
//                    + "' ,  consultation_hrs = '" + iConsultHrs
//                    + "' ,  marking_hrs = '" + iMarkingHrs
//                    + "' ,  tutorial_prep = '" + iTutorialPrep
//                    + "' ,  lecture_prep = '" + iLecturePrep
//                    + "' ,  staff_development = '" + iStaffDev
//                    + "' WHERE course_id = '" + courseCode + "' AND Year = '" + year + "' AND Term = '" + term + "';");
//            st.execute(updateDatabase);
//            System.out.println("Submitted");
////            updateMsg.setText("Successfully submitted");
////            updateMsg.setVisible(true);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//
//    }
//
//    @Override
//    public void initialize(URL location, ResourceBundle resources) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//}


