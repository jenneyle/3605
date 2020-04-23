/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infs3605;

import com.sun.prism.impl.Disposer;
import javafx.scene.control.TableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Rjian
 */
public class CourseAvailabilityTickCell extends TableCell<Disposer.Record, Boolean> {

    ImageView tickView;

    public CourseAvailabilityTickCell(int offer) {
        tickView.setFitWidth(64);
        tickView.setFitHeight(64);
        tickView.setPreserveRatio(true);
        if (offer == 1) {
            Image tickImg = new Image(
                    this.getClass().getResourceAsStream("/resources/tick.png"));
            tickView = new ImageView(tickImg);
        }
    }

    public ImageView getTickView() {
        return tickView;
    }

    public void setTickView(ImageView tickView) {
        this.tickView = tickView;
    }
    
    
}
