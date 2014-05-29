package server.packet;



public class Packet04AddEntity extends Packet {
	
	//private Entity e;

	public Packet04AddEntity(String info) {
		super(info);
	}

	@Override
	public void parse() {
		//temp
		//TODO: add actual functionality
		//e = new PlayerEntity("Monkey", 0, 0);
	}
	
	//public Entity getEntity()
	//{
	//	return this.e;
	//}
}
