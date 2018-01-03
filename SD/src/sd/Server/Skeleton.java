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
                System.out.println(request);
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

    private String interpretRequest(String request) throws InterruptedException {
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
    

    private String runCommand(String request) throws RequestFailedException, IOException, NoAuthorizationException, InterruptedException {
        String[] keywords = request.split("-", 2);
        System.out.println(keywords[0]);
        String command = keywords[0].toUpperCase();

        if (command.equals("LOGIN")) {
            userMustBeLogged(false);
            return login(keywords[1]);
        } else if (command.equals("REGISTAR")) {
            userMustBeLogged(false);
            return signUp(keywords[1]);
        } else if (command.equals("HERO")) {
            userMustBeLogged(true);
            int hero = Integer.parseInt(keywords[1]);
            return match.escolherHeroi(this.user, hero-1);
        } else if (command.equals("CONSTITUICAO")) {
            userMustBeLogged(true);
            return match.constituicao(this.user);
        } else if (command.equals("REALPLAY")) {
            userMustBeLogged(true);
            return match.jogar(this.user);
        } else if (command.equals("PLAY")) {
            userMustBeLogged(true);
            return play();
        } else if (command.equals("STATS")) {
            userMustBeLogged(true);
            return viewStats();
        } else if (command.equals("TERMINAR SESSAO")){
            this.user = null;
            System.out.println("Sessão terminada;");
            }
        else  throw new RequestFailedException(" não é um comando válido");
        return "";
    }
        
    private String login(String args)throws RequestFailedException {
        String[] param = args.split("-");

        try {
            this.user = match.login(param[0], param[1]);

        } catch (ArrayIndexOutOfBoundsException e) {
            throw new RequestFailedException("Os argumentos dados não são válidos");

        } catch (NoAuthorizationException e) {
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

    private String signUp(String args) throws RequestFailedException{
        String[] param = args.split("-");

        try{
            match.SignUp(param[0], param[1]);
        }catch (UsernameExistsException e) {
            throw new RequestFailedException(e.getMessage());
        }

        return "Registo efectuado com sucesso";
    }


    private String play() throws RequestFailedException {
        try{
            String u = match.distribuirUser(user);
        }catch(InterruptedException e){
            throw new RequestFailedException("Não foi possivel encontrar um jogo");
        }
        return "Jogo Encontrado";
    }
    
    private void endConnection() {

		try {
			cliSocket.close();
		} catch (IOException e) {
			out.println("Couldn't close client socket");
		}
	}
    
    private String viewStats(){
        String response = "Stats- " +
                          " Nome: " +user.getUsername() +
                          " Played: "+ user.getJogos()+
                          " Wins: " + user.getWin() +
                          " Rank: " + user.getRank();

        return response;
    }


}
