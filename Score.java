// Original code by Clara James
// Enhancements made by Christopher Bahn

/** Keeps track of, and display the user's score
 *
 */


public class Score {

	protected static int score;
	protected static int highScore = 0;
	protected static int increment;
	protected static boolean newHighScore = false; // creates a toggle to flip if user hits new high score during game

	public Score(){
		score = 0;
		increment = 0;  //how many points for eating a kibble. Changed to zero here because adjustScoreIncrement's extra points begin at 1 anyway
	}

	public static void resetScore() {
		score = 0;
	}

	public static void increaseScore() {
		score = score + increment;
	}

	public static void adjustScoreIncrement() {
		// more points if blocks on, warp wall off, large gameboard, faster speed, and as a percentage of the length of the snake
		int extraPoints = 0;
		if (!Snake.warpWallOn) {
			extraPoints++;
		}
		if (Block.blocksOn) {
			extraPoints++;
		}
		if (Snake.snakeGrowsQuickly) {
			extraPoints++;
		}
		if (Snake.snakeGrowsReallyQuickly) {
			extraPoints+=3;
		}
		// increment equals all the bonuses, starting at a minimum of 1. This decreases if user switches to easier modes. Bigness and Fastness are reduced by 1 each here to keep the amount of increase down; it could be pumped up for a really high-scoring game
		increment = extraPoints + SnakeGame.getBigness()-1 + SnakeGame.getFastness()-1 + Snake.snakeSize/10 ;
	}


	public int getScore(){
		return score;
	}

	//Checks if current score is greater than the current high score. 
	//updates high score and returns true if so.

	public boolean gameOver(){

		if (score > highScore) {
			highScore = score;
			return true;
		}
		return false;
	}

	//These methods are useful for displaying score at the end of the game

	public String getStringScore() {
		return Integer.toString(score);
	}

// updated method returns true if user gets new high score in middle of game, as well as updating int highScore.
	public boolean isThereNewHighScore() {
		if (score > highScore) {
			highScore = score;
			newHighScore = true;
		}
		return newHighScore;
	}

	public String getStringHighScore() {
		return Integer.toString(highScore);
	}

	public static int getIncrement() {
		return increment;
	}

}

