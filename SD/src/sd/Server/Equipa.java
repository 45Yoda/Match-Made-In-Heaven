package sd.Server;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Equipa {
	private int ranks[] = null;
        private int jog;
        private int pontuacao;
        private Lock lock;
        private List<Heroi> herois;
        
        public Equipa(int ranks[],int jog){
            this.ranks = ranks;
            this.jog = jog;
        }
        
        public Equipa() {
            this.ranks = new int[5];
            this.jog = 0;
            this.pontuacao = 0;
            this.lock= new ReentrantLock();
            //this.herois=parser();
        }
        
        public int getJog() {
            return this.jog;
        }
	
        public void insere(User user) {
            lock.lock();
            try{
                this.ranks[this.jog]=user.getRank();
                this.jog++;
                if (jog==5) notifyAll();
            }finally{lock.unlock();}
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
        
        public int getPontuacao() {
            lock.lock();
            try {
                return this.pontuacao;
            }
            finally{lock.unlock();}
        }
        
        public void reset() {
            this.ranks = new int[5];
            this.jog = 0;
            this.pontuacao = 0;
        }
        
        public void escolhaHeroi(User user) {
            while(user.getHeroi()==null) { //tempo(?)
                int i=0;//guardar nr de heroi escolhido
                if (herois.get(i).ocupado()) {
                    //mensagem de heroi ocupado
                }
                else {
                    herois.get(i).selecionar(user);
                }
            }
        }
        
        
     }
