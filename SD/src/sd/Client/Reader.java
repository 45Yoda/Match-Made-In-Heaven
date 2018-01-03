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
                if(menu.getOp()==0) {


                    if (input.equals("1")) {

                        login();
                        this.lock.lock();
                        c.await();
                        this.lock.unlock();

                        input = "1";

                    } else if (input.equals("2")) {
                        signup();

                        input = "2";

                    } else if (input.equals("0")) {
                            return;
                    }

                    if (input.equals("1") || input.equals("2")) {
                        clearScreen();
                        menu.showMenu();

                    } else System.out.println("Opção invalida.");
                }
                 else if(menu.getOp()==1){

                            if(input.equals("1")){
                                String query = String.join("-","PLAY");
                                escreverSocket.println(query);

                                System.out.println("Procurando Jogo");
                                this.lock.lock();
                                c.await();
                                this.lock.unlock();

                                input="1";
                            }
                            else if(input.equals("2")){
                                escreverSocket.println("Stats");
                            }

                            if(input.equals("1") || input.equals("2")){
                                clearScreen();
                                menu.showMenu();
                            }
              }  
                else if (menu.getOp()==2) {


                     if(input.equals("1")){
                            input="1";
                        }
                        if(input.equals("1") || input.equals("2")){
                            clearScreen();
                            menu.showMenu();
                        }

                    int h = Integer.parseInt(input);
                    if (h>0 && h<31) {
                        escreverSocket.println("HERO-"+h);
                        lock.lock();
                        c.await();
                        lock.unlock();
                    }
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
        } catch (IOException e) {
            e.printStackTrace();
        }

         System.out.println("* *                                                                                       * *");
         System.out.println("* *       Introduza password:                                                             * *");           
         
         try{
             password = lerTeclado.readLine();
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