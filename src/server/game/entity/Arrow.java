package server.game.entity;

import java.awt.Rectangle;

import server.game.Colours;
import server.game.Level;



public class Arrow extends Entity {
	private int colours= Colours.get(-1,222,321,555);
	private int facingDirection=0;
	private int x,y;
	private int airTime=60;

	private boolean adjusted=false;

	public Arrow(Level level,int x,int y, int facingDirection) {
		super(level, "Arrow", x, y, 1, false);
		this.x=x;
		this.y=y;
		this.facingDirection=facingDirection;
	}

	public void tick()
	{
		super.tick();

		if(!adjusted)
		{
			x=x+4;
			y=y+4;
			if(facingDirection==0)
			{
				y=y-8;
				adjusted=true;
			}
			if(facingDirection==1)
			{
				y=y+14;
				adjusted=true;
			}
			if(facingDirection==2)
			{
				x=x-6;
				adjusted=true;
			}
			if(facingDirection==3)
			{
				x=x+4;
				adjusted=true;
			}
		}
		
		if(facingDirection==0)
		{
			y--;
		}
		if(facingDirection==1)
		{

			y++;
		}
		if(facingDirection==2)
		{

			x--;
		}
		if(facingDirection==3)
		{

			x++;
		}
		airTime--;
		if(airTime<0)
		{
			die();
		}
	}
	public Rectangle getBounds()
	{
		if(airTime>50)
		{
			return new Rectangle(x,y,0,0);
		}
		else
		{
			return new Rectangle(x,y,8,8);
		}
	}
	/*
	public void render(Display display)
	{

		int xTile=2;
		int yTile=19;
		if(facingDirection==0)
		{
			display.render(x, y, xTile+yTile*32, colours, 0, 0, 1);
		}
		if(facingDirection==1)
		{
			display.render(x, y, xTile+yTile*32, colours, 0, 1, 1);
		}
		if(facingDirection==2)
		{
			display.render(x, y, (xTile+1)+yTile*32, colours, 0, 0, 1);
		}
		if(facingDirection==3)
		{
			display.render(x, y, (xTile+1)+yTile*32, colours, 1, 0, 1);
		}
	}
	*/
	
	public void die()
	{
		super.die();
	}

}
