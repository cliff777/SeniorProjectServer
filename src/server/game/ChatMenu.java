package server.game;

import java.awt.Font;

import server.game.entity.PlayerEntity;

public class ChatMenu extends Menu{
	private PlayerEntity player;
	private String msg;
	private String nextLine="";

	private int  stringLimit=18;
	private boolean firstCycle =true;
	private int waitForNextAction = 200;
	private long lastAction;
	private boolean talking = true;


	public ChatMenu(PlayerEntity player, String msg)
	{
		this.player=player;
		this.msg=msg;
	}
	public void tick()
	{
		if((System.currentTimeMillis() - lastAction) >= (waitForNextAction)) 
		{
			if(!firstCycle&&input.action.down)
			{
				if(!talking)
				{
					player.setLastAction(System.currentTimeMillis());
					game.setMenu(null);
				}

				if((nextLine.length()<=stringLimit)&&talking)
				{
					msg=nextLine;
					talking=false;
					lastAction=System.currentTimeMillis();
				}

				if((nextLine.length()>stringLimit)&&talking)
				{
					msg=nextLine.substring(0,stringLimit);
					nextLine=nextLine.substring(stringLimit);
					lastAction=System.currentTimeMillis();
				}
			}

			if(firstCycle)
			{
				if(msg.length()>stringLimit)
				{
					nextLine=msg.substring(stringLimit);
					msg=msg.substring(0, stringLimit);
					lastAction=System.currentTimeMillis();
				}
				else
				{
					talking=false;
					lastAction=System.currentTimeMillis();
				}
				firstCycle=false;
			}

		}


	}
	/*
	public void render(Display display)
	{
		display.render(4, 105, (1+27*32), Colours.get(-1, -1, 333, 555),0,0,1);

		display.render(4, 97, (1+27*32), Colours.get(-1, -1, 333, 555),0,1,1);

		display.render(148, 105, (1+27*32), Colours.get(-1, -1, 333, 555),1,0,1);
		display.render(148, 97, (1+27*32), Colours.get(-1, -1, 333, 555),1,1,1);

		for(int xS=2;xS<=18;xS++)
		{
			display.render((xS*8)-4, 105, (2+27*32), Colours.get(-1, -1, 333, 555),0,0,1);
			display.render((xS*8)-4, 97, (2+27*32), Colours.get(-1, -1, 333, 555),0,1,1);
		}
		Font.renderFont(msg,display, 8, 101, Colours.get(-1, -1, -1, 000), 1);
	}*/

}
