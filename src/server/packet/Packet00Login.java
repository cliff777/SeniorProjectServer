package server.packet;

import server.Client;
import server.Server;
import server.game.entity.PlayerEntity;

public class Packet00Login extends Packet {

	public Packet00Login(byte[] data, Client client) {
		super(data, 0, client);
	}

	@Override
	public void parse() {
		int x = Integer.parseInt(this.getInfo().split(":")[1]);
		int y = Integer.parseInt(this.getInfo().split(":")[2]);
		
		Server.players.put(getClient(), new PlayerEntity(x, y));
		
		//tell all other clients that a new player has joined
		for(Client client : Server.players.keySet())
		{
			if(client != getClient()) //Don't want to add a client to their own game
				client.sendPacket(new Packet02AddEntity(new String(2 + ":" + x + ":" + y).getBytes(), getClient()));
		}
	}

}
