/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bank_Test;

import Bank_test.*;

/**
 *
 * @author hung_yilai
 */
public interface ATM_Machine {
    
    public void greeting();
    //交易起始
    public void tranStart(Account acc);
    //交易過場
    public void transit(Account acc);
    //提款
    public void withdraw (Account acc);
    //存款
    public void deposit (Account acc);
    //轉帳
    public abstract void transfer (Account acc);
 
}
