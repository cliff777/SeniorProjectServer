package server.packet;

import java.io.IOException;
import java.io.OutputStreamWriter;

public abstract class Packet
{
	protected String info;
	protected String[] args;
		
	public Packet(String info)
	{
		this.info = info;
		args = info.split(":");
		
		parse();
		
	}
	
	public abstract void parse();
	
	public static int getID(String info)
	{
		return Integer.parseInt(info.split(":")[0]);
	}
	
	public void send(OutputStreamWriter out)
	{
		try
		{
			out.write(info);
			out.flush();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
}
