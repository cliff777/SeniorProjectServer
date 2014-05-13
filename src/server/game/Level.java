package server.game;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import server.game.entity.Entity;
import server.game.entity.PlayerEntity;
import server.game.entity.Skeleton;
import server.game.tile.Tile;



public class Level {

	private byte[]tiles;
	public int width;
	public int height;
	public boolean mapFixed=false;
	Game game;

	public List<Entity>[] entitiesInTiles;
	/**
	 * array list of entities in the game
	 */
	public ArrayList<Entity>entities = new ArrayList<Entity>();
	//public ArrayList<Entity>removeList=new ArrayList<Entity>();
	//	public int lastTile =0;
	private String imagePath;
	private BufferedImage image;
	private Random random = new Random();
	public Skeleton skelleton;
	//public Entity entity;
	public PlayerEntity player;
	public List<Entity>rowEntities=new ArrayList<Entity>();

	private Comparator<Entity> spriteSorter = new Comparator<Entity>() {
		public int compare(Entity e1, Entity e2) {
			if (e2.y < e1.y) return +1;
			if (e2.y > e1.y) return -1;
			return 0;
		}

	};

	@SuppressWarnings("unchecked")
	public Level(Game game,String imagePath)
	{
		this.game=game;
		if(imagePath!=null)
		{
			this.imagePath=imagePath;
			this.loadLevelFromFile();
		}
		else{

			this.width=64;
			this.height=64;
			tiles=new byte[width*height];
			this.generateLevel();
		}
		entitiesInTiles=new ArrayList[width*height];

		for(int i=0;i<width*height;i++)
		{
			entitiesInTiles[i]= new ArrayList<Entity>();
		}
	}

	public Image getImage()
	{
		return image;
	}

