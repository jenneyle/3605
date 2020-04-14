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
    
    private Button wDetailsButton;
    

    public Weighting(int weight_id, String course_id, int year, String term, int students_enrolled, int facetoface_hours,int pd_hours, double weighting_term){
        this.weight_id = new SimpleIntegerProperty(weight_id);
        this.course_id = new SimpleStringProperty(course_id);
        this.year = new SimpleIntegerProperty(year);
        this.term = new SimpleStringProperty(term);
        this.students_enrolled = new SimpleIntegerProperty(students_enrolled);
        this.facetoface_hours = new SimpleIntegerProperty(facetoface_hours);
        this.pd_hours = new SimpleIntegerProperty(pd_hours);
        System.out.println(pd_hours);
        this.weighting_term = new SimpleDoubleProperty(weighting_term);
        
        //this.editButton = new Button("Edit");
        this.wDetailsButton = new Button("WeightDetails");
    }

    public Button getWDetailsButton() {
        return wDetailsButton;
    }

    public void setWDetailsButton(Button wDetailsButton) {
        this.wDetailsButton = wDetailsButton;
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

    public int getfacetoface_hours() {
        return facetoface_hours.get();
    }

    public void setfacetoface_hours(int facetoface_hours) {
        this.facetoface_hours.set(facetoface_hours);
    }

    public int getpd_hours() {
        return pd_hours.get();
    }

    public void setpd_hours(int pd_hours){
        this.pd_hours.set(pd_hours);
    }

    public double getWeighting_term() {
        return (double) weighting_term.get();
    }

    public void setWeighting_term(double weighting_term) {
        this.weighting_term.set(weighting_term);
    }

}
