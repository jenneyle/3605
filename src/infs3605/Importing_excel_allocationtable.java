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
        //ArrayList<Integer> recordi = new ArrayList<>();
        FileInputStream fileopen = new FileInputStream(file);
        XSSFWorkbook workbook = new XSSFWorkbook(fileopen);
        XSSFSheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.iterator();
        int current_row = 0;
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
                    if (cell.getColumnIndex() == 6) {
                        double weight = Double.parseDouble(record.get(6));
                        int year = (int) Double.parseDouble(record.get(0));
                        int lic = (int) Double.parseDouble(record.get(5));

                        //System.out.println("in column "+cell.getColumnIndex());
                        //System.out.println(year);
                        boolean staffexist = false;
                        boolean courseexist = false;
                        String checkstaff = "select * from staff where staff_id='" + record.get(3) + "';";
                        String checkcourse = "select * from weighting where year=" + year + " and course_id='" + record.get(2) + "' and term='" + record.get(1) + "';";
                        String insertrow = "insert into Allocation (allocation_year,allocation_term,course_id,\n"
                                + "staff_id,allocation_description,lic,allocation_weight)\n"
                                + "VALUES (" + year + ",'" + record.get(1) + "','" + record.get(2) + "','" + record.get(3) + "','" + record.get(4) + "'," + lic + "," + weight + ");";
                        try {
                            //System.out.println(checkstaff);
                            if(database.getResultSet(checkstaff).next()) {
                                staffexist = true;
                                //System.out.println(staffexist);
                            }
                            //System.out.println(checkcourse);
                            if (database.getResultSet(checkcourse).next()) {
                                courseexist = true;
                                //System.out.println(courseexist);
                            }
                            if(staffexist ==true&& courseexist==true) {
                                database.insertStatement(insertrow);
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

    }

}
