package server.game.tile;


public class CycleTile extends Tile {
	protected int tileId;
	protected int tileColour;
	protected long lastChange;
	protected int timeToChange=1000;
	
	protected int alpha = 99;
	protected boolean nightTime=true;
	protected boolean dayTime=false;
	public CycleTile(int id, int x, int y, int tileColour, int levelColour) {
		super(id, false, false, levelColour);
		this.tileId = x + y * 32;
		this.tileColour = tileColour;
	}

	public void tick() {
		if ((System.currentTimeMillis() - lastChange) >= (timeToChange)) {
			lastChange = System.currentTimeMillis();
			
			
			
		}
		if(dayTime)
		{
			alpha++;
		}
		if(nightTime)
		{
			alpha--;
		}
		
		if(alpha>=99)
		{
			nightTime=true;
			dayTime=false;
		}
		if(alpha<=0)
		{
			dayTime=true;
			nightTime=false;
		}
	}
/*
	public void render(Display display, Level level, int x, int y,int flipX,int flipY) {
		display.render(x, y, tileId, tileColour, 0,0, 1);
		System.out.println(0x00+alpha);
	}

	@Override
	public void render(Display display, Level level, int x, int y) {
		// TODO Auto-generated method stub
		
	}
*/
	

	

}
