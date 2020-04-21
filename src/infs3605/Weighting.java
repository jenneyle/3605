/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infs3605;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;

/**
 *
 * @author jenneyle
 */
public class Weighting {

    private IntegerProperty weight_id;
    private StringProperty course_id;
    private IntegerProperty year;
    private StringProperty term;
    private IntegerProperty students_enrolled;
    private IntegerProperty facetoface_hours;
    private IntegerProperty pd_hours;
    private DoubleProperty weighting_term;
    private IntegerProperty tutorial_hrs;
    private IntegerProperty lecture_hrs;
    private IntegerProperty consultation_hrs;
    private IntegerProperty marking_hrs;
    private IntegerProperty tutorial_prep;
    private IntegerProperty lecture_prep;
    private IntegerProperty staff_development;
    private IntegerProperty repeat_lecture;
    private Button detailsButton;
    private Button updateButton;

    public Weighting() {
    }

    public Weighting(int weight_id, String course_id, int year, String term
            , int students_enrolled, int facetoface_hours, int pd_hours
            , double weighting_term) {
        this.weight_id = new SimpleIntegerProperty(weight_id);
        this.course_id = new SimpleStringProperty(course_id);
        this.year = new SimpleIntegerProperty(year);
        this.term = new SimpleStringProperty(term);
        this.students_enrolled = new SimpleIntegerProperty(students_enrolled);
        this.facetoface_hours = new SimpleIntegerProperty(facetoface_hours);
        this.pd_hours = new SimpleIntegerProperty(pd_hours);
        System.out.println(pd_hours);
        this.weighting_term = new SimpleDoubleProperty(weighting_term);
//        this.repeat_lecture = new SimpleIntegerProperty(repeat_lecture);

        //this.editButton = new Button("Edit");
        this.detailsButton = new Button("Details");
    }

   
//    public Weighting(int weight_id, String course_id, int year, String term, int students_enrolled, int facetoface_hours, int pd_hours, double weighting_term, int repeat_lecture) {
//        this.weight_id = new SimpleIntegerProperty(weight_id);
//        this.course_id = new SimpleStringProperty(course_id);
//        this.year = new SimpleIntegerProperty(year);
//        this.term = new SimpleStringProperty(term);
//        this.students_enrolled = new SimpleIntegerProperty(students_enrolled);
//        this.facetoface_hours = new SimpleIntegerProperty(facetoface_hours);
//        this.pd_hours = new SimpleIntegerProperty(pd_hours);
//        System.out.println(pd_hours);
//        this.weighting_term = new SimpleDoubleProperty(weighting_term);
//        this.repeat_lecture = new SimpleIntegerProperty(repeat_lecture);
//        this.detailsButton = detailsButton;
//    }
    

    public Weighting(int weight_id, String course_id, int year, String term, int students_enrolled, double weighting_term, int tutorial_hrs, int lecture_hrs, int consultation_hrs, int marking_hrs, int tutorial_prep, int lecture_prep, int staff_development) {
        this.weight_id = new SimpleIntegerProperty(weight_id);
        this.course_id = new SimpleStringProperty(course_id);
        this.year = new SimpleIntegerProperty(year);
        this.term = new SimpleStringProperty(term);
        this.students_enrolled = new SimpleIntegerProperty(students_enrolled);
        this.weighting_term = new SimpleDoubleProperty(weighting_term);
        this.tutorial_hrs = new SimpleIntegerProperty(tutorial_hrs);
        this.lecture_hrs = new SimpleIntegerProperty(lecture_hrs);
        this.consultation_hrs = new SimpleIntegerProperty(consultation_hrs);
        this.marking_hrs = new SimpleIntegerProperty(marking_hrs);
        this.tutorial_prep = new SimpleIntegerProperty(tutorial_prep);
        this.lecture_prep = new SimpleIntegerProperty(lecture_prep);
        this.staff_development = new SimpleIntegerProperty(staff_development);
        
        this.detailsButton = new Button("details");
        this.updateButton = new Button("update");
    }

    public Button getWDetailsButton() {
        return detailsButton;
    }

    public void setWDetailsButton(Button wDetailsButton) {
        this.detailsButton = wDetailsButton;
    }

    public Integer getWeight_id() {
        return weight_id.get();
    }

    public void setWeight_id(int weight_id) {
        this.weight_id.set(weight_id);
    }

    public String getCourse_id() {
        return course_id.get();
    }

    public void setCourse_id(String course_id) {
        this.course_id.set(course_id);
    }

    public int getYear() {
        return year.get();
    }

    public void setYear(int year) {
        this.year.set(year);
    }

    public String getTerm() {
        return term.get();
    }

    public void setTerm(String term) {
        this.term.set(term);
    }

    public int getStudents_enrolled() {
        return students_enrolled.get();
    }

    public void setStudents_enrolled(int students_enrolled) {
        this.students_enrolled.set(students_enrolled);
    }

    public int getFacetoface_hours() {
        return facetoface_hours.get();
    }

    public void setFacetoface_hours(int facetoface_hours) {
        this.facetoface_hours.set(facetoface_hours);
    }

    public int getPd_hours() {
        return pd_hours.get();
    }

    public void setPd_hours(int pd_hours) {
        this.pd_hours.set(pd_hours);
    }

    public double getWeighting_term() {
        return (double) weighting_term.get();
    }

    public void setWeighting_term(double weighting_term) {
        this.weighting_term.set(weighting_term);
    }

    public Integer getRepeatLecture() {
        return repeat_lecture.get();
    }

    public void setRepeatLecture(int repeat_lecture) {
        this.repeat_lecture.set(repeat_lecture);
    }

    public int getTutorial_hrs() {
        return tutorial_hrs.get();
    }

    public void setTutorial_hrs(int tutorial_hrs) {
        this.tutorial_hrs.set(tutorial_hrs);
    }

    public int getLecture_hrs() {
        return lecture_hrs.get();
    }

    public void setLecture_hrs(int lecture_hrs) {
        this.lecture_hrs.set(lecture_hrs);
    }

    public int getConsultation_hrs() {
        return consultation_hrs.get();
    }

    public void setConsultation_hrs(int consultation_hrs) {
        this.consultation_hrs.set(consultation_hrs);
    }

    public int getMarking_hrs() {
        return marking_hrs.get();
    }

    public void setMarking_hrs(int marking_hrs) {
        this.marking_hrs.set(marking_hrs);
    }

    public int getTutorial_prep() {
        return tutorial_prep.get();
    }

    public void setTutorial_prep(int tutorial_prep) {
        this.tutorial_prep.set(tutorial_prep);
    }

    public int getLecture_prep() {
        return lecture_prep.get();
    }

    public void setLecture_prep(int lecture_prep) {
        this.lecture_prep.set(lecture_prep);
    }

    public int getStaff_development() {
        return staff_development.get();
    }

    public void setStaff_development(int staff_development) {
        this.staff_development.set(staff_development);
    }

    public Button getUpdateButton() {
        return updateButton;
    }

    public void setUpdateButton(Button updateButton) {
        this.updateButton = updateButton;
    }
    
    

}
