import java.net.Socket;
import java.io.IOException;
import java.net.UnknownHostException;


public class HeavenMatch {
    private static final int port = 5000;
    public static void main(String[] args) throws UnknownHostException, IOException {
		Socket cli = new Socket(args[0], port);
		//Reader reader = new Reader(cli, info);
		Stub stub = new Stub( cli );

		//reader.start();
		stub.start(); 
	}
   
}
