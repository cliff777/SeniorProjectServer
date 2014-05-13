package server.game.tile;

import java.util.Random;

import server.game.Level;
import server.game.entity.Entity;



public class LeafTile extends BaseTile{
	private int[][] animationTileCoords;
	private int currentAnimationIndex;
	private long lastIterationTime;
	private int animationSwitchDelay;
	Random random=new Random();

	public LeafTile(int id, int[][] animationCoords, int tileColour, int levelColour, int animationSwitchDelay,int flipX,int flipY) {
		super(id, animationCoords[0][0], animationCoords[0][1], tileColour, levelColour, flipY, flipY);
		this.animationTileCoords = animationCoords;
		this.currentAnimationIndex = 0;
		this.solid=true;
		this.lastIterationTime = System.currentTimeMillis();
		this.animationSwitchDelay = animationSwitchDelay;

	}
	public boolean mayPass(Level level, int x, int y, Entity e) {
		return true;
	}
	public void tick() {
		if ((System.currentTimeMillis() - lastIterationTime) >= (animationSwitchDelay)) {
			lastIterationTime = System.currentTimeMillis();
			currentAnimationIndex = (currentAnimationIndex + 1) % animationTileCoords.length;
			int newAnimation=random.nextInt(4);
			this.tileId = (animationTileCoords[newAnimation][0] + (animationTileCoords[currentAnimationIndex][1] * 32));
		}
	}
}