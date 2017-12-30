package sd.Server;

import java.net.Socket;
import java.io.IOException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;


public class User extends Thread implements Comparable<User> {
    private String username;
    private String password;
    private Socket session;
    private int jogos;
    private int win;
    private int rank;
    private Lobby[] lobbys; //(!) sair daqui
    private int equipa;
    private Lock lock;
    private NotificationBuffer buffer;
    private Heroi heroi;
    
    public User(String username, String password,int rank,Lobby[] lobbys ){
        this.username = username;
        this.password = password;
        this.jogos=0;
        this.win=0;
        this.rank=rank;
        this.lobbys=lobbys; 
        this.lock= new ReentrantLock();
        this.equipa=-1;
        buffer = new NotificationBuffer();
        this.heroi=null;
    }
    
    public void reset() {
        this.equipa=-1;
        //this.heroi.libertar(); //nao há herois
        this.heroi=null;
    }
    /*
    public void User1(String username,String password,int rank,Lobby[] lobbys) {
        this.username=username;
        this.password=password;
        this.rank=rank;
        this.lobbys=lobbys;
        this.lock = new ReentrantLock();
        this.equipa=-1;
        buffer = new NotificationBuffer();
    }*/
            
    public void regUser(String username,String password){
        this.username = username;
        this.password = password;
        this.jogos=0;
        this.win=0;
        this.rank=-1;
        this.equipa=-1;
        buffer = new NotificationBuffer();
        this.heroi=null;
    }
    
    public Heroi getHeroi() {return this.heroi;}
    public NotificationBuffer getBuffer() {return this.buffer;}
    public void setHeroi(Heroi h) {this.heroi=h;}
    public int compareTo(User u){
        return username.compareTo(u.username);
    }

    public boolean authenticate(String password){
        return this.password.equals(password);
    }
    
    public void notificate(String message) {
		buffer.write(message);
	}
    
    public String readNotification() throws InterruptedException {
		return buffer.read();
	}
    
    public void resend() {
		buffer.reset();
	}
    
    public void acknowledge(int amount) {
		buffer.acknowledge(amount);
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
    
    //************************************mudar de classe**********************
    //distribuir um user de cada vez para nao haver mudanças de rankAdj
	//enquanto outra thread está a consultar
	public void distribuirUser(User user) throws InterruptedException{
		lock.lock();
		try {
			int rank = user.getRank();
                        int best = bestLobby(rank);
                        while(best==-1){
                            while(lobbys[rank].getJog()!=0){
                                lobbys[rank].notFull.await();
                            }
                            best = bestLobby(rank);
                        }
                        lobbys[best].gereUser(user);
                }
		finally {lock.unlock();}
	}


	//devolve o lobby mais cheio onde o user pode jogar
	public int bestLobby(int rank) {
		lock.lock();
		try {
		if (rank==-1) return ocup(0,4);
		else {
			int min=-1,max=-1;
			if (rank==0) {
				if (lobbys[1].getRankAdj()==0) {
					min=rank;
					max=rank+1;
					return ocup(0,1);
				}
				else return 0;
			}
			else if (rank==9) {
				if (lobbys[8].getRankAdj()==9) {
					min=rank-1;
					max=rank;
					return ocup(8,9);
				}
				else return 9;
			}
			else {
				if (lobbys[rank-1].getRankAdj()==rank) min=rank-1;
					else min=rank;
				if (lobbys[rank+1].getRankAdj()==rank) min=rank+1;
					else max=rank;
				if (min==max) return rank;
				else return ocup(min,max);
				}
		}
	}
		finally {lock.unlock();}
	}
        
	//devolve o lobby mais cheio entre dois pontos
	public int ocup(int min,int max) {
		int m=-1;
		for (int i = min;i<=max;i++)
			if (lobbys[i].getJog()>m && lobbys[i].getJog()!=10) m=i;
		          System.out.println(m);
                return m;
	}
//*******************************************************************+
        public void run() {
            try {distribuirUser(this);}
            catch(Exception e) {System.out.println("thread "+ e.getMessage() +" tetos");}
        }
}
