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
public enum Type { 
    CASH , DEBITCARD , ONLINE;
    
    public static Type getEnum(String s){
        switch (s) {
            
            case "CASH" :
                return Type.CASH;
               
            case "DEBITCARD" :
                return Type.DEBITCARD;
                
            case "ONLINE":
                return Type.ONLINE;
             
            default:
                return null;
         }
               
        
        
    }



}
