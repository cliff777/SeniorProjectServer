package server.game;

import java.util.List;

import server.game.entity.Arrow;
import server.game.entity.Entity;
import server.game.entity.PlayerEntity;

public class Collision {
	//private Level level;
	static long lastArrowHit=0;

	public static boolean entityCollision(List<Entity>entities)
	{
		if(entities.isEmpty())
		{
			return false;
		}
		for(int i=0;i<entities.size();i++)
		{
			for(int k=i+1;k<entities.size();k++)
			{
				Entity entity1 = entities.get(i);
				//entity2 is usually player
				Entity entity2 = entities.get(k);
				/*
				 * handles collision of entities
				 */
				/*if((entity1 instanceof Arrow)||(entity2 instanceof Arrow))
				{
					return false;
				}*/



				if(entity1.getBounds().intersects(entity2.getBounds()))
				{
					if(System.currentTimeMillis()-lastArrowHit>10)
					{


						//arrow removal
						if(entity1 instanceof Arrow)
						{

							entity1.die();
							entity2.health=entity2.health-1;
							
							lastArrowHit=System.currentTimeMillis();


						}
						if(entity2 instanceof Arrow)
						{
							entity2.die();
							entity1.health=entity1.health-1;
							lastArrowHit=System.currentTimeMillis();

						}

					}

					if(entity1 instanceof Arrow || entity2 instanceof Arrow)
					{
						return false;
					}
					//regular collision stuff
					if(entity1.getClass()!=PlayerEntity.class)
					{
						entity1.stopMoving();
					}
					if(entity2.getClass()!=PlayerEntity.class)
					{
						entity2.stopMoving();
					}
					if(entity2.getMobX()>entity1.getMobX())
					{

						entity2.x=entity2.x+1;
						entity1.x=entity1.x-1;
					}
					if(entity2.getMobX()<entity1.getMobX())
					{
						entity2.x=entity2.x-1;
						entity1.x=entity1.x+1;
					}
					if(entity2.getMobY()>entity1.getMobY())
					{
						entity2.y=entity2.y+1;
						entity1.y=entity1.y-1;
					}
					if(entity2.getMobY()<=entity1.getMobY())
					{
						entity2.y=entity2.y-1;
						entity1.y=entity1.y+1;
					}
					return true;
				}

			}
		}
		return false;
	}

	public static Entity getEntityActedWith(List<Entity>entities)
	{

		for(int i=0;i<entities.size();i++)
		{
			for(int k=i+1;k<entities.size();k++)
			{
				Entity entity1 = entities.get(i);

				//entity2 is usually player
				Entity entity2= entities.get(k);
				/*
				 * handles action of entities
				 */
				if(entity1 instanceof PlayerEntity || entity2 instanceof PlayerEntity)
				{

					if(entity1.getActionBounds().intersects(entity2.getActionBounds()))
					{
						entity1.stopMoving();
						entity2.stopMoving();

						if(entity1 instanceof PlayerEntity)
						{

							return entity2;
						}
						if(entity2 instanceof PlayerEntity)
						{
							return entity1;
						}
						return null;

					}
				}

			}
		}
		return null;
	}
}
