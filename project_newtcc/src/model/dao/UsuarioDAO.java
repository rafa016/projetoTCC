package model.dao;

import connection.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UsuarioDAO {
    private Connection con;
    Date data = new Date();
    String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

    public UsuarioDAO(){
        con = ConnectionFactory.getConnection();
    }

    public boolean checkLogin(String  senha_usua){
        String sqlLog = "INSERT INTO Historico(descricao_log, data_log, horario_log) VALUES (?, ?, ?);";
        PreparedStatement stmtLog;

        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean check = false;

        try{
            stmt = con.prepareStatement("SELECT * FROM Usuario WHERE senha_usua = ?");
            stmt.setString(1, senha_usua);
            rs = stmt.executeQuery();

            if (rs.next()){
                check = true;
                stmtLog = con.prepareStatement(sqlLog);
                stmtLog.setString(1, "  O Usuário acessou o Sistema.");
                stmtLog.setDate(2, java.sql.Date.valueOf(date));
                stmtLog.setString(3, String.valueOf(data).substring(11,20));
                stmtLog.executeUpdate();
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return check;
    }

    public void sairSistema(){
        String sqlLog = "INSERT INTO Historico(descricao_log, data_log, horario_log) VALUES (?, ?, ?);";
        PreparedStatement stmtLog;
        try {
            stmtLog = con.prepareStatement(sqlLog);
            stmtLog.setString(1, "  O Usuário deixou o Sistema");
            stmtLog.setDate(2, java.sql.Date.valueOf(date));
            stmtLog.setString(3, String.valueOf(data).substring(11,20));
            stmtLog.executeUpdate();
        } catch (SQLException ex) {}
    }

}
