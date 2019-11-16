package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectionFactory {
    public static final String DRIVER = "com.mysql.jdbc.Driver";
    public static final String URL = "jdbc:mysql://localhost:3306/HorarioEscolar?useTimezone=true&serverTimezone=UTC";
    public static final String USER = "root";
    public static final String PASS = "KD0L@TR4AKst!."; //KD0L@TR4AKst!.

    public static Connection getConnection(){
        try {
            Class.forName(DRIVER);
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException | SQLException ex) {
            throw new RuntimeException("Erro de Conexão: ", ex);
        }
    }

    public static void closeConnection(Connection con){
        if(con != null){
            try {
                con.close();
            } catch (SQLException ex) {
                System.err.println("Erro: " + ex);
            }
        }
    }

    public static void closeConnection(Connection con, PreparedStatement stmt){
        if(stmt != null){
            try {
                stmt.close();
            } catch (SQLException ex) {
                System.err.println("Erro: " + ex);
            }
            closeConnection(con);
        }
    }

    public static void closeConnection(Connection con, PreparedStatement stmt, ResultSet rs){
        if(stmt != null){
            try {
                rs.close();
            } catch (SQLException ex) {
                System.err.println("Erro: " + ex);
            }
            closeConnection(con, stmt);
        }
    }

}
