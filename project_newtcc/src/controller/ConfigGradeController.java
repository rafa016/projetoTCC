package controller;

import com.google.gson.Gson;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import config.Screen;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.bean.Salas;
import model.dao.HistoricoDAO;
import model.dao.ProfessoresDAO;
import model.dao.SalasDAO;
import model.dao.UsuarioDAO;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class ConfigGradeController implements Initializable {

    @FXML private javafx.scene.layout.AnchorPane AnchorPane;
    @FXML private JFXButton btnTelaMenuInicial;
    @FXML private JFXButton btnTelaSalas;
    @FXML private JFXButton btnTelaMaterias;
    @FXML private JFXButton btnTelaProfessores;
    @FXML private JFXButton btnTelaSeguranca;
    @FXML private JFXButton btnCarregarTabela;
    @FXML private JFXButton btnConfig;
    @FXML private JFXButton btnSair;
    @FXML private Pane paneConfig;
    @FXML private JFXButton btnCriarTabela;
    @FXML private TextField txtNumDeSalas;
    @FXML private TableView<Salas> tbSalas;
    @FXML private TableColumn<Salas, Integer> clmIdSala;
    @FXML private TableColumn<Salas, String> clmNomeSala;
    @FXML private TableColumn<Salas, CheckBox> clmSelecionarSala;
    @FXML private TableColumn<Salas, String> clmTecnico;
    @FXML private JFXRadioButton rdb1280x720;
    @FXML private JFXRadioButton rdb1600x900;
    @FXML private JFXRadioButton rdb1280x1024;
    @FXML private JFXButton btnMinimizar;
    @FXML private Label lblMsgPreencher;
    @FXML private JFXTextField txtPesquisarSala;
    @FXML private Pane paneMenuInicial;
    @FXML private Pane paneSalas;
    @FXML private Pane paneMaterias;
    @FXML private Pane paneProfessores;
    @FXML private Pane paneSeguranca;

    private String diretorio, nomeTabela;
    private String sala = "";
    private int id = 0;
    public static boolean arrayTecnico[];
    public static Long[][] arrayIdProf;
    public static String arraySalas[];
    public static String arrayProfs[];
    public static String arrayCoresProfs[];
    public static String arrayNumAulas[];
    public static int qtdProfessores;
    public static String[][] arrayMatrizControle;
    public static boolean carregado = false;
    public static int numAulas;
    String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        initTableSalas();

        txtPesquisarSala.setOnKeyPressed(KeyEvent->{
            if(String.valueOf(KeyEvent.getCode()).equals("BACK_SPACE")){
                if(txtPesquisarSala.getText().length() == 0){
                } else if(txtPesquisarSala.getText().length() == 1){
                    sala = "";
                    initTableSalas();
                } else if(txtPesquisarSala.getText().length() > 1){
                    sala = txtPesquisarSala.getText().substring(0,txtPesquisarSala.getText().length()-1);
                    initTableSalas();
                }
            }else{
                sala = txtPesquisarSala.getText() + KeyEvent.getText();
                initTableSalas();
            }
        });

        rdb1280x720.setOnMouseClicked((MouseEvent e)->{
            if(rdb1280x1024.isSelected()){
                String Path = "TelaConfigGrade1280x720";
                Screen screen = new Screen();
                screen.restart(Path);
            }
            if(rdb1600x900.isSelected()){
                String Path = "TelaConfigGrade1280x720";
                Screen screen = new Screen();
                screen.restart(Path);
            }
        });

        rdb1280x1024.setOnMouseClicked((MouseEvent e)->{
            if(rdb1280x720.isSelected()){
                String Path = "TelaConfigGrade1280x1024";
                Screen screen = new Screen();
                screen.restart(Path);
            }
            if(rdb1600x900.isSelected()){
                String Path = "TelaConfigGrade1280x1024";
                Screen screen = new Screen();
                screen.restart(Path);
            }
        });

        rdb1600x900.setOnMouseClicked((MouseEvent e)->{
            if(rdb1280x720.isSelected()){
                String Path = "TelaConfigGrade1600x900";
                Screen screen = new Screen();
                screen.restart(Path);
            }
            if(rdb1280x1024.isSelected()){
                String Path = "TelaConfigGrade1600x900";
                Screen screen = new Screen();
                screen.restart(Path);
            }
        });

        AnchorPane.setOnMouseClicked((MouseEvent e)->{
            paneConfig.setVisible(false);
            tbSalas.getSelectionModel().clearSelection();
            AnchorPane.requestFocus();
        });

        btnMinimizar.setOnMouseClicked((MouseEvent e)->{
            Stage stage = (Stage) btnMinimizar.getScene().getWindow();
            stage.setIconified(true);
        });

        btnConfig.setOnMouseClicked((MouseEvent e)->{
            Toolkit tk = Toolkit.getDefaultToolkit();
            Dimension d = tk.getScreenSize();
            if(paneConfig.isVisible()){
                paneConfig.setVisible(false);
            }else{
                paneConfig.setVisible(true);
            }
            if(d.width > 1280 && d.height > 1024){
                if(rdb1280x1024.isSelected()){
                    rdb1280x1024.setDisable(true);
                }else{
                    rdb1280x1024.setDisable(false);
                }
            }
            if(d.width > 1600 && d.height > 900){
                if(rdb1600x900.isSelected()){
                    rdb1600x900.setDisable(true);
                }else{
                    rdb1600x900.setDisable(false);
                }
            }
        });

        btnSair.setOnMouseClicked((MouseEvent e)->{
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            usuarioDAO.sairSistema();
            System.exit(0);
        });

        btnTelaMenuInicial.setOnMouseClicked((MouseEvent e)->{
            if(rdb1280x720.isSelected()){
                String Path = "TelaMenuInicial1280x720";
                Screen screen = new Screen();
                screen.restartTelas(Path);
            }
            if(rdb1280x1024.isSelected()){
                String Path = "TelaMenuInicial1280x1024";
                Screen screen = new Screen();
                screen.restartTelas(Path);
            }
            if(rdb1600x900.isSelected()){
                String Path = "TelaMenuInicial1600x900";
                Screen screen = new Screen();
                screen.restartTelas(Path);
            }
        });

        btnTelaMenuInicial.setOnMouseEntered((MouseEvent e)->{
            btnTelaMenuInicial.setStyle("-fx-background-color: #7d7e80; -fx-border-color: #7d7e80");
            paneMenuInicial.setStyle("-fx-background-color: #56e8e0; -fx-border-color: #56e8e0");
        });

        btnTelaMenuInicial.setOnMouseExited((MouseEvent e)->{
            btnTelaMenuInicial.setStyle("-fx-background-color: #27282A");
            paneMenuInicial.setStyle("-fx-background-color: #27282A; -fx-border-color: #27282A");
        });

        btnTelaSalas.setOnMouseClicked((MouseEvent e)->{
            if(rdb1280x720.isSelected()){
                String Path = "TelaSalas1280x720";
                Screen screen = new Screen();
                screen.restartTelas(Path);
            }
            if(rdb1280x1024.isSelected()){
                String Path = "TelaSalas1280x1024";
                Screen screen = new Screen();
                screen.restartTelas(Path);
            }
            if(rdb1600x900.isSelected()){
                String Path = "TelaSalas1600x900";
                Screen screen = new Screen();
                screen.restartTelas(Path);
            }
        });

        btnTelaSalas.setOnMouseEntered((MouseEvent e)->{
            btnTelaSalas.setStyle("-fx-background-color: #7d7e80; -fx-border-color: #7d7e80");
            paneSalas.setStyle("-fx-background-color: #56e8e0; -fx-border-color: #56e8e0");
        });

        btnTelaSalas.setOnMouseExited((MouseEvent e)->{
            btnTelaSalas.setStyle("-fx-background-color: #27282A");
            paneSalas.setStyle("-fx-background-color: #27282A; -fx-border-color: #27282A");
        });

        btnTelaMaterias.setOnMouseClicked((MouseEvent e)->{
            if(rdb1280x720.isSelected()){
                String Path = "TelaMaterias1280x720";
                Screen screen = new Screen();
                screen.restartTelas(Path);
            }
            if(rdb1280x1024.isSelected()){
                String Path = "TelaMaterias1280x1024";
                Screen screen = new Screen();
                screen.restartTelas(Path);
            }
            if(rdb1600x900.isSelected()){
                String Path = "TelaMaterias1600x900";
                Screen screen = new Screen();
                screen.restartTelas(Path);
            }
        });

        btnTelaMaterias.setOnMouseEntered((MouseEvent e)->{
            btnTelaMaterias.setStyle("-fx-background-color: #7d7e80; -fx-border-color: #7d7e80");
            paneMaterias.setStyle("-fx-background-color: #56e8e0; -fx-border-color: #56e8e0");
        });

        btnTelaMaterias.setOnMouseExited((MouseEvent e)->{
            btnTelaMaterias.setStyle("-fx-background-color: #27282A");
            paneMaterias.setStyle("-fx-background-color: #27282A; -fx-border-color: #27282A");
        });

        btnTelaProfessores.setOnMouseClicked((MouseEvent e)->{
            if(rdb1280x720.isSelected()){
                String Path = "TelaProfessores1280x720";
                Screen screen = new Screen();
                screen.restartTelas(Path);
            }
            if(rdb1280x1024.isSelected()){
                String Path = "TelaProfessores1280x1024";
                Screen screen = new Screen();
                screen.restartTelas(Path);
            }
            if(rdb1600x900.isSelected()){
                String Path = "TelaProfessores1600x900";
                Screen screen = new Screen();
                screen.restartTelas(Path);
            }
        });

        btnTelaProfessores.setOnMouseEntered((MouseEvent e)->{
            btnTelaProfessores.setStyle("-fx-background-color: #7d7e80; -fx-border-color: #7d7e80");
            paneProfessores.setStyle("-fx-background-color: #56e8e0; -fx-border-color: #56e8e0");
        });

        btnTelaProfessores.setOnMouseExited((MouseEvent e)->{
            btnTelaProfessores.setStyle("-fx-background-color: #27282A");
            paneProfessores.setStyle("-fx-background-color: #27282A; -fx-border-color: #27282A");
        });

        btnTelaSeguranca.setOnMouseClicked((MouseEvent e)->{
            if(rdb1280x720.isSelected()){
                String Path = "TelaSeguranca1280x720";
                Screen screen = new Screen();
                screen.restartTelas(Path);
            }
            if(rdb1280x1024.isSelected()){
                String Path = "TelaSeguranca1280x1024";
                Screen screen = new Screen();
                screen.restartTelas(Path);
            }
            if(rdb1600x900.isSelected()){
                String Path = "TelaSeguranca1600x900";
                Screen screen = new Screen();
                screen.restartTelas(Path);
            }
        });

        btnTelaSeguranca.setOnMouseEntered((MouseEvent e)->{
            btnTelaSeguranca.setStyle("-fx-background-color: #7d7e80; -fx-border-color: #7d7e80");
            paneSeguranca.setStyle("-fx-background-color: #56e8e0; -fx-border-color: #56e8e0");
        });

        btnTelaSeguranca.setOnMouseExited((MouseEvent e)->{
            btnTelaSeguranca.setStyle("-fx-background-color: #27282A");
            paneSeguranca.setStyle("-fx-background-color: #27282A; -fx-border-color: #27282A");
        });

        btnCriarTabela.setOnMouseClicked((MouseEvent e)->{
            int num = 0;
            carregado = false;
            String qtdProfes = txtNumDeSalas.getText();
            int quantTbSala = tbSalas.getItems().size();
            for (int x = 0; x < quantTbSala; x++) {
                tbSalas.getSelectionModel().select(x);
                if (tbSalas.getItems().get(x).getSelect_sala().isSelected()) {
                   num++;
                }
            }

            if(qtdProfes == null || qtdProfes == "" || num == 0 || txtNumDeSalas.getText().matches("[a-z]*") || txtNumDeSalas.getText().matches("[A-Z]*")){
                lblMsgPreencher.setText("Preencha todos os Campos");
                if(txtNumDeSalas.getText().matches("[a-z]*") || txtNumDeSalas.getText().matches("[A-Z]*")){
                    lblMsgPreencher.setText("Número de Aulas inválido");
                }
            }else{
                HistoricoDAO historicoDAO = new HistoricoDAO();
                historicoDAO.historicoCriarTabela();
                arraySalas = selecionaSalas();
                arrayNumAulas = retornaNumAulas();
                numAulas = Integer.parseInt(txtNumDeSalas.getText());

                ProfessoresDAO profDAO = new ProfessoresDAO();
                qtdProfessores = profDAO.RetornaQuantProfes();
                arrayProfs = profDAO.RetonaNomesProfes();
                arrayCoresProfs = profDAO.RetonaCoresProfes();

                Stage stage =  new Stage();
                Screen screen = new Screen();
                try {
                    screen.startGridView(stage);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                lblMsgPreencher.setText("");
                txtNumDeSalas.setText("");
                initTableSalas();
            }
        });

        btnCarregarTabela.setOnMouseClicked((MouseEvent e)->{
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int se = jFileChooser.showOpenDialog(null);
            if(se == JFileChooser.APPROVE_OPTION){
                nomeTabela = jFileChooser.getName();
                diretorio = jFileChooser.getSelectedFile().getPath();
                diretorio = diretorio.replace('\\', '/');
            }

            HistoricoDAO historicoDAO = new HistoricoDAO();
            historicoDAO.historicoAbrirTabela(nomeTabela);

            LerArquivoGSON();
            carregado = true;
            Stage stage =  new Stage();
            Screen screen = new Screen();
            try {
                screen.startGridView(stage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

    }

    public void initTableSalas(){
        clmIdSala.setCellValueFactory(new PropertyValueFactory("id_sala"));
        clmNomeSala.setCellValueFactory(new PropertyValueFactory("nome_sala"));
        clmSelecionarSala.setCellValueFactory(new PropertyValueFactory("select_sala"));
        clmTecnico.setCellValueFactory(new PropertyValueFactory("tecnico"));
        tbSalas.setItems(atualizaTabelaSalas());
    }

    public ObservableList<Salas> atualizaTabelaSalas(){
        SalasDAO salasDAO = new SalasDAO();
        return FXCollections.observableArrayList(salasDAO.getList(id, sala));
    }

    public String[] selecionaSalas(){
        int quantTbSala = tbSalas.getItems().size();
        int qtd = 0;
        for(int x = 0; x < quantTbSala; x++) {
            tbSalas.getSelectionModel().select(x);
            if (tbSalas.getItems().get(x).getSelect_sala().isSelected()) {
                qtd++;
                if(tbSalas.getSelectionModel().getSelectedItem().getTecnico().equals("Sim")){
                    qtd++;
                }
            }
        }
        String[] salas = new String[qtd + 1];
        salas[0] = " ";
        int num = 1;
        for(int a = 0; a < quantTbSala; a++) {
            tbSalas.getSelectionModel().select(a);
            if (tbSalas.getItems().get(a).getSelect_sala().isSelected()) {
                String nome_sala = tbSalas.getSelectionModel().getSelectedItem().getNome_sala();
                salas[num] = nome_sala;
                num++;
                if(tbSalas.getSelectionModel().getSelectedItem().getTecnico().equals("Sim")){
                    salas[num] = nome_sala + " B";
                    num++;
                }
            }
        }
        return salas;
    }

    public String[] retornaNumAulas(){
        int num = Integer.parseInt(txtNumDeSalas.getText()) * 5;
        String numAulas[] = new String[num];
        int a = 1;
        for(int x = 0; x < num; x++){
            numAulas[x] = String.valueOf(a);
            a++;
            if(a == (Integer.parseInt(txtNumDeSalas.getText()) + 1)){
                a = 1;
            }
        }
        return numAulas;
    }

    public void LerArquivoGSON() {
        Gson gson = new Gson();
        int c = 0;
        String[][] jsonTabelaPrincipal;
        String[] jsonNomes;
        String[] jsonEstilos;
        String[] jsonSalas;
        String[] jsonNumAulas;
        try {
            jsonTabelaPrincipal = gson.fromJson(new FileReader(diretorio + "\\tabelaprincipal.json"), String[][].class);

            arrayMatrizControle = jsonTabelaPrincipal;
            JOptionPane.showMessageDialog(null, arrayMatrizControle);

            jsonNomes = gson.fromJson(new FileReader(diretorio + "\\nomes.json"), String[].class);
            arrayProfs = jsonNomes;
            qtdProfessores = arrayProfs.length;

            jsonEstilos = gson.fromJson(new FileReader(diretorio + "\\estilos.json"), String[].class);
            arrayCoresProfs = jsonEstilos;

            jsonSalas = gson.fromJson(new FileReader(diretorio + "\\salas.json"), String[].class);
            arraySalas = jsonSalas;

            jsonNumAulas = gson.fromJson(new FileReader(diretorio + "\\qntsalas.json"), String[].class);
            arrayNumAulas = jsonNumAulas;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
