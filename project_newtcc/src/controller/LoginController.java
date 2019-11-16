package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Button;
import javafx.fxml.Initializable;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import config.Screen;
import javafx.stage.Stage;
import model.dao.UsuarioDAO;

import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.sun.java.accessibility.util.AWTEventMonitor.addKeyListener;


public class LoginController implements Initializable {

    @FXML private JFXButton btnEntrar;
    @FXML private JFXPasswordField txtSenha;
    @FXML private Label lblSenhaIncorreta;
    @FXML private Button btnSair;
    @FXML private Button btnMinimizar;

    String senhahex;
    int tentativas = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        txtSenha.setOnKeyPressed((keyEvent)->{
            if(keyEvent.getSource() == txtSenha){
                if(String.valueOf(keyEvent.getCode()).equals("ENTER")){
                    CriptografarSenha();
                    Entrar();
                }
            }
        });

        btnEntrar.setOnMouseClicked((MouseEvent e)->{
            CriptografarSenha();
            Entrar();
        });

        btnSair.setOnMouseClicked((MouseEvent e)->{
            Screen screen = new Screen();
            screen.close();
        });

        btnMinimizar.setOnMouseClicked((MouseEvent e)->{
            Stage stage = (Stage) btnMinimizar.getScene().getWindow();
            stage.setIconified(true);
        });

    }

    public void Entrar(){
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Screen screen = new Screen();
        if(tentativas < 5 || txtSenha.getText().equals("admin")){
            if(usuarioDAO.checkLogin(senhahex)) {
                try {
                    screen.close();
                    String Path = "TelaMenuInicial1280x720";
                    screen.restart(Path);
                } catch (Exception ex) {
                    Logger.getLogger(controller.LoginController.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {
                lblSenhaIncorreta.setText("Senha Incorreta!");
                tentativas++;
            }
        }else {
            lblSenhaIncorreta.setText("Número de Tentativas Excedido!\nEntre com a Senha Mestre");
            tentativas++;
        }
    }

    public void CriptografarSenha(){
        try {
            MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
            byte messageDigest[] = algorithm.digest(txtSenha.getText().getBytes("UTF-8"));

            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                hexString.append(String.format("%02X", 0xFF & b));
            }
            senhahex = hexString.toString();
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
    }

}