/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sd.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import sd.Server.Matching;
import sd.Server.User;

/**
 *
 * @author yoda45
 */
public class ServerReader extends Thread{
    private BufferedReader readSocket;
    private Matching m;
    private User u;
    private Socket cliSocket;

    
    ServerReader(Matching m,Socket cliSocket) throws IOException{
        this.m = m;
        this.cliSocket = cliSocket;
        this.readSocket = new BufferedReader(new InputStreamReader(cliSocket.getInputStream()));
        this.u = null;
    }
    
    public void run(){
        try{
            String inp;
            while((inp = readSocket.readLine())!=null){
                if(inp.equals("Iniciar Sessao")){
                    String user, pass;
                    user = readSocket.readLine();
                    pass = readSocket.readLine();
                    
                   
                    this.u = m.login(user, pass);
                   
                }
                
                else if(inp.equals("Registar")){
                    String user,pass;
                    user = readSocket.readLine();
                    pass = readSocket.readLine();
                    
                    m.SignUp(user, pass);
                }
                
                else if(inp.equals("Terminar Sessao")){
                    this.u = null;
                    System.out.println("Terminou Sessao");
                }
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    
    }
      
}
