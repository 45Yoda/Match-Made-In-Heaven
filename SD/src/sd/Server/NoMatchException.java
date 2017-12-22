/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sd.Server;

/**
 *
 * @author Bandolero
 */
public class NoMatchException extends Exception{

    public NoMatchException(String msg) {
        super(msg);
    }
    
    public NoMatchException(){
        super();
    }
    
}
