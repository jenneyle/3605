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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.apache.commons.codec.digest.DigestUtils;
import static org.apache.commons.codec.digest.MessageDigestAlgorithms.SHA_256;

/**
 * FXML Controller class
 *
 * @author Mashilan
 */
public class LoginController implements Initializable {

    /**
     * Initializes the controller class.
     */
    public static String loggedInUser;
    Database database= new Database();
    PageSwitchHelper pageswticher=new PageSwitchHelper();
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Button loginButton;
    @FXML
    private Button signupButton;
    @FXML
    private Label incorrectMessage;
    @FXML
    private ImageView logoimage;
    @FXML
    private void handleloginButton(ActionEvent event) throws IOException, SQLException {
        String user = username.getText();
        String correctPassword = password.getText();
        try {
            String hashpw = new DigestUtils(SHA_256).digestAsHex(correctPassword);
            String loginQuery = "SELECT * FROM USERS WHERE username ='" + user + "' AND PASSWORD = '" + hashpw + "';";
            
            ResultSet loginRS=database.getResultSet(loginQuery);
            if (loginRS.next()) {
                pageswticher.switcher(event, "DisplayAllocation.fxml");
                loggedInUser = user;
            } else {
                username.clear();
                password.clear();
                incorrectMessage.setVisible(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void handlesignupButton(ActionEvent event){
        try {
            pageswticher.switcher(event, "Signup.fxml");
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        incorrectMessage.setVisible(false);
        Image image=new Image("/resources/LecticLogo.png");
        logoimage.setImage(image);
    }
    
}
