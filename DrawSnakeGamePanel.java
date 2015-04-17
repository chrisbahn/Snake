
import java.awt.*;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.JPanel;

/** This class responsible for displaying the graphics, so the snake, grid, kibble, instruction text and high score
 *
 * @author Clara
 *
 */
public class DrawSnakeGamePanel extends JPanel {

	private static int gameStage = SnakeGame.BEFORE_GAME;  //use this to figure out what to paint
	// These two variables aid in centering text and graphics during the non-game portions of the program (displaying endgame results, etc.)
	int screenXCenter = (int) SnakeGame.xPixelMaxDimension/2;  //Cast just in case we have an odd number
	int screenYCenter = (int) SnakeGame.yPixelMaxDimension/2;  //Cast just in case we have an odd number

	private Snake snake;
	private Kibble kibble;
	private Block block;
	private Score score;

	DrawSnakeGamePanel(Snake s, Kibble k, Block block, Score sc){
		this.snake = s;
		this.kibble = k;
		this.block = block;
		this.score = sc;
		//set the background color to black
		Color backgroundColor = new Color(0, 150, 84);
		setBackground(backgroundColor);

	}

	// This creates a two-part visual display: The game on top, and a bottom display panel for scores and etc.
	public Dimension getPreferredSize() {
		return new Dimension(SnakeGame.xPixelMaxDimension, SnakeGame.yPixelMaxDimension+SnakeGame.bottomPanelHeight);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

        /* Where are we at in the game? 4 phases.. 
         * 1. Before game starts
         * 2. During game
         * 3. Game lost aka game over
         * 4. or, game won
         */

		gameStage = SnakeGame.getGameStage();

		switch (gameStage) {
			case 1: {
				displayInstructions(g);
				break;
			}
			case 2 : {
				displayGame(g);
				break;
			}
			case 3: {
				displayGameOver(g);
				break;
			}
			case 4: {
				displayGameWon(g);
				break;
			}
		}



	}

