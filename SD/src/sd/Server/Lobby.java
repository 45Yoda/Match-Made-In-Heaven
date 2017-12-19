package sd.Server;
import java.util.concurrent.ThreadLocalRandom;
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
        public Equipa getEquipaA() {return this.equipaA;}
        public Equipa getEquipaB() {return this.equipaB;}
        
	public synchronized void espera(User user) throws InterruptedException {
               if ( user.getRank()!=-1 && this.rank!=user.getRank()) rankAdj=user.getRank();
               jog++;
               while(jog<tam) {
                    wait();
                    }
               notifyAll();
               System.out.println("user lobby: "+user.getUsername());
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
                {equipaB.insere(user);user.setEquipa(1);}
		else if (equipaB.getJog()==5)
                    {equipaA.insere(user);user.setEquipa(0);}
		else if (equipaA.getMRank()>equipaB.getMRank()) {
				if (user.getRank()>=equipaA.getMRank())
                                    {equipaB.insere(user);user.setEquipa(1);}
				else {equipaA.insere(user);user.setEquipa(0);}
		} else {
			if (user.getRank()>=equipaA.getMRank())
                            {equipaA.insere(user);user.setEquipa(0);}
				else {equipaB.insere(user);user.setEquipa(1);}
		}
                System.out.println("eheh");
        }
        
        public void jogar(User user) {
            int pont = ThreadLocalRandom.current().nextInt(0,10+1);
            if (user.getEquipa()==0)
                equipaA.score(pont);
            else equipaB.score(pont);
            }
        
        public void atualizaRes(User user) {
            if (user.getEquipa()==0) {
                if (equipaA.getPontuacao()>equipaB.getPontuacao()){
                    user.registaJogo(1);
                }
                else user.registaJogo(0);
            }
        }
        }
