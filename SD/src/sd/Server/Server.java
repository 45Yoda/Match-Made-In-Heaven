/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author yoda45
 */  

 public class Server {
	private static final int port = 5000;

	public static void main(String[] args) throws IOException {
		ServerSocket srv = new ServerSocket(port);
		Matching match = new Matching();

		while(true) {
			Socket cliSocket = srv.accept();
			Skeleton cli = new Skeleton(match , cliSocket);
			cli.start();
		}
	}
}
