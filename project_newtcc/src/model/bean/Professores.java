package model.bean;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Professores {
    private int id_prof;
    private int quant_prof;
    private int qtd_aulas_prof;
    private String nome_prof;
    private String salas_prof;
    private String materias_prof;
    private String cor_primaria;
    private String cor_secundaria;
    private Button btn_cor_primaria;
    private Button btn_cor_secundaria;

    public Professores(){
        this(0, 0, 0, "", "", "", "", "", null, null);
    }

    public Professores(int id_prof, int quant_prof, int qtd_aulas_prof, String nome_prof, String salas_prof, String materias_prof, String cor_primaria, String cor_secundaria, Button btn_cor_primaria, Button btn_cor_secundaria) {
        this.id_prof = id_prof;
        this.quant_prof = quant_prof;
        this.qtd_aulas_prof = qtd_aulas_prof;
        this.nome_prof = nome_prof;
        this.salas_prof = salas_prof;
        this.materias_prof = materias_prof;
        this.cor_primaria = cor_primaria;
        this.cor_secundaria = cor_secundaria;
        this.btn_cor_primaria = btn_cor_primaria;
        this.btn_cor_secundaria = btn_cor_secundaria;
    }

    public int getId_prof() {
        return this.id_prof;
    }
    public void setId_prof(int id_prof) {
        this.id_prof = id_prof;
    }
    public int getQuant_prof() {
        return this.quant_prof;
    }
    public void setQuant_prof(int quant_prof) {
        this.quant_prof = quant_prof;
    }
    public int getQtd_aulas_prof() {
        return this.qtd_aulas_prof;
    }
    public void setQtd_aulas_prof(int qtd_aulas_prof) {
        this.qtd_aulas_prof = qtd_aulas_prof;
    }
    public String getNome_prof() {
        return this.nome_prof;
    }
    public void setNome_prof(String nome_prof) {
        this.nome_prof = nome_prof;
    }
    public String getSalas_prof() {
        return this.salas_prof;
    }
    public void setSalas_prof(String salas_prof) {
        this.salas_prof = salas_prof;
    }
    public String getMaterias_prof() {
        return this.materias_prof;
    }
    public void setMaterias_prof(String materias_prof) {
        this.materias_prof = materias_prof;
    }
    public String getCor_primaria() {
        return this.cor_primaria;
    }
    public void setCor_primaria(String cor_primaria) {
        this.cor_primaria = cor_primaria;
    }
    public String getCor_secundaria() {
        return this.cor_secundaria;
    }
    public void setCor_secundaria(String cor_secundaria) {
        this.cor_secundaria = cor_secundaria;
    }
    public Button getBtn_cor_primaria() {
        return this.btn_cor_primaria;
    }
    public void setBtn_cor_primaria(Button btn_cor_primaria) {
        this.btn_cor_primaria = btn_cor_primaria;
    }
    public Button getBtn_cor_secundaria() {
        return this.btn_cor_secundaria;
    }
    public void setBtn_cor_secundaria(Button btn_cor_secundaria) {
        this.btn_cor_secundaria = btn_cor_secundaria;
    }
}
