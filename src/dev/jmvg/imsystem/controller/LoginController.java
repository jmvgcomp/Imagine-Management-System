package dev.jmvg.imsystem.controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import dev.jmvg.imsystem.model.dao.FuncionarioDAO;
import dev.jmvg.imsystem.model.entities.Funcionarios;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
    Funcionarios funcionarios;

    @FXML
    private JFXTextField field_cpf;

    @FXML
    private JFXPasswordField field_senha;

    @FXML
    private Label lbl_inc;

    @FXML
    private Label lbl_correto;


    @FXML
    void fechar(MouseEvent event) {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }

    @FXML
    void minimizar(MouseEvent event) {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    void entrar(ActionEvent event){
        funcionarios = funcionarioDAO.getFuncionario(field_cpf.getText(), field_senha.getText());
        if(funcionarios == null){
            field_cpf.setText("");
            field_senha.setText("");
            lbl_inc.setVisible(true);
        }else{
            lbl_inc.setVisible(false);
            lbl_correto.setVisible(true);
            (((Node)event.getSource()).getScene()).getWindow().hide();
            InicialController.start();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
