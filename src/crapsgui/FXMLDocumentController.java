/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crapsgui;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.transform.Rotate;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class FXMLDocumentController implements Initializable {
    
    private int die1, die2, sumofdice, point, wincount = 0, losecount = 0;
    private Database database = new Database();
    private String rule = "You roll two dice. Each die has six faces, which contain one,two,three,four,five and six spots, respectively.\n After the dice have come to rest, the sum of the spots on the two upward faces is calculated.\n If the sum is 7 or 11 on the first throw, you win.\nIf the sum is 2,3 or 12 on the first throw(called \"craps\",you lose(i.e.,the \"house\" wins).\nIf the sum is 4,5,6,8,9 or 10 on the first throw, that sum becomes your \"point\".\n To win, you must continue rolling the dice until you \"make your point\"(i.e.,roll that same point value). \nYou lose by rolling a 7 before making your point.";

    @FXML
    private Label labelshow;
    @FXML
    private Label craps;
    @FXML
    private GridPane gridpane;
    @FXML
    public TextField student_id;
    @FXML
    private Button playbutton;
    @FXML
    private Button closebutton;
    @FXML
    private Label labelresult;
    @FXML
    private Button statbtn;
    @FXML
    private Button rules;

    @FXML
    private void press() {//method to handle if player don't want to play
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Result");
        alert.setHeaderText("");
        alert.setContentText("Thanks for playing the game");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            System.exit(0);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //initialize the status of playbutton
        playbutton.setDisable(true);
        //Set the backgroundimage of stage and cursor be cross
        BackgroundImage myBI = new BackgroundImage(new Image(getClass().getResource("Images/craps.jpg").toExternalForm(), 995, 663, false, true),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        gridpane.setBackground(new Background(myBI));
        gridpane.setCursor(Cursor.CROSSHAIR);
        labelresult.setStyle("-fx-text-fill: red");
        //set the tooptip for the rule of craps
        Tooltip tooltip = new Tooltip();
        tooltip.setText(rule);
        tooltip.setStyle("-fx-font-size:12");
        craps.setTooltip(tooltip);
        //limit the length of inputed SID
student_id.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
                if (student_id.getText().length() > 7) {
                    String s = student_id.getText().substring(0, 7);
                    student_id.setText(s);
                }
                if (student_id.getText().length() > 0) {
                    playbutton.setDisable(false);
                }
                if (student_id.getText().length() == 0) {
                    playbutton.setDisable(true);
                }

            }
        });

        /*Set the effect of button*/
        playbutton.setOnMouseEntered((MouseEvent e) -> {
            if (student_id.getText().length() > 0) {
                playbutton.setStyle("-fx-background-color: slateblue; -fx-text-fill: white; -fx-border-radius: 20; -fx-background-radius: 20; -fx-padding: 5;");
            }
        });
        playbutton.setOnMouseExited((MouseEvent e) -> {
            playbutton.setStyle("-fx-background-color: #DFB951; -fx-text-fill: #006464; -fx-border-radius: 20; -fx-background-radius: 20; -fx-padding: 5;");
        });
        statbtn.setOnMouseEntered((MouseEvent e) -> {
            statbtn.setStyle("-fx-background-color: slateblue; -fx-text-fill: white; -fx-border-radius: 20; -fx-background-radius: 20; -fx-padding: 5;");
        });
        statbtn.setOnMouseExited((MouseEvent e) -> {
            statbtn.setStyle("-fx-background-color: #DFB951; -fx-text-fill: #006464; -fx-border-radius: 20; -fx-background-radius: 20; -fx-padding: 5;");
        });
        closebutton.setOnMouseEntered((MouseEvent e) -> {
            closebutton.setStyle("-fx-background-color: slateblue; -fx-text-fill: white; -fx-border-radius: 20; -fx-background-radius: 20; -fx-padding: 5;");
        });
        closebutton.setOnMouseExited((MouseEvent e) -> {
            closebutton.setStyle("-fx-background-color: #DFB951; -fx-text-fill: #006464; -fx-border-radius: 20; -fx-background-radius: 20; -fx-padding: 5;");
        });
        rules.setOnMouseEntered((MouseEvent e) -> {
            rules.setStyle("-fx-background-color: slateblue; -fx-text-fill: white; -fx-border-radius: 20; -fx-background-radius: 20; -fx-padding: 5;");
        });
        rules.setOnMouseExited((MouseEvent e) -> {
            rules.setStyle("-fx-background-color: #DFB951; -fx-text-fill: #006464; -fx-border-radius: 20; -fx-background-radius: 20; -fx-padding: 5;");
        });

    }

    @FXML
    private void rollingdice() throws InterruptedException {
        //declare all variable and object needed
        student_id.setDisable(true);
        final Rotate rx = new Rotate(0, Rotate.X_AXIS);
        final Rotate ry = new Rotate(0, Rotate.Y_AXIS);
        final Rotate rz = new Rotate(0, Rotate.Z_AXIS);
        final Rotate rx2 = new Rotate(0, Rotate.X_AXIS);
        final Rotate ry2 = new Rotate(0, Rotate.Y_AXIS);
        final Rotate rz2 = new Rotate(0, Rotate.Z_AXIS);
        Animationstage animation = new Animationstage();
        final Stage dicestage1 = animation.Ani(800, 400, rx, ry, rz);
        final Stage dicestage2 = animation.Ani(930, 400, rx2, ry2, rz2);

        sumofdice = rollDice();
        //to insert
        //database.Insert("INSERT INTO CRAPS(STUDENT_ID,SUBMIT_DATETIME,DICE_1,DICE_2)VALUES(?,?,?,?)", student_id.getText(), die1, die2);
        showanimation(die1, rx, ry, rz);
        dicestage1.show();
        showanimation(die2, rx2, ry2, rz2);
        dicestage2.show();

        point = 0;
        switch (sumofdice) {
            case 7:
            case 11:
                showresult("You win!!", dicestage1, dicestage2);
                labelresult.setText(String.format("You win %d times and lose %d times.", ++wincount, losecount));
                point = 0;
                break;
            case 2:
            case 3:
            case 12:
                showresult("You Lose!!", dicestage1, dicestage2);
                labelresult.setText(String.format("You win %d times and lose %d times.", wincount, ++losecount));
                break;
            default:
                point = sumofdice;
                playbutton.setDisable(true);
                labelshow.setText("Your point is: " + point + ". \nYou need to throw the dice again\n until you make the point.");
                labelshow.setStyle("-fx-text-fill:red");
                final Button btncon = new Button("Roll!");
                btncon.setStyle("-fx-background-color:darkslateblue;-fx-text-fill:white");
                gridpane.add(btncon, 1, 3);
                btncon.setPrefSize(100, 20);

                btncon.setOnAction((EventHandler)->{
                        dicestage1.close();
                        dicestage2.close();
                        sumofdice = rollDice();
                        //to insert
             //           database.Insert("INSERT INTO CRAPS(STUDENT_ID,SUBMIT_DATETIME,DICE_1,DICE_2)VALUES(?,?,?,?)", student_id.getText(), die1, die2);
                        
                        showanimation(die1, rx, ry, rz);
                        showanimation(die2, rx2, ry2, rz2);
                        dicestage1.show();
                        dicestage2.show();
                        if (sumofdice == point) {
                            showresult("You win!!", dicestage1, dicestage2);
                            point = 0;
                            btncon.setDisable(true);
                            playbutton.setDisable(false);
                            reset(labelshow, gridpane, btncon);
                            labelresult.setText(String.format("You win %d times and lose %d times.", ++wincount, losecount));
                        } else if (sumofdice == 7) {
                            showresult("You Lose!!", dicestage1, dicestage2);
                            point = 0;
                            btncon.setDisable(true);
                            playbutton.setDisable(false);
                            reset(labelshow, gridpane, btncon);
                            labelresult.setText(String.format("You win %d times and lose %d times.", wincount, ++losecount));
                        }
                });
                break;
        }
    }

    //Set Timeline for animation
    public void animate(Rotate rx, Rotate ry, Rotate rz, int rotatey, int rotatex, int rotatez) {
        Timeline animation = new Timeline();
        //reuse timeline object
        animation.getKeyFrames().clear();
        animation.getKeyFrames().addAll(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(ry.angleProperty(), 0d),
                        new KeyValue(rx.angleProperty(), 0d),
                        new KeyValue(rz.angleProperty(), 0d)
                ),
                new KeyFrame(Duration.seconds(1),
                        new KeyValue(ry.angleProperty(), rotatey),
                        new KeyValue(rx.angleProperty(), rotatex),
                        new KeyValue(rz.angleProperty(), rotatez)
                ));
        animation.setCycleCount(1);
        animation.playFromStart();
    }
