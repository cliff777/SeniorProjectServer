package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import server.game.PlayerEntity;


public class Server 
{
	/*
	 * Main class for the server
	 * This class will accept incoming connections, 
	 * create client objects, and initialize the
	 * game level 
	 */
	
	//public static List<PlayerEntity> players = new ArrayList<PlayerEntity>();
	
	//public static List<Client> clients = new ArrayList<Client>();
	
	public static Map<Client, PlayerEntity> players = new HashMap<Client, PlayerEntity>();
	
	private static boolean running = true;
	
	public static void main(String[] args)
	{
		System.out.println("Started...");
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
			
			Client client = new Client(socket);
			players.put(client, new PlayerEntity("Name", 0, 0));
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
