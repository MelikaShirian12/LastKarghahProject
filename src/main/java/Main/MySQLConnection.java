package Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MySQLConnection {

    String URL = "jdbc:mysql://localhost/pro_k";
    String username = "root";
    String password = "Kim@Arj_578";

    static MySQLConnection mySQLConnection = new MySQLConnection();

    public Boolean ExecuteSQL(String SqlCmd) {

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection Con = DriverManager.getConnection(URL, username, password);

            Statement s = Con.prepareStatement(SqlCmd);
            s.execute(SqlCmd);

            Con.close();
            return true;
        } catch (Exception ex) {

            return false;
        }
    }

    public ResultSet ExecuteQuery(String sql) {

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection Con = DriverManager.getConnection(URL, username, password);

            Statement s = Con.prepareStatement(sql);
            ResultSet rs = s.executeQuery(sql);
            return rs;
        } catch (Exception ex) {

            return null;
        }
    }

}
