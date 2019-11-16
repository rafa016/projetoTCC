package controller;

import com.google.gson.Gson;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Timer;
import static javafx.scene.Cursor.HAND;

public class GradeController implements Initializable {
        @FXML GridPane gridPane;
        @FXML Button deleteButton;
        @FXML GridPane gridPaneModelo;
        @FXML Label labelErro;
        @FXML ScrollPane scroll;
        @FXML ScrollPane scroll2;
        @FXML AnchorPane principalAnchor;
        @FXML MenuItem smallMenu;
        @FXML MenuItem mediumMenu;
        @FXML MenuItem bigMenu;
        @FXML MenuButton menuButton;
        @FXML Button saveButton;
        @FXML Button printButton;
        @FXML Button lateralMenuButton;
        @FXML Button AnchorPane;

        private Node firstClickButton, secondClickButton;
        private int c, control, row, column, teacherID, firstClickButtonID, secondClickButtonID, firstControlEqual, secondControlEqual, numberFontSize, delBusy, numberLines = ConfigGradeController.arrayNumAulas.length + (ConfigGradeController.arrayNumAulas.length / ConfigGradeController.numAulas), lateralMenuSize = 1;
        private Button[] styleButton = new Button[ConfigGradeController.qtdProfessores];
        private Button[][] bodyButtons = new Button[numberLines][ConfigGradeController.arraySalas.length];
        private Label[] lblNumAula = new Label[numberLines];
        private Label[] lblClass = new Label[ConfigGradeController.arraySalas.length];
        private Label[] lblTeachers = new Label[styleButton.length];
        private int[][] controlMatrix = new int[numberLines][ConfigGradeController.arraySalas.length];
        private int[][] controlEqualMatrix = new int[numberLines][ConfigGradeController.arraySalas.length];
        private int[] idModelos = new int[styleButton.length];

        private String style, size = "SmallButton", squareSize = "SquareSmallButton";
        boolean delPressed = false, trocando = false, pegandoModelo = false, maxSizeLateralMenu;
        private Stage stage = new Stage();

        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        config.ToastNotification toast = new config.ToastNotification();

        boolean busy = false;
        DropShadow dp = new DropShadow();

