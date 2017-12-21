/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sd.Server;
import java.net.Socket;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.IOException;
/**
 *
 * @author yoda45
 */
public class Base extends Thread{
    private User user;
    private Socket cliSocket;
    private PrintWriter out;
    private BufferedReader in;
    private Matching match;
    private Thread notificator;
        
    Base(Matching aucHouse, Socket cliSocket) throws IOException {
		this.match = aucHouse;
		this.cliSocket = cliSocket;
		in = new BufferedReader(new InputStreamReader(cliSocket.getInputStream()));
		out = new PrintWriter(cliSocket.getOutputStream(), true);
		user = null;
		notificator = null;
	}
    
    



}
