/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bank_Test;

import DB_Test.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 *
 * @author hung_yilai
 */
public class Z_StatementResultDemo {

    public static void main(String[] args) {
        DBSource dbsource = null;
        Connection conn = null;
        Statement stmt = null;
        try {
            dbsource = new SimpleDBSource();
            conn = dbsource.getConnection();

            if (!conn.isClosed()) {
                System.out.println("已連接資料庫...");
            };

            //創建TracRecord表格
//            stmt = conn.createStatement();
//            stmt.executeUpdate("Create Table TracRecord "
//                    + "(date VARCHAR(10), "
//                    + "nationmoney VARCHAR(5) default ' ', "
//                    + "paidIn DOUBLE , "
//                    + "paidOut DOUBLE , "
//                    + "balance DOUBLE NOT NULL , "
//                    + "discription VARCHAR(40) default ' ')");
            //Statement Date物件 （將Date物件存進Ｍysql)
//            Date date = new Date();
//            java.util.Date jDate = new java.util.Date();
//            Timestamp currentTimestamp = new Timestamp(date.getTime());
//
//            //date物件
//            SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
//            stmt = conn.createStatement();
//            System.out.println(date);
//            String d = "  '  " + (ft.format(date)) + "  '  ";
//
//            String c = "  '  " + (date.toString()) + "  '  ";
////            System.out.println(d);
//            String query1 = "insert into dataTest values (19, "
//                    + d
//                    + ")";
//            stmt.executeUpdate(query1);
//            System.out.println(query1);

String s = "abc";
            Date date = new Date();
    SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
    String d = "'"+ft.format(date)+"',";
            
            Statement stmt4 = conn.createStatement();
//        String query2 = "Insert into Account.TracRecord values ('5', "
//                + d
//                + " 'GBP', "
//                +"'"+Type.CASH.name()+"',"
//                + " 4566, "
//                + "6766, "
//                + "876,"
//                + " 'first deposit' )";
//        System.out.println(query2);
//        stmt4.executeUpdate(query2);
        
        String query3 ="select type from Account.TracRecord";
        ResultSet rs = stmt4.executeQuery(query3);
        rs.next();
        String query2= rs.getString(1);
        System.out.println(query2);
        
        Type q = Type.getEnum(query2);
         System.out.println(q);
        

            //curentTimestamp
//stmt = conn.createStatement();
//SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd"); 
//String d = "  '  "+(ft.format(currentTimestamp))+"  '  ";
// String query1 = "insert into dataTest values (11, "
//                    + d
//                    + ")";
//            System.out.println(query1);
//            stmt.executeUpdate(query1);
//            Statement stmt1=conn.createStatement();
//            ResultSet rt = stmt1.executeQuery("SELECT * FROM dataTest");
//            rt.next();
//            Date c = rt.getDate(2);
//              System.out.println(c);
//              stmt.executeUpdate("insert into dataTest values (5,'2018-04-19')");


            //PreparedStatement
//        String sql="INSERT Into customer VALUES ( ?, ? )";//prepare statement
// 
//        PreparedStatement pstmt = conn.prepareStatement(sql);
//        pstmt.setString(1, "009");//第一個?要插入的值
//        pstmt.setString(2, "Mike");//第二個?要插入的值
//        pstmt.executeUpdate();
//        pstmt.clearParameters();
            //Batch
//            stmt = conn.createStatement(); 
//            stmt.addBatch(
//               "INSERT INTO Customer VALUES('100', 'John')"
//            );
//            stmt.addBatch(
//               "INSERT INTO Customer VALUES('101', 'Mary')"
//            );
//            stmt.addBatch(
//               "INSERT INTO Customer VALUES('102', 'Sandy')"
//            );
//            stmt.executeBatch();
            //取消執行
//            conn.setAutoCommit(false); 
            //增改刪
//            stmt = conn.createStatement(
//                        ResultSet.TYPE_SCROLL_SENSITIVE, 
//                        ResultSet.CONCUR_UPDATABLE);
//            ResultSet result = stmt.executeQuery(
//                    "SELECT * FROM TracRecord");
//            result.moveToInsertRow();
//            result.updateString("no", "1");
//            result.updateString("date", "20180202");
//            result.updateString("nationMoney", "TWD");
//            result.updateDouble("paidIn", 20000);
//            result.updateDouble("paidOut", 0);
//            result.updateDouble("balance", 20000);
//            result.updateString("discription", "first deposit");
//            result.insertRow();
//            ResultSet result = stmt.executeQuery(
//                    "SELECT * FROM Account.Customer");
//                    result.last();
//                    String ID = result.getString(1);
//                    System.out.println(ID);
            //MetaData
//            stmt = conn.createStatement(); 
//            ResultSet result = stmt.executeQuery("SELECT * FROM Account.TracRecord"); 
//            ResultSetMetaData metadata = result.getMetaData(); 
// 
//            for(int i = 1; i <= metadata.getColumnCount(); i++) { 
//                System.out.print(
//                        metadata.getTableName(i) + "."); 
//                System.out.print(
//                        metadata.getColumnName(i) + "\t|\t"); 
//                System.out.print(
//                        metadata.getColumnType(i) + "\t|\t");
//                System.out.println(
//                        metadata.getColumnTypeName(i)); 
//            }
//            ResultSet result = stmt.executeQuery(
//                                  "SELECT * FROM Customer"); 
//            while(result.next()) { 
//                System.out.print(result.getString(1) + "\t"); 
//                System.out.println(result.getString(2) + "\t"); 
//            }
            //表格中row數
            int rowSize = 0;
            String query = "select count(*) from Account.Customer";
            PreparedStatement st = conn.prepareStatement(query);
            rs = st.executeQuery();
            while (rs.next()) {
                rowSize =rs.getInt("count(*)");
            }
            System.out.println(rowSize);

//            
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    dbsource.closeConnection(conn);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
