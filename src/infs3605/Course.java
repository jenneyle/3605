/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infs3605;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Rjian
 */
public class Course {

    //Properties
    private StringProperty course_id;
    private StringProperty courseName;
    private ImageView t1Offer;
    private ImageView t2Offer;
    private ImageView t3Offer;
    private ImageView tsOffer;
    private Button detailsButton;
    private Button editButton;

    //Constructor
    public Course(String course_id, String courseName, int t1Offer, int t2Offer, int t3Offer, int tsOffer) {
        this.course_id = new SimpleStringProperty(course_id);
        this.courseName = new SimpleStringProperty(courseName);
        this.t1Offer = isOffered(t1Offer);
        this.t2Offer = isOffered(t2Offer);
        this.t3Offer = isOffered(t3Offer);
        this.tsOffer = isOffered(tsOffer);
        this.detailsButton = new Button("Details");
        this.editButton = new Button("Edit");
    }

    public Button getEditButton() {
        return editButton;
    }

    public void setEditButton(Button editButton) {
        this.editButton = editButton;
    }

    public ImageView isOffered(int offer) {

        //Create Tick Image View
        ImageView tickView = new ImageView();
        tickView.maxHeight(40);
        tickView.maxWidth(20);
        tickView.setFitWidth(20);
        tickView.setFitHeight(20);
        tickView.setPreserveRatio(true);

        //Set Tick if offered
        if (offer == 1) {
            Image tickImg = new Image(
                    this.getClass().getResourceAsStream("/resources/tick.png"));
            tickView = new ImageView(tickImg);
        }
        return tickView;
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

    public Button getDetailsButton() {
        return detailsButton;
    }

    public void setDetailsButton(Button detailsButton) {
        this.detailsButton = detailsButton;
    }

    public ImageView getT1Offer() {
        return t1Offer;
    }

    public void setT1Offer(ImageView t1Offer) {
        this.t1Offer = t1Offer;
    }

    public ImageView getT2Offer() {
        return t2Offer;
    }

    public void setT2Offer(ImageView t2Offer) {
        this.t2Offer = t2Offer;
    }

    public ImageView getT3Offer() {
        return t3Offer;
    }

    public void setT3Offer(ImageView t3Offer) {
        this.t3Offer = t3Offer;
    }

    public ImageView getTsOffer() {
        return tsOffer;
    }

    public void setTsOffer(ImageView tsOffer) {
        this.tsOffer = tsOffer;
    }

    //To String
    @Override
    public String toString() {
        return course_id.get();
    }
}
