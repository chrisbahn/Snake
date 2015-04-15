
import java.util.Timer;

import javax.swing.*;


public class SnakeGame {

	public final static int xPixelMaxDimension = 601;  //Pixels in window. 501 to have 50-pixel squares plus 1 to draw a border on last square
	public final static int yPixelMaxDimension = 601;

	public static int xSquares ;
	public static int ySquares ;

	// TODO this variable was originally 50. it controls the size of the individual squares. Create a toggle for small/medium/large gameboard that changes squareSize, xPixelMaxDimension and yPixelMaxDimension
	public final static int squareSize = 10; // This number must be a divisor of xPixelMaxDimension and yPixelMaxDimension, or the edge squares are unusable

	protected static Snake snake ;

	protected static Kibble kibble;

	protected static Score score;

	static final int BEFORE_GAME = 1;
	static final int DURING_GAME = 2;
	static final int GAME_OVER = 3;
	static final int GAME_WON = 4;   //The values are not important. The important thing is to use the constants 
	//instead of the values so you are clear what you are setting. Easy to forget what number is Game over vs. game won
	//Using constant names instead makes it easier to keep it straight. Refer to these variables 
	//using statements such as SnakeGame.GAME_OVER 

	private static int gameStage = BEFORE_GAME;  //use this to figure out what should be happening. 
	//Other classes like Snake and DrawSnakeGamePanel will need to query this, and change it's value

	// TODO clockInterval controls game speed. Default will be 400. Create a toggle under GameControls that will call howFast() in SnakeGame, which will port between slow/medium/fast/crazy speeds
	protected static long clockInterval = 400; //controls game speed
//	public static boolean speedFast = false; TODO One way to signify speed changes is to use booleans with names that evoke what state is happening. Another possibility is for the toggle in howFast() to simply change clockInterval.
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

		snakeFrame.setSize(xPixelMaxDimension, yPixelMaxDimension);
		snakeFrame.setUndecorated(true); //hide title bar
		snakeFrame.setVisible(true);
		snakeFrame.setResizable(false);

		snakePanel = new DrawSnakeGamePanel(snake, kibble, score);
		snakePanel.setFocusable(true);
		snakePanel.requestFocusInWindow(); //required to give this component the focus so it can generate KeyEvents

		snakeFrame.add(snakePanel);
		snakePanel.addKeyListener(new GameControls(snake));

		setGameStage(BEFORE_GAME);

		snakeFrame.setVisible(true);
	}

	private static void initializeGame() {
		//set up score, snake and first kibble
		xSquares = xPixelMaxDimension / squareSize;
		ySquares = yPixelMaxDimension / squareSize;

		snake = new Snake(xSquares, ySquares, squareSize);
		kibble = new Kibble(snake);
		score = new Score();

		gameStage = BEFORE_GAME;
	}

	protected static void newGame() {
		Timer timer = new Timer();
		GameClock clockTick = new GameClock(snake, kibble, score, snakePanel);
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
			GameClock clockTick = new GameClock(snake, kibble, score, snakePanel); // TODO If/when you implement Blocks, that variable needs to be added here. Same goes for the rest of this set.
			timer.scheduleAtFixedRate(clockTick, clockInterval , clockInterval);
		} else if (clockInterval == 400) {
			clockInterval = 300;
			System.out.println("clockInterval = 300. Medium-fast!");
			System.out.println("clockInterval = " + clockInterval);
			// Following code makes clockInterval change affect Timer and GameClock.
			Timer timer = new Timer();
			GameClock clockTick = new GameClock(snake, kibble, score, snakePanel);
			timer.scheduleAtFixedRate(clockTick, clockInterval , clockInterval);
		} else if (clockInterval == 300) {
			clockInterval = 200;
			System.out.println("clockInterval = 200. Fast!");
			System.out.println("clockInterval = " + clockInterval);
			// Following code makes clockInterval change affect Timer and GameClock.
			Timer timer = new Timer();
			GameClock clockTick = new GameClock(snake, kibble, score, snakePanel);
			timer.scheduleAtFixedRate(clockTick, clockInterval , clockInterval);
		} else if (clockInterval == 200) {
			clockInterval = 100;
			System.out.println("clockInterval = 100. Very fast!");
			System.out.println("clockInterval = " + clockInterval);
			// Following code makes clockInterval change affect Timer and GameClock.
			Timer timer = new Timer();
			GameClock clockTick = new GameClock(snake, kibble, score, snakePanel);
			timer.scheduleAtFixedRate(clockTick, clockInterval , clockInterval);
		} else if (clockInterval == 100) {
			clockInterval = 50;
			System.out.println("clockInterval = 50. INSANELY fast!");
			System.out.println("clockInterval = " + clockInterval);
			// Following code makes clockInterval change affect Timer and GameClock.
			Timer timer = new Timer();
			GameClock clockTick = new GameClock(snake, kibble, score, snakePanel);
			timer.scheduleAtFixedRate(clockTick, clockInterval , clockInterval);
		} else if (clockInterval == 50) {
			clockInterval = 500;
			System.out.println("clockInterval = 500. Slow!");
			System.out.println("clockInterval = " + clockInterval);
			// Following code makes clockInterval change affect Timer and GameClock.
			Timer timer = new Timer();
			GameClock clockTick = new GameClock(snake, kibble, score, snakePanel);
			timer.scheduleAtFixedRate(clockTick, 0 , clockInterval);
	}
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