	private void displayGameWon(Graphics g) {
		// TODO Replace this with something really special!
		String textScore = score.getStringScore();
		String textHighScore = score.getStringHighScore();
		Color innerPanelColor = new Color(50, 150, 84);
		g.setColor(innerPanelColor);
		g.fillRect(15, 15, SnakeGame.xPixelMaxDimension - 30, SnakeGame.yPixelMaxDimension - 15);
		g.setColor(Color.YELLOW);
		g.setFont(new Font("default", Font.BOLD, g.getFont().getSize() + 50));
		g.drawString("YOU WON SNAKE!!!", (SnakeGame.xPixelMaxDimension / 2) - 150, (SnakeGame.yPixelMaxDimension / 2) - 150);
		g.setFont(new Font("default", Font.BOLD, g.getFont().getSize() - 45));
		g.setColor(Color.WHITE);
		g.drawString("SCORE: " + textScore, (SnakeGame.xPixelMaxDimension / 2) - 150, (SnakeGame.yPixelMaxDimension / 2) - 75);
		g.drawString("HIGH SCORE: " + textHighScore, (SnakeGame.xPixelMaxDimension / 2) - 150, (SnakeGame.yPixelMaxDimension / 2) - 50);
		g.setFont(new Font("default", Font.PLAIN, g.getFont().getSize() - 5));
		// Displays only if user got a new high score this round
		if (score.isThereNewHighScore()) {
			g.setFont(new Font("default", Font.BOLD, g.getFont().getSize() + 10));
			g.setColor(Color.YELLOW);
			g.drawString("NEW HIGH SCORE!", (SnakeGame.xPixelMaxDimension / 2) - 150, (SnakeGame.yPixelMaxDimension / 2) - 15);
			g.setFont(new Font("default", Font.PLAIN, g.getFont().getSize() - 10));
			g.setColor(Color.WHITE);
		}
		g.drawString("Press any key to play again!", (SnakeGame.xPixelMaxDimension / 2) - 150, (SnakeGame.yPixelMaxDimension / 2)+25);
		g.drawString("or 'q' to quit", (SnakeGame.xPixelMaxDimension / 2) - 150, (SnakeGame.yPixelMaxDimension / 2) +50);

		displayBottomPanel(g);

	}
	private void displayGameOver(Graphics g) {
		String textScore = score.getStringScore();
		String textHighScore = score.getStringHighScore();

		Color innerPanelColor = new Color(50, 150, 84);
		g.setColor(innerPanelColor);
		g.fillRect(15, 15, SnakeGame.xPixelMaxDimension - 30, SnakeGame.yPixelMaxDimension - 15);
		g.setColor(Color.YELLOW);
		g.setFont(new Font("default", Font.BOLD, g.getFont().getSize() + 30));
		g.drawString("GAME OVER", (SnakeGame.xPixelMaxDimension / 2) - 150, (SnakeGame.yPixelMaxDimension / 2) - 150);
		g.setFont(new Font("default", Font.BOLD, g.getFont().getSize() - 25));
		g.setColor(Color.WHITE);
		g.drawString("SCORE: " + textScore, (SnakeGame.xPixelMaxDimension / 2) - 150, (SnakeGame.yPixelMaxDimension / 2) - 100);
		g.drawString("HIGH SCORE: " + textHighScore, (SnakeGame.xPixelMaxDimension / 2) - 150, (SnakeGame.yPixelMaxDimension / 2) - 75);
		g.setFont(new Font("default", Font.PLAIN, g.getFont().getSize() - 5));
		// Displays only if user got a new high score this round
		if (score.isThereNewHighScore()) {
			g.setFont(new Font("default", Font.BOLD, g.getFont().getSize() + 10));
			g.setColor(Color.YELLOW);
			g.drawString("NEW HIGH SCORE!", (SnakeGame.xPixelMaxDimension / 2) - 150, (SnakeGame.yPixelMaxDimension / 2) - 40);
			g.setFont(new Font("default", Font.PLAIN, g.getFont().getSize() - 10));
			g.setColor(Color.WHITE);
		}
		g.drawString("Press any key to play again!", (SnakeGame.xPixelMaxDimension / 2) - 150, (SnakeGame.yPixelMaxDimension / 2));
		g.drawString("or 'q' to quit", (SnakeGame.xPixelMaxDimension / 2) - 150, (SnakeGame.yPixelMaxDimension / 2) +25);

		displayBottomPanel(g);

	}

	private void displayGame(Graphics g) {
		displayGameGrid(g);
		displaySnake(g);
		displayKibble(g);
		displayBlock(g);
		displayBottomPanel(g);
	}

	private void displayGameGrid(Graphics g) {

		int maxX = SnakeGame.xPixelMaxDimension;
		int maxY= SnakeGame.yPixelMaxDimension;
		int squareSize = SnakeGame.squareSize;

		g.clearRect(0, 0, maxX, maxY);

		g.setColor(Color.lightGray);

		//Draw grid - horizontal lines
		for (int y=0; y <= maxY ; y+= squareSize){
			g.drawLine(0, y, maxX, y);
		}
		//Draw grid - vertical lines
		for (int x=0; x <= maxX ; x+= squareSize){
			g.drawLine(x, 0, x, maxY);
		}
	}

	private void displayKibble(Graphics g) {
		//Draw the kibble in random flashing color
		int colorA = randInt(0, 255);
		int colorB = randInt(0, 255);
		int colorC = randInt(0, 255);
		Color kibbleColor = new Color(colorA, colorB, colorC);
		g.setColor(kibbleColor);
		int x = kibble.getKibbleX() * SnakeGame.squareSize;
		int y = kibble.getKibbleY() * SnakeGame.squareSize;
		g.fillRect(x+1, y+1, SnakeGame.squareSize-2, SnakeGame.squareSize-2);
	}

	private void displayBlock(Graphics g) {
		//draw wall in black
		Color blockColor = new Color(0, 0, 0);
		g.setColor(blockColor);
		//get position of wall and multiply it by square size (to make as big as grid square)
		if (Block.blocksOn) {
			for (int i = 0; i < Block.howManyBlocks; i++) {
					int x = block.blocksListX.get(i) * SnakeGame.squareSize;
					int y = block.blocksListY.get(i) * SnakeGame.squareSize;
					g.fillRect(x, y, SnakeGame.squareSize, SnakeGame.squareSize);
				}
			}
		}


