import java.io.*;
import java.net.*;


public class ProxyThread extends Thread {
	private Socket client;
	private BufferedReader inputClientBr;
	private BufferedWriter outputClientBw;
	private BufferedReader inputServerBr;
	private BufferedWriter outputServerBw;


	ProxyThread(Socket client) {
		this.client = client;
		this.start();
	}


	public void run() {
		
		//System.out.println("New thread started for connection " + client.toString());
		
		try {

			inputClientBr = new BufferedReader(new InputStreamReader(client.getInputStream()));
			outputClientBw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));

			String requestTotal = inputClientBr.readLine();
			//System.out.println(requestTotal);

			String requestMethod = requestTotal.substring(0, requestTotal.indexOf(' '));
			System.out.println(requestMethod);

			String requestURL = requestTotal.substring(requestTotal.indexOf(' ') + 1);
			requestURL = requestURL.substring(0, requestURL.indexOf(' '));

			//System.out.println(requestURL);



			if (requestMethod.equals("CONNECT"))
			{	


				String socketSplit[] = requestURL.split(":");
				int port = Integer.valueOf(socketSplit[1]);
				System.out.println(port);
				System.out.println(socketSplit[0]);


				//get rid of the header
				for(int i =0; i < 5; i++) {
					inputClientBr.readLine();
				}


				InetAddress address = InetAddress.getByName(socketSplit[0]);
				Socket proxyToServer = new Socket(socketSplit[0], port);

				String connected = "HTTP/1.0 200 Connection established\r\n" + "Proxy-Agent: ProxyServer/1.0\r\n" + "\r\n";
				outputClientBw.write(connected);
				outputClientBw.flush();

				byte[] bytes = new byte[4096];
				System.out.println("through the loop0");
				//int bufferSize = client.getInputStream().read(bytes);
				int bufferSize;

				while ((bufferSize = client.getInputStream().read(bytes)) > 0 ) {

					System.out.println("Buffer Size: " + bufferSize);
					//System.out.println(new String(bytes));
					proxyToServer.getOutputStream().write( bytes, 0, bufferSize);
					//System.out.println("I am here");
				}
				
				proxyToServerClass psc = new proxyToServerClass(client.getInputStream(), client.getOutputStream());
				psc.start();
			}

		}
		catch (Exception e) {
			System.out.println("connection error");
			e.printStackTrace();
		}
	}


	public class proxyToServerClass extends Thread {

		private InputStream is;
		private OutputStream os;

		proxyToServerClass(InputStream is, OutputStream os) {
			this.is = is;
			this.os = os;

		}

		public void run() {
			
			try {
				byte[] bytes2 = new byte[4096];
				
				
				int readTotalBytes;

				while((readTotalBytes = is.read(bytes2)) > 0 ) {
					System.out.println("in the new thread loop");
					os.write(bytes2, 0, readTotalBytes);
				}
			}
			catch (Exception e )
			{
				e.printStackTrace();
			}
		}

	}
}
