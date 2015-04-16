
import java.util.Timer;

import javax.swing.*;


public class SnakeGame {

	// The variables below control the size of the gameboard and the individual squares. I deleted "final" from these variables and squareSize so they can be changed in the "howBig" method.
	public static int xPixelMaxDimension = 501;  //Pixels in window. 501 to have 50-pixel squares plus 1 to draw a border on last square
	public static int yPixelMaxDimension = 501;
	public static int bottomPanelHeight = 150;
	public static int squareSize = 25; // pixels per square. TODO It controls the size of the individual squares. It must be a divisor of xPixelMaxDimension and yPixelMaxDimension, or the edge squares are unusable. Also, if this value is changed from its initial declaration, it messes up the way the snake is drawn. Not sure why. Can that be fixed?
	public static int xSquares ; // these two variables are used below to help create gameboard; they are controlled by the values in the three variables above.
	public static int ySquares ;

	protected static Snake snake ;
	protected static Kibble kibble;
	protected static Score score;
	protected static Block block;

	static final int BEFORE_GAME = 1;
	static final int DURING_GAME = 2;
	static final int GAME_OVER = 3;
	static final int GAME_WON = 4;   //The values are not important. The important thing is to use the constants 
	//instead of the values so you are clear what you are setting. Easy to forget what number is Game over vs. game won
	//Using constant names instead makes it easier to keep it straight. Refer to these variables 
	//using statements such as SnakeGame.GAME_OVER 

	private static int gameStage = BEFORE_GAME;  //use this to figure out what should be happening. 
	//Other classes like Snake and DrawSnakeGamePanel will need to query this, and change it's value

	protected static long clockInterval = 400; //controls game speed; changed in the "howFast" method below. default is "slow"
	//Every time the clock ticks, the snake moves
	//This is the time between clock ticks, in milliseconds
	//1000 milliseconds = 1  second.

	static JFrame snakeFrame;
	static DrawSnakeGamePanel snakePanel;
	//Framework for this class adapted from the Java Swing Tutorial, FrameDemo and Custom Painting Demo. You should find them useful too.
	//http://docs.oracle.com/javase/tutorial/displayCode.html?code=http://docs.oracle.com/javase/tutorial/uiswing/examples/components/FrameDemoProject/src/components/FrameDemo.java
	//http://docs.oracle.com/javase/tutorial/uiswing/painting/step2.html

	private static void createAndShowGUI() {
		//Create and set up the window.
		snakeFrame = new JFrame();
		snakeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		snakeFrame.setSize(xPixelMaxDimension, yPixelMaxDimension+bottomPanelHeight); // TODO Adding text area below main game pane
		snakeFrame.setUndecorated(true); //hide title bar
		snakeFrame.setVisible(true);
		snakeFrame.setResizable(false);

		snakePanel = new DrawSnakeGamePanel(snake, kibble, block, score);
		snakePanel.setFocusable(true);
		snakePanel.requestFocusInWindow(); //required to give this component the focus so it can generate KeyEvents

		snakeFrame.add(snakePanel);
		snakePanel.addKeyListener(new GameControls(snake));

		setGameStage(BEFORE_GAME);

		snakeFrame.setVisible(true);
	}

	private static void initializeGame() {
		//set up score, snake and first kibble, and Block locations
		xSquares = xPixelMaxDimension / squareSize;
		ySquares = yPixelMaxDimension / squareSize;

		snake = new Snake(xSquares, ySquares, squareSize); // TODO MAYBE THIS IS THE KEY TO FIXING THE SIZING ISSUE. DECLARE A NEW SNAKE WHEN YOU CHANGE SIZE OF BOARD?
		block = new Block(snake);
		kibble = new Kibble(snake,block);
		score = new Score();

		gameStage = BEFORE_GAME;
	}

	protected static void newGame() {
		Timer timer = new Timer();
		GameClock clockTick = new GameClock(snake, kibble, block, score, snakePanel);
		timer.scheduleAtFixedRate(clockTick, 0 , clockInterval);
	}

