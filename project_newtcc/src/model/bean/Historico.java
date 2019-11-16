package model.bean;

public class Historico {
    private int id_evento;
    private String descricao_evento;
    private String tipo_evento;
    private String data_evento;
    private String horario_evento;

    public Historico(){
        this(0, "", "", "");
    }

    public Historico(int id_evento, String descricao_evento, String data_evento, String horario_evento){
        this.id_evento = id_evento;
        this.descricao_evento = descricao_evento;
        this.data_evento = data_evento;
        this.horario_evento = horario_evento;
    }

    public int getId_evento() {
        return this.id_evento;
    }
    public void setId_evento(int id_evento) {
        this.id_evento = id_evento;
    }
    public String getDescricao_evento() {
        return this.descricao_evento;
    }
    public void setDescricao_evento(String descricao_evento) {
        this.descricao_evento = descricao_evento;
    }
    public String getData_evento() {
        return this.data_evento;
    }
    public void setData_evento(String data_evento) {
        this.data_evento = data_evento;
    }
    public String getHorario_evento() {
        return this.horario_evento;
    }
    public void setHorario_evento(String horario_evento) {
        this.horario_evento = horario_evento;
    }
}
