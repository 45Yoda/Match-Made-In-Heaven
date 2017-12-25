package sd.Server;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.*;


public class Lobby {
	private static int tam=10;
	private int jog;
	private int rankAdj;
	private int rank;
	private Equipa equipaA;
	private Equipa equipaB;
        private int ready;
        Lock lock;
        Condition notFull;

	public Lobby(int rank) {
		this.jog = 0;
		this.rank=rank;
		this.rankAdj=-1;
                this.ready=0;
		equipaA= new Equipa();
		equipaB = new Equipa();
                lock = new ReentrantLock();
                notFull = lock.newCondition();
	}


	//devolve numero de jogadores
	public int getJog() {return this.jog;}
	public int getRank() {return this.rank;}
	public int getRankAdj() {return this.rankAdj;}
        public Equipa getEquipaA() {return this.equipaA;}
        public Equipa getEquipaB() {return this.equipaB;}
        
        
        public void gereUser(User user) throws InterruptedException{
            espera(user);
            distribuiEquipa(user);
            synchronized (this) { //espera que check todos os users
                while(equipaA.getJog()!=5 && equipaB.getJog()!=5) 
                    wait();
                //if(user.getEquipa()==0) equipaA.escolhaHeroi(user);
                //else equipaB.escolhaHeroi(user);
                if (user.getHeroi()==null) ready++;
                //System.out.println(ready);
                jog++;
                while(jog<20)
                    wait();
                notifyAll();
            }
            if (ready==10) {
            //apresentar escolhas de user
            jogar(user);
            synchronized (this) { //espera que check todos os users
                ready--;
                while(ready!=0)
                    wait();
                notifyAll();
            }
            //apresentar resultados
            atualizaRes(user);
            resetLobby(user);
            } else {
                //dizer que jogo interrompido
                resetLobby(user);
            }
        }
        
	public synchronized void espera(User user) throws InterruptedException {
               if ( user.getRank()!=-1 && this.rank!=user.getRank()) rankAdj=user.getRank();
               jog++;
               while(jog<tam) {
                    wait();
                    }
               notifyAll();
        }
        
        public synchronized void resetLobby(User user) throws InterruptedException{
            user.reset();
            this.jog--;
            if (jog==10) {
            this.jog=0;
            this.rankAdj=-1;
            this.equipaA.reset();
            this.equipaB.reset();
            notFull.signal();
            this.ready=0;
            }
        }
        
        public synchronized void distribuiEquipa(User user) throws InterruptedException {
            if (equipaA.getJog()==5) {
                    user.setEquipa(1);equipaB.insere(user);
                }else if (equipaB.getJog()==5) {
                    user.setEquipa(0);equipaA.insere(user);
                    }else if (equipaA.getMRank()>equipaB.getMRank()) {
				if (user.getRank()>=equipaA.getMRank()) {
                                    user.setEquipa(1);equipaB.insere(user);
                                }else {user.setEquipa(0);equipaA.insere(user);}
                            } else {
                                    if (user.getRank()>=equipaA.getMRank()) {
                                        user.setEquipa(0);equipaA.insere(user);
                                    }else {user.setEquipa(1);equipaB.insere(user);}
		}
        }
        
        public synchronized void jogar(User user) throws InterruptedException{
            
            int pont = ThreadLocalRandom.current().nextInt(0,10+1);
            if (user.getEquipa()==0)
                equipaA.score(pont);
            else equipaB.score(pont);
            }
        
        public synchronized void atualizaRes(User user) throws InterruptedException{
            
            if (user.getEquipa()==0) {
                if (equipaA.getPontuacao()>equipaB.getPontuacao()) user.registaJogo(1);
                else user.registaJogo(0);
            }
            else {if (equipaA.getPontuacao()>equipaB.getPontuacao()) user.registaJogo(0);
                else user.registaJogo(1);
                
            }
            
        }
        
        
        }