        @Override
        public void initialize(URL location, ResourceBundle resources) {

                setPatternStyle();
                addClassRoom();
                addNumberClasses();
                addTransparentButton();
                addTeacherButton();
                addTeachersNames();
                autoSave();

                gridPane.getChildren().forEach(item -> {
                        item.setOnMouseClicked(new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(MouseEvent event) {
                                        ChangeButtons(item);
                                        CompareButtons();
                                        setSquareStyle();
                                }
                        });
                });
                gridPaneModelo.getChildren().forEach(item -> {
                        item.setOnMouseClicked(new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(MouseEvent event) {
                                        addButtonsInBodyTable(item);
                                }
                        });
                });

                smallMenu.setOnAction(event -> {
                        setSmallStyle();
                });
                mediumMenu.setOnAction(event -> {
                        setMediumStyle();
                });
                bigMenu.setOnAction(event -> {
                        setBigStyle();
                });
                saveButton.setOnAction(event -> {
                        createFile();
                        WriteJSONGridPane();
                        writeJSONmodel();
                        writeJSONcolors();
                        writeJSONnames();
                        writeJSONclassrooms();
                        writeJSONclasses();
                        toastNotification();


                });
                principalAnchor.setOnKeyPressed(event -> {


                        if (event.getCode() == KeyCode.DELETE) {

                                delPressed = true;

                        }
                        if (delPressed & delBusy == 0) {

                                deleteButton();

                        }
                        delBusy += 1;

                });
                principalAnchor.setOnKeyReleased(event -> {
                        if (event.getCode() == KeyCode.DELETE) {

                                delPressed = false;
                                deleteButton();
                                delBusy = 0;


                        }


                });
                printButton.setOnAction(event -> {

                        GerarPDF();
                });
                lateralMenuButton.setOnAction(event -> {


                        Timer timer = new Timer();

                        TimerTask task = new TimerTask() {
                                @Override
                                public void run() {
                                        if (lateralMenuSize == 170) {

                                                timer.cancel();
                                                principalAnchor.setLeftAnchor(scroll2, 10.0);
                                                principalAnchor.setLeftAnchor(scroll, 300.0);
                                                principalAnchor.setLeftAnchor(printButton, 200.0);
                                                principalAnchor.setLeftAnchor(saveButton, 200.0);
                                                principalAnchor.setLeftAnchor(deleteButton, 200.0);

                                                for (int i = 0; i < 1; i++) {
                                                        for (int a = 0; a < styleButton.length; a++) {

                                                                styleButton[a].getStyleClass().setAll("SmallButton");


                                                                c++;
                                                        }
                                                }
                                                maxSizeLateralMenu = true;


                                        } else if (lateralMenuSize == 0) {

                                                maxSizeLateralMenu = false;

                                                principalAnchor.setLeftAnchor(scroll2, -40.0);
                                                principalAnchor.setLeftAnchor(scroll, 130.0);
                                                principalAnchor.setLeftAnchor(printButton, 30.0);
                                                principalAnchor.setLeftAnchor(saveButton, 30.0);
                                                principalAnchor.setLeftAnchor(deleteButton, 30.0);
                                                timer.cancel();
                                        }

                                        if (maxSizeLateralMenu) {
                                                lateralMenuSize--;

                                        } else {
                                                lateralMenuSize++;
                                        }

                                        scroll2.setPrefWidth(lateralMenuSize);


                                }
                        };
                        timer.scheduleAtFixedRate(task, 0, 2);


                });
        }

        public void getTeachersStyle(Node item) {
                if (deleteButton.getEffect() == null) {
                        for (Node button : gridPaneModelo.getChildren()) {
                                if (gridPaneModelo.getRowIndex(item) == gridPaneModelo.getRowIndex(button)) {
                                        DropShadow dp = new DropShadow();
                                        style = item.getStyle();
                                        dp.setHeight(30);
                                        dp.setWidth(30);
                                        dp.setSpread(0.21);
                                        dp.setColor(Color.BLUE);
                                        item.setEffect(dp);
                                } else {
                                        button.setEffect(null);
                                }
                        }
                }
        }

        public void addButtonsInBodyTable(Node item) {
                if (String.valueOf(item.getClass()).equals("class javafx.scene.control.Button")) {
                        if (busy == true) {
                                if (item.getEffect() != null) {
                                        item.setEffect(null);
                                        busy = false;
                                        pegandoModelo = false;
                                } else {
                                        getTeachersStyle(item);
                                        teacherID = Integer.parseInt(item.getId());
                                }
                        } else {
                                getTeachersStyle(item);
                                teacherID = Integer.parseInt(item.getId());
                                busy = true;
                                pegandoModelo = true;
                        }
                }
        }

        public boolean deleteButton() {
                if (pegandoModelo == false) {
                        if (trocando == false) {
                                if (busy == true) {
                                        deleteButton.setEffect(null);
                                        style = null;
                                        busy = false;
                                } else {
                                        DropShadow dp = new DropShadow();
                                        style = "-fx-background-color: #FFFFFF ;-fx-border-color: #FFFFFF; id:0";
                                        dp.setHeight(30);
                                        dp.setWidth(30);
                                        dp.setSpread(0.21);
                                        dp.setColor(Color.BLUE);
                                        deleteButton.setEffect(dp);
                                        busy = true;
                                }
                        } else {


                        }
                }
                return busy;
        }

        public void CompareButtons() {

                for (int i = 1; i < numberLines; i++) {
                        for (int a = 1; a < ConfigGradeController.arraySalas.length; a++) {
                                controlEqualMatrix[i][a] = 0;
                        }
                }
                for (int i = 1; i < numberLines; i++) {
                        for (int a = 1; a < ConfigGradeController.arraySalas.length; a++) {
                                for (int c = a + 1; c < ConfigGradeController.arraySalas.length; c++) {
                                        //System.out.println(i + "" + a + "   " + i+ ""+ c);
                                        if (controlMatrix[i][a] == controlMatrix[i][c] && controlMatrix[i][a] != 0 && controlMatrix[i][c] != 0 && a != c) {
                                                controlEqualMatrix[i][a] = 1;
                                                controlEqualMatrix[i][c] = 1;
                                        }
                                }
                        }
                }

        }

        public void setSquareStyle() {

                for (int i = 1; i < numberLines; i++) {
                        for (int a = 1; a < ConfigGradeController.arraySalas.length; a++) {
                                if (controlEqualMatrix[i][a] == 1) {
                                        for (Node node : gridPane.getChildren()) {
                                                if (GridPane.getRowIndex(node) == null) {
                                                } else if (GridPane.getRowIndex(node) == i && GridPane.getColumnIndex(node) == a) {
                                                        node.getStyleClass().setAll(squareSize);
                                                }
                                        }
                                } else {
                                        for (Node node : gridPane.getChildren()) {
                                                if (GridPane.getRowIndex(node) == null) {
                                                } else if (GridPane.getRowIndex(node) == i && GridPane.getColumnIndex(node) == a) {
                                                        node.getStyleClass().setAll(size);
                                                }
                                        }
                                }
                        }

                }
        }

        public void ChangeButtons(Node item) {
                if (String.valueOf(item.getClass()).equals("class javafx.scene.control.Button")) {

                        if (busy == true) {
                                item.getStyleClass().setAll(size);
                                item.setStyle(style);
                                controlMatrix[GridPane.getRowIndex(item)][GridPane.getColumnIndex(item)] = teacherID;
                                if (deleteButton.getEffect() != null) {
                                        controlMatrix[GridPane.getRowIndex(item)][GridPane.getColumnIndex(item)] = 0;
                                        controlEqualMatrix[GridPane.getRowIndex(item)][GridPane.getColumnIndex(item)] = 0;
                                }
                        } else {
                                if (control == 1) {
                                        if (GridPane.getColumnIndex(item) == column) {
                                                secondClickButton = item;
                                                secondClickButtonID = controlMatrix[GridPane.getRowIndex(item)][GridPane.getColumnIndex(item)];
                                                secondControlEqual = controlEqualMatrix[GridPane.getRowIndex(item)][GridPane.getColumnIndex(item)];
                                                controlMatrix[row][column] = secondClickButtonID;
                                                controlEqualMatrix[row][column] = secondControlEqual;
                                                controlMatrix[GridPane.getRowIndex(item)][GridPane.getColumnIndex(item)] = firstClickButtonID;
                                                controlEqualMatrix[GridPane.getRowIndex(item)][GridPane.getColumnIndex(item)] = firstControlEqual;
                                                gridPane.setConstraints(firstClickButton, GridPane.getColumnIndex(item), GridPane.getRowIndex(item));
                                                gridPane.setConstraints(secondClickButton, column, row);
                                                secondClickButton.setEffect(null);
                                                firstClickButton.setEffect(null);
                                                labelErro.setVisible(false);
                                                control = 0;
                                        } else {
                                                item.setEffect(null);
                                                firstClickButton.setEffect(null);
                                                control = 0;
                                                labelErro.setVisible(true);
                                        }
                                } else {
                                        row = GridPane.getRowIndex(item);
                                        column = GridPane.getColumnIndex(item);
                                        firstClickButton = item;
                                        firstClickButtonID = controlMatrix[row][column];
                                        firstControlEqual = controlEqualMatrix[row][column];
                                        control = 1;
                                        dp.setSpread(0.30);
                                        dp.setColor(Color.DARKRED);
                                        item.setEffect(dp);
                                }
                        }
                }
        }

