package server.packet;

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
	
}
