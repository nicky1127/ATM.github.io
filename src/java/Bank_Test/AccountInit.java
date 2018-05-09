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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Date;
/**
 *
 * @author hung_yilai
 */
public class AccountInit {
    
    protected AccountDAO dao;
    
    
    
    public AccountInit(AccountDAO dao){
        this.dao = dao;
    }
   //設定日期
    Date date = new Date();SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
    String d = "'"+ft.format(date)+"',";
    
    //Declaration connect to Database
    DBSource dbsource = null;
    Connection conn = null;
    Statement stmt = null;
    
    //宣告account預備
    Account account = new DebitAccount();
    
    public  void welcome(){
    System.out.println("===============================================");
    System.out.println("Welcome! My customer");
    
    }
    
    public  Account checkAccountID(){
    Scanner sc1 = new Scanner(System.in);
    Scanner sc2 = new Scanner(System.in);
    Scanner sc3 = new Scanner(System.in);
    
    
    
    System.out.println("Please input your Account ID");
    System.out.println("(Input N if you want to create an Account)");
    System.out.println("===============================================");
    System.out.print("...");
    String inputAccountID = sc1.next();
    System.out.println("\nConnecting to database...\n");
    
    try {   
        dbsource = new SimpleDBSource();
        conn = dbsource.getConnection();
    
    if (inputAccountID.equals("N")){   //customer無帳戶 且欲開新帳戶
         
            
        System.out.println("\n===============================================");      
        System.out.println("We are creating your new account");
        System.out.println("Please input your Name");
        System.out.print("\n...");
        String name = sc2.nextLine();
       
        System.out.println("\n===============================================");
        System.out.println("Now you have to make the first deposit to activate you account");
        System.out.println("How much deposit you would like to make");
        System.out.print("\n...");
        double firstDeposit = sc3.nextDouble();
        System.out.println("\nProcessing...");
        System.out.println("===============================================");
        
        
        //取得現有customer  並設定AccoutID
        int customerSize = 0;
        String query1 = "select count(*) from Account.Customer";
        PreparedStatement stmt1 = conn.prepareStatement(query1);
        ResultSet rs = stmt1.executeQuery();
        while(rs.next()){
            customerSize = rs.getInt("count(*)");
           }
        String accID = "00"+customerSize;
       
        //創建customer之account物件
        
        TracRecord firstAcc = new TracRecord(date, "     ", Type.CASH, 0 , 0,firstDeposit, "Account is created");
        ArrayList<TracRecord> t = new ArrayList<>();
        Account acc = new DebitAccount.DBuilder().setAccountID(accID).
                setCustomerName(name).setBankBook(t).setDiscription("").setInterestRate(0.03).build();
        
        //在Customer表格中加入新customer資料
        Statement stmt2 = conn.createStatement(
                        ResultSet.TYPE_SCROLL_SENSITIVE, 
                        ResultSet.CONCUR_UPDATABLE);
            
            ResultSet result = stmt2.executeQuery(
                    "SELECT * FROM Customer");
            result.moveToInsertRow();
            result.updateString("AccountID", accID);
            result.updateString("CustomerName", name);
            result.updateString("description", "DebitAccount");
            result.insertRow();
           
          //刪除帳戶
//            ResultSet result = stmt2.executeQuery(
//                    "SELECT * FROM Customer ");
//                    result.last();
//                    result.deleteRow();
//            
        //將第一筆存款記錄存進存摺
         
        acc.bankBook.add(firstAcc);
        
        dao.Add(acc);   
        account = acc;
        
        //創建新Costomer的交易紀錄(存摺)表格
        
        String TracRecordName = "TracRecord"+accID;
           Statement stmt3 = conn.createStatement();
            stmt3.executeUpdate("Create Table Account."+ TracRecordName
                    + " (no VARCHAR(3) primary key,"
                    + "date Date, "
                    + "nationmoney VARCHAR(5) default ' ', "
                    + "type enum('CASH','DEBITCARD', 'ONLINE') default 'CASH', "
                    + "paidIn DOUBLE , "
                    + "paidOut DOUBLE , "
                    + "balance DOUBLE NOT NULL , "
                    + "discription VARCHAR(40) default ' ')");
            
            
        //將第一筆存款記錄存進交易紀錄表格
        //方法一：使用Resultset update
//         Statement stmt4 = conn.createStatement(
//                        ResultSet.TYPE_SCROLL_SENSITIVE, 
//                        ResultSet.CONCUR_UPDATABLE);
//            
//         String query2 = "SELECT * FROM Account." + TracRecordName;
//            ResultSet result2 = stmt4.executeQuery(query2);
//            result2.moveToInsertRow();
//            result2.updateString("no", "1");
//            result2.updateTimestamp(2, currentTimestamp);
//            result2.updateString("nationMoney", "TWD");
//            result2.updateDouble("paidIn", acc.bankBook.get(0).getPaidIn());
//            result2.updateDouble("paidOut", acc.bankBook.get(0).getPaidOut());
//            result2.updateDouble("balance", acc.bankBook.get(0).getBalance());
//            result2.updateString("discription", "first deposit");
//            result2.insertRow();


         //方法二 stmt executeupdate
        Statement stmt4 = conn.createStatement();
        String query2 = "Insert into Account."
                + TracRecordName
                + " values ('1', "
                + d
                + " 'TWD',"
                +"'"+Type.CASH+"',"
                + acc.bankBook.get(0).getPaidIn()+"," 
                + acc.bankBook.get(0).getPaidOut()+","
                + acc.bankBook.get(0).getBalance()+","
                + "'first deposit')";
       
        stmt4.executeUpdate(query2);
        
        
        
            
        
        
        System.out.println("First deposit is made....");
        System.out.println("Your account is successfully created....");
        System.out.println("===============================================");
        System.out.println("Your Account ID is " + accID);
        System.out.println("Your transaction is shown below");
        try{
            dao.FindById(accID).printSheets();
            }catch(Exception e){
                e.printStackTrace();
            };
        
        System.out.println(""); 
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        
        
        
    
    
    
     
    
    } //if
    else{   //  判定輸入的account ID是否存在
        //方法一 從dao找
//        ArrayList k = new ArrayList();
//        ArrayList<Account> CustomerArray = dao.getAllAccounts();
//        for(int b=0;b<CustomerArray.size();b++){
//            k.add(CustomerArray.get(b).getAccountID());
//        }
//        
            try{
//                if(k.contains(inputAccountID)){
            account = dao.FindById(inputAccountID);
            
//                }
            }catch(Exception e){
//                e.printStackTrace();
//                    e.getMessage();
//                String s = "\nPlease input again!\n";
//                DAOException d =  new DAOException(s,e);
//                d.getMessage();

                System.out.println(e.getMessage());
                System.out.println("\nPlease input again!\n");
                checkAccountID();
            };
//            String customerName = account.getCustomerName();
//            System.out.println("Dear "+customerName);
            
        
//            else{
//            System.out.println("Sorry, this account ID does not exist yet!\n\n");
//            checkAccountID();
//        }
            //方法二 從資料庫找        
       
//        Statement stmt4 = conn.createStatement();
//        String input2 = "select * from Customer";
//        ResultSet result3 = stmt4.executeQuery(input2);
//        ArrayList a = new ArrayList();
//        
//        
//        while (result3.next()){
//            a.add(result3.getString(1));
//        }
//        if(a.contains(inputAccountID)){
//            result3.first();
//            while (result3.next()){
//                if(inputAccountID.equals(result3.getString(1))){
//                    String customerName = result3.getString(2);
//                    System.out.println("Dear "+customerName);
//                }
//            
//        }
            
//        }else{
//            System.out.println("Sorry, this account ID does not exist yet!");
//            checkAccountID();
//        }
//        
    }//else
    
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
    return account;
    }//checkAccountID()
} //AccountInit
    
