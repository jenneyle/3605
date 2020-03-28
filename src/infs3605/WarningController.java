/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infs3605;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Mashilan
 */
public class WarningController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    Button con;
    @FXML
    Button cancel;
    @FXML
    TableColumn<ArrayList,String> warnings;
    @FXML
    TableView table;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> data = FXCollections.observableArrayList();
        for(String i:ConstraintsCheck.warning){
            data.add(i);
        }
        table.setItems(data);
    }
    public void handleConBtn(ActionEvent event){
        StaffAllocationController.knowledgewarning=true;
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
    public void handleCancelBtn(ActionEvent event){
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
    
}
