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
		while(running)
		{
			try
			{
				in = new InputStreamReader(socket.getInputStream());
			}
			catch (IOException e)
			{
				e.printStackTrace();
				continue;
			}
			
			try
			{
				out = new OutputStreamWriter(socket.getOutputStream());
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
			
			String info = new String(data);
			//we now have a string of data to work with sent to the server by the client
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
	}
	
	
	
}
