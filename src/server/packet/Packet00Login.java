package server.packet;

public class Packet00Login extends Packet {
	private String name;

	public Packet00Login(String info) {
		super(info);
	}

	@Override
	public void parse() {
		this.name = args[1];
	}
	
	public String getName()
	{
		return this.name;
	}

}
