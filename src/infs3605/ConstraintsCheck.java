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
    static String staff_type="";
    static int countterm=0;
    static double currentweight=0;
    static double newweight=0;
    static int staff_capacity=0;
    static String staff_name="";
    

    public void check(String courseid, String staffid, int year, String term) {
        ConstraintsCheck.warning.clear();
        getdatabasevalue(courseid, staffid, year,term);
        if ((currentweight+ newweight) > staff_capacity){ 
            warning.add("exceed weight capacity");
        }
        if (staff_type.equals("Research")) {
            if (countterm + 1 >= 3) {
                warning.add("exceed term capacity");
            }
        }
    }
//    public void deletecheck(String courseid, String staffid, int year, String term) {
//        ConstraintsCheck.warning.clear();
//        getdatabasevalue(courseid, staffid, year,term);
//        if ((currentweight- newweight) > staff_capacity){ 
//            warning.add("this staff will be exceed weight capacity");
//        }
//        if (staff_type.equals("Research")) {
//            if (countterm + 1 >= 3) {
//                warning.add("this staff will be exceed term capacity");
//            }
//        }  
//    }
    public boolean duplicateallocation(String courseid, String staffid, int year, String term){
        boolean exist=false;
        String existQuery="select * from Allocation\n" +
                            "where course_id='"+courseid+"' \n" +
                            "and staff_id='"+staffid+"'\n" +
                            "and allocation_year="+year+"\n" +
                            "and allocation_term='"+term+"'";
        try {
            ResultSet rs=database.getResultSet(existQuery);
            while(rs.next()){
                exist=true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConstraintsCheck.class.getName()).log(Level.SEVERE, null, ex);
        }
        return exist;
    }
    public void getdatabasevalue(String courseid, String staffid, int year, String term){
        String searchQuery = "SELECT staff_id,sum(weighting_term*0.002+face_time*0.1+prep_dev*0.1) as weight,count(DISTINCT allocation_term) as countterm FROM Allocation\n" +
"                inner join Weighting on Allocation.course_id=Weighting.course_id and allocation_year=year and allocation_term=term\n" +
"                where staff_id='"+staffid+"'\n" +
"                GROUP by staff_id";
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
                staff_name=rs.getString("fname")+" "+rs.getString("lname");
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
    }
}
