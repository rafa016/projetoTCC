package gridPane;




import com.google.gson.Gson;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.sun.javafx.scene.layout.region.Margins;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;


import javax.print.Doc;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.URL;

import java.text.SimpleDateFormat;
import java.util.*;


import static javafx.scene.Cursor.HAND;


public class Controller implements Initializable {

    @FXML GridPane gridPane;
    @FXML Button botaoApagar;
    @FXML GridPane gridPaneModelo;
    @FXML Label labelerro;
    @FXML ScrollPane scroll;
    @FXML ScrollPane scroll2;
    @FXML AnchorPane anchorprincipal;
    @FXML MenuItem menupequeno;
    @FXML MenuItem menugrande;
    @FXML MenuItem menumedio;
    @FXML MenuButton menubotao;
    @FXML Button salvar;
    @FXML Button ler;


    public String[] salas = /*new String[41];*/{
            " ", "1E", "1Q", "1I", "1A", "1B", "1C", "2E", "2Q", "2I","2A", "2B", "2C", "3E", "3Q", "3I","3A", "3B", "3C",
    };
    private String[] professores = {
            "Rubens", "Giu", "Robertop", "Soninha", "Teté", "Thiago"
    };
    private String[] numAula = /*new String[28];*/ {
            "1", "2", "3", "4", "5", "6", "7", "8","1", "2", "3", "4", "5", "6", "7", "8","1", "2", "3", "4", "5", "6", "7", "8", "1", "2", "3", "4", "5", "6", "7", "8", "1", "2", "3", "4", "5", "6", "7", "8"
    };

    private boolean[] tecnico = { false, true, true, true, false, false, false};
    private Node botao, botao2;
    private int c = 1, control, a, b, id, idtroca1, idtroca2, idcontroleigual1, idcontroleigual2;
    private Button[] botoesmodelo = new Button[6];
    private Button[][] botoes = new Button[numAula.length + 1][salas.length];
    private Label[] lblnumaula = new Label[numAula.length];
    private Label[] lblsala = new Label[salas.length];
    private Label[] lblprofessores = new Label[botoesmodelo.length];
    private int[][] matrizcontrole = new int[numAula.length + 1][salas.length];
    private int[][] matrizcontroleigual = new int[numAula.length + 1][salas.length];
    private int[] idmodelos= new int[botoesmodelo.length];
    private String estilo, tamanho = "botao", tamanhoquadrado = "botaoquadrado";

