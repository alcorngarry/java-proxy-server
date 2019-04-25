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

			int indexHost = stringHeader.indexOf("Host: ");
			int indexBreak = stringHeader.indexOf("\n");


			String str = stringHeader.substring(indexHost);
			str = str.substring(6, indexBreak - 14);

			System.out.println(str);
			//System.out.println(str2.length());

			//Next you want to establish a connection to the site the client wants (send get request to the server)

			str = str.trim();

			Socket connectToSite = new Socket(str ,80);
			
			InputStream hostInStream = connectToSite.getInputStream();
			OutputStream hostOutStream = connectToSite.getOutputStream();
			hostOutStream.write(saveBytes);
			
			byte[] hostBytes = new byte[1024];
			
			hostInStream.read(hostBytes);

			String data = new String(hostBytes);

			//System.out.println(data);

			//Take the input sent from the server and then store it

			int indexLocation = data.indexOf("Location: ");
			int indexDate = data.indexOf("Date: ");
			//int indexTime = data.indexOf("GMT ");

			String dateString = data.substring(indexDate);
			String locationString = data.substring(indexLocation);

			int indexLine = locationString.indexOf("\n");

			locationString = locationString.substring(10, indexLine - 1);
			dateString = dateString.substring(6, indexLine - 1);

			BufferedWriter out = new BufferedWriter(new FileWriter("log.txt", true));
    		out.write(locationString + " , " + dateString + "\n");
    		out.close();

			System.out.println(locationString); 
			System.out.println(dateString);

			//Send the data to client

			////OutputStream clientOutput = client.getOutputStream();
			////System.out.println(new String(hostBytes));
			////clientOutput.write(hostBytes);
		}
		catch (Exception e) {

		}

	}
}
