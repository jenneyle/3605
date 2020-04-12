package infs3605;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Mashilan
 */
public class Database {

    public static Connection conn;

    public static void openConnection() {
        if (conn == null) {
            try {
                conn = DriverManager.getConnection("jdbc:sqlite:database.db");
            } catch (SQLException ex) {

                ex.printStackTrace();
            } finally {
                if (conn != null) {
                    try {
                        System.out.println("finally block");
                     //   conn.close(); // <-- This is important
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }
   
   
    

    

    public ResultSet getResultSet(String sqlstatement) throws SQLException {
        openConnection();
        ResultSet RS = conn.createStatement().executeQuery(sqlstatement);
        return RS;

    }

    public void insertStatement(String insert_query) throws SQLException {
        java.sql.Statement stmt = null;
        openConnection();
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(insert_query);
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }
}