	private void displayBottomPanel(Graphics g) {
		Color innerPanelColor = new Color(50, 150, 84);
		g.setColor(innerPanelColor);
		g.fillRect(10, SnakeGame.yPixelMaxDimension + 10, SnakeGame.xPixelMaxDimension - 20, 130);
		g.setColor(Color.WHITE);
		// The following code allows score, etc. to be updated on screen in real time during game
		String textScore = score.getStringScore();
		String textHighScore = score.getStringHighScore();
		String warpStatus = snake.isWarpWallOn();
		String blockStatus = block.areBlocksOn();
		String gamesizeStatus = (SnakeGame.getSizeOfGame());
		String speedStatus = (SnakeGame.getSpeedOfGame());
		int snakeSize = snake.getSnakeSize();
		String growthRate = snake.whatIsSnakeGrowthRate();
		int scoreIncrement = Score.getIncrement();

		g.drawString("Score: " + textScore ,20,SnakeGame.yPixelMaxDimension+60);
		if (!score.isThereNewHighScore()) {
			g.drawString("High score: " + textHighScore,20,SnakeGame.yPixelMaxDimension+80);
			// Displays only if user got a new high score this round
		} else if (score.isThereNewHighScore()) {
			g.setColor(Color.YELLOW);
			g.setFont(new Font("default", Font.BOLD, g.getFont().getSize()));
			g.drawString("NEW HIGH SCORE!", 20, SnakeGame.yPixelMaxDimension + 80);
			g.setFont(new Font("default", Font.PLAIN, g.getFont().getSize()));
			g.setColor(Color.WHITE);
		}
		g.drawString("Snake length: " +  snakeSize,20,SnakeGame.yPixelMaxDimension+100);
		g.drawString("Points per kibble: " +  scoreIncrement,20,SnakeGame.yPixelMaxDimension+120);
		g.drawString("Warp walls (w): " +  warpStatus,150,SnakeGame.yPixelMaxDimension+40);
		g.drawString("Blocks (b): " +  blockStatus,150,SnakeGame.yPixelMaxDimension+60);
		g.drawString("Board size (1-5): " +  gamesizeStatus,150,SnakeGame.yPixelMaxDimension+80);
		g.drawString("Speed (s): " +  speedStatus,150,SnakeGame.yPixelMaxDimension+100);
		g.drawString("Snake growth rate (g): " +  growthRate,150,SnakeGame.yPixelMaxDimension+120);
	}

	private void displaySnake(Graphics g) {
		LinkedList<Point> coordinates = snake.segmentsToDraw();

		//Draw head in flashing neon
		Color headSegmentColor = new Color(randInt(0, 255), randInt(0, 255), randInt(0, 255));
		g.setColor(headSegmentColor);
	//	g.setColor(Color.LIGHT_GRAY);
		Point head = coordinates.pop();
		g.fillRect((int)head.getX(), (int)head.getY(), SnakeGame.squareSize, SnakeGame.squareSize);

		//This sets the initial color of the snake body, which currently is the same as the dark green background of the panel
		int colorA = 0;
		int colorB = 150;
		int colorC = 84;
		Color bodySegmentColor = new Color(colorA, colorB, colorC);
//	The following line creates a snake whose entire body flashes in neon. A cool effect for some temporary event, perhaps
//		Color bodySegmentColor = new Color(randInt(0,255), randInt(0,255), randInt(0,255));
		// TODO can you change the snake colors to whatever kibbleColor is at the time it's eaten?
//		if (snake.didEatKibble(kibble)) {
//			bodySegmentColor = new Color(randInt(0,255), randInt(0,255), randInt(0,255));
//			g.setColor(bodySegmentColor);
//		}

// The colorup booleans control whether the color is iterating up or down. colorincrement controls how quickly it does so.
		boolean colorAUp = false;
		boolean colorBUp = false;
		boolean colorCUp = true;
		int colorAIncrement = 5;
		int colorBIncrement = 7;
		int colorCIncrement = 3;
		// the settings below create a snake that is solid-colored at first, but increasingly begins to flash in different colors the longer it gets.
//		int colorAIncrement = randInt(0,1);
//		int colorBIncrement = randInt(0,1);
//		int colorCIncrement = randInt(0,1);

		for (Point p : coordinates) {
			bodySegmentColor = new Color(colorA, colorB, colorC);
			g.setColor(bodySegmentColor);
			g.fillRect((int) p.getX(), (int) p.getY(), SnakeGame.squareSize, SnakeGame.squareSize);
		// These if statements determine how colorA,B,C increase/decrease
			if (colorAUp) {
				colorA += colorAIncrement;
				if (colorA > 255) {
					colorA -= (2*colorAIncrement);
					colorAUp = false;
				}
			}
			if (!colorAUp) {
				colorA -= colorAIncrement;
				if (colorA < 0) {
					colorA += (2*colorAIncrement);
					colorAUp = true;
				}
			}
			if (colorBUp) {
				colorB += colorBIncrement;
				if (colorB > 255) {
					colorB -= (2*colorBIncrement);
					colorBUp = false;
				}
			}
			if (!colorBUp) {
				colorB -= colorBIncrement;
				if (colorB < 0) {
					colorB += (2*colorBIncrement);
					colorBUp = true;
				}
			}
			if (colorCUp) {
				colorC += colorCIncrement;
				if (colorC > 255) {
					colorC -= (2*colorCIncrement);
					colorCUp = false;
				}
			}
			if (!colorCUp) {
				colorC -= colorCIncrement;
				if (colorC < 0) {
					colorC += (2*colorCIncrement);
					colorCUp = true;
				}
			}
		}

	}