//show different face of dice
    public void showanimation(int dicenum, Rotate rx, Rotate ry, Rotate rz) {
        switch (dicenum) {
            case 1:
                animate(rx, ry, rz, 450, 360, 360);
                break;
            case 2:
                animate(rx, ry, rz, 450, 450, 540);
                break;
            case 3:
                animate(rx, ry, rz, 180, 360, 540);
                break;
            case 4:
                animate(rx, ry, rz, 360, 360, 360);
                break;
            case 5:
                animate(rx, ry, rz, 450, 450, 360);
                break;
            case 6:
                animate(rx, ry, rz, 450, 450, 450);
                break;
        }
    }
//showing the result by alert

    public void showresult(String result, final Stage input, final Stage input2) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Result");
        alert.setHeaderText("");
        alert.setContentText(result);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            input.close();
            input2.close();
        }
    }

    public int rollDice() {
        Random random = new Random();
        die1 = 1 + random.nextInt(6);
        die2 = 1 + random.nextInt(6);
        int sum = die1 + die2;
        return sum;
    }

    public void reset(Label label, GridPane gridpane, Button btn) {
        label.setText("");
        gridpane.getChildren().remove(btn);
    }
// To load the new fxml for showing the statistic

    @FXML
    private void showstat(MouseEvent event) {
        String sid = student_id.getText();
        int[] temp = new int[6];
        //To get count from database by for loop
        for (int i = 0; i < temp.length; i++) 
            temp[i] = database.count("SELECT COUNT(*) from CRAPS where STUDENT_ID =? AND DICE_1 = ?", sid, i + 1) + database.count("SELECT COUNT(*) from CRAPS where STUDENT_ID =? AND DICE_2 = ?", sid, i + 1);

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Statistic.fxml"));
            Stage stage = new Stage();
            //to fix the stage
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(gridpane.getScene().getWindow());
                stage.setTitle("Statistic");
                stage.setScene(new Scene((Parent) fxmlLoader.load(), 600, 400));
            /*controller part aims to tranfer the data within two controller*/
            StatisticController controller = fxmlLoader.getController();
                controller.initData(sid);
                controller.inittemp(temp[0], temp[1], temp[2], temp[3], temp[4], temp[5]);
                controller.setstage(stage);
            stage.show();
        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window.", e);
        }
        
    }

    //to show the rule
    @FXML
    private void rule(MouseEvent event) {
        final Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Rules");
            alert.setHeaderText("CRAPS RULES");
            alert.setContentText(rule);
            alert.showAndWait();
    }
}
