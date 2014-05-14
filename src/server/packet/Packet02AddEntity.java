package server.packet;

import server.Client;

public class Packet02AddEntity extends Packet {

	public Packet02AddEntity(byte[] data, Client client) {
		super(data, 2, client);
	}

	@Override
	public void parse() {
		//never need to parse
	}

}
