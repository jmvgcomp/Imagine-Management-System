package dev.jmvg.imsystem.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import dev.jmvg.imsystem.Exceptions.DAOException;
import dev.jmvg.imsystem.model.dao.DAO;
import dev.jmvg.imsystem.model.entities.Fornecedores;
import dev.jmvg.imsystem.model.entities.Produtos;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class ProdutosController implements Initializable {


    @FXML
    private AnchorPane an_produtos,an_prod_for;


    @FXML
    private JFXTextField txt_pro_codigo,txt_pro_descricao, txt_pro_pesquisar,txt_pro_preco, txt_pro_quantidade;

    @FXML
    private JFXTextField txt_pro_fornecedor;

    @FXML
    private JFXTextArea txt_pro_observacao;


    @FXML
    private TableView<Produtos> tableView;

    @FXML
    private TableColumn<Produtos, Integer> col_pro_id;

    @FXML
    private TableColumn<Produtos, String> col_pro_descricao, col_pro_fornecedor, col_pro_preco, col_pro_quantidade;


    @FXML
    private Label txt_error,txt_error1;

    @FXML
    private TableView<Fornecedores> tableView_prod_for;

    @FXML
    private TableColumn<Fornecedores, Integer> prod_for_id;

    @FXML
    private TableColumn<Fornecedores, String> prod_for_nome, prod_for_cnpj,  prod_for_tel;


    @FXML
    private JFXTextField prod_for_txt_pesquisar;




    Fornecedores fornecedores = new Fornecedores();
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        inicializaColunaProdutos();
        inicializaColunaFornecedor();
        txt_pro_fornecedor.setOnMouseClicked(e -> {
            atualizaPesquisaFornecedor();
            an_prod_for.setVisible(true);
            an_produtos.setDisable(true);
        });
        try {
            tableView.setItems(listaProdutos());
            tableView_prod_for.setItems(listaFornecedores());
            atualizaPesquisaProduto();
            atualizaPesquisaFornecedor();
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnEditar(ActionEvent event) throws DAOException {
        if(tableView.getSelectionModel().getSelectedItem() == null) {
            txt_error.setText("Você deve selecionar um cliente para ser atualizado.");
            txt_error.setVisible(true);
        }else{
            txt_error.setVisible(false);
            Integer codigo = tableView.getSelectionModel().getSelectedItem().getCodigo();
            String descricao = tableView.getSelectionModel().getSelectedItem().getDescricao();
            String fornecedor = tableView.getSelectionModel().getSelectedItem().getFornecedores().getNome();
            String observacao = tableView.getSelectionModel().getSelectedItem().getObservacao();
            Double preco = tableView.getSelectionModel().getSelectedItem().getValor();
            Integer quantidade = tableView.getSelectionModel().getSelectedItem().getQuantidade();

            txt_pro_codigo.setText(String.valueOf(codigo));
            txt_pro_descricao.setText(descricao);
            txt_pro_fornecedor.setText(fornecedor);
            txt_pro_observacao.setText(observacao);
            txt_pro_preco.setText(String.valueOf(preco));
            txt_pro_quantidade.setText(String.valueOf(quantidade));
        }
    }

    @FXML
    void btnExcluir(ActionEvent event) throws DAOException {
        if(tableView.getSelectionModel().getSelectedItem() == null) {
            txt_error.setText("Você deve selecionar um produto para ser excluido.");
            txt_error.setVisible(true);
        }else {
            String nome = tableView.getSelectionModel().getSelectedItem().getDescricao();
            Integer codigo = tableView.getSelectionModel().getSelectedItem().getCodigo();

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Exclusão de produto");
            alert.setHeaderText("Você está tentando excluir um produto, está certo disso?");
            alert.setContentText("Produto a ser excluido: "+nome+" Código:"+codigo);

            ButtonType Cancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
            ButtonType Excluir = new ButtonType("Excluir", ButtonBar.ButtonData.OK_DONE);
            alert.getButtonTypes().setAll(Excluir, Cancelar);

            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == Excluir) {
                DAO<Produtos> produtosDAO = new DAO<>();
                produtosDAO.remover(Produtos.class, codigo);
                tableView.setItems(listaProdutos());
            }else{
                alert.close();
            }
        }
    }

    @FXML
    void btnSalvarAction(ActionEvent event) throws DAOException {

        salvarProdutos();
        atualizaPesquisaProduto();
        limparCampos();
    }


    private boolean verificarCampos(){
        if(txt_pro_descricao.getText().isEmpty() || txt_pro_fornecedor.getText().isEmpty() || txt_pro_preco.getText().isEmpty() || txt_pro_quantidade.getText().isEmpty()){
            txt_error.setVisible(true);
            txt_error.setText("Somente o campo observação é opcional, os demais, devem ser preenchidos.");
            return true;
        }
        return false;
    }
    private void limparCampos(){
        txt_pro_codigo.setText("");
        txt_pro_preco.setText("");
        txt_pro_descricao.setText("");
        txt_pro_fornecedor.setText("");
        txt_pro_quantidade.setText("");
        txt_pro_observacao.setText("");

    }

    private void inicializaColunaProdutos(){

        col_pro_id.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        col_pro_descricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        col_pro_fornecedor.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getFornecedores().getNome()));
        col_pro_preco.setCellValueFactory(new PropertyValueFactory<>("valor"));
        col_pro_quantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));

    }

    private ObservableList<Produtos> listaProdutos() throws DAOException {
        DAO<Produtos> produtosDAO = new DAO<>();
        Produtos produtos = new Produtos();
        return FXCollections.observableArrayList(produtosDAO.listarTodos(produtos));

    }



    private void salvarProdutos() throws DAOException {
        DAO<Produtos> produtosDAO = new DAO<>();

        Produtos produtos = new Produtos();
        if (!txt_pro_codigo.getText().isEmpty()) {
            produtos.setCodigo(Integer.valueOf(txt_pro_codigo.getText()));
        }
        produtos.setDescricao(txt_pro_descricao.getText());
        produtos.setValor(Double.parseDouble(txt_pro_preco.getText()));
        produtos.setQuantidade(Integer.parseInt(txt_pro_quantidade.getText()));
        produtos.setDescricao(txt_pro_descricao.getText());
        produtos.setObservacao(txt_pro_observacao.getText());
        produtos.setFornecedores(fornecedores);
        if (!verificarCampos()) {
            produtosDAO.salvar(produtos);

            tableView.setItems(listaProdutos());
        }
    }


    private void atualizaPesquisaProduto(){
        try {
            FilteredList<Produtos> filtrarDados = new FilteredList<>(listaProdutos(), e -> true);
            txt_pro_pesquisar.setOnKeyReleased(e -> {
                txt_pro_pesquisar.textProperty().addListener((ObservableValue, oldValue, newValue) -> {
                    filtrarDados.setPredicate((Predicate<? super Produtos>) produtos -> {
                        if (newValue == null || newValue.isEmpty()) {
                            return true;
                        }

                        String lowerCaseValue = newValue.toLowerCase();
                        if (produtos.getDescricao().contains(lowerCaseValue)) {
                            return true;
                        } else if (produtos.getFornecedores().getNome().toLowerCase().contains(lowerCaseValue)) {
                            return true;
                        } else return String.valueOf(produtos.getCodigo()).contains(newValue);
                    });
                });
                SortedList<Produtos> sortedList = new SortedList<>(filtrarDados);
                sortedList.comparatorProperty().bind(tableView.comparatorProperty());
                tableView.setItems(sortedList);


            });
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }
    //CONTROLLER TELA FORNECEDOR DENTRO DE PRODUTO

    @FXML
    void btnSair(ActionEvent event) {
        an_prod_for.setVisible(false);
        an_produtos.setDisable(false);


    }


    @FXML
    void btnSelecionar(ActionEvent event) {
        if(tableView_prod_for.getSelectionModel().getSelectedItem() == null) {
            txt_error1.setText("Você deve selecionar um fornecedor na tabela.");
            txt_error1.setVisible(true);
        }else {

            txt_error1.setVisible(false);

            Integer codigo = tableView_prod_for.getSelectionModel().getSelectedItem().getCodigo();
            String nome = tableView_prod_for.getSelectionModel().getSelectedItem().getNome();
            String cnpj = tableView_prod_for.getSelectionModel().getSelectedItem().getCnpj();
            String descricao = tableView_prod_for.getSelectionModel().getSelectedItem().getDescricao();
            String telefone = tableView_prod_for.getSelectionModel().getSelectedItem().getTelefone();

            fornecedores.setCodigo(codigo);
            fornecedores.setNome(nome);
            fornecedores.setDescricao(descricao);
            fornecedores.setCnpj(cnpj);
            fornecedores.setTelefone(telefone);
            txt_pro_fornecedor.setText(fornecedores.getNome());

            an_produtos.setDisable(false);
            an_prod_for.setVisible(false);

        }
    }


    private ObservableList<Fornecedores> listaFornecedores() throws DAOException {
        DAO<Fornecedores> funcionariosDAO = new DAO<>();
        Fornecedores fornecedores = new Fornecedores();
        return FXCollections.observableArrayList(funcionariosDAO.listarTodos(fornecedores));

    }

    private void inicializaColunaFornecedor(){
        prod_for_id.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        prod_for_nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        prod_for_cnpj.setCellValueFactory(new PropertyValueFactory<>("cnpj"));
        prod_for_tel.setCellValueFactory(new PropertyValueFactory<>("telefone"));
    }

    private void atualizaPesquisaFornecedor(){
        try {
            FilteredList<Fornecedores> filtrarDados = new FilteredList<>(listaFornecedores(), e -> true);
            prod_for_txt_pesquisar.setOnKeyReleased(e -> {
                prod_for_txt_pesquisar.textProperty().addListener((ObservableValue, oldValue, newValue) -> {
                    filtrarDados.setPredicate((Predicate<? super Fornecedores>) fornecedores -> {
                        if (newValue == null || newValue.isEmpty()) {
                            return true;
                        }

                        String lowerCaseValue = newValue.toLowerCase();
                        if (fornecedores.getNome().toLowerCase().contains(lowerCaseValue)) {
                            return true;
                        } else if (fornecedores.getCnpj().contains(newValue)) {
                            return true;
                        } else return fornecedores.getDescricao().toLowerCase().contains(lowerCaseValue);
                    });
                });
                SortedList<Fornecedores> sortedList = new SortedList<>(filtrarDados);
                sortedList.comparatorProperty().bind(tableView_prod_for.comparatorProperty());
                tableView_prod_for.setItems(sortedList);

            });

        } catch (DAOException e) {
            e.printStackTrace();
        }
    }


}
