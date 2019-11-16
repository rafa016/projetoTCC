package model.dao;

import connection.ConnectionFactory;
import model.bean.Historico;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HistoricoDAO {
    private Connection con;
    Date data = new Date();
    String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

    public HistoricoDAO(){
        con = ConnectionFactory.getConnection();
    }

    public List<Historico> getList(String evento){
        List<Historico> historico = new ArrayList<>();
        String sql = "SELECT descricao_log, data_log, horario_log FROM Historico WHERE descricao_log LIKE '%" + evento + "%' OR data_log LIKE '%" + evento + "%' OR horario_log LIKE '%" + evento + "%' ORDER BY id_log DESC;";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Historico h = new Historico();
                h.setDescricao_evento(rs.getString("descricao_log"));
                h.setData_evento(rs.getString("data_log"));
                h.setHorario_evento(rs.getString("horario_log"));
                historico.add(h);
            }
            stmt.close();
            rs.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Erro, Lista não foi Retornada");
            return null;
        }
        return historico;
    }

    public void historicoCriarTabela(){
        String sqlLog = "INSERT INTO Historico(descricao_log, data_log, horario_log) VALUES (?, ?, ?);";
        PreparedStatement stmtLog;
        try {
            stmtLog = con.prepareStatement(sqlLog);
            stmtLog.setString(1, "  O Usuário Criou uma nova Tabela");
            stmtLog.setDate(2, java.sql.Date.valueOf(date));
            stmtLog.setString(3, String.valueOf(data).substring(11,20));
            stmtLog.executeUpdate();
        } catch (SQLException ex) {}
    }

    public void historicoAbrirTabela(String nomeTabela){
        String sqlLog = "INSERT INTO Historico(descricao_log, data_log, horario_log) VALUES (?, ?, ?);";
        PreparedStatement stmtLog;
        try {
            stmtLog = con.prepareStatement(sqlLog);
            stmtLog.setString(1, "  O Usuário Abriu uma Tabela. Nome da Tabela: " + nomeTabela);
            stmtLog.setDate(2, java.sql.Date.valueOf(date));
            stmtLog.setString(3, String.valueOf(data).substring(11,20));
            stmtLog.executeUpdate();
        } catch (SQLException ex) {}
    }
}
