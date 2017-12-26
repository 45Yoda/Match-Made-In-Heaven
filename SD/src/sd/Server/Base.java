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
}      
    Base(Matching m, Socket cliSocket) throws IOException {
		this.match = m;
		this.cliSocket = cliSocket;
		in = new BufferedReader(new InputStreamReader(cliSocket.getInputStream()));
		out = new PrintWriter(cliSocket.getOutputStream(), true);
		user = null;
		notificator = null;
	}
    
    public void run() {
		String request = null;

		while((request = readLine()) != null) {
			String response = interpreteRequest(request);

			if (!response.isEmpty())
				out.println(response + "\n");
		}

		endConnection();
	}
    
    private String interpreteRequest(String request) throws NoMatchException{
        try {
			return runCommand(request);
		} catch (RequestFailedException e) {
			return "EXCEPTION\n" + e.getMessage();
		} catch (ArrayIndexOutOfBoundsException e) {
			return "EXCEPTION\n" + "Os argumentos não foram especificados";
		}
	}
    
    private String runCommand(String request) throws ArrayIndexOutOfBoundsException, RequestFailedException, NoMatchException {
		String[] keywords = request.split(" ");

		switch(keywords[0].toUpperCase()) {
			case "REGISTAR":
				userMustBeLogged(false);
				return signUp(keywords[1]);
			case "LOGIN":
				userMustBeLogged(false);
				return login(keywords[1]);
			case "CONFIRMAR":
				userMustBeLogged(true);
				return acknowledgeNotifications(keywords[1]);
                        case "JOGAR":
                                userMustBeLogged(true);
                                return play();
                        default:
				throw new RequestFailedException(keywords[0] + " não é um comando válido");
		}
	}
    
    private String signUp(String arguments) throws RequestFailedException{
        String[] parameters = arguments.split(" ",2);
        try{
            if(parameters.length != 2)
                throw new RequestFailedException("O username/password não podem ter espaços");;
            match.SignUp(parameters[0], parameters[1]);
        }catch (ArrayIndexOutOfBoundsException e) {
			throw new RequestFailedException("Os argumentos dados não são válidos");
        } catch (UsernameExistsException e) {
			throw new RequestFailedException(e.getMessage());
		}

		return "OK";
	}
        
    private String login(String arguments)throws RequestFailedException{
        String[] parameters = arguments.split(" ");
        try{
            user = match.login(parameters[0], parameters[1]);
            user.setSession(cliSocket);
            notificator = new Notificator(user, out);
            notificator.start();
	} catch (ArrayIndexOutOfBoundsException e) {
		throw new RequestFailedException("Os argumentos dados não são válidos");
	} catch (IOException e) {
			throw new RequestFailedException("Não foi possível iniciar sessão");
	} catch (NoAuthorizationException e) {
		throw new RequestFailedException(e.getMessage());
	}
        return "Loged";
    }
    
    private void userMustBeLogged(boolean status) throws RequestFailedException {
		if (status == true && user == null)
			throw new RequestFailedException("É necessário iniciar sessão para aceder ao Matchmaking");

		if (status == false && user != null)
			throw new RequestFailedException("Já existe uma sessão iniciada");
	}
    
    private String play()throws NoMatchException{
        try{
            user.distribuirUser(user);
        }catch(InterruptedException e){
            throw new NoMatchException("Não foi possivel encontrar um jogo");
        }
        return "";
    }
    
    private void endConnection() {
		if (notificator != null)
			notificator.interrupt();

		try {
			cliSocket.close();
		} catch (IOException e) {
			System.out.println("Couldn't close client socket");
		}
	}
    
    private String readLine() {
		String line = null;

		try {
			line = in.readLine();
		} catch(IOException e) {
			endConnection();
		}

		return line;
	} 

    private String acknowledgeNotifications(String arguments)throws RequestFailedException{
       try{
            int amount= Integer.parseInt(arguments);
            user.acknowledge(amount);
        } catch(ArrayIndexOutOfBoundsException | NumberFormatException e ){
            throw new RequestFailedException("Os argumentos dados não são validos");
        } 
       return "Acknowledged";
    }



}
