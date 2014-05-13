package server.game.entity;

import java.awt.Rectangle;
import java.util.Random;

import server.game.Collision;
import server.game.Level;
import server.game.tile.Tile;



/**
 * 
 * Main constructor for all entities in the game
 *
 */
public class Entity {

	public int x;
	public int y;
	public int xr=6;
	public int yr=6;
	public boolean removed;

	public Level level;

	protected String name;
	protected int speed;
	protected int numSteps=0;
	protected boolean isMoving;
	protected int movingDirection =1;
	protected int scale=1;
	protected boolean isSwimming;
	public int maxHealth=10;
	public int health=maxHealth;

	Random random = new Random();
	Monkey monkey;
	Turtle turtle;
	PlayerEntity player;
	Collision collision;

	public Entity(Level level)
	{
		this.level=level;
	}

	public Entity(Level level, String name, int x, int y, int speed, boolean isSwimming)
	{
		this.level = level;
		this.name = name;
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.isSwimming = isSwimming;
	}

	public boolean intersects(int x1,int y1,int x2,int y2)
	{
		return !(x+xr<x1||y+yr<y1||x-xr>x2||y-yr>y2);
	}

	public boolean isBlockableBy(Entity entity)
	{
		return true;
	}


	public void die()
	{
		removed=true;
	}

	/**
	 *
	 * @param display = the display
	 */
	/*
	public void render(Display display) {
	}
	*/

	protected void touchedBy(Entity entity)
	{

	}
	public int getMobX()
	{
		return 0;
	}
	public int getMobY()
	{
		return 0;
	}

	public int getLightRadius()
	{
		return 0;
	}
	//protected void touchedBy(Entity entity)



	public void move(int xa,int ya)
	{
		if(xa!= 0&&ya!=0)
		{
			move(xa,0);
			move(0,ya);
			numSteps--;
			return;
		}
		numSteps++;
		if(!hasCollided(xa,ya))
		{
			if(!Collision.entityCollision(level.entities))
			{
				if(ya<0)
				{//up

					movingDirection =0;
				}
				if(ya>0)
				{//down
					movingDirection =1;
				}
				if(xa<0)
				{//left
					movingDirection =2;
				}
				if(xa>0)
				{//right
					movingDirection =3;
				}

				x+=xa*speed;
				y+=ya*speed;
			}
		}
		if(level.getTile(this.x>>3, this.y>>3).getId()==3)
		{
			isSwimming= true;
		}
		if(isSwimming&&level.getTile(this.x>>3, this.y>>3).getId()!=3)
		{
			isSwimming=false;
		}



	}

	public boolean blocks(Entity e)
	{
		return e.isBlockableBy(this);
	}

	//public abstract Rectangle getBounds();

	public boolean hasCollided(int xa,int ya) {
		return false;
	}


	//public abstract void stopMoving();
	/**
	 * 
	 * @param xa
	 * @param ya
	 * @param x
	 * @param y
	 * @return
	 */
	protected boolean isSolidTile(int xa,int ya,int x,int y)
	{
		if(level==null)
		{
			return false;
		}
		Tile lastTile = level.getTile((this.x+x)>>3,(this.y+y)>>3);
		Tile newTile =level.getTile((this.x+x+xa)>>3,(this.y+y+ya)>>3);
		if(!lastTile.equals(newTile)&&newTile.isSolid())
		{
			return true;
		}
		return false;
	}
	/**
	 * 
	 * @return the name of the entity
	 */
	public String getName()
	{
		return name;
	}


	public void tick() {
		if(health<=0)
		{
			die();
		}
	}



	/**
	 * stops mob from moving
	 * @param mob
	 */
	public void stopMoving() {

	}
	/**
	 * returns the mobs x position
	 * @return
	 */

	public boolean checkMobs(Entity entity1, Entity entity2) {
		if(entity1.getBounds().intersects(entity2.getBounds()))
		{
			return true;
		}

		return false;
	}
	public boolean checkMobsAction(Entity entity1, Entity entity2) {
		if(entity1.getActionBounds().intersects(entity2.getActionBounds()))
		{
			return true;
		}

		return false;
	}
	/**
	 * the bounds of the entity
	 */
	public Rectangle getBounds() 
	{
		return null;

	}
	public Rectangle getActionBounds()
	{
		return null;
	}
	public String getMessage()
	{
		return "";
	}
	public int spawnX()
	{
		return 0;
	}
	public int spawnY()
	{
		return 0;
	}

}
