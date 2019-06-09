package dev.jmvg.imsystem.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import dev.jmvg.imsystem.Exceptions.DAOException;
import dev.jmvg.imsystem.model.dao.DAO;
import dev.jmvg.imsystem.model.dao.FuncionarioDAO;
import dev.jmvg.imsystem.model.entities.Funcionarios;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class FuncionariosController implements Initializable {
    @FXML
    private TableView<Funcionarios> tableView;
    @FXML
    private TableColumn<Funcionarios, Integer> col_fun_id;

    @FXML
    private TableColumn<Funcionarios, String> col_fun_nome;

    @FXML
    private TableColumn<Funcionarios, String> col_fun_cpf;

    @FXML
    private TableColumn<Funcionarios, String> col_fun_tel;

    @FXML
    private TableColumn<Funcionarios, String> col_fun_nivel;

    @FXML
    private JFXTextField txt_fun_pesquisar;

    @FXML
    private Label txt_error;

    @FXML
    private JFXPasswordField txt_fun_senha, txt_fun_conf_senha;
    @FXML
    private JFXTextField txt_fun_codigo, txt_fun_nome, txt_fun_tel, txt_fun_cpf;

    @FXML
    private JFXComboBox<String> txt_fun_nivel;


    @FXML
    void btnEditar(ActionEvent event) throws DAOException {
        if(tableView.getSelectionModel().getSelectedItem() == null) {
            txt_error.setText("Você deve selecionar um funcionário para ser atualizado.");
            txt_error.setVisible(true);
        }else{
            txt_error.setVisible(false);
            Integer codigo = tableView.getSelectionModel().getSelectedItem().getCodigo();
            String nome = tableView.getSelectionModel().getSelectedItem().getNome();
            String cpf = tableView.getSelectionModel().getSelectedItem().getCpf();
            String telefone = tableView.getSelectionModel().getSelectedItem().getTelefone();
            String senha = tableView.getSelectionModel().getSelectedItem().getSenha();
            String nivel = tableView.getSelectionModel().getSelectedItem().getNivel();

            txt_fun_codigo.setText(String.valueOf(codigo));
            txt_fun_nome.setText(nome);
            txt_fun_tel.setText(telefone);
            txt_fun_cpf.setText(cpf);
            txt_fun_senha.setText(senha);
            txt_fun_conf_senha.setText(senha);
            txt_fun_nivel.setValue(nivel);
        }
    }

    @FXML
    void btnExcluir(ActionEvent event) throws DAOException {
        if(tableView.getSelectionModel().getSelectedItem() == null) {
            txt_error.setText("Você deve selecionar um funcionário para ser excluido.");
            txt_error.setVisible(true);
        }else {
            String nome = tableView.getSelectionModel().getSelectedItem().getNome();
            Integer codigo = tableView.getSelectionModel().getSelectedItem().getCodigo();

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Exclusão de Funcionário");
            alert.setHeaderText("Você está tentando excluir um funcionário, está certo disso?");
            alert.setContentText("Funcionário a ser excluido: "+nome+" Código:"+codigo);

            ButtonType Cancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
            ButtonType Excluir = new ButtonType("Excluir", ButtonBar.ButtonData.OK_DONE);
            alert.getButtonTypes().setAll(Excluir, Cancelar);

            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == Excluir) {
                DAO<Funcionarios> funcionariosDAO = new FuncionarioDAO();
                funcionariosDAO.remover(Funcionarios.class, codigo);
                tableView.setItems(listaFuncionarios());
            }else{
                alert.close();
            }
        }
    }

    @FXML
    void btnSalvarAction(ActionEvent event) throws DAOException {

        salvarFuncionario();
        atualizaPesquisaFuncionaro();
        limparCampos();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        txt_fun_nivel.getItems().addAll( "Funcionário", "Gerente", "Administrador");
        inicializaColunaFuncionario();
        try {
            tableView.setItems(listaFuncionarios());
            atualizaPesquisaFuncionaro();
        } catch (DAOException e) {
            e.printStackTrace();
        }




    }

    private boolean verificarCampos(){
        if(txt_fun_nome.getText().isEmpty() || txt_fun_cpf.getText().isEmpty() || txt_fun_nivel.getValue().isEmpty() || txt_fun_tel.getText().isEmpty() || txt_fun_conf_senha.getText().isEmpty() || txt_fun_senha.getText().isEmpty()){
            txt_error.setVisible(true);
            txt_error.setText("Todos os campos devem ser preenchidos!");
            return true;
        }
        return false;
    }
    private void limparCampos(){
        txt_fun_nome.setText("");
       txt_fun_codigo.setText("");
        txt_fun_tel.setText("");
        txt_fun_nivel.setValue("");
       txt_fun_cpf.setText("");
       txt_fun_senha.setText("");
        txt_fun_conf_senha.setText("");
        txt_fun_pesquisar.setText("");

    }

    private void inicializaColunaFuncionario(){
        col_fun_id.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        col_fun_nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        col_fun_cpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        col_fun_tel.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        col_fun_nivel.setCellValueFactory(new PropertyValueFactory<>("nivel"));
    }

    private ObservableList<Funcionarios> listaFuncionarios() throws DAOException {
        DAO<Funcionarios> funcionariosDAO = new DAO<>();
        Funcionarios funcionarios = new Funcionarios();
        return FXCollections.observableArrayList(funcionariosDAO.listarTodos(funcionarios));

    }

    private void salvarFuncionario() throws DAOException {
        DAO<Funcionarios> funcionariosDAO = new DAO<>();
        Funcionarios funcionarios = new Funcionarios();
        if(!txt_fun_codigo.getText().isEmpty()){
            funcionarios.setCodigo(Integer.valueOf(txt_fun_codigo.getText()));
        }
        funcionarios.setNome(txt_fun_nome.getText());
        funcionarios.setCpf(txt_fun_cpf.getText());
        funcionarios.setTelefone(txt_fun_tel.getText());
        if(txt_fun_senha.getText().equals(txt_fun_conf_senha.getText())){
            funcionarios.setSenha(txt_fun_senha.getText());
            txt_error.setVisible(false);
        }else{
            txt_error.setVisible(true);
            return;
        }

        funcionarios.setNivel(txt_fun_nivel.getValue());
        if(!verificarCampos()) {

            funcionariosDAO.salvar(funcionarios);
            tableView.setItems(listaFuncionarios());
        }
    }

    public void atualizaPesquisaFuncionaro(){
        try {
            FilteredList<Funcionarios> filtrarDados = new FilteredList<>(listaFuncionarios(), e -> true);
            txt_fun_pesquisar.setOnKeyReleased(e -> {
                txt_fun_pesquisar.textProperty().addListener((ObservableValue, oldValue, newValue) -> {
                    filtrarDados.setPredicate((Predicate<? super Funcionarios>) funcionarios -> {
                        if (newValue == null || newValue.isEmpty()) {
                            return true;
                        }

                        String lowerCaseValue = newValue.toLowerCase();
                        if (String.valueOf(funcionarios.getTelefone()).contains(newValue)) {
                            return true;
                        } else if (funcionarios.getNome().toLowerCase().contains(lowerCaseValue)) {
                            return true;
                        } else return funcionarios.getCpf().contains(newValue);
                    });
                });
                SortedList<Funcionarios> sortedList = new SortedList<>(filtrarDados);
                sortedList.comparatorProperty().bind(tableView.comparatorProperty());
                tableView.setItems(sortedList);

            });
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }
}
