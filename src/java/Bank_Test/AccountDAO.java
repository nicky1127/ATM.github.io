/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bank_Test;


import java.util.ArrayList;

/**
 *
 * @author hung_yilai
 */
public interface AccountDAO {
    
    public  ArrayList<Account> CustomerArray = new ArrayList<>();
    
    public void Add(Account acc);
    public void Update(Account acc);
    public void Delete(String AccountID);
    public Account FindById (String AccountID) throws DAOException;
    public ArrayList<Account> getAllAccounts();
    
}
