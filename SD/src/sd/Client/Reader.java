package sd.Client;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import sd.Client.Menu;

public class Reader extends Thread{
    private BufferedReader socketReader;
    private Menu menu;
    private ReentrantLock lock; 
    private Condition cond;
    
    public Reader(Socket CliSocket, ReentrantLock l, Condition c) throws IOException{
        this.socketReader = new BufferedReader(new InputStreamReader(CliSocket.getInputStream()));;
        this.menu = new Menu();
        this.lock = l;
        this.cond = c;
    }
    
    public void run(){
        String line;
        String[] response;
        try{
            while((line = socketReader.readLine())!= null ){
                response = line.split(" "); 
                if(line.equals("Sessão iniciada com sucesso")){
                    System.out.println(line);
                    menu.setOp(1);
                    this.lock.lock();
                    cond.signal();
                    this.lock.unlock();
                }else if(line.equals("Registo efectuado com sucesso")){
                    System.out.println(line);
                    menu.setOp(0);
                    this.lock.lock();
                    cond.signal();
                    this.lock.unlock();
                }else if(line.equals("Jogo Encontrado")){
                    //Para escolher heroi depois de encontrado o jogo é necessário meter cenas aqui
                    System.out.println(line);
                    menu.setOp(0);
                    this.lock.lock();
                    cond.signal();
                    this.lock.unlock();
                }else if(response[0]=="Stats"){
                    System.out.println(line);
                    this.lock.lock();
                    cond.signal();
                    this.lock.unlock();
                }
                
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