    String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());


    boolean ocupado = false;
    DropShadow dp = new DropShadow();

    String[] styles = {
            "-fx-background-color: #FFFFFF ;-fx-border-color: #FFFFFF;",
            "-fx-background-color: pink ;   -fx-border-color: black;",
            "-fx-background-color: #00FF00; -fx-border-color: #D2691E;",
            "-fx-background-color: #20B2AA; -fx-border-color: #D2B48C;",
            "-fx-background-color: #A020F0; -fx-border-color: #DC143C;",
            "-fx-background-color: #FFD700; -fx-border-color: #5A0082;",
            "-fx-background-color: #BC8F8F; -fx-border-color: #FF69B4;",
            "-fx-background-color: #FF0000; -fx-border-color: #FF0000;",

    };

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        SetarEstiloPadrao();
        ColocarSalas();
        ColocarAulas();
        ColocarBotoesTransparente();
        ColocarBotoesProfessores();
        ColocarNomesProfessores();

        gridPane.getChildren().forEach(item -> {
            item.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    TrocarBotoes(item);
                    CompararBotoes();
                    DeixarQuadrado();
                }
            });
        });
        gridPaneModelo.getChildren().forEach(item -> {
            item.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                   ColocarProfessorTabelaPrincipal(item);
                }
            });
        });

        menupequeno.setOnAction(event -> {
            DeixarBotoesPequenos();
        });
        menugrande.setOnAction(event -> {
            DeixarBotoesGrandes();
        });
        menumedio.setOnAction(event -> {
            DeixarBotoesMedios();
        });
        salvar.setOnAction(event -> {
            CriarPasta();
            EscreverJSONTabela();
            EscreverJSONModelo();
            EscreverJSONCores();
            EscreverJSONNomes();
            EscreverJSONSalas();
            EscreverJSONQNTSalas();
        });
        ler.setOnAction(event -> {
            /*for(int i = 1; i < matrizcontroleigual.length; i++){
                System.out.println();
                for(int j = 1; j < matrizcontroleigual[0].length; j++){
                    System.out.print(" " +matrizcontroleigual[i][j] + " ");
                }
            }*/
            GerarPDF();
        });
    }



    public void PegarEstiloProfessor(Node item) {


        for (Node botao: gridPaneModelo.getChildren()) {
            if(gridPaneModelo.getRowIndex(item) == gridPaneModelo.getRowIndex(botao)){
                DropShadow dp = new DropShadow();
                estilo = item.getStyle();
                dp.setHeight(30);
                dp.setWidth(30);
                dp.setSpread(0.21);
                dp.setColor(Color.BLUE);
                item.setEffect(dp);
            }else{
                botao.setEffect(null);
            }
        }
    }
    public void ColocarProfessorTabelaPrincipal(Node item){
        if(String.valueOf(item.getClass()).equals( "class javafx.scene.control.Button")) {
            if(ocupado == true){
                if(item.getEffect() != null){
                    item.setEffect(null);
                    ocupado = false;
                }else{
                    PegarEstiloProfessor(item);
                    id = Integer.parseInt(item.getId());
                }
            }else{
                PegarEstiloProfessor(item);
                id = Integer.parseInt(item.getId());
                ocupado = true;
            }
        }

    }

    public boolean ApagarBotao() {

        if (ocupado == true) {
            botaoApagar.setEffect(null);
            estilo = null;
            ocupado = false;
        } else {
            DropShadow dp = new DropShadow();
            estilo = "-fx-background-color: #FFFFFF ;-fx-border-color: #FFFFFF; id:0";
            dp.setHeight(30);
            dp.setWidth(30);
            dp.setSpread(0.21);
            dp.setColor(Color.BLUE);
            botaoApagar.setEffect(dp);
            ocupado = true;
        }
        return ocupado;
    }

    public void CompararBotoes() {

        for (int i = 1; i < numAula.length + 1; i++) {
            for (int a = 1; a < salas.length; a++) {
                matrizcontroleigual[i][a] = 0;
            }
        }
        for (int i = 1; i < numAula.length + 1; i++) {
            for (int a = 1; a < salas.length; a++) {
                for (int c = a + 1; c < salas.length; c++) {
                    //System.out.println(i + "" + a + "   " + i+ ""+ c);
                    if (matrizcontrole[i][a] == matrizcontrole[i][c] && matrizcontrole[i][a] != 0 && matrizcontrole[i][c] != 0 && a != c) {
                        matrizcontroleigual[i][a] = 1;
                        matrizcontroleigual[i][c] = 1;
                    }
                }
            }
        }
        System.out.println();
        System.out.println();
    }
    public void DeixarQuadrado() {

        for (int i = 1; i < numAula.length + 1; i++) {
            for (int a = 1; a < salas.length; a++) {
                if (matrizcontroleigual[i][a] == 1) {
                    for (Node node : gridPane.getChildren()) {
                        if (gridPane.getRowIndex(node) == null) {
                        } else if (gridPane.getRowIndex(node) == i && gridPane.getColumnIndex(node) == a) {
                            node.getStyleClass().setAll(tamanhoquadrado);
                        }
                    }
                }else{
                    for (Node node : gridPane.getChildren()) {
                        if (gridPane.getRowIndex(node) == null) {
                        } else if (gridPane.getRowIndex(node) == i && gridPane.getColumnIndex(node) == a) {
                            node.getStyleClass().setAll(tamanho);
                        }
                    }
                }
            }

        }
    }
    public void TrocarBotoes(Node item){
        if(String.valueOf(item.getClass()).equals( "class javafx.scene.control.Button")) {

            if (ocupado == true) {
                item.getStyleClass().setAll(tamanho);
                item.setStyle(estilo);
                matrizcontrole[GridPane.getRowIndex(item)][GridPane.getColumnIndex(item)] = id;
                if (botaoApagar.getEffect() != null) {
                    matrizcontrole[GridPane.getRowIndex(item)][GridPane.getColumnIndex(item)] = 0;
                    matrizcontroleigual[GridPane.getRowIndex(item)][GridPane.getColumnIndex(item)] = 0;
                }
            } else {
                if (control == 1) {
                    if (GridPane.getColumnIndex(item) == b) {
                        botao2 = item;
                        idtroca2 = matrizcontrole[GridPane.getRowIndex(item)][GridPane.getColumnIndex(item)];
                        idcontroleigual2 = matrizcontroleigual[GridPane.getRowIndex(item)][GridPane.getColumnIndex(item)];
                        matrizcontrole[a][b] = idtroca2;
                        matrizcontroleigual[a][b] = idcontroleigual2;
                        matrizcontrole[GridPane.getRowIndex(item)][GridPane.getColumnIndex(item)] = idtroca1;
                        matrizcontroleigual[GridPane.getRowIndex(item)][GridPane.getColumnIndex(item)] = idcontroleigual1;
                        GridPane.setConstraints(botao, GridPane.getColumnIndex(item), GridPane.getRowIndex(item));
                        GridPane.setConstraints(botao2, b, a);
                        botao2.setEffect(null);
                        botao.setEffect(null);
                        labelerro.setVisible(false);
                        control = 0;
                    } else {
                        item.setEffect(null);
                        botao.setEffect(null);
                        control = 0;
                        labelerro.setVisible(true);
                    }
                } else {
                    a = GridPane.getRowIndex(item);
                    b = GridPane.getColumnIndex(item);
                    botao = item;
                    idtroca1 = matrizcontrole[a][b];
                    idcontroleigual1 = matrizcontroleigual[a][b];
                    control = 1;
                    dp.setSpread(0.30);
                    dp.setColor(Color.DARKRED);
                    item.setEffect(dp);
                }
            }
        }
    }

