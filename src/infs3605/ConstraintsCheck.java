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
    static boolean casual_staff=false;
    

    public boolean check(String courseid, String staffid, int year, String term) {
        boolean warning_exist=false;
        ConstraintsCheck.warning.add("");
        ConstraintsCheck.warning.add("");
        ConstraintsCheck.warning.add("");
        getdatabasevalue(courseid, staffid, year,term);
        if ((currentweight+ newweight) > staff_capacity){ 
            warning.set(0,"exceed weight capacity");
            warning_exist=true;
        }
        if (staff_type.equals("Research")) {
            if (countterm + 1 >= 3) {
                warning.set(1,"exceed term capacity");
                warning_exist=true;
            }
        }
        if(staff_type.equals("Casual")){
            
        }
        return warning_exist;
    }
//    public void deletecheck(String courseid, String staffid, int year, String term) {
//        ConstraintsCheck.warning.clear();
//        getdatabasevalue(courseid, staffid, year,term);
//        if ((currentweight- newweight) <= staff_capacity){ 
//        }
//        if (staff_type.equals("Research")) {
//            if (countterm - 1 < 3) {
//                String update_warning="";
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
        String searchQuery = "﻿select sum(weighting_term)  as weight,count(distinct allocation_term) as countterm from allocation \n" +
                                "inner join Weighting on Allocation.course_id=Weighting.course_id a\n" +
                                "nd allocation_year=year and allocation_term=term\n" +
                                "where staff_id='"+staffid+"' and allocation_year="+year+"\n" +
                                "group by staff_id";
        //System.out.println(searchQuery);
        String allocateweightQ = "﻿select weighting_term from Weighting"
                + "where Weighting.course_id='" + courseid + "'and year=" + year + " and term='" + term + "'";
        String searchStaff="Select * from Staff where staff_id='"+staffid+"';";
        String casualQuery="﻿select * from Allocation where staff_id='"+staffid+"'and allocation_term='"+term+"'";
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
            if(database.getResultSet(casualQuery).next()){
                casual_staff=true;
            }else{
                casual_staff=false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConstraintsCheck.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void cleandata(){
        String staff_type="";
        int countterm=0;
        double currentweight=0;
        double newweight=0;
        int staff_capacity=0;
        String staff_name="";
        boolean casual_staff=false;
        ConstraintsCheck.warning.clear();
    }
}
