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

/**
 *
 * @author jenneyle
 */
public class Weighting {

    private StringProperty weight_id;
    private StringProperty course_id;
    private IntegerProperty year;
    private StringProperty term;
    private IntegerProperty students_enrolled;
    private IntegerProperty face_time;
    private IntegerProperty prep_dev;
    private DoubleProperty weighting_term;

    public Weighting(String weight_id, String course_id, int year, String term, int students_enrolled, int face_time, int prep_dev, double weighting_term) {

        this.weight_id = new SimpleStringProperty(weight_id);
        this.course_id = new SimpleStringProperty(course_id);
        this.year = new SimpleIntegerProperty(year);
        this.term = new SimpleStringProperty(term);
        this.students_enrolled = new SimpleIntegerProperty(students_enrolled);
        this.face_time = new SimpleIntegerProperty(face_time);
        this.prep_dev = new SimpleIntegerProperty(prep_dev);
        this.weighting_term = new SimpleDoubleProperty(weighting_term);
    }

    public String getWeight_id() {
        return weight_id.get();
    }

    public void setWeight_id(String weight_id) {
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

    public int getFace_time() {
        return face_time.get();
    }

    public void setFace_time(int face_time) {
        this.face_time.set(face_time);
    }

    public int getPrep_dev() {
        return prep_dev.get();
    }

    public void setPrep_dev(int prep_dev) {
        this.prep_dev.set(prep_dev);
    }

    public int getWeighting_term() {
        return (int) weighting_term.get();
    }

    public void setWeighting_term(double weighting_term) {
        this.weighting_term.set(weighting_term);
    }
    
}
