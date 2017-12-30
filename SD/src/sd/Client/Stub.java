import java.net.Socket;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.NoSuchElementException;

public class Stub extends Thread{
    private Socket cliSocket;
    private ClientStatus client;
    private PrintWriter out;
    private Menu menu;
    private String[] initialMenu;
    private String[] sessionMenu;

    Stub(Socket cliSocket, ClientStatus client) throws IOException {
	this.cliSocket = cliSocket;
	this.client = client;

	out = new PrintWriter(cliSocket.getOutputStream(), true);
	menu = new Menu();
	setUpMenus();
    }

    public void run() {
	int option;

	while((option = showMenu()) != -1) {
            client.setCommand(option);
            runCommand(option);
            }

	System.out.println("\nLigação terminada!");
	System.exit(0);
	}

    private int showMenu() {
        int option = 0;

	try {
            if (!client.isLogged())
		option = menu.show(initialMenu);
            else {
		sessionMenu[0] = "1) Ler notificações " + "(" + client.getNumberOfNotifications() + ")";
		option = menu.show(sessionMenu) + 2;
                }
        } catch (NoSuchElementException e) {
            return -1;
            }

	return option;
	}

    private void runCommand(int option) {
	switch(option) {
            case 1: signup();
                    break;
            case 2: login();
                    break;
            case 3: readNotifications();
                    break;
            case 4: play();
                    break;
           // case 5: SelectHero();
           //         break;
            }
	}

    private void signup() {
	String username = menu.readString("Username: ");
	String password = menu.readString("Password: ");
	String query = String.join(" ", "REGISTAR", username, password);

	out.println(query);
	String response = client.getResponse();

	if (client.getReplyStatus()) {
            query = String.join(" ", "LOGIN", username, password);
            out.println(query);
            response = client.getResponse();
            client.setLogged(true);
            }

	menu.printResponse(response);
    }

    private void login() {
	String username = menu.readString("Username: ");
	String password = menu.readString("Password: ");
	String query = String.join(" ", "LOGIN", username, password);

	out.println(query);
	String response = client.getResponse();

	if (client.getReplyStatus())
            client.setLogged(true);

	menu.printResponse(response);
    }

    private void readNotifications() {
	int amountNotifications;
	String notifications;

	synchronized (client) {
            amountNotifications = client.getNumberOfNotifications();
            notifications = client.getNotifications();
	}

	if (amountNotifications == 0)
            notifications = "> Não há novas notificações!\n";
	else
            out.println("CONFIRMAR " + amountNotifications);

	menu.printResponse(notifications);
	}
    
    private void play(){
        String query = "JOGAR";
        
        out.println(query);
        String response = client.getResponse();
        menu.printResponse(response);
    }

	

    private void setUpMenus() {
	initialMenu = new String[2];
	sessionMenu = new String[2];

	initialMenu[0] = "1) Registar";
	initialMenu[1] = "2) Iniciar Sessão";

	sessionMenu[1] = "2) Jogar";
	//sessionMenu[2] = "3) Escolher Heroi"; 
	
    }
}
