/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bank_Test;


import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 *
 * @author hung_yilai
 */


/*
請用繼承來設計三種不同的帳戶
DebitAccount/CheckingAccount/VIP_Account
三個帳戶各有不同的計算利息方式或輸出報表方式
所以需要 新增利息欄位 和 覆寫(Override)報表方法
*/
public class DebitAccount extends Account {
    private double interestRate;
    private double overDraftLimit;
   
    
    
    //constructor
    protected DebitAccount(){} //Builder不需要有引數的建構子
//    private DebitAccount (DBuilder dBuilder) {
//        this.accountID = dBuilder.accountID;
//        this.customerName = dBuilder.customerName;
//        this.bankBook = dBuilder.bankBook;
//        this.discription = dBuilder.discription;
//        this.interestRate = dBuilder.
//    }
//    
    
//    public DebitAccount(String accountID,String customerName,
//            ArrayList<TracRecord> bankBook,double interest) {
//        super (accountID, customerName, bankBook);
//        this.interestRate = interest;
//        
//    }
    // getter&setter
    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interest) {
        this.interestRate = interest;
    }
    
    
    //Builder
    public static class DBuilder extends Account.Builder<DebitAccount,DBuilder> {
        @Override
        protected DebitAccount getObject() {
            return new DebitAccount();
        }
        @Override 
        protected DBuilder thisObject() {
            return this;
        }
        public DBuilder() {
        }
        public DBuilder setInterestRate(double interestRate) {
            object.interestRate = interestRate;
            return this;
        }
    }
    
    //餘額產生利息
    public void accrueInterest (Date date, String nM){
        int i = bankBook.size();
        double balance = bankBook.get(i-1).getBalance();
        
        double interest = balance * interestRate;
        balance += interest;
        
        TracRecord t = new TracRecord(date, nM, Type.CASH, interest, 0, balance, "Interest accrued: "+interest);
        bankBook.add(t);
        
    }
    
    //金融卡交易
    public void cardPay (double price, Date date, String nM) {
        
        int i = bankBook.size();
        double balance = bankBook.get(i-1).getBalance();
        balance = balance - price;
        
        TracRecord t = new TracRecord(date, nM, Type.DEBITCARD, 0, price, balance, "Pay by card, you purchased "+price);
        bankBook.add(t);
        
        
        System.out.println("CardPay Seccess!");
    }
    
    
    
    @Override
    public void printSheets (){
        
        
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("Customer Name : "+ customerName);                 //因為父類別已設protected
        System.out.println("-----------------------------------------------------------------------");
        
        System.out.println("Account ID : "+ accountID);
        System.out.println("-----------------------------------------------------------------------");
        
        System.out.println("Account Type :  Debit");
        System.out.println("-----------------------------------------------------------------------");
        
        
        
        if(bankBook.size() > 1){
        int i;
        for(i=1;i<bankBook.size();i++){
        
        Date date = bankBook.get(i).getDate(); 
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
        String nationMoney = bankBook.get(i).getNationMoney();
        String type = bankBook.get(i).type.name();
        double paidIn = bankBook.get(i).getPaidIn();
        double paidOut = bankBook.get(i).getPaidOut();
        double balance = bankBook.get(i).getBalance();
        String discription = bankBook.get(i).getDiscription();
        
        System.out.println("Date            NationMoney       Type         Paid_in     Paid_out       Balance           Discription");
        
        if (paidIn > 0 ){
        
            System.out.println(ft.format(date) +"         "+ nationMoney +"         "+ type + "          " + paidIn + "                    "+
                balance +"          "+ discription);
            System.out.println("----------------------------------------------------------------------------------------------");
        }   else {
            System.out.println(ft.format(date) +"         "+ nationMoney +"         "+ type + "                      " + paidOut + "        "+
                balance +"          "+ discription);
            System.out.println("----------------------------------------------------------------------------------------------");
        
        }
        
       }
        
        }else{
            Date date = bankBook.get(0).getDate();    
            SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
            String nationMoney = bankBook.get(0).getNationMoney();
            double paidIn = bankBook.get(0).getPaidIn();
            double paidOut = bankBook.get(0).getPaidOut();
            double balance = bankBook.get(0).getBalance();
            String discription = bankBook.get(0).getDiscription();
              System.out.println("Date           NationMoney       Paid_in     Paid_out       Balance           Discription");
              System.out.println(ft.format(date) +"      "+ nationMoney +"            "+ balance + "                       "+
                balance +"       "+ discription);
              System.out.println("-----------------------------------------------------------------------");
        
                }
        
        }
     
        
        
    }
