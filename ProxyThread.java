import java.io.*;
import java.net.*;


public class ProxyThread extends Thread {
	private Socket client;

	ProxyThread(Socket client) {
		this.client = client;
		this.start();
	}


	public void run() {
		
		//System.out.println("New thread started for connection " + client.toString());
		
		try {

			byte[] inBytes = new byte[1024];

			//First want to read the request from the client
			InputStream in = client.getInputStream();
			//BufferedReader inBuffer = new BufferedReader( new InputStreamReader(in));
			in.read(inBytes);

			byte[] saveBytes = inBytes;

			String stringHeader = new String(inBytes);

			int indexA = stringHeader.indexOf("Host: ");
			int indexB = stringHeader.indexOf("\n");


			String str = stringHeader.substring(indexA);
			str = str.substring(6, indexB - 14);

			//System.out.println(str);
			//System.out.println(str2.length());


			//Next you want to establish a connection to the site the client wants (send get request to the server)

			str = str.trim();

			Socket connectToSite = new Socket(str ,80);
			
			InputStream hostInStream = connectToSite.getInputStream();
			OutputStream hostOutStream = connectToSite.getOutputStream();
			hostOutStream.write(saveBytes);
			
			byte[] hostBytes = new byte[1024];
			
			hostInStream.read(hostBytes);

			System.out.println(new String(hostBytes));





			//Take the input sent from the server and then store it


			//Send the data to client

			DataOutputStream out = new DataOutputStream(client.getOutputStream());
		}
		catch (Exception e) {

		}

	}
}
