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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Z_SimpleDBSource implements DBSource {
    private Properties props;
    private String url;
    private String user;
    private String passwd;

    public Z_SimpleDBSource() throws IOException, 
                                         ClassNotFoundException {
        this("jdbc.properties");
    }
	
    public Z_SimpleDBSource(String configFile) throws IOException, 
                                                    ClassNotFoundException {
        props = new Properties();
        InputStream in =  Z_SimpleDBSource.class.getResourceAsStream("jdbc.properties");
//     FileInputStream in = new FileInputStream("/Users/hung_yilai/NetBeansProjects/Bank_test_net/src/java/Bank_Test/jdbc.properties");
//     props.load(new FileInputStream("src/java/Bank_Test/jdbc.properties"));
        props.load(in);
		
        url = props.getProperty("url");
        user = props.getProperty("user");
        passwd = props.getProperty("password");
		
//        Class.forName(
//                    props.getProperty("driver"));
//        System.out.println("直接输出prop对象:"+props);
//        System.out.println(System.getProperty("user.dir"));  //所在路徑
    }
    

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, passwd);
    }

    public void closeConnection(Connection conn) throws SQLException {
        conn.close();
    }
}
