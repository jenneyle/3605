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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author SOPHIA
 */
public class CourseInfoController implements Initializable {

    @FXML
    Button back;

    @FXML
    Text courseCode;
    @FXML
    Text courseName;
    @FXML
    ImageView t1Offer;
    @FXML
    ImageView t2Offer;
    @FXML
    ImageView t3Offer;
    @FXML
    ImageView tsOffer;
    
    String courseID;
    
    Course course;
    Database database = new Database();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void start(String courseID) {
        try {
            ResultSet rs = database.getResultSet("SELECT course_id, course_name"
                    + ", t1_offer, t2_offer, t3_offer, summer_Offer"
                    + " FROM Courses"
                    + " WHERE course_id = '" + courseID + "'"
            );
            while (rs.next()) {
                course = new Course(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getInt(6));
                
                courseCode.setText(course.getCourse_id());
                courseName.setText(course.getCourseName());
                //If course is availble in aterm show the green tick
                if (course.getT1Offer() == 1) {
                    t1Offer.setVisible(true);
                }
                if (course.getT2Offer() == 1) {
                    t2Offer.setVisible(true);
                }
                if (course.getT3Offer() == 1) {
                    t3Offer.setVisible(false);
                }
                if (course.getTsOffer() == 1) {
                    tsOffer.setVisible(true);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    @FXML
    public void handleBackButton(ActionEvent event) throws IOException {
        Stage stage = (Stage) back.getScene().getWindow();
        stage.close();
    }

    //For Course
    public void intialiser(ActionEvent event, String courseID, String page) throws IOException {
        System.out.println("Switching pages from Course Info");
        Parent parent = FXMLLoader.load(getClass().getResource(page));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        
    }

}
