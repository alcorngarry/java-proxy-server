import java.io.*;
import java.net.*;


public class ProxyThread extends Thread {
	private Socket client;

	ProxyThread(Socket client) {
		this.client = client;
		this.start();
	}


	public void run() {
		try {

			System.out.println("New thread started for connection " + client.toString());

			byte[] inBytes = new byte[1024];

			//First want to read the request fromm the client
			InputStream in = client.getInputStream();
			BufferedReader inBuffer = new BufferedReader( new InputStreamReader(in));
			//in.read(inBytes);
			//System.out.println(new String(inBytes));

			String requestLine = inBuffer.readLine();
			System.out.println(requestLine);


			//Next you want to establish a connection to the site the client wants (send get request to the server)


			//Take the input sent from the server and then store it


			//Send the data to client

			DataOutputStream out = new DataOutputStream(client.getOutputStream());

		}
		catch (Exception e) {

		}

	}


	//public String getHostAddress(String getRequest) {


	//}
}