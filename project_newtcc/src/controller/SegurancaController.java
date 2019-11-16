package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import config.Screen;
import connection.ConnectionFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.bean.Seguranca;
import model.dao.SegurancaDAO;
import model.dao.UsuarioDAO;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class SegurancaController implements Initializable {

    @FXML private AnchorPane AnchorPane;
    @FXML private JFXButton btnTelaMenuInicial;
    @FXML private JFXButton btnTelaSalas;
    @FXML private JFXButton btnTelaMaterias;
    @FXML private JFXButton btnTelaProfessores;
    @FXML private JFXButton btnTelaGradeEscolar;
    @FXML private JFXButton btnTelaSeguranca;
    @FXML private JFXButton btnConfig;
    @FXML private JFXButton btnMinimizar;
    @FXML private JFXButton btnSair;
    @FXML private JFXButton btnTrocaSenha;
    @FXML private JFXButton btnRestauraBackup;
    @FXML private JFXButton btnSalvaBackup;
    @FXML private Pane paneFundo;
    @FXML private Pane paneAlterarSenha;
    @FXML private Label lblMsgErro;
    @FXML private Label lblMsgSucesso;
    @FXML private PasswordField txtSenhaMestre;
    @FXML private PasswordField txtSenha;
    @FXML private PasswordField txtConfirmSenha;
    @FXML private JFXButton btnAlterarSenha;
    @FXML private JFXButton btnCancelarAlterarSenha;
    @FXML private JFXButton btnFecharPaneAlterarSenha;
    @FXML private Pane paneSalvarBackup;
    @FXML private JFXButton btnSalvarBackupDoBd;
    @FXML private TextField txtLocalSalvarBackup;
    @FXML private JFXButton btnSelecionarLocal;
    @FXML private JFXButton btnCancelar;
    @FXML private Label lblMsgErroSalvarBackup;
    @FXML private Label lblMsgSucessoSalvarBackup;
    @FXML private JFXButton btnFecharPaneSalvarBackup;
    @FXML private Pane paneRestaurarBackup;
    @FXML private JFXButton btnRestaurarBackupDoBd;
    @FXML private TextField txtLocalRestaurarBackup;
    @FXML private JFXButton btnSelecionarLocalRestaurarBackup;
    @FXML private JFXButton btnCancelarRestaurarBackup;
    @FXML private JFXButton btnConsultarLog;
    @FXML private Label lblMsgErroRestaurarBackup;
    @FXML private Label lblMsgSucessoRestaurarBackup;
    @FXML private JFXButton btnFecharPaneRestaurarBackup;
    @FXML private Pane paneConfig;
    @FXML private JFXRadioButton rdb1280x720;
    @FXML private JFXRadioButton rdb1600x900;
    @FXML private JFXRadioButton rdb1280x1024;
    @FXML private Hyperlink hplPoliticaDeSenhas;
    @FXML private Pane paneMenuInicial;
    @FXML private Pane paneSalas;
    @FXML private Pane paneMaterias;
    @FXML private Pane paneProfessores;
    @FXML private Pane paneGradeEscolar;
    @FXML private Pane paneFundoCarregando;

    String senhaAdmHex;
    String senhaHex;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        paneFundoCarregando.setVisible(false);

        btnConsultarLog.setOnMouseClicked((MouseEvent e)->{
            Stage stage =  new Stage();
            Screen screen = new Screen();
            try {
                screen.startHistoricoLOG(stage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        rdb1280x720.setOnMouseClicked((MouseEvent e)->{
            if(rdb1280x1024.isSelected()){
                String Path = "TelaSeguranca1280x720";
                Screen screen = new Screen();
                screen.restart(Path);
            }
            if(rdb1600x900.isSelected()){
                String Path = "TelaSeguranca1280x720";
                Screen screen = new Screen();
                screen.restart(Path);
            }
        });

        rdb1280x1024.setOnMouseClicked((MouseEvent e)->{
            if(rdb1280x720.isSelected()){
                String Path = "TelaSeguranca1280x1024";
                Screen screen = new Screen();
                screen.restart(Path);
            }
            if(rdb1600x900.isSelected()){
                String Path = "TelaSeguranca1280x1024";
                Screen screen = new Screen();
                screen.restart(Path);
            }
        });

        rdb1600x900.setOnMouseClicked((MouseEvent e)->{
            if(rdb1280x720.isSelected()){
                String Path = "TelaSeguranca1600x900";
                Screen screen = new Screen();
                screen.restart(Path);
            }
            if(rdb1280x1024.isSelected()){
                String Path = "TelaSeguranca1600x900";
                Screen screen = new Screen();
                screen.restart(Path);
            }
        });

        AnchorPane.setOnMouseClicked((MouseEvent e)->{
            paneConfig.setVisible(false);
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

        //-====================================== ALTERAR SENHA ============================================-//
        btnTrocaSenha.setOnMouseClicked((MouseEvent e)->{
            paneFundo.setVisible(true);
            paneAlterarSenha.setVisible(true);
        });

        btnAlterarSenha.setOnMouseClicked((MouseEvent e)->{

            try {
                MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
                byte messageDigest[] = algorithm.digest(txtSenhaMestre.getText().getBytes("UTF-8"));

                StringBuilder hexString = new StringBuilder();
                for (byte b : messageDigest) {
                    hexString.append(String.format("%02X", 0xFF & b));
                }
                senhaAdmHex = hexString.toString();
            } catch (NoSuchAlgorithmException ex) {
                ex.printStackTrace();
            } catch (UnsupportedEncodingException ex) {
                ex.printStackTrace();
            }

            if(txtSenha.getText().equals(txtConfirmSenha.getText())){
                if(senhaAdmHex.equals("8C6976E5B5410415BDE908BD4DEE15DFB167A9C873FC4BB8A81F6F2AB448A918")){
                    char[] caracterEspecial = {'@','’','!','#','$','%','¨','&','*','(',')','-','_','+','§','=','/','°','?',';',':','.','>',',','<','|','[','{',']','}','º','ª','¹','²','³','£','¢','¬'};
                    boolean achouNumero = false;
                    boolean achouMaiuscula = false;
                    boolean achouMinuscula = false;
                    boolean achouSimbolo = false;

                    for (char senha : txtSenha.getText().toCharArray()) {
                        if (senha >= '0' && senha <= '9') {
                            achouNumero = true;
                        } else if (senha >= 'A' && senha <= 'Z') {
                            achouMaiuscula = true;
                        } else if (senha >= 'a' && senha <= 'z') {
                            achouMinuscula = true;
                        }
                        for (int x = 0; x < caracterEspecial.length; x++) {
                            if (senha == caracterEspecial[x]) {
                                achouSimbolo = true;
                            }
                        }
                    }

                    if(txtSenha.getText().length() >= 6 && txtSenha.getText().length() <= 8 && achouNumero && achouMaiuscula && achouMinuscula && achouSimbolo){
                        try {
                            MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
                            byte[] messageDigest = algorithm.digest(txtSenha.getText().getBytes("UTF-8"));

                            StringBuilder hexString = new StringBuilder();
                            for (byte b : messageDigest) {
                                hexString.append(String.format("%02X", 0xFF & b));
                            }
                            senhaHex = hexString.toString();
                        } catch (NoSuchAlgorithmException ex) {
                            ex.printStackTrace();
                        } catch (UnsupportedEncodingException ex) {
                            ex.printStackTrace();
                        }

                        Seguranca seguranca = new Seguranca();
                        seguranca.setNova_senha(senhaHex);

                        SegurancaDAO segurancaDAO = new SegurancaDAO();
                        segurancaDAO.Update(seguranca);

                        txtSenhaMestre.setText("");
                        txtSenha.setText("");
                        txtConfirmSenha.setText("");
                        lblMsgErro.setText("");
                        lblMsgSucesso.setText("Alteração realizada com Sucesso!");
                    }else{
                        lblMsgErro.setText("Senhas Inválidas! Atenda a Política de Senhas");
                    }
                }else{
                    lblMsgErro.setText("Senha Mestre Incorreta!");
                }
            }else{
                lblMsgErro.setText("Senhas Diferentes ou Inválidas!");
            }
        });

        hplPoliticaDeSenhas.setOnMouseClicked((MouseEvent e)->{
            JOptionPane.showMessageDialog(null, "           Política de Senhas\n" +
                    "\n           Tamanho mínimo e máximo: 6 a 8 dígitos;" +
                    "\n           Complexidade:" +
                    "\n                 • uma Letra Maíscula;" +
                    "\n                 • um Caractere Especial (!, @, #, $, &, * ...);              " +
                    "\n                 • um Número.\n\n", "", -1);
        });

        btnCancelarAlterarSenha.setOnMouseClicked((MouseEvent e)->{
            paneFundo.setVisible(false);
            paneAlterarSenha.setVisible(false);
            lblMsgSucesso.setText("");
            lblMsgErro.setText("");
            txtSenha.setText("");
            txtConfirmSenha.setText("");
            txtSenhaMestre.setText("");
        });

        btnFecharPaneAlterarSenha.setOnMouseClicked((MouseEvent e)->{
            paneFundo.setVisible(false);
            paneAlterarSenha.setVisible(false);
            lblMsgSucesso.setText("");
            lblMsgErro.setText("");
            txtSenha.setText("");
            txtConfirmSenha.setText("");
            txtSenhaMestre.setText("");
        });

        //-====================================== SALVAR BACKUP ============================================-//
        btnSalvaBackup.setOnMouseClicked((MouseEvent e)->{
            paneFundo.setVisible(true);
            paneSalvarBackup.setVisible(true);
        });

        btnSelecionarLocal.setOnMouseClicked((MouseEvent e)->{
            JFileChooser jFileChooser = new JFileChooser();
            String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int se = jFileChooser.showSaveDialog(null);
            if(se == JFileChooser.APPROVE_OPTION){
                    String diretorio = jFileChooser.getSelectedFile().getPath();
                    diretorio = diretorio.replace('\\', '/');
                    diretorio = diretorio + "_" + date + ".sql";
                    txtLocalSalvarBackup.setText(diretorio);
            }
        });

        btnSalvarBackupDoBd.setOnMouseClicked((MouseEvent e)->{
            paneFundoCarregando.setVisible(true);
            lblMsgErroSalvarBackup.setText("");
            lblMsgSucessoSalvarBackup.setText("");
            String diretorio = txtLocalSalvarBackup.getText();
            String saveCmd = "C:/Program Files/MySQL/MySQL Workbench 8.0 CE/mysqldump.exe -u" + ConnectionFactory.USER
                    + " -p" + ConnectionFactory.PASS + " --add-drop-database -B HorarioEscolar" + " -r" + diretorio;
            Process process;
            try{
                process = Runtime.getRuntime().exec(saveCmd);
                int procCom = process.waitFor();
                if(procCom == 0){
                    lblMsgSucessoSalvarBackup.setText("Backup Salvo com Sucesso!");
                }else{
                    lblMsgErroSalvarBackup.setText("Não foi Possível Salvar o Backup");
                }
            } catch (Exception ex){}
            paneFundoCarregando.setVisible(false);
            txtLocalSalvarBackup.setText("");
        });

        btnCancelar.setOnMouseClicked((MouseEvent e)->{
            paneSalvarBackup.setVisible(false);
            paneFundo.setVisible(false);
            txtLocalSalvarBackup.setText("");
            lblMsgErroSalvarBackup.setText("");
            lblMsgSucessoSalvarBackup.setText("");
        });

        btnFecharPaneSalvarBackup.setOnMouseClicked((MouseEvent e)->{
            paneSalvarBackup.setVisible(false);
            paneFundo.setVisible(false);
            txtLocalSalvarBackup.setText("");
            lblMsgErroSalvarBackup.setText("");
            lblMsgSucessoSalvarBackup.setText("");
        });

        //-====================================== RESTAURAR BACKUP ============================================-//
        btnRestauraBackup.setOnMouseClicked((MouseEvent e)->{
            paneFundo.setVisible(true);
            paneRestaurarBackup.setVisible(true);
        });

        btnSelecionarLocalRestaurarBackup.setOnMouseClicked((MouseEvent e)->{
            JFileChooser jFileChooser = new JFileChooser();
            FileNameExtensionFilter fileNameExtensionFilter = new FileNameExtensionFilter("SQL", "sql");
            jFileChooser.setFileFilter(fileNameExtensionFilter);
            int se = jFileChooser.showOpenDialog(null);
            if(se == JFileChooser.APPROVE_OPTION){
                String diretorio = jFileChooser.getSelectedFile().getPath();
                diretorio = diretorio.replace('\\', '/');
                txtLocalRestaurarBackup.setText(diretorio);
            }
        });

        btnRestaurarBackupDoBd.setOnMouseClicked((MouseEvent e)->{
            System.out.println("ooooi");
            MostrarCarregando();
            System.out.println("era pra ter deixado visible");
            for(int i = 0; i< 5; i++){

                try {
                    System.out.println("oi");
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }

            }

            lblMsgErroRestaurarBackup.setText("");
            lblMsgSucessoRestaurarBackup.setText("");
            String diretorio = txtLocalRestaurarBackup.getText();
            String[] restoreCmd = new String[]{"C:/Program Files/MySQL/MySQL Workbench 8.0 CE/mysql.exe","--user=root",
                    "--password=" + ConnectionFactory.PASS, "-e", "source " + diretorio};
            Process process;
            try{
                process = Runtime.getRuntime().exec(restoreCmd);
                int procCom = process.waitFor();
                if(procCom == 0){
                    lblMsgSucessoRestaurarBackup.setText("Backup Restaurado com Sucesso!");
                }else{
                    lblMsgErroRestaurarBackup.setText("Não foi Possível Restaurar o Backup");
                }
            } catch (Exception ex){}
            txtLocalRestaurarBackup.setText("");
        });

        btnCancelarRestaurarBackup.setOnMouseClicked((MouseEvent e)->{
            paneRestaurarBackup.setVisible(false);
            paneFundo.setVisible(false);
            txtLocalRestaurarBackup.setText("");
            lblMsgErroRestaurarBackup.setText("");
            lblMsgSucessoRestaurarBackup.setText("");
        });

        btnFecharPaneRestaurarBackup.setOnMouseClicked((MouseEvent e)->{
            paneRestaurarBackup.setVisible(false);
            paneFundo.setVisible(false);
            txtLocalRestaurarBackup.setText("");
            lblMsgErroRestaurarBackup.setText("");
            lblMsgSucessoRestaurarBackup.setText("");
        });
    }

    public void MostrarCarregando(){
        paneFundoCarregando.setVisible(true);
    }

}
