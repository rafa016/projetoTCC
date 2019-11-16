package model.dao;

import connection.ConnectionFactory;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import model.bean.Professores;
import model.bean.Salas;

import javax.swing.*;

public class SalasDAO {

    private Connection con;
    Date data = new Date();
    String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

    public SalasDAO(){
        con = ConnectionFactory.getConnection();
    }

    public int RetornaIdSala(){
        String sql = "SELECT MAX(id_sala) FROM Salas;";
        Salas sala = new Salas();
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                sala.setId_sala(rs.getInt("MAX(id_sala)"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sala.getId_sala();
    }

    public boolean Create(Salas salas){
        int idSala = RetornaIdSala() + 1;
        String sql = "INSERT INTO Salas(nome_sala, tecnico) VALUES (?, ?);";
        String sqlLog = "INSERT INTO Historico(descricao_log, data_log, horario_log) VALUES (?, ?, ?);";
        PreparedStatement stmt = null;
        PreparedStatement stmtLog;
        try {
            stmt = con.prepareStatement(sql);
            stmt.setString(1, salas.getNome_sala());
            stmt.setString(2, salas.getTecnico());
            stmt.executeUpdate();

            stmtLog = con.prepareStatement(sqlLog);
            stmtLog.setString(1, "  O Usuário Cadastrou uma nova Sala. ID: " + idSala + ", Nome da Sala: " + salas.getNome_sala());
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

    public List<Salas> getList(int id_prof, String sala){
        List<Salas> salas = new ArrayList<>();
        String sql = "SELECT * FROM Salas WHERE nome_sala LIKE '%" + sala + "%'";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            ProfessoresDAO profDAO = new ProfessoresDAO();
            while(rs.next()){
                Salas s = new Salas();
                s.setId_sala(rs.getInt("id_sala"));
                s.setNome_sala(rs.getString("nome_sala"));
                s.setTecnico(rs.getString("tecnico"));

                ResultSet rss = profDAO.RetornaSalasSalvas(id_prof);

                while(rss.next()){
                    s.setFk_id_sala(rss.getInt("fk_id_sala"));
                    if(s.getFk_id_sala() == s.getId_sala()){
                        s.getSelect_sala().setSelected(true);
                    }
                }
                salas.add(s);
            }
            stmt.close();
            rs.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Erro, Lista não foi Retornada");
            return null;
        }
        return salas;
    }

    public boolean Update(Salas salas){
        String sql = "UPDATE Salas SET nome_sala = ?, tecnico = ? WHERE id_sala = ?";
        String sqlLog = "INSERT INTO Historico(descricao_log, data_log, horario_log) VALUES (?, ?, ?);";
        PreparedStatement stmt = null;
        PreparedStatement stmtLog;
        try {
            stmt = con.prepareStatement(sql);
            stmt.setString(1, salas.getNome_sala());
            stmt.setString(2, salas.getTecnico());
            stmt.setInt(3, salas.getId_sala());
            stmt.executeUpdate();

            stmtLog = con.prepareStatement(sqlLog);
            stmtLog.setString(1, "  O Usuário Alterou uma Sala existente. ID: " + salas.getId_sala() + ", Nome da Sala: " + salas.getNome_sala());
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

    public boolean Delete(Salas salas){
        String sql = "DELETE FROM Salas WHERE id_sala = ?";
        String sqlLog = "INSERT INTO Historico(descricao_log, data_log, horario_log) VALUES (?, ?, ?);";
        PreparedStatement stmt = null;
        PreparedStatement stmtLog;
        try {
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, salas.getId_sala());
            stmt.executeUpdate();

            stmtLog = con.prepareStatement(sqlLog);
            stmtLog.setString(1, "  O Usuário Excluiu uma Sala. ID: " + salas.getId_sala() + ", Nome da Sala: " + salas.getNome_sala());
            stmtLog.setDate(2, java.sql.Date.valueOf(date));
            stmtLog.setString(3, String.valueOf(data).substring(11,20));
            stmtLog.executeUpdate();
            return true;
        } catch (SQLException ex) {
            return false;
        }finally{
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public boolean confirmarSala(String nomeSala){
        String sql = "SELECT nome_sala FROM Salas WHERE nome_sala = '" + nomeSala + "'";
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