// ---- Montar a tabela principal ----

        public void addClassRoom() {
                for (int a = 1; a < ConfigGradeController.arraySalas.length; a++) {
                        lblClass[a] = new Label();
                        lblClass[a].setText(ConfigGradeController.arraySalas[a]);
                        lblClass[a].setMinWidth(30);
                        lblClass[a].setAlignment(Pos.CENTER);
                        gridPane.setConstraints(lblClass[a], a, 0);
                        gridPane.setMargin(lblClass[a], new Insets(4));
                        gridPane.getChildren().add(lblClass[a]);
                }
        }

        public void addNumberClasses() {

                for (int i = 1; i <= numberLines; i++) {
                        for (int a = 0; a < 1; a++) {
                                if (i % (ConfigGradeController.numAulas + 1) == 0) {
                                        lblNumAula[i - 1] = new Label();

                                        lblNumAula[i - 1].setStyle("-fx-font-size: 8px;");
                                        lblNumAula[i - 1].setAlignment(Pos.CENTER);
                                        gridPane.setConstraints(lblNumAula[i - 1], 0, i);
                                        gridPane.setMargin(lblNumAula[i - 1], new Insets(4));
                                        gridPane.getChildren().addAll(lblNumAula[i - 1]);

                                } else {

                                        lblNumAula[i - 1] = new Label();
                                        lblNumAula[i - 1].setText(ConfigGradeController.arrayNumAulas[c]);
                                        lblNumAula[i - 1].setStyle("-fx-font-size: 8px;");
                                        lblNumAula[i - 1].setAlignment(Pos.CENTER);
                                        gridPane.setConstraints(lblNumAula[i - 1], 0, i);
                                        gridPane.setMargin(lblNumAula[i - 1], new Insets(4));
                                        gridPane.getChildren().addAll(lblNumAula[i - 1]);
                                        c++;

                                }
                        }
                }
        }

        public void addTransparentButton() {

                Rectangle[] blackrecs = new Rectangle[ConfigGradeController.arraySalas.length];

                for (int i = 1; i < numberLines; i++) {
                        for (int a = 1; a < ConfigGradeController.arraySalas.length; a++) {
                                if (i % (ConfigGradeController.numAulas + 1) == 0) {

                                        blackrecs[a] = new Rectangle();
                                        blackrecs[a].setHeight(20);
                                        blackrecs[a].setWidth(40);
                                        blackrecs[a].setFill(Color.GRAY);
                                        gridPane.setConstraints(blackrecs[a], a, i);

                                        gridPane.getChildren().addAll(blackrecs[a]);

                                } else {
                                        bodyButtons[i][a] = new Button();
                                        bodyButtons[i][a].setCursor(HAND);
                                        bodyButtons[i][a].getStyleClass().setAll("SmallButton");
                                        if (controlMatrix[i][a] != 0) {

                                                bodyButtons[i][a].setStyle(ConfigGradeController.arrayCoresProfs[controlMatrix[i][a]]);

                                        } else {
                                                bodyButtons[i][a].setStyle(ConfigGradeController.arrayCoresProfs[0]);
                                                controlEqualMatrix[i][a] = 0;
                                        }
                                        gridPane.setConstraints(bodyButtons[i][a], a, i);
                                        gridPane.setMargin(bodyButtons[i][a], new Insets(4));
                                        gridPane.getChildren().addAll(bodyButtons[i][a]);
                                }


                        }
                }

        }
