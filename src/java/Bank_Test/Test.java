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
import java.io.*;
public class Test {
  public static void main(String args[]) throws IOException
  {
    char c;
    // 使用 System.in 创建 BufferedReader 
    BufferedReader br = new BufferedReader(new 
                       InputStreamReader(System.in));
    System.out.println("输入字符, 按下 'q' 键退出。");
    // 读取字符
    do {
       c = (char) br.read();
       System.out.println(c);
    } while(c != 'q');
    try{
        File d = new File("src/java/Bank_Test/abc.text");
    OutputStream f = new FileOutputStream(d);
    }catch(Exception e){
        e.getStackTrace();
    }
    
  }
}