// ---- Montar a tabela principal ----

    public void ColocarSalas() {
        for (int a = 1; a < salas.length; a++) {
            lblsala[a] = new Label();
            lblsala[a].setText(salas[a]);
            lblsala[a].setMinWidth(30);
            lblsala[a].setAlignment(Pos.CENTER);
            gridPane.setConstraints(lblsala[a], a, 0);
            gridPane.setMargin(lblsala[a], new Insets(4));
            gridPane.getChildren().add(lblsala[a]);
        }
    }
    public void ColocarAulas(){
        for (int i = 1; i <= numAula.length; i++) {
            for (int a = 0; a < 1; a++) {
                lblnumaula[i - 1] = new Label();
                lblnumaula[i - 1].setText(numAula[i - 1]);
                lblnumaula[i - 1].setStyle("-fx-font-size: 8px;");
                lblnumaula[i - 1].setAlignment(Pos.CENTER);
                gridPane.setConstraints(lblnumaula[i - 1], 0, c);
                gridPane.setMargin(lblnumaula[i - 1], new Insets(4));
                gridPane.getChildren().addAll(lblnumaula[i - 1]);
                c++;
            }
        }
    }

    public void ColocarBotoesTransparente(){
        for (int i = 1; i < numAula.length + 1; i++) {
            for (int a = 1; a < salas.length; a++) {
                botoes[i][a] = new Button();
                botoes[i][a].setCursor(HAND);
                botoes[i][a].getStyleClass().setAll("botao");
                botoes[i][a].setStyle(styles[0]);
                matrizcontroleigual[i][a] = 0;
                gridPane.setConstraints(botoes[i][a], a, i);
                gridPane.setMargin(botoes[i][a], new Insets(4));
                gridPane.getChildren().addAll(botoes[i][a]);

            }
        }

    }
    // ---- Fim Tabela Principal ----

    // ---- Montar a tabela Professores ----
    public void ColocarBotoesProfessores(){
        int c = 1;
        for (int i = 0; i < 1; i++) {
            for (int a = 0; a < botoesmodelo.length; a++) {
                botoesmodelo[a] = new Button();
                botoesmodelo[a].setCursor(HAND);
                botoesmodelo[a].getStyleClass().add("botao");
                botoesmodelo[a].setStyle(styles[c]);
                botoesmodelo[a].setId(String.valueOf(a + 1));
                idmodelos[a] = a + 1;
                gridPaneModelo.setConstraints(botoesmodelo[a], 0, a);
                gridPane.setMargin(botoesmodelo[a], new Insets(4));
                gridPaneModelo.getChildren().add(botoesmodelo[a]);

                c++;
            }
        }
    }

    public void ColocarNomesProfessores(){
        for (int i = 0; i < 1; i++) {
            for (int a = 0; a < botoesmodelo.length; a++) {
                lblprofessores[a] = new Label();
                lblprofessores[a].setText(professores[a]);
                gridPaneModelo.setConstraints(lblprofessores[a], 1, a);
                gridPane.setMargin(lblprofessores[a], new Insets(4));
                gridPaneModelo.getChildren().add(lblprofessores[a]);
            }
        }

    }
    // ---- Fim Tabela Professores ----

    public void SetarEstiloPadrao(){
        anchorprincipal.getStylesheets().add(getClass().getResource("botao.css").toExternalForm());
        scroll.getStylesheets().add(getClass().getResource("scrollpane.css").toExternalForm());
        scroll2.getStylesheets().add(getClass().getResource("scrollpane.css").toExternalForm());
        gridPane.setGridLinesVisible(true);
        salvar.getStyleClass().add("botaoapagar");
        botaoApagar.getStyleClass().add("botaoapagar");
        botaoApagar.setId(String.valueOf(0));

    }
    // ---- Inicio JSON ----
    private void EscreverJSONTabela(){
        Gson gson = new Gson();
        FileWriter writeFile = null;
        try{
            String jsonTabela = gson.toJson(matrizcontrole);

            writeFile = new FileWriter("backup " +  date + "\\tabelaprincipal.json");
            writeFile.write(
                    jsonTabela);
            writeFile.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    public void LerArquivoJSON() {
        Gson gson = new Gson();
        int c = 0;
        String[][] jsonTabelaPrincipal;
        String[] jsonNomes;
        String[] jsonEstilos;
        String[] jsonSalas;
        String[] jsonNumAulas;

        try {
            jsonTabelaPrincipal = gson.fromJson(new FileReader("backup " +  date + "\\tabelaprincipal.json"), String[][].class);
            JOptionPane.showMessageDialog(null, jsonTabelaPrincipal);

            jsonNomes = gson.fromJson(new FileReader("backup " +  date + "\\nomes.json"), String[].class);
            JOptionPane.showMessageDialog(null, jsonNomes);

            jsonEstilos = gson.fromJson(new FileReader("backup " +  date + "\\estilos.json"), String[].class);
            JOptionPane.showMessageDialog(null, jsonEstilos);

            jsonSalas = gson.fromJson(new FileReader("backup " +  date + "\\salas.json"), String[].class);
            JOptionPane.showMessageDialog(null, jsonSalas);

            jsonNumAulas  = gson.fromJson(new FileReader("backup " +  date + "\\qntsalas.json"), String[].class);
            JOptionPane.showMessageDialog(null, jsonNumAulas);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void EscreverJSONModelo(){
        Gson gson  = new Gson();
        FileWriter writeFile = null;
        try{
            String gsonModelos = gson.toJson(idmodelos);
            writeFile = new FileWriter("backup " +  date + "\\idmodelo.json");
            writeFile.write(gsonModelos);
            writeFile.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    private void EscreverJSONCores(){
        Gson gson = new Gson();
        FileWriter writeFile = null;
        String jsonCoes = gson.toJson(styles);
        try{
            writeFile = new FileWriter("backup " +  date + "\\estilos.json");
            writeFile.write(jsonCoes);
            writeFile.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    private void EscreverJSONNomes(){
        Gson gson = new Gson();
        FileWriter writeFile = null;
        String jsonNomes = gson.toJson(professores);
        try{
            writeFile = new FileWriter("backup " +  date + "\\nomes.json");
            writeFile.write(jsonNomes);
            writeFile.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    private void EscreverJSONSalas(){
        Gson gson = new Gson();
        FileWriter writeFile = null;
        String jsonsalas = gson.toJson(salas);
        try{
            writeFile = new FileWriter("backup " +  date + "\\salas.json");
            writeFile.write(jsonsalas);
            writeFile.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    private void EscreverJSONQNTSalas() {
        Gson gson = new Gson();
        FileWriter writeFile = null;
        String jsonNumAula = gson.toJson(numAula);
        try {
            writeFile = new FileWriter("backup " + date + "\\qntsalas.json");
            writeFile.write(
                    jsonNumAula);
            writeFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // ---- Fim JSON ----

    // ---- Menu do Tamanho dos Botoes ----

    public void DeixarBotoesPequenos(){

        tamanho = "botao";
        tamanhoquadrado = "botaoquadrado";

        for (int i = 1; i < numAula.length + 1; i++) {
            for (int a = 1; a < salas.length; a++) {

                if(String.valueOf(botoes[i][a].getStyleClass()) == "botao" || String.valueOf(botoes[i][a].getStyleClass()) == "botaogrande" || String.valueOf(botoes[i][a].getStyleClass()) =="botaomedio"){
                    botoes[i][a].getStyleClass().setAll("botao");
                }else{
                    botoes[i][a].getStyleClass().setAll("botaoquadrado");
                }
            }
        }
        for (int i = 0; i < 1; i++) {
            for (int a = 0; a < botoesmodelo.length; a++) {
                botoesmodelo[a].getStyleClass().clear();
                botoesmodelo[a].getStyleClass().add("botao");
            }
        }

    }
    public void DeixarBotoesGrandes(){
        tamanho = "botaogrande";
        tamanhoquadrado = "botaoquadradogrande";
        for (int i = 1; i < numAula.length + 1; i++) {
            for (int a = 1; a < salas.length; a++) {
                if(String.valueOf(botoes[i][a].getStyleClass()) == "botao" || String.valueOf(botoes[i][a].getStyleClass()) == "botaogrande" || String.valueOf(botoes[i][a].getStyleClass()) =="botaomedio"){
                    botoes[i][a].getStyleClass().setAll("botaogrande");
                }else{
                    botoes[i][a].getStyleClass().setAll("botaoquadradogrande");
                }
            }
        }
        for (int i = 0; i < 1; i++) {
            for (int a = 0; a < botoesmodelo.length; a++) {
                botoesmodelo[a].getStyleClass().clear();
                botoesmodelo[a].getStyleClass().add("botaogrande");
            }
        }
    }
    public void DeixarBotoesMedios(){
        tamanho = "botaomedio";
        tamanhoquadrado = "botaoquadradomedio";
        for (int i = 1; i < numAula.length + 1; i++) {
            for (int a = 1; a < salas.length; a++) {
                //botoes[i][a].getStyleClass().clear();
                if(String.valueOf(botoes[i][a].getStyleClass()) == "botao" || String.valueOf(botoes[i][a].getStyleClass()) == "botaogrande" || String.valueOf(botoes[i][a].getStyleClass()) =="botaomedio"){
                    botoes[i][a].getStyleClass().setAll("botaomedio");
                }else{
                    botoes[i][a].getStyleClass().setAll("botaoquadradomedio");
                }
            }
        }
        for (int i = 0; i < 1; i++) {
            for (int a = 0; a < botoesmodelo.length; a++) {
                //botoesmodelo[a].getStyleClass().clear();
                botoesmodelo[a].getStyleClass().setAll("botaomedio");
            }
        }
    }
    // ---- Fim Menu Botoes ----
    private void CriarPasta(){
        File diretorio = new File("backup " + date );
        diretorio.mkdir();
    }
    // ---- Gerar PDF ----
    private void GerarPDF(){


        Document doc = new Document(PageSize.A4.rotate(), 0, 0, 20, 20);


        int controlWhiteCells = 0;
        PdfPCell[] cabecalho = new PdfPCell[salas.length];
        float[] widths = new float[salas.length];
        PdfPCell whiteCells;

        PdfPCell[][] corpo = new PdfPCell[numAula.length + 1][salas.length];
        int fontSize = 8;



        try {
            PdfWriter.getInstance(doc , new FileOutputStream("Horario.pdf"));


            doc.open();

            PdfPTable pdftable = new PdfPTable(salas.length );



            for(int i = 0; i < salas.length ; i ++){

                if(i == 0){
                    cabecalho[i]  = new PdfPCell(new Paragraph(" ", FontFactory.getFont(FontFactory.COURIER, fontSize)));
                    cabecalho[i].setFixedHeight(12);
                    pdftable.addCell(cabecalho[i]);
                }else {


                    cabecalho[i] = new PdfPCell(new Paragraph(salas[i], FontFactory.getFont(FontFactory.COURIER, fontSize)));
                    cabecalho[i].setHorizontalAlignment(Element.ALIGN_CENTER);
                    cabecalho[i].setFixedHeight(12);

                    pdftable.addCell(cabecalho[i]);
                }
            }
            for(int i = 1; i < numAula.length + 1; i ++) {
                for(int j = 0; j < salas.length ; j ++) {
                    if(i != 9) {
                        if (i % 8 != 0) {
                            if (j == 0) {
                                corpo[i][j] = new PdfPCell(new Paragraph(numAula[i - 1], FontFactory.getFont(FontFactory.COURIER, fontSize)));
                                corpo[i][j].setHorizontalAlignment(Element.ALIGN_CENTER);
                                corpo[i][j].setFixedHeight(12);
                                pdftable.addCell(corpo[i][j]);
                            } else {
                                if (matrizcontrole[i][j] != 0) {
                                    corpo[i][j] = new PdfPCell(new Paragraph(professores[matrizcontrole[i][j] - 1], FontFactory.getFont(FontFactory.COURIER, fontSize)));
                                    corpo[i][j].setHorizontalAlignment(Element.ALIGN_CENTER);
                                    corpo[i][j].setFixedHeight(12);
                                    pdftable.addCell(corpo[i][j]);
                                } else {

                                    corpo[i][j] = new PdfPCell(new Paragraph(" "));
                                    corpo[i][j].setHorizontalAlignment(Element.ALIGN_CENTER);
                                    corpo[i][j].setFixedHeight(12);
                                    pdftable.addCell(corpo[i][j]);


                                }
                            }
                        } else {


                            whiteCells = new PdfPCell(new Paragraph(" "));
                            pdftable.addCell(whiteCells);


                        }
                    }else{
                        whiteCells = new PdfPCell(new Paragraph(" "));
                        pdftable.addCell(whiteCells);

                    }

                    }
                }

                /*for (int j = 0; j < salas.length + 1; j++) {
                    if (j == 0) {
                        corpo[j] = new PdfPCell(new Paragraph(numAula[j]));
                        corpo[j].setHorizontalAlignment(Element.ALIGN_CENTER);
                        pdftable.addCell(corpo[i][j]);
                    } else {

                        corpo[j] = new PdfPCell(new Paragraph(matrizcontrole[i][j -1] + ""));
                        corpo[j].setHorizontalAlignment(Element.ALIGN_CENTER);
                        pdftable.addCell(corpo[j]);
                    }

                }*/




            doc.add(pdftable);
            doc.close();
            Desktop.getDesktop().open(new File("Horario.pdf"));

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}








