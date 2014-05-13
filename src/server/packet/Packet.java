package server.packet;

public abstract class Packet
{
	private int id;
	private byte[] data;
	
	public Packet(byte[] data)
	{
		this.data = data;
		
		this.id = Integer.parseInt(String.valueOf(data[0]) + String.valueOf(data[1]));
	}
	
	public int getID()
	{
		return this.id;
	}
	
	public abstract void parse();
}
