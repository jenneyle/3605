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
        boolean check = true;
        //ArrayList<String> warning = new ArrayList<String>();
        String searchQuery = "SELECT Staff.staff_id,staff_capacity,staff_type,sum(weighting_term*0.002+face_time*0.1+prep_dev*0.1) as weight,count(DISTINCT allocation_term) as countterm FROM Allocation\n"
                + "inner join Weighting on Allocation.course_id=Weighting.course_id and allocation_year=year and allocation_term=term\n"
                + "inner join Staff on Staff.staff_id=Allocation.staff_id\n"
                + "where Staff.staff_id='" + staffid + "'\n"
                + "GROUP by Staff.staff_id";
        String allocateweightQ = "SELECT (weighting_term*0.002+face_time*0.1+prep_dev*0.1) as weight FROM Allocation\n"
                + "inner join Weighting on Allocation.course_id=Weighting.course_id and allocation_year=year and allocation_term=term\n"
                + "where Weighting.course_id='" + courseid + "'and year=" + year + " and term='" + term + "'";
        try {
            //double weight=rs2.getDouble("weight");
            ResultSet rs1 = database.getResultSet(searchQuery);
            if (rs1.getString("staff_type").equals("research")) {
                int countterm = rs1.getInt("countterm");
                if (countterm + 1 >= 3) {
                    check = false;
                    warning.add("as a research staff, he already been allocation for 2 terms this year");
                }
            }
            ResultSet rs2 = database.getResultSet(allocateweightQ);
            if (rs1.getDouble("weight") + rs2.getDouble("weight") > rs1.getInt("staff_capacity")) {
                check = false;
                warning.add("this staff will be exceed capacity if allocate to this course");
            }

        } catch (SQLException ex) {
            Logger.getLogger(ConstraintsCheck.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
