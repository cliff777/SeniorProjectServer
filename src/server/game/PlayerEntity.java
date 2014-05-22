package server.game;

public class PlayerEntity {
	
	int x, y;
	String name;
	
	public PlayerEntity(String name, int x, int y)
	{
		this.x = x;
		this.y = y;
		this.name = name;
	}
	
	public int getX()
	{
		return this.x;
	}
	
	public int getY()
	{
		return this.y;
	}
	
	public void setX(int x)
	{
		this.x = x;
	}
	
	public void setY(int y)
	{
		this.y = y;
	}

}
