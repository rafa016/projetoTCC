package model.dao;

import connection.ConnectionFactory;
import javafx.scene.control.Button;
import model.bean.Materias;
import model.bean.Professores;
import model.bean.Salas;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProfessoresDAO {

    private Connection con;
    Date data = new Date();
    String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

    public ProfessoresDAO(){ con = ConnectionFactory.getConnection(); }

    public boolean Create(Professores professores){
        int idProfessor = RetornaId() + 1;
        String sql = "INSERT INTO Professores(nome_prof, qtd_aulas_prof, id_cor_primaria, id_cor_secundaria) VALUES (?, ?, ?, ?);";
        String sqlLog = "INSERT INTO Historico(descricao_log, data_log, horario_log) VALUES (?, ?, ?);";
        PreparedStatement stmt = null;
        PreparedStatement stmtLog;
        try {
            stmt = con.prepareStatement(sql);
            stmt.setString(1, professores.getNome_prof());
            stmt.setInt(2, professores.getQtd_aulas_prof());
            stmt.setString(3, String.valueOf(professores.getCor_primaria()));
            stmt.setString(4, String.valueOf(professores.getCor_secundaria()));
            stmt.executeUpdate();

            stmtLog = con.prepareStatement(sqlLog);
            stmtLog.setString(1, "  O Usuário Cadastrou um(a) novo(a) professor(a). ID: " + idProfessor + ", Nome do Professor(a): " + professores.getNome_prof());
            stmtLog.setDate(2, java.sql.Date.valueOf(date));
            stmtLog.setString(3, String.valueOf(data).substring(11,20));
            stmtLog.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.err.println("Erro: " + ex);
            return false;
        }
    }

    public boolean CadastraMaterias(Professores professores, Materias materias){
        String sql = "INSERT INTO Prof_Materias(fk_id_prof, fk_id_materia) VALUES (?,?);";
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, professores.getId_prof());
            stmt.setInt(2, materias.getId_materia());
            stmt.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.err.println("Erro: " + ex);
            return false;
        }
    }

    public boolean CadastraSalas(Professores professores, Salas salas){
        String sql = "INSERT INTO Prof_Salas(fk_id_profe, fk_id_sala) VALUES (?,?);";
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, professores.getId_prof());
            stmt.setInt(2, salas.getId_sala());
            stmt.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.err.println("Erro: " + ex);
            return false;
        }
    }

    public int RetornaId(){
        String sql = "SELECT MAX(id_prof) FROM Professores;";
        Professores p = new Professores();
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                p.setId_prof(rs.getInt("MAX(id_prof)"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return p.getId_prof();
    }

    public List<Professores> getList(String nome){
        List<Professores> professores = new ArrayList<>();
        String sql = "SELECT * FROM Professores WHERE nome_prof LIKE '%" + nome + "%'";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Professores p = new Professores();
                p.setId_prof(rs.getInt("id_prof"));

                p.setCor_primaria(rs.getString("id_cor_primaria"));
                p.setCor_secundaria(rs.getString("id_cor_secundaria"));

                String style_prim = "-fx-background-color: " + p.getCor_primaria() + "; -fx-border-color: " + p.getCor_primaria() + ";";
                String style_secun = "-fx-background-color: " + p.getCor_secundaria() + "; -fx-border-color: " + p.getCor_secundaria() + ";";

                p.setBtn_cor_primaria(new Button());
                p.getBtn_cor_primaria().setMinSize(55,7);
                p.getBtn_cor_primaria().setPrefSize(55, 20);
                p.getBtn_cor_primaria().setStyle(style_prim);

                p.setBtn_cor_secundaria(new Button());
                p.getBtn_cor_secundaria().setMinSize(55,7);
                p.getBtn_cor_secundaria().setPrefSize(55, 20);
                p.getBtn_cor_secundaria().setStyle(style_secun);

                RetornaSalas(p.getId_prof());
                String salas_prof = RetornaSalas(p.getId_prof());

                RetornaMaterias(p.getId_prof());
                String materias_prof = RetornaMaterias(p.getId_prof());

                p.setNome_prof(rs.getString("nome_prof"));
                p.setQtd_aulas_prof(rs.getInt("qtd_aulas_prof"));
                p.setSalas_prof(salas_prof);
                p.setMaterias_prof(materias_prof);
                professores.add(p);
            }
            stmt.close();
            rs.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Erro, Lista não foi Retornada");
            return null;
        }
        return professores;
    }

    public String RetornaSalas(int id_prof){
        String sql = "SELECT Salas.nome_sala FROM Salas, Prof_Salas WHERE fk_id_profe = " + id_prof + " AND fk_id_sala = id_sala";
        Professores p = new Professores();
        int num = 0;
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                if(num == 0){
                    p.setSalas_prof(p.getSalas_prof() + rs.getString("Salas.nome_sala"));
                    num++;
                }else{
                    p.setSalas_prof(p.getSalas_prof() + "/" + rs.getString("Salas.nome_sala"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return p.getSalas_prof();
    }

    public String RetornaMaterias(int id_prof){
        String sql = "SELECT Materias.abrev_nome_materia FROM Materias, Prof_Materias WHERE fk_id_prof = " + id_prof + " AND fk_id_materia = id_materia";
        Professores p = new Professores();
        int num = 0;
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                if(num == 0){
                    p.setMaterias_prof(p.getMaterias_prof() + rs.getString("Materias.abrev_nome_materia"));
                    num++;
                }else{
                    p.setMaterias_prof(p.getMaterias_prof() + "/" + rs.getString("Materias.abrev_nome_materia"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return p.getMaterias_prof();
    }

    public boolean Update(Professores professores){
        String sql = "UPDATE Professores SET nome_prof = ?, qtd_aulas_prof = ?, id_cor_primaria = ?, id_cor_secundaria = ? WHERE id_prof = ?";
        String sqlLog = "INSERT INTO Historico(descricao_log, data_log, horario_log) VALUES (?, ?, ?);";
        PreparedStatement stmt;
        PreparedStatement stmtLog;
        try {
            stmt = con.prepareStatement(sql);
            stmt.setString(1, professores.getNome_prof());
            stmt.setInt(2, professores.getQtd_aulas_prof());
            stmt.setString(3, String.valueOf(professores.getCor_primaria()));
            stmt.setString(4, String.valueOf(professores.getCor_secundaria()));
            stmt.setInt(5, professores.getId_prof());
            stmt.executeUpdate();

            stmtLog = con.prepareStatement(sqlLog);
            stmtLog.setString(1, "  O Usuário Alterou um(a) Professor(a) existente. ID: " + professores.getId_prof() + ", Nome do(a) Professor(a): " + professores.getNome_prof());
            stmtLog.setDate(2, java.sql.Date.valueOf(date));
            stmtLog.setString(3, String.valueOf(data).substring(11,20));
            stmtLog.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.err.println("Erro: " + ex);
            return false;
        }
    }

    public ResultSet RetornaMateriasSalvas(int id_prof){
        String sql = "SELECT fk_id_materia FROM Prof_Materias WHERE fk_id_prof = " + id_prof;
        ResultSet rss = null;
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            rss = stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rss;
    }

    public ResultSet RetornaSalasSalvas(int id_prof){
        String sql = "SELECT fk_id_sala FROM Prof_Salas WHERE fk_id_profe = " + id_prof;
        ResultSet rss = null;
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            rss = stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rss;
    }

    public boolean Delete(Professores professores){
        String sql1 = "DELETE FROM Prof_Materias WHERE fk_id_prof = ?";
        String sql2 = "DELETE FROM Prof_Salas WHERE fk_id_profe = ?";
        String sql3 = "DELETE FROM Professores WHERE id_prof = ?";
        String sqlLog = "INSERT INTO Historico(descricao_log, data_log, horario_log) VALUES (?, ?, ?);";
        PreparedStatement stmt = null;
        PreparedStatement stmtLog;
        try {
            stmt = con.prepareStatement(sql1);
            stmt.setInt(1, professores.getId_prof());
            stmt.executeUpdate();
            stmt = con.prepareStatement(sql2);
            stmt.setInt(1, professores.getId_prof());
            stmt.executeUpdate();
            stmt = con.prepareStatement(sql3);
            stmt.setInt(1, professores.getId_prof());
            stmt.executeUpdate();

            stmtLog = con.prepareStatement(sqlLog);
            stmtLog.setString(1, "  O Usuário Excluiu um(a) Professor(a). ID: " + professores.getId_prof() + ", Nome da Sala: " + professores.getNome_prof());
            stmtLog.setDate(2, java.sql.Date.valueOf(date));
            stmtLog.setString(3, String.valueOf(data).substring(11,20));
            stmtLog.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.err.println("Erro: " + ex);
            return false;
        }finally{
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public boolean DeleteSalasMaterias(Professores professores){
        String sql1 = "DELETE FROM Prof_Materias WHERE fk_id_prof = ?";
        String sql2 = "DELETE FROM Prof_Salas WHERE fk_id_profe = ?";
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement(sql1);
            stmt.setInt(1, professores.getId_prof());
            stmt.executeUpdate();
            stmt = con.prepareStatement(sql2);
            stmt.setInt(1, professores.getId_prof());
            stmt.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.err.println("Erro: " + ex);
            return false;
        }
    }

    public int RetornaQuantProfes(){
        Professores p = new Professores();
        String sql = "SELECT COUNT(*) FROM Professores;";
        PreparedStatement stmt;
        try {
            stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                p.setQuant_prof(rs.getInt("COUNT(*)"));
            }
        } catch (SQLException ex) {
            System.err.println("Erro: " + ex);
        }
        return p.getQuant_prof();
    }

    public String[] RetonaNomesProfes(){
        Professores p = new Professores();
        String sql = "SELECT nome_prof FROM Professores;";
        PreparedStatement stmt = null;
        int num = 0;

        try {
            stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                num++;
            }
        } catch (SQLException ex) {
            System.err.println("Erro: " + ex);
        }

        String arrayProfes[] = new String[num];
        num = 0;
        try {
            stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                p.setNome_prof(rs.getString("nome_prof"));
                arrayProfes[num] = p.getNome_prof();
                num++;
            }
        } catch (SQLException ex) {
            System.err.println("Erro: " + ex);
        }
        return arrayProfes;
    }

    public String[] RetonaCoresProfes(){
        Professores p = new Professores();
        String sql = "SELECT id_cor_primaria, id_cor_secundaria FROM Professores";
        PreparedStatement stmt = null;
        int num = 0;

        try {
            stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                num++;
            }
            num = num + 2;
        } catch (SQLException ex) {
            System.err.println("Erro: " + ex);
        }
        String arrayCoresProfes[] = new String[num];
        arrayCoresProfes[0] = "-fx-background-color: #FFFFFF; -fx-border-color: #FFFFFF;";
        num = 1;
        try {
            stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                p.setCor_primaria(rs.getString("id_cor_primaria"));
                p.setCor_secundaria(rs.getString("id_cor_secundaria"));
                arrayCoresProfes[num] = "-fx-background-color: " + p.getCor_primaria() + "; -fx-border-color: " + p.getCor_secundaria() + ";";
                num++;
            }
        } catch (SQLException ex) {
            System.err.println("Erro: " + ex);
        }
        arrayCoresProfes[num] = "-fx-background-color: #FF0000; -fx-border-color: #FF0000;";
        return arrayCoresProfes;
    }

    public boolean confirmarNomeProf(String nomeProf){
        String sql = "SELECT nome_prof FROM Professores WHERE nome_prof = '" + nomeProf + "'";
        PreparedStatement stmt;
        Boolean confirm = false;
        try {
            stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                confirm = true;
            }
        } catch (SQLException ex) {
            System.err.println("Erro: " + ex);
        }
        return confirm;
    }

    public boolean confirmarCorPrim(String corPrima){
        corPrima = "#" + String.valueOf(corPrima).substring(2, 8);
        String sql = "SELECT id_cor_primaria FROM Professores WHERE id_cor_primaria = '" + corPrima + "'";
        PreparedStatement stmt;
        Boolean confirm = false;
        try {
            stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                confirm = true;
            }
        } catch (SQLException ex) {
            System.err.println("Erro: " + ex);
        }
        return confirm;
    }

    public boolean confirmarCorSec(String corSecun){
        corSecun = "#" + String.valueOf(corSecun).substring(2, 8);
        String sql = "SELECT id_cor_secundaria FROM Professores WHERE id_cor_secundaria = '" + corSecun + "'";
        PreparedStatement stmt;
        Boolean confirm = false;
        try {
            stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                confirm = true;
            }
        } catch (SQLException ex) {
            System.err.println("Erro: " + ex);
        }
        return confirm;
    }

}
