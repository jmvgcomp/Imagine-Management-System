package dev.jmvg.imsystem.view;

import dev.jmvg.imsystem.connection.ConnectionFactory;
import dev.jmvg.imsystem.model.dao.DAO;
import dev.jmvg.imsystem.model.entities.Fornecedores;
import dev.jmvg.imsystem.model.entities.Funcionarios;
import dev.jmvg.imsystem.model.entities.Produtos;
import dev.jmvg.imsystem.model.entities.Vendas;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.persistence.EntityManager;
import java.sql.Timestamp;
import java.util.Date;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../dev/jmvg/imsystem/view/gui/Telaprincipal.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        Fornecedores f = new Fornecedores();
        f.setNome("CONEXAO EPI");
        f.setDescricao("Empresa de EPI em Geral");
        f.setCnpj("0554.254/3333-1");
        f.setTelefone("(81) 00225-4454");

        Produtos p = new Produtos();
        p.setDescricao("Bota de Couro");
        p.setValor(350.0);
        p.setQuantidade(1);
        p.setFornecedores(f);

        Funcionarios fun = new Funcionarios();
        fun.setNome("João");
        fun.setTelefone("81 00000-0000");
        fun.setCpf("111.222.333-44");
        fun.setNivel("Gerente");
        fun.setSenha("123");

        Vendas v = new Vendas();

        Date date = new Date();
        Timestamp t = new Timestamp(date.getTime());
        v.setHorario(t);
        v.setValorTotal(350.0);
        v.setFuncionarios(fun);

        DAO<Fornecedores> fornecedoresDAO = new DAO<>();

        fornecedoresDAO.salvar(f);

        //launch(args);
    }
}
