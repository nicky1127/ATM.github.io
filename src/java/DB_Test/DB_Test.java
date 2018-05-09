package DB_Test;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author hung_yilai
 */
public class DB_Test {
    
    public static void main(String[] args) {
        
           try {
            DBSource dbsource = new SimpleDBSource();
            Connection conn = dbsource.getConnection();
            
            if(!conn.isClosed()) {
                System.out.println("資料庫連接已開啟…");
            }
            
            dbsource.closeConnection(conn);
            
            if(conn.isClosed()) {
                System.out.println("資料庫連接已關閉…");
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
}
