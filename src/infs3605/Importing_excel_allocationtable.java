/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infs3605;

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
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                record.add(cell.getStringCellValue());
                if (cell.getColumnIndex() == 7) {
                    int year = Integer.valueOf(record.get(0));
                    int lic = Integer.valueOf(record.get(5));
                    double weight = Double.valueOf(record.get(6));
                    String insertrow = "insert into Allocation (allocation_year,allocation_term,course_id,\n"
                            + "staff_id,allocation_description,lic,allocation_weight)\n"
                            + "VALUES (" + year + ",'" + record.get(1) + "','" + record.get(2) + "','" + record.get(3) + "','" + record.get(4) + "'," + lic + "," + weight + ");";
                    try {
                        database.insertStatement(insertrow);
                        record.clear();
                    } catch (SQLException ex) {
                        Logger.getLogger(Importing_excel_allocationtable.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }

    }

}
