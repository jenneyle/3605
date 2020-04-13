/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infs3605;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Rjian
 */
public class CourseHistoricalAllocation {
    
    private StringProperty yearTerm;
    private StringProperty staffName;
    
    public CourseHistoricalAllocation (String yearTerm, String staffName) {
        this.yearTerm = new SimpleStringProperty(yearTerm);
        this.staffName = new SimpleStringProperty(staffName);
    }
    
    public String getYearTerm() {
        return yearTerm.get();
    }

    public void setYearTerm(String yearTerm) {
        this.yearTerm = new SimpleStringProperty(yearTerm);
    }
    
    public String getStaffName() {
        return staffName.get();
    }

    public void setStaffName(String staffName) {
        this.staffName = new SimpleStringProperty(staffName);
    }
    
}
