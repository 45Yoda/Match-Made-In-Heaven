package sd.Server;
import java.util.concurrent.locks.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author diogo
 */
public class Tester {
    

    public static void main(String[] args) throws InterruptedException {
    
    Lobby[] lobbys = new Lobby[10];        

        for(int i=0;i<10;i++) 
            lobbys[i]= new Lobby(i);

        Thread[] threads = new Thread[10];
        for(int i=0;i<10;i++) {
            threads[i]=new User(Integer.toString(i),Integer.toString(i),4,lobbys);
            threads[i].start();
        }
        
        for(int i = 0; i < 10; i++)
            threads[i].join();
    }
}

