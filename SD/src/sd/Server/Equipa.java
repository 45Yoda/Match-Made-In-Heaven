package sd.Server;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Equipa {
	private int ranks[] = null;
        private int jog;
        private Lock lock;
        
        public Equipa(int ranks[],int jog){
            this.ranks = ranks;
            this.jog = jog;
        }
        
        public Equipa() {
            this.ranks = new int[5];
            this.jog = 0;
            this.lock= new ReentrantLock();
        }
        
        public int getJog() {return this.jog;}
	
        public synchronized void insere(User user) throws InterruptedException{
            ranks[jog]=user.getRank();
            jog++;
            System.out.println("nr jogadores equipa: "+jog);
            while(jog<5) {
                System.out.println("vou esperar");
                wait();
            }
             notifyAll();
             System.out.println("oh pa mim a escolher herois");
             //escolhaHeroi();
	}

    //devolve a media de ranks da equipa
	public int getMRank() {
            lock.lock();
            try{
                ranks = new int[5];
		int soma=0;
		int jog=0;
		for(int i=0;i<this.jog;i++)
			if (ranks[i]!=-1) {
			soma+=ranks[i];
			jog++;
			}
                if (jog==0) return 0;
		return soma/jog;
            }
            finally{lock.unlock();}
        }
}