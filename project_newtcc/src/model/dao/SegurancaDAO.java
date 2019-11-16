package model.dao;

import connection.ConnectionFactory;
import model.bean.Seguranca;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SegurancaDAO {

    private Connection con;

    public SegurancaDAO(){
        con = ConnectionFactory.getConnection();
    }

    public boolean Update(Seguranca seguranca){
        String sql = "UPDATE Usuario SET senha_usua = ? WHERE id_usua = 1;";
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement(sql);
            stmt.setString(1, seguranca.getNova_senha());
            stmt.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.err.println("Erro: " + ex);
            return false;
        }finally{
            ConnectionFactory.closeConnection(con, stmt);
        }
    }


}