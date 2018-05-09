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
public class DAOException extends Exception {
    
    public DAOException(Throwable cause){
        super(cause);
    }
    public DAOException(String message,Throwable cause){
        super(message,cause);
    }
}
