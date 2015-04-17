
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
			// if Blocks are activated and the kibble location is the same as any of the blocks, location not allowed
			if (Block.blocksOn){
				for (int i = 0; i < Block.howManyBlocks; i++) {
					if (kibbleX == Block.blocksListX.get(i) && kibbleY == Block.blocksListY.get(i)) {
						kibbleInSnake = true;
						}
					}
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
