/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author yoda45
 */
public class Menu2 {
    private String[] options;
    private int op;
    
    
    public void showMenu(){
        
        switch(op){
            
            case 0: carregaMenu(0);
                        break;
            case 1: carregaMenu(1);
                        break;
        
        }    
        
        
     
        System.out.println("           * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *  ");
        
    }
    
    private void carregaMenu(int opt){
        String [] r = null;
        
        switch(opt){
        
            case 0: String [] start = {
                    "           * *                                                                                       * * ",
                    "           * *             |  \\/  | / _ \\_   _/ __| |_| |                                            * * ",
                    "           * *             | |\\/| |/ /_\\ \\| || (__|  _  |                                            * * ",
                    "           * *             |_|  |_/_/   \\_|_| \\___|_| |_|                                            * * ",
                    "           * *                                                                                       * *  ",
                    "           * *             |  \\/  | /_\\ |   \\| __| |_ _| \\| | | || | __| /_\\ \\ / / __| \\| |          * *",
                    "           * *             | |\\/| |/ _ \\| |) | _|   | || .` | | __ | _| / _ \\ V /| _|| .` |          * *",         
                    "           * *             |_|  |_/_/ \\_\\___/|___| |___|_|\\_| |_||_|___/_/ \\_\\_/ |___|_|\\_|          * *",
                    "           * *                                                                                       * * ",
                    "           * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *",
                    "           * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *",
                    "           * *                    1 - INICIAR SESSÃO                                                 * *",
                    "           * *                    2 - REGISTAR                                                       * *",
                    "           * *                                                                                       * *",              
                    "           * *                    0 - SAIR                                                           * *",
                    "           * *                                                                                       * *"
           };
                r = start;
                break;
            
            case 1: String[] login ={
                //TODO jogar,stats, sair
            };
                        r = login;
                        break;
                
                
        }
        
        System.out.println("\n           * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
        System.out.println("           * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
        
        
         for(int i = 0;i<r.length; i++){
            System.out.println(r[i]);
        }
        
         System.out.println("           * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *  ");
        
    }


    //TODO ver isto:
    public void menuHeroi() {
        System.out.println("           * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *  ");
        System.out.println("           * 1-Andormeda                       11-Chamaeleon                      21-Hydra             *  ");
        System.out.println("           * 2-Apus                            12-Cancer                          22-Lemon             *  ");
        System.out.println("           * 3-Argo                            13-Coma                            23-Lupus             *  ");
        System.out.println("           * 4-Aries                           14-Crater                          24-Musca             *  ");
        System.out.println("           * 5-Bootes                          15-Crux                            25-Orion             *  ");
        System.out.println("           * 6-Camelopardalis                  16-Corvus                          26-Pavo              *  ");
        System.out.println("           * 7-Carina                          17-Delphinus                       27-Pegasus           *  ");
        System.out.println("           * 8-Cassiopeia                      18-Draco                           28-Pictor            *  ");
        System.out.println("           * 9-Centauros                       19-Fornax                          29-Reticulum         *  ");
        System.out.println("           * 10-Cetus                          20-Hercules                        30-Scrutum           *  ");
        System.out.println("           * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *  ");
    }
    
    public int getOp(){
        return this.op;
    }
    
    public void setOp(int n){
        this.op = n;
    }
    
}
    
    /*

    public String readString(String msg){
       String r = "";
        
        try{
            System.out.println(msg);
            r = lerTeclado.readLine();
       
        }catch(IOException e){
           System.out.println(e.getMessage());
       }
       return r;
    }
    
    public int readInt(String msg){
        int num= 0;
        try{
            System.out.print(msg + "\n");
            num = Integer.parseInt(lerTeclado.readLine());
        }catch(NumberFormatException e){
            System.out.println("\n> O valor introduzido não é válido!\n");
            num = readInt(msg);
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
        
        return num;
    }
    
  */  

