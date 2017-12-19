package sd.Server;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Lobby {
	private static int tam=10;
	private int jog;
	private int rankAdj;
	private int rank;
	private Equipa equipaA;
	private Equipa equipaB;
        private Lock lock;

	public Lobby(int rank) {
		this.jog = 0;
		this.rank=rank;
		this.rankAdj=-1;
		equipaA= new Equipa();
		equipaB = new Equipa();
                lock = new ReentrantLock();
	}


	//devolve numero de jogadores
	public int getJog() {return this.jog;}
	public int getRank() {return this.rank;}
	public int getRankAdj() {return this.rankAdj;}
        
	public synchronized void espera(User user) throws InterruptedException {
              
		if ( user.getRank()!=-1 && this.rank!=user.getRank()) rankAdj=user.getRank();
		jog++;
		while(jog<tam) {
			wait();
                }
		notifyAll();
                distribuiEquipa(user);
	}
        
        public void resetLobby() {
            this.jog=0;
            this.rankAdj=-1;
            this.equipaA=new Equipa();
            this.equipaB=new Equipa();
        }
        
        public void distribuiEquipa(User user) throws InterruptedException {
            System.out.println("user: " + user.getUsername());
            if (equipaA.getJog()==5)
			equipaB.insere(user);
		else if (equipaB.getJog()==5)
			equipaA.insere(user);
		else if (equipaA.getMRank()>equipaB.getMRank()) {
				if (user.getRank()>=equipaA.getMRank())
					equipaB.insere(user);
				else equipaA.insere(user);
		} else {
			if (user.getRank()>=equipaA.getMRank())
					equipaA.insere(user);
				else equipaB.insere(user);
		}
                System.out.println("eheh");
        }
}