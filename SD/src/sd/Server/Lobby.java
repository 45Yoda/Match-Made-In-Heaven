package Server;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

import static java.lang.Thread.sleep;

public class Lobby {
	private static int tam=10;//2
	private int jog;
	private int rankAdj;
	private int rank;
	private Equipa equipaA;
	private Equipa equipaB;
    Lock lock;
    Condition notFull;
    private List<String> notificacoes;
        
	public Lobby(int rank) {
		this.jog = 0;
		this.rank=rank;
		this.rankAdj=-1;
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
    public synchronized int escolhaHerois(User user,int i) throws InterruptedException {
        int r=-1;
	    if(user.getEquipa()==0) r=equipaA.escolhaHeroi(user,i);
        else r=equipaB.escolhaHeroi(user,i);
        return r;
	}
        
	public synchronized void espera(User user) throws InterruptedException {
               if ( user.getRank()!=-1 && this.rank!=user.getRank()) rankAdj=user.getRank();
               jog++;
        System.out.println(jog);
               while(jog<tam) {
                    wait();
                    }
               notifyAll();
        }
        
        public synchronized void resetLobby(User user) throws InterruptedException{
            user.reset();
            this.jog++;
            if (jog==20) { //ultima thread dá reset ao lobby 4*
                lock.lock();
		try {
            this.jog=0;
            this.rankAdj=-1;
            this.equipaA.reset();
            this.equipaB.reset();
            this.notificacoes.clear();
            notFull.signalAll();
                }
                finally{lock.unlock();}
            }
        }
        
        public synchronized void distribuiEquipa(User user) throws InterruptedException {
            System.out.println("Distribuiçao");
	    if (equipaA.getJog()==5) {//1
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
            notificacoes.add("Equipa "+eq+" > User: " +user.getUsername()+" "+user.getHeroi().getNome()+": "+pont+" pontos.");
            if (user.getEquipa()==0)
                equipaA.score(pont);
            else equipaB.score(pont);
                while(notificacoes.size()!=20) //4
                    wait();
                notifyAll();
            List<String> lista = new ArrayList<>();
            for(int i=10;i<20;i++) //2 4
                lista.add(notificacoes.get(i));
            if (equipaA.getPontuacao()>equipaB.getPontuacao()) lista.add("Equipa Vencedora: A!!!");
            else lista.add("Equipa Vencedora: B!!!");
            sleep(500);
            atualizaRes(user);
            resetLobby(user);
            System.out.println(lista);

            return lista;
            }
        
        //notificaçoes da constituição das equipas
        public synchronized List<String> notConst(User user) throws InterruptedException{
            String eq = null;
            if (user.getEquipa()==0) eq="A";
            else eq="B";
            notificacoes.add("Equipa "+eq+" > " +"User: "+user.getUsername()+" Heroi: "+user.getHeroi().getNome());
            while(notificacoes.size()!=10)//2
                wait();
            notifyAll();
            return notificacoes.stream().limit(10).collect(Collectors.<String>toList());//10
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

    public synchronized void esperaEquipa(User user) throws InterruptedException {
        if (this.equipaA.getJog()==5 && this.equipaB.getJog()==5) notifyAll();
        while(this.equipaA.getJog()!=5 && this.equipaB.getJog()!=5)
            wait();
    }
}