	public static void main(String[] args) {
		//Schedule a job for the event-dispatching thread:
		//creating and showing this application's GUI.
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				initializeGame();
				createAndShowGUI();
			}
		});
	}

	//change speed
	public static void howFast() {
		if (clockInterval == 500) {
			clockInterval = 400;
			System.out.println("clockInterval = 400. Medium-slow!");
			System.out.println("clockInterval = " + clockInterval);
			// Following code makes clockInterval change affect Timer and GameClock.
			Timer timer = new Timer();
			GameClock clockTick = new GameClock(snake, kibble, block, score, snakePanel); // TODO If/when you implement Blocks, that variable needs to be added here. Same goes for the rest of this set.
			timer.scheduleAtFixedRate(clockTick, clockInterval , clockInterval);
		} else if (clockInterval == 400) {
			clockInterval = 300;
			System.out.println("clockInterval = 300. Medium-fast!");
			System.out.println("clockInterval = " + clockInterval);
			// Following code makes clockInterval change affect Timer and GameClock.
			Timer timer = new Timer();
			GameClock clockTick = new GameClock(snake, kibble, block, score, snakePanel);
			timer.scheduleAtFixedRate(clockTick, clockInterval , clockInterval);
		} else if (clockInterval == 300) {
			clockInterval = 200;
			System.out.println("clockInterval = 200. Fast!");
			System.out.println("clockInterval = " + clockInterval);
			// Following code makes clockInterval change affect Timer and GameClock.
			Timer timer = new Timer();
			GameClock clockTick = new GameClock(snake, kibble, block, score, snakePanel);
			timer.scheduleAtFixedRate(clockTick, clockInterval , clockInterval);
		} else if (clockInterval == 200) {
			clockInterval = 100;
			System.out.println("clockInterval = 100. Very fast!");
			System.out.println("clockInterval = " + clockInterval);
			// Following code makes clockInterval change affect Timer and GameClock.
			Timer timer = new Timer();
			GameClock clockTick = new GameClock(snake, kibble, block, score, snakePanel);
			timer.scheduleAtFixedRate(clockTick, clockInterval , clockInterval);
		} else if (clockInterval == 100) {
			clockInterval = 50;
			System.out.println("clockInterval = 50. INSANELY fast!");
			System.out.println("clockInterval = " + clockInterval);
			// Following code makes clockInterval change affect Timer and GameClock.
			Timer timer = new Timer();
			GameClock clockTick = new GameClock(snake, kibble, block, score, snakePanel);
			timer.scheduleAtFixedRate(clockTick, clockInterval , clockInterval);
		} else if (clockInterval == 50) {
			clockInterval = 500;
			System.out.println("clockInterval = 500. Slow!");
			System.out.println("clockInterval = " + clockInterval);
			// Following code makes clockInterval change affect Timer and GameClock.
			Timer timer = new Timer();
			GameClock clockTick = new GameClock(snake, kibble, block, score, snakePanel);
			timer.scheduleAtFixedRate(clockTick, 0 , clockInterval);
	}
	}

	//change size of screen
	public static void howBig(int bigness) {
		if (bigness == 1) { // a small game
			xPixelMaxDimension = 501;
			yPixelMaxDimension = 501;
//			squareSize = 50;
//			snake = new Snake(xSquares, ySquares, squareSize); // TODO Creating a new Snake here does not solve sizing issue. Keep trying
			System.out.println("bigness = 1. Small game!");
		}
		else if (bigness == 2) { // a medium game
			xPixelMaxDimension = 601;
			yPixelMaxDimension = 601;
//			squareSize = 25;
			System.out.println("bigness = 2. Medium game!");
		}
		else if (bigness == 3) { // a large game
			xPixelMaxDimension = 751;
			yPixelMaxDimension = 751;
//			squareSize = 25;
			System.out.println("bigness = 3. Large game!");
		}
		else if (bigness == 4) { // a very large game
			xPixelMaxDimension = 951;
			yPixelMaxDimension = 751;
//			squareSize = 10;
			System.out.println("bigness = 4. Enormous game!");
		}
		else if (bigness == 5) { // a very large game
			xPixelMaxDimension = 1201;
			yPixelMaxDimension = 751;
//			squareSize = 5;
			System.out.println("bigness = 5. GARGANTUAN game!");
		}

		//changes above are used here to set game size
		snakeFrame.setSize(xPixelMaxDimension, yPixelMaxDimension);
		xSquares = xPixelMaxDimension / squareSize;
		ySquares = yPixelMaxDimension / squareSize;
		snake.setMaxX(xPixelMaxDimension / squareSize);
		snake.setMaxY(yPixelMaxDimension / squareSize);
		snake.setSnakeSquares(new int[xPixelMaxDimension / squareSize][yPixelMaxDimension / squareSize]);

	}

	public static int getGameStage() {
		return gameStage;
	}

	// TODO IF this is never used, why is it here?
	public static boolean gameEnded() {
		if (gameStage == GAME_OVER || gameStage == GAME_WON){
			return true;
		}
		return false;
	}

	public static void setGameStage(int gameStage) {
		SnakeGame.gameStage = gameStage;
	}
}
