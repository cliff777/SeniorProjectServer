package server.game;

/**
 * 
 * Colors for the game
 *
 */
public class Colours {
	/**each colour  will accept numbers 0 to 5
	 * EX: Colours.get(000,000,000,000) Would make each color black, -1 is transparent,
	 * first 0 is a red value/second 0 is a green value/third 0 is a blue value, 000 is
	 * black, 555 is white, 500 is full red, 050 is full green, 005 is full blue.
	 *  
	 *  Each parameter is a color from the sprite sheet, this class allows you to set the new colors you desire
	 * @param colour1 = black
	 * @param colour2 = dark grey
	 * @param colour3 = light grey
	 * @param colour4 = white
	 * 
	 * 
	 */
	public static int get(int colour1, int colour2, int colour3, int colour4) {
		//returns one long number 
		return (get(colour4) << 24) + (get(colour3) << 16) + (get(colour2) << 8) + get(colour1);
	}

	/**
	 * @param colour
	 * @return Returns the value value from 0 to 5
	 */
	private static int get(int colour) {
		//if it gets a -1, the color wont be rendered
		if (colour < 0)
		{
			return 255;
		}
		//red, gets the first number, full red 500/100 to get the 5 then takes % 10, r would = 5
		int r = colour / 100 % 10;
		//green, gets the second number
		int g = colour / 10 % 10;
		//blue, doesn't need to be divided because its the last digit
		int b = colour % 10;

		return r * 36 + g * 6 + b;
	}
}
