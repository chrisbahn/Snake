
import java.util.Random;

public class Kibble {

	/** Identifies a random square to display a kibble
	 * Any square is ok, so long as it doesn't have any snake segments or Blocks in it.
	 *
	 */

	private int kibbleX; //This is the square number (not pixel)
	private int kibbleY;  //This is the square number (not pixel)

	public Kibble(Snake s, Block b){
		//Kibble needs to know where the snake is, so it does not create a kibble in the snake
		//Pick a random location for kibble, check if it is in the snake
		//If in snake, try again
		moveKibble(s,b);
	}

	protected void moveKibble(Snake s, Block b){
		Random rng = new Random();
		boolean kibbleInSnake = true;
		while (kibbleInSnake == true) {
			//Generate random kibble location
			kibbleX = rng.nextInt(SnakeGame.xSquares);
			kibbleY = rng.nextInt(SnakeGame.ySquares);
			kibbleInSnake = s.isSnakeSegment(kibbleX, kibbleY); // checks if kibble location is inside the snake; if so, location is not allowed and new random location is generated
				if (Block.blocksOn && (kibbleX == b.getBlock1X() && kibbleX == b.getBlock1X())){ // if Blocks are activated and the kibble location is the same as Block 1, location not allowed
					kibbleInSnake = true;
				}
			}
		}


	public int getKibbleX() {
		return kibbleX;
	}
	public int getKibbleY() {
		return kibbleY;
	}



}
