/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infs3605;

import static infs3605.Database.conn;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * FXML Controller class
 *
 * @author SOPHIA
 */
public class ReportsController implements Initializable {

    PageSwitchHelper pageSwitcher = new PageSwitchHelper();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    public void handleImportBtn(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile.exists()) {
            try {
                Importing_excel_allocationtable.reading_excel(selectedFile);
            } catch (IOException ex) {
                Logger.getLogger(AllocationTableController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @FXML
    public void handleExportBtn(ActionEvent event) throws IOException {
        try {

            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM Allocation JOIN Staff ON Allocation.staff_id = Staff.staff_id");
            Workbook wb = new XSSFWorkbook();
            Sheet sheet = wb.createSheet("Current Allocations");

            CellStyle headerCellStyle = wb.createCellStyle();
            org.apache.poi.ss.usermodel.Font headerFont = wb.createFont();
            headerFont.setBold(true);
            headerCellStyle.setFont(headerFont);

            Row headerRow = sheet.createRow(0);

            String[] headers = {"Year", "Term", "Course Code", "Staff ID", "LIC?", "Allocation Weight", "First Name", "Last Name"};
            for (int i = 0; i < headers.length; i++) {
                org.apache.poi.ss.usermodel.Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerCellStyle);
            }
            int index = 1;

            while (rs.next()) {
                Row row = sheet.createRow(index);

                row.createCell(0).setCellValue(rs.getString(2));
                row.createCell(1).setCellValue(rs.getString(3));
                row.createCell(2).setCellValue(rs.getString(4));
                row.createCell(3).setCellValue(rs.getString(5));
                row.createCell(4).setCellValue(rs.getString(7));
                row.createCell(5).setCellValue(rs.getString(8));
                row.createCell(6).setCellValue(rs.getString(10));
                row.createCell(7).setCellValue(rs.getString(11));

                index++;
            }

            FileOutputStream fileOut = new FileOutputStream("Current Allocations.xlsx");

            wb.write(fileOut);
            fileOut.close();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Export Downloaded");
            alert.setHeaderText(null);
            alert.setContentText("Export to excel spreadsheet is complete");
            alert.showAndWait();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    public void handleAllocateBtn(MouseEvent event) throws IOException {
        pageSwitcher.switcher(event, "StaffAllocation.fxml");
    }

    //button to view course weightings
    @FXML
    public void handleWeightingBtn(MouseEvent event) throws IOException {
        pageSwitcher.switcher(event, "WeightingTable.fxml");
    }

    //button to view staff details
    @FXML
    public void handleStaffBtn(MouseEvent event) throws IOException {
        pageSwitcher.switcher(event, "StaffTable.fxml");
    }

    @FXML
    public void handleCourseBtn(MouseEvent event) throws IOException {
        pageSwitcher.switcher(event, "CourseTable.fxml");
    }

    @FXML
    public void handleCurrentAlloBtn(MouseEvent event) throws IOException {
        pageSwitcher.switcher(event, "AllocationTable.fxml");
    }

    @FXML
    public void handleLogoutBtn(MouseEvent event) throws IOException {
        pageSwitcher.switcher(event, "Login.fxml");
    }

    @FXML
    public void handleReportsBtn(MouseEvent event) throws IOException {
        pageSwitcher.switcher(event, "Reports.fxml");

    }
}
