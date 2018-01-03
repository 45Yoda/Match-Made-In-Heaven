/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
/**
 *
 * @author Rui_vieira
 */
public class Matching {
    private Lock userLock;
    private Lock lobbyLock;
    private Map<String, User> users;
    private Lobby lobbys[] = new Lobby[10];;
    
    
    Matching(){
        users = new TreeMap<>() ;
        inicializarLobbys();
        userLock = new ReentrantLock();
        lobbyLock = new ReentrantLock();
        }

    private void inicializarLobbys() {
        for(int i = 0;i<10;i++)
            lobbys[i]=new Lobby(i);
    }

    public void SignUp(String username, String password)throws UsernameExistsException{
        userLock.lock();
        try{
            if (users.containsKey(username))
                throw new UsernameExistsException("O nome de utilizador não está disponível");
            users.put(username, new User(username, password,-1));
            }
        finally {
            userLock.unlock();
            }
    }
    
    /**
     *
     * @param username
     * @param password
     * @return
     */
    public User login(String username, String password) throws NoAuthorizationException {
        User user;
        userLock.lock();
        try{
            user = users.get(username);
            if(user == null || !user.authenticate(password))
                throw new NoAuthorizationException("Os dados inseridos estão incorretos");

        }
        finally{
            userLock.unlock();
        }
        return user;
    }

    public String distribuirUser(User user) throws InterruptedException{

         int rank = user.getRank();
            int best = bestLobby(rank);
            while (best == -1) {
                while (lobbys[rank].getJog() != 0) {
                    lobbys[rank].notFull.await();
                }
                best = bestLobby(rank);
            }
            user.setLobby(best);
            lobbys[best].espera(user);
        System.out.println("distribuir por equipas: ");
            lobbys[best].distribuiEquipa(user);
            lobbys[best].esperaEquipa(user);
            System.out.println("Lobby: "+user.getLobby() + " Equipa "+user.getEquipa());
            return "menu herois";
        }

     public String escolherHeroi(User user, int h) throws InterruptedException {
        int a= lobbys[user.getLobby()].escolhaHerois(user,h);
        if (a==1) return "Heroi Selecionado";
        else return "Heroi ja escolhido";
    }

    public String constituicao(User user) throws InterruptedException {
        List<String> string = new ArrayList<>();
        string.add("Constituicao Equipa");
        string.addAll(lobbys[user.getLobby()].notConst(user));
        return String.join("-",string);
    }


    public String jogar(User user) throws InterruptedException {
        List<String> not = new ArrayList<>();
        int lobby = user.getLobby();
        not.add("Resultado do Jogo");
        not.addAll(lobbys[lobby].jogar(user));
        System.out.println(not);
        return String.join("-",not);
    }


    //devolve o lobby mais cheio onde o user pode jogar
    public int bestLobby(int rank) {
        lobbyLock.lock();
        try {
            if (rank==-1) return ocup(0,4);
            else {
                if (rank==0) {
                    if (lobbys[1].getRankAdj()==0)
                        return ocup(0,1);
                    else return 0;
                }
                else if (rank==9) {
                    if (lobbys[8].getRankAdj()==9) {
                        return ocup(8,9);
                    }
                    else return 9;
                }
                else {
                    int min=-1,max=-1;
                    if (lobbys[rank-1].getRankAdj()==rank) min=rank-1;
                    else min=rank;
                    if (lobbys[rank+1].getRankAdj()==rank) min=rank+1;
                    else max=rank;
                    if (min==max) return rank;
                    else return ocup(min,max);
                }
            }
        }
        finally {lobbyLock.unlock();}
    }

    //devolve o lobby mais cheio entre dois pontos
    public int ocup(int min,int max) {
        int m=-1;
        for (int i = min;i<=max;i++)
            if (lobbys[i].getJog()>m && lobbys[i].getJog()!=10) m=i;//10
        System.out.println("Lobby: " +m);
        return m;
    }
}