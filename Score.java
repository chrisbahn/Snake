
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
		increment = 1;  //how many points for eating a kibble
		//Possible TODO get more points for eating kibbles, the longer the snake gets?
	}

	public static void resetScore() {
		score = 0;
	}

	public static void increaseScore() {

		score = score + increment;

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

}

