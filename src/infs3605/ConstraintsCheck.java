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
    static String staff_type;
    public static ArrayList<String> countterm = new ArrayList<String>();
    static double currentweight;
    static double newweight;
    static int staff_capacity;
    static String staff_name;
    static boolean casual_staff;
    static String licname;
    

    public void check(String courseid, String staffid, int year, String term, int liccheck,double weight) {
        cleandata();
        getdatabasevalue(courseid, staffid, year,term);
        if ((currentweight+ weight) > staff_capacity){ 
            warning.add(staff_name + " will exceed weight capacity");
            
        }
        if (staff_type.equals("Full-time Teaching/Research")) {
            countterm.add(term);
            long distinctterm =countterm.stream().distinct().count();
            System.out.println("countterm: "+distinctterm);
            if (distinctterm> 2) {
                warning.add(staff_name +"as a Full-time Teaching/Research staff will exceed year capacity");
            }
        }
        if(staff_type.equals("Casual Teaching")&&casual_staff==true){
            warning.add(staff_name +" as a Casual staff already allocated to a courses in"+term);
        }
        if(!licname.equals("null") && liccheck==1){
            warning.add(licname+" has been allocted as LIC in this couse" );
        }
        for( String i :warning){
            System.out.println(i);
        }
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
//    public void updatecoursecheck(String courseid, double weight,String term, int year){
//        ArrayList<String> warning=new ArrayList<String>();
//        ArrayList<String> affectStaff=new ArrayList<String>();
//        ArrayList<Integer> affectStaffcapacity=new ArrayList<Integer>();
//        String courseStaff="﻿SELECT s.staff_id,s.staff_capacity from Allocation a \n" +
//                            "join Staff  s on  a.staff_id=s.staff_id\n" +
//                            "where course_id='"+courseid+"' and allocation_year="+year+" and allocation_term='"+term+"'";
//        ResultSet rs1;
//        try {
//            rs1 = database.getResultSet(courseStaff);
//            while(rs1.next()){
//                affectStaff.add(rs1.getString(1));
//                affectStaffcapacity.add(rs1.getInt(2));
//            }
//            if(affectStaff.size()!=0){
//                for(int i=0;i<affectStaff.size();i++){
//                    String checkweight="﻿select sum(weighting_term)  as weight from allocation \n" +
//                                "inner join Weighting on Allocation.course_id=Weighting.course_id a\n" +
//                                "nd allocation_year=year and allocation_term=term\n" +
//                                "where staff_id='"+affectStaff.get(i)+"' and allocation_year="+year+" and courseid!='"+courseid+"\n" +
//                                "group by staff_id";
//                    ResultSet rs2=database.getResultSet(checkweight);
//                    while(rs2.next()){
//                        if((rs2.getInt(1)+weight)>affectStaffcapacity.get(i)){
//                            warning.add("You now have unallocated capacity for "+courseid+". Do you still want to continue? Y/N");
//                        }
//                    }
//                }
//                
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(ConstraintsCheck.class.getName()).log(Level.SEVERE, null, ex);
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
        String searchQuery = "select allocation_weight, allocation_term from Allocation";
        //System.out.println(searchQuery);
        String searchStaff="Select * from Staff where staff_id='"+staffid+"';";
        String casualQuery="select * from Allocation where staff_id='"+staffid+"'and allocation_term='"+term+"'";
        String licQuery="select lic,Fname,Lname from Allocation \n"
                        +"join Staff on Staff.staff_id=Allocation.staff_id \n"
                        + "where allocation_term='"+term+"' and allocation_year= "+year+" and course_id= '"+courseid+"' and lic=1;";
        //System.out.println(searchQuery);
        try {
            ResultSet rs=database.getResultSet(searchStaff);
            while(rs.next()){
                staff_capacity=rs.getInt("staff_capacity");
                staff_type=rs.getString("staff_type");
                staff_name=rs.getString("fname")+" "+rs.getString("lname");
            }
            ResultSet rs1 = database.getResultSet(searchQuery);
            while(rs1.next()){
                currentweight=currentweight+rs1.getDouble("allocation_weight");
                countterm.add(rs1.getString("allocation_term"));
                //System.out.println(rs1.getString("allocation_term"));
            }
//            for(String i : countterm){
//                System.out.println(i);
//            }
            if(database.getResultSet(casualQuery).next()){
                casual_staff=true;
            }else{
                casual_staff=false;
            }
            ResultSet rs3=database.getResultSet(licQuery);
            while(rs3.next()){
                licname=rs3.getString(2)+" "+rs3.getString(3);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConstraintsCheck.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void cleandata(){
        staff_type="";
        countterm.clear();
        currentweight=0;
        newweight=0;
        staff_capacity=0;
        staff_name="";
        casual_staff=false;
        licname="null";
        ConstraintsCheck.warning.clear();
    }
}
