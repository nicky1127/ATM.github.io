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
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 *
 * @author hung_yilai
 */
public class ATM_MachineImp implements ATM_Machine{
// 這是一個ATM機台類別，請將其設計成有提款，存款，轉帳的功能。
    // 封裝帳戶欄位：用以取得帳戶物件Account
    private Account acc;
    private  ArrayList<Account> customerArray;
    Date date = new Date();
    SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
    String d = "'"+ft.format(date)+"',";
    
    
    
    
    // getter & setter

    public ArrayList<Account> getCustomerArray() {
        return customerArray;
    }

    public void setCustomerArray(ArrayList<Account> customerArray) {
        this.customerArray = customerArray;
    }

    public Account getAcc() {
        return acc;
    }

    public void setAcc(Account acc) {
        this.acc = acc;
    }

    

   

   
    
    // 設計建構子：類別的進入點用以初始化參數
    
    public ATM_MachineImp(ArrayList<Account> customerArray, Account acc){
        this.customerArray = customerArray;
        this.acc=acc;
      
    }

    
    // 設計提款，存款，轉帳方法
    
    //歡迎詞
    public void greeting(){
        System.out.println(" ");
        System.out.println("===============================================");
        System.out.println("Welcome to Nicky International Bank");
    }
    
    //交易起始
    public void tranStart(Account acc){
        
//        System.out.println("Please input your Account ID\n");
//        Scanner sc1 = new Scanner(System.in);
//        String accountID = sc1.next();
//        acc = customerArray.get(Integer.parseInt(accountID));
        System.out.println("\n=====================================");
        System.out.println("what kind of transaction would you like to make?");
        System.out.println("--------------------------- ");
        System.out.println("|  1. Whithdrawl                           ");
        System.out.println("|  2. Deposit                                 ");
        System.out.println("|  3. Transfer                                ");
        System.out.println("|  4. Check Balance                      ");
        System.out.println("|  5. Cancel                    ");
        System.out.println(" ---------------------------\n");
        System.out.print("Input the number....");
        
        Scanner sc2 = new Scanner(System.in);
        int choice = sc2.nextInt();
        
        if(choice == 1){
            withdraw(acc);
        }else if (choice == 2){
            deposit(acc);
        }else if(choice == 3){
            transfer(acc);
        }else if (choice == 4){
            acc.printSheets();;
            transit(acc);
        }else {
                System.out.println("\n================================");
                System.out.println("Hope you are satisfied the service, see you soon!");
                System.out.println("Bye!!!");
                System.out.println("================================");
        }
        
    }
    
    public void transit(Account acc){
        System.out.println("\n=====================================");
        System.out.println("Do you wish to use another service?");
        System.out.println("--------------------------- ");
        System.out.println("|     Yes           (input   Y )                ");
        System.out.println("|     No            (input   N )                  ");
        System.out.println(" ---------------------------\n");
        System.out.print(" ......");
        Scanner sc1 = new Scanner(System.in);
        String choice1 = sc1.next();
        if (choice1.equals("Y")){
            tranStart(acc);
            
        }else{
            System.out.println("\n=====================================");
            System.out.println("Do you wish to show your transaction history?");
            System.out.println("--------------------------- ");
            System.out.println("|     Yes           (input   Y )                ");
            System.out.println("|     No            (input   N )                  ");
            System.out.println(" ---------------------------\n");
            System.out.print(" ......");
            Scanner sc2 = new Scanner(System.in);
            String choice2 = sc2.next();
            if(choice2.equals("Y")){
                acc.printSheets();
                System.out.println("\n================================");
                System.out.println("Hope you are satisfied the service, see you soon!");
                System.out.println("Bye!!!");
                System.out.println("================================");
                
            }else{
                System.out.println("\n================================");
                System.out.println("Hope you are satisfied the service, see you soon!");
                System.out.println("Bye!!!");
                System.out.println("================================");
            }
        }
        
        
        
    }
    
    
    
