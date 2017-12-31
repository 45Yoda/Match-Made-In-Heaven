
import java.net.Socket;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import sd.Client.Menu;

public class Stub extends Thread{
    private Socket cliSocket;
    private PrintWriter out;
    private Menu menu;
    private ReentrantLock lock;
    private Condition c;
    
    Stub(Socket cliSocket) throws IOException {
	this.cliSocket = cliSocket;

	out = new PrintWriter(cliSocket.getOutputStream(), true);
	menu = new Menu();
    }

    public void run() {
	int op;
        String inputGeter = "> Escolha uma das opções: \n";
        try{
            menu.showMenu();
            while((op= menu.readInt(inputGeter))!= -1){
                if(menu.getOp() == 0){
                    if(op == 1) login();
                    if(op == 2) signup();
                    if(op == 3) break;
                }else if(menu.getOp() == 0){
                    if(op == 1) play();
                    //if(op == 2) selectHero();
                    if(op == 3) break;
                }
                if(op >= 1 && op <= 3){
                    space();
                    menu.showMenu();
                }else{
                    System.out.println("Opção inválida.\n");
                }
                
            }
        cliSocket.shutdownOutput();    
        }
        catch(Exception e){
            System.out.println(e.getMessage());
	}

    }

// Adaptar tudo que está para baixo
    private void signup() throws InterruptedException {
	String username = menu.readString("Username: ");
	String password = menu.readString("Password: ");
	String query = String.join(" ", "REGISTAR", username, password);

	out.println(query);
        this.lock.lock();
	c.await();
        this.lock.unlock();
	menu.setOp(1);
    }

    private void login() {
	String username = menu.readString("Username: ");
	String password = menu.readString("Password: ");
	String query = String.join(" ", "LOGIN", username, password);

	out.println(query);
    }

    
    private void play(){
        String query = "JOGAR";
        out.println(query);
        
    }
    
   // private void selectHero(){}
    
    private void space(){
		for(int i = 0;i<40;i++)
			System.out.println();
	}

}