// ---- Fim Tabela Principal ----

        // ---- Montar a tabela Professores ----
        public void addTeacherButton() {
                int c = 1;
                for (int i = 0; i < 1; i++) {
                        for (int a = 0; a < styleButton.length; a++) {
                                styleButton[a] = new Button();
                                styleButton[a].setCursor(HAND);
                                styleButton[a].getStyleClass().add("Button1px");
                                styleButton[a].setStyle(ConfigGradeController.arrayCoresProfs[c]);
                                styleButton[a].setId(String.valueOf(a + 1));
                                idModelos[a] = a + 1;
                                gridPaneModelo.setConstraints(styleButton[a], 0, a);
                                gridPane.setMargin(styleButton[a], new Insets(4));
                                gridPaneModelo.getChildren().add(styleButton[a]);

                                c++;
                        }
                }
        }

        public void addTeachersNames() {
                for (int i = 0; i < 1; i++) {
                        for (int a = 0; a < styleButton.length; a++) {
                                lblTeachers[a] = new Label();
                                lblTeachers[a].setText(ConfigGradeController.arrayProfs[a]);
                                gridPaneModelo.setConstraints(lblTeachers[a], 1, a);
                                gridPane.setMargin(lblTeachers[a], new Insets(4));
                                gridPaneModelo.getChildren().add(lblTeachers[a]);
                        }
                }
        }
