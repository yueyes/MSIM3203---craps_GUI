/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crapsgui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author chiwoon
 */
public class StatisticController implements Initializable {
    private Stage stage;
    
    @FXML
    private TextField sid;
    @FXML
    private TextField throw1;
    @FXML
    private TextField throw2;
    @FXML
    private TextField throw3;
    @FXML
    private TextField throw4;
    @FXML
    private TextField throw5;
    @FXML
    private TextField throw6;
    @FXML
    private TextField s;
    @FXML
    private Button btn;

  
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Database db = new Database();
        String tempsid = sid.getText();
        btn.setOnMousePressed((k)->{
            stage.close();
        });
        
        
    }    
public void setstage(Stage stage){
this.stage =stage;
}
  public void initData(String data) {
    sid.setText(data);
  }
  
  public void inittemp(int count1,int count2,int count3,int count4,int count5,int count6){
  throw1.setText(String.valueOf(count1));
    throw2.setText(String.valueOf(count2));
    throw3.setText(String.valueOf(count3));
    throw4.setText(String.valueOf(count4));
    throw5.setText(String.valueOf(count5));
    throw6.setText(String.valueOf(count6));
  }
    
}
