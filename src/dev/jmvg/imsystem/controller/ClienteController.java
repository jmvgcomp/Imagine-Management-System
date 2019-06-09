package dev.jmvg.imsystem.controller;

import com.jfoenix.controls.JFXTextField;
import dev.jmvg.imsystem.Exceptions.DAOException;
import dev.jmvg.imsystem.model.dao.DAO;
import dev.jmvg.imsystem.model.entities.Clientes;
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

public class ClienteController implements Initializable {
    @FXML
    private TableView<Clientes> tableView;

    @FXML
    private JFXTextField txt_cli_codigo, txt_cli_representante, txt_cli_empresa, txt_cli_tel, txt_cli_cnpj, txt_cli_pesquisar;

    @FXML
    private TableColumn<Clientes, Integer> col_cli_id;

    @FXML
    private TableColumn<Clientes, String> col_cli_representante, col_cli_empresa, col_cli_cnpj, col_cli_telefone;

    @FXML
    private Label txt_error;


    @FXML
    void btnEditar(ActionEvent event) throws DAOException {
        if(tableView.getSelectionModel().getSelectedItem() == null) {
            txt_error.setText("Você deve selecionar um cliente para ser atualizado.");
            txt_error.setVisible(true);
        }else{
            txt_error.setVisible(false);
            Integer codigo = tableView.getSelectionModel().getSelectedItem().getCodigo();
            String representante = tableView.getSelectionModel().getSelectedItem().getRepresentante();
            String empresa = tableView.getSelectionModel().getSelectedItem().getEmpresa();
            String cnpj = tableView.getSelectionModel().getSelectedItem().getCnpj();
            String telefone = tableView.getSelectionModel().getSelectedItem().getTelefone();

            txt_cli_codigo.setText(String.valueOf(codigo));
            txt_cli_representante.setText(representante);
            txt_cli_empresa.setText(empresa);
            txt_cli_cnpj.setText(cnpj);
            txt_cli_tel.setText(telefone);
        }
    }

    @FXML
    void btnExcluir(ActionEvent event) throws DAOException {
        if(tableView.getSelectionModel().getSelectedItem() == null) {
            txt_error.setText("Você deve selecionar um cliente para ser excluido.");
            txt_error.setVisible(true);
        }else {
            String nome = tableView.getSelectionModel().getSelectedItem().getEmpresa();
            Integer codigo = tableView.getSelectionModel().getSelectedItem().getCodigo();

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Exclusão de Cliente");
            alert.setHeaderText("Você está tentando excluir um cliente, está certo disso?");
            alert.setContentText("Cliente a ser excluido: "+nome+" Código:"+codigo);

            ButtonType Cancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
            ButtonType Excluir = new ButtonType("Excluir", ButtonBar.ButtonData.OK_DONE);
            alert.getButtonTypes().setAll(Excluir, Cancelar);

            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == Excluir) {
                DAO<Clientes> clientesDAO = new DAO<>();
                clientesDAO.remover(Clientes.class, codigo);
                tableView.setItems(listaClientes());
            }else{
                alert.close();
            }
        }
    }

    @FXML
    void btnSalvarAction(ActionEvent event) throws DAOException {

        salvarClientes();
        atualizaPesquisaFuncionaro();
        limparCampos();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inicializaColunaClientes();
        try {
            tableView.setItems(listaClientes());
            atualizaPesquisaFuncionaro();
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    private boolean verificarCampos(){
        if(txt_cli_empresa.getText().isEmpty() || txt_cli_cnpj.getText().isEmpty() || txt_cli_tel.getText().isEmpty() || txt_cli_representante.getText().isEmpty()){
            txt_error.setVisible(true);
            txt_error.setText("Todos os campos devem ser preenchidos!");
            return true;
        }
        return false;
    }
    private void limparCampos(){
        txt_cli_representante.setText("");
        txt_cli_codigo.setText("");
        txt_cli_tel.setText("");
        txt_cli_cnpj.setText("");
        txt_cli_empresa.setText("");
        txt_cli_pesquisar.setText("");

    }

    private void inicializaColunaClientes(){
        col_cli_id.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        col_cli_empresa.setCellValueFactory(new PropertyValueFactory<>("empresa"));
        col_cli_cnpj.setCellValueFactory(new PropertyValueFactory<>("cnpj"));
        col_cli_representante.setCellValueFactory(new PropertyValueFactory<>("representante"));
        col_cli_telefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
    }

    private ObservableList<Clientes> listaClientes() throws DAOException {
        DAO<Clientes> clientesDAO = new DAO<>();
        Clientes clientes = new Clientes();
        return FXCollections.observableArrayList(clientesDAO.listarTodos(clientes));

    }

    private void salvarClientes() throws DAOException {
        DAO<Clientes> clientesDAO = new DAO<>();
        Clientes clientes = new Clientes();
        if(!txt_cli_codigo.getText().isEmpty()){
            clientes.setCodigo(Integer.valueOf(txt_cli_codigo.getText()));
        }
        clientes.setRepresentante(txt_cli_representante.getText());
        clientes.setCnpj(txt_cli_cnpj.getText());
        clientes.setEmpresa(txt_cli_empresa.getText());
        clientes.setTelefone(txt_cli_tel.getText());

        if(!verificarCampos()) {

            clientesDAO.salvar(clientes);
            tableView.setItems(listaClientes());
        }
    }

    public void atualizaPesquisaFuncionaro(){
        try {
            FilteredList<Clientes> filtrarDados = new FilteredList<>(listaClientes(), e -> true);
            txt_cli_pesquisar.setOnKeyReleased(e -> {
                txt_cli_pesquisar.textProperty().addListener((ObservableValue, oldValue, newValue) -> {
                    filtrarDados.setPredicate((Predicate<? super Clientes>) clientes -> {
                        if (newValue == null || newValue.isEmpty()) {
                            return true;
                        }

                        String lowerCaseValue = newValue.toLowerCase();
                        if (String.valueOf(clientes.getRepresentante()).contains(lowerCaseValue)) {
                            return true;
                        } else if (clientes.getEmpresa().toLowerCase().contains(lowerCaseValue)) {
                            return true;
                        } else return clientes.getCnpj().contains(newValue);
                    });
                });
                SortedList<Clientes> sortedList = new SortedList<>(filtrarDados);
                sortedList.comparatorProperty().bind(tableView.comparatorProperty());
                tableView.setItems(sortedList);

            });
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }
}
