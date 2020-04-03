/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infs3605;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mashilan
 */
public class ConstraintsCheck {

    public Database database = new Database();
    public static ArrayList<String> warning = new ArrayList<String>();
    

    public void check(String courseid, String staffid, int year, String term) {
        String staff_type="";
        int countterm=0;
        double currentweight=0;
        double newweight=0;
        int staff_capacity=0;
        String searchQuery = "SELECT Staff.staff_id,staff_capacity,staff_type,sum(weighting_term*0.002+face_time*0.1+prep_dev*0.1) as weight,count(DISTINCT allocation_term) as countterm FROM Allocation\n"
                + "inner join Weighting on Allocation.course_id=Weighting.course_id and allocation_year=year and allocation_term=term\n"
                + "inner join Staff on Staff.staff_id=Allocation.staff_id\n"
                + "where Staff.staff_id='" + staffid + "'\n"
                + "GROUP by Staff.staff_id";
        //System.out.println(searchQuery);
        String allocateweightQ = "SELECT (weighting_term*0.002+face_time*0.1+prep_dev*0.1) as weight FROM Allocation\n"
                + "inner join Weighting on Allocation.course_id=Weighting.course_id and allocation_year=year and allocation_term=term\n"
                + "where Weighting.course_id='" + courseid + "'and year=" + year + " and term='" + term + "'";
        String searchStaff="Select * from Staff where staff_id='"+staffid+"';";
        //System.out.println(allocateweightQ);
        try {
            ResultSet rs=database.getResultSet(searchStaff);
            while(rs.next()){
                staff_capacity=rs.getInt("staff_capacity");
                staff_type=rs.getString("staff_type");
            }
            ResultSet rs1 = database.getResultSet(searchQuery);
            while(rs1.next()){
                currentweight=rs1.getDouble("weight");
                countterm=rs1.getInt("countterm");
            }
            
            ResultSet rs2 = database.getResultSet(allocateweightQ);
            while(rs2.next()){
                newweight=rs2.getDouble("weight");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConstraintsCheck.class.getName()).log(Level.SEVERE, null, ex);
        }
        if ((currentweight+ newweight) > staff_capacity){ 
            warning.add("this staff will be exceed capacity if allocate to this course");
        }
        if (staff_type.equals("Research")) {
            if (countterm + 1 >= 3) {
                warning.add("as a research staff, he already been allocation for 2 terms this year");
            }
        }
    }
}
