package model.bean;

public class Usuario {
    private int id_usua;
    private String senha_usua;

    public Usuario(){
        this(0, "");
    }

    public Usuario(int id_usua, String senha_usua) {
        this.id_usua = id_usua;
        this.senha_usua = senha_usua;
    }

    public int getId_usua() {
        return this.id_usua;
    }
    public void setId_usua(int id_usua) {
        this.id_usua = id_usua;
    }
    public String getSenha_usua() {
        return this.senha_usua;
    }
    public void setSenha_usua(String senha_usua) {
        this.senha_usua = senha_usua;
    }
}
