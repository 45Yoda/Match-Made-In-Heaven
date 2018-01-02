package Server;

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
         herois = new ArrayList<>();
         inicializarHeroes();
    }

    public void inicializarHeroes() {
        herois.add(new Heroi("Andormeda"));
        herois.add(new Heroi("Apus"));
        herois.add(new Heroi("Argo"));
        herois.add(new Heroi("Aries"));
        herois.add(new Heroi("Bootes"));
        herois.add(new Heroi("Camelopardalis"));
        herois.add(new Heroi("Carina"));
        herois.add(new Heroi("Cassiopeia"));
        herois.add(new Heroi("Centauros"));
        herois.add(new Heroi("Cetus"));
        herois.add(new Heroi("Chamaeleon"));
        herois.add(new Heroi("Cancer"));
        herois.add(new Heroi("Coma"));
        herois.add(new Heroi("Crater"));
        herois.add(new Heroi("Crux"));
        herois.add(new Heroi("Corvus"));
        herois.add(new Heroi("Delphinus"));
        herois.add(new Heroi("Draco"));
        herois.add(new Heroi("Fornax"));
        herois.add(new Heroi("Hercules"));
        herois.add(new Heroi("Hydra"));
        herois.add(new Heroi("Lemon"));
        herois.add(new Heroi("Lupus"));
        herois.add(new Heroi("Musca"));
        herois.add(new Heroi("Orion"));
        herois.add(new Heroi("Pavo"));
        herois.add(new Heroi("Pegasus"));
        herois.add(new Heroi("Pictor"));
        herois.add(new Heroi("Reticulum"));
        herois.add(new Heroi("Scrutum"));
    }

    public int getJog() {
            return this.jog;
        }
	
    public void insere(User user) {
        lock.lock();
        try{
            this.ranks[this.jog]=user.getRank();
            this.jog++;
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

        public int escolhaHeroi(User user,int i) {
            int h = herois.get(i).selecionar(user);
            return h;
        }
        
        
     }
