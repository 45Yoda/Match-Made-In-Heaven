
package sd.Client;

import java.net.Socket;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;


public class HeavenMatch {
    private static final int port = 5000;
    
    public static void main(String[] args) throws IOException, InterruptedException {
	ReentrantLock lock = new ReentrantLock();
        Condition cond = lock.newCondition();	
        try{
            Socket cli = new Socket("localhost", port);
            //Reader reader = new Reader(cli, lock , cond );
            Stub stub = new Stub( cli, lock , cond );

            //reader.start();
            stub.start(); 
                
            //reader.join();
            stub.join();
            
            System.out.println("Até uma próxima!\n");
            cli.close();
	}
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
   
}
