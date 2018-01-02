/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;
import java.net.Socket;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.IOException;
import static java.lang.System.out;

/**
 *
 * @author yoda45
 */
public class Skeleton extends Thread{
    private User user;
    private Socket cliSocket;
    private PrintWriter writeSocket;
    private BufferedReader readSocket;
    private Matching match;

      
    Skeleton(Matching m, Socket cliSocket) throws IOException {
		this.match = m;
		this.cliSocket = cliSocket;
		this.readSocket = new BufferedReader(new InputStreamReader(cliSocket.getInputStream()));
		this.writeSocket = new PrintWriter(cliSocket.getOutputStream(), true);
		user = null;
	}

    public void run() {
    	try{
    		String request;
            while((request = readSocket.readLine()) != null){
    			String response = null;
                response = interpretRequest(request);

    		if(!response.isEmpty())
    		    this.writeSocket.println(response);
				out.println(response + "\n");
    		}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		endConnection();

	}

    private String interpretRequest(String request){
        try {
            return runCommand(request);
		} catch (RequestFailedException e) {
			return "EXCEPTION\n" + e.getMessage();
		} catch (IOException e) {
            e.printStackTrace();
        } catch (NoAuthorizationException e) {
            e.printStackTrace();
        }
        return "";
    }
    
    private String runCommand(String request) throws RequestFailedException, IOException, NoAuthorizationException {
        String user,pass;


		switch(request) {
            case "Iniciar Sessao":
				userMustBeLogged(false);
                user = readSocket.readLine();
				pass = readSocket.readLine();

                return login(user,pass);

			case "Registar":
				userMustBeLogged(false);

				user = readSocket.readLine();
				pass = readSocket.readLine();

				return signUp(user,pass);

			case "Play":
                userMustBeLogged(true);
                return play();

            case "Stats":
                userMustBeLogged(true);
                return viewStats();

            case "Terminar Sessão":
                this.user = null;
                System.out.println("Sessão terminada;");

            default:
                throw new RequestFailedException(" não é um comando válido");
		}
	}
        
    private String login(String usern,String pass)throws RequestFailedException{

        try{
            this.user = match.login(usern, pass);

	    }catch (NoAuthorizationException e) {
		    throw new RequestFailedException(e.getMessage());
	    }

        return "Logged in!";
    }



    private void userMustBeLogged(boolean status) throws RequestFailedException {
		if (status == true && user == null)
			throw new RequestFailedException("É necessário iniciar sessão para aceder ao Matchmaking");

		if (status == false && user != null)
			throw new RequestFailedException("Já existe uma sessão iniciada");
	}

    private String signUp(String user,String pass) throws RequestFailedException{

        try{
            match.SignUp(user, pass);
        }catch (UsernameExistsException e) {
            throw new RequestFailedException(e.getMessage());
        }

        return "Registo efectuado com sucesso";
    }


    private String play() throws RequestFailedException {
        try{
            match.distribuirUser(user);
        }catch(InterruptedException e){
            throw new RequestFailedException("Não foi possivel encontrar um jogo");
        }
        return "Jogo encontrado";
    }
    
    private void endConnection() {

		try {
			cliSocket.close();
		} catch (IOException e) {
			out.println("Couldn't close client socket");
		}
	}
    
    private String viewStats(){
        String response = "Stats " + user.getUsername() +
                          " Played: "+ user.getJogos()+
                          " + Wins : " + user.getWin() +
                          " Rank " + user.getRank() ;
        
        return response;
    }


}
