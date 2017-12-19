package sd.Server;

import java.net.Socket;
import java.io.IOException;

public class User implements Comparable<User>{
    private final String username;
    private final String password;
    private Socket session;
    private int jogos;
    private int win;
    private int rank;

    User(String username,String password){
        this.username = username;
        this.password = password;
        this.jogos=0;
        this.win=0;
        this.rank=-1;
    }

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

    public void atualizaRank(int res) {
        this.jogos++;
        if (res==1) this.win++;
        if (this.jogos>=10) this.rank = (this.win/this.rank)-1;
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
