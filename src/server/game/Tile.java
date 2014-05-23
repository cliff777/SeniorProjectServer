package server.game;

public class Tile
{
	private int x, y, id;
	
	public Tile(int x, int y, int id)
	{
		this.setX(x);
		this.setY(y);
		this.setId(id);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
