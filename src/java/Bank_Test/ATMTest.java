/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bank_Test;

import DB_Test.DBSource;
import DB_Test.SimpleDBSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;



/**
 *
 * @author hung_yilai
 */
public class ATMTest {
    public static void main(String[] args) {
        
        //工廠製造出一個ＤＡＯ
        AccountDAOFactory factory = new AccountDAOFactory();
        AccountDAO dao= factory.createAccountDAO();
        Account acc = new DebitAccount();
        
        //將資料庫中的customer表格轉進 DAO customerArray
        DBSource dbsource = null;
        Connection conn = null;
        Statement stmt = null;
        try  {
            dbsource = new SimpleDBSource();
            conn = dbsource.getConnection();
            
            if( !conn.isClosed()) {System.out.println("已連接銀行資料庫...");};
            
            //計算customer數
            int customerSize = 0;
            String query1 = "select count(*) from Account.Customer";
            PreparedStatement stmt1 = conn.prepareStatement(query1);
            ResultSet rs = stmt1.executeQuery();
            while(rs.next()){
            customerSize = rs.getInt("count(*)");
           }
            
            //讀取customer表格將account資料存進dao
            Statement stmt2 = conn.createStatement();
            Statement stmt3 = conn.createStatement();
            Statement stmt4 = conn.createStatement();
            String query2 = "select * from customer";
            ResultSet result2 = stmt2.executeQuery(query2);
            int x =0;    //用以紀錄走到哪一個Customer
            
                while (result2.next()){
                    
                    String accID = result2.getString(1);
                    String cusName = result2.getString(2);
                    String descrn = result2.getString(3);
                    ArrayList<TracRecord> bankBook = new ArrayList<TracRecord>();
                    
                    //計算交易記錄筆數
                    ResultSet result4 = stmt4.executeQuery(
                    "SELECT * FROM Account.TracRecord00"+x);
                    result4.last();
                    String tracRecordSize = result4.getString(1);
                    
                    //將account的交易紀錄存進tracRecord
                    String query3 = "select *  from Account.TracRecord00"+x;
                    ResultSet result3 = stmt3.executeQuery(query3);
                     while (result3.next()){
                   
                    Date date = result3.getDate(2);
                    String nationMoney = result3.getString(3);
                    String typeString = result3.getString(4);
                    Double paidIn = result3.getDouble(5);
                    Double paidOut = result3.getDouble(6);
                    Double balance = result3.getDouble(7);
                    String description = result3.getString(8);
                    TracRecord t = new TracRecord(date, nationMoney, Type.getEnum(typeString), paidIn, paidOut,balance, description);
                    bankBook.add(t);
                    }
                    
                    acc = new DebitAccount.DBuilder().setAccountID(accID).
                    setCustomerName(cusName).setBankBook(bankBook).setDiscription(descrn).
                    setInterestRate(0.13).build();
                    dao.Add(acc);
                    x++;
                }
                
                
                
        
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
//        
//        
//        //交易初始設定
        AccountInit Init = new AccountInit(dao);
        Init.welcome();
       acc = Init.checkAccountID();
//        
//        ArrayList<TracRecord> y = new ArrayList<TracRecord>();
//        Account acc2 = new DebitAccount.DBuilder().setAccountID("001").
//                setCustomerName("Ray").setBankBook(y).setDiscription("").
//                setInterestRate(0.02).build();
//        TracRecord firstAcc2 = new TracRecord("      ", "     ", Type.CASH, 0 , 0,100000, "");
//        acc2.bankBook.add(firstAcc2);
//        dao.Add(acc2);
//        
//        
//        
        ATM_Machine machine1 = new ATM_MachineImp(dao.getAllAccounts(),acc);
//    
       machine1.greeting();
       machine1.tranStart(acc);
        

        
//        machine1.tranInit();
//        dao.FindById("000").printSheets();
    }
}

