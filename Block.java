/**
 * Created by christopherbahn on 4/15/15.
 */

import java.util.Random;
public class Block {

    private static int block1X; // Creates X coordinate for impassable block. This is the square number (not pixel)
    private static int block1Y;  //Creates Y coordinate for impassable block. This is the square number (not pixel)
    private int[] blockArrayX; // Creates X coordinate array for the blocks. TODO if block1X works, this is the next step
    private int[] blockArrayY;  //Creates Y coordinate for impassable block. This is the square number (not pixel)
    public static boolean blocksOn = true;

    public Block(Snake s){
        placeBlock(s);
    }

    //keytype 'b' toggles the blocks on or off
    public static void areBlocksOn() {
        if (blocksOn) {
            blocksOn = false;
        } else {
            blocksOn = true;
        }
    }

    protected void placeBlock(Snake s){
        Random rng = new Random();
        boolean blockInSnake = true;
        while (blockInSnake == true && blocksOn == true) {
            //Generate random block location
            block1X = rng.nextInt(SnakeGame.xSquares);
            block1Y = rng.nextInt(SnakeGame.ySquares);
            blockInSnake = s.isSnakeSegment(block1X, block1Y); //Is block inside the Snake? It shouldn't be
        }
    }





    public static int getBlock1X() {
        return block1X;
    }
    public void setBlock1X(int block1X) {
        this.block1X = block1X;
    }
    public static int getBlock1Y() {
        return block1Y;
    }
    public void setBlock1Y(int block1Y) {
        this.block1Y = block1Y;
    }





}
