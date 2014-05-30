package server;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import server.game.Tile;
import server.packet.Packet;
import server.packet.Packet01Move;
import server.packet.Packet03TileUpdate;
import server.packet.Packet04AddEntity;
import server.packet.Packet05RemoveEntity;


public class Client extends Thread
{
	
	private Socket socket;
	private boolean running = true;
	private OutputStreamWriter out;
	private InputStreamReader in;
	
	public Client(Socket socket)
	{
		this.socket = socket;
		
		this.start();
	}
	
	@Override
	public void run()
	{
		
		try
		{
			this.in = new InputStreamReader(socket.getInputStream());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		try
		{
			this.out = new OutputStreamWriter(socket.getOutputStream());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		
		//this player has joined, tell all other players to add another entity
		for(Client client : Server.players.keySet())
		{
			if(client != this)
			{
				//tell other player that we have just joined
				new Packet04AddEntity("4:player").send(client.getOutput());
				
				//tell the player that just joined that there is another player in the game
				new Packet04AddEntity("4:player").send(this.getOutput());
				
			}
		}
		
		
		
		while(running)
		{
			
			if(socket.isClosed())
			{
				System.out.println("closed");
			}
			
			
			char[] cData = new char[1024];
			
			try
			{
				in.read(cData);
			}
			catch (IOException e)
			{
				e.printStackTrace();
				
				try
				{
					socket.close();
				}
				catch (IOException e1)
				{
					e1.printStackTrace();
				}
				
				continue;
			}
			
			//turn char array into byte array
			byte[] data = new byte[cData.length];
			
			for(int i = 0; i < data.length; i++)
			{
				data[i] = (byte)cData[i];
			}
			
			String info = new String(data).trim();
			//we now have a string of data to work with sent to the server by the client
						
			int id = Packet.getID(info);
			
			switch(id)
			{
			case 1:
				Packet01Move p01 = new Packet01Move(info);
				Server.players.get(this).setX(p01.getNewX());
				Server.players.get(this).setY(p01.getNewY());

				for(Client client : Server.players.keySet())
				{
					if(client != this)
					{
						new Packet01Move("1:" + p01.getNewX() + ":" + p01.getNewY()).send(client.getOutput());
					}
				}
				break;
			case 2:
				//disconnecting
				for(Client client : Server.players.keySet())
				{
					if(client != this)
					{
						new Packet05RemoveEntity("5:player").send(client.getOutput());
					}
				}
				
				Server.players.remove(this);
				
				break;
				
			case 3:
				Packet03TileUpdate p03 = new Packet03TileUpdate(info);
				 Tile t = p03.getTile();
				 System.out.println("got tile update packet");
				 
				 //let all other players know about the tile change
				 for(Client client : Server.players.keySet())
				 {
					 if(client != this)
					 {
						 p03.send(client.getOutput());
						 System.out.println("sending out tile update packet");
					 }
				 }
				 
				break;
			}
		}
		
		try
		{
			socket.close();
			in.close();
			out.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		Server.players.remove(this);
	}	
	
	public OutputStreamWriter getOutput()
	{
		return this.out;
	}
}
