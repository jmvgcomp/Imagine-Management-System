package dev.jmvg.imsystem.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import dev.jmvg.imsystem.Exceptions.DAOException;
import dev.jmvg.imsystem.model.dao.DAO;
import dev.jmvg.imsystem.model.entities.Fornecedores;
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

public class FornecedoresController implements Initializable {


    @FXML
    private JFXTextField txt_for_codigo, txt_for_nome, txt_for_descricao, txt_for_cnpj, txt_for_tel;

    @FXML
    private TableView<Fornecedores> tableView;

    @FXML
    private TableColumn<Fornecedores, Integer> col_for_id;

    @FXML
    private TableColumn<Fornecedores, String> col_for_nome, col_for_descricao, col_for_cnpj, col_for_telefone;



    @FXML
    private JFXButton btnSalvar;

    @FXML
    private JFXTextField txt_for_pesquisar;

    @FXML
    private FontAwesomeIconView btnPesquisar;

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
            String nome = tableView.getSelectionModel().getSelectedItem().getNome();
            String descricao = tableView.getSelectionModel().getSelectedItem().getDescricao();
            String cnpj = tableView.getSelectionModel().getSelectedItem().getCnpj();
            String telefone = tableView.getSelectionModel().getSelectedItem().getTelefone();

            txt_for_codigo.setText(String.valueOf(codigo));
            txt_for_nome.setText(nome);
            txt_for_descricao.setText(descricao);
            txt_for_cnpj.setText(cnpj);
            txt_for_tel.setText(telefone);
        }
    }

    @FXML
    void btnExcluir(ActionEvent event) throws DAOException {
        if(tableView.getSelectionModel().getSelectedItem() == null) {
            txt_error.setText("Você deve selecionar um cliente para ser excluido.");
            txt_error.setVisible(true);
        }else {
            String nome = tableView.getSelectionModel().getSelectedItem().getNome();
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
                DAO<Fornecedores> clientesDAO = new DAO<>();
                clientesDAO.remover(Fornecedores.class, codigo);
                tableView.setItems(listaFornecedores());
            }else{
                alert.close();
            }
        }
    }

    @FXML
    void btnSalvarAction(ActionEvent event) throws DAOException {

        salvarFornecedores();
        atualizaPesquisaFornecedores();
        limparCampos();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inicializaColunaClientes();
        try {
            tableView.setItems(listaFornecedores());
            atualizaPesquisaFornecedores();
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    private boolean verificarCampos(){
        if(txt_for_nome.getText().isEmpty() || txt_for_cnpj.getText().isEmpty() || txt_for_tel.getText().isEmpty() || txt_for_descricao.getText().isEmpty()){
            txt_error.setVisible(true);
            txt_error.setText("Todos os campos devem ser preenchidos!");
            return true;
        }
        return false;
    }
    private void limparCampos(){
        txt_for_descricao.setText("");
        txt_for_codigo.setText("");
        txt_for_tel.setText("");
        txt_for_cnpj.setText("");
        txt_for_nome.setText("");
        txt_for_pesquisar.setText("");

    }

    private void inicializaColunaClientes(){
        col_for_id.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        col_for_nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        col_for_cnpj.setCellValueFactory(new PropertyValueFactory<>("cnpj"));
        col_for_descricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        col_for_telefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
    }

    private ObservableList<Fornecedores> listaFornecedores() throws DAOException {
        DAO<Fornecedores> fornecedoresDAO = new DAO<>();
        Fornecedores fornecedores = new Fornecedores();
        return FXCollections.observableArrayList(fornecedoresDAO.listarTodos(fornecedores));

    }

    private void salvarFornecedores() throws DAOException {
        DAO<Fornecedores> fornecedoresDAO = new DAO<>();
        Fornecedores fornecedores = new Fornecedores();
        if(!txt_for_codigo.getText().isEmpty()){
            fornecedores.setCodigo(Integer.valueOf(txt_for_codigo.getText()));
        }
        fornecedores.setNome(txt_for_nome.getText());
        fornecedores.setCnpj(txt_for_cnpj.getText());
        fornecedores.setDescricao(txt_for_descricao.getText());
        fornecedores.setTelefone(txt_for_tel.getText());

        if(!verificarCampos()) {

            fornecedoresDAO.salvar(fornecedores);
            tableView.setItems(listaFornecedores());
        }
    }

    private void atualizaPesquisaFornecedores(){
        try {
            FilteredList<Fornecedores> filtrarDados = new FilteredList<>(listaFornecedores(), e -> true);
            txt_for_pesquisar.setOnKeyReleased(e -> {
                txt_for_pesquisar.textProperty().addListener((ObservableValue, oldValue, newValue) -> {
                    filtrarDados.setPredicate((Predicate<? super Fornecedores>) fornecedores -> {
                        if (newValue == null || newValue.isEmpty()) {
                            return true;
                        }

                        String lowerCaseValue = newValue.toLowerCase();
                        if (String.valueOf(fornecedores.getNome()).toLowerCase().contains(lowerCaseValue)) {
                            return true;
                        } else if (fornecedores.getDescricao().toLowerCase().contains(lowerCaseValue)) {
                            return true;
                        } else return fornecedores.getCnpj().contains(newValue);
                    });
                });
                SortedList<Fornecedores> sortedList = new SortedList<>(filtrarDados);
                sortedList.comparatorProperty().bind(tableView.comparatorProperty());
                tableView.setItems(sortedList);

            });
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

}
