/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infs3605;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import org.apache.commons.codec.digest.DigestUtils;
import static org.apache.commons.codec.digest.MessageDigestAlgorithms.SHA3_256;
import static org.apache.commons.codec.digest.MessageDigestAlgorithms.SHA_256;
import sun.security.provider.MD5;

/**
 * FXML Controller class
 *
 * @author Mashilan
 */
public class SignupController implements Initializable {

    /**
     * Initializes the controller class.
     */
    Database database=new Database();
    PageSwitchHelper pageswitcher=new PageSwitchHelper();
    @FXML
    private TextField username;
    @FXML
    private PasswordField p1;
    @FXML
    private PasswordField p2;
    @FXML
    private Button signupButton;
    @FXML
    private Label showmessage1;
    @FXML
    private Label showmessage2;
    @FXML
    private void handlesignupButton(ActionEvent event){
        String pw1=p1.getText();
        String pw2=p2.getText();
        boolean checkusername=true;
        boolean checkpassword=true;
        //String username=username.getText();
        String username_check="select username from users where username='"+username.getText()+"';";
        try {
            if(database.getResultSet(username_check).next()){
                showmessage1.setText("Username already exist");
                showmessage1.setTextFill(Color.web("#DC143C"));
                checkusername=false;
                username.clear();
            }else{
                showmessage1.setText("Good username");
                showmessage1.setTextFill(Color.web("#7FFF00"));
            }           
        } catch (SQLException ex) {
            Logger.getLogger(SignupController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(pw1.equals(pw2)){
            showmessage2.setText("Good password");
            showmessage2.setTextFill(Color.web("#7FFF00"));
        }else{
            showmessage2.setText("Passwords don't match");
            showmessage2.setTextFill(Color.web("#DC143C"));
            p1.clear();
            p2.clear();
            checkpassword=false;
        }
        if(checkusername==true && checkpassword==true){
            String hashpw = new DigestUtils(SHA_256).digestAsHex(pw1);
            String signupuser="insert into users (username,password) values('"+username.getText()+"','"+hashpw+"');";
            try {
               database.insertStatement(signupuser);
            } catch (SQLException ex) {
                Logger.getLogger(SignupController.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                pageswitcher.switcher(event, "Login.fxml");
            } catch (IOException ex) {
                Logger.getLogger(SignupController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
}
