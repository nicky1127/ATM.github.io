package Bank_Test;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hung_yilai
 */
import DB_Test.*;
import java.sql.Connection;
import java.sql.SQLException;

public interface Z_DBSource {
    public Connection getConnection() throws SQLException;
    public void closeConnection(Connection conn) throws SQLException;
}
