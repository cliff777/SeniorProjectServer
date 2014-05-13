package server;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import server.packet.Packet;
import server.packet.Packet00Login;
import server.packet.Packet01Move;

public class Client extends Thread
{
	
	private Socket socket;
	private boolean running = true;
	
	public Client(Socket socket)
	{
		this.socket = socket;
	}
	
	@Override
	public void run()
	{
		while(running)
		{
			InputStreamReader in;
			
			try
			{
				in = new InputStreamReader(socket.getInputStream());
			}
			catch (IOException e)
			{
				e.printStackTrace();
				continue;
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
			
			Packet p;
			
			int id = Integer.parseInt(String.valueOf(data[0]) + String.valueOf(data[1]));
			
			switch(id)
			{
			case 0:
				p = new Packet00Login(data);
				p.parse();
				break;
			case 1:
				p = new Packet01Move(data);
				p.parse();
				break;
			}

			
			
			
		}
		
		try
		{
			socket.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
}
