package server.game;


public class Menu {
	protected Game game;
	protected KeyInputHandler input;
	
	public void init(Game game, KeyInputHandler input)
	{
		this.input=input;
		this.game=game;
	}
	public void tick()
	{
		
	}
	/**
	 * render stuff for the menu
	 * @param display
	 */
	/*
	public void render(Display display)
	{
		
	}
	*/

}
