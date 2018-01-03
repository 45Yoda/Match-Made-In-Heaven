/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author yoda45
 */
public class Reader extends Thread{
    
    private BufferedReader lerTeclado;
    private PrintWriter escreverSocket;
    private Socket socket;
    private Menu menu;
    private ReentrantLock lock;
    private Condition c;
    
    public Reader(Socket cli, Menu menu, ReentrantLock l, Condition c){
        try{
            this.lerTeclado = new BufferedReader(new InputStreamReader(System.in));
            this.escreverSocket = new PrintWriter(cli.getOutputStream(),true);
            this.socket = cli;
            this.menu = menu;
            this.lock = l;
            this.c = c;
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    
    
    public void run(){
        String input = null;
        
        try{
            menu.showMenu();
            while((input = lerTeclado.readLine()) != null){
                switch(menu.getOp()){
                    case 0:
                        clearScreen();
                        if(input.equals("1")){
                            //escreverSocket.println("LOGIN");
                            login();
                            /*
                            this.lock.lock();
                            c.await();
                            this.lock.unlock();
                            */
                            input = "1";
                        }
                        else if(input.equals("2")){
                            //escreverSocket.println("REGISTAR");
                            signup();

                            input = "2";
                        }
                        
                        else if(input.equals("0")){
                            break;
                        }
                        
                        if(input.equals("1") || input.equals("2")){
                            clearScreen();
                            menu.showMenu();
                        }
                        
                        else System.out.println("Opção invalida.");
                    /*
                    case 1:
                            clearScreen();
                            if(input.equals("1")){
                                escreverSocket.println("Play");

                                input="1";
                            }
                            else if(input.equals("2")){
                                escreverSocket.println("Stats");
                            }

                            if(input.equals("1") || input.equals("2")){
                                clearScreen();
                                menu.showMenu();
                            }
                    */

                    default:
                        break;
              }  
            
            
            }
            
            socket.shutdownOutput();

            
        }catch(Exception e){
           System.out.println(e.getMessage());
        }
    }
    
    public void login(){
        String username="";
        String password="";
        String query;
        System.out.println("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
        System.out.println("* *                                                                                       * *");
        System.out.println("* *       Introduza  username:                                                            * *");


        try {
            username = lerTeclado.readLine();
            //escreverSocket.println(username);

        } catch (IOException e) {
            e.printStackTrace();
        }

         System.out.println("* *                                                                                       * *");
         System.out.println("* *       Introduza password:                                                             * *");           
         
         try{
             password = lerTeclado.readLine();
             //escreverSocket.println(password);
         }catch(Exception e){
             System.out.println(e.getMessage());
         }

         try{
            query = String.join("-","LOGIN",username,password);
            escreverSocket.println(query);
         }catch(Exception e){
             System.out.println(e.getMessage());
         }


         System.out.println("* *                                                                                       * *");
         System.out.println("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
         System.out.println("                  * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *              \n");

    }
    
    public void signup(){
        String username = "";
        String password = "";
        String query = "";
        System.out.println("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
        System.out.println("* *                                                                                       * *");
        System.out.println("* *       Introduza  username:                                                            * *");
         
         try{
            username = lerTeclado.readLine();
            //escreverSocket.println(username);
         }catch(Exception e){
             System.out.println(e.getMessage());
         }
         
         System.out.println("* *                                                                                       * *");
         System.out.println("* *       Introduza password:                                                             * *");           
         
         try{
             password = lerTeclado.readLine();
             //escreverSocket.println(password);
         }catch(Exception e){
             System.out.println(e.getMessage());
         }

        try{
            query = String.join("-","REGISTAR",username,password);
            System.out.println(query);
            escreverSocket.println(query);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
         
         System.out.println("* *                                                                                       * *");
         System.out.println("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
         System.out.println("                  * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *              \n");

    }
    

    private static void clearScreen(){
              for(int i=0;i<=20;i++){
                  System.out.println("\n");
            }
    }


}