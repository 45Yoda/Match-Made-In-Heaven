package Server;

import java.net.Socket;
import java.io.IOException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;


public class User implements Comparable<User> {
    private String username;
    private String password;
    private Socket session;
    private int jogos;
    private int win;
    private int rank;
    private int equipa;
    private Lock lock;
    private Heroi heroi;
    private int lobby;
    
    public User(String username, String password,int rank){
        this.username = username;
        this.password = password;
        this.jogos=0;
        this.win=0;
        this.rank=rank;
        this.lock= new ReentrantLock();
        this.equipa=-1;
        this.heroi=null;
        this.lobby=-1;
    }
    
    public void reset() {
        this.equipa=-1;
        this.heroi.libertar();
        this.heroi=null;
        this.lobby=-1;
    }

    public void regUser(String username,String password){
        this.username = username;
        this.password = password;
        this.jogos=0;
        this.win=0;
        this.rank=-1;
        this.equipa=-1;
        this.heroi=null;
        this.lobby=-1;
    }
    
    public Heroi getHeroi() {return this.heroi;}
    public void setHeroi(Heroi h) {this.heroi=h;}
    public int compareTo(User u){
        return username.compareTo(u.username);
    }

    public boolean authenticate(String password){
        return this.password.equals(password);
    }

    public void setSession(Socket sock) throws IOException{
        if(session != null && !session.isClosed())
            session.close();

        session = sock;
    }

    public int getRank() {return this.rank;}
    public String getUsername() {return this.username;}
    public int getEquipa() {return equipa;}
    public int getJogos() {return jogos;}
    public int getWin() {return win;}
    public int getLobby() {return this.lobby;}

    public void setLobby(int l) {this.lobby=l;}
    public void setEquipa(int e) {
        this.equipa=e;
    }
    public void registaJogo(int res) {
        this.jogos++;
        if (res==1) this.win++;
        if (this.jogos>=10) this.rank = ((this.win*10/this.jogos)-1);
    }

    public String toString(){
        return username;
    }

    public boolean equals(Object o){
        if(o==this)
            return true;

        if(o == null || (this.getClass() != o.getClass()))
            return false;

        User u = (User) o;
        return username.equals(u.username);
    }
}
