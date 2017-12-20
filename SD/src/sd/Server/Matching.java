/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sd.Server;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
/**
 *
 * @author Rui_vieira
 */
public class Matching {
    private Lock userLock;
    private Lock lobbyLock;
    private User users[];
    private Lobby lobbys[];
    
    
    Matching(){
        User users[] = null;
        Lobby lobbys[] = null;
        userLock = new ReentrantLock();
        lobbyLock = new ReentrantLock();
        
        }
    
    
}