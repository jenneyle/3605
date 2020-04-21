/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infs3605;

import com.sun.prism.impl.Disposer;
import static infs3605.Database.conn;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Rjian
 */
public class NewCourseController implements Initializable {

    Database database = new Database();
    PageSwitchHelper pageSwitcher = new PageSwitchHelper();

    @FXML
    TextField courseCode;
    @FXML
    TextField courseName;
//    @FXML
//    TextField courseDes;
    @FXML
    TextArea allocationNotes;
    @FXML
    CheckBox t1;
    @FXML
    CheckBox t2;
    @FXML
    CheckBox t3;
    @FXML
    CheckBox ts;
    @FXML
    TextField studentsEnrolled;
    @FXML
    TextField tutorialHrs;
    @FXML
    TextField lectureHrs;
    @FXML
    CheckBox repeatedLecture;
    @FXML
    TextField consultationHrs;
    @FXML
    TextField markingHrs;
    @FXML
    TextField tutorialPrep;
    @FXML
    TextField lecturePrep;
    @FXML
    TextField staffDev;
    @FXML
    TextField overallWeighting;

    @FXML
    private Label updateMsg;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        updateMsg.setVisible(false);

    }

    public void handleSubmitBtn(ActionEvent event) throws IOException, SQLException {

        String iCourseCode = courseCode.getText();
        String iCourseName = courseName.getText();
//        String iCourseDes = courseDes.getText();
        String iAllocationNotes = allocationNotes.getText();
        int iT1 = 0;
        int iT2 = 0;
        int iT3 = 0;
        int iTs = 0;

        if (t1.isSelected()) {
            iT1 = 1;
        }
        if (t2.isSelected()) {
            iT2 = 1;
        }
        if (t3.isSelected()) {
            iT3 = 1;
        }
        if (ts.isSelected()) {
            iTs = 1;
        }

        int iStudentsEnrolled = Integer.parseInt(studentsEnrolled.getText());
        int iTutorialHrs = Integer.parseInt(tutorialHrs.getText());
        int iLectureHrs = Integer.parseInt(lectureHrs.getText());
        int iConsultationHrs = Integer.parseInt(consultationHrs.getText());
        int iMarkingHrs = Integer.parseInt(markingHrs.getText());
        int iTutorialPrep = Integer.parseInt(tutorialPrep.getText());
        int iLecturePrep = Integer.parseInt(lecturePrep.getText());
        int iStaffDev = Integer.parseInt(staffDev.getText());
        double iOverallWeighting = Double.parseDouble(overallWeighting.getText());
        //+ iStaffDev + ","

        //double totalWeight = (iStudentsEnrolled * 0.002) + (iFaceToFaceHrs * 0.1) + (iPrepAndDevHrs * 0.1);
        Statement st = conn.createStatement();
        try {
            System.out.println("it works");
            String insertData = ("INSERT INTO Courses VALUES ('" + iCourseCode + "','" + iCourseName + "'," + iT1 + "," + iT2 + "," + iT3 + "," + iTs + ",'" + "0" + "','" + iAllocationNotes + "')");

            ResultSet rs = database.getResultSet("SELECT CURRENT_DATE");
            int currentYear = rs.getInt(1);

            if (iT1 == 1) {
                String insertData2 = ("INSERT INTO Weighting (course_id, Year, Term, students_enrolled, tutorial_hrs, lecture_hrs, consultation_hrs, marking_hrs, tutorial_prep, lecture_prep, staff_development, weighting_term) "
                        + "VALUES ('" + iCourseCode + "'," + currentYear + ", 'Term 1'," + iStudentsEnrolled + "," + iTutorialHrs + "," + iLectureHrs + "," + iConsultationHrs + "," + iMarkingHrs + "," + iTutorialPrep + "," + iLecturePrep + "," + iStaffDev + "," + iOverallWeighting + "),"
                        + "('" + iCourseCode + "'," + (currentYear + 1) + ",'Term 1'," + iStudentsEnrolled + "," + iTutorialHrs + "," + iLectureHrs + "," + iConsultationHrs + "," + iMarkingHrs + "," + iTutorialPrep + "," + iLecturePrep + "," + iStaffDev + "," + iOverallWeighting + "),"
                        + "('" + iCourseCode + "'," + (currentYear + 2) + ",'Term 1'," + iStudentsEnrolled + "," + iTutorialHrs + "," + iLectureHrs + "," + iConsultationHrs + "," + iMarkingHrs + "," + iTutorialPrep + "," + iLecturePrep + "," + iStaffDev + "," + iOverallWeighting + "),"
                        + "('" + iCourseCode + "'," + (currentYear + 3) + ",'Term 1'," + iStudentsEnrolled + "," + iTutorialHrs + "," + iLectureHrs + "," + iConsultationHrs + "," + iMarkingHrs + "," + iTutorialPrep + "," + iLecturePrep + "," + iStaffDev + "," + iOverallWeighting + "),"
                        + "('" + iCourseCode + "'," + (currentYear + 4) + ",'Term 1'," + iStudentsEnrolled + "," + iTutorialHrs + "," + iLectureHrs + "," + iConsultationHrs + "," + iMarkingHrs + "," + iTutorialPrep + "," + iLecturePrep + "," + iStaffDev + "," + iOverallWeighting + ")");
                st.execute(insertData2);
            }

            if (iT2 == 1) {
                String insertData2 = ("INSERT INTO Weighting (course_id, Year, Term, students_enrolled, tutorial_hrs, lecture_hrs, consultation_hrs, marking_hrs, tutorial_prep, lecture_prep, staff_development, weighting_term) "
                        + "VALUES ('" + iCourseCode + "'," + currentYear + ", 'Term 2'," + iStudentsEnrolled + "," + iTutorialHrs + "," + iLectureHrs + "," + iConsultationHrs + "," + iMarkingHrs + "," + iTutorialPrep + "," + iLecturePrep + "," + iStaffDev + "," + iOverallWeighting + "),"
                        + "('" + iCourseCode + "'," + (currentYear + 1) + ",'Term 2'," + iStudentsEnrolled + "," + iTutorialHrs + "," + iLectureHrs + "," + iConsultationHrs + "," + iMarkingHrs + "," + iTutorialPrep + "," + iLecturePrep + "," + iStaffDev + "," + iOverallWeighting + "),"
                        + "('" + iCourseCode + "'," + (currentYear + 2) + ",'Term 2'," + iStudentsEnrolled + "," + iTutorialHrs + "," + iLectureHrs + "," + iConsultationHrs + "," + iMarkingHrs + "," + iTutorialPrep + "," + iLecturePrep + "," + iStaffDev + "," + iOverallWeighting + "),"
                        + "('" + iCourseCode + "'," + (currentYear + 3) + ",'Term 2'," + iStudentsEnrolled + "," + iTutorialHrs + "," + iLectureHrs + "," + iConsultationHrs + "," + iMarkingHrs + "," + iTutorialPrep + "," + iLecturePrep + "," + iStaffDev + "," + iOverallWeighting + "),"
                        + "('" + iCourseCode + "'," + (currentYear + 4) + ",'Term 2'," + iStudentsEnrolled + "," + iTutorialHrs + "," + iLectureHrs + "," + iConsultationHrs + "," + iMarkingHrs + "," + iTutorialPrep + "," + iLecturePrep + "," + iStaffDev + "," + iOverallWeighting + ")");
                st.execute(insertData2);
            }

            if (iT3 == 1) {
                String insertData2 = ("INSERT INTO Weighting (course_id, Year, Term, students_enrolled, tutorial_hrs, lecture_hrs, consultation_hrs, marking_hrs, tutorial_prep, lecture_prep, staff_development, weighting_term) "
                        + "VALUES ('" + iCourseCode + "'," + currentYear + ", 'Term 3'," + iStudentsEnrolled + "," + iTutorialHrs + "," + iLectureHrs + "," + iConsultationHrs + "," + iMarkingHrs + "," + iTutorialPrep + "," + iLecturePrep + "," + iStaffDev + "," + iOverallWeighting + "),"
                        + "('" + iCourseCode + "'," + (currentYear + 1) + ",'Term 3'," + iStudentsEnrolled + "," + iTutorialHrs + "," + iLectureHrs + "," + iConsultationHrs + "," + iMarkingHrs + "," + iTutorialPrep + "," + iLecturePrep + "," + iStaffDev + "," + iOverallWeighting + "),"
                        + "('" + iCourseCode + "'," + (currentYear + 2) + ",'Term 3'," + iStudentsEnrolled + "," + iTutorialHrs + "," + iLectureHrs + "," + iConsultationHrs + "," + iMarkingHrs + "," + iTutorialPrep + "," + iLecturePrep + "," + iStaffDev + "," + iOverallWeighting + "),"
                        + "('" + iCourseCode + "'," + (currentYear + 3) + ",'Term 3'," + iStudentsEnrolled + "," + iTutorialHrs + "," + iLectureHrs + "," + iConsultationHrs + "," + iMarkingHrs + "," + iTutorialPrep + "," + iLecturePrep + "," + iStaffDev + "," + iOverallWeighting + "),"
                        + "('" + iCourseCode + "'," + (currentYear + 4) + ",'Term 3'," + iStudentsEnrolled + "," + iTutorialHrs + "," + iLectureHrs + "," + iConsultationHrs + "," + iMarkingHrs + "," + iTutorialPrep + "," + iLecturePrep + "," + iStaffDev + "," + iOverallWeighting + ")");
                st.execute(insertData2);
            }

            if (iTs == 1) {
                String insertData2 = ("INSERT INTO Weighting (course_id, Year, Term, students_enrolled, tutorial_hrs, lecture_hrs, consultation_hrs, marking_hrs, tutorial_prep, lecture_prep, staff_development, weighting_term)"
                        + "VALUES ('" + iCourseCode + "'," + currentYear + ", 'Summer Term'," + iStudentsEnrolled + "," + iTutorialHrs + "," + iLectureHrs + "," + iConsultationHrs + "," + iMarkingHrs + "," + iTutorialPrep + "," + iLecturePrep + "," + iStaffDev + "," + iOverallWeighting + "),"
                        + "('" + iCourseCode + "'," + (currentYear + 1) + ",'Summer Term'," + iStudentsEnrolled + "," + iTutorialHrs + "," + iLectureHrs + "," + iConsultationHrs + "," + iMarkingHrs + "," + iTutorialPrep + "," + iLecturePrep + "," + iStaffDev + "," + iOverallWeighting + "),"
                        + "('" + iCourseCode + "'," + (currentYear + 2) + ",'Summer Term'," + iStudentsEnrolled + "," + iTutorialHrs + "," + iLectureHrs + "," + iConsultationHrs + "," + iMarkingHrs + "," + iTutorialPrep + "," + iLecturePrep + "," + iStaffDev + "," + iOverallWeighting + "),"
                        + "('" + iCourseCode + "'," + (currentYear + 3) + ",'Summer Term'," + iStudentsEnrolled + "," + iTutorialHrs + "," + iLectureHrs + "," + iConsultationHrs + "," + iMarkingHrs + "," + iTutorialPrep + "," + iLecturePrep + "," + iStaffDev + "," + iOverallWeighting + "),"
                        + "('" + iCourseCode + "'," + (currentYear + 4) + ",'Summer Term'," + iStudentsEnrolled + "," + iTutorialHrs + "," + iLectureHrs + "," + iConsultationHrs + "," + iMarkingHrs + "," + iTutorialPrep + "," + iLecturePrep + "," + iStaffDev + "," + iOverallWeighting + ")");
                st.execute(insertData2);

            }

            st.execute(insertData);
            updateMsg.setText("Successfully submitted");
            updateMsg.setTextFill(Color.web("#008000"));
            updateMsg.setVisible(true);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    //button to allocate staff to course
    @FXML
    public void handleAllocateBtn(MouseEvent event) throws IOException {
        pageSwitcher.switcher(event, "StaffAllocation.fxml");
    }

    //button to view course weightings
    @FXML
    public void handleWeightingBtn(ActionEvent event) throws IOException {
        pageSwitcher.switcher(event, "Weighting.fxml");
    }

    @FXML
    public void handleWeightingBtn(MouseEvent event) throws IOException {
        pageSwitcher.switcher(event, "Weighting.fxml");
    }

    //button to view staff details
    @FXML
    public void handleStaffBtn(MouseEvent event) throws IOException {
        pageSwitcher.switcher(event, "StaffTable.fxml");
    }

    @FXML
    public void handleCourseBtn(MouseEvent event) throws IOException {
        pageSwitcher.switcher(event, "CourseTable.fxml");
    }

    @FXML
    public void handleCurrentAlloBtn(MouseEvent event) throws IOException {
        pageSwitcher.switcher(event, "AllocationTable.fxml");
    }
@FXML
    public void handleLogoutBtn(MouseEvent event) throws IOException {
        pageSwitcher.switcher(event, "Login.fxml");
    }
}
