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
 * @author Mashilan
 */
public class Allocation {
    private IntegerProperty year;
    private StringProperty term;
    private StringProperty course_id;
    private StringProperty staff_id;
    private DoubleProperty weight;
    private Button button;
    
    public Allocation(){
        this(0,"","","",0.0);
    }
   

    public Allocation(int year, String term,String course_id, String staff_id,double weight) {
        this.year=new SimpleIntegerProperty(year);
        this.term=new SimpleStringProperty(term);
        this.course_id =new SimpleStringProperty(course_id);
        this.staff_id = new SimpleStringProperty(staff_id);
        this.weight=new SimpleDoubleProperty(weight);
        this.button = new Button("Allocate");
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }

    public String getCourse_id() {
        return course_id.get();
    }

    public void setCourse_id(String course_id) {
        this.course_id.set(course_id);
    }

    public String getStaff_id() {
        return staff_id.get();
    }

    public void setStaff_id(String staff_id) {
        this.staff_id.set(staff_id);
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

    public double getWeight() {
        return weight.get();
    }

    public void setWeight(double weight) {
        this.weight.set(weight);
    }
    

   
    
    
    
    
}
