package model.bean;

import javafx.scene.control.CheckBox;

public class Materias {
    private int id_materia;
    private int fk_id_materia;
    private String nome_materia;
    private String abrev_nome;
    private CheckBox select_mat;

    public Materias(){
        this(0, 0, "", "", null);
    }

    public Materias(int id_materia, int fk_id_materia, String nome_materia, String abrev_nome, CheckBox select_mat) {
        this.id_materia = id_materia;
        this.fk_id_materia = fk_id_materia;
        this.nome_materia = nome_materia;
        this.abrev_nome = abrev_nome;
        this.select_mat = new CheckBox();
    }

    public int getId_materia() {
        return this.id_materia;
    }
    public void setId_materia(int id_materia) {
        this.id_materia = id_materia;
    }
    public int getFk_id_materia() {
        return this.fk_id_materia;
    }
    public void setFk_id_materia(int fk_id_materia) {
        this.fk_id_materia = fk_id_materia;
    }
    public String getNome_materia() {
        return this.nome_materia;
    }
    public void setNome_materia(String nome_materia) {
        this.nome_materia = nome_materia;
    }
    public String getAbrev_nome() {
        return this.abrev_nome;
    }
    public void setAbrev_nome(String abrev_nome) {
        this.abrev_nome = abrev_nome;
    }
    public CheckBox getSelect_mat() {
        return this.select_mat;
    }
    public void setSelect_mat(CheckBox select_mat) {
        this.select_mat = select_mat;
    }
}