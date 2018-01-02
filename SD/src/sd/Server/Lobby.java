package Server;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

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
    private List<String> notificacoes;
        
	public Lobby(int rank) {
		this.jog = 0;
		this.rank=rank;
		this.rankAdj=-1;
        this.ready=0;
		equipaA= new Equipa();
		equipaB = new Equipa();
        lock = new ReentrantLock();
        notFull = lock.newCondition();
        notificacoes = new ArrayList<>();
	}


	//devolve numero de jogadores
	public int getJog() {return this.jog;}
	public int getRank() {return this.rank;}
	public int getRankAdj() {return this.rankAdj;}
    public Equipa getEquipaA() {return this.equipaA;}
    public Equipa getEquipaB() {return this.equipaB;}
    public List<String> getNot() {return this.notificacoes;}


    //espera que equipas estejam distribuidas e trata da escolha de herois
    public synchronized int gereEquipa(User user) throws InterruptedException {
        if (equipaA.getJog()==5 && equipaB.getJog()==5) notifyAll();
        while(equipaA.getJog()!=5 && equipaB.getJog()!=5)
            wait();
        // if(user.getEquipa()==0) equipaA.escolhaHeroi(user);
        //else equipaB.escolhaHeroi(user);
        if (user.getHeroi()==null) ready++;
        jog++;
        while(jog<20) //espera que check todos os users
            wait();
        notifyAll();
        return ready;
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
            this.jog++;
            if ((ready==10 && jog==40)||ready<0 && jog==30) { //ultima thread dá reset ao lobby
                lock.lock();
		try {
            this.jog=0;
            this.rankAdj=-1;
            this.equipaA.reset();
            this.equipaB.reset();
            this.ready=0;
            this.notificacoes.clear();
            notFull.signalAll();
                }
                finally{lock.unlock();}
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
        
        public synchronized List<String> jogar(User user) throws InterruptedException{
            String eq = null;
            if (user.getEquipa()==0) eq="A";
            else eq="B";
            int pont = ThreadLocalRandom.current().nextInt(0,10+1);
            notificacoes.add("Equipa "+eq+": " +user.getUsername()+" ----> "+user.getHeroi().getNome()+": "+pont+" pontos.");
            if (user.getEquipa()==0)
                equipaA.score(pont);
            else equipaB.score(pont);
            jog++;
                while(jog<30) //espera que todos os users joguem
                    wait();
                notifyAll();
            List<String> lista = new ArrayList<>();
            for(int i=10;i<20;i++)
                lista.add(notificacoes.get(i));
            return lista;
            }
        
        //notificaçoes da constituição das equipas
        public synchronized List<String> notConst(User user) throws InterruptedException{
            String eq = null;
            if (user.getEquipa()==0) eq="A";
            else eq="B";
            notificacoes.add("Equipa "+eq+": " +user.getUsername()+" ----> "+user.getHeroi().getNome());
            while(notificacoes.size()!=10)
                wait();
            notifyAll();
            return notificacoes.stream().limit(10).collect(Collectors.<String>toList());
        }

        public synchronized void atualizaRes(User user) throws InterruptedException{
            int rank=user.getRank();
            if (user.getEquipa()==0) {
                if (equipaA.getPontuacao()>equipaB.getPontuacao()) user.registaJogo(1);
                else user.registaJogo(0);
            }
            else {if (equipaA.getPontuacao()>equipaB.getPontuacao()) user.registaJogo(0);
                else user.registaJogo(1);
                
            }
            }
        
        }
