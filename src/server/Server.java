package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server 
{
	/*
	 * Main class for the server
	 * This class will accept incoming connections, 
	 * create client objects, and initialize the
	 * game level 
	 */
	
	private static boolean running = true;
	
	public static void main(String[] args)
	{
		ServerSocket server;
		
		try
		{
			server = new ServerSocket(4567);
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return;
		}
		
		while(running)
		{
			Socket socket;
			
			try
			{
				socket = server.accept();
			}
			catch (IOException e)
			{
				e.printStackTrace();
				continue;
			}
			
			new Client(socket).start();
		}
		
		try 
		{
			server.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
	}
	

}
