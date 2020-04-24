/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infs3605;

import com.microsoft.schemas.office.visio.x2012.main.CellType;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.Cell;
import static org.apache.poi.ss.usermodel.CellType.NUMERIC;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Mashilan
 */
public class Importing_excel_allocationtable {

    public static Database database = new Database();

    public static void reading_excel(File file) throws IOException {
        ArrayList<String> record = new ArrayList<>();
        FileInputStream fileopen = new FileInputStream(file);
        XSSFWorkbook workbook = new XSSFWorkbook(fileopen);
        XSSFSheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.iterator();
        int current_row = 0;
        int successrow=0;
        //System.out.println("i have got the file");
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            current_row = current_row + 1;
            // System.out.println("in row "+current_row);
            if (current_row == 1) {
                continue;
            } else {
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    //System.out.println(cell.getCellType().toString());
                    switch (cell.getCellType().toString()) {
                        case "NUMERIC":
                            record.add(String.valueOf(cell.getNumericCellValue()));

                            break;
                        case "STRING":
                            record.add(cell.getStringCellValue());
                            break;
                    }
                    //System.out.println(cell.getColumnIndex());
                    if (cell.getColumnIndex() == 7) {
                        int year = (int) Double.parseDouble(record.get(0));
                        int lic = (int) Double.parseDouble(record.get(6));
                        double weight = Double.parseDouble(record.get(7));
                        String courseid=record.get(2);
                        String term=record.get(1);

                        //System.out.println("in column "+cell.getColumnIndex());
                        //System.out.println(year);
                        String staffid="";
                        boolean courseexist = false;
                        boolean duplicate=false;
                        String checkstaff = "select staff_id from staff where Fname='"+record.get(3)+"' and Lname='"+record.get(4)+"';";
                        String checkcourse = "select * from weighting where year=" + year + " and course_id='" + courseid + "' and term='" + term + "';";
                        try {
                            //System.out.println(checkstaff);
                            if(database.getResultSet(checkstaff).next()) {
                                staffid=database.getResultSet(checkstaff).getString(1);
                                String checkduplicate="select * from allocation where allocation_year="+year+" and course_id='" + courseid + "' and allocation_term='" + term + "' and staff_id='"+staffid+"';";
                                if(database.getResultSet(checkduplicate).next()){
                                    duplicate=true;
                                    
                                }
                            }
                            //System.out.println(duplicate);
                            //System.out.println(checkcourse);
                            if (database.getResultSet(checkcourse).next()) {
                                courseexist = true;
                            }
                            if(!staffid.isEmpty()&& courseexist==true&&duplicate==false) {
                                String insertrow = "insert into Allocation (allocation_year,allocation_term,course_id,\n"
                                + "staff_id,allocation_description,lic,allocation_weight)\n"
                                + "VALUES (" + year + ",'" + term + "','" + courseid + "','" + staffid + "','" + record.get(5) + "'," + lic + "," + weight + ");";
                                database.insertStatement(insertrow);
                                successrow=successrow+1;
                                System.out.println("inserted");
                            }
                            record.clear();
                        } catch (SQLException ex) {
                            Logger.getLogger(Importing_excel_allocationtable.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }

        }
        String conclusion = "Successfully imported " + successrow + " out of " + (current_row-1) + " records from excel";
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Import Complete");
        alert.setHeaderText(null);
        alert.setContentText(conclusion);
        alert.showAndWait();
        record.clear();

    }

}
