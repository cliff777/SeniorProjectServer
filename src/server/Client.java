package server;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;


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
			in = new InputStreamReader(socket.getInputStream());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		try
		{
			out = new OutputStreamWriter(socket.getOutputStream());
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
				client.send("4:player");
				//tell the player that just joined that there is another player
				this.send("4:player");
			}
		}
		
		//
		
		while(running)
		{
			
			if(!socket.isConnected())
			{
				System.out.println("closed");
				//closed, tell all other players to remove an entity, make sure to exit this
				for(Client client : Server.players.keySet())
				{
					if(client != this)
					{
						client.send("5:player");
					}
				}
				
				return;
			}
			
			
			char[] cData = new char[1024];
			
			try
			{
				in.read(cData);
			}
			catch (IOException e)
			{
				e.printStackTrace();
				continue;
			}
			
			//turn char array into byte array
			byte[] data = new byte[cData.length];
			
			for(int i = 0; i < data.length; i++)
			{
				data[i] = (byte)cData[i];
			}
			
			String info = new String(data);
			info = info.trim();
			//we now have a string of data to work with sent to the server by the client
			
			String[] split = info.split(":");
			
			int id = Integer.parseInt(split[0]);
			
			switch(id)
			{
			case 1:
				int newX = Integer.parseInt(split[1]);
				int newY = Integer.parseInt(split[2]);
				
				Server.players.get(this).setX(newX);
				Server.players.get(this).setY(newY);
				
				for(Client client : Server.players.keySet())
				{
					if(client != this)
					{
						client.send("1:" + newX + ":" + newY);
					}
				}
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
	
	public void send(String info)
	{
		try
		{
			this.out.write(info);
			this.out.flush();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	
	
}
