
package sd.Client;

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
        
        String input = null;
        Socket cli = null;
        ReentrantLock lock = new ReentrantLock();
        Condition cond = lock.newCondition();	
        
        try{
            cli = new Socket("localhost", port);
            
            BufferedReader lerSocket = new BufferedReader(new InputStreamReader(cli.getInputStream()));
            //Reader reader = new Reader(cli, lock , cond );
            Menu2 menu = new Menu2();
            Reader2 rd2 = new Reader2(cli,menu,lock,cond);
            Writer w = new Writer(lerSocket,menu,lock,cond);
            
            //Stub stub = new Stub( cli, lock , cond );

            rd2.start();
            w.start();
            //stub.start(); 
                
            rd2.join();
            w.join();
            
            //stub.join();
            
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
