/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bank_Test;

import java.util.*;

/**
 *
 * @author hung_yilai
 */
public class AccountDAOMemoryImp implements AccountDAO{

    
    
    
    protected  static ArrayList<Account> CustomerArray = new ArrayList<>();
    
    @Override
    public void Add(Account acc){
        CustomerArray.add(Integer.parseInt(acc.getAccountID()), acc);
    };
    
    @Override
    public void Update(Account acc){
        CustomerArray.add(Integer.parseInt(acc.getAccountID()), acc);
    };
    
    @Override
    public void Delete(String AccountID){
        CustomerArray.remove(Integer.parseInt(AccountID));
        
    };
    
    @Override
    public Account FindById(String AccountID) throws DAOException{
        try {
                return CustomerArray.get(Integer.parseInt(AccountID));
                } catch (IndexOutOfBoundsException e) {
                
                throw new DAOException("Error finding account in DAO", e);
                
                }
        
                 
//        return CustomerArray.get(Integer.parseInt(AccountID));
       
    };
    
    @Override
    public ArrayList<Account> getAllAccounts(){
        ArrayList<Account> accs = new ArrayList<Account>();
        for (Account a : CustomerArray) {
            if (a != null) {
                accs.add(a);
} }
           return accs; 
//        return accs.toArray(new Account[0]);
    };
}
