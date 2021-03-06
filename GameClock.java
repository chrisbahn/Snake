// Original code by Clara James
// Enhancements made by Christopher Bahn

import java.util.TimerTask;

public class GameClock extends TimerTask {

	Snake snake;
	Kibble kibble;
	Block block;
	Score score;
	DrawSnakeGamePanel gamePanel;

	public GameClock(Snake snake, Kibble kibble, Block block, Score score, DrawSnakeGamePanel gamePanel){
		this.snake = snake;
		this.kibble = kibble;
		this.block = block;
		this.score = score;
		this.gamePanel = gamePanel;
	}

	@Override
	public void run() {
		// This method will be called every clock tick

		int stage = SnakeGame.getGameStage();

		switch (stage) {
			case SnakeGame.BEFORE_GAME: {
				//don't do anything, waiting for user to press a key to start
				break;
			}
			case SnakeGame.DURING_GAME: {
				//
				snake.moveSnake();
				if (snake.didEatKibble(kibble) == true) {
					//tell kibble to update
					kibble.moveKibble(snake, block);
					Score.increaseScore();
				}
				Score.adjustScoreIncrement();
				break;
			}
			case SnakeGame.GAME_OVER: {
				this.cancel();		//Stop the Timer	
				break;
			}
			case SnakeGame.GAME_WON: {
				this.cancel();   //stop timer
				break;
			}


		}

		gamePanel.repaint();		//In every circumstance, must update screen

	}
}
