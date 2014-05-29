package server.packet;

public class Packet01Move extends Packet {
	private int newX, newY;

	public Packet01Move(String info) {
		super(info);
	}

	@Override
	public void parse() {
		this.newX = Integer.parseInt(args[1]);
		this.newY = Integer.parseInt(args[2]);
	}
	
	public int getNewX()
	{
		return this.newX;
	}
	
	public int getNewY()
	{
		return this.newY;
	}

}
