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

    private IntegerProperty id;
    private IntegerProperty year;
    private StringProperty term;
    private StringProperty course_id;
    private StringProperty staff_name;
    private DoubleProperty weight;
    private Button editButton;
    private StringProperty warning1;
    private StringProperty warning2;

    // ObservableList<Allocation> allocations = AllocationTableController.allocationTable.getSelectionModel().getSelectedItems();
    public Allocation(int id, String course_id, int year, String term, double weight, String staff_id,String warning1,String warning2) {
        this.id = new SimpleIntegerProperty(id);
        this.year = new SimpleIntegerProperty(year);
        this.term = new SimpleStringProperty(term);
        this.course_id = new SimpleStringProperty(course_id);
        this.staff_name = new SimpleStringProperty(staff_id);
        this.weight = new SimpleDoubleProperty(weight);
        if(warning1==null){
            warning1="null";
        }else{
            warning1="exceed capacity";
        }
        this.warning1=new SimpleStringProperty(warning1);
        if(warning2==null){
            warning2="null";
        }else{
            warning1="exceed terms";
        }
        this.warning2=new SimpleStringProperty(warning2);
        this.editButton = new Button("Edit");
        
        
        System.out.println(this.warning1.get());

//        editButton.setOnAction(e -> {
//
//        
//        for (Allocation allocation : AllocationTableController.data) {
//            if (allocation.getButton() == editButton) {
//                System.out.println(allocation.getCourse_id());
//                System.out.println(allocation.getStaff_id());
//
//            }
//
//        }
//        });
        // AllocationTableController.data 
    }

    public Button getButton() {
        return editButton;
    }

    public void setButton(Button button) {
        this.editButton = button;
    }

    public String getCourse_id() {
        return course_id.get();
    }

    public void setCourse_id(String course_id) {
        this.course_id.set(course_id);
    }

    public String getStaff_name() {
        return staff_name.get();
    }

    public void setStaff_name(String staff_name) {
        this.staff_name.set(staff_name);
    }

    public int getYear() {
        return year.get();
    }

    public void setYear(int year) {
        this.year.set(year);
    }
    
    public int getId() {
        return id.get();
    }
    
    public void setId(int id) {
        this.id.set(id);
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
    public String getwarning1() {
        return warning1.get();
    }

    public void setwarning1(String warning1) {
        this.warning1.set(warning1);
    }
    public String getwarning2() {
        return warning2.get();
    }

    public void setwarning2(String warning2) {
        this.warning2.set(warning2);
    }
    



    //    public Allocation(){
//        this(0,"","","",0.0);
//    }
//    public Allocation(int year, String term,String course_id, String staff_name,double weight) {
//        this.year=new SimpleIntegerProperty(year);
//        this.term=new SimpleStringProperty(term);
//        this.course_id =new SimpleStringProperty(course_id);
//        this.staff_name = new SimpleStringProperty(staff_name);
//        this.weight=new SimpleDoubleProperty(weight);
//        this.button = new Button("Allocate");
//    } 
}
