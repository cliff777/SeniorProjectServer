package server.game.entity;

import java.awt.Rectangle;

import server.game.Colours;
import server.game.Level;

public class Skeleton extends Entity {
	private int tickCount;
	private int colour=Colours.get(-1, 555, 500, 000);
	private long lastMove;
	protected int xOffset;
	protected int yOffset;
	private String message = "Ooh ooh ah ah!";
	private int randomWalkTime=0;
	private boolean stopMoving = false;
	private int tryToFire=2000;
	private long lastFire;
	Arrow arrow;

	//Rectangle monkeyBox=new Rectangle();
	public Skeleton(Level level, String name, int x, int y, int speed,boolean isSwimming) {
		super(level, "Skelly", x, y, 1,isSwimming);

	}

	public void tick() {
		super.tick();
		int xa=0;
		int ya=0;

		if (level.player != null && randomWalkTime == 0) {
			int xd = level.player.x - x;
			int yd = level.player.y - y;
			if ((xd * xd + yd * yd <= 80 * 80)&&(xd * xd + yd * yd >= 30 * 30)) {
				xa = 0;
				ya = 0;
				if (xd < 0)
				{
					xa = -1;
				}
				if (xd > 0)
				{
					xa = +1;
				}
				if (yd < 0)
				{
					ya = -1;
				}
				if (yd > 0) 
				{
					ya = +1;
				}
			}
			if((xd * xd + yd * yd >0)&&(xd * xd + yd * yd <= 80 * 80))
			{
				if((System.currentTimeMillis()-lastFire)>=tryToFire)
				{
					lastFire=System.currentTimeMillis();
					//System.out.println("new arrow");
					arrow =new Arrow(level, xOffset, yOffset, movingDirection);
					level.addEntity(arrow);
				}
			}
		}


		if (random.nextInt(200) == 0) {
			randomWalkTime = 60;
			xa = (random.nextInt(3) - 1) * random.nextInt(2);
			ya = (random.nextInt(3) - 1) * random.nextInt(2);
		}
		if (randomWalkTime > 0) randomWalkTime--;
		if ((System.currentTimeMillis() - lastMove) >= (20)) {
			lastMove = System.currentTimeMillis();
			if(!stopMoving)
			{
				move(xa,ya);
			}
			stopMoving=false;
		}

		tickCount++;
	}

	public void stopMoving(Entity entity)
	{
		stopMoving=true;
	}

	public void die()
	{
		super.die();
	}

	/*
	public void render(Display display) {
		int xTile=0;
		int yTile=21;

		int walkingSpeed =3;
		int flipTopX=(numSteps>>walkingSpeed)&1;
		int flipTopY=(numSteps>>walkingSpeed)&1;
		int flipBottomL=(numSteps>>walkingSpeed)&1;
		int flipBottomR=(numSteps>>walkingSpeed)&1;

		int modifier =8*scale;
		xOffset =x-modifier/2;
		yOffset =y-modifier/2-4;
		//Font.render((x>>3)+", "+(y>>3), display, x, y, Colours.get(-1, -1, -1, 555), 1);
		if(movingDirection==1)
		{
			xTile+=2;
		}
		else if(movingDirection>1)
		{
			xTile+=4+((numSteps>>walkingSpeed)&1)*2;
			flipTopX=(movingDirection-1)%2;
		}

		if(isSwimming)
		{
			int waterColour = 0;
			yOffset+=4;
			if(tickCount%60<15)
			{
				yOffset-=1;
				waterColour=Colours.get(-1, -1, 225, -1);

			}
			else if(15<=tickCount%60&&tickCount%60<30)
			{
				waterColour=Colours.get(-1, 225, 115, -1);
			}
			else if(30<=tickCount%60&&tickCount%60<45)
			{
				yOffset-=1;
				waterColour=Colours.get(-1, 115, -1, 225);
			}
			else
			{
				waterColour=Colours.get(-1, 225, 115, -1);
			}
			display.render(xOffset, yOffset+3, 0+27*32, waterColour, flipTopX-1,flipTopY-1, 1);
			display.render(xOffset+8, yOffset+4, 0+27*32, waterColour, 1,1, 1);
		}



		display.render(xOffset+(modifier*flipTopX), yOffset, (xTile+yTile*32), colour,flipTopX,flipTopY-1,scale);
		display.render(xOffset+modifier-(modifier*flipTopX), yOffset, (xTile+1)+yTile*32, colour,flipTopX,flipTopY-1,scale);


		if(!isSwimming)
		{
			display.render(xOffset+(modifier*flipBottomL), yOffset+modifier, xTile+(yTile+1)*32, colour,flipBottomL,flipBottomR-1,scale);
			display.render(xOffset+modifier-(modifier*flipBottomL), yOffset+modifier, (xTile+1)+(yTile+1)*32, colour,flipBottomL,flipBottomR-1,scale);
		}
	}*/

	public Rectangle getActionBounds()
	{
		return new Rectangle(x-2,y-2,12,12);
	}
	public String getMessage()
	{
		return message;
	}
	public int getMobX()
	{
		return xOffset;
	}
	public int getMobY()
	{
		return yOffset;
	}
	public Rectangle getBounds()
	{
		return new Rectangle(x,y,8,8);
	}


	public boolean hasCollided(int xa, int ya) {
		int xMin = 0;
		int xMax = 7;
		int yMin = 3;
		int yMax = 7;
		for (int x = xMin; x < xMax; x++) {
			if (isSolidTile(xa, ya, x, yMin)) {
				stopMoving(this);
				return true;
			}
		}
		for (int x = xMin; x < xMax; x++) {
			if (isSolidTile(xa, ya, x, yMax)) {
				stopMoving(this);
				return true;
			}
		}
		for (int y = yMin; y < yMax; y++) {
			if (isSolidTile(xa, ya, xMin, y)) {
				stopMoving(this);
				return true;
			}
		}
		for (int y = yMin; y < yMax; y++) {
			if (isSolidTile(xa, ya, xMax, y)) {
				stopMoving(this);
				return true;
			}
		}
		return false;
	}
	
	




}