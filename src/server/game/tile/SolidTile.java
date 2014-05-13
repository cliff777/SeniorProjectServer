package server.game.tile;

import server.game.Level;
import server.game.entity.Entity;


public class SolidTile extends BaseTile{

	 public SolidTile(int id, int x, int y, int tileColour, int levelColour) {
	        super(id, x, y, tileColour, levelColour,0,0);
	        this.solid = true;
	 }
	 
	 public boolean mayPass(Level level, int x,int y, Entity e)
	 {
		 return false;
	 }

}
