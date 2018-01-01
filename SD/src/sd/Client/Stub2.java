
package sd.Client;

import java.io.BufferedReader;
import java.net.Socket;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.NoSuchElementException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import sd.Client.Menu;

public class Stub extends Thread{
    private Socket cliSocket;
    private String input;
    private PrintWriter out;
    //rivate MatchMIH menu;
    private ReentrantLock lock;
    private Condition c;
    
    Stub(Socket cliSocket, ReentrantLock l, Condition c) {
	try{
            this.cliSocket = cliSocket;
            this.input = null;
            this.out = new PrintWriter(cliSocket.getOutputStream(), true);
         //   this.menu = new MatchMIH();
            this.lock = l;
            this.c = c;
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    public void run() {
                
                
                try{
//                        menu.inicia();
                        System.out.println("SAI");
                    }
                    /*menu.showMenu();
            
            while((op= menu.readInt(inputGeter))!= -1){
                if(menu.getOp() == 0){
                
                   
                    if(op == 3) break;
                }else if(menu.getOp() == 0){
                    if(op == 1) play();
                    //if(op == 2) selectHero();
                    if(op == 3) break;
                }
                if(op >= 1 && op <= 3){
                    space();
                    menu.showMenu();
                }else{
                    System.out.println("Opção inválida.\n");
                }
                
            }*/
            
        
        catch(Exception e){
            System.out.println(e.getMessage());
	}

    }

// Adaptar tudo que está para baixo
    public void signup(String username,String password) throws InterruptedException {
	String query = String.join(" ", "REGISTAR", username, password);

	out.println(query);
        
    }
    

    public void login(String username,String password) {
                
	String query = String.join(" ", "LOGIN", username, password);

	out.println(query);
          try{
            this.lock.lock();
	c.await();
        }catch(InterruptedException e){
            e.getMessage();
        }finally{
          
        this.lock.unlock();
      
        }
    }

    
    private void play(){
        String query = "JOGAR";
        out.println(query);
        
    }
    
   // private void selectHero(){}
    
    private void space(){
		for(int i = 0;i<40;i++)
			System.out.println();
	}
    
    public void setInput(String inp){
        this.input = inp;
    }

    private String getInput() {
       return this.input;
    }
    
}
