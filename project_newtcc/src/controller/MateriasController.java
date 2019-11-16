package controller;

import com.jfoenix.controls.JFXButton;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.bean.Materias;
import model.bean.Salas;
import model.dao.MateriasDAO;
import model.dao.SalasDAO;
import model.dao.UsuarioDAO;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class MateriasController implements Initializable {
    @FXML private AnchorPane AnchorPane;
    @FXML private JFXButton btnTelaMenuInicial;
    @FXML private JFXButton btnTelaSalas;
    @FXML private JFXButton btnTelaProfessores;
    @FXML private JFXButton btnTelaGradeEscolar;
    @FXML private JFXButton btnTelaSeguranca;
    @FXML private TextField txtNomeMateria;
    @FXML private Button btnCadastrar;
    @FXML private Button btnAlterar;
    @FXML private Button btnExcluir;
    @FXML private TextField txtAbrevNome;
    @FXML private JFXButton btnConfig;
    @FXML private JFXButton btnSair;
    @FXML private Pane paneConfig;
    @FXML private TableView<Materias> tbMateria;
    @FXML private TableColumn<Materias, Integer> clmId;
    @FXML private TableColumn<Materias, String> clmNomeMateria;
    @FXML private TableColumn<Materias, String> clmAbrevNome;
    @FXML private JFXRadioButton rdb1280x720;
    @FXML private JFXRadioButton rdb1600x900;
    @FXML private JFXRadioButton rdb1280x1024;
    @FXML private Label lblMsgPreencher;
    @FXML private JFXButton btnCancelar;
    @FXML private JFXButton btnMinimizar;
    @FXML private Pane paneFundo;
    @FXML private Pane paneExcluirMateria;
    @FXML private JFXButton btnExcluirNao;
    @FXML private JFXButton btnExcluirSim;
    @FXML private JFXButton btnSairExcluirMateria;
    @FXML private JFXButton btnPesquisarMaterias;
    @FXML private JFXButton btnRestaurarMaterias;
    @FXML private JFXTextField txtPesquisarMateria;
    @FXML private Label lblMsgExistenteNome;
    @FXML private Label lblMsgExistenteAbrev;
    @FXML private Label lblMsgErroExcluir;
    @FXML private Pane paneMenuInicial;
    @FXML private Pane paneSalas;
    @FXML private Pane paneProfessores;
    @FXML private Pane paneGradeEscolar;
    @FXML private Pane paneSeguranca;

    private Materias selecionada;
    private int id = 0;
    private String materia = "";
    private boolean confirm;
    boolean retorno;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        initTable();

        txtNomeMateria.setOnMouseClicked((MouseEvent e)->{
            if(!btnCadastrar.getText().equals("Alterar")){
                tbMateria.getSelectionModel().clearSelection();
            }
            btnAlterar.setDisable(true);
            btnExcluir.setDisable(true);
        });

        txtAbrevNome.setOnMouseClicked((MouseEvent e)->{
            if(!btnCadastrar.getText().equals("Alterar")){
                tbMateria.getSelectionModel().clearSelection();
            }
            btnAlterar.setDisable(true);
            btnExcluir.setDisable(true);
        });

        txtNomeMateria.setOnKeyPressed((keyEvent)->{
            if(String.valueOf(keyEvent.getCode()).equals("ENTER")){
                txtAbrevNome.requestFocus();
            }
        });

        txtAbrevNome.setOnKeyPressed((keyEvent)->{
            if(String.valueOf(keyEvent.getCode()).equals("ENTER")){
                CadastrarAlterarMaterias();
            }
        });

        txtPesquisarMateria.setOnKeyPressed(KeyEvent->{
            if(String.valueOf(KeyEvent.getCode()).equals("BACK_SPACE")){
                if(txtPesquisarMateria.getText().length() == 0){
                } else if(txtPesquisarMateria.getText().length() == 1){
                    materia = "";
                    initTable();
                } else if(txtPesquisarMateria.getText().length() > 1){
                    materia = txtPesquisarMateria.getText().substring(0,txtPesquisarMateria.getText().length()-1);
                    initTable();
                }
            }else{
                materia = txtPesquisarMateria.getText() + KeyEvent.getText();
                initTable();
            }
        });

        rdb1280x720.setOnMouseClicked((MouseEvent e)->{
            if(rdb1280x1024.isSelected()){
                String Path = "TelaMaterias1280x720";
                Screen screen = new Screen();
                screen.restart(Path);
            }
            if(rdb1600x900.isSelected()){
                String Path = "TelaMaterias1280x720";
                Screen screen = new Screen();
                screen.restart(Path);
            }
        });

        rdb1280x1024.setOnMouseClicked((MouseEvent e)->{
            if(rdb1280x720.isSelected()){
                String Path = "TelaMaterias1280x1024";
                Screen screen = new Screen();
                screen.restart(Path);
            }
            if(rdb1600x900.isSelected()){
                String Path = "TelaMaterias1280x1024";
                Screen screen = new Screen();
                screen.restart(Path);
            }
        });

        rdb1600x900.setOnMouseClicked((MouseEvent e)->{
            if(rdb1280x720.isSelected()){
                String Path = "TelaMaterias1600x900";
                Screen screen = new Screen();
                screen.restart(Path);
            }
            if(rdb1280x1024.isSelected()){
                String Path = "TelaMaterias1600x900";
                Screen screen = new Screen();
                screen.restart(Path);
            }
        });

        AnchorPane.setOnMouseClicked((MouseEvent e)->{
            paneConfig.setVisible(false);
            tbMateria.getSelectionModel().clearSelection();
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
           CadastrarAlterarMaterias();
        });

        btnAlterar.setOnMouseClicked((MouseEvent e)->{
            confirm = true;
            txtNomeMateria.setText(selecionada.getNome_materia());
            txtAbrevNome.setText(selecionada.getAbrev_nome());
            btnCancelar.setVisible(true);
            btnAlterar.setVisible(false);
            btnExcluir.setVisible(false);
            btnCadastrar.setText("Alterar");
            lblMsgPreencher.setText("");
            lblMsgExistenteAbrev.setText("");
            lblMsgExistenteNome.setText("");
        });

        btnCancelar.setOnMouseClicked((MouseEvent e)->{
            confirm = false;
            txtNomeMateria.setText("");
            txtAbrevNome.setText("");
            btnCancelar.setVisible(false);
            btnAlterar.setVisible(true);
            btnExcluir.setVisible(true);
            btnCadastrar.setText("Cadastrar");
            lblMsgPreencher.setText("");
            lblMsgExistenteAbrev.setText("");
            lblMsgExistenteNome.setText("");
            tbMateria.getSelectionModel().clearSelection();
            btnAlterar.setDisable(true);
            btnExcluir.setDisable(true);
            AnchorPane.requestFocus();
        });

        btnExcluir.setOnMouseClicked((MouseEvent e)->{
            lblMsgErroExcluir.setText("");
            lblMsgExistenteNome.setText("");
            lblMsgExistenteAbrev.setText("");
            lblMsgPreencher.setText("");
            paneFundo.setVisible(true);
            paneExcluirMateria.setVisible(true);
            lblMsgPreencher.setText("");
        });

        btnExcluirSim.setOnMouseClicked((MouseEvent e)->{
            deleta();
            if(!retorno){
                lblMsgErroExcluir.setText("Esta Matéria está vinculada a um Professor,\ndesvincule-a para Exclui-la");
            }else {
                tbMateria.setItems(atualizaTabela());
                paneFundo.setVisible(false);
                paneExcluirMateria.setVisible(false);
            }
            confirm = false;
        });

        btnExcluirNao.setOnMouseClicked((MouseEvent e)->{
            paneFundo.setVisible(false);
            paneExcluirMateria.setVisible(false);
            confirm = false;
        });

        btnSairExcluirMateria.setOnMouseClicked((MouseEvent e)->{
            paneFundo.setVisible(false);
            paneExcluirMateria.setVisible(false);
            confirm = false;
        });

        tbMateria.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Materias>() {
            @Override
            public void changed(ObservableValue<? extends Materias> observable, Materias oldValue, Materias newValue) {
                selecionada = (Materias) newValue;
                btnAlterar.setDisable(false);
                btnExcluir.setDisable(false);
            }
        });
    }

    public void initTable(){
        clmId.setCellValueFactory(new PropertyValueFactory("id_materia"));
        clmNomeMateria.setCellValueFactory(new PropertyValueFactory("nome_materia"));
        clmAbrevNome.setCellValueFactory(new PropertyValueFactory("abrev_nome"));
        tbMateria.setItems(atualizaTabela());
    }

    public ObservableList<Materias> atualizaTabela(){
        MateriasDAO materiasDAO = new MateriasDAO();
        return FXCollections.observableArrayList(materiasDAO.getList(id, materia));
    }

    public void deleta(){
        if(selecionada != null){
            MateriasDAO materiasDAO = new MateriasDAO();
            retorno = materiasDAO.Delete(selecionada);
        }
    }

    public void CadastrarAlterarMaterias(){
        MateriasDAO materiasDAO = new MateriasDAO();
        Materias materias = new Materias();
        if(selecionada == null || !confirm){
            if(txtNomeMateria.getText() == null || txtAbrevNome.getText() == null || txtNomeMateria.getText().trim().equals("") || txtAbrevNome.getText().trim().equals("")){
                lblMsgPreencher.setText("Preencha Todos os Campos");
                lblMsgExistenteNome.setText("");
                lblMsgExistenteAbrev.setText("");
            }else {
                lblMsgPreencher.setText("");
                lblMsgExistenteNome.setText("");
                if(materiasDAO.confirmarNomeMateria(txtNomeMateria.getText())){
                    lblMsgExistenteNome.setText("*esta matéria já existe*");
                    lblMsgPreencher.setText("");
                }
                lblMsgExistenteAbrev.setText("");
                if(materiasDAO.confirmarAbrevMateria(txtAbrevNome.getText())){
                    lblMsgExistenteAbrev.setText("*esta abrev. já existe*");
                    lblMsgPreencher.setText("");
                }
                if(!materiasDAO.confirmarAbrevMateria(txtAbrevNome.getText()) && !materiasDAO.confirmarNomeMateria(txtNomeMateria.getText())){
                    materias.setNome_materia(txtNomeMateria.getText());
                    materias.setAbrev_nome(txtAbrevNome.getText());
                    materiasDAO.Create(materias);

                    tbMateria.setItems(atualizaTabela());
                    txtNomeMateria.setText("");
                    txtAbrevNome.setText("");
                    lblMsgPreencher.setText("");
                    txtNomeMateria.requestFocus();
                }
            }
        }else{
            if(txtNomeMateria.getText() == null || txtAbrevNome.getText() == null || txtNomeMateria.getText().trim().equals("") || txtAbrevNome.getText().trim().equals("")){
                lblMsgPreencher.setText("Preencha Todos os Campos");
                lblMsgExistenteNome.setText("");
                lblMsgExistenteAbrev.setText("");
            }else {
                int num = 0;
                lblMsgPreencher.setText("");
                lblMsgExistenteNome.setText("");
                if (materiasDAO.confirmarNomeMateria(txtNomeMateria.getText()) && !txtNomeMateria.getText().equals(selecionada.getNome_materia())) {
                    lblMsgExistenteNome.setText("*esta materia já existe*");
                    lblMsgPreencher.setText("");
                    num++;
                }

                lblMsgExistenteAbrev.setText("");
                if (materiasDAO.confirmarAbrevMateria(txtAbrevNome.getText()) && !txtAbrevNome.getText().equals(selecionada.getAbrev_nome())) {
                    lblMsgExistenteAbrev.setText("*esta abrev. já existe*");
                    lblMsgPreencher.setText("");
                    num++;
                }

                if(num == 0) {
                    materias.setId_materia(selecionada.getId_materia());
                    materias.setNome_materia(txtNomeMateria.getText());
                    materias.setAbrev_nome(txtAbrevNome.getText());

                    materiasDAO.Update(materias);

                    tbMateria.setItems(atualizaTabela());
                    txtNomeMateria.setText("");
                    txtAbrevNome.setText("");
                    lblMsgPreencher.setText("");
                    lblMsgExistenteNome.setText("");
                    lblMsgExistenteAbrev.setText("");

                    btnCancelar.setVisible(false);
                    btnAlterar.setVisible(true);
                    btnExcluir.setVisible(true);
                    btnCadastrar.setText("Cadastrar");
                    tbMateria.getSelectionModel().clearSelection();
                    btnAlterar.setDisable(true);
                    btnExcluir.setDisable(true);
                    AnchorPane.requestFocus();
                }
            }
        }
    }

}
