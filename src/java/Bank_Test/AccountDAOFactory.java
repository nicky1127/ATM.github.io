/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bank_Test;



/**
 *
 * @author hung_yilai
 */
public class AccountDAOFactory {
    
    public AccountDAO createAccountDAO(){
        return new AccountDAOMemoryImp();
    }
    
}
