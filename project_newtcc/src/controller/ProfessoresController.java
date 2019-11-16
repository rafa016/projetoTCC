package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXColorPicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import config.Screen;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.bean.Materias;
import model.bean.Professores;
import model.bean.Salas;
import model.dao.MateriasDAO;
import model.dao.ProfessoresDAO;
import model.dao.SalasDAO;
import model.dao.UsuarioDAO;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class ProfessoresController implements Initializable {

    @FXML private javafx.scene.layout.AnchorPane AnchorPane;
    @FXML private TabPane tabPane;
    @FXML private Tab tabCadastroProfessores;
    @FXML private Tab tabVisualizarProf;
    @FXML private JFXButton btnTelaMenuInicial;
    @FXML private JFXButton btnTelaSalas;
    @FXML private JFXButton btnTelaMaterias;
    @FXML private JFXButton btnTelaGradeEscolar;
    @FXML private JFXButton btnTelaSeguranca;
    @FXML private JFXColorPicker clpCorPrimaria;
    @FXML private JFXColorPicker clpCorSecundaria;
    @FXML private TableView<Materias> tbMaterias;
    @FXML private TableColumn<Materias, Integer> clmIdMateria;
    @FXML private TableColumn<Materias, String> clmNomeMateria;
    @FXML private TableColumn<Materias, String> clmAbrevNome;
    @FXML private TableColumn<Materias, Boolean> clmSelecionarMateria;
    @FXML private TableView<Salas> tbSalas;
    @FXML private TableColumn<Salas, Integer> clmIdSala;
    @FXML private TableColumn<Salas, String> clmNomeSala;
    @FXML private TableColumn<Salas, Boolean> clmSelecionarSala;
    @FXML private TableColumn<Salas, String> clmTecnico;
    @FXML private TableView<Professores> tbProfessores;
    @FXML private TableColumn<Professores, Integer> clmIdProf;
    @FXML private TableColumn<Professores, String> clmNomeProf;
    @FXML private TableColumn<Professores, Integer> clmQuantidadeAulas;
    @FXML private TableColumn<Professores, String> clmSalaProf;
    @FXML private TableColumn<Professores, String> clmMateriaProf;
    @FXML private TableColumn<Professores, String> clmCorPrimaria;
    @FXML private TableColumn<Professores, String> clmCorSecundaria;
    @FXML private TextField txtNomeProf;
    @FXML private TextField txtQtdAulasProf;
    @FXML private JFXButton btnConfig;
    @FXML private JFXButton btnSair;
    @FXML private Pane paneConfig;
    @FXML private JFXRadioButton rdb1280x720;
    @FXML private JFXRadioButton rdb1280x1024;
    @FXML private JFXRadioButton rdb1600x900;
    @FXML private JFXButton btnCadastrar;
    @FXML private JFXButton btnAlterar;
    @FXML private JFXButton btnExcluir;
    @FXML private Pane paneExcluirProf;
    @FXML private JFXButton btnExcluirNao;
    @FXML private JFXButton btnExcluirSim;
    @FXML private JFXButton btnSairExcluirProf;
    @FXML private JFXButton btnMinimizar;
    @FXML private Label lblMsgPreencher;
    @FXML private Pane paneFundo;
    @FXML private JFXButton btnCancelar;
    @FXML private Label lblMsgExistenteNomeProf;
    @FXML private Label lblMsgExistenteCorPrim;
    @FXML private Label lblMsgExistenteCorSec;
    @FXML private JFXTextField txtPesquisarProfessor;
    @FXML private Pane paneMenuInicial;
    @FXML private Pane paneSalas;
    @FXML private Pane paneMaterias;
    @FXML private Pane paneGradeEscolar;
    @FXML private Pane paneSeguranca;

    private boolean confirm = false;
    private String materia = "";
    private String sala = "";
    private String nome = "";
    private int id = 0;
    private Professores selecionada;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        initTable();
        initTableSalas();
        initTableMaterias();

        txtNomeProf.setOnKeyPressed((keyEvent)->{
            if(String.valueOf(keyEvent.getCode()).equals("ENTER")){
                txtQtdAulasProf.requestFocus();
            }
        });

        txtQtdAulasProf.setOnKeyPressed((keyEvent)->{
            if(String.valueOf(keyEvent.getCode()).equals("ENTER")){
                clpCorPrimaria.requestFocus();
            }
        });

        txtPesquisarProfessor.setOnKeyPressed(KeyEvent->{
            if(String.valueOf(KeyEvent.getCode()).equals("BACK_SPACE")){
                if(txtPesquisarProfessor.getText().length() == 0){
                } else if(txtPesquisarProfessor.getText().length() == 1){
                    nome = "";
                    initTable();
                } else if(txtPesquisarProfessor.getText().length() > 1){
                    nome = txtPesquisarProfessor.getText().substring(0,txtPesquisarProfessor.getText().length()-1);
                    initTable();
                }
            }else{
                nome = txtPesquisarProfessor.getText() + KeyEvent.getText();
                initTable();
            }
        });

        rdb1280x720.setOnMouseClicked((MouseEvent e)->{
            if(rdb1280x1024.isSelected()){
                String Path = "TelaProfessores1280x720";
                Screen screen = new Screen();
                screen.restart(Path);
            }
            if(rdb1600x900.isSelected()){
                String Path = "TelaProfessores1280x720";
                Screen screen = new Screen();
                screen.restart(Path);
            }
        });

        rdb1280x1024.setOnMouseClicked((MouseEvent e)->{
            if(rdb1280x720.isSelected()){
                String Path = "TelaProfessores1280x1024";
                Screen screen = new Screen();
                screen.restart(Path);
            }
            if(rdb1600x900.isSelected()){
                String Path = "TelaProfessores1280x1024";
                Screen screen = new Screen();
                screen.restart(Path);
            }
        });

        rdb1600x900.setOnMouseClicked((MouseEvent e)->{
            if(rdb1280x720.isSelected()){
                String Path = "TelaProfessores1600x900";
                Screen screen = new Screen();
                screen.restart(Path);
            }
            if(rdb1280x1024.isSelected()){
                String Path = "TelaProfessores1600x900";
                Screen screen = new Screen();
                screen.restart(Path);
            }
        });

        AnchorPane.setOnMouseClicked((MouseEvent e)->{
            paneConfig.setVisible(false);
            tbProfessores.getSelectionModel().clearSelection();
            tbSalas.getSelectionModel().clearSelection();
            tbMaterias.getSelectionModel().clearSelection();
            btnAlterar.setDisable(true);
            btnExcluir.setDisable(true);
            AnchorPane.requestFocus();
        });

        tabPane.setOnMouseClicked((MouseEvent e)->{
            paneConfig.setVisible(false);
            tbProfessores.getSelectionModel().clearSelection();
            tbSalas.getSelectionModel().clearSelection();
            tbMaterias.getSelectionModel().clearSelection();
            btnAlterar.setDisable(true);
            btnExcluir.setDisable(true);
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

        btnTelaGradeEscolar.setOnMouseClicked((MouseEvent e)->{
            if(rdb1280x720.isSelected()){
                String Path = "TelaConfigGrade1280x720";
                Screen screen = new Screen();
                screen.restartTelas(Path);
            }
            if(rdb1280x1024.isSelected()){
                String Path = "TelaConfigGrade1280x1024";
                Screen screen = new Screen();
                screen.restartTelas(Path);
            }
            if(rdb1600x900.isSelected()){
                String Path = "TelaConfigGrade1600x900";
                Screen screen = new Screen();
                screen.restartTelas(Path);
            }
        });

        btnTelaGradeEscolar.setOnMouseEntered((MouseEvent e)->{
            btnTelaGradeEscolar.setStyle("-fx-background-color: #7d7e80; -fx-border-color: #7d7e80");
            paneGradeEscolar.setStyle("-fx-background-color: #56e8e0; -fx-border-color: #56e8e0");
        });

        btnTelaGradeEscolar.setOnMouseExited((MouseEvent e)->{
            btnTelaGradeEscolar.setStyle("-fx-background-color: #27282A");
            paneGradeEscolar.setStyle("-fx-background-color: #27282A; -fx-border-color: #27282A");
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

        btnCadastrar.setOnMouseClicked((MouseEvent e)->{
            ProfessoresDAO professoresDAO = new ProfessoresDAO();
            boolean confirmMat = false, confirmSala = false;

            int quantTbMat = tbMaterias.getItems().size();
            int quantTbSala = tbSalas.getItems().size();

            for(int x = 0; x < quantTbMat; x++) {
                tbMaterias.getSelectionModel().select(x);
                if (tbMaterias.getItems().get(x).getSelect_mat().isSelected()) {
                    confirmMat = true;
                }
            }
            for(int x = 0; x < quantTbSala; x++) {
                tbSalas.getSelectionModel().select(x);
                if (tbSalas.getItems().get(x).getSelect_sala().isSelected()) {
                    confirmSala = true;
                }
            }
            if(selecionada == null || !confirm){
                if(txtNomeProf.getText() == null || txtNomeProf.getText().trim().equals("") || txtQtdAulasProf.getText() == null || txtQtdAulasProf.getText().trim().equals("") || String.valueOf(clpCorPrimaria.getValue()).equals("0xffffffff") || String.valueOf(clpCorSecundaria.getValue()).equals("0xffffffff") ||!confirmMat || !confirmSala){
                    lblMsgPreencher.setText("Preencha Todos os Campos");
                    lblMsgExistenteNomeProf.setText("");
                    lblMsgExistenteCorPrim.setText("");
                    lblMsgExistenteCorSec.setText("");
                }else{
                    lblMsgPreencher.setText("");
                    lblMsgExistenteNomeProf.setText("");
                    if (professoresDAO.confirmarNomeProf(txtNomeProf.getText())) {
                        lblMsgExistenteNomeProf.setText("*este Professor já existe*");
                        lblMsgPreencher.setText("");
                    }
                    lblMsgExistenteCorPrim.setText("");
                    if (professoresDAO.confirmarCorPrim(String.valueOf(clpCorPrimaria.getValue()))) {
                        lblMsgExistenteCorPrim.setText("*esta Cor já está em uso*");
                        lblMsgPreencher.setText("");
                    }
                    lblMsgExistenteCorSec.setText("");
                    if (professoresDAO.confirmarCorSec(String.valueOf(clpCorSecundaria.getValue()))) {
                        lblMsgExistenteCorSec.setText("*esta Cor já está em uso*");
                        lblMsgPreencher.setText("");
                    }
                    if (!professoresDAO.confirmarNomeProf(txtNomeProf.getText()) && !professoresDAO.confirmarCorPrim(String.valueOf(clpCorPrimaria.getValue())) && !professoresDAO.confirmarCorSec(String.valueOf(clpCorSecundaria.getValue()))) {
                        Professores professores = new Professores();
                        professores.setNome_prof(txtNomeProf.getText());
                        professores.setQtd_aulas_prof(Integer.parseInt(txtQtdAulasProf.getText()));
                        professores.setCor_primaria("#" + String.valueOf(clpCorPrimaria.getValue()).substring(2, 8));
                        professores.setCor_secundaria("#" + String.valueOf(clpCorSecundaria.getValue()).substring(2, 8));

                        professoresDAO.Create(professores);

                        professoresDAO.RetornaId();
                        int id_prof = professoresDAO.RetornaId();

                        // ------------------------------------------------------------------------- //
                        for (int x = 0; x < quantTbMat; x++) {

                            tbMaterias.getSelectionModel().select(x);
                            Materias materias = new Materias();

                            if (tbMaterias.getItems().get(x).getSelect_mat().isSelected()) {
                                int id_materia = tbMaterias.getSelectionModel().getSelectedItem().getId_materia();
                                materias.setId_materia(id_materia);
                                professores.setId_prof(id_prof);
                                professoresDAO.CadastraMaterias(professores, materias);
                            }
                        }
                        initTableMaterias();
                        // ------------------------------------------------------------------------- //
                        for (int x = 0; x < quantTbSala; x++) {

                            tbSalas.getSelectionModel().select(x);
                            Salas salas = new Salas();

                            if (tbSalas.getItems().get(x).getSelect_sala().isSelected()) {
                                int id_sala = tbSalas.getSelectionModel().getSelectedItem().getId_sala();
                                salas.setId_sala(id_sala);
                                professores.setId_prof(id_prof);
                                professoresDAO.CadastraSalas(professores, salas);
                            }
                        }
                        initTableSalas();

                        txtNomeProf.setText("");
                        txtQtdAulasProf.setText("");
                        clpCorPrimaria.setValue(javafx.scene.paint.Color.WHITE);
                        clpCorSecundaria.setValue(javafx.scene.paint.Color.WHITE);

                        initTable();
                    }
                }
            }else {
                if(txtNomeProf.getText() == null || txtNomeProf.getText().trim().equals("") ||!confirmMat || !confirmSala){
                    lblMsgPreencher.setText("Preencha Todos os Campos");
                    lblMsgExistenteNomeProf.setText("");
                    lblMsgExistenteCorPrim.setText("");
                    lblMsgExistenteCorSec.setText("");
                }else{
                    int num = 0;
                    lblMsgPreencher.setText("");

                    lblMsgExistenteNomeProf.setText("");
                    if (professoresDAO.confirmarNomeProf(txtNomeProf.getText()) && !txtNomeProf.getText().equals(selecionada.getNome_prof())) {
                        lblMsgExistenteNomeProf.setText("*este Professor já existe*");
                        lblMsgPreencher.setText("");
                        num++;
                    }

                    String corPrim = "#" + String.valueOf(clpCorPrimaria.getValue()).substring(2, 8);
                    lblMsgExistenteCorPrim.setText("");
                    if (professoresDAO.confirmarCorPrim(String.valueOf(clpCorPrimaria.getValue())) && !corPrim.equals(selecionada.getCor_primaria())) {
                        lblMsgExistenteCorPrim.setText("*esta cor já está em uso*");
                        lblMsgPreencher.setText("");
                        num++;
                    }

                    String corSec = "#" + String.valueOf(clpCorSecundaria.getValue()).substring(2, 8);
                    lblMsgExistenteCorSec.setText("");
                    if (professoresDAO.confirmarCorSec(String.valueOf(clpCorSecundaria.getValue())) && !corSec.equals(selecionada.getCor_secundaria())) {
                        lblMsgExistenteCorSec.setText("*esta cor já está em uso*");
                        lblMsgPreencher.setText("");
                        num++;
                    }

                    if(num == 0) {
                        Professores professores = new Professores();
                        professores.setId_prof(selecionada.getId_prof());
                        professores.setNome_prof(txtNomeProf.getText());
                        professores.setQtd_aulas_prof(Integer.parseInt(txtQtdAulasProf.getText()));
                        professores.setCor_primaria("#" + String.valueOf(clpCorPrimaria.getValue()).substring(2, 8));
                        professores.setCor_secundaria("#" + String.valueOf(clpCorSecundaria.getValue()).substring(2, 8));

                        professoresDAO.Update(professores);
                        professoresDAO.DeleteSalasMaterias(professores);

                        // ------------------------------------------------------------------------- //
                        for (int x = 0; x < quantTbMat; x++) {

                            tbMaterias.getSelectionModel().select(x);
                            Materias materias = new Materias();

                            if (tbMaterias.getItems().get(x).getSelect_mat().isSelected()) {
                                int id_materia = tbMaterias.getSelectionModel().getSelectedItem().getId_materia();
                                materias.setId_materia(id_materia);
                                professores.setId_prof(selecionada.getId_prof());
                                professoresDAO.CadastraMaterias(professores, materias);
                            }
                        }
                        // ------------------------------------------------------------------------- //
                        for (int x = 0; x < quantTbSala; x++) {

                            tbSalas.getSelectionModel().select(x);
                            Salas salas = new Salas();

                            if (tbSalas.getItems().get(x).getSelect_sala().isSelected()) {
                                int id_sala = tbSalas.getSelectionModel().getSelectedItem().getId_sala();
                                salas.setId_sala(id_sala);
                                professores.setId_prof(selecionada.getId_prof());
                                professoresDAO.CadastraSalas(professores, salas);
                            }
                        }

                        id = 0;
                        initTable();
                        initTableSalas();
                        initTableMaterias();

                        txtNomeProf.setText("");
                        txtQtdAulasProf.setText("");
                        lblMsgPreencher.setText("");
                        lblMsgExistenteNomeProf.setText("");
                        lblMsgExistenteCorPrim.setText("");
                        lblMsgExistenteCorSec.setText("");
                        clpCorPrimaria.setValue(javafx.scene.paint.Color.WHITE);
                        clpCorSecundaria.setValue(javafx.scene.paint.Color.WHITE);
                        tabVisualizarProf.setDisable(false);

                        btnCancelar.setVisible(false);
                        tbProfessores.getSelectionModel().clearSelection();
                        btnAlterar.setVisible(true);
                        btnExcluir.setVisible(true);
                        btnCadastrar.setText("Cadastrar");
                    }
                }
            }
            tbSalas.getSelectionModel().clearSelection();
            tbMaterias.getSelectionModel().clearSelection();
        });

        btnAlterar.setOnMouseClicked((MouseEvent e)->{
            confirm = true;
            tabCadastroProfessores.getTabPane().getSelectionModel().select(tabCadastroProfessores);
            txtNomeProf.setText(selecionada.getNome_prof());
            txtQtdAulasProf.setText(String.valueOf(selecionada.getQtd_aulas_prof()));
            clpCorPrimaria.setValue(Color.valueOf(selecionada.getCor_primaria()));
            clpCorSecundaria.setValue(Color.valueOf(selecionada.getCor_secundaria()));
            id = selecionada.getId_prof();

            initTableMaterias();
            initTableSalas();

            btnCancelar.setVisible(true);
            btnAlterar.setVisible(false);
            btnExcluir.setVisible(false);
            tabVisualizarProf.setDisable(true);
            btnCadastrar.setText("Alterar");
            lblMsgPreencher.setText("");
            lblMsgExistenteNomeProf.setText("");
            lblMsgExistenteCorPrim.setText("");
            lblMsgExistenteCorSec.setText("");
        });

        btnExcluir.setOnMouseClicked((MouseEvent e)->{
            confirm = false;
            ProfessoresDAO professoresDAO = new ProfessoresDAO();
            professoresDAO.Delete(selecionada);
            initTable();
        });

        btnCancelar.setOnMouseClicked((MouseEvent e)->{
            confirm = false;
            txtNomeProf.setText("");
            txtQtdAulasProf.setText("");
            lblMsgPreencher.setText("");
            lblMsgExistenteNomeProf.setText("");
            lblMsgExistenteCorPrim.setText("");
            lblMsgExistenteCorSec.setText("");
            btnCadastrar.setText("Cadastrar");

            btnAlterar.setVisible(true);
            btnExcluir.setVisible(true);
            btnCancelar.setVisible(false);
            tabVisualizarProf.setDisable(false);

            clpCorPrimaria.setValue(javafx.scene.paint.Color.WHITE);
            clpCorSecundaria.setValue(javafx.scene.paint.Color.WHITE);
            tbProfessores.getSelectionModel().clearSelection();
            btnAlterar.setDisable(true);
            btnExcluir.setDisable(true);

            id = 0;

            initTableSalas();
            initTableMaterias();

        });

        btnExcluir.setOnMouseClicked((MouseEvent e)->{
            paneFundo.setVisible(true);
            paneExcluirProf.setVisible(true);
            lblMsgPreencher.setText("");
        });

        btnExcluirSim.setOnMouseClicked((MouseEvent e)->{
            paneFundo.setVisible(false);
            paneExcluirProf.setVisible(false);
            ProfessoresDAO professoresDAO = new ProfessoresDAO();
            professoresDAO.Delete(selecionada);
            initTable();
        });

        btnExcluirNao.setOnMouseClicked((MouseEvent e)->{
            paneFundo.setVisible(false);
            paneExcluirProf.setVisible(false);
        });

        btnSairExcluirProf.setOnMouseClicked((MouseEvent e)->{
            paneFundo.setVisible(false);
            paneExcluirProf.setVisible(false);
        });

        tbProfessores.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Professores>() {
            @Override
            public void changed(ObservableValue<? extends Professores> observable, Professores oldValue, Professores newValue) {
                selecionada = (Professores) newValue;
                btnAlterar.setDisable(false);
                btnExcluir.setDisable(false);
            }
        });

    }

    public void initTable(){
        clmIdProf.setCellValueFactory(new PropertyValueFactory("id_prof"));
        clmNomeProf.setCellValueFactory(new PropertyValueFactory("nome_prof"));
        clmQuantidadeAulas.setCellValueFactory(new PropertyValueFactory("qtd_aulas_prof"));
        clmSalaProf.setCellValueFactory(new PropertyValueFactory("salas_prof"));
        clmMateriaProf.setCellValueFactory(new PropertyValueFactory("materias_prof"));
        clmCorPrimaria.setCellValueFactory(new PropertyValueFactory("btn_cor_primaria"));
        clmCorSecundaria.setCellValueFactory(new PropertyValueFactory("btn_cor_secundaria"));
        tbProfessores.setItems(atualizaTabela());
    }

    public ObservableList<Professores> atualizaTabela(){
        ProfessoresDAO professoresDAO = new ProfessoresDAO();
        return FXCollections.observableArrayList(professoresDAO.getList(nome));
    }

    public void initTableMaterias(){
        clmIdMateria.setCellValueFactory(new PropertyValueFactory("id_materia"));
        clmNomeMateria.setCellValueFactory(new PropertyValueFactory("nome_materia"));
        clmAbrevNome.setCellValueFactory(new PropertyValueFactory("abrev_nome"));
        clmSelecionarMateria.setCellValueFactory(new PropertyValueFactory("select_mat"));
        tbMaterias.setItems(atualizaTabelaMaterias());
    }

    public ObservableList<Materias> atualizaTabelaMaterias(){
        MateriasDAO materiasDAO = new MateriasDAO();
        return FXCollections.observableArrayList(materiasDAO.getList(id, materia));
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

}
