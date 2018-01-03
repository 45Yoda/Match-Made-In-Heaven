
package Client;

import java.io.BufferedReader;
import java.net.Socket;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;


public class HeavenMatch {
    private static final int port = 5000;
    
    public static void main(String[] args) throws IOException, InterruptedException {

        Socket cli = null;
        ReentrantLock lock = new ReentrantLock();
        Condition cond = lock.newCondition();	
        
        try{
            cli = new Socket("localhost", port);
            
            BufferedReader lerSocket = new BufferedReader(new InputStreamReader(cli.getInputStream()));
            Menu menu = new Menu();
            Reader rd = new Reader(cli,menu,lock,cond);
            Writer w = new Writer(cli,lerSocket,menu,lock,cond);
            
            rd.start();
            w.start();

                
            rd.join();
            w.join();
            
            lerSocket.close();
            
            System.out.println("Até uma próxima!\n");
            cli.close();
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
        catch(InterruptedException e){
            System.out.println(e.getMessage());
        }
    }
   
}
