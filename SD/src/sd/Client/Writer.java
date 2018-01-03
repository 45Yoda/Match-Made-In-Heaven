/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.io.BufferedReader;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author yoda45
 */
public class Writer extends Thread {
    private BufferedReader lerSocket;
    private Menu menu;
    private ReentrantLock lock; 
    private Condition cond;
    
    public Writer(BufferedReader socket, Menu menu, ReentrantLock l, Condition c){
        this.lerSocket = socket;
        this.menu = menu;
        this.lock = l;
        this.cond = c;
    }
    
   
    public void run(){
        String inp;
        
        try{       
                while((inp = lerSocket.readLine()) != null){
                    if(inp.equals("Logged in!")){
                        menu.setOp(1);

                    }
                    if(inp.equals("Registo efectuado com sucesso")){
                        menu.setOp(0);

                    }
                    if(inp.equals("Jogo encontrado")){
                        menu.setOp(2);
                    }
                    if(inp.equals("Terminou sessão") || inp.equals("Username inexistente") || inp.equals("Password incorrecta")){
                        menu.setOp(0);

                    }
                }
                System.out.println("\n"+inp+"\n");
    
        }catch(Exception e){
                System.out.println(e.getMessage());
        }
    
    
    }
    
}
