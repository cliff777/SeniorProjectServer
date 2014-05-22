package server.packet;

import server.game.EntityType;


public class Packet05RemoveEntity extends Packet
{
	private EntityType toRemove;

	public Packet05RemoveEntity(String info) {
		super(info);
	}

	@Override
	public void parse() {
		//TODO: add actual functionality
		toRemove = EntityType.MONKEY;
	}

	public EntityType getToRemove()
	{
		return this.toRemove;
	}
}
