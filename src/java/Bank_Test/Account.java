/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bank_Test;


import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 *
 * @author ray Nick
 */
public  abstract class Account extends AccountDAOMemoryImp implements CardPay, 
        AccrueInterest,Comparable<Account>{  // 這是帳戶類別物件
    
    
    
    
    protected String accountID;
    protected String customerName;
    protected ArrayList<TracRecord>  bankBook;
    protected String discription; 
    
//    public Account (Builder builder) {
//        this.accountID = builder.accountID;
//        this.customerName = builder.customerName;
//        this.bankBook = builder.bankBook;
//        this.discription = builder.discription;
//    }
    //constructor
    public Account(){}
    
    
   

    //Builder建置
     abstract class Builder<T extends Account, B extends Builder<T,B>> {
        
        protected T object;
        protected B thisObject;
        protected abstract T getObject(); //Each concrete implementing subclass overrides this so that T becomes an object of the concrete subclass
        protected abstract B thisObject();
        
        protected String accountID;
        protected String customerName;
        protected ArrayList<TracRecord>  bankBook;
        protected String discription; 
        
        protected Builder() {
        object = getObject();
        thisObject = thisObject();
        }
        
       
        public B setAccountID(String accountID){object.accountID = accountID;
        return thisObject;
        }
        public B setCustomerName(String customerName){object.customerName = customerName;
        return thisObject;
        }
        public B setBankBook(ArrayList<TracRecord>  bankBook){object.bankBook = bankBook;
        return thisObject;
        }
        public B setDiscription(String discription){object.discription = discription;
        return thisObject;
        }
        public T build(){ return object;}
        
        
    }
    //getter&setter
    public String getAccountID() {
        return accountID;
    }

    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public ArrayList<TracRecord> getBankBook() {        //物件陣列需要getter&setter?
        return bankBook;
    }

    public void setBankBook (ArrayList<TracRecord> BankBook) {
        this.bankBook = bankBook;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }
    


    // 封裝帳戶欄位：account name/account ID/balance
//    public Account(String accountID,String customerName, ArrayList<TracRecord> bankBook) {
//        this.accountID = accountID;
//        this.customerName = customerName;
//        this.bankBook = bankBook;
//        
//    }
    

    //Comparable
    
    public int compareTo(Account acc){
        int result = this.customerName.compareTo(acc.getCustomerName());
        if (result > 0) {
             
            System.out.println("the element is after " + this.getCustomerName());
            return 1;
        }else if (result < 0){
            
            System.out.println("the element is before " + this.getCustomerName());
            return -1; 
        }else {return 0;}
    }
    
    
    //print
    public void printSheets (){
        
        
        
        System.out.println("Customer Name : "+ customerName);
        System.out.println("-----------------------------------------------------------------------");
        
        System.out.println("Account ID : "+ accountID);
        System.out.println("-----------------------------------------------------------------------");
        
        
        
        if(bankBook.size() > 1){
        int i;
        for(i=1;i<bankBook.size();i++){
        
        Date date = bankBook.get(i).getDate();     
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
        String nationMoney = bankBook.get(i).getNationMoney();
        double paidIn = bankBook.get(i).getPaidIn();
        double paidOut = bankBook.get(i).getPaidOut();
        double balance = bankBook.get(i).getBalance();
        String discription = bankBook.get(i).getDiscription();
        
        System.out.println("Date           NationMoney       Paid_in     Paid_out       Balance           Discription");
        
        if (paidIn > 0 ){
        
            System.out.println(ft.format(date) +"       "+ nationMoney +"            "+ paidIn + "                       "+
                balance +"       "+ discription);
            System.out.println("-----------------------------------------------------------------------");
        }   else {
            System.out.println(ft.format(date) +"       "+ nationMoney +"                       " + paidOut + "        "+
                balance +"       "+ discription);
            System.out.println("-----------------------------------------------------------------------");
        
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
              System.out.println(ft.format(date) +"       "+ nationMoney +"            "+ paidIn + "                       "+
                balance +"       "+ discription);
              System.out.println("-----------------------------------------------------------------------");
        
                }
        
        }
     
        
        
    }

