package dev.jmvg.imsystem.view;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
    private double xOffset = 0;
    private  double yOffset = 0;
    @Override
    public void start(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/dev/jmvg/imsystem/view/gui/telas/Login.fxml"));
        stage.initStyle(StageStyle.TRANSPARENT);

        //movimento da tela

        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });

        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }
        });


        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
      /*  Fornecedores f = new Fornecedores();
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

        DAO<Produtos> produtosDAO = new DAO<>();

        try {
            produtosDAO.listarTodos(p).forEach(System.out::println);
        } catch (DAOException e) {
            e.printStackTrace();
        }*/
        launch(args);
    }
}
