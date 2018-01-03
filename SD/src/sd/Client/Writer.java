/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author yoda45
 */
public class Writer extends Thread {
    private BufferedReader lerSocket;
    private PrintWriter escreverSocket;
    private Menu menu;
    private ReentrantLock lock; 
    private Condition cond;
    
    public Writer(Socket cli,BufferedReader socket, Menu menu, ReentrantLock l, Condition c) throws IOException {
        this.lerSocket = socket;
        this.menu = menu;
        this.lock = l;
        this.cond = c;
        this.escreverSocket = new PrintWriter(cli.getOutputStream(),true);

    }
    
   
    public void run(){
        String inp;
        
        try{       
                while((inp = lerSocket.readLine()) != null){
                    //System.out.println(inp);
                    String[] q = inp.split("-");
                    if(inp.equals("Logged in!")){
                        menu.setOp(1);
                        this.lock.lock();
                        cond.signal();
                        this.lock.unlock();

                    }

                    if(inp.equals("Registo efectuado com sucesso")){
                        menu.setOp(0);
                    }

                    if(inp.equals("Jogo Encontrado")){
                        menu.setOp(2);
                        this.lock.lock();
                        cond.signal();
                        this.lock.unlock();
                    }

                    if(inp.equals("Terminou sessão") || inp.equals("Username inexistente") || inp.equals("Password incorrecta")){
                        menu.setOp(0);
                    }
                    if(inp.equals("Heroi Selecionado")){
                        System.out.println(inp);
                        escreverSocket.println("CONSTITUICAO");
                    }
                    if (inp.equals("Heroi ja escolhido")){
                        System.out.println(inp);
                        sleep(3);
                        menu.setOp(2);
                        this.lock.lock();
                        cond.signal();
                        this.lock.unlock();
                    }
                    if(q[0].equals("Constituicao Equipa")){
                        for(int i=0;i<q.length;i++){
                            System.out.println(q[i]);
                        }
                        escreverSocket.println("REALPLAY");
                    }
                    if(q[0].equals("Resultado do Jogo")){
                        for(int i=0;i<q.length;i++){
                            System.out.println(q[i]);
                        }
                        sleep(300);
                        menu.setOp(1);
                        lock.lock();
                        cond.signal();
                        lock.unlock();
                    }
                    if(inp.equals("Heroi já escolhido")){
                        System.out.println(inp);

                    }

                    if(q[0].equals("Stats")){
                        System.out.println(q[1]);
                    }

                }
                System.out.println("\n"+inp+"\n");
    
        }catch(Exception e){
                System.out.println(e.getMessage());
        }
    
    
    }
    
}
