/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infs3605;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;

/**
 *
 * @author Rjian
 */
public class Course {
    //Properties
    private StringProperty course_id;
    private StringProperty courseName;
    private IntegerProperty t1Offer;
    private IntegerProperty t2Offer;
    private IntegerProperty t3Offer;
    private Button editButton;
    
    //Constructor
    public Course(String course_id, String courseName, int t1Offer, int t2Offer, int t3Offer) {    
        this.course_id = new SimpleStringProperty(course_id);
        this.courseName = new SimpleStringProperty(courseName);
        this.t1Offer = new SimpleIntegerProperty(t1Offer);
        this.t2Offer = new SimpleIntegerProperty(t2Offer);
        this.t3Offer = new SimpleIntegerProperty(t3Offer);
        this.editButton = new Button("Edit");
    }
    
    //Getters and Setters
    public String getCourse_id() {
        return course_id.get();
    }

    public void setCourse_id(String course_id) {
        this.course_id = new SimpleStringProperty(course_id);
    }

    public String getCourseName() {
        return courseName.get();
    }

    public void setCourseName(String courseName) {
        this.courseName = new SimpleStringProperty(courseName);
    }

    public int getT1Offer() {
        return t1Offer.get();
    }

    public void setT1Offer(int t1Offer) {
        this.t1Offer = new SimpleIntegerProperty(t1Offer);
    }

    public int getT2Offer() {
        return t2Offer.get();
    }

    public void setT2Offer(int t2Offer) {
        this.t2Offer = new SimpleIntegerProperty(t2Offer);
    }

    public int getT3Offer() {
        return t3Offer.get();
    }

    public void setT3Offer(int t3Offer) {    
        this.t3Offer = new SimpleIntegerProperty(t3Offer);
    }

    public Button getEditButton() {
        return editButton;
    }

    public void setEditButton(Button editButton) {
        this.editButton = editButton;
    }
}
