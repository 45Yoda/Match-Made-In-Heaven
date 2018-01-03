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
public class Menu {
    private String[] options;
    private int op;
    
    
    public void showMenu(){
        
        switch(op){
            
            case 0: carregaMenu(0);
                        break;
            case 1: carregaMenu(1);
                        break;
            case 2: carregaMenu(2);
                        break;
        
        }    
        
        
     
        System.out.println("           * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *  ");
        
    }
    
    private void carregaMenu(int opt){
        String [] r = null;
        
        if(opt == 0) {
            String[] start = {
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
                    "           * *                                                                                       * *",
                    "           * *                    1 - INICIAR SESSÃO                                                 * *",
                    "           * *                    2 - REGISTAR                                                       * *",
                    "           * *                                                                                       * *",
                    "           * *                    0 - SAIR                                                           * *",
                    "           * *                                                                                       * *"
            };
            r = start;
        }
        else if(opt == 1) {
            String[] loggedMenu = {
                    "           * *                                                                                       * *  ",
                    "           * *                    1 - PLAY!                                                          * *  ",
                    "           * *                    2 - STATS                                                          * *  ",
                    "           * *                                                                                       * *  ",
                    "           * *                    0 - SAIR                                                           * *  ",
                    "           * *                                                                                       * *  ",
            };
            r = loggedMenu;
         }
        else if(opt == 2) {
            String[] menuHerois = {
                    "           * 1-Andormeda                       11-Chamaeleon                      21-Hydra             *  ",
                    "           * 2-Apus                            12-Cancer                          22-Lemon             *  ",
                    "           * 3-Argo                            13-Coma                            23-Lupus             *  ",
                    "           * 4-Aries                           14-Crater                          24-Musca             *  ",
                    "           * 5-Bootes                          15-Crux                            25-Orion             *  ",
                    "           * 6-Camelopardalis                  16-Corvus                          26-Pavo              *  ",
                    "           * 7-Carina                          17-Delphinus                       27-Pegasus           *  ",
                    "           * 8-Cassiopeia                      18-Draco                           28-Pictor            *  ",
                    "           * 9-Centauros                       19-Fornax                          29-Reticulum         *  ",
                    "           * 10-Cetus                          20-Hercules                        30-Scrutum           *  ",
            };
            r = menuHerois;
        }


        System.out.println("\n           * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
        System.out.println("           * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
        
        
         for(int i = 0;i<r.length; i++){
            System.out.println(r[i]);
        }
        
         System.out.println("           * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *  ");
        
    }


    public int getOp(){
        return this.op;
    }
    
    public void setOp(int n){
        this.op = n;
    }
    
}