	private void loadLevelFromFile()
	{
		try{
			this.image=ImageIO.read(Level.class.getResource(this.imagePath));
			this.width=image.getWidth();
			this.height=image.getHeight();
			tiles=new byte [width*height];
			this.loadTiles();



		}catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	//width is width of image
	//tileColours is the pixel colour for each tile on the image
	private void loadTiles()
	{
		int[] tileColours =this.image.getRGB(0, 0, width, height, null, 0, width);
		for(int y=0;y<height;y++)
		{
			for(int x=0;x<width;x++)
			{
				tileCheck: 	for(Tile t: Tile.tiles)
				{
					if(t!=null&&t.getLevelColour()==tileColours[x+y*width])
					{

						this.tiles[x+y*width]=t.getId();

						break tileCheck;
					}
				}
			}
		}
		fixMap();

	}

	private void fixMap()
	{
		//random patches
		for(int y=0;y<height*8;y++)
		{
			if(random.nextInt(2000)<=1)
			{
				y++;
			}
			for(int x=0;x<width*8;x++)
			{
				if(random.nextInt(2000)<=1)
				{
					x++;
				}
				if(random.nextInt(7000)<=1)
				{
					Tile tileId=Tile.VOID;
					if(random.nextInt(10)>=5)
					{
						tileId=Tile.DIRT;
					}
					else
					{
						tileId=Tile.SAND;
					}
					makePatches(x,y,tileId);

				}

			}
		}
		for(int y=0;y<height*8;y++)
		{

			for(int x=0;x<width*8;x++)
			{
				//add sand
				/*if(getTile((x>>3),(y>>3)).id==3)
					System.out.println("hi"+x+", "+y);
				 */
				if(getTile((x>>3), (y>>3))==Tile.WATER)
				{
					//System.out.println("Water!");
					if(getTile((x>>3)-1,(y>>3))==Tile.GRASS)
					{
						setTile((x>>3)-1,y>>3,Tile.SAND);
					}
					if(getTile((x>>3)+1,y>>3)==Tile.GRASS)
					{
						setTile((x>>3)+1,y>>3,Tile.SAND);
					}

					if(getTile(x>>3,(y>>3)-1)==Tile.GRASS)
					{
						setTile(x>>3,(y>>3)-1,Tile.SAND);
					}
					if(getTile((x>>3),(y>>3)+1)==Tile.GRASS)
					{
						setTile(x>>3,(y>>3)+1,Tile.SAND);
					}
				}

				//some random trees
				if(random.nextInt(1000)<=1)
				{
					if(getTile(x>>3,y>>3)==Tile.GRASS&&getTile((x>>3)-1,y>>3)==Tile.GRASS&&getTile((x>>3)+1,y>>3)==Tile.GRASS
							&&getTile(x>>3,(y>>3)-1)==Tile.GRASS&&getTile(x>>3,(y>>3)-2)==Tile.GRASS)
					{
						setTile(x>>3,y>>3,Tile.LOG);
						setTile(x>>3,(y>>3)-1,Tile.LOG);
						setTile(x>>3,(y>>3)-2,Tile.LEAVES);
						setTile((x>>3)-1,(y>>3)-1,Tile.LEAVES);
						setTile((x>>3)+1,(y>>3)-1,Tile.LEAVES);
					}
				}


			}
		}


	}
	public void makePatches(int xPos,int yPos,Tile tile)
	{

		if(getTile((xPos>>3),(yPos>>3))!=Tile.WATER&&getTile((xPos>>3),(yPos>>3))!=Tile.STONE)
		{
			setTile((xPos>>3),(yPos>>3),tile);
			setTile((xPos>>3)+1,(yPos>>3),tile);

			setTile((xPos>>3)-1,(yPos>>3)+1,tile);
			setTile((xPos>>3),(yPos>>3)+1,tile);
			setTile((xPos>>3)+1,(yPos>>3)+1,tile);
			setTile((xPos>>3)+2,(yPos>>3)+1,tile);

			setTile((xPos>>3),(yPos>>3)+2,tile);
			setTile((xPos>>3)+1,(yPos>>3)+2,tile);
		}
	}



	public void generateLevel()
	{
		for(int y = 0;y<height;y++)
		{
			for(int x =0;x<width;x++)
			{
				if(x*y%10<7)
				{
					tiles[x+y*width]=Tile.GRASS.getId();
				}
				else
				{
					tiles[x+y*width]=Tile.STONE.getId();
				}
			}
		}
	}



	/**
	 * cycles through all the tiles and draws them
	 * @param display = the display
	 * @param xOffset = offset of x
	 * @param yOffset = offset of y
	 */
	/*
	public void renderTiles(Display display, int xOffset,int yOffset)
	{
		if(!mapFixed)
		{
			//fixMap();
			mapFixed=true;
		}
		if(xOffset<0)
		{
			xOffset=0;
		}
		if(xOffset>((width<<3)-display.width))
		{
			xOffset=((width<<3)-display.width);
		}

		if(yOffset<0)
		{
			yOffset=0;
		}
		if(yOffset>((height<<3)-display.height))
		{
			yOffset=((height<<3)-display.height);
		}
		display.setOffset(xOffset,yOffset);

		for (int y = (yOffset >> 3); y < (yOffset + display.height >> 3) +1; y++) {
			for (int x = (xOffset >> 3); x < (xOffset + display.width >> 3) + 1; x++) {
				getTile(x, y).render(display, this, x << 3, y << 3);
			}
		}
	}*/

	/*
	public void renderTheEntities(Display display,int xOffset,int yOffset)
	{

		if(xOffset<0)
		{
			xOffset=0;
		}
		if(xOffset>((width<<3)-display.width))
		{
			xOffset=((width<<3)-display.width);
		}

		if(yOffset<0)
		{
			yOffset=0;
		}
		if(yOffset>((height<<3)-display.height))
		{
			yOffset=((height<<3)-display.height);
		}
		display.setOffset(xOffset, yOffset);
		for(int y=(yOffset>>3)-3;y<=((yOffset)+display.height>>3)+3;y++)
		{
			for(int x=(xOffset>>3)-3;x<=(xOffset+display.width>>3)+3;x++)
			{
				if(x<0||x>=width||y<0||y>=height)
				{
					continue;
				}
				rowEntities.addAll(entitiesInTiles[x+y*width]);
			}
			if(rowEntities.size()>0)
			{
				renderEntities(display,rowEntities);
			}
			rowEntities.clear();
		}
		display.setOffset(0, 0);
	}
	*/




	/**
	 * gets the tile at specified x and y value then returns the if of the tile
	 * @param x = x coordinate on map
	 * @param y = y coordinate on map
	 * @return Id of tile
	 */
	public Tile getTile(int x,int y)
	{

		if(0>x||x>=width||0>y||y>=height)
		{
			return Tile.VOID;
		}
		return Tile.tiles[tiles[x+y*width]];
	}

	public void setTile(int x,int y,Tile t)
	{
		if(0>x||x>=width||0>y||y>=height)
		{
			return;
		}
		tiles[x+y*width]= t.id;
	}
	public int searchForTile(int x,int y,Tile t)
	{
		return y&x;

	}

	/**
	 * draws all the entities in the Entity list
	 * @param display = the display
	 * @param list = the list of entities
	 */
	
	/*
	public void renderEntities(Display display,List<Entity>list)
	{
		Collections.sort(list,spriteSorter);
		for(int i=0;i<list.size();i++)
		{
			list.get(i).render(display);
		}
	}
	*/
	
	/**
	 * this recieves an entity then adds the entity to an array list of other entites
	 * @param entity = the entity to be added
	 */
	public void addEntity(Entity entity)
	{
		if(entity instanceof PlayerEntity)
		{
			player =(PlayerEntity) entity;
		}
		entity.removed=false;
		entities.add(entity);

		insertEntity((entity.x>>3),(entity.y>>3),entity);
	}


	public void remove(Entity e)
	{
		entities.remove(e);
		int x1=(e.x>>3);
		int y1=(e.y>>3);
		removeEntity(x1,y1,e);
	}


	private void insertEntity(int x, int y, Entity e) {
		if (x < 0 || x>=width || y<0 || y >= height) 
		{
			return;
		}
		entitiesInTiles[x + y * width].add(e);
	}


	public void removeEntity(int x,int y,Entity entity)
	{
		if(x<0||x>=width||y<0||y>=height)
		{
			return;
		}
		entitiesInTiles[x+y*width].remove(entity);
	}
	/**
	 * First cycles through each tile and calls the tiles tick, then cycles through all the entities and calls there tick. If the entities remove boolean is true then the method to remove the entity is called
	 */
	public void tick()
	{
		for(Tile t: Tile.tiles)
		{
			if(t==null)
			{
				break;
			}
			t.tick();
		}
		for(int i=0;i<entities.size();i++)
		{
			Entity e=entities.get(i);
			int x1=(e.x>>3);
			int y1=(e.y>>3);

			e.tick();
			if(e.removed)
			{
				entities.remove(i--);
				removeEntity(x1,y1,e);

			}
			else
			{

				int x2=(e.x>>3);
				int y2=(e.y>>3);
				if(x1!=x2||y1!=y2)
				{
					removeEntity(x1,y1,e);
					insertEntity(x2,y2,e);
				}
			}
		}
		//if()
	}
	public int getMapWidth()
	{
		return (width>>3);
	}
	public int getMapHeight()
	{
		return (height>>3);
	}

	public List<Entity> getEntities(int x1,int y1,int x2,int y2)
	{
		List<Entity>result=new ArrayList<Entity>();
		int xt1=(x1>>3)-1;
		int yt1=(y1>>3)-1;
		int xt2=(x2>>3)-1;
		int yt2=(y2>>3)-1;
		for(int y=yt1;y<yt2;y++)
		{
			for(int x=xt1;x<=xt2;x++)
			{
				if(x<0||x>=width||y<0||y>=height)
				{
					List<Entity>entities=entitiesInTiles[x+y*width];
					for(int i=0;i<entities.size();i++)
					{
						Entity e = entities.get(i);
						if(e.intersects(x1, y1, x2, y2))
						{

							result.add(e);
						}
					}
				}
			}
		}
		return result;
	}

	public void spawnHostiles(Entity entity)
	{
		int amount = random.nextInt(20)+10;
		for(int spawning=0;spawning<=amount;spawning++)
		{
			addEntity(new Skeleton(this,"skeleton",hostileSpawnX(),hostileSpawnY(),0,false));

		}
	}

	public int hostileSpawnX()
	{
		int sX=random.nextInt(150)+40;
		int posNeg=random.nextInt(2);
		if(posNeg==0)
		{
			return player.x-sX;
		}
		else
		{
			return player.x+sX;
		}


	}
	public int hostileSpawnY()
	{
		int sY=random.nextInt(150);
		int posNeg=random.nextInt(2);
		if(posNeg==0)
		{
			return player.y-sY;
		}
		else
		{
			return player.y+sY;
		}
	}

}
