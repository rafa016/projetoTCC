package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import config.Screen;
import javafx.scene.input.MouseEvent;
import javafx.fxml.Initializable;
import java.awt.*;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.dao.HistoricoDAO;
import model.dao.UsuarioDAO;

import java.net.URL;

public class MenuInicialController implements Initializable {

    @FXML private javafx.scene.layout.AnchorPane AnchorPane;
    @FXML private JFXButton btnTelaSalas;
    @FXML private JFXButton btnTelaMaterias;
    @FXML private JFXButton btnTelaProfessores;
    @FXML private JFXButton btnTelaGradeEscolar;
    @FXML private JFXButton btnTelaSeguranca;
    @FXML private JFXButton btnConfig;
    @FXML private JFXButton btnMinimizar;
    @FXML private JFXButton btnSair;
    @FXML private Pane paneConfig;
    @FXML private JFXRadioButton rdb1280x720;
    @FXML private JFXRadioButton rdb1600x900;
    @FXML private JFXRadioButton rdb1280x1024;
    @FXML private Pane paneSalas;
    @FXML private Pane paneMaterias;
    @FXML private Pane paneProfessores;
    @FXML private Pane paneGradeEscolar;
    @FXML private Pane paneSeguranca;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        rdb1280x720.setOnMouseClicked((MouseEvent e)->{
            if(rdb1280x1024.isSelected()){
                String Path = "TelaMenuInicial1280x720";
                Screen screen = new Screen();
                screen.restart(Path);
            }
            if(rdb1600x900.isSelected()){
                String Path = "TelaMenuInicial1280x720";
                Screen screen = new Screen();
                screen.restart(Path);
            }
        });

        rdb1280x1024.setOnMouseClicked((MouseEvent e)->{
            if(rdb1280x720.isSelected()){
                String Path = "TelaMenuInicial1280x1024";
                Screen screen = new Screen();
                screen.restart(Path);
            }
            if(rdb1600x900.isSelected()){
                String Path = "TelaMenuInicial1280x1024";
                Screen screen = new Screen();
                screen.restart(Path);
            }
        });

        rdb1600x900.setOnMouseClicked((MouseEvent e)->{
            if(rdb1280x720.isSelected()){
                String Path = "TelaMenuInicial1600x900";
                Screen screen = new Screen();
                screen.restart(Path);
            }
            if(rdb1280x1024.isSelected()){
                String Path = "TelaMenuInicial1600x900";
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
            if(d.width >= 1280 && d.height >= 1024){
                if(rdb1280x1024.isSelected()){
                    rdb1280x1024.setDisable(true);
                }else{
                    rdb1280x1024.setDisable(false);
                }
            }
            if(d.width >= 1600 && d.height >= 900){
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

    }
}
