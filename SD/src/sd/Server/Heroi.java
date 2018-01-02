/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

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
        this.lock= new ReentrantLock();
    }
    
    public String getNome() {return this.nome;}
    public boolean getUse() {return this.usage;}
    
    public int selecionar(User user) {
        lock.lock();
        try{
            int h=0;
            if (usage==false) {
            usage=true;
            user.setHeroi(this);
            h=1;
            }
            return h;
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
