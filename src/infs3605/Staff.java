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
 * @author Rjian
 */
public class Staff {

    //Properties
    private StringProperty staffId;
    private StringProperty firstName;
    private StringProperty lastName;
    private StringProperty staffType;
    private DoubleProperty staffCapacity;
    private StringProperty leftoverCapacity;
    private Button editButton;
    private Button detailsButton;
    private Button deleteButton;

    //Constructor
    public Staff(String staffId, String firstName, String lastName, String staffType, double staffCapacity, String leftoverCapacity) {
        this.staffId = new SimpleStringProperty(staffId);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.staffType = new SimpleStringProperty(staffType);
        this.staffCapacity = new SimpleDoubleProperty(staffCapacity);
        this.leftoverCapacity = new SimpleStringProperty(leftoverCapacity);
        this.editButton = new Button("Edit");
        this.detailsButton = new Button("Details");
        this.deleteButton = new Button("Delete");
    }

    public Button getDeleteButton() {
        return deleteButton;
    }

    public void setDeleteButton(Button deleteButton) {
        this.deleteButton = deleteButton;
    }

    public Button getDetailsButton() {
        return detailsButton;
    }

    public void setDetailsButton(Button detailsButton) {
        this.detailsButton = detailsButton;
    }

    //Getters and Setters
    public String getStaffId() {
        return staffId.get();
    }

    public void setStaffId(String staffId) {
        this.staffId = new SimpleStringProperty(staffId);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String firstName) {
        this.firstName = new SimpleStringProperty(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String lastName) {
        this.lastName = new SimpleStringProperty(lastName);
    }

    public String getStaffType() {
        return staffType.get();
    }

    public void setStaffType(String staffType) {
        this.staffType = new SimpleStringProperty(staffType);
    }

    public double getStaffCapacity() {
        return staffCapacity.get();
    }

    public void setStaffCapacity(double staffCapacity) {
        this.staffCapacity = new SimpleDoubleProperty(staffCapacity);
    }

    public String getLeftoverCapacity() {
        return leftoverCapacity.get();
    }

    public void setLeftoverCapacity(String leftoverCapacity) {
        this.leftoverCapacity = new SimpleStringProperty(leftoverCapacity);
    }
    
    

    public Button getEditButton() {
        return editButton;
    }

    public void setEditButton(Button editButton) {
        this.editButton = editButton;
    }

}
