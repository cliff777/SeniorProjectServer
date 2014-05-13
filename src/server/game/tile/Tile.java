package server.game.tile;

import server.game.Colours;
import server.game.Level;
import server.game.entity.Entity;


public class Tile {

	public static final Tile[] tiles = new Tile[256];
	public static final Tile VOID = new SolidTile(0,0,0,Colours.get(000, 500, -1, -1),0xFF000000);
	public static final Tile STONE = new SolidTile(1,1,0,Colours.get(332, 333, 333, 343),0xFF555555);
	public static final Tile GRASS = new BaseTile(2,2,0,Colours.get(253, -1, 141, 250),0xFF00FF00,0,0);
	public static final Tile WATER =new AnimatedTile(3,new int[][] { { 0, 5 }, { 1, 5 }, { 2, 5 }, { 1, 5 } },Colours.get(-1, 004, 115, -1),0xFF0000FF,500, 0, 0);
	public static final Tile LOG = new SolidTile(4,3,0,Colours.get(100, 433, 322, 211),0xFFaa5500);
	public static final Tile SAND = new BaseTile(5,4,0,Colours.get(-1, -1, 550, 554),0xFFFFFF00,0,0);
	public static final Tile DIRT = new BaseTile(6,4,0,Colours.get(-1, -1, 211, 322),0xFF412000,0,0);
	public static final Tile LEAVES = new LeafTile(7,new int[][] { { 0, 4 }, { 1, 4 }, { 2, 4 }, { 1, 4 } },Colours.get(121, -1, 151, -1),0xFF9eff7d,500,0,0);
	public static final Tile FENCE = new FenceTile(8,new int[][] {{3,5},{4,5},{5,5},{6,5},{7,5},{8,5},{9,5},{10,5},{11,5}},Colours.get(253, 200,321,141),0xFFFF7d01,0,0);
	public static final Tile PLANK = new SolidTile(9,6,0,Colours.get(-1, 321, 432, -1),0xFF845510);


	//public static final Tile CYCLE = new CycleTile(5,0,0,Colours.get(000, 000, 000, 000),0xFF000000);


	public byte id;
	protected boolean solid;
	protected boolean emitter;
	private int levelColour;
/**
 * 
 * @param id = int Id of tile
 * @param isSolid = boolean true if entity cant pass through tile
 * @param isEmitter = boolean true if can produce light
 * @param levelColour = int color associated with tile on map sheet
 */
	public Tile(int id, boolean isSolid,boolean isEmitter,int levelColour)
	{

		this.id=(byte)id;
		if(tiles[id]!=null)
		{
			throw new RuntimeException("Duplicate tile id on: "+id);
		}
		this.solid=isSolid;
		this.emitter=isEmitter;
		this.levelColour=levelColour;
		tiles[id]=this;
	}
	/**
	 * @return int, the id of the block
	 */
	public byte getId()
	{
		return id;
	}
	public boolean mayPass(Level level, int x, int y, Entity e) {
		return true;
	}
	/**
	 * @return boolean, the block is solid
	 */
	public boolean isSolid()
	{
		return solid;
	}
	/**
	 * @return boolean, the block can produce light
	 */

	/**
	 * @return int, the color associated with the tile on the map sheet 
	 */
	public int getLevelColour()
	{
		return levelColour;
	}
	public void bumpedInto(Level level,int x1,int y1,Entity entity)
	{
		
	}
	public void steppedOn(Level level, int x1,int y1, Entity entity)
	{
		
	}
	public void hurt(Level level, int x, int y, Entity entity, int damage, int direction)
	{
		
	}
	/**
	 * represents each tiles tick function
	 */
	public void tick()
	{
		
	}
	/**
	 * @param display = display
	 * @param level = level
	 * @param x = int x position in game
	 * @param y = int y position in game
	 */


	

}
