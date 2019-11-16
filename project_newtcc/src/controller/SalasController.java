package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
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
import javafx.stage.Stage;
import model.bean.Salas;
import model.dao.SalasDAO;
import model.dao.UsuarioDAO;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class SalasController implements Initializable {

    @FXML private JFXButton btnTelaMenuInicial;
    @FXML private JFXButton btnTelaSalas;
    @FXML private JFXButton btnTelaMaterias;
    @FXML private JFXButton btnTelaProfessores;
    @FXML private JFXButton btnTelaGradeEscolar;
    @FXML private JFXButton btnTelaSeguranca;
    @FXML private JFXButton btnSair;
    @FXML private TextField txtNomeSala;
    @FXML private JFXButton btnCadastrar;
    @FXML private TableView<Salas> tbSala;
    @FXML private TableColumn<Salas, Integer> clmId;
    @FXML private TableColumn<Salas, String> clmNomeSala;
    @FXML private TableColumn<Salas, String> clmTecnico;
    @FXML private JFXButton btnAlterar;
    @FXML private JFXButton btnExcluir;
    @FXML private JFXButton btnConfig;
    @FXML private Pane paneConfig;
    @FXML private JFXRadioButton rdb1280x720;
    @FXML private JFXRadioButton rdb1280x1024;
    @FXML private JFXRadioButton rdb1600x900;
    @FXML private javafx.scene.layout.AnchorPane AnchorPane;
    @FXML private JFXButton btnMinimizar;
    @FXML private JFXButton btnCancelar;
    @FXML private JFXButton btnExcluirNao;
    @FXML private JFXButton btnExcluirSim;
    @FXML private JFXButton btnSairExcluirSala;
    @FXML private Pane paneFundo;
    @FXML private Pane paneExcluirSala;
    @FXML private Label lblMsgPreencher;
    @FXML private Label lblMsgExistente;
    @FXML private JFXCheckBox txbSim;
    @FXML private JFXCheckBox txbNao;
    @FXML private Label lblMsgErroExcluir;
    @FXML private JFXTextField txtPesquisarSala;
    @FXML private Pane paneMenuInicial;
    @FXML private Pane paneSalas;
    @FXML private Pane paneMaterias;
    @FXML private Pane paneProfessores;
    @FXML private Pane paneGradeEscolar;
    @FXML private Pane paneSeguranca;
    @FXML private javafx.scene.layout.AnchorPane AnchorPaneBotoes;

    private String sala = "";
    private Salas selecionada;
    private int id = 0;
    private boolean confirm;
    private boolean retorno;
    private int contador = 0;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        initTable();

        AnchorPane.getOnMouseClicked();

        AnchorPane.setOnMouseClicked((MouseEvent e)->{
            paneConfig.setVisible(false);
            tbSala.getSelectionModel().clearSelection();
            btnAlterar.setDisable(true);
            btnExcluir.setDisable(true);
            AnchorPane.requestFocus();
            contador = 0;
            LimparBotoes(0);
        });

        AnchorPane.setOnKeyPressed((keyEvent)->{
            if(String.valueOf(keyEvent.getCode()).equals("UP")) {
                if (contador < 1) {
                    btnTelaMenuInicial.requestFocus();
                    SelecionaBotao();
                    contador++;
                }else{
                    if(btnTelaSalas.isFocused()){
                        btnTelaMenuInicial.requestFocus(); //btnTelaMenuInicial.grabFocus();
                    } if(btnTelaMaterias.isFocused()){
                        btnTelaSalas.requestFocus();
                    }else if(btnTelaProfessores.isFocused()){
                        btnTelaMaterias.requestFocus();
                    }else if(btnTelaGradeEscolar.isFocused()){
                        btnTelaProfessores.requestFocus();
                    }else if(btnTelaSeguranca.isFocused()){
                        btnTelaGradeEscolar.requestFocus();
                    }
                    SelecionaBotao();
                }
            }else if(String.valueOf(keyEvent.getCode()).equals("DOWN")) {
                if (contador < 1) {
                    btnTelaMaterias.requestFocus();
                    SelecionaBotao();
                    contador++;
                }else{
                    if(btnTelaMenuInicial.isFocused()){
                        btnTelaSalas.requestFocus();
                    }else if(btnTelaSalas.isFocused()){
                        btnTelaMaterias.requestFocus();
                    }else if(btnTelaMaterias.isFocused()){
                        btnTelaProfessores.requestFocus();
                    }else if(btnTelaProfessores.isFocused()){
                        btnTelaGradeEscolar.requestFocus();
                    }else if(btnTelaGradeEscolar.isFocused()){
                        btnTelaSeguranca.requestFocus();
                    }
                    SelecionaBotao();
                }
            }
        });

        txtNomeSala.setOnMouseClicked((MouseEvent e)->{
            if(!btnCadastrar.getText().equals("Alterar")){
                tbSala.getSelectionModel().clearSelection();
            }
            btnAlterar.setDisable(true);
            btnExcluir.setDisable(true);
        });

        txtNomeSala.setOnKeyPressed((keyEvent)->{
            if(String.valueOf(keyEvent.getCode()).equals("ENTER")){
                txbSim.requestFocus();
                txbSim.setSelected(true);
                txbNao.setSelected(false);
            }
        });

        txbSim.setOnKeyPressed((keyEvent)->{
            if(String.valueOf(keyEvent.getCode()).equals("RIGHT")){
                txbNao.requestFocus();
                txbNao.setSelected(true);
                txbSim.setSelected(false);
            }
            if(String.valueOf(keyEvent.getCode()).equals("ENTER")){
                CadastrarAlterarSalas();
            }
        });

        txbNao.setOnKeyPressed((keyEvent)->{
            if(String.valueOf(keyEvent.getCode()).equals("LEFT")){
                txbSim.requestFocus();
                txbSim.setSelected(true);
                txbNao.setSelected(false);
            }
            if(String.valueOf(keyEvent.getCode()).equals("ENTER")){
                CadastrarAlterarSalas();
            }
        });

        txtPesquisarSala.setOnMouseClicked((MouseEvent e)->{
            txtPesquisarSala.setOnKeyPressed(KeyEvent->{
                if(String.valueOf(KeyEvent.getCode()).equals("BACK_SPACE")){
                    if(txtPesquisarSala.getText().length() == 0){
                    } else if(txtPesquisarSala.getText().length() == 1){
                        sala = "";
                        initTable();
                    } else if(txtPesquisarSala.getText().length() > 1){
                        sala = txtPesquisarSala.getText().substring(0,txtPesquisarSala.getText().length()-1);
                        initTable();
                    }
                }else{
                    sala = txtPesquisarSala.getText() + KeyEvent.getText();
                    initTable();
                }
            });
        });

        txbSim.setOnMouseClicked((MouseEvent e)->{
            if(txbNao.isSelected()){
                txbNao.setSelected(false);
            }
        });

        txbNao.setOnMouseClicked((MouseEvent e)->{
            if(txbSim.isSelected()){
                txbSim.setSelected(false);
            }
        });

        rdb1280x720.setOnMouseClicked((MouseEvent e)->{
            if(rdb1280x1024.isSelected()){
                String Path = "TelaSalas1280x720";
                Screen screen = new Screen();
                screen.restart(Path);
            }
            if(rdb1600x900.isSelected()){
                String Path = "TelaSalas1280x720";
                Screen screen = new Screen();
                screen.restart(Path);
            }
        });

        rdb1280x1024.setOnMouseClicked((MouseEvent e)->{
            if(rdb1280x720.isSelected()){
                String Path = "TelaSalas1280x1024";
                Screen screen = new Screen();
                screen.restart(Path);
            }
            if(rdb1600x900.isSelected()){
                String Path = "TelaSalas1280x1024";
                Screen screen = new Screen();
                screen.restart(Path);
            }
        });

        rdb1600x900.setOnMouseClicked((MouseEvent e)->{
            if(rdb1280x720.isSelected()){
                String Path = "TelaSalas1600x900";
                Screen screen = new Screen();
                screen.restart(Path);
            }
            if(rdb1280x1024.isSelected()){
                String Path = "TelaSalas1600x900";
                Screen screen = new Screen();
                screen.restart(Path);
            }
        });

        btnMinimizar.setOnMouseClicked((MouseEvent e)->{
            Stage stage = (Stage) btnMinimizar.getScene().getWindow();
            stage.setIconified(true);
        });

        btnConfig.setOnMouseClicked((MouseEvent e)->{
            Toolkit tk = Toolkit.getDefaultToolkit();
            Dimension d = tk.getScreenSize();
            paneConfig.setVisible(true);
            if(paneConfig.isVisible()){
                paneConfig.setVisible(false);
            }
            if(d.width > 1280 && d.height > 1024){
                rdb1280x1024.setDisable(false);
                if(rdb1280x1024.isSelected()){
                    rdb1280x1024.setDisable(true);
                }
            }
            if(d.width > 1600 && d.height > 900){
                rdb1600x900.setDisable(false);
                if(rdb1600x900.isSelected()){
                    rdb1600x900.setDisable(true);
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
            CadastrarAlterarSalas();
        });

        btnAlterar.setOnMouseClicked((MouseEvent e)->{
            confirm = true;
            txtNomeSala.setText(selecionada.getNome_sala());
            if (selecionada.getTecnico().trim().equals("Sim")) {
                txbSim.setSelected(true);
            } else {
                txbNao.setSelected(true);
            }
            btnCancelar.setVisible(true);
            btnAlterar.setVisible(false);
            btnExcluir.setVisible(false);
            btnCadastrar.setText("Alterar");
            lblMsgPreencher.setText("");
            lblMsgExistente.setText("");
        });

        btnCancelar.setOnMouseClicked((MouseEvent e)->{
            confirm = false;
            txtNomeSala.setText("");
            txbNao.setSelected(false);
            txbSim.setSelected(false);
            btnCancelar.setVisible(false);
            btnAlterar.setVisible(true);
            btnExcluir.setVisible(true);
            btnCadastrar.setText("Cadastrar");
            lblMsgPreencher.setText("");
            lblMsgExistente.setText("");
            tbSala.getSelectionModel().clearSelection();
            btnAlterar.setDisable(true);
            btnExcluir.setDisable(true);
        });

        btnExcluir.setOnMouseClicked((MouseEvent e)->{
            lblMsgErroExcluir.setText("");
            lblMsgExistente.setText("");
            lblMsgPreencher.setText("");
            paneFundo.setVisible(true);
            paneExcluirSala.setVisible(true);
            btnExcluirNao.setFocusTraversable(true);
        });

        btnExcluirSim.setOnMouseClicked((MouseEvent e)->{
            deleta();
            if(!retorno){
                lblMsgErroExcluir.setText("Esta Sala está vinculada a um Professor,\ndesvincule-a para Exclui-la");
            }else{
                tbSala.setItems(atualizaTabela());
                paneFundo.setVisible(false);
                paneExcluirSala.setVisible(false);
            }
        });

        btnExcluirNao.setOnMouseClicked((MouseEvent e)->{
            paneFundo.setVisible(false);
            paneExcluirSala.setVisible(false);
            btnExcluirNao.setFocusTraversable(false);
        });

        btnSairExcluirSala.setOnMouseClicked((MouseEvent e)->{
            paneFundo.setVisible(false);
            paneExcluirSala.setVisible(false);
        });

        tbSala.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Salas>() {
            @Override
            public void changed(ObservableValue<? extends Salas> observable, Salas oldValue, Salas newValue) {
                selecionada = (Salas) newValue;
                btnAlterar.setDisable(false);
                btnExcluir.setDisable(false);
            }
        });
    }

    public void initTable(){
        clmId.setCellValueFactory(new PropertyValueFactory("id_sala"));
        clmNomeSala.setCellValueFactory(new PropertyValueFactory("nome_sala"));
        clmTecnico.setCellValueFactory(new PropertyValueFactory("tecnico"));
        tbSala.setItems(atualizaTabela());
    }

    public ObservableList<Salas> atualizaTabela(){
        SalasDAO salasDAO = new SalasDAO();
        return FXCollections.observableArrayList(salasDAO.getList(id, sala));
    }

    public void deleta(){
        if(selecionada != null){
            SalasDAO salasDAO = new SalasDAO();
            retorno = salasDAO.Delete(selecionada);
        }
    }

    public void CadastrarAlterarSalas(){
        SalasDAO salasDAO = new SalasDAO();
        Salas salas = new Salas();
        if(selecionada == null || !confirm){
            if(txtNomeSala.getText() == null || txtNomeSala.getText().trim().equals("") || !txbNao.isSelected() && !txbSim.isSelected()){
                lblMsgPreencher.setText("Preencha Todos os Campos");
                lblMsgExistente.setText("");
            }else{
                if(salasDAO.confirmarSala(txtNomeSala.getText())){
                    lblMsgExistente.setText("*esta sala já existe*");
                    lblMsgPreencher.setText("");
                }else{
                    if(txbSim.isSelected()){
                        salas.setTecnico("Sim");
                    }else{
                        salas.setTecnico("Não");
                    }
                    salas.setNome_sala(txtNomeSala.getText());
                    salasDAO.Create(salas);

                    tbSala.setItems(atualizaTabela());
                    txtNomeSala.setText("");
                    lblMsgPreencher.setText("");
                    lblMsgExistente.setText("");
                    txbNao.setSelected(false);
                    txbSim.setSelected(false);
                }
            }
        }else{
            if(txtNomeSala.getText() == null || txtNomeSala.getText().trim().equals("") || !txbNao.isSelected() && !txbSim.isSelected()) {
                lblMsgPreencher.setText("Preencha Todos os Campos");
                lblMsgExistente.setText("");
            }else{
                if(salasDAO.confirmarSala(txtNomeSala.getText()) && !txtNomeSala.getText().equals(selecionada.getNome_sala())){
                    lblMsgExistente.setText("*esta sala já existe*");
                    lblMsgPreencher.setText("");
                }else{
                    salas.setId_sala(selecionada.getId_sala());
                    salas.setNome_sala(txtNomeSala.getText());
                    if (txbSim.isSelected()) {
                        salas.setTecnico("Sim");
                    } else {
                        salas.setTecnico("Não");
                    }
                    salasDAO.Update(salas);

                    tbSala.setItems(atualizaTabela());
                    txtNomeSala.setText("");
                    lblMsgPreencher.setText("");
                    lblMsgExistente.setText("");

                    btnCancelar.setVisible(false);
                    btnAlterar.setVisible(true);
                    btnExcluir.setVisible(true);
                    btnCadastrar.setText("Cadastrar");
                    txbNao.setSelected(false);
                    txbSim.setSelected(false);
                    tbSala.getSelectionModel().clearSelection();
                    btnAlterar.setDisable(true);
                    btnExcluir.setDisable(true);
                }
            }
        }
    }

    public void SelecionaBotao(){
        if(btnTelaMenuInicial.isFocused()){
            LimparBotoes(1);
        } else if(btnTelaSalas.isFocused()){
            LimparBotoes(2);
        } else if(btnTelaMaterias.isFocused()){
            LimparBotoes(3);
        } else if(btnTelaProfessores.isFocused()){
            LimparBotoes(4);
        } else if(btnTelaGradeEscolar.isFocused()){
            LimparBotoes(5);
        } else if(btnTelaSeguranca.isFocused()){
            LimparBotoes(6);
        }
    }

    public void LimparBotoes(int codigo){
        btnTelaMenuInicial.setStyle("-fx-background-color: #27282A");
        paneMenuInicial.setStyle("-fx-background-color: #27282A; -fx-border-color: #27282A");
        btnTelaSalas.setStyle("-fx-background-color: #525355");
        paneSalas.setStyle("-fx-background-color: #20B2AA; -fx-border-color: #20B2AA");
        btnTelaMaterias.setStyle("-fx-background-color: #27282A");
        paneMaterias.setStyle("-fx-background-color: #27282A; -fx-border-color: #27282A");
        btnTelaProfessores.setStyle("-fx-background-color: #27282A");
        paneProfessores.setStyle("-fx-background-color: #27282A; -fx-border-color: #27282A");
        btnTelaGradeEscolar.setStyle("-fx-background-color: #27282A");
        paneGradeEscolar.setStyle("-fx-background-color: #27282A; -fx-border-color: #27282A");
        btnTelaSeguranca.setStyle("-fx-background-color: #27282A");
        paneSeguranca.setStyle("-fx-background-color: #27282A; -fx-border-color: #27282A");

        switch(codigo){
            case 1:
                btnTelaMenuInicial.setStyle("-fx-background-color: #7d7e80; -fx-border-color: #7d7e80");
                paneMenuInicial.setStyle("-fx-background-color: #56e8e0; -fx-border-color: #56e8e0");
                break;
            case 2:
                btnTelaSalas.setStyle("-fx-background-color: #7d7e80; -fx-border-color: #7d7e80");
                paneSalas.setStyle("-fx-background-color: #56e8e0; -fx-border-color: #56e8e0");
                break;
            case 3:
                btnTelaMaterias.setStyle("-fx-background-color: #7d7e80; -fx-border-color: #7d7e80");
                paneMaterias.setStyle("-fx-background-color: #56e8e0; -fx-border-color: #56e8e0");
                break;
            case 4:
                btnTelaProfessores.setStyle("-fx-background-color: #7d7e80; -fx-border-color: #7d7e80");
                paneProfessores.setStyle("-fx-background-color: #56e8e0; -fx-border-color: #56e8e0");
                break;
            case 5:
                btnTelaGradeEscolar.setStyle("-fx-background-color: #7d7e80; -fx-border-color: #7d7e80");
                paneGradeEscolar.setStyle("-fx-background-color: #56e8e0; -fx-border-color: #56e8e0");
                break;
            case 6:
                btnTelaSeguranca.setStyle("-fx-background-color: #7d7e80; -fx-border-color: #7d7e80");
                paneSeguranca.setStyle("-fx-background-color: #56e8e0; -fx-border-color: #56e8e0");
                break;
        }
    }

}
