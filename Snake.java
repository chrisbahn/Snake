
import java.awt.Point;
import java.util.LinkedList;

public class Snake {

	final int DIRECTION_UP = 0;
	final int DIRECTION_DOWN = 1;
	final int DIRECTION_LEFT = 2;
	final int DIRECTION_RIGHT = 3;  //These are completely arbitrary numbers. 

	private boolean hitWall = false;
	private boolean ateTail = false;

	private boolean snakeFillsSpace = false; // TODO TEST

	private int snakeSquares[][];  //represents all of the squares on the screen
	//NOT pixels!
	//A 0 means there is no part of the snake in this square
	//A non-zero number means part of the snake is in the square
	//The head of the snake is 1, rest of segments are numbered in order

	private int currentHeading;  //Direction snake is going in, ot direction user is telling snake to go
	private int lastHeading;    //Last confirmed movement of snake. See moveSnake method

	private int snakeSize;   //size of snake - how many segments?

	//TODO ORIGINALLY 2; CREATE TOGGLE FOR FOR BEGINNER/EXPERT SETTING? Make one version where growthIncrement goes up 1 or more with each new kibble eaten
	private int growthIncrement = 2; //how many squares the snake grows after it eats a kibble

	protected int justAteMustGrowThisMuch = 0;

	private int maxX, maxY, snakeSquareSize;
	private int snakeHeadX, snakeHeadY; //store coordinates of head - first segment

	private static boolean warpWallOn = false; // variable that sets Warp Wall on or off


	// Constructor for Snake
	public Snake(int maxX, int maxY, int snakeSquareSize){
		this.maxX = maxX;
		this.maxY = maxY;
		this.snakeSquareSize = snakeSquareSize;
//		this.snakeSquareSize = SnakeGame.squareSize; TODO IS THIS VALUE BETTER?
		//Create and fill snakeSquares with 0s
		snakeSquares = new int[maxX][maxY];
		fillSnakeSquaresWithZeros();
		createStartSnake();
	}

	protected void createStartSnake(){
		//snake starts as 3 horizontal squares in the center of the screen, moving left
		int screenXCenter = (int) maxX/2;  //Cast just in case we have an odd number
		int screenYCenter = (int) maxY/2;  //Cast just in case we have an odd number

		snakeSquares[screenXCenter][screenYCenter] = 1;
		snakeSquares[screenXCenter+1][screenYCenter] = 2;
		snakeSquares[screenXCenter+2][screenYCenter] = 3;

		snakeHeadX = screenXCenter;
		snakeHeadY = screenYCenter;

		snakeSize = 3;

		currentHeading = DIRECTION_LEFT;
		lastHeading = DIRECTION_LEFT;

		justAteMustGrowThisMuch = 0;
	}

	private void fillSnakeSquaresWithZeros() {
		for (int x = 0; x < this.maxX; x++){
			for (int y = 0 ; y < this.maxY ; y++) {
				snakeSquares[x][y] = 0;
			}
		}
	}

	public LinkedList<Point> segmentsToDraw(){
		//Return a list of the actual x and y coordinates of the top left of each snake segment
		//Useful for the Panel class to draw the snake
		LinkedList<Point> segmentCoordinates = new LinkedList<Point>();
		for (int segment = 1 ; segment <= snakeSize ; segment++ ) {
			//search array for each segment number
			for (int x = 0 ; x < maxX ; x++) {
				for (int y = 0 ; y < maxY ; y++) {
					if (snakeSquares[x][y] == segment){
						//make a Point for this segment's coordinates and add to list
						Point p = new Point(x * snakeSquareSize , y * snakeSquareSize);
						segmentCoordinates.add(p);
						// System.out.println("coordinates: " + x * snakeSquareSize + " " + y * snakeSquareSize);
					}
				}
			}
		}
		return segmentCoordinates;

	}

	public void snakeUp(){
		if (currentHeading == DIRECTION_UP || currentHeading == DIRECTION_DOWN) { return; }
		currentHeading = DIRECTION_UP;
	}
	public void snakeDown(){
		if (currentHeading == DIRECTION_DOWN || currentHeading == DIRECTION_UP) { return; }
		currentHeading = DIRECTION_DOWN;
	}
	public void snakeLeft(){
		if (currentHeading == DIRECTION_LEFT || currentHeading == DIRECTION_RIGHT) { return; }
		currentHeading = DIRECTION_LEFT;
	}
	public void snakeRight(){
		if (currentHeading == DIRECTION_RIGHT || currentHeading == DIRECTION_LEFT) { return; }
		currentHeading = DIRECTION_RIGHT;
	}

//	public void	eatKibble(){
//		//record how much snake needs to grow after eating food
//		justAteMustGrowThisMuch += growthIncrement;
//	}

