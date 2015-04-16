
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
		Color backgroundColor = new Color(200, 200, 250);
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
		g.clearRect(100,100,350,350);
		g.drawString("YOU WON SNAKE!!!", 150, 150);

	}
	private void displayGameOver(Graphics g) {

		g.clearRect(screenXCenter-175,screenYCenter-175,350,350);
		g.drawString("GAME OVER", screenXCenter-50,screenYCenter-150);

		String textScore = score.getStringScore();
		String textHighScore = score.getStringHighScore();
		g.drawString("SCORE = " + textScore, screenXCenter-50,screenYCenter-100);

		g.drawString("HIGH SCORE = " + textHighScore, screenXCenter-50,screenYCenter-50);
		// Displays only if user got a new high score this round
		if (score.isThereNewHighScore()) {
			g.drawString("NEW HIGH SCORE!", screenXCenter-50,screenYCenter);
		}
		g.drawString("press a key to play again", screenXCenter-100,screenYCenter+100);
		g.drawString("Press q to quit the game",screenXCenter-100,screenYCenter+125);

	}

	private void displayGame(Graphics g) {
		displayGameGrid(g);
		displaySnake(g);
		displayKibble(g);
		displayBlock(g);

		// The following code allows score, etc. to be updated on screen in real time during game
		String textScore = score.getStringScore();
		String textHighScore = score.getStringHighScore();
		String warpStatus = (Snake.isWarpWallOn());
		String gamesizeStatus = (SnakeGame.getSizeOfGame());
		String speedStatus = (SnakeGame.getSpeedOfGame());
		int snakeSize = (Snake.getSnakeSize());
		String growthRate = (Snake.whatIsSnakeGrowthRate());

		g.drawString("Score: " + textScore ,10,SnakeGame.yPixelMaxDimension+25);
		if (!score.isThereNewHighScore()) {
		g.drawString("High score: " + textHighScore,10,SnakeGame.yPixelMaxDimension+45);
		// Displays only if user got a new high score this round
		} else if (score.isThereNewHighScore()) {
			g.drawString("NEW HIGH SCORE: " + textHighScore,10,SnakeGame.yPixelMaxDimension+45);
		}
		g.drawString("Warp walls (w): " +  warpStatus,10,SnakeGame.yPixelMaxDimension+65);
		g.drawString("Size (1-5): " +  gamesizeStatus,10,SnakeGame.yPixelMaxDimension+85);
		g.drawString("Speed (s): " +  speedStatus,10,SnakeGame.yPixelMaxDimension+105);
		g.drawString("Snake length: " +  snakeSize,10,SnakeGame.yPixelMaxDimension+125);
		g.drawString("Growth rate (g): " +  growthRate,10,SnakeGame.yPixelMaxDimension+145);

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
		int colorA = randInt(0,255);
		int colorB = randInt(0,255);
		int colorC = randInt(0,255);
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
			int x = block.getBlock1X() * SnakeGame.squareSize;
			int y = block.getBlock1Y() * SnakeGame.squareSize;
			g.fillRect(x, y, SnakeGame.squareSize, SnakeGame.squareSize);
		}
	}

	private void displaySnake(Graphics g) {
		LinkedList<Point> coordinates = snake.segmentsToDraw();

		//Draw head in flashing neon
		Color headSegmentColor = new Color(randInt(0,255), randInt(0,255), randInt(0,255));
		g.setColor(headSegmentColor);
	//	g.setColor(Color.LIGHT_GRAY);
		Point head = coordinates.pop();
		g.fillRect((int)head.getX(), (int)head.getY(), SnakeGame.squareSize, SnakeGame.squareSize);

		//Draw rest of snake in black
		// todo modify code to make snake body change color. SETTING all colorA,B,C to random CREATES NEON-FLASH SNAKE; THIS IS HARD ON THE EYES BUT MAKES A REALLY COOL EFFECT FOR SOME KIND OF TEMPORARY EFFECT, LIKE SOME KIND OF SUPERPELLET. Setting the color increments to random, with low numbers, creates a snake that gradually changes from a solid to a neon flash.
//		int colorA = randInt(0,255);
//		int colorB = randInt(0,255);
//		int colorC = randInt(0,255);
		int colorA = 250;
		int colorB = 0;
		int colorC = 0;

//		Color bodySegmentColor = new Color(colorA, colorB, colorC);
		Color bodySegmentColor = new Color(randInt(0,255), randInt(0,255), randInt(0,255));
		if (snake.didEatKibble(kibble)) {
			bodySegmentColor = new Color(randInt(0,255), randInt(0,255), randInt(0,255));
			g.setColor(bodySegmentColor);
		}
		// TODO If you can figure out how the game reports when a kibble is eaten, you can change the snake colors to whatever kibbleColor is at the time

// The colorup booleans control whether the color is iterating up or down. colorincrement controls how quickly it does so.
		boolean colorAUp = true;
		boolean colorBUp = true;
		boolean colorCUp = true;
		int colorAIncrement = 5;
		int colorBIncrement = 7;
		int colorCIncrement = 3;
		// the settings below create a snake that is solid-colored at first, but increasingly begins to flash in different colors the longer it gets.
//		int colorAIncrement = randInt(0,1);
//		int colorBIncrement = randInt(0,1);
//		int colorCIncrement = randInt(0,1);

		for (Point p : coordinates) {
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

			bodySegmentColor = new Color(colorA, colorB, colorC);
			g.setColor(bodySegmentColor);
			g.fillRect((int) p.getX(), (int) p.getY(), SnakeGame.squareSize, SnakeGame.squareSize);
		}

	}



	public static int randInt(int min, int max) {
		Random rand = new Random();
		int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
	}

	private void displayInstructions(Graphics g) {
		g.drawString("Welcome to SNAKE II: THE REVENGE OF SNAKE I",50,50);
		g.drawString("Round and round they went with their snakes, snakily...",50,100); // TODO Change this quote. Potential addition: Randomize quotes about snakes here, and as a lagniappe during displayGame?
		g.drawString("― Aldous Huxley, Brave New World",75,125);
		g.drawString("Press any key to begin!",50,200);
		g.drawString("Press 'w' to toggle warp walls on/off", 50, 250);
		g.drawString("Press 'b' to toggle blocks on/off", 50, 275);
		g.drawString("Press 's' to cycle through game speeds", 50, 300);
		g.drawString("(very slow to insanely fast!)", 100, 325);
		g.drawString("Press 1, 2, 3, 4 or 5 to choose a gameboard size", 50, 350);
		g.drawString("(small/medium/large/enormous/gargantuan!)", 100, 375);
		g.drawString("Press 's' to change snake growth rate", 50, 400);
		g.drawString("(normal, fast, or alarming!)", 100, 425);
		g.drawString("Press 'q' to quit the game",50,450);
// To write something in the bottom panel adapt the code below and use yPixelMaxDimension+10 to yPixelMaxDimension+140
//		g.drawString("This is test text 1!",25,SnakeGame.yPixelMaxDimension+10);

	}


}

