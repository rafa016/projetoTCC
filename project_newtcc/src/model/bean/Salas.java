package model.bean;

import javafx.scene.control.CheckBox;

public class Salas {
    private int id_sala;
    private int fk_id_sala;
    private String nome_sala;
    private String tecnico;
    private CheckBox select_sala;

    public Salas(){
        this(0, 0, "", "", null);
    }

    public Salas(int id_sala, int fk_id_sala, String nome_sala, String tecnico, CheckBox select_sala) {
        this.id_sala = id_sala;
        this.fk_id_sala = fk_id_sala;
        this.nome_sala = nome_sala;
        this.tecnico = tecnico;
        this.select_sala = new CheckBox();
    }

    public int getId_sala() {
        return this.id_sala;
    }
    public void setId_sala(int id_sala) {
        this.id_sala = id_sala;
    }
    public int getFk_id_sala() {
        return this.fk_id_sala;
    }
    public void setFk_id_sala(int fk_id_sala) {
        this.fk_id_sala = fk_id_sala;
    }
    public String getNome_sala() {
        return this.nome_sala;
    }
    public void setNome_sala(String nome_sala) {
        this.nome_sala = nome_sala;
    }
    public String getTecnico() {
        return this.tecnico;
    }
    public void setTecnico(String tecnico) {
        this.tecnico = tecnico;
    }
    public CheckBox getSelect_sala() {
        return this.select_sala;
    }
    public void setSelect_sala(CheckBox select_sala) {
        this.select_sala = select_sala;
    }
}