	protected void moveSnake(){
		//Called every clock tick

		//Must check that the direction snake is being sent in is not contrary to current heading
		//So if current heading is down, and snake is being sent up, then should ignore.
		//Without this code, if the snake is heading up, and the user presses left then down quickly, the snake will back into itself.
		if (currentHeading == DIRECTION_DOWN && lastHeading == DIRECTION_UP) {
			currentHeading = DIRECTION_UP; //keep going the same way
		}
		if (currentHeading == DIRECTION_UP && lastHeading == DIRECTION_DOWN) {
			currentHeading = DIRECTION_DOWN; //keep going the same way
		}
		if (currentHeading == DIRECTION_LEFT && lastHeading == DIRECTION_RIGHT) {
			currentHeading = DIRECTION_RIGHT; //keep going the same way
		}
		if (currentHeading == DIRECTION_RIGHT && lastHeading == DIRECTION_LEFT) {
			currentHeading = DIRECTION_LEFT; //keep going the same way
		}

		//Did you hit the wall, snake? 
		//Or eat your tail? Don't move. 

		if (hitWall == true || ateTail == true) {
			SnakeGame.setGameStage(SnakeGame.GAME_OVER);
			return;
		}

		// TODO TEST BAHN If snake filled the screen, you win.
		if (wonGame()) {
			SnakeGame.setGameStage(SnakeGame.GAME_WON);
			System.out.println("YOU DID IT!");
			return;
		}


		//Use snakeSquares array, and current heading, to move snake

		//Put a 1 in new snake head square
		//increase all other snake segments by 1
		//set tail to 0 if snake did not just eat
		//Otherwise leave tail as is until snake has grown the correct amount 

		//Find the head of the snake - snakeHeadX and snakeHeadY

		//Increase all snake segments by 1
		//All non-zero elements of array represent a snake segment

		for (int x = 0 ; x < maxX ; x++) {
			for (int y = 0 ; y < maxY ; y++){
				if (snakeSquares[x][y] != 0) {
					snakeSquares[x][y]++;
				}
			}
		}

		//now identify where to add new snake head
		if (currentHeading == DIRECTION_UP) {
			//Subtract 1 from Y coordinate so head is one square up
			snakeHeadY-- ;
		}
		if (currentHeading == DIRECTION_DOWN) {
			//Add 1 to Y coordinate so head is 1 square down
			snakeHeadY++ ;
		}
		if (currentHeading == DIRECTION_LEFT) {
			//Subtract 1 from X coordinate so head is 1 square to the left
			snakeHeadX -- ;
		}
		if (currentHeading == DIRECTION_RIGHT) {
			//Add 1 to X coordinate so head is 1 square to the right
			snakeHeadX ++ ;
		}

		//Does this make snake hit the wall?
		// IF/ELSE settings for WARP WALL
		if (warpWallOn) {
			//Snake hits edge of screen and teleports to the other side
			//left side to right side
			if (snakeHeadX < 0) {
				snakeHeadX = maxX - 1;
			}
			//right to left
			if (snakeHeadX == maxX) {
				snakeHeadX = 0;
			}
			//top to bottom
			if (snakeHeadY < 0) {
				snakeHeadY = maxY - 1;
			}
			//bottom to top
			if (snakeHeadY == maxY) {
				snakeHeadY = 0;
			}
		}
		if (!warpWallOn) {
	//Snake hits brick wall outsides and dies
			if (snakeHeadX >= maxX || snakeHeadX < 0 || snakeHeadY >= maxY || snakeHeadY < 0 ) {
				hitWall = true;
				SnakeGame.setGameStage(SnakeGame.GAME_OVER);
				return;
			}
		}

		// Did Snake hit the inner blocks?
		if (Block.blocksOn) {
			//Snake hits brick wall insides and dies
			if (snakeHeadX == Block.getBlock1X() && snakeHeadY == Block.getBlock1Y()) {
				hitWall = true;
				System.out.println("You hit a block!");
				SnakeGame.setGameStage(SnakeGame.GAME_OVER);
				return;
			}
		}



		//Does this make the snake eat its tail?
		if (snakeSquares[snakeHeadX][snakeHeadY] != 0) {
			ateTail = true;
			SnakeGame.setGameStage(SnakeGame.GAME_OVER);
			return;
		}
		//Otherwise, game is still on. Add new head
		snakeSquares[snakeHeadX][snakeHeadY] = 1;

		//If snake did not just eat, then remove tail segment
		//to keep snake the same length.
		//find highest number, which should now be the same as snakeSize+1, and set to 0
		if (justAteMustGrowThisMuch == 0) {
			for (int x = 0 ; x < maxX ; x++) {
				for (int y = 0 ; y < maxY ; y++){
					if (snakeSquares[x][y] == snakeSize+1) {
						snakeSquares[x][y] = 0;
					}
				}
			}
		}
		else {
			//Snake has just eaten. leave tail as is.  Decrease justAte... variable by 1.
			justAteMustGrowThisMuch -- ;
			snakeSize ++;
		}

		lastHeading = currentHeading; //Update last confirmed heading

	}

