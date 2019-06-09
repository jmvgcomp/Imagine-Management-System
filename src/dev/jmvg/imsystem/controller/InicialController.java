package dev.jmvg.imsystem.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class InicialController implements Initializable {

    @FXML
    private AnchorPane an_funcionarios, an_vendas, an_produtos, an_clientes, an_fornecedores, an_relatorios, an_menu;

    @FXML
    private JFXButton btn_menu_funcionarios, btn_menu_vendas, btn_menu_produtos, btn_menu_clientes, btn_menu_fornecedores, btn_menu_relatório;


    private static double xOffset = 0;
    private static   double yOffset = 0;
    @FXML
    void menuButtonAction(ActionEvent event) {
        if(event.getSource() == btn_menu_funcionarios){
            System.out.println("clicou btn_menu_funcionarios");
            an_funcionarios.toFront();
            an_menu.toFront();
        }
        if(event.getSource() == btn_menu_vendas){
            System.out.println("clicou btn_menu_vendas");
            an_vendas.toFront();
            an_menu.toFront();
        }
        if(event.getSource() == btn_menu_produtos){
            System.out.println("clicou btn_menu_produtos");
            an_produtos.toFront();
            an_menu.toFront();

        }
        if(event.getSource() == btn_menu_clientes){
            System.out.println("clicou btn_menu_clientes");
            an_clientes.toFront();
            an_menu.toFront();
        }
        if(event.getSource() == btn_menu_fornecedores){
            System.out.println("clicou btn_menu_fornecedores");
            an_fornecedores.toFront();
            an_menu.toFront();
        }
        if(event.getSource() == btn_menu_relatório){
            System.out.println("clicou btn_menu_relatório");
            an_relatorios.toFront();
            an_menu.toFront();
        }
    }



    static void start(){
        try{
            Parent root = FXMLLoader.load(InicialController.class.getResource("/dev/jmvg/imsystem/view/gui/telas/Inicial.fxml"));
            Stage  stage = new Stage();
            root.setOnMousePressed(event -> {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            });

            root.setOnMouseDragged(event -> {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            });


            stage.initStyle(StageStyle.TRANSPARENT);
            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);
            stage.setScene(scene);
            stage.show();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
