/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infs3605;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
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
    AnchorPane pane;
    @FXML
    Label title;
    @FXML
    TextArea warnings;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String name=ConstraintsCheck.staff_name;
        int warningcount=0;
        title.setText("You have encounter "+ConstraintsCheck.warning.size()+"/ errors");
        for(String i :ConstraintsCheck.warning){
            warnings.setText(i+"\n");
        }
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