    //提款========================================================
    public void withdraw (Account acc){
        
        DBSource dbsource = null;
        Connection conn = null;
        Statement stmt = null;
        double amount;
        String nM;
        
        try {   
        System.out.println("\nProcessing...\n");
        dbsource = new SimpleDBSource();
        conn = dbsource.getConnection();
        
        
        System.out.println("\nHow much withdrawl would you like to make?");
        System.out.print(".......");
        Scanner sc1 = new Scanner(System.in);
        amount = sc1.nextDouble();
        nM = " TWD ";
        
        //得到上次交易餘額
        int i = acc.bankBook.size();
        double balance = acc.bankBook.get(i-1).getBalance();
        
        if (amount >balance ){ //驗證餘額是否夠本次提款
            
            System.out.println("\nInsufficient funds\n");
            transit(acc);
            
            
        }else{
        balance = balance - amount;
        
        //本次交易之交易紀錄
        TracRecord t = new TracRecord(date, nM, Type.CASH , 0,amount, balance, "withdraw "+nM+amount);
         //double不傳值怎麼表示
       //將本次交易紀錄存進存摺
        acc.bankBook.add(t);
        
        //計算交易前之交易記錄筆數
        String query2 ="SELECT * FROM Account.TracRecord"+acc.getAccountID();
       
        stmt = conn.createStatement();
        ResultSet result = stmt.executeQuery(query2);
                    result.last();
                    String tracRecordSize = result.getString(1);
        tracRecordSize = "'"+(Integer.parseInt(tracRecordSize)+1)+"',";
        //將交易紀錄存到資料庫
        String TracRecordName = "TracRecord"+acc.getAccountID();
        stmt = conn.createStatement();
        String query1 = "Insert into Account."
                + TracRecordName
                + " values ("
                + tracRecordSize
                + d
                + " 'TWD',"
                +"'"+Type.CASH+"',"
                + acc.bankBook.get((acc.bankBook.size()-1)).getPaidIn()+"," 
                + acc.bankBook.get((acc.bankBook.size()-1)).getPaidOut()+","
                + acc.bankBook.get((acc.bankBook.size()-1)).getBalance()+","
                + "'"+acc.bankBook.get((acc.bankBook.size()-1)).getDiscription()+"'"+")";
       
        stmt.executeUpdate(query1);
        
        System.out.println("\nWithdrawal Seccess!");
        
        transit(acc);
        
        
        } //else
        
        
        
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

    //存款 =====================================================
    
    
    public void deposit (Account acc){
        
        DBSource dbsource = null;
        Connection conn = null;
        Statement stmt = null;
        double amount;
        String nM;
        
        try {   
        System.out.println("\nProcessing...\n");
        dbsource = new SimpleDBSource();
        conn = dbsource.getConnection();
        
        
        System.out.println("\nHow much deposit would you like to make?");
        System.out.print(".......");
        Scanner sc1 = new Scanner(System.in);
        amount = sc1.nextDouble();
        nM = " TWD ";
        //得到帳戶餘額
        int i = acc.bankBook.size();
        double balance = acc.bankBook.get(i-1).getBalance();
        
        
        balance = balance + amount;
        
        //本次交易之交易紀錄
        TracRecord t = new TracRecord(date, nM, Type.CASH , amount, 0, balance, "deposit "+nM+amount);
         //double不傳值怎麼表示
       //將本次交易紀錄存進存摺
        acc.bankBook.add(t);
        
        //計算交易前之交易記錄筆數
        String query2 ="SELECT * FROM Account.TracRecord"+acc.getAccountID();
       
        stmt = conn.createStatement();
        ResultSet result = stmt.executeQuery(query2);
                    result.last();
                    String tracRecordSize = result.getString(1);
        tracRecordSize = "'"+(Integer.parseInt(tracRecordSize)+1)+"',";
        //將交易紀錄存到資料庫
        String TracRecordName = "TracRecord"+acc.getAccountID();
        stmt = conn.createStatement();
        String query1 = "Insert into Account."
                + TracRecordName
                + " values ("
                + tracRecordSize
                + d
                + " 'TWD',"
                +"'"+Type.CASH+"',"
                + acc.bankBook.get((acc.bankBook.size()-1)).getPaidIn()+"," 
                + acc.bankBook.get((acc.bankBook.size()-1)).getPaidOut()+","
                + acc.bankBook.get((acc.bankBook.size()-1)).getBalance()+","
                + "'"+acc.bankBook.get((acc.bankBook.size()-1)).getDiscription()+"'"+")";
       
        stmt.executeUpdate(query1);
        
        System.out.println("\nDeposit Seccess!");
        
        transit(acc);
        
        
        
        
        
        
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
    //轉帳
   
    public void transfer (Account acc1){
        
        DBSource dbsource = null;
        Connection conn = null;
        Statement stmt = null;
        double amount;
        String acc2AccountID;
        String nM;
        
        try {   
        System.out.println("\nProcessing...\n");
        dbsource = new SimpleDBSource();
        conn = dbsource.getConnection();
        
        
        System.out.println("\nPlease input the account ID of the recipient?");
        System.out.println("......");
        Scanner sc1 = new Scanner(System.in);
        acc2AccountID = sc1.next();
        Account acc2 = customerArray.get(Integer.parseInt(acc2AccountID));;
        
        System.out.println("\nHow much transfer would you like to make?");
        Scanner sc2 = new Scanner(System.in);
        amount = sc2.nextDouble();
        
        nM = " TWD ";
        
        //得到雙方上次交易餘額
        int i = acc1.bankBook.size();
        int j = acc2.bankBook.size();
        
        double balance1 = acc1.bankBook.get(i-1).getBalance();
        double balance2 = acc2.bankBook.get(j-1).getBalance();
        
        if (amount >balance1 ){ //驗證餘額是否夠本次提款
            
            System.out.println("\nInsufficient funds\n");
            transit(acc);
            
            
        }else{
        balance1 = balance1 - amount;
        balance2 = balance2 + amount;
        
        //本次交易之交易紀錄
       //將本次交易紀錄存進存摺
        TracRecord t = new TracRecord(date, nM, Type.ONLINE, 0, amount, balance1, "Transfer "+nM+amount+" to "+ acc2.getCustomerName());
        acc1.bankBook.add(t);
        TracRecord y = new TracRecord(date, nM, Type.ONLINE, amount, 0, balance2, acc1.getCustomerName()+ " transsfer "+nM+ amount +" to you");
        acc2.bankBook.add(y);
         
        
        //計算交易前之交易記錄筆數
        String query1 ="SELECT * FROM Account.TracRecord"+acc1.getAccountID();
        String query2 ="SELECT * FROM Account.TracRecord"+acc2.getAccountID();
        stmt = conn.createStatement();
        Statement stmt2 = conn.createStatement();
        //acc1筆數
        ResultSet result1 = stmt.executeQuery(query1);
                    result1.last();
                    String tracRecordSize1 = result1.getString(1);
        tracRecordSize1 = "'"+(Integer.parseInt(tracRecordSize1)+1)+"',";
        //acc2筆數
        ResultSet result2 = stmt2.executeQuery(query2);
                    result2.last();
                    String tracRecordSize2 = result2.getString(1);
        tracRecordSize2 = "'"+(Integer.parseInt(tracRecordSize2)+1)+"',";
        //將交易紀錄存到資料庫
        String TracRecordName1 = "TracRecord"+acc1.getAccountID();
        String TracRecordName2 = "TracRecord"+acc2.getAccountID();
//        stmt = conn.createStatement();
        String query1_1 = "Insert into Account."
                + TracRecordName1
                + " values ("
                + tracRecordSize1
                + d
                + " 'TWD',"
                +"'"+Type.ONLINE+"',"
                + acc1.bankBook.get((acc1.bankBook.size()-1)).getPaidIn()+"," 
                + acc1.bankBook.get((acc1.bankBook.size()-1)).getPaidOut()+","
                + acc1.bankBook.get((acc1.bankBook.size()-1)).getBalance()+","
                + "'"+acc1.bankBook.get((acc1.bankBook.size()-1)).getDiscription()+"'"+")";
       
        stmt.executeUpdate(query1_1);
        String query2_1 = "Insert into Account."
                + TracRecordName2
                + " values ("
                + tracRecordSize2
                + d
                + " 'TWD',"
                +"'"+Type.ONLINE+"',"
                + acc2.bankBook.get((acc2.bankBook.size()-1)).getPaidIn()+"," 
                + acc2.bankBook.get((acc2.bankBook.size()-1)).getPaidOut()+","
                + acc2.bankBook.get((acc2.bankBook.size()-1)).getBalance()+","
                + "'"+acc2.bankBook.get((acc2.bankBook.size()-1)).getDiscription()+"'"+")";
       
        stmt.executeUpdate(query2_1);
        
        System.out.println("\nWithdrawal Seccess!");
        
        transit(acc1);
        
        
        } //else
        
        
        
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

//    public static void main(String[] args) {
//        DBSource dbsource = null;
//        Connection conn = null;
//        Statement stmt = null;
//        Date date = new Date();
//        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
//    String d = "'"+ft.format(date)+"',";
//    
//        ArrayList<TracRecord> y = new ArrayList<TracRecord>();
//        AccountDAOFactory factory = new AccountDAOFactory();
//        AccountDAO dao= factory.createAccountDAO();
//        
//        Account acc2 = new DebitAccount.DBuilder().setAccountID("000").
//                setCustomerName("Ray").setBankBook(y).setDiscription("").
//                setInterestRate(0.02).build();
//        
//        TracRecord firstAcc2 = new TracRecord(date, "     ", Type.CASH, 0 , 0,100000, "");
//        acc2.bankBook.add(firstAcc2);
//        acc2.printSheets();
//        
//        
//        
//        
//        dao.Add(acc2);
//        
//        
//        String query2 ="SELECT * FROM Account.TracRecord"+acc2.getAccountID();
//        System.out.println(query2);
//        try { 
//         dbsource = new SimpleDBSource();
//         conn = dbsource.getConnection();
////         
//         stmt = conn.createStatement();
//        ResultSet result = stmt.executeQuery(query2);
//                    result.last();
//                    String tracRecordSize = result.getString(1);
//        System.out.println("safe");
//        tracRecordSize = "'"+(Integer.parseInt(tracRecordSize)+1)+"',";
//        System.out.println(tracRecordSize);
//        //將交易紀錄存到資料庫
//        String TracRecordName = "TracRecord"+acc2.getAccountID();
//        stmt = conn.createStatement();
//        String query1 = "Insert into Account."
//                + TracRecordName
//                + " values ("
//                + tracRecordSize
//                + d
//                + " 'TWD',"
//                +"'"+Type.CASH+"',"
//                + acc2.bankBook.get((acc2.bankBook.size()-1)).getPaidIn()+"," 
//                + acc2.bankBook.get((acc2.bankBook.size()-1)).getPaidOut()+","
//                + acc2.bankBook.get((acc2.bankBook.size()-1)).getBalance()+","
//                + "'Withdrawl')";
//        stmt.executeUpdate(query1);
//        System.out.println(query1);
//        System.out.println((acc2.bankBook.size()-1));
//        
//        ATM_Machine machine1 = new ATM_MachineImp(dao.getAllAccounts());
//        machine1.withdraw(acc2);
        
        


//    } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            if (stmt != null) {
//                try {
//                    stmt.close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//            if (conn != null) {
//                try {
//                    dbsource.closeConnection(conn);
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//}
    public static void main(String[] args) {
        ArrayList a = new ArrayList();
        a.add("a");
        a.add("b");
        a.add("c");
        if (a.contains("d")){
            System.out.println("yes");
        }else{
             System.out.println("no");
        }
        
   
    }
    
    
}

