package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.bean.Historico;
import model.dao.HistoricoDAO;

import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class HistoricoController implements Initializable {

    @FXML private JFXTextField txtPesquisarEvento;
    @FXML private TableView<Historico> tbHistorico;
    @FXML private TableColumn<Historico, String> clmDescricaoEvento;
    @FXML private TableColumn<Historico, String> clmDataEvento;
    @FXML private TableColumn<Historico, String> clmHorarioEvento;

    private String evento = "";

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        initTable();

        tbHistorico.setOnMouseClicked((MouseEvent e)->{
            tbHistorico.getSelectionModel().clearSelection();
        });

        txtPesquisarEvento.setOnKeyPressed(KeyEvent->{
            if(String.valueOf(KeyEvent.getCode()).equals("BACK_SPACE")){
                if(txtPesquisarEvento.getText().length() == 0){
                } else if(txtPesquisarEvento.getText().length() == 1){
                    evento = "";
                    initTable();
                } else if(txtPesquisarEvento.getText().length() > 1){
                    evento = txtPesquisarEvento.getText().substring(0,txtPesquisarEvento.getText().length()-1);
                    initTable();
                }
            }else{
                evento = txtPesquisarEvento.getText() + KeyEvent.getText();
                initTable();
            }
        });

    }

    public void initTable(){
        clmDescricaoEvento.setCellValueFactory(new PropertyValueFactory("descricao_evento"));
        clmDataEvento.setCellValueFactory(new PropertyValueFactory("data_evento"));
        clmHorarioEvento.setCellValueFactory(new PropertyValueFactory("horario_evento"));
        tbHistorico.setItems(atualizaTabela());
    }

    public ObservableList<Historico> atualizaTabela(){
        HistoricoDAO historicoDAO = new HistoricoDAO();
        return FXCollections.observableArrayList(historicoDAO.getList(evento));
    }

}
