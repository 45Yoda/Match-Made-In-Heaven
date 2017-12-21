/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sd.Server;

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
        Lobby lobbys[] = null;
        userLock = new ReentrantLock();
        lobbyLock = new ReentrantLock();
        }
    
    public void SignUp(String username, String password)throws UsernameExistsException{
        userLock.lock();
        try{
            if (users.containsKey(username))
                throw new UsernameExistsException("O nome de utilizador não está disponível");
            users.put(username, new User(username, password,-1,null));
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
            user.resend();
        }
        finally{
            userLock.unlock();
        }
        return user;
    }
    
    
}