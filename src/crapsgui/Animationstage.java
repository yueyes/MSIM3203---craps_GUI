/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crapsgui;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.fxyz.shapes.primitives.CuboidMesh;

/**
 *
 * @author chi woon
 */
public class Animationstage{
         final double unit = 80.0;

   
public Stage Ani(int positionX, int positionY,Rotate rx,Rotate ry,Rotate rz){
CuboidMesh cuboid = new CuboidMesh(unit,unit,unit);
cuboid.setTextureModeImage(getClass().getResource("Images/dice.png").toExternalForm());
    cuboid.setTranslateX(unit);
    cuboid.setTranslateY(unit);
    cuboid.getTransforms().addAll(rx,ry,rz);
        final Parent root = new Group(cuboid);
        Scene scene = new Scene(root, unit * 2, unit * 2, Color.TRANSPARENT);

        Stage stage = new Stage();
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX( positionX);
        stage.setY( positionY);
                        stage.setScene(scene);
                                stage.initStyle(StageStyle.TRANSPARENT);
                                
                               return stage; 
}   
    
        public void animate(Rotate rx,Rotate ry,Rotate rz, int rotatey, int rotatex,int rotatez) {
        Timeline animation = new Timeline();
        animation.getKeyFrames().addAll(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(ry.angleProperty(), 0d)
                        , new KeyValue(rx.angleProperty(), 0d)
                        , new KeyValue(rz.angleProperty(), 0d)
                ),
                new KeyFrame(Duration.seconds(1),
                        new KeyValue(ry.angleProperty(), rotatey)
                        , new KeyValue(rx.angleProperty(), rotatex)
                        , new KeyValue(rz.angleProperty(), rotatez)
                ));
        animation.setCycleCount(1);
        animation.play();
    }
}
