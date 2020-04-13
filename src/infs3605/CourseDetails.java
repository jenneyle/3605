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
public class CourseDetails {
    //Properties
    private StringProperty course_id;
    private StringProperty courseName;
    private StringProperty courseDesc;
    private IntegerProperty t1Offer;
    private IntegerProperty t2Offer;
    private IntegerProperty t3Offer;
    private IntegerProperty tsOffer;
    private StringProperty allocationNotes;
    private Button editButton;
    
    //Constructor
    public CourseDetails(String course_id, String courseName, String courseDesc
                        , int t1Offer, int t2Offer, int t3Offer
                        , int tsOffer, String allocationNotes) {    
        this.course_id = new SimpleStringProperty(course_id);
        this.courseName = new SimpleStringProperty(courseName);
        this.courseDesc = new SimpleStringProperty(courseDesc);
        this.t1Offer = new SimpleIntegerProperty(t1Offer);
        this.t2Offer = new SimpleIntegerProperty(t2Offer);
        this.t3Offer = new SimpleIntegerProperty(t3Offer);
        this.tsOffer = new SimpleIntegerProperty(tsOffer);
        this.allocationNotes = new SimpleStringProperty(allocationNotes);
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
    
    public String getCourseDesc() {
        return courseDesc.get();
    }
    
    public void setCourseDesc(String courseDesc) {
        this.courseDesc = new SimpleStringProperty(courseDesc);
    }
    
    public String getAllocationNotes() {
        return allocationNotes.get();
    }

    public void setAllocationNotes(String notes) {
        this.allocationNotes = new SimpleStringProperty(notes);
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
    
    public int getTsOffer() {
        return tsOffer.get();
    }

    public void setTsOffer(int tsOffer) {    
        this.tsOffer = new SimpleIntegerProperty(tsOffer);
    }

    public Button getEditButton() {
        return editButton;
    }

    public void setEditButton(Button editButton) {
        this.editButton = editButton;
    }
    
    @Override
    public String toString() {
        return course_id.get();
    }
}
