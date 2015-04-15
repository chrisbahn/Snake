
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

	private Snake snake;
	private Kibble kibble;
	private Score score;

	DrawSnakeGamePanel(Snake s, Kibble k, Score sc){
		this.snake = s;
		this.kibble = k;
		this.score = sc;
	}

	public Dimension getPreferredSize() {
		return new Dimension(SnakeGame.xPixelMaxDimension, SnakeGame.yPixelMaxDimension);
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

		g.clearRect(100,100,350,350);
		g.drawString("GAME OVER", 150, 150);

		String textScore = score.getStringScore();
		String textHighScore = score.getStringHighScore();
		String newHighScore = score.newHighScore();

		g.drawString("SCORE = " + textScore, 150, 250);

		g.drawString("HIGH SCORE = " + textHighScore, 150, 300);
		g.drawString(newHighScore, 150, 400);
		// BAHN TODO: IMPLEMENT A BOOLEAN CHECK ON NEW HIGH SCORE, THEN PULL THE TRIGGER ON THE FOLLOWING IF LOOP:
//		if (thereIsNewHighScore) {
//			g.drawString("NEW HIGH SCORE!", 150, 325);
//		thereIsNewHighScore = false; // should this be reset here, or at the game-restart method? probably the latter! but if it works here, what the hell
//		}

		g.drawString("press a key to play again", 150, 350);
		g.drawString("Press q to quit the game",150,400);

	}

	private void displayGame(Graphics g) {
		displayGameGrid(g);
		displaySnake(g);
		displayKibble(g);
	}

	private void displayGameGrid(Graphics g) {

		int maxX = SnakeGame.xPixelMaxDimension;
		int maxY= SnakeGame.yPixelMaxDimension;
		int squareSize = SnakeGame.squareSize;

		g.clearRect(0, 0, maxX, maxY);

		g.setColor(Color.darkGray);

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

	private void displaySnake(Graphics g) {

		LinkedList<Point> coordinates = snake.segmentsToDraw();

		//Draw head in grey
		g.setColor(Color.LIGHT_GRAY);
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

		Color bodySegmentColor = new Color(colorA, colorB, colorC);
		if (snake.didEatKibble(kibble)) {
			bodySegmentColor = new Color(randInt(0,255), randInt(0,255), randInt(0,255));
		}
		// TODO If you can figure out how the game reports when a kibble is eaten, you can change the snake colors to whatever kibbleColor is at the time



		boolean colorAUp = true;
		boolean colorBUp = true;
		boolean colorCUp = true;
		int colorAIncrement = 5;
		int colorBIncrement = 7;
		int colorCIncrement = 3;
//		int colorAIncrement = randInt(0,25);
//		int colorBIncrement = randInt(0,25);
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
			g.fillRect((int)p.getX(), (int)p.getY(), SnakeGame.squareSize, SnakeGame.squareSize);
		}

	}



	public static int randInt(int min, int max) {
		Random rand = new Random();
		int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
	}

	private void displayInstructions(Graphics g) {
		g.drawString("SNAKES...",100,100); // TODO Change this quote to one that doesn't sound complainy. Potential addition: Randomize quotes about snakes here?
		g.drawString("...why did it have to be snakes?",125,125);
		g.drawString("- Indiana Jones",300,150);
		g.drawString("Press any key to begin!",100,200);
		g.drawString("Press 'w' to toggle warp walls (on/off)", 100, 250);
		g.drawString("Other instructions go here!",100,300);
		g.drawString("Press 'q' to quit the game",100,375);

	}


}

