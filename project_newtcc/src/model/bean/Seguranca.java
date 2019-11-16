package model.bean;

public class Seguranca {
    private String nova_senha;

    public Seguranca(){
        this("");
    }

    public Seguranca(String nova_senha) {
        this.nova_senha = nova_senha;
    }

    public String getNova_senha() {
        return this.nova_senha;
    }
    public void setNova_senha(String nova_senha) {
        this.nova_senha = nova_senha;
    }
}
