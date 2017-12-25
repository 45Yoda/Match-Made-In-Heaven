/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sd.Server;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
/**
 *
 * @author stifler55
 */
public class Heroi {
    private String nome;
    private boolean usage;
    private Lock lock;
    

    
    public Heroi() {
        this.nome="";
        this.usage=false;
        this.lock= new ReentrantLock();
    }
    
    public Heroi(String n) {
        this.nome=n;
        this.usage=false;
    }
    
    public void selecionar(User user) {
        lock.lock();
        try{
            usage=true;
            user.setHeroi(this);
        } 
        finally {lock.unlock();}
    }
    
    public void libertar() {
        lock.lock();
        try{
            usage=false;
        }
        finally {lock.unlock();}
    }
    
    public boolean ocupado() {
        lock.lock();
        try{
            return usage;
        }
        finally {lock.unlock();}
    }
}