// ---- Fim Tabela Professores ----

        public void setPatternStyle() {
                principalAnchor.getStylesheets().add(getClass().getResource("/css/button.css").toExternalForm());
                scroll.getStylesheets().add(getClass().getResource("/css/scrollpane.css").toExternalForm());
                scroll2.getStylesheets().add(getClass().getResource("/css/scrollpane.css").toExternalForm());
                gridPane.setGridLinesVisible(true);
                saveButton.setFocusTraversable(false);
                deleteButton.setFocusTraversable(false);
                printButton.setFocusTraversable(false);
                //save.getStyleClass().add("DeleteButton");
                //deleteButton.getStyleClass().add("DeleteButton");
                deleteButton.setId(String.valueOf(0));

        }

        // ---- Inicio JSON ----
        private void WriteJSONGridPane() {
                Gson gson = new Gson();
                FileWriter writeFile = null;
                try {
                        String jsonTabela = gson.toJson(controlMatrix);

                        writeFile = new FileWriter("backup " + date + "\\tabelaprincipal.json");
                        writeFile.write(
                                jsonTabela);
                        writeFile.close();
                } catch (IOException e) {
                        e.printStackTrace();
                }
        }

        private void writeJSONmodel() {
                Gson gson = new Gson();
                FileWriter writeFile = null;
                try {
                        String gsonModelos = gson.toJson(idModelos);
                        writeFile = new FileWriter("backup " + date + "\\idmodelo.json");
                        writeFile.write(gsonModelos);
                        writeFile.close();
                } catch (IOException e) {
                        e.printStackTrace();
                }
        }

        private void writeJSONcolors() {
                Gson gson = new Gson();
                FileWriter writeFile = null;
                String jsonCoes = gson.toJson(ConfigGradeController.arrayCoresProfs);
                try {
                        writeFile = new FileWriter("backup " + date + "\\estilos.json");
                        writeFile.write(jsonCoes);
                        writeFile.close();
                } catch (IOException e) {
                        e.printStackTrace();
                }
        }

        private void writeJSONnames() {
                Gson gson = new Gson();
                FileWriter writeFile = null;
                String jsonNomes = gson.toJson(ConfigGradeController.arrayProfs);
                try {
                        writeFile = new FileWriter("backup " + date + "\\nomes.json");
                        writeFile.write(jsonNomes);
                        writeFile.close();
                } catch (IOException e) {
                        e.printStackTrace();
                }
        }

        private void writeJSONclassrooms() {
                Gson gson = new Gson();
                FileWriter writeFile = null;
                String jsonsalas = gson.toJson(ConfigGradeController.arraySalas);
                try {
                        writeFile = new FileWriter("backup " + date + "\\salas.json");
                        writeFile.write(jsonsalas);
                        writeFile.close();
                } catch (IOException e) {
                        e.printStackTrace();
                }
        }

        private void writeJSONclasses() {
                Gson gson = new Gson();
                FileWriter writeFile = null;
                String jsonNumAula = gson.toJson(ConfigGradeController.arrayNumAulas);
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

        public void setSmallStyle() {

                size = "SmallButton";
                squareSize = "SquareSmallButton";
                numberFontSize = 10;
                for (int i = 1; i < numberLines; i++) {
                        for (int a = 1; a < ConfigGradeController.arraySalas.length; a++) {

                                if (String.valueOf(bodyButtons[i][a].getStyleClass()) == "SmallButton" || String.valueOf(bodyButtons[i][a].getStyleClass()) == "BigButton" || String.valueOf(bodyButtons[i][a].getStyleClass()) == "MediumButton") {
                                        bodyButtons[i][a].getStyleClass().setAll("SmallButton");
                                } else {
                                        bodyButtons[i][a].getStyleClass().setAll("SquareSmallButton");
                                }
                        }
                }
                for (int i = 0; i < 1; i++) {
                        for (int a = 0; a < styleButton.length; a++) {
                                styleButton[a].getStyleClass().clear();
                                styleButton[a].getStyleClass().add("SmallButton");
                        }
                }
                for (int i = 0; i < lblNumAula.length; i++) {

                        lblNumAula[i].setStyle("-fx-font-size: " + numberFontSize + "px;");

                }

        }

        public void setBigStyle() {
                size = "BigButton";
                squareSize = "SquareBigButton";
                numberFontSize = 14;
                for (int i = 1; i < numberLines; i++) {
                        for (int a = 1; a < ConfigGradeController.arraySalas.length; a++) {
                                if (String.valueOf(bodyButtons[i][a].getStyleClass()) == "SmallButton" || String.valueOf(bodyButtons[i][a].getStyleClass()) == "BigButton" || String.valueOf(bodyButtons[i][a].getStyleClass()) == "MediumButton") {
                                        bodyButtons[i][a].getStyleClass().setAll("BigButton");
                                } else {
                                        bodyButtons[i][a].getStyleClass().setAll("SquareBigButton");
                                }
                        }
                }
                for (int i = 0; i < 1; i++) {
                        for (int a = 0; a < styleButton.length; a++) {
                                styleButton[a].getStyleClass().clear();
                                styleButton[a].getStyleClass().add("BigButton");
                        }
                }
                for (int i = 0; i < lblNumAula.length; i++) {

                        lblNumAula[i].setStyle("-fx-font-size: " + numberFontSize + "px;");

                }
        }

        public void setMediumStyle() {
                size = "MediumButton";
                squareSize = "SquareMediumButton";
                numberFontSize = 12;
                for (int i = 1; i < numberLines; i++) {
                        for (int a = 1; a < ConfigGradeController.arraySalas.length; a++) {
                                if (String.valueOf(bodyButtons[i][a].getStyleClass()) == "SmallButton" || String.valueOf(bodyButtons[i][a].getStyleClass()) == "BigButton" || String.valueOf(bodyButtons[i][a].getStyleClass()) == "MediumButton") {
                                        bodyButtons[i][a].getStyleClass().setAll("MediumButton");
                                } else {
                                        bodyButtons[i][a].getStyleClass().setAll("SquareMediumButton");
                                }
                        }
                }
                for (int i = 0; i < 1; i++) {
                        for (int a = 0; a < styleButton.length; a++) {
                                styleButton[a].getStyleClass().setAll("MediumButton");
                        }
                }
                for (int i = 0; i < lblNumAula.length; i++) {

                        lblNumAula[i].setStyle("-fx-font-size: " + numberFontSize + "px;");

                }
        }

        // ---- Fim Menu Botoes ----
        private void createFile() {
                File directory = new File("backup " + date);
                directory.mkdir();
        }

        private void GerarPDF() {
                System.out.println(ConfigGradeController.arrayNumAulas.length);
                System.out.println(numberLines);
                int controlLines = 0;
                int limiteAula = (8);


                Document doc = new Document(PageSize.A4.rotate(), 0, 0, 20, 20);


                int controlWhiteCells = 0;
                PdfPCell[] cabecalho = new PdfPCell[ConfigGradeController.arraySalas.length];
                float[] widths = new float[ConfigGradeController.arraySalas.length];
                PdfPCell whiteCells;

                PdfPCell[][] corpo = new PdfPCell[numberLines][ConfigGradeController.arraySalas.length];
                int fontSize = 8;
                PdfPTable pdfPTable = new PdfPTable(ConfigGradeController.arraySalas.length);


                try {
                        PdfWriter.getInstance(doc, new FileOutputStream("Horario.pdf"));

                        doc.open();


                        for (int i = 0; i < ConfigGradeController.arraySalas.length; i++) {
                                if (i == 0) {
                                        cabecalho[i] = new PdfPCell(new Paragraph(" ", FontFactory.getFont(FontFactory.COURIER, fontSize)));
                                        cabecalho[i].setFixedHeight(12);
                                        pdfPTable.addCell(cabecalho[i]);
                                } else {
                                        cabecalho[i] = new PdfPCell(new Paragraph(ConfigGradeController.arraySalas[i], FontFactory.getFont(FontFactory.COURIER, fontSize)));
                                        cabecalho[i].setHorizontalAlignment(Element.ALIGN_CENTER);
                                        cabecalho[i].setFixedHeight(12);
                                        pdfPTable.addCell(cabecalho[i]);
                                }
                        }


                        for (int i = 1; i < numberLines; i++) {
                                if (i % (ConfigGradeController.numAulas + 1) != 0) {
                                        for (int j = 0; j < ConfigGradeController.arraySalas.length; j++) {
                                                if (j == 0) {
                                                        corpo[i][j] = new PdfPCell(new Paragraph(ConfigGradeController.arrayNumAulas[controlLines], FontFactory.getFont(FontFactory.COURIER, fontSize)));
                                                        corpo[i][j].setHorizontalAlignment(Element.ALIGN_CENTER);
                                                        corpo[i][j].setFixedHeight(12);
                                                        pdfPTable.addCell(corpo[i][j]);
                                                } else {
                                                        if (controlMatrix[i][j] != 0) {
                                                                corpo[i][j] = new PdfPCell(new Paragraph(ConfigGradeController.arrayProfs[controlMatrix[i][j] - 1], FontFactory.getFont(FontFactory.COURIER, fontSize)));
                                                                corpo[i][j].setHorizontalAlignment(Element.ALIGN_CENTER);
                                                                corpo[i][j].setFixedHeight(12);
                                                                pdfPTable.addCell(corpo[i][j]);
                                                        } else {
                                                                corpo[i][j] = new PdfPCell(new Paragraph(" "));
                                                                corpo[i][j].setHorizontalAlignment(Element.ALIGN_CENTER);
                                                                corpo[i][j].setFixedHeight(12);
                                                                pdfPTable.addCell(corpo[i][j]);
                                                        }
                                                }
                                        }
                                        controlLines++;
                                } else {
                                        for (int l = 0; l < ConfigGradeController.arraySalas.length; l++) {
                                                if (l == 0) {
                                                        cabecalho[l] = new PdfPCell(new Paragraph(" ", FontFactory.getFont(FontFactory.COURIER, fontSize)));
                                                        cabecalho[l].setFixedHeight(12);
                                                        pdfPTable.addCell(cabecalho[l]);
                                                } else {
                                                        cabecalho[l] = new PdfPCell(new Paragraph(ConfigGradeController.arraySalas[l], FontFactory.getFont(FontFactory.COURIER, fontSize)));
                                                        cabecalho[l].setHorizontalAlignment(Element.ALIGN_CENTER);
                                                        cabecalho[l].setFixedHeight(12);
                                                        pdfPTable.addCell(cabecalho[l]);
                                                }
                                        }
                                }

                        }


                        doc.add(pdfPTable);
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

        public void toastNotification() {
                int toastMsgTime = 1500;
                int fadeInTime = 500;
                int fadeOutTime = 500;
                toast.makeText(stage, "Salvo com sucesso!!", toastMsgTime, fadeInTime, fadeOutTime);

        }

        public void autoSave() {

                Timer timer = new Timer();
                TimerTask task = new TimerTask() {
                        boolean stageClosed;

                        @Override
                        public void run() {
                                principalAnchor.getScene().getWindow().setOnCloseRequest(event -> {
                                        stageClosed = true;


                                });
                                if (stageClosed) {
                                        timer.cancel();
                                }
                                createFile();
                                WriteJSONGridPane();
                                writeJSONmodel();
                                writeJSONcolors();
                                writeJSONnames();
                                writeJSONclassrooms();
                                writeJSONclasses();


                        }
                };
                timer.scheduleAtFixedRate(task, 2000, 1000);


        }
}














/*
package controller;

import com.google.gson.Gson;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javax.swing.*;
import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import static javafx.scene.Cursor.HAND;

public class GradeController implements Initializable {

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

    private Node botao, botao2;
    private int c = 1, control, a, b, id, idtroca1, idtroca2, idcontroleigual1, idcontroleigual2;
    private Button[] botoesmodelo = new Button[ConfigGradeController.qtdProfessores];
    private Button[][] botoes = new Button[ConfigGradeController.arrayNumAulas.length + 1][ConfigGradeController.arraySalas.length];
    private Label[] lblnumaula = new Label[ConfigGradeController.arrayNumAulas.length];
    private Label[] lblsala = new Label[ConfigGradeController.arraySalas.length];
    private Label[] lblprofessores = new Label[botoesmodelo.length];
    private int[][] matrizcontrole = new int[ConfigGradeController.arrayNumAulas.length + 1][ConfigGradeController.arraySalas.length];
    private int[][] matrizcontroleigual = new int[ConfigGradeController.arrayNumAulas.length + 1][ConfigGradeController.arraySalas.length];
    private int[] idmodelos= new int[botoesmodelo.length];
    private String estilo, tamanho = "botao", tamanhoquadrado = "botaoquadrado";
    String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    boolean ocupado = false;
    DropShadow dp = new DropShadow();
    private String nomeTabela;
    private String nomeDiretorio;
    private File diretorio;

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
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int se = jFileChooser.showSaveDialog(null);
            if(se == JFileChooser.APPROVE_OPTION){
                nomeTabela = jFileChooser.getName();
                nomeDiretorio = jFileChooser.getSelectedFile().getPath();
                nomeDiretorio = nomeDiretorio.replace('\\', '/');
            }
            CriarPasta();
            EscreverJSONTabela();
            EscreverJSONModelo();
            EscreverJSONCores();
            EscreverJSONNomes();
            EscreverJSONSalas();
            EscreverJSONQNTSalas();
        });
    }

    public void PegarEstiloProfessor(Node item) {
        for (Node botao: gridPaneModelo.getChildren()) {
            if(GridPane.getRowIndex(item).equals(GridPane.getRowIndex(botao))){
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
        if(String.valueOf(item.getClass()).equals("class javafx.scene.control.Button")) {
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
        for (int i = 1; i < ConfigGradeController.arrayNumAulas.length + 1; i++) {
            for (int a = 1; a < ConfigGradeController.arraySalas.length; a++) {
                matrizcontroleigual[i][a] = 0;
            }
        }
        for (int i = 1; i < ConfigGradeController.arrayNumAulas.length + 1; i++) {
            for (int a = 1; a < ConfigGradeController.arraySalas.length; a++) {
                for (int c = a + 1; c < ConfigGradeController.arraySalas.length; c++) {
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
        for (int i = 1; i < ConfigGradeController.arrayNumAulas.length + 1; i++) {
            for (int a = 1; a < ConfigGradeController.arraySalas.length; a++) {
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
        for (int a = 1; a < ConfigGradeController.arraySalas.length; a++) {
            lblsala[a] = new Label();
            lblsala[a].setText(ConfigGradeController.arraySalas[a]);
            lblsala[a].setMinWidth(30);
            lblsala[a].setAlignment(Pos.CENTER);
            gridPane.setConstraints(lblsala[a], a, 0);
            gridPane.setMargin(lblsala[a], new Insets(4));
            gridPane.getChildren().add(lblsala[a]);
        }
    }

    public void ColocarAulas(){
        for (int i = 1; i <= ConfigGradeController.arrayNumAulas.length; i++) {
            for (int a = 0; a < 1; a++) {
                lblnumaula[i - 1] = new Label();
                lblnumaula[i - 1].setText(ConfigGradeController.arrayNumAulas[i - 1]);
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
        for (int i = 1; i < ConfigGradeController.arrayNumAulas.length + 1; i++) {
            for (int a = 1; a < ConfigGradeController.arraySalas.length; a++) {
                botoes[i][a] = new Button();
                botoes[i][a].setCursor(HAND);
                botoes[i][a].getStyleClass().setAll("botao");
                botoes[i][a].setStyle(ConfigGradeController.arrayCoresProfs[0]);
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
                botoesmodelo[a].setStyle(ConfigGradeController.arrayCoresProfs[c]);
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
                lblprofessores[a].setText(ConfigGradeController.arrayProfs[a]);
                gridPaneModelo.setConstraints(lblprofessores[a], 1, a);
                gridPane.setMargin(lblprofessores[a], new Insets(4));
                gridPaneModelo.getChildren().add(lblprofessores[a]);
            }
        }
    }// ---- Fim Tabela Professores ----

    public void SetarEstiloPadrao(){
        anchorprincipal.getStylesheets().add(getClass().getResource("/css/button.css").toExternalForm());
        scroll.getStylesheets().add(getClass().getResource("/css/scrollpane.css").toExternalForm());
        scroll2.getStylesheets().add(getClass().getResource("/css/scrollpane.css").toExternalForm());
        gridPane.setGridLinesVisible(true);
        salvar.getStyleClass().add("botaoapagar");
        botaoApagar.getStyleClass().add("botaoapagar");
        botaoApagar.setId(String.valueOf(0));
    }// ---- Inicio JSON ----

    private void EscreverJSONTabela(){
        Gson gson = new Gson();
        FileWriter writeFile;
        try{
            String jsonTabela = gson.toJson(matrizcontrole);

            writeFile = new FileWriter( diretorio + "\\tabelaprincipal.json");
            writeFile.write(
                    jsonTabela);
            writeFile.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    private void EscreverJSONModelo(){
        Gson gson  = new Gson();
        FileWriter writeFile;
        try{
            String gsonModelos = gson.toJson(idmodelos);
            writeFile = new FileWriter(diretorio + "\\idmodelo.json");
            writeFile.write(gsonModelos);
            writeFile.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    private void EscreverJSONCores(){
        Gson gson = new Gson();
        FileWriter writeFile ;
        String jsonCoes = gson.toJson(ConfigGradeController.arrayCoresProfs);
        try{
            writeFile = new FileWriter(diretorio + "\\estilos.json");
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
        String jsonNomes = gson.toJson(ConfigGradeController.arrayProfs);
        try{
            writeFile = new FileWriter(diretorio + "\\nomes.json");
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
        String jsonsalas = gson.toJson(ConfigGradeController.arraySalas);
        try{
            writeFile = new FileWriter(diretorio + "\\salas.json");
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
        String jsonNumAula = gson.toJson(ConfigGradeController.arrayNumAulas);
        try {
            writeFile = new FileWriter(diretorio + "\\qntsalas.json");
            writeFile.write(
                    jsonNumAula);
            writeFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }// ---- Fim JSON ----

    // ---- Menu do Tamanho dos Botoes ----
    public void DeixarBotoesPequenos(){
        tamanho = "botao";
        tamanhoquadrado = "botaoquadrado";
        for (int i = 1; i < ConfigGradeController.arrayNumAulas.length + 1; i++) {
            for (int a = 1; a < ConfigGradeController.arraySalas.length; a++) {

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
        for (int i = 1; i < ConfigGradeController.arrayNumAulas.length + 1; i++) {
            for (int a = 1; a < ConfigGradeController.arraySalas.length; a++) {
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
        for (int i = 1; i < ConfigGradeController.arrayNumAulas.length + 1; i++) {
            for (int a = 1; a < ConfigGradeController.arraySalas.length; a++) {
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
        diretorio = new File(nomeDiretorio + date );
        diretorio.mkdir();
    }
}*/