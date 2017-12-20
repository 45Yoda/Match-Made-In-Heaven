package sd.Server;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Equipa {
	private int ranks[] = null;
        private int jog;
        private int pontuacao;
        private Lock lock;
        
        public Equipa(int ranks[],int jog){
            this.ranks = ranks;
            this.jog = jog;
        }
        
        public Equipa() {
            this.ranks = new int[5];
            this.jog = 0;
            this.pontuacao = 0;
            this.lock= new ReentrantLock();
        }
        
        public int getJog() {
            return this.jog;
        }
	
        public void insere(User user) {
                this.ranks[this.jog]=user.getRank();
                this.jog++;
        }
        
        
    //devolve a media de ranks da equipa
	public int getMRank() {
            lock.lock();
            try{
		int soma=0;
		int jogadores=0;
		for(int i=0;i<this.jog;i++) 
			if (ranks[i]!=-1) {
                            soma+=ranks[i];
                            jogadores++;
			}
                if (jogadores==0) return 0;
                else return soma/jogadores;
            }
            finally{lock.unlock();}
        }
        
        public void score(int pont) {
            lock.lock();
            try{
            this.pontuacao+=pont;
            }
            finally{lock.unlock();}
        }
        
        public int getPontuacao() {return this.pontuacao;}
     }
