package model.dao;

import com.sun.org.apache.bcel.internal.generic.Select;
import connection.ConnectionFactory;
import controller.ProfessoresController;
import javafx.scene.control.CheckBox;
import model.bean.Materias;
import model.bean.Professores;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MateriasDAO {

    private Connection con;
    Date data = new Date();
    String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

    public MateriasDAO(){
        con = ConnectionFactory.getConnection();
    }

    public int RetornaIdMateria(){
        String sql = "SELECT MAX(id_materia) FROM Materias;";
        Materias materias = new Materias();
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                materias.setId_materia(rs.getInt("MAX(id_materia)"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return materias.getId_materia();
    }

    public boolean Create(Materias materias){
        int idMateria = RetornaIdMateria() + 1;
        String sql = "INSERT INTO Materias(nome_materia, abrev_nome_materia) VALUES (?, ?);";
        String sqlLog = "INSERT INTO Historico(descricao_log, data_log, horario_log) VALUES (?, ?, ?);";
        PreparedStatement stmt = null;
        PreparedStatement stmtLog = null;
        try {
            stmt = con.prepareStatement(sql);
            stmt.setString(1, materias.getNome_materia());
            stmt.setString(2, materias.getAbrev_nome());
            stmt.executeUpdate();

            stmtLog = con.prepareStatement(sqlLog);
            stmtLog.setString(1, "  O Usuário Cadastrou uma nova Matéria. ID: " + idMateria + ", Nome da Matéria: " + materias.getNome_materia());
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

    public List<Materias> getList(int id_prof, String materia){
        List<Materias> materias = new ArrayList<>();
        String sql = "SELECT * FROM Materias WHERE nome_materia LIKE '%" + materia + "%' OR abrev_nome_materia LIKE '%" + materia + "%'";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            ProfessoresDAO profDAO = new ProfessoresDAO();
            while(rs.next()){
                Materias m = new Materias();
                m.setId_materia(rs.getInt("id_materia"));
                m.setNome_materia(rs.getString("nome_materia"));
                m.setAbrev_nome(rs.getString("abrev_nome_materia"));

                ResultSet rss = profDAO.RetornaMateriasSalvas(id_prof);

                while(rss.next()){
                    m.setFk_id_materia(rss.getInt("fk_id_materia"));
                    if(m.getFk_id_materia() == m.getId_materia()){
                        m.getSelect_mat().setSelected(true);
                    }
                }
                materias.add(m);
            }
            stmt.close();
            rs.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Erro, Lista não foi Retornada");
            return null;
        }
        return materias;
    }

    public boolean Update(Materias materias){
        String sql = "UPDATE Materias SET nome_materia = ?, abrev_nome_materia = ? WHERE id_materia = ?";
        String sqlLog = "INSERT INTO Historico(descricao_log, data_log, horario_log) VALUES (?, ?, ?);";
        PreparedStatement stmt = null;
        PreparedStatement stmtLog;
        try {
            stmt = con.prepareStatement(sql);
            stmt.setString(1, materias.getNome_materia());
            stmt.setString(2, materias.getAbrev_nome());
            stmt.setInt(3, materias.getId_materia());
            stmt.executeUpdate();

            stmtLog = con.prepareStatement(sqlLog);
            stmtLog.setString(1, "  O Usuário Alterou uma Matéria existente. ID: " + materias.getId_materia() + ", Nome da Sala: " + materias.getNome_materia());
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

    public boolean Delete(Materias materias){
        String sql = "DELETE FROM Materias WHERE id_materia = ?";
        String sqlLog = "INSERT INTO Historico(descricao_log, data_log, horario_log) VALUES (?, ?, ?);";
        PreparedStatement stmt = null;
        PreparedStatement stmtLog;
        try {
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, materias.getId_materia());
            stmt.executeUpdate();

            stmtLog = con.prepareStatement(sqlLog);
            stmtLog.setString(1, "  O Usuário Excluiu uma Matéria. ID: " + materias.getId_materia() + ", Nome da Sala: " + materias.getNome_materia());
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

    public boolean confirmarNomeMateria(String nomeMateria){
        String sql = "SELECT nome_materia FROM Materias WHERE nome_materia = '" + nomeMateria + "'";
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

    public boolean confirmarAbrevMateria(String abrevMateria){
        String sql = "SELECT abrev_nome_materia FROM Materias WHERE abrev_nome_materia = '" + abrevMateria + "'";
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
