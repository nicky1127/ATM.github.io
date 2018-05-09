/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bank_Test;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author hung_yilai
 */
public class TracRecord {
    
    
    
    private Date date;
    private String nationMoney;
    Type type;
    private double paidIn;
    private double paidOut;
    private double balance;
    private String discription;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getNationMoney() {
        return nationMoney;
    }

    public void setNationMoney(String nationMoney) {
        this.nationMoney = nationMoney;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    

    public double getPaidIn() {
        return paidIn;
    }

    public void setPaidIn(double paidIn) {
        this.paidIn = paidIn;
    }

    public double getPaidOut() {
        return paidOut;
    }

    public void setPaidOut(double paidOut) {
        this.paidOut = paidOut;
    }
    
    

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public TracRecord(Date date, String nationMoney, Type type, double paidIn, 
            double paidOut, double balance, String discription) {
        this.date = date;
        this.nationMoney = nationMoney;
        this.type = type;
        this.paidIn = paidIn;
        this.paidOut = paidOut;
        this.balance = balance;
        this.discription = discription;
    }
    
    
    

    
    
    
}