	public static int randInt(int min, int max) {
		Random rand = new Random();
		int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
	}

	private void displayInstructions(Graphics g) {
		Color innerPanelColor = new Color(50, 150, 84);
		g.setColor(innerPanelColor);
		g.fillRect(15, 15, SnakeGame.xPixelMaxDimension - 30, SnakeGame.yPixelMaxDimension - 15);
		g.setColor(Color.YELLOW);
		g.setFont(new Font("default", Font.BOLD, g.getFont().getSize() + 30));
		g.drawString("SNAKE II", 150, 135);
		g.setFont(new Font("default", Font.PLAIN, g.getFont().getSize() - 30));
		g.drawString("THE REVENGE OF SNAKE I", 160, 165);
		g.setColor(Color.WHITE);
		g.drawString("Press any key to begin!", 175, 200);
		g.setFont(new Font("default", Font.BOLD, g.getFont().getSize()));
		g.drawString("Game controls:", 50, 275);
		g.setFont(new Font("default", Font.PLAIN, g.getFont().getSize()));
		g.drawString("(Difficult settings earn more points!)", 100, 295);
		g.drawString("Press 'w' to toggle warp walls on/off", 50, 315);
		g.drawString("Press 'b' to toggle blocks on/off", 50, 335);
		g.drawString("Press 's' to cycle through game speeds", 50, 355);
		g.drawString("(very slow to insanely fast!)", 100, 375);
		g.drawString("Press 1, 2, 3, 4 or 5 to choose a gameboard size", 50, 395);
		g.drawString("(small/medium/large/enormous/gargantuan!)", 100, 415);
		g.drawString("Press 's' to change snake growth rate", 50, 435);
		g.drawString("(normal, fast, or alarming!)", 100, 455);
		g.drawString("Press 'q' to quit the game", 50, 475);
// To write something in the bottom panel adapt the code below and use yPixelMaxDimension+10 to yPixelMaxDimension+140
//		g.drawString("This is test text 1!",25,);
		g.setColor(innerPanelColor);
		g.fillRect(15, SnakeGame.yPixelMaxDimension + 15, SnakeGame.xPixelMaxDimension - 30, 120);
		g.setFont(new Font("default", Font.ITALIC, g.getFont().getSize()));
		g.setColor(Color.WHITE);
		g.drawString("Round and round they went with their snakes, snakily...", 75, SnakeGame.yPixelMaxDimension + 70);
		g.setFont(new Font("default", Font.PLAIN, g.getFont().getSize()));
		g.drawString("― Aldous Huxley, Brave New World",100,SnakeGame.yPixelMaxDimension+90);

	}


}