	protected boolean didHitWall(){
		return hitWall;

	}

	protected boolean didEatTail(){
		return ateTail;
	}

	public boolean isSnakeSegment(int kibbleX, int kibbleY) {
		if (snakeSquares[kibbleX][kibbleY] == 0) {
			return false;
		}
		return true;
	}

	public boolean didEatKibble(Kibble kibble) {
		//Is this kibble in the snake? It should be in the same square as the snake's head
		if (kibble.getKibbleX() == snakeHeadX && kibble.getKibbleY() == snakeHeadY){
			justAteMustGrowThisMuch += growthIncrement;
			growthIncrement++; // TODO Put an if loop around this so an advanced version of the game increases growthIncrement, but basic does not
			System.out.println("growthIncrement = " + growthIncrement);
			return true;
		}
		return false;
	}

	public String toString(){
		String textsnake = "";
		//This looks the wrong way around. Actually need to do it this way or snake is drawn flipped 90 degrees. 
		for (int y = 0 ; y < maxY ; y++) {
			for (int x = 0 ; x < maxX ; x++){
				textsnake = textsnake + snakeSquares[x][y];
			}
			textsnake += "\n";
		}
		return textsnake;
	}

	public boolean wonGame() {

		//If all of the squares have snake segments in, the snake has eaten so much kibble 
		//that it has filled the screen. Win!
		for (int x = 0 ; x < maxX ; x++) {
			for (int y = 0 ; y < maxY ; y++){
				if (snakeSquares[x][y] == 0) {
					//there is still empty space on the screen, so haven't won
				//	System.out.println("Snake hasn't filled screen");
					return false;
				}
			}
		}
		//But if we get here, the snake has filled the screen. win!
		System.out.println("Snake STILL hasn't filled screen");
		SnakeGame.setGameStage(SnakeGame.GAME_WON);
		System.out.println("Snake HAS filled screen");
		return true;
	}

	public void reset() {
		hitWall = false;
		ateTail = false;
		fillSnakeSquaresWithZeros();
		createStartSnake();

	}

	public boolean isGameOver() {
		if (hitWall == true || ateTail == true){
			SnakeGame.setGameStage(SnakeGame.GAME_OVER);
			return true;
		}
		return false;
	}

	//toggles the WarpWallOn variable when key 'w' is pressed
	public static void warpWall() {
		if (warpWallOn) {
			System.out.println("Warp Wall OFF");
			warpWallOn = false;
		} else {
			System.out.println("Warp Wall ON");
			warpWallOn = true;
		}
	}

// these have to be used in SnakeGame.HowBig or the snake gets drawn weirdly
	public int getMaxX() {
		return maxX;
	}
	public void setMaxX(int maxX) {
		this.maxX = maxX;
	}
	public int getMaxY() {
		return maxY;
	}
	public void setMaxY(int maxY) {
		this.maxY = maxY;
	}
	public int getSnakeSquareSize() {
		return snakeSquareSize;
	}
	public void setSnakeSquareSize(int snakeSquareSize) {
		this.snakeSquareSize = snakeSquareSize;
	}
	public int[][] getSnakeSquares() {
		return snakeSquares;
	}
	public void setSnakeSquares(int[][] snakeSquares) {
		this.snakeSquares = snakeSquares;
	}
}


