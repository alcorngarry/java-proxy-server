import java.io.*;
import java.net.*;


public class ProxyServer {

	public static void main(String[] args) {

		try {
			//Clients connect to the proxy server(this machine) through port 1366
			ServerSocket server = new ServerSocket(8086);
			ProxyThread a = new ProxyThread(server.accept());

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

}