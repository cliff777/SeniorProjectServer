package server.packet;

import server.Client;

public abstract class Packet
{
	private byte[] data;
	private String info;
	private int id;
	private Client client;
	
	public Packet(byte[] data, int id, Client client)
	{
		this.data = data;
		this.info = new String(data);
		this.id = id;
		this.client = client;
	}
	
	public Packet(byte[] data, int id)
	{
		this.data = data;
		this.info = new String(data);
		this.id = id;
	}
	
	//only ever called when a packet is recieved
	public abstract void parse();

	public byte[] getData() {
		return data;
	}
	
	public int getID()
	{
		return this.id;
	}
	
	public String getInfo()
	{
		return this.info;
	}
	
	public Client getClient()
	{
		return this.client;
	}
}
